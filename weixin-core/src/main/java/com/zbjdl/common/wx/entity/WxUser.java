package com.zbjdl.common.wx.entity;


import com.zbjdl.common.respository.entity.VersionableEntity;
import com.zbjdl.common.utils.StringUtils;


/**
 * 微信活动抽象实体
 * 
 * 
 */
public class WxUser extends VersionableEntity{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 微信OpenId
	 */
	private String openId;
	
	/**
	 * 微信昵称
	 */
	private String nickName;
	/**
	 * 微信头像地址
	 */
	private String photoUrl;
	/**
	 * 软文分享次数
	 */
	private Integer articleShareCount;
	/**
	 * 渠道
	 */
	private String channel;
	
	public WxUser() {
		super();
	}

	public WxUser(String openId) {
		this.openId = openId;
	}
	
	public WxUser(String nickName, String photoUrl) {
		super();
		this.nickName = nickName;
		this.photoUrl = photoUrl;
	}

	/**
	 * 判断是否已经绑定
	 * @return
	 */
	public boolean isBind(){
		if(StringUtils.isBlank(nickName) || StringUtils.isBlank(photoUrl)){
			return false;
		} else {
			return true;
		}
	}
	

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getArticleShareCount() {
		return articleShareCount;
	}

	public void setArticleShareCount(Integer articleShareCount) {
		this.articleShareCount = articleShareCount;
	}
	public void addArticleShareCount() {
		if(this.articleShareCount == null){
			this.articleShareCount = 1;
		} else {
			this.articleShareCount += 1;
		}
	}
}
