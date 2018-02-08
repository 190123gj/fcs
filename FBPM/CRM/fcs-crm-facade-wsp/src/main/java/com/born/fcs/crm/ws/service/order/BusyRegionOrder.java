package com.born.fcs.crm.ws.service.order;

import java.util.List;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

public class BusyRegionOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 2763032853808303206L;
	
	/** 部门名 */
	private String depName;
	/** 部门编号 */
	private String depPath;
	/** 区域启用状态 */
	private String status;
	/** 区域 */
	private List<region> region;
	
	@Override
	public void check() {
		validateHasText(depPath, "部门编号");
	}
	
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
	
	public List<region> getRegion() {
		return this.region;
	}
	
	public void setRegion(List<region> region) {
		this.region = region;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BusyRegionOrder [depName=");
		builder.append(depName);
		builder.append(", depPath=");
		builder.append(depPath);
		builder.append(", status=");
		builder.append(status);
		builder.append(", region=");
		builder.append(region);
		builder.append("]");
		return builder.toString();
	}
	
	public static class region {
		/** 区域编号 */
		private String code;
		/** 区域名 */
		private String name;
		
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
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("region [code=");
			builder.append(code);
			builder.append(", name=");
			builder.append(name);
			builder.append("]");
			return builder.toString();
		}
		
	}
	
}
