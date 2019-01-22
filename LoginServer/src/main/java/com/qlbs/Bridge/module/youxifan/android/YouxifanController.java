package com.qlbs.Bridge.module.youxifan.android;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qlbs.Bridge.common.ResultCode;
import com.qlbs.Bridge.common.result.IResult;
import com.qlbs.Bridge.domain.OrderStatusEnum;
import com.qlbs.Bridge.domain.entity.PayOrder;
import com.qlbs.Bridge.module.AbstractController;

@RestController
@RequestMapping("/youxifan")
public class YouxifanController extends AbstractController {

	@Resource
	private YouxifanService service;

	@Override
	public IResult login(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("uid");
		String tstamp = request.getParameter("tstamp");
		String serverIdStr = request.getParameter("serverId");
		String gameKey = request.getParameter("gameKey");
		String qdCode1 = request.getParameter("qdCode1");
		String qdCode2 = request.getParameter("qdCode2");
		if (StringUtils.isEmpty(tstamp))
			tstamp = "0";
		IResult result = checkParams(userId, gameKey, serverIdStr, qdCode1, qdCode2);
		if (result != null) {
			error("The parameters are null or empty, userId:{}, tstamp:{}, gameKey:{}, serverId:{}, qdCode1:{}, qdCode2:{}", //
					userId, tstamp, gameKey, serverIdStr, qdCode1, qdCode2);
			return result;
		}
		// TODO act=0则sdk校验, 否则跳过校验
		result = getUserDataService().authenticate(userId, serverIdStr, gameKey, qdCode1);
		return result;
	}

	/**
	 *
	 * @return 0：接收成功 1:签名错误 2：未知错误
	 */
	@Override
	public String excharge(HttpServletRequest request, HttpServletResponse response) {
		String resultCode = null;
		ReChargeInfo reChargeInfo = null;
		try {
			// 解析平台方参数
			reChargeInfo = service.parsReChargeInfo(request);
			// 获取到有效订单信息
			PayOrder payOrder = getPayOrderService().getPayOrder(reChargeInfo.getCpOrderId());
			if (payOrder != null) {
				// 验证订单信息
				boolean flag = service.verifyOrder(reChargeInfo, payOrder);
				if (flag) {
					// 兑换游戏币
					getPayOrderService().exchangeGameMoney(payOrder);
					resultCode = ResultCode.CODE_0;
				} else {
					resultCode = ResultCode.CODE_1;
					info("充值回调数据校验错误, reChargeInfo:{}", reChargeInfo);
				}
				payOrder.setPayStatus(OrderStatusEnum.Order_Confirmed_Exchanging);
				payOrder.setChannelOrderId(reChargeInfo.getOrderId());
			} else {
				resultCode = ResultCode.CODE_1;
				info("充值失败订单有误, reChargeInfo:{}", reChargeInfo);
			}
		} catch (Exception e) {
			resultCode = ResultCode.CODE_2;
			error("充值回调出现异常, reChargeInfo:{}", reChargeInfo);
		}
		return resultCode;
	}

}
