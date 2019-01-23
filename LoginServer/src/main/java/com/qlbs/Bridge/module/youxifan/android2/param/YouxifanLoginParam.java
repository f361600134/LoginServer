package com.qlbs.Bridge.module.youxifan.android2.param;

import com.qlbs.Bridge.module.common.impl.AbstractParameter;

public class YouxifanLoginParam extends AbstractParameter {

	private String sessionId;
	private String platformId;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

}
