package com.qlbs.Bridge.domain.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * 广州青蓝冰水有限公司
 * 
 * @auth Jimmy
 * @date:2018/11/21 21:33
 **/
@Entity
@Table(name = "player_info")
@IdClass(PlayerInfoPK.class)
public class PlayerInfo implements Serializable {
	@Id
	@Column(length = 50)
	private String account;// 账号
	@Id
	@Column(length = 50)
	private String playerid;// 玩家id
	@Column
	private int profession;// 职业
	@Column
	private int gender;// 性别
	@Column
	private int level;// 等级
	@Column
	private String serverid;// 服务id
	@Column
	private BigInteger offlinetime;// 下线时间

	public PlayerInfo() {
	}

	public PlayerInfo(String account, String playerid, int profession, int gender, int level, String serverid, long offlinetime) {
		this.account = account;
		this.playerid = playerid;
		this.profession = profession;
		this.gender = gender;
		this.level = level;
		this.serverid = serverid;
		this.offlinetime = BigInteger.valueOf(offlinetime);
	}

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

	public int getProfession() {
		return profession;
	}

	public void setProfession(int profession) {
		this.profession = profession;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getServerid() {
		return serverid;
	}

	public void setServerid(String serverid) {
		this.serverid = serverid;
	}

	public BigInteger getOfflinetime() {
		return offlinetime;
	}

	public void setOfflinetime(BigInteger offlinetime) {
		this.offlinetime = offlinetime;
	}
}
