package com.qlbs.Bridge.module.youxifan.android2.param;

import com.qlbs.Bridge.module.common.impl.IExchargeParam;

public class YouxifanExchargeParam implements IExchargeParam {

	private String orderid;// 订单号
	private String username;// 登录账号户名
	private Integer gameid;// 游戏 ID
	private String roleid;// 游戏⻆ID
	private Integer serverid;// 服务器 ID
	private String paytype;// 付类型
	private Integer amount;// 成功充值额（元）
	private Integer paytime;// 玩家充值时间戳
	private String attach;// 商户拓展参数
	private String sign;// 服务端返回的签名
	private String userid;// 登录账号 ID(为兼容⽼包，此参数不参与签名)

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getGameid() {
		return gameid;
	}

	public void setGameid(Integer gameid) {
		this.gameid = gameid;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public Integer getServerid() {
		return serverid;
	}

	public void setServerid(Integer serverid) {
		this.serverid = serverid;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getPaytime() {
		return paytime;
	}

	public void setPaytime(Integer paytime) {
		this.paytime = paytime;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "YouxifanIOSExchargeParam [username=" + username + ", gameid=" + gameid + ", roleid=" + roleid + ", serverid=" + serverid + ", paytype=" + paytype + ", amount=" + amount + ", paytime=" + paytime + ", attach=" + attach + ", sign=" + sign + ", userid=" + userid + "]";
	}

	@Override
	public String getOrderId() {
		return getOrderid();
	}

	@Override
	public void setIExchargeParam(IExchargeParam param) {
		// TODO Auto-generated method stub

	}

}
