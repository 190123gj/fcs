package com.born.fcs.pm.biz.service.projectcreditcondition;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.born.fcs.pm.ws.info.projectcreditcondition.*;
import com.born.fcs.pm.ws.service.report.PmReportService;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.daointerface.ProjectCreditAssetAttachmentDAO;
import com.born.fcs.pm.dal.dataobject.FCreditConditionConfirmDO;
import com.born.fcs.pm.dal.dataobject.FCreditConditionConfirmItemDO;
import com.born.fcs.pm.dal.dataobject.ProjectCreditAssetAttachmentDO;
import com.born.fcs.pm.dal.dataobject.ProjectCreditConditionDO;
import com.born.fcs.pm.dal.dataobject.ProjectCreditMarginDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dataobject.ProjectCreditConditionFormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.CreditCheckStatusEnum;
import com.born.fcs.pm.ws.enums.CreditConditionTypeEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.ReleaseStatusEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.order.common.CommonAttachmentBatchOrder;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.counterguarantee.CreditConditionReleaseOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.FCreditConditionConfirmOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.FCreditConditionConfirmQueryOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditAssetAttachmentOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;
import com.born.fcs.pm.ws.service.projectcreditcondition.ProjectCreditConditionService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("projectCreditConditionService")
public class ProjectCreditConditionServiceImpl extends BaseFormAutowiredDomainService implements
																						ProjectCreditConditionService {
	ProjectCreditConditionServiceImpl projectCreditionServiceImpl;
	@Autowired
	ProjectCreditAssetAttachmentDAO projectCreditAssetAttachmentDAO;
	@Autowired
	CommonAttachmentService commonAttachmentService;
	@Autowired
	PmReportService pmReportService;
	
	private ProjectCreditConditionInfo convertDO2Info(ProjectCreditConditionDO DO) {
		if (DO == null)
			return null;
		ProjectCreditConditionInfo info = new ProjectCreditConditionInfo();
		BeanCopier.staticCopy(DO, info);
		info.setType(CreditConditionTypeEnum.getByCode(DO.getType()));
		info.setStatus(CreditCheckStatusEnum.getByCode(DO.getStatus()));
		info.setReleaseStatus(ReleaseStatusEnum.getByCode(DO.getReleaseStatus()));
		info.setIsConfirm(BooleanEnum.getByCode(DO.getIsConfirm()));
		return info;
	}
	
	private FCreditConditionConfirmInfo covertFCreditConditionConfirmDO2Info(	FCreditConditionConfirmDO DO) {
		if (DO == null)
			return null;
		FCreditConditionConfirmInfo info = new FCreditConditionConfirmInfo();
		BeanCopier.staticCopy(DO, info);
		//info.setCouncil(councilService.queryCouncilById(DO.getCouncilId()));
		info.setCustomerType(CustomerTypeEnum.getByCode(DO.getCustomerType()));
		info.setTimeUnit(TimeUnitEnum.getByCode(DO.getTimeUnit()));
		info.setIsMargin(BooleanEnum.getByCode(DO.getIsMargin()));
		return info;
	}
	
	private FCreditConditionConfirmItemInfo convertFCreditConditionConfirmItemDO2Info(	FCreditConditionConfirmItemDO DO) {
		if (DO == null)
			return null;
		FCreditConditionConfirmItemInfo info = new FCreditConditionConfirmItemInfo();
		BeanCopier.staticCopy(DO, info);
		info.setType(CreditConditionTypeEnum.getByCode(DO.getType()));
		info.setStatus(CreditCheckStatusEnum.getByCode(DO.getStatus()));
		info.setIsConfirm(BooleanEnum.getByCode(DO.getIsConfirm()));
		return info;
	}
	
	private ProjectCreditAssetAttachmentInfo convertDO2AttachmentInfo(	ProjectCreditAssetAttachmentDO DO) {
		if (DO == null)
			return null;
		ProjectCreditAssetAttachmentInfo info = new ProjectCreditAssetAttachmentInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	private ProjectCreditMarginInfo convertDO2MarginInfo(ProjectCreditMarginDO DO) {
		if (DO == null)
			return null;
		ProjectCreditMarginInfo info = new ProjectCreditMarginInfo();
		BeanCopier.staticCopy(DO, info);
		info.setIsFrozen(BooleanEnum.getByCode(DO.getIsFrozen()));
		info.setIsPledge(BooleanEnum.getByCode(DO.getIsPledge()));
		info.setPeriodUnit(TimeUnitEnum.getByCode(DO.getPeriodUnit()));
		return info;
	}
	
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
	
	@Override
	public ProjectCreditConditionInfo findById(long confirmId) {
		ProjectCreditConditionDO DO = projectCreditConditionDAO.findById(confirmId);
		if(DO==null){
			return null;
		}
		ProjectCreditConditionInfo info = new ProjectCreditConditionInfo();
		BeanCopier.staticCopy(DO, info);
		info.setType(CreditConditionTypeEnum.getByCode(DO.getType()));
		return info;
	}
	
	@Override
	public FormBaseResult saveProjectCreditCondition(final FCreditConditionConfirmOrder order) {
		return commonFormSaveProcess(order, "授信条件落实情况", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				if (order.getConfirmId() == null || order.getConfirmId() <= 0) {
					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					final Date now = FcsPmDomainHolder.get().getSysDate();
					//保存
					FCreditConditionConfirmDO fCreditConditionConfirmDO = new FCreditConditionConfirmDO();
					BeanCopier.staticCopy(order, fCreditConditionConfirmDO);
					fCreditConditionConfirmDO.setFormId(formInfo.getFormId());
					fCreditConditionConfirmDO.setRawAddTime(now);
					fCreditConditionConfirmDO.setAmount(new Money(order.getAmount()));
					Long id = FCreditConditionConfirmDAO.insert(fCreditConditionConfirmDO);//主表id
					if (order.getIsMargin() != null
						&& order.getIsMargin().equals(BooleanEnum.IS.code())) {
						//将保证金信息写入保证金落实信息表
						ProjectCreditMarginDO marginDO = new ProjectCreditMarginDO();
						BeanCopier.staticCopy(order.getProjectCreditMarginOrder(), marginDO);
						
						marginDO.setCreditId(order.getProjectCreditMarginOrder().getCreditId());
						marginDO.setConfirmId(id);
						marginDO.setProjectCode(order.getProjectCode());
						marginDO.setRawAddTime(now);
						projectCreditMarginDAO.insert(marginDO);
					}
					List<ProjectCreditConditionOrder> conditionOrders = order
						.getProjectCreditConditionOrders();
					String creditIds = "";
					Long userId = order.getUserId();
					String userName = order.getUserName();
					String userAccount = order.getUserAccount();
					if (ListUtil.isNotEmpty(conditionOrders)) {
						for (ProjectCreditConditionOrder projectCreditConditionOrder : conditionOrders) {
							projectCreditConditionOrder.setProjectCode(order.getProjectCode());
							if (projectCreditConditionOrder.getIsConfirm() == null) {
								projectCreditConditionOrder.setIsConfirm("NO");
							}
							//							if (projectCreditConditionOrder.getIsConfirm().equals("IS")) {
							creditIds += projectCreditConditionOrder.getId() + ",";
							//							}
							projectCreditConditionOrder.setUserId(userId);
							projectCreditConditionOrder.setUserName(userName);
							projectCreditConditionOrder.setUserAccount(userAccount);
						}
						creditIds = creditIds.substring(0, creditIds.length() - 1);
					}
					
					if (order.getProjectCreditMarginOrder() != null) {
						order.getProjectCreditMarginOrder().setCreditId(creditIds);
					}
					//					order.setProjectCreditConditionOrders(conditionOrders);
					
					if (ListUtil.isNotEmpty(conditionOrders)) {
						if (order.getConfirmId() != null && order.getConfirmId() > 0) {
							FCreditConditionConfirmItemDAO.deleteByConfirmId(order.getConfirmId());
						}
						for (ProjectCreditConditionOrder projectCreditConditionOrder : conditionOrders) {
							projectCreditConditionOrder.setCheckStatus(0);
							projectCreditConditionOrder.setCheckIndex(0);
							if (id != 0) {
								projectCreditConditionOrder.setConfirmId(id);
							}
							
							saveFCreditConditionConfirmItem(projectCreditConditionOrder);
							
						}
					}
					
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(id);
				} else {
					//更新
					//					String code = order.getProjectCode();
					Long id = order.getConfirmId();
					FCreditConditionConfirmDO confirm = FCreditConditionConfirmDAO.findById(id);
					if (null == confirm) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到授信条件落实情况记录");
					}
					BeanCopier.staticCopy(order, confirm);
					confirm.setAmount(new Money(order.getAmount()));
					FCreditConditionConfirmDAO.update(confirm);
					List<ProjectCreditConditionOrder> conditionOrders = order
						.getProjectCreditConditionOrders();
					String creditIds = "";
					Long userId = order.getUserId();
					String userName = order.getUserName();
					String userAccount = order.getUserAccount();
					if (ListUtil.isNotEmpty(conditionOrders)) {
						for (ProjectCreditConditionOrder projectCreditConditionOrder : conditionOrders) {
							projectCreditConditionOrder.setProjectCode(order.getProjectCode());
							if (projectCreditConditionOrder.getIsConfirm() == null) {
								projectCreditConditionOrder.setIsConfirm("NO");
							}
							creditIds += projectCreditConditionOrder.getId() + ",";
							
							projectCreditConditionOrder.setUserId(userId);
							projectCreditConditionOrder.setUserName(userName);
							projectCreditConditionOrder.setUserAccount(userAccount);
						}
						creditIds = creditIds.substring(0, creditIds.length() - 1);
					}
					if (order.getProjectCreditMarginOrder() != null) {
						order.getProjectCreditMarginOrder().setCreditId(creditIds);
					}
					//					order.setProjectCreditConditionOrders(conditionOrders);
					
					if (ListUtil.isNotEmpty(conditionOrders)) {
						FCreditConditionConfirmItemDAO.deleteByConfirmId(id);
						for (ProjectCreditConditionOrder projectCreditConditionOrder : conditionOrders) {
							//							if (order.getCheckStatus() == 1) {//提交状态
							projectCreditConditionOrder.setCheckStatus(0);
							projectCreditConditionOrder.setCheckIndex(0);
							if (id != 0) {
								projectCreditConditionOrder.setConfirmId(id);
							}
							
							saveFCreditConditionConfirmItem(projectCreditConditionOrder);
						}
					}
					//将保证金信息写入保证金落实信息表
					if ("IS".equals(order.getIsMargin())) {
						ProjectCreditMarginDO marginDO = projectCreditMarginDAO.findByConfirmId(id);
						if (marginDO != null) {
							BeanCopier.staticCopy(order.getProjectCreditMarginOrder(), marginDO);
							marginDO.setCreditId(order.getProjectCreditMarginOrder().getCreditId());
							marginDO.setConfirmId(id);
							marginDO.setProjectCode(order.getProjectCode());
							projectCreditMarginDAO.update(marginDO);
						} else {
							ProjectCreditMarginDO marginDO1 = new ProjectCreditMarginDO();
							//将保证金信息写入保证金落实信息表
							BeanCopier.staticCopy(order.getProjectCreditMarginOrder(), marginDO1);
							marginDO1
								.setCreditId(order.getProjectCreditMarginOrder().getCreditId());
							marginDO1.setConfirmId(id);
							marginDO1.setProjectCode(order.getProjectCode());
							projectCreditMarginDAO.insert(marginDO1);
						}
					} else {
						projectCreditMarginDAO.deleteByConfirmId(id);
					}
				}
				FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<FCreditConditionConfirmInfo> query(	FCreditConditionConfirmQueryOrder order) {
		QueryBaseBatchResult<FCreditConditionConfirmInfo> baseBatchResult = new QueryBaseBatchResult<FCreditConditionConfirmInfo>();
		try {
			FCreditConditionConfirmDO projectCreditConditionDO = new FCreditConditionConfirmDO();
			BeanCopier.staticCopy(order, projectCreditConditionDO);
			
			long totalCount = extraDAO.searchCreditConditionCount(projectCreditConditionDO,
				order.getSubmitTimeStart(), order.getSubmitTimeEnd(), order.getDeptIdList());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<FCreditConditionConfirmDO> pageList = extraDAO.searchCreditConditionList(
				projectCreditConditionDO, component.getFirstRecord(), component.getPageSize(),
				order.getSubmitTimeStart(), order.getSubmitTimeEnd(), order.getDeptIdList(),
				order.getSortCol(), order.getSortOrder());
			
			List<FCreditConditionConfirmInfo> list = baseBatchResult.getPageList();
			if (totalCount > 0) {
				for (FCreditConditionConfirmDO sf : pageList) {
					FCreditConditionConfirmInfo info = new FCreditConditionConfirmInfo();
					//					info = covertFCreditConditionConfirmDO2Info(sf);
					//					LoanUseApplyFormInfo info = new LoanUseApplyFormInfo();
					BeanCopier.staticCopy(sf, info);
					info.setFormCode(FormCodeEnum.getByCode(sf.getFormCode()));
					info.setFormStatus(FormStatusEnum.getByCode(sf.getFormStatus()));
					info.setCustomerType(CustomerTypeEnum.getByCode(sf.getCustomerType()));
					info.setTimeUnit(TimeUnitEnum.getByCode(sf.getTimeUnit()));
					info.setProjectStatus(ProjectStatusEnum.getByCode(sf.getProjectStatus()));
					list.add(info);
				}
			}
			baseBatchResult.setSuccess(true);
			baseBatchResult.setPageList(list);
			baseBatchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询授信条件落实情况项目列表失败" + e.getMessage(), e);
			baseBatchResult.setSuccess(false);
			baseBatchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return baseBatchResult;
	}
	
	@Override
	public List<ProjectCreditConditionInfo> findProjectCreditConditionByProjectCode(String projectCode) {
		List<ProjectCreditConditionInfo> listProjectCreditConditionInfo = new ArrayList<ProjectCreditConditionInfo>();
		List<ProjectCreditConditionDO> listProjectCreditConditionDO = projectCreditConditionDAO
			.findByProjectCode(projectCode);
		for (ProjectCreditConditionDO projectCreditConditionDO : listProjectCreditConditionDO) {
			ProjectCreditConditionInfo projectCreditConditionInfo = convertDO2Info(projectCreditConditionDO);
			listProjectCreditConditionInfo.add(projectCreditConditionInfo);
		}
		return listProjectCreditConditionInfo;
	}
	
	//	@Override
	//	public ProjectCreditConditionInfo queryProjectCreditConditionByFormId(long formId) {
	//		FCreditConditionConfirmDO projectCreditConditionDO = FCreditConditionConfirmDAO
	//			.findByFormId(formId);
	//		ProjectCreditConditionInfo info = new ProjectCreditConditionInfo();
	//		BeanCopier.staticCopy(projectCreditConditionDO, info);
	//		return info;
	//	}
	
	@Override
	public FCreditConditionConfirmInfo queryFCreditConditionConfirmByFormId(long formId) {
		return covertFCreditConditionConfirmDO2Info(FCreditConditionConfirmDAO.findByFormId(formId));
	}
	
	@Override
	public FCreditConditionConfirmInfo findFCreditConditionConfirmByProjectCode(String projectCode) {
		return covertFCreditConditionConfirmDO2Info(FCreditConditionConfirmDAO
			.findByProjectCode(projectCode));
	}
	
	@Override
	public List<ProjectCreditConditionInfo> findProjectCreditConditionByProjectCodeAndStatus(	String projectCode,
																								String status) {
		List<ProjectCreditConditionInfo> listProjectCreditConditionInfo = new ArrayList<ProjectCreditConditionInfo>();
		List<ProjectCreditConditionDO> listProjectCreditConditionDO = projectCreditConditionDAO
			.findByProjectCodeAndStatus(projectCode, status);
		for (ProjectCreditConditionDO projectCreditConditionDO : listProjectCreditConditionDO) {
			ProjectCreditConditionInfo projectCreditConditionInfo = convertDO2Info(projectCreditConditionDO);
			listProjectCreditConditionInfo.add(projectCreditConditionInfo);
		}
		return listProjectCreditConditionInfo;
	}
	
	//保存授信落实情况表单
	@Override
	public FcsBaseResult saveFCreditConditionConfirmItem(final ProjectCreditConditionOrder order) {
		return commonProcess(order, "授信条件落实情况表单", new BeforeProcessInvokeService() {
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				String idStr = "";
				Date now = FcsPmDomainHolder.get().getSysDate();
				FCreditConditionConfirmItemDO fCreditConditionConfirmItemDO = new FCreditConditionConfirmItemDO();
				ProjectCreditConditionDO projectCreditConditionDO = projectCreditConditionDAO
					.findByProjectCodeAndItemIdAndType(order.getProjectCode(), order.getItemId(),
						order.getType());
				//				fCreditConditionConfirmItemDO = new FCreditConditionConfirmItemDO();
				
				fCreditConditionConfirmItemDO.setRawAddTime(now);
				BeanCopier.staticCopy(order, fCreditConditionConfirmItemDO,
					UnBoxingConverter.getInstance());
				fCreditConditionConfirmItemDO.setId(0);
				fCreditConditionConfirmItemDO.setStatus("APPLYING");
				
				if (order.getIsConfirm() != null && "IS".equals(order.getIsConfirm())) {
					projectCreditConditionDO.setStatus(CreditCheckStatusEnum.APPLYING.code());//已落实审核中
				} else {
					projectCreditConditionDO.setStatus(CreditCheckStatusEnum.NOT_APPLY.code());//未落实
				}
				projectCreditConditionDAO.update(projectCreditConditionDO);
				long id = FCreditConditionConfirmItemDAO.insert(fCreditConditionConfirmItemDO);
				//要是当前申请的这个单子 是确认落实的情况，则干掉其它单子  也申请了同一条数据未确认落实的数据
				if (fCreditConditionConfirmItemDO.getIsConfirm() != null
					&& "IS".equals(fCreditConditionConfirmItemDO.getIsConfirm())) {
					List<FCreditConditionConfirmItemDO> listItemDO = FCreditConditionConfirmItemDAO
						.findByProjectCodeAndItemIdAndType(order.getProjectCode(),
							order.getItemId(), order.getType());
					for (FCreditConditionConfirmItemDO DO : listItemDO) {
						if (DO.getIsConfirm() == null || "NO".equals(DO.getIsConfirm())) {
							FCreditConditionConfirmItemDAO.deleteById(DO.getId());
						}
					}
				}
				
				idStr = "" + id;
				projectCreditAssetAttachmentDAO.deleteByCreditId(projectCreditConditionDO.getId());//删除
				//插入附件
				List<ProjectCreditAssetAttachmentOrder> listAttachmentOrder = order
					.getAttachmentOrders();
				if (listAttachmentOrder != null) {
					for (ProjectCreditAssetAttachmentOrder attachmentOrder : listAttachmentOrder) {
						ProjectCreditAssetAttachmentDO DO = new ProjectCreditAssetAttachmentDO();
						BeanCopier.staticCopy(attachmentOrder, DO, UnBoxingConverter.getInstance());
						DO.setAssetId(projectCreditConditionDO.getAssetId());
						DO.setCreditId(projectCreditConditionDO.getId());
						projectCreditAssetAttachmentDAO.insert(DO);
					}
				}
				//保存附件
				if (StringUtil.isNotEmpty(order.getContractAgreementUrl())) {
					CommonAttachmentBatchOrder attachOrder = new CommonAttachmentBatchOrder();
					attachOrder.setProjectCode(order.getProjectCode());
					attachOrder.setBizNo("PM_" + idStr);
					attachOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
					attachOrder
						.setModuleType(CommonAttachmentTypeEnum.PROJECT_CREDIT_CONDITION_CONTRACT_AGREEMENT);
					attachOrder.setUploaderId(order.getUserId());
					attachOrder.setUploaderName(order.getUserName());
					attachOrder.setUploaderAccount(order.getUserAccount());
					attachOrder.setPath(order.getContractAgreementUrl());
					attachOrder.setRemark("授信落实附件-合同/协议");
					commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
				}
				//保存附件
				if (StringUtil.isNotEmpty(order.getRightVouche())) {
					CommonAttachmentBatchOrder attachOrder = new CommonAttachmentBatchOrder();
					attachOrder.setProjectCode(order.getProjectCode());
					attachOrder.setBizNo("PM_" + idStr);
					attachOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
					attachOrder
						.setModuleType(CommonAttachmentTypeEnum.PROJECT_CREDIT_CONDITION_ATTACHMENT);
					attachOrder.setUploaderId(order.getUserId());
					attachOrder.setUploaderName(order.getUserName());
					attachOrder.setUploaderAccount(order.getUserAccount());
					attachOrder.setPath(order.getRightVouche());
					attachOrder.setRemark("授信落实附件-普通附件");
					commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
				}
				return null;
			}
		}, null, null);
		
	}
	
	@Override
	public List<FCreditConditionConfirmItemInfo> findByFormId(long formId) {
		List<FCreditConditionConfirmItemInfo> creditConditionInfoList = Lists.newArrayList();
		if (formId > 0) {
			FCreditConditionConfirmDO DO = FCreditConditionConfirmDAO.findByFormId(formId);
			List<FCreditConditionConfirmItemDO> creditConditionDOList = FCreditConditionConfirmItemDAO
				.findByProjectCode(DO.getProjectCode());
			for (FCreditConditionConfirmItemDO fCreditConditionConfirmItemDO : creditConditionDOList) {
				FCreditConditionConfirmItemInfo info = new FCreditConditionConfirmItemInfo();
				info = convertFCreditConditionConfirmItemDO2Info(fCreditConditionConfirmItemDO);
				creditConditionInfoList.add(info);
			}
		}
		return creditConditionInfoList;
	}
	
	@Override
	public FCreditConditionConfirmInfo findCreditConditionByFormId(long formId) {
		FCreditConditionConfirmInfo info = new FCreditConditionConfirmInfo();
		FCreditConditionConfirmDO DO = FCreditConditionConfirmDAO.findByFormId(formId);
		info = covertFCreditConditionConfirmDO2Info(DO);
		ProjectCreditMarginDO projectCreditMarginDO = projectCreditMarginDAO.findByConfirmId(DO
			.getConfirmId());
		info.setProjectCreditMarginInfo(convertDO2MarginInfo(projectCreditMarginDO));
		return info;
	}
	
	@Override
	public List<FCreditConditionConfirmItemInfo> findFCreditConditionConfirmItemByProjectCodeAndStatus(	String projectCode,
																										String status) {
		List<FCreditConditionConfirmItemInfo> listProjectCreditConditionItemInfo = new ArrayList<FCreditConditionConfirmItemInfo>();
		List<FCreditConditionConfirmItemDO> listProjectCreditConditionItemDO = FCreditConditionConfirmItemDAO
			.findFCreditConditionConfirmItemByProjectCodeAndStauts(projectCode, status);
		for (FCreditConditionConfirmItemDO projectCreditConditionItemDO : listProjectCreditConditionItemDO) {
			FCreditConditionConfirmItemInfo projectCreditConditionItemInfo = convertFCreditConditionConfirmItemDO2Info(projectCreditConditionItemDO);
			listProjectCreditConditionItemInfo.add(projectCreditConditionItemInfo);
		}
		return listProjectCreditConditionItemInfo;
	}
	
	@Override
	public FcsBaseResult saveCreditConditionRelease(final CreditConditionReleaseOrder order) {
		return commonProcess(order, "保存授信条件解保情况", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				ProjectCreditConditionDO condition = projectCreditConditionDAO.findById(order
					.getId());
				if (null == condition) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						"未找到授信条件落实情况记录");
				}
				if ("YY".equals(order.getIsCounter())) {
					if (order.getReleaseId() == condition.getReleaseId()) {
						condition.setReleaseStatus(ReleaseStatusEnum.RELEASED.code());
						projectCreditConditionDAO.updateReleaseById(condition);
					}
				} else if ("Y".equals(order.getIsCounter())) {
					if (condition.getReleaseId() == 0) {
						BeanCopier.staticCopy(order, condition);
						condition.setReleaseStatus(ReleaseStatusEnum.RELEASING.code());
						projectCreditConditionDAO.updateReleaseById(condition);
					}
				} else {
					if (order.getReleaseId() == condition.getReleaseId()) {
						condition.setReleaseId(0L);
						condition.setReleaseGist("");
						condition.setReleaseReason("");
						condition.setReleaseStatus(ReleaseStatusEnum.WAITING.code());
						projectCreditConditionDAO.updateReleaseById(condition);
					}
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public List<FCreditConditionConfirmItemInfo> findByConfirmId(long confirmId) {
		List<FCreditConditionConfirmItemInfo> listProjectCreditConditionItemInfo = new ArrayList<FCreditConditionConfirmItemInfo>();
		List<FCreditConditionConfirmItemDO> listProjectCreditConditionItemDO = FCreditConditionConfirmItemDAO
			.findByConfirmId(confirmId);
		for (FCreditConditionConfirmItemDO projectCreditConditionItemDO : listProjectCreditConditionItemDO) {
			FCreditConditionConfirmItemInfo projectCreditConditionItemInfo = convertFCreditConditionConfirmItemDO2Info(projectCreditConditionItemDO);
			listProjectCreditConditionItemInfo.add(projectCreditConditionItemInfo);
		}
		if (ListUtil.isNotEmpty(listProjectCreditConditionItemInfo)) {
			for (FCreditConditionConfirmItemInfo itemInfo : listProjectCreditConditionItemInfo) {
				ProjectCreditConditionInfo projectCreditConditionInfo = findByProjectCodeAndItemIdAndType(
					itemInfo.getProjectCode(), itemInfo.getItemId(), itemInfo.getType().code());
				if (projectCreditConditionInfo != null) {
					List<ProjectCreditAssetAttachmentInfo> listAttachmentInfo = findByCreditId(projectCreditConditionInfo
						.getId());
					
					itemInfo.setListAttachmentInfos(listAttachmentInfo);
				}
			}
		}
		return listProjectCreditConditionItemInfo;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectCreditConditionInfo> queryCreditCondition(	ProjectCreditConditionQueryOrder order) {
		QueryBaseBatchResult<ProjectCreditConditionInfo> baseBatchResult = new QueryBaseBatchResult<>();
		try {
			ProjectCreditConditionDO projectCreditCondition = new ProjectCreditConditionDO();
			BeanCopier.staticCopy(order, projectCreditCondition);
			List<String> status = Lists.newArrayList();
			if (ListUtil.isNotEmpty(order.getStatusList())) {
				status = order.getStatusList();
			}
			long totalCount = projectCreditConditionDAO.findByConditionCount(
				projectCreditCondition, status);
			PageComponent component = new PageComponent(order, totalCount);
			
			List<ProjectCreditConditionInfo> list = baseBatchResult.getPageList();
			if (totalCount > 0) {
				List<ProjectCreditConditionDO> pageList = projectCreditConditionDAO
					.findByCondition(projectCreditCondition, component.getFirstRecord(),
						component.getPageSize(), status);
				for (ProjectCreditConditionDO doObj : pageList) {
					ProjectCreditConditionInfo info = new ProjectCreditConditionInfo();
					info = convertDO2Info(doObj);
					list.add(info);
				}
			}
			baseBatchResult.setSuccess(true);
			baseBatchResult.setPageList(list);
			baseBatchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询授信条件落实列表失败" + e.getMessage(), e);
			baseBatchResult.setSuccess(false);
			baseBatchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return baseBatchResult;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryProjectAndCredit(ProjectQueryOrder order) {
		QueryBaseBatchResult<ProjectInfo> baseBatchResult = new QueryBaseBatchResult<ProjectInfo>();
		try {
			ProjectCreditConditionFormDO projectDO = new ProjectCreditConditionFormDO();
			
			if (order != null)
				BeanCopier.staticCopy(order, projectDO);
			
			if (new Long(order.getProjectId()) != null)
				projectDO.setProjectId(order.getProjectId());
			
			BeanCopier.staticCopy(order, projectDO);
			//			List<String> busiTypeList = null;
			//			busiTypeList = order.getBusiTypeList();
			long totalCount = extraDAO
				.searchCreditAndProjectCount(projectDO, order.getDeptIdList());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<ProjectDO> pageList = extraDAO.searchCreditAndProjectList(projectDO,
				component.getFirstRecord(), component.getPageSize(), order.getDeptIdList(),
				order.getSortCol(), order.getSortOrder());
			
			List<ProjectInfo> list = baseBatchResult.getPageList();
			if (totalCount > 0) {
				for (ProjectDO sf : pageList) {
					ProjectInfo project = DoConvert.convertProjectDO2Info(sf);
					list.add(project);
				}
			}
			baseBatchResult.setSuccess(true);
			baseBatchResult.setPageList(list);
			baseBatchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询授信落实项目列表失败" + e.getMessage(), e);
			baseBatchResult.setSuccess(false);
			baseBatchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return baseBatchResult;
		
	}
	
	@Override
	public ProjectCreditConditionInfo findByProjectCodeAndItemIdAndType(String projectCode,
																		Long itemId, String type) {
		ProjectCreditConditionInfo info = new ProjectCreditConditionInfo();
		//		ProjectCreditConditionDO infoDO = projectCreditConditionDAO.findByItemId(itemId);
		//		info = convertDO2Info(infoDO);
		ProjectCreditConditionDO infoDO = projectCreditConditionDAO
			.findByProjectCodeAndItemIdAndType(projectCode, itemId, type);
		info = convertDO2Info(infoDO);
		return info;
	}
	
	@Override
	public List<ProjectCreditAssetAttachmentInfo> findByCreditId(long creditId) {
		List<ProjectCreditAssetAttachmentInfo> listInfo = new ArrayList<ProjectCreditAssetAttachmentInfo>();
		//将附件信息写入资产图像信息里面
		List<ProjectCreditAssetAttachmentDO> listDO = projectCreditAssetAttachmentDAO
			.findByCreditId(creditId);
		for (ProjectCreditAssetAttachmentDO projectCreditAssetAttachmentDO : listDO) {
			listInfo.add(convertDO2AttachmentInfo(projectCreditAssetAttachmentDO));
		}
		return listInfo;
	}
	
	@Override
	public Money findMarginAmountByProjectCode(String projectCode) {
		return projectCreditMarginDAO.findMarginAmountByProjectCode(projectCode);
	}

	@Override
	public QueryBaseBatchResult<CounterGuaranteeMeasuresInfo> measuresList(FCreditConditionConfirmQueryOrder queryOrder) {

		QueryBaseBatchResult<CounterGuaranteeMeasuresInfo> batchResult = new QueryBaseBatchResult<>();

		List<CounterGuaranteeMeasuresInfo> data = new ArrayList<CounterGuaranteeMeasuresInfo>();
		String countSql = "SELECT count(*)  count " +
				"FROM project p JOIN project_credit_condition pc ON p.project_code = pc.project_code \n" +
				"LEFT JOIN f_council_summary_project_pledge_asset asset  ON pc.item_id=asset.id AND pc.type=asset.type where 1=1 ";
		String sql = "SELECT p.dept_code,p.dept_name,p.busi_manager_name,p.project_code,p.project_name,p.customer_name,p.busi_type,p.busi_type_name,\n" +
				"CASE  WHEN pc.type='PLEDGE' THEN '抵押' WHEN pc.type='MORTGAGE' THEN '质押' WHEN pc.type='GUARANTOR' THEN '保证' ELSE '文字授信条件' END type,\n" +
				"pc.item_desc,IFNULL(asset.evaluation_price,0) as evaluation_price,IFNULL(asset.evaluation_price*(asset.pledge_rate/100)-debted_amount,0) AS clearing_amount,asset.ownership_name\n" +
				"FROM project p JOIN project_credit_condition pc ON p.project_code = pc.project_code \n" +
				"LEFT JOIN f_council_summary_project_pledge_asset asset ON pc.item_id=asset.id  AND pc.type=asset.type where 1=1 ";
		if(StringUtil.isNotBlank(queryOrder.getDeptName())){
			countSql=countSql+"  and p.dept_name like '%"+queryOrder.getDeptName()+"%'";
			sql=sql+"  and p.dept_name like '%"+queryOrder.getDeptName()+"%'";

		}
		if(StringUtil.isNotBlank(queryOrder.getProjectName())){
			countSql=countSql+"  and p.project_name like '%"+queryOrder.getProjectName()+"%'";
			sql=sql+"  and p.project_name like '%"+queryOrder.getProjectName()+"%'";
		}
		if(StringUtil.isNotBlank(queryOrder.getBusiManagerName())){
			countSql=countSql+"  and p.busi_manager_name like '%"+queryOrder.getBusiManagerName()+"%'";
			sql=sql+"  and p.busi_manager_name like '%"+queryOrder.getBusiManagerName()+"%'";
		}
		if(StringUtil.isNotBlank(queryOrder.getBusiType())){
			countSql=countSql+"  and busi_type = '"+queryOrder.getBusiType()+"'";
			sql=sql+"  and busi_type = '"+queryOrder.getBusiType()+"'";
		}
		//权限
		if(ListUtil.isNotEmpty(queryOrder.getDeptIdList())){
			String deptIds="";
			for(Long deptId:queryOrder.getDeptIdList()){
				deptIds=deptIds+deptId+",";
			}
			if(StringUtil.isNotBlank(deptIds)){
				deptIds=deptIds.substring(0,deptIds.length()-1);
			}
			countSql=countSql+"  and dept_id in("+deptIds+") ";
			sql=sql+"  and dept_id in("+deptIds+") ";
		}
		if(queryOrder.getBusiManagerId()>0){
			countSql=countSql+"  and busi_manager_id= '"+queryOrder.getBusiManagerId()+"' ";
			sql=sql+"  and busi_manager_id= '"+queryOrder.getBusiManagerId()+"' ";
		}
		if(StringUtil.isNotBlank(queryOrder.getSortCol())&&StringUtil.isNotBlank(queryOrder.getSortOrder())){
			sql=sql+" order by "+queryOrder.getSortCol()+" "+queryOrder.getSortOrder();
		}
		sql=sql+" LIMIT "+queryOrder.getLimitStart()+","+queryOrder.getPageSize()+";";

		PmReportDOQueryOrder reportDOQueryOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		reportDOQueryOrder.setFieldMap(fieldMap);
		reportDOQueryOrder.setSql(countSql);
		List<DataListItem> dataResult = pmReportService.doQuery(reportDOQueryOrder);
		long totalSize=0;
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				totalSize = Long.parseLong(String.valueOf(itm.getMap().get("count")));
			}
		}

		if (totalSize > 0) {
			PageComponent component = new PageComponent(queryOrder, totalSize);
			reportDOQueryOrder.setSql(sql);
			dataResult = pmReportService.doQuery(reportDOQueryOrder);
			if (dataResult != null) {
				for (DataListItem itm : dataResult) {
					String deptName = String.valueOf(itm.getMap().get("dept_name"));
					String deptCode = String.valueOf(itm.getMap().get("dept_code"));
					String busiManagerName = String.valueOf(itm.getMap().get("busi_manager_name"));
					String projectCode = String.valueOf(itm.getMap().get("project_code"));
					String projectName = String.valueOf(itm.getMap().get("project_name"));
					String customerName = String.valueOf(itm.getMap().get("customer_name"));
					String busiType = String.valueOf(itm.getMap().get("busi_type"));
					String busiTypeName = String.valueOf(itm.getMap().get("busi_type_name"));
					String type = String.valueOf(itm.getMap().get("type"));
					String itemDesc = String.valueOf(itm.getMap().get("item_desc"));
					Money evaluationPrice = toMoney(itm.getMap().get("evaluation_price"));
					Money clearingAmount = toMoney(itm.getMap().get("clearing_amount"));
					String ownershipName = itm.getMap().get("ownership_name")==null?null:String.valueOf(itm.getMap().get("ownership_name"));
					CounterGuaranteeMeasuresInfo info = new CounterGuaranteeMeasuresInfo();
					info.setDeptName(deptName);
					info.setBusiManagerName(busiManagerName);
					info.setProjectName(projectName);
					info.setCustomerName(customerName);
					info.setBusiTypeName(busiTypeName);
					info.setType(type);
					info.setItemDesc(itemDesc);
					info.setEvaluationPrice(evaluationPrice);
					info.setClearingAmount(clearingAmount);
					info.setOwnershipName(ownershipName);
					data.add(info);
				}
			}
			batchResult.initPageParam(component);
			batchResult.setPageList(data);
		}
		batchResult.setSuccess(true);
		return batchResult;
	}
	//转化Money
	private Money toMoney(Object fen) {
		try {
			String s = String.valueOf(fen);
			return Money.amout(s).divide(100);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return Money.zero();
	}
}
