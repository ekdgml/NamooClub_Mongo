package com.namoo.club.dao.mongo.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import com.mongodb.BasicDBObject;
import com.namoo.club.dao.mongo.document.CommunityDoc;
import com.namoo.club.dao.mongo.document.CommunityManagerDoc;
import com.namoo.club.dao.mongo.document.CommunityMemberDoc;
import com.namoo.club.dao.mongo.repository.CommunityRepositoryCustom;

public class CommunityRepositoryImpl extends SimpleMongoRepository<CommunityDoc, Integer> implements CommunityRepositoryCustom {
	//
	@Autowired
	public CommunityRepositoryImpl(MongoRepositoryFactory factory, MongoTemplate template) {
		super(factory.<CommunityDoc, Integer>getEntityInformation(CommunityDoc.class), template);
	}

	@Override
	public void addCommunityManager(CommunityManagerDoc comManager) {
		//
		MongoOperations operations = getMongoOperations();
		
		Query query = new Query(Criteria.where("_id").is(comManager.getComNo()));
		
		// 1.매니저로 등록
		Update update = new Update();
		update.set("manager", comManager);
		
		operations.updateFirst(query, update, CommunityDoc.class);
	}

	@Override
	public void addCommunityMember(CommunityMemberDoc comMember) {
		//
		MongoOperations operations = getMongoOperations();
		
		Query query = new Query(Criteria.where("_id").is(comMember.getComNo()));
		
		Update update = new Update();
		update.push("members", comMember);
		
		operations.updateFirst(query, update, CommunityDoc.class);
	}

	@Override
	public void deleteCommunityMember(int comNo, String email) {
		//
		MongoOperations operations = getMongoOperations();
		
		Query query = new Query(Criteria.where("_id").is(comNo));
		
		Update update = new Update();
		update.pull("members", new BasicDBObject("rolePerson._id", email));
		
		operations.updateFirst(query, update, CommunityDoc.class);
	}
	
	@Override
	public void updateCommunityManager(CommunityManagerDoc manager) {
		//
		MongoOperations operations = getMongoOperations();
		
		Query query = new Query(Criteria.where("_id").is(manager.getComNo()));
		
		Update update = new Update();
		update.set("manager", manager);
		operations.updateFirst(query, update, CommunityDoc.class);
	}
}
