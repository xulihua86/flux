package com.github.flux.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
import com.github.flux.base.FluxHelp;
import com.github.flux.cache.RedisCache;
import com.github.flux.entity.User;
import com.github.flux.mapper.UserMapper;
import com.github.flux.service.UserService;
import com.github.flux.util.ConfigUtil;
import com.github.flux.util.JDKSerializeUtil;
import com.github.flux.util.RandomUtil;
import com.github.flux.util.result.BaseResult;
import com.github.flux.util.result.MapResult;

@Transactional
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	
	public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private RedisCache redisCache; 
	
	@Override
	public BaseMapper<User> getBaseMapper() {
		return userMapper;
	}

	// 手机， uuid, 验证码登录
	public Map<String, Object> login(String mobile, String uuid, String code){
		
		// 验证
		String key = FluxHelp.getValidCodeKey(mobile, uuid);
		String value = redisCache.getString(key);
		
		if(StringUtils.isEmpty(value)) {
			return MapResult.initMap(BaseResult.INVALID_CODE.getCode(), BaseResult.INVALID_CODE.getMsg());
		}
		if(!value.equals(code)) {
			return MapResult.initMap(BaseResult.VALIDCODE_ERROR.getCode(), BaseResult.VALIDCODE_ERROR.getMsg());
		}
		
		// 从库里查找用
		User user = userMapper.getByMobile(mobile);
		if(user == null) {
			user = new User();
			user.setMobile(mobile);
			user.setAccount(0l);
			user.setAddSum(0l);
			user.setCreateTime(System.currentTimeMillis());
			user.setFrozenAccount(0l);
			user.setGender(2);
			user.setLocked(0);
			user.setSubSum(0l);
			userMapper.add(user);
		}
		
		String token = RandomUtil.getUUID();
		// 用户登录失效时间
		String tokenKey = FluxHelp.gerTokenKey(user.getUserid());
		redisCache.setString(tokenKey, token, ConfigUtil.getIntValue("user.token.expireTime"));
	
		// 用户缓存
		key = FluxHelp.getUserKey(user.getUserid());
		redisCache.setObject(key, user);
		
		// 返回值
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("userid", user.getUserid());
		data.put("token", token);
		
		Map<String, Object> map = MapResult.successMap();
		map.put("data", data);
		
		return map;
	}
	
	// 更新用户信息
	public void update(User user){
		
		if(user == null || user.getUserid() == null) return;
		Long userid = user.getUserid();
		// User userdb = userMapper.getById(userid);
		User userdb = this.getByIdWithCache(userid);
		if(user.getGender() != null) {
			userdb.setGender(user.getGender());
		}
		if(user.getIndustry() != null) {
			userdb.setIndustry(user.getIndustry());
		}
		if(user.getNickname() != null) {
			userdb.setNickname(user.getNickname());
		}
		if(user.getLogo() != null) {
			userdb.setLogo(user.getLogo());
		}
		if(user.getYear() != null ) {
			userdb.setYear(user.getYear());
		}
		if(user.getSignature() != null) {
			userdb.setSignature(user.getSignature());
		}
		
		
		if(user.getAccount() != null) {
			userdb.setAccount(user.getAccount());
		}
		if(user.getAddSum() != null) {
			userdb.setAddSum(user.getAddSum());
		}
		if(user.getSubSum() != null) {
			userdb.setSubSum(user.getSubSum());
		}
		if(user.getFrozenAccount() != null) {
			userdb.setFrozenAccount(user.getFrozenAccount());
		}
		
		// 更新db
		userMapper.update(userdb);
		logger.info("更新 user db {} 成功", userid);
		// 更新缓存
		String key = FluxHelp.getUserKey(user.getUserid());
		redisCache.setObject(key, userdb);
		logger.info("更新 user cache {} 成功", userid);
	}
	
	// 先走cache, 在走db
	public User getByIdWithCache(Long userid){
		String key = FluxHelp.getUserKey(userid);
		byte[] bs = redisCache.getObject(key);
		User user = (User)JDKSerializeUtil.deserialize(bs);
		if(user == null) {
			user = userMapper.getById(userid);
			if(user != null) {
				redisCache.setObject(key, user);
			}
		}
		return user;
	}

}
