package com.namoo.club.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.namoo.club.shared.BaseMongoTestCase;

import dom.entity.ClubCategory;
import dom.entity.Community;

public class CommunityDaoTest extends BaseMongoTestCase{
	//
	private static final String DATASET_JSON = "/com/namoo/club/dao/communities.json";
	
	@Autowired
	private CommunityDao dao;
	
	@Autowired
	private UserDao userDao;
	
	//-------------------------------------------
	@Test
	public void testReadAllCommunities() {
		//
		assertEquals(2, dao.readAllCommunities().size());
	}
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testCreateCommunity() {
		//
		Community community = new Community();
		community.setName("test");
		community.setDescription("testDes");
		
		int comNo = dao.createCommunity(community);
		//검증
		community = dao.readCommunity(comNo);
		assertEquals("test", community.getName());
		assertEquals("testDes", community.getDescription());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testUpdateCommunity() {
		//
		Community community = dao.readCommunity(1);
		community.setName("com1_test");
		community.setDescription("com1_des_test");
		dao.updateCommunity(community);
		
		//검증
		community = dao.readCommunity(1);
		assertEquals("com1_test", community.getName());
		assertEquals("com1_des_test", community.getDescription());
	}
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testDeleteCommunity() {
		//
		dao.deleteCommunity(2);
		//검증
		assertEquals(1, dao.readAllCommunities().size());
	}
	
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadAllCategories() {
		//
		assertEquals(2, dao.readAllCategories(1).size());
	}
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testCreateClubCategory() {
		//
		List<ClubCategory> categories = new ArrayList<ClubCategory>();
		ClubCategory category = new ClubCategory(2, 2, "category2");
		ClubCategory category2 = new ClubCategory(3, 2, "category3");
		categories.add(category);
		categories.add(category2);
		
		dao.insertCategories(categories);
		//검증
		assertEquals(2, dao.readAllCategories(2).size());
	}
}
