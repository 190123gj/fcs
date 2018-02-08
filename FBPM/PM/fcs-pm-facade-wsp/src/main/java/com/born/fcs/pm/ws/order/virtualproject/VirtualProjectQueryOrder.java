package com.born.fcs.pm.ws.order.virtualproject;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.VirtualProjectStatusEnum;

/**
 * 虚拟项目
 *
 * @author wuzj
 */
public class VirtualProjectQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = 9975799048794960L;
	
	/** 主键 */
	private long virtualId;
	
	/** 状态 */
	private VirtualProjectStatusEnum status;
	
	public long getVirtualId() {
		return this.virtualId;
	}
	
	public void setVirtualId(long virtualId) {
		this.virtualId = virtualId;
	}
	
	public VirtualProjectStatusEnum getStatus() {
		return this.status;
	}
	
	public void setStatus(VirtualProjectStatusEnum status) {
		this.status = status;
	}
	
}
