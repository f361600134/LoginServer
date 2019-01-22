package com.qlbs.Bridge.common;

/**
 * 不同渠道充值返回结果定义不同 有些渠道(youxifan)0表示成功, 有些(quick)1表示成功. 这里定义所有渠道需要的返回值类型
 */
public class ResultCode {

	public static String CODE_MINUS_1 = "-1";
	public static String CODE_0 = "0";
	public static String CODE_1 = "1";
	public static String CODE_2 = "2";

	public static String CODE_SUCCESS = "SUCCESS";
	public static String CODE_FAILD = "FAILD";
	public static String CODE_SIGN_ERROR = "SignError";
	public static String CODE_ORDER_ERROR = "OrderError";

}
