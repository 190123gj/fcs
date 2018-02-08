package com.born.fcs.pm.ws.order.financeaffirm;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 项目收费/划付明细查询Order
 *
 */
public class ProjectChargePayQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -4407939132995619801L;
	
	private String projectCode; //项目编号
	
	private String projectName; //项目名称
	
	private long customerId; //客户ID
	
	private String customerName; //客户名称
	
	private String busiType;//业务品种
	
	/**
	 * CHARGE_NOTIFICATION 收费通知
	 * 
	 * CAPITAL_APPROPROATION_APPLY 资金划付
	 */
	private String affirmFormType;
	
	/**
	 * 费用类型
	 */
	private String feeType;
	
	/** 费用类型 */
	private List<String> feeTypeList;
	
	/**
	 * 收费/划付时间-开始
	 */
	private Date payTimeStart;
	
	/**
	 * 收费/划付时间-结束
	 */
	private Date payTimeEnd;
	
	/**
	 * 用来判定剩余退还金额是否大于0
	 */
	private String returnAmountStr;
	
	/** 财务确认-资金划付和收费通知-明细id */
	private List<Long> payIdList;
	
	private long detailId;

	/** 不参与计算的formId */
	private long expectFormId;

	public long getExpectFormId() {
		return expectFormId;
	}

	public void setExpectFormId(long expectFormId) {
		this.expectFormId = expectFormId;
	}

	public long getDetailId() {
		return detailId;
	}
	
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	
	public List<Long> getPayIdList() {
		return payIdList;
	}
	
	public void setPayIdList(List<Long> payIdList) {
		this.payIdList = payIdList;
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
	
	public String getBusiType() {
		return this.busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getAffirmFormType() {
		return this.affirmFormType;
	}
	
	public void setAffirmFormType(String affirmFormType) {
		this.affirmFormType = affirmFormType;
	}
	
	public String getFeeType() {
		return this.feeType;
	}
	
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	public Date getPayTimeStart() {
		return this.payTimeStart;
	}
	
	public void setPayTimeStart(Date payTimeStart) {
		this.payTimeStart = payTimeStart;
	}
	
	public Date getPayTimeEnd() {
		return this.payTimeEnd;
	}
	
	public void setPayTimeEnd(Date payTimeEnd) {
		this.payTimeEnd = payTimeEnd;
	}
	
	public List<String> getFeeTypeList() {
		return this.feeTypeList;
	}
	
	public void setFeeTypeList(List<String> feeTypeList) {
		this.feeTypeList = feeTypeList;
	}
	
	public String getReturnAmountStr() {
		return returnAmountStr;
	}
	
	public void setReturnAmountStr(String returnAmountStr) {
		this.returnAmountStr = returnAmountStr;
	}
	
}
