package com.born.fcs.pm.ws.order.capitalappropriationapply;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.FcsQueryPageBase;

/**
 * 资金划付申请-资金划付信息查询Order
 * @author Ji
 */
public class FCapitalAppropriationApplyFeeQueryOrder extends FcsQueryPageBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1009061127315569756L;
	
	private Long id;
	
	private long applyId;
	
	private String appropriateReason;
	
	private String remark;
	
	private Date rawUpdateTime;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public long getApplyId() {
		return applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public String getAppropriateReason() {
		return appropriateReason;
	}
	
	public void setAppropriateReason(String appropriateReason) {
		this.appropriateReason = appropriateReason;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
