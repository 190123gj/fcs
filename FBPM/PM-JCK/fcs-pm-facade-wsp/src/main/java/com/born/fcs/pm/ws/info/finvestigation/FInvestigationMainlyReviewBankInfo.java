package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 开户情况
 * 
 * @author lirz
 *
 * 2016-3-10 下午1:57:44
 */
public class FInvestigationMainlyReviewBankInfo extends BaseToStringInfo{

	private static final long serialVersionUID = -6086017903574383359L;
	
	private long id; //主键
	private long MReviewId; //对应客户主体评价ID
	private String bankName; //开户行
	private String bankDesc; //开户描述
	private String accountNo; //账号
	private String basicFlag; //是否基本账号
	private int sortOrder;
	private Date rawAddTime;
	private Date rawUpdateTime;

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getMReviewId() {
		return MReviewId;
	}
	
	public void setMReviewId(long MReviewId) {
		this.MReviewId = MReviewId;
	}

	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankDesc() {
		return bankDesc;
	}
	
	public void setBankDesc(String bankDesc) {
		this.bankDesc = bankDesc;
	}

	public String getAccountNo() {
		return accountNo;
	}
	
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getBasicFlag() {
		return basicFlag;
	}
	
	public void setBasicFlag(String basicFlag) {
		this.basicFlag = basicFlag;
	}

	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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

}
