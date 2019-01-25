package com.zbjdl.common.wx.exception;

import com.zbjdl.common.exception.BaseException;

public class BizRuntimeException extends BaseException {
	private static final long serialVersionUID = -8918418947102387683L;

	public BizRuntimeException() {
		super();
	}

	public BizRuntimeException(String message) {
		super(message);
	}

	public BizRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public BizRuntimeException(Throwable cause) {
		super(cause);
	}
}
