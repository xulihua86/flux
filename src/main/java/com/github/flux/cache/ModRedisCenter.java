package com.github.flux.cache;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 根据key hash 取模获取 释放redis链接
 */
public class ModRedisCenter {

	public static final Logger logger = LoggerFactory.getLogger(ModRedisCenter.class);
	
	private static ModRedisCenter instance = null;
	
	public static final String redisHostPort = "127.0.0.1:6379";  // 127.0.0.1:8282,127.0.0.1:8283
	
	private ModRedisCenter() {
		initialPool();
	}

	/**
	 * 实例化服务池
	 * 
	 * @param zkPath
	 *            内容是该节点下配置的nutcracker服务列表
	 * @return
	 */
	public synchronized static ModRedisCenter getInstance() {
		if (instance == null){
			instance = new ModRedisCenter();
		}
		return instance;
	}

	private List<JedisPool> listJedisPool = null;
	private int MAX_ACTIVE = 512;
	private int MAX_IDLE = 256;
	private int MAX_WAIT = 5120;
	private int TIME_OUT = 3000;
	private boolean TEST_ON_BORROW = false;
	private boolean TEST_WHILE_IDLE = false;

	/**
	 * 初始化redis 池
	 * 
	 * @param path
	 */
	public void initialPool() {
		try {
			//如果不为null 先清理再建立
			if(null!=listJedisPool&&listJedisPool.size()>0){
				for(int i=0;i<listJedisPool.size();i++){
					listJedisPool.get(i).destroy();
				}
			}
			listJedisPool = new ArrayList<JedisPool>();
			String serverString = redisHostPort;
			logger.info("serverString={}",serverString);
			String serverArray[] = serverString.split("[,]");
			JedisPool pool = null;
			JedisPoolConfig config = null;
			String redisInfo[] = null;
			for (String server : serverArray) {
				config = new JedisPoolConfig();
				config.setMaxTotal(MAX_ACTIVE);
				config.setMaxIdle(MAX_IDLE);
				config.setMaxWaitMillis(MAX_WAIT);
				config.setTestOnBorrow(TEST_ON_BORROW);
				config.setTestWhileIdle(TEST_WHILE_IDLE);
				redisInfo = server.split("[:]");
				pool = new JedisPool(config, redisInfo[0],
						Integer.valueOf(redisInfo[1].toString()), TIME_OUT);
				listJedisPool.add(pool);
			}
			logger.info("init  listJedisPool  ok");

		} catch (Exception e) {
			logger.error("initialPool error", e);
		}
	}

	/**
	 * 根据key 获取redis实例
	 * 
	 * @param modkey hash用的key
	 * @return
	 */
	public Jedis getJedisByKey(String modkey) {
		Jedis jedis = null;
		try {
			int tmpHashCode = Math.abs(modkey.hashCode());
			int result = tmpHashCode % listJedisPool.size();
			jedis = listJedisPool.get(result).getResource();
		} catch (Exception e) {
			logger.error("getJedisByKey error=" + modkey, e);
		}
		return jedis;
	}

	/**
	 * 根据key释放链接
	 * @param modkey hash用的key
	 * @param jedis
	 */
	public void releaseJedisByKey(String modkey, Jedis jedis) {
		try {
			int tmpHashCode = Math.abs(modkey.hashCode());
			int result = tmpHashCode % listJedisPool.size();
			listJedisPool.get(result).returnResource(jedis);
		} catch (Exception e) {
			logger.error("releaseJedisByKey error=" + modkey, e);
		}
	}
	
	/**
	 * 根据key释放损坏链接
	 * @param modkey hash用的key
	 * @param jedis
	 */
	public void releaseBrokenJedisByKey(String modkey, Jedis jedis) {
		try {
			int tmpHashCode = Math.abs(modkey.hashCode());
			int result = tmpHashCode % listJedisPool.size();
			listJedisPool.get(result).returnBrokenResource(jedis);;
		} catch (Exception e) {
			logger.error("releaseJedisByKey error=" + modkey, e);
		}
	}
	
	/**
	 * 内部接口也是回调接口，只定义抽象方法
	 * @author haoxw
	 * @since 2014/4/22
	 */
	public interface JedisCallback {
		Object execute(Jedis jedis) throws Exception;
	}

	/**
	 * 具体动作 并自动返回连接 需要具体实现
	 * @param callback
	 * @param modkey
	 * @return
	 */
	public Object getResult(JedisCallback callback,String modkey)  {
		Jedis jedis = getJedisByKey(modkey);
		try {
			return callback.execute(jedis);
		}catch(Exception e){
			logger.error("releaseJedisByKey error=" + modkey, e);
			releaseBrokenJedisByKey(modkey,jedis);
			throw new RuntimeException("Redis getResult exception", e);
		} finally {
			if (jedis != null)
				releaseJedisByKey(modkey,jedis);
		}
	}

	public List<JedisPool> getListJedisPool() {
		return listJedisPool;
	}

	public void setListJedisPool(List<JedisPool> listJedisPool) {
		this.listJedisPool = listJedisPool;
	}

	
	
}
