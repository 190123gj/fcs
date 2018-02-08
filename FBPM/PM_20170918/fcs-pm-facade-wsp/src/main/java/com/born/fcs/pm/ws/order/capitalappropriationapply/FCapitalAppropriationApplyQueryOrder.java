package com.born.fcs.pm.ws.order.capitalappropriationapply;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.base.QueryFormBase;

/**
 * 资金划付申请查询Order
 * @author Ji
 */
public class FCapitalAppropriationApplyQueryOrder extends QueryFormBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4256481503407295120L;
	private Long formId; //表单ID
	private Long applyId;
	
	/**
	 * 简单流程（IS/NO）
	 */
	private String isSimple;
	
	private long outBizNo;
	
	private String formStatus;
	
	private String formUserName;
	
	private String busiTypeName;
	
	private String projectCode;
	
	private String projectName;
	
	private String projectType;
	
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
	
	private List<String> appropriateReasonList;
	
	public Long getApplyId() {
		return applyId;
	}
	
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
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
	
	public String getProjectType() {
		return projectType;
	}
	
	public void setProjectType(String projectType) {
		this.projectType = projectType;
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
	
	@Override
	public String getFormStatus() {
		return formStatus;
	}
	
	@Override
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
	
	public String getIsSimple() {
		return this.isSimple;
	}
	
	public void setIsSimple(String isSimple) {
		this.isSimple = isSimple;
	}
	
	public long getOutBizNo() {
		return outBizNo;
	}
	
	public void setOutBizNo(long outBizNo) {
		this.outBizNo = outBizNo;
	}
	
	public List<String> getAppropriateReasonList() {
		return appropriateReasonList;
	}
	
	public void setAppropriateReasonList(List<String> appropriateReasonList) {
		this.appropriateReasonList = appropriateReasonList;
	}
	
	public Long getFormId() {
		return formId;
	}
	
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	
}
