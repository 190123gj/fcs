package com.born.fcs.pm.biz.service.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.ChannalService;
import com.born.fcs.crm.ws.service.CompanyCustomerService;
import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.crm.ws.service.info.CompanyOwnershipStructureInfo;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectBondDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectEntrustedDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectGuaranteeDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectLgLitigationDO;
import com.born.fcs.pm.dal.dataobject.FCounterGuaranteeReleaseDO;
import com.born.fcs.pm.dal.dataobject.FCounterGuaranteeRepayDO;
import com.born.fcs.pm.dal.dataobject.FLoanUseApplyDO;
import com.born.fcs.pm.dal.dataobject.FLoanUseApplyReceiptDO;
import com.born.fcs.pm.dal.dataobject.FProjectCustomerBaseInfoDO;
import com.born.fcs.pm.dal.dataobject.FProjectDO;
import com.born.fcs.pm.dal.dataobject.FProjectEquityStructureDO;
import com.born.fcs.pm.dal.dataobject.FProjectGuaranteeEntrustedDO;
import com.born.fcs.pm.dal.dataobject.FProjectLgLitigationDO;
import com.born.fcs.pm.dal.dataobject.FProjectUnderwritingDO;
import com.born.fcs.pm.dal.dataobject.FormDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectIssueInformationDO;
import com.born.fcs.pm.dal.dataobject.UserExtraMessageDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChannelRelationEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.enums.SysDateSeqNameEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.bpm.Role;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.common.BusiTypeInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.excel.OldProjectExcelDetailInfo;
import com.born.fcs.pm.ws.info.excel.OldProjectExcelInfo;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationAmountOrder;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationBatchOrder;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationOrder;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.ProjectImportBatchResult;
import com.born.fcs.pm.ws.result.common.ProjectImportResult;
import com.born.fcs.pm.ws.service.common.BasicDataService;
import com.born.fcs.pm.ws.service.common.ProjectChannelRelationService;
import com.born.fcs.pm.ws.service.common.ProjectImportService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("projectImportService")
public class ProjectImportServiceImpl extends BaseAutowiredDomainService implements
																		ProjectImportService {
	@Autowired
	ProjectService projectService;
	@Autowired
	CompanyCustomerService companyCustomerServiceClient;
	@Autowired
	ProjectRelatedUserService projectRelatedUserService;
	@Autowired
	BasicDataService basicDataService;
	@Autowired
	ChannalService channelClient;
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	@Autowired
	SysParameterService sysParameterService;
	@Autowired
	ProjectChannelRelationService projectChannelRelationService;
	
	@Override
	public ProjectImportBatchResult importBatch(List<OldProjectExcelInfo> oldInfos) {
		ProjectImportBatchResult result = new ProjectImportBatchResult();
		List<ProjectImportResult> batchResult = Lists.newArrayList();
		for (OldProjectExcelInfo info : oldInfos) {
			batchResult.add(importOne(info));
		}
		result.setBatchResult(batchResult);
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public ProjectImportResult importOne(final OldProjectExcelInfo oldInfo) {
		
		return (ProjectImportResult) commonProcess(oldInfo, "已解保项目导入",
			new BeforeProcessInvokeService() {
				@Override
				public Domain before() {
					
					String projectName = oldInfo.getProjectName();
					String customerName = oldInfo.getCustomerName();
					DateFormat dateFormat = DateUtil.getFormat("yyyy-MM-dd");
					DateFormat yearFormat = DateUtil.getFormat("yyyy");
					
					Date now = FcsPmDomainHolder.get().getSysDate();
					
					//导入结果
					ProjectImportResult result = (ProjectImportResult) FcsPmDomainHolder.get()
						.getAttribute("result");
					result.setProjectName(projectName);
					result.setCustomerName(customerName);
					
					//检查项目是否存在
					ProjectQueryOrder projectQueryOrder = new ProjectQueryOrder();
					projectQueryOrder.setProjectName(oldInfo.getProjectName());
					QueryBaseBatchResult<ProjectInfo> exists = projectService
						.queryProject(projectQueryOrder);
					if (exists != null && exists.getTotalCount() > 0) {
						result.setProjectCode(exists.getPageList().get(0).getProjectCode());
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "项目已存在");
					}
					
					/*
					 * 各种基础数据
					 */
					
					//查询客户信息
					CompanyCustomerInfo customer = companyCustomerServiceClient.queryByName(oldInfo
						.getCustomerName());
					if (customer == null) {
						throw ExceptionFactory
							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "客户不存在");
					}
					
					//查询业务品种
					BusiTypeInfo bigBusiType = basicDataService.querySingleBusiTypeByName(
						oldInfo.getBusiTypeName(), CustomerTypeEnum.ENTERPRISE.code());
					if (bigBusiType == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"产品类型[" + oldInfo.getBusiTypeName() + "]不存在");
					}
					
					//查询业务品种细分类
					BusiTypeInfo subBusiType = basicDataService.querySingleBusiTypeByName(
						oldInfo.getBusiSubTypeName(), CustomerTypeEnum.ENTERPRISE.code());
					if (subBusiType == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"产品类型[" + oldInfo.getBusiSubTypeName() + "]不存在");
					}
					
					//银行融资担保只存储大类，其他存储小类
					boolean isBankFinancing = ProjectUtil.isBankFinancing(bigBusiType.getCode());
					//业务类型
					BusiTypeInfo busiType = subBusiType;
					if (isBankFinancing) {
						busiType = bigBusiType;
					}
					
					boolean isBond = ProjectUtil.isBond(busiType.getCode());
					boolean isEntrusted = ProjectUtil.isEntrusted(busiType.getCode());
					boolean isLitigation = ProjectUtil.isLitigation(busiType.getCode());
					boolean isUnderwriting = ProjectUtil.isUnderwriting(busiType.getCode());
					
					//查询业务经理
					List<UserExtraMessageDO> busiManagers = userExtraMessageDAO.findByName(oldInfo
						.getBusiManagerName());
					//业务经理
					UserDetailInfo busiManager = null;
					//所属部门
					Org dept = null;
					if (ListUtil.isEmpty(busiManagers)) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未查询到业务经理[" + oldInfo.getBusiManagerName() + "]");
					}
					for (UserExtraMessageDO extra : busiManagers) {
						
						UserDetailInfo userDetail = bpmUserQueryService
							.findUserDetailByAccount(extra.getUserAccount());
						if (userDetail == null) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"业务经理不存在[" + oldInfo.getBusiManagerName() + "]");
						}
						List<Org> depts = userDetail.getOrgList();
						for (Org org : depts) {
							if (StringUtil.equals(org.getName(), oldInfo.getDeptName())) {
								busiManager = userDetail;
								dept = org;
								break;
							}
						}
						if (busiManager != null)
							break;
					}
					if (busiManager == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未匹配到业务经理[" + oldInfo.getBusiManagerName() + "]");
					}
					
					if (dept == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未匹配到部门[" + oldInfo.getDeptName() + "]");
					}
					
					//风险经理
					UserDetailInfo riskManager = null;
					if (StringUtil.isNotBlank(oldInfo.getRiskManagerName())) {
						riskManager = getRiskManager(oldInfo.getRiskManagerName());
						if (riskManager == null) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"未匹配到风险经理[" + oldInfo.getRiskManagerName() + "]");
						}
					}
					
					//法务经理
					UserDetailInfo legalManager = null;
					if (StringUtil.isNotBlank(oldInfo.getLegalManagerName())) {
						legalManager = getLegalManager(oldInfo.getLegalManagerName());
						if (legalManager == null) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"未匹配到法务经理[" + oldInfo.getLegalManagerName() + "]");
						}
					}
					
					//查询资金渠道
					ChannalInfo capitalChannel = null;
					if (StringUtil.isNotBlank(oldInfo.getCapitalChannelName())) {
						capitalChannel = channelClient.queryByChannalName(oldInfo
							.getCapitalChannelName());
						if (capitalChannel == null) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"未匹配到资金渠道[" + oldInfo.getCapitalChannelName() + "]");
						}
					}
					
					//查询项目渠道
					ChannalInfo projectChannel = null;
					if (StringUtil.isNotBlank(oldInfo.getProjectChannelName())) {
						projectChannel = channelClient.queryByChannalName(oldInfo
							.getCapitalChannelName());
						if (projectChannel == null) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"未匹配到项目渠道[" + oldInfo.getProjectChannelName() + "]");
						}
					}
					
					/*
					 * 立项
					 */
					FormCodeEnum setupFormCode = FormCodeEnum.SET_UP_GUARANTEE_ENTRUSTED;
					if (isUnderwriting) {
						setupFormCode = FormCodeEnum.SET_UP_UNDERWRITING;
					} else if (isLitigation) {
						setupFormCode = FormCodeEnum.SET_UP_LITIGATION_ENTERPRISE;
					}
					
					Date setupDate = null;
					try {
						setupDate = dateFormat.parse(getParseAbleDate(oldInfo.getProjectAddTime()));
					} catch (ParseException e) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
							"立项时间解析错误[" + oldInfo.getProjectAddTime() + "]");
					}
					//生成项目编号
					String projectCode = genProjectCode(yearFormat.format(setupDate),
						busiType.getCode(), dept.getCode());
					
					//创建立项表单
					FormDO setupForm = new FormDO();
					setupForm.setFormCode(setupFormCode.getCode());
					
					//全部通过
					String checkStatus = setupFormCode.getDefaultCheckStatus().replaceAll("0", "1");
					setupForm.setCheckStatus(checkStatus);
					setupForm.setFormUrl(setupFormCode.getViewUrl());
					setupForm.setFormName(setupFormCode.getMessage());
					setupForm.setRawAddTime(now);
					setupForm.setSubmitTime(now);
					setupForm.setFinishTime(now);
					setFormUser(projectCode, setupForm, busiManager, dept);
					//{"formRemark_customerBaseInfo":"","formRemark_project":""}
					JSONObject remark = new JSONObject();
					remark.put("formRemark_customerBaseInfo",
						"已解保项目导入," + DateUtil.simpleDate(new Date()));
					remark.put("formRemark_project", "已解保项目导入," + DateUtil.simpleDate(new Date()));
					setupForm.setCustomizeField(remark.toJSONString());
					setupForm.setFormId(formDAO.insert(setupForm));
					
					//立项客户信息
					FProjectCustomerBaseInfoDO setupCustomer = new FProjectCustomerBaseInfoDO();
					BeanCopier.staticCopy(customer, setupCustomer);
					setupCustomer.setCustomerId(customer.getUserId());
					setupCustomer.setCustomerType(CustomerTypeEnum.ENTERPRISE.code());
					setupCustomer.setProjectCode(projectCode);
					setupCustomer.setProjectName(projectName);
					setupCustomer.setFormId(setupForm.getFormId());
					setupCustomer.setRawAddTime(now);
					FProjectCustomerBaseInfoDAO.insert(setupCustomer);
					
					//股权结构
					List<CompanyOwnershipStructureInfo> structures = customer
						.getCompanyOwnershipStructure();
					if (ListUtil.isNotEmpty(structures)) {
						FProjectEquityStructureDO structure = new FProjectEquityStructureDO();
						structure.setProjectCode(projectCode);
						structure.setProjectName(projectName);
						structure.setCustomerId(customer.getUserId());
						structure.setCustomerName(structure.getCustomerName());
						structure.setFormId(setupForm.getFormId());
						for (CompanyOwnershipStructureInfo struc : structures) {
							BeanCopier.staticCopy(struc, structure);
							structure.setEquityRatio(NumberUtil.parseDouble(struc.getEquity()));//股权比例
							structure.setStockholder(struc.getShareholdersName());//主要股东名称
							structure.setContributionWay(struc.getMethord()); //出资方式
							structure.setCapitalContributions(struc.getAmount());//出资额（万元）
							structure.setId(0);
							structure.setRawAddTime(now);
							FProjectEquityStructureDAO.insert(structure);
						}
					}
					
					//立项项目信息
					Money amount = Money.amout(oldInfo.getAmount()).multiply(10000);
					FProjectDO setupProject = new FProjectDO();
					setupProject.setProjectCode(projectCode);
					setupProject.setProjectName(projectName);
					setupProject.setFormId(setupForm.getFormId());
					setupProject.setAmount(amount);
					setupProject.setBusiType(busiType.getCode());
					setupProject.setBusiTypeName(busiType.getName());
					setupProject.setRemark("已解保项目导入," + DateUtil.simpleDate(new Date()));
					setupProject.setIndustryCode(customer.getIndustryCode());
					setupProject.setIndustryName(customer.getIndustryName());
					setupProject.setDeptId(dept.getId());
					setupProject.setDeptCode(dept.getCode());
					setupProject.setDeptName(dept.getName());
					setupProject.setDeptPath(dept.getPath());
					setupProject.setDeptPathName(dept.getPathName());
					setupProject.setCustomerId(customer.getUserId());
					setupProject.setCustomerName(customer.getCustomerName());
					setupProject.setCustomerType(customer.getCustomerType());
					setupProject.setBusiManagerId(busiManager.getId());
					setupProject.setBusiManagerName(busiManager.getName());
					setupProject.setBusiManagerAccount(busiManager.getAccount());
					setupProject.setTimeLimit(NumberUtil.parseInt(oldInfo.getTimeLimit()));
					if (setupProject.getTimeLimit() > 0)
						setupProject.setTimeUnit(TimeUnitEnum.MONTH.code());
					setupProject.setRawAddTime(now);
					setupProject.setProjectId(FProjectDAO.insert(setupProject));
					
					//项目明细
					if (isUnderwriting) {
						FProjectUnderwritingDO projectDetail = new FProjectUnderwritingDO();
						projectDetail.setProjectId(setupProject.getProjectId());
						projectDetail.setRawAddTime(now);
						FProjectUnderwritingDAO.insert(projectDetail);
					} else if (isLitigation) {
						FProjectLgLitigationDO projectDetail = new FProjectLgLitigationDO();
						projectDetail.setProjectId(setupProject.getProjectId());
						projectDetail.setGuaranteeFee(NumberUtil.parseDouble(oldInfo.getRate()));
						projectDetail.setGuaranteeFeeType(ChargeTypeEnum.PERCENT.code());
						if (legalManager != null) {
							projectDetail.setLegalManagerId(legalManager.getId());
							projectDetail.setLegalManagerName(legalManager.getName());
							projectDetail.setLegalManagerAccount(legalManager.getAccount());
						}
						projectDetail.setRawAddTime(now);
						FProjectLgLitigationDAO.insert(projectDetail);
					} else {
						
						FProjectGuaranteeEntrustedDO projectDetail = new FProjectGuaranteeEntrustedDO();
						projectDetail.setProjectId(setupProject.getProjectId());
						
						if (projectChannel != null) {
							projectDetail.setProjectChannelId(projectChannel.getId());
							projectDetail.setProjectChannelName(projectChannel.getChannelName());
						}
						
						if (capitalChannel != null) {
							projectDetail.setCapitalChannelId(capitalChannel.getId());
							projectDetail.setCapitalChannelName(capitalChannel.getChannelName());
						}
						
						if (riskManager != null) {
							projectDetail.setRiskManagerId(riskManager.getId());
							projectDetail.setRiskManagerName(riskManager.getName());
							projectDetail.setRiskManagerAccount(riskManager.getAccount());
						}
						
						FProjectGuaranteeEntrustedDAO.insert(projectDetail);
					}
					
					//设置业务经理
					setRelatedUser(projectCode, busiManager, dept,
						ProjectRelatedUserTypeEnum.BUSI_MANAGER);
					//设置风险经理
					setRelatedUser(projectCode, riskManager, null,
						ProjectRelatedUserTypeEnum.RISK_MANAGER);
					//设置法务经理
					setRelatedUser(projectCode, legalManager, null,
						ProjectRelatedUserTypeEnum.LEGAL_MANAGER);
					
					/*
					 * 放用款 
					 */
					Money loanAmount = Money.zero();
					List<ProjectIssueInformationDO> issueInfo = Lists.newArrayList();
					if (ListUtil.isNotEmpty(oldInfo.getOutInfo())) {
						FormDO loanForm = new FormDO();
						loanForm.setFormCode(FormCodeEnum.LOAN_USE_APPLY.code());
						//全部通过
						loanForm.setCheckStatus("1");
						loanForm.setFormUrl(FormCodeEnum.LOAN_USE_APPLY.getViewUrl());
						loanForm.setFormName(FormCodeEnum.LOAN_USE_APPLY.getMessage());
						loanForm.setRawAddTime(now);
						loanForm.setSubmitTime(now);
						loanForm.setFinishTime(now);
						setFormUser(projectCode, loanForm, busiManager, dept);
						loanForm.setFormId(formDAO.insert(loanForm));
						//放用款回执
						List<FLoanUseApplyReceiptDO> receipts = Lists.newArrayList();
						for (OldProjectExcelDetailInfo outInfo : oldInfo.getOutInfo()) {
							
							FLoanUseApplyReceiptDO receipt = new FLoanUseApplyReceiptDO();
							Date actualLoanDate = null;
							try {
								actualLoanDate = dateFormat.parse(getParseAbleDate(outInfo
									.getTime()));
							} catch (ParseException e) {
								logger.error(e.getMessage(), e);
								throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
									"放款日期解析出错[" + outInfo.getTime() + "]");
							}
							receipt.setActualLoanTime(actualLoanDate);
							receipt.setActualAmount(Money
								.amout(
									StringUtil.isBlank(outInfo.getAmount()) ? "0" : outInfo
										.getAmount()).multiply(10000));
							
							if (receipt.getActualAmount().getCent() <= 0) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
									"放款金额必须大于0[" + outInfo.getTime() + "]");
							}
							
							loanAmount.addTo(receipt.getActualAmount());
							
							if (ProjectUtil.isBankFinancing(busiType.getCode())) {
								if ("111".equals(subBusiType.getCode())) { //流动资金贷款
									receipt.setLiquidityLoanAmount(receipt.getActualAmount());
								} else if ("112".equals(subBusiType.getCode())) { //固定资产融资
									receipt.setFixedAssetsFinancingAmount(receipt.getActualAmount());
								} else if ("113".equals(subBusiType.getCode())) { //承兑汇票担保
									receipt.setAcceptanceBillAmount(receipt.getActualAmount());
								} else if ("114".equals(subBusiType.getCode())) { //信用证担保
									receipt.setCreditLetterAmount(receipt.getActualAmount());
								}
							}
							if (capitalChannel != null) {
								receipt.setCapitalChannelId(capitalChannel.getId());
								receipt.setCapitalChannelName(capitalChannel.getChannelName());
								receipt.setCapitalChannelCode(capitalChannel.getChannelCode());
								receipt.setCapitalChannelType(capitalChannel.getChannelType());
							}
							
							receipts.add(receipt);
						}
						
						//新增申请
						FLoanUseApplyDO apply = new FLoanUseApplyDO();
						apply.setAmount(loanAmount);
						apply.setOriginalAmount(amount);
						apply.setApplyType("BOTH");
						apply.setProjectCode(projectCode);
						apply.setRawAddTime(now);
						apply.setFormId(loanForm.getFormId());
						apply.setRemark("已解保项目导入," + DateUtil.simpleDate(new Date()));
						apply.setApplyId(FLoanUseApplyDAO.insert(apply));
						for (FLoanUseApplyReceiptDO receipt : receipts) {
							receipt.setProjectCode(apply.getProjectCode());
							receipt.setApplyAmount(apply.getAmount());
							receipt.setRawAddTime(now);
							receipt.setApplyId(apply.getApplyId());
							receipt.setApplyType("BOTH");
							receipt.setRemark("已解保项目导入," + DateUtil.simpleDate(new Date()));
							FLoanUseApplyReceiptDAO.insert(receipt);
							
							//同时新增发行数据(发债)
							ProjectIssueInformationDO issueDO = new ProjectIssueInformationDO();
							issueDO.setActualAmount(receipt.getActualAmount());
							issueDO.setIssueDate(receipt.getActualLoanTime());
							issueInfo.add(issueDO);
						}
					}
					/*
					 * 解保、还款
					 */
					Money releaseAmount = Money.zero();
					
					FormDO releaseForm = new FormDO();
					FormCodeEnum releaseFormCode = FormCodeEnum.COUNTER_GUARANTEE;
					if (isLitigation) {
						releaseFormCode = FormCodeEnum.COUNTER_GUARANTEE_LITIGATION;
					}
					releaseForm.setFormCode(releaseFormCode.code());
					
					//全部通过
					releaseForm.setCheckStatus("1");
					releaseForm.setFormUrl(releaseFormCode.getViewUrl());
					releaseForm.setFormName(releaseFormCode.getMessage());
					releaseForm.setRawAddTime(now);
					releaseForm.setSubmitTime(now);
					releaseForm.setFinishTime(now);
					setFormUser(projectCode, releaseForm, busiManager, dept);
					
					if (ListUtil.isNotEmpty(oldInfo.getBackInfo())) {
						
						if (!isLitigation)
							releaseForm.setFormId(formDAO.insert(releaseForm));
						
						List<FCounterGuaranteeRepayDO> repays = Lists.newArrayList();
						for (OldProjectExcelDetailInfo backInfo : oldInfo.getBackInfo()) {
							FCounterGuaranteeRepayDO repay = new FCounterGuaranteeRepayDO();
							Date repayTime = null;
							try {
								repayTime = dateFormat.parse(getParseAbleDate(backInfo.getTime()));
							} catch (ParseException e) {
								logger.error(e.getMessage(), e);
								throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
									"还款日期解析出错[" + backInfo.getTime() + "]");
							}
							repay.setRepayTime(DateUtil.dtSimpleFormat(repayTime));
							repay.setRepayAmount(Money.amout(
								StringUtil.isBlank(backInfo.getAmount()) ? "0" : backInfo
									.getAmount()).multiply(10000));
							
							if (repay.getRepayAmount().getCent() <= 0) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
									"还款金额必须大于0[" + backInfo.getTime() + "]");
							}
							releaseAmount.addTo(repay.getRepayAmount());
							
							if (ProjectUtil.isBankFinancing(busiType.getCode())) {
								if ("111".equals(subBusiType.getCode())) { //流动资金贷款
									repay.setLiquidityLoanAmount(repay.getRepayAmount());
								} else if ("112".equals(subBusiType.getCode())) { //固定资产融资
									repay.setFixedAssetsFinancingAmount(repay.getRepayAmount());
								} else if ("113".equals(subBusiType.getCode())) { //承兑汇票担保
									repay.setAcceptanceBillAmount(repay.getRepayAmount());
								} else if ("114".equals(subBusiType.getCode())) { //信用证担保
									repay.setCreditLetterAmount(repay.getRepayAmount());
								}
							}
							if (capitalChannel != null) {
								repay.setCapitalChannelId(capitalChannel.getId());
								repay.setCapitalChannelName(capitalChannel.getChannelName());
								repay.setCapitalChannelCode(capitalChannel.getChannelCode());
								repay.setCapitalChannelType(capitalChannel.getChannelType());
							}
							repays.add(repay);
						}
						
						if (isLitigation) {//诉保生成多个单子
						
							Money releasedAmount = Money.zero();
							for (FCounterGuaranteeRepayDO repay : repays) {
								releaseForm.setFormId(0);
								releaseForm.setFinishTime(DateUtil.parse(repay.getRepayTime()));
								releaseForm.setFormId(formDAO.insert(releaseForm));
								
								FCounterGuaranteeReleaseDO release = new FCounterGuaranteeReleaseDO();
								BeanCopier.staticCopy(setupProject, release);
								release.setReleaseBalance(amount.subtract(releasedAmount));
								release.setApplyAmount(repay.getRepayAmount());
								release.setCreditAmount(amount);
								release.setFormId(releaseForm.getFormId());
								release.setRawAddTime(now);
								FCounterGuaranteeReleaseDAO.insert(release);
								
								releasedAmount.addTo(repay.getRepayAmount());
							}
						} else {
							FCounterGuaranteeReleaseDO release = new FCounterGuaranteeReleaseDO();
							BeanCopier.staticCopy(setupProject, release);
							release.setReleaseBalance(loanAmount);
							release.setApplyAmount(releaseAmount);
							release.setCreditAmount(amount);
							release.setFormId(releaseForm.getFormId());
							release.setRawAddTime(now);
							FCounterGuaranteeReleaseDAO.insert(release);
							for (FCounterGuaranteeRepayDO repay : repays) {
								repay.setRawAddTime(now);
								repay.setFormId(releaseForm.getFormId());
								FCounterGuaranteeRepayDAO.insert(repay);
							}
						}
					} else if (isLitigation) {
						releaseForm.setFormId(formDAO.insert(releaseForm));
						FCounterGuaranteeReleaseDO release = new FCounterGuaranteeReleaseDO();
						BeanCopier.staticCopy(setupProject, release);
						release.setReleaseBalance(amount);
						release.setApplyAmount(amount);
						release.setCreditAmount(amount);
						release.setFormId(releaseForm.getFormId());
						release.setRawAddTime(now);
						releaseAmount = amount;
						FCounterGuaranteeReleaseDAO.insert(release);
					}
					
					//创建项目
					ProjectDO project = new ProjectDO();
					BeanCopier.staticCopy(setupProject, project);
					project.setIsHis(1);
					project.setUsedAmount(loanAmount);
					project.setLoanedAmount(loanAmount);
					project.setReleasableAmount(loanAmount);
					if (isLitigation) {
						project.setReleasableAmount(amount);
						project.setIsCourtRuling(BooleanEnum.IS.code());
					}
					project.setReleasedAmount(releaseAmount);
					project.setContractAmount(amount);
					project.setRawAddTime(setupDate);
					Date startTime = null;
					try {
						startTime = dateFormat.parse(getParseAbleDate(oldInfo.getStartTime()));
					} catch (ParseException e) {
						logger.error(e.getMessage(), e);
						throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
							"授信开始时间解析出错[" + oldInfo.getStartTime() + "]");
					}
					Date endTime = null;
					try {
						endTime = dateFormat.parse(getParseAbleDate(oldInfo.getEndTime()));
					} catch (ParseException e) {
						logger.error(e.getMessage(), e);
						throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
							"授信结束时间解析出错[" + oldInfo.getEndTime() + "]");
					}
					project.setStartTime(startTime);
					project.setEndTime(endTime);
					project.setApprovalTime(startTime);
					project.setIsApproval(BooleanEnum.IS.code());
					project.setIsApprovalDel(BooleanEnum.NO.code());
					project.setIsMaximumAmount(BooleanEnum.NO.code());
					project.setProjectId(0);
					if (project.getReleasableAmount().greaterThan(project.getReleasedAmount())) {
						project.setPhases(ProjectPhasesEnum.AFTERWARDS_PHASES.code());
						project.setPhasesStatus(ProjectPhasesStatusEnum.APPROVAL.code());
						project.setStatus(ProjectStatusEnum.NORMAL.code());
					} else {
						project.setPhases(ProjectPhasesEnum.FINISH_PHASES.code());
						project.setPhasesStatus(ProjectPhasesStatusEnum.APPROVAL.code());
						project.setStatus(ProjectStatusEnum.FINISH.code());
					}
					
					String spCode = null;
					long spId = 0;
					
					if (!isUnderwriting) {
						
						spCode = genSpCode(yearFormat.format(project.getApprovalTime()));
						/*
						 * 创建批复数据
						 */
						FCouncilSummaryProjectDO summaryProject = new FCouncilSummaryProjectDO();
						BeanCopier.staticCopy(project, summaryProject);
						summaryProject.setIsDel(BooleanEnum.NO.code());
						summaryProject.setOneVoteDown(BooleanEnum.NO.code());
						summaryProject.setRawAddTime(now);
						summaryProject.setIsMaximumAmount(BooleanEnum.NO.code());
						summaryProject.setVoteResult(ProjectVoteResultEnum.END_PASS.code());
						summaryProject.setVoteResultDesc("已解保项目导入,"
															+ DateUtil.simpleDate(new Date()));
						summaryProject.setRemark(summaryProject.getVoteResultDesc());
						summaryProject.setSpCode(spCode);
						summaryProject.setSpId(FCouncilSummaryProjectDAO.insert(summaryProject));
						//创建详细信息
						if (isBond) {
							FCouncilSummaryProjectBondDO projectDetail = new FCouncilSummaryProjectBondDO();
							projectDetail.setGuaranteeFee(NumberUtil.parseDouble(oldInfo.getRate()));
							projectDetail.setGuaranteeFeeType(ChargeTypeEnum.PERCENT.code());
							projectDetail.setRawAddTime(now);
							projectDetail.setSpId(summaryProject.getSpId());
							if (capitalChannel != null) {
								projectDetail.setCapitalChannelId(capitalChannel.getId());
								projectDetail.setCapitalChannelName(capitalChannel.getChannelName());
							}
							FCouncilSummaryProjectBondDAO.insert(projectDetail);
						} else if (isEntrusted) {
							FCouncilSummaryProjectEntrustedDO projectDetail = new FCouncilSummaryProjectEntrustedDO();
							projectDetail.setRawAddTime(now);
							projectDetail.setSpId(summaryProject.getSpId());
							if (capitalChannel != null) {
								projectDetail.setCapitalChannelId(capitalChannel.getId());
								projectDetail.setCapitalChannelName(capitalChannel.getChannelName());
							}
							FCouncilSummaryProjectEntrustedDAO.insert(projectDetail);
						} else if (isLitigation) {
							FCouncilSummaryProjectLgLitigationDO projectDetail = new FCouncilSummaryProjectLgLitigationDO();
							projectDetail.setGuaranteeFee(NumberUtil.parseDouble(oldInfo.getRate()));
							projectDetail.setGuaranteeFeeType(ChargeTypeEnum.PERCENT.code());
							projectDetail.setRawAddTime(now);
							projectDetail.setSpId(summaryProject.getSpId());
							FCouncilSummaryProjectLgLitigationDAO.insert(projectDetail);
						} else if (isUnderwriting) {
							//donothing
						} else {
							FCouncilSummaryProjectGuaranteeDO projectDetail = new FCouncilSummaryProjectGuaranteeDO();
							projectDetail.setRawAddTime(now);
							projectDetail.setSpId(summaryProject.getSpId());
							if (capitalChannel != null) {
								projectDetail.setCapitalChannelId(capitalChannel.getId());
								projectDetail.setCapitalChannelName(capitalChannel.getChannelName());
							}
							FCouncilSummaryProjectGuaranteeDAO.insert(projectDetail);
						}
						
						project.setSpId(summaryProject.getSpId());
						project.setSpCode(spCode);
						
						spId = summaryProject.getSpId();
					}
					
					if (ProjectUtil.isBond(busiType.getCode())) { //发债
						project.setAccumulatedIssueAmount(amount);
						if (ListUtil.isNotEmpty(issueInfo)) {
							project.setAccumulatedIssueAmount(Money.zero());
							for (ProjectIssueInformationDO issueDO : issueInfo) {
								project.getAccumulatedIssueAmount()
									.addTo(issueDO.getActualAmount());
								BeanCopier.staticCopy(project, issueDO);
								issueDO.setId(0);
								projectIssueInformationDAO.insert(issueDO);
							}
						}
					}
					
					//插入项目
					project.setContractNo("无");
					project.setIsHis(1);
					projectDAO.insert(project);
					
					//插入渠道
					if (!isLitigation && !isUnderwriting) {
						
						if (projectChannel != null) {
							saveChannel(projectCode, String.valueOf(setupForm.getFormId()),
								projectChannel, ChannelRelationEnum.PROJECT_CHANNEL,
								ProjectPhasesEnum.SET_UP_PHASES);
						}
						
						if (capitalChannel != null) {
							
							saveChannel(projectCode, String.valueOf(setupForm.getFormId()),
								capitalChannel, ChannelRelationEnum.CAPITAL_CHANNEL,
								ProjectPhasesEnum.SET_UP_PHASES);
							
							saveChannel(projectCode, "spId_" + spId, capitalChannel,
								ChannelRelationEnum.CAPITAL_CHANNEL,
								ProjectPhasesEnum.COUNCIL_PHASES);
							
							//更新渠道合同金额
							ProjectChannelRelationAmountOrder amountOrder = new ProjectChannelRelationAmountOrder();
							amountOrder.setContractAmount(amount);
							amountOrder.setLoanedAmount(loanAmount);
							amountOrder.setUsedAmount(loanAmount);
							amountOrder.setReleasableAmount(loanAmount);
							amountOrder.setChannelCode(capitalChannel.getChannelCode());
							amountOrder.setProjectCode(projectCode);
							projectChannelRelationService.updateRelatedAmount(amountOrder);
						}
					}
					
					result.setProjectCode(projectCode);
					
					return null;
				}
			}, null, null);
	}
	
	@Override
	public ProjectImportBatchResult reImportBatch(List<OldProjectExcelInfo> oldInfos) {
		ProjectImportBatchResult result = new ProjectImportBatchResult();
		List<ProjectImportResult> batchResult = Lists.newArrayList();
		for (OldProjectExcelInfo info : oldInfos) {
			batchResult.add(reImportOne(info));
		}
		result.setBatchResult(batchResult);
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public ProjectImportResult reImportOne(final OldProjectExcelInfo oldInfo) {
		return (ProjectImportResult) commonProcess(oldInfo, "重新导入已解保项目",
			new BeforeProcessInvokeService() {
				@Override
				public Domain before() {
					
					String projectName = oldInfo.getProjectName();
					String customerName = oldInfo.getCustomerName();
					DateFormat dateFormat = DateUtil.getFormat("yyyy-MM-dd");
					
					Date now = FcsPmDomainHolder.get().getSysDate();
					
					//导入结果
					ProjectImportResult result = (ProjectImportResult) FcsPmDomainHolder.get()
						.getAttribute("result");
					result.setProjectName(projectName);
					result.setCustomerName(customerName);
					
					//检查项目是否存在
					ProjectDO project = new ProjectDO();
					project.setProjectName(oldInfo.getProjectName());
					List<ProjectDO> exists = projectDAO.findByCondition(project, null, 0, null,
						null, null, null, null, null, null, null, null, null, null, null, null,
						null, null, null, null, null, null, null, null, null, null, null, 0, 1);
					if (ListUtil.isEmpty(exists)) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "项目不存在");
					}
					project = exists.get(0);
					
					/*
					 *查询立项
					 */
					Date setupDate = null;
					try {
						setupDate = dateFormat.parse(getParseAbleDate(oldInfo.getProjectAddTime()));
					} catch (ParseException e) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
							"立项时间解析错误[" + oldInfo.getProjectAddTime() + "]");
					}
					
					/*
					 * 放用款 
					 */
					Money loanAmount = Money.zero();
					List<ProjectIssueInformationDO> issueInfo = Lists.newArrayList();
					if (ListUtil.isNotEmpty(oldInfo.getOutInfo())) {
						
						//查询放用款申请单
						List<FLoanUseApplyDO> applys = FLoanUseApplyDAO.findByProjectCode(project
							.getProjectCode());
						
						if (ListUtil.isEmpty(applys)) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
								"原放用款申请未找到");
						}
						
						FLoanUseApplyDO apply = applys.get(0);
						
						//放用款回执
						List<FLoanUseApplyReceiptDO> receipts = Lists.newArrayList();
						for (OldProjectExcelDetailInfo outInfo : oldInfo.getOutInfo()) {
							FLoanUseApplyReceiptDO receipt = new FLoanUseApplyReceiptDO();
							Date actualLoanDate = null;
							try {
								actualLoanDate = dateFormat.parse(getParseAbleDate(outInfo
									.getTime()));
							} catch (ParseException e) {
								logger.error(e.getMessage(), e);
								throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
									"放款日期解析出错[" + outInfo.getTime() + "]");
							}
							receipt.setActualLoanTime(actualLoanDate);
							receipt.setActualAmount(Money
								.amout(
									StringUtil.isBlank(outInfo.getAmount()) ? "0" : outInfo
										.getAmount()).multiply(10000));
							
							if (receipt.getActualAmount().getCent() <= 0) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
									"放款金额必须大于0[" + outInfo.getTime() + "]");
							}
							loanAmount.addTo(receipt.getActualAmount());
							receipts.add(receipt);
						}
						
						FLoanUseApplyReceiptDAO.deleteApplyId(apply.getApplyId());
						for (FLoanUseApplyReceiptDO receipt : receipts) {
							receipt.setProjectCode(apply.getProjectCode());
							receipt.setApplyAmount(apply.getAmount());
							receipt.setRawAddTime(now);
							receipt.setApplyId(apply.getApplyId());
							receipt.setApplyType("BOTH");
							receipt.setRemark("已解保项目重新导入," + DateUtil.simpleDate(new Date()));
							FLoanUseApplyReceiptDAO.insert(receipt);
							
							//同时新增发行数据(发债)
							ProjectIssueInformationDO issueDO = new ProjectIssueInformationDO();
							issueDO.setActualAmount(receipt.getActualAmount());
							issueDO.setIssueDate(receipt.getActualLoanTime());
							issueInfo.add(issueDO);
						}
					}
					
					/*
					 * 解保、还款
					 */
					Money releaseAmount = Money.zero();
					if (ListUtil.isNotEmpty(oldInfo.getBackInfo())) {
						//查询解保申请单
						List<FCounterGuaranteeReleaseDO> releases = FCounterGuaranteeReleaseDAO
							.findByProjectCode(project.getProjectCode());
						
						if (ListUtil.isEmpty(releases)) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
								"原解保申请未找到");
						}
						FCounterGuaranteeReleaseDO release = releases.get(0);
						
						List<FCounterGuaranteeRepayDO> repays = Lists.newArrayList();
						for (OldProjectExcelDetailInfo backInfo : oldInfo.getBackInfo()) {
							FCounterGuaranteeRepayDO repay = new FCounterGuaranteeRepayDO();
							Date repayTime = null;
							try {
								repayTime = dateFormat.parse(getParseAbleDate(backInfo.getTime()));
							} catch (ParseException e) {
								logger.error(e.getMessage(), e);
								throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
									"还款日期解析出错[" + backInfo.getTime() + "]");
							}
							repay.setRepayTime(DateUtil.dtSimpleFormat(repayTime));
							repay.setRepayAmount(Money.amout(
								StringUtil.isBlank(backInfo.getAmount()) ? "0" : backInfo
									.getAmount()).multiply(10000));
							
							if (repay.getRepayAmount().getCent() <= 0) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
									"还款金额必须大于0[" + backInfo.getTime() + "]");
							}
							releaseAmount.addTo(repay.getRepayAmount());
							repays.add(repay);
						}
						
						FCounterGuaranteeRepayDAO.deleteByFormId(release.getFormId());
						for (FCounterGuaranteeRepayDO repay : repays) {
							repay.setRawAddTime(now);
							repay.setFormId(release.getFormId());
							FCounterGuaranteeRepayDAO.insert(repay);
						}
					}
					
					//创建项目
					project.setUsedAmount(loanAmount);
					project.setLoanedAmount(loanAmount);
					project.setReleasableAmount(loanAmount);
					project.setReleasedAmount(releaseAmount);
					project.setRawAddTime(setupDate);
					Date startTime = null;
					try {
						startTime = dateFormat.parse(getParseAbleDate(oldInfo.getStartTime()));
					} catch (ParseException e) {
						logger.error(e.getMessage(), e);
						throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
							"授信开始时间解析出错[" + oldInfo.getStartTime() + "]");
					}
					Date endTime = null;
					try {
						endTime = dateFormat.parse(getParseAbleDate(oldInfo.getEndTime()));
					} catch (ParseException e) {
						logger.error(e.getMessage(), e);
						throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
							"授信结束时间解析出错[" + oldInfo.getEndTime() + "]");
					}
					project.setStartTime(startTime);
					project.setEndTime(endTime);
					project.setApprovalTime(startTime);
					project.setIsApproval(BooleanEnum.IS.code());
					project.setIsApprovalDel(BooleanEnum.NO.code());
					project.setIsMaximumAmount(BooleanEnum.NO.code());
					if (project.getReleasableAmount().greaterThan(project.getReleasedAmount())) {
						project.setPhases(ProjectPhasesEnum.AFTERWARDS_PHASES.code());
						project.setPhasesStatus(ProjectPhasesStatusEnum.APPROVAL.code());
						project.setStatus(ProjectStatusEnum.NORMAL.code());
					} else {
						project.setPhases(ProjectPhasesEnum.FINISH_PHASES.code());
						project.setPhasesStatus(ProjectPhasesStatusEnum.APPROVAL.code());
						project.setStatus(ProjectStatusEnum.FINISH.code());
					}
					
					ProjectDO newProject = new ProjectDO();
					BeanCopier.staticCopy(project, newProject);
					
					projectDAO.deleteById(project.getProjectId());
					List<ProjectIssueInformationDO> oldIssues = projectIssueInformationDAO
						.findByProjectCode(project.getProjectCode());
					if (ListUtil.isNotEmpty(oldIssues)) {
						for (ProjectIssueInformationDO oldIssue : oldIssues) {
							projectIssueInformationDAO.deleteById(oldIssue.getId());
						}
					}
					
					newProject.setProjectId(0);
					newProject.setIsHis(1);
					
					if (ProjectUtil.isBond(newProject.getBusiType())
						&& ListUtil.isNotEmpty(issueInfo)) {
						for (ProjectIssueInformationDO newIssue : issueInfo) {
							BeanCopier.staticCopy(newProject, newIssue);
							newIssue.setId(0);
							projectIssueInformationDAO.insert(newIssue);
						}
					}
					
					//插入项目
					projectDAO.insert(newProject);
					result.setProjectCode(project.getProjectCode());
					return null;
				}
			}, null, null);
	}
	
	/**
	 * 处理各种怪异日期格式 9/9.22 9/9/9
	 * @param date
	 * @return
	 */
	private String getParseAbleDate(String date) {
		date = date.replaceAll("/", "-").replaceAll("\\.", "-");
		if (date.split("-")[0].length() == 1)
			date = "0" + date;
		return date;
	}
	
	/**
	 * 保存渠道
	 * @param projectCode
	 * @param bizNo
	 * @param channel
	 * @param relation
	 * @param phase
	 */
	private void saveChannel(String projectCode, String bizNo, ChannalInfo channel,
								ChannelRelationEnum relation, ProjectPhasesEnum phase) {
		ProjectChannelRelationBatchOrder capitalChannelOrder = new ProjectChannelRelationBatchOrder();
		capitalChannelOrder.setBizNo(bizNo);
		capitalChannelOrder.setChannelRelation(relation);
		capitalChannelOrder.setPhases(phase);
		capitalChannelOrder.setProjectCode(projectCode);
		ProjectChannelRelationOrder ccOrder = new ProjectChannelRelationOrder();
		ccOrder.setChannelId(channel.getId());
		ccOrder.setChannelCode(channel.getChannelCode());
		ccOrder.setChannelName(channel.getChannelName());
		ccOrder.setChannelType(channel.getChannelType());
		List<ProjectChannelRelationOrder> ccOrders = Lists.newArrayList();
		ccOrders.add(ccOrder);
		capitalChannelOrder.setRelations(ccOrders);
		projectChannelRelationService.saveByBizNoAndType(capitalChannelOrder);
	}
	
	/**
	 * 设置表单人员信息
	 * @param form
	 * @param busiManager
	 */
	private void setFormUser(String projectCode, FormDO form, UserDetailInfo busiManager, Org dept) {
		form.setRelatedProjectCode(projectCode);
		form.setUserId(busiManager.getId());
		form.setUserAccount(busiManager.getAccount());
		form.setUserName(busiManager.getName());
		form.setUserEmail(busiManager.getEmail());
		form.setUserMobile(busiManager.getMobile());
		if (dept == null)
			dept = busiManager.getPrimaryOrg();
		if (dept != null) {
			form.setDeptId(dept.getId());
			form.setDeptCode(dept.getCode());
			form.setDeptName(dept.getName());
			form.setDeptPath(dept.getPath());
			form.setDeptPathName(dept.getPathName());
		}
		form.setStatus(FormStatusEnum.APPROVAL.code());
		form.setRemark("已解保项目导入," + DateUtil.simpleDate(new Date()));
	}
	
	/**
	 * 设置项目相关人员
	 * @param user
	 * @param dept
	 * @param userType
	 */
	private void setRelatedUser(String projectCode, UserDetailInfo user, Org dept,
								ProjectRelatedUserTypeEnum userType) {
		if (user == null)
			return;
		ProjectRelatedUserOrder relatedUserOrder = new ProjectRelatedUserOrder();
		relatedUserOrder.setProjectCode(projectCode);
		relatedUserOrder.setUserType(userType);
		relatedUserOrder.setUserId(user.getId());
		relatedUserOrder.setUserAccount(user.getAccount());
		relatedUserOrder.setUserName(user.getName());
		relatedUserOrder.setUserEmail(user.getEmail());
		relatedUserOrder.setUserMobile(user.getMobile());
		if (dept == null)
			dept = user.getPrimaryOrg();
		if (dept != null) {
			relatedUserOrder.setDeptId(dept.getId());
			relatedUserOrder.setDeptCode(dept.getCode());
			relatedUserOrder.setDeptName(dept.getName());
			relatedUserOrder.setDeptPath(dept.getPath());
			relatedUserOrder.setDeptPathName(dept.getPathName());
		}
		relatedUserOrder.setIsCurrent(BooleanEnum.IS);
		relatedUserOrder.setRemark("已解保项目导入," + DateUtil.simpleDate(new Date()));
		FcsBaseResult setResult = projectRelatedUserService.setRelatedUser(relatedUserOrder);
		if (!setResult.isSuccess()) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
				"设置" + userType.getMessage() + "出错");
		}
	}
	
	/**
	 * 查询风险经理
	 * @param name
	 * @return
	 */
	private UserDetailInfo getRiskManager(String name) {
		List<UserExtraMessageDO> users = userExtraMessageDAO.findByName(name);
		if (users == null)
			return null;
		if (users.size() == 1)
			return bpmUserQueryService.findUserDetailByAccount(users.get(0).getUserAccount());
		
		//风险部部门编码
		String riskDeptCode = sysParameterService
			.getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_DEPT_CODE.code());
		for (UserExtraMessageDO extra : users) {
			UserDetailInfo user = bpmUserQueryService.findUserDetailByAccount(extra
				.getUserAccount());
			if (user != null) {
				for (Org org : user.getOrgList()) {
					if (StringUtil.equals(org.getCode(), riskDeptCode))
						return user;
				}
			}
		}
		return null;
	}
	
	/**
	 * 查询法务经理
	 * @param name
	 * @return
	 */
	private UserDetailInfo getLegalManager(String name) {
		List<UserExtraMessageDO> users = userExtraMessageDAO.findByName(name);
		if (users == null)
			return null;
		if (users.size() == 1)
			return bpmUserQueryService.findUserDetailByAccount(users.get(0).getUserAccount());
		
		//法务经理角色
		String legalManagerRole = sysParameterService
			.getSysParameterValue(SysParamEnum.SYS_PARAM_LEGAL_MANAGER_ROLE_NAME.code());
		for (UserExtraMessageDO extra : users) {
			UserDetailInfo user = bpmUserQueryService.findUserDetailByAccount(extra
				.getUserAccount());
			if (user != null) {
				for (Role role : user.getRoleList()) {
					if (StringUtil.equals(role.getCode(), legalManagerRole))
						return user;
					//替换掉系统前缀
					if (StringUtil.equals(
						role.getCode().replaceAll("BusinessSys_", "").replaceAll("bpm_", ""),
						legalManagerRole))
						return user;
				}
			}
		}
		return null;
	}
	
	/**
	 * 生成项目编号
	 * @param busiType
	 * @return
	 */
	private synchronized String genProjectCode(String year, String busiType, String deptCode) {
		deptCode = (deptCode == null ? "" : deptCode);
		busiType = (busiType == null ? "" : busiType);
		String projectCode = year + "-" + StringUtil.leftPad(deptCode, 3, "0") + "-"
								+ StringUtil.leftPad(busiType, 3, "0");
		String seqName = SysDateSeqNameEnum.PROJECT_CODE_SEQ.code() + projectCode;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		projectCode += "-" + StringUtil.leftPad(String.valueOf(seq), 3, "0");
		return projectCode;
	}
	
	/**
	 * 生成批复编号
	 * @param year
	 * @return
	 */
	private synchronized String genSpCode(String year) {
		long seq = dateSeqService.getNextSeqNumberWithoutCache(
			SysDateSeqNameEnum.COUNCIL_SP_CODE_SEQ.code() + year, false);
		return "风控批复【" + year + "】第" + StringUtil.leftPad(String.valueOf(seq), 3, "0") + "号";
	}
	
	@Override
	protected ProjectImportResult createResult() {
		return new ProjectImportResult();
	}
}
