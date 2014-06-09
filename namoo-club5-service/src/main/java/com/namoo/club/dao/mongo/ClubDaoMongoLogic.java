package com.namoo.club.dao.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.namoo.club.dao.ClubDao;
import com.namoo.club.dao.mongo.document.ClubDoc;
import com.namoo.club.dao.mongo.repository.ClubRepository;
import com.namoo.club.dao.mongo.shared.IdGenerator;

import dom.entity.Club;

@Repository
public class ClubDaoMongoLogic implements ClubDao {
	//
	@Autowired
	private ClubRepository repository;
	@Autowired
	private IdGenerator idGenerator;
	
	@Override
	public List<Club> readAllClubsByComNo(int comNo) {
		//
		List<ClubDoc> docs = repository.findByComNo(comNo);
		List<Club> clubs = new ArrayList<Club>();
		for (ClubDoc doc : docs) {
			clubs.add(doc.createDomain());
		}
		return clubs;
	}

	@Override
	public List<Club> readAllClubs() {
		//
		List<ClubDoc> docs = repository.findAll();
		List<Club> clubs = new ArrayList<Club>();
		for (ClubDoc doc : docs) {
			clubs.add(doc.createDomain());
		}
		return clubs;
	}

	@Override
	public Club readClub(int clubNo) {
		//
		ClubDoc doc = repository.findOne(clubNo);
		if (doc != null) {
			return doc.createDomain();
		}
		return null;
	}

	@Override
	public int createClub(Club club) {
		//
		club.setClubNo(idGenerator.next("club"));
		ClubDoc doc = new ClubDoc(club);
		repository.save(doc);
		return club.getClubNo();
	}

	@Override
	public void updateClub(Club club) {
		//
		ClubDoc doc = repository.findOne(club.getClubNo());
		doc = new ClubDoc(club);
		repository.save(doc);
	}

	@Override
	public void deleteClub(int clubNo) {
		//
		repository.delete(clubNo);
	}

}
