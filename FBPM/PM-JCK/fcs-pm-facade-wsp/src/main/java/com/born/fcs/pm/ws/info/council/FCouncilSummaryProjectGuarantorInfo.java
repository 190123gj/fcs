package com.born.fcs.pm.ws.info.council;

import java.util.Date;

import com.born.fcs.pm.ws.enums.GuarantorTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 会议纪要 - 项目评审会 - 授信条件 - 保证人
 * 
 * @author wuzj
 * 
 */
public class FCouncilSummaryProjectGuarantorInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 5865551713760376884L;
	/** 主键 */
	private long id;
	/** 会议纪要对应项目ID */
	private long spId;
	/** 保证类型 */
	private GuarantorTypeEnum guarantorType;
	/** 保证人 */
	private String guarantor;
	/** 保证额度 */
	private Money guaranteeAmount = new Money(0, 0);
	/** 保证方式 */
	private String guaranteeWay;
	/** 排序 */
	private int sortOrder;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getSpId() {
		return this.spId;
	}
	
	public void setSpId(long spId) {
		this.spId = spId;
	}
	
	public GuarantorTypeEnum getGuarantorType() {
		return this.guarantorType;
	}
	
	public void setGuarantorType(GuarantorTypeEnum guarantorType) {
		this.guarantorType = guarantorType;
	}
	
	public String getGuarantor() {
		return this.guarantor;
	}
	
	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}
	
	public Money getGuaranteeAmount() {
		return this.guaranteeAmount;
	}
	
	public void setGuaranteeAmount(Money guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}
	
	public String getGuaranteeWay() {
		return this.guaranteeWay;
	}
	
	public void setGuaranteeWay(String guaranteeWay) {
		this.guaranteeWay = guaranteeWay;
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
