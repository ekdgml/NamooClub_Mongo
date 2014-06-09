package com.namoo.club.dao.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import dom.entity.SocialPerson;

@Document(collection="users")
public class SocialPersonDoc {
	//
	@Id
	private String email;
	private String name;
	private String password;
	
	//---------------------------------------------------------------
	
	public SocialPersonDoc() {
		//
	}
	
	public SocialPersonDoc(SocialPerson person) {
		//
		this.name = person.getName();
		this.email = person.getEmail();
		this.password = person.getPassword();
	}
	//---------------------------------------------------------------
	
	public SocialPerson createDomain() {
		//
		SocialPerson person = new SocialPerson();
		person.setEmail(email);
		person.setName(name);
		person.setPassword(password);
		 
		return person;
	}
	//---------------------------------------------------------------
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
