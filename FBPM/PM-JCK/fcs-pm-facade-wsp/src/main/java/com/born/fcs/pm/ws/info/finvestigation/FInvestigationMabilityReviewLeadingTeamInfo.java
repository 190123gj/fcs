/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 客户主要高管人员
 * 
 * @author lirz
 *
 * 2016-3-10 下午4:37:00
 */
public class FInvestigationMabilityReviewLeadingTeamInfo extends BaseToStringInfo{

	private static final long serialVersionUID = -1135960948820575757L;
	
	private long id;
	private long maReviewId;
	private String name; //姓名
	private String sex; //性别
	private int age; //年龄
	private String degree; //学历
	private String title; //职务
	private String resume; //履历
	private int sortOrder;
	private Date rawAddTime;
	private Date rawUpdateTime;

	private List<FInvestigationMabilityReviewLeadingTeamResumeInfo> resumes;
	
    //========== getters and setters ==========

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getMaReviewId() {
		return maReviewId;
	}
	
	public void setMaReviewId(long maReviewId) {
		this.maReviewId = maReviewId;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}

	public String getDegree() {
		return degree;
	}
	
	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getResume() {
		return resume;
	}
	
	public void setResume(String resume) {
		this.resume = resume;
	}

	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}

	public List<FInvestigationMabilityReviewLeadingTeamResumeInfo> getResumes() {
		return this.resumes;
	}

	public void setResumes(List<FInvestigationMabilityReviewLeadingTeamResumeInfo> resumes) {
		this.resumes = resumes;
	}

}
