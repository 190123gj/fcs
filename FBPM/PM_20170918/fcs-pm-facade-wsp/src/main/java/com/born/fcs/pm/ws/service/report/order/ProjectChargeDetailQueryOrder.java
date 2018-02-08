package com.born.fcs.pm.ws.service.report.order;

import java.util.Date;
import java.util.List;

/**
 * 项目收费明细
 * @author wuzj
 */
public class ProjectChargeDetailQueryOrder extends ProjectReportQueryOrder {
	
	private static final long serialVersionUID = -8649369642873587567L;
	
	/** 收费开始时间 */
	private Date chargeTimeStart;
	/** 收费结束时间 */
	private Date chargeTimeEnd;
	/** 收费种类 */
	private List<String> feeTypeList;
	
	public Date getChargeTimeStart() {
		return this.chargeTimeStart;
	}
	
	public void setChargeTimeStart(Date chargeTimeStart) {
		this.chargeTimeStart = chargeTimeStart;
	}
	
	public Date getChargeTimeEnd() {
		return this.chargeTimeEnd;
	}
	
	public void setChargeTimeEnd(Date chargeTimeEnd) {
		this.chargeTimeEnd = chargeTimeEnd;
	}
	
	public List<String> getFeeTypeList() {
		return this.feeTypeList;
	}
	
	public void setFeeTypeList(List<String> feeTypeList) {
		this.feeTypeList = feeTypeList;
	}
	
}
