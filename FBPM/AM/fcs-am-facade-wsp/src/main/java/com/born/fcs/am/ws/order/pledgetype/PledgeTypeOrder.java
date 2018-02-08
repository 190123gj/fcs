package com.born.fcs.am.ws.order.pledgetype;

import java.util.Date;
import java.util.List;

import com.born.fcs.am.ws.order.pledgeimage.PledgeImageCustomOrder;
import com.born.fcs.am.ws.order.pledgenetwork.PledgeNetworkCustomOrder;
import com.born.fcs.am.ws.order.pledgetext.PledgeTextCustomOrder;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 抵质押品分类Order
 *
 * @author jil
 *
 */
public class PledgeTypeOrder extends ProcessOrder {

	private static final long serialVersionUID = 7909326623014702381L;

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

	private Date rawUpdateTime;

	private List<PledgeTextCustomOrder> pledgeTextCustomOrders;

	private List<PledgeImageCustomOrder> pledgeImageCustomOrders;

	private List<PledgeNetworkCustomOrder> pledgeNetworkCustomOrders;

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

	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}

	public List<PledgeTextCustomOrder> getPledgeTextCustomOrders() {
		return pledgeTextCustomOrders;
	}

	public void setPledgeTextCustomOrders(
			List<PledgeTextCustomOrder> pledgeTextCustomOrders) {
		this.pledgeTextCustomOrders = pledgeTextCustomOrders;
	}

	public List<PledgeImageCustomOrder> getPledgeImageCustomOrders() {
		return pledgeImageCustomOrders;
	}

	public void setPledgeImageCustomOrders(
			List<PledgeImageCustomOrder> pledgeImageCustomOrders) {
		this.pledgeImageCustomOrders = pledgeImageCustomOrders;
	}

	public List<PledgeNetworkCustomOrder> getPledgeNetworkCustomOrders() {
		return pledgeNetworkCustomOrders;
	}

	public void setPledgeNetworkCustomOrders(
			List<PledgeNetworkCustomOrder> pledgeNetworkCustomOrders) {
		this.pledgeNetworkCustomOrders = pledgeNetworkCustomOrders;
	}

	public String getIsMapPosition() {
		return isMapPosition;
	}

	public void setIsMapPosition(String isMapPosition) {
		this.isMapPosition = isMapPosition;
	}

}
