package com.qlbs.Bridge.module.youxifan.android2;

import com.qlbs.Bridge.common.result.ErrorCodeEnum;
import com.qlbs.Bridge.common.result.support.OrderResult;

public class YouxifanOrderResult extends OrderResult {

	private String sign;

	public YouxifanOrderResult(ErrorCodeEnum codeEnum) {
		super(codeEnum);
	}

	public YouxifanOrderResult(ErrorCodeEnum codeEnum, String orderId, String sign) {
		super(codeEnum, orderId);
		this.sign = sign;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public static YouxifanOrderResult build(ErrorCodeEnum processEnum, String orderId, String sign) {
		YouxifanOrderResult result = new YouxifanOrderResult(processEnum, orderId, sign);
		return result;
	}

}
