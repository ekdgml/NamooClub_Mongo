package com.namoo.club.dao.mongo.document;

import org.springframework.data.annotation.Id;

import dom.entity.CommunityManager;

public class CommunityManagerDoc {
	//
	@Id
	private int comNo;
	private SocialPersonDoc rolePerson;
	
	//---------------------------------------------------------
	
	public CommunityManagerDoc() {
		//
	}
	
	public CommunityManagerDoc(CommunityManager manager) {
		//
		this.comNo = manager.getCommunityNo();
		this.rolePerson = new SocialPersonDoc(manager.getRolePerson());
	}
	//---------------------------------------------------------
	
	public CommunityManager createDomain() {
		//
		CommunityManager manager = new CommunityManager();
		manager.setCommunityNo(comNo);
		manager.setRolePerson(rolePerson.createDomain());
		
		return manager;
	}
	//---------------------------------------------------------
	public int getComNo() {
		return comNo;
	}
	public void setComNo(int comNo) {
		this.comNo = comNo;
	}
	public SocialPersonDoc getRolePerson() {
		return rolePerson;
	}
	public void setRolePerson(SocialPersonDoc rolePerson) {
		this.rolePerson = rolePerson;
	}
}
