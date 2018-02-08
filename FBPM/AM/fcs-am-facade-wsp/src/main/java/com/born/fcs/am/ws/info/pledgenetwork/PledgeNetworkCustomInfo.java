package com.born.fcs.am.ws.info.pledgenetwork;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 抵质押品-网络信息-自定义Info
 *
 * @author jil
 *
 */
public class PledgeNetworkCustomInfo extends BaseToStringInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4560260263740098900L;

	private long networkId;

	private long typeId;

	private String websiteName;

	private String websiteAddr;

	private Date rawAddTime;

	private Date rawUpdateTime;

	public long getNetworkId() {
		return networkId;
	}

	public void setNetworkId(long networkId) {
		this.networkId = networkId;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public String getWebsiteAddr() {
		return websiteAddr;
	}

	public void setWebsiteAddr(String websiteAddr) {
		this.websiteAddr = websiteAddr;
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
