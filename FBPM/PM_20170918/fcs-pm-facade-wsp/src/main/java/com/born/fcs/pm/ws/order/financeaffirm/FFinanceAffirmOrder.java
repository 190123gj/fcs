package com.born.fcs.pm.ws.order.financeaffirm;

import java.util.List;

import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

/**
 * 财务确认-资金划付和收费通知Order
 *
 *
 * @author Ji
 *
 */
public class FFinanceAffirmOrder extends ProcessOrder {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6334714860693386445L;
	
	private Long affirmId;
	
	private Long formId;
	
	private String affirmFormType;
	
	private Money amount = new Money(0, 0);
	
	private String remark;
	
	private String attach;
	
	private String projectCode;
	
	private String projectName;
	
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
	
	private List<FFinanceAffirmDetailOrder> detailOrders;
	
	public Long getAffirmId() {
		return affirmId;
	}
	
	public void setAffirmId(Long affirmId) {
		this.affirmId = affirmId;
	}
	
	public Long getFormId() {
		return formId;
	}
	
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	
	public String getAffirmFormType() {
		return affirmFormType;
	}
	
	public void setAffirmFormType(String affirmFormType) {
		this.affirmFormType = affirmFormType;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getAttach() {
		return attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	public List<FFinanceAffirmDetailOrder> getDetailOrders() {
		return detailOrders;
	}
	
	public void setDetailOrders(List<FFinanceAffirmDetailOrder> detailOrders) {
		this.detailOrders = detailOrders;
	}
	
}
