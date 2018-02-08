package com.born.fcs.pm.ws.info.council;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.VoteResultEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

public class CouncilProjectVoteInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 8890687458703165016L;
	
	private long id;
	
	private long councilProjectId;
	/** 会议ID */
	private long councilId;
	/** 会议编号 */
	private String councilCode;
	/** 项目编号 */
	private String projectCode;
	/** 项目编号 */
	private String projectName;
	/** 评委ID */
	private long judgeId;
	/** 评委名称 */
	private String judgeName;
	/** 评委角色ID */
	private long roleId;
	/** 评委角色名称 */
	private String roleName;
	/** 评委角色主部门 */
	private String orgName;
	/** 投票状态(是否已投票) */
	private BooleanEnum voteStatus;
	/** 投票结果 */
	private VoteResultEnum voteResult;
	/** 投票结果备注 */
	private String voteRemark;
	/** */
	private int sortOrder;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
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
	
	public void setCouncilCode(String councilCode) {
		this.councilCode = councilCode;
	}
	
	public String getOrgName() {
		return this.orgName;
	}
	
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	
	public VoteResultEnum getVoteResult() {
		return this.voteResult;
	}
	
	public void setVoteResult(VoteResultEnum voteResult) {
		this.voteResult = voteResult;
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
	
	public long getCouncilProjectId() {
		return this.councilProjectId;
	}
	
	public void setCouncilProjectId(long councilProjectId) {
		this.councilProjectId = councilProjectId;
	}
	
}
