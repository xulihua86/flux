package com.sinowel.netflow.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {

	private static Logger logger = Logger.getLogger(JedisUtil.class);
	
	public static final String JS_TICKET_KEY = PropertiesUtil.getProperty("jsticket.key");
	public static final String TOKEN_KEY = PropertiesUtil.getProperty("token.key");
	private static JedisPool pool = null;
	
	static {
		String redisUrl = PropertiesUtil.getProperty("redis.url");
		String password = PropertiesUtil.getProperty("redis.password");
		int port = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));
		int timeout = Integer.parseInt(PropertiesUtil.getProperty("redis.timeout"));
		
		// 连接池配置
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		// 最大连接数
		poolConfig.setMaxActive(Integer.parseInt(PropertiesUtil.getProperty("redis.MaxActive")));
		// 最大空闲数
		poolConfig.setMaxIdle(Integer.parseInt(PropertiesUtil.getProperty("redis.MaxIdle")));
		// 最小空闲数
		poolConfig.setMinIdle(Integer.parseInt(PropertiesUtil.getProperty("redis.MinIdle")));
		// 最大等待时间
		poolConfig.setMaxWait(Long.parseLong(PropertiesUtil.getProperty("redis.MaxWait")));
		// 返回连接之前测试是否连接成功
		poolConfig.setTestOnReturn(Boolean.parseBoolean(PropertiesUtil.getProperty("redis.TestOnReturn")));
		// 打开连接之前测试是否连接成功
		poolConfig.setTestOnBorrow(Boolean.parseBoolean(PropertiesUtil.getProperty("redis.TestOnBorrow")));
		// 创建连接池
	    pool = new JedisPool(poolConfig, redisUrl, port, timeout, password);
	}
	
	private JedisUtil(){}
	
	public static Jedis getInstance() {
		try {
			return pool.getResource();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		return null;
	}

	public static void returnResource(Jedis jedis) {
		try {
			pool.returnResource(jedis);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public static synchronized void setJsTicket(String value) {
		Jedis jedis = null;
		try {
			jedis = getInstance();
			jedis.set(JS_TICKET_KEY, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
	}

	public static String getJsTicket() {
		Jedis jedis = null;
		try {
			jedis = getInstance();
			return jedis.get(JS_TICKET_KEY);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
		
		return null;
	}
	
	public static synchronized void setToken(String value) {
		Jedis jedis = null;
		try {
			jedis = getInstance();
			jedis.set(TOKEN_KEY, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
	}
	
	public static String getToken() {
		Jedis jedis = null;
		try {
			jedis = getInstance();
			return jedis.get(TOKEN_KEY);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
		
		return null;
	}
	
	public static synchronized Long setTokenExpire(int seconds) {
		Jedis jedis = null;
		try {
			jedis = getInstance();
			return jedis.expire(TOKEN_KEY, seconds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
		
		return null;
	}
	
	public static synchronized Long setJsTicketExpire(int seconds) {
		Jedis jedis = null;
		try {
			jedis = getInstance();
			return jedis.expire(JS_TICKET_KEY, seconds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
		
		return null;
	}
	
	/**
	 *  查看某个TOKEN_KEY的剩余生存时间,单位【秒】.永久生存或者不存在的都返回-1
	 *  @return
	 *  @author langkai
	 *  @version 1.0
	 *  </pre>
	 *  Created on :2015年1月26日 下午8:33:18
	 *  LastModified:
	 *  History:
	 *  </pre>
	 */
	public static Long getTokenTTL() {
		Jedis jedis = null;
		try {
			jedis = getInstance();
			return jedis.ttl(TOKEN_KEY);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
		
		return null;
	}

	/**
	 *  查看某个JS_TICKET_KEY的剩余生存时间,单位【秒】.永久生存或者不存在的都返回-1
	 *  @return
	 *  @author langkai
	 *  @version 1.0
	 *  </pre>
	 *  Created on :2015年1月26日 下午8:33:18
	 *  LastModified:
	 *  History:
	 *  </pre>
	 */
	public static Long getJsTicketTTL() {
		Jedis jedis = null;
		try {
			jedis = getInstance();
			return jedis.ttl(JS_TICKET_KEY);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
		
		return null;
	}

	/**
	 *  设置redis计数PV<br>
	 *  实际Key：当天：key::yyyyMMdd::PV<br>
	 *  		累计：key::PV<br>
	 *  @param key 按活动和功能区分的key
	 *  @param isCurrDay 计数方式：true:当天计数 false:一直累计
	 *  @return -1:出现异常 other:计数值
	 *  @author langkai
	 *  @version 1.0
	 *  </pre>
	 *  Created on :2015年4月17日 上午11:12:58
	 *  LastModified:
	 *  History:
	 *  </pre>
	 */
	public Long setCounter(String key, boolean isCurrDay) {
		return setCounter(key, isCurrDay, Constant.ONE, Constant.STR_NULL);
	}
	
	/**
	 *  设置redis计数（PV、UV）<br>
	 *  实际Key：当天：key::yyyyMMdd::PV<br>
	 *  		累计：key::PV<br>
	 *  @param key 按活动和功能区分的key
	 *  @param isCurrDay 计数方式：true:当天计数 false:一直累计
	 *  @param counterType 计数类型：0:PV和UV 1:PV 2:UV
	 *  @param members 用于UV重复判断的依据（如openID）
	 *  @return -1:出现异常 other:计数值
	 *  @author langkai
	 *  @version 1.0
	 *  </pre>
	 *  Created on :2015年4月17日 上午11:12:58
	 *  LastModified:
	 *  History:
	 *  </pre>
	 */
	public Long setCounter(String key, boolean isCurrDay, String counterType, String... members) {
		Jedis jedis = null;
		try {
			// key不能为空
			if (key == null || key.length() == 0) {
				throw new Exception();
			}
			
			// 取得redis对象
			jedis = JedisUtil.getInstance();
			
			boolean counterFlag = true;
			if (Constant.ZERO.equals(counterType) || Constant.TWO.equals(counterType)) {
				// 访问信息流水, 定期删除
				// sadd添加 scard集合数 del删除
				Long recordCount = jedis.sadd(key, members);
				if (recordCount == null || recordCount == 0) {
					counterFlag = false;
				}
			}
			
			StringBuilder strBuf = new StringBuilder();
			if (isCurrDay) {
				// 按天计数
				String strDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
				strBuf.append(key).append(Constant.STR_COLON_TWO).append(strDate).append(Constant.STR_COLON_TWO);
			} else {
				strBuf.append(key).append(Constant.STR_COLON_TWO);
			}
			
			String[] keyArr = null;
			// PV 和 UV
			if (Constant.ZERO.equals(counterType)) {
				keyArr = new String[2];
				keyArr[0] = strBuf.append(Constant.PV).toString();
				keyArr[1] = strBuf.append(Constant.UV).toString();
			} else if (Constant.ONE.equals(counterType)) {
				// PV
				keyArr = new String[1];
				keyArr[0] = strBuf.append(Constant.PV).toString();
			} else if (Constant.TWO.equals(counterType)) {
				// UV
				keyArr = new String[1];
				keyArr[1] = strBuf.append(Constant.UV).toString();
			} else {
				return null;
			}
			
			// 分别保存计数
			for (String keyTmp : keyArr) {
				// 排除UV有重复情况
				if (counterFlag) {
					return jedis.incr(keyTmp);
				}
			}
		} catch(Exception e) {
			logger.error(Constant.STR_MINUS_1, e);
		} finally {
			// 资源回收
			JedisUtil.returnResource(jedis);
		}
		
		return null;
	}
	
	public static void main(String[] args) {
//		ApplicationContext ac =  new ClassPathXmlApplicationContext("classpath:spring-mvc.xml");
//		JedisUtil.getInstance().del(JedisUtil.JS_TICKET_KEY);
//		JedisUtil.getInstance().del(JedisUtil.TOKEN_KEY);
		System.out.println(JedisUtil.getJsTicket());
		System.out.println(JedisUtil.getToken());
//		System.out.println(JedisUtil.getInstance().get("sdf"));
//		System.out.println(JedisUtil.getInstance().ttl("jsticket"));
//		JedisUtil.getInstance().set("wechatJob", Constant.ZERO);
	}
}
