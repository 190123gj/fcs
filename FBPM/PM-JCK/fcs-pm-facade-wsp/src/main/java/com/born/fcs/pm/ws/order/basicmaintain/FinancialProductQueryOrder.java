/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.basicmaintain;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 理财产品Order
 *
 * @author wuzj
 *
 */
public class FinancialProductQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -2516787712400278807L;
	
	private Long productId;
	
	private String productType;
	
	private String termType;
	
	private String interestType;
	
	private String productName;
	
	private String issueInstitution;
	
	private String interestSettlementWay;
	
	private String sellStatus;
	
	public Long getProductId() {
		return this.productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public String getProductType() {
		return this.productType;
	}
	
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public String getTermType() {
		return this.termType;
	}
	
	public void setTermType(String termType) {
		this.termType = termType;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getIssueInstitution() {
		return this.issueInstitution;
	}
	
	public void setIssueInstitution(String issueInstitution) {
		this.issueInstitution = issueInstitution;
	}
	
	public String getInterestSettlementWay() {
		return this.interestSettlementWay;
	}
	
	public void setInterestSettlementWay(String interestSettlementWay) {
		this.interestSettlementWay = interestSettlementWay;
	}
	
	public String getSellStatus() {
		return this.sellStatus;
	}
	
	public void setSellStatus(String sellStatus) {
		this.sellStatus = sellStatus;
	}
	
	public String getInterestType() {
		return this.interestType;
	}
	
	public void setInterestType(String interestType) {
		this.interestType = interestType;
	}
	
}
