package com.born.fcs.pm.biz.service.financialproject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialTansferApplyDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialTradeTansferDO;
import com.born.fcs.pm.dataobject.FinancialProjectTransferFormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialTansferApplyInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectTransferFormInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialTradeTansferInfo;
import com.born.fcs.pm.ws.order.financialproject.FProjectFinancialTansferApplyOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectTransferFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeTansferOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeTansferQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectTransferService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("financialProjectTransferService")
public class FinancialProjectTransferServiceImpl extends BaseFormAutowiredDomainService implements
																						FinancialProjectTransferService {
	
	@Autowired
	FinancialProjectService financialProjectService;
	
	@Override
	public QueryBaseBatchResult<FinancialProjectTransferFormInfo> queryPage(FinancialProjectTransferFormQueryOrder order) {
		QueryBaseBatchResult<FinancialProjectTransferFormInfo> batchResult = new QueryBaseBatchResult<FinancialProjectTransferFormInfo>();
		
		try {
			FinancialProjectTransferFormDO projectForm = new FinancialProjectTransferFormDO();
			
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("f.form_id");
			}
			
			if (StringUtil.isBlank(order.getSortOrder())) {
				order.setSortOrder("DESC");
			}
			
			projectForm.setApplyId(order.getApplyId());
			projectForm.setProductName(order.getProductName());
			projectForm.setFormStatus(order.getFormStatus());
			projectForm.setProjectCode(order.getProjectCode());
			projectForm.setLoginUserId(order.getLoginUserId());
			projectForm.setDeptIdList(order.getDeptIdList());
			projectForm.setCreateUserId(order.getCreateUserId());
			
			if (StringUtil.isNotBlank(order.getTransferTimeStart())) {
				projectForm.setTransferTimeStart(DateUtil.string2DateTimeByAutoZero(order
					.getTransferTimeStart()));
			}
			
			if (StringUtil.isNotBlank(order.getTransferTimeEnd())) {
				projectForm.setTransferTimeEnd(DateUtil.string2DateTimeBy23(order
					.getTransferTimeEnd()));
			}
			
			long totalCount = extraDAO.searchFinancialProjectTransferFormCount(projectForm);
			PageComponent component = new PageComponent(order, totalCount);
			
			//查询的分页参数
			projectForm.setSortCol(order.getSortCol());
			projectForm.setSortOrder(order.getSortOrder());
			projectForm.setLimitStart(component.getFirstRecord());
			projectForm.setPageSize(component.getPageSize());
			
			List<FinancialProjectTransferFormDO> list = extraDAO
				.searchFinancialProjectTransferForm(projectForm);
			
			List<FinancialProjectTransferFormInfo> pageList = new ArrayList<FinancialProjectTransferFormInfo>(
				list.size());
			for (FinancialProjectTransferFormDO DO : list) {
				FinancialProjectTransferFormInfo info = new FinancialProjectTransferFormInfo();
				BeanCopier.staticCopy(DO, info);
				info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
				info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
				info.setCouncilStatus(ProjectCouncilStatusEnum.getByCode(DO.getCouncilStatus()));
				info.setCouncilType(ProjectCouncilEnum.getByCode(DO.getCouncilType()));
				info.setProject(financialProjectService.queryByCode(info.getProjectCode()));
				info.setTrade(queryTradeByApplyId(info.getApplyId()));
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询理财项目转让申请列表失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectFinancialTradeTansferInfo> queryTrade(	ProjectFinancialTradeTansferQueryOrder order) {
		QueryBaseBatchResult<ProjectFinancialTradeTansferInfo> batchResult = new QueryBaseBatchResult<ProjectFinancialTradeTansferInfo>();
		
		try {
			ProjectFinancialTradeTansferDO trade = new ProjectFinancialTradeTansferDO();
			
			BeanCopier.staticCopy(order, trade);
			
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("trade_id");
			}
			
			if (StringUtil.isBlank(order.getSortOrder())) {
				order.setSortOrder("DESC");
			}
			
			long totalCount = projectFinancialTradeTansferDAO.findByConditionCount(trade,
				order.getTransferTimeStart(), order.getTransferTimeEnd());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<ProjectFinancialTradeTansferDO> list = projectFinancialTradeTansferDAO
				.findByCondition(trade, order.getTransferTimeStart(), order.getTransferTimeEnd(),
					order.getSortCol(), order.getSortOrder(), component.getFirstRecord(),
					component.getPageSize());
			
			List<ProjectFinancialTradeTansferInfo> pageList = new ArrayList<ProjectFinancialTradeTansferInfo>(
				list.size());
			for (ProjectFinancialTradeTansferDO DO : list) {
				ProjectFinancialTradeTansferInfo info = new ProjectFinancialTradeTansferInfo();
				BeanCopier.staticCopy(DO, info);
				info.setIsBuyBack(BooleanEnum.getByCode(DO.getIsBuyBack()));
				info.setIsConfirm(BooleanEnum.getByCode(DO.getIsConfirm()));
				info.setIsTransferOwnership(BooleanEnum.getByCode(DO.getIsTransferOwnership()));
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询理财项目转让交易列表失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public FProjectFinancialTansferApplyInfo queryApplyByFormId(long formId) {
		FProjectFinancialTansferApplyDO apply = FProjectFinancialTansferApplyDAO
			.findByFormId(formId);
		if (apply != null) {
			FProjectFinancialTansferApplyInfo info = new FProjectFinancialTansferApplyInfo();
			BeanCopier.staticCopy(apply, info);
			info.setIsBuyBack(BooleanEnum.getByCode(apply.getIsBuyBack()));
			info.setIsTransferOwnership(BooleanEnum.getByCode(apply.getIsTransferOwnership()));
			info.setCouncilStatus(ProjectCouncilStatusEnum.getByCode(apply.getCouncilStatus()));
			info.setCouncilType(ProjectCouncilEnum.getByCode(apply.getCouncilType()));
			return info;
		}
		return null;
	}
	
	@Override
	public ProjectFinancialTradeTansferInfo queryTradeByApplyId(long applyId) {
		ProjectFinancialTradeTansferDO trade = projectFinancialTradeTansferDAO
			.findByApplyId(applyId);
		if (trade != null) {
			ProjectFinancialTradeTansferInfo info = new ProjectFinancialTradeTansferInfo();
			BeanCopier.staticCopy(trade, info);
			info.setIsBuyBack(BooleanEnum.getByCode(trade.getIsBuyBack()));
			info.setIsConfirm(BooleanEnum.getByCode(trade.getIsConfirm()));
			info.setIsTransferOwnership(BooleanEnum.getByCode(trade.getIsTransferOwnership()));
			return info;
		}
		return null;
	}
	
	@Override
	public FormBaseResult save(final FProjectFinancialTansferApplyOrder order) {
		return commonFormSaveProcess(order, "保存理财项目转让申请单", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				
				ProjectFinancialInfo project = financialProjectService.queryByCode(order
					.getProjectCode());
				if (project == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "理财项目不存在");
				}
				
				if (!project.getStatus().canTransfer()) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"当前状态不允许转让");
				}
				
				FProjectFinancialTansferApplyDO apply = FProjectFinancialTansferApplyDAO
					.findByFormId(form.getFormId());
				
				long transferingNum = financialProjectService.transferingNum(
					order.getProjectCode(), apply == null ? 0 : apply.getApplyId());
				
				long redeemingNum = financialProjectService.redeemingNum(order.getProjectCode(),
					apply == null ? 0 : apply.getApplyId());
				
				if (project.getOriginalHoldNum() < (order.getTransferNum() + transferingNum + redeemingNum)) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE, "可转让份额不足");
				}
				
				boolean isUpdate = false;
				if (apply == null) {
					apply = new FProjectFinancialTansferApplyDO();
					BeanCopier.staticCopy(order, apply, UnBoxingConverter.getInstance());
					apply.setRawAddTime(now);
				} else {
					isUpdate = true;
					long applyId = apply.getApplyId();
					BeanCopier.staticCopy(order, apply, UnBoxingConverter.getInstance());
					apply.setApplyId(applyId);
				}
				
				apply.setFormId(form.getFormId());
				apply.setTransferingNum(transferingNum);
				apply.setRedeemingNum(redeemingNum);
				//持有份额不含回购部分
				apply.setHoldNum(project.getOriginalHoldNum());
				
				if (isUpdate) {
					FProjectFinancialTansferApplyDAO.update(apply);
				} else {
					FProjectFinancialTansferApplyDAO.insert(apply);
				}
				
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				financialProjectService.changeStatus(order.getProjectCode(),
					FinancialProjectStatusEnum.TRANSFER_DRAFT);
				return null;
			}
		});
	}
	
	@Override
	public FcsBaseResult saveTrade(final ProjectFinancialTradeTansferOrder order) {
		return commonProcess(order, "理财项目转让信息维护", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				FinancialProjectTransferFormQueryOrder formOrder = new FinancialProjectTransferFormQueryOrder();
				formOrder.setApplyId(order.getApplyId());
				QueryBaseBatchResult<FinancialProjectTransferFormInfo> applyForm = queryPage(formOrder);
				if (applyForm.getTotalCount() == 0) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "转让申请单不存在");
				}
				
				FinancialProjectTransferFormInfo applyFormInfo = applyForm.getPageList().get(0);
				if (applyFormInfo.getFormStatus() != FormStatusEnum.APPROVAL) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"当前状态不允许对转让信息进行维护");
				}
				
				FcsPmDomainHolder.get().addAttribute("projectCode", applyFormInfo.getProjectCode());
				
				//确认回购
				if (order.getIsConfirm() == BooleanEnum.IS) {
					ProjectFinancialTradeTansferDO tradeDO = projectFinancialTradeTansferDAO
						.findByApplyId(order.getApplyId());
					if (tradeDO == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"转让信息不存在");
					}
					if (!BooleanEnum.IS.code().equals(tradeDO.getIsBuyBack())) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "该转让不能回购");
					}
					
					if (now.before(tradeDO.getBuyBackTime())) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "尚未到回购日期");
					}
					
					tradeDO.setIsConfirm(BooleanEnum.IS.code());
					tradeDO.setConfirmTime(now);
					if (order.getBuyBackTime() != null) {//是否重新传了回购时间
						tradeDO.setBuyBackTime(order.getBuyBackTime());
					}
					if (order.getBuyBackPrice() != null
						&& order.getBuyBackPrice().greaterThan(ZERO_MONEY)) {//重新传了回购单价
						tradeDO.setBuyBackPrice(order.getBuyBackPrice());
					}
					//确认回购
					projectFinancialTradeTansferDAO.update(tradeDO);
					
					FcsPmDomainHolder.get().addAttribute("trade", tradeDO);
				} else if (order.getIsConfirm() == BooleanEnum.YES) { //资金划付申请通过后
					ProjectFinancialTradeTansferDO tradeDO = projectFinancialTradeTansferDAO
						.findByApplyId(order.getApplyId());
					if (tradeDO == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"转让信息不存在");
					}
					
					if (!BooleanEnum.IS.code().equals(tradeDO.getIsBuyBack())) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "该转让不能回购");
					}
					
					if (BooleanEnum.NO.code().equals(tradeDO.getIsConfirm())) {
						tradeDO.setIsConfirm(BooleanEnum.YES.code());
						//待确认回购
						projectFinancialTradeTansferDAO.update(tradeDO);
					}
				} else {
					ProjectFinancialTradeTansferInfo trade = queryTradeByApplyId(applyFormInfo
						.getApplyId());
					if (trade != null) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "转让信息已维护");
					}
					
					ProjectFinancialTradeTansferDO tradeDO = new ProjectFinancialTradeTansferDO();
					BeanCopier.staticCopy(order, tradeDO);
					tradeDO.setIsBuyBack(order.getIsBuyBack().code());
					tradeDO.setIsTransferOwnership(order.getIsTransferOwnership().code());
					tradeDO.setFlowNo(BusinessNumberUtil.gainNumber());
					tradeDO.setIsConfirm(BooleanEnum.NO.code());
					tradeDO.setRawAddTime(now);
					projectFinancialTradeTansferDAO.insert(tradeDO);
					
					FcsPmDomainHolder.get().addAttribute("trade", tradeDO);
				}
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				
				String projectCode = (String) FcsPmDomainHolder.get().getAttribute("projectCode");
				ProjectFinancialTradeTansferDO trade = (ProjectFinancialTradeTansferDO) FcsPmDomainHolder
					.get().getAttribute("trade");
				
				ProjectFinancialDO project = projectFinancialDAO.findByCodeForUpdate(projectCode);
				if (project == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"理财项目不存在");
				}
				if (order.getIsConfirm() == BooleanEnum.IS) {
					//修改回购份额 和 持有份额
					project.setBuyBackNum(project.getBuyBackNum() + trade.getTransferNum());
					project.setActualHoldNum(project.getActualHoldNum() + trade.getTransferNum());
					project.setStatus(FinancialProjectStatusEnum.BUY_BACK_FINISH.code());
				} else if (order.getIsConfirm() != BooleanEnum.YES) {
					//修改已转让份额 和 原始未转让份额
					project.setTransferedNum(project.getTransferedNum() + trade.getTransferNum());
					project.setOriginalHoldNum(project.getOriginalHoldNum()
												- trade.getTransferNum());
					project.setActualHoldNum(project.getActualHoldNum() - trade.getTransferNum());
					if (BooleanEnum.IS.code().equals(trade.getIsBuyBack())) {
						project.setStatus(FinancialProjectStatusEnum.BUY_BACK_WAITING.code());
					} else {
						project.setStatus(FinancialProjectStatusEnum.TRANSFERED.code());
					}
				}
				projectFinancialDAO.update(project);
				return null;
			}
		});
	}
}
