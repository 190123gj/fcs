package com.born.fcs.pm.biz.service.setup;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.crm.ws.service.ChannalService;
import com.born.fcs.crm.ws.service.CompanyCustomerService;
import com.born.fcs.crm.ws.service.PersonalCustomerService;
import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.crm.ws.service.info.CompanyOwnershipStructureInfo;
import com.born.fcs.crm.ws.service.info.PersonalCustomerInfo;
import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.CommonAttachmentDO;
import com.born.fcs.pm.dal.dataobject.FProjectBankLoanDO;
import com.born.fcs.pm.dal.dataobject.FProjectCounterGuaranteeGuarantorDO;
import com.born.fcs.pm.dal.dataobject.FProjectCounterGuaranteePledgeDO;
import com.born.fcs.pm.dal.dataobject.FProjectCustomerBaseInfoDO;
import com.born.fcs.pm.dal.dataobject.FProjectDO;
import com.born.fcs.pm.dal.dataobject.FProjectEquityStructureDO;
import com.born.fcs.pm.dal.dataobject.FProjectExternalGuaranteeDO;
import com.born.fcs.pm.dal.dataobject.FProjectGuaranteeEntrustedDO;
import com.born.fcs.pm.dal.dataobject.FProjectLgLitigationDO;
import com.born.fcs.pm.dal.dataobject.FProjectUnderwritingDO;
import com.born.fcs.pm.dal.dataobject.FormDO;
import com.born.fcs.pm.dal.dataobject.ProjectChannelRelationDO;
import com.born.fcs.pm.dataobject.FormProjectDO;
import com.born.fcs.pm.dataobject.SetupFormProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CertTypeEnum;
import com.born.fcs.pm.ws.enums.ChannelRelationEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.EnterpriseNatureEnum;
import com.born.fcs.pm.ws.enums.EnterpriseScaleEnum;
import com.born.fcs.pm.ws.enums.EvaluateCompanyRegionEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.GuaranteeTypeEnum;
import com.born.fcs.pm.ws.enums.PledgeTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.SysDateSeqNameEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;
import com.born.fcs.pm.ws.info.setup.FProjectBankLoanInfo;
import com.born.fcs.pm.ws.info.setup.FProjectCounterGuaranteeGuarantorInfo;
import com.born.fcs.pm.ws.info.setup.FProjectCounterGuaranteePledgeInfo;
import com.born.fcs.pm.ws.info.setup.FProjectCustomerBaseInfo;
import com.born.fcs.pm.ws.info.setup.FProjectEquityStructureInfo;
import com.born.fcs.pm.ws.info.setup.FProjectExternalGuaranteeInfo;
import com.born.fcs.pm.ws.info.setup.FProjectGuaranteeEntrustedInfo;
import com.born.fcs.pm.ws.info.setup.FProjectInfo;
import com.born.fcs.pm.ws.info.setup.FProjectLgLitigationInfo;
import com.born.fcs.pm.ws.info.setup.FProjectUnderwritingInfo;
import com.born.fcs.pm.ws.info.setup.SetupFormProjectInfo;
import com.born.fcs.pm.ws.order.common.CopyHisFormOrder;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationBatchOrder;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationOrder;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.setup.FProjectBankLoanBatchOrder;
import com.born.fcs.pm.ws.order.setup.FProjectBankLoanOrder;
import com.born.fcs.pm.ws.order.setup.FProjectCounterGuaranteeGuarantorOrder;
import com.born.fcs.pm.ws.order.setup.FProjectCounterGuaranteeOrder;
import com.born.fcs.pm.ws.order.setup.FProjectCounterGuaranteePledgeOrder;
import com.born.fcs.pm.ws.order.setup.FProjectCustomerBaseInfoOrder;
import com.born.fcs.pm.ws.order.setup.FProjectEquityStructureBatchOrder;
import com.born.fcs.pm.ws.order.setup.FProjectEquityStructureOrder;
import com.born.fcs.pm.ws.order.setup.FProjectExternalGuaranteeBatchOrder;
import com.born.fcs.pm.ws.order.setup.FProjectExternalGuaranteeOrder;
import com.born.fcs.pm.ws.order.setup.FProjectGuaranteeEntrustedOrder;
import com.born.fcs.pm.ws.order.setup.FProjectLgLitigationOrder;
import com.born.fcs.pm.ws.order.setup.FProjectOrder;
import com.born.fcs.pm.ws.order.setup.FProjectUnderwritingOrder;
import com.born.fcs.pm.ws.order.setup.SetupFormQueryOrder;
import com.born.fcs.pm.ws.order.setup.SetupFormSaveOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectChannelRelationService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.setup.ProjectSetupService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("projectSetupService")
public class ProjectSetupServiceImpl extends BaseFormAutowiredDomainService implements
																			ProjectSetupService {
	
	@Autowired
	ProjectRelatedUserService projectRelatedUserService;
	
	@Autowired
	CompanyCustomerService companyCustomerServiceClient;
	
	@Autowired
	PersonalCustomerService personalCustomerServiceClient;
	
	@Autowired
	ProjectChannelRelationService projectChannelRelationService;
	
	@Autowired
	ChannalService channelClient;
	
	/**
	 * 保存其他数据的时候 项目不存在则初始化一个
	 * @param order
	 * @return
	 */
	private FcsBaseResult initProject(final SetupFormSaveOrder order) {
		
		return commonProcess(order, "初始化项目信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				final Date nowDate = FcsPmDomainHolder.get().getSysDate();
				
				FProjectDO project = FProjectDAO.findByFormId(order.getFormId());
				
				if (project == null) {
					
					project = new FProjectDO();
					
					String projectCode = genProjectCode(order.getBusiType(), order.getDeptCode()); //生成项目编号
					
					project.setBusiManagerId(order.getUserId());
					project.setBusiManagerName(order.getUserName());
					project.setBusiManagerAccount(order.getUserAccount());
					project.setProjectCode(projectCode);
					project.setFormId(order.getFormId());
					project.setBusiType(order.getBusiType());
					project.setBusiTypeName(order.getBusiTypeName());
					project.setRawAddTime(nowDate);
					if (order.isSaveCounterGuarantee())
						project.setOtherCounterGuarntee(order.getOtherCounterGuarantee());
					
					FProjectDAO.insert(project);
					
					//保存业务经理到相关人员表
					ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
					relatedUser.setProjectCode(projectCode);
					relatedUser.setUserType(ProjectRelatedUserTypeEnum.BUSI_MANAGER);
					relatedUser.setRemark("业务经理");
					relatedUser.setUserId(project.getBusiManagerId());
					relatedUser.setUserAccount(project.getBusiManagerAccount());
					relatedUser.setUserName(project.getBusiManagerName());
					if (order.getDeptId() == null)
						relatedUser.setDeptId(order.getDeptId());
					relatedUser.setDeptCode(order.getDeptCode());
					relatedUser.setDeptName(order.getDeptName());
					relatedUser.setDeptPath(order.getDeptPath());
					relatedUser.setDeptPathName(order.getDeptPathName());
					projectRelatedUserService.setRelatedUser(relatedUser);
				} else if (order.isSaveCounterGuarantee()) {
					project.setOtherCounterGuarntee(order.getOtherCounterGuarantee());
					FProjectDAO.update(project);
				}
				FcsBaseResult result = (FcsBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				result.setReturnObject(covertProjcetDO2Info(project));
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FormBaseResult copyHistory(final CopyHisFormOrder order) {
		///复制历史立项表单
		return (FormBaseResult) commonProcess(order, "复制历史立项表单", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				//查询历史表单信息
				FormDO hisForm = formDAO.findByFormId(order.getFormId());
				if (hisForm == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "历史表单不存在");
				}
				
				FormCodeEnum formCode = FormCodeEnum.getByCode(hisForm.getFormCode());
				//新增form
				FormDO copyForm = new FormDO();
				BeanCopier.staticCopy(order, copyForm, UnBoxingConverter.getInstance());
				copyForm.setFormCode(formCode.code());
				copyForm.setFormName(formCode.message());
				copyForm.setFormUrl(formCode.viewUrl());
				copyForm.setStatus(FormStatusEnum.DRAFT.code());
				String checkStatus = formCode.defaultCheckStatus();
				String approvalCheckStatus = "";
				for (int i = 0; i < checkStatus.length(); i++) {
					approvalCheckStatus += "1";
				}
				copyForm.setCheckStatus(approvalCheckStatus);
				copyForm.setRawAddTime(now);
				
				//查询历史项目信息
				FProjectDO project = FProjectDAO.findByFormId(order.getFormId());
				//原始业务类型
				String hisBusiType = project.getBusiType();
				//新业务类型
				project.setBusiType(order.getBusiType());
				project.setBusiTypeName(order.getBusiTypeName());
				
				logger.info(project.getBusiType(), order.getDeptCode());
				//生成新的项目编号
				String projectCode = genProjectCode(project.getBusiType(), order.getDeptCode());
				
				copyForm.setRelatedProjectCode(projectCode);
				long formId = formDAO.insert(copyForm);
				copyForm.setFormId(formId);
				
				//1、新增项目信息
				long hisProjectId = project.getProjectId();
				BeanCopier.staticCopy(order, project);
				project.setProjectCode(projectCode);
				project.setProjectName(project.getProjectName() /*+ "-副本"*/);
				project.setBusiManagerId(order.getUserId());
				project.setBusiManagerName(order.getUserName());
				project.setBusiManagerAccount(order.getUserAccount());
				project.setFormId(formId);
				project.setProjectId(0);
				project.setProjectId(FProjectDAO.insert(project));
				
				//项目详细信息
				if (formCode == FormCodeEnum.SET_UP_GUARANTEE_ENTRUSTED) {
					FProjectGuaranteeEntrustedDO pDetail = FProjectGuaranteeEntrustedDAO
						.findByProjectId(hisProjectId);
					pDetail.setId(0);
					pDetail.setProjectId(project.getProjectId());
					pDetail.setRiskManagerId(0);
					pDetail.setRiskManagerAccount(null);
					pDetail.setRiskManagerAccount(null);
					pDetail.setRawAddTime(now);
					FProjectGuaranteeEntrustedDAO.insert(pDetail);
				} else if (formCode == FormCodeEnum.SET_UP_UNDERWRITING) {
					FProjectUnderwritingDO pDetail = FProjectUnderwritingDAO
						.findByProjectId(hisProjectId);
					pDetail.setId(0);
					pDetail.setProjectId(project.getProjectId());
					pDetail.setRawAddTime(now);
					FProjectUnderwritingDAO.insert(pDetail);
				} else if (formCode == FormCodeEnum.SET_UP_LITIGATION_PERSIONAL
							|| formCode == FormCodeEnum.SET_UP_LITIGATION_ENTERPRISE) {
					FProjectLgLitigationDO pDetail = FProjectLgLitigationDAO
						.findByProjectId(hisProjectId);
					pDetail.setId(0);
					pDetail.setProjectId(project.getProjectId());
					pDetail.setLegalManagerId(0);
					pDetail.setLegalManagerName(null);
					pDetail.setLegalManagerAccount(null);
					pDetail.setRawAddTime(now);
					FProjectLgLitigationDAO.insert(pDetail);
				}
				
				//2、从crm获取最新的客户信息
				FProjectCustomerBaseInfoDO customerInfo = new FProjectCustomerBaseInfoDO();
				customerInfo.setProjectCode(projectCode);
				customerInfo.setCustomerType(project.getCustomerType());
				customerInfo.setFormId(formId);
				customerInfo.setCustomerId(project.getCustomerId());
				if (StringUtil.equals(CustomerTypeEnum.PERSIONAL.code(), project.getCustomerType())) {
					PersonalCustomerInfo pc = personalCustomerServiceClient.queryByUserId(project
						.getCustomerId());
					if (pc != null) {
						BeanCopier.staticCopy(pc, customerInfo);
						customerInfo.setCustomerId(pc.getUserId());
						customerInfo.setFormId(formId);
						customerInfo.setProjectCode(projectCode);
						if (StringUtil.equals(CertTypeEnum.IDENTITY_CARD.code(), pc.getCertType())) {
							customerInfo.setBusiLicenseUrl(pc.getCertImgFont());
							customerInfo.setOrgCodeUrl(pc.getCertImgBack());
						} else {
							customerInfo.setBusiLicenseUrl(pc.getCertImgFont());
						}
						customerInfo.setContactNo(pc.getMobile());
					}
					//新增个人客户
					FProjectCustomerBaseInfoDAO.insert(customerInfo);
				} else {
					
					CompanyCustomerInfo cc = companyCustomerServiceClient.queryByUserId(
						project.getCustomerId(), null);
					if (cc != null) {
						BeanCopier.staticCopy(cc, customerInfo);
						customerInfo.setCustomerId(cc.getUserId());
						customerInfo.setFormId(formId);
						customerInfo.setProjectCode(projectCode);
						FProjectCustomerBaseInfoDAO.insert(customerInfo);
						
						//3、查询股权结构
						List<CompanyOwnershipStructureInfo> structre = cc
							.getCompanyOwnershipStructure();
						if (ListUtil.isNotEmpty(structre)) {
							//新增股权结构
							for (CompanyOwnershipStructureInfo struc : structre) {
								FProjectEquityStructureDO sDO = new FProjectEquityStructureDO();
								sDO.setFormId(formId);
								sDO.setProjectCode(projectCode);
								sDO.setCustomerId(cc.getUserId());
								sDO.setCustomerName(cc.getCustomerName());
								sDO.setAmountType(struc.getAmountType());
								sDO.setEquityRatio(NumberUtil.parseDouble(struc.getEquity()));//股权比例
								sDO.setStockholder(struc.getShareholdersName());//主要股东名称
								sDO.setContributionWay(struc.getMethord()); //出资方式
								sDO.setCapitalContributions(struc.getAmount());//出资额
								FProjectEquityStructureDAO.insert(sDO);
							}
						}
					}
				}
				
				//4、银行贷款情况
				List<FProjectBankLoanDO> bankLoans = FProjectBankLoanDAO.findByFormId(hisForm
					.getFormId());
				if (ListUtil.isNotEmpty(bankLoans)) {
					for (FProjectBankLoanDO bankLoan : bankLoans) {
						bankLoan.setId(0);
						bankLoan.setFormId(formId);
						bankLoan.setProjectCode(projectCode);
						bankLoan.setCustomerId(customerInfo.getCustomerId());
						bankLoan.setCustomerName(customerInfo.getCustomerName());
						bankLoan.setRawAddTime(now);
						FProjectBankLoanDAO.insert(bankLoan);
					}
				}
				//5、对外担保情况
				List<FProjectExternalGuaranteeDO> egs = FProjectExternalGuaranteeDAO
					.findByFormId(hisForm.getFormId());
				if (ListUtil.isNotEmpty(egs)) {
					for (FProjectExternalGuaranteeDO eg : egs) {
						eg.setId(0);
						eg.setFormId(formId);
						eg.setProjectCode(projectCode);
						eg.setCustomerId(customerInfo.getCustomerId());
						eg.setCustomerName(customerInfo.getCustomerName());
						eg.setRawAddTime(now);
						FProjectExternalGuaranteeDAO.insert(eg);
					}
				}
				if (formCode == FormCodeEnum.SET_UP_GUARANTEE_ENTRUSTED) {
					//6、反担保/担保情况
					List<FProjectCounterGuaranteePledgeDO> cgs = FProjectCounterGuaranteePledgeDAO
						.findByFormId(hisForm.getFormId());
					for (FProjectCounterGuaranteePledgeDO cg : cgs) {
						cg.setId(0);
						cg.setFormId(formId);
						cg.setProjectCode(projectCode);
						cg.setCustomerId(customerInfo.getCustomerId());
						cg.setCustomerName(customerInfo.getCustomerName());
						cg.setRawAddTime(now);
						FProjectCounterGuaranteePledgeDAO.insert(cg);
					}
					
					//反担保附件
					CommonAttachmentDO cdo = new CommonAttachmentDO();
					cdo.setBizNo(String.valueOf(hisForm.getFormId()));
					cdo.setModuleType(CommonAttachmentTypeEnum.COUNTER_GUARANTEE.code());
					List<CommonAttachmentDO> cas = commonAttachmentDAO.findByBizNoModuleType(cdo);
					if (ListUtil.isNotEmpty(cas)) {
						for (CommonAttachmentDO ca : cas) {
							ca.setAttachmentId(0);
							ca.setBizNo(String.valueOf(formId));
							ca.setProjectCode(projectCode);
							ca.setRawAddTime(now);
							commonAttachmentDAO.insert(ca);
						}
					}
					
					//保证
					List<FProjectCounterGuaranteeGuarantorDO> cggs = FProjectCounterGuaranteeGuarantorDAO
						.findByFormId(hisForm.getFormId());
					if (ListUtil.isNotEmpty(cggs)) {
						for (FProjectCounterGuaranteeGuarantorDO cgg : cggs) {
							cgg.setId(0);
							cgg.setFormId(formId);
							cgg.setProjectCode(projectCode);
							cgg.setCustomerId(customerInfo.getCustomerId());
							cgg.setCustomerName(customerInfo.getCustomerName());
							cgg.setRawAddTime(now);
							FProjectCounterGuaranteeGuarantorDAO.insert(cgg);
						}
					}
				}
				
				//复制资金渠道、项目渠道
				ProjectChannelRelationDO projectChannelRelation = new ProjectChannelRelationDO();
				projectChannelRelation.setBizNo(String.valueOf(order.getFormId()));
				List<ProjectChannelRelationDO> channels = projectChannelRelationDAO
					.findByCondition(projectChannelRelation, null, null, 0, 999);
				if (ListUtil.isNotEmpty(channels)) {
					boolean isBankFinancingHis = ProjectUtil.isBankFinancing(hisBusiType);
					boolean isBankFinancingNew = ProjectUtil.isBankFinancing(project.getBusiType());
					boolean hasOne = false;
					for (ProjectChannelRelationDO channel : channels) {
						//银行融资担保复制到非银行融资担保的资金渠道不复制
						if (isBankFinancingHis
							&& !isBankFinancingNew
							&& hasOne
							&& StringUtil.equals(channel.getChannelRelation(),
								ChannelRelationEnum.CAPITAL_CHANNEL.code())) {
							continue;
						}
						
						channel.setId(0);
						channel.setBizNo(String.valueOf(formId));
						channel.setProjectCode(projectCode);
						channel.setRawAddTime(now);
						projectChannelRelationDAO.insert(channel);
						
						if (StringUtil.equals(channel.getChannelRelation(),
							ChannelRelationEnum.CAPITAL_CHANNEL.code())) {
							hasOne = true;
						}
					}
				}
				
				//如果是委贷则默认资金渠道
				if (ProjectUtil.isEntrusted(project.getBusiType())) {
					projectChannelRelationDAO.deleteByBizNoAndType(String.valueOf(formId),
						ChannelRelationEnum.CAPITAL_CHANNEL.code());
					String channalCode = sysParameterService
						.getSysParameterValue(SysParamEnum.ENTRUSTED_DEFAULT_CHANNEL.code());
					ChannalInfo channel = channelClient.queryByChannalCode(channalCode);
					if (channel != null) {
						ProjectChannelRelationDO capitalChannel = new ProjectChannelRelationDO();
						capitalChannel.setChannelId(channel.getId());
						capitalChannel.setChannelCode(channel.getChannelCode());
						capitalChannel.setChannelName(channel.getChannelName());
						capitalChannel.setChannelType(channel.getChannelType());
						capitalChannel.setChannelRelation(ChannelRelationEnum.CAPITAL_CHANNEL
							.code());
						projectChannelRelationDAO.insert(capitalChannel);
					}
				}
				
				//保存业务经理到相关人员表
				ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
				relatedUser.setProjectCode(projectCode);
				relatedUser.setUserType(ProjectRelatedUserTypeEnum.BUSI_MANAGER);
				relatedUser.setRemark("业务经理");
				relatedUser.setUserId(order.getUserId());
				relatedUser.setUserAccount(order.getUserAccount());
				relatedUser.setUserName(order.getUserName());
				relatedUser.setDeptId(order.getDeptId());
				relatedUser.setDeptCode(order.getDeptCode());
				relatedUser.setDeptName(order.getDeptName());
				relatedUser.setDeptPath(order.getDeptPath());
				relatedUser.setDeptPathName(order.getDeptPathName());
				projectRelatedUserService.setRelatedUser(relatedUser);
				
				FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				result.setFormInfo(DoConvert.convertFormInfo(copyForm));
				
				return null;
			}
		}, null, null);
	}
	
	/**
	 * 生成项目编号
	 * @param busiType
	 * @return
	 */
	private synchronized String genProjectCode(String busiType, String deptCode) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		deptCode = (deptCode == null ? "" : deptCode);
		busiType = (busiType == null ? "" : busiType);
		String projectCode = year + "-" + StringUtil.leftPad(deptCode, 3, "0") + "-"
								+ StringUtil.leftPad(busiType, 3, "0");
		String seqName = SysDateSeqNameEnum.PROJECT_CODE_SEQ.code() + projectCode;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		projectCode += "-" + StringUtil.leftPad(String.valueOf(seq), 3, "0");
		return projectCode;
	}
	
	@Override
	public FormBaseResult saveCustomerBaseInfo(final FProjectCustomerBaseInfoOrder order) {
		return commonFormSaveProcess(order, "保存客户基本信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				final Date nowDate = FcsPmDomainHolder.get().getSysDate();
				
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				if (form.getStatus() != FormStatusEnum.DRAFT
					&& form.getStatus() != FormStatusEnum.BACK
					&& form.getStatus() != FormStatusEnum.CANCEL
					&& form.getStatus() != FormStatusEnum.END) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "当前状态不能保存");
				}
				
				FProjectCustomerBaseInfoDO customer = FProjectCustomerBaseInfoDAO.findByFormId(form
					.getFormId());
				
				boolean isUpdate = false;
				
				/* 新增企业基本信息 */
				if (customer == null) {
					customer = new FProjectCustomerBaseInfoDO();
					BeanCopier.staticCopy(order, customer, UnBoxingConverter.getInstance());
					customer.setFormId(form.getFormId());
					customer.setRawAddTime(nowDate);
				} else { /* 修改企业基本信息 */
					isUpdate = true;
					BeanCopier.staticCopy(order, customer, UnBoxingConverter.getInstance());
				}
				
				if (isUpdate) {
					FProjectCustomerBaseInfoDAO.update(customer);
				} else {
					FProjectCustomerBaseInfoDAO.insert(customer);
				}
				
				FcsPmDomainHolder.get().addAttribute("customer", customer);
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain after(Domain domain) {
				
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				order.setFormId(form.getFormId());
				order.setFormCode(form.getFormCode());
				initProject(order);
				
				try {
					//同步股权结果
					FProjectCustomerBaseInfoDO customer = (FProjectCustomerBaseInfoDO) FcsPmDomainHolder
						.get().getAttribute("customer");
					if (customer != null
						&& CustomerTypeEnum.ENTERPRISE.code().equals(customer.getCustomerType())
						&& customer.getCustomerId() > 0) {
						//当前表单的数据
						List<FProjectEquityStructureDO> es = FProjectEquityStructureDAO
							.findByFormId(form.getFormId());
						//客户信息
						CompanyCustomerInfo customerInfo = companyCustomerServiceClient
							.queryByUserId(customer.getCustomerId(), null);
						//当前表单股权结构为空，客户信息中存在股权结构则同步
						if (ListUtil.isEmpty(es) && customerInfo != null
							&& ListUtil.isNotEmpty(customerInfo.getCompanyOwnershipStructure())) {
							FProjectEquityStructureBatchOrder sBatchOrder = new FProjectEquityStructureBatchOrder();
							BeanCopier.staticCopy(form, sBatchOrder,
								UnBoxingConverter.getInstance());
							sBatchOrder.setFromCustomer(true);
							sBatchOrder.setFormCode(form.getFormCode());
							sBatchOrder.setFormId(form.getFormId());
							sBatchOrder.setCustomerId(customer.getCustomerId());
							sBatchOrder.setCustomerName(customer.getCustomerName());
							sBatchOrder.setCustomerType(CustomerTypeEnum.ENTERPRISE.code());
							sBatchOrder.setBusiType(order.getBusiType());
							sBatchOrder.setBusiTypeName(order.getBusiTypeName());
							sBatchOrder.setCheckIndex(1);
							sBatchOrder.setCheckStatus(1);
							List<FProjectEquityStructureOrder> orderList = Lists.newArrayList();
							for (CompanyOwnershipStructureInfo struc : customerInfo
								.getCompanyOwnershipStructure()) {
								FProjectEquityStructureOrder sOrder = new FProjectEquityStructureOrder();
								BeanCopier.staticCopy(sBatchOrder, sOrder);
								sOrder.setEquityRatio(NumberUtil.parseDouble(struc.getEquity()));//股权比例
								sOrder.setStockholder(struc.getShareholdersName());//主要股东名称
								sOrder.setContributionWay(struc.getMethord()); //出资方式
								sOrder.setCapitalContributions(struc.getAmount());//出资额（万元）
								sOrder.setAmountType(struc.getAmountType());
								orderList.add(sOrder);
							}
							sBatchOrder.setEquityStructureOrder(orderList);
							saveEquityStructure(sBatchOrder);
						}
					}
				} catch (Exception e) {
					logger.error("同步CRM股权结构出错 {}", e);
				}
				return null;
			}
		});
	}
	
	@Override
	public FProjectCustomerBaseInfo queryCustomerBaseInfoByFormId(long formId) {
		return covertCustomerDO2Info(FProjectCustomerBaseInfoDAO.findByFormId(formId));
	}
	
	@Override
	public FProjectCustomerBaseInfo queryCustomerBaseInfoByProjectCode(String projectCode) {
		return covertCustomerDO2Info(FProjectCustomerBaseInfoDAO.findByProjectCode(projectCode));
	}
	
	private FProjectCustomerBaseInfo covertCustomerDO2Info(FProjectCustomerBaseInfoDO DO) {
		if (DO == null)
			return null;
		FProjectCustomerBaseInfo info = new FProjectCustomerBaseInfo();
		BeanCopier.staticCopy(DO, info);
		info.setEnterpriseType(EnterpriseNatureEnum.getByCode(DO.getEnterpriseType()));
		info.setIsOneCert(BooleanEnum.getByCode(DO.getIsOneCert()));
		info.setScale(EnterpriseScaleEnum.getByCode(DO.getScale()));
		info.setCustomerType(CustomerTypeEnum.getByCode(DO.getCustomerType()));
		info.setIsLocalGovPlatform(BooleanEnum.getByCode(DO.getIsLocalGovPlatform()));
		info.setIsExportOrientedEconomy(BooleanEnum.getByCode(DO.getIsExportOrientedEconomy()));
		info.setEnterpriseType(EnterpriseNatureEnum.getByCode(DO.getEnterpriseType()));
		info.setCertType(CertTypeEnum.getByCode(DO.getCertType()));
		info.setActualControlManCertType(CertTypeEnum.getByCode(DO.getActualControlManCertType()));
		return info;
	}
	
	@Override
	public FormBaseResult saveEquityStructure(final FProjectEquityStructureBatchOrder order) {
		return commonFormSaveProcess(order, "保存股权结构", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				final Date nowDate = FcsPmDomainHolder.get().getSysDate();
				
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				if (form.getStatus() != FormStatusEnum.DRAFT
					&& form.getStatus() != FormStatusEnum.BACK
					&& form.getStatus() != FormStatusEnum.CANCEL
					&& form.getStatus() != FormStatusEnum.END) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "当前状态不能保存");
				}
				
				//删除原来的记录
				FProjectEquityStructureDAO.deleteByFormId(form.getFormId());
				
				if (ListUtil.isNotEmpty(order.getEquityStructureOrder())) {
					
					for (FProjectEquityStructureOrder sOrder : order.getEquityStructureOrder()) {
						
						if (sOrder.isNull())
							continue;
						
						FProjectEquityStructureDO sDo = new FProjectEquityStructureDO();
						BeanCopier.staticCopy(sOrder, sDo, UnBoxingConverter.getInstance());
						sDo.setRawAddTime(nowDate);
						sDo.setFormId(form.getFormId());
						sDo.setCapitalContributions(sOrder.getCapitalContributions());
						FProjectEquityStructureDAO.insert(sDo);
					}
				}
				
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				if (!order.isFromCustomer()) {
					FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					order.setFormId(form.getFormId());
					order.setFormCode(form.getFormCode());
					initProject(order);
				}
				return null;
			}
		});
	}
	
	@Override
	public List<FProjectEquityStructureInfo> queryEquityStructureByFormId(long formId) {
		List<FProjectEquityStructureDO> list = FProjectEquityStructureDAO.findByFormId(formId);
		List<FProjectEquityStructureInfo> data = Lists.newArrayList();
		if (ListUtil.isNotEmpty(list)) {
			for (FProjectEquityStructureDO DO : list) {
				data.add(covertEquityStructureDO2Info(DO));
			}
		}
		return data;
	}
	
	@Override
	public List<FProjectEquityStructureInfo> queryEquityStructureByProjectCode(String projectCode) {
		List<FProjectEquityStructureDO> list = FProjectEquityStructureDAO
			.findByProjectCode(projectCode);
		List<FProjectEquityStructureInfo> data = Lists.newArrayList();
		if (ListUtil.isNotEmpty(list)) {
			for (FProjectEquityStructureDO DO : list) {
				data.add(covertEquityStructureDO2Info(DO));
			}
		}
		return data;
	}
	
	private FProjectEquityStructureInfo covertEquityStructureDO2Info(FProjectEquityStructureDO DO) {
		if (DO == null)
			return null;
		FProjectEquityStructureInfo info = new FProjectEquityStructureInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	@Override
	public FormBaseResult saveBankLoan(final FProjectBankLoanBatchOrder order) {
		
		return commonFormSaveProcess(order, "保存银行贷款情况", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				final Date nowDate = FcsPmDomainHolder.get().getSysDate();
				
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				if (form.getStatus() != FormStatusEnum.DRAFT
					&& form.getStatus() != FormStatusEnum.BACK
					&& form.getStatus() != FormStatusEnum.CANCEL
					&& form.getStatus() != FormStatusEnum.END) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "当前状态不能保存");
				}
				
				//删除原来的记录
				FProjectBankLoanDAO.deleteByFormId(form.getFormId());
				if (ListUtil.isNotEmpty(order.getBankLoans())) {
					
					for (FProjectBankLoanOrder bOrder : order.getBankLoans()) {
						
						if (bOrder.isNull())
							continue;
						
						FProjectBankLoanDO bDo = new FProjectBankLoanDO();
						if (StringUtil.isNotBlank(bOrder.getLoanStartTimeStr())) {
							bOrder.setLoanStartTime(DateUtil.parse(bOrder.getLoanStartTimeStr()));
						}
						if (StringUtil.isNotBlank(bOrder.getLoanEndTimeStr())) {
							bOrder.setLoanEndTime(DateUtil.parse(bOrder.getLoanEndTimeStr()));
						}
						BeanCopier.staticCopy(bOrder, bDo, UnBoxingConverter.getInstance());
						bDo.setRawAddTime(nowDate);
						bDo.setFormId(form.getFormId());
						bDo.setLoanAmount(bOrder.getLoanAmount());
						bDo.setLoanBalance(bOrder.getLoanBalance());
						
						FProjectBankLoanDAO.insert(bDo);
					}
				}
				
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				order.setFormId(form.getFormId());
				order.setFormCode(form.getFormCode());
				initProject(order);
				return null;
			}
		});
	}
	
	@Override
	public List<FProjectBankLoanInfo> queryBankLoanByFormId(long formId) {
		List<FProjectBankLoanDO> list = FProjectBankLoanDAO.findByFormId(formId);
		List<FProjectBankLoanInfo> data = Lists.newArrayList();
		if (ListUtil.isNotEmpty(list)) {
			for (FProjectBankLoanDO DO : list) {
				data.add(covertBankLoanDO2Info(DO));
			}
		}
		return data;
	}
	
	@Override
	public List<FProjectBankLoanInfo> queryBankLoanByProjectCode(String projectCode) {
		List<FProjectBankLoanDO> list = FProjectBankLoanDAO.findByProjectCode(projectCode);
		List<FProjectBankLoanInfo> data = Lists.newArrayList();
		if (ListUtil.isNotEmpty(list)) {
			for (FProjectBankLoanDO DO : list) {
				data.add(covertBankLoanDO2Info(DO));
			}
		}
		return data;
	}
	
	private FProjectBankLoanInfo covertBankLoanDO2Info(FProjectBankLoanDO DO) {
		if (DO == null)
			return null;
		FProjectBankLoanInfo info = new FProjectBankLoanInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	@Override
	public FormBaseResult saveExternalGuarantee(final FProjectExternalGuaranteeBatchOrder order) {
		return commonFormSaveProcess(order, "保存对外担保情况", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				final Date nowDate = FcsPmDomainHolder.get().getSysDate();
				
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				if (form.getStatus() != FormStatusEnum.DRAFT
					&& form.getStatus() != FormStatusEnum.BACK
					&& form.getStatus() != FormStatusEnum.CANCEL
					&& form.getStatus() != FormStatusEnum.END) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "当前状态不能保存");
				}
				
				//删除原来的记录
				FProjectExternalGuaranteeDAO.deleteByFormId(form.getFormId());
				
				if (ListUtil.isNotEmpty(order.getExternalGuaranteeOrder())) {
					
					for (FProjectExternalGuaranteeOrder gOrder : order.getExternalGuaranteeOrder()) {
						
						if (gOrder.isNull())
							continue;
						
						if (StringUtil.isNotBlank(gOrder.getStartTimeStr())) {
							gOrder.setStartTime(DateUtil.parse(gOrder.getStartTimeStr()));
						}
						if (StringUtil.isNotBlank(gOrder.getEndTimeStr())) {
							gOrder.setEndTime(DateUtil.parse(gOrder.getEndTimeStr()));
						}
						
						FProjectExternalGuaranteeDO gDo = new FProjectExternalGuaranteeDO();
						BeanCopier.staticCopy(gOrder, gDo, UnBoxingConverter.getInstance());
						gDo.setRawAddTime(nowDate);
						gDo.setFormId(form.getFormId());
						gDo.setAmount(gOrder.getAmount());
						FProjectExternalGuaranteeDAO.insert(gDo);
					}
				}
				
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				order.setFormId(form.getFormId());
				order.setFormCode(form.getFormCode());
				initProject(order);
				return null;
			}
		});
	}
	
	@Override
	public List<FProjectExternalGuaranteeInfo> queryExternalGuaranteeByFormId(long formId) {
		List<FProjectExternalGuaranteeDO> list = FProjectExternalGuaranteeDAO.findByFormId(formId);
		List<FProjectExternalGuaranteeInfo> data = Lists.newArrayList();
		if (ListUtil.isNotEmpty(list)) {
			for (FProjectExternalGuaranteeDO DO : list) {
				data.add(covertExternalGuaranteeDO2Info(DO));
			}
		}
		return data;
	}
	
	@Override
	public List<FProjectExternalGuaranteeInfo> queryExternalGuaranteeByProjectCode(	String projectCode) {
		List<FProjectExternalGuaranteeDO> list = FProjectExternalGuaranteeDAO
			.findByProjectCode(projectCode);
		List<FProjectExternalGuaranteeInfo> data = Lists.newArrayList();
		if (ListUtil.isNotEmpty(list)) {
			for (FProjectExternalGuaranteeDO DO : list) {
				data.add(covertExternalGuaranteeDO2Info(DO));
			}
		}
		return data;
	}
	
	private FProjectExternalGuaranteeInfo covertExternalGuaranteeDO2Info(	FProjectExternalGuaranteeDO DO) {
		if (DO == null)
			return null;
		FProjectExternalGuaranteeInfo info = new FProjectExternalGuaranteeInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	@Override
	public FormBaseResult saveCounterGuarantee(final FProjectCounterGuaranteeOrder order) {
		return commonFormSaveProcess(order, "保存反担保/担保情况", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				final Date nowDate = FcsPmDomainHolder.get().getSysDate();
				
				//取得表单信息
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
				}
				
				if (form.getStatus() != FormStatusEnum.DRAFT
					&& form.getStatus() != FormStatusEnum.BACK
					&& form.getStatus() != FormStatusEnum.CANCEL
					&& form.getStatus() != FormStatusEnum.END) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "当前状态不能保存");
				}
				
				//抵押
				FProjectCounterGuaranteePledgeDAO.deleteByFormIdAndType(form.getFormId(),
					GuaranteeTypeEnum.PLEDGE.code());
				if (order.getPledgeOrder() != null) {
					for (FProjectCounterGuaranteePledgeOrder pOrder : order.getPledgeOrder()) {
						
						if (pOrder.isNull())
							continue;
						
						FProjectCounterGuaranteePledgeDO DO = new FProjectCounterGuaranteePledgeDO();
						BeanCopier.staticCopy(pOrder, DO, UnBoxingConverter.getInstance());
						DO.setNetValue(pOrder.getNetValue());
						DO.setFormId(form.getFormId());
						DO.setRawAddTime(nowDate);
						FProjectCounterGuaranteePledgeDAO.insert(DO);
					}
				}
				
				//质押
				FProjectCounterGuaranteePledgeDAO.deleteByFormIdAndType(form.getFormId(),
					GuaranteeTypeEnum.MORTGAGE.code());
				if (order.getMortgageOrder() != null) {
					for (FProjectCounterGuaranteePledgeOrder mOrder : order.getMortgageOrder()) {
						
						if (mOrder.isNull())
							continue;
						
						FProjectCounterGuaranteePledgeDO DO = new FProjectCounterGuaranteePledgeDO();
						BeanCopier.staticCopy(mOrder, DO, UnBoxingConverter.getInstance());
						DO.setNetValue(mOrder.getNetValue());
						DO.setFormId(form.getFormId());
						DO.setRawAddTime(nowDate);
						FProjectCounterGuaranteePledgeDAO.insert(DO);
					}
				}
				
				// 保证人
				FProjectCounterGuaranteeGuarantorDAO.deleteByFormId(form.getFormId());
				if (order.getGuarantorOrder() != null) {
					for (FProjectCounterGuaranteeGuarantorOrder gOrder : order.getGuarantorOrder()) {
						
						if (gOrder.isNull())
							continue;
						
						FProjectCounterGuaranteeGuarantorDO DO = new FProjectCounterGuaranteeGuarantorDO();
						BeanCopier.staticCopy(gOrder, DO, UnBoxingConverter.getInstance());
						
						DO.setRegisterCapital(gOrder.getRegisterCapital());
						DO.setTotalAsset(gOrder.getTotalAsset());
						DO.setExternalGuaranteeAmount(gOrder.getExternalGuaranteeAmount());
						
						DO.setFormId(form.getFormId());
						DO.setRawAddTime(nowDate);
						FProjectCounterGuaranteeGuarantorDAO.insert(DO);
					}
				}
				
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				order.setFormId(form.getFormId());
				order.setFormCode(form.getFormCode());
				order.setSaveCounterGuarantee(true);
				initProject(order);
				return null;
			}
		});
	}
	
	@Override
	public List<FProjectCounterGuaranteePledgeInfo> queryCounterGuaranteePledgeByFormId(long formId) {
		List<FProjectCounterGuaranteePledgeDO> list = FProjectCounterGuaranteePledgeDAO
			.findByFormId(formId);
		List<FProjectCounterGuaranteePledgeInfo> data = Lists.newArrayList();
		if (ListUtil.isNotEmpty(list)) {
			for (FProjectCounterGuaranteePledgeDO DO : list) {
				data.add(covertCounterGuaranteePledgeDO2Info(DO));
			}
		}
		return data;
	}
	
	@Override
	public List<FProjectCounterGuaranteePledgeInfo> queryCounterGuaranteePledgeByProjectCode(	String projectCode) {
		List<FProjectCounterGuaranteePledgeDO> list = FProjectCounterGuaranteePledgeDAO
			.findByProjectCode(projectCode);
		List<FProjectCounterGuaranteePledgeInfo> data = Lists.newArrayList();
		if (ListUtil.isNotEmpty(list)) {
			for (FProjectCounterGuaranteePledgeDO DO : list) {
				data.add(covertCounterGuaranteePledgeDO2Info(DO));
			}
		}
		return data;
	}
	
	private FProjectCounterGuaranteePledgeInfo covertCounterGuaranteePledgeDO2Info(	FProjectCounterGuaranteePledgeDO DO) {
		if (DO == null)
			return null;
		FProjectCounterGuaranteePledgeInfo info = new FProjectCounterGuaranteePledgeInfo();
		BeanCopier.staticCopy(DO, info);
		info.setType(GuaranteeTypeEnum.getByCode(DO.getType()));
		info.setPledgeType(PledgeTypeEnum.getByCode(DO.getPledgeType()));
		return info;
	}
	
	@Override
	public List<FProjectCounterGuaranteeGuarantorInfo> queryCounterGuaranteeGuarantorByFormId(	long formId) {
		List<FProjectCounterGuaranteeGuarantorDO> list = FProjectCounterGuaranteeGuarantorDAO
			.findByFormId(formId);
		List<FProjectCounterGuaranteeGuarantorInfo> data = Lists.newArrayList();
		if (ListUtil.isNotEmpty(list)) {
			for (FProjectCounterGuaranteeGuarantorDO DO : list) {
				data.add(covertCounterGuaranteeGuarantorDO2Info(DO));
			}
		}
		return data;
	}
	
	@Override
	public List<FProjectCounterGuaranteeGuarantorInfo> queryCounterGuaranteeGuarantorByProjectCode(	String projectCode) {
		List<FProjectCounterGuaranteeGuarantorDO> list = FProjectCounterGuaranteeGuarantorDAO
			.findByProjectCode(projectCode);
		List<FProjectCounterGuaranteeGuarantorInfo> data = Lists.newArrayList();
		if (ListUtil.isNotEmpty(list)) {
			for (FProjectCounterGuaranteeGuarantorDO DO : list) {
				data.add(covertCounterGuaranteeGuarantorDO2Info(DO));
			}
		}
		return data;
	}
	
	private FProjectCounterGuaranteeGuarantorInfo covertCounterGuaranteeGuarantorDO2Info(	FProjectCounterGuaranteeGuarantorDO DO) {
		if (DO == null)
			return null;
		FProjectCounterGuaranteeGuarantorInfo info = new FProjectCounterGuaranteeGuarantorInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	@Override
	public QueryBaseBatchResult<SetupFormProjectInfo> querySetupForm(SetupFormQueryOrder order) {
		
		QueryBaseBatchResult<SetupFormProjectInfo> baseBatchResult = new QueryBaseBatchResult<SetupFormProjectInfo>();
		SetupFormProjectDO setupForm = new SetupFormProjectDO();
		
		BeanCopier.staticCopy(order, setupForm);
		
		long totalSize = extraDAO.searchSetupFormCount(setupForm, order.getSubmitTimeStart(),
			order.getSubmitTimeEnd(), order.getDeptIdList(), order.getBusiTypeList());
		
		PageComponent component = new PageComponent(order, totalSize);
		List<FormProjectDO> pageList = extraDAO.searchSetupForm(setupForm,
			component.getFirstRecord(), component.getPageSize(), order.getSubmitTimeStart(),
			order.getSubmitTimeEnd(), order.getDeptIdList(), order.getBusiTypeList(),
			order.getSortCol(), order.getSortOrder());
		
		List<SetupFormProjectInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (FormProjectDO sf : pageList) {
				SetupFormProjectInfo info = new SetupFormProjectInfo();
				BeanCopier.staticCopy(sf, info);
				info.setFormCode(FormCodeEnum.getByCode(sf.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(sf.getFormStatus()));
				info.setTimeUnit(TimeUnitEnum.getByCode(sf.getTimeUnit()));
				info.setProjectStatus(ProjectStatusEnum.getByCode(sf.getProjectStatus()));
				list.add(info);
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
	
	/**
	 * 保存项目信息公共部分
	 * @param order
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private FProjectDO saveProjectCommon(final FProjectOrder order) {
		
		final Date nowDate = FcsPmDomainHolder.get().getSysDate();
		
		//取得表单信息
		FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
		
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "获取表单信息出错");
		}
		
		if (form.getStatus() != FormStatusEnum.DRAFT && form.getStatus() != FormStatusEnum.BACK
			&& form.getStatus() != FormStatusEnum.CANCEL && form.getStatus() != FormStatusEnum.END) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "当前状态不能保存");
		}
		
		FProjectDO project = FProjectDAO.findByFormId(form.getFormId());
		
		boolean isUpdate = false;
		
		/* 新增 */
		if (project == null) {
			
			project = new FProjectDO();
			BeanCopier.staticCopy(order, project, UnBoxingConverter.getInstance());
			String projectCode = genProjectCode(order.getBusiType(), order.getDeptCode()); //生成项目编号
			
			project.setBusiManagerId(order.getUserId());
			project.setBusiManagerAccount(order.getUserAccount());
			project.setBusiManagerName(order.getUserName());
			project.setProjectCode(projectCode);
			project.setFormId(form.getFormId());
			project.setRawAddTime(nowDate);
			
		} else {/* 修改 */
			//项目主要信息只是修改金额
			BeanCopier.staticCopy(order, project, UnBoxingConverter.getInstance());
			isUpdate = true;
		}
		
		project.setAmount(order.getAmount());
		//更新业务经理为当前编辑的人
		project.setBusiManagerId(order.getUserId());
		project.setBusiManagerAccount(order.getUserAccount());
		project.setBusiManagerName(order.getUserName());
		project.setDeptId(order.getDeptId() == null ? 0 : order.getDeptId());
		project.setDeptName(order.getDeptName());
		//更新业务类型
		project.setBusiType(order.getBusiType());
		project.setBusiTypeName(order.getBusiTypeName());
		
		if (StringUtil.isBlank(project.getBelongToNc())) {
			project.setBelongToNc(BooleanEnum.NO.code());
		}
		
		//人民币的时候
		if (StringUtil.equals(order.getForeignCurrencyCode(), "CNY")) {
			project.setForeignCurrencyCode(null);
			project.setForeignAmount(null);
			project.setForeignCurrencyName(null);
			project.setExchangeRate(null);
			project.setExchangeUpdateTime(null);
		}
		
		if (isUpdate) {
			FProjectDAO.update(project);
		} else {
			project.setProjectId(FProjectDAO.insert(project));
		}
		
		return project;
	}
	
	@Override
	public FormBaseResult saveGuaranteeEntrustedProject(final FProjectGuaranteeEntrustedOrder order) {
		
		return commonFormSaveProcess(order, "保存担保/委贷类项目信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				final Date nowDate = FcsPmDomainHolder.get().getSysDate();
				FProjectDO project = saveProjectCommon(order);
				
				FProjectGuaranteeEntrustedDO projectDetail = FProjectGuaranteeEntrustedDAO
					.findByProjectId(project.getProjectId());
				
				/*新增*/
				boolean isUpdate = false;
				if (projectDetail == null) {
					
					projectDetail = new FProjectGuaranteeEntrustedDO();
					BeanCopier.staticCopy(order, projectDetail, UnBoxingConverter.getInstance());
					projectDetail.setRawAddTime(nowDate);
					
				} else {/*修改*/
					isUpdate = true;
					long id = projectDetail.getId();
					BeanCopier.staticCopy(order, projectDetail, UnBoxingConverter.getInstance());
					projectDetail.setId(id);
				}
				
				projectDetail.setProjectId(project.getProjectId());
				
				//保存资金渠道
				List<ProjectChannelRelationOrder> capitalChannels = order.getCapitalChannelOrder();
				if (ListUtil.isEmpty(capitalChannels)) {
					capitalChannels = Lists.newArrayList();
					ProjectChannelRelationOrder capitalChannel = new ProjectChannelRelationOrder();
					capitalChannel.setChannelId(order.getCapitalChannelId());
					capitalChannel.setChannelCode(order.getCapitalChannelCode());
					capitalChannel.setChannelType(order.getCapitalChannelType());
					capitalChannel.setChannelName(order.getCapitalChannelName());
					capitalChannel.setSubChannelId(order.getCapitalSubChannelId());
					capitalChannel.setSubChannelCode(order.getCapitalSubChannelCode());
					capitalChannel.setSubChannelType(order.getCapitalSubChannelType());
					capitalChannel.setSubChannelName(order.getCapitalSubChannelName());
					capitalChannels.add(capitalChannel);
				}
				
				ProjectChannelRelationBatchOrder channelOrder = new ProjectChannelRelationBatchOrder();
				channelOrder.setBizNo(String.valueOf(project.getFormId()));
				channelOrder.setProjectCode(project.getProjectCode());
				channelOrder.setPhases(ProjectPhasesEnum.SET_UP_PHASES);
				channelOrder.setChannelRelation(ChannelRelationEnum.CAPITAL_CHANNEL);
				channelOrder.setRelations(capitalChannels);
				FcsBaseResult result = projectChannelRelationService
					.saveByBizNoAndType(channelOrder);
				if (!result.isSuccess()) {
					throw ExceptionFactory.newFcsException(result.getFcsResultEnum(), "保存资金渠道出错");
				}
				
				//保存项目渠道
				List<ProjectChannelRelationOrder> projectChannels = Lists.newArrayList();
				ProjectChannelRelationOrder projectChannel = new ProjectChannelRelationOrder();
				projectChannel.setChannelId(order.getProjectChannelId());
				projectChannel.setChannelCode(order.getProjectChannelCode());
				projectChannel.setChannelType(order.getProjectChannelType());
				projectChannel.setChannelName(order.getProjectChannelName());
				projectChannel.setSubChannelId(order.getProjectSubChannelId());
				projectChannel.setSubChannelCode(order.getProjectSubChannelCode());
				projectChannel.setSubChannelType(order.getProjectSubChannelType());
				projectChannel.setSubChannelName(order.getProjectSubChannelName());
				projectChannels.add(projectChannel);
				channelOrder.setChannelRelation(ChannelRelationEnum.PROJECT_CHANNEL);
				channelOrder.setRelations(projectChannels);
				result = projectChannelRelationService.saveByBizNoAndType(channelOrder);
				
				if (!result.isSuccess()) {
					throw ExceptionFactory.newFcsException(result.getFcsResultEnum(), "保存项目渠道出错");
				}
				
				if (isUpdate) {
					FProjectGuaranteeEntrustedDAO.update(projectDetail);
				} else {
					FProjectGuaranteeEntrustedDAO.insert(projectDetail);
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FormBaseResult saveUnderwritingProject(final FProjectUnderwritingOrder order) {
		return commonFormSaveProcess(order, "保存承销类项目信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				final Date nowDate = FcsPmDomainHolder.get().getSysDate();
				FProjectDO project = saveProjectCommon(order);
				
				FProjectUnderwritingDO projectDetail = FProjectUnderwritingDAO
					.findByProjectId(project.getProjectId());
				
				boolean isUpdate = false;
				/*新增*/
				if (projectDetail == null) {
					
					projectDetail = new FProjectUnderwritingDO();
					BeanCopier.staticCopy(order, projectDetail, UnBoxingConverter.getInstance());
					projectDetail.setRawAddTime(nowDate);
					
				} else {/*修改*/
					isUpdate = true;
					long detailId = projectDetail.getId();
					BeanCopier.staticCopy(order, projectDetail, UnBoxingConverter.getInstance());
					projectDetail.setId(detailId);
				}
				
				projectDetail.setProjectId(project.getProjectId());
				
				if (isUpdate) {
					FProjectUnderwritingDAO.update(projectDetail);
				} else {
					FProjectUnderwritingDAO.insert(projectDetail);
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FormBaseResult saveLgLitigationProject(final FProjectLgLitigationOrder order) {
		return commonFormSaveProcess(order, "保存诉讼保函类项目信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				final Date nowDate = FcsPmDomainHolder.get().getSysDate();
				FProjectDO project = saveProjectCommon(order);
				
				FProjectLgLitigationDO projectDetail = FProjectLgLitigationDAO
					.findByProjectId(project.getProjectId());
				
				boolean isUpdate = false;
				/*新增*/
				if (projectDetail == null) {
					
					projectDetail = new FProjectLgLitigationDO();
					BeanCopier.staticCopy(order, projectDetail, UnBoxingConverter.getInstance());
					projectDetail.setRawAddTime(nowDate);
					
				} else {/*修改*/
					isUpdate = true;
					long id = projectDetail.getId();
					BeanCopier.staticCopy(order, projectDetail, UnBoxingConverter.getInstance());
					projectDetail.setId(id);
				}
				
				projectDetail.setProjectId(project.getProjectId());
				
				if (isUpdate) {
					FProjectLgLitigationDAO.update(projectDetail);
				} else {
					FProjectLgLitigationDAO.insert(projectDetail);
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FProjectGuaranteeEntrustedInfo queryGuaranteeEntrustedProjectByFormId(long formId) {
		FProjectDO projectCommon = FProjectDAO.findByFormId(formId);
		if (projectCommon == null)
			return null;
		return convertGuaranteeEntrustedProjectDO2Info(projectCommon,
			FProjectGuaranteeEntrustedDAO.findByProjectId(projectCommon.getProjectId()));
	}
	
	@Override
	public FProjectGuaranteeEntrustedInfo queryGuaranteeEntrustedProjectByCode(String projectCode) {
		FProjectDO projectCommon = FProjectDAO.findByProjectCode(projectCode);
		if (projectCommon == null)
			return null;
		return convertGuaranteeEntrustedProjectDO2Info(projectCommon,
			FProjectGuaranteeEntrustedDAO.findByProjectId(projectCommon.getProjectId()));
	}
	
	private FProjectGuaranteeEntrustedInfo convertGuaranteeEntrustedProjectDO2Info(	FProjectDO projectCommon,
																					FProjectGuaranteeEntrustedDO projectDetail) {
		if (projectCommon == null) {
			return null;
		}
		FProjectGuaranteeEntrustedInfo info = new FProjectGuaranteeEntrustedInfo();
		if (projectDetail != null) {
			BeanCopier.staticCopy(projectDetail, info);
			info.setHasPledge(BooleanEnum.getByCode(projectDetail.getHasPledge()));
			info.setHasEvaluateCompany(BooleanEnum.getByCode(projectDetail.getHasEvaluateCompany()));
			info.setEvaluateCompanyRegion(EvaluateCompanyRegionEnum.getByCode(projectDetail
				.getEvaluateCompanyRegion()));
			info.setDepositType(ChargeTypeEnum.getByCode(projectDetail.getDepositType()));
		}
		BeanCopier.staticCopy(projectCommon, info);
		info.setBelongToNc(BooleanEnum.getByCode(projectCommon.getBelongToNc()));
		info.setCustomerType(CustomerTypeEnum.getByCode(projectCommon.getCustomerType()));
		info.setTimeUnit(TimeUnitEnum.getByCode(projectCommon.getTimeUnit()));
		//查询资金渠道
		List<ProjectChannelRelationInfo> capitalChannels = projectChannelRelationService
			.queryByBizNoAndType(String.valueOf(projectCommon.getFormId()),
				ChannelRelationEnum.CAPITAL_CHANNEL);
		info.setCapitalChannels(capitalChannels);
		if (ListUtil.isNotEmpty(capitalChannels)) {
			ProjectChannelRelationInfo channel = capitalChannels.get(0);
			info.setCapitalChannelId(channel.getChannelId());
			info.setCapitalChannelCode(channel.getChannelCode());
			info.setCapitalChannelType(channel.getChannelType());
			info.setCapitalChannelName(channel.getChannelName());
			info.setCapitalSubChannelId(channel.getSubChannelId());
			info.setCapitalSubChannelCode(channel.getSubChannelCode());
			info.setCapitalSubChannelType(channel.getSubChannelType());
			info.setCapitalSubChannelName(channel.getSubChannelName());
		}
		//查询项目渠道
		List<ProjectChannelRelationInfo> projectChannels = projectChannelRelationService
			.queryByBizNoAndType(String.valueOf(projectCommon.getFormId()),
				ChannelRelationEnum.PROJECT_CHANNEL);
		if (ListUtil.isNotEmpty(projectChannels)) {
			ProjectChannelRelationInfo channel = projectChannels.get(0);
			info.setProjectChannelId(channel.getChannelId());
			info.setProjectChannelCode(channel.getChannelCode());
			info.setProjectChannelType(channel.getChannelType());
			info.setProjectChannelName(channel.getChannelName());
			info.setProjectSubChannelId(channel.getSubChannelId());
			info.setProjectSubChannelCode(channel.getSubChannelCode());
			info.setProjectSubChannelType(channel.getSubChannelType());
			info.setProjectSubChannelName(channel.getSubChannelName());
		}
		return info;
	}
	
	@Override
	public FProjectUnderwritingInfo queryUnderwritingProjectByFormId(long formId) {
		FProjectDO projectCommon = FProjectDAO.findByFormId(formId);
		if (projectCommon == null)
			return null;
		return convertUnderwritingProjectDO2Info(projectCommon,
			FProjectUnderwritingDAO.findByProjectId(projectCommon.getProjectId()));
	}
	
	@Override
	public FProjectUnderwritingInfo queryUnderwritingProjectByCode(String projectCode) {
		FProjectDO projectCommon = FProjectDAO.findByProjectCode(projectCode);
		if (projectCommon == null)
			return null;
		return convertUnderwritingProjectDO2Info(projectCommon,
			FProjectUnderwritingDAO.findByProjectId(projectCommon.getProjectId()));
	}
	
	private FProjectUnderwritingInfo convertUnderwritingProjectDO2Info(	FProjectDO projectCommon,
																		FProjectUnderwritingDO projectDetail) {
		if (projectCommon == null) {
			return null;
		}
		FProjectUnderwritingInfo info = new FProjectUnderwritingInfo();
		if (projectDetail != null) {
			BeanCopier.staticCopy(projectDetail, info);
			info.setHasFinancialSupport(BooleanEnum.getByCode(projectDetail
				.getHasFinancialSupport()));
		}
		BeanCopier.staticCopy(projectCommon, info);
		info.setBelongToNc(BooleanEnum.getByCode(projectCommon.getBelongToNc()));
		info.setCustomerType(CustomerTypeEnum.getByCode(projectCommon.getCustomerType()));
		info.setTimeUnit(TimeUnitEnum.getByCode(projectCommon.getTimeUnit()));
		return info;
	}
	
	@Override
	public FProjectLgLitigationInfo queryLgLitigationProjectByFormId(long formId) {
		FProjectDO projectCommon = FProjectDAO.findByFormId(formId);
		if (projectCommon == null)
			return null;
		return convertLgLitigationProjectDO2Info(projectCommon,
			FProjectLgLitigationDAO.findByProjectId(projectCommon.getProjectId()));
	}
	
	@Override
	public FProjectLgLitigationInfo queryLgLitigationProjectByCode(String projectCode) {
		FProjectDO projectCommon = FProjectDAO.findByProjectCode(projectCode);
		if (projectCommon == null)
			return null;
		return convertLgLitigationProjectDO2Info(projectCommon,
			FProjectLgLitigationDAO.findByProjectId(projectCommon.getProjectId()));
	}
	
	private FProjectLgLitigationInfo convertLgLitigationProjectDO2Info(	FProjectDO projectCommon,
																		FProjectLgLitigationDO projectDetail) {
		if (projectCommon == null) {
			return null;
		}
		FProjectLgLitigationInfo info = new FProjectLgLitigationInfo();
		if (projectDetail != null) {
			BeanCopier.staticCopy(projectDetail, info);
			info.setCoInstitutionChargeType(ChargeTypeEnum.getByCode(projectDetail
				.getCoInstitutionChargeType()));
			info.setDepositType(ChargeTypeEnum.getByCode(projectDetail.getDepositType()));
			info.setGuaranteeFeeType(ChargeTypeEnum.getByCode(projectDetail.getGuaranteeFeeType()));
		}
		BeanCopier.staticCopy(projectCommon, info);
		info.setBelongToNc(BooleanEnum.getByCode(projectCommon.getBelongToNc()));
		info.setCustomerType(CustomerTypeEnum.getByCode(projectCommon.getCustomerType()));
		info.setTimeUnit(TimeUnitEnum.getByCode(projectCommon.getTimeUnit()));
		return info;
	}
	
	@Override
	public FProjectInfo queryProjectByCode(String projectCode) {
		return covertProjcetDO2Info(FProjectDAO.findByProjectCode(projectCode));
	}
	
	@Override
	public FProjectInfo queryProjectByFormId(long formId) {
		return covertProjcetDO2Info(FProjectDAO.findByFormId(formId));
	}
	
	private FProjectInfo covertProjcetDO2Info(FProjectDO DO) {
		if (DO == null) {
			return null;
		}
		FProjectInfo info = new FProjectInfo();
		BeanCopier.staticCopy(DO, info);
		info.setTimeUnit(TimeUnitEnum.getByCode(DO.getTimeUnit()));
		info.setCustomerType(CustomerTypeEnum.getByCode(DO.getCustomerType()));
		return info;
	}
	
}
