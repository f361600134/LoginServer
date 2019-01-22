package com.qlbs.Bridge.module.youxifan.ios.param;

/**
 * 游戏fan登录参数
 * 
 * @auth Jeremy
 * @date 2019年1月9日下午2:26:37
 */
public class YouxifanIOSLoginParam extends AbstractParameter {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public YouxifanIOSLoginParam() {
		super();
	}

	public YouxifanIOSLoginParam(String token) {
		super();
		this.token = token;
	}

}
