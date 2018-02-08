package com.born.fcs.am.ws.info.pledgeasset;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * @author Ji
 *
 *         资产通用信息
 */

public class PledgeTypeCommonInfo extends BaseToStringInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4793393213347472054L;

	private long commonId;

	private long assetsId;

	private long typeId;

	private double pledgeRate;

	private String evaluationCompany;

	private Money evaluationPrice = new Money(0, 0);

	private Money mortgagePrice = new Money(0, 0);

	private String longitude;

	private String latitude;

	private Date rawAddTime;

	public long getCommonId() {
		return commonId;
	}

	public void setCommonId(long commonId) {
		this.commonId = commonId;
	}

	public long getAssetsId() {
		return assetsId;
	}

	public void setAssetsId(long assetsId) {
		this.assetsId = assetsId;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public double getPledgeRate() {
		return pledgeRate;
	}

	public void setPledgeRate(double pledgeRate) {
		this.pledgeRate = pledgeRate;
	}

	public String getEvaluationCompany() {
		return evaluationCompany;
	}

	public void setEvaluationCompany(String evaluationCompany) {
		this.evaluationCompany = evaluationCompany;
	}

	public Money getEvaluationPrice() {
		return evaluationPrice;
	}

	public void setEvaluationPrice(Money evaluationPrice) {
		this.evaluationPrice = evaluationPrice;
	}

	public Money getMortgagePrice() {
		return mortgagePrice;
	}

	public void setMortgagePrice(Money mortgagePrice) {
		this.mortgagePrice = mortgagePrice;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

}
