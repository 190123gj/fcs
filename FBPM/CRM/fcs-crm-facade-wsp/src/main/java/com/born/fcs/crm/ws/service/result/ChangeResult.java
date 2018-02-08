package com.born.fcs.crm.ws.service.result;

import java.util.List;

import com.born.fcs.crm.ws.service.order.ChangeDetailOrder;
import com.yjf.common.lang.result.ResultBase;

public class ChangeResult extends ResultBase {
	
	private static final long serialVersionUID = 23942869524259673L;
	/** 修改记录 */
	private List<ChangeDetailOrder> changeOrder;
	
	public List<ChangeDetailOrder> getChangeOrder() {
		return this.changeOrder;
	}
	
	public void setChangeOrder(List<ChangeDetailOrder> changeOrder) {
		this.changeOrder = changeOrder;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChangeResult [changeOrder=");
		builder.append(changeOrder);
		builder.append("]");
		return builder.toString();
	}
	
}
