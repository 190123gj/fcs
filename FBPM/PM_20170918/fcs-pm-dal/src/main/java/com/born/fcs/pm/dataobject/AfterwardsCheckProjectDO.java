package com.born.fcs.pm.dataobject;

import java.util.Date;

public class AfterwardsCheckProjectDO extends FormProjectDO {
	
	/*首次放款时间*/
	
	private static final long serialVersionUID = -5544737718617803487L;
	
	private long id;
	
	private Date firstLoanUseTime;
	/*报告期*/
	private String rounds;
	
	private Date afterwardsCheckAddTime;
	
	private Date checkDate;
	
	private String checkAddress;
	
	private String edition;
	
	public Date getFirstLoanUseTime() {
		return this.firstLoanUseTime;
	}
	
	public void setFirstLoanUseTime(Date firstLoanUseTime) {
		this.firstLoanUseTime = firstLoanUseTime;
	}
	
	public String getRounds() {
		return this.rounds;
	}
	
	public void setRounds(String rounds) {
		this.rounds = rounds;
	}
	
	public Date getAfterwardsCheckAddTime() {
		return this.afterwardsCheckAddTime;
	}
	
	public void setAfterwardsCheckAddTime(Date afterwardsCheckAddTime) {
		this.afterwardsCheckAddTime = afterwardsCheckAddTime;
	}
	
	public Date getCheckDate() {
		return this.checkDate;
	}
	
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	public String getCheckAddress() {
		return this.checkAddress;
	}
	
	public void setCheckAddress(String checkAddress) {
		this.checkAddress = checkAddress;
	}
	
	public String getEdition() {
		return this.edition;
	}
	
	public void setEdition(String edition) {
		this.edition = edition;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
}
