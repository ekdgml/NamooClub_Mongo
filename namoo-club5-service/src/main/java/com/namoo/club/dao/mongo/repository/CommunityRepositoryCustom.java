package com.namoo.club.dao.mongo.repository;

import com.namoo.club.dao.mongo.document.CommunityManagerDoc;
import com.namoo.club.dao.mongo.document.CommunityMemberDoc;


public interface CommunityRepositoryCustom {
	//
	void addCommunityManager(CommunityManagerDoc comManager);
	void addCommunityMember(CommunityMemberDoc comMember);
	void deleteCommunityMember(int comNo, String email);
	void updateCommunityManager(CommunityManagerDoc comManager);
}
