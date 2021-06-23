package com.louisblogs.louismall.member.service.impl;

import com.louisblogs.louismall.member.dao.MemberLevelDao;
import com.louisblogs.louismall.member.entity.MemberLevelEntity;
import com.louisblogs.louismall.member.exception.PhoneExistException;
import com.louisblogs.louismall.member.exception.UsernameExistException;
import com.louisblogs.louismall.member.vo.MemberRegistVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.common.utils.Query;

import com.louisblogs.louismall.member.dao.MemberDao;
import com.louisblogs.louismall.member.entity.MemberEntity;
import com.louisblogs.louismall.member.service.MemberService;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

	@Autowired
	MemberLevelDao memberLevelDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<MemberEntity> page = this.page(
				new Query<MemberEntity>().getPage(params),
				new QueryWrapper<MemberEntity>()
		);

		return new PageUtils(page);
	}

	@Override
	public void regist(MemberRegistVo vo) {
		MemberDao memberDao = this.baseMapper;
		MemberEntity entity = new MemberEntity();

		//设置默认等级
		MemberLevelEntity levelEntity = memberLevelDao.getDefaultLevel();
		entity.setLevelId(levelEntity.getId());

		//检查用户名和手机号是否唯一。为了让controller能感知异常,异常机制
		checkPhoneUnique(vo.getPhone());
		checkUsernameUnique(vo.getUserName());

		entity.setMobile(vo.getPhone());
		entity.setUsername(vo.getUserName());

		//密码要进行加密存储
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encode = passwordEncoder.encode(vo.getPassword());
		entity.setPassword(encode);

		//TODO 其他的默认信息

		memberDao.insert(entity);
	}

	@Override
	public void checkPhoneUnique(String phone) throws PhoneExistException {
		MemberDao memberDao = this.baseMapper;
		Integer mobile = memberDao.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone));
		if (mobile > 0) {
			throw new PhoneExistException();
		}
	}

	@Override
	public void checkUsernameUnique(String username) throws UsernameExistException {
		MemberDao memberDao = this.baseMapper;
		Integer mobile = memberDao.selectCount(new QueryWrapper<MemberEntity>().eq("username", username));
		if (mobile > 0) {
			throw new UsernameExistException();
		}
	}

}