package com.born.fcs.pm.ws.order.council;

import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.order.base.FcsQueryPageBase;

public class CouncilVoteProjectQueryOrder extends FcsQueryPageBase {
	
	private static final long serialVersionUID = 6970309888327414230L;
	
	private long councilId;
	
	private String councilCode;
	
	private String projectCode;
	
	private String projectName;
	
	/** 投票结果 */
	private ProjectVoteResultEnum projectVoteResult;
	
	private String busiType;
	/** 授信类型 */
	private String busiTypeName;
	
	private long judgeId;
	
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
	
	public ProjectVoteResultEnum getProjectVoteResult() {
		return this.projectVoteResult;
	}
	
	public void setProjectVoteResult(ProjectVoteResultEnum projectVoteResult) {
		this.projectVoteResult = projectVoteResult;
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
	
	public long getJudgeId() {
		return this.judgeId;
	}
	
	public void setJudgeId(long judgeId) {
		this.judgeId = judgeId;
	}
	
}
