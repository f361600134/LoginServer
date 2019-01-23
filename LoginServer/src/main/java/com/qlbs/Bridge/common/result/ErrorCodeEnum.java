package com.qlbs.Bridge.common.result;

/**
 * 验证错误码
 * 
 * @auth Jeremy
 * @date 2018年9月3日下午8:41:10
 */
public enum ErrorCodeEnum {

	SUCCESS(0, ""), //
	IllEGAL_PARAMS(1, "参数错误"), //
	ERROR_UNKNOWN(2, "未知错误"), //
	ERROR_TIMEOUT(3, "超时错误"), //
	ERROR_CREATE_USER(4, "玩家保存失败"), //
	ERROR_NO_LOGIN(5, "用户未登录"), //
	ERROR_SIGN(6, "签名错误"), //
	ERROR_FORBIDDEN_IP(7, "很抱歉，您的IP已被禁止登陆"), //
	ERROR_SUB_ERROR(8, "无子包标识"), //
	SERVER_MAINTENANCE(9, "服务器正在维护"), //
	ERROR_CHANNEL(10, "未知的客户端渠道号"), //
	ERROR_ORDER_CREATE(11, "订单创建失败"), //
	ERROR_RUNNING(12, "代码错误"), //
	;

	private int status;
	private String desc;

	private ErrorCodeEnum(int status, String desc) {
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
	public static ErrorCodeEnum getStateEnum(int status) {
		for (ErrorCodeEnum statusEnum : values()) {
			if (statusEnum.status == status) {
				return statusEnum;
			}
		}
		return null;
	}

}
