package com.born.fcs.pm.ws.order.common;

import com.born.fcs.pm.ws.enums.bpm.BpmCallBackOpTypeEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * BPM后台处理回调
 * 
 * @author wuzj
 */
public class BpmCallBackOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 2571552045316270042L;
	
	/** 表单ID */
	private long formId;
	
	/** 当前节点ID(跳转到结束节点的时候才需要，其他情况不必传) */
	private String nodeId;
	
	/** 操作类型 */
	BpmCallBackOpTypeEnum opType;
	
	@Override
	public void check() {
		validateGreaterThan(formId, "表单ID");
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getNodeId() {
		return this.nodeId;
	}
	
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	public BpmCallBackOpTypeEnum getOpType() {
		return this.opType;
	}
	
	public void setOpType(BpmCallBackOpTypeEnum opType) {
		this.opType = opType;
	}
}
