package com.zbjdl.common.wechat.cache;

/**
 * 缓存对象枚举
 * 
 * 
 * @since 2015-04-12 13:51:18
 */
public interface CacheAble {
	/**
	 * 存储缓存数据
	 * 
	 * @param value		缓存数据值
	 */
	void cache(Object value);
	
	/**
	 * 读取缓存数据
	 * @param cache		缓存对象
	 * @return
	 */
	<T> T getCache();
	
	/**
	 * 清除缓存数据
	 */
	void clearCache();
}
