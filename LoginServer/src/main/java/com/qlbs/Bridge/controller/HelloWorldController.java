package com.qlbs.Bridge.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qlbs.Bridge.domain.entity.UserData;
import com.qlbs.Bridge.repository.UserDataRepository;
import com.qlbs.Bridge.util.CommonUtil;

@RestController
public class HelloWorldController {

	// @Autowired
	// private ExtractSessionFactory session;
	@Autowired
	private UserDataRepository userRepository;

	@RequestMapping("/index")
	public String index() {
		return "Hello World";
	}

	@RequestMapping("/param1")
	public String param1(Stu stu) {
		System.out.println(stu);
		return stu.toString();
	}

	@RequestMapping(value = "/param2")
	public String param2(@RequestParam Map<String, String> parameter) {
		System.out.println(parameter);
		return parameter.toString();
	}

	@RequestMapping("/param")
	public String param(HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		try {
			Stu stu = CommonUtil.convertMap(Stu.class, map);
			System.out.println("==>" + stu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (String key : map.keySet()) {
			System.out.println(key + ":" + Arrays.toString(map.get(key)));
		}
		return "Hello World";
	}

	// http://localhost:8181/save?uid=1&serverId=1&identityId=1
	@RequestMapping("/save")
	public UserData save(HttpServletRequest request) {
		String userId = request.getParameter("uid");
		UserData user = new UserData();
		user.setIdentityId(userId);
		user.setUserId(userId);
		user.setServerId(Integer.valueOf(userId));
		userRepository.save(user);
		// session.create(user);
		return user;
	}

	@RequestMapping("/update")
	public UserData update(HttpServletRequest request) {
		String userId = request.getParameter("uid");
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		UserData user = new UserData();
		user.setIdentityId(userId);
		user.setUserId(userId);
		user.setServerId(Integer.valueOf(userId));
		// userRepository.save(user);
		session.save(user);
		session.getTransaction().commit();
		return user;
	}

	@RequestMapping("/save1")
	public UserData save1(HttpServletRequest request) {
		List<UserData> users = new ArrayList<>();
		String userId = "4";
		UserData user = new UserData();
		user.setIdentityId(userId);
		user.setUserId(userId);
		user.setServerId(Integer.valueOf(userId));

		userId = "5";
		UserData user2 = new UserData();
		// user2.setIdentityId(userId);
		user2.setUserId(userId);
		user2.setServerId(Integer.valueOf(userId));

		users.add(user);
		users.add(user2);

		userRepository.save(users);
		return user;
	}

}
