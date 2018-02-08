package com.born.fcs.pm.ws.order.fund;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 退费申请Order
 *
 *
 * @author Ji
 *
 */
public class FRefundApplicationOrder extends FormOrderBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2497050507698665497L;
	
	private Long refundId;
	
	private String projectCode;
	
	private String projectName;
	
	private String attach;
	
	private String remark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private List<FRefundApplicationFeeOrder> feeOrders;
	
	public Long getRefundId() {
		return refundId;
	}
	
	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getAttach() {
		return attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
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
	
	public List<FRefundApplicationFeeOrder> getFeeOrders() {
		return feeOrders;
	}
	
	public void setFeeOrders(List<FRefundApplicationFeeOrder> feeOrders) {
		this.feeOrders = feeOrders;
	}
	
}
