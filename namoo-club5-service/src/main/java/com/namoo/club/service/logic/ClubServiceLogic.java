package com.namoo.club.service.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namoo.club.dao.ClubDao;
import com.namoo.club.dao.CommunityDao;
import com.namoo.club.dao.MemberDao;
import com.namoo.club.dao.UserDao;
import com.namoo.club.service.facade.ClubService;
import com.namoo.club.shared.exception.NamooClubExceptionFactory;

import dom.entity.Club;
import dom.entity.ClubManager;
import dom.entity.ClubMember;
import dom.entity.SocialPerson;

@Service
public class ClubServiceLogic implements ClubService {
	//
	@Autowired
	private ClubDao clubDao;
	@Autowired
	private CommunityDao comDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private UserDao userDao;

	// ------------------------------------------------------------------------

	@Override
	public Club registClub(int categoryNo, int communityNo, String clubName, String description, String email) {
		//
		SocialPerson person = userDao.readUser(email);
		if (isExistClubByName(communityNo, clubName)) {
			throw NamooClubExceptionFactory.createRuntime("이미 존재하는 클럽입니다.");
		}
		Club club = new Club(categoryNo, communityNo, clubName, description, new SocialPerson(email, person.getName()));
		int clubNo = clubDao.createClub(club);
		club.setClubNo(clubNo);

		memberDao.addClubManager(new ClubManager(clubNo, person, true));
		return club;
	}

	private boolean isExistClubByName(int communityNo, String clubName) {
		//
		List<Club> clubs = clubDao.readAllClubsByComNo(communityNo);

		if (!clubs.isEmpty()) {
			for (Club club : clubs) {
				if (club.getName().equals(clubName)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Club findClub(int clubNo) {
		//
		Club club = clubDao.readClub(clubNo);
		club.setManagers(memberDao.readAllClubManagers(clubNo));
		club.setMembers(memberDao.readAllClubMembers(clubNo));
		return club;
	}

	@Override
	public void joinAsMember(int clubNo, String email) {
		//
		Club club = clubDao.readClub(clubNo);
		if (club == null) {
			throw NamooClubExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}

		memberDao.addClubMember(new ClubMember(clubNo, new SocialPerson(email, "qwer")));
	}

	@Override
	public List<Club> findAllClubsByComNo(int comNo) {
		//
		return clubDao.readAllClubsByComNo(comNo);
	}
	
	@Override
	public List<Club> findAllClubs() {
		//
		return clubDao.readAllClubs();
	}

	@Override
	public ClubMember findClubMember(int clubNo, String email) {
		//
		Club club = clubDao.readClub(clubNo);
		if (club == null) {
			throw NamooClubExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}

		return memberDao.readClubMember(clubNo, email);
	}

	@Override
	public List<ClubMember> findAllClubMember(int clubNo) {
		//
		Club club = clubDao.readClub(clubNo);
		if (club == null) {
			throw NamooClubExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}
		return memberDao.readAllClubMembers(clubNo);
	}

	@Override
	public int countMembers(int clubNo) {
		//
		Club club = clubDao.readClub(clubNo);
		if (club == null) {
			throw NamooClubExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}
		return memberDao.readAllClubMembers(clubNo).size();
	}

	@Override
	public void removeClub(int clubNo, boolean forcingRemove) {
		//
		if (clubDao.readClub(clubNo) == null) {
			throw NamooClubExceptionFactory.createRuntime("존재하지 않는 클럽입니다.");
		}
		if (forcingRemove) {
			clubDao.deleteClub(clubNo);
		} else {
		throw NamooClubExceptionFactory.createRuntime("멤버부터 탈퇴시키세요.");
		}

	}

	@Override
	public List<Club> findBelongClubs(String email, int comNo) {
		//
		List<Club> clubs = clubDao.readAllClubsByComNo(comNo);
		if (clubs == null) return null;

		List<Club> belongs = new ArrayList<>();

		for (Club club : clubs) {
			if (memberDao.readClubMember(club.getClubNo(), email) != null) {
				belongs.add(club);
			}
		}
		return belongs;
	}
	
	@Override
	public List<Club> findNotBelogClubs(String email, int comNo) {
		//
		List<Club> clubs = clubDao.readAllClubsByComNo(comNo);
		List<Club> belongs = new ArrayList<>();
		for (Club club : clubs) {
			if (memberDao.readClubMember(club.getClubNo(), email) != null) {
				belongs.add(club);
			}
		}
		List<Club> unjoinClubs = new ArrayList<Club>(clubs);
		List<Club> remove = new ArrayList<Club>();

		for (Club joinClub : belongs) {
			for (Club club: clubs) {
				if (club.getClubNo() == (joinClub.getClubNo())) {
					remove.add(club);
					break;
				}
			}
		}
		if (!remove.isEmpty()) {
			unjoinClubs.removeAll(remove);
		}
		return unjoinClubs;
	}

	@Override
	public List<Club> findManagedClubsByComNo(String email, int comNo) {
		//
		List<Club> clubs = clubDao.readAllClubsByComNo(comNo);
		if (clubs == null)
			return null;

		List<Club> managers = new ArrayList<>();
		for (Club club : clubs) {
			if (memberDao.readClubManager(club.getClubNo(), email) != null) {

				managers.add(club);
			}
		}
		return managers;

	}
	
	@Override
	public List<Club> findManagedClubs(String email) {
		//
		List<Club> clubs = clubDao.readAllClubs();
		if (clubs == null)
			return null;

		List<Club> managers = new ArrayList<>();
		for (Club club : clubs) {
			if (memberDao.readClubManager(club.getClubNo(), email) != null) {
				managers.add(club);
			}
		}
		return managers;

	}

	@Override
	public void withdrawalClub(int clubNo, String email) {
		//
		Club club = clubDao.readClub(clubNo);
		if (club == null) {
			throw NamooClubExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}
		memberDao.deleteClubMember(clubNo, email);
	}

	@Override
	public void commissionManagerClub(int clubNo, String originEmail, SocialPerson nwPerson) {
		//
		memberDao.deleteClubManager(clubNo, originEmail);
		ClubManager manager = new ClubManager(clubNo, nwPerson, false);
		memberDao.addClubManager(manager);
	}

	@Override
	public void commissionKingManagerClub(int clubNo, SocialPerson originPerson,  SocialPerson nwPerson) {
		//
		// 1.기존의 kingManager는 false로 update
		ClubManager manager = new ClubManager(clubNo, originPerson, false);
		memberDao.updateClubManager(manager);
		
		// 2.기존의 manager는 true로 update
		manager = new ClubManager(clubNo, nwPerson, true);
		memberDao.updateClubManager(manager);
	}

	@Override
	public List<ClubManager> findAllClubManager(int clubNo) {
		//
		Club club = clubDao.readClub(clubNo);
		if (club == null) {
			throw NamooClubExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}
		return memberDao.readAllClubManagers(clubNo);
	}

	@Override
	public ClubManager findClubManager(int clubNo, String email) {
		//
		Club club = clubDao.readClub(clubNo);
		if (club == null) {
			throw NamooClubExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}

		return memberDao.readClubManager(clubNo, email);
	}

	@Override
	public ClubManager findClubKingManager(int clubNo) {
		//
		Club club = clubDao.readClub(clubNo);
		if (club == null) {
			throw NamooClubExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}

		return memberDao.readClubKingManager(clubNo);
	}

	@Override
	public void assignManagerClub(int clubNo, SocialPerson person) {
		//
		memberDao.deleteClubMember(clubNo, person.getEmail());
		memberDao.addClubManager(new ClubManager(clubNo, person, false));
	}

	

}
