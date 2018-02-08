package com.born.fcs.crm.ws.service.info;

import java.io.Serializable;

/** 部门业务区域 */
public class BusyRegionInfo implements Serializable {
	
	private static final long serialVersionUID = -5913862560569416523L;
	/** 部门名 */
	private String depName;
	/** 部门编号 */
	private String depPath;
	/** 区域编号 */
	private String code;
	/** 区域名 */
	private String name;
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
	
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
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
		builder.append("BusyRegionInfo [depName=");
		builder.append(depName);
		builder.append(", depPath=");
		builder.append(depPath);
		builder.append(", code=");
		builder.append(code);
		builder.append(", name=");
		builder.append(name);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
	
}
