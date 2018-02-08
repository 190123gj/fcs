package com.born.fcs.pm.ws.order.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 理财项目赎回交易查询Order
 *
 *
 * @author wuzj
 *
 */
public class ProjectFinancialTradeRedeemQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = 739326764798677709L;
	//交易ID
	private long tradeId;
	//流水号
	private String flowNo;
	//项目编号
	private String projectCode;
	//申请单表单ID
	private long applyId;
	
	//赎回时间
	private Date redeemTimeStart;
	
	private Date redeemTimeEnd;
	
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
	
	public Date getRedeemTimeStart() {
		return this.redeemTimeStart;
	}
	
	public void setRedeemTimeStart(Date redeemTimeStart) {
		this.redeemTimeStart = redeemTimeStart;
	}
	
	public Date getRedeemTimeEnd() {
		return this.redeemTimeEnd;
	}
	
	public void setRedeemTimeEnd(Date redeemTimeEnd) {
		this.redeemTimeEnd = redeemTimeEnd;
	}
}
