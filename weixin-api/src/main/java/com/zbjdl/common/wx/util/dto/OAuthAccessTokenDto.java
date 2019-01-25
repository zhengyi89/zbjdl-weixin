package com.zbjdl.common.wx.util.dto;


/**
 {
   "access_token":"ACCESS_TOKEN",
   "expires_in":7200,		单位（秒）
   "refresh_token":"REFRESH_TOKEN",
   "openid":"OPENID",
   "scope":"SCOPE"
}
 * 
 * @since 2014-11-28 16:20:46
 */
public class OAuthAccessTokenDto extends BaseAPIDto{
	private String scope;
	private String openid;
	private String access_token;
	private String expires_in;
	private String refresh_token;
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
}
