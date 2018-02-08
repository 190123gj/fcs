package com.born.fcs.pm.ws.order.council;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.yjf.common.lang.util.money.Money;

/**
 * 会议纪要 - 项目评审会-项目通用信息
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryProjectOrder extends FCouncilSummaryOrder {
	
	private static final long serialVersionUID = -9100812810314936621L;
	/** 会议纪要对应项目ID */
	private Long spId;
	/** 项目编号 */
	private String projectCode;
	/** 项目名称 */
	private String projectName;
	/** 客户ID */
	private Long customerId;
	/** 客户名称 */
	private String customerName;
	/** 客户类型 */
	private String customerType;
	/** 业务类型 */
	private String busiType;
	/** 业务类型名称 */
	private String busiTypeName;
	/** 用途 */
	private String loanPurpose;
	/** 拟发行金额/保全金额/授信金额 */
	private Money amount;
	/** 项目期限 */
	private Integer timeLimit;
	/** 期限单位 */
	private String timeUnit;
	/** 期限备注 */
	private String timeRemark;
	/** 还款方式 多次/一次 */
	private String repayWay;
	/** 是否连续年度等额偿还 */
	private String isRepayEqual;
	/** 收费方式 多次/一次 */
	private String chargeWay;
	/** 收费备注 */
	private String chargeRemark;
	/** 先收/后扣 */
	private String chargePhase;
	/** 首次收费外,是否以后为每年度期初 */
	private String isChargeEveryBeginning;
	/** 是否为最高额授信 */
	private String isMaximumAmount;
	/** 投票结果 */
	private ProjectVoteResultEnum voteResult;
	
	/** 投票结果描述 */
	private String voteResultDesc;
	/** 是否作废 */
	private String isDel;
	/** 备注说明 */
	private String remark;
	
	/** 概要 */
	private String projectOverview;
	
	/** 是否南川分公司项目 YES/NO */
	private String belongToNc;
	
	/** 其他 */
	private String other;
	
	@Override
	public void check() {
		validateHasText(projectCode, "项目编号");
		if (isFormChangeApply != BooleanEnum.IS) {
			validateNotNull(summaryId, "会议纪要ID");
			validateHasZore(summaryId, "会议纪要ID");
		}
	}
	
	public Long getSpId() {
		return this.spId;
	}
	
	public void setSpId(Long spId) {
		this.spId = spId;
	}
	
	public Long getSummaryId() {
		return this.summaryId;
	}
	
	public void setSummaryId(Long summaryId) {
		this.summaryId = summaryId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public Long getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCustomerType() {
		return this.customerType;
	}
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	public String getBusiType() {
		return this.busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return this.busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public String getLoanPurpose() {
		return this.loanPurpose;
	}
	
	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public Integer getTimeLimit() {
		return this.timeLimit;
	}
	
	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public String getTimeUnit() {
		return this.timeUnit;
	}
	
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	public String getTimeRemark() {
		return this.timeRemark;
	}
	
	public void setTimeRemark(String timeRemark) {
		this.timeRemark = timeRemark;
	}
	
	public String getRepayWay() {
		return this.repayWay;
	}
	
	public void setRepayWay(String repayWay) {
		this.repayWay = repayWay;
	}
	
	public String getIsRepayEqual() {
		return this.isRepayEqual;
	}
	
	public void setIsRepayEqual(String isRepayEqual) {
		this.isRepayEqual = isRepayEqual;
	}
	
	public String getChargeWay() {
		return this.chargeWay;
	}
	
	public void setChargeWay(String chargeWay) {
		this.chargeWay = chargeWay;
	}
	
	public String getIsChargeEveryBeginning() {
		return this.isChargeEveryBeginning;
	}
	
	public void setIsChargeEveryBeginning(String isChargeEveryBeginning) {
		this.isChargeEveryBeginning = isChargeEveryBeginning;
	}
	
	public String getIsMaximumAmount() {
		return this.isMaximumAmount;
	}
	
	public void setIsMaximumAmount(String isMaximumAmount) {
		this.isMaximumAmount = isMaximumAmount;
	}
	
	public String getChargePhase() {
		return this.chargePhase;
	}
	
	public void setChargePhase(String chargePhase) {
		this.chargePhase = chargePhase;
	}
	
	public String getChargeRemark() {
		return this.chargeRemark;
	}
	
	public void setChargeRemark(String chargeRemark) {
		this.chargeRemark = chargeRemark;
	}
	
	public ProjectVoteResultEnum getVoteResult() {
		return this.voteResult;
	}
	
	public void setVoteResult(ProjectVoteResultEnum voteResult) {
		this.voteResult = voteResult;
	}
	
	public String getIsDel() {
		return this.isDel;
	}
	
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	
	public String getVoteResultDesc() {
		return this.voteResultDesc;
	}
	
	public void setVoteResultDesc(String voteResultDesc) {
		this.voteResultDesc = voteResultDesc;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getOther() {
		return this.other;
	}
	
	public void setOther(String other) {
		this.other = other;
	}
	
	public String getBelongToNc() {
		return this.belongToNc;
	}
	
	public void setBelongToNc(String belongToNc) {
		this.belongToNc = belongToNc;
	}
	
	public String getProjectOverview() {
		return this.projectOverview;
	}
	
	public void setProjectOverview(String projectOverview) {
		this.projectOverview = projectOverview;
	}
	
}
