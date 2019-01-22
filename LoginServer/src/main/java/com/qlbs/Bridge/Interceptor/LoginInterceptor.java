package com.qlbs.Bridge.Interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.qlbs.Bridge.common.config.DefineConfig;
import com.qlbs.Bridge.common.result.ErrorCodeEnum;
import com.qlbs.Bridge.common.result.IResult;
import com.qlbs.Bridge.common.result.support.AuthResult;
import com.qlbs.Bridge.util.HttpClientUtil;
import com.qlbs.Bridge.util.UrlUtil;

/**
 * 登录拦截器,拦截所有平台的ip
 * 
 * @test http://url/whitelist?ip=1&channel=*
 * @auth Jeremy
 * @date 2018年9月10日上午11:51:27
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 拦截所有平台的登录, 如果校验不通过, 返回前端IP被封等信息
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean bool = false;
		// // 服务器状态为维护
		// if (checkServerState(request, response)) {
		// // 白名单校验
		// bool = checkWhiteList(request, response);
		// } else {
		// 否则黑名单校验
		bool = checkBlackList(request, response);
		// }
		return bool;
	}

	/**
	 * 校验服务器状态是否为维护状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @return boolean
	 * @date 2018年10月11日上午11:13:43
	 */
	private boolean checkServerState(HttpServletRequest request, HttpServletResponse response) {
		String url = DefineConfig.serverMgrUrl + "?action=serverState";
		byte result = -99;// 默认错误
		try {
			String temp = HttpClientUtil.doGet(url);
			result = temp == null ? result : Byte.valueOf(temp);
			// logger.info("checkServerState 获取服务器状态: result:" + result);
		} catch (Exception e) {
			logger.error("url:{}, result:{}", url, result);
			logger.error("getInfoFromService has an error", e);
		}
		// -1:已被删除,0:停机,1:维护,2:良好,3:繁忙,4:爆满,5:火爆
		return result == 1;
	}

	/**
	 * 白名单校验
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @return boolean
	 * @date 2018年10月10日下午8:48:18
	 */
	private boolean checkWhiteList(HttpServletRequest request, HttpServletResponse response) {
		// 玩家的账号
		String userId = request.getParameter("userId");
		String serverId = request.getParameter("serverId");
		if (StringUtils.isAnyBlank(userId, serverId)) {
			logger.info("checkWhiteList error, userId:[{}] or serverId:[{}] is must not be null", userId, serverId);
			return false;
		}
		String url = DefineConfig.serverMgrUrl + "?action=InternalLogin&userId=" + userId + "&serverId=" + serverId;
		String result = null;
		try {
			result = HttpClientUtil.doGet(url);
			// true表示存在, false表示不在白名单列表, 不提示任何信息,直接返回登录失败
			if (StringUtils.equals(result, "true"))
				return true;

		} catch (Exception e) {
			logger.error("url:{}, userId:{}, result:{}", url, userId, result);
			logger.error("checkWhiteList has an error", e);
		}
		logger.info("checkWhiteList error, the user is not in internal list, userId:{}, serverId:{}, result:{}", userId, serverId, result);
		return false;
	}

	/**
	 * 黑名单校验
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @return boolean
	 * @date 2018年10月10日下午8:36:05
	 */
	private boolean checkBlackList(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder builder = new StringBuilder(DefineConfig.serverMgrUrl);
		builder.append("/whitelist").append("?ip=").append(UrlUtil.getIpAddr(request)).append("&channel=*");// *表示所有渠道
		String url = builder.toString();
		String result = null;
		try {
			result = HttpClientUtil.doGet(url);
			// true通过, false不通过
			if (StringUtils.equals(result, "false")) {
				IResult failed = AuthResult.faild(ErrorCodeEnum.ERROR_FORBIDDEN_IP);
				String json = JSON.toJSONString(failed);
				// 这里的json单独构造,不支持直接返回json
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println(json);
				out.flush();
				out.close();
				return false;
			}
		} catch (Exception e) {
			logger.error("url:{}, getRequestURI:{}, getRemoteAddr:{}, result:{}", url, request.getRequestURI(), UrlUtil.getIpAddr(request), result);
			logger.error("checkBlackList has an error", e);
		}
		return true;
	}

}
