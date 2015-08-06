package com.github.flux.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.flux.cache.impl.RedisCacheImpl.Zset;

import redis.clients.jedis.Tuple;

public interface RedisCache {

	/**
	   * add  添加成功同时会标记操作结果为成功
	   *
	   * @param key        分布式key
	   * @param resId resId
	   * @param score      score
	   * @return boolean
	   */
	  public boolean addWithSortedSet(String key, long score, String member);

	  /**
	   * 添加多个id 一次最多200个记录
	   *
	   * @param key 分布式key
	   * @param map map
	   * @return boolean
	   */
	  public boolean addWithSortedSet(String key, Map<String, Object> map);

	  /**
	   * 重建zset
	   *
	   * @param key 分布式key
	   * @param map map
	   * @return boolean
	   */
	  public boolean rebuildZset(final String key, final Map<String, Object> map);
	  
	  /**
	   * 删除zset 一条id
	   *
	   * @param key        分布式key
	   * @param resId resId
	   * @return boolean
	   */
	  public boolean delZsetResId(String key, String resId);

	  /**
	   * 删除key
	   *
	   * @param key key
	   * @return boolean
	   */
	  public boolean delKey(String key);

	  /**
	   * 删除多个id
	   *
	   * @param key    分布式key
	   * @param resIds resIds
	   * @return boolean
	   */
	  public boolean delZsetIds(String key, List<String> resIds);

	  /**
	   * 获取一个key对应的resid -1全部
	   *
	   * @param key   分布式key
	   * @param start start
	   * @param count count
	   * @return 失败返回null
	   */
	  public List<String> getZsetList(String key, int start, int count);

	  /**
	   * 获取一个key在score时间之间的member  从小到大
	   *
	   * @param key        key
	   * @param startscore startscore
	   * @param endscore   endscore
	   * @param offset     起始角标
	   * @param count      记录数
	   * @return IdList
	   */
	  public List<String> getZsetListByScore(String key, long startscore, long endscore, int offset, int count);

	  /**
	   * 获取一个key在score时间之间的idlist  从大到小
	   *
	   * @param key    key
	   * @param max    max
	   * @param min    min
	   * @param offset 起始角标
	   * @param count  记录数
	   * @return IdList
	   */
	  public List<String> getZsetByScoreDesc(String key, long max, long min, int offset, int count);

	  /**
	   * 获取一个key在score时间之间的idlist with score  从大到小
	   *
	   * @param key    key
	   * @param max    max
	   * @param min    min
	   * @param offset offset
	   * @param count  count
	   * @return IdListExt
	   */
	  public List<Zset> getZsetWithScoreByScoreDesc(final String key, final long max, final long min, final int offset, final int count);

	  /**
	   * 返回redis Tuple 包含socre
	   * @param key
	   * @param max
	   * @param min
	   * @return
	   */
	  public Set<Tuple> revrangeTupleByScoreWithSortedSet(final String key, final double max, final double min);
	  
	  /**
	   * 获取某个key的总size
	   *
	   * @param key key
	   * @return int
	   */
	  public int getZsetCount(String key);

	  
	  /**
	   *  key是否存在resId
	   *
	   * @param key        key
	   * @param resId resId
	   * @return boolean
	   */
	  public boolean existZsetMember(String key, String resId);

	  public Long zremrangeByScore(final String key, final long min, final long max);
	  
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
	  public Set<String> revrangeByScoreWithSortedSetLimit(final String key, final double max, final double min);
	  
	  /**
		 * 将字符串值 value 关联到 key 。
		 * 如果 key 已经持有其他值， setString 就覆写旧值，无视类型。
		 * 对于某个原本带有生存时间（TTL）的键来说， 当 setString 成功在这个键上执行时， 这个键原有的 TTL 将被清除。
		 * 时间复杂度：O(1)
		 * @param key key
		 * @param value string value
		 * @return 在设置操作成功完成时，才返回 OK 。
		 */
		public String setString(final String key, final String value);
		
		public String setObject(final String key, final Object object);
		
		public byte[] getObject(final String key);
		
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
		public String setString(final String key, final String value, final int expire);
		
		/**
		 * 将 key 的值设为 value ，当且仅当 key 不存在。若给定的 key 已经存在，则 setStringIfNotExists 不做任何动作。
		 * 时间复杂度：O(1)
		 * @param key key
		 * @param value string value
		 * @return 设置成功，返回 1 。设置失败，返回 0 。
		 */
		public Long setStringIfNotExists(final String key, final String value);
		
