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
import com.namoo.club.dao.mongo.document.ClubDoc;
import com.namoo.club.dao.mongo.document.ClubManagerDoc;
import com.namoo.club.dao.mongo.document.ClubMemberDoc;
import com.namoo.club.dao.mongo.repository.ClubRepositoryCustom;

public class ClubRepositoryImpl extends SimpleMongoRepository<ClubDoc, Integer> implements ClubRepositoryCustom {
	//
	@Autowired
	public ClubRepositoryImpl(MongoRepositoryFactory factory, MongoTemplate template) {
		super(factory.<ClubDoc, Integer>getEntityInformation(ClubDoc.class), template);
	}
	
	@Override
	public void addClubMember(ClubMemberDoc clubMember) {
		//
		MongoOperations operations = getMongoOperations();
		
		Query query = new Query(Criteria.where("_id").is(clubMember.getClubNo()));
		
		Update update = new Update();
		update.push("members", clubMember);
		
		operations.updateFirst(query, update, ClubDoc.class);
	}

	@Override
	public void addClubManager(ClubManagerDoc clubManager) {
		//
		MongoOperations operations = getMongoOperations();
		
		Query query = new Query(Criteria.where("_id").is(clubManager.getClubNo()));
		
		Update update = new Update();
		update.push("managers", clubManager);
		
		operations.updateFirst(query, update, ClubDoc.class);
	}
	
	@Override
	public void deleteClubMember(int clubNo, String email) {
		//
		MongoOperations operations = getMongoOperations();
		
		Query query = new Query(Criteria.where("_id").is(clubNo));
		
		Update update = new Update();
		update.pull("members", new BasicDBObject("rolePerson._id", email));
		
		operations.updateFirst(query, update, ClubDoc.class);
	}

	@Override
	public void deleteClubManager(int clubNo, String email) {
		//
		MongoOperations operations = getMongoOperations();
		
		Query query = new Query(Criteria.where("_id").is(clubNo));
		
		Update update = new Update();
		update.pull("managers", new BasicDBObject("rolePerson._id", email));
		
		operations.updateFirst(query, update, ClubDoc.class);
	}

	@Override
	public void updateClubManager(ClubManagerDoc clubManager) {
		//
		MongoOperations operations = getMongoOperations();
		
		String email = clubManager.getRolePerson().getEmail();
		Criteria criteria = Criteria.where("_id").is(clubManager.getClubNo());
		// 매니저 중에 id가 같은 한 사람만 나온다!
		criteria.andOperator(Criteria.where("managers").elemMatch(Criteria.where("rolePerson._id").is(email)));
		Query query = new Query(criteria);
		
		Update update = new Update();
		update.set("managers.$", clubManager);
		
		operations.updateFirst(query, update, ClubDoc.class);
	}
}
