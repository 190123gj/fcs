package com.born.fcs.pm.biz.service.financialproject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormFeeOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountChangeOrder;
import com.born.fcs.fm.ws.service.common.ReceiptPaymentFormService;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialRedeemApplyDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialTradeRedeemDO;
import com.born.fcs.pm.dataobject.FinancialProjectRedeemFormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialRedeemApplyInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectRedeemFormInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialTradeRedeemInfo;
import com.born.fcs.pm.ws.order.financialproject.FProjectFinancialRedeemApplyOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectRedeemFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeRedeemOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeRedeemQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectRedeemService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("financialProjectRedeemService")
public class FinancialProjectRedeemServiceImpl extends BaseFormAutowiredDomainService implements
																						FinancialProjectRedeemService {
	@Autowired
	FinancialProjectService financialProjectService;
	
	@Autowired
	ReceiptPaymentFormService receiptPaymentFormServiceClient;
	
	@Autowired
	ForecastService forecastServiceClient;
	
	@Override
	public QueryBaseBatchResult<FinancialProjectRedeemFormInfo> queryPage(	FinancialProjectRedeemFormQueryOrder order) {
		QueryBaseBatchResult<FinancialProjectRedeemFormInfo> batchResult = new QueryBaseBatchResult<FinancialProjectRedeemFormInfo>();
		
		try {
			FinancialProjectRedeemFormDO projectForm = new FinancialProjectRedeemFormDO();
			
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
			projectForm.setDraftUserId(order.getDraftUserId());
			projectForm.setDeptIdList(order.getDeptIdList());
			projectForm.setCreateUserId(order.getCreateUserId());
			if (StringUtil.isNotBlank(order.getApplyTimeStart())) {
				projectForm.setApplyTimeStart(DateUtil.string2DateTimeByAutoZero(order
					.getApplyTimeStart()));
			}
			
			if (StringUtil.isNotBlank(order.getApplyTimeEnd())) {
				projectForm.setApplyTimeEnd(DateUtil.string2DateTimeBy23(order.getApplyTimeEnd()));
			}
			
			if (StringUtil.isNotBlank(order.getRedeemTimeStart())) {
				projectForm.setRedeemTimeStart(DateUtil.string2DateTimeByAutoZero(order
					.getRedeemTimeStart()));
			}
			
			if (StringUtil.isNotBlank(order.getRedeemTimeEnd())) {
				projectForm
					.setRedeemTimeEnd(DateUtil.string2DateTimeBy23(order.getRedeemTimeEnd()));
			}
			
			projectForm.setFormStatusList(order.getFormStatusList());
			
			long totalCount = extraDAO.searchFinancialProjectRedeemFormCount(projectForm);
			PageComponent component = new PageComponent(order, totalCount);
			
			//查询的分页参数
			projectForm.setSortCol(order.getSortCol());
			projectForm.setSortOrder(order.getSortOrder());
			projectForm.setLimitStart(component.getFirstRecord());
			projectForm.setPageSize(component.getPageSize());
			
			List<FinancialProjectRedeemFormDO> list = extraDAO
				.searchFinancialProjectRedeemForm(projectForm);
			
			List<FinancialProjectRedeemFormInfo> pageList = new ArrayList<FinancialProjectRedeemFormInfo>(
				list.size());
			Map<String, ProjectFinancialInfo> projectMap = Maps.newHashMap();
			for (FinancialProjectRedeemFormDO DO : list) {
				FinancialProjectRedeemFormInfo info = new FinancialProjectRedeemFormInfo();
				BeanCopier.staticCopy(DO, info);
				info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
				info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
				ProjectFinancialInfo project = projectMap.get(info.getProjectCode());
				if (project == null) {
					project = financialProjectService.queryByCode(info.getProjectCode());
					info.setProject(project);
					projectMap.put(info.getProjectCode(), project);
				} else {
					info.setProject(project);
				}
				info.setTrade(queryTradeByApplyId(info.getApplyId()));
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询理财项目赎回申请列表失败 {}", e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectFinancialTradeRedeemInfo> queryTrade(ProjectFinancialTradeRedeemQueryOrder order) {
		QueryBaseBatchResult<ProjectFinancialTradeRedeemInfo> batchResult = new QueryBaseBatchResult<ProjectFinancialTradeRedeemInfo>();
		
		try {
			ProjectFinancialTradeRedeemDO trade = new ProjectFinancialTradeRedeemDO();
			
			BeanCopier.staticCopy(order, trade);
			
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("trade_id");
			}
			
			if (StringUtil.isBlank(order.getSortOrder())) {
				order.setSortOrder("DESC");
			}
			
			long totalCount = projectFinancialTradeRedeemDAO.findByConditionCount(trade,
				order.getRedeemTimeStart(), order.getRedeemTimeEnd());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<ProjectFinancialTradeRedeemDO> list = projectFinancialTradeRedeemDAO
				.findByCondition(trade, order.getRedeemTimeStart(), order.getRedeemTimeEnd(),
					order.getSortCol(), order.getSortOrder(), component.getFirstRecord(),
					component.getPageSize());
			
			List<ProjectFinancialTradeRedeemInfo> pageList = new ArrayList<ProjectFinancialTradeRedeemInfo>(
				list.size());
			for (ProjectFinancialTradeRedeemDO DO : list) {
				ProjectFinancialTradeRedeemInfo info = new ProjectFinancialTradeRedeemInfo();
				BeanCopier.staticCopy(DO, info);
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询理财项目赎回交易列表失败 {}", e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public FProjectFinancialRedeemApplyInfo queryApplyByFormId(long formId) {
		FProjectFinancialRedeemApplyDO apply = FProjectFinancialRedeemApplyDAO.findByFormId(formId);
		if (apply != null) {
			FProjectFinancialRedeemApplyInfo info = new FProjectFinancialRedeemApplyInfo();
			BeanCopier.staticCopy(apply, info);
			return info;
		}
		return null;
	}
	
	@Override
	public ProjectFinancialTradeRedeemInfo queryTradeByApplyId(long applyId) {
		ProjectFinancialTradeRedeemDO trade = projectFinancialTradeRedeemDAO.findByApplyId(applyId);
		if (trade != null) {
			ProjectFinancialTradeRedeemInfo info = new ProjectFinancialTradeRedeemInfo();
			BeanCopier.staticCopy(trade, info);
			return info;
		}
		return null;
	}
	
	@Override
	public FormBaseResult save(final FProjectFinancialRedeemApplyOrder order) {
		return commonFormSaveProcess(order, "保存理财项目赎回申请单", new BeforeProcessInvokeService() {
			
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
				
				FProjectFinancialRedeemApplyDO apply = FProjectFinancialRedeemApplyDAO
					.findByFormId(form.getFormId());
				
				double transferingNum = financialProjectService.transferingNum(
					order.getProjectCode(), apply == null ? 0 : apply.getApplyId());
				
				double redeemingNum = financialProjectService.redeemingNum(order.getProjectCode(),
					apply == null ? 0 : apply.getApplyId());
				
				BigDecimal transfering = new BigDecimal(Double.toString(transferingNum));
				BigDecimal redeeming = new BigDecimal(Double.toString(redeemingNum));
				BigDecimal redeemNum = new BigDecimal(
					Double.toString(order.getRedeemNum() == null ? 0 : order.getRedeemNum()));
				
				if (project.getActualBuyNum() < (redeemNum.subtract(redeeming)
					.subtract(transfering).doubleValue())) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE, "可赎回份额不足");
				}
				
				boolean isUpdate = false;
				if (apply == null) {
					apply = new FProjectFinancialRedeemApplyDO();
					BeanCopier.staticCopy(order, apply, UnBoxingConverter.getInstance());
					apply.setRawAddTime(now);
				} else {
					isUpdate = true;
					long applyId = apply.getApplyId();
					BeanCopier.staticCopy(order, apply, UnBoxingConverter.getInstance());
					apply.setApplyId(applyId);
				}
				
				if (apply.getRedeemPrice() == null
					|| !apply.getRedeemPrice().greaterThan(ZERO_MONEY)) {
					apply.setRedeemPrice(project.getActualPrice());
				}
				
				//应收本金
				apply.setRedeemPrincipal(apply.getRedeemPrice().multiply(apply.getRedeemNum()));
				//利息
				Money interest = financialProjectService.caculateRedeemInterest(
					apply.getProjectCode(), apply.getRedeemPrice(), apply.getRedeemNum(),
					apply.getRedeemTime());
				apply.setRedeemInterest(interest);
				apply.setFormId(form.getFormId());
				apply.setTransferingNum(transferingNum);
				apply.setRedeemingNum(redeemingNum);
				apply.setBuyNum(project.getActualBuyNum());
				apply.setHoldNum(project.getOriginalHoldNum());
				apply.setActualHoldNum(project.getActualHoldNum());
				
				if (isUpdate) {
					FProjectFinancialRedeemApplyDAO.update(apply);
				} else {
					FProjectFinancialRedeemApplyDAO.insert(apply);
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult saveTrade(final ProjectFinancialTradeRedeemOrder order) {
		return commonProcess(order, "理财项目赎回信息维护", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				FinancialProjectRedeemFormQueryOrder formOrder = new FinancialProjectRedeemFormQueryOrder();
				formOrder.setApplyId(order.getApplyId());
				QueryBaseBatchResult<FinancialProjectRedeemFormInfo> applyForm = queryPage(formOrder);
				if (applyForm.getTotalCount() == 0) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "赎回申请单不存在");
				}
				
				FinancialProjectRedeemFormInfo applyFormInfo = applyForm.getPageList().get(0);
				if (applyFormInfo.getFormStatus() != FormStatusEnum.APPROVAL) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"当前状态不允许对赎回信息进行维护");
				}
				
				FcsPmDomainHolder.get().addAttribute("projectCode", applyFormInfo.getProjectCode());
				
				ProjectFinancialTradeRedeemInfo trade = queryTradeByApplyId(applyFormInfo
					.getApplyId());
				if (trade != null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"赎回信息已维护");
				}
				
				ProjectFinancialTradeRedeemDO tradeDO = new ProjectFinancialTradeRedeemDO();
				BeanCopier.staticCopy(order, tradeDO);
				try {
					Date redeemTime = order.getRedeemTime();
					if (redeemTime == null)
						redeemTime = applyFormInfo.getRedeemTime();
					if (redeemTime == null)
						redeemTime = now;
					tradeDO.setRedeemTime(DateUtil.string2DateTimeByAutoZero(DateUtil
						.dtSimpleFormat(redeemTime)));
				} catch (ParseException e) {
					logger.error(e.getMessage(), e);
				}
				tradeDO.setFlowNo(BusinessNumberUtil.gainNumber());
				
				tradeDO.setRedeemPrice(applyFormInfo.getRedeemPrice());
				if (tradeDO.getRedeemNum() == 0)
					tradeDO.setRedeemNum(applyFormInfo.getRedeemNum());
				//根据本金计算价格
				tradeDO.setRedeemPrice(tradeDO.getRedeemPrincipal().divide(tradeDO.getRedeemNum()));
				if (StringUtil.isBlank(tradeDO.getRedeemReason()))
					tradeDO.setRedeemReason(applyFormInfo.getRedeemReason());
				tradeDO.setRawAddTime(now);
				tradeDO.setRedeemInterestRate(financialProjectService.caculateInterestRate(
					tradeDO.getProjectCode(), tradeDO.getRedeemInterest(),
					tradeDO.getRedeemPrincipal(), tradeDO.getRedeemTime()) * 100);
				projectFinancialTradeRedeemDAO.insert(tradeDO);
				
				String projectCode = (String) FcsPmDomainHolder.get().getAttribute("projectCode");
				
				ProjectFinancialDO project = projectFinancialDAO.findByCodeForUpdate(projectCode);
				if (project == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"理财项目不存在");
				}
				
				//修改已赎回份额等信息
				project.setRedeemedNum(NumberUtil.doubleCaculate(project.getRedeemedNum(),
					tradeDO.getRedeemNum(), "+"));
				//				//优先赎回回购的部分、方便计算原始份额继续转让、2016-10-10 多次转让后该逻辑失效
				//				double originalHoldNum = project.getRedeemedNum() - project.getBuyBackNum();
				//				if (originalHoldNum > 0) {
				//					project.setOriginalHoldNum(project.getOriginalHoldNum() - originalHoldNum);
				//				}
				//赎回后减掉对应的持有份额
				project.setActualHoldNum(NumberUtil.doubleCaculate(project.getActualHoldNum(),
					tradeDO.getRedeemNum(), "-"));
				project.setOriginalHoldNum(NumberUtil.doubleCaculate(project.getOriginalHoldNum(),
					tradeDO.getRedeemNum(), "-"));
				if (project.getOriginalHoldNum() <= 0 && now.before(project.getActualExpireDate())) {//提前赎回完成
					project.setStatus(FinancialProjectStatusEnum.FINISH.code());
				}
				projectFinancialDAO.update(project);
				//同步赎回信息到资金系统收款单
				try {
					ReceiptPaymentFormOrder rpOrder = new ReceiptPaymentFormOrder();
					rpOrder.setContractNo(null);
					rpOrder.setSourceForm(SourceFormEnum.FINANCIAL_REDEEM);
					rpOrder.setSourceFormId(String.valueOf(applyFormInfo.getFormId()));
					rpOrder.setSourceFormSys(SystemEnum.PM);
					rpOrder.setProjectCode(projectCode);
					rpOrder.setProjectName(project.getProductName());
					rpOrder.setUserId(applyFormInfo.getFormUserId());
					rpOrder.setUserName(applyFormInfo.getFormUserName());
					rpOrder.setUserAccount(applyFormInfo.getFormUserAccount());
					rpOrder.setDeptId(applyFormInfo.getFormDeptId());
					rpOrder.setDeptCode(applyFormInfo.getFormDeptCode());
					rpOrder.setDeptName(applyFormInfo.getFormDeptName());
					rpOrder.setRemark("理财产品赎回");
					
					List<ReceiptPaymentFormFeeOrder> feeOrderList = Lists.newArrayList();
					//理财产品赎回本金
					ReceiptPaymentFormFeeOrder feeOrder = new ReceiptPaymentFormFeeOrder();
					feeOrder.setFeeType(SubjectCostTypeEnum.FINANCING_PRODUCT_REDEMPTION);
					feeOrder.setAmount(tradeDO.getRedeemPrincipal());
					feeOrder.setRemark("实收本金");
					feeOrderList.add(feeOrder);
					feeOrder = new ReceiptPaymentFormFeeOrder();
					feeOrder.setFeeType(SubjectCostTypeEnum.INANCING_INVEST_INCOME);
					feeOrder.setAmount(tradeDO.getRedeemInterest());
					feeOrder.setRemark("实收利息");
					feeOrderList.add(feeOrder);
					
					rpOrder.setFeeOrderList(feeOrderList);
					logger.info("同步赎回信息到资金系统：{}", rpOrder);
					receiptPaymentFormServiceClient.add(rpOrder);
					
					try {
						ForecastAccountChangeOrder changeForecastOrder = new ForecastAccountChangeOrder();
						changeForecastOrder.setFeeType(ForecastFeeTypeEnum.REDEEM);
						changeForecastOrder.setProjectCode(projectCode);
						changeForecastOrder.setAmount(tradeDO.getRedeemPrincipal().add(
							tradeDO.getRedeemInterest()));
						changeForecastOrder.setUserId(applyFormInfo.getFormUserId());
						changeForecastOrder.setUserAccount(applyFormInfo.getFormUserAccount());
						changeForecastOrder.setUserName(applyFormInfo.getFormUserName());
						changeForecastOrder.setForecastMemo("理财产品赎回信息维护：实收本金 "
															+ tradeDO.getRedeemPrincipal()
																.toStandardString()
															+ "，实收利息 "
															+ tradeDO.getRedeemInterest()
																.toStandardString());
						forecastServiceClient.change(changeForecastOrder);
					} catch (Exception e) {
						logger.error("更新资金预测信息出错：{}", e);
					}
				} catch (Exception e) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"同步赎回信息到资金系统出错");
				}
				
				return null;
			}
		}, null, null);
	}
}
