package com.born.fcs.pm.ws.order.setup;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.base.QueryProjectBase;

/**
 * 
 * 项目立项查询Order
 *
 * @author wuzj
 *
 */
public class SetupFormQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = -8697731786427147205L;
	
	/**
	 * 授信客户信息
	 */
	private long customerId;
	
	private String customerName;
	/**
	 * 用信客户信息
	 */
	private long trueCustomerId;
	
	private String trueCustomerName;
	
	/**
	 * 部门
	 */
	private long projectDeptId;
	
	private String projectDeptName;
	
	private long formDeptId;
	
	private String formDeptName;
	
	private String formStatus;
	
	private String projectCode;
	
	private String projectName;
	
	private String busiType;
	
	private String busiTypeName;
	
	private List<String> busiTypeList;
	
	/**
	 * 风险经理
	 */
	private long riskManagerId;
	
	private String riskManagerName;
	
	/**
	 * 客户经理
	 */
	private long busiManagerId;
	
	private String busiManagerName;
	
	/**
	 * 客户经理B角
	 */
	private long busiManagerbId;
	
	private String busiManagerbName;
	
	private Date submitTimeStart;
	
	private Date submitTimeEnd;
	
	@Override
	public String getSortCol() {
		if (super.getSortCol() == null || "".equals(super.getSortCol())) {
			return "f.form_id";
		}
		return super.getSortCol();
	}
	
	@Override
	public String getSortOrder() {
		if (super.getSortOrder() == null || "".equals(super.getSortOrder())) {
			return "DESC";
		}
		return super.getSortOrder();
	}
	
	public long getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public long getTrueCustomerId() {
		return this.trueCustomerId;
	}
	
	public void setTrueCustomerId(long trueCustomerId) {
		this.trueCustomerId = trueCustomerId;
	}
	
	public String getTrueCustomerName() {
		return this.trueCustomerName;
	}
	
	public void setTrueCustomerName(String trueCustomerName) {
		this.trueCustomerName = trueCustomerName;
	}
	
	public long getProjectDeptId() {
		return this.projectDeptId;
	}
	
	public void setProjectDeptId(long projectDeptId) {
		this.projectDeptId = projectDeptId;
	}
	
	public String getProjectDeptName() {
		return this.projectDeptName;
	}
	
	public void setProjectDeptName(String projectDeptName) {
		this.projectDeptName = projectDeptName;
	}
	
	public long getFormDeptId() {
		return this.formDeptId;
	}
	
	public void setFormDeptId(long formDeptId) {
		this.formDeptId = formDeptId;
	}
	
	public String getFormDeptName() {
		return this.formDeptName;
	}
	
	public void setFormDeptName(String formDeptName) {
		this.formDeptName = formDeptName;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getBusiType() {
		return this.busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return this.busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public long getRiskManagerId() {
		return this.riskManagerId;
	}
	
	public void setRiskManagerId(long riskManagerId) {
		this.riskManagerId = riskManagerId;
	}
	
	public String getRiskManagerName() {
		return this.riskManagerName;
	}
	
	public void setRiskManagerName(String riskManagerName) {
		this.riskManagerName = riskManagerName;
	}
	
	public long getBusiManagerId() {
		return this.busiManagerId;
	}
	
	public void setBusiManagerId(long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}
	
	public String getBusiManagerName() {
		return this.busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public long getBusiManagerbId() {
		return this.busiManagerbId;
	}
	
	public void setBusiManagerbId(long busiManagerbId) {
		this.busiManagerbId = busiManagerbId;
	}
	
	public String getBusiManagerbName() {
		return this.busiManagerbName;
	}
	
	public void setBusiManagerbName(String busiManagerbName) {
		this.busiManagerbName = busiManagerbName;
	}
	
	public Date getSubmitTimeStart() {
		return this.submitTimeStart;
	}
	
	public void setSubmitTimeStart(Date submitTimeStart) {
		this.submitTimeStart = submitTimeStart;
	}
	
	public Date getSubmitTimeEnd() {
		return this.submitTimeEnd;
	}
	
	public void setSubmitTimeEnd(Date submitTimeEnd) {
		this.submitTimeEnd = submitTimeEnd;
	}
	
	public String getFormStatus() {
		return this.formStatus;
	}
	
	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	
	public List<String> getBusiTypeList() {
		return this.busiTypeList;
	}
	
	public void setBusiTypeList(List<String> busiTypeList) {
		this.busiTypeList = busiTypeList;
	}
	
}
