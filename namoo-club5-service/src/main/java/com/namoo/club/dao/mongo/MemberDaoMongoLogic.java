package com.namoo.club.dao.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.namoo.club.dao.MemberDao;
import com.namoo.club.dao.mongo.document.ClubDoc;
import com.namoo.club.dao.mongo.document.ClubManagerDoc;
import com.namoo.club.dao.mongo.document.ClubMemberDoc;
import com.namoo.club.dao.mongo.document.CommunityDoc;
import com.namoo.club.dao.mongo.document.CommunityManagerDoc;
import com.namoo.club.dao.mongo.document.CommunityMemberDoc;
import com.namoo.club.dao.mongo.repository.ClubRepository;
import com.namoo.club.dao.mongo.repository.CommunityRepository;

import dom.entity.ClubManager;
import dom.entity.ClubMember;
import dom.entity.CommunityManager;
import dom.entity.CommunityMember;

@Repository
public class MemberDaoMongoLogic implements MemberDao {
	//
	@Autowired
	private CommunityRepository comRepository;
	@Autowired
	private ClubRepository clubRepository;
	
	@Override
	public void addCommunityManager(CommunityManager comManager) {
		//
		comRepository.addCommunityManager(new CommunityManagerDoc(comManager));
	}

	@Override
	public void addCommunityMember(CommunityMember comMember) {
		//
		comRepository.addCommunityMember(new CommunityMemberDoc(comMember));
	}

	@Override
	public CommunityMember readCommunityMember(int comNo, String email) {
		//
		CommunityDoc doc = comRepository.findOne(comNo);
		List<CommunityMemberDoc> memberDocs = doc.getMembers();
		if (memberDocs != null) {
			CommunityMemberDoc found = null;
			for (CommunityMemberDoc memberDoc : memberDocs) {
				if (memberDoc.getRolePerson().getEmail().equals(email)) {
					found = memberDoc;
					break;
				}
			}
			if (found != null) {
				return found.createDomain();
			}
		}
		return null;
	}

	@Override
	public CommunityManager readCommunityManager(int comNo) {
		//
		CommunityDoc doc = comRepository.findOne(comNo);
		return doc.getManager().createDomain();
	}

	@Override
	public List<CommunityMember> readAllCommunityMember(int comNo) {
		//
		CommunityDoc doc = comRepository.findOne(comNo);
		List<CommunityMemberDoc> memberDocs = doc.getMembers();
		List<CommunityMember> members = new ArrayList<CommunityMember>();
		for (CommunityMemberDoc memberDoc : memberDocs) {
			members.add(memberDoc.createDomain());
		}
		return members;
	}

	@Override
	public void deleteCommuninyMember(int comNo, String email) {
		//
		comRepository.deleteCommunityMember(comNo, email);
	}
	
	@Override
	public void updateCommunityManager(CommunityManager communityManager) {
		//
		comRepository.updateCommunityManager(new CommunityManagerDoc(communityManager));
	}

	
	//-------------------------------------------------------------------------------------------------------
	// Club

	@Override
	public void addClubMember(ClubMember clubMember) {
		//
		clubRepository.addClubMember(new ClubMemberDoc(clubMember));
	}

	@Override
	public void addClubManager(ClubManager clubManager) {
		//
		clubRepository.addClubManager(new ClubManagerDoc(clubManager));
	}
	
	@Override
	public void deleteClubMember(int clubNo, String email) {
		//
		clubRepository.deleteClubMember(clubNo, email);
	}

	@Override
	public void deleteClubManager(int clubNo, String email) {
		//
		clubRepository.deleteClubManager(clubNo, email);
	}

	@Override
	public void updateClubManager(ClubManager clubManager) {
		//
		clubRepository.updateClubManager(new ClubManagerDoc(clubManager));
	}
	
	@Override
	public List<ClubMember> readAllClubMembers(int clubNo) {
		//
		ClubDoc doc = clubRepository.findOne(clubNo);
		List<ClubMember> members = new ArrayList<ClubMember>();
		if (doc.getMembers() != null) {
			for (ClubMemberDoc memberDoc : doc.getMembers()) {
				members.add(memberDoc.createDomain());
			}
		}
		return members;
	}

	@Override
	public List<ClubManager> readAllClubManagers(int clubNo) {
		//
		ClubDoc doc = clubRepository.findOne(clubNo);
		List<ClubManager> managers = new ArrayList<ClubManager>();
		if (doc.getManagers() != null) {
			for (ClubManagerDoc managerDoc : doc.getManagers()) {
				managers.add(managerDoc.createDomain());
			}
		}
		return managers;
	}

	@Override
	public ClubMember readClubMember(int clubNo, String email) {
		//
		ClubDoc doc = clubRepository.findOne(clubNo);
		ClubMember member = null;
		if (doc.getMembers() != null) {
			for (ClubMemberDoc memberDoc : doc.getMembers()) {
				if (memberDoc.getRolePerson().getEmail().equals(email)) {
					member = memberDoc.createDomain();
					break;
				}
			}
		}
		return member;
	}

	@Override
	public ClubManager readClubManager(int clubNo, String email) {
		//
		ClubDoc doc = clubRepository.findOne(clubNo);
		ClubManager manager = null;
		if (doc.getManagers() != null) {
			for (ClubManagerDoc managerDoc : doc.getManagers()) {
				if (managerDoc.getRolePerson().getEmail().equals(email)) {
					manager = managerDoc.createDomain();
					break;
				}
			}
		}
		return manager;
	}

	@Override
	public ClubManager readClubKingManager(int clubNo) {
		//
		ClubDoc doc = clubRepository.findOne(clubNo);
		ClubManager manager = null;
		for (ClubManagerDoc managerDoc : doc.getManagers()) {
			if (managerDoc.isKingManager()) {
				manager = managerDoc.createDomain();
				break;
			}
		}
		return manager;
	}
}
