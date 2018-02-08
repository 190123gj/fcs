package com.born.fcs.pm.ws.order.fund;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.FcsQueryPageBase;

/**
 * 退费申请-退费信息查询Order
 * @author Ji
 */
public class FRefundApplicationFeeQueryOrder extends FcsQueryPageBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5551757277658771174L;
	
	private Long id;
	
	private long refundId;
	
	private String refundReason;
	
	private String remark;
	
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
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
