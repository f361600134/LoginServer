/**  
 * 广州青蓝冰水有限公司 
 * @date:2018年11月21日下午11:19:43 
 */
package com.qlbs.Bridge.domain.entity;

import java.io.Serializable;

/**
 * @auth Jimmy
 * @date 2018年11月21日下午11:19:43
 */
public class PlayerInfoPK implements Serializable {
	private String account;// 账号
	private String playerid;// 玩家id

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPlayerid() {
		return playerid;
	}

	public void setPlayerid(String playerid) {
		this.playerid = playerid;
	}
}