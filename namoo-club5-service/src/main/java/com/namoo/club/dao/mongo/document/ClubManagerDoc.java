package com.namoo.club.dao.mongo.document;

import dom.entity.ClubManager;


public class ClubManagerDoc {
	//
	private int clubNo;
	private SocialPersonDoc rolePerson;
	private boolean kingManager;
	
	//------------------------------------------------------------------------
	
	public ClubManagerDoc() {
		//
	}
	
	public ClubManagerDoc(ClubManager manager) {
		//
		this.clubNo = manager.getClubNo();
		this.rolePerson = new SocialPersonDoc(manager.getRolePerson());
		this.kingManager = manager.isKingManager();
	}
	//------------------------------------------------------------------------
	
	public ClubManager createDomain() {
		//
		ClubManager manager = new ClubManager();
		manager.setClubNo(clubNo);
		manager.setRolePerson(rolePerson.createDomain());
		manager.setKingManager(kingManager);
		
		return manager;
	}
	//------------------------------------------------------------------------
	public int getClubNo() {
		return clubNo;
	}
	public void setClubNo(int clubNo) {
		this.clubNo = clubNo;
	}
	public SocialPersonDoc getRolePerson() {
		return rolePerson;
	}
	public void setRolePerson(SocialPersonDoc rolePerson) {
		this.rolePerson = rolePerson;
	}
	public boolean isKingManager() {
		return kingManager;
	}
	public void setKingManager(boolean kingManager) {
		this.kingManager = kingManager;
	}
}
