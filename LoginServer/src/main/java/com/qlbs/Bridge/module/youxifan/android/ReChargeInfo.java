package com.qlbs.Bridge.module.youxifan.android;

public class ReChargeInfo {

	public String platformId; // 联运平台Id
	public String uid; // 联运平台用户Id
	public String zoneId; // 服务器分区Id
	public String roleId; // 角色Id
	public String cpOrderId; // CP订单号
	public String orderId; // 聚合订单号
	public String orderStatus; // 订单状态1:成功；其他的均为失败
	public String amount; // 成功充值金额(单位：分)
	public String extInfo; // CP自定义参数
	public String payTime; // 提交充值时间(yyyyMMddHHmmss)
	public String paySucTime; // 充值成功时间(yyyyMMddHHmmss)
	public String notifyUrl; // 支付结果通知地址(CP充值时传入的值)
	public String clientType; // 游戏客户端类型(1:android 2:ios)
	public String sign; // 参数签名串

	/**
	 * 检测充值回调的合法性
	 * 
	 * @return
	 */
	public boolean checkValidate(String verifyStr) {
		return verifyStr.equals(sign);
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getCpOrderId() {
		return cpOrderId;
	}

	public void setCpOrderId(String cpOrderId) {
		this.cpOrderId = cpOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getExtInfo() {
		return extInfo;
	}

	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getPaySucTime() {
		return paySucTime;
	}

	public void setPaySucTime(String paySucTime) {
		this.paySucTime = paySucTime;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "ReChargeInfo [platformId=" + platformId + ", uid=" + uid + ", zoneId=" + zoneId + ", roleId=" + roleId + ", cpOrderId=" + cpOrderId + ", orderId=" + orderId + ", orderStatus=" + orderStatus + ", amount=" + amount + ", extInfo=" + extInfo + ", payTime=" + payTime + ", paySucTime=" + paySucTime
				+ ", notifyUrl=" + notifyUrl + ", clientType=" + clientType + ", sign=" + sign + "]";
	}

}
