package com.born.fcs.pm.ws.order.council;

import org.springframework.util.Assert;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.StringUtil;

public class CouncilProjectVoteOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 4744358629538120737L;
	
	private long id;
	
	private long councilId;
	
	private String councilCode;
	
	private String projectCode;
	
	private String projectName;
	
	private long judgeId;
	
	private String judgeName;
	
	private long roleId;
	
	private String roleName;
	
	private String orgName;
	
	private BooleanEnum voteStatus;
	
	private String voteResult;
	
	private String voteResultDesc;
	
	private String voteRemark;
	
	private int sortOrder;
	
	private boolean isRiskSecretary;
	
	public boolean isRiskSecretary() {
		return isRiskSecretary;
	}
	
	public void setRiskSecretary(boolean isRiskSecretary) {
		this.isRiskSecretary = isRiskSecretary;
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
	
	public String getOrgName() {
		return this.orgName;
	}
	
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	
	public long getJudgeId() {
		return this.judgeId;
	}
	
	public void setJudgeId(long judgeId) {
		this.judgeId = judgeId;
	}
	
	public String getJudgeName() {
		return this.judgeName;
	}
	
	public void setJudgeName(String judgeName) {
		this.judgeName = judgeName;
	}
	
	public long getRoleId() {
		return this.roleId;
	}
	
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	
	public String getRoleName() {
		return this.roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public BooleanEnum getVoteStatus() {
		return this.voteStatus;
	}
	
	public void setVoteStatus(BooleanEnum voteStatus) {
		this.voteStatus = voteStatus;
	}
	
	public String getVoteResult() {
		return this.voteResult;
	}
	
	public void setVoteResult(String voteResult) {
		this.voteResult = voteResult;
	}
	
	public String getVoteResultDesc() {
		return this.voteResultDesc;
	}
	
	public void setVoteResultDesc(String voteResultDesc) {
		this.voteResultDesc = voteResultDesc;
	}
	
	public String getVoteRemark() {
		return this.voteRemark;
	}
	
	public void setVoteRemark(String voteRemark) {
		this.voteRemark = voteRemark;
	}
	
	public int getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	@Override
	public void check() {
		validateNotNull(voteResult, "投票结果");
		Assert
			.isTrue(
				(id > 0 || (councilId > 0 && StringUtil.isNotEmpty(this.projectCode) && this.judgeId > 0)),
				"必须选定会议的某个项目才能投票");
		
	}
}
