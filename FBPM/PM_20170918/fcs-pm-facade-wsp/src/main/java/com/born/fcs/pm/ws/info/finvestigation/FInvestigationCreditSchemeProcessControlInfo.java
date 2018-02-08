package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;

import com.born.fcs.pm.ws.enums.ProcessControlEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 尽职调查报告 - 授信方案 - 过程控制(客户主体评级/资产负债率/其他)
 * 
 * @author lirz
 * 
 * 2016-3-21 下午4:02:30
 */
public class FInvestigationCreditSchemeProcessControlInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -8356231009370714216L;
	
	private long id; //主键
	private long formId;
	private ProcessControlEnum type; //所属过程控制
	private String upRate;
	private String upBp;
	private String downRate;
	private String downBp;
	private String content;
	private int sortOrder;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public ProcessControlEnum getType() {
		return this.type;
	}
	
	public void setType(ProcessControlEnum type) {
		this.type = type;
	}
	
	public String getUpRate() {
		return this.upRate;
	}
	
	public void setUpRate(String upRate) {
		this.upRate = upRate;
	}
	
	public String getUpBp() {
		return this.upBp;
	}
	
	public void setUpBp(String upBp) {
		this.upBp = upBp;
	}
	
	public String getDownRate() {
		return this.downRate;
	}
	
	public void setDownRate(String downRate) {
		this.downRate = downRate;
	}
	
	public String getDownBp() {
		return this.downBp;
	}
	
	public void setDownBp(String downBp) {
		this.downBp = downBp;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
