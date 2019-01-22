package com.qlbs.Bridge.domain;

public enum OrderStatusEnum {

	Order_Delete_By_Manual(-3, "非法充值，手动作废订单"), //
	Order_Params_Error(-2, "订单请求参数非法"), //
	Order_Created_Failure(-1, "订单创建失败..."), //
	Order_Created_Submitting(1, "订单创建完成,等待提交"), //
	Order_Submitted_Break(2, "订单完成提交,异常中断"), //
	Order_Submitted_Confirming(3, "订单完成提交,等待确认"), //
	Order_Confirmed_Break(4, "订单完成确认,异常中断"), //
	Order_Confirmed_Failure(5, "订单完成确认,支付结果为失败"), //
	Order_Confirmed_Exchanging(6, "订单完成确认,支付成功,等待兑换"), //
	Order_Exchanged_Break(7, "订单兑换游戏币失败,异常中断"), //
	Order_Exchanged_Success(8, "订单兑换游戏币成功"), //
	Order_Rescue_Exchanged_Success(9, "补单兑换游戏币成功"), //
	Order_Rescue_Exchanged_Failure(10, "补单兑换游戏币失败"), //
	Order_Exchanged_Failure(11, "订单兑换游戏币失败"), //
	Order_Created_Paying(12, "订单创建完成，等待支付"),;

	private int status;
	private String desc;

	private OrderStatusEnum(int status, String desc) {
		this.status = status;
		this.desc = desc;
	}

	public int getStatus() {
		return status;
	}

	public String getDesc() {
		return desc;
	}

	/**
	 * 获取到订单枚举
	 * 
	 * @param status
	 * @return
	 * @return PayOrderStatusEnum
	 * @date 2018年9月3日下午4:19:19
	 */
	public static OrderStatusEnum getStateEnum(int status) {
		for (OrderStatusEnum statusEnum : values()) {
			if (statusEnum.status == status) {
				return statusEnum;
			}
		}
		return null;
	}

	/**
	 * 通过状态获取描述
	 * 
	 * @param status
	 * @return
	 * @return String
	 * @date 2018年9月3日下午4:19:11
	 */
	public static String getDesc(int status) {
		OrderStatusEnum statusEnum = getStateEnum(status);
		return statusEnum.getDesc();
	}
}
