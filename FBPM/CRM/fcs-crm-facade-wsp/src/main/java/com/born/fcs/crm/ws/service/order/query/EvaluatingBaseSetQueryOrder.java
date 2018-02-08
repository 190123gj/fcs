package com.born.fcs.crm.ws.service.order.query;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 基础指标配置查询
 * **/
public class EvaluatingBaseSetQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -6664669969192218619L;
	/** 1级指标id */
	private long level1Id;
	/** 2级指标id */
	private long level2Id;
	/** 3级指标id */
	private long level3Id;
	/** 4级指标id */
	private long level4Id;
	/** 该条指标等级 */
	private String level;
	/** 父类指标id */
	private long perentLevel;
	/** 指标类型 */
	private String type;
	/** 指标状态 */
	private String status;
	
	public long getLevel1Id() {
		return this.level1Id;
	}
	
	public void setLevel1Id(long level1Id) {
		this.level1Id = level1Id;
	}
	
	public long getLevel2Id() {
		return this.level2Id;
	}
	
	public void setLevel2Id(long level2Id) {
		this.level2Id = level2Id;
	}
	
	public long getLevel3Id() {
		return this.level3Id;
	}
	
	public void setLevel3Id(long level3Id) {
		this.level3Id = level3Id;
	}
	
	public long getLevel4Id() {
		return this.level4Id;
	}
	
	public void setLevel4Id(long level4Id) {
		this.level4Id = level4Id;
	}
	
	public String getLevel() {
		return this.level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public long getPerentLevel() {
		return this.perentLevel;
	}
	
	public void setPerentLevel(long perentLevel) {
		this.perentLevel = perentLevel;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
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
		builder.append("EvaluatingBaseSetQueryOrder [level1Id=");
		builder.append(level1Id);
		builder.append(", level2Id=");
		builder.append(level2Id);
		builder.append(", level3Id=");
		builder.append(level3Id);
		builder.append(", level4Id=");
		builder.append(level4Id);
		builder.append(", level=");
		builder.append(level);
		builder.append(", perentLevel=");
		builder.append(perentLevel);
		builder.append(", type=");
		builder.append(type);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
	
}
