package com.namoo.club.dao.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import dom.entity.ClubCategory;

public class ClubCategoryDoc {
	//
	@Id
	private int categoryNo;
	@Indexed
	private int comNo;
	private String categoryName;
	
	//----------------------------------------------------
	
	public ClubCategoryDoc() {
		//
	}
	
	public ClubCategoryDoc(ClubCategory category) {
		//
		this.categoryNo = category.getCategoryNo();
		this.comNo = category.getCommunityNo();
		this.categoryName = category.getCategoryName();
	}
	//----------------------------------------------------
	
	public ClubCategory createDomain() {
		//
		ClubCategory category = new ClubCategory();
		category.setCategoryNo(categoryNo);
		category.setCommunityNo(comNo);
		category.setCategoryName(categoryName);
		
		return category;
	}
	//----------------------------------------------------
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
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
