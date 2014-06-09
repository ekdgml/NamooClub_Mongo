package com.namoo.club.dao.mongo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.namoo.club.dao.mongo.document.SocialPersonDoc;

public interface UserRepository extends CrudRepository<SocialPersonDoc, String>{
	//
	List<SocialPersonDoc> findAll();
}
