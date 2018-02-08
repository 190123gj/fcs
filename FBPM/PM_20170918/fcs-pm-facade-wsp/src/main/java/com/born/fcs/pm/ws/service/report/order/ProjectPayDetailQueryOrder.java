package com.born.fcs.pm.ws.service.report.order;

import java.util.Date;
import java.util.List;

/**
 * 项目划付明细
 * @author wuzj
 */
public class ProjectPayDetailQueryOrder extends ProjectReportQueryOrder {
	
	private static final long serialVersionUID = 431552772845906365L;
	
	/** 划付开始时间 */
	private Date payTimeStart;
	/** 划付结束时间 */
	private Date payTimeEnd;
	/** 收费种类 */
	private List<String> feeTypeList;
	
	public Date getPayTimeStart() {
		return this.payTimeStart;
	}
	
	public void setPayTimeStart(Date payTimeStart) {
		this.payTimeStart = payTimeStart;
	}
	
	public Date getPayTimeEnd() {
		return this.payTimeEnd;
	}
	
	public void setPayTimeEnd(Date payTimeEnd) {
		this.payTimeEnd = payTimeEnd;
	}
	
	public List<String> getFeeTypeList() {
		return this.feeTypeList;
	}
	
	public void setFeeTypeList(List<String> feeTypeList) {
		this.feeTypeList = feeTypeList;
	}
	
}
