/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.finvestigation;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 客户主要高管人员
 * 
 * @author lirz
 *
 * 2016-3-10 下午4:37:00
 */
public class FInvestigationMabilityReviewLeadingTeamOrder extends ValidateOrderBase{

	private static final long serialVersionUID = -5967181843754985652L;

	private long id;
	private long maReviewId;
	private String name; //姓名
	private String sex; //性别
	private String birth; //生日
	private Integer age; //年龄
	private String degree; //学历
	private String title; //职务
	private String resume; //履历
	private int sortOrder;
	//履历
	private List<FInvestigationMabilityReviewLeadingTeamResumeOrder> resumes;

    public boolean isNull() {
    	return isNull(name)
    			&& isNull(sex)
    			&& isNull(birth)
    			&& (null == age || age == 0)
    			&& isNull(degree)
    			&& isNull(title)
    			&& isNull(resume)
    			&& isNull(resumes)
    			;
    }
    
    private boolean isNull(List<FInvestigationMabilityReviewLeadingTeamResumeOrder> resumes) {
    	if (null == resumes || resumes.size() <= 0) {
    		return true;
    	}
    	
    	for (FInvestigationMabilityReviewLeadingTeamResumeOrder order : resumes) {
    		if (!order.isNull()) {
    			return false;
    		}
    	}
    	
    	return true;
    }

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

	public String getBirth() {
		return this.birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public Integer getAge() {
		return null == age ? 0 : age.intValue();
	}
	
	public void setAge(Integer age) {
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

	public List<FInvestigationMabilityReviewLeadingTeamResumeOrder> getResumes() {
		return this.resumes;
	}

	public void setResumes(List<FInvestigationMabilityReviewLeadingTeamResumeOrder> resumes) {
		this.resumes = resumes;
	}

	/**
     * @return
     *
     * @see java.lang.Object#toString()
     */
	@Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
