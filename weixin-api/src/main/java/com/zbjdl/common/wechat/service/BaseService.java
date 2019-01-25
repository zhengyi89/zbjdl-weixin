package com.zbjdl.common.wechat.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zbjdl.common.httpclient.HttpClientUtil;
import com.zbjdl.common.httpclient.MethodType;
import com.zbjdl.common.json.JsonMapper;
import com.zbjdl.common.utils.config.ConfigRepository;
import com.zbjdl.common.utils.config.ConfigurationUtils;
import com.zbjdl.common.utils.config.dao.ConfigDao;
import com.zbjdl.common.utils.config.entity.Config;
import com.zbjdl.common.utils.config.enumtype.ValueDataTypeEnum;
import com.zbjdl.common.utils.config.enumtype.ValueTypeEnum;
import com.zbjdl.common.utils.config.impl.DefaultConfigRepository;
import com.zbjdl.common.wechat.dto.AccessToken;
import com.zbjdl.common.wechat.dto.BaseResult;
import com.zbjdl.common.wx.util.ConfigEnum;
import com.zbjdl.common.wx.util.ConfigUtils;




/**
 * 提供微信服务基础接口
 * 
 * 
 * @since 2015-04-20 11:25:16
 */
public class BaseService {
	private static Logger LOGGER = LoggerFactory.getLogger(BaseService.class);
	public static String appId = (String) ConfigUtils.getAppConfigParam(ConfigEnum.WX_APPID);
	public static String secret = (String) ConfigUtils.getAppConfigParam(ConfigEnum.WX_SECRET);

	//Access_token获取地址，2000(次/天)
	public static String url_access_token = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	private static String NAME_SPACE;
	private static ConfigDao configDao;
		
	
	/**
	 * 将消息发送到微信
	 * @param <T>
	 */
	@SuppressWarnings("unchecked")
	public static <T extends BaseResult> T sendToWx(String url , Class<T> clazz , Object... params) throws HttpException, IOException{
		if(params != null && params.length > 0){
			url = String.format(url, params);
		}
		LOGGER.info("调用微信接口:{}" , url);
		HttpClientUtil util = new HttpClientUtil();
		String response = util.doRequest(MethodType.GET, url, "UTF-8");
		JsonMapper mapper = new JsonMapper();
		BaseResult result = (BaseResult)mapper.fromJson(response, clazz);
		if(result != null && !result.isSuccess()){
			LOGGER.error("调用接口失败：{}" , response);
		}
		return (T)result;
	}
		
	/**
	 * 参数中含有Access_token，请保证第一个参数为Access_token
	 * @param url
	 * @param clazz
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends BaseResult>T sendToWxWithAccessToken(String url , Class<? extends BaseResult> clazz , Object... params) throws HttpException, IOException{
		BaseResult result = (BaseResult)sendToWx(url, clazz, getAccessTokenParams(false , params));
		if(result == null || result.isAccessTokenOutOfTime()){
			LOGGER.error("access_token time out");
			result = (BaseResult)sendToWx(url, clazz, getAccessTokenParams(true , params));
		}
		return (T) result;
	}
	
	//组织请求参数
	private static Object[] getAccessTokenParams(boolean isAnyCase , Object... params) throws IOException{
		String accessToken = getAccessToken(isAnyCase);
		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(accessToken);
		if(params != null){
			paramsList.addAll(Arrays.asList(params));
		}
		return paramsList.toArray();
	}
	
	/**
	 * 获取命名空间
	 */
	public static String getNameSpace() {
		try {
			if(StringUtils.isBlank(NAME_SPACE)){
				if(!ConfigurationUtils.initialized()){
					ConfigurationUtils.init();
				}
				ConfigRepository configRepository = (ConfigRepository) ConfigurationUtils.getContext().getBean("defaultConfigRepository");
				Field field = DefaultConfigRepository.class.getDeclaredField("configNamespace");
				field.setAccessible(true);
				NAME_SPACE = (String)field.get(configRepository);
			}
			return NAME_SPACE;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取配置Dao
	 */
	public static ConfigDao getConfigDao(){
		if(configDao == null){
			configDao = (ConfigDao) ConfigurationUtils.getContext().getBean("configDao");
		}
		return configDao;
	}
	
	
	// 获取当前系统AccessToken，此方法最好不要对外公开调用
	private static String getAccessToken(boolean isAnyCase) throws IOException{
		ConfigDao configDao = getConfigDao();
		String nameSpace = getNameSpace();
		Config config = new Config();
		config.setNamespace(nameSpace);
		config.setType(ConfigurationUtils.CONFIG_TYPE_APP);
		config.setConfigKey(ConfigEnum.WX_ACCESS_TOKEN.getConfigKey());
		
		List<Config> configList = configDao.queryConfig(config);
		if(configList == null || configList.size() == 0){
			config = new Config();
			config.setNamespace(nameSpace);
			config.setType(ConfigurationUtils.CONFIG_TYPE_APP);
			config.setConfigKey(ConfigEnum.WX_ACCESS_TOKEN.getConfigKey());
			config.setUpdatable(true);
			config.setDeletable(true);
			config.setUpdateDate(new Date());
			config.setCreateDate(new Date());
			config.setValueType(ValueTypeEnum.VALUE);
			config.setValueDataType(ValueDataTypeEnum.STRING);
			configDao.add(config);
		} else {
			config = configList.get(0);
		}
		long outTime = System.currentTimeMillis() - config.getUpdateDate().getTime();
		if(isAnyCase || (outTime > 7000000 || StringUtils.isBlank(config.getValue()))){
			LOGGER.info("AccessToken过期，开始重新查询:更新时间:{} , 时间差:{}" , config.getUpdateDate() , outTime);
			String url_access_token = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
			String appId = (String) ConfigUtils.getAppConfigParam(ConfigEnum.WX_APPID);
			String secret = (String) ConfigUtils.getAppConfigParam(ConfigEnum.WX_SECRET);
			AccessToken accessToken = sendToWx(url_access_token , AccessToken.class ,  appId , secret);
			if(accessToken == null || StringUtils.isBlank(accessToken.getAccess_token())){
				LOGGER.error("获取AccessToken失败");
				return null;
			}
			config.setValue(accessToken.getAccess_token());
			config.setUpdateDate(new Date());
			configDao.update(config);
		}
		return config.getValue();
	}	
	
	
	
	

}
