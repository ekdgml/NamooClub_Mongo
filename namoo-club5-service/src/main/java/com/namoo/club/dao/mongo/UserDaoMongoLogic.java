package com.namoo.club.dao.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.namoo.club.dao.UserDao;
import com.namoo.club.dao.mongo.document.SocialPersonDoc;
import com.namoo.club.dao.mongo.repository.UserRepository;

import dom.entity.SocialPerson;

@Repository
public class UserDaoMongoLogic implements UserDao {
	//
	@Autowired
	private UserRepository repository;
	
	@Override
	public List<SocialPerson> readAllUsers() {
		//
		List<SocialPersonDoc> docs = repository.findAll();
		List<SocialPerson> persons = new ArrayList<SocialPerson>();
		for (SocialPersonDoc doc : docs) {
			persons.add(doc.createDomain());
		}
		return persons;
	}

	@Override
	public SocialPerson readUser(String email) {
		//
		SocialPersonDoc doc = repository.findOne(email);
		if (doc != null) {
			return doc.createDomain();
		}
		return null;
	}

	@Override
	public void createUser(SocialPerson user) {
		//
		SocialPersonDoc doc = new SocialPersonDoc(user);
		repository.save(doc);
	}

	@Override
	public void updateUser(SocialPerson user) {
		//
		SocialPersonDoc doc = repository.findOne(user.getEmail());
		doc = new SocialPersonDoc(user);
		repository.save(doc);
	}

	@Override
	public void deleteUser(String email) {
		//
		repository.delete(email);
	}

}
