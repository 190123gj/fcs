package com.born.fcs.pm.ws.order.check;

import java.util.List;

import org.springframework.util.Assert;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 
 * 查询保后相同版本
 * 
 * @author lirz
 * 
 * 2017-1-17 下午2:14:03
 * 
 */
public class AfterwardsEditionQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -2154317805955979191L;
	
	private long formId;
	
	private String projectCode;
	
	private int roundYear;
	
	private int roundTime;
	
	private String edition;
	
	private List<String> formStatuses;
	
	@Override
	public void check() {
		Assert.isTrue(formId > 0, "表单ID必须大于0");
		Assert.hasText(projectCode, "项目编号不能为空");
		Assert.isTrue(roundYear > 0, "期数-年必须大于0");
		Assert.isTrue(roundTime > 0, "期次必须大于0");
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public int getRoundYear() {
		return this.roundYear;
	}
	
	public void setRoundYear(int roundYear) {
		this.roundYear = roundYear;
	}
	
	public int getRoundTime() {
		return this.roundTime;
	}
	
	public void setRoundTime(int roundTime) {
		this.roundTime = roundTime;
	}
	
	public String getEdition() {
		return this.edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public List<String> getFormStatuses() {
		return this.formStatuses;
	}
	
	public void setFormStatuses(List<String> formStatuses) {
		this.formStatuses = formStatuses;
	}
	
}
