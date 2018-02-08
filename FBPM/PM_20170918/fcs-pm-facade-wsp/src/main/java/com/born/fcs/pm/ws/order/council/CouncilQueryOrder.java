package com.born.fcs.pm.ws.order.council;

import java.util.List;

import com.born.fcs.pm.ws.order.base.FcsQueryPageBase;

public class CouncilQueryOrder extends FcsQueryPageBase {
	
	private static final long serialVersionUID = 8920040959489573678L;
	
	/** 会议类型ID */
	private long councilType;
	
	private String councilTypeCode;
	
	/** 会议类型 */
	private String councilTypeName;
	/** 会议状态 */
	private String status;
	
	/** 满足条件的会议状态集合 */
	private List<String> statuss;
	
	private long councilId;
	/** 相关人员（列席人员或评委） */
	private long relatveId;
	
	/** 会议纪要查询council导致死循环 */
	private boolean queryFromSummary = false;
	
	/**
	 * 用于判定查询哪些类型的数据
	 */
	private List<String> councilTypeCodes;
	
	/**
	 * 哪些公司【目前用作对信汇的操作】
	 */
	private List<String> companyNames;
	
	/**
	 * 会议编号
	 */
	private String councilCode;
	
	/**
	 * 项目名
	 */
	private String projectName;
	
	public String getCouncilCode() {
		return this.councilCode;
	}
	
	public void setCouncilCode(String councilCode) {
		this.councilCode = councilCode;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public List<String> getCompanyNames() {
		return this.companyNames;
	}
	
	public void setCompanyNames(List<String> companyNames) {
		this.companyNames = companyNames;
	}
	
	public List<String> getStatuss() {
		return this.statuss;
	}
	
	public void setStatuss(List<String> statuss) {
		this.statuss = statuss;
	}
	
	public List<String> getCouncilTypeCodes() {
		return this.councilTypeCodes;
	}
	
	public void setCouncilTypeCodes(List<String> councilTypeCodes) {
		this.councilTypeCodes = councilTypeCodes;
	}
	
	public long getCouncilType() {
		return this.councilType;
	}
	
	public void setCouncilType(long councilType) {
		this.councilType = councilType;
	}
	
	public String getCouncilTypeCode() {
		return this.councilTypeCode;
	}
	
	public void setCouncilTypeCode(String councilTypeCode) {
		this.councilTypeCode = councilTypeCode;
	}
	
	public String getCouncilTypeName() {
		return this.councilTypeName;
	}
	
	public void setCouncilTypeName(String councilTypeName) {
		this.councilTypeName = councilTypeName;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public long getCouncilId() {
		return this.councilId;
	}
	
	public void setCouncilId(long councilId) {
		this.councilId = councilId;
	}
	
	public long getRelatveId() {
		return this.relatveId;
	}
	
	public void setRelatveId(long relatveId) {
		this.relatveId = relatveId;
	}
	
	public boolean isQueryFromSummary() {
		return this.queryFromSummary;
	}
	
	public void setQueryFromSummary(boolean queryFromSummary) {
		this.queryFromSummary = queryFromSummary;
	}
	
}
