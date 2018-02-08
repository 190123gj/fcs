package com.born.fcs.pm.ws.info.council;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChargePhaseEnum;
import com.born.fcs.pm.ws.enums.ChargeWayEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.OneVoteResultEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.enums.RepayWayEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 会议纪要 - 项目评审会-项目批复
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryProjectInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 3107513959565320210L;
	//模板类型  ，担保：DB 委贷：WD 发债：FZ 诉保：SB 承销：CX 理财：LC 
	private String templete;
	/** 会议纪要对应项目ID */
	private long spId;
	/** 批复编号：项目纪要编号 */
	private String spCode;
	/** 会议纪要ID */
	private long summaryId;
	/** 项目编号 */
	private String projectCode;
	/** 项目名称 */
	private String projectName;
	/** 客户ID */
	private long customerId;
	/** 客户名称 */
	private String customerName;
	/** 客户类型 */
	private CustomerTypeEnum customerType;
	/** 业务类型 */
	private String busiType;
	/** 业务类型名称 */
	private String busiTypeName;
	/** 用途 */
	private String loanPurpose;
	/** 拟发行金额/保全金额/授信金额 */
	private Money amount = new Money(0, 0);
	/** 是否为最高额授信 */
	private BooleanEnum isMaximumAmount;
	/** 项目期限 */
	private int timeLimit;
	/** 期限单位 */
	private TimeUnitEnum timeUnit;
	/** 期限备注 */
	private String timeRemark;
	/** 还款方式 多次/一次 */
	private RepayWayEnum repayWay;
	/** 是否连续年度等额偿还 */
	private BooleanEnum isRepayEqual;
	/** 收费方式 多次/一次 */
	private ChargeWayEnum chargeWay;
	/** 先收/后扣 */
	private ChargePhaseEnum chargePhase;
	/** 收费备注 */
	private String chargeRemark;
	/** 首次收费外,是否以后为每年度期初 */
	private BooleanEnum isChargeEveryBeginning;
	/** 投票结果 */
	private ProjectVoteResultEnum voteResult;
	/** 是否作废 */
	private BooleanEnum isDel;
	/** 投票结果 */
	private String voteResultDesc;
	/** 一票决定 */
	private OneVoteResultEnum oneVoteDown;
	/** 一票决定说明 */
	private String oneVoteDownMark;
	/** 一票决定时间 */
	private Date oneVoteDownTime;
	/** 其他 */
	private String other;
	/** 是否南川分公司项目 YES/NO */
	private BooleanEnum belongToNc;
	/** 说明 */
	private String remark;
	/** 会议纪要通过时间 */
	private Date approvalTime;
	/** 批复简述 */
	private String overview;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	public String getTemplete() {
		return this.templete;
	}
	
	public void setTemplete(String templete) {
		this.templete = templete;
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
	
	public long getSummaryId() {
		return this.summaryId;
	}
	
	public void setSummaryId(long summaryId) {
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
	
	public void setCustomerType(CustomerTypeEnum customerType) {
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
	
	public BooleanEnum getIsMaximumAmount() {
		return this.isMaximumAmount;
	}
	
	public void setIsMaximumAmount(BooleanEnum isMaximumAmount) {
		this.isMaximumAmount = isMaximumAmount;
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
	
	public void setTimeUnit(TimeUnitEnum timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	public String getTimeRemark() {
		return this.timeRemark;
	}
	
	public void setTimeRemark(String timeRemark) {
		this.timeRemark = timeRemark;
	}
	
	public RepayWayEnum getRepayWay() {
		return this.repayWay;
	}
	
	public void setRepayWay(RepayWayEnum repayWay) {
		this.repayWay = repayWay;
	}
	
	public BooleanEnum getIsRepayEqual() {
		return this.isRepayEqual;
	}
	
	public void setIsRepayEqual(BooleanEnum isRepayEqual) {
		this.isRepayEqual = isRepayEqual;
	}
	
	public ChargeWayEnum getChargeWay() {
		return this.chargeWay;
	}
	
	public void setChargeWay(ChargeWayEnum chargeWay) {
		this.chargeWay = chargeWay;
	}
	
	public BooleanEnum getIsChargeEveryBeginning() {
		return this.isChargeEveryBeginning;
	}
	
	public void setIsChargeEveryBeginning(BooleanEnum isChargeEveryBeginning) {
		this.isChargeEveryBeginning = isChargeEveryBeginning;
	}
	
	public ProjectVoteResultEnum getVoteResult() {
		return this.voteResult;
	}
	
	public void setVoteResult(ProjectVoteResultEnum voteResult) {
		this.voteResult = voteResult;
	}
	
	public BooleanEnum getIsDel() {
		return this.isDel;
	}
	
	public void setIsDel(BooleanEnum isDel) {
		this.isDel = isDel;
	}
	
	public ChargePhaseEnum getChargePhase() {
		return this.chargePhase;
	}
	
	public void setChargePhase(ChargePhaseEnum chargePhase) {
		this.chargePhase = chargePhase;
	}
	
	public String getChargeRemark() {
		return this.chargeRemark;
	}
	
	public void setChargeRemark(String chargeRemark) {
		this.chargeRemark = chargeRemark;
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
	
	public Date getApprovalTime() {
		return this.approvalTime;
	}
	
	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
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
	
	public OneVoteResultEnum getOneVoteDown() {
		return this.oneVoteDown;
	}
	
	public void setOneVoteDown(OneVoteResultEnum oneVoteDown) {
		this.oneVoteDown = oneVoteDown;
	}
	
	public String getOneVoteDownMark() {
		return this.oneVoteDownMark;
	}
	
	public void setOneVoteDownMark(String oneVoteDownMark) {
		this.oneVoteDownMark = oneVoteDownMark;
	}
	
	public Date getOneVoteDownTime() {
		return this.oneVoteDownTime;
	}
	
	public void setOneVoteDownTime(Date oneVoteDownTime) {
		this.oneVoteDownTime = oneVoteDownTime;
	}
	
	public String getOther() {
		return this.other;
	}
	
	public void setOther(String other) {
		this.other = other;
	}
	
	public BooleanEnum getBelongToNc() {
		return this.belongToNc;
	}
	
	public void setBelongToNc(BooleanEnum belongToNc) {
		this.belongToNc = belongToNc;
	}
	
	public String getOverview() {
		return this.overview;
	}
	
	public void setOverview(String overview) {
		this.overview = overview;
	}
	
}
