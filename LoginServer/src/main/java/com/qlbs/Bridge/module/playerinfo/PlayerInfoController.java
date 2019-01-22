package com.qlbs.Bridge.module.playerinfo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.qlbs.Bridge.domain.entity.PlayerInfo;
import com.qlbs.Bridge.service.PlayerInfoService;

/**
 * 广州青蓝冰水有限公司
 * 
 * @auth Jimmy
 * @date:2018/11/21 21:31
 **/
@RestController
@RequestMapping("/playerinfo")
public class PlayerInfoController {

	@Resource
	private PlayerInfoService playerInfoService;

	@RequestMapping("/save")
	public void save(HttpServletRequest request) {
		String account = request.getParameter("account");
		String playerid = request.getParameter("playerid");
		int profession = Integer.parseInt(request.getParameter("profession"));
		int gender = Integer.parseInt(request.getParameter("gender"));
		int level = Integer.parseInt(request.getParameter("level"));
		String serverid = request.getParameter("serverid");
		long offlinetime = Long.parseLong(request.getParameter("offlinetime"));
		PlayerInfo playerInfo = new PlayerInfo(account, playerid, profession, gender, level, serverid, offlinetime);
		playerInfoService.save(playerInfo);
	}

	@RequestMapping("/playerinfo")
	public String playerinfo(HttpServletRequest request) {
		String account = request.getParameter("account");
		Map<String, List<PlayerInfo>> playerInfoList = playerInfoService.getPlayerInfoList(account);
		return JSONArray.toJSONString(playerInfoList);
	}
}
