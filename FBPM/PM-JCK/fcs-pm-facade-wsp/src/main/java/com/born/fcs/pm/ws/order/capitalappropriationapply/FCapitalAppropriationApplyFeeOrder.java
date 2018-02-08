package com.born.fcs.pm.ws.order.capitalappropriationapply;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 资金划付申请-资金划付信息Order
 *
 *
 * @author Ji
 *
 */
public class FCapitalAppropriationApplyFeeOrder extends FormOrderBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4963726382598313149L;
	
	private Long id;
	
	private long applyId;
	
	private Double appropriateAmount;
	
	private String appropriateReason;
	
	private String remark;
	
	private Date rawAddTime;
	
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
	
	public Double getAppropriateAmount() {
		return appropriateAmount;
	}
	
	public void setAppropriateAmount(Double appropriateAmount) {
		this.appropriateAmount = appropriateAmount;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
}
