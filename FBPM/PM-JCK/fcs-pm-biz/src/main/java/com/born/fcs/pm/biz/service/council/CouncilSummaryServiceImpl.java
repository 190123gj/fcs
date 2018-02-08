package com.born.fcs.pm.biz.service.council;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.CommonAttachmentDO;
import com.born.fcs.pm.dal.dataobject.CouncilApplyDO;
import com.born.fcs.pm.dal.dataobject.CouncilDO;
import com.born.fcs.pm.dal.dataobject.CouncilProjectDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectBondDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectChargeWayDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectEntrustedDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectGuaranteeDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectGuarantorDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectLgLitigationDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectPledgeAssetDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectProcessControlDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectRepayWayDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectUnderwritingDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryRiskHandleDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCreditSchemeDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCreditSchemeGuarantorDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCreditSchemePledgeAssetDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationCreditSchemeProcessControlDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationLitigationDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationUnderwritingDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChargePhaseEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.ChargeWayEnum;
import com.born.fcs.pm.ws.enums.ChargeWayPhaseEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.enums.CompareEnum;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.GuaranteeTypeEnum;
import com.born.fcs.pm.ws.enums.GuarantorTypeEnum;
import com.born.fcs.pm.ws.enums.ProcessControlEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.enums.RepayWayEnum;
import com.born.fcs.pm.ws.enums.RepayWayPhaseEnum;
import com.born.fcs.pm.ws.enums.SysDateSeqNameEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.council.CouncilInfo;
import com.born.fcs.pm.ws.info.council.CouncilProjectInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectBondInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectChargeWayInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectCreditConditionInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectEntrustedInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectGuaranteeInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectGuarantorInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectLgLitigationInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectPledgeAssetInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectProcessControlInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectRepayWayInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectUnderwritingInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryRiskHandleInfo;
import com.born.fcs.pm.ws.order.common.ChangeProjectStatusOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyOrder;
import com.born.fcs.pm.ws.order.council.CouncilProjectOrder;
import com.born.fcs.pm.ws.order.council.CouncilQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilSummaryUploadOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectBondOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectChargeWayOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectCreditConditionOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectEntrustedOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectGuaranteeOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectGuarantorOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectLgLitigationOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectPledgeAssetOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectProcessControlOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectRepayWayOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectUnderwritingOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryRiskHandleBatchOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryRiskHandleOrder;
import com.born.fcs.pm.ws.order.council.OneVoteDownOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.council.CouncilSummaryResult;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.council.CouncilApplyService;
import com.born.fcs.pm.ws.service.council.CouncilProjectService;
import com.born.fcs.pm.ws.service.council.CouncilService;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;
import com.born.fcs.pm.ws.service.council.CouncilTypeService;
import com.born.fcs.pm.ws.service.investigation.InvestigationService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import com.yjf.common.service.base.ProcessInvokeService;

