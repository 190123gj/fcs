package com.born.fcs.pm.dataobject;

import java.util.Date;
import java.util.List;

import com.yjf.common.lang.util.money.Money;

/**
 * 项目签报
 *
 *
 * @author wuzj
 *
 */
public class FormChangeApplySearchDO extends SimpleFormDO {
	
	private static final long serialVersionUID = -2731425692673842117L;
	
	private String projectStatus;
	
	private long applyId;
	
	private String applyCode;
	
	private String likeApplyCode;
	
	private String applyType;
	
	private String applyTitle;
	
	private String projectCode;
	
	private String likeProjectCode;
	
	private String projectName;
	
	private long customerId;
	
	private String customerName;
	
	private String customerType;
	
	private String busiType;
	
	private String busiTypeName;
	
	private String changeForm;
	
	private long changeFormId;
	
	private String changeRemark;
	
	private String isNeedCouncil;
	
	private String status;
	
	private Money chargedAmount = new Money(0, 0);
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	//查询条件部分
	List<Long> deptIdList;
	
	List<String> statusList;
	
	private String formStatus;
	
	private String isSelForCharge;
	
	private String isSelXinHui;
	
	long loginUserId;
	
	long pageSize;
	
	long limitStart;
	
	String sortCol;
	
	String sortOrder;
	
	public String getProjectStatus() {
		return this.projectStatus;
	}
	
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public String getApplyCode() {
		return this.applyCode;
	}
	
	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}
	
	public String getApplyType() {
		return this.applyType;
	}
	
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	
	public String getApplyTitle() {
		return this.applyTitle;
	}
	
	public void setApplyTitle(String applyTitle) {
		this.applyTitle = applyTitle;
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
	
	public String getCustomerType() {
		return this.customerType;
	}
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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
	
	public String getChangeForm() {
		return this.changeForm;
	}
	
	public void setChangeForm(String changeForm) {
		this.changeForm = changeForm;
	}
	
	public long getChangeFormId() {
		return this.changeFormId;
	}
	
	public void setChangeFormId(long changeFormId) {
		this.changeFormId = changeFormId;
	}
	
	public String getChangeRemark() {
		return this.changeRemark;
	}
	
	public void setChangeRemark(String changeRemark) {
		this.changeRemark = changeRemark;
	}
	
	public String getIsNeedCouncil() {
		return this.isNeedCouncil;
	}
	
	public void setIsNeedCouncil(String isNeedCouncil) {
		this.isNeedCouncil = isNeedCouncil;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Money getChargedAmount() {
		return this.chargedAmount;
	}
	
	public void setChargedAmount(Money chargedAmount) {
		this.chargedAmount = chargedAmount;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public List<Long> getDeptIdList() {
		return this.deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	
	public List<String> getStatusList() {
		return this.statusList;
	}
	
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
	
	public String getFormStatus() {
		return this.formStatus;
	}
	
	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	
	public long getLoginUserId() {
		return this.loginUserId;
	}
	
	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public long getPageSize() {
		return this.pageSize;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getLimitStart() {
		return this.limitStart;
	}
	
	public void setLimitStart(long limitStart) {
		this.limitStart = limitStart;
	}
	
	public String getSortCol() {
		return this.sortCol;
	}
	
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
	
	public String getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public String getIsSelForCharge() {
		return this.isSelForCharge;
	}
	
	public void setIsSelForCharge(String isSelForCharge) {
		this.isSelForCharge = isSelForCharge;
	}
	
	public String getIsSelXinHui() {
		return this.isSelXinHui;
	}
	
	public void setIsSelXinHui(String isSelXinHui) {
		this.isSelXinHui = isSelXinHui;
	}
	
	public String getLikeApplyCode() {
		return this.likeApplyCode;
	}
	
	public void setLikeApplyCode(String likeApplyCode) {
		this.likeApplyCode = likeApplyCode;
	}
	
	public String getLikeProjectCode() {
		return this.likeProjectCode;
	}
	
	public void setLikeProjectCode(String likeProjectCode) {
		this.likeProjectCode = likeProjectCode;
	}
	
}
