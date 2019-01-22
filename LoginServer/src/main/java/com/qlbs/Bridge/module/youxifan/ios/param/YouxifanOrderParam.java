package com.qlbs.Bridge.module.youxifan.ios.param;

public class YouxifanOrderParam extends PreOrderParam {

	private String userId;

	public YouxifanOrderParam(String qd1, String qd2, String playerId, String playerName, String gameKey, String serverId, String eUrl, String money, String yuanbao, String sign, String userId) {
		super(qd1, qd2, playerId, playerName, gameKey, serverId, eUrl, money, yuanbao, sign);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "YouxifanOrderParam [userId=" + userId + "]";
	}

}