		/**
		 * 返回 key 所关联的字符串值。如果 key 不存在那么返回特殊值 nil 。
		 * 假如 key 储存的值不是字符串类型，返回一个错误，因为 getString 只能用于处理字符串值。
		 * 时间复杂度: O(1)
		 * @param key key
		 * @return 当 key 不存在时，返回 nil ，否则，返回 key 的值。如果 key 不是字符串类型，那么返回一个错误。
		 */
		public String getString(final String key);
		
		
		/**
		 * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
		 * @param key key
		 * @param values value的数组
		 * @return 执行 listPushTail 操作后，表的长度
		 */
		public Long listPushTail(final String key, final String... values);
		
		
		/**
		 * 将一个或多个值 value 插入到列表 key 的表头
		 * @param key key
		 * @param value string value
		 * @return 执行 listPushHead 命令后，列表的长度。
		 */
		public Long listPushHead(final String key, final String value);
		
		/**
		 * 将一个或多个值 value 插入到列表 key 的表头, 当列表大于指定长度是就对列表进行修剪(trim)
		 * @param key key
		 * @param value string value
		 * @param size 链表超过这个长度就修剪元素
		 * @return 执行 listPushHeadAndTrim 命令后，列表的长度。
		 */
		public Long listPushHeadAndTrim(final String key, final String value, final long size) ;
		
		/**
		 * 从头部(left)向尾部(right)变量链表，删除count个值等于value的元素，返回值为实际删除的数量。
		 * @param key
		 * @param count
		 * @param value
		 * @return
		 */
		public Long listLremDelete(final String key, final long count, final String value);
		
		/**
		 * 同{@link #batchListPushTail(String, String[], boolean)},不同的是利用redis的事务特性来实现
		 * @param key key
		 * @param values value的数组
		 * @return null
		 */
		public Object updateListInTransaction(final String key, final List<String> values) ;
		
		/**
		 * 返回list所有元素，下标从0开始，负值表示从后面计算，-1表示倒数第一个元素，key不存在返回空列表
		 * @param key key
		 * @return list所有元素
		 */
		public List<String> listGetAll(final String key);
		
		/**
		 * 返回指定区间内的元素，下标从0开始，负值表示从后面计算，-1表示倒数第一个元素，key不存在返回空列表
		 * @param key key
		 * @param beginIndex 下标开始索引（包含）
		 * @param endIndex 下标结束索引（不包含）
		 * @return 指定区间内的元素
		 */
		public List<String> listRange(final String key, final long beginIndex, final long endIndex);
		
		public Long incrBy(final String key,final Integer step) ;
		
		
		
		/**added by wgx 201505
		 * 
		 * 
		 * 一个跨jvm的id生成器，利用了redis原子性操作的特点
		 * @param key id的key
		 * @return 返回生成的Id
		 */
		public long makeId(final String key);
		
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
		public Long hashSet(final String key, final String field, final String value);
		
		/**
		 * 返回哈希表 key 中给定域 field 的值。
		 * 时间复杂度:O(1)
		 * @param key key
		 * @param field 域
		 * @return 给定域的值。当给定域不存在或是给定 key 不存在时，返回 nil 。
		 */
		public String hashGet(final String key, final String field);
		
		/**
		 * 同时将多个 field-value (域-值)对设置到哈希表 key 中。
		 * 时间复杂度: O(N) (N为fields的数量)
		 * @param key key
		 * @param hash field-value的map
		 * @return 如果命令执行成功，返回 OK 。当 key 不是哈希表(hash)类型时，返回一个错误。
		 */
		public String hashMultipleSet(final String key, final Map<String, String> hash);
		
		
		/**
		 * 返回哈希表 key 中，一个或多个给定域的值。如果给定的域不存在于哈希表，那么返回一个 nil 值。
		 * 时间复杂度: O(N) (N为fields的数量)
		 * @param key key
		 * @param fields field的数组
		 * @return 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。
		 */
		public List<String> hashMultipleGet(final String key, final String... fields);
		
		/**
		 * 返回哈希表 key 中，所有的域和值。在返回值里，紧跟每个域名(field name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。
		 * 时间复杂度: O(N)
		 * @param key key
		 * @return 以列表形式返回哈希表的域和域的值。若 key 不存在，返回空列表。
		 */
		public Map<String, String> hashGetAll(final String key);
		
		/**
		 * 删除hash的域，如果指定多个field，则删除多个
		 * @param key
		 * @param fields
		 * @return
		 */
		public Long hashDel(final String key, final String... fields);
		
		/**
		 * 入栈
		 * @param key
		 * @param values
		 * @return
		 */
		public Long pushQueue(final String key, final String... values);
		
		/**
		 * 出栈
		 * @param key
		 * @return
		 */
		public String popQueue(final String key);
		
		

}
