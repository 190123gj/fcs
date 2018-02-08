package com.born.fcs.pm.biz.service.projectcreditcondition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.born.fcs.pm.ws.info.projectcreditcondition.FCreditConditionConfirmInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.FCreditConditionConfirmItemInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditAssetAttachmentInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditMarginInfo;
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
import com.born.fcs.pm.ws.service.projectcreditcondition.ProjectCreditConditionService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("projectCreditConditionService")
public class ProjectCreditConditionServiceImpl extends BaseFormAutowiredDomainService implements
																						ProjectCreditConditionService {
	ProjectCreditConditionServiceImpl projectCreditionServiceImpl;
	@Autowired
	ProjectCreditAssetAttachmentDAO projectCreditAssetAttachmentDAO;
	
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
					fCreditConditionConfirmDO.setRawUpdateTime(now);
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
					//将保证金信息写入保证金落实信息表
					if (order.getIsMargin() != null
						&& order.getIsMargin().equals(BooleanEnum.IS.code())) {
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
				Date now = FcsPmDomainHolder.get().getSysDate();
				FCreditConditionConfirmItemDO fCreditConditionConfirmItemDO = null;
				fCreditConditionConfirmItemDO = FCreditConditionConfirmItemDAO.findByItemIdAndType(
					order.getItemId(), order.getType());
				ProjectCreditConditionDO projectCreditConditionDO = projectCreditConditionDAO
					.findById(order.getId());
				if (fCreditConditionConfirmItemDO == null) { //新增
					fCreditConditionConfirmItemDO = new FCreditConditionConfirmItemDO();
					
					fCreditConditionConfirmItemDO.setRawAddTime(now);
					BeanCopier.staticCopy(order, fCreditConditionConfirmItemDO,
						UnBoxingConverter.getInstance());
					if (order.getCheckStatus() == 1) { //提交
						fCreditConditionConfirmItemDO.setStatus("APPLYING");
						FCreditConditionConfirmItemDAO.insert(fCreditConditionConfirmItemDO);
					} else {
						projectCreditConditionDO.setStatus("HOLDING");
						fCreditConditionConfirmItemDO.setStatus("HOLDING");
						
						projectCreditConditionDAO.update(projectCreditConditionDO);
						
						FCreditConditionConfirmItemDAO.insert(fCreditConditionConfirmItemDO);
					}
					//插入附件
					List<ProjectCreditAssetAttachmentOrder> listAttachmentOrder = order
						.getAttachmentOrders();
					if (listAttachmentOrder != null) {
						for (ProjectCreditAssetAttachmentOrder attachmentOrder : listAttachmentOrder) {
							ProjectCreditAssetAttachmentDO DO = new ProjectCreditAssetAttachmentDO();
							BeanCopier.staticCopy(attachmentOrder, DO,
								UnBoxingConverter.getInstance());
							DO.setAssetId(projectCreditConditionDO.getAssetId());
							DO.setCreditId(projectCreditConditionDO.getId());
							projectCreditAssetAttachmentDAO.insert(DO);
						}
					}
				} else { //修改
					BeanCopier.staticCopy(order, fCreditConditionConfirmItemDO,
						UnBoxingConverter.getInstance());
					if (order.getCheckStatus() == 1) { //提交
						if (fCreditConditionConfirmItemDO.getIsConfirm().equals(
							BooleanEnum.NO.code())) {
							FCreditConditionConfirmItemDAO.deleteById(fCreditConditionConfirmItemDO
								.getId());
						}
						fCreditConditionConfirmItemDO.setStatus(CreditCheckStatusEnum.APPLYING
							.code());
						FCreditConditionConfirmItemDAO.update(fCreditConditionConfirmItemDO);
					} else {
						if (fCreditConditionConfirmItemDO.getIsConfirm().equals(
							BooleanEnum.NO.code())) {
							FCreditConditionConfirmItemDAO.deleteById(fCreditConditionConfirmItemDO
								.getId());
							projectCreditConditionDO.setStatus(CreditCheckStatusEnum.NOT_APPLY
								.code());
						} else {
							fCreditConditionConfirmItemDO.setStatus(CreditCheckStatusEnum.HOLDING
								.code());
							projectCreditConditionDO.setStatus(CreditCheckStatusEnum.HOLDING.code());
							FCreditConditionConfirmItemDAO.update(fCreditConditionConfirmItemDO);
						}
						projectCreditConditionDAO.update(projectCreditConditionDO);
						
					}
					projectCreditAssetAttachmentDAO.deleteByCreditId(projectCreditConditionDO
						.getId());//删除
					//插入附件
					List<ProjectCreditAssetAttachmentOrder> listAttachmentOrder = order
						.getAttachmentOrders();
					if (listAttachmentOrder != null) {
						for (ProjectCreditAssetAttachmentOrder attachmentOrder : listAttachmentOrder) {
							ProjectCreditAssetAttachmentDO DO = new ProjectCreditAssetAttachmentDO();
							BeanCopier.staticCopy(attachmentOrder, DO,
								UnBoxingConverter.getInstance());
							DO.setAssetId(projectCreditConditionDO.getAssetId());
							DO.setCreditId(projectCreditConditionDO.getId());
							projectCreditAssetAttachmentDAO.insert(DO);
						}
					}
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
				if ("Y".equals(order.getIsCounter())) {
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
		return listProjectCreditConditionItemInfo;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectCreditConditionInfo> queryCreditCondition(	ProjectCreditConditionQueryOrder order) {
		QueryBaseBatchResult<ProjectCreditConditionInfo> baseBatchResult = new QueryBaseBatchResult<>();
		try {
			ProjectCreditConditionDO projectCreditCondition = new ProjectCreditConditionDO();
			BeanCopier.staticCopy(order, projectCreditCondition);
			
			long totalCount = projectCreditConditionDAO
				.findByConditionCount(projectCreditCondition);
			PageComponent component = new PageComponent(order, totalCount);
			
			List<ProjectCreditConditionInfo> list = baseBatchResult.getPageList();
			if (totalCount > 0) {
				List<ProjectCreditConditionDO> pageList = projectCreditConditionDAO
					.findByCondition(projectCreditCondition, component.getFirstRecord(),
						component.getPageSize());
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
}
