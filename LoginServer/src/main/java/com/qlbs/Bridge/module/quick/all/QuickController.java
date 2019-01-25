package com.qlbs.Bridge.module.quick.all;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qlbs.Bridge.common.ResultCode;
import com.qlbs.Bridge.common.result.IResult;
import com.qlbs.Bridge.domain.OrderStatusEnum;
import com.qlbs.Bridge.domain.entity.PayOrder;
import com.qlbs.Bridge.module.AbstractController;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@RestController
@RequestMapping("/quick")
public class QuickController extends AbstractController {

	@Autowired
	private QuickService service;

	@Override
	public IResult login(HttpServletRequest request, HttpServletResponse response) {
		// uid从客户端接口获取到的渠道原始uid,无需任何加工如拼接渠道ID等
		String userId = request.getParameter("userId");
		String serverIdStr = request.getParameter("serverId");
		String gameKey = request.getParameter("gameKey");
		// 游戏客户端从SDK客户端中获取的token值,原样传递无需解密,此值长度范围小于512,CP需预留足够长度
		String token = request.getParameter("token");
		// quick渠道下得子渠道, 譬如如360游戏、91助手, 一般用于订单统计
		String channelId = formatStr(request.getParameter("channelId"));
		String qdCode1 = request.getParameter("qdCode1");
		String qdCode2 = request.getParameter("qdCode2");

		IResult result = checkParams(userId, serverIdStr, gameKey, token, channelId, qdCode1, qdCode2);
		if (result != null) {
			info("The parameters are null or empty, userId:{}, serverId:{}, gameKey:{}, token:{}, channelId:{}, qdCode1:{}, qdCode2:{}", //
					userId, serverIdStr, gameKey, token, channelId, qdCode1, qdCode2);
			return result;
		}
		result = service.preAuthenticate(Integer.valueOf(qdCode1), userId, token);
		if (!result.isSuccess()) {
			return result;
		}
		return getUserDataService().authenticate(userId, serverIdStr, gameKey, qdCode1);
	}

	// 替换{ | } | -
	private String formatStr(String s) {
		if (StringUtils.isBlank(s)) {
			return s;
		}
		return s.replaceAll("\\{|\\}|\\-", "");
	}

	/**
	 * 同步充值
	 * 
	 * @return 同步成功游戏方需要对该订单进行排重处理，检查是否处理过，处理过不再处理。处理成功回写SUCCESS给QuickSDK。
	 *         游戏在返回失败时(非SUCCESS时),可以给出简明的错误提示.如:SignError、AmountError
	 */
	@Override
	public String excharge(HttpServletRequest request, HttpServletResponse response) {
		String ntData = request.getParameter("nt_data");
		String sign = request.getParameter("sign");
		String md5Sign = request.getParameter("md5Sign");
		if (!service.verifyOrder(ntData, sign, md5Sign)) {
			return ResultCode.CODE_SIGN_ERROR;
		}
		// 渠道参数转成对象便于使用
		QuickData data = service.strToQuickData(ntData);
		if (data == null) {
			return ResultCode.CODE_FAILD;
		}
		String resultCode = ResultCode.CODE_FAILD;
		PayOrder payOrder = getPayOrderService().getPayOrder(data.getGame_order());
		if (payOrder != null) {
			// 兑换游戏币
			getPayOrderService().exchangeGameMoney(payOrder);
			payOrder.setPayStatus(OrderStatusEnum.Order_Confirmed_Exchanging);
			payOrder.setChannelOrderId(data.getGame_order());
			resultCode = ResultCode.CODE_SUCCESS;
		} else {
			resultCode = ResultCode.CODE_ORDER_ERROR;
			info("充值失败订单有误, data:{}", data);
		}
		return resultCode;
	}

	public static void main(String[] args) {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><quicksdk_message><message><is_test>1</is_test><channel>0</channel><channel_uid>506067f729485b32d687eb3cd7d63fba@_()</channel_uid><channel_order></channel_order><game_order>9536F90D879041B3B45B9A5702D4ACB4</game_order><order_no>00020180904204119476215793</order_no><pay_time>2018-09-04 20:41:19</pay_time><amount>600.00</amount><status>0</status><extras_params>test</extras_params></message></quicksdk_message>";
		// String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"
		// standalone=\"no\"?><quicksdk_message><is_test>1</is_test><channel>0</channel><channel_uid>506067f729485b32d687eb3cd7d63fba@_()</channel_uid><channel_order></channel_order><game_order>9536F90D879041B3B45B9A5702D4ACB4</game_order><order_no>00020180904204119476215793</order_no><pay_time>2018-09-04
		// 20:41:19</pay_time><amount>600.00</amount><status>0</status><extras_params>test</extras_params></quicksdk_message>";
		xml = xml.replaceAll("\\<quicksdk_message>|\\</quicksdk_message>", "");
		System.out.println(xml);
		XStream xStream = new XStream(new DomDriver());
		// QuickStream xStream = new QuickStream();
		xStream.alias("message", QuickData.class);
		// xStream.omitField(QuickData.class, "quicksdk_message");
		// xStream.alias("message", QuickData.class);
		// System.out.println(xStream.fromXML(xml).toString());
		QuickData data = (QuickData) xStream.fromXML(xml);
		System.out.println("toBean:" + data);

	}

}
