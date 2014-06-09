package com.namoo.club.dao.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.namoo.club.dao.CommunityDao;
import com.namoo.club.dao.mongo.document.ClubCategoryDoc;
import com.namoo.club.dao.mongo.document.ClubDoc;
import com.namoo.club.dao.mongo.document.CommunityDoc;
import com.namoo.club.dao.mongo.repository.ClubRepository;
import com.namoo.club.dao.mongo.repository.CommunityRepository;
import com.namoo.club.dao.mongo.shared.IdGenerator;

import dom.entity.ClubCategory;
import dom.entity.Community;

@Repository
public class CommunityDaoMongoLogic implements CommunityDao {
	//
	@Autowired
	private CommunityRepository repository;
	@Autowired
	private ClubRepository clubRepository;
	@Autowired
	private IdGenerator idGenerator;
	
	@Override
	public List<Community> readAllCommunities() {
		//
		List<CommunityDoc> docs = repository.findAll();
		List<Community> communities = new ArrayList<Community>();
		
		for (CommunityDoc doc : docs) {
			communities.add(doc.createDomain());
		}
		return communities;
	}

	@Override
	public Community readCommunity(int comNo) {
		//
		CommunityDoc doc = repository.findOne(comNo);
		return doc.createDomain();
	}

	@Override
	public int createCommunity(Community community) {
		//
		community.setComNo(idGenerator.next("community"));
		CommunityDoc doc = new CommunityDoc(community);
		repository.save(doc);
		return community.getComNo();
	}

	@Override
	public void updateCommunity(Community community) {
		//
		CommunityDoc doc = repository.findOne(community.getComNo());
		doc = new CommunityDoc(community);
		repository.save(doc);
	}

	@Override
	public void deleteCommunity(int comNo) {
		//
		repository.delete(comNo);
		List<ClubDoc> docs = clubRepository.findByComNo(comNo);
		for (ClubDoc doc : docs) {
			clubRepository.delete(doc);
		}
	}

	@Override
	public List<ClubCategory> readAllCategories(int comNo) {
		//
		CommunityDoc doc = repository.findOne(comNo);
		List<ClubCategoryDoc> categoryDocs = doc.getCategories();
		List<ClubCategory> categories = new ArrayList<ClubCategory>();
		for (ClubCategoryDoc categoryDoc : categoryDocs) {
			categories.add(categoryDoc.createDomain());
		}
		return categories;
	}

	@Override
	public void insertCategories(List<ClubCategory> categories) {
		//
		CommunityDoc doc = repository.findOne(categories.get(0).getCommunityNo());
		List<ClubCategoryDoc> docs = new ArrayList<ClubCategoryDoc>();
		for (ClubCategory category : categories) {
			docs.add(new ClubCategoryDoc(category));
		}
		doc.setCategories(docs);
		repository.save(doc);
	}
}
