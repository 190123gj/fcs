package com.born.fcs.pm.ws.info.common;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目批复信息
 *
 *
 * @author wuzj
 *
 */
public class ProjectApprovaInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -5303985310138632240L;
	
	private long spId;
	
	private String spCode;
	
	private String projectCode;
	
	private String projectName;
	
	private long customerId;
	
	private String customerName;
	
	private CustomerTypeEnum customerType;
	
	private String busiType;
	
	private String busiTypeName;
	
	private Money amount = new Money(0, 0);
	
	private int timeLimit;
	
	private TimeUnitEnum timeUnit;
	
	private Date approvalTime;
	
	/** 投票结果 */
	private ProjectVoteResultEnum voteResult;
	/** 是否作废 */
	private BooleanEnum isDel;
	/** 投票结果 */
	private String voteResultDesc;
	/** 是否一票否决 */
	private BooleanEnum oneVoteDown;
	/** 一票否决说明 */
	private String oneVoteDownMark;
	/** 一票否决时间 */
	private Date oneVoteDownTime;
	
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
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
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
	
	public BooleanEnum getIsDel() {
		return this.isDel;
	}
	
	public void setIsDel(BooleanEnum isDel) {
		this.isDel = isDel;
	}
	
	public Date getApprovalTime() {
		return this.approvalTime;
	}
	
	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}
	
	public ProjectVoteResultEnum getVoteResult() {
		return this.voteResult;
	}
	
	public void setVoteResult(ProjectVoteResultEnum voteResult) {
		this.voteResult = voteResult;
	}
	
	public String getVoteResultDesc() {
		return this.voteResultDesc;
	}
	
	public void setVoteResultDesc(String voteResultDesc) {
		this.voteResultDesc = voteResultDesc;
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
	
	public Date getOneVoteDownTime() {
		return this.oneVoteDownTime;
	}
	
	public void setOneVoteDownTime(Date oneVoteDownTime) {
		this.oneVoteDownTime = oneVoteDownTime;
	}
	
}
