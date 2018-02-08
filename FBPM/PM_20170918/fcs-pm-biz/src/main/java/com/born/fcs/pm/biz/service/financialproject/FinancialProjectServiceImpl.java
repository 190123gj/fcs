package com.born.fcs.pm.biz.service.financialproject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.fm.ws.enums.ForecastChildTypeOneEnum;
import com.born.fcs.fm.ws.enums.ForecastChildTypeTwoEnum;
import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormFeeOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountOrder;
import com.born.fcs.fm.ws.service.common.ReceiptPaymentFormService;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.daointerface.ProjectFinancialSettlementDAO;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialSettlementDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialWithdrawingDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
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
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectRedeemFormInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectTransferFormInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectWithdrawingInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialSettlementInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialTradeRedeemInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialTradeTansferInfo;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectRedeemFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectTransferFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectWithdrawingQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialSettlementOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialSettlementQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeRedeemQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeTansferQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryAmountBatchResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.financialproject.FinancialProjectInterestResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
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
	
	@Autowired
	private ForecastService forecastServiceClient;
	
	@Autowired
	private ProjectFinancialSettlementDAO projectFinancialSettlementDAO;
	
	@Autowired
	ReceiptPaymentFormService receiptPaymentFormServiceClient;
	
	@Autowired
	ProjectRelatedUserService projectRelatedUserService;
	
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
			
			if (order.getHoldNum() != null) {
				projectFinancial.setOriginalHoldNum(order.getHoldNum());
			} else {
				projectFinancial.setOriginalHoldNum(-1.0f); //不限制这个作为查询条件
			}
			
			if (order.getIsRoll() != null)
				projectFinancial.setIsRoll(order.getIsRoll().code());
			
			if (order.getIsOpen() != null)
				projectFinancial.setIsOpen(order.getIsOpen().code());
			
			if (order.getInterestSettlementWay() != null)
				projectFinancial.setInterestSettlementWay(order.getInterestSettlementWay().code());
			
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
				pageList.add(DoConvert.convertFinancialProjectInfo(DO));
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
	
	@SuppressWarnings("deprecation")
	@Override
	public QueryBaseBatchResult<ProjectFinancialInfo> queryPurchasing(	FinancialProjectQueryOrder order) {
		
		QueryBaseBatchResult<ProjectFinancialInfo> batchResult = new QueryBaseBatchResult<ProjectFinancialInfo>();
		
		try {
			ProjectFinancialDO projectFinancial = new ProjectFinancialDO();
			BeanCopier.staticCopy(order, projectFinancial, UnBoxingConverter.getInstance());
			
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("id");
			}
			if (StringUtil.isBlank(order.getSortOrder())) {
				order.setSortOrder("DESC");
			}
			Map<String, Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put("productId", order.getProductId());
			paramMap.put("projectCode", order.getProjectCode());
			paramMap.put("productName", order.getProductName());
			
			paramMap.put("loginUserId", order.getLoginUserId());
			paramMap.put("deptIdList", order.getDeptIdList());
			paramMap.put("relatedRoleList", order.getRelatedRoleList());
			paramMap.put("busiManagerId", order.getBusiManagerId());
			paramMap.put("issueInstitution", order.getIssueInstitution());
			long totalCount = busiDAO.queryPurchasingFinancialProjectCount(paramMap);
			PageComponent component = new PageComponent(order, totalCount);
			
			paramMap.put("limitStart", component.getFirstRecord());
			paramMap.put("pageSize", component.getPageSize());
			paramMap.put("sortOrder", order.getSortOrder());
			paramMap.put("sortCol", order.getSortCol());
			
			List<FProjectFinancialDO> list = busiDAO.queryPurchasingFinancialProject(paramMap);
			
			List<ProjectFinancialInfo> pageList = new ArrayList<ProjectFinancialInfo>(list.size());
			for (FProjectFinancialDO DO : list) {
				
				ProjectFinancialInfo info = new ProjectFinancialInfo();
				BeanCopier.staticCopy(DO, info);
				info.setProductType(FinancialProductTypeEnum.getByCode(DO.getProductType()));
				info.setTermType(FinancialProductTermTypeEnum.getByCode(DO.getTermType()));
				info.setInterestType(FinancialProductInterestTypeEnum.getByCode(DO
					.getInterestType()));
				info.setInterestSettlementWay(InterestSettlementWayEnum.getByCode(DO
					.getInterestSettlementWay()));
				info.setTimeUnit(TimeUnitEnum.getByCode(DO.getTimeUnit()));
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询待划付理财项目失败" + e.getMessage(), e);
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
			
			long totalCount = projectFinancialWithdrawingDAO.findByConditionCount(queryDO,
				order.getWithdrawDateStart(), order.getWithdrawDateEnd(),
				order.getWithdrawMonthStart(), order.getWithdrawMonthEnd());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<ProjectFinancialWithdrawingDO> list = projectFinancialWithdrawingDAO
				.findByCondition(queryDO, order.getWithdrawDateStart(), order.getWithdrawDateEnd(),
					order.getWithdrawMonthStart(), order.getWithdrawMonthEnd(), order.getSortCol(),
					order.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			
			List<FinancialProjectWithdrawingInfo> pageList = new ArrayList<FinancialProjectWithdrawingInfo>(
				list.size());
			DateFormat dateFormat = DateUtil.getFormat("yyyy-MM");
			Calendar calendar = Calendar.getInstance();
			for (ProjectFinancialWithdrawingDO DO : list) {
				FinancialProjectWithdrawingInfo info = new FinancialProjectWithdrawingInfo();
				BeanCopier.staticCopy(DO, info);
				info.setWithdrawFinish(BooleanEnum.getByCode(DO.getWithdrawFinish()));
				//info.setProject(queryByCode(info.getProjectCode()));
				//已经结息的就取当前计息时间
				if (BooleanEnum.YES == info.getWithdrawFinish()) {
					info.setWithdrawedDate(info.getWithdrawDate());
				} else { //没结息的取上个月的结息时间
					calendar.setTime(info.getWithdrawDate());
					calendar.add(Calendar.MONTH, -1);
					ProjectFinancialWithdrawingDO lastMonthWithdraw = null;
					String lastMonth = dateFormat.format(calendar.getTime());
					if ("RECEIPT".equals(info.getWithdrawType())) { //应收计提
						lastMonthWithdraw = projectFinancialWithdrawingDAO
							.findByProjectCodeAndMonth(info.getProjectCode(), lastMonth);
					} else { //应付计提
						lastMonthWithdraw = projectFinancialWithdrawingDAO.findByTradeIdAndMonth(
							info.getTransferTradeId(), lastMonth);
					}
					if (lastMonthWithdraw != null)
						info.setWithdrawedDate(lastMonthWithdraw.getWithdrawDate());
				}
				pageList.add(info);
			}
			
			batchResult.setTotalAmount(projectFinancialWithdrawingDAO.findByConditionStatistics(
				queryDO, order.getWithdrawDateStart(), order.getWithdrawDateEnd(),
				order.getWithdrawMonthStart(), order.getWithdrawMonthEnd()));
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
		return DoConvert.convertFinancialProjectInfo(projectFinancialDAO.findByCode(projectCode));
	}
	
	@Override
	public FcsBaseResult confirm(final FinancialProjectOrder order) {
		return commonProcess(order, "理财项目信息维护", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				ProjectFinancialDO project = projectFinancialDAO.findByCode(order.getProjectCode());
				if (project == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "理财项目不存在");
				}
				
				if (order.getIsConfirm() == BooleanEnum.IS) {//理财项目信息维护
				
					if (order.getActualBuyDate() == null || order.getActualExpireDate() == null
						|| order.getInterestRate() <= 0
						|| StringUtil.isBlank(order.getProjectCode())) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
							"请求参数不完整");
					}
					
					//维护实际购买信息
					project.setActualBuyDate(order.getActualBuyDate());
					project.setActualExpireDate(order.getActualExpireDate());
					project.setCycleExpireDate(project.getActualExpireDate());
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
					project.setInterestRate(order.getInterestRate());
					projectFinancialDAO.update(project);
					
					Calendar calendarNow = Calendar.getInstance();
					calendarNow.setTime(now);
					Calendar calendarBuyDate = Calendar.getInstance();
					calendarBuyDate.setTime(project.getActualBuyDate());
					int year = calendarNow.get(Calendar.YEAR) - calendarBuyDate.get(Calendar.YEAR);
					int month = calendarNow.get(Calendar.MONTH)
								- calendarBuyDate.get(Calendar.MONTH);
					
					//当前维护时间和实际购买时间之间相差的月份
					int betweenMonth = year * 12 + month;
					if (betweenMonth >= 0) {
						Calendar calendarCaculate = Calendar.getInstance();
						calendarCaculate.setTime(project.getActualBuyDate());
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
						boolean isNow = false;
						Money lastMonthTotal = Money.zero();
						for (int i = 0; i <= betweenMonth; i++) {
							if (i == betweenMonth) {//本月就是当前
								calendarCaculate = calendarNow;
								isNow = true;
							} else { //如果不是本月则计算日为当月最后一天
								if (i > 0)
									calendarCaculate.add(Calendar.MONTH, 1);
								calendarCaculate.set(Calendar.DAY_OF_MONTH,
									calendarCaculate.getActualMaximum(Calendar.DAY_OF_MONTH));
							}
							String thisMonth = sdf.format(calendarCaculate.getTime());
							ProjectFinancialWithdrawingDO withdrawDO = new ProjectFinancialWithdrawingDO();
							withdrawDO.setWithdrawMonth(thisMonth);
							withdrawDO.setWithdrawType("RECEIPT");
							withdrawDO.setWithdrawFinish("NO");
							withdrawDO.setProjectCode(project.getProjectCode());
							withdrawDO.setYearDayNum(project.getYearDayNum());
							long thisMonthCount = 0;
							Date caculateDate = calendarCaculate.getTime();
							if (isNow) //当月要查询一下是否已经有记录了
								thisMonthCount = projectFinancialWithdrawingDAO
									.findByConditionCount(withdrawDO, null, null, null, null);
							if (thisMonthCount == 0) {//当月没有计提数据就新增一条
								withdrawDO.setBuyDate(project.getActualBuyDate());
								withdrawDO.setExpireDate(project.getPreFinishTime() == null ? project
									.getCycleExpireDate() : project.getPreFinishTime());
								if (StringUtil.equals(project.getIsOpen(), BooleanEnum.IS.code()))
									withdrawDO.setExpireDate(null);
								withdrawDO.setInterestRate(project.getInterestRate());
								withdrawDO.setProductId(project.getProductId());
								withdrawDO.setProductName(project.getProductName());
								withdrawDO.setRawAddTime(now);
								//计算下当时的计提情况
								withdrawDO.setWithdrawDate(caculateDate);
								withdrawDO.setWithdrawDay(DateUtil.dtSimpleFormat(caculateDate));
								//计算收益
								ProjectFinancialInfo projectInfo = DoConvert
									.convertFinancialProjectInfo(project);
								FinancialProjectInterestResult interestResult = caculateProjectInterest(
									projectInfo, caculateDate);
								Money withdrawingInterest = interestResult.getWithdrawingInterest();
								//计息本金
								Money caculatePrincipal = interestResult.getCaculatePrincipal();
								withdrawDO.setPrincipal(caculatePrincipal);
								if (isNow) {
									Calendar calendar = Calendar.getInstance();
									calendar.setTime(caculateDate);
									calendar.add(Calendar.DAY_OF_MONTH, 1);
									if (calendar.get(Calendar.DAY_OF_MONTH) == 1) { //如果是最后一天
										withdrawDO.setWithdrawFinish("YES");
										//累计结息金额
										withdrawDO.setTotalInterest(withdrawingInterest);
										//本月已结息
										withdrawDO.setWithdrawedInterest(withdrawingInterest
											.subtract(lastMonthTotal));
									} else { //尚未结息 本月累计就是上月累计
										//如果项目到期就结息
										if (projectInfo.isExipre(now)) {
											withdrawDO.setWithdrawFinish("YES");
											withdrawDO.setTotalInterest(withdrawingInterest);
											withdrawDO.setWithdrawingInterest(Money.zero());
											withdrawDO.setWithdrawedInterest(withdrawingInterest
												.subtract(lastMonthTotal));
										} else {
											withdrawDO.setTotalInterest(lastMonthTotal);
											//当前实时结息金额
											withdrawDO.setWithdrawingInterest(withdrawingInterest
												.subtract(lastMonthTotal));
										}
										
									}
								} else {
									withdrawDO.setWithdrawFinish("YES");
									//累计结息金额
									withdrawDO.setTotalInterest(withdrawingInterest);
									//本月已结息
									withdrawDO.setWithdrawedInterest(withdrawingInterest
										.subtract(lastMonthTotal));
								}
								lastMonthTotal = withdrawDO.getTotalInterest();
								projectFinancialWithdrawingDAO.insert(withdrawDO);
							}
						}
					}
					
					//维护后同步预测数据到资金系统 
					syncForecast(project, now);
					
				} else if (order.getIsConfirmExpire() == BooleanEnum.IS) { //到期信息维护
				
					String oldRoll = project.getIsRoll();
					String oldOpen = project.getIsOpen();
					
					if (StringUtil.isNotEmpty(order.getIsRoll()))
						project.setIsRoll(order.getIsRoll());
					if (StringUtil.isNotEmpty(order.getIsOpen()))
						project.setIsOpen(order.getIsOpen());
					
					if (order.getActualPrincipal().greaterThan(ZERO_MONEY)) { //只是维护实际值
					
						project.setIsRoll(oldRoll);
						project.setIsOpen(oldOpen);
						ProjectFinancialInfo projectInfo = DoConvert
							.convertFinancialProjectInfo(project);
						if (projectInfo.getStatus() != FinancialProjectStatusEnum.EXPIRE) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS, "尚未到期");
						}
						
						//维护了金额则当前就是实际到期日
						project.setActualExpireDate(now);
						project.setCycleExpireDate(now);
						project.setActualPrincipal(order.getActualPrincipal());
						project.setActualInterest(order.getActualInterest());
						project.setActualInterestRate(caculateInterestRate(
							project.getProjectCode(), project.getActualInterest(), project
								.getActualPrincipal(),
							(project.getPreFinishTime() == null ? project.getCycleExpireDate()
								: project.getPreFinishTime())) * 100);
						project.setOriginalHoldNum(0);
						project.setActualHoldNum(0);
						project.setStatus(FinancialProjectStatusEnum.FINISH.code());
						
						//同步赎回信息到资金系统收款单
						try {
							ReceiptPaymentFormOrder rpOrder = new ReceiptPaymentFormOrder();
							rpOrder.setContractNo(null);
							rpOrder.setSourceForm(SourceFormEnum.FINANCIAL_REDEEM_EXPIRE);
							rpOrder.setSourceFormId(String.valueOf(project.getProjectId()));
							rpOrder.setSourceFormSys(SystemEnum.PM);
							rpOrder.setProjectCode(project.getProjectCode());
							rpOrder.setProjectName(project.getProductName());
							rpOrder.setUserId(project.getCreateUserId());
							rpOrder.setUserName(project.getCreateUserName());
							rpOrder.setUserAccount(project.getCreateUserAccount());
							rpOrder.setDeptId(project.getDeptId());
							rpOrder.setDeptCode(project.getDeptCode());
							rpOrder.setDeptName(project.getDeptName());
							rpOrder.setRemark("理财产品到期赎回");
							
							List<ReceiptPaymentFormFeeOrder> feeOrderList = Lists.newArrayList();
							//理财产品赎回本金
							ReceiptPaymentFormFeeOrder feeOrder = new ReceiptPaymentFormFeeOrder();
							feeOrder.setFeeType(SubjectCostTypeEnum.FINANCING_PRODUCT_REDEMPTION);
							feeOrder.setAmount(project.getActualPrincipal());
							feeOrder.setRemark("实收本金");
							feeOrderList.add(feeOrder);
							feeOrder = new ReceiptPaymentFormFeeOrder();
							feeOrder.setFeeType(SubjectCostTypeEnum.INANCING_INVEST_INCOME);
							feeOrder.setAmount(project.getActualInterest());
							feeOrder.setRemark("实收利息");
							feeOrderList.add(feeOrder);
							
							rpOrder.setFeeOrderList(feeOrderList);
							logger.info("同步到期赎回信息到资金系统：{}", rpOrder);
							receiptPaymentFormServiceClient.add(rpOrder);
						} catch (Exception e) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.DO_ACTION_STATUS_ERROR, "同步到期赎回信息到资金系统出错");
						}
					}
					projectFinancialDAO.update(project);
				}
				
				FcsBaseResult result = (FcsBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				//不能传DO
				ProjectFinancialInfo projectInfo = DoConvert.convertFinancialProjectInfo(project);
				result.setReturnObject(projectInfo);
				result.setKeyId(project.getProjectId());
				return null;
			}
		}, null, null);
	}
	
	/**
	 * 同步预测数据到资金
	 * @param project
	 */
	private void syncForecast(ProjectFinancialDO project, Date now) {
		try {
			if (project.getActualExpireDate().after(now)) {
				ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
				forecastAccountOrder.setForecastStartTime(project.getActualExpireDate());
				Money amount = Money.zero();
				amount.addTo(project.getActualPrice().multiply(project.getActualBuyNum()));
				//持有天数
				long days = DateUtil.getDateBetween(project.getActualBuyDate(),
					project.getActualExpireDate()) / 1000 / 3600 / 24;
				// 持有期投资收益=（票面单价*持有份额）* 年化利率 * 持有天数 / 365
				Money totalInterest = project.getActualPrice().multiply(project.getActualBuyNum())
					.multiply(project.getInterestRate()).divide(100).multiply(days)
					.divide(project.getYearDayNum());
				amount.addTo(totalInterest);
				forecastAccountOrder.setAmount(amount);
				forecastAccountOrder.setForecastMemo("理财产品赎回（" + project.getProjectCode() + "）");
				forecastAccountOrder.setForecastType(ForecastTypeEnum.IN_LCYW);
				forecastAccountOrder.setFundDirection(FundDirectionEnum.IN);
				forecastAccountOrder.setOrderNo(project.getProjectCode() + "_"
												+ ForecastFeeTypeEnum.REDEEM.code());
				
				//设置中长期属性
				forecastAccountOrder.setForecastChildTypeOne(ForecastChildTypeOneEnum
					.getByCode(project.getTermType()));
				//设置产品类型
				forecastAccountOrder.setForecastChildTypeTwo(ForecastChildTypeTwoEnum
					.getByCode(project.getProductType()));
				
				forecastAccountOrder.setSystemForm(SystemEnum.PM);
				forecastAccountOrder.setUsedDeptId(String.valueOf(project.getDeptId()));
				forecastAccountOrder.setUsedDeptName(project.getDeptName());
				forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.REDEEM);
				forecastAccountOrder.setProjectCode(project.getProjectCode());
				forecastAccountOrder.setCustomerId(project.getProductId());
				forecastAccountOrder.setCustomerName(project.getProductName());
				logger.info("理财项目信息维护后资金流入预测,projectCode：{}, forecastAccountOrder：{} ",
					project.getProjectCode(), forecastAccountOrder);
				forecastServiceClient.add(forecastAccountOrder);
			}
		} catch (Exception e) {
			logger.error("同步理财项目预测数据到资金系统出错{}", e);
		}
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
			logger.info("开始计算理财项目收益 caculateProjectInterest ：{} {}", project,
				DateUtil.dtSimpleFormat(calculateDate));
			
			if (project != null) {
				
				result.setProjectCode(project.getProjectCode());
				
				Date now = getSysdate();
				
				result.setProductId(project.getProductId());
				result.setProductName(project.getProductName());
				result.setProjectCode(project.getProjectCode());
				
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
				if (project.isExipre(now)) {
					calculateDate = project.getCycleExpireDate();
				}
				
				//当前时间已经过了项目提前完成时间这结束时间为项目完成时间
				if (project.getPreFinishTime() != null
					&& calculateDate.after(project.getPreFinishTime())) {
					calculateDate = project.getPreFinishTime();
				}
				
				result.setBuyDate(buyDate);
				result.setCaculateDate(calculateDate);
				
				FinancialProjectStatusEnum status = project.getStatus();
				
				//以下状态项目还未购买
				if (status != FinancialProjectStatusEnum.PURCHASING) {
					
					Money totalInterest = Money.zero();
					Money price = project.getActualPrice();
					double interestRate = project.getInterestRate() / 100;
					
					//持有天数
					long days = DateUtil.getDateBetween(buyDate, calculateDate) / 1000 / 3600 / 24;
					if (days < 0)
						days = 0;
					
					// 持有期投资收益=（票面单价*持有份额）* 年化利率 * 持有天数 / 365
					totalInterest = price.multiply(project.getActualBuyNum())
						.multiply(interestRate).multiply(days).divide(project.getYearDayNum());
					
					//转让部分 假设没转让时 的原收益（过户且不回购的才算-减持有份数的才算）
					Money transferInterest = Money.zero();
					//转让部分 假设没转让时 过户的原收益 //过户不回购的才不计提
					Money transferOwnershipInterest = Money.zero();
					//赎回部分 假设没赎回时 的原收益
					Money redeemInterest = Money.zero();
					
					//项目所有转让交易信息
					ProjectFinancialTradeTansferQueryOrder transferOrder = new ProjectFinancialTradeTansferQueryOrder();
					transferOrder.setProjectCode(project.getProjectCode());
					transferOrder.setPageSize(100);
					transferOrder.setTransferTimeStart(buyDate);
					transferOrder.setTransferTimeEnd(calculateDate);
					QueryBaseBatchResult<ProjectFinancialTradeTansferInfo> transferTrades = financialProjectTransferService
						.queryTrade(transferOrder);
					for (ProjectFinancialTradeTansferInfo transferInfo : transferTrades
						.getPageList()) {
						
						//过户不回购-才减少持有份数
						if (transferInfo.getIsTransferOwnership() == BooleanEnum.IS
							&& transferInfo.getIsBuyBack() != BooleanEnum.IS) {
							long transferDays = 0;
							//							if (transferInfo.getIsBuyBack() == BooleanEnum.IS
							//								&& transferInfo.getIsConfirm() == BooleanEnum.IS) {
							//								//已回购则只计算 ‘转让时间’ 到 ‘回购时间’ 之间的天数
							//								transferDays = DateUtil.getDateBetween(
							//									transferInfo.getTransferTime(), transferInfo.getBuyBackTime()) / 1000 / 3600 / 24;
							//							} else {
							//								//不回购或者还没回购 则计算‘转让时间’ 到‘计算时间’之间的天数
							transferDays = DateUtil.getDateBetween(transferInfo.getTransferTime(),
								calculateDate) / 1000 / 3600 / 24;
							//							}
							if (transferDays < 0)
								transferDays = 0;
							Money tInterest = price.multiply(transferInfo.getTransferNum())
								.multiply(interestRate).multiply(transferDays)
								.divide(project.getYearDayNum());
							transferInterest.addTo(tInterest); //累加转让部分收益
							transferOwnershipInterest.addTo(tInterest); //累加过户部分的收益
						}
					}
					
					//赎回交易信息
					ProjectFinancialTradeRedeemQueryOrder redeemOrder = new ProjectFinancialTradeRedeemQueryOrder();
					redeemOrder.setProjectCode(project.getProjectCode());
					redeemOrder.setPageSize(100);
					redeemOrder.setRedeemTimeStart(buyDate);
					redeemOrder.setRedeemTimeEnd(calculateDate);
					QueryBaseBatchResult<ProjectFinancialTradeRedeemInfo> redeemTrades = financialProjectRedeemService
						.queryTrade(redeemOrder);
					for (ProjectFinancialTradeRedeemInfo redeemInfo : redeemTrades.getPageList()) {
						//计算‘赎回时间’ 到 ‘计算时间’ 之间的天数
						long redeemDays = DateUtil.getDateBetween(redeemInfo.getRedeemTime(),
							calculateDate) / 1000 / 3600 / 24;
						
						if (redeemDays < 0)
							redeemDays = 0;
						
						Money rInterest = price.multiply(redeemInfo.getRedeemNum())
							.multiply(interestRate).multiply(redeemDays)
							.divide(project.getYearDayNum());
						redeemInterest.addTo(rInterest);
					}
					
					//持有期收益
					result.setHoldingPeriodInterest(totalInterest.subtract(transferInterest)
						.subtract(redeemInterest));
					//result.setHoldingPeriodInterest(totalInterest);
					
					//计提收益
					result.setWithdrawingInterest(totalInterest.subtract(transferOwnershipInterest)
						.subtract(redeemInterest));
					
					//最新的计提利息数据
					ProjectFinancialWithdrawingDO latestWithdraw = projectFinancialWithdrawingDAO
						.findLatest(project.getProjectCode());
					if (latestWithdraw != null) {
						result.setWithdrawedInterest(latestWithdraw.getTotalInterest());
					}
				}
				
				result.setSuccess(true);
				result.setProject(project);
				//此时的本金
				result.setCaculatePrincipal(project.getActualPrice().multiply(
					project.getOriginalHoldNum()));
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
											double transferNum, Date transferDate) {
		
		//预计转让收益=转让价格-本金-持有收益
		//     转让价格=转让份额*转让单价
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
					.multiply(project.getInterestRate()).divide(100).multiply(days)
					.divide(project.getYearDayNum());
				
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
	public Money caculateRedeemInterest(String projectCode, Money redeemPrice, double redeemNum,
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
					.multiply(project.getInterestRate()).divide(100).multiply(days)
					.divide(project.getYearDayNum());
				
				interest.addTo(holdingPeriodInterest);
			}
			logger.info("计算理财项目赎回收益结束：{} , {}", projectCode, interest);
		} catch (Exception e) {
			logger.error("计算赎回收益出错 {}", e);
		}
		return interest;
	}
	
	@Override
	public double caculateInterestRate(String projectCode, Money interest, Money principal,
										Date caculateDate) {
		double rate = 0;
		try {
			logger.info("开始计算收益率 ：项目编号 {} , 收益 {} , 本金{}", projectCode, interest.getAmount(),
				principal.getAmount());
			
			if (caculateDate == null)
				caculateDate = getSysdate();
			
			if (interest.getCent() == 0 || principal.getCent() == 0) {
				return 0;
			}
			ProjectFinancialInfo project = queryByCode(projectCode);
			if (project != null) {
				if (project.getStatus() == FinancialProjectStatusEnum.EXPIRE
					|| project.getStatus() == FinancialProjectStatusEnum.FINISH) {
					caculateDate = DateUtil.string2DateTimeByAutoZero(DateUtil
						.dtSimpleFormat(project.getPreFinishTime() == null ? project
							.getActualExpireDate() : project.getPreFinishTime()));
				} else {
					caculateDate = DateUtil.string2DateTimeByAutoZero(DateUtil
						.dtSimpleFormat(caculateDate));
				}
				//持有天数
				long days = DateUtil.getDateBetween(project.getActualBuyDate(), caculateDate) / 1000 / 3600 / 24;
				if (days <= 0)
					return 0;
				
				rate = (double) interest.getCent() / (double) principal.getCent()
						* project.getYearDayNum() / (double) days;
			}
			
		} catch (Exception e) {
			logger.error("计算收益率出错 {}", e);
			
		}
		return rate;
	}
	
	@Override
	public double redeemingNum(String projectCode, long applyId) {
		BigDecimal applyingNum = new BigDecimal(0);
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
					BigDecimal redeemNum = new BigDecimal(Double.toString(formInfo.getRedeemNum()));
					applyingNum = applyingNum.add(redeemNum);
				} else if (formInfo.getFormStatus() == FormStatusEnum.APPROVAL) { //已通过的审核
					ProjectFinancialTradeRedeemInfo trade = financialProjectRedeemService
						.queryTradeByApplyId(formInfo.getApplyId());
					if (trade == null) { //还未做赎回信息维护的（实际赎回份额还没从可赎回份额中减掉）
						BigDecimal redeemNum = new BigDecimal(Double.toString(formInfo
							.getRedeemNum()));
						applyingNum = applyingNum.add(redeemNum);
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询赎回申请中份额出错 {}", e);
		}
		return applyingNum.doubleValue();
	}
	
	@Override
	public double transferingNum(String projectCode, long applyId) {
		
		BigDecimal applyingNum = new BigDecimal(0);
		try {
			FinancialProjectTransferFormQueryOrder order = new FinancialProjectTransferFormQueryOrder();
			order.setProjectCode(projectCode);
			order.setPageSize(100);
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
					BigDecimal transferNum = new BigDecimal(Double.toString(formInfo
						.getTransferNum()));
					applyingNum = applyingNum.add(transferNum);
				} else if (formInfo.getFormStatus() == FormStatusEnum.APPROVAL) { //已通过的审核
					ProjectFinancialTradeTansferInfo trade = financialProjectTransferService
						.queryTradeByApplyId(formInfo.getApplyId());
					if (formInfo.getCouncilStatus() != ProjectCouncilStatusEnum.COUNCIL_DENY //会议未通过的不算
						&& trade == null) { //还未做转让信息维护的（实际转让份额还没从可转让份额中减掉）
						BigDecimal transferNum = new BigDecimal(Double.toString(formInfo
							.getTransferNum()));
						applyingNum = applyingNum.add(transferNum);
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询转让申请中份额出错 {}", e);
		}
		return applyingNum.doubleValue();
		
	}
	
	@Override
	public FcsBaseResult initPorjectRecord(final String setupProjectCode, final Money buyAmount) {
		
		logger.info("生成理财产品购买记录：{}，{}", setupProjectCode, buyAmount);
		
		return transactionTemplate.execute(new TransactionCallback<FcsBaseResult>() {
			@Override
			public FcsBaseResult doInTransaction(TransactionStatus status) {
				FcsBaseResult result = createResult();
				try {
					
					Date now = getSysdate();
					
					FProjectFinancialDO financialProject = FProjectFinancialDAO
						.findByProjectCode(setupProjectCode);
					
					if (financialProject == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"申请单不存在");
					}
					
					//计算实际能购买的份额
					BigDecimal actualBuyNum = buyAmount.getAmount().divide(
						financialProject.getPrice().getAmount(), 2, BigDecimal.ROUND_HALF_EVEN);
					BigDecimal buyNum = new BigDecimal(
						Double.toString(financialProject.getBuyNum()));
					financialProject.setBuyNum(buyNum.add(actualBuyNum).doubleValue());
					financialProject.setBuyTimes(financialProject.getBuyTimes() + 1); //累加购买次数
					FProjectFinancialDAO.update(financialProject);
					
					//生成购买的理财项目
					ProjectFinancialDO pf = new ProjectFinancialDO();
					BeanCopier.staticCopy(financialProject, pf);
					//在立项的基础上添加购买的序号
					pf.setOriginalCode(financialProject.getProjectCode());
					pf.setProjectCode(financialProject.getProjectCode()
										+ "-"
										+ StringUtil.leftPad(
											String.valueOf(financialProject.getBuyTimes()), 2, "0"));
					pf.setActualBuyNum(actualBuyNum.doubleValue());
					pf.setStatus(FinancialProjectStatusEnum.PURCHASING.code());
					//初始化的时候到期时间就是实际到期时间 
					pf.setCycleExpireDate(pf.getActualExpireDate());
					//默认不开放
					pf.setIsOpen(BooleanEnum.NO.code());
					pf.setRawAddTime(now);
					logger.info("生成理财项目待购买记录:{}", pf);
					projectFinancialDAO.insert(pf);
					
					ProjectRelatedUserInfo busiManager = projectRelatedUserService
						.getBusiManager(setupProjectCode);
					if (busiManager != null) {
						//保存业务到相关人员表
						ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
						BeanCopier.staticCopy(busiManager, relatedUser);
						relatedUser.setRelatedId(0);
						relatedUser.setIsCurrent(BooleanEnum.IS);
						relatedUser.setIsDel(BooleanEnum.NO);
						relatedUser.setTransferTime(null);
						relatedUser.setProjectCode(pf.getProjectCode());
						relatedUser.setUserType(ProjectRelatedUserTypeEnum.BUSI_MANAGER);
						relatedUser.setRemark("复制立项业务经理");
						projectRelatedUserService.setRelatedUser(relatedUser);
					}
					
					ProjectRelatedUserInfo riskManager = projectRelatedUserService
						.getRiskManager(setupProjectCode);
					if (riskManager != null) {
						//保存风险到相关人员表
						ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
						BeanCopier.staticCopy(riskManager, relatedUser);
						relatedUser.setRelatedId(0);
						relatedUser.setIsCurrent(BooleanEnum.IS);
						relatedUser.setIsDel(BooleanEnum.NO);
						relatedUser.setTransferTime(null);
						relatedUser.setProjectCode(pf.getProjectCode());
						relatedUser.setUserType(ProjectRelatedUserTypeEnum.RISK_MANAGER);
						relatedUser.setRemark("复制立项风险经理");
						projectRelatedUserService.setRelatedUser(relatedUser);
					}
					
					result.setSuccess(true);
				} catch (Exception e) {
					result.setSuccess(false);
					if (status != null)
						status.setRollbackOnly();
				}
				return result;
			}
		});
	}
	
	@Override
	public QueryAmountBatchResult<ProjectFinancialSettlementInfo> querySettlement(	final ProjectFinancialSettlementQueryOrder order) {
		QueryAmountBatchResult<ProjectFinancialSettlementInfo> batchResult = new QueryAmountBatchResult<ProjectFinancialSettlementInfo>();
		try {
			ProjectFinancialSettlementDO sDO = new ProjectFinancialSettlementDO();
			BeanCopier.staticCopy(order, sDO);
			
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("settlement_id");
			}
			if (StringUtil.isBlank(order.getSortOrder())) {
				order.setSortOrder("asc");
			}
			
			long totalCount = projectFinancialSettlementDAO.findByConditionCount(sDO,
				order.getSettlementTimeStart(), order.getSettlementTimeEnd());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<ProjectFinancialSettlementDO> list = projectFinancialSettlementDAO
				.findByCondition(sDO, order.getSettlementTimeStart(), order.getSettlementTimeEnd(),
					order.getSortCol(), order.getSortOrder(), component.getFirstRecord(),
					component.getPageSize());
			
			List<ProjectFinancialSettlementInfo> pageList = new ArrayList<ProjectFinancialSettlementInfo>(
				list.size());
			for (ProjectFinancialSettlementDO DO : list) {
				ProjectFinancialSettlementInfo info = new ProjectFinancialSettlementInfo();
				BeanCopier.staticCopy(DO, info);
				pageList.add(info);
			}
			
			Money totalAmount = projectFinancialSettlementDAO.findByConditionStatistics(sDO,
				order.getSettlementTimeStart(), order.getSettlementTimeEnd());
			batchResult.setTotalAmount(totalAmount);
			
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询理财项目结息失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public FcsBaseResult settlement(final ProjectFinancialSettlementOrder order) {
		return commonProcess(order, "理财产品结息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				ProjectFinancialDO project = projectFinancialDAO.findByCode(order.getProjectCode());
				if (project == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "理财项目不存在");
				}
				ProjectFinancialSettlementDO sDO = new ProjectFinancialSettlementDO();
				sDO.setProjectCode(order.getProjectCode());
				sDO.setOriginalCode(project.getOriginalCode());
				sDO.setSettlementTime(order.getSettlementTime());
				sDO.setSettlementAmount(order.getSettlementAmount());
				sDO.setProductId(project.getProductId());
				sDO.setProductName(project.getProductName());
				sDO.setRawAddTime(now);
				sDO.setSettlementId(projectFinancialSettlementDAO.insert(sDO));
				//累计已结息金额
				project.getSettlementAmount().addTo(sDO.getSettlementAmount());
				projectFinancialDAO.update(project);
				
				ReceiptPaymentFormOrder rpOrder = new ReceiptPaymentFormOrder();
				rpOrder.setContractNo(null);
				rpOrder.setSourceForm(SourceFormEnum.FINANCIAL_SETTLEMENT);
				rpOrder.setSourceFormId(String.valueOf(sDO.getSettlementId()));
				rpOrder.setSourceFormSys(SystemEnum.PM);
				rpOrder.setProjectCode(project.getProjectCode());
				rpOrder.setProjectName(project.getProductName());
				rpOrder.setUserId(project.getCreateUserId());
				rpOrder.setUserName(project.getCreateUserName());
				rpOrder.setUserAccount(project.getCreateUserAccount());
				rpOrder.setDeptId(project.getDeptId());
				rpOrder.setDeptCode(project.getDeptCode());
				rpOrder.setDeptName(project.getDeptName());
				rpOrder.setTransferName(null);
				rpOrder.setProductName(project.getProductName());
				rpOrder.setRemark("理财产品结息");
				
				List<ReceiptPaymentFormFeeOrder> feeOrderList = Lists.newArrayList();
				//理财产品赎回本金
				ReceiptPaymentFormFeeOrder feeOrder = new ReceiptPaymentFormFeeOrder();
				feeOrder.setFeeType(SubjectCostTypeEnum.INANCING_INVEST_INCOME);
				feeOrder.setOccurTime(sDO.getSettlementTime());
				feeOrder.setRemark("结息时间：" + DateUtil.dtSimpleFormat(sDO.getSettlementTime()));
				//转让价格
				feeOrder.setAmount(sDO.getSettlementAmount());
				feeOrderList.add(feeOrder);
				
				rpOrder.setFeeOrderList(feeOrderList);
				logger.info("同步结息信息到资金系统收款单：{}", rpOrder);
				receiptPaymentFormServiceClient.add(rpOrder);
				return null;
			}
		}, null, null);
	}
}
