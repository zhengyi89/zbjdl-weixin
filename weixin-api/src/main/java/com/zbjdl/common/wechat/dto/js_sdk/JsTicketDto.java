package com.zbjdl.common.wechat.dto.js_sdk;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zbjdl.common.wechat.dto.BaseResult;

/**
 * 微信JS接口临时票据数据传输对象
 * 
 * 
 * @since 2015-04-12 15:33:58
 */
public class JsTicketDto extends BaseResult{
	private static final long serialVersionUID = 1L;
	/**
	 * 临时票据
	 */
	private String ticket;
	/**
	 * 临时票据有效期
	 */
	private Integer expires_in;
	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public Integer getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}
}
