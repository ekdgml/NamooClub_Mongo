package com.namoo.club.dao.mongo.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import dom.entity.Club;
import dom.entity.ClubManager;
import dom.entity.ClubMember;

@Document(collection="clubs")
public class ClubDoc {
	//
	@Id
	private int clubNo;
	@Indexed
	private int comNo;
	private int categoryNo;
	private String name;
	private String description;
	private Date openDate;

	private List<ClubManagerDoc> managers;
	private List<ClubMemberDoc> members;
	
	//------------------------------------------------------------------------------
	
	public ClubDoc() {
		//
	}
	
	public ClubDoc(Club club) {
		//
		this.clubNo = club.getClubNo();
		this.categoryNo = club.getCategoryNo();
		this.comNo = club.getComNo();
		this.name= club.getName();
		this.description = club.getDescription();
		this.openDate = new Date();
		
		if (club.getMembers() != null) {
			for (ClubMember member : club.getMembers()) {
				//
				this.members.add(new ClubMemberDoc(member));
			}
		}
	}
	//------------------------------------------------------------------------------
	
	public Club createDomain() {
		//
		Club club = new Club();
		club.setCategoryNo(categoryNo);
		club.setClubNo(clubNo);
		club.setComNo(comNo);
		club.setDescription(description);
		club.setName(name);
		club.setOpenDate(openDate);
		
		if (managers != null) {
			List<ClubManager> manager = new ArrayList<ClubManager>();
			for (ClubManagerDoc doc : managers) {
				manager.add(doc.createDomain());
			}
			club.setManagers(manager);
		}
		
		if (members != null) {
			List<ClubMember> member = new ArrayList<ClubMember>();
			for (ClubMemberDoc doc : members) {
				member.add(doc.createDomain());
			}
			club.setMembers(member);
		}
		
		return club;
	}
	//------------------------------------------------------------------------------
	public int getClubNo() {
		return clubNo;
	}
	public void setClubNo(int clubNo) {
		this.clubNo = clubNo;
	}
	public int getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}
	public int getComNo() {
		return comNo;
	}
	public void setComNo(int comNo) {
		this.comNo = comNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public List<ClubManagerDoc> getManagers() {
		return managers;
	}
	public void setManagers(List<ClubManagerDoc> managers) {
		this.managers = managers;
	}
	public List<ClubMemberDoc> getMembers() {
		return members;
	}
	public void setMembers(List<ClubMemberDoc> members) {
		this.members = members;
	}
}
