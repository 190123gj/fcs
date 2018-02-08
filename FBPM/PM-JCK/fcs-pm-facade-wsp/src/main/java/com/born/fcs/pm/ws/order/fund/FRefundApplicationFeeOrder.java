package com.born.fcs.pm.ws.order.fund;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 退费申请-退费信息Order
 *
 *
 * @author Ji
 *
 */
public class FRefundApplicationFeeOrder extends FormOrderBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3063415186297564191L;
	
	private Long id;
	
	private long refundId;
	
	private Double refundAmount;
	
	private String refundReason;
	
	private String remark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public long getRefundId() {
		return refundId;
	}
	
	public void setRefundId(long refundId) {
		this.refundId = refundId;
	}
	
	public Double getRefundAmount() {
		return refundAmount;
	}
	
	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}
	
	public String getRefundReason() {
		return refundReason;
	}
	
	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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
