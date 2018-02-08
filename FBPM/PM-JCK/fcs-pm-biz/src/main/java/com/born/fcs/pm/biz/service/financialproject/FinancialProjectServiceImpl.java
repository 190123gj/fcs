package com.born.fcs.pm.biz.service.financialproject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialWithdrawingDO;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FinancialProductInterestTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTermTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.InterestSettlementWayEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectRedeemFormInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectTransferFormInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectWithdrawingInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialTradeRedeemInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialTradeTansferInfo;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectRedeemFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectTransferFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectWithdrawingQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeRedeemQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeTansferQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryAmountBatchResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.financialproject.FinancialProjectInterestResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectRedeemService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectTransferService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("financialProjectService")
public class FinancialProjectServiceImpl extends BaseAutowiredDomainService implements
																			FinancialProjectService {
	@Autowired
	FinancialProjectTransferService financialProjectTransferService;
	
	@Autowired
	FinancialProjectRedeemService financialProjectRedeemService;
	
	@SuppressWarnings("deprecation")
	@Override
	public QueryBaseBatchResult<ProjectFinancialInfo> query(FinancialProjectQueryOrder order) {
		
		QueryBaseBatchResult<ProjectFinancialInfo> batchResult = new QueryBaseBatchResult<ProjectFinancialInfo>();
		
		try {
			ProjectFinancialDO projectFinancial = new ProjectFinancialDO();
			BeanCopier.staticCopy(order, projectFinancial, UnBoxingConverter.getInstance());
			
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("project_id");
			}
			
			if (StringUtil.isBlank(order.getSortOrder())) {
				order.setSortOrder("DESC");
			}
			
			long totalCount = projectFinancialDAO.findByConditionCount(projectFinancial,
				order.getLoginUserId(), order.getDeptIdList(), order.getStatusList(),
				order.getHasHoldNum() == null ? null : order.getHasHoldNum().code(),
				order.getHasOrignalHoldNum() == null ? null : order.getHasOrignalHoldNum().code(),
				order.getBuyDateStart(), order.getBuyDateEnd(), order.getExpireDateStart(),
				order.getExpireDateEnd());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<ProjectFinancialDO> list = projectFinancialDAO.findByCondition(projectFinancial,
				order.getLoginUserId(), order.getDeptIdList(), order.getStatusList(),
				order.getHasHoldNum() == null ? null : order.getHasHoldNum().code(),
				order.getHasOrignalHoldNum() == null ? null : order.getHasOrignalHoldNum().code(),
				order.getBuyDateStart(), order.getBuyDateEnd(), order.getExpireDateStart(),
				order.getExpireDateEnd(), order.getSortCol(), order.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
			
			List<ProjectFinancialInfo> pageList = new ArrayList<ProjectFinancialInfo>(list.size());
			for (ProjectFinancialDO DO : list) {
				pageList.add(convertDO2Info(DO));
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询理财项目失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public QueryAmountBatchResult<FinancialProjectWithdrawingInfo> queryWithdraw(	FinancialProjectWithdrawingQueryOrder order) {
		
		QueryAmountBatchResult<FinancialProjectWithdrawingInfo> batchResult = new QueryAmountBatchResult<FinancialProjectWithdrawingInfo>();
		
		try {
			ProjectFinancialWithdrawingDO queryDO = new ProjectFinancialWithdrawingDO();
			BeanCopier.staticCopy(order, queryDO);
			
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("id");
			}
			
			if (StringUtil.isBlank(order.getSortOrder())) {
				order.setSortOrder("DESC");
			}
			
			long totalCount = projectFinancialWithdrawingDAO.findByConditionCount(queryDO);
			PageComponent component = new PageComponent(order, totalCount);
			
			List<ProjectFinancialWithdrawingDO> list = projectFinancialWithdrawingDAO
				.findByCondition(queryDO, order.getSortCol(), order.getSortOrder(),
					component.getFirstRecord(), component.getPageSize());
			
			List<FinancialProjectWithdrawingInfo> pageList = new ArrayList<FinancialProjectWithdrawingInfo>(
				list.size());
			for (ProjectFinancialWithdrawingDO DO : list) {
				FinancialProjectWithdrawingInfo info = new FinancialProjectWithdrawingInfo();
				BeanCopier.staticCopy(DO, info);
				info.setProject(queryByCode(info.getProjectCode()));
				pageList.add(info);
			}
			
			batchResult.setTotalAmount(projectFinancialWithdrawingDAO
				.findByConditionStatistics(queryDO));
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询理财项目计提台帐失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public ProjectFinancialInfo queryByCode(String projectCode) {
		return convertDO2Info(projectFinancialDAO.findByCode(projectCode));
	}
	
	private ProjectFinancialInfo convertDO2Info(ProjectFinancialDO DO) {
		if (DO == null) {
			return null;
		}
		ProjectFinancialInfo info = new ProjectFinancialInfo();
		BeanCopier.staticCopy(DO, info);
		info.setProductType(FinancialProductTypeEnum.getByCode(DO.getProductType()));
		info.setTermType(FinancialProductTermTypeEnum.getByCode(DO.getTermType()));
		info.setInterestType(FinancialProductInterestTypeEnum.getByCode(DO.getInterestType()));
		info.setInterestSettlementWay(InterestSettlementWayEnum.getByCode(DO
			.getInterestSettlementWay()));
		info.setStatus(FinancialProjectStatusEnum.getByCode(DO.getStatus()));
		info.setTimeUnit(TimeUnitEnum.getByCode(DO.getTimeUnit()));
		return info;
	}
	
	@Override
	public FcsBaseResult confirm(final FinancialProjectOrder order) {
		return commonProcess(order, "理财项目信息维护", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				ProjectFinancialDO project = projectFinancialDAO.findByCode(order.getProjectCode());
				if (project == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "理财项目不存在");
				}
				
				if (order.getIsConfirm() == BooleanEnum.IS) {//理财项目信息维护
				
					if (order.getActualBuyDate() == null || order.getActualExpireDate() == null
					//|| order.getActualBuyNum() <= 0
						|| StringUtil.isBlank(order.getProjectCode())) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
							"请求参数不完整");
					}
					
					//维护实际购买信息
					project.setActualBuyDate(order.getActualBuyDate());
					project.setActualExpireDate(order.getActualExpireDate());
					//已根据资金划付金额自动计算出来
					//project.setActualBuyNum(order.getActualBuyNum());
					if (order.getActualPrice().greaterThan(ZERO_MONEY)) {
						project.setActualPrice(order.getActualPrice());
					} else {
						project.setActualPrice(project.getPrice());
					}
					project.setActualHoldNum(project.getActualBuyNum());
					project.setOriginalHoldNum(project.getActualBuyNum());
					project.setStatus(FinancialProjectStatusEnum.PURCHASED.code());
					projectFinancialDAO.update(project);
				} else if (order.getIsConfirmExpire() == BooleanEnum.IS) { //到期信息维护
					project.setActualPrincipal(order.getActualPrincipal());
					project.setActualInterest(order.getActualInterest());
					project.setActualInterestRate(caculateInterestRate(project.getProjectCode(),
						project.getActualInterest(), project.getActualPrincipal()) * 100);
					project.setStatus(FinancialProjectStatusEnum.FINISH.code());
					projectFinancialDAO.update(project);
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult changeStatus(String projectCode, FinancialProjectStatusEnum status) {
		
		FcsBaseResult result = new FcsBaseResult();
		
		try {
			logger.info("修改理财产品状态 projectCode {} ,status {}", projectCode, status);
			ProjectFinancialDO project = projectFinancialDAO.findByCode(projectCode);
			if (project == null) {
				result.setSuccess(false);
				result.setMessage("理财项目不存在");
				return result;
			}
			
			project.setStatus(status.code());
			
			projectFinancialDAO.update(project);
			
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("修改理财项目状态出错 {}", e);
		}
		
		return result;
	}
	
	@Override
	public FinancialProjectInterestResult caculateInterest(String projectCode, Date calculateDate) {
		logger.info("开始计算理财项目收益 caculateInterest ：{} {}", projectCode, calculateDate);
		ProjectFinancialInfo project = queryByCode(projectCode);
		return caculateProjectInterest(project, calculateDate);
	}
	
	@Override
	public FinancialProjectInterestResult caculateProjectInterest(ProjectFinancialInfo project,
																	Date calculateDate) {
		FinancialProjectInterestResult result = new FinancialProjectInterestResult();
		try {
			
			logger.info("开始计算理财项目收益 caculateProjectInterest ：{} {}", project, calculateDate);
			
			if (project != null) {
				
				result.setProjectCode(project.getProjectCode());
				
				Date now = getSysdate();
				
				result.setProductId(project.getProductId());
				result.setProductName(project.getProductName());
				result.setProjectCode(project.getProjectCode());
				result.setProjectName(project.getProjectName());
				
				//开始时间为项目购买时间
				Date buyDate = project.getActualBuyDate();
				
				//还没购买
				if (buyDate == null) {
					result.setSuccess(true);
					return result;
				}
				
				//结束时间默认为当前时间
				if (calculateDate == null) {
					calculateDate = DateUtil
						.string2DateTimeByAutoZero(DateUtil.dtSimpleFormat(now));
				} else {
					calculateDate = DateUtil.string2DateTimeByAutoZero(DateUtil
						.dtSimpleFormat(calculateDate));
				}
				
				//当前时间已经过了项目到期时间这结束时间为项目到期时间
				if (calculateDate.after(project.getActualExpireDate())) {
					calculateDate = project.getActualExpireDate();
				}
				
				result.setBuyDate(buyDate);
				result.setCaculateDate(calculateDate);
				
				FinancialProjectStatusEnum status = project.getStatus();
				
				//以下状态项目还未购买
				if (status != FinancialProjectStatusEnum.SET_UP_DRAFT
					&& status != FinancialProjectStatusEnum.SET_UP_AUDITING
					&& status != FinancialProjectStatusEnum.SET_UP_APPROVAL
					&& status != FinancialProjectStatusEnum.COUNCIL_WAITING
					&& status != FinancialProjectStatusEnum.CAPITAL_APPLY_WAITING
					&& status != FinancialProjectStatusEnum.CAPITAL_APPLY_DRAFT
					&& status != FinancialProjectStatusEnum.CAPITAL_APPLY_AUDITING
					&& status != FinancialProjectStatusEnum.CAPITAL_APPLY_APPROVAL
					&& status != FinancialProjectStatusEnum.FAILED) {
					
					Money totalInterest = Money.zero();
					Money price = project.getActualPrice();
					double interestRate = project.getInterestRate() / 100;
					
					//持有天数
					long days = DateUtil.getDateBetween(buyDate, calculateDate) / 1000 / 3600 / 24;
					
					// 持有期投资收益=（票面单价*持有份额）* 年化利率 * 持有天数 / 365
					totalInterest = price.multiply(project.getActualBuyNum())
						.multiply(interestRate).multiply(days).divide(365);
					
					//转让部分 假设没转让时 的原收益
					Money transferInterest = Money.zero();
					//转让部分 假设没转让时 过户的原收益
					Money transferOwnershipInterest = Money.zero();
					//赎回部分 假设没赎回时 的原收益
					Money redeemInterest = Money.zero();
					
					//项目所有转让交易信息
					ProjectFinancialTradeTansferQueryOrder transferOrder = new ProjectFinancialTradeTansferQueryOrder();
					transferOrder.setProjectCode(project.getProjectCode());
					transferOrder.setPageSize(9999);
					transferOrder.setTransferTimeStart(buyDate);
					transferOrder.setTransferTimeEnd(calculateDate);
					QueryBaseBatchResult<ProjectFinancialTradeTansferInfo> transferTrades = financialProjectTransferService
						.queryTrade(transferOrder);
					for (ProjectFinancialTradeTansferInfo transferInfo : transferTrades
						.getPageList()) {
						
						long transferDays = 0;
						if (transferInfo.getIsBuyBack() == BooleanEnum.IS
							&& transferInfo.getIsConfirm() == BooleanEnum.IS) {
							//已回购则只计算 ‘转让时间’ 到 ‘回购时间’ 之间的天数
							transferDays = DateUtil.getDateBetween(transferInfo.getTransferTime(),
								transferInfo.getBuyBackTime()) / 1000 / 3600 / 24;
						} else {
							//不回购或者还没回购 则计算‘转让时间’ 到‘计算时间’之间的天数
							transferDays = DateUtil.getDateBetween(transferInfo.getTransferTime(),
								calculateDate) / 1000 / 3600 / 24;
						}
						
						Money tInterest = price.multiply(transferInfo.getTransferNum())
							.multiply(interestRate).multiply(transferDays).divide(365);
						
						transferInterest.addTo(tInterest); //累加转让部分收益
						if (transferInfo.getIsTransferOwnership() == BooleanEnum.IS) {
							transferOwnershipInterest.addTo(tInterest); //累加过户部分的收益
						}
					}
					
					//赎回交易信息
					ProjectFinancialTradeRedeemQueryOrder redeemOrder = new ProjectFinancialTradeRedeemQueryOrder();
					redeemOrder.setProjectCode(project.getProjectCode());
					redeemOrder.setPageSize(9999);
					redeemOrder.setRedeemTimeStart(buyDate);
					redeemOrder.setRedeemTimeEnd(calculateDate);
					QueryBaseBatchResult<ProjectFinancialTradeRedeemInfo> redeemTrades = financialProjectRedeemService
						.queryTrade(redeemOrder);
					for (ProjectFinancialTradeRedeemInfo redeemInfo : redeemTrades.getPageList()) {
						//计算‘赎回时间’ 到 ‘计算时间’ 之间的天数
						long redeemDays = DateUtil.getDateBetween(redeemInfo.getRedeemTime(),
							calculateDate) / 1000 / 3600 / 24;
						Money rInterest = price.multiply(redeemInfo.getRedeemNum())
							.multiply(interestRate).multiply(redeemDays).divide(365);
						redeemInterest.addTo(rInterest);
					}
					
					//持有期收益
					result.setHoldingPeriodInterest(totalInterest.subtract(transferInterest)
						.subtract(redeemInterest));
					//result.setHoldingPeriodInterest(totalInterest);
					
					//计提收益(未过户的转让部分收益也要计算)
					result.setWithdrawingInterest(totalInterest.subtract(transferOwnershipInterest)
						.subtract(redeemInterest));
					
					//最新的数据
					ProjectFinancialWithdrawingDO latestWithdraw = projectFinancialWithdrawingDAO
						.findLatest(project.getProjectCode());
					if (latestWithdraw != null) {
						result.setWithdrawedInterest(latestWithdraw.getTotalInterest());
					}
				}
				
				result.setSuccess(true);
				result.setMessage("SUCCESS");
				
				logger.info("计算理财项目收益结束 caculateInterest ：{}", result);
				
			} else {
				result.setSuccess(false);
				result.setMessage("理财项目不存在");
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("计算理财项目持有期收益出错");
			logger.error("计算理财项目持有期收益出错 {}", e);
		}
		
		return result;
	}
	
	@Override
	public Money caculateTransferInterest(String projectCode, Money transferPrice,
											long transferNum, Date transferDate) {
		
		Money interest = Money.zero();
		try {
			logger.info("开始计算理财项目转让收益 ：项目编号 {} , 转让单价 {} , 转让份额 {} , 转让日期 {}", projectCode,
				transferPrice, transferNum, transferDate);
			
			ProjectFinancialInfo project = queryByCode(projectCode);
			if (project != null) {
				transferDate = DateUtil.string2DateTimeByAutoZero(DateUtil
					.dtSimpleFormat(transferDate));
				
				//持有天数
				long days = DateUtil.getDateBetween(project.getActualBuyDate(), transferDate) / 1000 / 3600 / 24;
				
				//持有期利息
				Money holdingPeriodInterest = project.getActualPrice().multiply(transferNum)
					.multiply(project.getInterestRate()).divide(100).multiply(days).divide(365);
				
				//转让收益
				Money transferInterest = transferPrice.multiply(transferNum).subtract(
					project.getActualPrice().multiply(transferNum));
				
				//转让收益=转让单价*转让份额-本金-持有期收益
				interest.addTo(transferInterest).subtractFrom(holdingPeriodInterest);
			}
			logger.info("计算理财项目转让收益结束：{} , {}", projectCode, interest);
		} catch (Exception e) {
			logger.error("计算转让收益出错 {}", e);
		}
		
		return interest;
	}
	
	@Override
	public Money caculateRedeemInterest(String projectCode, Money redeemPrice, long redeemNum,
										Date redeemDate) {
		Money interest = Money.zero();
		try {
			logger.info("开始计算理财项目赎回收益 ：项目编号 {} , 赎回单价 {} , 赎回份额 {} , 赎回日期 {}", projectCode,
				redeemPrice, redeemNum, redeemDate);
			Date now = getSysdate();
			ProjectFinancialInfo project = queryByCode(projectCode);
			if (project != null) {
				
				//默认赎回时间为当前时间
				if (redeemDate == null) {
					redeemDate = now;
				}
				
				redeemDate = DateUtil
					.string2DateTimeByAutoZero(DateUtil.dtSimpleFormat(redeemDate));
				
				//默认价格为购买价格
				if (redeemPrice == null || !redeemPrice.greaterThan(ZERO_MONEY)) {
					redeemPrice = project.getActualPrice();
				}
				
				//持有天数
				long days = DateUtil.getDateBetween(project.getActualBuyDate(), redeemDate) / 1000 / 3600 / 24;
				
				//持有期利息
				Money holdingPeriodInterest = redeemPrice.multiply(redeemNum)
					.multiply(project.getInterestRate()).divide(100).multiply(days).divide(365);
				
				interest.addTo(holdingPeriodInterest);
			}
			logger.info("计算理财项目赎回收益结束：{} , {}", projectCode, interest);
		} catch (Exception e) {
			logger.error("计算赎回收益出错 {}", e);
		}
		return interest;
	}
	
	@Override
	public double caculateInterestRate(String projectCode, Money interest, Money principal) {
		
		double rate = 0;
		try {
			
			logger.info("开始计算收益率 ：项目编号 {} , 收益 {} , 本金{}", projectCode, interest.getAmount(),
				principal.getAmount());
			
			Date now = getSysdate();
			if (interest.getCent() == 0 || principal.getCent() == 0) {
				return 0;
			}
			ProjectFinancialInfo project = queryByCode(projectCode);
			if (project != null) {
				
				if (project.getStatus() == FinancialProjectStatusEnum.EXPIRE) {
					now = DateUtil.string2DateTimeByAutoZero(DateUtil.dtSimpleFormat(project
						.getActualExpireDate()));
				} else {
					now = DateUtil.string2DateTimeByAutoZero(DateUtil.dtSimpleFormat(now));
				}
				//持有天数
				long days = DateUtil.getDateBetween(project.getActualBuyDate(), now) / 1000 / 3600 / 24;
				if (days == 0)
					return 0;
				rate = (double) interest.getCent() / (double) principal.getCent() * (double) 365
						/ (double) days;
			}
			
		} catch (Exception e) {
			logger.error("计算收益率出错 {}", e);
			
		}
		return rate;
	}
	
	@Override
	public long redeemingNum(String projectCode, long applyId) {
		long applyingNum = 0;
		try {
			FinancialProjectRedeemFormQueryOrder order = new FinancialProjectRedeemFormQueryOrder();
			order.setProjectCode(projectCode);
			order.setPageSize(999);
			QueryBaseBatchResult<FinancialProjectRedeemFormInfo> redeemForm = financialProjectRedeemService
				.queryPage(order);
			for (FinancialProjectRedeemFormInfo formInfo : redeemForm.getPageList()) {
				if (applyId > 0 && formInfo.getApplyId() == applyId) {//不计算当前
					continue;
				}
				if (formInfo.getFormStatus() == FormStatusEnum.DRAFT
					|| formInfo.getFormStatus() == FormStatusEnum.SUBMIT
					|| formInfo.getFormStatus() == FormStatusEnum.CANCEL
					|| formInfo.getFormStatus() == FormStatusEnum.AUDITING
					|| formInfo.getFormStatus() == FormStatusEnum.BACK) {
					applyingNum += formInfo.getRedeemNum();
				} else if (formInfo.getFormStatus() == FormStatusEnum.APPROVAL) { //已通过的审核
					ProjectFinancialTradeRedeemInfo trade = financialProjectRedeemService
						.queryTradeByApplyId(formInfo.getApplyId());
					if (trade == null) { //还未做赎回信息维护的（实际赎回份额还没从持有份额中减掉）
						applyingNum += formInfo.getRedeemNum();
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询赎回申请中份额出错 {}", e);
		}
		return applyingNum;
	}
	
	@Override
	public long transferingNum(String projectCode, long applyId) {
		
		long applyingNum = 0;
		try {
			FinancialProjectTransferFormQueryOrder order = new FinancialProjectTransferFormQueryOrder();
			order.setProjectCode(projectCode);
			order.setPageSize(999);
			QueryBaseBatchResult<FinancialProjectTransferFormInfo> transferForm = financialProjectTransferService
				.queryPage(order);
			for (FinancialProjectTransferFormInfo formInfo : transferForm.getPageList()) {
				if (applyId > 0 && formInfo.getApplyId() == applyId) {//不计算当前
					continue;
				}
				if (formInfo.getFormStatus() == FormStatusEnum.DRAFT
					|| formInfo.getFormStatus() == FormStatusEnum.SUBMIT
					|| formInfo.getFormStatus() == FormStatusEnum.CANCEL
					|| formInfo.getFormStatus() == FormStatusEnum.AUDITING
					|| formInfo.getFormStatus() == FormStatusEnum.BACK) {
					applyingNum += formInfo.getTransferNum();
				} else if (formInfo.getFormStatus() == FormStatusEnum.APPROVAL) { //已通过的审核
					ProjectFinancialTradeTansferInfo trade = financialProjectTransferService
						.queryTradeByApplyId(formInfo.getApplyId());
					if (formInfo.getCouncilStatus() != ProjectCouncilStatusEnum.COUNCIL_DENY //会议未通过的不算
						&& trade == null) { //还未做转让信息维护的（实际转让份额还没从持有份额中减掉）
						applyingNum += formInfo.getTransferNum();
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询转让申请中份额出错 {}", e);
		}
		return applyingNum;
		
	}
}
