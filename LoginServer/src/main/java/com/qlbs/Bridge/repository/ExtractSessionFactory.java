package com.qlbs.Bridge.repository;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

/**
 * 用于获取到Session
 * 
 * @auth Jeremy
 * @date 2018年9月19日上午1:26:09
 */
@Repository
public class ExtractSessionFactory {

	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private final EntityManagerFactory entitySessionFactory = null;

	@Bean
	public SessionFactory getSessionFactory() {
		if (entitySessionFactory.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		return entitySessionFactory.unwrap(SessionFactory.class);
	}

}
