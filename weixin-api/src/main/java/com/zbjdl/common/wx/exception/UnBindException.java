package com.zbjdl.common.wx.exception;

import com.zbjdl.common.exception.BaseException;

/**
 * 微信OpendId未绑定会员时，抛出此异常，请引导用户绑定
 * 
 * 
 * @since 2014-9-9 21:02:35
 */
public class UnBindException extends BaseException {
	private static final long serialVersionUID = -8918418947102387683L;

	public UnBindException() {
		super();
	}

	public UnBindException(String message) {
		super(message);
	}

	public UnBindException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnBindException(Throwable cause) {
		super(cause);
	}
}
