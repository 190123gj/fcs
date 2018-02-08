package com.born.fcs.pm.dataobject;

public class CouncilVoteProjectDO extends com.born.fcs.pm.dal.dataobject.ProjectDO {
	
	private static final long serialVersionUID = -8451984063149932283L;
	
	private long id;
	
	private long councilProjectId;
	
	private long councilId;
	
	private long formId;
	
	private String councilCode;
	
	private String projectCode;
	
	private String projectName;
	
	private int sortOrder;
	
	private String projectVoteResult;
	
	private String compereMessage;
	
	private int judgesCount;
	
	private int passCount;
	
	private int notpassCount;
	
	private int quitCount;
	
	private String oneVoteDown;
	
	private String oneVoteDownMark;
	
	private String voteStatus;
	
	private String voteResult;
	
	private long judgeId;
	
	private String riskSecretaryQuit;
	
	private String riskSecretaryQuitMark;
	
	public String getRiskSecretaryQuit() {
		return this.riskSecretaryQuit;
	}
	
	public void setRiskSecretaryQuit(String riskSecretaryQuit) {
		this.riskSecretaryQuit = riskSecretaryQuit;
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
	
	public long getCouncilProjectId() {
		return this.councilProjectId;
	}
	
	public void setCouncilProjectId(long councilProjectId) {
		this.councilProjectId = councilProjectId;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getCompereMessage() {
		return this.compereMessage;
	}
	
	public void setCompereMessage(String compereMessage) {
		this.compereMessage = compereMessage;
	}
	
	public long getCouncilId() {
		return this.councilId;
	}
	
	public void setCouncilId(long councilId) {
		this.councilId = councilId;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
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
	
	public String getOneVoteDown() {
		return this.oneVoteDown;
	}
	
	public void setOneVoteDown(String oneVoteDown) {
		this.oneVoteDown = oneVoteDown;
	}
	
	public String getOneVoteDownMark() {
		return this.oneVoteDownMark;
	}
	
	public void setOneVoteDownMark(String oneVoteDownMark) {
		this.oneVoteDownMark = oneVoteDownMark;
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
	
	public String getProjectVoteResult() {
		return this.projectVoteResult;
	}
	
	public void setProjectVoteResult(String projectVoteResult) {
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
	
	public long getJudgeId() {
		return this.judgeId;
	}
	
	public void setJudgeId(long judgeId) {
		this.judgeId = judgeId;
	}
	
}
