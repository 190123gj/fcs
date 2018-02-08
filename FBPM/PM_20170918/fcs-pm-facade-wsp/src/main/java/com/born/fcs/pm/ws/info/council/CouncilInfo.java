package com.born.fcs.pm.ws.info.council;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.enums.CouncilStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.VoteRuleTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;

/**
 * 会议信息
 * 
 * 
 * @author Fei
 * 
 */
public class CouncilInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -4422132595560997191L;
	
	private long councilId;
	
	private String councilCode;
	
	private String councilSubject;
	
	private Date startTime;
	
	private Date endTime;
	
	private long councilType;
	
	private CouncilTypeEnum councilTypeCode;
	
	private String councilTypeName;
	
	private String councilPlace;
	
	private BooleanEnum councilOnline;
	
	private CouncilStatusEnum status;
	
	private String summaryUrl;
	
	private long createManId;
	
	private String createManAccount;
	
	private String createManName;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
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
	private String projectsCode;
	private String projectsName;
	private String applyIds;
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
	
	private Double passRate;
	
	private Double indeterminateRate;
	
	private CompanyNameEnum companyName;
	
	public Double getPassRate() {
		return this.passRate;
	}
	
	public CompanyNameEnum getCompanyName() {
		return this.companyName;
	}
	
	public void setCompanyName(CompanyNameEnum companyName) {
		this.companyName = companyName;
	}
	
	public void setPassRate(Double passRate) {
		this.passRate = passRate;
	}
	
	public Double getIndeterminateRate() {
		return this.indeterminateRate;
	}
	
	public void setIndeterminateRate(Double indeterminateRate) {
		this.indeterminateRate = indeterminateRate;
	}
	
	public int getMajorNum() {
		return this.majorNum;
	}
	
	public void setMajorNum(int majorNum) {
		this.majorNum = majorNum;
	}
	
	public int getLessNum() {
		return this.lessNum;
	}
	
	public BooleanEnum getCouncilOnline() {
		return this.councilOnline;
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
	
	public void setCouncilOnline(BooleanEnum councilOnline) {
		this.councilOnline = councilOnline;
	}
	
	public void setLessNum(int lessNum) {
		this.lessNum = lessNum;
	}
	
	private CouncilTypeInfo councilTypeInfo;
	
	//会议纪要
	private FCouncilSummaryInfo summary;
	//会议纪要表单
	private FormInfo summaryForm;
	
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
	
	public BooleanEnum getIfVote() {
		return this.ifVote;
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
	
	public CouncilTypeEnum getCouncilTypeCode() {
		return this.councilTypeCode;
	}
	
	public void setCouncilTypeCode(CouncilTypeEnum councilTypeCode) {
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
	
	public CouncilStatusEnum getStatus() {
		return this.status;
	}
	
	public void setStatus(CouncilStatusEnum status) {
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
	
	public List<CouncilJudgeInfo> getJudges() {
		return this.judges;
	}
	
	public void setJudges(List<CouncilJudgeInfo> judges) {
		this.judges = judges;
		if (this.judges != null && this.judges.size() > 0) {
			StringBuffer nameStrb = new StringBuffer();
			StringBuffer idStrb = new StringBuffer();
			StringBuffer keyStrb = new StringBuffer();
			for (CouncilJudgeInfo i : this.judges) {
				nameStrb.append(",");
				nameStrb.append(i.getJudgeName());
				idStrb.append(",");
				idStrb.append(i.getJudgeId());
				keyStrb.append(",");
				keyStrb.append(i.getId());
			}
			this.judgesName = nameStrb.toString().replaceFirst(",", "");
			this.judgesId = idStrb.toString().replaceFirst(",", "");
			this.judgesKey = keyStrb.toString().replaceFirst(",", "");
		}
		
	}
	
	public List<CouncilParticipantInfo> getParticipants() {
		return this.participants;
	}
	
	public void setParticipants(List<CouncilParticipantInfo> participants) {
		this.participants = participants;
		if (this.participants != null && this.participants.size() > 0) {
			StringBuffer nameStrb = new StringBuffer();
			StringBuffer idb = new StringBuffer();
			StringBuffer keyStrb = new StringBuffer();
			for (CouncilParticipantInfo i : this.participants) {
				nameStrb.append(",");
				nameStrb.append(i.getParticipantName());
				idb.append(",");
				idb.append(i.getParticipantId());
				keyStrb.append(",");
				keyStrb.append(i.getId());
			}
			this.participantsName = nameStrb.toString().replaceFirst(",", "");
			this.participantsId = idb.toString().replaceFirst(",", "");
			this.participantsKey = keyStrb.toString().replaceFirst(",", "");
		}
		
	}
	
	public List<CouncilProjectInfo> getProjects() {
		return this.projects;
	}
	
	public void setProjects(List<CouncilProjectInfo> projects) {
		this.projects = projects;
		if (this.projects != null && this.projects.size() > 0) {
			StringBuffer nameStrb = new StringBuffer();
			StringBuffer codeStrb = new StringBuffer();
			StringBuffer applyIdStrb = new StringBuffer();
			StringBuffer keyStrb = new StringBuffer();
			for (CouncilProjectInfo i : this.projects) {
				nameStrb.append(",");
				nameStrb.append(i.getProjectName());
				codeStrb.append(",");
				codeStrb.append(i.getProjectCode());
				applyIdStrb.append(",");
				applyIdStrb.append(i.getApplyId());
				keyStrb.append(",");
				keyStrb.append(i.getId());
			}
			this.projectsName = nameStrb.toString().replaceFirst(",", "");
			this.projectsCode = codeStrb.toString().replaceFirst(",", "");
			this.applyIds = applyIdStrb.toString().replaceFirst(",", "");
			this.projectsKey = keyStrb.toString().replaceFirst(",", "");
		}
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
	
	public String getJudgesName() {
		return this.judgesName;
	}
	
	public void setJudgesName(String judgesName) {
		this.judgesName = judgesName;
	}
	
	public String getParticipantsId() {
		return this.participantsId;
	}
	
	public void setParticipantsId(String participantsId) {
		this.participantsId = participantsId;
	}
	
	public String getParticipantsName() {
		return this.participantsName;
	}
	
	public void setParticipantsName(String participantsName) {
		this.participantsName = participantsName;
	}
	
	public String getProjectsCode() {
		return this.projectsCode;
	}
	
	public void setProjectsCode(String projectsCode) {
		this.projectsCode = projectsCode;
	}
	
	public String getProjectsName() {
		return this.projectsName;
	}
	
	public void setProjectsName(String projectsName) {
		this.projectsName = projectsName;
	}
	
	public String getApplyIds() {
		return this.applyIds;
	}
	
	public void setApplyIds(String applyIds) {
		this.applyIds = applyIds;
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
	
	public CouncilTypeInfo getCouncilTypeInfo() {
		return this.councilTypeInfo;
	}
	
	public void setCouncilTypeInfo(CouncilTypeInfo councilTypeInfo) {
		this.councilTypeInfo = councilTypeInfo;
	}
	
	public FCouncilSummaryInfo getSummary() {
		return this.summary;
	}
	
	public void setSummary(FCouncilSummaryInfo summary) {
		this.summary = summary;
	}
	
	public FormInfo getSummaryForm() {
		return this.summaryForm;
	}
	
	public void setSummaryForm(FormInfo summaryForm) {
		this.summaryForm = summaryForm;
	}
	
	public String getSummaryUrl() {
		return this.summaryUrl;
	}
	
	public void setSummaryUrl(String summaryUrl) {
		this.summaryUrl = summaryUrl;
	}
	
}
