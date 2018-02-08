package com.born.fcs.fm.ws.info.incomeconfirm;

import java.util.Date;

import com.born.fcs.fm.ws.enums.ConfirmStatusEnum;
import com.born.fcs.fm.ws.enums.IncomeConfirmStatusEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 收入确认明细
 * @author wuzj
 */
public class IncomeConfirmDetailListInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -1749648060740466544L;
	
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
	
	/** 明细ID */
	private long detailId;
	/** 收入期间 */
	private String incomePeriod;
	/** 确认状态 */
	private ConfirmStatusEnum confirmStatus;
	/** 系统预估分摊金额 */
	private Money systemEstimatedAmount = new Money(0, 0);
	/** 收入确认金额 */
	private Money incomeConfirmedAmount = new Money(0, 0);
	/** 是否确认 */
	private BooleanEnum isConfirmed;
	/** 明细新增时间 */
	private Date detailAddTime;
	/** 明细修改时间 */
	private Date detailUpdateTime;
	
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
	
	public IncomeConfirmStatusEnum getIncomeConfirmStatus() {
		return incomeConfirmStatus;
	}
	
	public void setIncomeConfirmStatus(IncomeConfirmStatusEnum incomeConfirmStatus) {
		this.incomeConfirmStatus = incomeConfirmStatus;
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
	
	public long getDetailId() {
		return detailId;
	}
	
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	
	public String getIncomePeriod() {
		return incomePeriod;
	}
	
	public void setIncomePeriod(String incomePeriod) {
		this.incomePeriod = incomePeriod;
	}
	
	public ConfirmStatusEnum getConfirmStatus() {
		return confirmStatus;
	}
	
	public void setConfirmStatus(ConfirmStatusEnum confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	
	public Money getSystemEstimatedAmount() {
		return systemEstimatedAmount;
	}
	
	public void setSystemEstimatedAmount(Money systemEstimatedAmount) {
		this.systemEstimatedAmount = systemEstimatedAmount;
	}
	
	public Money getIncomeConfirmedAmount() {
		return incomeConfirmedAmount;
	}
	
	public void setIncomeConfirmedAmount(Money incomeConfirmedAmount) {
		this.incomeConfirmedAmount = incomeConfirmedAmount;
	}
	
	public BooleanEnum getIsConfirmed() {
		return isConfirmed;
	}
	
	public void setIsConfirmed(BooleanEnum isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	
	public Date getDetailAddTime() {
		return detailAddTime;
	}
	
	public void setDetailAddTime(Date detailAddTime) {
		this.detailAddTime = detailAddTime;
	}
	
	public Date getDetailUpdateTime() {
		return detailUpdateTime;
	}
	
	public void setDetailUpdateTime(Date detailUpdateTime) {
		this.detailUpdateTime = detailUpdateTime;
	}
	
}
