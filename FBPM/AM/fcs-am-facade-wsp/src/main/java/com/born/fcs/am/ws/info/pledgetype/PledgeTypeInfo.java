package com.born.fcs.am.ws.info.pledgetype;

import java.util.Date;
import java.util.List;

import com.born.fcs.am.ws.info.pledgeimage.PledgeImageCustomInfo;
import com.born.fcs.am.ws.info.pledgenetwork.PledgeNetworkCustomInfo;
import com.born.fcs.am.ws.info.pledgetext.PledgeTextCustomInfo;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

public class PledgeTypeInfo extends BaseToStringInfo {

	private static final long serialVersionUID = 4707698711824974520L;

	private long typeId;

	private String levelOne;

	private String levelOneDesc;

	private String levelTwo;

	private String levelTwoDesc;

	private String levelThree;

	private String levelThreeDesc;

	private double pledgeRate;

	private BooleanEnum isMapPosition;

	private Date rawAddTime;

	private Date rawUpdateTime;

	private List<PledgeTextCustomInfo> textInfos;

	private List<PledgeImageCustomInfo> imageInfos;

	private List<PledgeNetworkCustomInfo> networkInfos;

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
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

	public BooleanEnum getIsMapPosition() {
		return isMapPosition;
	}

	public void setIsMapPosition(BooleanEnum isMapPosition) {
		this.isMapPosition = isMapPosition;
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

	public List<PledgeTextCustomInfo> getTextInfos() {
		return textInfos;
	}

	public void setTextInfos(List<PledgeTextCustomInfo> textInfos) {
		this.textInfos = textInfos;
	}

	public List<PledgeImageCustomInfo> getImageInfos() {
		return imageInfos;
	}

	public void setImageInfos(List<PledgeImageCustomInfo> imageInfos) {
		this.imageInfos = imageInfos;
	}

	public List<PledgeNetworkCustomInfo> getNetworkInfos() {
		return networkInfos;
	}

	public void setNetworkInfos(List<PledgeNetworkCustomInfo> networkInfos) {
		this.networkInfos = networkInfos;
	}

}
