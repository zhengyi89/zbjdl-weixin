package com.zbjdl.common.wechat.cache;

import com.zbjdl.common.redis.RedisClientUtils;

/**
 * 微信缓存枚举
 * 
 * 
 * @since 2015-04-12 13:29:42
 */
public enum WxCacheEnum implements CacheAble{
	/**
	 * 微信JS-SDK
	 */
	JSAPI_TICKET("JSAPI_TICKET" , 7000 , String.class);
	
	/**
	 * 微信系统缓存信息统一前缀，避免和默认客户端下其它用户冲突
	 */
	private static final String WX_PREFIX = "HENGBAO_COMMON_WX_";
	
	/**
	 * 缓存数据名称
	 */
	private String key;
	/**
	 * 缓存数据有效期(单位：秒)
	 */
	private int timeToLive;
	/**
	 * 缓存数据类型
	 */
	private Class<?> clazz;
	
	private WxCacheEnum(String key , int timeToLive , Class<?> clazz){
		this.key = key;
		this.timeToLive = timeToLive;
		this.clazz = clazz;
	}
	
	/**
	 * 存储缓存对象
	 * 
	 * @param cache			存储对象
	 * @param value			存储对象值
	 */
	@Override
	public void cache(Object value){
		//CacheUtil.putData(WX_PREFIX + this.getKey() , value , this.getTimeToLive());
		RedisClientUtils.getRedisTemplate().setex(WX_PREFIX + this.getKey(), this.getTimeToLive(),value.toString());
	}
	/**
	 * 读取缓存数据
	 * @param cache		缓存对象
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCache() {
		//return (T) CacheUtil.getData(this.getClazz() , WX_PREFIX + this.getKey());
		return (T)RedisClientUtils.getRedisTemplate().get(WX_PREFIX + this.getKey());
		
	}
	
	@Override
	public void clearCache() {
		//CacheUtil.removeData(WX_PREFIX + this.getKey());
		RedisClientUtils.getRedisTemplate().del(WX_PREFIX + this.getKey());
	}
	
	public String getKey() {
		return key;
	}

	public int getTimeToLive() {
		return timeToLive;
	}

	public Class<?> getClazz() {
		return clazz;
	}
}