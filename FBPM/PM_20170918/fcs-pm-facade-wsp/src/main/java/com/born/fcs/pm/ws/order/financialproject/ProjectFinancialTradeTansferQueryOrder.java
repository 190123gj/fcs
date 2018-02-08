package com.born.fcs.pm.ws.order.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 
 * 理财项目转让交易查询Order
 *
 * @author wuzj
 *
 */
public class ProjectFinancialTradeTansferQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -2953258218718909908L;
	//交易ID
	private long tradeId;
	//流水号
	private String flowNo;
	//项目编号
	private String projectCode;
	//申请单表单ID
	private long applyId;
	//转让日期
	private Date transferTimeStart;
	private Date transferTimeEnd;
	
	public long getTradeId() {
		return this.tradeId;
	}
	
	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
	}
	
	public String getFlowNo() {
		return this.flowNo;
	}
	
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public Date getTransferTimeStart() {
		return this.transferTimeStart;
	}
	
	public void setTransferTimeStart(Date transferTimeStart) {
		this.transferTimeStart = transferTimeStart;
	}
	
	public Date getTransferTimeEnd() {
		return this.transferTimeEnd;
	}
	
	public void setTransferTimeEnd(Date transferTimeEnd) {
		this.transferTimeEnd = transferTimeEnd;
	}
	
}
