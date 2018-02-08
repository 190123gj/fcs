package com.born.fcs.pm.biz.service.contract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.am.ws.info.pledgeasset.AssetSimpleInfo;
import com.born.fcs.am.ws.order.pledgeasset.AssetQueryOrder;
import com.born.fcs.am.ws.service.pledgeasset.PledgeAssetService;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.biz.service.common.MailService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.dal.dataobject.FProjectContractCheckDO;
import com.born.fcs.pm.dal.dataobject.FProjectContractDO;
import com.born.fcs.pm.dal.dataobject.FProjectContractExtraValueModifyDO;
import com.born.fcs.pm.dal.dataobject.FProjectContractItemDO;
import com.born.fcs.pm.dal.dataobject.FProjectContractItemInvalidDO;
import com.born.fcs.pm.dal.dataobject.FormDO;
import com.born.fcs.pm.dal.dataobject.ProjectChannelRelationDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dataobject.ProjectContractFormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BasisOfDecisionEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChargePhaseEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.ContractStatusEnum;
import com.born.fcs.pm.ws.enums.ContractTypeEnum;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.LetterTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectContractTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.StampPhaseEnum;
import com.born.fcs.pm.ws.enums.SysDateSeqNameEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractCheckInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractExtraValueModifyInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractInvalidResultInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractItemInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractItemInvalidInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractResultInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContrctInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectLgLitigationInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.order.common.CommonAttachmentBatchOrder;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationAmountOrder;
import com.born.fcs.pm.ws.order.common.SendMailOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.SysWebAccessTokenOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractChannelOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractExtraValueBatchOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractItemInvalidOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractItemOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractQueryOrder;
import com.born.fcs.pm.ws.order.expireproject.MessageAlertOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.contract.ProjectContractCheckMessageResult;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;
import com.born.fcs.pm.ws.service.common.ProjectChannelRelationService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.common.SysWebAccessTokenService;
import com.born.fcs.pm.ws.service.contract.ProjectContractExtraValueService;
import com.born.fcs.pm.ws.service.contract.ProjectContractService;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;
import com.born.fcs.pm.ws.service.expireproject.MessageAlertService;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationService;
import com.born.fcs.pm.ws.service.fund.LoanUseApplyService;
import com.born.fcs.pm.ws.service.projectcreditcondition.ProjectCreditConditionService;
import com.born.fcs.pm.ws.service.releaseproject.ReleaseProjectService;
import com.born.fcs.pm.ws.service.sms.SMSService;
import com.google.common.collect.Lists;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("projectContractService")
public class ProjectContractServiceImpl extends BaseFormAutowiredDomainService implements
																				ProjectContractService {
	
	@Autowired
	ProjectContractExtraValueService projectContractExtraValueService;
	
	@Autowired
	private ProjectRelatedUserService projectRelatedUserService;
	
	@Autowired
	private SiteMessageService siteMessageService;
	
	@Autowired
	private BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	protected SysWebAccessTokenService sysWebAccessTokenService;
	
	@Autowired
	protected MailService mailService;
	
	@Autowired
	protected SMSService sMSService;
	
	@Autowired
	private MessageAlertService messageAlertService;
	
	@Autowired
	private CouncilSummaryService councilSummaryService;
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ChargeNotificationService chargeNotificationService;
	@Autowired
	private ReleaseProjectService releaseProjectService;
	
	@Autowired
	CommonAttachmentService commonAttachmentService;

	@Autowired
	ProjectChannelRelationService projectChannelRelationService;
	@Autowired
	ProjectCreditConditionService projectCreditConditionService;
	@Autowired
	PledgeAssetService pledgeAssetServiceClient;
	@Autowired
	LoanUseApplyService loanUseApplyService;
	
	@Override
	public ProjectContrctInfo findById(long id) {
		ProjectContrctInfo info = null;
		List<FProjectContractItemDO> itemDO = null;
		List<ProjectContractItemInfo> itemInfo = Lists.newArrayList();
		if (id > 0) {
			FProjectContractDO DO = fProjectContractDAO.findById(id);
			itemDO = fProjectContractItemDAO.findByFormContractId(id);
			info = convertDO2Info(DO);
		}
		if (itemDO.size() > 0) {
			for (FProjectContractItemDO DO2 : itemDO) {
				itemInfo.add(convertDO2Info(DO2));
			}
		}
		info.setProjectContractItemInfos(itemInfo);
		return info;
	}
	
	@Override
	public ProjectContractItemInfo findByContractCode(String contractCode) {
		FProjectContractItemDO itemDO = fProjectContractItemDAO.findByContractCode(contractCode);
		ProjectContractItemInfo info = convertDO2Info(itemDO);
		return info;
	}

	@Override
	public ProjectContractItemInfo findByContractCodeProjectCreditConditionUse(String contractCode) {
		FProjectContractItemDO DO = fProjectContractItemDAO.findByContractCode(contractCode);
		if (DO == null)
			return null;
		ProjectContractItemInfo info = new ProjectContractItemInfo();
		BeanCopier.staticCopy(DO, info);
		info.setContractType(ContractTypeEnum.getByCode(DO.getContractType()));
		info.setStampPhase(StampPhaseEnum.getByCode(DO.getStampPhase()));
		info.setLetterType(LetterTypeEnum.getByCode(DO.getLetterType()));
		info.setContractStatus(ContractStatusEnum.getByCode(DO.getContractStatus()));
		info.setDecisionType(BasisOfDecisionEnum.getByCode(DO.getBasisOfDecisionType()));
		return info;
	}



	private ProjectContractItemInfo convertDO2Info(FProjectContractItemDO DO) {
		if (DO == null)
			return null;
		ProjectContractItemInfo info = new ProjectContractItemInfo();
		BeanCopier.staticCopy(DO, info);
		info.setContractType(ContractTypeEnum.getByCode(DO.getContractType()));
		info.setStampPhase(StampPhaseEnum.getByCode(DO.getStampPhase()));
		info.setLetterType(LetterTypeEnum.getByCode(DO.getLetterType()));
		info.setContractStatus(ContractStatusEnum.getByCode(DO.getContractStatus()));
		info.setDecisionType(BasisOfDecisionEnum.getByCode(DO.getBasisOfDecisionType()));
		List<ProjectCreditConditionInfo> creditConditionInfos=Lists.newArrayList();
		if(StringUtil.isNotBlank(DO.getCreditMeasure())){
			String measures[]=DO.getCreditMeasure().split(",");
			for(String meaSure:measures){
				if(StringUtil.isNotBlank(meaSure)){
					ProjectCreditConditionInfo creditConditionInfo= projectCreditConditionService.findById(Long.parseLong(meaSure));
					if(creditConditionInfo!=null){
						if(!creditConditionInfo.getItemDesc().startsWith("关键信息")&&creditConditionInfo.getAssetId()>0) {
						AssetQueryOrder assetQueryOrder = new AssetQueryOrder();
						List<Long> assetsIdList = rop.thirdparty.com.google.common.collect.Lists.newArrayList();
						assetsIdList.add(creditConditionInfo.getAssetId());
						assetQueryOrder.setAssetsIdList(assetsIdList);
						QueryBaseBatchResult<AssetSimpleInfo> queryAssetSimple = pledgeAssetServiceClient.queryAssetSimple(assetQueryOrder);
						if (ListUtil.isNotEmpty(queryAssetSimple.getPageList())) {
							AssetSimpleInfo assetSimpleInfo = queryAssetSimple.getPageList().get(0);
							creditConditionInfo.setWarrantNo(assetSimpleInfo.getAssetRemarkInfo());
						}
					}else{
						creditConditionInfo.setWarrantNo("");
					}
					creditConditionInfos.add(creditConditionInfo);
					}
				}
			}
		}
       info.setCreditConditionInfos(creditConditionInfos);
		return info;
	}
	
	private ProjectContrctInfo convertDO2Info(FProjectContractDO DO) {
		if (DO == null)
			return null;
		ProjectContrctInfo info = new ProjectContrctInfo();
		BeanCopier.staticCopy(DO, info);
		info.setApplyType(ProjectContractTypeEnum.getByCode(DO.getApplyType()));
		return info;
	}
	
	private ProjectContractResultInfo convertDO2Info(ProjectContractFormDO DO) {
		if (DO == null)
			return null;
		ProjectContractResultInfo info = new ProjectContractResultInfo();
		BeanCopier.staticCopy(DO, info);
		info.setContractStatus(ContractStatusEnum.getByCode(DO.getContractStatus()));
		info.setContractType(ContractTypeEnum.getByCode(DO.getContractType()));
		info.setApplyType(ProjectContractTypeEnum.getByCode(DO.getApplyType()));
		info.setLetterType(LetterTypeEnum.getByCode(DO.getLetterType()));
		return info;
	}
	
	private synchronized String genContractCode(String busiTypeCode, String deptCode,
												ProjectContractTypeEnum applyType,
												LetterTypeEnum letterType) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String code = "";
		if (applyType == ProjectContractTypeEnum.PROJECT_CONTRACT) {
			String seqName = SysDateSeqNameEnum.PROJECT_CONTRACT_CODE_SEQ.code() + year;
			long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
			code = busiTypeCode + "-" + deptCode + "-" + year + "-"
					+ StringUtil.leftPad(String.valueOf(seq), 4, "0");
		}else if(applyType == ProjectContractTypeEnum.PROJECT_DB_LETTER){
			String seqName = SysDateSeqNameEnum.PROJECT_DB_LETTER_CODE_SEQ.code() + year;
			long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
			code = busiTypeCode + "-" + deptCode + "-" + year + "-"
					+ StringUtil.leftPad(String.valueOf(seq), 4, "0") + "dbh";
		} else if (applyType == ProjectContractTypeEnum.PROJECT_WRIT) {
			String seqName = SysDateSeqNameEnum.PROJECT_WRIT_CODE_SEQ.code() + year;
			long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
			code = busiTypeCode + "-" + deptCode + "-" + year + "-"
					+ StringUtil.leftPad(String.valueOf(seq), 4, "0") + "ws";
		} else {
			String seqName = SysDateSeqNameEnum.PROJECT_LETTER_CODE_SEQ.code() + year;
			long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
			String str = "";
			if (letterType == LetterTypeEnum.CONTRACT) {
				str = "ht";
			} else if (letterType == LetterTypeEnum.NOT_CONTRACT) {
				str = "fht";
			}else if (letterType == LetterTypeEnum.WRIT) {
				str = "ws";
			} else {
				str = "tz";
			}
			code = busiTypeCode + "-" + deptCode + "-" + year + "-"
					+ StringUtil.leftPad(String.valueOf(seq), 4, "0") + str;
		}
		return code;
	}
	
	@Override
	public FormBaseResult save(final ProjectContractOrder order) {
		return commonFormSaveProcess(order, "保存项目合同集", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				//校验主合同
				if (order.getItem() == null) {
					throw ExceptionFactory
						.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "没有明细，不能保存");
				}
				if(order.getApplyType()==ProjectContractTypeEnum.PROJECT_CONTRACT) {
					checkContractCode2(order);
					checkIsHaveMain(order);
				}
				//				checkStatus
				// 添加判断，合同暂存的时候无视
				// 合同保存的时候判定制式合同是否都有content
				// 1 代表保存
				if (1 == order.getCheckStatus()) {
					if (ListUtil.isNotEmpty(order.getItem())) {
						for (ProjectContractItemOrder itemOrder : order.getItem()) {
							// 代表制式合同
							if (ContractTypeEnum.STANDARD.code()
								.equals(itemOrder.getContractType())) {
								// 若尚未暂存
								if (itemOrder.getId() <= 0) {
									throw ExceptionFactory.newFcsException(
										FcsResultEnum.INCOMPLETE_REQ_PARAM,
										itemOrder.getContractName() + "尚未填写合同内容！");
								}
								FProjectContractItemDO itemDO = fProjectContractItemDAO
									.findById(itemOrder.getId());
								// 若没有内容
								if (itemDO == null || StringUtil.isBlank(itemDO.getContent())) {
									throw ExceptionFactory.newFcsException(
										FcsResultEnum.INCOMPLETE_REQ_PARAM,
										itemOrder.getContractName() + "尚未填写合同内容！");
								}
							} else if (ContractTypeEnum.NOTSTANDARD.code().equals(
								itemOrder.getContractType())) {
								// 若尚未暂存
								if (itemOrder.getId() <= 0) {
									throw ExceptionFactory.newFcsException(
										FcsResultEnum.INCOMPLETE_REQ_PARAM,
										itemOrder.getContractName() + "尚未填写合同内容！");
								}
								FProjectContractItemDO itemDO = fProjectContractItemDAO
									.findById(itemOrder.getId());
								// 若没有内容
								if (itemDO == null || StringUtil.isBlank(itemDO.getFileUrl())) {
									throw ExceptionFactory.newFcsException(
										FcsResultEnum.INCOMPLETE_REQ_PARAM,
										itemOrder.getContractName() + "尚未填写合同内容！");
								}
							}
						}
					}
				}
				
				if (order.getContractId() <= 0) {
					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					final Date now = FcsPmDomainHolder.get().getSysDate();
					//保存
					FProjectContractDO projectContractDO = new FProjectContractDO();
					BeanCopier.staticCopy(order, projectContractDO, UnBoxingConverter.getInstance());
					projectContractDO.setFormId(formInfo.getFormId());
					projectContractDO.setApplyType(order.getApplyType().code());
					projectContractDO.setRawAddTime(now);
					long contractId = fProjectContractDAO.insert(projectContractDO);
					boolean isHaveMain = false;
					for (ProjectContractItemOrder itemOrder : order.getItem()) {
						FProjectContractItemDO itemDO = new FProjectContractItemDO();
						BeanCopier.staticCopy(itemOrder, itemDO, UnBoxingConverter.getInstance());
						itemDO.setContractId(contractId);
						itemDO.setContractStatus(ContractStatusEnum.DRAFT.code());
						if (itemDO.getId() > 0) {//重新提交只更新
							// 添加判断，制式合同不修改fileurl ，
							// 因为制式合同的url是单独存的，这里一定是空，修改之后会导致url为空。
							if (ContractTypeEnum.STANDARD.code()
								.equals(itemOrder.getContractType())||ContractTypeEnum.NOTSTANDARD.code()
										.equals(itemOrder.getContractType())) {
								FProjectContractItemDO infoDO = fProjectContractItemDAO
									.findById(itemOrder.getId());
								if (infoDO == null) {
									throw ExceptionFactory.newFcsException(
										FcsResultEnum.HAVE_NOT_DATA, "未找到项目合同记录");
								}
								itemDO.setFileUrl(infoDO.getFileUrl());
								itemDO.setContent(infoDO.getContent());
								itemDO.setContentMessage(infoDO.getContentMessage());
							}
							itemDO.setLetterType(itemOrder.getLetterType() == null ? null
								: itemOrder.getLetterType().code());
							
							fProjectContractItemDAO.update(itemDO);
							
						} else {
							itemDO.setRawAddTime(now);
							itemDO.setContractCode(genContractCode(order.getBusiType(),
								order.getDeptCode(), order.getApplyType(),
								itemOrder.getLetterType()));
							itemDO.setLetterType(itemOrder.getLetterType() == null ? null
								: itemOrder.getLetterType().code());
							itemDO.setBasisOfDecisionType(itemOrder.getDecisionType() == null ? null
								: itemOrder.getDecisionType().code());
							long detailId = fProjectContractItemDAO.insert(itemDO);
							saveCommonAttachment(order, itemOrder, formInfo.getFormId(), detailId);
						}
						if (itemOrder.getIsMain() != null && itemOrder.getIsMain().equals("IS")) {
							isHaveMain = true;
						}
					}
					if (isHaveMain) {//第一次申请有主合同，第一次申请才更新
						ProjectDO project = projectDAO.findByProjectCodeForUpdate(order
							.getProjectCode());
						if(!StringUtil.equals(project.getIsRedoProject(),"IS")){
						project.setPhases(ProjectPhasesEnum.CONTRACT_PHASES.code());
						project.setStatus(ProjectStatusEnum.NORMAL.code());
						project.setPhasesStatus(ProjectPhasesStatusEnum.DRAFT.code());
						projectDAO.update(project);
						}
					}
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(contractId);
				} else {
					//更新
					FProjectContractDO contractDO = fProjectContractDAO.findById(order
						.getContractId());
					if (null == contractDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到项目合同集记录");
					}
					BeanCopier.staticCopy(order, contractDO, UnBoxingConverter.getInstance());
					contractDO.setApplyType(order.getApplyType().code());
					fProjectContractDAO.update(contractDO);
					List<FProjectContractItemDO> inDataBaseList = fProjectContractItemDAO
						.findByFormContractId(order.getContractId());
					Map<Long, FProjectContractItemDO> inDataBaseMap = new HashMap<Long, FProjectContractItemDO>();
					for (FProjectContractItemDO DO : inDataBaseList) {
						inDataBaseMap.put(DO.getId(), DO);
					}
					FormDO formDO=formDAO.findByFormId(order.getFormId());
					//更新项目合同集--合同
					for (ProjectContractItemOrder itemOrder : order.getItem()) {
						if (itemOrder.getId() == null || itemOrder.getId() <= 0) {//不存在就新增
							final Date now = FcsPmDomainHolder.get().getSysDate();
							FProjectContractItemDO itemDO = new FProjectContractItemDO();
							BeanCopier.staticCopy(itemOrder, itemDO,
								UnBoxingConverter.getInstance());
							itemDO.setContractId(order.getContractId());
							itemDO.setContractStatus(formDO.getStatus());
							itemDO.setRawAddTime(now);
							itemDO.setContractCode(genContractCode(order.getBusiType(),
								order.getDeptCode(), order.getApplyType(),
								itemOrder.getLetterType()));
							itemDO.setLetterType(itemOrder.getLetterType() == null ? null
								: itemOrder.getLetterType().code());
							itemDO.setBasisOfDecisionType(itemOrder.getDecisionType() == null ? null
								: itemOrder.getDecisionType().code());
							long detailId = fProjectContractItemDAO.insert(itemDO);
							saveCommonAttachment(order, itemOrder, contractDO.getFormId(), detailId);
						} else {
							FProjectContractItemDO itemDO = fProjectContractItemDAO
								.findById(itemOrder.getId());
							if (null == itemDO) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
									"未找到合同记录");
							}
							String fileUrl = itemDO.getFileUrl();
							String content = itemDO.getContent();
							String contentMessage = itemDO.getContentMessage();
							String itemDOStatus = itemDO.getContractStatus();
							BeanCopier.staticCopy(itemOrder, itemDO,
								UnBoxingConverter.getInstance());
							itemDO.setContractId(order.getContractId());
							itemDO.setContractStatus(itemDOStatus);
							// 添加判断，制式合同不修改fileurl ，
							// 因为制式合同的url是单独存的，这里一定是空，修改之后会导致url为空。
							if (ContractTypeEnum.STANDARD.code()
								.equals(itemOrder.getContractType())
								|| ContractTypeEnum.NOTSTANDARD.code().equals(
									itemOrder.getContractType())) {
								itemDO.setFileUrl(fileUrl);
								itemDO.setContent(content);
								itemDO.setContentMessage(contentMessage);
							}
							itemDO.setLetterType(itemOrder.getLetterType() == null ? null
								: itemOrder.getLetterType().code());
							itemDO.setBasisOfDecisionType(itemOrder.getDecisionType() == null ? null
								: itemOrder.getDecisionType().code());
							if(contractDO.getApplyType().equals(ProjectContractTypeEnum.PROJECT_LETTER.code())){//更新编号
								itemDO.setContractCode(updateContractCode(itemDO));
							}
							fProjectContractItemDAO.update(itemDO);
							inDataBaseMap.remove(itemDO.getId());
							saveCommonAttachment(order, itemOrder, contractDO.getFormId(),
								itemDO.getId());
						}

					}
					if (inDataBaseMap.size() > 0) {//删除
						for (long id : inDataBaseMap.keySet()) {
							//删除附件
							FProjectContractItemDO DO = inDataBaseMap.get(id);
							String bizNo = "PM_" + contractDO.getFormId() + "_" + DO.getId();
							if (StringUtil.isNotEmpty(DO.getReferAttachment())) {
								bizNo = bizNo + "_CKFJ";
								commonAttachmentService.deleteByBizNoModuleType(bizNo,
									CommonAttachmentTypeEnum.CONTRACT_REFER);
							}
							if (StringUtil.isNotEmpty(DO.getFileUrl())) {
								bizNo = bizNo + "_HT";
								commonAttachmentService.deleteByBizNoModuleType(bizNo,
									CommonAttachmentTypeEnum.CONTRACT_REFER_ATTACHMENT);
							}
							fProjectContractItemDAO.deleteById(id);
						}
					}
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
				}
				return null;
			}
		}, null, null);
	}

	private String updateContractCode(FProjectContractItemDO itemDO){
		String code=itemDO.getContractCode();
		String letterType=itemDO.getLetterType();
		if(code.endsWith("ht")||code.endsWith("ws")||code.endsWith("tz")){
			code=code.substring(0,code.length()-2);
		}else if(code.endsWith("fht")){
			code=code.substring(0,code.length()-3);
		}
		if (StringUtil.equals(letterType,LetterTypeEnum.CONTRACT.code())) {
			code =code+ "ht";
		} else if (StringUtil.equals(letterType,LetterTypeEnum.NOT_CONTRACT.code())) {
			code = code+"fht";
		}else if (StringUtil.equals(letterType,LetterTypeEnum.WRIT.code())) {
			code = code+"ws";
		} else {
			code = code+"tz";
		}
		return code;
	}

	private void checkContractCode2(ProjectContractOrder contractOrder) {
		List<ProjectContractItemOrder> items = contractOrder.getItem();
		if(ListUtil.isNotEmpty(items)){
			String temp="";
			for(int i=0;i<items.size()-1;i++){
				temp = items.get(i).getContractCode2();
				if(!StringUtil.isNotBlank(temp)){
					continue;
				}
				for (int j = i + 1; j < items.size(); j++)
				{
					if (temp.equals(items.get(j).getContractCode2()))
					{
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"[CQEXG"+temp+"]合同编号重复,不能保存!");
					}
				}
			}
		}
		for (ProjectContractItemOrder item : items) {
			if(StringUtil.isNotBlank(item.getContractCode2())){
				FProjectContractItemDO itemDO=fProjectContractItemDAO.findByContractCode2(item.getContractCode2());
				if(itemDO!=null&&item.getId()!=itemDO.getId()){
					throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
							"合同编号["+item.getContractCode2()+"]已存在，不能保存!");
				}
			}
		}
	}

	private void checkIsHaveMain(ProjectContractOrder contractOrder) {
		List<ProjectContractItemOrder> items = contractOrder.getItem();
		//本张单子是否有主合同
		boolean thisHaveMain = false;
		//是否自由流程
		boolean isFree = "IS".equals(contractOrder.getFreeFlow()) ? true : false;
		boolean haveMainInDB=false;
		Long mainId = 0l;
		int thisCountMain=0;//在次添加合同的时候校验
		for (ProjectContractItemOrder item : items) {
			if ("IS".equals(item.getIsMain())) {
				thisHaveMain = true;
				if(item.getId()!=null){
					mainId = item.getId();
				}
				thisCountMain++;
			}
		}
		if(thisCountMain>1){
			throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
					"该项目已经有主合同，不能添加!");
		}
		ProjectContractQueryOrder order = new ProjectContractQueryOrder();
		order.setPageSize(9999l);
		order.setProjectCode(contractOrder.getProjectCode());
		order.setIsMain("IS");
		//order.setExceptContractStatus(ContractStatusEnum.INVALID);
		QueryBaseBatchResult<ProjectContractResultInfo> batchResult = query(order);

		if (ListUtil.isNotEmpty(batchResult.getPageList())) {
			for (ProjectContractResultInfo info : batchResult.getPageList()) {
				if(info.getContractStatus()!=ContractStatusEnum.END){
					haveMainInDB=true;
				}
			}
		}
		if (thisHaveMain) {//如果本张单子有主合同就校验是否有存在其他主合同
			if (batchResult != null) {
				List<ProjectContractResultInfo> infoLists = batchResult.getPageList();
				if (ListUtil.isNotEmpty(infoLists)) {
					for (ProjectContractResultInfo info : infoLists) {
						if (mainId != info.getId()&&info.getContractStatus()!=ContractStatusEnum.END) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
									"该项目已经有主合同，不能保存!");
						}
					}

				}
			}
		}
		//不是自由流程，本张单子没有主合同，这项目没有主合同
		if(!isFree&&!thisHaveMain&&!haveMainInDB&&contractOrder.getCheckStatus()==1){
			throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
					"非自由流程必须要主合同，不能保存!");
		}
	}

	public FcsBaseResult saveStandardContractContent(final ProjectContractItemOrder order) {

		return commonProcess(order, "合同保存", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				if (order == null || order.getId() <= 0) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
						"未找到项目合同主键!");
					//					result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
					//					result.setMessage("未找到项目制式合同主键!");
					//					return null;
				}
				FProjectContractItemDO itemDO = fProjectContractItemDAO.findById(order.getId());
				if (itemDO == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						"未找到项目合同记录!");
					//					result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
					//					result.setMessage("未找到项目制式合同记录!");
					//					return null;
				}
				// 为order设置contractId 用于发送站内信使用
				if (order.getContractId() == null || order.getContractId() <= 0) {
					order.setContractId(itemDO.getContractId());
				}
				itemDO.setId(order.getId());
				// 更新判定，制式合同需要多更新两个属性。非制式合同只更新审核信息
				itemDO.setContent(order.getContent());
				itemDO.setContentMessage(order.getContentMessage());
				// 审核时 不修订fileurl
				if (!order.isChecker()) {
					itemDO.setFileUrl(order.getFileUrl());
				}
				//itemDO.setContractStatus(ContractStatusEnum.DRAFT.code());
				// 设计上此处只有更新，没有单纯的插入，插入是在项目合同集插入的时候做
				// 此处只更新制式合同，非制式合同只有审核信息更新
				if (ContractTypeEnum.STANDARD.code().equals(itemDO.getContractType())) {
					fProjectContractItemDAO.update(itemDO);
				}
				Date nowDate = FcsPmDomainHolder.get().getSysDate();
				// 审核信息插入
				if (order.isChecker()) {

					FProjectContractCheckDO checkInfoDO = fProjectContractCheckDAO
						.findByContractItemIdAndUserId(order.getId(), order.getUserId());
					boolean hasOldItem = true;
					if (checkInfoDO == null) {
						hasOldItem = false;
						checkInfoDO = new FProjectContractCheckDO();
					}

					checkInfoDO.setContractId(itemDO.getContractId());
					checkInfoDO.setContractItemId(itemDO.getId());
					checkInfoDO.setUserId(order.getUserId());
					checkInfoDO.setUserName(order.getUserName());
					checkInfoDO.setFileUrl(order.getCheckFileUrl());
					checkInfoDO.setRemark(order.getCheckRemark());
					checkInfoDO.setRawAddTime(nowDate);
					if (hasOldItem) {
						fProjectContractCheckDAO.update(checkInfoDO);
					} else {
						fProjectContractCheckDAO.insert(checkInfoDO);
					}
				}
				if (ListUtil.isNotEmpty(order.getProjectContractExtraValue())) {
					// 执行任务，制式合同的属性插入属性表，如果是更新需写入更新表
					ProjectContractExtraValueBatchOrder extraOrder = new ProjectContractExtraValueBatchOrder();
					extraOrder.setContractId(itemDO.getContractId());
					extraOrder.setContractItemId(itemDO.getId());
					extraOrder.setContractCode(itemDO.getContractCode());
					extraOrder.setTemplateId(itemDO.getTemplateId());
					extraOrder.setProjectContractExtraValue(order.getProjectContractExtraValue());
					// 添加修改人信息
					extraOrder.setUserId(order.getUserId());
					extraOrder.setUserName(order.getUserName());
					// 因为此处不支持食物套事物，所以单独抓去方法
					//projectContractExtraValueService.extraValueSave(extraOrder);
					projectContractExtraMessageSave(extraOrder, nowDate, order.isChecker());
				}
				return null;
			}
		}, null, new AfterProcessInvokeService() {

			@Override
			public Domain after(Domain domain) {
				// 若无数据，不执行
				if (ListUtil.isNotEmpty(order.getProjectContractExtraValue())) {
					Date now = FcsPmDomainHolder.get().getSysDate();
					// 获取是否有插入或修改记录
					boolean dataChanged = (boolean) FcsPmDomainHolder.get().getAttribute(
						"dataChanged");
					if (dataChanged) {
						// 业务经理 A角
						// 尊敬的用户张三，您于2016-03-23对项目 XXX项目名称XXX 提交的合同申请有新的修改记录，
						// 修改人：张三，快去查看审核详情吧！  查看审核详情
						changeForUser(order, now);
						// 风险经理
						// 尊敬的用户张三，由李四于2016-03-24对项目 XXX项目名称XXX 提交的合同申请有新的修改记录，
						// 修改人：张三，快去查看审核详情吧！  查看审核详情
						// 获取风控委秘书
						String roleName = sysParameterService
							.getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_SECRETARY_ROLE_NAME
								.code());

						List<SysUser> users = bpmUserQueryService.findUserByRoleAlias(roleName);
						for (SysUser user : users) {
							changeForRiskSecretary(order, now, user);
						}
					}
				}
				return null;
			}

			private void changeForUser(final ProjectContractItemOrder order, Date now) {
				List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
				SimpleUserInfo sendUser = new SimpleUserInfo();
				String subject = "合同修改通知";
				ProjectContrctInfo projectContractInfo = findById(order.getContractId());
				// 查询业务经理A角
				// 业务经理A角
				//				RelatedUserInfo busiManager = projectRelatedUserService.getBusiManager(projectContractInfo
				//					.getProjectCode());

				// 改成合同发起人而不是项目发起人
				//				ProjectContractItemInfo projectContractItemInfo = findContractItemById(order
				//					.getId());
				//				if (projectContractItemInfo == null || projectContractItemInfo.getContractId() <= 0) {
				//					return;
				//				}
				//				ProjectContrctInfo projectContrctInfo = findById(projectContractItemInfo
				//					.getContractId());
				if (projectContractInfo == null || projectContractInfo.getFormId() <= 0) {
					return;
				}
				FormDO formDO = formDAO.findByFormId(projectContractInfo.getFormId());
				//				if (busiManager != null) {
				//					sendUser.setUserId(busiManager.getUserId());
				//					SysUser sysUser = bpmUserQueryService.findUserByUserId(busiManager.getUserId());
				if (formDO != null) {
					sendUser.setUserId(formDO.getUserId());
					SysUser sysUser = bpmUserQueryService.findUserByUserId(formDO.getUserId());
					if (sysUser != null) {
						sendUser.setUserAccount(sysUser.getAccount());
						sendUser.setUserName(sysUser.getFullname());
						sendUser.setEmail(sysUser.getEmail());
						sendUser.setMobile(sysUser.getMobile());
					}
					notifyUserList.add(sendUser);
				}

				if (ListUtil.isNotEmpty(notifyUserList)) {

					SimpleUserInfo[] sendUsers = notifyUserList
						.toArray(new SimpleUserInfo[notifyUserList.size()]);

					//表单站内地址
					String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/contract/viewAudit.htm?formId="
										+ projectContractInfo.getFormId();
					String forMessage = "<a href='" + messageUrl + "'>查看审核详情</a>";

					//替换审核地址
					StringBuilder sb = new StringBuilder();
					sb.append("尊敬的用户");
					sb.append(sendUser.getUserName());
					sb.append("，您于 ");
					sb.append(DateUtil.formatDay(now));
					sb.append("对项目  ");
					sb.append(projectContractInfo.getProjectName());
					sb.append("  提交的合同申请有新的修改记录，修改人：");
					sb.append(order.getUserName());
					sb.append("， 快去查看审核详情吧！");
					String messageContent = sb.toString();
					messageContent += forMessage;
					if (StringUtil.isNotBlank(messageContent)) {
						MessageOrder ormessageOrderder = MessageOrder.newSystemMessageOrder();
						ormessageOrderder.setMessageSubject(subject);
						ormessageOrderder.setMessageTitle("合同内容修改通知");
						ormessageOrderder.setMessageContent(messageContent);
						ormessageOrderder.setSendUsers(sendUsers);
						siteMessageService.addMessageInfo(ormessageOrderder);
					}

					for (SimpleUserInfo userInfo : notifyUserList) {

						//发送邮件

						String accessToken = getAccessToken(userInfo);

						//站外审核地址
						String outSiteUrl = "<a href=\"" + getFaceWebUrl() + messageUrl
											+ "&accessToken=" + accessToken
											+ "\" target=\"_blank\">点击处理</a>";

						String mailContent = sb.toString();
						mailContent += outSiteUrl;
						if (StringUtil.isNotBlank(mailContent)) {
							//邮件地址
							List<String> mailAddress = Lists.newArrayList(userInfo.getEmail());
							SendMailOrder mailOrder = new SendMailOrder();
							mailOrder.setSendTo(mailAddress);
							mailOrder.setSubject(subject);
							mailOrder.setContent(mailContent);
							mailService.sendHtmlEmail(mailOrder);
						}
						if (StringUtil.isNotEmpty(userInfo.getMobile())) {

							String contentTxt = sb.toString();
							//发送短信
							sMSService.sendSMS(userInfo.getMobile(), contentTxt, null);
						}
					}
				}
			}

			private void changeForRiskSecretary(final ProjectContractItemOrder order, Date now,
												SysUser sendUser) {
				List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
				String subject = "合同修改通知";
				ProjectContrctInfo projectContractInfo = findById(order.getContractId());
				// 业务经理A角
				//				RelatedUserInfo busiManager = projectRelatedUserService.getBusiManager(projectContractInfo
				//					.getProjectCode());

				//表单站内地址
				String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/contract/viewAudit.htm?formId="
									+ projectContractInfo.getFormId();
				String forMessage = "<a href='" + messageUrl + "'>查看审核详情</a>";
				if (sendUser != null) {
					SimpleUserInfo userInfo = new SimpleUserInfo();
					userInfo.setUserId(sendUser.getUserId());
					userInfo.setUserName(sendUser.getFullname());
					userInfo.setUserAccount(sendUser.getAccount());
					userInfo.setEmail(sendUser.getEmail());
					userInfo.setMobile(sendUser.getMobile());
					notifyUserList.add(userInfo);
				}

				if (projectContractInfo == null || projectContractInfo.getFormId() <= 0) {
					return;
				}
				FormDO formDO = formDAO.findByFormId(projectContractInfo.getFormId());

				if (ListUtil.isNotEmpty(notifyUserList)) {

					SimpleUserInfo[] sendUsers = notifyUserList
						.toArray(new SimpleUserInfo[notifyUserList.size()]);
					//替换审核地址
					StringBuilder sb = new StringBuilder();
					sb.append("尊敬的用户");
					sb.append(sendUsers[0].getUserName());
					sb.append("，由");
					sb.append(bpmUserQueryService.findUserByUserId(formDO.getUserId())
						.getFullname());
					sb.append("于 ");
					sb.append(DateUtil.formatDay(now));
					sb.append("对项目  ");
					sb.append(projectContractInfo.getProjectName());
					sb.append("  提交的合同申请有新的修改记录，修改人：");
					sb.append(order.getUserName());
					sb.append("， 快去查看审核详情吧！");
					String messageContent = sb.toString();
					messageContent += forMessage;
					if (StringUtil.isNotBlank(messageContent)) {
						MessageOrder ormessageOrderder = MessageOrder.newSystemMessageOrder();
						ormessageOrderder.setMessageSubject(subject);
						ormessageOrderder.setMessageTitle("合同内容修改通知");
						ormessageOrderder.setMessageContent(messageContent);
						ormessageOrderder.setSendUsers(sendUsers);
						siteMessageService.addMessageInfo(ormessageOrderder);
					}

					for (SimpleUserInfo userInfo : notifyUserList) {

						//发送邮件

						String accessToken = getAccessToken(userInfo);

						//站外审核地址
						String outSiteUrl = "<a href=\"" + getFaceWebUrl() + messageUrl
											+ "&accessToken=" + accessToken
											+ "\" target=\"_blank\">点击处理</a>";

						String mailContent = sb.toString();
						mailContent += outSiteUrl;
						if (StringUtil.isNotBlank(mailContent)) {
							//邮件地址
							List<String> mailAddress = Lists.newArrayList(userInfo.getEmail());
							SendMailOrder mailOrder = new SendMailOrder();
							mailOrder.setSendTo(mailAddress);
							mailOrder.setSubject(subject);
							mailOrder.setContent(mailContent);
							mailService.sendHtmlEmail(mailOrder);
						}
						if (StringUtil.isNotEmpty(userInfo.getMobile())) {

							String contentTxt = sb.toString();
							//发送短信
							sMSService.sendSMS(userInfo.getMobile(), contentTxt, null);
						}
					}
				}
			}
		});

	}

	@Override
	public QueryBaseBatchResult<ProjectContractResultInfo> query(ProjectContractQueryOrder order) {
		QueryBaseBatchResult<ProjectContractResultInfo> baseBatchResult = new QueryBaseBatchResult<ProjectContractResultInfo>();

		ProjectContractFormDO queryCondition = new ProjectContractFormDO();
		Date applyTimeStart = null;
		Date applyTimeEnd = null;
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);

		if (order.getCustomerName() != null)
			queryCondition.setCustomerName(order.getCustomerName());
		if (order.getProjectCode() != null)
			queryCondition.setProjectCode(order.getProjectCode());
		if (order.getBusiType() != null)
			queryCondition.setBusiType(order.getBusiType());
		if (order.getContractCode() != null)
			queryCondition.setContractCode(order.getContractCode());
		if (order.getContractName() != null)
			queryCondition.setContractName(order.getContractName());
		if (order.getRemark() != null)
			queryCondition.setRemark(order.getRemark());
		if (order.getOperateDate() != null) {
			applyTimeStart = DateUtil.getStartTimeOfTheDate(order.getOperateDate());
			applyTimeEnd = DateUtil.getEndTimeOfTheDate(order.getOperateDate());
		}
		if (order.getContractStatus() != null) {
			queryCondition.setContractStatus(order.getContractStatus());
		}
		if (order.getExceptContractStatus() != null) {
			queryCondition.setExceptContractStatus(order.getExceptContractStatus().code());
		}
		if ("IS".equals(order.getIsChargeNotification())) {
			queryCondition.setIsChargeNotification("IS");
		}

		if (order.getApplyType() != null) {
			queryCondition.setApplyType(order.getApplyType().code());
		} else {
			queryCondition.setApplyType(ProjectContractTypeEnum.PROJECT_CONTRACT.code());
		}
		if (order.getLetterType() != null) {
			queryCondition.setLetterType(order.getLetterType().code());
		}
		queryCondition.setDeptIdList(order.getDeptIdList());
		long totalSize = extraDAO.searchProjectContractListCount(queryCondition, applyTimeStart,
			applyTimeEnd);

		PageComponent component = new PageComponent(order, totalSize);
		List<ProjectContractFormDO> pageList = extraDAO.searchProjectContractList(queryCondition,
			component.getFirstRecord(), component.getPageSize(), applyTimeStart, applyTimeEnd,
			order.getSortCol(), order.getSortOrder());
		List<ProjectContractResultInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (ProjectContractFormDO DO : pageList) {
				ProjectContractResultInfo info = convertDO2Info(DO);
				if (isEnoughMoney(DO)) {
					info.setIsCanStampApply("IS");
				}
				list.add(info);
			}
		}

		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}

	@Override
	public FcsBaseResult saveContractBack(final ProjectContractItemOrder order) {
		return commonProcess(order, "合同回传", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				final Date now = FcsPmDomainHolder.get().getSysDate();
				FProjectContractItemDO itemDO = fProjectContractItemDAO.findById(order.getId());
				FProjectContractDO contractDO = fProjectContractDAO.findById(itemDO.getContractId());
				ProjectDO projectDO = projectDAO.findByProjectCode(contractDO.getProjectCode());
				itemDO.setSignPersonAId(order.getSignPersonAId());
				itemDO.setSignPersonBId(order.getSignPersonBId());
				itemDO.setSignedTime(order.getSignedTime());
				itemDO.setSignPersonA(order.getSignPersonA());
				itemDO.setSignPersonB(order.getSignPersonB());
				itemDO.setContractScanUrl(order.getContractScanUrl());
				itemDO.setReturnRemark(order.getReturnRemark());
				itemDO.setReturnAddTime(now);
				if (itemDO.getIsMain() != null && itemDO.getIsMain().equals("IS")) {//主合同维护合同金额
					if(ListUtil.isNotEmpty(order.getChannels())){//更新渠道合同金额 银团类
						for(ProjectContractChannelOrder channelOrder:order.getChannels()){
							ProjectChannelRelationDO channelRelationDO=projectChannelRelationDAO.findById(channelOrder.getChannelId());
							Money channelContractAmountTotal=Money.zero();
							channelRelationDO.setLiquidityLoansAmount(channelOrder.getLiquidityLoansAmount());
							channelRelationDO.setAcceptanceBillAmount(channelOrder.getAcceptanceBillAmount());
							channelRelationDO.setFinancialAmount(channelOrder.getFinancialAmount());
							channelRelationDO.setCreditAmount(channelOrder.getCreditAmount());
							channelContractAmountTotal=channelContractAmountTotal.add(channelOrder.getLiquidityLoansAmount())
									.add(channelOrder.getAcceptanceBillAmount())
									.add(channelOrder.getFinancialAmount())
									.add(channelOrder.getCreditAmount());
							channelRelationDO.setContractAmount(channelContractAmountTotal);
							projectChannelRelationDAO.update(channelRelationDO);
						}
					}else{//非银团类
						ProjectChannelRelationAmountOrder amountOrder=new ProjectChannelRelationAmountOrder();
						amountOrder.setProjectCode(projectDO.getProjectCode());
						amountOrder.setContractAmount(order.getContractAmount());
						FcsBaseResult result=projectChannelRelationService.updateRelatedAmount(amountOrder);
						if (!result.isSuccess()) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE,
									"更新合同金额到项目渠道表出错");
						}
					}
					itemDO.setContractAmount(order.getContractAmount());
				}
				//				if (ProjectUtil.isBond(projectDO.getBusiType())) {//发债类更新批复字段
				//					itemDO.setApprovedAmount(order.getApprovedAmount());
				//					itemDO.setApprovedTime(order.getApprovedTime());
				//					itemDO.setApprovedUrl(order.getApprovedUrl());
				//				}
				itemDO.setContractStatus(ContractStatusEnum.RETURN.code());
				fProjectContractItemDAO.update(itemDO);
				//保存附件
				if (StringUtil.isNotEmpty(order.getContractScanUrl())) {
					CommonAttachmentBatchOrder attachOrder = new CommonAttachmentBatchOrder();
					attachOrder.setProjectCode(contractDO.getProjectCode());
					attachOrder.setBizNo("PM_" + contractDO.getFormId() + "_" + itemDO.getId()
											+ "_HCWJ");
					attachOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
					attachOrder.setModuleType(CommonAttachmentTypeEnum.CONTRACT_SCANURL);
					if (order.getUserId() != null)
						attachOrder.setUploaderId(order.getUserId());
					attachOrder.setUploaderName(order.getUserName());
					attachOrder.setUploaderAccount(order.getUserAccount());
					attachOrder.setPath(order.getContractScanUrl());
					attachOrder.setRemark(ProjectContractTypeEnum.getByCode(
						contractDO.getApplyType()).message()
											+ "(" + itemDO.getContractName() + ")回传文件");
					commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
				}
				if (itemDO.getIsMain() != null && itemDO.getIsMain().equals("IS")) {//如果是主合同，更新到project表里去
					boolean isGuarantee = ProjectUtil.isGuarantee(projectDO.getBusiType());//是否担保业务
					boolean isBond = ProjectUtil.isBond(projectDO.getBusiType());//发债业务是担保的一种
					boolean isLitigation = ProjectUtil.isLitigation(projectDO.getBusiType());//是否诉讼业务
					boolean isUnderwriting = ProjectUtil.isUnderwriting(projectDO.getBusiType());//承销业务
					if (projectDO.getPhases().equals(ProjectPhasesEnum.CONTRACT_PHASES.code())) {
						if (isBond || isUnderwriting) {
							projectDO.setPhases(ProjectPhasesEnum.FUND_RAISING_PHASES.code());
							projectDO.setPhasesStatus(ProjectPhasesStatusEnum.WAITING.code());
						}
						//						else if (isLitigation) {
						//							projectDO.setPhases(ProjectPhasesEnum.AFTERWARDS_PHASES.code());
						//							projectDO.setPhasesStatus(ProjectPhasesStatusEnum.WAITING.code());
						//						}
					}
					projectDO.setContractNo(itemDO.getContractCode());
					projectDO.setContractTime(itemDO.getSignedTime());
					projectDO.setContractAmount(itemDO.getContractAmount());
					//					if (isLitigation) {
					//						MessageAlertOrder order = new MessageAlertOrder();
					//						order.setProjectCode(projectDO.getProjectCode());
					//						order.setCustomerName(projectDO.getCustomerName());
					//						order.setAlertPhrase(ProjectPhasesEnum.AFTERWARDS_PHASES.code());
					//						messageAlertService.add(order);
					//						FcsPmDomainHolder.get()
					//							.addAttribute("isLitigation", BooleanEnum.YES.code());
					//						FcsPmDomainHolder.get().addAttribute("projectCode",
					//							projectDO.getProjectCode());
					//						//可解保金额为授信金额
					//						projectDO.setReleasableAmount(projectDO.getAmount());
					//						//诉讼保函项目把合同签订时间写带project表里的startTime
					//						projectDO.setStartTime(itemDO.getSignedTime());
					//					}
					projectDAO.update(projectDO);

				}
				return null;
			}
		}, null, null);
	}

	@Override
	public FcsBaseResult saveCourtRuling(final ProjectContractItemOrder order) {
		return commonProcess(order, "上传法务裁定书", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				final Date now = FcsPmDomainHolder.get().getSysDate();
				FProjectContractItemDO itemDO = fProjectContractItemDAO.findById(order.getId());
				FProjectContractDO contractDO = fProjectContractDAO.findById(itemDO.getContractId());
				ProjectDO projectDO = projectDAO.findByProjectCode(contractDO.getProjectCode());
				itemDO.setCourtRulingUrl(order.getCourtRulingUrl());
				itemDO.setCourtRulingTime(order.getCourtRulingTime());
				itemDO.setCourtRulingAddTime(now);
				fProjectContractItemDAO.update(itemDO);
				//保存附件
				if (StringUtil.isNotEmpty(order.getCourtRulingUrl())) {
					CommonAttachmentBatchOrder attachOrder = new CommonAttachmentBatchOrder();
					attachOrder.setProjectCode(contractDO.getProjectCode());
					attachOrder.setBizNo("PM_" + contractDO.getFormId() + "_" + itemDO.getId()
											+ "_CDS");
					attachOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
					attachOrder.setModuleType(CommonAttachmentTypeEnum.CONTRACT_COURT_RULINGURL);
					attachOrder.setUploaderId(order.getUserId());
					attachOrder.setUploaderName(order.getUserName());
					attachOrder.setUploaderAccount(order.getUserAccount());
					attachOrder.setPath(order.getCourtRulingUrl());
					attachOrder.setRemark(ProjectContractTypeEnum.getByCode(
						contractDO.getApplyType()).message()
											+ "(" + itemDO.getContractName() + ")法院裁定书");
					commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
				}
				boolean isGuarantee = ProjectUtil.isGuarantee(projectDO.getBusiType());//是否担保业务
				boolean isBond = ProjectUtil.isBond(projectDO.getBusiType());//发债业务是担保的一种
				boolean isLitigation = ProjectUtil.isLitigation(projectDO.getBusiType());//是否诉讼业务
				boolean isUnderwriting = ProjectUtil.isUnderwriting(projectDO.getBusiType());//承销业务
				if (projectDO.getPhases().equals(ProjectPhasesEnum.CONTRACT_PHASES.code())) {
					if (isLitigation&&!StringUtil.equals(projectDO.getIsRedoProject(),"IS")) {
						projectDO.setPhases(ProjectPhasesEnum.AFTERWARDS_PHASES.code());
						projectDO.setPhasesStatus(ProjectPhasesStatusEnum.WAITING.code());
					}
				}
				if (isLitigation&&!StringUtil.equals(projectDO.getIsRedoProject(),"IS")) {
					MessageAlertOrder order = new MessageAlertOrder();
					order.setProjectCode(projectDO.getProjectCode());
					order.setCustomerName(projectDO.getCustomerName());
					order.setAlertPhrase(ProjectPhasesEnum.AFTERWARDS_PHASES.code());
					messageAlertService.add(order);
					FcsPmDomainHolder.get().addAttribute("isLitigation", BooleanEnum.YES.code());
					FcsPmDomainHolder.get().addAttribute("projectCode", projectDO.getProjectCode());
					//可解保金额为授信金额
					projectDO.setReleasableAmount(projectDO.getContractAmount());
					//						//诉讼保函项目把裁定书出具时间写带project表里的startTime
					projectDO.setStartTime(itemDO.getCourtRulingTime());
					projectDO.setIsCourtRuling(BooleanEnum.IS.code());
					projectDO.setCourtRulingTime(itemDO.getCourtRulingTime());
				}
				projectDAO.update(projectDO);
				
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				Object obj = FcsPmDomainHolder.get().getAttribute("isLitigation");
				if (null != obj && StringUtil.equals(BooleanEnum.YES.code(), obj.toString())) {
					//诉保的，上传法院裁定书完成后，进入可解保
					String projectCode = FcsPmDomainHolder.get().getAttribute("projectCode")
							.toString();
					releaseProjectService.add(projectCode);
					ProjectDO project = projectDAO.findByProjectCode(projectCode);
					if (null != project.getRightVoucherExtenDate()) {
						project.setRightVoucherExtenDate(null);
						projectDAO.update(project);
					}
				}
				return null;
			}
		});
	}
	
	@Override
	public ProjectContractItemInfo findContractItemById(long id) {
		ProjectContractItemInfo info = null;
		if (id > 0) {
			FProjectContractItemDO DO = fProjectContractItemDAO.findById(id);
			info = convertDO2Info(DO);
		}
		return info;
	}
	
	@Override
	public ProjectContrctInfo findByFormId(long formId) {
		ProjectContrctInfo info = null;
		List<FProjectContractItemDO> itemDO = null;
		List<ProjectContractItemInfo> itemInfo = Lists.newArrayList();
		if (formId > 0) {
			FProjectContractDO DO = fProjectContractDAO.findByFormId(formId);
			itemDO = fProjectContractItemDAO.findByFormContractId(DO.getContractId());
			info = convertDO2Info(DO);
		}
		if (itemDO.size() > 0) {
			for (FProjectContractItemDO DO2 : itemDO) {
				itemInfo.add(convertDO2Info(DO2));
			}
		}
		info.setProjectContractItemInfos(itemInfo);
		return info;
	}
	
	@Override
	public FcsBaseResult updateContractStatus(final ProjectContractItemOrder order) {
		return commonProcess(order, "更新合同状态", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				if (order.getContractStatus().equals(ContractStatusEnum.CONFIRMED.code())) {
					if(StringUtil.equals("IS",order.getIsContract())){//合同更新
						String idStatuss[] = order.getIds().split(";");
						for(String idStatus:idStatuss){
							String ids[] = idStatus.split(",");
							String id = ids[0];
							String isneedStamp=ids[1];
							if (StringUtil.isNotBlank(id)) {
								FProjectContractItemDO itemDO = fProjectContractItemDAO.findById(Long
										.parseLong(id));
								itemDO.setContractStatus(order.getContractStatus());
								itemDO.setIsNeedStamp(isneedStamp);
								fProjectContractItemDAO.update(itemDO);
							}
						}
					}else {
						String ids[] = order.getIds().split(",");
						for (String id : ids) {
							if (StringUtil.isNotBlank(id)) {
								FProjectContractItemDO itemDO = fProjectContractItemDAO.findById(Long
										.parseLong(id));
								itemDO.setContractStatus(order.getContractStatus());
								fProjectContractItemDAO.update(itemDO);
							}
						}
					}
				} else {
					FProjectContractItemDO itemDO = fProjectContractItemDAO.findById(order.getId());
					FProjectContractDO contractDO =	fProjectContractDAO.findById(itemDO.getContractId());
					ProjectInfo projectInfo=projectService.queryByCode(contractDO.getProjectCode(),false);
					if (order.getContractStatus().equals(ContractStatusEnum.INVALID.code())) {
//						if(!ProjectUtil.isUnderwriting(projectInfo.getBusiType())){//承销业务不做限制
//							if(ProjectUtil.isLitigation(projectInfo.getBusiType())){//诉保在上传法律裁定书之前可以作废合同
//								if(projectInfo.getIsCourtRuling()==BooleanEnum.IS){
//									throw ExceptionFactory
//											.newFcsException(FcsResultEnum.EXECUTE_FAILURE, "该项目已上传法院裁定书，不能作废!");
//								}
//							}else{
//								//已回传未放款作废合同校验 放款申请单中是否有提交过的放用款申请单
//								LoanUseApplyQueryOrder queryOrder=new LoanUseApplyQueryOrder();
//								queryOrder.setProjectCode(contractDO.getProjectCode());
//								QueryBaseBatchResult<LoanUseApplyFormInfo> loanUseApplyList=loanUseApplyService.searchApplyForm(queryOrder);
//								if(ListUtil.isNotEmpty(loanUseApplyList.getPageList())){
//									throw ExceptionFactory
//											.newFcsException(FcsResultEnum.EXECUTE_FAILURE, "该项目已申请过放用款申请单，不能作废!");
//								}
//							}
//						}

						itemDO.setInvalidReason(order.getInvalidReason());
					}
					itemDO.setContractStatus(order.getContractStatus());
					fProjectContractItemDAO.update(itemDO);
					FcsPmDomainHolder.get().addAttribute("contractItem", itemDO);
				}
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				//合同作废发送消息
				FProjectContractItemDO contractItem = (FProjectContractItemDO) FcsPmDomainHolder
					.get().getAttribute("contractItem");
				if (contractItem != null
					&& StringUtil.equals(contractItem.getContractStatus(),
						ContractStatusEnum.INVALID.code())) {
					
					String gzadmin = sysParameterService
						.getSysParameterValue(SysParamEnum.SYS_PARAM_GZ_ROLE_NAME.code());
					List<SysUser> gzList = bpmUserQueryService.findUserByRoleAlias(gzadmin);
					String frzadmin = sysParameterService
						.getSysParameterValue(SysParamEnum.SYS_PARAM_FRZ_ROLE_NAME.code());
					List<SysUser> frzList = bpmUserQueryService.findUserByRoleAlias(frzadmin);
					String gzfrzadmin = sysParameterService
						.getSysParameterValue(SysParamEnum.SYS_PARAM_GZFRZ_ROLE_NAME.code());
					List<SysUser> gzfrzList = bpmUserQueryService.findUserByRoleAlias(gzfrzadmin);
					
					if (ListUtil.isNotEmpty(gzList) || ListUtil.isNotEmpty(frzList)
						|| ListUtil.isNotEmpty(gzfrzList)) {
						
						//去重
						Map<Long, SysUser> userMap = Maps.newHashMap();
						
						if (gzList != null) {
							for (SysUser user : gzList) {
								userMap.put(user.getUserId(), user);
							}
						}
						if (frzList != null) {
							for (SysUser user : frzList) {
								userMap.put(user.getUserId(), user);
							}
						}
						if (gzfrzList != null) {
							for (SysUser user : gzfrzList) {
								userMap.put(user.getUserId(), user);
							}
						}
						SimpleUserInfo[] sendUsers = new SimpleUserInfo[userMap.size()];
						int i = 0;
						for (long userId : userMap.keySet()) {
							SysUser user = userMap.get(userId);
							SimpleUserInfo sUser = new SimpleUserInfo();
							sUser.setUserId(user.getUserId());
							sUser.setUserName(user.getFullname());
							sUser.setUserAccount(user.getAccount());
							sendUsers[i] = sUser;
							i++;
						}
						
						FProjectContractDO contract = fProjectContractDAO.findById(contractItem
							.getContractId());
						if (contract != null) {
							MessageOrder order = MessageOrder.newSystemMessageOrder();
							String messageContent = "项目：" + contract.getProjectName() + "，项目编号："
													+ contract.getProjectCode() + "有合同作废，合同编号："
													+ contractItem.getContractCode() + "请知晓！";
							order.setMessageSubject("合同作废提醒");
							order.setMessageTitle("合同作废提醒");
							order.setMessageContent(messageContent);
							order.setSendUsers(sendUsers);
							order.setWithSenderName(true);
							siteMessageService.addMessageInfo(order);
						}
					}
				}
				return null;
			}
		});
	}
	
	@Override
	public long checkISConfirmAll(String projectCode) {
		long count = extraDAO.isConfirmAllCount(projectCode);
		return count;
	}
	
	@Override
	public List<ProjectContractResultInfo> searchIsConfirmAll(String projectCode) {
		List<ProjectContractResultInfo> info = Lists.newArrayList();
		List<ProjectContractFormDO> DO = extraDAO.searchIsConfirmAll(projectCode);
		for (ProjectContractFormDO tempDO : DO) {
			if (isEnoughMoney(tempDO)) {
				info.add(convertDO2Info(tempDO));
			}
		}
		return info;
	}
	
	@Override
	public ProjectContractCheckMessageResult findCheckMessageByContractItemId(	ProjectContractItemOrder order) {
		ProjectContractCheckMessageResult result = new ProjectContractCheckMessageResult();
		// 可能要判断userid 来执行结果集的编排，userid单独放入
		if (order.getId() <= 0) {
			result.setFcsResultEnum(FcsResultEnum.ILLEGAL_SIGN);
			result.setMessage("合同id不能为空！");
			return result;
		}
		List<FProjectContractCheckDO> checkInfoDOs = fProjectContractCheckDAO
			.findByContractItemId(order.getId());
		for (FProjectContractCheckDO checkInfoDO : checkInfoDOs) {
			ProjectContractCheckInfo checkInfo = new ProjectContractCheckInfo();
			MiscUtil.copyPoObject(checkInfo, checkInfoDO);
			result.getAllCheckMessage().add(checkInfo);
			if (checkInfoDO.getUserId() == order.getUserId()) {
				// 相等代表是自己的。存入owner list
				result.getOwnerCheckMessage().add(checkInfo);
			} else {
				// 否则存入other list
				result.getOthersCheckMessage().add(checkInfo);
			}
		}
		// 查询历史修改记录
		List<FProjectContractExtraValueModifyDO> modifyInfoDOs = fProjectContractExtraValueModifyDAO
			.findByItemId(order.getId());
		List<ProjectContractExtraValueModifyInfo> modifyInfos = new ArrayList<ProjectContractExtraValueModifyInfo>();
		if (modifyInfoDOs != null) {
			for (FProjectContractExtraValueModifyDO infoDO : modifyInfoDOs) {
				ProjectContractExtraValueModifyInfo info = new ProjectContractExtraValueModifyInfo();
				MiscUtil.copyPoObject(info, infoDO);
				modifyInfos.add(info);
			}
		}
		result.setModifyInfos(modifyInfos);
		result.setSuccess(true);
		return result;
	}
	
	/**
	 * 当系统识别到合同名称为“诉讼保全担保函”时，去判断批复中的收费方式为先收还是后收，
	 * 如果先收，则需要判断系统中审核通过的收费通知书是否足额收取了担保费和保证金
	 * @param DO
	 * @return
	 */
	private boolean isEnoughMoney(ProjectContractFormDO DO) {
		if (ProjectUtil.isLitigation(DO.getBusiType()) && DO.getContractName() != null
			&& DO.getContractName().equals("诉讼保全担保函")) {
			ProjectInfo projectInfo = projectService.queryByCode(DO.getProjectCode(), false);
			FCouncilSummaryProjectLgLitigationInfo lgLitigationInfo = councilSummaryService
				.queryLgLitigationProjectCsByProjectCode(DO.getProjectCode(), true);
			if (lgLitigationInfo != null
				&& lgLitigationInfo.getChargePhase() == ChargePhaseEnum.BEFORE) {
				//担保费
				Money guaranteeFee = ZERO_MONEY;
				//保证金
				Money deposit = ZERO_MONEY;
				if (projectInfo.getContractAmount().equals(ZERO_MONEY)) {
					if (lgLitigationInfo.getGuaranteeFeeType() == ChargeTypeEnum.PERCENT) {
						guaranteeFee = lgLitigationInfo.getAmount().multiply(
							lgLitigationInfo.getGuaranteeFee() / 100);
					} else {
						guaranteeFee = new Money(lgLitigationInfo.getGuaranteeFee());
					}
					if (lgLitigationInfo.getDepositType() == ChargeTypeEnum.PERCENT) {
						deposit = lgLitigationInfo.getAmount().multiply(
							lgLitigationInfo.getDeposit() / 100);
					} else {
						deposit = new Money(lgLitigationInfo.getDeposit());
					}
				} else {//函、通知书项目里如果没有合同金额就按以前的方式，如果有合同金额就以合同金额为标准
					if (lgLitigationInfo.getGuaranteeFeeType() == ChargeTypeEnum.PERCENT) {
						guaranteeFee = projectInfo.getContractAmount().multiply(
							lgLitigationInfo.getGuaranteeFee() / 100);
					} else {
						guaranteeFee = new Money(lgLitigationInfo.getGuaranteeFee());
					}
					if (lgLitigationInfo.getDepositType() == ChargeTypeEnum.PERCENT) {
						deposit = projectInfo.getContractAmount().multiply(
							lgLitigationInfo.getDeposit() / 100);
					} else {
						deposit = new Money(lgLitigationInfo.getDeposit());
					}
				}
				//查收费通知单里的担保费、存入保证金
				Money chargeGuaranteeFee = chargeNotificationService.queryChargeTotalAmount(
					DO.getProjectCode(), FeeTypeEnum.GUARANTEE_FEE).getOther();
				Money chargedeposit = chargeNotificationService.queryChargeTotalAmount(
					DO.getProjectCode(), FeeTypeEnum.GUARANTEE_DEPOSIT).getOther();
				if ((chargeGuaranteeFee.greaterThan(guaranteeFee) || chargeGuaranteeFee
					.equals(guaranteeFee))
					&& (chargedeposit.greaterThan(deposit) || chargedeposit.equals(deposit))) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
	
	/**
	 * 获取站外访问密钥
	 * @param userInfo
	 * @return
	 */
	protected String getAccessToken(SimpleUserInfo userInfo) {
		SysWebAccessTokenOrder tokenOrder = new SysWebAccessTokenOrder();
		BeanCopier.staticCopy(userInfo, tokenOrder);
		tokenOrder.setRemark("站外访问链接");
		FcsBaseResult tokenResult = sysWebAccessTokenService.addUserAccessToken(tokenOrder);
		if (tokenResult != null && tokenResult.isSuccess()) {
			return tokenResult.getUrl();
		} else {
			return "";
		}
	}
	
	/**
	 * 更新非制式合同 子合同url
	 * @param order
	 * @return
	 * @see com.born.fcs.pm.ws.service.contract.ProjectContractService#updateComtractItemUrl(com.born.fcs.pm.ws.order.contract.ProjectContractItemOrder)
	 */
	@Override
	public FcsBaseResult updateComtractItemUrl(ProjectContractItemOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			if (order == null || order.getId() == null) {
				result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
				result.setMessage("子合同id不能为空！");
				return result;
			}
			FProjectContractItemDO itemDO = fProjectContractItemDAO.findById(order.getId());
			if (itemDO == null) {
				result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
				result.setMessage("未查询到数据！");
				return result;
			}
			itemDO.setFileUrl(order.getFileUrl());
			fProjectContractItemDAO.update(itemDO);
			result.setSuccess(true);
			
		} catch (Exception e) {
			logger.error("更新子合同url失败，原因：" + e.getMessage(), e);
		}
		return result;
	}
	
	private void saveCommonAttachment(ProjectContractOrder order,
										ProjectContractItemOrder itemOrder, long formId,
										long detailId) {
		//保存附件
		CommonAttachmentBatchOrder attachOrder = new CommonAttachmentBatchOrder();
		attachOrder.setProjectCode(order.getProjectCode());
		attachOrder.setBizNo("PM_" + formId + "_" + detailId + "_CKFJ");
		attachOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
		attachOrder.setModuleType(CommonAttachmentTypeEnum.CONTRACT_REFER);
		attachOrder.setUploaderId(order.getUserId());
		attachOrder.setUploaderName(order.getUserName());
		attachOrder.setUploaderAccount(order.getUserAccount());
		if (StringUtil.isNotEmpty(itemOrder.getReferAttachment())) {
			attachOrder.setPath(itemOrder.getReferAttachment());
			attachOrder.setRemark(order.getApplyType().message() + "("
									+ itemOrder.getContractName() + ")参考附件");
			commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
		}
		if (StringUtil.isNotEmpty(itemOrder.getFileUrl())) {
			attachOrder.setBizNo("PM_" + formId + "_" + detailId + "_HT");
			attachOrder.setPath(itemOrder.getFileUrl());
			attachOrder.setRemark(order.getApplyType().message() + "("
									+ itemOrder.getContractName() + ")");
			attachOrder.setModuleType(CommonAttachmentTypeEnum.CONTRACT_REFER_ATTACHMENT);
			commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
		}
	}

	@Override
	public ProjectContractItemInvalidInfo findContractInvalidById(long id) {
		ProjectContractItemInvalidInfo info=null;
		FProjectContractItemInvalidDO DO=fProjectContractItemInvalidDAO.findById(id);
		if(DO!=null){
			info=new ProjectContractItemInvalidInfo();
			BeanCopier.staticCopy(DO, info);
		}
		return info;
	}

	@Override
	public ProjectContractItemInvalidInfo findContractInvalidByFormId(long formId) {
		ProjectContractItemInvalidInfo info=null;
		FProjectContractItemInvalidDO DO=fProjectContractItemInvalidDAO.findByFormId(formId);
		if(DO!=null){
			info=new ProjectContractItemInvalidInfo();
			BeanCopier.staticCopy(DO, info);
		}
		return info;
	}

	@Override
	public FormBaseResult saveContractInvalid(final ProjectContractItemInvalidOrder order) {
		return commonFormSaveProcess(order, "合同作废", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				if (order.getId() <= 0) {
					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					final Date now = FcsPmDomainHolder.get().getSysDate();
					//保存
					FProjectContractItemInvalidDO invalidDO = new FProjectContractItemInvalidDO();
					BeanCopier.staticCopy(order, invalidDO, UnBoxingConverter.getInstance());
					invalidDO.setFormId(formInfo.getFormId());
					invalidDO.setRawAddTime(now);
					fProjectContractItemInvalidDAO.insert(invalidDO);
					//更新合同状态
					FProjectContractItemDO itemDO=fProjectContractItemDAO.findByContractCode(order.getContractCode());
					if(itemDO!=null){
						itemDO.setContractStatus(ContractStatusEnum.INVALIDING.code());
						fProjectContractItemDAO.update(itemDO);
					}
				} else {
					//更新
					FProjectContractItemInvalidDO invalidDO = fProjectContractItemInvalidDAO.findById(order.getId());
					if (null == invalidDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"未找到合同作废记录");
					}
					BeanCopier.staticCopy(order, invalidDO, UnBoxingConverter.getInstance());
					fProjectContractItemInvalidDAO.update(invalidDO);
				}
				return null;
			}
		}, null, null);
	}


	@Override
	public QueryBaseBatchResult<ProjectContractInvalidResultInfo> queryInvalidList(ProjectContractQueryOrder order) {
		QueryBaseBatchResult<ProjectContractInvalidResultInfo> baseBatchResult = new QueryBaseBatchResult<ProjectContractInvalidResultInfo>();

		ProjectContractFormDO queryCondition = new ProjectContractFormDO();
		Date applyTimeStart = null;
		Date applyTimeEnd = null;
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		if (order.getOperateDate() != null) {
			applyTimeStart = DateUtil.getStartTimeOfTheDate(order.getOperateDate());
			applyTimeEnd = DateUtil.getEndTimeOfTheDate(order.getOperateDate());
		}
		queryCondition.setDeptIdList(order.getDeptIdList());
		long totalSize = extraDAO.searchProjectContractInvalidListCount(queryCondition, applyTimeStart,
				applyTimeEnd);

		PageComponent component = new PageComponent(order, totalSize);
		List<ProjectContractFormDO> pageList = extraDAO.searchProjectContractInvalidList(queryCondition,
				component.getFirstRecord(), component.getPageSize(), applyTimeStart, applyTimeEnd,
				order.getSortCol(), order.getSortOrder());
		List<ProjectContractInvalidResultInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (ProjectContractFormDO DO : pageList) {
				ProjectContractInvalidResultInfo info = new ProjectContractInvalidResultInfo();
				BeanCopier.staticCopy(DO, info);
				info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
				list.add(info);
			}
		}

		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}

	@Override
	public FcsBaseResult modfiyCnt(ProjectContractOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			if (ListUtil.isNotEmpty(order.getItem())) {
				for(ProjectContractItemOrder itemOrder:order.getItem()){
					FProjectContractItemDO itemDO = fProjectContractItemDAO.findById(itemOrder.getId());
					if (itemDO == null) {
						result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
						result.setMessage("未查询到数据！");
						return result;
					}
					itemDO.setCnt(itemOrder.getCnt());
					fProjectContractItemDAO.update(itemDO);
				}
			}
			result.setSuccess(true);

		} catch (Exception e) {
			logger.error("更新子合同份数失败，原因：" + e.getMessage(), e);
		}
		return result;
	}

}
