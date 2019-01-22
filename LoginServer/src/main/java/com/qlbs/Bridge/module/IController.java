package com.qlbs.Bridge.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

import com.qlbs.Bridge.common.result.IResult;

public interface IController {

	/**
	 * 登录, 抽象类处理基本的无sdk登录, 增加抽象方法, 如果需要sdk登录则直接实现抽象方法即可
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @return IResult
	 * @date 2019年1月21日下午5:30:29
	 */
	@RequestMapping("/login")
	public IResult login(HttpServletRequest request, HttpServletResponse response);

	@RequestMapping("/createOrder")
	public IResult createOrder(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 平台商返回的值不是json格式, 所以这里的返回值改成String <br>
	 * 充值 因为每个平台充值返回的信息不同, 所以需要组装成我们需要的数据, 然后统一调用我们的接口进行充值.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @return String
	 * @date 2018年9月10日下午5:30:31
	 */
	@RequestMapping("/excharge")
	public String excharge(HttpServletRequest request, HttpServletResponse response);

	public Class<?> getLoginParams();

	public boolean checkLogin(Object param);

}
