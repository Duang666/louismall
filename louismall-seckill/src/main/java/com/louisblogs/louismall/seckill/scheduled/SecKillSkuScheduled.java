package com.louisblogs.louismall.seckill.scheduled;

import com.louisblogs.louismall.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author ：luqi
 * @description：秒杀商品定时上架
 * @date ：2021/7/1 17:43
 */

@Slf4j
@Service
public class SecKillSkuScheduled {

	private final String UPLOAD_LOCK = "seckill:upload:lock";

	@Autowired
	SeckillService seckillService;

	@Autowired
	RedissonClient redissonClient;

	//TODO 幂等性处理
//    //异步任务 + 下
//    @Async
	//定时任务
	@Scheduled(cron = "*/3 * * * * ?")
	public void uploadSeckillSkuLatest3Days() {
		log.info("上架秒杀商品信息....");
		//1、重复上架无需处理
		//加分布式锁 所有的业务执行完成，状态已经更新完成。释放，所以后期他人获取到就会拿到最新的状态(原子性)
		RLock lock = redissonClient.getLock(UPLOAD_LOCK);
		lock.lock(10, TimeUnit.MINUTES);
		try {
			seckillService.uploadSeckillSkuLatest3Days();
		} finally {
			lock.unlock();
		}
	}

}
