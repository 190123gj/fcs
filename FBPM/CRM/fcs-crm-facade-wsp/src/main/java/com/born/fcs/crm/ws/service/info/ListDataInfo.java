package com.born.fcs.crm.ws.service.info;

import java.io.Serializable;
import java.util.Date;

/**
 * 集合类信息存储
 * */
public class ListDataInfo implements Serializable {
	private static final long serialVersionUID = 8495626366707761143L;
	/** 主键 */
	private long id;
	/** 类型 */
	private String dataType;
	/** 描述该集合用途 */
	private String description;
	/** String类型1 */
	private String str1;
	/** String类型2 */
	private String str2;
	/** String类型3 */
	private String str3;
	/** String类型4 */
	private String str4;
	/** String类型5 */
	private String str5;
	/** String类型6 */
	private String str6;
	/** String类型7 */
	private String str7;
	/** String类型8 */
	private String str8;
	/** String类型9 */
	private String integer1;
	/** String类型10 */
	private String double1;
	/** 创建时间 */
	private Date rawAddTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getDataType() {
		return this.dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getStr1() {
		return this.str1;
	}
	
	public void setStr1(String str1) {
		this.str1 = str1;
	}
	
	public String getStr2() {
		return this.str2;
	}
	
	public void setStr2(String str2) {
		this.str2 = str2;
	}
	
	public String getStr3() {
		return this.str3;
	}
	
	public void setStr3(String str3) {
		this.str3 = str3;
	}
	
	public String getStr4() {
		return this.str4;
	}
	
	public void setStr4(String str4) {
		this.str4 = str4;
	}
	
	public String getStr5() {
		return this.str5;
	}
	
	public void setStr5(String str5) {
		this.str5 = str5;
	}
	
	public String getStr6() {
		return this.str6;
	}
	
	public void setStr6(String str6) {
		this.str6 = str6;
	}
	
	public String getStr7() {
		return this.str7;
	}
	
	public void setStr7(String str7) {
		this.str7 = str7;
	}
	
	public String getStr8() {
		return this.str8;
	}
	
	public void setStr8(String str8) {
		this.str8 = str8;
	}
	
	public String getInteger1() {
		return this.integer1;
	}
	
	public void setInteger1(String integer1) {
		this.integer1 = integer1;
	}
	
	public String getDouble1() {
		return this.double1;
	}
	
	public void setDouble1(String double1) {
		this.double1 = double1;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
}
