package com.born.fcs.pm.ws.info.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

public class FProjectFinancialTansferApplyInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -1570285959770681738L;
	
	/** 申请ID */
	private long applyId;
	/** 表单ID */
	private long formId;
	/** 项目编号 */
	private String projectCode;
	/** 申请时持有数 */
	private long holdNum;
	/** 转让中数量 */
	private long transferingNum;
	/** 赎回数量 */
	private long redeemingNum;
	/** 转让单价 */
	private long transferPrice;
	/** 转让数量 */
	private long transferNum;
	/** 转让开始时间 */
	private Date transferTimeStart;
	/** 转让结束时间 */
	private Date transferTimeEnd;
	/** 转让原因 */
	private String transferReason;
	/** 附件 */
	private String attach;
	/** 是否过户 */
	private BooleanEnum isTransferOwnership;
	/** 是否回购 */
	private BooleanEnum isBuyBack;
	/** 回购单价 */
	private Money buyBackPrice = new Money(0, 0);
	/** 回购时间 */
	private Date buyBackTime;
	/** 上会申请ID */
	private long councilApplyId;
	/** 上会类型 */
	private ProjectCouncilEnum councilType;
	/** 上会状态 */
	private ProjectCouncilStatusEnum councilStatus;
	/** 新增时间 */
	private Date rawAddTime;
	/** 更新时间 */
	private Date rawUpdateTime;
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public long getHoldNum() {
		return this.holdNum;
	}
	
	public void setHoldNum(long holdNum) {
		this.holdNum = holdNum;
	}
	
	public long getTransferPrice() {
		return this.transferPrice;
	}
	
	public void setTransferPrice(long transferPrice) {
		this.transferPrice = transferPrice;
	}
	
	public long getTransferNum() {
		return this.transferNum;
	}
	
	public void setTransferNum(long transferNum) {
		this.transferNum = transferNum;
	}
	
	public Date getTransferTimeStart() {
		return this.transferTimeStart;
	}
	
	public void setTransferTimeStart(Date transferTimeStart) {
		this.transferTimeStart = transferTimeStart;
	}
	
	public Date getTransferTimeEnd() {
		return this.transferTimeEnd;
	}
	
	public void setTransferTimeEnd(Date transferTimeEnd) {
		this.transferTimeEnd = transferTimeEnd;
	}
	
	public String getTransferReason() {
		return this.transferReason;
	}
	
	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}
	
	public String getAttach() {
		return this.attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
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
	
	public long getTransferingNum() {
		return this.transferingNum;
	}
	
	public void setTransferingNum(long transferingNum) {
		this.transferingNum = transferingNum;
	}
	
	public long getRedeemingNum() {
		return this.redeemingNum;
	}
	
	public void setRedeemingNum(long redeemingNum) {
		this.redeemingNum = redeemingNum;
	}
	
	public BooleanEnum getIsTransferOwnership() {
		return this.isTransferOwnership;
	}
	
	public void setIsTransferOwnership(BooleanEnum isTransferOwnership) {
		this.isTransferOwnership = isTransferOwnership;
	}
	
	public BooleanEnum getIsBuyBack() {
		return this.isBuyBack;
	}
	
	public void setIsBuyBack(BooleanEnum isBuyBack) {
		this.isBuyBack = isBuyBack;
	}
	
	public Money getBuyBackPrice() {
		return this.buyBackPrice;
	}
	
	public void setBuyBackPrice(Money buyBackPrice) {
		this.buyBackPrice = buyBackPrice;
	}
	
	public Date getBuyBackTime() {
		return this.buyBackTime;
	}
	
	public void setBuyBackTime(Date buyBackTime) {
		this.buyBackTime = buyBackTime;
	}
	
	public long getCouncilApplyId() {
		return councilApplyId;
	}
	
	public void setCouncilApplyId(long councilApplyId) {
		this.councilApplyId = councilApplyId;
	}
	
	public ProjectCouncilEnum getCouncilType() {
		return councilType;
	}
	
	public void setCouncilType(ProjectCouncilEnum councilType) {
		this.councilType = councilType;
	}
	
	public ProjectCouncilStatusEnum getCouncilStatus() {
		return councilStatus;
	}
	
	public void setCouncilStatus(ProjectCouncilStatusEnum councilStatus) {
		this.councilStatus = councilStatus;
	}
}
