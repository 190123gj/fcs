package com.born.fcs.pm.ws.info.council;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilCompereMessageEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

public class CouncilProjectInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 6970309888327414230L;
	
	private long id;
	
	private String councilId;
	
	private String councilCode;
	
	private long applyId;
	
	private String projectCode;
	
	private String projectName;
	
	private int sortOrder;
	/** 投票结果 */
	private ProjectVoteResultEnum projectVoteResult;
	
	private ProjectCouncilCompereMessageEnum compereMessage;
	
	private int judgesCount;
	
	private int passCount;
	
	private int notpassCount;
	
	private int quitCount;
	
	/** 是否被本次不议 */
	private BooleanEnum riskSecretaryQuit;
	/** 被本次不议的原因 */
	private String riskSecretaryQuitMark;
	/** 是否被一票否决 */
	private BooleanEnum oneVoteDown;
	/** 被一票否决的原因 */
	private String oneVoteDownMark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private String customerName;
	
	private String busiType;
	/** 授信类型 */
	private String busiTypeName;
	
	private Money amount = new Money(0, 0);;
	
	private double chargeRate;
	
	private int timeLimit;
	
	private TimeUnitEnum timeUnit;
	
	public long getApplyId() {
		return this.applyId;
	}
	
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
	
	public String getRiskSecretaryQuitMark() {
		return this.riskSecretaryQuitMark;
	}
	
	public void setRiskSecretaryQuitMark(String riskSecretaryQuitMark) {
		this.riskSecretaryQuitMark = riskSecretaryQuitMark;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getCouncilId() {
		return this.councilId;
	}
	
	public void setCouncilId(String councilId) {
		this.councilId = councilId;
	}
	
	public String getCouncilCode() {
		return this.councilCode;
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
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public double getChargeRate() {
		return this.chargeRate;
	}
	
	public void setChargeRate(double chargeRate) {
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
	
	public BooleanEnum getOneVoteDown() {
		return this.oneVoteDown;
	}
	
	public void setOneVoteDown(BooleanEnum oneVoteDown) {
		this.oneVoteDown = oneVoteDown;
	}
	
	public String getOneVoteDownMark() {
		return this.oneVoteDownMark;
	}
	
	public void setOneVoteDownMark(String oneVoteDownMark) {
		this.oneVoteDownMark = oneVoteDownMark;
	}
	
}
