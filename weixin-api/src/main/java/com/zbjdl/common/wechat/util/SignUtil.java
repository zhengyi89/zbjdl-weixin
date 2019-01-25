package com.zbjdl.common.wechat.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信各种签到需要算法工具类
 */
public class SignUtil {
	private static Logger LOGGER = LoggerFactory.getLogger(SignUtil.class);
	
	public static MessageDigest crypt = null;
	static {
		try {
			crypt = MessageDigest.getInstance("SHA-1");
		} catch (Exception e){
			LOGGER.error("SHA-1算法实例化失败" , e);
		}
	}
	
	/**
	 * SHA-1算法加密
	 * @throws UnsupportedEncodingException 
	 */
	public static String sha1Digest(String content , String charset) throws UnsupportedEncodingException{
		crypt.reset();
		crypt.update(content.getBytes(charset));
		return SignUtil.byteToHex(crypt.digest());
	}
	
	/**
	 * 字符转换为16进制
	 * @param hash
	 * @return
	 */
	public static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	/**
	 * 创建临时字符串
	 * @return
	 */
	public static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 创建时间戳
	 * @return
	 */
	public static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}
