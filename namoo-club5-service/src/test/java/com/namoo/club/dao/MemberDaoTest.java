package com.namoo.club.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.namoo.club.shared.BaseMongoTestCase;

import dom.entity.ClubManager;
import dom.entity.ClubMember;
import dom.entity.Community;
import dom.entity.CommunityManager;
import dom.entity.CommunityMember;
import dom.entity.SocialPerson;

public class MemberDaoTest extends BaseMongoTestCase{
	//
	private static final String DATASET_JSON = "/com/namoo/club/dao/clubs.json";
	
	@Autowired
	private MemberDao dao;
	@Autowired
	private CommunityDao comDao;

	//-------------------------------------------------------------------------
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testAddCommunityManager() {
		//
		Community community = new Community("com3", "com3_des");
		int comNo = comDao.createCommunity(community);
		
		CommunityManager comManager = new CommunityManager();
		comManager.setCommunityNo(comNo);
		
		SocialPerson person = new SocialPerson("이주성", "wntjd@naver.com", "1234");
		comManager.setRolePerson(person);
		
		dao.addCommunityManager(comManager);
		
		//검증
		CommunityManager manager = dao.readCommunityManager(comNo);
		assertEquals("wntjd@naver.com",manager.getEmail());
		assertEquals("이주성", manager.getName());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testAddCommunityMember() {
		//
		CommunityMember comMember = new CommunityMember(2, new SocialPerson("wntjd@naver.com", "이주성"));
		dao.addCommunityMember(comMember);
		//검증
		assertEquals("wntjd@naver.com", dao.readCommunityMember(2, "wntjd@naver.com").getEmail());
		assertEquals("이주성", dao.readCommunityMember(2, "wntjd@naver.com").getName());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadCommunityMember() {
		//
		CommunityMember comMember = dao.readCommunityMember(1, "wntjd@naver.com");
		//검증
		assertEquals("이주성", comMember.getName());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadCommunityManager() {
		//
		CommunityManager comManager = dao.readCommunityManager(1);
		//검증
		assertEquals("이주성", comManager.getName());
	}
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadAllCommunityMember() {
		//
		assertEquals(1, dao.readAllCommunityMember(1).size());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testDeleteCommuninyMember() {
		//
		dao.deleteCommuninyMember(1, "wntjd@naver.com");
		assertEquals(0, dao.readAllCommunityMember(1).size());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testUpdateCommunityManager() {
		//
		CommunityManager communityManager = new CommunityManager(1, new SocialPerson("박상희","ekdgml@naver.com", "asdf"));
		dao.updateCommunityManager(communityManager);
		
		//assertion
		assertEquals("ekdgml@naver.com", dao.readCommunityManager(1).getEmail());
		assertEquals("박상희", dao.readCommunityManager(1).getName());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testAddClubMember() {
		//
		ClubMember clubMember = new ClubMember(2, new SocialPerson("wntjd@naver.com", "이주성"));
		dao.addClubMember(clubMember);
		//검증
		clubMember = dao.readClubMember(2, "wntjd@naver.com");
		assertEquals("이주성", clubMember.getName());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testAddClubManager() {
		//
		ClubManager clubManager = new ClubManager(2, new SocialPerson("wntjd@naver.com", "이주성"), false);
		dao.addClubManager(clubManager);
		//검증
		assertEquals("이주성", dao.readClubManager(2, "wntjd@naver.com").getName());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testAddClubManager_ForKing() {
		//
		ClubManager clubKingManager = new ClubManager(2, new SocialPerson("wntjd@naver.com", "이주성"), true);
		dao.addClubManager(clubKingManager);
		//검증
		assertEquals("이주성", dao.readClubKingManager(2).getName());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testDeleteClubMember() {
		//
		dao.deleteClubMember(1, "wntjd@naver.com");
		//검증
		assertEquals(2, dao.readAllClubMembers(1).size());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testDeleteClubManager() {
		//
		dao.deleteClubManager(1, "hong@naver.com");
		//검증
		assertEquals(1, dao.readAllClubManagers(1).size());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadAllClubMembers() {
		//
		List<ClubMember> results = dao.readAllClubMembers(1);
		assertEquals(3, results.size());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadAllClubManagers() {
		//
		assertEquals(2, dao.readAllClubManagers(1).size());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadClubMember() {
		//
		ClubMember clubMember = dao.readClubMember(1, "ekdgml@naver.com");
		assertEquals("박상희", clubMember.getName());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadClubManager() {
		//
		ClubManager clubManager = dao.readClubManager(1, "hong@naver.com");
		assertEquals("홍길동", clubManager.getName());
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadClubKingManager() {
		//
		ClubManager clubKingManager = dao.readClubKingManager(1);
		assertEquals("박상희", clubKingManager.getName());
	}
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testUpdateClubManager() {
		//
		ClubManager clubManager = new ClubManager(1, new SocialPerson("ekdgml@naver.com", "이주성"), false);
		dao.updateClubManager(clubManager);
		
		//assertion
		
		assertEquals("이주성", dao.readClubManager(1, "ekdgml@naver.com").getName());
		assertFalse(dao.readClubManager(1, "ekdgml@naver.com").isKingManager());
	}

}
