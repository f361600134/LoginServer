package com.qlbs.Bridge.common.result;

/**
 * 返回前端接口类
 * 
 * @auth Jeremy
 * @date 2018年9月21日下午1:55:02
 */
public interface IResult {

	/**
	 * 构建返回结果
	 * 
	 * @return
	 * @return IResult
	 * @date 2018年9月21日下午2:06:23
	 */
	public IResult build();

	/**
	 * 是否成功
	 * 
	 * @return
	 * @return boolean
	 * @date 2018年12月29日下午5:30:54
	 */
	public boolean isSuccess();

}
