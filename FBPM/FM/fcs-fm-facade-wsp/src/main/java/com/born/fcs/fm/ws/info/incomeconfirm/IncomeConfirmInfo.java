package com.born.fcs.fm.ws.info.incomeconfirm;

import java.util.Date;
import java.util.List;

import com.born.fcs.fm.ws.enums.IncomeConfirmStatusEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 收入确认
 * @author jil
 *
 */
public class IncomeConfirmInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -8810684677116263922L;
	/** 收入确认ID */
	private long incomeId;
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
	/** 收入确认状态(未完成收入确认，已完成收入确认) */
	private IncomeConfirmStatusEnum incomeConfirmStatus;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	/** 收入确认明细 */
	private List<IncomeConfirmDetailInfo> incomeConfirmDetailInfos;
	
	public long getIncomeId() {
		return incomeId;
	}
	
	public void setIncomeId(long incomeId) {
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
	
	public List<IncomeConfirmDetailInfo> getIncomeConfirmDetailInfos() {
		return incomeConfirmDetailInfos;
	}
	
	public void setIncomeConfirmDetailInfos(List<IncomeConfirmDetailInfo> incomeConfirmDetailInfos) {
		this.incomeConfirmDetailInfos = incomeConfirmDetailInfos;
	}
	
	public IncomeConfirmStatusEnum getIncomeConfirmStatus() {
		return incomeConfirmStatus;
	}
	
	public void setIncomeConfirmStatus(IncomeConfirmStatusEnum incomeConfirmStatus) {
		this.incomeConfirmStatus = incomeConfirmStatus;
	}
	
}
