package com.born.fcs.pm.ws.info.setup;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;

public class FProjectUnderwritingInfo extends FProjectInfo {
	
	private static final long serialVersionUID = 8873267921439474355L;
	
	private long id;
	
	private long coInstitutionId;
	
	private String coInstitutionName;
	
	private long letterInstitutionId;
	
	private String letterInstitutionName;
	
	private String repaySource;
	
	private BooleanEnum hasFinancialSupport;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getCoInstitutionId() {
		return this.coInstitutionId;
	}
	
	public void setCoInstitutionId(long coInstitutionId) {
		this.coInstitutionId = coInstitutionId;
	}
	
	public String getCoInstitutionName() {
		return this.coInstitutionName;
	}
	
	public void setCoInstitutionName(String coInstitutionName) {
		this.coInstitutionName = coInstitutionName;
	}
	
	public long getLetterInstitutionId() {
		return this.letterInstitutionId;
	}
	
	public void setLetterInstitutionId(long letterInstitutionId) {
		this.letterInstitutionId = letterInstitutionId;
	}
	
	public String getLetterInstitutionName() {
		return this.letterInstitutionName;
	}
	
	public void setLetterInstitutionName(String letterInstitutionName) {
		this.letterInstitutionName = letterInstitutionName;
	}
	
	public String getRepaySource() {
		return this.repaySource;
	}
	
	public void setRepaySource(String repaySource) {
		this.repaySource = repaySource;
	}
	
	public BooleanEnum getHasFinancialSupport() {
		return this.hasFinancialSupport;
	}
	
	public void setHasFinancialSupport(BooleanEnum hasFinancialSupport) {
		this.hasFinancialSupport = hasFinancialSupport;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
}
