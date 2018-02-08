package com.born.fcs.pm.ws.order.common;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.google.common.collect.Lists;

public class ProjectQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = -7878575429297405697L;
	
	/** ID */
	private long projectId;
	/** 客户类型 */
	private CustomerTypeEnum customerType;
	/** 业务经理B ID */
	private long busiManagerbId;
	
	/** 法务经理 ID */
	private long legalManagerId;
	/** 业务经理B 名称 */
	private String busiManagerbName;
	/***
	 * 风险经理名称
	 */
	private String riskManager;
	/** 部门ID */
	private long deptId;
	/** 部门名称 */
	private String deptName;
	/** 部门路径 */
	private String deptPath;
	/** 部门路径 */
	private String deptPathName;
	/** 部门编码 */
	private String deptCode;
	/** 业务类型 */
	private String busiType;
	/** 业务类型 */
	private String busiSubType;
	/** 业务类型名称 */
	private String busiTypeName;
	/** 项目阶段 */
	private ProjectPhasesEnum phases;
	List<ProjectPhasesEnum> phasesList = Lists.newArrayList();
	
	/** 项目阶段状态 */
	private ProjectPhasesStatusEnum phasesStatus;
	List<ProjectPhasesStatusEnum> phasesStatusList = Lists.newArrayList();
	
	/** 项目状态 */
	private ProjectStatusEnum status;
	List<ProjectStatusEnum> statusList = Lists.newArrayList();
	
	/** 是否手动停止继续发售(承销/发债发售信息维护) */
	private BooleanEnum isContinue;
	/** 是否批复 */
	private BooleanEnum isApproval;
	/** 是否批复作废 */
	private BooleanEnum isApprovalDel;
	/** 是否可复议 YES:表示查询可复议条件的，IS、NO表示查询is_recouncil对应值得 */
	private BooleanEnum isRecouncil;
	/** 会议纪要通过时间 */
	private Date approvalTimeStart;
	private Date approvalTimeEnd;
	
	/** 业务类型 */
	List<String> busiTypeList = Lists.newArrayList();
	
	/** 还有放款金额 */
	private BooleanEnum hasLoanAmount;
	/** 还有用款金额 */
	private BooleanEnum hasUseAmount;
	/** 还有放款金额或者用款金额 */
	private BooleanEnum hasBothAmount;
	/** 是否有收费经 */
	private BooleanEnum hasChargeAmount;
	/** 承销发债是否有发行金额 */
	private BooleanEnum hasIssueAmount;
	/** 是否有代偿金额 */
	private BooleanEnum hasCompensatoryAmount;
	/** 合同是否签订 */
	private BooleanEnum hasContract;
	/** 合同签订时间 */
	private Date contractTimeStart;
	private Date contractTimeEnd;
	/** 子系统对应表单类型 */
	private String dockFormType;
	/** 是否解保中 */
	private String isReleasing;
	/** 查询适用于新增追偿的用户id **/
	private String recoverySelecterId;
	
	/** 诉保项目是否上传法院裁定书 **/
	private BooleanEnum hasCourtRuling;
	
	/** 是否属于南充分公司项目 YES/NO */
	private String belongToNc;
	
	/** 是否重新授信 IS/NO */
	private String isRedo;
	/** 是否重新授信衍生项目 IS/NO */
	private String isRedoProject;
	/** 从哪个项目（项目编号）重新授信而来 */
	private String redoFrom;
	
	private String isHisProject;
	
	public BooleanEnum getHasCourtRuling() {
		return hasCourtRuling;
	}
	
	public void setHasCourtRuling(BooleanEnum hasCourtRuling) {
		this.hasCourtRuling = hasCourtRuling;
	}
	
	public String getRecoverySelecterId() {
		return this.recoverySelecterId;
	}
	
	public void setRecoverySelecterId(String recoverySelecterId) {
		this.recoverySelecterId = recoverySelecterId;
	}
	
	public long getProjectId() {
		return this.projectId;
	}
	
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	
	public long getBusiManagerbId() {
		return this.busiManagerbId;
	}
	
	public long getLegalManagerId() {
		return this.legalManagerId;
	}
	
	public void setLegalManagerId(long legalManagerId) {
		this.legalManagerId = legalManagerId;
	}
	
	public void setBusiManagerbId(long busiManagerbId) {
		this.busiManagerbId = busiManagerbId;
	}
	
	public String getBusiManagerbName() {
		return this.busiManagerbName;
	}
	
	public void setBusiManagerbName(String busiManagerbName) {
		this.busiManagerbName = busiManagerbName;
	}
	
	public String getRiskManager() {
		return this.riskManager;
	}
	
	public void setRiskManager(String riskManager) {
		this.riskManager = riskManager;
	}
	
	public long getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getDeptPath() {
		return this.deptPath;
	}
	
	public void setDeptPath(String deptPath) {
		this.deptPath = deptPath;
	}
	
	public String getDeptPathName() {
		return this.deptPathName;
	}
	
	public void setDeptPathName(String deptPathName) {
		this.deptPathName = deptPathName;
	}
	
	public String getDeptCode() {
		return this.deptCode;
	}
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	@Override
	public String getBusiType() {
		return this.busiType;
	}
	
	@Override
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiSubType() {
		return this.busiSubType;
	}
	
	public void setBusiSubType(String busiSubType) {
		this.busiSubType = busiSubType;
	}
	
	public String getBusiTypeName() {
		return this.busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public ProjectPhasesEnum getPhases() {
		return this.phases;
	}
	
	public void setPhases(ProjectPhasesEnum phases) {
		this.phases = phases;
	}
	
	public ProjectStatusEnum getStatus() {
		return this.status;
	}
	
	public void setStatus(ProjectStatusEnum status) {
		this.status = status;
	}
	
	public ProjectPhasesStatusEnum getPhasesStatus() {
		return this.phasesStatus;
	}
	
	public void setPhasesStatus(ProjectPhasesStatusEnum phasesStatus) {
		this.phasesStatus = phasesStatus;
	}
	
	public List<ProjectPhasesEnum> getPhasesList() {
		return phasesList;
	}
	
	public void setPhasesList(List<ProjectPhasesEnum> phasesList) {
		this.phasesList = phasesList;
	}
	
	public List<ProjectPhasesStatusEnum> getPhasesStatusList() {
		return phasesStatusList;
	}
	
	public void setPhasesStatusList(List<ProjectPhasesStatusEnum> phasesStatusList) {
		this.phasesStatusList = phasesStatusList;
	}
	
	public List<ProjectStatusEnum> getStatusList() {
		return this.statusList;
	}
	
	public void setStatusList(List<ProjectStatusEnum> statusList) {
		this.statusList = statusList;
	}
	
	public List<String> getBusiTypeList() {
		return this.busiTypeList;
	}
	
	public void setBusiTypeList(List<String> busiTypeList) {
		this.busiTypeList = busiTypeList;
	}
	
	public BooleanEnum getHasLoanAmount() {
		return this.hasLoanAmount;
	}
	
	public void setHasLoanAmount(BooleanEnum hasLoanAmount) {
		this.hasLoanAmount = hasLoanAmount;
	}
	
	public BooleanEnum getHasUseAmount() {
		return this.hasUseAmount;
	}
	
	public void setHasUseAmount(BooleanEnum hasUseAmount) {
		this.hasUseAmount = hasUseAmount;
	}
	
	public BooleanEnum getHasBothAmount() {
		return this.hasBothAmount;
	}
	
	public void setHasBothAmount(BooleanEnum hasBothAmount) {
		this.hasBothAmount = hasBothAmount;
	}
	
	public BooleanEnum getIsContinue() {
		return isContinue;
	}
	
	public void setIsContinue(BooleanEnum isContinue) {
		this.isContinue = isContinue;
	}
	
	public CustomerTypeEnum getCustomerType() {
		return customerType;
	}
	
	public void setCustomerType(CustomerTypeEnum customerType) {
		this.customerType = customerType;
	}
	
	public BooleanEnum getIsApproval() {
		return this.isApproval;
	}
	
	public void setIsApproval(BooleanEnum isApproval) {
		this.isApproval = isApproval;
	}
	
	public BooleanEnum getIsApprovalDel() {
		return isApprovalDel;
	}
	
	public void setIsApprovalDel(BooleanEnum isApprovalDel) {
		this.isApprovalDel = isApprovalDel;
	}
	
	public Date getApprovalTimeStart() {
		return this.approvalTimeStart;
	}
	
	public void setApprovalTimeStart(Date approvalTimeStart) {
		this.approvalTimeStart = approvalTimeStart;
	}
	
	public Date getApprovalTimeEnd() {
		return this.approvalTimeEnd;
	}
	
	public void setApprovalTimeEnd(Date approvalTimeEnd) {
		this.approvalTimeEnd = approvalTimeEnd;
	}
	
	public BooleanEnum getHasContract() {
		return this.hasContract;
	}
	
	public void setHasContract(BooleanEnum hasContract) {
		this.hasContract = hasContract;
	}
	
	public Date getContractTimeStart() {
		return this.contractTimeStart;
	}
	
	public void setContractTimeStart(Date contractTimeStart) {
		this.contractTimeStart = contractTimeStart;
	}
	
	public Date getContractTimeEnd() {
		return this.contractTimeEnd;
	}
	
	public void setContractTimeEnd(Date contractTimeEnd) {
		this.contractTimeEnd = contractTimeEnd;
	}
	
	public BooleanEnum getIsRecouncil() {
		return this.isRecouncil;
	}
	
	public void setIsRecouncil(BooleanEnum isRecouncil) {
		this.isRecouncil = isRecouncil;
	}
	
	public BooleanEnum getHasChargeAmount() {
		return this.hasChargeAmount;
	}
	
	public void setHasChargeAmount(BooleanEnum hasChargeAmount) {
		this.hasChargeAmount = hasChargeAmount;
	}
	
	public BooleanEnum getHasIssueAmount() {
		return this.hasIssueAmount;
	}
	
	public void setHasIssueAmount(BooleanEnum hasIssueAmount) {
		this.hasIssueAmount = hasIssueAmount;
	}
	
	public BooleanEnum getHasCompensatoryAmount() {
		return hasCompensatoryAmount;
	}
	
	public void setHasCompensatoryAmount(BooleanEnum hasCompensatoryAmount) {
		this.hasCompensatoryAmount = hasCompensatoryAmount;
	}
	
	public String getDockFormType() {
		return dockFormType;
	}
	
	public void setDockFormType(String dockFormType) {
		this.dockFormType = dockFormType;
	}
	
	public String getIsReleasing() {
		return isReleasing;
	}
	
	public void setIsReleasing(String isReleasing) {
		this.isReleasing = isReleasing;
	}
	
	public String getBelongToNc() {
		return this.belongToNc;
	}
	
	public void setBelongToNc(String belongToNc) {
		this.belongToNc = belongToNc;
	}
	
	public String getIsRedo() {
		return this.isRedo;
	}
	
	public void setIsRedo(String isRedo) {
		this.isRedo = isRedo;
	}
	
	public String getIsRedoProject() {
		return this.isRedoProject;
	}
	
	public void setIsRedoProject(String isRedoProject) {
		this.isRedoProject = isRedoProject;
	}
	
	public String getRedoFrom() {
		return this.redoFrom;
	}
	
	public void setRedoFrom(String redoFrom) {
		this.redoFrom = redoFrom;
	}
	
	public String getIsHisProject() {
		return this.isHisProject;
	}
	
	public void setIsHisProject(String isHisProject) {
		this.isHisProject = isHisProject;
	}
	
}
