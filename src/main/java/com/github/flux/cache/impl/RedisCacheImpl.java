package com.github.flux.cache.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.flux.cache.ModRedisCenter;
import com.github.flux.cache.ModRedisCenter.JedisCallback;
import com.github.flux.cache.RedisCache;
import com.github.flux.util.JDKSerializeUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;


public class RedisCacheImpl implements RedisCache {

	private static final Logger log = LoggerFactory.getLogger(RedisCacheImpl.class);
	
	private ModRedisCenter modRedisCenter;
	
	public RedisCacheImpl(){
		modRedisCenter =  ModRedisCenter.getInstance();
	}

	@Override
	public boolean addWithSortedSet(final String key, final long score, final String member) {
		boolean b = (Boolean) modRedisCenter.getResult(new JedisCallback() {
			@Override
			public Object execute(Jedis jedis) throws Exception {
				try {
					jedis.zadd(key, score, member);
				} catch (Exception e) {
					log.error("", e);
					return false;
				}
				return true;
			}
		}, key);
		return b;
	}

	@Override
	public boolean delKey(final String key) {
		boolean b = false;
		try {
			b = (Boolean) modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					long delkey = jedis.del(key);
					log.info("delkey {} key:{}", delkey, key);
					return true;
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}
	
	
	 /** 
     * 删除模糊匹配的key 
     * @param likeKey 模糊匹配的key 
     * @return 删除成功的条数 
     *  Set<String> keys = _jedis.keys(likeKey + "*");  
     */  	
	public long delKeysLike(final String likeKey) {  
		long count = 0;
		List<JedisPool> list = modRedisCenter.getListJedisPool();
		for(JedisPool pool : list) {
			Jedis jedis = null;
			try{
				jedis = pool.getResource();
				Set<String> keys = jedis.keys(likeKey + "*"); 
				count += jedis.del(keys.toArray(new String[keys.size()])); 
				pool.returnResource(jedis);
			}catch(Exception ex) {
				log.error("", ex);
				pool.returnBrokenResource(jedis);
			}
		}
		return count;
    }  
	
	
	// ---------------------------------------------zset ---------------------------------------------------
	@Override
	public boolean addWithSortedSet(final String key, final Map<String, Object> map) {
		boolean b;
		b = (Boolean) modRedisCenter.getResult(new JedisCallback() {
			@Override
			public Object execute(Jedis jedis) throws Exception {
				try {
					for (Object o : map.entrySet()) {
						Map.Entry entry = (Map.Entry) o;
						jedis.zadd(key,
								Long.valueOf(entry.getValue().toString()),
								entry.getKey().toString());
					}
				} catch (Exception e) {
					log.error("", e);
					return false;
				}
				return true;
			}
		}, key);
		return b;
	}

	@Override
	public boolean rebuildZset(final String key, final Map<String, Object> map) {
		boolean b;
		b = (Boolean) modRedisCenter.getResult(new JedisCallback() {
			@Override
			public Object execute(Jedis jedis) throws Exception {
				try {
					Long i = jedis.del(key); // 先把key删除，然后重建
					log.info("delete key:{}, resutl:{}", key, i);
					for (Object o : map.entrySet()) {
						Map.Entry entry = (Map.Entry) o;
						jedis.zadd(key,
								Long.valueOf(entry.getValue().toString()),
								entry.getKey().toString());
					}
				} catch (Exception e) {
					log.error("", e);
					return false;
				}
				return true;
			}
		}, key);
		return b;
	}

	@Override
	public boolean delZsetResId(final String key, final String resId) {
		boolean b;
		b = (Boolean) modRedisCenter.getResult(new JedisCallback() {
			@Override
			public Object execute(Jedis jedis) throws Exception {
				try {
					jedis.zrem(key, resId);
				} catch (Exception e) {
					log.error("", e);
					return false;
				}
				return true;
			}
		}, key);
		return b;
	}

	/**
	 * 根据范围删除内容
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Long zremrangeByScore(final String key, final long min, final long max) {
		Long b = null;
		try {
			b =  (Long)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.zremrangeByScore(key, min, max);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}

	@Override
	public boolean delZsetIds(final String key, final List<String> resIds) {
		boolean b;
		b = (Boolean) modRedisCenter.getResult(new JedisCallback() {
			@Override
			public Object execute(Jedis jedis) throws Exception {
				try {

					for (String resId : resIds) {
						jedis.zrem(key, resId);
					}

				} catch (Exception e) {
					log.error("", e);
					return false;
				}
				return true;
			}
		}, key);

		return b;
	}

	@Override
	public List<String> getZsetList(final String key, final int start, final int count) {
		List<String> returnList = null;
		try {
			Object obj = modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					long total = jedis.zcard(key);
					if (total <= 0)
						return null;
					if (start > total - 1)
						return null;
					Set<String> set;
					List<String> list = new ArrayList<String>();
					if (start == 0 && count == -1) {
						set = jedis.zrange(key, 0, total - 1);
						if (set != null && set.size() > 0) {
							list = (new ArrayList<String>(set));
						}
					} else {
						set = jedis.zrange(key, start, start + count - 1);
						if (set != null && set.size() > 0) {
							list = new ArrayList<String>(set);
						}
					}
					return list;
				}
			}, key);

			if (obj != null) {
				returnList = (List) obj;
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return returnList;
	}

	@Override
	public List<String> getZsetListByScore(final String key, final long startscore, final long endscore, final int offset, final int count) {
		List<String> returnList = null;
	    try {
	      Object obj = modRedisCenter.getResult(new JedisCallback() {
	        @Override
	        public Object execute(Jedis jedis) throws Exception {
	          long total = jedis.zcard(key);
	          if (total <= 0)
	            return null;
	          List<String> list = new ArrayList<String>();
	          Set<String> set = jedis.zrangeByScore(key, startscore, endscore, offset, count);
	          if (set != null && set.size() > 0) {
	            list = new ArrayList<String>(set);
	          }
	          return list;
	        }
	      }, key);
	      
	      if (obj != null) {
	    	  returnList = (List) obj;
	      }
	    } catch (Exception e) {
	      log.error("", e);
	    }
		return returnList;
	}

	@Override
	public List<String> getZsetByScoreDesc(final String key, final long max, final long min, final int offset, final int count) {
		
		List<String> returnList = null;
	    try {
	      Object obj = modRedisCenter.getResult(new JedisCallback() {
	        @Override
	        public Object execute(Jedis jedis) throws Exception {
	          long total = jedis.zcard(key);
	          if (total <= 0)
	            return null;
	          List<String> list = new ArrayList<String>();
	          Set<String> set = jedis.zrevrangeByScore(key, max, min, offset, count);
	          if (set != null && set.size() > 0) {
	        	  list = new ArrayList<String>(set);
	          }
	          return list;
	        }
	      }, key);
	      if (obj != null) {
	    	  returnList = (List) obj;
	      }
	    } catch (Exception e) {
	      log.error("", e);
	    }
	    return returnList;
	}

	@Override
	public List<Zset> getZsetWithScoreByScoreDesc(final String key, final long max, final long min, final int offset, final int count) {
		
		List<Zset> returnList = null;
	    try {
	      Object obj = modRedisCenter.getResult(new JedisCallback() {
	        @Override
	        public Object execute(Jedis jedis) throws Exception {
	          long total = jedis.zcard(key);
	          if (total <= 0)
	            return null;
	          // IdListExt idListBean = new IdListExt();
	          List<Zset> list = new ArrayList<Zset>();
	          Set<Tuple> set = jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
	          // List<IdScore> listIdScore = new ArrayList<>();
	          if (set != null && set.size() > 0) {
	            for (Tuple t : set) {
	              Zset zset = new Zset();
	              zset.setScore(new Double(t.getScore()).longValue());
	              zset.setResId(t.getElement());
	              list.add(zset);
	            }
	          }
	          return list;
	        }
	      }, key);
	      if (obj != null) {
	        returnList = (List) obj;
	      }
	    } catch (Exception e) {
	      log.error("", e);
	    }
		return returnList;
	}
	
	public Set<Tuple> revrangeTupleByScoreWithSortedSet(final String key, final double max, final double min) {
		Set<Tuple> b = null;
		try {
			b =  (Set<Tuple>)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.zrevrangeByScoreWithScores(key, max, min);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}


	@Override
	public int getZsetCount(final String key) {
		int count = 0;
	    try {
	      Object obj = modRedisCenter.getResult(new JedisCallback() {
	        @Override
	        public Object execute(Jedis jedis) throws Exception {
	          return jedis.zcard(key).intValue();
	        }
	      }, key);
	      
	      if (obj != null)
	        count = Integer.valueOf(obj.toString());
	    } catch (Exception e) {
	      log.error("", e);
	    }
	    return count;
	}

	@Override
	public boolean existZsetMember(final String key, final String resId) {
		long index = -1;
	    try {
	      index = (Long) modRedisCenter.getResult(new JedisCallback() {
	        @Override
	        public Object execute(Jedis jedis) throws Exception {
	          Long res = jedis.zrank(key, resId);
	          if (res == null) {
	            return -1l;
	          } else {
	            return res;
	          }
	        }
	      }, key);
	    } catch (Exception e) {
	      log.error("", e);
	    }
	    return index >= 0;
	}
	
	/**
	 * added by wgx 201505
	 * 根据时间区间返回特定条数内容
	 * @param key
	 * @param max
	 * @param min
	 * @param offset
	 * @param counts
	 * @return
	 */
	public Set<String> revrangeByScoreWithSortedSetLimit(final String key, final double max, final double min) {
		
		Set<String> b = null;
		try {
			b =  (Set<String>)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.zrevrangeByScore(key, max, min);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}

	
	// ------------------------------------------------string------------------------------------------------
	
	/**
	 * 将字符串值 value 关联到 key 。
	 * 如果 key 已经持有其他值， setString 就覆写旧值，无视类型。
	 * 对于某个原本带有生存时间（TTL）的键来说， 当 setString 成功在这个键上执行时， 这个键原有的 TTL 将被清除。
	 * 时间复杂度：O(1)
	 * @param key key
	 * @param value string value
	 * @return 在设置操作成功完成时，才返回 OK 。
	 */
	public String setString(final String key, final String value) {
		String b = null;
		try {
			b =  (String)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.set(key, value);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}

	
	public String setObject(final String key, final Object object) {
		String b = null;
		try {
			b =  (String)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.set(key.getBytes(), JDKSerializeUtil.serialize(object));
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}
	
	public byte[] getObject(final String key) {
		byte[] b = null;
		try {
			b =  (byte[])modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.get(key.getBytes());
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}
	
	/**
	 * 将值 value 关联到 key ，并将 key 的生存时间设为 expire (以秒为单位)。
	 * 如果 key 已经存在， 将覆写旧值。
	 * 类似于以下两个命令:
	 * SET key value
	 * EXPIRE key expire # 设置生存时间
	 * 不同之处是这个方法是一个原子性(atomic)操作，关联值和设置生存时间两个动作会在同一时间内完成，在 Redis 用作缓存时，非常实用。
	 * 时间复杂度：O(1)
	 * @param key key
	 * @param value string value
	 * @param expire 生命周期
	 * @return 设置成功时返回 OK 。当 expire 参数不合法时，返回一个错误。
	 */
	public String setString(final String key, final String value, final int expire) {
		String b = null;
		try {
			b =  (String)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.setex(key, expire, value);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
		
	}
	
	
	/**
	 * 将 key 的值设为 value ，当且仅当 key 不存在。若给定的 key 已经存在，则 setStringIfNotExists 不做任何动作。
	 * 时间复杂度：O(1)
	 * @param key key
	 * @param value string value
	 * @return 设置成功，返回 1 。设置失败，返回 0 。
	 */
	public Long setStringIfNotExists(final String key, final String value) {
		
		Long b = null;
		try {
			b =  (Long)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.setnx(key, value);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
		
	}

	/**
	 * 返回 key 所关联的字符串值。如果 key 不存在那么返回特殊值 nil 。
	 * 假如 key 储存的值不是字符串类型，返回一个错误，因为 getString 只能用于处理字符串值。
	 * 时间复杂度: O(1)
	 * @param key key
	 * @return 当 key 不存在时，返回 nil ，否则，返回 key 的值。如果 key 不是字符串类型，那么返回一个错误。
	 */
	public String getString(final String key) {
		String b = null;
		try {
			b =  (String)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.get(key);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}

	
	
	
	// ------------------------------------------------list------------------------------------------------
	/**
	 * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
	 * @param key key
	 * @param values value的数组
	 * @return 执行 listPushTail 操作后，表的长度
	 */
	public Long listPushTail(final String key, final String... values) {
		
		Long b = null;
		try {
			b =  (Long)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.rpush(key, values);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}

	/**
	 * 将一个或多个值 value 插入到列表 key 的表头
	 * @param key key
	 * @param value string value
	 * @return 执行 listPushHead 命令后，列表的长度。
	 */
	public Long listPushHead(final String key, final String value) {
		
		Long b = null;
		try {
			b =  (Long)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.lpush(key, value);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
		
	}

	/**
	 * 将一个或多个值 value 插入到列表 key 的表头, 当列表大于指定长度是就对列表进行修剪(trim)
	 * @param key key
	 * @param value string value
	 * @param size 链表超过这个长度就修剪元素
	 * @return 执行 listPushHeadAndTrim 命令后，列表的长度。
	 */
	public Long listPushHeadAndTrim(final String key, final String value, final long size) {
		Long b = null;
		try {
			b =  (Long)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					Pipeline pipeline = jedis.pipelined();
					Response<Long> result = pipeline.lpush(key, value);
					// 修剪列表元素, 如果 size - 1 比 end 下标还要大，Redis将 size 的值设置为 end 。
					pipeline.ltrim(key, 0, size - 1);
					pipeline.sync();
					return result.get();
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}

	

	/**
	 * 同{@link #batchListPushTail(String, String[], boolean)},不同的是利用redis的事务特性来实现
	 * @param key key
	 * @param values value的数组
	 * @return null
	 */
	public Object updateListInTransaction(final String key, final List<String> values) {
		
		Object b = null;
		try {
			b =  modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					Transaction transaction = jedis.multi();
					transaction.del(key);
					for (String value : values) {
						transaction.rpush(key, value);
					}
					transaction.exec();
					return null;
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
		
	}

	/**
	 * 返回list所有元素，下标从0开始，负值表示从后面计算，-1表示倒数第一个元素，key不存在返回空列表
	 * @param key key
	 * @return list所有元素
	 */
	@SuppressWarnings("unchecked")
	public List<String> listGetAll(final String key) {
		
		List<String> b = null;
		try {
			b =  (List<String>)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.lrange(key, 0, -1);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}

	/**
	 * 返回指定区间内的元素，下标从0开始，负值表示从后面计算，-1表示倒数第一个元素，key不存在返回空列表
	 * @param key key
	 * @param beginIndex 下标开始索引（包含）
	 * @param endIndex 下标结束索引（不包含）
	 * @return 指定区间内的元素
	 */
	@SuppressWarnings("unchecked")
	public List<String> listRange(final String key, final long beginIndex, final long endIndex) {
		
		List<String> b = null;
		try {
			b =  (List<String>)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.lrange(key, beginIndex, endIndex - 1);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}
	
	/**
	 * 从头部(left)向尾部(right)变量链表，删除count个值等于value的元素，返回值为实际删除的数量。
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	public Long listLremDelete(final String key, final long count, final String value) {
		Long b = null;
		try {
			b =  (Long)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.lrem(key, count, value);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}

	
	/* ======================================incr====================================== */
	public Long incrBy(final String key,final Integer step) {
		Long b = null;
		try {
			b =  (Long)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.incrBy(key, step);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}
	
	/**
	 * 一个跨jvm的id生成器，利用了redis原子性操作的特点
	 * @param key id的key
	 * @return 返回生成的Id
	 */
	public long makeId(final String key) {
		long b = 0;
		try {
			b =  (long)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Long execute(Jedis jedis) throws Exception {
					long id = jedis.incr(key);
					if ((id + 75807) >= Long.MAX_VALUE) {
						// 避免溢出，重置，getSet命令之前允许incr插队，75807就是预留的插队空间
						jedis.getSet(key, "0");
					}
					return id;
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}

	
	/* ======================================Hashes====================================== */

	/**
	 * 将哈希表 key 中的域 field 的值设为 value 。
	 * 如果 key 不存在，一个新的哈希表被创建并进行 hashSet 操作。
	 * 如果域 field 已经存在于哈希表中，旧值将被覆盖。
	 * 时间复杂度: O(1)
	 * @param key key
	 * @param field 域
	 * @param value string value
	 * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
	 */
	public Long hashSet(final String key, final String field, final String value) {
		Long b = null;
		try {
			b =  (Long)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.hset(key, field, value);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}


	/**
	 * 返回哈希表 key 中给定域 field 的值。
	 * 时间复杂度:O(1)
	 * @param key key
	 * @param field 域
	 * @return 给定域的值。当给定域不存在或是给定 key 不存在时，返回 nil 。
	 */
	public String hashGet(final String key, final String field) {
		String b = null;
		try {
			b =  (String)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.hget(key, field);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}


	/**
	 * 同时将多个 field-value (域-值)对设置到哈希表 key 中。
	 * 时间复杂度: O(N) (N为fields的数量)
	 * @param key key
	 * @param hash field-value的map
	 * @return 如果命令执行成功，返回 OK 。当 key 不是哈希表(hash)类型时，返回一个错误。
	 */
	public String hashMultipleSet(final String key, final Map<String, String> hash) {
		String b = null;
		try {
			b =  (String)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.hmset(key, hash);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}

	/**
	 * 返回哈希表 key 中，一个或多个给定域的值。如果给定的域不存在于哈希表，那么返回一个 nil 值。
	 * 时间复杂度: O(N) (N为fields的数量)
	 * @param key key
	 * @param fields field的数组
	 * @return 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。
	 */
	@SuppressWarnings("unchecked")
	public List<String> hashMultipleGet(final String key, final String... fields) {
		List<String> b = null;
		try {
			b =  (List<String>)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.hmget(key, fields);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}
	
	/**
	 * 返回哈希表 key 中，所有的域和值。在返回值里，紧跟每个域名(field name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。
	 * 时间复杂度: O(N)
	 * @param key key
	 * @return 以列表形式返回哈希表的域和域的值。若 key 不存在，返回空列表。
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> hashGetAll(final String key) {
		Map<String, String> b = null;
		try {
			b =  (Map<String, String>)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.hgetAll(key);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}
	
	
	public Long hashDel(final String key, final String... fields) {
		Long b = null;
		try {
			b =  (Long)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.hdel(key, fields);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
	}

	
	/* ======================================Queue====================================== */
	
    public Long pushQueue(final String key, final String... values) {
		Long b = null;
		try {
			b =  (Long)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					 return jedis.rpush(key, values);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
    }

    public String popQueue(final String key) {
		String b = null;
		try {
			b =  (String)modRedisCenter.getResult(new JedisCallback() {
				@Override
				public Object execute(Jedis jedis) throws Exception {
					return jedis.lpop(key);
				}
			}, key);
		} catch (Exception e) {
			log.error("", e);
		}
		return b;
    }

    
    
    /**
     * Zset数据封装对象
     * @author Administrator
     *
     */
    public class Zset {

    	public String resId; // required
    	public long score;

    	public Zset() {
    	}
    	
    	public Zset(String resId, long score) {
    		super();
    		this.resId = resId;
    		this.score = score;
    	}
    	
    	public String getResId() {
    		return resId;
    	}
    	public void setResId(String resId) {
    		this.resId = resId;
    	}
    	public long getScore() {
    		return score;
    	}
    	public void setScore(long score) {
    		this.score = score;
    	}

    	@Override
    	public String toString() {
    		return "Zset [resId=" + resId + ", score=" + score + "]";
    	}
  
    }

	public ModRedisCenter getModRedisCenter() {
		return modRedisCenter;
	}

	public void setModRedisCenter(ModRedisCenter modRedisCenter) {
		this.modRedisCenter = modRedisCenter;
	}
 
}
