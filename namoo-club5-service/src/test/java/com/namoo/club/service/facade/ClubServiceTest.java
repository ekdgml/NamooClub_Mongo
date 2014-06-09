package com.namoo.club.service.facade;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.namoo.club.shared.BaseMongoTestCase;

import dom.entity.Club;
import dom.entity.ClubMember;
import dom.entity.SocialPerson;

@UsingDataSet(locations="/com/namoo/club/service/facade/clubs.json", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
public class ClubServiceTest extends BaseMongoTestCase {
	//
	@Autowired
	private ClubService clubService;

	//-------------------------------------------------------------
	@Test
	
	public void testRegistClub() {
		//
		Club club = clubService.registClub(1, 1, "TestClub", "TestClub's description", "wntjd");
		
		// 검증
		club = clubService.findClub(club.getClubNo());
		assertEquals(1, club.getCategoryNo());
		assertEquals(1, club.getComNo());
		assertEquals("TestClub", club.getName());
		assertEquals("TestClub's description", club.getDescription());
		assertEquals("wntjd", clubService.findClubKingManager(club.getClubNo()).getEmail());
	}

	@Test
	
	public void testFindClub() {
		//
		Club club = clubService.findClub(1);
		
		// 검증
		assertEquals(1, club.getClubNo());
		assertEquals(1, club.getComNo());
		assertEquals(1, club.getCategoryNo());
		assertEquals("club1", club.getName());
		assertEquals("club1_des", club.getDescription());
		assertEquals("박상희", club.getKingManager().getName()); // 매니저 이름 확인
		assertEquals(2, club.getManagers().size()); // 매니저의 숫자 확인(매니저 + 킹매니저 = 2)
		assertEquals(3, club.getMembers().size()); //멤버 숫자 확인(멤버 + 매니저 + 킹매니저 = 3)
	}
	
	@Test
	
	public void testJoinAsMember() {
		//
		clubService.joinAsMember(4, "wntjd");
		Club club = clubService.findClub(4);
		//검증
		assertThat(club.getMembers().size(), is(1));
		assertThat(club.getMembers().get(0).getEmail(), is("wntjd"));
	}

	@Test
	
	public void testFindAllClubs() {
		//
		List<Club> clubs = clubService.findAllClubsByComNo(1);
		//검증
		assertEquals(4, clubs.size());
		assertThat(clubs.get(1).getName(), is("club2"));
	}

	@Test
	
	public void testFindClubMember() {
		//
		ClubMember clubMember = clubService.findClubMember(1, "wntjd");
		
		// 검증
		assertEquals(1, clubMember.getClubNo());
		assertEquals("wntjd", clubMember.getEmail());
		assertThat(clubMember.getName(), is("이주성"));
	}

	@Test
	
	public void testFindAllClubMember() {
		//
		List<ClubMember> clubMembers = clubService.findAllClubMember(1);
		
		// 검증 
		assertEquals(3, clubMembers.size());
		assertThat(clubMembers.get(0).getName(), is("박상희"));
	}

	@Test
	
	public void testRemoveClub() {
		//
		clubService.removeClub(1, true);
		
		// 검증
		List<Club> clubs = clubService.findAllClubsByComNo(1);
		assertEquals(3, clubs.size());
		assertThat(clubs.get(0).getName(), is("club2"));
		
	}

	@Test
	
	public void testFindBelongClubs() {
		//
		List<Club> clubs = clubService.findBelongClubs("wntjd", 1);
		
		// 검증
		assertEquals(1, clubs.size());
		assertThat(clubs.get(0).getName(), is("club1"));
	}

	@Test
	
	public void testFindManagedClubs() {
		//
		List<Club> managedClubs = clubService.findManagedClubsByComNo("hong", 1);
	
		//
		assertEquals(1, managedClubs.size());
		assertThat(managedClubs.get(0).getName(), is("club1"));
		
	}

	@Test
	
	public void testWithdrawalClub() {
		//
		clubService.withdrawalClub(1, "wntjd");
		//검증
		assertEquals(2, clubService.findAllClubMember(1).size());
		assertNull(clubService.findClubMember(1, "wntjd"));
	}

	@Test
	
	public void testCommissionManagerClub() {
		//
		clubService.commissionManagerClub(1, "hong", new SocialPerson("wntjd", "이주성"));
		//
		assertEquals(2, clubService.findAllClubManager(1).size());
		assertThat(clubService.findClubManager(1, "wntjd").getName(), is("이주성"));
		assertThat(clubService.findClubMember(1, "hong").getName(), is("홍길동"));
		
		
	}
	
	@Test
	
	public void testCommissionKingManagerClub() {
		//
		clubService.commissionKingManagerClub(1, new SocialPerson("ekdgml", "박상희"), new SocialPerson("hong", "홍길동"));
		//
		assertEquals("hong", clubService.findClubKingManager(1).getEmail());
		assertThat(clubService.findClubKingManager(1).getName(), is("홍길동"));
		assertThat(clubService.findClubManager(1, "ekdgml").getName(), is("박상희"));
	}
}