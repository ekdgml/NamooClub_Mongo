package com.namoo.club.dao.mongo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.namoo.club.dao.mongo.document.CommunityDoc;

public interface CommunityRepository extends CrudRepository<CommunityDoc, Integer>, CommunityRepositoryCustom{
	//
	List<CommunityDoc> findAll();
}
