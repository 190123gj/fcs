package com.born.fcs.crm.ws.service.order;

import java.util.Date;
import java.util.HashMap;

import com.yjf.common.lang.util.money.Money;

/**
 * 征信查询回写到客户关系表order
 */
public class UpdateFromCreditOrder extends ChangeListOrder {
	
	private static final long serialVersionUID = -7152424030216622623L;
	
	private long userId;
	
	private String legalPersion;
	
	private Date establishedTime;
	
	private Money registerCapital = new Money(0, 0);
	
	private String busiScope;
	
	private String address;
	
	private String orgCode;
	
	private String localTaxCertNo;
	
	private String taxRegCertificateNo;
	
	private String loanCardNo;
	
	public static HashMap<String, String> changeMap() {
		HashMap<String, String> map = new HashMap<>();
		map.put("legalPersion", "法定代表人");
		map.put("establishedTime", "成立时间 ");
		map.put("registerCapital", "注册资本");
		map.put("busiScope", "经营范围");
		map.put("address", "企业地址");
		map.put("orgCode", "组织机构代码");
		map.put("localTaxCertNo", "地税证");
		map.put("loanCardNo", "贷款卡号");
		map.put("taxRegCertificateNo", "国税证");
		return map;
	}
	
	public String getLocalTaxCertNo() {
		return localTaxCertNo;
	}
	
	public void setLocalTaxCertNo(String localTaxCertNo) {
		this.localTaxCertNo = localTaxCertNo;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getLegalPersion() {
		return legalPersion;
	}
	
	public void setLegalPersion(String legalPersion) {
		this.legalPersion = legalPersion;
	}
	
	public Date getEstablishedTime() {
		return establishedTime;
	}
	
	public void setEstablishedTime(Date establishedTime) {
		this.establishedTime = establishedTime;
	}
	
	public Money getRegisterCapital() {
		return registerCapital;
	}
	
	public void setRegisterCapital(Money registerCapital) {
		this.registerCapital = registerCapital;
	}
	
	public String getBusiScope() {
		return busiScope;
	}
	
	public void setBusiScope(String busiScope) {
		this.busiScope = busiScope;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getOrgCode() {
		return orgCode;
	}
	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	public String getTaxRegCertificateNo() {
		return taxRegCertificateNo;
	}
	
	public void setTaxRegCertificateNo(String taxRegCertificateNo) {
		this.taxRegCertificateNo = taxRegCertificateNo;
	}
	
	public String getLoanCardNo() {
		return loanCardNo;
	}
	
	public void setLoanCardNo(String loanCardNo) {
		this.loanCardNo = loanCardNo;
	}
}
