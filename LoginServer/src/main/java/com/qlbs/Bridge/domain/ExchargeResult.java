package com.qlbs.Bridge.domain;

public class ExchargeResult {

	private int code;
	private String description;
	private int gameMoney;
	private int payMoney;

	public int getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(int payMoney) {
		this.payMoney = payMoney;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public int getGameMoney() {
		return gameMoney;
	}

	public void setGameMoney(int gameMoney) {
		this.gameMoney = gameMoney;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isSuccess() {
		return getCode() == 0;
	}

	@Override
	public String toString() {
		return "ExchargeResult [code=" + code + ", description=" + description + ", gameMoney=" + gameMoney + ", payMoney=" + payMoney + "]";
	}

}