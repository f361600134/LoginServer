package com.qlbs.Bridge.module.quick.all;

/**
 * Quick 渠道的nt_data参数的xml格式解析后数据bean
 * 
 * @auth Jeremy
 * @date 2018年12月29日下午6:28:30
 */
public class QuickData {

	// 是否为测试订单 1为测试 0为线上正式订单，游戏应根据情况确定上线后是否向测试订单发放道具。
	private String is_test;
	// 渠道标示ID 注意:游戏可根据实情,确定发货时是否校验充值来源渠道是否与该角色注册渠道相符
	private String channel;
	// 渠道用户唯一标示,该值从客户端GetUserId()中可获取
	private String channel_uid;
	// 游戏在调用QuickSDK发起支付时传递的游戏方订单,这里会原样传回
	private String game_order;
	// QuickSDK唯一订单号
	private String order_no;
	// 支付时间
	private String pay_time;
	// 成交金额，单位元，游戏最终发放道具金额应以此为准
	private String amount;
	// 充值状态:0成功, 1失败(为1时 应返回FAILED失败)
	private String status;
	// 可为空,充值状态游戏客户端调用SDK发起支付时填写的透传参数.没有则为空
	private String extras_params;
	// 官方已经删掉了
	private String channel_order;

	public String getIs_test() {
		return is_test;
	}

	public String getChannel() {
		return channel;
	}

	public String getChannel_uid() {
		return channel_uid;
	}

	public String getGame_order() {
		return game_order;
	}

	public String getOrder_no() {
		return order_no;
	}

	public String getPay_time() {
		return pay_time;
	}

	public String getAmount() {
		return amount;
	}

	public String getStatus() {
		return status;
	}

	public String getExtras_params() {
		return extras_params;
	}

	public String getChannel_order() {
		return channel_order;
	}

	public void setChannel_order(String channel_order) {
		this.channel_order = channel_order;
	}

	public void setIs_test(String is_test) {
		this.is_test = is_test;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setChannel_uid(String channel_uid) {
		this.channel_uid = channel_uid;
	}

	public void setGame_order(String game_order) {
		this.game_order = game_order;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setExtras_params(String extras_params) {
		this.extras_params = extras_params;
	}

	@Override
	public String toString() {
		return "QuickData [is_test=" + is_test + ", channel=" + channel + ", channel_uid=" + channel_uid + ", game_order=" + game_order + ", order_no=" + order_no + ", pay_time=" + pay_time + ", amount=" + amount + ", status=" + status + ", extras_params=" + extras_params + "]";
	}

}
