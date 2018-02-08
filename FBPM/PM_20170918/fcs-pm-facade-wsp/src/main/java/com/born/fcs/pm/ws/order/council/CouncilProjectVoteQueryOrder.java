package com.born.fcs.pm.ws.order.council;

import com.born.fcs.pm.ws.order.base.FcsQueryPageBase;

public class CouncilProjectVoteQueryOrder extends FcsQueryPageBase {
	
	private static final long serialVersionUID = 8244837707828125966L;
	
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
	/** 投票状态(是否已投票) */
	private String voteStatus;
	/** 投票结果 */
	private String voteResult;
	
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
	
	public String getVoteStatus() {
		return this.voteStatus;
	}
	
	public void setVoteStatus(String voteStatus) {
		this.voteStatus = voteStatus;
	}
	
	public String getVoteResult() {
		return this.voteResult;
	}
	
	public void setVoteResult(String voteResult) {
		this.voteResult = voteResult;
	}
	
}
