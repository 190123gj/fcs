package com.born.fcs.pm.ws.order.capitalappropriationapply;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 资金划付申请Order
 *
 *
 * @author Ji
 *
 */
public class FCapitalAppropriationApplyOrder extends FormOrderBase {
	
	private static final long serialVersionUID = 55888928329956547L;
	
	private Long applyId;
	
	private long outBizNo;
	
	private String projectCode;
	
	private String projectName;
	
	private String projectType;
	
	private String attach;
	
	private String remark;
	
	/**
	 * 简单流程（IS/NO）
	 */
	private String isSimple;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private List<FCapitalAppropriationApplyFeeOrder> fCapitalAppropriationApplyFeeOrders;
	
	private List<FCapitalAppropriationApplyTransferOrder> transferOrder;
	
	private FCapitalAppropriationApplyPayeeOrder payeeOrder;
	
	public Long getApplyId() {
		return applyId;
	}
	
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
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
	
	public String getProjectType() {
		return projectType;
	}
	
	public void setProjectType(String projectType) {
		this.projectType = projectType;
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
	
	public String getIsSimple() {
		return this.isSimple;
	}
	
	public void setIsSimple(String isSimple) {
		this.isSimple = isSimple;
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
	
	public List<FCapitalAppropriationApplyFeeOrder> getfCapitalAppropriationApplyFeeOrders() {
		return fCapitalAppropriationApplyFeeOrders;
	}
	
	public void setfCapitalAppropriationApplyFeeOrders(	List<FCapitalAppropriationApplyFeeOrder> fCapitalAppropriationApplyFeeOrders) {
		this.fCapitalAppropriationApplyFeeOrders = fCapitalAppropriationApplyFeeOrders;
	}
	
	public List<FCapitalAppropriationApplyTransferOrder> getTransferOrder() {
		return this.transferOrder;
	}
	
	public void setTransferOrder(List<FCapitalAppropriationApplyTransferOrder> transferOrder) {
		this.transferOrder = transferOrder;
	}
	
	public long getOutBizNo() {
		return outBizNo;
	}
	
	public void setOutBizNo(long outBizNo) {
		this.outBizNo = outBizNo;
	}
	
	public FCapitalAppropriationApplyPayeeOrder getPayeeOrder() {
		return payeeOrder;
	}
	
	public void setPayeeOrder(FCapitalAppropriationApplyPayeeOrder payeeOrder) {
		this.payeeOrder = payeeOrder;
	}
	
}
