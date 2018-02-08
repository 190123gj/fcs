package com.born.fcs.pm.ws.order.transfer;

import java.util.List;

import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 项目移交(移交到法务部)Order
 * @author wuzj
 */
public class ProjectTransferOrder extends FormOrderBase {
	
	private static final long serialVersionUID = 2336921184894046127L;
	
	/** 移交类型 */
	//private ProjectTransferTypeEnum transferType;
	
	/** 详细信息 */
	private List<ProjectTransferDetailOrder> detailOrder;
	
	@Override
	public void check() {
		//validateNotNull(transferType, "移交类型");
		validateNotNull(detailOrder, "移交明细");
	}
	
	//	public ProjectTransferTypeEnum getTransferType() {
	//		return this.transferType;
	//	}
	//	
	//	public void setTransferType(ProjectTransferTypeEnum transferType) {
	//		this.transferType = transferType;
	//	}
	
	public List<ProjectTransferDetailOrder> getDetailOrder() {
		return this.detailOrder;
	}
	
	public void setDetailOrder(List<ProjectTransferDetailOrder> detailOrder) {
		this.detailOrder = detailOrder;
	}
	
}
