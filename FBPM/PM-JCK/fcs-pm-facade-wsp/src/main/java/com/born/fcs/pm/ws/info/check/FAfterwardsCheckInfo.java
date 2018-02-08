package com.born.fcs.pm.ws.info.check;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CheckReportEditionEnums;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;
import com.yjf.common.lang.util.money.Money;

public class FAfterwardsCheckInfo extends SimpleFormProjectInfo {
	
	private static final long serialVersionUID = 6708654470011753850L;
	
	private long id;
	private Date checkDate;
	private String checkAddress;
	private CheckReportEditionEnums edition;
	private int roundYear;
	private int roundTime;
	private Money usedAmount = new Money(0, 0);
	private Money repayedAmount = new Money(0, 0);
	private String useWay;
	private Date rawAddTime;
	private Date rawUpdateTime;
	private BooleanEnum isLastest;
	
	private boolean isSpecialIndustry = false; //是否属于特殊行业
	private String industryName; //行业名称
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Date getCheckDate() {
		return checkDate;
	}
	
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	public String getCheckAddress() {
		return checkAddress;
	}
	
	public void setCheckAddress(String checkAddress) {
		this.checkAddress = checkAddress;
	}
	
	public CheckReportEditionEnums getEdition() {
		return edition;
	}
	
	public void setEdition(CheckReportEditionEnums edition) {
		this.edition = edition;
	}
	
	public int getRoundYear() {
		return roundYear;
	}
	
	public void setRoundYear(int roundYear) {
		this.roundYear = roundYear;
	}
	
	public int getRoundTime() {
		return roundTime;
	}
	
	public void setRoundTime(int roundTime) {
		this.roundTime = roundTime;
	}
	
	public Money getUsedAmount() {
		return usedAmount;
	}
	
	public void setUsedAmount(Money usedAmount) {
		this.usedAmount = usedAmount;
	}
	
	public Money getRepayedAmount() {
		return repayedAmount;
	}
	
	public void setRepayedAmount(Money repayedAmount) {
		this.repayedAmount = repayedAmount;
	}
	
	public String getUseWay() {
		return useWay;
	}
	
	public void setUseWay(String useWay) {
		this.useWay = useWay;
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
	
	public boolean isSpecialIndustry() {
		return isSpecialIndustry;
	}
	
	public void setSpecialIndustry(boolean isSpecialIndustry) {
		this.isSpecialIndustry = isSpecialIndustry;
	}
	
	public String getIndustryName() {
		return industryName;
	}
	
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public BooleanEnum getIsLastest() {
		return this.isLastest;
	}

	public void setIsLastest(BooleanEnum isLastest) {
		this.isLastest = isLastest;
	}
	
}
