package com.born.fcs.crm.ws.service.order;

import java.util.Date;
import java.util.List;

import com.born.fcs.crm.ws.service.info.ListDataInfo;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 渠道增加，修改
 * **/
public class ChannalOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -2314759059878917568L;
	/** 联系人 */
	private List<ListDataInfo> listData;
	/** 主键 */
	private long id;
	/** 渠道编号 */
	private String channelCode;
	/** 渠道名称 */
	private String channelName;
	/** 渠道分类 */
	private String channelType;
	/** 金融机构属性 */
	private String institutionalAttributes;
	/** 联系地址 */
	private String address;
	/** 联系人1 */
	private String contactPerson1;
	/** 联系人1电话 */
	private String contactMobile1;
	/** 联系人2 */
	private String contactPerson2;
	/** 联系人2电话 */
	private String contactMobile2;
	/** 牵头行 */
	private String leadings;
	/** 损失分摊比例 */
	private double lossAllocationRate;
	/** 保证金比例 */
	private double bondRate;
	/** 授信额度 */
	private Money creditAmount = new Money(0, 0);
	/** 判断授信额度 */
	private String isCreditAmount;
	/** 单笔限额 */
	private Money singleLimit = new Money(0, 0);
	/** 判断单笔限额 */
	private String isSingleLimit;
	/** 不超过净资产的几倍 */
	private double times;
	/** 判断倍数 */
	private String isTimes;
	/** 不超过净资产的百分比 */
	private double percent;
	/** 判断百分比 */
	private String isPercent;
	/** 授信起始日 */
	private Date creditStartDate;
	/** 授信截止日 */
	private Date creditEndDate;
	/** 代偿期限:天 */
	private int compensatoryLimit;
	/** 自然日,工作日 */
	private String dayType;
	/** 是否跨年 */
	private String straddleYear;
	/** 附件链接 */
	private String enclosureUrl;
	/** 渠道状态 */
	private String status;
	/** 创建时间 */
	private Date rawAddTime;
	/** 更新时间 */
	private Date rawUpdateTime;
	/** 录入人 */
	private String inputPerson;
	/** 授信到期是否已提醒 */
	private String isRemind = BooleanEnum.NO.code();
	/** 历史数据 */
	private String isHistory = BooleanEnum.NO.code();
	/** 是否续签数据 */
	private String isXuQian = BooleanEnum.NO.code();
	/** 是否暂存 IS/ON 如果是保存，则渠道号相同的其他数据变成历史数据 */
	private String isTemporary;
	/** 关联的合同号 */
	private String contractNo;
	
	@Override
	public void check() {
		validateHasText(channelCode, "渠道编号");
		validateHasText(channelName, "渠道名称");
	}
	
	public String getContractNo() {
		return this.contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public String getIsHistory() {
		return this.isHistory;
	}
	
	public void setIsHistory(String isHistory) {
		this.isHistory = isHistory;
	}
	
	public String getIsTemporary() {
		return this.isTemporary;
	}
	
	public void setIsTemporary(String isTemporary) {
		this.isTemporary = isTemporary;
	}
	
	public void setIsXuQian(String isXuQian) {
		this.isXuQian = isXuQian;
	}
	
	public String getInputPerson() {
		return this.inputPerson;
	}
	
	public void setInputPerson(String inputPerson) {
		this.inputPerson = inputPerson;
	}
	
	public String getIsRemind() {
		return this.isRemind;
	}
	
	public String getIsXuQian() {
		return this.isXuQian;
	}
	
	public void setIsRemind(String isRemind) {
		this.isRemind = isRemind;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getChannelCode() {
		return this.channelCode;
	}
	
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	public String getChannelName() {
		return this.channelName;
	}
	
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	public String getChannelType() {
		return this.channelType;
	}
	
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	
	public String getInstitutionalAttributes() {
		return this.institutionalAttributes;
	}
	
	public void setInstitutionalAttributes(String institutionalAttributes) {
		this.institutionalAttributes = institutionalAttributes;
	}
	
	public void setCreditAmount(Money creditAmount) {
		this.creditAmount = creditAmount;
	}
	
	public void setSingleLimit(Money singleLimit) {
		this.singleLimit = singleLimit;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getLeadings() {
		return this.leadings;
	}
	
	public void setLeadings(String leadings) {
		this.leadings = leadings;
	}
	
	public double getLossAllocationRate() {
		return this.lossAllocationRate;
	}
	
	public void setLossAllocationRate(double lossAllocationRate) {
		this.lossAllocationRate = lossAllocationRate;
	}
	
	public double getBondRate() {
		return this.bondRate;
	}
	
	public void setBondRate(double bondRate) {
		this.bondRate = bondRate;
	}
	
	public String getIsCreditAmount() {
		return this.isCreditAmount;
	}
	
	public void setIsCreditAmount(String isCreditAmount) {
		this.isCreditAmount = isCreditAmount;
	}
	
	public String getIsSingleLimit() {
		return this.isSingleLimit;
	}
	
	public void setIsSingleLimit(String isSingleLimit) {
		this.isSingleLimit = isSingleLimit;
	}
	
	public double getTimes() {
		return this.times;
	}
	
	public void setTimes(double times) {
		this.times = times;
	}
	
	public String getIsTimes() {
		return this.isTimes;
	}
	
	public void setIsTimes(String isTimes) {
		this.isTimes = isTimes;
	}
	
	public double getPercent() {
		return this.percent;
	}
	
	public void setPercent(double percent) {
		this.percent = percent;
	}
	
	public String getIsPercent() {
		return this.isPercent;
	}
	
	public void setIsPercent(String isPercent) {
		this.isPercent = isPercent;
	}
	
	public Date getCreditStartDate() {
		return this.creditStartDate;
	}
	
	public void setCreditStartDate(Date creditStartDate) {
		this.creditStartDate = creditStartDate;
	}
	
	public Date getCreditEndDate() {
		return this.creditEndDate;
	}
	
	public void setCreditEndDate(Date creditEndDate) {
		this.creditEndDate = creditEndDate;
	}
	
	public int getCompensatoryLimit() {
		return this.compensatoryLimit;
	}
	
	public void setCompensatoryLimit(int compensatoryLimit) {
		this.compensatoryLimit = compensatoryLimit;
	}
	
	public String getDayType() {
		return this.dayType;
	}
	
	public void setDayType(String dayType) {
		this.dayType = dayType;
	}
	
	public String getStraddleYear() {
		return this.straddleYear;
	}
	
	public void setStraddleYear(String straddleYear) {
		this.straddleYear = straddleYear;
	}
	
	public String getEnclosureUrl() {
		return this.enclosureUrl;
	}
	
	public void setEnclosureUrl(String enclosureUrl) {
		this.enclosureUrl = enclosureUrl;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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
	
	public Money getCreditAmount() {
		return this.creditAmount;
	}
	
	public Money getSingleLimit() {
		return this.singleLimit;
	}
	
	public String getContactPerson1() {
		return this.contactPerson1;
	}
	
	public void setContactPerson1(String contactPerson1) {
		this.contactPerson1 = contactPerson1;
	}
	
	public String getContactMobile1() {
		return this.contactMobile1;
	}
	
	public void setContactMobile1(String contactMobile1) {
		this.contactMobile1 = contactMobile1;
	}
	
	public String getContactPerson2() {
		return this.contactPerson2;
	}
	
	public void setContactPerson2(String contactPerson2) {
		this.contactPerson2 = contactPerson2;
	}
	
	public String getContactMobile2() {
		return this.contactMobile2;
	}
	
	public void setContactMobile2(String contactMobile2) {
		this.contactMobile2 = contactMobile2;
	}
	
	public List<ListDataInfo> getListData() {
		return this.listData;
	}
	
	public void setListData(List<ListDataInfo> listData) {
		this.listData = listData;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChannalOrder [listData=");
		builder.append(listData);
		builder.append(", id=");
		builder.append(id);
		builder.append(", channelCode=");
		builder.append(channelCode);
		builder.append(", channelName=");
		builder.append(channelName);
		builder.append(", channelType=");
		builder.append(channelType);
		builder.append(", institutionalAttributes=");
		builder.append(institutionalAttributes);
		builder.append(", address=");
		builder.append(address);
		builder.append(", contactPerson1=");
		builder.append(contactPerson1);
		builder.append(", contactMobile1=");
		builder.append(contactMobile1);
		builder.append(", contactPerson2=");
		builder.append(contactPerson2);
		builder.append(", contactMobile2=");
		builder.append(contactMobile2);
		builder.append(", leadings=");
		builder.append(leadings);
		builder.append(", lossAllocationRate=");
		builder.append(lossAllocationRate);
		builder.append(", bondRate=");
		builder.append(bondRate);
		builder.append(", creditAmount=");
		builder.append(creditAmount);
		builder.append(", isCreditAmount=");
		builder.append(isCreditAmount);
		builder.append(", singleLimit=");
		builder.append(singleLimit);
		builder.append(", isSingleLimit=");
		builder.append(isSingleLimit);
		builder.append(", times=");
		builder.append(times);
		builder.append(", isTimes=");
		builder.append(isTimes);
		builder.append(", percent=");
		builder.append(percent);
		builder.append(", isPercent=");
		builder.append(isPercent);
		builder.append(", creditStartDate=");
		builder.append(creditStartDate);
		builder.append(", creditEndDate=");
		builder.append(creditEndDate);
		builder.append(", compensatoryLimit=");
		builder.append(compensatoryLimit);
		builder.append(", dayType=");
		builder.append(dayType);
		builder.append(", straddleYear=");
		builder.append(straddleYear);
		builder.append(", enclosureUrl=");
		builder.append(enclosureUrl);
		builder.append(", status=");
		builder.append(status);
		builder.append(", rawAddTime=");
		builder.append(rawAddTime);
		builder.append(", rawUpdateTime=");
		builder.append(rawUpdateTime);
		builder.append(", inputPerson=");
		builder.append(inputPerson);
		builder.append(", isRemind=");
		builder.append(isRemind);
		builder.append(", isHistory=");
		builder.append(isHistory);
		builder.append(", isXuQian=");
		builder.append(isXuQian);
		builder.append(", isTemporary=");
		builder.append(isTemporary);
		builder.append(", contractNo=");
		builder.append(contractNo);
		builder.append("]");
		return builder.toString();
	}
	
}
