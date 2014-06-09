package com.namoo.club.dao.mongo.document;

import dom.entity.CommunityMember;


public class CommunityMemberDoc {
	//
	private int comNo;
	private SocialPersonDoc rolePerson;
	
	//----------------------------------------------------------------------
	
	public CommunityMemberDoc() {
		//
	}
	
	public CommunityMemberDoc(CommunityMember member) {
		//
		this.comNo = member.getCommunityNo();
		this.rolePerson = new SocialPersonDoc(member.getRolePerson());
	}
	//----------------------------------------------------------------------
	
	public CommunityMember createDomain() {
		//
		CommunityMember member = new CommunityMember();
		member.setCommunityNo(comNo);
		member.setRolePerson(rolePerson.createDomain());
		
		return member;
	}
	//----------------------------------------------------------------------
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
