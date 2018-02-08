package com.born.fcs.pm.ws.order.capitalappropriationapply;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 资金划付申请-资金划付信息Order
 *
 *
 * @author Ji
 *
 */
public class FCapitalAppropriationApplyFeeOrder extends FormOrderBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4963726382598313149L;
	
	private Long id;
	
	private long applyId;
	
	private Double appropriateAmount;
	
	private String appropriateReason;
	
	/**
	 * 被扣划、冻结、其他
	 */
	private String comType;
	
	private String remark;
	
	private String formChange;
	
	private String riskCouncilSummary;
	
	private String projectApproval;
	
	private String financeAffirmDetailId;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private List<FCapitalAppropriationApplyFeeCompensatoryChannelOrder> compensatoryChannelOrders;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public long getApplyId() {
		return applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public String getAppropriateReason() {
		return appropriateReason;
	}
	
	public void setAppropriateReason(String appropriateReason) {
		this.appropriateReason = appropriateReason;
	}
	
	public String getComType() {
		return this.comType;
	}
	
	public void setComType(String comType) {
		this.comType = comType;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public Double getAppropriateAmount() {
		return appropriateAmount;
	}
	
	public void setAppropriateAmount(Double appropriateAmount) {
		this.appropriateAmount = appropriateAmount;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public String getFormChange() {
		return formChange;
	}
	
	public void setFormChange(String formChange) {
		this.formChange = formChange;
	}
	
	public String getRiskCouncilSummary() {
		return riskCouncilSummary;
	}
	
	public void setRiskCouncilSummary(String riskCouncilSummary) {
		this.riskCouncilSummary = riskCouncilSummary;
	}
	
	public String getProjectApproval() {
		return projectApproval;
	}
	
	public void setProjectApproval(String projectApproval) {
		this.projectApproval = projectApproval;
	}
	
	public String getFinanceAffirmDetailId() {
		return financeAffirmDetailId;
	}
	
	public void setFinanceAffirmDetailId(String financeAffirmDetailId) {
		this.financeAffirmDetailId = financeAffirmDetailId;
	}
	
	public List<FCapitalAppropriationApplyFeeCompensatoryChannelOrder> getCompensatoryChannelOrders() {
		return compensatoryChannelOrders;
	}
	
	public void setCompensatoryChannelOrders(	List<FCapitalAppropriationApplyFeeCompensatoryChannelOrder> compensatoryChannelOrders) {
		this.compensatoryChannelOrders = compensatoryChannelOrders;
	}
	
}
