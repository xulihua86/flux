package com.github.flux.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.flux.base.BaseMapper;
import com.github.flux.base.BaseServiceImpl;
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
		String key = "valid:" + mobile + ":" + uuid;
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
		user.setToken(token);
		// 指token失效时间
		user.setExpireTime(System.currentTimeMillis() + ConfigUtil.getLongValue("user.token.expireTime"));
		
		// 放入缓存
		key = "user:" + user.getUserid();
		redisCache.setObject(key, user);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("userid", user.getUserid());
		data.put("token", token);
		
		Map<String, Object> map = MapResult.successMap();
		map.put("data", data);
		
		return map;
	}
	

	
	// 更新缓存
	public void updateWithCache(User user){
		String key = "user:" + user.getUserid();
		redisCache.setObject(key, user);
		userMapper.update(user);
	}
	
	
	public User getByIdWithCache(Long userid){
		String key = "user:" + userid;
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
