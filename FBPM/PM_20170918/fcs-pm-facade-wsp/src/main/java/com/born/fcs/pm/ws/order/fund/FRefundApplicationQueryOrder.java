package com.born.fcs.pm.ws.order.fund;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryFormBase;

/**
 * 退费申请查询Order
 * @author Ji
 */
public class FRefundApplicationQueryOrder extends QueryFormBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2056222389553176079L;
	
	private Long refundId;
	
	private String formStatus;
	
	private String formUserName;
	
	private String busiTypeName;
	
	private String projectCode;
	
	private String projectName;
	
	private String attach;
	
	private String remark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	/**
	 * 客户经理
	 */
	private long busiManagerId;
	
	private String busiManagerName;
	
	private Date submitTimeStart;
	
	private Date submitTimeEnd;
	
	public Long getRefundId() {
		return refundId;
	}
	
	public void setRefundId(Long refundId) {
		this.refundId = refundId;
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
	
	public String getAttach() {
		return attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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
	
	public long getBusiManagerId() {
		return busiManagerId;
	}
	
	public void setBusiManagerId(long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}
	
	public String getBusiManagerName() {
		return busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public Date getSubmitTimeStart() {
		return submitTimeStart;
	}
	
	public void setSubmitTimeStart(Date submitTimeStart) {
		this.submitTimeStart = submitTimeStart;
	}
	
	public Date getSubmitTimeEnd() {
		return submitTimeEnd;
	}
	
	public void setSubmitTimeEnd(Date submitTimeEnd) {
		this.submitTimeEnd = submitTimeEnd;
	}
	
	public String getFormStatus() {
		return formStatus;
	}
	
	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	
	public String getFormUserName() {
		return formUserName;
	}
	
	public void setFormUserName(String formUserName) {
		this.formUserName = formUserName;
	}
	
	public String getBusiTypeName() {
		return busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
}
