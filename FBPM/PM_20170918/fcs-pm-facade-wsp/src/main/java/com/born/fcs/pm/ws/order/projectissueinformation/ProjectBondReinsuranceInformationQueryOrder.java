package com.born.fcs.pm.ws.order.projectissueinformation;

import com.born.fcs.pm.ws.base.QueryProjectBase;

/**
 * 承销/发债类项目发行信息查询Order
 * @author Ji
 */
public class ProjectBondReinsuranceInformationQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = 6853551272264519069L;
	/** 主键ID */
	private Long id;
	/** 对应发债项目编号 */
	private String projectCode;
	/** 项目名称 */
	private String projectName;
	/** 分保方 */
	private String reinsuranceObject;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getReinsuranceObject() {
		return reinsuranceObject;
	}
	
	public void setReinsuranceObject(String reinsuranceObject) {
		this.reinsuranceObject = reinsuranceObject;
	}
	
}
