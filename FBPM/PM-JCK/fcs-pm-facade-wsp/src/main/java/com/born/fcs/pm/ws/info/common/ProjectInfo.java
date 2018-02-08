package com.born.fcs.pm.ws.info.common;

import java.util.Calendar;
import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目主信息
 * 
 * @author wuzj
 */
public class ProjectInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -6868292874055108878L;
	
	private long projectId;
	//项目编号
	private String projectCode;
	//项目名称
	private String projectName;
	//客户ID
	private long customerId;
	//客户姓名
	private String customerName;
	//客户类型
	private CustomerTypeEnum customerType;
	//客户行业编码
	private String industryCode;
	//客户行业名称
	private String industryName;
	//业务类型
	private String busiType;
	//业务类型名称
	private String busiTypeName;
	//业务类型
	private String busiSubType;
	//业务类型名称
	private String busiSubTypeName;
	//授信用途
	private String loanPurpose;
	//授信期限
	private int timeLimit;
	//期限单位
	private TimeUnitEnum timeUnit;
	//授信期间
	private Date startTime;
	
	private Date endTime;
	//金额
	private Money amount = new Money(0, 0);
	//累计发行金额（承销、发债）
	private Money accumulatedIssueAmount = new Money(0, 0);
	//已放款金额
	private Money loanedAmount = new Money(0, 0);
	//已用款金额
	private Money usedAmount = new Money(0, 0);
	//已还款金额
	private Money repayedAmount = new Money(0, 0);
	//已收费金额
	private Money chargedAmount = new Money(0, 0);
	//已退费金额
	private Money refundAmount = new Money(0, 0);
	//是否最高授信
	private BooleanEnum isMaximumAmount;
	//总的可解保金额
	private Money releasableAmount = new Money(0, 0);
	//已解保金额
	private Money releasedAmount = new Money(0, 0);
	//在保金额
	private Money releasingAmount = new Money(0, 0);
	//客户保证金
	private Money customerDepositAmount = new Money(0, 0);
	//保证金
	private Money selfDepositAmount = new Money(0, 0);
	//已代偿本金
	private Money compPrincipalAmount = new Money(0, 0);
	//已代偿利息
	private Money compInterestAmount = new Money(0, 0);
	//主合同编号
	private String contractNo;
	//合同签订时间
	private Date contractTime;
	//会议纪要通过时间
	private Date approvalTime;
	//项目评审会会议纪要ID
	private long spId;
	//项目评审会会议纪要编号（通过的项目为项目批复编号）
	private String spCode;
	//是否通过
	private BooleanEnum isApproval;
	//项目批复是否作废
	private BooleanEnum isApprovalDel;
	//是否可复议
	private BooleanEnum isRecouncil;
	//上次复议时间
	private Date lastRecouncilTime;
	//业务经理
	private long busiManagerId;
	
	private String busiManagerAccount;
	
	private String busiManagerName;
	//业务经理B
	private long busiManagerbId;
	
	private String busiManagerbAccount;
	
	private String busiManagerbName;
	
	private long deptId;
	
	//部门编号
	protected String deptCode;
	
	//部门名称
	protected String deptName;
	
	//部门路径 deptId.deptId. 
	protected String deptPath;
	
	//部门路径名称 deptPathName/deptPathName/
	protected String deptPathName;
	
	//项目阶段
	private ProjectPhasesEnum phases;
	//项目状态
	private ProjectStatusEnum status;
	//阶段状态
	private ProjectPhasesStatusEnum phasesStatus;
	//是否手动停止继续发售(承销/发债发售信息维护)
	private BooleanEnum isContinue;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	//发债详细信息
	private ProjectBondDetailInfo bondDetailInfo;
	
	//委贷详细信息
	private ProjectEntrustedDetailInfo entrustedDetailInfo;
	
	//承销详细信息
	private ProjectUnderwritingDetailInfo underwritingDetailInfo;
	
	//诉讼保函详细信息
	private ProjectLgLitigationDetailInfo lgLitigationDetailInfo;
	
	//担保详细信息
	private ProjectGuaranteeDetailInfo guaranteeDetailInfo;
	
	// 管理人员查看工作台时的统计
	private int count;
	
	/////////////////////////  获取首页用于展示的方法
	/**
	 * 是否立项申请审核通过
	 * @return
	 */
	public String getSetUpApproval() {
		if (phases != null && ProjectPhasesEnum.SET_UP_PHASES != phases) {
			return BooleanEnum.YES.code();
		} else {
			return BooleanEnum.NO.code();
		}
	}
	
	/**
	 * 是否会议纪要审核通过 (合同阶段 --待填写/填写中)
	 * @return
	 */
	public String getCouncilSummaryApproval() {
		if (phases == null) {
			return BooleanEnum.NO.code();
		}
		if (ProjectPhasesEnum.CONTRACT_PHASES == phases) {
			if (ProjectPhasesStatusEnum.WAITING == phasesStatus) {
				return BooleanEnum.YES.code();
			}
		}
		return BooleanEnum.NO.code();
	}
	
	/// 合同编号 getContractNo 合同申请审核通过后 代表已回传
	public String getContractApproval() {
		if (StringUtil.isBlank(contractNo)) {
			return BooleanEnum.NO.code();
		}
		return BooleanEnum.YES.code();
	}
	
	// 放用款回执审核通过 已放款金额大于0 loanedAmount
	/**
	 * 放用款回执审核通过
	 * @return
	 */
	public String getLoanUsePhasesPass() {
		if (loanedAmount != null && loanedAmount.greaterThan(Money.zero())) {
			return BooleanEnum.YES.code();
		} else {
			return BooleanEnum.NO.code();
		}
	}
	
	// 项目到期前1个月启用到期通知
	/**
	 * 项目到期前1个月启用到期通知 ，若在到期一个月内，启动通知【此处无法取到系统时间】
	 */
	public String getProjectOverTime() {
		if (endTime == null) {
			return BooleanEnum.NO.code();
		}
		// 判定到期时间减去一个月是否小于今日
		
		// 判定是否应该开始
		Calendar calendarNow = Calendar.getInstance();
		Calendar calendarEndTime = Calendar.getInstance();
		calendarEndTime.setTime(endTime);
		// 若不减一个月也小与今日,代表已过期
		if (calendarNow.getTimeInMillis() > calendarEndTime.getTimeInMillis()) {
			return BooleanEnum.NO.code();
		}
		calendarEndTime.add(Calendar.MONTH, -1);
		
		// 小与今日代表今日在一个月内，
		if (calendarNow.getTimeInMillis() > calendarEndTime.getTimeInMillis()) {
			return BooleanEnum.YES.code();
		}
		return BooleanEnum.NO.code();
		
	}
	
	// 费用收取通知单审核通过后，启用：保后检查报告、chargedAmount
	/**
	 * 费用收取通知单审核通过后，
	 * @return
	 */
	public String getChargeAmountNoticePass() {
		if (chargedAmount != null && chargedAmount.greaterThan(Money.zero())) {
			return BooleanEnum.YES.code();
		} else {
			return BooleanEnum.NO.code();
		}
	}
	
	// spCode存在 项目评审会通过 启用会议评审
	/**
	 * 项目评审会通过
	 * @return
	 */
	public String getSpCouncilPass() {
		if (BooleanEnum.IS == isApproval) {
			return BooleanEnum.YES.code();
		} else {
			return BooleanEnum.NO.code();
		}
	}
	
	/**
	 * 期限
	 * @return
	 */
	public String getTimeLimitView() {
		if (timeLimit > 0 && timeUnit != null) {
			return timeLimit + timeUnit.viewName();
		} else {
			return "";
		}
	}
	
	/**
	 * 在保余额
	 * @return
	 */
	public Money getBalance() {
		if (isEntrusted()) {
			return loanedAmount.subtract(releasedAmount);
		} else {
			return releasableAmount.subtract(releasedAmount);
		}
	}
	
	/**
	 * 是否是承销业务
	 * 
	 * @param busiType
	 * @return 是：true
	 */
	public boolean isUnderwriting() {
		if (StringUtil.isNotBlank(busiType) && busiType.trim().startsWith("5")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 否是诉讼保函业务
	 * 
	 * @param busiType
	 * @return 是：true
	 */
	public boolean isLitigation() {
		if (StringUtil.isNotBlank(busiType) && busiType.trim().startsWith("211")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 是否发债业务
	 * @param busiType
	 * @return
	 */
	public boolean isBond() {
		if (StringUtil.isNotBlank(busiType) && busiType.trim().startsWith("12")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 是否委贷业务
	 * @param busiType
	 * @return
	 */
	public boolean isEntrusted() {
		if (StringUtil.isNotBlank(busiType) && busiType.trim().startsWith("4")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 是否担保业务
	 * @param busiType
	 * @return
	 */
	public boolean isGuarantee() {
		return !isUnderwriting() && !isLitigation() && !isBond() && !isEntrusted();
	}
	
	public long getProjectId() {
		return this.projectId;
	}
	
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void setCount(int count) {
		this.count = count;
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
	
	public long getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public CustomerTypeEnum getCustomerType() {
		return this.customerType;
	}
	
	public String getCustomerTypeMessage() {
		return this.customerType == null ? "" : customerType.message();
	}
	
	public void setCustomerType(CustomerTypeEnum customerType) {
		this.customerType = customerType;
	}
	
	public String getIndustryCode() {
		return this.industryCode;
	}
	
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	
	public String getIndustryName() {
		return this.industryName;
	}
	
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
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
	
	public String getBusiSubType() {
		return this.busiSubType;
	}
	
	public void setBusiSubType(String busiSubType) {
		this.busiSubType = busiSubType;
	}
	
	public String getBusiSubTypeName() {
		return this.busiSubTypeName;
	}
	
	public void setBusiSubTypeName(String busiSubTypeName) {
		this.busiSubTypeName = busiSubTypeName;
	}
	
	public String getLoanPurpose() {
		return this.loanPurpose;
	}
	
	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}
	
	public int getTimeLimit() {
		return this.timeLimit;
	}
	
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public TimeUnitEnum getTimeUnit() {
		return this.timeUnit;
	}
	
	public String getTimeUnitMessage() {
		return this.timeUnit == null ? "" : timeUnit.message();
	}
	
	public void setTimeUnit(TimeUnitEnum timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	public Date getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Money getAmount() {
		return this.amount;
	}
	
	public String getAmountCNString() {
		Money money = this.amount == null ? Money.zero() : amount;
		return money.toCNString();
	}
	
	public String getAmountStandardString() {
		Money money = this.amount == null ? Money.zero() : amount;
		return money.toStandardString();
	}
	
	public String getAmountTenThousandString() {
		Money money = this.amount == null ? Money.zero() : amount;
		return getMoneyByw2(money);
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public Money getLoanedAmount() {
		return this.loanedAmount;
	}
	
	public String getLoanedAmountCNString() {
		Money money = this.loanedAmount == null ? Money.zero() : loanedAmount;
		return money.toCNString();
	}
	
	public String getLoanedAmountStandardString() {
		Money money = this.loanedAmount == null ? Money.zero() : loanedAmount;
		return money.toStandardString();
	}
	
	public String getLoanedAmountTenThousandString() {
		Money money = this.loanedAmount == null ? Money.zero() : loanedAmount;
		return getMoneyByw2(money);
	}
	
	public void setLoanedAmount(Money loanedAmount) {
		this.loanedAmount = loanedAmount;
	}
	
	public Money getUsedAmount() {
		return this.usedAmount;
	}
	
	public String getUsedAmountCNString() {
		Money money = this.usedAmount == null ? Money.zero() : usedAmount;
		return money.toCNString();
	}
	
	public String getUsedAmountStandardString() {
		Money money = this.usedAmount == null ? Money.zero() : usedAmount;
		return money.toStandardString();
	}
	
	public String getUsedAmountTenThousandString() {
		Money money = this.usedAmount == null ? Money.zero() : usedAmount;
		return getMoneyByw2(money);
	}
	
	public void setUsedAmount(Money usedAmount) {
		this.usedAmount = usedAmount;
	}
	
	public Money getRepayedAmount() {
		return this.repayedAmount;
	}
	
	public String getRepayedAmountCNString() {
		Money money = this.repayedAmount == null ? Money.zero() : repayedAmount;
		return money.toCNString();
	}
	
	public String getRepayedAmountStandardString() {
		Money money = this.repayedAmount == null ? Money.zero() : repayedAmount;
		return money.toStandardString();
	}
	
	public String getRepayedAmountTenThousandString() {
		Money money = this.repayedAmount == null ? Money.zero() : repayedAmount;
		return getMoneyByw2(money);
	}
	
	public void setRepayedAmount(Money repayedAmount) {
		this.repayedAmount = repayedAmount;
	}
	
	public BooleanEnum getIsMaximumAmount() {
		return this.isMaximumAmount;
	}
	
	public String getIsMaximumAmountMessage() {
		return this.isMaximumAmount == null ? "" : isMaximumAmount.message();
	}
	
	public void setIsMaximumAmount(BooleanEnum isMaximumAmount) {
		this.isMaximumAmount = isMaximumAmount;
	}
	
	public Money getReleasedAmount() {
		return releasedAmount;
	}
	
	public String getReleasedAmountCNString() {
		Money money = this.releasedAmount == null ? Money.zero() : releasedAmount;
		return money.toCNString();
	}
	
	public String getReleasedAmountStandardString() {
		Money money = this.releasedAmount == null ? Money.zero() : releasedAmount;
		return money.toStandardString();
	}
	
	public String getReleasedAmountTenThousandString() {
		Money money = this.releasedAmount == null ? Money.zero() : releasedAmount;
		return getMoneyByw2(money);
	}
	
	public void setReleasedAmount(Money releasedAmount) {
		this.releasedAmount = releasedAmount;
	}
	
	public Money getReleasingAmount() {
		return releasingAmount;
	}
	
	public String getReleasingAmountCNString() {
		Money money = this.releasingAmount == null ? Money.zero() : releasingAmount;
		return money.toCNString();
	}
	
	public String getReleasingAmountStandardString() {
		Money money = this.releasingAmount == null ? Money.zero() : releasingAmount;
		return money.toStandardString();
	}
	
	public String getReleasingAmountTenThousandString() {
		Money money = this.releasingAmount == null ? Money.zero() : releasingAmount;
		return getMoneyByw2(money);
	}
	
	public void setReleasingAmount(Money releasingAmount) {
		this.releasingAmount = releasingAmount;
	}
	
	/**
	 * 合同申请审核通过后 代表合同已回传
	 * @return
	 */
	public String getContractNo() {
		return this.contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public long getSpId() {
		return this.spId;
	}
	
	public void setSpId(long spId) {
		this.spId = spId;
	}
	
	public String getSpCode() {
		return this.spCode;
	}
	
	public void setSpCode(String spCode) {
		this.spCode = spCode;
	}
	
	public long getBusiManagerId() {
		return this.busiManagerId;
	}
	
	public void setBusiManagerId(long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}
	
	public String getBusiManagerAccount() {
		return this.busiManagerAccount;
	}
	
	public void setBusiManagerAccount(String busiManagerAccount) {
		this.busiManagerAccount = busiManagerAccount;
	}
	
	public String getBusiManagerName() {
		return this.busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public long getBusiManagerbId() {
		return this.busiManagerbId;
	}
	
	public void setBusiManagerbId(long busiManagerbId) {
		this.busiManagerbId = busiManagerbId;
	}
	
	public String getBusiManagerbAccount() {
		return this.busiManagerbAccount;
	}
	
	public void setBusiManagerbAccount(String busiManagerbAccount) {
		this.busiManagerbAccount = busiManagerbAccount;
	}
	
	public String getBusiManagerbName() {
		return this.busiManagerbName;
	}
	
	public void setBusiManagerbName(String busiManagerbName) {
		this.busiManagerbName = busiManagerbName;
	}
	
	public long getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptCode() {
		return this.deptCode;
	}
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getDeptPath() {
		return this.deptPath;
	}
	
	public void setDeptPath(String deptPath) {
		this.deptPath = deptPath;
	}
	
	public String getDeptPathName() {
		return this.deptPathName;
	}
	
	public void setDeptPathName(String deptPathName) {
		this.deptPathName = deptPathName;
	}
	
	public ProjectPhasesEnum getPhases() {
		return this.phases;
	}
	
	public String getPhasesMessage() {
		return this.phases == null ? "" : phases.message();
	}
	
	public void setPhases(ProjectPhasesEnum phases) {
		this.phases = phases;
	}
	
	public ProjectStatusEnum getStatus() {
		return this.status;
	}
	
	public String getStatusMessage() {
		return this.status == null ? "" : status.message();
	}
	
	public void setStatus(ProjectStatusEnum status) {
		this.status = status;
	}
	
	public ProjectPhasesStatusEnum getPhasesStatus() {
		return this.phasesStatus;
	}
	
	public String getPhasesStatusMessage() {
		return this.phasesStatus == null ? "" : phasesStatus.message();
	}
	
	public void setPhasesStatus(ProjectPhasesStatusEnum phasesStatus) {
		this.phasesStatus = phasesStatus;
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
	
	public BooleanEnum getIsContinue() {
		return isContinue;
	}
	
	public String getIsContinueMessage() {
		return isContinue == null ? "" : isContinue.message();
	}
	
	public void setIsContinue(BooleanEnum isContinue) {
		this.isContinue = isContinue;
	}
	
	public Money getAccumulatedIssueAmount() {
		return accumulatedIssueAmount;
	}
	
	public String getAccumulatedIssueAmountCNString() {
		Money money = this.accumulatedIssueAmount == null ? Money.zero() : accumulatedIssueAmount;
		return money.toCNString();
	}
	
	public String getAccumulatedIssueAmountStandardString() {
		Money money = this.accumulatedIssueAmount == null ? Money.zero() : accumulatedIssueAmount;
		return money.toStandardString();
	}
	
	public String getAccumulatedIssueAmountTenThousandString() {
		Money money = this.accumulatedIssueAmount == null ? Money.zero() : accumulatedIssueAmount;
		return getMoneyByw2(money);
	}
	
	public void setAccumulatedIssueAmount(Money accumulatedIssueAmount) {
		this.accumulatedIssueAmount = accumulatedIssueAmount;
	}
	
	public Money getCustomerDepositAmount() {
		return this.customerDepositAmount;
	}
	
	public String getCustomerDepositAmountCNString() {
		Money money = this.customerDepositAmount == null ? Money.zero() : customerDepositAmount;
		return money.toCNString();
	}
	
	public String getCustomerDepositAmountStandardString() {
		Money money = this.customerDepositAmount == null ? Money.zero() : customerDepositAmount;
		return money.toStandardString();
	}
	
	public String getCustomerDepositAmountTenThousandString() {
		Money money = this.customerDepositAmount == null ? Money.zero() : customerDepositAmount;
		return getMoneyByw2(money);
	}
	
	public void setCustomerDepositAmount(Money customerDepositAmount) {
		this.customerDepositAmount = customerDepositAmount;
	}
	
	public Money getSelfDepositAmount() {
		return this.selfDepositAmount;
	}
	
	public String getSelfDepositAmountCNString() {
		Money money = this.selfDepositAmount == null ? Money.zero() : selfDepositAmount;
		return money.toCNString();
	}
	
	public String getSelfDepositAmountStandardString() {
		Money money = this.selfDepositAmount == null ? Money.zero() : selfDepositAmount;
		return money.toStandardString();
	}
	
	public String getSelfDepositAmountTenThousandString() {
		Money money = this.selfDepositAmount == null ? Money.zero() : selfDepositAmount;
		return getMoneyByw2(money);
	}
	
	public void setSelfDepositAmount(Money selfDepositAmount) {
		this.selfDepositAmount = selfDepositAmount;
	}
	
	public BooleanEnum getIsApproval() {
		return this.isApproval;
	}
	
	public String getIsApprovalMessage() {
		return this.isApproval == null ? "" : isApproval.message();
	}
	
	public void setIsApproval(BooleanEnum isApproval) {
		this.isApproval = isApproval;
	}
	
	public BooleanEnum getIsRecouncil() {
		return this.isRecouncil;
	}
	
	public String getIsRecouncilMessage() {
		return this.isRecouncil == null ? "" : isRecouncil.message();
	}
	
	public void setIsRecouncil(BooleanEnum isRecouncil) {
		this.isRecouncil = isRecouncil;
	}
	
	public Date getContractTime() {
		return this.contractTime;
	}
	
	public void setContractTime(Date contractTime) {
		this.contractTime = contractTime;
	}
	
	public Date getApprovalTime() {
		return this.approvalTime;
	}
	
	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}
	
	public Money getCompPrincipalAmount() {
		return compPrincipalAmount;
	}
	
	public String getCompPrincipalAmountCNString() {
		Money money = this.compPrincipalAmount == null ? Money.zero() : compPrincipalAmount;
		return money.toCNString();
	}
	
	public String getCompPrincipalAmountStandardString() {
		Money money = this.compPrincipalAmount == null ? Money.zero() : compPrincipalAmount;
		return money.toStandardString();
	}
	
	public String getCompPrincipalAmountTenThousandString() {
		Money money = this.compPrincipalAmount == null ? Money.zero() : compPrincipalAmount;
		return getMoneyByw2(money);
	}
	
	public void setCompPrincipalAmount(Money compPrincipalAmount) {
		this.compPrincipalAmount = compPrincipalAmount;
	}
	
	public Money getCompInterestAmount() {
		return compInterestAmount;
	}
	
	public String getCompInterestAmountCNString() {
		Money money = this.compInterestAmount == null ? Money.zero() : compInterestAmount;
		return money.toCNString();
	}
	
	public String getCompInterestAmountStandardString() {
		Money money = this.compInterestAmount == null ? Money.zero() : compInterestAmount;
		return money.toStandardString();
	}
	
	public String getCompInterestAmountTenThousandString() {
		Money money = this.compInterestAmount == null ? Money.zero() : compInterestAmount;
		return getMoneyByw2(money);
	}
	
	public void setCompInterestAmount(Money compInterestAmount) {
		this.compInterestAmount = compInterestAmount;
	}
	
	public BooleanEnum getIsApprovalDel() {
		return this.isApprovalDel;
	}
	
	public String getIsApprovalDelMessage() {
		return this.isApprovalDel == null ? "" : isApprovalDel.message();
	}
	
	public void setIsApprovalDel(BooleanEnum isApprovalDel) {
		this.isApprovalDel = isApprovalDel;
	}
	
	public Date getLastRecouncilTime() {
		return this.lastRecouncilTime;
	}
	
	public void setLastRecouncilTime(Date lastRecouncilTime) {
		this.lastRecouncilTime = lastRecouncilTime;
	}
	
	public ProjectBondDetailInfo getBondDetailInfo() {
		return this.bondDetailInfo;
	}
	
	public void setBondDetailInfo(ProjectBondDetailInfo bondDetailInfo) {
		this.bondDetailInfo = bondDetailInfo;
	}
	
	public ProjectEntrustedDetailInfo getEntrustedDetailInfo() {
		return this.entrustedDetailInfo;
	}
	
	public void setEntrustedDetailInfo(ProjectEntrustedDetailInfo entrustedDetailInfo) {
		this.entrustedDetailInfo = entrustedDetailInfo;
	}
	
	public ProjectUnderwritingDetailInfo getUnderwritingDetailInfo() {
		return this.underwritingDetailInfo;
	}
	
	public void setUnderwritingDetailInfo(ProjectUnderwritingDetailInfo underwritingDetailInfo) {
		this.underwritingDetailInfo = underwritingDetailInfo;
	}
	
	public ProjectLgLitigationDetailInfo getLgLitigationDetailInfo() {
		return this.lgLitigationDetailInfo;
	}
	
	public void setLgLitigationDetailInfo(ProjectLgLitigationDetailInfo lgLitigationDetailInfo) {
		this.lgLitigationDetailInfo = lgLitigationDetailInfo;
	}
	
	public ProjectGuaranteeDetailInfo getGuaranteeDetailInfo() {
		return this.guaranteeDetailInfo;
	}
	
	public void setGuaranteeDetailInfo(ProjectGuaranteeDetailInfo guaranteeDetailInfo) {
		this.guaranteeDetailInfo = guaranteeDetailInfo;
	}
	
	public Money getReleasableAmount() {
		return releasableAmount;
	}
	
	public void setReleasableAmount(Money releasableAmount) {
		this.releasableAmount = releasableAmount;
	}
	
	public Money getChargedAmount() {
		return this.chargedAmount;
	}
	
	public void setChargedAmount(Money chargedAmount) {
		this.chargedAmount = chargedAmount;
	}
	
	public Money getRefundAmount() {
		return refundAmount;
	}
	
	public void setRefundAmount(Money refundAmount) {
		this.refundAmount = refundAmount;
	}
}
