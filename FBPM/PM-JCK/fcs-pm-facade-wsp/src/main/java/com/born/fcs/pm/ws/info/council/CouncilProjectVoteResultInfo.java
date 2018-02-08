package com.born.fcs.pm.ws.info.council;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilCompereMessageEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.VoteResultEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 上会项目的项目信息和投票结果信息
 * 
 * @author Fei
 * 
 */
public class CouncilProjectVoteResultInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 8890687458703165016L;
	
	private long id;
	
	private long councilProjectId;
	/** 会议ID */
	private long councilId;
	
	/** 表单ID */
	private long formId;
	/** 会议编号 */
	private String councilCode;
	/** 项目编号 */
	private String projectCode;
	/** 项目编号 */
	private String projectName;
	
	/** 客户名称 */
	private String customerName;
	/** 授信类型 */
	private String loanType;
	
	private String busiType;
	
	private String busiTypeName;
	/** 授信金额 */
	private Money amount = new Money(0, 0);
	/** 费用利率（%） */
	private String chargeRate;
	
	/** 授信期限 */
	private int timeLimit;
	/** 授信期限 */
	private TimeUnitEnum timeUnit;
	
	/** 投票统计 */
	private String voteRatio;
	
	/** 投票结果 */
	private ProjectVoteResultEnum projectVoteResult;
	
	private ProjectCouncilCompereMessageEnum compereMessage;
	
	private int judgesCount;
	
	private int passCount;
	
	private int notpassCount;
	
	private int quitCount;
	
	/** 是否被一票否决 */
	private BooleanEnum oneVoteDown;
	
	private BooleanEnum voteStatus;
	
	private VoteResultEnum voteResult;
	
	/** 是否被本次不议 */
	private BooleanEnum riskSecretaryQuit;
	
	private String riskSecretaryQuitMark;
	
	public BooleanEnum getRiskSecretaryQuit() {
		return this.riskSecretaryQuit;
	}
	
	public void setRiskSecretaryQuit(BooleanEnum riskSecretaryQuit) {
		this.riskSecretaryQuit = riskSecretaryQuit;
	}
	
	public ProjectCouncilCompereMessageEnum getCompereMessage() {
		return this.compereMessage;
	}
	
	public void setCompereMessage(ProjectCouncilCompereMessageEnum compereMessage) {
		this.compereMessage = compereMessage;
	}
	
	public long getCouncilProjectId() {
		return this.councilProjectId;
	}
	
	public void setCouncilProjectId(long councilProjectId) {
		this.councilProjectId = councilProjectId;
	}
	
	public String getRiskSecretaryQuitMark() {
		return this.riskSecretaryQuitMark;
	}
	
	public void setRiskSecretaryQuitMark(String riskSecretaryQuitMark) {
		this.riskSecretaryQuitMark = riskSecretaryQuitMark;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getCouncilId() {
		return this.councilId;
	}
	
	public void setCouncilId(long councilId) {
		this.councilId = councilId;
	}
	
	public String getCouncilCode() {
		return this.councilCode;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public BooleanEnum getOneVoteDown() {
		return this.oneVoteDown;
	}
	
	public void setOneVoteDown(BooleanEnum oneVoteDown) {
		this.oneVoteDown = oneVoteDown;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public void setCouncilCode(String councilCode) {
		this.councilCode = councilCode;
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
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getLoanType() {
		return this.loanType;
	}
	
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	
	public String getBusiType() {
		return this.busiType;
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
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public String getChargeRate() {
		return this.chargeRate;
	}
	
	public void setChargeRate(String chargeRate) {
		this.chargeRate = chargeRate;
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
	
	public String getVoteRatio() {
		return this.voteRatio;
	}
	
	public void setVoteRatio(String voteRatio) {
		this.voteRatio = voteRatio;
	}
	
	public ProjectVoteResultEnum getProjectVoteResult() {
		return this.projectVoteResult;
	}
	
	public void setProjectVoteResult(ProjectVoteResultEnum projectVoteResult) {
		this.projectVoteResult = projectVoteResult;
	}
	
	public int getJudgesCount() {
		return this.judgesCount;
	}
	
	public void setJudgesCount(int judgesCount) {
		this.judgesCount = judgesCount;
	}
	
	public int getPassCount() {
		return this.passCount;
	}
	
	public void setPassCount(int passCount) {
		this.passCount = passCount;
	}
	
	public int getNotpassCount() {
		return this.notpassCount;
	}
	
	public void setNotpassCount(int notpassCount) {
		this.notpassCount = notpassCount;
	}
	
	public int getQuitCount() {
		return this.quitCount;
	}
	
	public void setQuitCount(int quitCount) {
		this.quitCount = quitCount;
	}
	
	public BooleanEnum getVoteStatus() {
		return this.voteStatus;
	}
	
	public void setVoteStatus(BooleanEnum voteStatus) {
		this.voteStatus = voteStatus;
	}
	
	public VoteResultEnum getVoteResult() {
		return this.voteResult;
	}
	
	public void setVoteResult(VoteResultEnum voteResult) {
		this.voteResult = voteResult;
	}
	
}
