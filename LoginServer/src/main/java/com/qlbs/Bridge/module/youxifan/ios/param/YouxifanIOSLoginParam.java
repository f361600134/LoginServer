package com.qlbs.Bridge.module.youxifan.ios.param;

import com.qlbs.Bridge.module.common.impl.AbstractLoginParameter;

/**
 * 游戏fan登录参数
 * 
 * @auth Jeremy
 * @date 2019年1月9日下午2:26:37
 */
public class YouxifanIOSLoginParam extends AbstractLoginParameter {

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

	@Override
	public String toString() {
		return "YouxifanIOSLoginParam [token=" + token + ", getGameKey()=" + getGameKey() + ", getUserId()=" + getUserId() + ", getServerId()=" + getServerId() + ", getQdCode1()=" + getQdCode1() + ", getQdCode2()=" + getQdCode2() + "]";
	}

}
