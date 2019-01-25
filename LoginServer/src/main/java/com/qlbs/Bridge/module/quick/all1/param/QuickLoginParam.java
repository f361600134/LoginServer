package com.qlbs.Bridge.module.quick.all1.param;

import org.apache.commons.lang3.StringUtils;

import com.qlbs.Bridge.module.common.impl.AbstractLoginParameter;

public class QuickLoginParam extends AbstractLoginParameter {

	private String token;
	private String channalId;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getChannalId() {
		if (StringUtils.isBlank(channalId)) {
			return channalId;
		}
		return channalId.replaceAll("\\{|\\}|\\-", "");
	}

	public void setChannalId(String channalId) {
		this.channalId = channalId;
	}

	public QuickLoginParam() {
	}

	public QuickLoginParam(String token, String channalId) {
		super();
		this.token = token;
		this.channalId = channalId;
	}

}
