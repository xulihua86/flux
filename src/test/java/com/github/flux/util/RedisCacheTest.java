package com.github.flux.util;

import org.junit.Test;

import com.github.flux.cache.RedisCache;
import com.github.flux.cache.impl.RedisCacheImpl;

public class RedisCacheTest {

	@Test
	public void setStringTest() {
		RedisCache redis = new RedisCacheImpl();
		String result = redis.setString("test:12", "13");
		System.out.println(result);
	}
	
	@Test
	public void setObjectTest() {
		RedisCache redis = new RedisCacheImpl();
		User user = new User();
		user.setId(1);
		user.setAge(10L);
		user.setName("name");
		
		String result = redis.setObject("test:user:1", user);
		System.out.println(result);
	}
	
	@Test
	public void serializeTest() {
		User user = new User();
		user.setId(1);
		user.setAge(10L);
		user.setName("name");
		byte[] bs = JDKSerializeUtil.serialize(user);
		User user1 = (User)JDKSerializeUtil.deserialize(bs);
		System.out.println(user1);
		
	}
	
	
	
	
}
