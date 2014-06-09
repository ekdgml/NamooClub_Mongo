package com.namoo.club.dao.mongo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.namoo.club.dao.mongo.document.ClubDoc;

public interface ClubRepository extends CrudRepository<ClubDoc, Integer>, ClubRepositoryCustom{
	//
	List<ClubDoc> findByComNo(Integer comNo);
	List<ClubDoc> findAll();
}
