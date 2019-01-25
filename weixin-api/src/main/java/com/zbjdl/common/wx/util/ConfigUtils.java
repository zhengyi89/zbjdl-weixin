package com.zbjdl.common.wx.util;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zbjdl.common.utils.config.ConfigParam;
import com.zbjdl.common.utils.config.ConfigParamGroup;
import com.zbjdl.common.utils.config.ConfigurationUtils;

/**
 * 统一配置组件实用工具类
 *
 */
public class ConfigUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtils.class);

	public static final String CONFIG_TYPE_TEXT_RESOURCES = "config_type_text_resources";

	/**
	 * 获取配置参数
	 *
	 * @param configType 参数的分类
	 * @param configKey  参数key(含默认值)
	 * @return 参数值
	 */
	@SuppressWarnings("rawtypes")
	public static Object getConfigParam(String configType, ConfigEnum configKey) {
		ConfigParam configParam = ConfigurationUtils.getConfigParam(configType, configKey.getConfigKey());
		if (null != configParam && null != configParam.getValue()) {
			return configParam.getValue();
		} else {
			LOGGER.info("configType:{}, configKey:{}, defaultValue:{}",
					configType, configKey.getConfigKey(), configKey.getDefaultValue());
			return configKey.getDefaultValue();
		}
	}

	/**
	 * 获取产品层配置参数
	 *
	 * @param configKey 参数key(含默认值)
	 * @return 参数值
	 */
	public static Object getAppConfigParam(ConfigEnum configKey) {
		return getConfigParam(ConfigurationUtils.CONFIG_TYPE_APP, configKey);
	}

	/**
	 * 获取产品层配置参数
	 *
	 * @param configKey 参数key(含默认值)
	 * @return 参数值
	 */
	public static List<String> getAppConfigParamList(ConfigEnum configKey) {
		return getConfigParamList(ConfigurationUtils.CONFIG_TYPE_APP, configKey);
	}

	
	/**
	 * 读取统一配置参数，并且返回列表类型
	 * @param configGroup	配置类型，例如:ConfigurationUtils.CONFIG_TYPE_APP
	 * @param configKey		配置键
	 * @param defaultValue	默认值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getConfigParamList(String configGroup , ConfigEnum config) {
		String configKey = config.getConfigKey();
		Object defaultValue = config.getDefaultValue();
		ConfigParamGroup<?> configParamGroup = ConfigurationUtils.getConfigParamGroup(configGroup);
		ConfigParam<?> configParam = configParamGroup.getConfig(configKey);
		if (null != configParam && null != configParam.getValue()) {
			Object value = configParam.getValue();
			if (value instanceof List) {
				return (List<String>) value;
			} else if (value instanceof String && ((String) value).indexOf('@') == 0) {
				String key = (String) value;
				return getResourcesConfigList(key.substring(1, key.length()), defaultValue);
			}
		}
		return (List<String>) defaultValue;
	}
	
	/**
	 * 获取子系统配置参数
	 *
	 * @param configKey 参数key(含默认值)
	 * @return 参数值
	 */
	public static Object getSysConfigParam(ConfigEnum configKey) {
		return getConfigParam(ConfigurationUtils.CONFIG_TYPE_SYS, configKey);
	}

	/**
	 * 获取数据字典统一配置 Map
	 *
	 * @param configKey 配置键
	 * @return 参数值
	 */
	public static Map<String, String> getResourcesConfigMap(ConfigEnum configKey) {
		return getResourcesConfigMap(configKey.getConfigKey(), configKey.getDefaultValue());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map<String, String> getResourcesConfigMap(String configKey, Object defaultValue) {
		ConfigParamGroup configParamGroup = ConfigurationUtils.getConfigParamGroup(CONFIG_TYPE_TEXT_RESOURCES);
		ConfigParam configParam = configParamGroup.getConfig(configKey);
		if (null != configParam && null != configParam.getValue()) {
			Object value = configParam.getValue();
			if (value instanceof Map) {
				return (Map<String, String>) value;
			} else if (value instanceof String && ((String) value).indexOf('@') == 0) {
				String key = (String) value;
				return getResourcesConfigMap(key.substring(1, key.length()), defaultValue);
			}
		}
		return (Map<String, String>) defaultValue;
	}

	/**
	 * 获取数据字典统一配置 List
	 *
	 * @param configKey 配置键
	 * @return 参数值
	 */
	public static List<String> getResourcesConfigList(ConfigEnum configKey) {
		return getResourcesConfigList(configKey.getConfigKey(), configKey.getDefaultValue());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static List<String> getResourcesConfigList(String configKey, Object defaultValue) {
		ConfigParamGroup configParamGroup = ConfigurationUtils.getConfigParamGroup(CONFIG_TYPE_TEXT_RESOURCES);
		ConfigParam configParam = configParamGroup.getConfig(configKey);
		if (null != configParam && null != configParam.getValue()) {
			Object value = configParam.getValue();
			if (value instanceof List) {
				return (List<String>) value;
			} else if (value instanceof String && ((String) value).indexOf('@') == 0) {
				String key = (String) value;
				return getResourcesConfigList(key.substring(1, key.length()), defaultValue);
			}
		}
		return (List<String>) defaultValue;
	}

}
