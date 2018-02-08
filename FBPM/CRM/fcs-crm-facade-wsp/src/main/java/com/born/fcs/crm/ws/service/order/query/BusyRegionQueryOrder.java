package com.born.fcs.crm.ws.service.order.query;

import com.born.fcs.pm.ws.base.QueryPageBase;

public class BusyRegionQueryOrder extends QueryPageBase {
	private static final long serialVersionUID = 8802932294019575813L;
	/** 部门名 */
	private String depName;
	/** 部门编号 */
	private String depPath;
	/** 区域启用状态 */
	private String status;
	
	public String getDepName() {
		return this.depName;
	}
	
	public void setDepName(String depName) {
		this.depName = depName;
	}
	
	public String getDepPath() {
		return this.depPath;
	}
	
	public void setDepPath(String depPath) {
		this.depPath = depPath;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BusyRegionQueryOrder [depName=");
		builder.append(depName);
		builder.append(", depPath=");
		builder.append(depPath);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
	
}
