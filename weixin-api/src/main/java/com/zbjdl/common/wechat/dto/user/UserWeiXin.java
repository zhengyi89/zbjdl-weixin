package com.zbjdl.common.wechat.dto.user;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.zbjdl.common.wechat.dto.BaseResult;

/**
 * 
 * 
 * @since 2014-11-28 16:20:46
 */
public class UserWeiXin extends BaseResult{
	private static final long serialVersionUID = 1L;
	private String openid;
	private String nickname;
	private Integer sex;
	private String province;
	private String city;
	private String country;
	/**
	 * 头像地址可能会更新
	 */
	private String headimgurl;
	private String unionid;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this , ToStringStyle.MULTI_LINE_STYLE);
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
}
