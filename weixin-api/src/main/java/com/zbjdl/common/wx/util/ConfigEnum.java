package com.zbjdl.common.wx.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 所有配置信息
 *
 */
public enum ConfigEnum {
	/**
	 * 商户微信系统域名
	 */
	MCHSERVICE_WX_HOST("MCHSERVICE_WX_HOST" , "http://wx.yunpal.net"),
	
	/**
	 * 是否为生产环境
	 */
	//TRZ_PRODUCTION_MODE("TRZ_PRODUCTION_MODE", true),

	/**
	 * 签名超时时间
	 */
	//TRZ_SIGNATURE_TIMEOUT("TRZ_SIGNATURE_TIMEOUT", 300L),

	
	
	/**
	 * 微信参数配置：是否真实环境
	 */
	WX_PRODUCT("WX_PRODUCT" , true),
	
	/**
	 * 微信系统APPID
	 */
	WX_APPID("WX_APPID" , ""),
	
	/**
	 * 微信系统SECRET
	 */
	WX_SECRET("WX_SECRET" , ""),
	
	/**
	 * 微信系统Access_token
	 */
	WX_ACCESS_TOKEN("WX_ACCESS_TOKEN" , ""),
	
	/**
	 * 微信软文推广内容
	 */
	WX_ARTICLE_CONTENT("WX_ARTICLE_CONTENT" , null),
	
	/**
	 * 微信服务器地址
	 */
	WX_API_SYSTEM_HOST("WX_API_SYSTEM_HOST" , "https://api.weixin.qq.com");
	

	private static Map<String, ConfigEnum> valueMap = Maps.newHashMap();

	private String configKey;
	private Object defaultValue;

	static {
		for (ConfigEnum item : ConfigEnum.values()) {
			valueMap.put(item.configKey, item);
		}
	}

	ConfigEnum(String configKey, Object defaultValue) {
		this.configKey = configKey;
		this.defaultValue = defaultValue;
	}

	public String getConfigKey() {
		return configKey;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

}
