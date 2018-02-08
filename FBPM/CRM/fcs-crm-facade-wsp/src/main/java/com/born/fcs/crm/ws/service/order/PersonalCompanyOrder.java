package com.born.fcs.crm.ws.service.order;

import java.util.Date;

import com.yjf.common.lang.util.money.Money;

/**
 * 个人客户：公司增加，修改 Order
 * **/
public class PersonalCompanyOrder {
	
	/** 主键Id */
	private Long id;
	/** 用户查询Id */
	private String customerId;
	/** 公司名称 */
	private String perCompany;
	/** 注册资本 */
	private Money perRegistAmount = Money.zero();
	/** 注册资本提交数据用 */
	private String perRegistAmountString;
	/** 注册时间 */
	private Date perRegistDate;
	/** 注册时间 */
	private String perRegistDateString;
	/** 联系人 */
	private String perLinkPerson;
	/** 经营地址 */
	private String perAddress;
	/** 联系电话 */
	private String perLinkMobile;
	/** 备注 */
	private String perMemo;
	/** 存储状态 */
	private String perStatus;
	//	/** 正式数据id */
	//	private Long perentId;
	//	/** 暂存数据 */
	//	private Long childId;
	/** 是否暂存数据 */
	private String perIsTemporary;
	/** 创建时间 */
	private String rawAddTime;
	/** 更新时间 */
	private String rawUpdateTime;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getPerCompany() {
		return this.perCompany;
	}
	
	public void setPerCompany(String perCompany) {
		this.perCompany = perCompany;
	}
	
	public Money getPerRegistAmount() {
		return this.perRegistAmount;
	}
	
	public void setPerRegistAmount(Money perRegistAmount) {
		this.perRegistAmount = perRegistAmount;
	}
	
	public String getPerRegistAmountString() {
		return this.perRegistAmountString;
	}
	
	public void setPerRegistAmountString(String perRegistAmountString) {
		this.perRegistAmountString = perRegistAmountString;
	}
	
	public Date getPerRegistDate() {
		return this.perRegistDate;
	}
	
	public void setPerRegistDate(Date perRegistDate) {
		this.perRegistDate = perRegistDate;
	}
	
	public String getPerRegistDateString() {
		return this.perRegistDateString;
	}
	
	public void setPerRegistDateString(String perRegistDateString) {
		this.perRegistDateString = perRegistDateString;
	}
	
	public String getPerLinkPerson() {
		return this.perLinkPerson;
	}
	
	public void setPerLinkPerson(String perLinkPerson) {
		this.perLinkPerson = perLinkPerson;
	}
	
	public String getPerAddress() {
		return this.perAddress;
	}
	
	public void setPerAddress(String perAddress) {
		this.perAddress = perAddress;
	}
	
	public String getPerLinkMobile() {
		return this.perLinkMobile;
	}
	
	public void setPerLinkMobile(String perLinkMobile) {
		this.perLinkMobile = perLinkMobile;
	}
	
	public String getPerMemo() {
		return this.perMemo;
	}
	
	public void setPerMemo(String perMemo) {
		this.perMemo = perMemo;
	}
	
	public String getPerStatus() {
		return this.perStatus;
	}
	
	public void setPerStatus(String perStatus) {
		this.perStatus = perStatus;
	}
	
	//	public long getPerentId() {
	//		return this.perentId;
	//	}
	//	
	//	public void setPerentId(Long perentId) {
	//		this.perentId = perentId;
	//	}
	//	
	//	public long getChildId() {
	//		return this.childId;
	//	}
	//	
	//	public void setChildId(Long childId) {
	//		this.childId = childId;
	//	}
	
	public String getPerIsTemporary() {
		return this.perIsTemporary;
	}
	
	public void setPerIsTemporary(String perIsTemporary) {
		this.perIsTemporary = perIsTemporary;
	}
	
	public String getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(String rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public String getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(String rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PersonalCompanyOrder [id=");
		builder.append(id);
		builder.append(", customerId=");
		builder.append(customerId);
		builder.append(", perCompany=");
		builder.append(perCompany);
		builder.append(", perRegistAmount=");
		builder.append(perRegistAmount);
		builder.append(", perRegistAmountString=");
		builder.append(perRegistAmountString);
		builder.append(", perRegistDate=");
		builder.append(perRegistDate);
		builder.append(", perRegistDateString=");
		builder.append(perRegistDateString);
		builder.append(", perLinkPerson=");
		builder.append(perLinkPerson);
		builder.append(", perAddress=");
		builder.append(perAddress);
		builder.append(", perLinkMobile=");
		builder.append(perLinkMobile);
		builder.append(", perMemo=");
		builder.append(perMemo);
		builder.append(", perStatus=");
		builder.append(perStatus);
		builder.append(", perIsTemporary=");
		builder.append(perIsTemporary);
		builder.append(", rawAddTime=");
		builder.append(rawAddTime);
		builder.append(", rawUpdateTime=");
		builder.append(rawUpdateTime);
		builder.append("]");
		return builder.toString();
	}
	
}
