package com.qlbs.Bridge.common.result;

/**
 * 客户端结果集抽象类
 * 
 * @auth Jeremy
 * @date 2018年9月21日下午1:54:38
 */
public class AbstractResult implements IResult {

	// 错误码
	private int code;
	// 错误描述
	private String desc;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public AbstractResult(int code, String desc) {
		super();
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 是否正确
	 * 
	 * @return
	 * @return boolean
	 * @date 2018年12月29日下午3:36:22
	 */
	@Override
	public boolean isSuccess() {
		return code == ErrorCodeEnum.SUCCESS.getStatus();
	}

	public AbstractResult(ErrorCodeEnum eEnum) {
		super();
		this.code = eEnum.getStatus();
		this.desc = eEnum.getDesc();
	}

	@Override
	public IResult build() {
		return this;
	}

	@Override
	public String toString() {
		return "AbstractResult [code=" + code + ", desc=" + desc + "]";
	}

	@Override
	public String getResult() {
		return null;
	}

	public static IResult build(ErrorCodeEnum codeEnum) {
		return new AbstractResult(codeEnum);
	}

}
