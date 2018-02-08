package com.born.fcs.pm.ws.order.virtualproject;

import org.springframework.util.Assert;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 虚拟项目
 *
 * @author wuzj
 */
public class VirtualProjectDeleteOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 4785589931970441121L;
	
	/** 主键 */
	private long virtualId;
	
	/** 虚拟项目编号 */
	private String projectCode;
	
	@Override
	public void check() {
		Assert.isTrue(virtualId > 0 || (projectCode != null && !"".equals(projectCode)),
			"虚拟项目编号或单据ID不能为空");
	}
	
	public long getVirtualId() {
		return this.virtualId;
	}
	
	public void setVirtualId(long virtualId) {
		this.virtualId = virtualId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
}
