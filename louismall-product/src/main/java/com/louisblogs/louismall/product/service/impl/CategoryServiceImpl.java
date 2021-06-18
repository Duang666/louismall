package com.louisblogs.louismall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.louisblogs.louismall.product.entity.CategoryBrandRelationEntity;
import com.louisblogs.louismall.product.service.CategoryBrandRelationService;
import com.louisblogs.louismall.product.vo.front.Catelog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.common.utils.Query;

import com.louisblogs.louismall.product.dao.CategoryDao;
import com.louisblogs.louismall.product.entity.CategoryEntity;
import com.louisblogs.louismall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sun.plugin2.util.SystemUtil;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

//	private Map<String, Object> cache = new HashMap<>();

//	@Autowired
//	CategoryDao categoryDao;

	@Autowired
	CategoryBrandRelationService categoryBrandRelationService;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	RedissonClient redisson;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<CategoryEntity> page = this.page(
				new Query<CategoryEntity>().getPage(params),
				new QueryWrapper<CategoryEntity>()
		);

		return new PageUtils(page);
	}

	//查出所有分类以及子分类，以树形结构组装起来
	@Override
	public List<CategoryEntity> listWithTree() {
		//1、查出所有分类
		List<CategoryEntity> entities = baseMapper.selectList(null);

		//2、组装成父子的树形结构
		//2.1、找到所有的一级分类
		List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity ->
				categoryEntity.getParentCid() == 0
		).map((menu) -> {
			menu.setChildren(getChildren(menu, entities));
			return menu;
		}).sorted((menu1, menu2) -> {
			//return menu1.getSort() - menu2.getSort();   报空指针异常
			return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
		}).collect(Collectors.toList());

		return level1Menus;
	}

	//递归查找所有菜单子菜单
	private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {
		List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
			return categoryEntity.getParentCid().equals(root.getCatId());
		}).map(categoryEntity -> {
			//1、找到子菜单
			categoryEntity.setChildren(getChildren(categoryEntity, all));
			return categoryEntity;
		}).sorted((menu1, menu2) -> {
			//2、菜单的排序
			return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
		}).collect(Collectors.toList());

		return children;
	}

	//删除菜单
	@Override
	public void removeMenuByIds(List<Long> asList) {
		//TODO 1、检查当前删除的菜单，是否被别的地方引用

		//

		baseMapper.deleteBatchIds(asList);
	}

	//找到catelogId的完整路径：[父/子/孙]
	@Override
	public Long[] findCatelogPath(Long catelogId) {
		List<Long> paths = new ArrayList<>();
		List<Long> parentPath = findParentPath(catelogId, paths);

		//逆序转换
		Collections.reverse(parentPath);

		return parentPath.toArray(new Long[parentPath.size()]);
	}

	private List<Long> findParentPath(Long catelogId, List<Long> paths) {
		//1、收集当前节点id
		paths.add(catelogId);
		CategoryEntity byId = this.getById(catelogId);
		if (byId.getParentCid() != 0) {
			findParentPath(byId.getParentCid(), paths);
		}
		return paths;
	}

	/**
	 * 级联更新所有关联的数据
	 * 1、同时进行多种缓存操作 @Caching
	 * 2、指定删除某个分区下的所有数据 @CacheEvict(value = "category", allEntries = true)
	 * 3、储存同一类型的数据，都可以指定成同一个分区。分区名默认就是缓存的前缀
	 *
	 * @param category
	 * @CacheEvict:失效模式
	 */
