package com.namoo.club.dao.mongo.shared;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class IdGenerator {
	//
	@Autowired
	private MongoTemplate template;
	
	public int next(String key) {
		//
		Query query = new Query(Criteria.where("_id").is(key));
		Update update = new Update();
		update.inc("seq", 1);
		FindAndModifyOptions options = FindAndModifyOptions.options()
				.returnNew(true)
				.upsert(true);
		Sequence sequence = template.findAndModify(query, update, options, Sequence.class);
		return sequence.getSeq();
	}
}