@Service("councilSummaryService")
public class CouncilSummaryServiceImpl extends BaseFormAutowiredDomainService implements
																				CouncilSummaryService {
	
	@Autowired
	CouncilProjectService councilProjectService;
	
	@Autowired
	CouncilService councilService;
	
	@Autowired
	CouncilTypeService councilTypeService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	InvestigationService investigationService;
	
	@Autowired
	private CouncilApplyService councilApplyService;
	
	@Override
	public CouncilSummaryResult initCouncilSummary(final FCouncilSummaryOrder order) {
		
		return (CouncilSummaryResult) commonProcess(order, "初始化会议纪要",
			new BeforeProcessInvokeService() {
				
				@Override
				public Domain before() {
					CouncilInfo council = councilService.queryCouncilById(order.getCouncilId());
					
					if (council == null) {
						throw ExceptionFactory
							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "会议不存在");
					}
					
					if (council.getStatus() != CouncilStatusEnum.BREAK_UP) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "会议尚未结束");
					}
					
					if (CouncilTypeEnum.PROJECT_REVIEW == council.getCouncilTypeCode()) {// 项目评审会
						order.setFormCode(FormCodeEnum.COUNCIL_SUMMARY_PROJECT_REVIEW);
					} else if (CouncilTypeEnum.RISK_HANDLE == council.getCouncilTypeCode()) {// 风险审查会
						order.setFormCode(FormCodeEnum.COUNCIL_SUMMARY_RISK_HANDLE);
					} else {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"无法匹配会议类型");
					}
					
					FcsPmDomainHolder.get().addAttribute("council", council);
					
					// 会议讨论的项目
					List<CouncilProjectInfo> councilProjects = council.getProjects();
					
					if (ListUtil.isEmpty(councilProjects)) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"会议项目信息不存在");
					}
					
					FcsPmDomainHolder.get().addAttribute("councilProjects", councilProjects);
					
					String defultCheckStatus = "";
					String relatedProjectCode = "";
					for (CouncilProjectInfo cp : councilProjects) {
						if (StringUtil.isBlank(relatedProjectCode)) {
							relatedProjectCode = cp.getProjectCode();
						} else {
							relatedProjectCode += "," + cp.getProjectCode();
						}
					}
					
					if (CouncilTypeEnum.PROJECT_REVIEW == council.getCouncilTypeCode()) {// 项目评审会 - 页签
						for (CouncilProjectInfo cp : councilProjects) {
							if (cp.getProjectVoteResult() == ProjectVoteResultEnum.END_PASS) {
								defultCheckStatus += "0";
							} else {// 未通过的项目不保存任何信息、所以不用验证
								defultCheckStatus += "1";
							}
						}
					} else { // 风险审查会 单页
						defultCheckStatus = "0";
					}
					order.setDefaultCheckStatus(defultCheckStatus);
					order.setCouncilType(council.getCouncilTypeCode().code());
					order.setRelatedProjectCode(relatedProjectCode);
					return null;
				}
			}, new ProcessInvokeService() {
				
				@SuppressWarnings({ "unchecked", "deprecation" })
				@Override
				public void process(Domain domain) {
					
					// 保存会议纪要先
					CouncilSummaryResult saveSummaryResult = saveCouncilSummary(order);
					
					// 是否初始化会议纪要
					BooleanEnum isInitSummary = (BooleanEnum) FcsPmDomainHolder.get().getAttribute(
						"isInitSummary");
					
					if (isInitSummary != BooleanEnum.IS) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "会议纪要已经初始化");
					}
					
					if (!saveSummaryResult.isSuccess()) {
						throw ExceptionFactory.newFcsException(
							saveSummaryResult.getFcsResultEnum(), saveSummaryResult.getMessage());
					}
					
					// 会议类型
					FCouncilSummaryInfo summary = saveSummaryResult.getSummary();
					CouncilTypeEnum councilType = summary.getCouncilType();
					
					// 会议讨论的项目
					List<CouncilProjectInfo> councilProjects = (List<CouncilProjectInfo>) FcsPmDomainHolder
						.get().getAttribute("councilProjects");
					
					List<FCouncilSummaryRiskHandleOrder> riskHandleOrders = null;
					if (councilType == CouncilTypeEnum.RISK_HANDLE) {
						riskHandleOrders = Lists.newArrayList();
					}
					
					int checkIndex = 0;
					for (CouncilProjectInfo cp : councilProjects) { // 循环所有会议讨论项目
					
						ProjectVoteResultEnum voteResult = cp.getProjectVoteResult();
						if (cp.getRiskSecretaryQuit() == BooleanEnum.YES) {//风控委秘书执行了本次不议
							voteResult = ProjectVoteResultEnum.END_QUIT;
						}
						
						FCouncilSummaryProjectOrder pOrder = new FCouncilSummaryProjectOrder();
						
						ProjectDO project = null; //授信项目
						
						if (ProjectUtil.isFinancial(cp.getProjectCode())) { //处理理财项目
							ProjectFinancialDO proejctFinancialDO = projectFinancialDAO
								.findByCode(cp.getProjectCode());
							if (proejctFinancialDO == null) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
									"理财项目信息不存在");
							}
							pOrder.setProjectCode(proejctFinancialDO.getProjectCode()); //理财项目编号
							pOrder.setProjectName(proejctFinancialDO.getProductName()); //理财产品名称
							pOrder.setTimeLimit(proejctFinancialDO.getTimeLimit()); //理财产品期限
							pOrder.setTimeUnit(proejctFinancialDO.getTimeUnit());
							pOrder.setCustomerId(proejctFinancialDO.getProductId());//理财产品ID
							pOrder.setCustomerName(proejctFinancialDO.getIssueInstitution()); //理财产品发行机构
							FcsPmDomainHolder.get().addAttribute("projectFinancial",
								proejctFinancialDO);
						} else {
							//处理授信项目
							project = projectDAO.findByProjectCode(cp.getProjectCode());
							if (project == null) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
									"项目信息不存在");
							}
							BeanCopier.staticCopy(project, pOrder, UnBoxingConverter.getInstance());
							FcsPmDomainHolder.get().addAttribute("project", project);
						}
						
						pOrder.setSummaryId(summary.getSummaryId());
						pOrder.setVoteResult(voteResult);
						pOrder.setVoteResultDesc(getVoteResultDesc(cp));
						pOrder.setFormId(saveSummaryResult.getFormInfo().getFormId());
						pOrder.setFormCode(saveSummaryResult.getFormInfo().getFormCode());
						pOrder.setCheckIndex(checkIndex);
						pOrder.setSpId(0l);
						checkIndex++;
						
						if (councilType == CouncilTypeEnum.PROJECT_REVIEW) {// 初始化项目信息
						
							// 后面处理项目状态的时候需要用到
							FcsPmDomainHolder.get().addAttribute("isProjectReview", BooleanEnum.IS);
							
							if (voteResult != ProjectVoteResultEnum.END_PASS
								|| ProjectUtil.isFinancial(cp.getProjectCode())) {
								saveProjectCsCommon(pOrder);
								continue;
							}
							
							// 根据业务类型初始化项目信息
							if (ProjectUtil.isBond(project.getBusiType())) { // 发债业务
								FCouncilSummaryProjectBondOrder bondOrder = new FCouncilSummaryProjectBondOrder();
								BeanCopier.staticCopy(pOrder, bondOrder);
								if (!saveBondProjectCs(bondOrder).isSuccess()) {
									throw ExceptionFactory.newFcsException(
										FcsResultEnum.EXECUTE_FAIL, "初始化发债项目会议纪要失败");
								}
							} else if (ProjectUtil.isLitigation(project.getBusiType())) {// 诉讼保函
								FCouncilSummaryProjectLgLitigationOrder lgLitigationOrder = new FCouncilSummaryProjectLgLitigationOrder();
								BeanCopier.staticCopy(pOrder, lgLitigationOrder);
								if (!saveLgLitigationProjectCs(lgLitigationOrder).isSuccess()) {
									throw ExceptionFactory.newFcsException(
										FcsResultEnum.EXECUTE_FAIL, "初始化诉讼担保项目会议纪要失败");
								}
							} else if (ProjectUtil.isUnderwriting(project.getBusiType())) { // 承销业务
								FCouncilSummaryProjectUnderwritingOrder underwritingOrder = new FCouncilSummaryProjectUnderwritingOrder();
								BeanCopier.staticCopy(pOrder, underwritingOrder);
								if (!saveUnderwritingProjectCs(underwritingOrder).isSuccess()) {
									throw ExceptionFactory.newFcsException(
										FcsResultEnum.EXECUTE_FAIL, "初始化承销项目会议纪要失败");
								}
							} else if (ProjectUtil.isEntrusted(project.getBusiType())) { // 委贷业务
								FCouncilSummaryProjectEntrustedOrder entrustedOrder = new FCouncilSummaryProjectEntrustedOrder();
								BeanCopier.staticCopy(pOrder, entrustedOrder);
								if (!saveEntrustedProjectCs(entrustedOrder).isSuccess()) {
									throw ExceptionFactory.newFcsException(
										FcsResultEnum.EXECUTE_FAIL, "初始化委贷项目会议纪要失败");
								}
							} else { // 其他(担保)通用
								FCouncilSummaryProjectGuaranteeOrder guaranteeOrder = new FCouncilSummaryProjectGuaranteeOrder();
								BeanCopier.staticCopy(pOrder, guaranteeOrder);
								if (!saveGuaranteeProjectCs(guaranteeOrder).isSuccess()) {
									throw ExceptionFactory.newFcsException(
										FcsResultEnum.EXECUTE_FAIL, "初始化担保项目会议纪要失败");
								}
							}
						} else { // 风险处置会
							FCouncilSummaryRiskHandleOrder riskHandleOrder = new FCouncilSummaryRiskHandleOrder();
							BeanCopier.staticCopy(pOrder, riskHandleOrder);
							riskHandleOrders.add(riskHandleOrder);
						}
					} // 循环项目结束
					
					if (councilType == CouncilTypeEnum.RISK_HANDLE) { // 初始化风险处置会处置方案
						FCouncilSummaryRiskHandleBatchOrder riskHandleOrder = new FCouncilSummaryRiskHandleBatchOrder();
						BeanCopier.staticCopy(order, riskHandleOrder);
						riskHandleOrder.setRiskHandleOrder(riskHandleOrders);
						if (!saveRiskHandleCs(riskHandleOrder).isSuccess()) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
								"初始化风险处置方案失败");
						}
					}
					
					CouncilSummaryResult result = (CouncilSummaryResult) FcsPmDomainHolder.get()
						.getAttribute("result");
					result.setFormInfo(saveSummaryResult.getFormInfo());
					result.setSummary(summary);
				}
			}, new AfterProcessInvokeService() {
				
				@SuppressWarnings("unchecked")
				@Override
				public Domain after(Domain domain) {
					
					BooleanEnum isProjectReview = (BooleanEnum) FcsPmDomainHolder.get()
						.getAttribute("isProjectReview");
					
					if (isProjectReview == BooleanEnum.IS) {
						// 会议讨论的项目
						List<CouncilProjectInfo> councilProjects = (List<CouncilProjectInfo>) FcsPmDomainHolder
							.get().getAttribute("councilProjects");
						
						ChangeProjectStatusOrder order = new ChangeProjectStatusOrder();
						for (CouncilProjectInfo cp : councilProjects) {
							if (!ProjectUtil.isFinancial(cp.getProjectCode())) {
								// 项目进入评审阶段 阶段的状态为填写中
								order.setProjectCode(cp.getProjectCode());
								order.setPhase(ProjectPhasesEnum.COUNCIL_PHASES);
								order.setPhaseStatus(ProjectPhasesStatusEnum.DRAFT);
								if (cp.getProjectVoteResult() == ProjectVoteResultEnum.END_NOPASS) { // 项目未成立
									order.setPhaseStatus(ProjectPhasesStatusEnum.NOPASS);
									order.setStatus(ProjectStatusEnum.FAILED);
								} else if (cp.getProjectVoteResult() == ProjectVoteResultEnum.END_QUIT) { // 本次议还是待上会
									order.setPhaseStatus(ProjectPhasesStatusEnum.WAITING);
								}
								projectService.changeProjectStatus(order);
							}
						}
						
					}
					return null;
				}
			});
		
	}
	
	@Override
	public CouncilSummaryResult saveCouncilSummary(final FCouncilSummaryOrder order) {
		
		return (CouncilSummaryResult) commonFormSaveProcess(order, "保存会议纪要",
			new BeforeProcessInvokeService() {
				
				@Override
				public Domain before() {
					
					order.setCheckIndex(-1);
					
					FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					
					if (form == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"获取表单信息出错");
					}
					
					final Date now = FcsPmDomainHolder.get().getSysDate();
					
					FCouncilSummaryDO summary = FCouncilSummaryDAO.findByFormId(form.getFormId());
					
					boolean isUpdate = false;
					
					if (summary == null) {// 新增
					
						// 会议信息
						CouncilInfo council = (CouncilInfo) FcsPmDomainHolder.get().getAttribute(
							"council");
						
						if (council == null)
							council = councilService.queryCouncilById(order.getCouncilId());
						
						if (council == null) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"会议不存在");
						}
						
						summary = new FCouncilSummaryDO();
						summary.setRawAddTime(now);
						summary.setCouncilId(council.getCouncilId());
						summary.setCouncilCode(council.getCouncilCode());
						summary.setCouncilType(council.getCouncilTypeCode().code());
						summary.setFormId(form.getFormId());
						summary
							.setSummaryCode(genSummaryCode(council.getCouncilTypeCode().prefix()));
						summary.setInitiatorId(council.getCompereId()); //主持人
						summary.setInitiatorName(council.getCompereName()); //主持人
						FcsPmDomainHolder.get().addAttribute("council", council);
						
					} else {// 修改
						isUpdate = true;
						// 页面只涉及这3个属性的修改
						//						summary.setInitiatorId(order.getInitiatorId() == null ? 0 : order
						//							.getInitiatorId());
						//						summary.setInitiatorAccount(order.getInitiatorAccount());
						//						summary.setInitiatorName(order.getInitiatorName());
						summary.setOverview(order.getOverview());
					}
					
					if (order.getIsFormChangeApply() != BooleanEnum.IS) {//签报不保存会议纪要信息
						if (isUpdate) {
							FCouncilSummaryDAO.update(summary);
						} else {
							summary.setSummaryId(FCouncilSummaryDAO.insert(summary));
							FcsPmDomainHolder.get().addAttribute("isInitSummary", BooleanEnum.IS);
						}
					}
					
					CouncilSummaryResult result = (CouncilSummaryResult) FcsPmDomainHolder.get()
						.getAttribute("result");
					result.setSummary(covertCouncilSummaryDO2Info(summary));
					
					return null;
				}
			}, null, null);
	}
	
	@Override
	public CouncilSummaryResult saveGmworkingCouncilSummary(final CouncilSummaryUploadOrder order) {
		return (CouncilSummaryResult) commonProcess(order, "上传会议纪要",
			new BeforeProcessInvokeService() {
				
				@Override
				public Domain before() {
					//Date now = FcsPmDomainHolder.get().getSysDate();
					CouncilDO councilDO = councilDAO.findById(order.getCouncilId());
					if (councilDO == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"会议信息不存在");
					}
					// 根据councilid保存评审结果
					List<CouncilProjectDO> projectDOs = councilProjectDAO.findByCouncilId(order
						.getCouncilId());
					for (CouncilProjectDO councilProject : projectDOs) {
						// 添加判断： 若已经保存过投票结果，不再修订投票结果
						if (ProjectVoteResultEnum.END_PASS.code().equals(
							councilProject.getProjectVoteResult())
							|| ProjectVoteResultEnum.END_NOPASS.code().equals(
								councilProject.getProjectVoteResult())) {
						} else {
							for (CouncilProjectInfo voteInfo : order.getVoteInfo()) {
								if (councilProject.getId() == voteInfo.getId()) {
									councilProject.setProjectVoteResult(voteInfo
										.getProjectVoteResult().code());
									councilProjectDAO.update(councilProject);
									
									/// 若投票结果为通过，执行信汇母公司会判定R
									if (ProjectVoteResultEnum.END_PASS == voteInfo
										.getProjectVoteResult()) {
										//  添加任务，如果是信汇的总经理办公会完成且要求上母公司会议，增加母公司会议
										if (CouncilTypeEnum.GM_WORKING.code().equals(
											councilDO.getCouncilTypeCode())) {
											CouncilApplyDO applyDO = councilApplyDAO
												.findById(councilProject.getApplyId());
											if (CompanyNameEnum.XINHUI.code().equals(
												applyDO.getCompanyName())
												&& BooleanEnum.YES.code().equals(
													applyDO.getMotherCompanyApply())) {
												
												CouncilApplyOrder applyOrder = new CouncilApplyOrder();
												MiscUtil.copyPoObject(applyOrder, applyDO);
												applyOrder.setCouncilCode(applyDO
													.getMotherCouncilCode());
												applyOrder.setCouncilTypeDesc(CouncilTypeEnum
													.getByCode(applyDO.getMotherCouncilCode())
													.message());
												applyOrder.setMotherCouncilCode(null);
												applyOrder.setCompanyName(CompanyNameEnum.NORMAL);
												applyOrder.setMotherCompanyApply(BooleanEnum.NO);
												//												applyOrder.setRawAddTime(now);
												applyOrder.setStatus(CouncilApplyStatusEnum.WAIT
													.code());
												applyOrder.setChildId(councilProject.getApplyId());
												
												//councilApplyDAO.insert(applyDO);
												applyOrder.setApplyId(0);
												
												// 以order的companyName进行存库操作
												applyOrder.setHasCompanyName(true);
												councilApplyService.saveCouncilApply(applyOrder);
											}
										}
									}
									
								}
							}
							
						}
						
					}
					// 根据councilid 保存 会议纪要url
					
					councilDO.setSummaryUrl(order.getSummaryUrl());
					councilDAO.update(councilDO);
					return null;
				}
			}, null, null);
	}
	
	@Override
	public FCouncilSummaryInfo queryCouncilSummaryById(long summaryId) {
		return covertCouncilSummaryDO2Info(FCouncilSummaryDAO.findById(summaryId));
	}
	
	@Override
	public FCouncilSummaryInfo queryCouncilSummaryByFormId(long formId) {
		return covertCouncilSummaryDO2Info(FCouncilSummaryDAO.findByFormId(formId));
	}
	
	@Override
	public FCouncilSummaryInfo queryCouncilSummaryByCouncilId(long councilId) {
		return covertCouncilSummaryDO2Info(FCouncilSummaryDAO.findByCouncilId(councilId));
		
	}
	
	@Override
	public FCouncilSummaryInfo queryCouncilSummaryByCouncilCode(String councilCode) {
		return covertCouncilSummaryDO2Info(FCouncilSummaryDAO.findByCouncilCode(councilCode));
	}
	
	@Override
	public List<FCouncilSummaryProjectInfo> queryProjectCsBySummaryId(long summaryId) {
		List<FCouncilSummaryProjectDO> data = FCouncilSummaryProjectDAO.findBySummaryId(summaryId);
		if (ListUtil.isEmpty(data))
			return null;
		List<FCouncilSummaryProjectInfo> list = Lists.newArrayList();
		for (FCouncilSummaryProjectDO DO : data) {
			list.add(covertCouncilSummaryProjectDO2Info(DO));
		}
		return list;
	}
	
	@Override
	public FCouncilSummaryProjectInfo queryProjectCsByProjectCode(String projectCode) {
		List<FCouncilSummaryProjectDO> cs = FCouncilSummaryProjectDAO
			.findByProjectCode(projectCode);
		if (ListUtil.isNotEmpty(cs)) {
			return covertCouncilSummaryProjectDO2Info(cs.get(0));
		}
		return null;
	}
	
	@Override
	public FCouncilSummaryProjectInfo queryProjectCsBySpCode(String spCode) {
		return covertCouncilSummaryProjectDO2Info(FCouncilSummaryProjectDAO.findBySpCode(spCode));
	}
	
	@Override
	public FCouncilSummaryProjectInfo queryProjectCsBySpId(long spId) {
		return covertCouncilSummaryProjectDO2Info(FCouncilSummaryProjectDAO.findById(spId));
	}
	
	/**
	 * 保存项目会议纪要通用部分
	 * 
	 * @param order
	 * @return
	 */
	public FcsBaseResult saveProjectCsCommon(final FCouncilSummaryProjectOrder order) {
		
		return commonProcess(order, "保存项目会议纪要通用信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				int checkIndex = order.getCheckIndex();
				
				BooleanEnum isInitSummary = (BooleanEnum) FcsPmDomainHolder.get().getAttribute(
					"isInitSummary");
				if (isInitSummary != BooleanEnum.IS
					&& order.getIsFormChangeApply() != BooleanEnum.IS) {
					// 保存会议纪要部分
					saveCouncilSummary(order);
				}
				
				// 保存会议纪要的时候会改变这个值
				order.setCheckIndex(checkIndex);
				
				FCouncilSummaryProjectDO sp = null;
				if (order.getSpId() != null && order.getSpId() > 0) {
					sp = FCouncilSummaryProjectDAO.findById(order.getSpId());
					if (sp == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"项目信息不存在");
					}
				}
				
				FCouncilSummaryDO summary = FCouncilSummaryDAO.findById(order.getSummaryId());
				if (summary == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "会议纪要不存在");
				}
				
				boolean isUpdate = false;
				
				//尽职调查时候反担保附件
				List<CommonAttachmentDO> attachmentDOs = null;
				
				if (sp == null) { // 初始化项目信息
				
					//理财项目
					if (ProjectUtil.isFinancial(order.getProjectCode())) {
						
						ProjectFinancialDO projectFinancial = (ProjectFinancialDO) FcsPmDomainHolder
							.get().getAttribute("project");
						if (projectFinancial == null) {
							projectFinancial = projectFinancialDAO.findByCode(order
								.getProjectCode());
						}
						
						if (projectFinancial == null) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"理财项目不存在");
						}
						
						sp = new FCouncilSummaryProjectDO();
						sp.setSummaryId(summary.getSummaryId());
						sp.setSpCode(genSpCode(order.getVoteResult()));
						sp.setIsDel(BooleanEnum.NO.code());
						sp.setOneVoteDown(BooleanEnum.NO.code());
						
						// 投票结果
						sp.setVoteResult(order.getVoteResult().code());
						sp.setVoteResultDesc(order.getVoteResultDesc());
						sp.setRawAddTime(now);
						sp.setApprovalTime(null);//会议纪要审核通过时间初始化为空
						
						sp.setProjectCode(projectFinancial.getProjectCode()); //理财项目编号
						sp.setProjectName(projectFinancial.getProductName()); //理财产品名称
						sp.setTimeLimit(projectFinancial.getTimeLimit()); //理财产品期限
						sp.setTimeUnit(projectFinancial.getTimeUnit());
						sp.setCustomerId(projectFinancial.getProductId());//理财产品ID
						sp.setCustomerName(projectFinancial.getIssueInstitution()); //理财产品发行机构
						
					} else { //处理授信业务
					
						ProjectDO project = (ProjectDO) FcsPmDomainHolder.get().getAttribute(
							"project");
						if (project == null) {
							project = projectDAO.findByProjectCode(order.getProjectCode());
						}
						if (project == null) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"项目不存在");
						}
						
						sp = new FCouncilSummaryProjectDO();
						BeanCopier.staticCopy(project, sp);
						sp.setSummaryId(summary.getSummaryId());
						sp.setSpCode(genSpCode(order.getVoteResult()));
						sp.setIsDel(BooleanEnum.NO.code());
						sp.setOneVoteDown(BooleanEnum.NO.code());
						
						// 投票结果
						sp.setVoteResult(order.getVoteResult().code());
						sp.setVoteResultDesc(order.getVoteResultDesc());
						sp.setRawAddTime(now);
						sp.setApprovalTime(null);//会议纪要审核通过时间初始化为空（复议过来的项目信息中会有上次通过的时间）
						
						long investigationFormId = investigationService
							.queryInvestigationFormIdByProjectCode(project.getProjectCode());
						
						// 根据业务类型不同，从尽职调查信息中拉取相关数据
						if (ProjectUtil.isLitigation(project.getBusiType())) { // 诉讼担保业务
						
							FInvestigationLitigationDO investigationInfo = FInvestigationLitigationDAO
								.findByFormId(investigationFormId);
							if (investigationInfo != null) {
								// 设置一些修改过的数据
								// sp.setAmount(investigationInfo.get);
							}
							
							FcsPmDomainHolder.get().addAttribute("investigationInfo",
								investigationInfo);
						} else if (ProjectUtil.isUnderwriting(project.getBusiType())) { // 承销业务
							FInvestigationUnderwritingDO investigationInfo = FInvestigationUnderwritingDAO
								.findByFormId(investigationFormId);
							if (investigationInfo != null) {
								sp.setProjectName(investigationInfo.getProjectName());
								sp.setTimeLimit(investigationInfo.getTimeLimit());
								sp.setTimeUnit(investigationInfo.getTimeUnit());
							}
							FcsPmDomainHolder.get().addAttribute("investigationInfo",
								investigationInfo);
						} else { // 其他通用(发债、担保、委贷)
							FInvestigationCreditSchemeDO investigationInfo = FInvestigationCreditSchemeDAO
								.findByFormId(investigationFormId);
							if (investigationInfo != null) {
								sp.setTimeLimit(investigationInfo.getTimeLimit());
								sp.setTimeUnit(investigationInfo.getTimeUnit());
								sp.setAmount(investigationInfo.getCreditAmount());
								sp.setLoanPurpose(investigationInfo.getLoanPurpose());
								sp.setChargePhase(investigationInfo.getChargePhase());
								sp.setChargeWay(investigationInfo.getChargeWay());
								sp.setOther(investigationInfo.getOther());
							}
							FcsPmDomainHolder.get().addAttribute("investigationInfo",
								investigationInfo);
							
							//尽职调查时候反担保附件
							CommonAttachmentDO cdo = new CommonAttachmentDO();
							cdo.setBizNo(String.valueOf(investigationFormId));
							cdo.setModuleType(CommonAttachmentTypeEnum.COUNTER_GUARANTEE.code());
							attachmentDOs = commonAttachmentDAO.findByBizNoModuleType(cdo);
						}
						
						//尽职中反担保的附件
						if (ListUtil.isNotEmpty(attachmentDOs)) {
							for (CommonAttachmentDO attach : attachmentDOs) {
								attach.setAttachmentId(0);
								attach.setBizNo("spId_" + sp.getSpId());
								commonAttachmentDAO.insert(attach);
							}
						}
					} //授信业务处理完毕
					
				} else { // 修改项目信息
					isUpdate = true;
					ProjectVoteResultEnum voteResult = ProjectVoteResultEnum.getByCode(sp
						.getVoteResult());
					if (!ProjectUtil.isFinancial(sp.getProjectCode())
						&& voteResult == ProjectVoteResultEnum.END_PASS) {
						sp.setAmount(order.getAmount());
						sp.setTimeLimit(order.getTimeLimit() == null ? 0 : order.getTimeLimit());
						sp.setTimeUnit(order.getTimeUnit());
						sp.setChargePhase(order.getChargePhase());
						sp.setChargeWay(order.getChargeWay());
						sp.setIsChargeEveryBeginning(StringUtil.isNotEmpty(order
							.getIsChargeEveryBeginning()) ? order.getIsChargeEveryBeginning()
							: BooleanEnum.NO.code());
						sp.setIsDel(BooleanEnum.NO.code());
						sp.setIsRepayEqual(order.getIsRepayEqual());
						sp.setLoanPurpose(order.getLoanPurpose());
						sp.setRepayWay(order.getRepayWay());
						sp.setChargeRemark(order.getChargeRemark());
						sp.setOther(order.getOther());
					}
					sp.setRemark(order.getRemark());
				}
				
				sp.setIsMaximumAmount(StringUtil.isNotEmpty(order.getIsMaximumAmount()) ? order
					.getIsMaximumAmount() : BooleanEnum.NO.code());
				
				if (isUpdate) {
					FCouncilSummaryProjectDAO.update(sp);
				} else {
					sp.setSpId(0l);
					sp.setSpId(FCouncilSummaryProjectDAO.insert(sp));
				}
				
				FcsPmDomainHolder.get().addAttribute("projectCommon", sp);
				
				return null;
			}
		}, null, null);
		
	}
	
	@Override
	public FormBaseResult saveBondProjectCs(final FCouncilSummaryProjectBondOrder order) {
		return commonFormSaveProcess(order, "保存发债项目会议纪要", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				// 保存通用部分信息
				saveProjectCsCommon(order);
				
				// 获取通用部分信息
				FCouncilSummaryProjectDO commonInfo = (FCouncilSummaryProjectDO) FcsPmDomainHolder
					.get().getAttribute("projectCommon");
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				FCouncilSummaryProjectBondDO bondProject = FCouncilSummaryProjectBondDAO
					.findBySpId(commonInfo.getSpId());
				
				boolean isUpdate = false;
				if (bondProject == null) {// 新增(主要信息来自项目调查)
				
					FInvestigationCreditSchemeDO investigationInfo = (FInvestigationCreditSchemeDO) FcsPmDomainHolder
						.get().getAttribute("investigationInfo");
					
					bondProject = new FCouncilSummaryProjectBondDO();
					bondProject.setRawAddTime(now);
					bondProject.setSpId(commonInfo.getSpId());
					if (investigationInfo != null) {
						bondProject.setCapitalChannelId(investigationInfo.getCapitalChannelId());
						bondProject.setCapitalChannelName(investigationInfo.getCapitalChannelName());
						bondProject.setCapitalSubChannelId(investigationInfo
							.getCapitalSubChannelId());
						bondProject.setCapitalSubChannelName(investigationInfo
							.getCapitalSubChannelName());
						bondProject.setProcessFlag(investigationInfo.getProcessFlag());
						
						// copy 尽职调查中的抵(质)押
						copyPledgeFromInvestigation(investigationInfo.getFormId(),
							commonInfo.getSpId(), now);
						
						// copy 尽职调查中的保证人
						copyGuarantorFromInvestigation(investigationInfo.getSchemeId(),
							commonInfo.getSpId(), now);
						
						// copy 尽职调查中过程控制
						copyProcessControlFromInvestigation(investigationInfo.getFormId(),
							commonInfo.getSpId(), now);
					}
					
				} else {// 修改
				
					isUpdate = true;
					long id = bondProject.getId();
					BeanCopier.staticCopy(order, bondProject, UnBoxingConverter.getInstance());
					bondProject.setId(id);
					// 收费方式
					saveChargeWay(order.getChargeWayOrder(), commonInfo.getSpId(), now);
					// 还款方式
					saveRepayWay(order.getRepayWayOrder(), commonInfo.getSpId(), now);
					// 抵(质)押
					savePledgeAssets(commonInfo.getSpId(), order);
					// 保证人
					saveGuarantors(commonInfo.getSpId(), order.getGuarantorOrders());
					//过程控制
					saveProcesses(commonInfo.getSpId(), order);
				}
				
				if (isUpdate) {
					FCouncilSummaryProjectBondDAO.update(bondProject);
				} else {
					FCouncilSummaryProjectBondDAO.insert(bondProject);
				}
				
				FcsBaseResult result = (FcsBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				result.setKeyId(bondProject.getSpId());
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FCouncilSummaryProjectBondInfo queryBondProjectCsByProjectCode(String projectCode,
																			boolean queryAll) {
		
		List<FCouncilSummaryProjectDO> cs = FCouncilSummaryProjectDAO
			.findByProjectCode(projectCode);
		FCouncilSummaryProjectDO common = null;
		if (ListUtil.isNotEmpty(cs)) {
			common = cs.get(0);
		}
		if (common == null)
			return null;
		
		// 项目信息ID
		long spId = common.getSpId();
		
		FCouncilSummaryProjectBondInfo info = covertBondProjectCsDO2Info(common,
			FCouncilSummaryProjectBondDAO.findBySpId(spId));
		
		if (info != null && queryAll) {
			// 还款方式
			info.setRepayWayList(queryRepayWayBySpId(spId));
			// 收费方式
			info.setChargeWayList(queryChargeWayBySpId(spId));
			//授信条件
			setCreditCondition2Info(info);
		}
		
		return info;
	}
	
	@Override
	public FCouncilSummaryProjectBondInfo queryBondProjectCsBySpId(long spId, boolean queryAll) {
		
		FCouncilSummaryProjectDO common = FCouncilSummaryProjectDAO.findById(spId);
		if (common == null)
			return null;
		
		FCouncilSummaryProjectBondInfo info = covertBondProjectCsDO2Info(common,
			FCouncilSummaryProjectBondDAO.findBySpId(spId));
		
		if (info != null && queryAll) {
			// 还款方式
			info.setRepayWayList(queryRepayWayBySpId(spId));
			// 收费方式
			info.setChargeWayList(queryChargeWayBySpId(spId));
			//授信条件
			setCreditCondition2Info(info);
		}
		
		return info;
	}
	
	@Override
	public FormBaseResult saveGuaranteeProjectCs(final FCouncilSummaryProjectGuaranteeOrder order) {
		return commonFormSaveProcess(order, "保存担保项目会议纪要", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				// 保存通用部分信息
				saveProjectCsCommon(order);
				
				// 获取通用部分信息
				FCouncilSummaryProjectDO commonInfo = (FCouncilSummaryProjectDO) FcsPmDomainHolder
					.get().getAttribute("projectCommon");
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				FCouncilSummaryProjectGuaranteeDO geProject = FCouncilSummaryProjectGuaranteeDAO
					.findBySpId(commonInfo.getSpId());
				
				boolean isUpdate = false;
				if (geProject == null) {// 新增(主要信息来自项目调查)
				
					FInvestigationCreditSchemeDO investigationInfo = (FInvestigationCreditSchemeDO) FcsPmDomainHolder
						.get().getAttribute("investigationInfo");
					
					geProject = new FCouncilSummaryProjectGuaranteeDO();
					geProject.setRawAddTime(now);
					geProject.setSpId(commonInfo.getSpId());
					if (investigationInfo != null) {
						
						geProject.setCapitalChannelId(investigationInfo.getCapitalChannelId());
						geProject.setCapitalChannelName(investigationInfo.getCapitalChannelName());
						geProject.setCapitalSubChannelId(investigationInfo.getCapitalSubChannelId());
						geProject.setCapitalSubChannelName(investigationInfo
							.getCapitalSubChannelName());
						geProject.setGuaranteeFee(investigationInfo.getChargeRate());
						geProject.setGuaranteeFeeType(ChargeTypeEnum.PERCENT.code());
						geProject.setProcessFlag(investigationInfo.getProcessFlag());
						
						// 从尽职调查中copy抵（质）押
						copyPledgeFromInvestigation(investigationInfo.getFormId(),
							commonInfo.getSpId(), now);
						
						// copy 尽职调查中的保证人
						copyGuarantorFromInvestigation(investigationInfo.getSchemeId(),
							commonInfo.getSpId(), now);
						
						// 从尽职调查中copy过程控制
						copyProcessControlFromInvestigation(investigationInfo.getFormId(),
							commonInfo.getSpId(), now);
					}
					
				} else {// 修改
				
					isUpdate = true;
					long id = geProject.getId();
					BeanCopier.staticCopy(order, geProject, UnBoxingConverter.getInstance());
					geProject.setId(id);
					// 收费方式
					saveChargeWay(order.getChargeWayOrder(), commonInfo.getSpId(), now);
					// 抵(质)押
					savePledgeAssets(commonInfo.getSpId(), order);
					// 保证人
					saveGuarantors(commonInfo.getSpId(), order.getGuarantorOrders());
					//过程控制
					saveProcesses(commonInfo.getSpId(), order);
				}
				
				if (isUpdate) {
					FCouncilSummaryProjectGuaranteeDAO.update(geProject);
				} else {
					FCouncilSummaryProjectGuaranteeDAO.insert(geProject);
				}
				
				FcsBaseResult result = (FcsBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				result.setKeyId(geProject.getSpId());
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FCouncilSummaryProjectGuaranteeInfo queryGuaranteeProjectCsByProjectCode(String projectCode,
																					boolean queryAll) {
		List<FCouncilSummaryProjectDO> cs = FCouncilSummaryProjectDAO
			.findByProjectCode(projectCode);
		FCouncilSummaryProjectDO common = null;
		if (ListUtil.isNotEmpty(cs)) {
			common = cs.get(0);
		}
		if (common == null)
			return null;
		
		long spId = common.getSpId();
		FCouncilSummaryProjectGuaranteeInfo info = convertGuaranteeProjectCsDO2Info(common,
			FCouncilSummaryProjectGuaranteeDAO.findBySpId(spId));
		if (info != null && queryAll) {
			// 收费方式
			info.setChargeWayList(queryChargeWayBySpId(spId));
			//授信条件
			setCreditCondition2Info(info);
		}
		
		return info;
	}
	
	@Override
	public FCouncilSummaryProjectGuaranteeInfo queryGuaranteeProjectCsBySpId(long spId,
																				boolean queryAll) {
		FCouncilSummaryProjectDO common = FCouncilSummaryProjectDAO.findById(spId);
		if (common == null)
			return null;
		
		FCouncilSummaryProjectGuaranteeInfo info = convertGuaranteeProjectCsDO2Info(common,
			FCouncilSummaryProjectGuaranteeDAO.findBySpId(spId));
		if (info != null && queryAll) {
			// 收费方式
			info.setChargeWayList(queryChargeWayBySpId(spId));
			//授信条件
			setCreditCondition2Info(info);
		}
		
		return info;
	}
	
	@Override
	public FormBaseResult saveEntrustedProjectCs(final FCouncilSummaryProjectEntrustedOrder order) {
		return commonFormSaveProcess(order, "保存委贷项目会议纪要", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				// 保存通用部分信息
				saveProjectCsCommon(order);
				
				// 获取通用部分信息
				FCouncilSummaryProjectDO commonInfo = (FCouncilSummaryProjectDO) FcsPmDomainHolder
					.get().getAttribute("projectCommon");
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				FCouncilSummaryProjectEntrustedDO geProject = FCouncilSummaryProjectEntrustedDAO
					.findBySpId(commonInfo.getSpId());
				
				boolean isUpdate = false;
				if (geProject == null) {// 新增(主要信息来自项目调查)
				
					FInvestigationCreditSchemeDO investigationInfo = (FInvestigationCreditSchemeDO) FcsPmDomainHolder
						.get().getAttribute("investigationInfo");
					
					geProject = new FCouncilSummaryProjectEntrustedDO();
					geProject.setRawAddTime(now);
					geProject.setSpId(commonInfo.getSpId());
					if (investigationInfo != null) {
						
						geProject.setCapitalChannelId(investigationInfo.getCapitalChannelId());
						geProject.setCapitalChannelName(investigationInfo.getCapitalChannelName());
						geProject.setCapitalSubChannelId(investigationInfo.getCapitalSubChannelId());
						geProject.setCapitalSubChannelName(investigationInfo
							.getCapitalSubChannelName());
						geProject.setInterestRate(investigationInfo.getChargeRate());
						geProject.setProcessFlag(investigationInfo.getProcessFlag());
						
						// 从尽职调查中copy抵（质）押
						copyPledgeFromInvestigation(investigationInfo.getFormId(),
							commonInfo.getSpId(), now);
						
						// copy 尽职调查中的保证人
						copyGuarantorFromInvestigation(investigationInfo.getSchemeId(),
							commonInfo.getSpId(), now);
						
						// 从尽职调查中copy过程控制
						copyProcessControlFromInvestigation(investigationInfo.getFormId(),
							commonInfo.getSpId(), now);
					}
					
				} else {// 修改
				
					isUpdate = true;
					long id = geProject.getId();
					BeanCopier.staticCopy(order, geProject, UnBoxingConverter.getInstance());
					geProject.setId(id);
					// 收费方式
					saveChargeWay(order.getChargeWayOrder(), commonInfo.getSpId(), now);
					// 抵(质)押
					savePledgeAssets(commonInfo.getSpId(), order);
					// 保证人
					saveGuarantors(commonInfo.getSpId(), order.getGuarantorOrders());
					//过程控制
					saveProcesses(commonInfo.getSpId(), order);
				}
				
				if (isUpdate) {
					FCouncilSummaryProjectEntrustedDAO.update(geProject);
				} else {
					FCouncilSummaryProjectEntrustedDAO.insert(geProject);
				}
				
				FcsBaseResult result = (FcsBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				result.setKeyId(geProject.getSpId());
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FCouncilSummaryProjectEntrustedInfo queryEntrustedProjectCsByProjectCode(String projectCode,
																					boolean queryAll) {
		List<FCouncilSummaryProjectDO> cs = FCouncilSummaryProjectDAO
			.findByProjectCode(projectCode);
		FCouncilSummaryProjectDO common = null;
		if (ListUtil.isNotEmpty(cs)) {
			common = cs.get(0);
		}
		if (common == null)
			return null;
		
		long spId = common.getSpId();
		FCouncilSummaryProjectEntrustedInfo info = convertEntrustedProjectCsDO2Info(common,
			FCouncilSummaryProjectEntrustedDAO.findBySpId(spId));
		if (info != null && queryAll) {
			// 收费方式
			info.setChargeWayList(queryChargeWayBySpId(spId));
			//授信条件
			setCreditCondition2Info(info);
		}
		
		return info;
	}
	
	@Override
	public FCouncilSummaryProjectEntrustedInfo queryEntrustedProjectCsBySpId(long spId,
																				boolean queryAll) {
		FCouncilSummaryProjectDO common = FCouncilSummaryProjectDAO.findById(spId);
		if (common == null)
			return null;
		
		FCouncilSummaryProjectEntrustedInfo info = convertEntrustedProjectCsDO2Info(common,
			FCouncilSummaryProjectEntrustedDAO.findBySpId(spId));
		if (info != null && queryAll) {
			// 收费方式
			info.setChargeWayList(queryChargeWayBySpId(spId));
			//授信条件
			setCreditCondition2Info(info);
		}
		
		return info;
	}
	
	@Override
	public FormBaseResult saveLgLitigationProjectCs(final FCouncilSummaryProjectLgLitigationOrder order) {
		return commonFormSaveProcess(order, "保存诉讼担保项目会议纪要", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				// 保存通用部分信息
				saveProjectCsCommon(order);
				
				// 获取通用部分信息
				FCouncilSummaryProjectDO commonInfo = (FCouncilSummaryProjectDO) FcsPmDomainHolder
					.get().getAttribute("projectCommon");
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				FCouncilSummaryProjectLgLitigationDO lgLitigationProject = FCouncilSummaryProjectLgLitigationDAO
					.findBySpId(commonInfo.getSpId());
				
				boolean isUpdate = false;
				if (lgLitigationProject == null) {// 新增
					lgLitigationProject = new FCouncilSummaryProjectLgLitigationDO();
					lgLitigationProject.setRawAddTime(now);
					lgLitigationProject.setSpId(commonInfo.getSpId());
					
					// 尽职调查信息
					FInvestigationLitigationDO investigationInfo = (FInvestigationLitigationDO) FcsPmDomainHolder
						.get().getAttribute("investigationInfo");
					
					if (investigationInfo != null) {
						lgLitigationProject.setAssureObject(investigationInfo.getContent());
						lgLitigationProject.setGuaranteeFee(investigationInfo.getGuaranteeFee());
						lgLitigationProject.setGuaranteeFeeType(investigationInfo
							.getGuaranteeType());
						lgLitigationProject.setCoInstitutionId(investigationInfo
							.getCoInstitutionId());
						lgLitigationProject.setCoInstitutionName(investigationInfo
							.getCoInstitutionName());
						lgLitigationProject.setCoInstitutionCharge(investigationInfo
							.getInformationFee());
						lgLitigationProject.setCoInstitutionChargeType(ChargeTypeEnum.PERCENT
							.code());
					}
					
				} else {// 修改
					isUpdate = true;
					long id = lgLitigationProject.getId();
					BeanCopier.staticCopy(order, lgLitigationProject,
						UnBoxingConverter.getInstance());
					lgLitigationProject.setId(id);
					// 收费方式
					saveChargeWay(order.getChargeWayOrder(), commonInfo.getSpId(), now);
				}
				
				if (isUpdate) {
					FCouncilSummaryProjectLgLitigationDAO.update(lgLitigationProject);
				} else {
					FCouncilSummaryProjectLgLitigationDAO.insert(lgLitigationProject);
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FCouncilSummaryProjectLgLitigationInfo queryLgLitigationProjectCsByProjectCode(	String projectCode,
																							boolean queryAll) {
		
		List<FCouncilSummaryProjectDO> cs = FCouncilSummaryProjectDAO
			.findByProjectCode(projectCode);
		FCouncilSummaryProjectDO common = null;
		if (ListUtil.isNotEmpty(cs)) {
			common = cs.get(0);
		}
		if (common == null)
			return null;
		
		long spId = common.getSpId();
		
		FCouncilSummaryProjectLgLitigationInfo info = convertLgLitigationProjectCsDO2Info(common,
			FCouncilSummaryProjectLgLitigationDAO.findBySpId(spId));
		if (info != null && queryAll) {
			// 收费方式
			info.setChargeWayList(queryChargeWayBySpId(spId));
		}
		
		return info;
	}
	
	@Override
	public FCouncilSummaryProjectLgLitigationInfo queryLgLitigationProjectCsBySpId(long spId,
																					boolean queryAll) {
		FCouncilSummaryProjectDO common = FCouncilSummaryProjectDAO.findById(spId);
		if (common == null)
			return null;
		
		FCouncilSummaryProjectLgLitigationInfo info = convertLgLitigationProjectCsDO2Info(common,
			FCouncilSummaryProjectLgLitigationDAO.findBySpId(spId));
		if (info != null && queryAll) {
			// 收费方式
			info.setChargeWayList(queryChargeWayBySpId(spId));
		}
		
		return info;
	}
	
	@Override
	public FormBaseResult saveUnderwritingProjectCs(final FCouncilSummaryProjectUnderwritingOrder order) {
		return commonFormSaveProcess(order, "保存承销项目会议纪要", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				// 保存通用部分信息
				saveProjectCsCommon(order);
				
				// 获取通用部分信息
				FCouncilSummaryProjectDO commonInfo = (FCouncilSummaryProjectDO) FcsPmDomainHolder
					.get().getAttribute("projectCommon");
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				FCouncilSummaryProjectUnderwritingDO underwritingProject = FCouncilSummaryProjectUnderwritingDAO
					.findBySpId(commonInfo.getSpId());
				
				boolean isUpdate = false;
				if (underwritingProject == null) {// 新增
					underwritingProject = new FCouncilSummaryProjectUnderwritingDO();
					underwritingProject.setRawAddTime(now);
					underwritingProject.setSpId(commonInfo.getSpId());
					
					// 尽职调查信息
					FInvestigationUnderwritingDO investigationInfo = (FInvestigationUnderwritingDO) FcsPmDomainHolder
						.get().getAttribute("investigationInfo");
					
					if (investigationInfo != null) {
						underwritingProject.setBourseFee(investigationInfo.getChargeRate()); // 交易所费率
						underwritingProject.setBourseFeeType(ChargeTypeEnum.PERCENT.code());
						underwritingProject.setClubFee(investigationInfo.getClubRate()); // 会所费率
						underwritingProject.setClubFeeType(ChargeTypeEnum.PERCENT.code());
						underwritingProject.setBourseId(investigationInfo.getExchangeId()); // 交易所
						underwritingProject.setBourseName(investigationInfo.getExchangeName());
						underwritingProject.setLawFirmFee(investigationInfo.getLawOfficeRate()); // 律所费率
						underwritingProject.setLawFirmFeeType(ChargeTypeEnum.PERCENT.code());
						underwritingProject.setUnderwritingFee(investigationInfo
							.getUnderwritingRate()); // 承销费率
						underwritingProject.setUnderwritingFeeType(ChargeTypeEnum.PERCENT.code());
						underwritingProject.setOtherFee(investigationInfo.getOtherRate());
						underwritingProject.setOtherFeeType(ChargeTypeEnum.PERCENT.code());
						underwritingProject.setReleaseRate(investigationInfo.getIssueRate());
					}
					
				} else {// 修改
					isUpdate = true;
					BeanCopier.staticCopy(order, underwritingProject,
						UnBoxingConverter.getInstance());
					// 收费方式
					saveChargeWay(order.getChargeWayOrder(), commonInfo.getSpId(), now);
					
				}
				
				if (isUpdate) {
					FCouncilSummaryProjectUnderwritingDAO.update(underwritingProject);
				} else {
					FCouncilSummaryProjectUnderwritingDAO.insert(underwritingProject);
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult oneVoteDown(final OneVoteDownOrder order) {
		return commonProcess(order, "一票否决", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				FCouncilSummaryProjectDO projectCs = FCouncilSummaryProjectDAO.findById(order
					.getSpId());
				
				if (projectCs == null) {
					ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "会议纪要项目不存在");
				}
				
				if (!ProjectVoteResultEnum.END_PASS.code().equals(projectCs.getVoteResult())) {
					ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"未通过审批的项目");
				}
				
				if (BooleanEnum.YES.code().equals(projectCs.getOneVoteDown())) {
					ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"该项目已一票否决");
				}
				
				if (ProjectUtil.isFinancial(projectCs.getProjectCode())) { //理财产品一票否决？
				
				} else {
					ProjectDO project = projectDAO.findByProjectCode(projectCs.getProjectCode());
					if (project == null) {
						ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
					}
					
					FCouncilSummaryInfo summary = queryCouncilSummaryById(projectCs.getSummaryId());
					if (summary == null) {
						ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "会议纪要不存在");
					}
					
					CouncilProjectOrder oneVoteDownOrder = new CouncilProjectOrder();
					oneVoteDownOrder.setCouncilId(summary.getCouncilId());
					oneVoteDownOrder.setProjectCode(projectCs.getProjectCode());
					oneVoteDownOrder.setOneVoteDownMark(order.getReason());
					FcsBaseResult denyResult = councilProjectService.oneVoteDown(oneVoteDownOrder);
					
					if (!denyResult.isSuccess()) {
						ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE, "执行一票否决失败");
					}
					
					// 项目失败
					project.setPhases(ProjectPhasesEnum.COUNCIL_PHASES.code());
					project.setPhasesStatus(ProjectPhasesStatusEnum.NOPASS.code());
					project.setStatus(ProjectStatusEnum.FAILED.code());
					project.setIsApproval(BooleanEnum.NO.code());
					//				会议纪要审核通过后再标记				
					//				if (project.getLastRecouncilTime() == null
					//					&& (ProjectUtil.isEntrusted(project.getBusiType())
					//						|| ProjectUtil.isGuarantee(project.getBusiType()) || ProjectUtil
					//							.isBond(project.getBusiType()))) {//可复议标记,担保、委贷只可复议一次
					//					project.setIsRecouncil(BooleanEnum.IS.code());
					//				}
					projectDAO.update(project);
				}
				
				// 更新
				projectCs.setOneVoteDown(BooleanEnum.YES.code());
				projectCs.setOneVoteDownMark(order.getReason());
				projectCs.setOneVoteDownTime(now);
				FCouncilSummaryProjectDAO.update(projectCs);
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FCouncilSummaryProjectUnderwritingInfo queryUnderwritingProjectCsByProjectCode(	String projectCode,
																							boolean queryAll) {
		List<FCouncilSummaryProjectDO> cs = FCouncilSummaryProjectDAO
			.findByProjectCode(projectCode);
		FCouncilSummaryProjectDO common = null;
		if (ListUtil.isNotEmpty(cs)) {
			common = cs.get(0);
		}
		if (common == null)
			return null;
		
		long spId = common.getSpId();
		
		FCouncilSummaryProjectUnderwritingInfo info = convertUnderwritingProjectCsDO2Info(common,
			FCouncilSummaryProjectUnderwritingDAO.findBySpId(spId));
		if (info != null && queryAll) {
			// 收费方式
			info.setChargeWayList(queryChargeWayBySpId(spId));
		}
		return info;
	}
	
	@Override
	public FCouncilSummaryProjectUnderwritingInfo queryUnderwritingProjectCsBySpId(long spId,
																					boolean queryAll) {
		FCouncilSummaryProjectDO common = FCouncilSummaryProjectDAO.findById(spId);
		if (common == null)
			return null;
		
		FCouncilSummaryProjectUnderwritingInfo info = convertUnderwritingProjectCsDO2Info(common,
			FCouncilSummaryProjectUnderwritingDAO.findBySpId(spId));
		if (info != null && queryAll) {
			// 收费方式
			info.setChargeWayList(queryChargeWayBySpId(spId));
		}
		return info;
	}
	
	@Override
	public FCouncilSummaryProjectCreditConditionInfo queryCreditConditionBySpId(long spId,
																				boolean queryCommon) {
		
		FCouncilSummaryProjectCreditConditionInfo ccInfo = new FCouncilSummaryProjectCreditConditionInfo();
		ccInfo.setSpId(spId);
		if (queryCommon) {
			FCouncilSummaryProjectInfo projectCommon = queryProjectCsBySpId(spId);
			BeanCopier.staticCopy(projectCommon, ccInfo);
		}
		setCreditCondition2Info(ccInfo);
		return ccInfo;
	}
	
	@Override
	public FormBaseResult saveRiskHandleCs(final FCouncilSummaryRiskHandleBatchOrder order) {
		return (FormBaseResult) commonProcess(order, "保存风险处置会议纪要",
		
		new BeforeProcessInvokeService() {
			
			@SuppressWarnings({ "deprecation" })
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				// 是否初始化会议纪要
				BooleanEnum isInitSummary = (BooleanEnum) FcsPmDomainHolder.get().getAttribute(
					"isInitSummary");
				
				CouncilSummaryResult summaryResult = null;
				if (isInitSummary == BooleanEnum.IS) {// 初始化的时候直接获取已保存的会议纪要
					summaryResult = (CouncilSummaryResult) FcsPmDomainHolder.get().getAttribute(
						"result");
				} else {
					summaryResult = saveCouncilSummary(order);
				}
				
				FCouncilSummaryInfo summary = summaryResult.getSummary();
				
				if (!summaryResult.isSuccess() || summary == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "会议纪要保存失败");
				}
				
				if (ListUtil.isEmpty(order.getRiskHandleOrder())) {
					throw ExceptionFactory
						.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "风险处置项目不存在");
				}
				
				// 循环保存各个项目的会议纪要
				for (FCouncilSummaryRiskHandleOrder handleOrder : order.getRiskHandleOrder()) {
					
					FCouncilSummaryRiskHandleDO riskHandle = null;
					
					if (handleOrder.getHandleId() != null && handleOrder.getHandleId() > 0) {
						
						riskHandle = FCouncilSummaryRiskHandleDAO.findById(handleOrder
							.getHandleId());
						if (riskHandle == null) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"风险处置项目不存在");
						}
					}
					
					ProjectInfo project = projectService.queryByCode(handleOrder.getProjectCode(),
						false);
					if (project == null) {
						throw ExceptionFactory
							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
					}
					
					if (StringUtil.isBlank(handleOrder.getIsComp())) {
						handleOrder.setIsComp(BooleanEnum.NO.code());
					}
					if (StringUtil.isBlank(handleOrder.getIsExtend())) {
						handleOrder.setIsExtend(BooleanEnum.NO.code());
					}
					if (StringUtil.isBlank(handleOrder.getIsOther())) {
						handleOrder.setIsOther(BooleanEnum.NO.code());
					}
					
					boolean isUpdate = false;
					if (riskHandle == null) {// 新增
						riskHandle = new FCouncilSummaryRiskHandleDO();
						BeanCopier.staticCopy(handleOrder, riskHandle,
							UnBoxingConverter.getInstance());
						riskHandle.setRawAddTime(now);
					} else {// 修改
						isUpdate = true;
						// 一些页面用不到得数据防止丢失
						long handleId = riskHandle.getHandleId();
						BeanCopier.staticCopy(handleOrder, riskHandle,
							UnBoxingConverter.getInstance());
						riskHandle.setHandleId(handleId);
					}
					
					riskHandle.setSummaryId(summary.getSummaryId());
					riskHandle.setProjectCode(project.getProjectCode());
					riskHandle.setProjectName(project.getProjectName());
					riskHandle.setCustomerId(project.getCustomerId());
					riskHandle.setCustomerName(project.getCustomerName());
					riskHandle.setCustomerType(project.getCustomerType().code());
					
					//代偿
					if (StringUtil.equals(BooleanEnum.IS.code(), riskHandle.getIsComp())) {
						Money limitAmount = project.getBalance();
						if (riskHandle.getCompPrincipal().greaterThan(limitAmount)) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE,
								"代偿本金不能大于在保余额：" + limitAmount.toStandardString() + "元");
						}
					}
					
					if (isUpdate) {
						FCouncilSummaryRiskHandleDAO.update(riskHandle);
					} else {
						FCouncilSummaryRiskHandleDAO.insert(riskHandle);
					}
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public List<FCouncilSummaryRiskHandleInfo> queryRiskHandleCsBySummaryId(long summaryId) {
		List<FCouncilSummaryRiskHandleDO> data = FCouncilSummaryRiskHandleDAO
			.findBySummaryId(summaryId);
		if (ListUtil.isEmpty(data))
			return null;
		List<FCouncilSummaryRiskHandleInfo> list = Lists.newArrayList();
		for (FCouncilSummaryRiskHandleDO DO : data) {
			list.add(convertRiskHandleCsDO2Info(DO));
		}
		return list;
	}
	
	@Override
	public List<FCouncilSummaryRiskHandleInfo> queryRiskHandleCsByProjectCode(String projectCode) {
		List<FCouncilSummaryRiskHandleDO> data = FCouncilSummaryRiskHandleDAO
			.findByProjectCode(projectCode);
		List<FCouncilSummaryRiskHandleInfo> list = Lists.newArrayList();
		if (data != null) {
			for (FCouncilSummaryRiskHandleDO DO : data) {
				list.add(convertRiskHandleCsDO2Info(DO));
			}
		}
		return list;
	}
	
	@Override
	public List<FCouncilSummaryRiskHandleInfo> queryApprovalRiskHandleCsByProjectCode(	String projectCode) {
		List<FCouncilSummaryRiskHandleDO> data = busiDAO.findApprovalRiskHandleSummary(projectCode);
		List<FCouncilSummaryRiskHandleInfo> list = Lists.newArrayList();
		if (data != null) {
			for (FCouncilSummaryRiskHandleDO DO : data) {
				list.add(convertRiskHandleCsDO2Info(DO));
			}
		}
		return list;
	}
	
	@Override
	public FCouncilSummaryProjectGuarantorInfo queryGuarantorById(long itemId) {
		FCouncilSummaryProjectGuarantorDO DO = FCouncilSummaryProjectGuarantorDAO.findById(itemId);
		if (DO != null) {
			FCouncilSummaryProjectGuarantorInfo info = new FCouncilSummaryProjectGuarantorInfo();
			BeanCopier.staticCopy(DO, info);
			info.setGuarantorType(GuarantorTypeEnum.getByCode(DO.getGuarantorType()));
			return info;
		}
		return null;
	}
	
	@Override
	public FCouncilSummaryProjectPledgeAssetInfo queryPledgeAssetById(long itemId) {
		FCouncilSummaryProjectPledgeAssetDO DO = FCouncilSummaryProjectPledgeAssetDAO
			.findById(itemId);
		if (DO != null) {
			FCouncilSummaryProjectPledgeAssetInfo info = new FCouncilSummaryProjectPledgeAssetInfo();
			BeanCopier.staticCopy(DO, info);
			info.setType(GuaranteeTypeEnum.getByCode(DO.getType()));
			return info;
		}
		return null;
	}
	
	/**
	 * 保存收费方式
	 * 
	 * @param data
	 * @param spId
	 */
	@SuppressWarnings("deprecation")
	private void saveChargeWay(List<FCouncilSummaryProjectChargeWayOrder> data, long spId, Date now) {
		
		if (data == null || spId <= 0)
			return;
		
		FCouncilSummaryProjectChargeWayDAO.deleteBySpId(spId);
		for (FCouncilSummaryProjectChargeWayOrder chargeWayOrder : data) {
			
			if (chargeWayOrder.isNull())
				continue;
			
			FCouncilSummaryProjectChargeWayDO cwd = new FCouncilSummaryProjectChargeWayDO();
			BeanCopier.staticCopy(chargeWayOrder, cwd, UnBoxingConverter.getInstance());
			cwd.setSpId(spId);
			if (chargeWayOrder.getAmount() != null) {
				cwd.setAmount(Money.amout(String.valueOf(chargeWayOrder.getAmount())));
			}
			cwd.setRawAddTime(now);
			FCouncilSummaryProjectChargeWayDAO.insert(cwd);
		}
	}
	
	/**
	 * 保存还款方式
	 * 
	 * @param data
	 * @param spId
	 */
	@SuppressWarnings("deprecation")
	private void saveRepayWay(List<FCouncilSummaryProjectRepayWayOrder> data, long spId, Date now) {
		
		if (data == null || spId <= 0)
			return;
		
		FCouncilSummaryProjectRepayWayDAO.deleteBySpId(spId);
		for (FCouncilSummaryProjectRepayWayOrder repayWayOrder : data) {
			if (repayWayOrder.isNull())
				continue;
			
			FCouncilSummaryProjectRepayWayDO rwd = new FCouncilSummaryProjectRepayWayDO();
			BeanCopier.staticCopy(repayWayOrder, rwd, UnBoxingConverter.getInstance());
			rwd.setSpId(spId);
			rwd.setRawAddTime(now);
			FCouncilSummaryProjectRepayWayDAO.insert(rwd);
		}
	}
	
	/***
	 * 保存 抵(质)押
	 * @param spId
	 * @param order
	 */
	private void savePledgeAssets(long spId, FCouncilSummaryProjectCreditConditionOrder order) {
		Map<GuaranteeTypeEnum, List<FCouncilSummaryProjectPledgeAssetDO>> map = new HashMap<>();
		List<FCouncilSummaryProjectPledgeAssetDO> list = FCouncilSummaryProjectPledgeAssetDAO
			.findBySpId(spId);
		if (ListUtil.isNotEmpty(list)) {
			for (FCouncilSummaryProjectPledgeAssetDO doObj : list) {
				GuaranteeTypeEnum type = GuaranteeTypeEnum.getByCode(doObj.getType());
				List<FCouncilSummaryProjectPledgeAssetDO> items = map.get(type);
				if (null == items) {
					items = new ArrayList<>();
					map.put(type, items);
				}
				items.add(doObj);
			}
		}
		savePledgeAssets(spId, GuaranteeTypeEnum.PLEDGE, order.getPledgeOrders(),
			map.get(GuaranteeTypeEnum.PLEDGE));
		savePledgeAssets(spId, GuaranteeTypeEnum.MORTGAGE, order.getMortgageOrders(),
			map.get(GuaranteeTypeEnum.MORTGAGE));
	}
	
	/***
	 * 保存抵(质)押
	 * @param spId
	 * @param type
	 * @param pledges
	 * @param items
	 */
	private void savePledgeAssets(long spId, GuaranteeTypeEnum type,
									List<FCouncilSummaryProjectPledgeAssetOrder> pledges,
									List<FCouncilSummaryProjectPledgeAssetDO> items) {
		if (spId <= 0 || null == type) {
			return;
		}
		
		if (ListUtil.isEmpty(pledges)) {
			FCouncilSummaryProjectPledgeAssetDAO.deleteBySpIdAndType(spId, type.code());
			return;
		}
		
		Map<Long, FCouncilSummaryProjectPledgeAssetDO> map = new HashMap<>();
		if (ListUtil.isNotEmpty(items)) {
			for (FCouncilSummaryProjectPledgeAssetDO doObj : items) {
				map.put(doObj.getId(), doObj);
			}
		}
		
		int sortOrder = 1;
		Date now = getSysdate();
		for (FCouncilSummaryProjectPledgeAssetOrder order : pledges) {
			order.setSortOrder(sortOrder++);
			order.setType(type.code());
			FCouncilSummaryProjectPledgeAssetDO doObj = map.get(order.getId());
			if (null == doObj) {
				doObj = new FCouncilSummaryProjectPledgeAssetDO();
				BeanCopier.staticCopy(order, doObj);
				doObj.setSpId(spId);
				doObj.setType(type.code());
				doObj.setTypeDesc(type.message());
				doObj.setRawAddTime(now);
				FCouncilSummaryProjectPledgeAssetDAO.insert(doObj);
			} else {
				if (!isEqual(order, doObj)) {
					BeanCopier.staticCopy(order, doObj);
					doObj.setSpId(spId);
					FCouncilSummaryProjectPledgeAssetDAO.update(doObj);
				}
			}
			map.remove(order.getId());
		}
		
		if (null != map && map.size() > 0) {
			for (long id : map.keySet()) {
				FCouncilSummaryProjectPledgeAssetDAO.deleteById(id);
			}
		}
	}
	
	/**
	 * 抵(质)押是否相同
	 * @param order
	 * @param doObj
	 * @return
	 */
	private boolean isEqual(FCouncilSummaryProjectPledgeAssetOrder order,
							FCouncilSummaryProjectPledgeAssetDO doObj) {
		return order.getAssetsId() == doObj.getAssetsId()
				&& StringUtil.equals(order.getAssetType(), doObj.getAssetType())
				&& StringUtil.equals(order.getOwnershipName(), doObj.getOwnershipName())
				&& order.getEvaluationPrice().equals(doObj.getEvaluationPrice())
				&& new Double(order.getPledgeRate()).equals(new Double(doObj.getPledgeRate()))
				&& StringUtil.equals(order.getSynPosition(), doObj.getSynPosition())
				&& StringUtil.equals(order.getPostposition(), doObj.getPostposition())
				&& order.getDebtedAmount().equals(doObj.getDebtedAmount())
				&& order.getSortOrder() == doObj.getSortOrder();
	}
	
	/***
	 * 保存过程控制
	 * @param spId
	 * @param type
	 * @param orders
	 * @param items
	 */
	private void saveProcesses(long spId, FCouncilSummaryProjectCreditConditionOrder order) {
		Map<ProcessControlEnum, List<FCouncilSummaryProjectProcessControlDO>> map = new HashMap<>();
		List<FCouncilSummaryProjectProcessControlDO> list = FCouncilSummaryProjectProcessControlDAO
			.findBySpId(spId);
		if (ListUtil.isNotEmpty(list)) {
			for (FCouncilSummaryProjectProcessControlDO doObj : list) {
				ProcessControlEnum type = ProcessControlEnum.getByCode(doObj.getType());
				List<FCouncilSummaryProjectProcessControlDO> items = map.get(type);
				if (null == items) {
					items = new ArrayList<>();
					map.put(type, items);
				}
				items.add(doObj);
			}
		}
		saveProcesses(spId, ProcessControlEnum.CUSTOMER_GRADE, order.getCustomerGrades(),
			map.get(ProcessControlEnum.CUSTOMER_GRADE));
		saveProcesses(spId, ProcessControlEnum.DEBT_RATIO, order.getDebtRatios(),
			map.get(ProcessControlEnum.DEBT_RATIO));
		saveProcesses(spId, ProcessControlEnum.OTHER, order.getOthers(),
			map.get(ProcessControlEnum.OTHER));
	}
	
	/***
	 * 保存过程控制
	 * @param spId
	 * @param type
	 * @param orders
	 * @param items
	 */
	private void saveProcesses(long spId, ProcessControlEnum type,
								List<FCouncilSummaryProjectProcessControlOrder> orders,
								List<FCouncilSummaryProjectProcessControlDO> items) {
		if (spId <= 0 || type == null) {
			return;
		}
		
		if (ListUtil.isEmpty(orders)) {
			FCouncilSummaryProjectProcessControlDAO.deleteBySpIdAndType(spId, type.code());
			return;
		}
		
		Map<Long, FCouncilSummaryProjectProcessControlDO> map = Maps.newHashMap();
		if (ListUtil.isNotEmpty(items)) {
			for (FCouncilSummaryProjectProcessControlDO item : items) {
				map.put(item.getId(), item);
			}
		}
		
		Date now = getSysdate();
		int sortOrder = 1;
		for (FCouncilSummaryProjectProcessControlOrder order : orders) {
			
			if (order.isNull()) {
				continue;
			}
			
			order.setType(type.code());
			order.setSpId(spId);
			order.setSortOrder(sortOrder++);
			FCouncilSummaryProjectProcessControlDO doObj = map.get(order.getId());
			if (null == doObj) {
				doObj = new FCouncilSummaryProjectProcessControlDO();
				BeanCopier.staticCopy(order, doObj);
				doObj.setRawAddTime(now);
				FCouncilSummaryProjectProcessControlDAO.insert(doObj);
			} else {
				if (!isEqual(order, doObj)) {
					BeanCopier.staticCopy(order, doObj);
					doObj.setSpId(spId);
					FCouncilSummaryProjectProcessControlDAO.update(doObj);
				}
			}
			map.remove(order.getId());
		}
		
		if (null != map && map.size() > 0) {
			for (long id : map.keySet()) {
				FCouncilSummaryProjectProcessControlDAO.deleteById(id);
			}
		}
	}
	
	/***
	 * 过程控制是否相同
	 * @param order
	 * @param doObj
	 * @return
	 */
	private boolean isEqual(FCouncilSummaryProjectProcessControlOrder order,
							FCouncilSummaryProjectProcessControlDO doObj) {
		return StringUtil.equals(order.getUpRate(), doObj.getUpRate())
				&& StringUtil.equals(order.getUpBp(), doObj.getUpBp())
				&& StringUtil.equals(order.getDownRate(), doObj.getDownRate())
				&& StringUtil.equals(order.getDownBp(), doObj.getDownBp())
				&& StringUtil.equals(order.getContent(), doObj.getContent());
	}
	
	/**
	 * 保存保证
	 * @param spId
	 * @param guarantors
	 */
	private void saveGuarantors(long spId, List<FCouncilSummaryProjectGuarantorOrder> guarantors) {
		if (spId <= 0) {
			return;
		}
		
		if (ListUtil.isEmpty(guarantors)) {
			FCouncilSummaryProjectGuarantorDAO.deleteBySpId(spId);
			return;
		}
		
		List<FCouncilSummaryProjectGuarantorDO> items = FCouncilSummaryProjectGuarantorDAO
			.findBySpId(spId);
		Map<Long, FCouncilSummaryProjectGuarantorDO> map = new HashMap<>();
		if (ListUtil.isNotEmpty(items)) {
			for (FCouncilSummaryProjectGuarantorDO doObj : items) {
				map.put(doObj.getId(), doObj);
			}
		}
		
		Date now = getSysdate();
		int sortOrder = 1;
		for (FCouncilSummaryProjectGuarantorOrder order : guarantors) {
			if (!order.isNull()) {
				order.setSpId(spId);
				order.setSortOrder(sortOrder++);
				FCouncilSummaryProjectGuarantorDO doObj = map.get(order.getId());
				if (null == doObj) {
					doObj = new FCouncilSummaryProjectGuarantorDO();
					BeanCopier.staticCopy(order, doObj);
					doObj.setGuaranteeAmount(order.getGuaranteeAmount());
					doObj.setRawAddTime(now);
					FCouncilSummaryProjectGuarantorDAO.insert(doObj);
				} else {
					if (!isEqual(order, doObj)) {
						BeanCopier.staticCopy(order, doObj);
						doObj.setSpId(spId);
						doObj.setGuaranteeAmount(order.getGuaranteeAmount());
						FCouncilSummaryProjectGuarantorDAO.update(doObj);
					}
				}
				map.remove(order.getId());
			}
		}
		
		if (null != map && map.size() > 0) {
			for (long id : map.keySet()) {
				FCouncilSummaryProjectGuarantorDAO.deleteById(id);
			}
		}
	}
	
	/**
	 * 保证是否相同
	 * @param order
	 * @param doObj
	 * @return
	 */
	private boolean isEqual(FCouncilSummaryProjectGuarantorOrder order,
							FCouncilSummaryProjectGuarantorDO doObj) {
		return order.getSortOrder() == doObj.getSortOrder()
				&& StringUtil.equals(order.getGuarantorType(), doObj.getGuarantorType())
				&& StringUtil.equals(order.getGuarantor(), doObj.getGuarantor())
				&& StringUtil.equals(order.getGuaranteeWay(), doObj.getGuaranteeWay())
				&& order.getGuaranteeAmount().equals(doObj.getGuaranteeAmount());
	}
	
	/**
	 * 生成会议纪要编号
	 * 
	 * @param prefix
	 * @return
	 */
	private synchronized String genSummaryCode(String prefix) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		long seq = dateSeqService.getNextSeqNumberWithoutCache(
			SysDateSeqNameEnum.COUNCIL_SUMMARY_CODE_SEQ.code() + year, false);
		return (prefix == null ? "" : prefix) + "【" + year + "】"
				+ StringUtil.leftPad(String.valueOf(seq), 3, "0");
	}
	
	/**
	 * 通过的项目和未通过得单独编号
	 * 
	 * @param isPass
	 * @return
	 */
	private synchronized String genSpCode(ProjectVoteResultEnum voteResult) {
		if (voteResult == ProjectVoteResultEnum.END_PASS
			|| voteResult == ProjectVoteResultEnum.END_NOPASS) {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			long seq = dateSeqService.getNextSeqNumberWithoutCache(
				SysDateSeqNameEnum.COUNCIL_SP_CODE_SEQ.code() + year, false);
			return "风控批复【" + year + "】第" + StringUtil.leftPad(String.valueOf(seq), 3, "0") + "号";
		} else {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			long seq = dateSeqService.getNextSeqNumberWithoutCache(
				SysDateSeqNameEnum.COUNCIL_SP_CODE_SEQ.code() + "_FAILED" + year, false);
			return year + StringUtil.leftPad(String.valueOf(seq), 3, "0");
		}
	}
	
	/**
	 * 获取投票结果
	 * 
	 * @param voteResultInfo
	 * @return
	 */
	private String getVoteResultDesc(CouncilProjectInfo voteResultInfo) {
		
		String result = null;
		if (voteResultInfo != null) {
			ProjectVoteResultEnum voteResult = voteResultInfo.getProjectVoteResult();
			if (voteResult == ProjectVoteResultEnum.NOT_BEGIN) {
				result = "未开始投票";
			} else if (voteResult == ProjectVoteResultEnum.IN_VOTE) {
				result = "投票中";
			} else {
				String vr = "出席会议的委员" + voteResultInfo.getJudgesCount() + "人，其中：";
				if (voteResultInfo.getPassCount() > 0) {
					vr += "同意" + voteResultInfo.getPassCount() + "票，";
				}
				if (voteResultInfo.getNotpassCount() > 0) {
					vr += "不同意" + voteResultInfo.getNotpassCount() + "票，";
				}
				if (voteResultInfo.getQuitCount() > 0) {
					vr += "本次不议" + voteResultInfo.getQuitCount() + "票，";
				}
				vr += "投票结果为 " + voteResult.getMessage();
				result = vr;
			}
			//风控委秘书执行了本次不议
			if (voteResultInfo.getRiskSecretaryQuit() == BooleanEnum.YES) {
				// 添加判断，若投票结果尚未出现且风控委秘书已执行本次不议，展示【未全部投票】
				if (voteResult == ProjectVoteResultEnum.NOT_BEGIN
					|| voteResult == ProjectVoteResultEnum.IN_VOTE) {
					result = "未全部投票";
				}
				result += "，<span style='color:red;'> 风控委秘书执行了本次不议</span>";
			}
		}
		return result;
	}
	
	/**
	 * 从尽职调查中copy抵（质）押
	 * 
	 * @param formId尽职调查表单ID
	 * @param spId 项目信息ID
	 */
	private void copyPledgeFromInvestigation(long formId, long spId, Date now) {
		
		List<FInvestigationCreditSchemePledgeAssetDO> pledges = FInvestigationCreditSchemePledgeAssetDAO
			.findByFormId(formId);
		if (pledges != null) {
			for (FInvestigationCreditSchemePledgeAssetDO pledge : pledges) {
				FCouncilSummaryProjectPledgeAssetDO sPledge = new FCouncilSummaryProjectPledgeAssetDO();
				BeanCopier.staticCopy(pledge, sPledge);
				sPledge.setId(0);
				sPledge.setSpId(spId);
				sPledge.setRawAddTime(now);
				FCouncilSummaryProjectPledgeAssetDAO.insert(sPledge);
			}
		}
	}
	
	/**
	 * 从尽职调查中copy保证人
	 * 
	 * @param schemeId 授信方案ID
	 * @param spId 项目信息ID
	 */
	private void copyGuarantorFromInvestigation(long schemeId, long spId, Date now) {
		List<FInvestigationCreditSchemeGuarantorDO> guarantors = FInvestigationCreditSchemeGuarantorDAO
			.findBySchemeId(schemeId);
		if (guarantors != null) {
			for (FInvestigationCreditSchemeGuarantorDO guarantor : guarantors) {
				FCouncilSummaryProjectGuarantorDO sGuarantor = new FCouncilSummaryProjectGuarantorDO();
				BeanCopier.staticCopy(guarantor, sGuarantor);
				sGuarantor.setId(0);
				sGuarantor.setSpId(spId);
				sGuarantor.setRawAddTime(now);
				FCouncilSummaryProjectGuarantorDAO.insert(sGuarantor);
			}
		}
	}
	
	/**
	 * 从尽职调查中copy过程控制
	 * 
	 * @param formId 尽调表单ID
	 * @param spId 项目信息ID
	 */
	private void copyProcessControlFromInvestigation(long formId, long spId, Date now) {
		
		List<FInvestigationCreditSchemeProcessControlDO> processControls = FInvestigationCreditSchemeProcessControlDAO
			.findByFormId(formId);
		if (processControls != null) {
			for (FInvestigationCreditSchemeProcessControlDO processControl : processControls) {
				FCouncilSummaryProjectProcessControlDO spc = new FCouncilSummaryProjectProcessControlDO();
				BeanCopier.staticCopy(processControl, spc);
				spc.setId(0);
				spc.setSpId(spId);
				spc.setRawAddTime(now);
				FCouncilSummaryProjectProcessControlDAO.insert(spc);
			}
		}
	}
	
	/**
	 * 查询收费方式
	 * @param spId
	 * @return
	 */
	private List<FCouncilSummaryProjectChargeWayInfo> queryChargeWayBySpId(long spId) {
		List<FCouncilSummaryProjectChargeWayDO> dos = FCouncilSummaryProjectChargeWayDAO
			.findBySpId(spId);
		if (dos != null) {
			List<FCouncilSummaryProjectChargeWayInfo> data = Lists.newArrayList();
			for (FCouncilSummaryProjectChargeWayDO DO : dos) {
				FCouncilSummaryProjectChargeWayInfo info = new FCouncilSummaryProjectChargeWayInfo();
				BeanCopier.staticCopy(DO, info);
				info.setAmountType(ChargeTypeEnum.getByCode(DO.getAmountType()));
				info.setPhase(ChargeWayPhaseEnum.getByCode(DO.getPhase()));
				data.add(info);
			}
			return data;
		}
		return null;
	}
	
	/**
	 * 查询还款方式
	 * @param spId
	 * @return
	 */
	private List<FCouncilSummaryProjectRepayWayInfo> queryRepayWayBySpId(long spId) {
		List<FCouncilSummaryProjectRepayWayDO> dos = FCouncilSummaryProjectRepayWayDAO
			.findBySpId(spId);
		
		if (dos != null) {
			List<FCouncilSummaryProjectRepayWayInfo> data = Lists.newArrayList();
			
			for (FCouncilSummaryProjectRepayWayDO DO : dos) {
				FCouncilSummaryProjectRepayWayInfo info = new FCouncilSummaryProjectRepayWayInfo();
				BeanCopier.staticCopy(DO, info);
				info.setPhase(RepayWayPhaseEnum.getByCode(DO.getPhase()));
				data.add(info);
			}
			
			return data;
		}
		return null;
	}
	
	/**
	 * 设置授信条件信息
	 * @param info
	 */
	private void setCreditCondition2Info(FCouncilSummaryProjectCreditConditionInfo ccInfo) {
		
		/**
		 * 查询抵（质）押
		 */
		List<FCouncilSummaryProjectPledgeAssetInfo> pledges = Lists.newArrayList();
		List<FCouncilSummaryProjectPledgeAssetInfo> mortgages = Lists.newArrayList();
		List<FCouncilSummaryProjectPledgeAssetDO> list = FCouncilSummaryProjectPledgeAssetDAO
			.findBySpId(ccInfo.getSpId());
		Money pledgeAssessPrice = new Money(0L); //抵押评估价格
		Money pledgePrice = new Money(0L); //抵押价格
		Money mortgageAssessPrice = new Money(0L); //质押评估价格
		Money mortgagePrice = new Money(0L); //质押价格
		if (ListUtil.isNotEmpty(list)) {
			for (FCouncilSummaryProjectPledgeAssetDO doObj : list) {
				GuaranteeTypeEnum type = GuaranteeTypeEnum.getByCode(doObj.getType());
				FCouncilSummaryProjectPledgeAssetInfo info = new FCouncilSummaryProjectPledgeAssetInfo();
				BeanCopier.staticCopy(doObj, info);
				info.setType(type);
				if (type == GuaranteeTypeEnum.PLEDGE) {
					pledges.add(info);
					pledgeAssessPrice.addTo(info.getEvaluationPrice());
					pledgePrice.addTo(info.getEvaluationPrice().multiply(info.getPledgeRate()));
				} else if (type == GuaranteeTypeEnum.MORTGAGE) {
					mortgages.add(info);
					mortgageAssessPrice.addTo(info.getEvaluationPrice());
					mortgagePrice.addTo(info.getEvaluationPrice().multiply(info.getPledgeRate()));
				}
			}
		}
		
		ccInfo.setPledges(pledges);
		ccInfo.setMortgages(mortgages);
		ccInfo.setPledgeAssessPrice(pledgeAssessPrice);
		ccInfo.setPledgePrice(pledgePrice.divide(100L));
		ccInfo.setMortgageAssessPrice(mortgageAssessPrice);
		ccInfo.setMortgagePrice(mortgagePrice.divide(100L));
		
		/***
		 * 查询保证
		 */
		List<FCouncilSummaryProjectGuarantorInfo> guarantors = new ArrayList<>();
		List<FCouncilSummaryProjectGuarantorDO> guarantorDOs = FCouncilSummaryProjectGuarantorDAO
			.findBySpId(ccInfo.getSpId());
		Money guarantorAmount = new Money(0L);
		for (FCouncilSummaryProjectGuarantorDO guarantor : guarantorDOs) {
			FCouncilSummaryProjectGuarantorInfo guarantorInfo = new FCouncilSummaryProjectGuarantorInfo();
			BeanCopier.staticCopy(guarantor, guarantorInfo);
			guarantorInfo
				.setGuarantorType(GuarantorTypeEnum.getByCode(guarantor.getGuarantorType()));
			guarantors.add(guarantorInfo);
			guarantorAmount.addTo(guarantor.getGuaranteeAmount());
		}
		ccInfo.setGuarantors(guarantors);
		ccInfo.setGuarantorAmount(guarantorAmount);
		
		/**
		 * 查询过程控制
		 */
		List<FCouncilSummaryProjectProcessControlDO> items = FCouncilSummaryProjectProcessControlDAO
			.findBySpId(ccInfo.getSpId());
		if (ListUtil.isNotEmpty(items)) {
			Map<ProcessControlEnum, List<FCouncilSummaryProjectProcessControlInfo>> map = Maps
				.newHashMap();
			for (FCouncilSummaryProjectProcessControlDO doObj : items) {
				ProcessControlEnum type = ProcessControlEnum.getByCode(doObj.getType());
				List<FCouncilSummaryProjectProcessControlInfo> pList = map.get(type);
				if (null == pList) {
					pList = Lists.newArrayList();
					map.put(type, pList);
				}
				FCouncilSummaryProjectProcessControlInfo info = new FCouncilSummaryProjectProcessControlInfo();
				BeanCopier.staticCopy(doObj, info);
				info.setType(type);
				pList.add(info);
			}
			
			ccInfo.setCustomerGrades(map.get(ProcessControlEnum.CUSTOMER_GRADE));
			ccInfo.setDebtRatios(map.get(ProcessControlEnum.DEBT_RATIO));
			ccInfo.setOthers(map.get(ProcessControlEnum.OTHER));
		}
	}
	
	/**
	 * 会议纪要DO转Info
	 * @param DO
	 * @return
	 */
	private FCouncilSummaryInfo covertCouncilSummaryDO2Info(FCouncilSummaryDO DO) {
		if (DO == null)
			return null;
		FCouncilSummaryInfo info = new FCouncilSummaryInfo();
		BeanCopier.staticCopy(DO, info);
		CouncilQueryOrder order = new CouncilQueryOrder();
		order.setCouncilId(DO.getCouncilId());
		order.setQueryFromSummary(true);
		QueryBaseBatchResult<CouncilInfo> result = councilService.queryCouncil(order);
		if (result.getTotalCount() > 0) {
			info.setCouncil(result.getPageList().get(0));
		}
		info.setCouncilType(CouncilTypeEnum.getByCode(DO.getCouncilType()));
		return info;
	}
	
	/**
	 * 会议纪要项目DO转Info
	 * @param DO
	 * @return
	 */
	private FCouncilSummaryProjectInfo covertCouncilSummaryProjectDO2Info(	FCouncilSummaryProjectDO DO) {
		if (DO == null)
			return null;
		FCouncilSummaryProjectInfo info = new FCouncilSummaryProjectInfo();
		BeanCopier.staticCopy(DO, info);
		info.setIsChargeEveryBeginning(BooleanEnum.getByCode(DO.getIsChargeEveryBeginning()));
		info.setIsRepayEqual(BooleanEnum.getByCode(DO.getIsRepayEqual()));
		info.setRepayWay(RepayWayEnum.getByCode(DO.getRepayWay()));
		info.setVoteResult(ProjectVoteResultEnum.getByCode(DO.getVoteResult()));
		info.setTimeUnit(TimeUnitEnum.getByCode(DO.getTimeUnit()));
		info.setChargeWay(ChargeWayEnum.getByCode(DO.getChargeWay()));
		info.setChargePhase(ChargePhaseEnum.getByCode(DO.getChargePhase()));
		info.setCustomerType(CustomerTypeEnum.getByCode(DO.getCustomerType()));
		info.setIsDel(BooleanEnum.getByCode(DO.getIsDel()));
		info.setIsMaximumAmount(BooleanEnum.getByCode(DO.getIsMaximumAmount()));
		info.setOneVoteDown(BooleanEnum.getByCode(DO.getOneVoteDown()));
		
		return info;
	}
	
	/**
	 * 担保DO转info
	 * @param common
	 * @param DO
	 * @return
	 */
	private FCouncilSummaryProjectGuaranteeInfo convertGuaranteeProjectCsDO2Info(	FCouncilSummaryProjectDO common,
																					FCouncilSummaryProjectGuaranteeDO DO) {
		if (common == null)
			return null;
		
		FCouncilSummaryProjectGuaranteeInfo info = new FCouncilSummaryProjectGuaranteeInfo();
		if (DO != null) {
			BeanCopier.staticCopy(DO, info);
			info.setInterestRateFloat(CompareEnum.getByCode(DO.getInterestRateFloat()));
			info.setGuaranteeFeeType(ChargeTypeEnum.getByCode(DO.getGuaranteeFeeType()));
			info.setTotalCostType(ChargeTypeEnum.getByCode(DO.getTotalCostType()));
			info.setOtherFeeType(ChargeTypeEnum.getByCode(DO.getOtherFeeType()));
		}
		
		BeanCopier.staticCopy(common, info);
		info.setIsChargeEveryBeginning(BooleanEnum.getByCode(common.getIsChargeEveryBeginning()));
		info.setIsRepayEqual(BooleanEnum.getByCode(common.getIsRepayEqual()));
		info.setRepayWay(RepayWayEnum.getByCode(common.getRepayWay()));
		info.setVoteResult(ProjectVoteResultEnum.getByCode(common.getVoteResult()));
		info.setTimeUnit(TimeUnitEnum.getByCode(common.getTimeUnit()));
		info.setChargeWay(ChargeWayEnum.getByCode(common.getChargeWay()));
		info.setChargePhase(ChargePhaseEnum.getByCode(common.getChargePhase()));
		info.setCustomerType(CustomerTypeEnum.getByCode(common.getCustomerType()));
		info.setIsMaximumAmount(BooleanEnum.getByCode(common.getIsMaximumAmount()));
		info.setOneVoteDown(BooleanEnum.getByCode(common.getOneVoteDown()));
		info.setIsDel(BooleanEnum.getByCode(common.getIsDel()));
		return info;
	}
	
	/***
	 * 委贷DO转info
	 * @param common
	 * @param DO
	 * @return
	 */
	private FCouncilSummaryProjectEntrustedInfo convertEntrustedProjectCsDO2Info(	FCouncilSummaryProjectDO common,
																					FCouncilSummaryProjectEntrustedDO DO) {
		if (common == null)
			return null;
		
		FCouncilSummaryProjectEntrustedInfo info = new FCouncilSummaryProjectEntrustedInfo();
		if (DO != null) {
			BeanCopier.staticCopy(DO, info);
			info.setOtherFeeType(ChargeTypeEnum.getByCode(DO.getOtherFeeType()));
		}
		
		BeanCopier.staticCopy(common, info);
		info.setIsChargeEveryBeginning(BooleanEnum.getByCode(common.getIsChargeEveryBeginning()));
		info.setIsRepayEqual(BooleanEnum.getByCode(common.getIsRepayEqual()));
		info.setRepayWay(RepayWayEnum.getByCode(common.getRepayWay()));
		info.setVoteResult(ProjectVoteResultEnum.getByCode(common.getVoteResult()));
		info.setTimeUnit(TimeUnitEnum.getByCode(common.getTimeUnit()));
		info.setChargeWay(ChargeWayEnum.getByCode(common.getChargeWay()));
		info.setChargePhase(ChargePhaseEnum.getByCode(common.getChargePhase()));
		info.setCustomerType(CustomerTypeEnum.getByCode(common.getCustomerType()));
		info.setIsMaximumAmount(BooleanEnum.getByCode(common.getIsMaximumAmount()));
		info.setOneVoteDown(BooleanEnum.getByCode(common.getOneVoteDown()));
		info.setIsDel(BooleanEnum.getByCode(common.getIsDel()));
		
		return info;
	}
	
	/**
	 * 发债会议纪要DO转Info
	 * @param common
	 * @param DO
	 * @return
	 */
	private FCouncilSummaryProjectBondInfo covertBondProjectCsDO2Info(	FCouncilSummaryProjectDO common,
																		FCouncilSummaryProjectBondDO DO) {
		if (common == null)
			return null;
		
		FCouncilSummaryProjectBondInfo info = new FCouncilSummaryProjectBondInfo();
		if (DO != null) {
			BeanCopier.staticCopy(DO, info);
			info.setInterestRateFloat(CompareEnum.getByCode(DO.getInterestRateFloat()));
			info.setGuaranteeFeeType(ChargeTypeEnum.getByCode(DO.getGuaranteeFeeType()));
			info.setTotalCostType(ChargeTypeEnum.getByCode(DO.getTotalCostType()));
			info.setOtherFeeType(ChargeTypeEnum.getByCode(DO.getOtherFeeType()));
		}
		
		BeanCopier.staticCopy(common, info);
		info.setIsChargeEveryBeginning(BooleanEnum.getByCode(common.getIsChargeEveryBeginning()));
		info.setIsRepayEqual(BooleanEnum.getByCode(common.getIsRepayEqual()));
		info.setRepayWay(RepayWayEnum.getByCode(common.getRepayWay()));
		info.setVoteResult(ProjectVoteResultEnum.getByCode(common.getVoteResult()));
		info.setTimeUnit(TimeUnitEnum.getByCode(common.getTimeUnit()));
		info.setChargeWay(ChargeWayEnum.getByCode(common.getChargeWay()));
		info.setChargePhase(ChargePhaseEnum.getByCode(common.getChargePhase()));
		info.setCustomerType(CustomerTypeEnum.getByCode(common.getCustomerType()));
		info.setIsMaximumAmount(BooleanEnum.getByCode(common.getIsMaximumAmount()));
		info.setOneVoteDown(BooleanEnum.getByCode(common.getOneVoteDown()));
		info.setIsDel(BooleanEnum.getByCode(common.getIsDel()));
		
		return info;
	}
	
	/***
	 * 承销DO转info
	 * @param common
	 * @param DO
	 * @return
	 */
	private FCouncilSummaryProjectUnderwritingInfo convertUnderwritingProjectCsDO2Info(	FCouncilSummaryProjectDO common,
																						FCouncilSummaryProjectUnderwritingDO DO) {
		if (common == null)
			return null;
		
		FCouncilSummaryProjectUnderwritingInfo info = new FCouncilSummaryProjectUnderwritingInfo();
		if (DO != null) {
			BeanCopier.staticCopy(DO, info);
			info.setBourseFeeType(ChargeTypeEnum.getByCode(DO.getBourseFeeType()));
			info.setLawFirmFeeType(ChargeTypeEnum.getByCode(DO.getLawFirmFeeType()));
			info.setClubFeeType(ChargeTypeEnum.getByCode(DO.getClubFeeType()));
			info.setUnderwritingFeeType(ChargeTypeEnum.getByCode(DO.getUnderwritingFeeType()));
			info.setOtherFeeType(ChargeTypeEnum.getByCode(DO.getOtherFeeType()));
		}
		
		BeanCopier.staticCopy(common, info);
		info.setIsChargeEveryBeginning(BooleanEnum.getByCode(common.getIsChargeEveryBeginning()));
		info.setIsRepayEqual(BooleanEnum.getByCode(common.getIsRepayEqual()));
		info.setRepayWay(RepayWayEnum.getByCode(common.getRepayWay()));
		info.setVoteResult(ProjectVoteResultEnum.getByCode(common.getVoteResult()));
		info.setTimeUnit(TimeUnitEnum.getByCode(common.getTimeUnit()));
		info.setChargeWay(ChargeWayEnum.getByCode(common.getChargeWay()));
		info.setChargePhase(ChargePhaseEnum.getByCode(common.getChargePhase()));
		info.setCustomerType(CustomerTypeEnum.getByCode(common.getCustomerType()));
		info.setIsMaximumAmount(BooleanEnum.getByCode(common.getIsMaximumAmount()));
		info.setOneVoteDown(BooleanEnum.getByCode(common.getOneVoteDown()));
		info.setIsDel(BooleanEnum.getByCode(common.getIsDel()));
		
		return info;
	}
	
	/***
	 * 诉保DO转info
	 * @param common
	 * @param DO
	 * @return
	 */
	private FCouncilSummaryProjectLgLitigationInfo convertLgLitigationProjectCsDO2Info(	FCouncilSummaryProjectDO common,
																						FCouncilSummaryProjectLgLitigationDO DO) {
		
		if (common == null)
			return null;
		
		FCouncilSummaryProjectLgLitigationInfo info = new FCouncilSummaryProjectLgLitigationInfo();
		
		if (DO != null) {
			BeanCopier.staticCopy(DO, info);
			info.setCoInstitutionChargeType(ChargeTypeEnum.getByCode(DO
				.getCoInstitutionChargeType()));
			info.setDepositType(ChargeTypeEnum.getByCode(DO.getDepositType()));
			info.setGuaranteeFeeType(ChargeTypeEnum.getByCode(DO.getGuaranteeFeeType()));
			info.setOtherFeeType(ChargeTypeEnum.getByCode(DO.getOtherFeeType()));
		}
		
		BeanCopier.staticCopy(common, info);
		info.setIsChargeEveryBeginning(BooleanEnum.getByCode(common.getIsChargeEveryBeginning()));
		info.setIsRepayEqual(BooleanEnum.getByCode(common.getIsRepayEqual()));
		info.setRepayWay(RepayWayEnum.getByCode(common.getRepayWay()));
		info.setVoteResult(ProjectVoteResultEnum.getByCode(common.getVoteResult()));
		info.setTimeUnit(TimeUnitEnum.getByCode(common.getTimeUnit()));
		info.setChargeWay(ChargeWayEnum.getByCode(common.getChargeWay()));
		info.setChargePhase(ChargePhaseEnum.getByCode(common.getChargePhase()));
		info.setCustomerType(CustomerTypeEnum.getByCode(common.getCustomerType()));
		info.setIsMaximumAmount(BooleanEnum.getByCode(common.getIsMaximumAmount()));
		info.setOneVoteDown(BooleanEnum.getByCode(common.getOneVoteDown()));
		info.setIsDel(BooleanEnum.getByCode(common.getIsDel()));
		
		return info;
	}
	
	/***
	 * 风险处置方案DO转info
	 * @param DO
	 * @return
	 */
	private FCouncilSummaryRiskHandleInfo convertRiskHandleCsDO2Info(FCouncilSummaryRiskHandleDO DO) {
		if (DO == null)
			return null;
		FCouncilSummaryRiskHandleInfo info = new FCouncilSummaryRiskHandleInfo();
		BeanCopier.staticCopy(DO, info);
		info.setIsComp(BooleanEnum.getByCode(DO.getIsComp()));
		info.setIsExtend(BooleanEnum.getByCode(DO.getIsExtend()));
		info.setIsOther(BooleanEnum.getByCode(DO.getIsOther()));
		info.setCustomerType(CustomerTypeEnum.getByCode(DO.getCustomerType()));
		info.setExtendTimeUnit(TimeUnitEnum.getByCode(DO.getExtendTimeUnit()));
		return info;
	}
	
	@Override
	protected FormBaseResult createResult() {
		return new CouncilSummaryResult();
	}
}
