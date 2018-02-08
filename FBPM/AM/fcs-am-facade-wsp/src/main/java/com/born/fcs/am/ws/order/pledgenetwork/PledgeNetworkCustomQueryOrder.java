/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.ws.order.pledgenetwork;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 抵质押品-网络信息-自定义Order
 *
 * @author jil
 *
 */
public class PledgeNetworkCustomQueryOrder extends QueryPageBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6744020142207248192L;

	private long networkId;

	private long typeId;

	private String websiteName;

	private String websiteAddr;

	private Date rawAddTime;

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

}
