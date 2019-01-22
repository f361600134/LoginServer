package com.qlbs.Bridge.domain.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.qlbs.Bridge.domain.OrderStatusEnum;

/**
 * 在@Column(name = "loginname")中如果没有下划线，那么所有字符都小写，有大写会变为 “_” + 小写
 */
@Entity
@Table(name = "order_pay")
public class PayOrder {

	// 订单号（主键）
	@Id
	@Column(name = "orderId", length = 50)
	private String orderId;

	// 支付货币
	@Column(name = "money")
	private float money;

	// 支付货币(大数据)
	@Column(name = "moneyBig", scale = 2)
	private BigDecimal moneyBig;

	// 支付方式
	@Column(name = "payWay", length = 20)
	private String payWay;

	// 换算后的货币
	@Column(name = "gameMoney")
	private int gameMoney;

	// 订单的状态 {status}
	@Column(name = "status")
	private int status;

	// 订单的状态附加描述{statusDesc}
	@Column(name = "statusDesc", length = 80)
	private String statusDesc;

	// 订单生成时间
	@Column(name = "creationTime")
	private Timestamp creationTime;

	// 订单更新时间
	@Column(name = "updateTime")
	private Timestamp updateTime;

	// 订单的追踪流水备注
	@Column(name = "traces", length = 80)
	private String traces = "";

	// 订单的备注
	@Column(name = "remark", length = 255)
	private String remark;

	// 服务器id
	@Column(name = "serverId")
	private int serverId;

	// 玩家账号
	@Column(name = "identityName", length = 50)
	private String identityName;

	// 玩家id
	@Column(name = "playerId", length = 50)
	private String playerId;

	// 玩家角色名称
	@Column(name = "playerName", length = 50)
	private String playerName;

	// 玩家其他附加信息
	@Column(name = "playerOther", length = 128)
	private String playerOther;

	// 玩家充值时的系统
	@Column(name = "equipment", length = 50)
	private String equipment;

	// 父渠道id
	@Column(name = "qdCode1")
	private int qdCode1;

	// 子渠道id
	@Column(name = "qdCode2")
	private int qdCode2;

	// 渠道合作方的订单号
	@Column(name = "channelOrderId", length = 50)
	private String channelOrderId;

	// 渠道合作方用户id
	@Column(name = "userId", length = 50)
	private String userId;

	// 渠道合作方用户名
	@Column(name = "userName", length = 50)
	private String userName;

	// 渠道合作方附加信息 ：渠道名字
	@Column(name = "channelOther", length = 64)
	private String channelOther;

	// 支付卡号
	@Column(name = "cardNo", length = 64)
	private String cardNo;

	// 支付卡面额
	@Column(name = "cardMoney", length = 64)
	private String cardMoney;

	// 兑换游戏币的回调接口
	@Column(name = "exchangeUrl", length = 128)
	private String exchangeUrl;

	// 游戏key
	@Column(name = "gameKey", length = 30)
	private String gameKey;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public BigDecimal getMoneyBig() {
		return moneyBig;
	}

	public void setMoneyBig(BigDecimal moneyBig) {
		this.moneyBig = moneyBig;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public int getGameMoney() {
		return gameMoney;
	}

	public void setGameMoney(int gameMoney) {
		this.gameMoney = gameMoney;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getTraces() {
		return traces;
	}

	public void setTraces(String traces) {
		this.traces = traces;
	}

	public String appendTrace(String trace) {
		setTraces(getTraces() + "\r\n" + trace);
		return getTraces();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getIdentityName() {
		return identityName;
	}

	public void setIdentityName(String identityName) {
		this.identityName = identityName;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerOther() {
		return playerOther;
	}

	public void setPlayerOther(String playerOther) {
		this.playerOther = playerOther;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public int getQdCode1() {
		return qdCode1;
	}

	public void setQdCode1(int qdCode1) {
		this.qdCode1 = qdCode1;
	}

	public int getQdCode2() {
		return qdCode2;
	}

	public void setQdCode2(int qdCode2) {
		this.qdCode2 = qdCode2;
	}

	public String getChannelOrderId() {
		return channelOrderId;
	}

	public void setChannelOrderId(String channelOrderId) {
		this.channelOrderId = channelOrderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getChannelOther() {
		return channelOther;
	}

	public void setChannelOther(String channelOther) {
		this.channelOther = channelOther;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardMoney() {
		return cardMoney;
	}

	public void setCardMoney(String cardMoney) {
		this.cardMoney = cardMoney;
	}

	public String getExchangeUrl() {
		return exchangeUrl;
	}

	public void setExchangeUrl(String exchangeUrl) {
		this.exchangeUrl = exchangeUrl;
	}

	public String getGameKey() {
		return gameKey;
	}

	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
	}

	public void setPayStatus(OrderStatusEnum statusEnum) {
		this.setStatus(statusEnum.getStatus());
		this.setStatusDesc(statusEnum.getDesc());
		this.appendTrace(statusEnum.getDesc());
	}

	public PayOrder() {
	}

	@Override
	public String toString() {
		StringBuilder rs = new StringBuilder();
		String[] properties = { "orderId", "money", "payWay", "gameMoney", "status", "statusDesc", "creationTime", "updateTime", "traces", "remark", "serverId", "playerId", "playerName", "playerOther", "identityName", "qdCode1", "qdCode2", "channelOrderId", "userId", "channelOther", "exchangeUrl", "gameKey" };
		Object[] values = { orderId, money, payWay, gameMoney, status, statusDesc, creationTime, updateTime, traces, remark, serverId, playerId, playerName, playerOther, identityName, qdCode1, qdCode2, channelOrderId, userId, channelOther, exchangeUrl, gameKey };
		for (int i = 0, length = properties.length; i < length; i++) {
			if (values[i] != null)
				rs.append(properties[i]).append(":").append(values[i]);
		}
		return rs.toString();
	}

}
