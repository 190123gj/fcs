package com.born.fcs.crm.biz.service.customer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;

import com.born.fcs.crm.biz.service.Date.SqlDateService;
import com.born.fcs.crm.dal.daointerface.BusyRegionDAO;
import com.born.fcs.crm.dal.daointerface.ChangeDetailDAO;
import com.born.fcs.crm.dal.daointerface.ChangeListDAO;
import com.born.fcs.crm.dal.daointerface.ChannalContractDAO;
import com.born.fcs.crm.dal.daointerface.ChannalInfoDAO;
import com.born.fcs.crm.dal.daointerface.CompanyOwnershipStructureDAO;
import com.born.fcs.crm.dal.daointerface.CompanyQualificationDAO;
import com.born.fcs.crm.dal.daointerface.CustomerBaseInfoDAO;
import com.born.fcs.crm.dal.daointerface.CustomerCompanyDetailDAO;
import com.born.fcs.crm.dal.daointerface.CustomerInfoForEvalueDAO;
import com.born.fcs.crm.dal.daointerface.CustomerPersonDetailDAO;
import com.born.fcs.crm.dal.daointerface.CustomerRelationDAO;
import com.born.fcs.crm.dal.daointerface.CustomerRelationListDAO;
import com.born.fcs.crm.dal.daointerface.CutomerInfoVerifyMessageDAO;
import com.born.fcs.crm.dal.daointerface.EvaluatingBaseDAO;
import com.born.fcs.crm.dal.daointerface.EvaluatingBaseQueryDAO;
import com.born.fcs.crm.dal.daointerface.EvaluatingBaseSetDAO;
import com.born.fcs.crm.dal.daointerface.EvaluatingFinancialSetDAO;
import com.born.fcs.crm.dal.daointerface.EvaluatingLevelDAO;
import com.born.fcs.crm.dal.daointerface.EvaluetingListDAO;
import com.born.fcs.crm.dal.daointerface.EvaluetingListForAuditDAO;
import com.born.fcs.crm.dal.daointerface.ExtraDAO;
import com.born.fcs.crm.dal.daointerface.ListDataSaveDAO;
import com.born.fcs.crm.dal.daointerface.PersonalCompanyDAO;
import com.born.fcs.crm.dal.dataobject.CustomerBaseInfoDO;
import com.born.fcs.crm.dal.dataobject.CustomerInfoForEvalueDO;
import com.born.fcs.crm.dal.dataobject.EvaluatingLevelDO;
import com.born.fcs.crm.integration.pm.service.AfterwardsCheckServiceClient;
import com.born.fcs.crm.integration.pm.service.CrmUseServiceClient;
import com.born.fcs.crm.integration.pm.service.InvestigationServiceClient;
import com.born.fcs.crm.integration.pm.service.ProjectServiceClient;
import com.born.fcs.crm.ws.service.enums.ProjecteStatusEnum;
import com.born.fcs.crm.ws.service.info.CustomerFinanInfo;
import com.born.fcs.crm.ws.service.info.CustomerInfoForEvalue;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationFinancialReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FinancialReviewKpiInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.google.common.collect.Lists;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class BaseAutowiredDAO {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected static ScriptEngine calculatingStr = new ScriptEngineManager()
		.getEngineByName("JavaScript");
	@Autowired
	protected ExtraDAO extraDAO;
	@Autowired
	protected CustomerBaseInfoDAO customerBaseInfoDAO;
	@Autowired
	protected CustomerInfoForEvalueDAO customerInfoForEvalueDAO;
	@Autowired
	protected CustomerCompanyDetailDAO customerCompanyDetailDAO;
	@Autowired
	protected CompanyOwnershipStructureDAO companyOwnershipStructureDAO;
	@Autowired
	protected CustomerPersonDetailDAO customerPersonDetailDAO;
	@Autowired
	protected PersonalCompanyDAO personalCompanyDAO;
	
	@Autowired
	protected SqlDateService sqlDateService;
	@Autowired
	protected ChannalInfoDAO channalInfoDAO;
	@Autowired
	protected EvaluatingBaseSetDAO evaluatingBaseSetDAO;
	@Autowired
	protected EvaluetingListForAuditDAO evaluetingListForAuditDAO;
	@Autowired
	protected EvaluatingFinancialSetDAO evaluatingFinancialSetDAO;
	@Autowired
	protected CompanyQualificationDAO companyQualificationDAO;
	@Autowired
	protected ListDataSaveDAO listDataSaveDAO;
	@Autowired
	protected EvaluatingBaseQueryDAO evaluatingBaseQueryDAO;
	@Autowired
	protected EvaluatingBaseDAO evaluatingBaseDAO;
	@Autowired
	protected EvaluetingListDAO evaluetingListDAO;
	@Autowired
	protected EvaluatingLevelDAO evaluatingLevelDAO;
	
	//项目管理接口
	@Autowired
	protected ProjectServiceClient projectServiceClient;//项目管理中customerId对应客户系统中的userId
	@Autowired
	protected InvestigationServiceClient investigationServiceClient;
	//消息推送
	@Autowired
	protected SiteMessageService siteMessageWebService;
	
	@Autowired
	protected AfterwardsCheckServiceClient afterwardsCheckServiceClient;
	
	@Autowired
	protected CrmUseServiceClient crmUseServiceClient;
	
	@Autowired
	protected ChangeDetailDAO changeDetailDAO;
	@Autowired
	protected ChangeListDAO changeListDAO;
	//校验信息
	@Autowired
	protected CutomerInfoVerifyMessageDAO cutomerInfoVerifyMessageDAO;
	
	@Autowired
	protected CustomerRelationDAO customerRelationDAO;
	
	@Autowired
	protected ChannalContractDAO channalContractDAO;
	
	@Autowired
	protected CustomerRelationListDAO customerRelationListDAO;
	
	@Autowired
	protected BusyRegionDAO busyRegionDAO;
	
	/**
	 * 客户上会状态
	 * @param nowStatus 当前状态
	 * @return 是否可签报
	 * */
	protected boolean checkProjecteStatus(long userId, String nowStatus) {
		
		boolean canChange = false;
		if (StringUtil.notEquals(nowStatus, ProjecteStatusEnum.IS.code())) {
			ProjectQueryOrder projectQueryOrder = new ProjectQueryOrder();
			projectQueryOrder.setCustomerId(userId);
			projectQueryOrder.setPageSize(100);
			QueryBaseBatchResult<ProjectInfo> project = projectServiceClient
				.queryProject(projectQueryOrder);
			if (project != null && project.isSuccess()
				&& ListUtil.isNotEmpty(project.getPageList())) {
				boolean is = false;
				boolean in = false;
				boolean befor = false;
				for (ProjectInfo info : project.getPageList()) {
					//项目阶段
					ProjectPhasesEnum status = info.getPhases();
					if (status == ProjectPhasesEnum.CONTRACT_PHASES
						|| status == ProjectPhasesEnum.LOAN_USE_PHASES
						|| status == ProjectPhasesEnum.FUND_RAISING_PHASES
						|| status == ProjectPhasesEnum.AFTERWARDS_PHASES
						|| status == ProjectPhasesEnum.RECOVERY_PHASES
						|| status == ProjectPhasesEnum.FINISH_PHASES) {
						//上会后
						is = true;
						break;
					} else if (status == ProjectPhasesEnum.COUNCIL_PHASES) {
						//上会中
						in = true;
					} else if (status == ProjectPhasesEnum.INVESTIGATING_PHASES
								|| status == ProjectPhasesEnum.SET_UP_PHASES) {
						befor = true;
					}
				}
				
				ProjecteStatusEnum checkStatus = null;
				if (is) {
					checkStatus = ProjecteStatusEnum.IS;
					canChange = true;
				} else if (in) {
					checkStatus = ProjecteStatusEnum.IN;
				} else if (befor) {
					checkStatus = ProjecteStatusEnum.BEFORE;
				}
				if (checkStatus != null && StringUtil.notEquals(nowStatus, checkStatus.code())) {
					changeProjecteStatus(userId, checkStatus);
				}
				
			}
		} else {
			canChange = true;
		}
		return canChange;
		
	}
	
	/** 更新状态 */
	private void changeProjecteStatus(long userId, ProjecteStatusEnum status) {
		try {
			CustomerBaseInfoDO customerBaseInfo = new CustomerBaseInfoDO();
			customerBaseInfo.setUserId(userId);
			customerBaseInfo.setProjectStatus(status.code());
			customerBaseInfoDAO.updateProjectStatus(customerBaseInfo);
		} catch (Exception e) {
			logger.error("更新客户立项状态异常：", e);
		}
		
	}
	
	/** 获取评级客户暂存信息 */
	protected CustomerInfoForEvalue getEvalueInfo(Long formId) {
		if (formId != null && formId > 0) {
			CustomerInfoForEvalue evalueInfo = null;
			CustomerInfoForEvalueDO doInfo = customerInfoForEvalueDAO.findById(formId);
			if (doInfo != null) {
				evalueInfo = new CustomerInfoForEvalue();
				BeanCopier.staticCopy(doInfo, evalueInfo);
			}
			return evalueInfo;
		}
		return null;
		
	}
	
	/** 从Pm中查询当前用户的财务信息 */
	protected CustomerFinanInfo queryFinanceFormPm(long userId) {
		CustomerFinanInfo customerFinanInfo = new CustomerFinanInfo();
		ProjectQueryOrder projectQueryOrder = new ProjectQueryOrder();
		projectQueryOrder.setCustomerId(userId);
		List<ProjectPhasesEnum> phasesList = Lists.newArrayList();
		//		phasesList.add(ProjectPhasesEnum.INVESTIGATING_PHASES);
		
		phasesList.add(ProjectPhasesEnum.COUNCIL_PHASES);
		//phasesList.add(ProjectPhasesEnum.RE_COUNCIL_PHASES);
		phasesList.add(ProjectPhasesEnum.CONTRACT_PHASES);
		phasesList.add(ProjectPhasesEnum.LOAN_USE_PHASES);
		phasesList.add(ProjectPhasesEnum.AFTERWARDS_PHASES);
		phasesList.add(ProjectPhasesEnum.RECOVERY_PHASES);
		phasesList.add(ProjectPhasesEnum.FINISH_PHASES);
		projectQueryOrder.setPhasesList(phasesList);
		QueryBaseBatchResult<ProjectInfo> baseBatchResult = projectServiceClient
			.queryProject(projectQueryOrder);//查询该客户的项目
		
		if (baseBatchResult.isSuccess() && ListUtil.isNotEmpty(baseBatchResult.getPageList())) {
			Map<Long, ProjectInfo> resultmMap = new HashMap<>();
			for (ProjectInfo proInfo : baseBatchResult.getPageList()) {
				resultmMap.put(proInfo.getProjectId(), proInfo);
			}
			
			Object[] keySet = resultmMap.keySet().toArray();
			Arrays.sort(keySet);
			for (int i = keySet.length - 1; i >= 0; i--) {
				ProjectInfo pInfo = resultmMap.get(keySet[i]);
				String projectCode = pInfo.getProjectCode();//"2016-0100-121-005";
				
				//暂时只取尽调数据
				//				FAfterwardsCheckReportContentInfo infos = afterwardsCheckServiceClient
				//					.queryFinancilInfo(projectCode);
				//				if (infos != null && ListUtil.isNotEmpty(infos.getFinancials())
				//					&& ListUtil.isNotEmpty(infos.getProfits())
				//					&& ListUtil.isNotEmpty(infos.getFlows())) {
				//					Map<String, FinancialKpiInfo> map = new HashMap<>();
				//					//资产负债表
				//					if (infos.getFinancials() != null) {
				//						for (FinancialKpiInfo info : infos.getFinancials()) {
				//							map.put(info.getKpiCode(), info);
				//						}
				//					}
				//					//利润及利润分配表
				//					if (infos.getProfits() != null) {
				//						for (FinancialKpiInfo info : infos.getProfits()) {
				//							map.put(info.getKpiCode(), info);
				//						}
				//					}
				//					//现金流量表
				//					if (infos.getFlows() != null) {
				//						for (FinancialKpiInfo info : infos.getFlows()) {
				//							map.put(info.getKpiCode(), info);
				//						}
				//					}
				//					customerFinanInfo.initDataBH(map);
				//					break;
				//				} else {
				
				Map<String, FinancialReviewKpiInfo> map = new HashMap<>();
				long formId = investigationServiceClient
					.queryInvestigationFormIdByProjectCode(projectCode);//统过projectCode去查formId
				
				if (formId > 0) {
					
					FInvestigationFinancialReviewInfo financeInfo = investigationServiceClient
						.findFInvestigationFinancialReviewByFormId(formId);//通过formId查财务信息
					
					if (financeInfo != null) {
						//资产负债表
						if (financeInfo.getFinancialTables() != null) {
							for (FinancialReviewKpiInfo info : financeInfo.getFinancialTables()) {
								map.put(info.getKpiCode(), info);
							}
						}
						//利润及利润分配表
						if (financeInfo.getProfitTables() != null) {
							for (FinancialReviewKpiInfo info : financeInfo.getProfitTables()) {
								map.put(info.getKpiCode(), info);
							}
						}
						//现金流量表
						if (financeInfo.getFlowTables() != null) {
							for (FinancialReviewKpiInfo info : financeInfo.getFlowTables()) {
								map.put(info.getKpiCode(), info);
							}
						}
						//盈利能力分析
						if (financeInfo.getBenifitAblilities() != null) {
							for (FinancialReviewKpiInfo info : financeInfo.getBenifitAblilities()) {
								map.put(info.getKpiCode(), info);
							}
						}
						customerFinanInfo.initDataZJ(map);
						break;
					}
					
				}
				
			}
			
			//			}
		}
		return customerFinanInfo;
	}
	
	/**
	 * 对象中值全为空
	 * @return true 全为空
	 * */
	protected boolean allValueIsNull(Object bean) {
		Map<String, String> map = BeanMap.create(bean);
		Object sMap[] = map.keySet().toArray();
		for (int i = 0; i < map.size(); i++) {
			Object value = map.get(sMap[i]);
			if (value != null) {
				if (String.valueOf(value.getClass()).toLowerCase().indexOf("money") > -1) {
					if ("0.00".equals(String.valueOf(value)))
						continue;
				}
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 对象中值全为空
	 * @return true 全为空
	 * */
	protected boolean checkEvalueType(Object bean) {
		Map<String, String> map = BeanMap.create(bean);
		if (map.containsKey("evalueType") && StringUtil.isNotBlank(map.get("evalueType"))) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 返回值标准化 小数点后两位 四舍五入
	 * @param s 需要处理的数据
	 * 
	 * */
	public static String checkValue(String s, int num) {
		try {
			if (num == 2) {
				double d = Double.parseDouble(s);
				double rs = Math.round(d * 100) / 100.0;
				s = String.valueOf(rs);
			} else {
				double d = Double.parseDouble(s);
				double rs = Math.round(d * 10) / 10.0;
				s = String.valueOf(rs);
			}
			
		} catch (Exception e) {
			
		}
		
		return s;
		
	}
	
	/** 上一次评级 */
	protected String lastEvalueLevel(long userId) {
		if (userId > 0) {
			EvaluatingLevelDO evaluatingLevel = new EvaluatingLevelDO();
			evaluatingLevel.setUserId(userId);
			List<EvaluatingLevelDO> list = evaluatingLevelDAO.findWithCondition(evaluatingLevel, 0,
				1);
			if (ListUtil.isNotEmpty(list)) {
				return list.get(0).getLevel();
			}
		}
		return null;
		
	}
	
}
