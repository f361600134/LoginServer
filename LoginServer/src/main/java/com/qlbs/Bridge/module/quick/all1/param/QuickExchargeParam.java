package com.qlbs.Bridge.module.quick.all1.param;

import com.qlbs.Bridge.common.annotation.Filed;
import com.qlbs.Bridge.module.common.impl.IExchargeParam;

public class QuickExchargeParam implements IExchargeParam {

	private String nt_data;
	private String sign;
	private String md5Sign;

	@Filed(isNull = true)
	private IExchargeParam quickData;

	@Override
	public String getOrderId() {
		return quickData.getOrderId();
	}

	public String getNt_data() {
		return nt_data;
	}

	public void setNt_data(String nt_data) {
		this.nt_data = nt_data;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getMd5Sign() {
		return md5Sign;
	}

	public void setMd5Sign(String md5Sign) {
		this.md5Sign = md5Sign;
	}

	public IExchargeParam getQuickData() {
		return quickData;
	}

	public void setQuickData(IExchargeParam quickData) {
		this.quickData = quickData;
	}

	@Override
	public void setIExchargeParam(IExchargeParam param) {
		this.quickData = param;
	}

}
