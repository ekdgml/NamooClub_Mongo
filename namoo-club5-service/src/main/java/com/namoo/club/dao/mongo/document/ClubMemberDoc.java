package com.namoo.club.dao.mongo.document;

import dom.entity.ClubMember;


public class ClubMemberDoc {
	//
	private int clubNo;
	private SocialPersonDoc rolePerson;
	
	//--------------------------------------------------------
	
	public ClubMemberDoc() {
		//
	}
	
	public ClubMemberDoc(ClubMember member) {
		//
		this.clubNo = member.getClubNo();
		this.rolePerson = new SocialPersonDoc(member.getRolePerson());
	}
	//--------------------------------------------------------
	
	public ClubMember createDomain() {
		//
		ClubMember member = new ClubMember();
		member.setClubNo(clubNo);
		member.setRolePerson(rolePerson.createDomain());
		
		return member;
	}
	//--------------------------------------------------------
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
}
