package com.qlbs.Bridge.module.youxifan.ios.param;

import com.qlbs.Bridge.module.common.impl.PreOrderParam;

public class YouxifanIOSOrderParam extends PreOrderParam {

	public YouxifanIOSOrderParam(String qd1, String qd2, String playerId, String playerName, String gameKey, String serverId, String eUrl, String money, String yuanbao, String sign, String userId) {
		super(qd1, qd2, playerId, playerName, gameKey, serverId, eUrl, money, yuanbao, sign, userId);
	}

	private String userId;

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "YouxifanOrderParam [userId=" + userId + "]";
	}

}