//	@Caching(evict={
//		@CacheEvict(value = "category", key = "'getLevel1Categorys'"),
//		@CacheEvict(value = "category", key = "'getCatalogJson'")
//	})
	@CacheEvict(value = "category", allEntries = true)
	@Transactional
	@Override
	public void updateCascade(CategoryEntity category) {
		this.updateById(category);
		categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());

		//同时修改缓存中的数据
		//redis.del("catalogJson");  等待下次主动查询进行更新
	}

	//渲染首页数据
	//每一个需要缓存的数据我们都来指定要放到那个名字的缓存。【缓存的分区（按照业务类型分）】
	//默认行为：
	//1）如果缓存中有，方法不用调用
	//2）key默认自动生成：缓存的名字::SimpleKey []（自动生成的key值）
	//3）缓存的value的值，默认使用jdk序列化机制，将序列化后的数据存到redis
	//4）默认ttl时间 -1；
	//自定义：
	//1）指定生成的缓存使用的key；  key属性指定，接受一个spgl
	//2）指定缓存的数据的存话时间    配置文件中修改ttl
	//3）将数据保存为json格式
	@Cacheable(value = {"category"}, key = "#root.method.name", sync = true)
	//代表当前方法的结果需要缓存，如果缓存中有，方法不用调用。如果缓存中没有，会调用方法，最后将方法的结果调用缓存
	@Override
	public List<CategoryEntity> getLevel1Categorys() {
		System.out.println("getLevel1Categorys...");
		long l = System.currentTimeMillis();
		List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
		return categoryEntities;
	}

	@Cacheable(value = {"category"}, key = "#root.methodName", sync = true)
	@Override
	public Map<String, List<Catelog2Vo>> getCatalogJson() {
		System.out.println("查询了数据库。。。。。");
		List<CategoryEntity> selectList = baseMapper.selectList(null);
		//1、查出所有1级分类
		List<CategoryEntity> level1Categorys = getParent_cid(selectList, 0L);

		//2、封装数据
		Map<String, List<Catelog2Vo>> parent_cid = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
			//1、每一个的一级分类，查到一级分类的二级分类
			List<CategoryEntity> categoryEntities = getParent_cid(selectList, v.getCatId());
			//2、封装上面的结果
			List<Catelog2Vo> catelog2Vos = null;
			if (categoryEntities != null) {
				catelog2Vos = categoryEntities.stream().map(l2 -> {
					Catelog2Vo catelog2Vo = new Catelog2Vo("v.getCatId().toString()", null, "item.getCatId().toString()", l2.getName());

					//1、找当前二级分类的三级分类
					List<CategoryEntity> level3Catelog = getParent_cid(selectList, l2.getCatId());
					if (level3Catelog != null) {
						List<Catelog2Vo.Catalog3Vo> collect = level3Catelog.stream().map(l3 -> {
							//2、封装成指定格式
							Catelog2Vo.Catalog3Vo catalog3Vo = new Catelog2Vo.Catalog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
							return catalog3Vo;
						}).collect(Collectors.toList());
						catelog2Vo.setCatalog3List(collect);
					}

					return catelog2Vo;
				}).collect(Collectors.toList());
			}
			return catelog2Vos;
		}));
		return parent_cid;
	}

	//TODO 产生堆外内存溢出：OutOfDirectMemoryError
	//1、springboot2.0以后默认使用lettuce作为操作redis的客户端，它使用netty进行网络通信
	//2、lettuce的bug导致netty堆外内存溢出 -Xmx300m netty如果没有指定堆外内存，默认使用-Xmx300m
	//可以通过-Dio.netty.maxDirectMemory进行设置
	//解决方案：不能使用-Dio.netty.maxDirectMemory只去调大堆外内存
	//1）升级lettuce客户端    2）切换使用jedis
	//@Override
	public Map<String, List<Catelog2Vo>> getCatalogJson2() {
		//给缓存中放json字符串， 拿出的json字符串， 还用逆转为能用的对象类型; [序列化与反序列化]

		/**
		 * 1、空结果缓存：解决缓存穿透
		 * 2、设置过期时间（加随机值）：解决缓存雪崩
		 * 3、加锁：解决缓存击穿
		 */

		//1、加入缓存逻辑，缓存中存的数据是json字符串
		//JSON跨语言，跨平台兼容
		String catalogJson = redisTemplate.opsForValue().get("catalogJson");
		if (StringUtils.isEmpty(catalogJson)) {
			//2、缓存中没有，查询数据库
			System.out.println("缓存不命中。。。。查询数据库。。。。");
			Map<String, List<Catelog2Vo>> catalogJsonFromDb = getCatalogJsonFromDbWithRedisLock();

			return catalogJsonFromDb;
		}

		System.out.println("缓存命中。。。。直接返回。。。。");
		//转为我们指定的对象
		Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catelog2Vo>>>() {
		});
		return result;
	}

	/**
	 * 缓存里面的数据如何和数据库保持一致
	 * 缓存数据一致性
	 * 1）双写模式
	 * 2）失效模式
	 *
	 * @return
	 */
	public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedissonLock() {

		//1、锁得名字：锁的粒度，越细越快。
		//锁的粒度：具体缓存的是某个数据，11号商品；    produck-11-lock
		RLock lock = redisson.getLock("CatalogJson-lock");
		lock.lock();

		Map<String, List<Catelog2Vo>> dataFromDb;
		try {
			dataFromDb = getDataFromDb();
		} finally {
			lock.unlock();
		}
		return dataFromDb;

	}

	public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedisLock() {
		//1、占分布式锁。去redis占坑
		String uuid = UUID.randomUUID().toString();
		Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 300, TimeUnit.SECONDS);
		if (lock) {
			System.out.println("获取分布式锁成功。。。");
			//加锁成功。。执行业务
			//2、设置过期时间，必须和加锁是同步的，原子的
//			redisTemplate.expire("lock",30,TimeUnit.SECONDS);
			Map<String, List<Catelog2Vo>> dataFromDb;
			try {
				dataFromDb = getDataFromDb();
			} finally {
				//获取值对比+对比成功删除=原子操作  lua脚本解锁
//			String lockValue = redisTemplate.opsForValue().get("lock");
//			if (uuid.equals(lockValue)){
//				//删除自己的锁
//				redisTemplate.delete("local");  //删除锁
//			}
				String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
				//删除锁
				Long lock1 = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList("lock"), uuid);
			}
			return dataFromDb;

		} else {
			//加锁失败。。重试。 synchronized ()
			//休眠100ms重试
			System.out.println("获取分布式锁失败。。。等待重试");
			try {
				Thread.sleep(200);
			} catch (Exception e) {

			}
			return getCatalogJsonFromDbWithRedisLock(); //自旋的方式
		}
	}

	private Map<String, List<Catelog2Vo>> getDataFromDb() {
		String catalogJson = redisTemplate.opsForValue().get("catalogJson");
		if (!StringUtils.isEmpty(catalogJson)) {
			//缓存不为null直接返回
			Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catelog2Vo>>>() {
			});
			return result;
		}
		System.out.println("查询了数据库。。。。。");

		List<CategoryEntity> selectList = baseMapper.selectList(null);

		//1、查出所有1级分类
		List<CategoryEntity> level1Categorys = getParent_cid(selectList, 0L);

		//2、封装数据
		Map<String, List<Catelog2Vo>> parent_cid = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
			//1、每一个的一级分类，查到一级分类的二级分类
			List<CategoryEntity> categoryEntities = getParent_cid(selectList, v.getCatId());
			//2、封装上面的结果
			List<Catelog2Vo> catelog2Vos = null;
			if (categoryEntities != null) {
				catelog2Vos = categoryEntities.stream().map(l2 -> {
					Catelog2Vo catelog2Vo = new Catelog2Vo("v.getCatId().toString()", null, "item.getCatId().toString()", l2.getName());

					//1、找当前二级分类的三级分类
					List<CategoryEntity> level3Catelog = getParent_cid(selectList, l2.getCatId());
					if (level3Catelog != null) {
						List<Catelog2Vo.Catalog3Vo> collect = level3Catelog.stream().map(l3 -> {
							//2、封装成指定格式
							Catelog2Vo.Catalog3Vo catalog3Vo = new Catelog2Vo.Catalog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
							return catalog3Vo;
						}).collect(Collectors.toList());
						catelog2Vo.setCatalog3List(collect);
					}

					return catelog2Vo;
				}).collect(Collectors.toList());
			}

			return catelog2Vos;
		}));

		//3、查到的数据再放入缓存，将对象转为json放在缓存中
		String s = JSON.toJSONString(parent_cid);
		redisTemplate.opsForValue().set("catalogJson", s, 1, TimeUnit.DAYS);
		return parent_cid;
	}

	//从数据库查询并封装分类数据
	public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithLocalLock() {

//		//1、如果缓存中就有缓存的
//		Map<String, List<Catelog2Vo>> catalogJson = (Map<String, List<Catelog2Vo>>) cache.get("catalogJson");
//		if (cache.get("catalogJson")==null) {
//			//调用业务  xxxx
//			//返回数据，又放入缓存
//			cache.put("catalogJson",parent_cid);
//		}
//		return catalogJson;

		//只要是同一把锁，就能锁住需要这个锁的所有线程
		//1、synchronized (this): SpringBoot所有的组件在容器中都是单利的。
		//TODO 本地锁：synchronized，JUC（Losk）,在分布式情况下，想要锁住所有，必须使用分布式锁

		//同步代码块
		synchronized (this) {
			//得到锁以后，我们应该再去缓存中确定一 次。如果没有才需要继续查询
			return getDataFromDb();
		}
	}

	private List<CategoryEntity> getParent_cid(List<CategoryEntity> selectList, Long parent_cid) {
		List<CategoryEntity> collect = selectList.stream().filter(item -> item.getParentCid().equals(parent_cid)).collect(Collectors.toList());
		//return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", v.getCatId()));
		return collect;
	}
}



