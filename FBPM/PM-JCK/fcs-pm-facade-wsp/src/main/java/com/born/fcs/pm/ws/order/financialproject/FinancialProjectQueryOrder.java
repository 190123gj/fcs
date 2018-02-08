package com.born.fcs.pm.ws.order.financialproject;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;

/**
 * 理财项目查询Order
 *
 * @author wuzj
 *
 */
public class FinancialProjectQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = -7405547247747810034L;
	
	private List<String> statusList;
	
	private Long productId;
	
	private String productType;
	
	private String productName;
	
	private String termType;
	
	private String issueInstitution;
	
	private Long createUserId;
	
	private String createUserAccount;
	
	private String createUserName;
	
	private String status;
	
	private BooleanEnum hasHoldNum;
	
	private BooleanEnum hasOrignalHoldNum;
	
	//查询该时间点到期的项目
	private Date actualExpireDate;
	
	private Date buyDateStart;
	
	private Date buyDateEnd;
	
	private Date expireDateStart;
	
	private Date expireDateEnd;
	
	public List<String> getStatusList() {
		return this.statusList;
	}
	
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
	
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
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getTermType() {
		return this.termType;
	}
	
	public void setTermType(String termType) {
		this.termType = termType;
	}
	
	public String getIssueInstitution() {
		return this.issueInstitution;
	}
	
	public void setIssueInstitution(String issueInstitution) {
		this.issueInstitution = issueInstitution;
	}
	
	public Long getCreateUserId() {
		return this.createUserId;
	}
	
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	
	public String getCreateUserAccount() {
		return this.createUserAccount;
	}
	
	public void setCreateUserAccount(String createUserAccount) {
		this.createUserAccount = createUserAccount;
	}
	
	public String getCreateUserName() {
		return this.createUserName;
	}
	
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public BooleanEnum getHasHoldNum() {
		return this.hasHoldNum;
	}
	
	public void setHasHoldNum(BooleanEnum hasHoldNum) {
		this.hasHoldNum = hasHoldNum;
	}
	
	public BooleanEnum getHasOrignalHoldNum() {
		return this.hasOrignalHoldNum;
	}
	
	public void setHasOrignalHoldNum(BooleanEnum hasOrignalHoldNum) {
		this.hasOrignalHoldNum = hasOrignalHoldNum;
	}
	
	public Date getActualExpireDate() {
		return this.actualExpireDate;
	}
	
	public void setActualExpireDate(Date actualExpireDate) {
		this.actualExpireDate = actualExpireDate;
	}
	
	public Date getBuyDateStart() {
		return this.buyDateStart;
	}
	
	public void setBuyDateStart(Date buyDateStart) {
		this.buyDateStart = buyDateStart;
	}
	
	public Date getBuyDateEnd() {
		return this.buyDateEnd;
	}
	
	public void setBuyDateEnd(Date buyDateEnd) {
		this.buyDateEnd = buyDateEnd;
	}
	
	public Date getExpireDateStart() {
		return this.expireDateStart;
	}
	
	public void setExpireDateStart(Date expireDateStart) {
		this.expireDateStart = expireDateStart;
	}
	
	public Date getExpireDateEnd() {
		return this.expireDateEnd;
	}
	
	public void setExpireDateEnd(Date expireDateEnd) {
		this.expireDateEnd = expireDateEnd;
	}
	
}
