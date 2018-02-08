package com.born.fcs.pm.ws.info.check;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 开发项目完成情况检查
 * 
 * @author lirz
 *
 * 2016-6-7 下午2:02:16
 */
public class FAfterwardsCheckReportProjectSimpleInfo extends BaseToStringInfo{

	private static final long serialVersionUID = -1270615499815478304L;

	private long id;
	private long formId;
	private String projectName;
	private String projectType;
	private String delAble;
	private int checkStatus;

    //========== getters and setters ==========

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}

	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectType() {
		return projectType;
	}
	
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getDelAble() {
		return this.delAble;
	}

	public void setDelAble(String delAble) {
		this.delAble = delAble;
	}

	public int getCheckStatus() {
		return this.checkStatus;
	}

	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}

}
