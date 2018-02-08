/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.ws.order.pledgetype;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 抵质押品分类Order
 *
 * @author jil
 *
 */
public class PledgeTypeQueryOrder extends QueryPageBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6744020142207248192L;

	private Long typeId;

	private String levelOne;

	private String levelOneDesc;

	private String levelTwo;

	private String levelTwoDesc;

	private String levelThree;

	private String levelThreeDesc;

	private double pledgeRate;

	private String isMapPosition;

	private Date rawAddTime;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getLevelOne() {
		return levelOne;
	}

	public void setLevelOne(String levelOne) {
		this.levelOne = levelOne;
	}

	public String getLevelOneDesc() {
		return levelOneDesc;
	}

	public void setLevelOneDesc(String levelOneDesc) {
		this.levelOneDesc = levelOneDesc;
	}

	public String getLevelTwo() {
		return levelTwo;
	}

	public void setLevelTwo(String levelTwo) {
		this.levelTwo = levelTwo;
	}

	public String getLevelTwoDesc() {
		return levelTwoDesc;
	}

	public void setLevelTwoDesc(String levelTwoDesc) {
		this.levelTwoDesc = levelTwoDesc;
	}

	public String getLevelThree() {
		return levelThree;
	}

	public void setLevelThree(String levelThree) {
		this.levelThree = levelThree;
	}

	public String getLevelThreeDesc() {
		return levelThreeDesc;
	}

	public void setLevelThreeDesc(String levelThreeDesc) {
		this.levelThreeDesc = levelThreeDesc;
	}

	public double getPledgeRate() {
		return pledgeRate;
	}

	public void setPledgeRate(double pledgeRate) {
		this.pledgeRate = pledgeRate;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

	public String getIsMapPosition() {
		return isMapPosition;
	}

	public void setIsMapPosition(String isMapPosition) {
		this.isMapPosition = isMapPosition;
	}

}
