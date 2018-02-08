package com.born.fcs.pm.biz.service.projectissueinformation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmOrder;
import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectIssueInformationDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.fm.service.IncomeConfirmServiceClient;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.ExpireProjectStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.SellStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.projectissueinformation.ProjectIssueInformationInfo;
import com.born.fcs.pm.ws.order.common.CommonAttachmentBatchOrder;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationAmountOrder;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.expireproject.ExpireProjectOrder;
import com.born.fcs.pm.ws.order.projectissueinformation.ProjectBondReinsuranceInformationOrder;
import com.born.fcs.pm.ws.order.projectissueinformation.ProjectIssueInformationOrder;
import com.born.fcs.pm.ws.order.projectissueinformation.ProjectIssueInformationQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;
import com.born.fcs.pm.ws.service.common.ProjectChannelRelationService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.contract.ProjectContractService;
import com.born.fcs.pm.ws.service.expireproject.ExpireProjectService;
import com.born.fcs.pm.ws.service.projectissueinformation.ProjectBondReinsuranceInformationService;
import com.born.fcs.pm.ws.service.projectissueinformation.ProjectIssueInformationService;
import com.born.fcs.pm.ws.service.releaseproject.ReleaseProjectService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("projectIssueInformationService")
public class ProjectIssueInformationServiceImpl extends BaseAutowiredDomainService implements
																					ProjectIssueInformationService {
	@Autowired
	ProjectService projectService;
	@Autowired
	protected ExpireProjectService expireProjectService;
	@Autowired
	protected ReleaseProjectService releaseProjectService;
	@Autowired
	protected ProjectContractService projectContractService;
	@Autowired
	protected ProjectBondReinsuranceInformationService projectBondReinsuranceInformationService;
	@Autowired
	CommonAttachmentService commonAttachmentService;
	@Autowired
	ProjectChannelRelationService projectChannelRelationService;
	@Autowired
	IncomeConfirmServiceClient incomeConfirmServiceClient;
	
	private ProjectIssueInformationInfo convertDO2Info(ProjectIssueInformationDO DO) {
		if (DO == null)
			return null;
		ProjectIssueInformationInfo info = new ProjectIssueInformationInfo();
		BeanCopier.staticCopy(DO, info);
		info.setCustomerType(CustomerTypeEnum.getByCode(DO.getCustomerType()));
		info.setIsContinue(BooleanEnum.getByCode(DO.getIsContinue()));
		info.setStatus(SellStatusEnum.getByCode(DO.getStatus()));
		info.setIsReinsurance(BooleanEnum.getByCode(DO.getIsReinsurance()));
		return info;
	}
	
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
	
	@Override
	public ProjectIssueInformationInfo findById(long id) {
		ProjectIssueInformationInfo info = null;
		if (id > 0) {
			ProjectIssueInformationDO DO = projectIssueInformationDAO.findById(id);
			info = convertDO2Info(DO);
		}
		return info;
	}
	
	@Override
	public FcsBaseResult save(final ProjectIssueInformationOrder order) {
		return commonProcess(order, "保存承销/发债类项目发行信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				//计算实际发售金额总和
				Money sumActualAmount = new Money(0, 0);
				List<ProjectIssueInformationDO> listProjectIssueInformationDO = projectIssueInformationDAO
					.findByProjectCode(order.getProjectCode());
				
				if (listProjectIssueInformationDO != null) {
					for (ProjectIssueInformationDO projectIssueInformationDO : listProjectIssueInformationDO) {
						sumActualAmount.addTo(projectIssueInformationDO.getActualAmount());
					}
				}
				sumActualAmount = sumActualAmount.add(new Money(order.getActualAmount()));
				//更新项目信息累计已发行金额
				ProjectDO projectDO = projectDAO.findByProjectCode(order.getProjectCode());
				projectDO.setAccumulatedIssueAmount(sumActualAmount);
				//				if (ProjectUtil.isBond(projectDO.getBusiType())) { //移至下面
				//					//发债类项目将发行金额写入可解保总金额中
				//					projectDO.setReleasableAmount(sumActualAmount);
				//				}
				
				//计算剩余可发售金额
				Money surplusAmount = new Money(0, 0);
				//				if (projectDO.getIsMaximumAmount() != null
				//					&& projectDO.getIsMaximumAmount().equals("IS")
				//					&& order.getBusiType().startsWith("12")) {
				//					surplusAmount = projectDO.getAmount().subtract(sumActualAmount)
				//						.add(projectDO.getReleasedAmount());
				//				} else {
				if (ProjectUtil.isBond(order.getBusiType())) {
					surplusAmount = new Money(order.getAmount()).subtract(sumActualAmount);
				} else {
					surplusAmount = projectDO.getAmount().subtract(sumActualAmount);
				}
				
				if (!surplusAmount.greaterThan(new Money(0))) {
					//					projectDO.setIsContinue("NO");//页面未填时，说明已发售完成
					if (order.getBusiType().startsWith("12")
						&& projectDO.getIsMaximumAmount() != null
						&& projectDO.getIsMaximumAmount().equals("IS")) {
						order.setIsContinue("IS");
					} else {
						order.setIsContinue("NO");
					}
				} else {
					if (BooleanEnum.NO.code().equals(order.getIsContinue())) {
						projectDO.setIsContinue("IS");
					} else {
						projectDO.setIsContinue("NO");
					}
					
				}
				//计算发售状态
				if (surplusAmount.greaterThan(new Money(0))
					&& BooleanEnum.IS.code().equals(order.getIsContinue())) {
					order.setStatus("SELLING");
				} else if (!surplusAmount.greaterThan(new Money(0))
							|| BooleanEnum.NO.code().equals(order.getIsContinue())) {
					order.setStatus("SELL_FINISH");
				} else {
					order.setStatus("SELL_PAUSE");
				}
				
				//承销项目做了发售信息维护后，并且选择不再继续发售，则项目已完成，能做的操作就是到期的发送通知和资料归档
				if (ProjectUtil.isUnderwriting(projectDO.getBusiType())
					&& BooleanEnum.NO.code().equals(order.getIsContinue())) {
					//					projectDO.setPhases(ProjectPhasesEnum.FINISH_PHASES.code());
					projectDO.setPhasesStatus(ProjectPhasesStatusEnum.APPROVAL.code());
					projectDO.setStatus(ProjectStatusEnum.SELL_FINISH.code());
				}
				if (ProjectUtil.isUnderwriting(projectDO.getBusiType())
					&& !surplusAmount.greaterThan(new Money(0))) {
					projectDO.setPhasesStatus(ProjectPhasesStatusEnum.APPROVAL.code());
					projectDO.setStatus(ProjectStatusEnum.SELL_FINISH.code());
				}
				
				//发债项目发售信息维护后，进入到放用款阶段
				if (ProjectUtil.isBond(projectDO.getBusiType())) {
					projectDO.setProjectCode(projectDO.getProjectCode());
					projectDO.setPhases(ProjectPhasesEnum.AFTERWARDS_PHASES.code());
					projectDO.setPhasesStatus(ProjectPhasesStatusEnum.DRAFT.code());
					
				}
				//未发售的项目 第一次发售增加开始时间和截止时间
				if (projectDO.getStartTime() == null && projectDO.getEndTime() == null) {
					projectDO.setStartTime(order.getIssueDate());
					//截止时间
					Date endTime = new Date();
					if (order.getTimeUnit() != null) {
						if (order.getTimeUnit().equals("YEAR")) {//单位是年
							endTime = DateUtil.getDateByMonth(order.getIssueDate(),
								order.getTimeLimit() * 12);
						} else if (order.getTimeUnit().equals("MONTH")) {//单位是月
							endTime = DateUtil.getDateByMonth(order.getIssueDate(),
								order.getTimeLimit());
						} else {//单位是天
							endTime = DateUtil.increaseDate(order.getIssueDate(),
								order.getTimeLimit());
						}
					}
					projectDO.setEndTime(endTime);
					// 添加到期项目列表中
					//发债或承销，以第一次发售的开始时间+授信期限 作为结束时间,START TIME ENDTIME 为NUL才更新
					ExpireProjectOrder expireOrder = new ExpireProjectOrder();
					expireOrder.setProjectCode(projectDO.getProjectCode());
					expireOrder.setProjectName(projectDO.getProjectName());
					Date expireDate = projectDO.getEndTime();
					expireOrder.setExpireDate(expireDate);
					expireOrder.setStatus(ExpireProjectStatusEnum.UNDUE);
					expireProjectService.add(expireOrder);
					
					//项目有到期时间预测退换客户保证金
					if (endTime != null) {
						projectService.syncForecastDeposit(projectDO.getProjectCode());
					}
				}
				//第二次以上包括第二次发售
				if (projectDO.getStartTime() != null && projectDO.getEndTime() != null
					&& order.getIssueDate() != null
					&& DateUtil.countDays(projectDO.getStartTime(), order.getIssueDate()) > 0) {
					//截止时间
					Date endTime1 = new Date();
					if (order.getTimeUnit() != null) {
						if (order.getTimeUnit().equals("YEAR")) {//单位是年
							endTime1 = DateUtil.getDateByMonth(order.getIssueDate(),
								order.getTimeLimit() * 12);
						} else if (order.getTimeUnit().equals("MONTH")) {//单位是月
							endTime1 = DateUtil.getDateByMonth(order.getIssueDate(),
								order.getTimeLimit());
						} else {//单位是天
							endTime1 = DateUtil.increaseDate(order.getIssueDate(),
								order.getTimeLimit());
						}
					}
					projectDO.setEndTime(endTime1);
					
				}
				ProjectIssueInformationDO information = null;
				Date now = FcsPmDomainHolder.get().getSysDate();
				if (order.getId() != null && order.getId() > 0) {
					information = projectIssueInformationDAO.findById(order.getId());
					if (information == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"承销/发债类项目发行信息不存在");
					}
				}
				
				if (information == null) { //新增
					information = new ProjectIssueInformationDO();
					//					order.setRawUpdateTime(now);
					BeanCopier.staticCopy(order, information, UnBoxingConverter.getInstance());
					information.setRawAddTime(now);
					information.setActualAmount(new Money(order.getActualAmount()));
					information.setAmount(new Money(order.getAmount()));
					
					//发债类第一次发售
					if (ProjectUtil.isBond(order.getBusiType())) {
						if ((listProjectIssueInformationDO == null || listProjectIssueInformationDO
							.size() == 0) && BooleanEnum.IS.code().equals(order.getIsReinsurance())) {
							
							List<ProjectBondReinsuranceInformationOrder> listOrder = order
								.getListBondReinsuranceOrder();
							if (listOrder != null && listOrder.size() > 0) {
								for (ProjectBondReinsuranceInformationOrder projectBondReinsuranceInformationOrder : listOrder) {
									projectBondReinsuranceInformationOrder.setProjectCode(order
										.getProjectCode());
									projectBondReinsuranceInformationOrder.setProjectName(order
										.getProjectName());
									projectBondReinsuranceInformationService
										.save(projectBondReinsuranceInformationOrder);
									//将分保信息保存到project表里面
									if ("IS".equals(projectBondReinsuranceInformationOrder
										.getIsJin())) {
										projectDO
											.setReinsuranceAmount(projectBondReinsuranceInformationOrder
												.getReinsuranceAmount());
										projectDO
											.setReinsuranceRatio(projectBondReinsuranceInformationOrder
												.getReinsuranceRatio());
									}
								}
								
							}
						}
					}
					
					if (StringUtil.equals("IS", information.getIsReinsurance())) {
						//分保
						information.setActualOccurAmount(ProjectUtil.getReinsuranceOccurAmount(
							information.getActualAmount(), projectDO.getReinsuranceAmount(),
							information.getAmount()));
					} else {
						information.setActualOccurAmount(information.getActualAmount());
					}
					
					projectIssueInformationDAO.insert(information);
				} else { //修改
					BeanCopier.staticCopy(order, information, UnBoxingConverter.getInstance());
					
					information.setActualAmount(new Money(order.getActualAmount()));
					information.setAmount(new Money(order.getAmount()));
					
					if (StringUtil.equals("IS", information.getIsReinsurance())) {
						//分保
						information.setActualOccurAmount(ProjectUtil.getReinsuranceOccurAmount(
							information.getActualAmount(), projectDO.getReinsuranceAmount(),
							information.getAmount()));
					} else {
						information.setActualOccurAmount(information.getActualAmount());
					}
					projectIssueInformationDAO.update(information);
				}
				
				projectDO.setRegulatorApprovalAmount(information.getAmount());
				
				if (ProjectUtil.isBond(projectDO.getBusiType())) {
					//发债类项目将发行金额写入可解保总金额中
					projectDO.setReleasableAmount(sumActualAmount);
					if (projectDO.getReinsuranceRatio() > 0) { //有分保比例
						//2017-08-03 直接算出客户解保金额
						//projectDO.setReleasableAmount(projectDO.getReinsuranceAmount());
						Money releaseable = ProjectUtil.getReinsuranceOccurAmount(sumActualAmount,
							projectDO.getReinsuranceAmount(),
							projectDO.getRegulatorApprovalAmount());
						projectDO.setReleasableAmount(releaseable);
					}
					
					//更新渠道相关金额(发债只有一个资金渠道所以直接取项目的可解保金额)
					ProjectChannelRelationAmountOrder channelAmountOrder = new ProjectChannelRelationAmountOrder();
					channelAmountOrder.setProjectCode(projectDO.getProjectCode());
					channelAmountOrder.setReleasableAmount(projectDO.getReleasableAmount());
					channelAmountOrder.setUpdateReleasable(true);
					projectChannelRelationService.updateRelatedAmount(channelAmountOrder);
				}
				
				projectDAO.update(projectDO);
				
				FcsPmDomainHolder.get().addAttribute("projectDO", projectDO);
				FcsPmDomainHolder.get().addAttribute("information", information);
				
				try {
					//将收费信息同步到收入确认表中
					if (projectDO.getProjectCode() != null) {
						ProjectDO projectDO1 = projectDAO.findByProjectCode(projectDO
							.getProjectCode());
						IncomeConfirmOrder order = new IncomeConfirmOrder();
						order.setProjectCode(projectDO.getProjectCode());
						order.setCustomerId(projectDO1.getCustomerId());
						order.setCustomerName(projectDO1.getCustomerName());
						order.setBusiType(projectDO1.getBusiType());
						order.setBusiTypeName(projectDO1.getBusiTypeName());
						order.setProjectName(projectDO1.getProjectName());
						order.setChargedAmount(projectDO1.getChargedAmount());
						order.setRelatedProjectCode(projectDO.getProjectCode());
						order.setNotConfirmedIncomeAmount(projectDO1.getChargedAmount());
						order.setCheckStatus(0);
						order.setCheckIndex(0);
						incomeConfirmServiceClient.save(order);
					}
				} catch (Exception e) {
					logger.error("收费信息同步到收入确认表出错：{}", e);
				}
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				//发债类项目 发售后添加到可解保列表中
				ProjectDO projectDO = (ProjectDO) FcsPmDomainHolder.get().getAttribute("projectDO");
				if (null != projectDO) {
					releaseProjectService.addBond(projectDO.getProjectCode());
				}
				
				//加入附件管理
				ProjectIssueInformationDO information = (ProjectIssueInformationDO) FcsPmDomainHolder
					.get().getAttribute("information");
				if (information != null && projectDO != null) {
					//保存附件
					CommonAttachmentBatchOrder attachOrder = new CommonAttachmentBatchOrder();
					attachOrder.setProjectCode(projectDO.getProjectCode());
					attachOrder.setBizNo("ISSUE_VOUCHER_" + information.getId());
					attachOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
					attachOrder.setModuleType(CommonAttachmentTypeEnum.ISSUE_VOUCHER);
					if (order.getUserId() != null)
						attachOrder.setUploaderId(order.getUserId());
					attachOrder.setUploaderName(order.getUserName());
					attachOrder.setUploaderAccount(order.getUserAccount());
					attachOrder.setPath(information.getVoucherUrl());
					attachOrder.setRemark("募集资金到账凭证");
					commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
					if (StringUtil.isNotEmpty(information.getLetterUrl())) {
						attachOrder.setBizNo("ISSUE_LETTER_" + information.getId());
						attachOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
						attachOrder.setModuleType(CommonAttachmentTypeEnum.ISSUE_LETTER);
						attachOrder.setPath(information.getLetterUrl());
						attachOrder.setRemark("担保函");
						commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
					}
				}
				return null;
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectIssueInformationInfo> query(	ProjectIssueInformationQueryOrder order) {
		QueryBaseBatchResult<ProjectIssueInformationInfo> baseBatchResult = new QueryBaseBatchResult<ProjectIssueInformationInfo>();
		
		ProjectIssueInformationDO queryCondition = new ProjectIssueInformationDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (order.getId() != null)
			queryCondition.setId(order.getId());
		
		long totalSize = projectIssueInformationDAO.findByConditionCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<ProjectIssueInformationDO> pageList = projectIssueInformationDAO.findByCondition(
			queryCondition, component.getFirstRecord(), component.getPageSize());
		
		List<ProjectIssueInformationInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (ProjectIssueInformationDO product : pageList) {
				list.add(convertDO2Info(product));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryIssueProject(ProjectQueryOrder order) {
		QueryBaseBatchResult<ProjectInfo> baseBatchResult = new QueryBaseBatchResult<ProjectInfo>();
		try {
			ProjectIssueInformationDO projectDO = new ProjectIssueInformationDO();
			
			if (order != null)
				BeanCopier.staticCopy(order, projectDO);
			
			if (new Long(order.getProjectId()) != null)
				projectDO.setProjectId(order.getProjectId());
			
			BeanCopier.staticCopy(order, projectDO);
			//			List<String> busiTypeList = null;
			//			busiTypeList = order.getBusiTypeList();
			long totalCount = extraDAO.searchIssueCount(projectDO, order.getDeptIdList());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<ProjectDO> pageList = extraDAO.searchIssueList(projectDO,
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
			logger.error("查询承销/发债已发售项目列表失败" + e.getMessage(), e);
			baseBatchResult.setSuccess(false);
			baseBatchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return baseBatchResult;
	}
	
	@Override
	public List<ProjectIssueInformationInfo> findProjectIssueInformationByProjectCode(	String projectCode) {
		List<ProjectIssueInformationInfo> listProjectIssueInformationInfo = new ArrayList<ProjectIssueInformationInfo>();
		List<ProjectIssueInformationDO> listProjectIssueInformationDO = projectIssueInformationDAO
			.findByProjectCode(projectCode);
		for (ProjectIssueInformationDO projectIssueInformationDO : listProjectIssueInformationDO) {
			ProjectIssueInformationInfo projectIssueInformationInfo = convertDO2Info(projectIssueInformationDO);
			listProjectIssueInformationInfo.add(projectIssueInformationInfo);
		}
		return listProjectIssueInformationInfo;
	}
	
	//发债类项目专用
	@Override
	public int updateStatusByProjectCode(String projectCode) {
		int num = 0;
		ProjectDO projectDO = projectDAO.findByProjectCode(projectCode);
		List<ProjectIssueInformationDO> listProjectIssueInformationDO = projectIssueInformationDAO
			.findByProjectCode(projectCode);
		for (ProjectIssueInformationDO projectIssueInformationDO : listProjectIssueInformationDO) {
			//计算可发行的剩余金额
			Money surplusAmount = new Money(0, 0);
			if (projectDO.getIsMaximumAmount() != null
				&& projectDO.getIsMaximumAmount().equals("IS")) {
				surplusAmount = projectDO.getAmount().subtract(
					projectDO.getAccumulatedIssueAmount());
				surplusAmount = surplusAmount.add(projectDO.getReleasedAmount());
			}
			if (surplusAmount.greaterThan(new Money(0))) {
				projectIssueInformationDO.setIsContinue("IS");
				projectIssueInformationDO.setStatus("SELLING");
			} else {
				projectIssueInformationDO.setStatus("SELLING");
			}
			num = projectIssueInformationDAO.update(projectIssueInformationDO);
		}
		return num;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryConsignmentSales(ProjectQueryOrder order) {
		QueryBaseBatchResult<ProjectInfo> baseBatchResult = new QueryBaseBatchResult<ProjectInfo>();
		try {
			ProjectDO projectDO = new ProjectDO();
			
			if (order != null)
				BeanCopier.staticCopy(order, projectDO);
			
			if (new Long(order.getProjectId()) != null)
				projectDO.setProjectId(order.getProjectId());
			
			BeanCopier.staticCopy(order, projectDO);
			//			List<String> busiTypeList = null;
			//			busiTypeList = order.getBusiTypeList();
			long totalCount = extraDAO
				.searchConsignmentSalesCount(projectDO, order.getDeptIdList());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<ProjectDO> pageList = extraDAO.searchConsignmentSalesList(projectDO,
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
			logger.error("查询承销项目列表失败" + e.getMessage(), e);
			baseBatchResult.setSuccess(false);
			baseBatchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return baseBatchResult;
		
	}
	
	//根据当前日期和项目截止  更新状态
	@Override
	public int updateStatusByEndTime(String projectCode) {
		int num = 0;
		try {
			int temp = 0;//记录当前时间和项目截止时间差值
			ProjectDO projectDO = projectDAO.findByProjectCode(projectCode);
			List<ProjectIssueInformationDO> listProjectIssueInformationDO = projectIssueInformationDAO
				.findByProjectCode(projectCode);
			
			Date now = getSysdate();//当前日期
			String nowTime = DateUtil.dtSimpleFormat(now);
			String endTime = DateUtil.dtSimpleFormat(projectDO.getEndTime());//项目截止时间
			temp = DateUtil.calculateDecreaseDate(endTime, nowTime);
			for (ProjectIssueInformationDO projectIssueInformationDO : listProjectIssueInformationDO) {
				if (temp > -30) {
					projectIssueInformationDO.setStatus("SELL_FINISH");
					projectIssueInformationDAO.update(projectIssueInformationDO);
					projectDO.setIsContinue("IS");// 强制手动关闭
					if (ProjectUtil.isUnderwriting(projectDO.getBusiType())) {
						projectDO.setPhasesStatus(ProjectPhasesStatusEnum.APPROVAL.code());
						projectDO.setStatus(ProjectStatusEnum.SELL_FINISH.code());
					}
				}
				
			}
			projectDAO.update(projectDO);
		} catch (Exception e) {
			logger.error("计算当前日期和截止日期差值失败" + e.getMessage(), e);
			e.printStackTrace();
		}
		return num;
		
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryBond(ProjectQueryOrder order) {
		QueryBaseBatchResult<ProjectInfo> baseBatchResult = new QueryBaseBatchResult<ProjectInfo>();
		try {
			ProjectIssueInformationDO projectDO = new ProjectIssueInformationDO();
			
			if (order != null)
				BeanCopier.staticCopy(order, projectDO);
			
			if (new Long(order.getProjectId()) != null)
				projectDO.setProjectId(order.getProjectId());
			
			BeanCopier.staticCopy(order, projectDO);
			//			List<String> busiTypeList = null;
			//			busiTypeList = order.getBusiTypeList();
			long totalCount = extraDAO.searchBondCount(projectDO, order.getDeptIdList());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<ProjectDO> pageList = extraDAO.searchBondList(projectDO,
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
			logger.error("查询发债项目列表失败" + e.getMessage(), e);
			baseBatchResult.setSuccess(false);
			baseBatchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return baseBatchResult;
	}
}
