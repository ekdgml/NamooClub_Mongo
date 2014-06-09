package com.namoo.club.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.namoo.club.shared.BaseMongoTestCase;

import dom.entity.SocialPerson;

public class UserDaoTest extends BaseMongoTestCase{
	//
	private static final String DATASET_JSON = "/com/namoo/club/dao/users.json";
	
	@Autowired
	private UserDao dao;
	
	//-------------------------------------------------------------------------
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadAllUsers() {
		//
		assertEquals(3, dao.readAllUsers().size());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testCreateUser() {
		//
		SocialPerson user = new SocialPerson("abcd", "abcd@a.a", "abcd");
		dao.createUser(user);
		
		//검증
		user = dao.readUser("abcd@a.a");
		assertEquals("abcd", user.getPassword());
		assertEquals("abcd", user.getName());
		assertEquals("abcd@a.a", user.getEmail());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testUpdateUser() {
		//
		SocialPerson user = dao.readUser("ekdgml@naver.com");
		user.setPassword("asdfasdf");
		dao.updateUser(user);
		//검증
		user = dao.readUser("ekdgml@naver.com");
		assertEquals("asdfasdf", user.getPassword());
	}
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testDeleteUser() {
		//
		dao.deleteUser("ekdgml@naver.com");
		
		//assert
		assertNull(dao.readUser("ekdgml@naver.com"));
	}

}
