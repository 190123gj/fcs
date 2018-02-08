package com.born.fcs.pm.ws.order.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;

/**
 * 理财项目计提台帐查询
 *
 * @author wuzj
 */
public class FinancialProjectWithdrawingQueryOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = 8654104652676761730L;
	
	/** 产品ID */
	private Long productId;
	/** 转让交易ID */
	private Long transferTradeId;
	/** 计提类型 RECEIPT/PAYMENT */
	private String withdrawType;
	/** 是否完成计提 YES/NO */
	private String withdrawFinish;
	/** 产品名称 */
	private String productName;
	/** 项目编号 */
	private String projectCode;
	/** 计提天 */
	private String withdrawDay;
	/** 计提月份 */
	private String withdrawMonth;
	private String withdrawMonthStart;
	private String withdrawMonthEnd;
	/** 最新计提时间-起 */
	private Date withdrawDateStart;
	/** 最新计提时间-止 */
	private Date withdrawDateEnd;
	/** 创建人 */
	private long createUserId;
	
	public Long getProductId() {
		return this.productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Long getTransferTradeId() {
		return this.transferTradeId;
	}
	
	public void setTransferTradeId(Long transferTradeId) {
		this.transferTradeId = transferTradeId;
	}
	
	public String getWithdrawType() {
		return this.withdrawType;
	}
	
	public void setWithdrawType(String withdrawType) {
		this.withdrawType = withdrawType;
	}
	
	public String getWithdrawFinish() {
		return this.withdrawFinish;
	}
	
	public void setWithdrawFinish(String withdrawFinish) {
		this.withdrawFinish = withdrawFinish;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getWithdrawDay() {
		return this.withdrawDay;
	}
	
	public void setWithdrawDay(String withdrawDay) {
		this.withdrawDay = withdrawDay;
	}
	
	public String getWithdrawMonth() {
		return this.withdrawMonth;
	}
	
	public void setWithdrawMonth(String withdrawMonth) {
		this.withdrawMonth = withdrawMonth;
	}
	
	public String getWithdrawMonthStart() {
		return this.withdrawMonthStart;
	}
	
	public void setWithdrawMonthStart(String withdrawMonthStart) {
		this.withdrawMonthStart = withdrawMonthStart;
	}
	
	public String getWithdrawMonthEnd() {
		return this.withdrawMonthEnd;
	}
	
	public void setWithdrawMonthEnd(String withdrawMonthEnd) {
		this.withdrawMonthEnd = withdrawMonthEnd;
	}
	
	public Date getWithdrawDateStart() {
		return this.withdrawDateStart;
	}
	
	public void setWithdrawDateStart(Date withdrawDateStart) {
		this.withdrawDateStart = withdrawDateStart;
	}
	
	public Date getWithdrawDateEnd() {
		return this.withdrawDateEnd;
	}
	
	public void setWithdrawDateEnd(Date withdrawDateEnd) {
		this.withdrawDateEnd = withdrawDateEnd;
	}
	
	public long getCreateUserId() {
		return this.createUserId;
	}
	
	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}
}
