package com.born.fcs.pm.ws.order.finvestigation;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.finvestigation.base.FInvestigationBaseOrder;
import com.born.fcs.pm.ws.order.finvestigation.declare.FInvestigationPersonOrder;
import com.yjf.common.lang.util.DateUtil;

/**
 * 
 * @author lirz
 * 
 * 2016-3-8 下午2:00:20
 */
public class FInvestigationOrder extends FInvestigationBaseOrder {
	
	private static final long serialVersionUID = -161309010463448792L;
	//主键ID
	private long investigateId;
	//业务类型
	private String busiType;
	//业务类型名称
	private String busiTypeName;
	//申明条件(YNYYY...)
	private String declares;
	//调查地点
	private String investigatePlace;
	//调查日期
	private Date investigateDate;
	//调查人员
	private String investigatePersion;
	//调查人员ID(多个以逗号分隔)
	private String investigatePersionId;
	//客户接待的人员
	private String receptionPersion;
	//客户接待的人员的职务
	private String receptionDuty;
	private String review; //复议标识
	private String version ; //尽调版本
	private List<FInvestigationPersonOrder> persons; //调查情况中参与人员
	
	private long copiedFormId; //被复制的表单ID
	
	public long getInvestigateId() {
		return investigateId;
	}
	
	public void setInvestigateId(long investigateId) {
		this.investigateId = investigateId;
	}
	
	public String getBusiType() {
		return busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public String getDeclares() {
		return declares;
	}
	
	public void setDeclares(String declares) {
		this.declares = declares;
	}
	
	public String getInvestigatePlace() {
		return investigatePlace;
	}
	
	public void setInvestigatePlace(String investigatePlace) {
		this.investigatePlace = investigatePlace;
	}
	
	public Date getInvestigateDate() {
		return investigateDate;
	}
	
	public void setInvestigateDate(Date investigateDate) {
		this.investigateDate = investigateDate;
	}
	
	public void setInvestigateDateStr(String investigateDateStr) {
		this.investigateDate = DateUtil.strToDtSimpleFormat(investigateDateStr);
	}
	
	public String getInvestigatePersion() {
		return investigatePersion;
	}
	
	public void setInvestigatePersion(String investigatePersion) {
		this.investigatePersion = investigatePersion;
	}
	
	public String getInvestigatePersionId() {
		return investigatePersionId;
	}
	
	public void setInvestigatePersionId(String investigatePersionId) {
		this.investigatePersionId = investigatePersionId;
	}
	
	public String getReceptionPersion() {
		return receptionPersion;
	}
	
	public void setReceptionPersion(String receptionPersion) {
		this.receptionPersion = receptionPersion;
	}
	
	public String getReceptionDuty() {
		return receptionDuty;
	}
	
	public void setReceptionDuty(String receptionDuty) {
		this.receptionDuty = receptionDuty;
	}
	
	public String getReview() {
		return review;
	}
	
	public void setReview(String review) {
		this.review = review;
	}
	
	public List<FInvestigationPersonOrder> getPersons() {
		return this.persons;
	}
	
	public void setPersons(List<FInvestigationPersonOrder> persons) {
		this.persons = persons;
	}
	
	public long getCopiedFormId() {
		return this.copiedFormId;
	}
	
	public void setCopiedFormId(long copiedFormId) {
		this.copiedFormId = copiedFormId;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
