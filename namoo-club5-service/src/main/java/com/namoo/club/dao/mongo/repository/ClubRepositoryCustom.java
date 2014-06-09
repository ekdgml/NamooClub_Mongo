package com.namoo.club.dao.mongo.repository;

import com.namoo.club.dao.mongo.document.ClubManagerDoc;
import com.namoo.club.dao.mongo.document.ClubMemberDoc;

public interface ClubRepositoryCustom {
	//
	void addClubMember(ClubMemberDoc clubMember);
	void addClubManager(ClubManagerDoc clubManager);
	void deleteClubMember(int clubNo, String email);
	void deleteClubManager(int clubNo, String email);
	void updateClubManager(ClubManagerDoc clubManager);
}
