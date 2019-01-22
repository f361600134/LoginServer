package com.qlbs.Bridge.module.youxifan.ios.param;

/**
 * 创建订单参数
 * 
 * @auth Jeremy
 * @date 2019年1月10日下午2:42:27
 */
public class PreOrderParam implements IParameter {

	private String qd1;
	private String qd2;
	private String playerId;
	private String playerName;
	private String gameKey;
	private String serverId;
	private String eUrl;
	private String money;
	private String yuanbao;
	private String sign;
	// private String userId;

	public PreOrderParam(String qd1, String qd2, String playerId, String playerName, String gameKey, String serverId, String eUrl, String money, String yuanbao, String sign) {
		super();
		this.qd1 = qd1;
		this.qd2 = qd2;
		this.playerId = playerId;
		this.playerName = playerName;
		this.gameKey = gameKey;
		this.serverId = serverId;
		this.eUrl = eUrl;
		this.money = money;
		this.yuanbao = yuanbao;
		this.sign = sign;
		// this.userId = userId;
	}

	public void setQd1(String qd1) {
		this.qd1 = qd1;
	}

	public void setQd2(String qd2) {
		this.qd2 = qd2;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public void seteUrl(String eUrl) {
		this.eUrl = eUrl;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public void setYuanbao(String yuanbao) {
		this.yuanbao = yuanbao;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	// public void setUserId(String userId) {
	// this.userId = userId;
	// }

	public String getQd1() {
		return qd1;
	}

	public String getQd2() {
		return qd2;
	}

	public String getPlayerId() {
		return playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getGameKey() {
		return gameKey;
	}

	public String getServerId() {
		return serverId;
	}

	public String geteUrl() {
		return eUrl;
	}

	public String getMoney() {
		return money;
	}

	public String getYuanbao() {
		return yuanbao;
	}

	public String getSign() {
		return sign;
	}

	// public String getUserId() {
	// return userId;
	// }

}
