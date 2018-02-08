package com.born.fcs.pm.ws.info.council;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 会议纪要 - 风险处置会 - 处置方案
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryRiskHandleInfo extends FCouncilSummaryProjectCreditConditionInfo {
	
	private static final long serialVersionUID = -7150819324879461183L;
	
	/** 主键 */
	private long handleId;
	/** 会议纪要ID */
	//	private long summaryId;
	//	/** 项目编号 */
	//	private String projectCode;
	//	/** 项目名称 */
	//	private String projectName;
	//	/** 对应客户ID */
	//	private long customerId;
	//	/** 对应客户名称 */
	//	private String customerName;
	//	/** 客户类型 */
	//	private CustomerTypeEnum customerType;
	/** 是否展期 */
	private BooleanEnum isExtend;
	/** 展期本金 */
	private Money extendPrincipal = new Money(0, 0);
	/** 展期期限 */
	private int extendTimeLimit;
	/** 展期期限单位 */
	private TimeUnitEnum extendTimeUnit;
	/** 展期说明 */
	private String extendRemark;
	/**
	 * 展期数据 [{extendPrincipal:xx,extendTimeLimit:xx,extendTimeUnit:xx},{
	 * extendPrincipal :xx,extendTimeLimit:xx,extendTimeUnit:xx}]
	 */
	private String extendData;
	/***
	 * 展期数据
	 */
	private List<FCouncilSummaryRiskHandleExtendInfo> extendDataList = Lists.newArrayList();
	/** 是否代偿 */
	private BooleanEnum isComp;
	/** 代偿本金 */
	private Money compPrincipal = new Money(0, 0);
	/** 代偿利息 */
	private String compInterest;
	/** 代偿违约金 */
	private String compPenalty;
	/** 代偿罚息 */
	private String compPenaltyInterest;
	/** 代偿其他 */
	private String compOther;
	/** 代偿截止时间 */
	private Date compEndTime;
	/** 代偿说明 */
	private String compRemark;
	
	/** 是否重新授信 */
	private BooleanEnum isRedo;
	/** 重新授信项目编号 */
	private String redoProjectCode;
	/** 重新授信项目名称 */
	private String redoProjectName;
	/** 重新授信金额 */
	private Money redoAmount = new Money(0, 0);
	/** 重新授信期限 */
	private int redoTimeLimit;
	/** 重新授信期限单位 */
	private TimeUnitEnum redoTimeUnit;
	/** 重新授信期限备注 */
	private String redoTimeRemark;
	/** 重新授信业务类型 */
	private String redoBusiType;
	/** 重新授信业务类型名称 */
	private String redoBusiTypeName;
	/** 重新授信描述 */
	private String redo;
	/** 重新授信备注 */
	private String redoRemark;
	/** 重新授信费用 */
	private double fee;
	/** 重新授信费用类型 */
	private ChargeTypeEnum feeType;
	/** 是否其他组合 */
	private BooleanEnum isOther;
	/** 其他组合 */
	private String otherComb;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	/** 在保月余额 */
	private Money inGuaranteeAmount;
	
	public long getHandleId() {
		return this.handleId;
	}
	
	public void setHandleId(long handleId) {
		this.handleId = handleId;
	}
	
	//	public long getSummaryId() {
	//		return this.summaryId;
	//	}
	//	
	//	public void setSummaryId(long summaryId) {
	//		this.summaryId = summaryId;
	//	}
	//	
	//	public String getProjectCode() {
	//		return this.projectCode;
	//	}
	//	
	//	public void setProjectCode(String projectCode) {
	//		this.projectCode = projectCode;
	//	}
	//	
	//	public String getProjectName() {
	//		return this.projectName;
	//	}
	//	
	//	public void setProjectName(String projectName) {
	//		this.projectName = projectName;
	//	}
	//	
	//	public long getCustomerId() {
	//		return this.customerId;
	//	}
	//	
	//	public void setCustomerId(long customerId) {
	//		this.customerId = customerId;
	//	}
	//	
	//	public String getCustomerName() {
	//		return this.customerName;
	//	}
	//	
	//	public void setCustomerName(String customerName) {
	//		this.customerName = customerName;
	//	}
	//	
	//	public CustomerTypeEnum getCustomerType() {
	//		return this.customerType;
	//	}
	//	
	//	public void setCustomerType(CustomerTypeEnum customerType) {
	//		this.customerType = customerType;
	//	}
	
	public BooleanEnum getIsExtend() {
		return this.isExtend;
	}
	
	public void setIsExtend(BooleanEnum isExtend) {
		this.isExtend = isExtend;
	}
	
	public Money getExtendPrincipal() {
		return this.extendPrincipal;
	}
	
	public void setExtendPrincipal(Money extendPrincipal) {
		this.extendPrincipal = extendPrincipal;
	}
	
	public int getExtendTimeLimit() {
		return this.extendTimeLimit;
	}
	
	public void setExtendTimeLimit(int extendTimeLimit) {
		this.extendTimeLimit = extendTimeLimit;
	}
	
	public TimeUnitEnum getExtendTimeUnit() {
		return this.extendTimeUnit;
	}
	
	public void setExtendTimeUnit(TimeUnitEnum extendTimeUnit) {
		this.extendTimeUnit = extendTimeUnit;
	}
	
	public String getExtendRemark() {
		return this.extendRemark;
	}
	
	public void setExtendRemark(String extendRemark) {
		this.extendRemark = extendRemark;
	}
	
	public String getExtendData() {
		return this.extendData;
	}
	
	public void setExtendData(String extendData) {
		this.extendData = extendData;
	}
	
	public List<FCouncilSummaryRiskHandleExtendInfo> getExtendDataList() {
		if (this.extendDataList == null) {
			this.extendDataList = Lists.newArrayList();
		} else {
			this.extendDataList.clear();
		}
		if (StringUtil.isNotEmpty(extendData)) {
			JSONArray extendArr = JSONArray.parseArray(extendData);
			if (extendArr != null && !extendArr.isEmpty()) {
				for (Object extend : extendArr) {
					JSONObject extendJson = (JSONObject) extend;
					FCouncilSummaryRiskHandleExtendInfo extendInfo = new FCouncilSummaryRiskHandleExtendInfo();
					String extendPrincipal = extendJson.getString("extendPrincipal");
					if (extendPrincipal != null && !"".equals(extendPrincipal)) {
						extendInfo.setExtendPrincipal(Money.amout(extendPrincipal));
					}
					extendInfo.setExtendTimeLimit(extendJson.getIntValue("extendTimeLimit"));
					extendInfo.setExtendTimeUnit(TimeUnitEnum.getByCode(extendJson
						.getString("extendTimeUnit")));
					this.extendDataList.add(extendInfo);
				}
			}
		}
		return this.extendDataList;
	}
	
	public void setExtendDataList(List<FCouncilSummaryRiskHandleExtendInfo> extendDataList) {
		this.extendDataList = extendDataList;
	}
	
	public BooleanEnum getIsComp() {
		return this.isComp;
	}
	
	public void setIsComp(BooleanEnum isComp) {
		this.isComp = isComp;
	}
	
	public Money getCompPrincipal() {
		return this.compPrincipal;
	}
	
	public void setCompPrincipal(Money compPrincipal) {
		this.compPrincipal = compPrincipal;
	}
	
	public Date getCompEndTime() {
		return this.compEndTime;
	}
	
	public void setCompEndTime(Date compEndTime) {
		this.compEndTime = compEndTime;
	}
	
	public String getCompRemark() {
		return this.compRemark;
	}
	
	public void setCompRemark(String compRemark) {
		this.compRemark = compRemark;
	}
	
	public BooleanEnum getIsRedo() {
		return this.isRedo;
	}
	
	public void setIsRedo(BooleanEnum isRedo) {
		this.isRedo = isRedo;
	}
	
	public String getRedoProjectCode() {
		return this.redoProjectCode;
	}
	
	public void setRedoProjectCode(String redoProjectCode) {
		this.redoProjectCode = redoProjectCode;
	}
	
	public String getRedoProjectName() {
		return this.redoProjectName;
	}
	
	public void setRedoProjectName(String redoProjectName) {
		this.redoProjectName = redoProjectName;
	}
	
	public Money getRedoAmount() {
		return this.redoAmount;
	}
	
	public void setRedoAmount(Money redoAmount) {
		this.redoAmount = redoAmount;
	}
	
	public int getRedoTimeLimit() {
		return this.redoTimeLimit;
	}
	
	public void setRedoTimeLimit(int redoTimeLimit) {
		this.redoTimeLimit = redoTimeLimit;
	}
	
	public TimeUnitEnum getRedoTimeUnit() {
		return this.redoTimeUnit;
	}
	
	public void setRedoTimeUnit(TimeUnitEnum redoTimeUnit) {
		this.redoTimeUnit = redoTimeUnit;
	}
	
	public String getRedoTimeRemark() {
		return this.redoTimeRemark;
	}
	
	public void setRedoTimeRemark(String redoTimeRemark) {
		this.redoTimeRemark = redoTimeRemark;
	}
	
	public String getRedoBusiType() {
		return this.redoBusiType;
	}
	
	public void setRedoBusiType(String redoBusiType) {
		this.redoBusiType = redoBusiType;
	}
	
	public String getRedoBusiTypeName() {
		return this.redoBusiTypeName;
	}
	
	public void setRedoBusiTypeName(String redoBusiTypeName) {
		this.redoBusiTypeName = redoBusiTypeName;
	}
	
	public String getRedo() {
		return this.redo;
	}
	
	public void setRedo(String redo) {
		this.redo = redo;
	}
	
	public String getRedoRemark() {
		return this.redoRemark;
	}
	
	public void setRedoRemark(String redoRemark) {
		this.redoRemark = redoRemark;
	}
	
	public double getFee() {
		return this.fee;
	}
	
	public void setFee(double fee) {
		this.fee = fee;
	}
	
	public ChargeTypeEnum getFeeType() {
		return this.feeType;
	}
	
	public void setFeeType(ChargeTypeEnum feeType) {
		this.feeType = feeType;
	}
	
	public BooleanEnum getIsOther() {
		return this.isOther;
	}
	
	public void setIsOther(BooleanEnum isOther) {
		this.isOther = isOther;
	}
	
	public String getOtherComb() {
		return this.otherComb;
	}
	
	public void setOtherComb(String otherComb) {
		this.otherComb = otherComb;
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
	
	public Money getInGuaranteeAmount() {
		return this.inGuaranteeAmount;
	}
	
	public void setInGuaranteeAmount(Money inGuaranteeAmount) {
		this.inGuaranteeAmount = inGuaranteeAmount;
	}
	
	public String getCompInterest() {
		return this.compInterest;
	}
	
	public void setCompInterest(String compInterest) {
		this.compInterest = compInterest;
	}
	
	public String getCompPenalty() {
		return this.compPenalty;
	}
	
	public void setCompPenalty(String compPenalty) {
		this.compPenalty = compPenalty;
	}
	
	public String getCompPenaltyInterest() {
		return this.compPenaltyInterest;
	}
	
	public void setCompPenaltyInterest(String compPenaltyInterest) {
		this.compPenaltyInterest = compPenaltyInterest;
	}
	
	public String getCompOther() {
		return this.compOther;
	}
	
	public void setCompOther(String compOther) {
		this.compOther = compOther;
	}
	
}
