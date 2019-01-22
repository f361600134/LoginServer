package com.qlbs.Bridge.module.youxifan.ios;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qlbs.Bridge.common.result.IResult;
import com.qlbs.Bridge.module.AbstractController;
import com.qlbs.Bridge.module.youxifan.ios.param.YouxifanIOSExchargeParam;
import com.qlbs.Bridge.module.youxifan.ios.param.YouxifanIOSLoginParam;
import com.qlbs.Bridge.util.CommonUtil;

@RestController
@RequestMapping("/YouxifanIOS")
public class YouxifanIOSController extends AbstractController {

	@Override
	public Class<?> getLoginParams() {
		return YouxifanIOSLoginParam.class;
	}

	// @Override
	// public IResult login(HttpServletRequest request, HttpServletResponse
	// response) {
	// info("This is YouxifanIOSController's login method");
	// Map<String, String[]> paramMap = request.getParameterMap();
	// try {
	// YouxifanIOSLoginParam param =
	// CommonUtil.convertMap(YouxifanIOSLoginParam.class, paramMap);
	// IResult result = checkParams(param);
	// if (result == null) {
	// // TODO
	// info("验证成功...");
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// error("强转参数错误");
	// }
	// return null;
	// }

	/**
	 * 可以改成注解参数, 好像应该是支持的. request内容转对象参数
	 *
	 */
	@Override
	public String excharge(HttpServletRequest request, HttpServletResponse response) {
		info("This is YouxifanIOSController's excharge method");
		Map<String, String[]> paramMap = request.getParameterMap();
		try {
			YouxifanIOSExchargeParam param = CommonUtil.convertMap(YouxifanIOSExchargeParam.class, paramMap);
			IResult result = checkParams(param);
			if (result == null) {
				// TODO
				info("验证成功...");
			}
		} catch (Exception e) {
			error("充值验证失败, 参数:{}", e);
		}
		return null;
	}

	// @Override
	// public String excharge(@RequestParam Map<String, String> parameter) {
	// logger.info("parameter:{}", parameter);
	// YouxifanIOSExchargeParam param = new YouxifanIOSExchargeParam();
	// try {
	// BeanUtils.populate(param, parameter);
	// IResult result = checkParams(param);
	// if (result == null) {
	// // logger.info("param:{}", param.toString());
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return param.toString();
	// }

}
