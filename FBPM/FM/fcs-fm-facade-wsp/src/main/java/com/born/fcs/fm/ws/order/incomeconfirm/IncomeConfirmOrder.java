package com.born.fcs.fm.ws.order.incomeconfirm;

import java.util.List;

import com.born.fcs.pm.ws.order.base.FormOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 收入确认
 * @author jil
 * 
 */
public class IncomeConfirmOrder extends FormOrderBase {
	
	private static final long serialVersionUID = -779194969302886567L;
	/** 收入确认ID */
	private Long incomeId;
	/** 项目编号 */
	private String projectCode;
	/** 项目名称 */
	private String projectName;
	/** 客户ID */
	private long customerId;
	/** 客户名称 */
	private String customerName;
	/** 业务类型 */
	private String busiType;
	/** 业务类型名称 */
	private String busiTypeName;
	/** 收费总金额 */
	private Money chargedAmount = new Money(0, 0);
	/** 已确认收入金额 */
	private Money confirmedIncomeAmount = new Money(0, 0);
	/** 未确认金额 */
	private Money notConfirmedIncomeAmount = new Money(0, 0);
	/** 本月确认收入金额 */
	private Money thisMonthConfirmedIncomeAmount = new Money(0, 0);
	/** 收入确认明细order */
	private List<IncomeConfirmDetailOrder> incomeConfirmDetailOrders;
	private boolean isCalculate = false;
	
	public Long getIncomeId() {
		return incomeId;
	}
	
	public void setIncomeId(Long incomeId) {
		this.incomeId = incomeId;
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
	
	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getBusiType() {
		return busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public Money getChargedAmount() {
		return chargedAmount;
	}
	
	public void setChargedAmount(Money chargedAmount) {
		this.chargedAmount = chargedAmount;
	}
	
	public Money getConfirmedIncomeAmount() {
		return confirmedIncomeAmount;
	}
	
	public void setConfirmedIncomeAmount(Money confirmedIncomeAmount) {
		this.confirmedIncomeAmount = confirmedIncomeAmount;
	}
	
	public Money getNotConfirmedIncomeAmount() {
		return notConfirmedIncomeAmount;
	}
	
	public void setNotConfirmedIncomeAmount(Money notConfirmedIncomeAmount) {
		this.notConfirmedIncomeAmount = notConfirmedIncomeAmount;
	}
	
	public Money getThisMonthConfirmedIncomeAmount() {
		return thisMonthConfirmedIncomeAmount;
	}
	
	public void setThisMonthConfirmedIncomeAmount(Money thisMonthConfirmedIncomeAmount) {
		this.thisMonthConfirmedIncomeAmount = thisMonthConfirmedIncomeAmount;
	}
	
	public List<IncomeConfirmDetailOrder> getIncomeConfirmDetailOrders() {
		return incomeConfirmDetailOrders;
	}
	
	public void setIncomeConfirmDetailOrders(	List<IncomeConfirmDetailOrder> incomeConfirmDetailOrders) {
		this.incomeConfirmDetailOrders = incomeConfirmDetailOrders;
	}
	
	public boolean isCalculate() {
		return this.isCalculate;
	}
	
	public void setCalculate(boolean isCalculate) {
		this.isCalculate = isCalculate;
	}
	
}
