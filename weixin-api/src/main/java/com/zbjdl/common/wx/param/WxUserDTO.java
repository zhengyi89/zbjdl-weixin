package com.zbjdl.common.wx.param;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zbjdl.common.wx.enumtype.WxUserChannel;

public class WxUserDTO implements Serializable {

	private static final long serialVersionUID = -4061411033432310331L;
	
	/**
	 * 微信OpenId
	 */
	private String openId;
	
	/**
	 * 微信头像链接
	 */
	private String photoUrl;
	
	/**
	 * 微信昵称
	 */
	private String nickName;
	
	/**
	 * 渠道
	 */
	private WxUserChannel channel;
	
	public WxUserDTO() {
		super();
	}

	public WxUserDTO(String openId, String photoUrl, String nickName) {
		super();
		this.openId = openId;
		this.photoUrl = photoUrl;
		this.nickName = nickName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public WxUserChannel getChannel() {
		return channel;
	}

	public void setChannel(WxUserChannel channel) {
		this.channel = channel;
	}
}
