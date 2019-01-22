package com.qlbs.Bridge.module.youxifan.android;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.qlbs.Bridge.domain.entity.PayOrder;
import com.qlbs.Bridge.util.CommonUtil;
import com.qlbs.Bridge.util.MD5;

/**
 * youxifan业务逻辑, 用于游戏饭平台返回的数据解析, 验证, 数据获取等
 * 
 * @auth Jeremy
 * @date 2018年9月10日下午6:27:45
 */
@Service
public class YouxifanService {

	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 解析参数
	 * 
	 * @param request
	 * @return
	 * @return ReChargeInfo
	 * @date 2018年10月8日上午9:55:59
	 */
	public ReChargeInfo parsReChargeInfo(HttpServletRequest request) {
		ReChargeInfo rs = new ReChargeInfo();
		rs.setPlatformId(request.getParameter("platformId"));
		rs.setUid(request.getParameter("uid"));
		rs.setZoneId(request.getParameter("zoneId"));
		rs.setRoleId(request.getParameter("roleId"));
		rs.setCpOrderId(request.getParameter("cpOrderId"));
		rs.setOrderId(request.getParameter("orderId"));
		rs.setOrderStatus(request.getParameter("orderStatus"));
		rs.setAmount(request.getParameter("amount"));
		rs.setExtInfo(request.getParameter("extInfo"));
		rs.setPayTime(request.getParameter("payTime"));
		rs.setPaySucTime(request.getParameter("paySucTime"));
		rs.setNotifyUrl(request.getParameter("notifyUrl"));
		rs.setClientType(request.getParameter("clientType"));
		rs.setSign(request.getParameter("sign"));
		return rs;
	}

	/**
	 * 校验签名
	 * 
	 * @param reChargeInfo
	 * @return
	 * @return true means
	 * @date 2018年9月10日下午6:26:51
	 */
	public boolean verifyOrder(ReChargeInfo reChargeInfo, PayOrder payOrder) {
		String[] extract = new String[] { "sign" };
		Map<String, String> resultMap = CommonUtil.objToTreeMap(reChargeInfo, extract);
		String localSign = getLocalSign(resultMap, payOrder);
		if (StringUtils.isAnyBlank(localSign, reChargeInfo.getSign())) {
			logger.error("签名为空,  localSign:{}, reChargeInfo:{}", localSign, reChargeInfo.getSign());
			return false;
		}
		if (StringUtils.equals(reChargeInfo.getSign(), localSign)) {
			return true;
		}
		logger.error("签名不相同,  localSign:{}, reChargeInfo:{}", localSign, reChargeInfo.getSign());
		return false;
	}

	/**
	 * 获得自己md5 <br>
	 * 格式: key1=value1&key2=value2&key3=value3APPKEY<br>
	 * 
	 * @param map
	 * @return
	 */
	private String getLocalSign(Map<String, String> map, PayOrder payOrder) {
		StringBuilder url = new StringBuilder();
		// key1=value1&key2=value2&key3=value3&
		for (Map.Entry<String, String> entry : map.entrySet()) {
			url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		// key1=value1&key2=value2&key3=value3
		// 移除掉最后的&
		url.deleteCharAt(url.length() - 1);
		// key1=value1&key2=value2&key3=value3appkey
		url.append(YouxifanConfig.appkey);
		String mySign = MD5.digest(url.toString(), true);
		return mySign;
	}

}
