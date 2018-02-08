package com.born.fcs.pm.ws.order.council;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.enums.VoteRuleTypeEnum;
import com.born.fcs.pm.ws.info.council.CouncilJudgeInfo;
import com.born.fcs.pm.ws.info.council.CouncilParticipantInfo;
import com.born.fcs.pm.ws.info.council.CouncilProjectInfo;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

public class CouncilOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -727971658022471275L;
	
	private long councilId;
	
	private String councilCode;
	
	private String councilSubject;
	
	private Date startTime;
	
	private Date endTime;
	
	private long councilType;
	
	private String councilTypeCode;
	
	private String councilTypeName;
	
	private String councilPlace;
	
	private BooleanEnum councilOnline;
	
	private String status;
	
	private long createManId;
	
	private String createManAccount;
	
	private String createManName;
	
	/** 主持人 */
	private Long compereId;
	private String compereName;
	
	/** 参会评委 */
	private List<CouncilJudgeInfo> judges;
	private String judgesKey;
	private String judgesId;
	private String judgesName;
	/** 列席人员 */
	private List<CouncilParticipantInfo> participants;
	private String participantsKey;
	private String participantsId;
	private String participantsName;
	/** 参会项目 */
	private List<CouncilProjectInfo> projects;
	private String projectsKey;
	private String applyIds;
	private String projectsCode;
	private String projectsName;
	
	/** 决策机构ID */
	private long decisionInstitutionId;
	/** 决策机构名 */
	private long decisionInstitutionName;
	
	private int majorNum;
	
	private int lessNum;
	
	private BooleanEnum ifVote;
	
	private VoteRuleTypeEnum voteRuleType;
	
	private int passNum;
	
	private int indeterminateNum;
	
	private CompanyNameEnum companyName;
	
	public BooleanEnum getIfVote() {
		return this.ifVote;
	}
	
	public CompanyNameEnum getCompanyName() {
		return this.companyName;
	}
	
	public void setCompanyName(CompanyNameEnum companyName) {
		this.companyName = companyName;
	}
	
	public int getMajorNum() {
		return this.majorNum;
	}
	
	public void setMajorNum(int majorNum) {
		this.majorNum = majorNum;
	}
	
	public Long getCompereId() {
		return this.compereId;
	}
	
	public void setCompereId(Long compereId) {
		this.compereId = compereId;
	}
	
	public String getCompereName() {
		return this.compereName;
	}
	
	public void setCompereName(String compereName) {
		this.compereName = compereName;
	}
	
	public int getLessNum() {
		return this.lessNum;
	}
	
	public BooleanEnum getCouncilOnline() {
		return this.councilOnline;
	}
	
	public void setCouncilOnline(BooleanEnum councilOnline) {
		this.councilOnline = councilOnline;
	}
	
	public void setLessNum(int lessNum) {
		this.lessNum = lessNum;
	}
	
	public void setIfVote(BooleanEnum ifVote) {
		this.ifVote = ifVote;
	}
	
	public VoteRuleTypeEnum getVoteRuleType() {
		return this.voteRuleType;
	}
	
	public void setVoteRuleType(VoteRuleTypeEnum voteRuleType) {
		this.voteRuleType = voteRuleType;
	}
	
	public int getPassNum() {
		return this.passNum;
	}
	
	public void setPassNum(int passNum) {
		this.passNum = passNum;
	}
	
	public int getIndeterminateNum() {
		return this.indeterminateNum;
	}
	
	public void setIndeterminateNum(int indeterminateNum) {
		this.indeterminateNum = indeterminateNum;
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
	
	public String getCouncilSubject() {
		return this.councilSubject;
	}
	
	public void setCouncilSubject(String councilSubject) {
		this.councilSubject = councilSubject;
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
	
	public long getCouncilType() {
		return this.councilType;
	}
	
	public void setCouncilType(long councilType) {
		this.councilType = councilType;
	}
	
	public String getCouncilTypeCode() {
		return this.councilTypeCode;
	}
	
	public void setCouncilTypeCode(String councilTypeCode) {
		this.councilTypeCode = councilTypeCode;
	}
	
	public String getCouncilTypeName() {
		return this.councilTypeName;
	}
	
	public void setCouncilTypeName(String councilTypeName) {
		this.councilTypeName = councilTypeName;
	}
	
	public String getCouncilPlace() {
		return this.councilPlace;
	}
	
	public void setCouncilPlace(String councilPlace) {
		this.councilPlace = councilPlace;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public long getCreateManId() {
		return this.createManId;
	}
	
	public void setCreateManId(long createManId) {
		this.createManId = createManId;
	}
	
	public String getCreateManAccount() {
		return this.createManAccount;
	}
	
	public void setCreateManAccount(String createManAccount) {
		this.createManAccount = createManAccount;
	}
	
	public String getCreateManName() {
		return this.createManName;
	}
	
	public void setCreateManName(String createManName) {
		this.createManName = createManName;
	}
	
	public List<CouncilJudgeInfo> getJudges() {
		return this.judges;
	}
	
	public void setJudges(List<CouncilJudgeInfo> judges) {
		this.judges = judges;
	}
	
	public List<CouncilParticipantInfo> getParticipants() {
		return this.participants;
	}
	
	public void setParticipants(List<CouncilParticipantInfo> participants) {
		this.participants = participants;
	}
	
	public List<CouncilProjectInfo> getProjects() {
		return this.projects;
	}
	
	public void setProjects(List<CouncilProjectInfo> projects) {
		this.projects = projects;
	}
	
	public long getDecisionInstitutionId() {
		return this.decisionInstitutionId;
	}
	
	public void setDecisionInstitutionId(long decisionInstitutionId) {
		this.decisionInstitutionId = decisionInstitutionId;
	}
	
	public long getDecisionInstitutionName() {
		return this.decisionInstitutionName;
	}
	
	public void setDecisionInstitutionName(long decisionInstitutionName) {
		this.decisionInstitutionName = decisionInstitutionName;
	}
	
	public String getJudgesId() {
		return this.judgesId;
	}
	
	public void setJudgesId(String judgesId) {
		this.judgesId = judgesId;
	}
	
	public String getParticipantsId() {
		return this.participantsId;
	}
	
	public void setParticipantsId(String participantsId) {
		this.participantsId = participantsId;
	}
	
	public String getApplyIds() {
		return this.applyIds;
	}
	
	public void setApplyIds(String applyIds) {
		this.applyIds = applyIds;
	}
	
	public String getJudgesName() {
		return this.judgesName;
	}
	
	public void setJudgesName(String judgesName) {
		this.judgesName = judgesName;
	}
	
	public String getParticipantsName() {
		return this.participantsName;
	}
	
	public void setParticipantsName(String participantsName) {
		this.participantsName = participantsName;
	}
	
	public String getProjectsName() {
		return this.projectsName;
	}
	
	public void setProjectsName(String projectsName) {
		this.projectsName = projectsName;
	}
	
	public String getProjectsCode() {
		return this.projectsCode;
	}
	
	public void setProjectsCode(String projectsCode) {
		this.projectsCode = projectsCode;
	}
	
	public String getJudgesKey() {
		return this.judgesKey;
	}
	
	public void setJudgesKey(String judgesKey) {
		this.judgesKey = judgesKey;
	}
	
	public String getParticipantsKey() {
		return this.participantsKey;
	}
	
	public void setParticipantsKey(String participantsKey) {
		this.participantsKey = participantsKey;
	}
	
	public String getProjectsKey() {
		return this.projectsKey;
	}
	
	public void setProjectsKey(String projectsKey) {
		this.projectsKey = projectsKey;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CouncilOrder [councilId=");
		builder.append(councilId);
		builder.append(", councilCode=");
		builder.append(councilCode);
		builder.append(", councilSubject=");
		builder.append(councilSubject);
		builder.append(", startTime=");
		builder.append(startTime);
		builder.append(", endTime=");
		builder.append(endTime);
		builder.append(", councilType=");
		builder.append(councilType);
		builder.append(", councilTypeName=");
		builder.append(councilTypeName);
		builder.append(", councilPlace=");
		builder.append(councilPlace);
		builder.append(", status=");
		builder.append(status);
		builder.append(", createManId=");
		builder.append(createManId);
		builder.append(", createManAccount=");
		builder.append(createManAccount);
		builder.append(", createManName=");
		builder.append(createManName);
		builder.append("]");
		return builder.toString();
	}
}
