package com.born.fcs.pm.biz.service.financialproject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.fm.ws.enums.ForecastChildTypeOneEnum;
import com.born.fcs.fm.ws.enums.ForecastChildTypeTwoEnum;
import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormFeeOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountChangeOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountOrder;
import com.born.fcs.fm.ws.service.common.ReceiptPaymentFormService;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialTansferApplyDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialTradeTansferDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialWithdrawingDO;
import com.born.fcs.pm.dataobject.FinancialProjectTransferFormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
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
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectTransferService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("financialProjectTransferService")
public class FinancialProjectTransferServiceImpl extends BaseFormAutowiredDomainService implements
																						FinancialProjectTransferService {
	
	@Autowired
	private FinancialProjectService financialProjectService;
	
	@Autowired
	private ReceiptPaymentFormService receiptPaymentFormServiceClient;
	
	@Autowired
	private ForecastService forecastServiceClient;
	
	@Autowired
	private FormService formService;
	
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
			projectForm.setDraftUserId(order.getDraftUserId());
			projectForm.setDeptIdList(order.getDeptIdList());
			projectForm.setCreateUserId(order.getCreateUserId());
			projectForm.setTransferTimeStart(order.getTransferTimeStart());
			projectForm.setTransferTimeEnd(order.getTransferTimeEnd());
			projectForm.setFormStatusList(order.getFormStatusList());
			
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
			Map<String, ProjectFinancialInfo> projectMap = Maps.newHashMap();
			for (FinancialProjectTransferFormDO DO : list) {
				FinancialProjectTransferFormInfo info = new FinancialProjectTransferFormInfo();
				BeanCopier.staticCopy(DO, info);
				info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
				info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
				info.setCouncilStatus(ProjectCouncilStatusEnum.getByCode(DO.getCouncilStatus()));
				info.setCouncilType(ProjectCouncilEnum.getByCode(DO.getCouncilType()));
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
			logger.error("查询理财项目转让申请列表失败 {}", e);
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
			logger.error("查询理财项目转让交易列表失败 {}", e);
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
				
				FProjectFinancialTansferApplyDO apply = FProjectFinancialTansferApplyDAO
					.findByFormId(form.getFormId());
				
				double transferingNum = financialProjectService.transferingNum(
					order.getProjectCode(), apply == null ? 0 : apply.getApplyId());
				double redeemingNum = financialProjectService.redeemingNum(order.getProjectCode(),
					apply == null ? 0 : apply.getApplyId());
				
				BigDecimal transfering = new BigDecimal(Double.toString(transferingNum));
				BigDecimal redeeming = new BigDecimal(Double.toString(redeemingNum));
				BigDecimal transferNum = new BigDecimal(
					Double.toString(order.getTransferNum() == null ? 0 : order.getTransferNum()));
				if (project.getActualBuyNum() < (transferNum.subtract(redeeming).subtract(
					transfering).doubleValue())) {
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
				apply.setBuyNum(project.getActualBuyNum());
				//过户并不回购减少持有份额
				apply.setHoldNum(project.getOriginalHoldNum());
				//实际可转让那个份额
				apply.setActualHoldNum(project.getActualHoldNum());
				
				if (isUpdate) {
					FProjectFinancialTansferApplyDAO.update(apply);
				} else {
					FProjectFinancialTansferApplyDAO.insert(apply);
				}
				
				return null;
			}
		}, null, null);
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
				
				ProjectFinancialDO project = projectFinancialDAO.findByCodeForUpdate(applyFormInfo
					.getProjectCode());
				if (project == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"理财项目不存在");
				}
				//理财项目是否有变动
				boolean hasChange = false;
				FcsBaseResult result = (FcsBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				
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
					projectFinancialTradeTansferDAO.update(tradeDO);
					
					//修改可转让和赎回份额
					project.setBuyBackNum(NumberUtil.doubleCaculate(project.getBuyBackNum(),
						tradeDO.getTransferNum(), "+"));
					project.setActualHoldNum(NumberUtil.doubleCaculate(project.getActualHoldNum(),
						tradeDO.getTransferNum(), "+"));
					hasChange = true;
					
					result.setKeyId(tradeDO.getTradeId());
					
				} else if (order.getIsConfirm() == BooleanEnum.YES) { //资金划付申请通过后、待确认回购
				
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
						projectFinancialTradeTansferDAO.update(tradeDO);
					}
					
					result.setKeyId(tradeDO.getTradeId());
					
				} else { //转让信息维护
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
					tradeDO.setTradeId(projectFinancialTradeTansferDAO.insert(tradeDO));
					
					//修改已转让份额 和 可转让份额
					project.setTransferedNum(NumberUtil.doubleCaculate(project.getTransferedNum(),
						tradeDO.getTransferNum(), "+"));
					project.setActualHoldNum(NumberUtil.doubleCaculate(project.getActualHoldNum(),
						tradeDO.getTransferNum(), "-"));
					//转让过户且不回购的减持有份额
					if (order.getIsTransferOwnership() == BooleanEnum.IS
						&& order.getIsBuyBack() != BooleanEnum.IS) {
						project.setOriginalHoldNum(NumberUtil.doubleCaculate(
							project.getOriginalHoldNum(), tradeDO.getTransferNum(), "-"));
					}
					
					hasChange = true;
					
					result.setKeyId(tradeDO.getTradeId());
					
					// 除过户且不回购外才计提
					if (!(StringUtil.equals(tradeDO.getIsTransferOwnership(), BooleanEnum.IS.code()) && !StringUtil
						.equals(tradeDO.getIsBuyBack(), BooleanEnum.IS.code()))) {
						
						Calendar calendarNow = Calendar.getInstance();
						calendarNow.setTime(now);
						Calendar calendarBuyDate = Calendar.getInstance();
						calendarBuyDate.setTime(project.getActualBuyDate());
						int year = calendarNow.get(Calendar.YEAR)
									- calendarBuyDate.get(Calendar.YEAR);
						int month = calendarNow.get(Calendar.MONTH)
									- calendarBuyDate.get(Calendar.MONTH);
						//当前维护时间和实际购买时间之间相差的月份
						int betweenMonth = year * 12 + month;
						if (betweenMonth >= 0) { //如果
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
								withdrawDO.setWithdrawFinish("NO");
								withdrawDO.setWithdrawType("PAYMENT");
								withdrawDO.setTransferTradeId(tradeDO.getTradeId());
								withdrawDO.setProjectCode(project.getProjectCode());
								long thisMonthCount = 0;
								Date caculateDate = calendarCaculate.getTime();
								if (isNow) //当前月才查询一下
									thisMonthCount = projectFinancialWithdrawingDAO
										.findByConditionCount(withdrawDO, null, null, null, null);
								
								if (thisMonthCount == 0) {//当月没有计提数据就新增一条
									withdrawDO.setTransferTo(tradeDO.getTransferTo());
									//本金=转让份数*购买价格
									withdrawDO.setPrincipal(project.getActualPrice().multiply(
										tradeDO.getTransferNum()));
									withdrawDO.setBuyDate(tradeDO.getTransferTime());
									//到期日（需要回购的为回购日期，不需要回购的为产品到期日） ，
									if (StringUtil.equals(tradeDO.getIsBuyBack(),
										BooleanEnum.IS.code())) {
										withdrawDO.setExpireDate(tradeDO.getBuyBackTime());
									} else {
										withdrawDO.setExpireDate(project.getPreFinishTime() == null ? project
											.getCycleExpireDate() : project.getPreFinishTime());
										if (StringUtil.equals(project.getIsOpen(),
											BooleanEnum.IS.code()))
											withdrawDO.setExpireDate(null);
									}
									//利率=融资利率
									withdrawDO.setInterestRate(tradeDO.getInterestRate());
									withdrawDO.setProductId(project.getProductId());
									withdrawDO.setProductName(project.getProductName());
									withdrawDO.setRawAddTime(now);
									
									withdrawDO.setWithdrawDate(now);
									withdrawDO.setWithdrawDay(DateUtil.dtSimpleFormat(now));
									
									//计算当前的计提情况
									if (withdrawDO.getExpireDate() != null
										&& caculateDate.after(withdrawDO.getExpireDate())) {
										caculateDate = withdrawDO.getExpireDate();
									}
									
									//持有天数
									long transferDays = DateUtil.getDateBetween(
										withdrawDO.getBuyDate(), caculateDate) / 1000 / 3600 / 24;
									//截到现在累计计提数据 本金*融资利率*持有天数/365   每个月月末计提
									Money transferWithdrawInterest = withdrawDO.getPrincipal()
										.multiply(withdrawDO.getInterestRate()).divide(100)
										.multiply(transferDays).divide(project.getYearDayNum());
									
									if (isNow) {
										Calendar calendar = Calendar.getInstance();
										calendar.setTime(caculateDate);
										calendar.add(Calendar.DAY_OF_MONTH, 1);
										if (calendar.get(Calendar.DAY_OF_MONTH) == 1) { //如果是最后一天
											withdrawDO.setWithdrawFinish("YES");
											//累计结息金额
											withdrawDO.setTotalInterest(transferWithdrawInterest);
											//本月已结息
											withdrawDO
												.setWithdrawedInterest(transferWithdrawInterest
													.subtract(lastMonthTotal));
										} else {//尚未结息 本月累计就是上月累计
										
											//如果项目到期就结息
											if (withdrawDO.getExpireDate() != null
												&& now.after(withdrawDO.getExpireDate())) {
												withdrawDO.setWithdrawFinish("YES");
												withdrawDO
													.setTotalInterest(transferWithdrawInterest);
												withdrawDO.setWithdrawingInterest(Money.zero());
												withdrawDO
													.setWithdrawedInterest(transferWithdrawInterest
														.subtract(lastMonthTotal));
											} else {
												withdrawDO.setTotalInterest(lastMonthTotal);
												//本月实时结息金额 = 当前累计计提  - 上月累计计提
												withdrawDO
													.setWithdrawingInterest(transferWithdrawInterest
														.subtract(lastMonthTotal));
											}
											
										}
									} else { //历史结息
										withdrawDO.setWithdrawFinish("YES");
										//累计结息金额
										withdrawDO.setTotalInterest(transferWithdrawInterest);
										//本月已结息
										withdrawDO.setWithdrawedInterest(transferWithdrawInterest
											.subtract(lastMonthTotal));
									}
									lastMonthTotal = withdrawDO.getTotalInterest();
									projectFinancialWithdrawingDAO.insert(withdrawDO);
								}
							}
						}
					}
					
					//同步转让信息到资金系统收款单
					try {
						ReceiptPaymentFormOrder rpOrder = new ReceiptPaymentFormOrder();
						rpOrder.setContractNo(null);
						rpOrder.setSourceForm(SourceFormEnum.FINANCIAL_TRANSFER);
						rpOrder.setSourceFormId(String.valueOf(applyFormInfo.getFormId()));
						rpOrder.setSourceFormSys(SystemEnum.PM);
						rpOrder.setProjectCode(project.getProjectCode());
						rpOrder.setProjectName(project.getProductName());
						rpOrder.setUserId(applyFormInfo.getFormUserId());
						rpOrder.setUserName(applyFormInfo.getFormUserName());
						rpOrder.setUserAccount(applyFormInfo.getFormUserAccount());
						rpOrder.setDeptId(applyFormInfo.getFormDeptId());
						rpOrder.setDeptCode(applyFormInfo.getFormDeptCode());
						rpOrder.setDeptName(applyFormInfo.getFormDeptName());
						rpOrder.setTransferName(tradeDO.getTransferTo());
						rpOrder.setProductName(project.getProductName());
						rpOrder.setRemark("理财产品转让");
						
						List<ReceiptPaymentFormFeeOrder> feeOrderList = Lists.newArrayList();
						//理财产品赎回本金
						ReceiptPaymentFormFeeOrder feeOrder = new ReceiptPaymentFormFeeOrder();
						if (StringUtil.equals(tradeDO.getIsTransferOwnership(),
							BooleanEnum.IS.code())) {
							feeOrder.setFeeType(SubjectCostTypeEnum.FINANCING_PRODUCT_TRANSFER);
						} else {
							feeOrder.setFeeType(SubjectCostTypeEnum.FINANCING_PRODUCT_TRANSFER_NOT);
						}
						//转让价格
						feeOrder.setAmount(tradeDO.getTransferPrice().multiply(
							tradeDO.getTransferNum()));
						feeOrder.setRemark("转让单价："
											+ tradeDO.getTransferPrice().toStandardString()
											+ ",转让份额："
											+ tradeDO.getTransferNum()
											+ (StringUtil.equals(tradeDO.getIsBuyBack(),
												BooleanEnum.IS.code()) ? ("，回购时间：" + DateUtil
												.dtSimpleFormat(tradeDO.getBuyBackTime())) : ""));
						feeOrderList.add(feeOrder);
						
						rpOrder.setFeeOrderList(feeOrderList);
						logger.info("同步转让信息到资金系统：{}", rpOrder);
						receiptPaymentFormServiceClient.add(rpOrder);
						
						try {
							ForecastAccountChangeOrder changeForecastOrder = new ForecastAccountChangeOrder();
							changeForecastOrder.setFeeType(ForecastFeeTypeEnum.TRANSFER);
							changeForecastOrder.setProjectCode(project.getProjectCode());
							changeForecastOrder.setAmount(tradeDO.getTransferPrice().multiply(
								tradeDO.getTransferNum()));
							changeForecastOrder.setUserId(applyFormInfo.getFormUserId());
							changeForecastOrder.setUserAccount(applyFormInfo.getFormUserAccount());
							changeForecastOrder.setUserName(applyFormInfo.getFormUserName());
							changeForecastOrder.setForecastMemo("理财产品转让信息维护：转让单价："
																+ tradeDO.getTransferPrice()
																	.toStandardString() + "，转让份额："
																+ tradeDO.getTransferNum());
							forecastServiceClient.change(changeForecastOrder);
						} catch (Exception e) {
							logger.error("更新资金预测信息出错：{}", e);
						}
					} catch (Exception e) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "同步转让信息到资金系统出错");
					}
				}
				
				if (hasChange)
					projectFinancialDAO.update(project);
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult syncForecast(long applyId) {
		FcsBaseResult result = createResult();
		try {
			FProjectFinancialTansferApplyDO apply = FProjectFinancialTansferApplyDAO
				.findById(applyId);
			if (apply != null) {
				//理财项目
				ProjectFinancialInfo project = financialProjectService.queryByCode(apply
					.getProjectCode());
				if (project != null) {
					FormInfo form = formService.findByFormId(apply.getFormId());
					//转让 流入预测
					ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
					forecastAccountOrder.setProjectCode(project.getProjectCode());
					forecastAccountOrder.setCustomerId(project.getProductId());
					forecastAccountOrder.setCustomerName(project.getProductName());
					forecastAccountOrder.setForecastStartTime(apply.getTransferTime());
					forecastAccountOrder.setAmount(apply.getTransferPrice().multiply(
						apply.getTransferNum()));
					forecastAccountOrder.setForecastMemo("理财产品转让（" + apply.getProjectCode() + "）");
					forecastAccountOrder.setForecastType(ForecastTypeEnum.IN_LCYW);
					forecastAccountOrder.setFundDirection(FundDirectionEnum.IN);
					//设置中长期属性
					forecastAccountOrder.setForecastChildTypeOne(ForecastChildTypeOneEnum
						.getByCode(project.getTermType().code()));
					//设置产品类型
					forecastAccountOrder.setForecastChildTypeTwo(ForecastChildTypeTwoEnum
						.getByCode(project.getProductType().code()));
					
					forecastAccountOrder.setOrderNo(apply.getProjectCode() + "_"
													+ ForecastFeeTypeEnum.TRANSFER.code() + "_"
													+ applyId);
					forecastAccountOrder.setSystemForm(SystemEnum.PM);
					forecastAccountOrder.setUsedDeptId(String.valueOf(form.getDeptId()));
					forecastAccountOrder.setUsedDeptName(form.getDeptName());
					forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.TRANSFER);
					forecastAccountOrder.setUserId(form.getUserId());
					forecastAccountOrder.setUserName(form.getUserName());
					forecastAccountOrder.setUserAccount(form.getUserAccount());
					logger.info("理财转让通过后资金流入预测,projectCode：{}, forecastAccountOrder：{} ",
						apply.getProjectCode(), forecastAccountOrder);
					forecastServiceClient.add(forecastAccountOrder);
					
					//回购 流出预测
					if (StringUtil.equals(apply.getIsBuyBack(), BooleanEnum.IS.code())) {
						forecastAccountOrder = new ForecastAccountOrder();
						forecastAccountOrder.setForecastStartTime(apply.getBuyBackTime());
						forecastAccountOrder.setAmount(apply.getBuyBackPrice().multiply(
							apply.getTransferNum()));
						forecastAccountOrder.setForecastMemo("理财产品回购（" + apply.getProjectCode()
																+ "）");
						forecastAccountOrder.setForecastType(ForecastTypeEnum.OUT_LCYW);
						forecastAccountOrder.setFundDirection(FundDirectionEnum.OUT);
						forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.BUY_BACK);
						forecastAccountOrder.setOrderNo(apply.getProjectCode() + "_"
														+ ForecastFeeTypeEnum.BUY_BACK.code() + "_"
														+ applyId);
						
						//设置中长期属性
						forecastAccountOrder.setForecastChildTypeOne(ForecastChildTypeOneEnum
							.getByCode(project.getTermType().code()));
						//设置产品类型
						forecastAccountOrder.setForecastChildTypeTwo(ForecastChildTypeTwoEnum
							.getByCode(project.getProductType().code()));
						
						forecastAccountOrder.setSystemForm(SystemEnum.PM);
						forecastAccountOrder.setUsedDeptId(String.valueOf(form.getDeptId()));
						forecastAccountOrder.setUsedDeptName(form.getDeptName());
						forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.BUY_BACK);
						forecastAccountOrder.setUserId(form.getUserId());
						forecastAccountOrder.setUserName(form.getUserName());
						forecastAccountOrder.setUserAccount(form.getUserAccount());
						forecastAccountOrder.setProjectCode(project.getProjectCode());
						forecastAccountOrder.setCustomerId(project.getProductId());
						forecastAccountOrder.setCustomerName(project.getProductName());
						logger.info("理财转让通过后资金流出预测,projectCode：{}, forecastAccountOrder：{} ",
							apply.getProjectCode(), forecastAccountOrder);
						forecastServiceClient.add(forecastAccountOrder);
					} else { //不回购重新预测赎回金额
					
						forecastAccountOrder = new ForecastAccountOrder();
						
						forecastAccountOrder.setForecastStartTime(project.getActualExpireDate());
						forecastAccountOrder.setForecastMemo("理财产品赎回（" + project.getProjectCode()
																+ "）");
						forecastAccountOrder.setForecastType(ForecastTypeEnum.IN_LCYW);
						forecastAccountOrder.setFundDirection(FundDirectionEnum.IN);
						forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.REDEEM);
						forecastAccountOrder.setOrderNo(project.getProjectCode() + "_"
														+ ForecastFeeTypeEnum.REDEEM.code());
						//设置中长期属性
						forecastAccountOrder.setForecastChildTypeOne(ForecastChildTypeOneEnum
							.getByCode(project.getTermType().code()));
						//设置产品类型
						forecastAccountOrder.setForecastChildTypeTwo(ForecastChildTypeTwoEnum
							.getByCode(project.getProductType().code()));
						
						forecastAccountOrder.setSystemForm(SystemEnum.PM);
						forecastAccountOrder.setUsedDeptId(String.valueOf(project.getDeptId()));
						forecastAccountOrder.setUsedDeptName(project.getDeptName());
						forecastAccountOrder.setProjectCode(project.getProjectCode());
						forecastAccountOrder.setCustomerId(project.getProductId());
						forecastAccountOrder.setCustomerName(project.getProductName());
						
						// （购买份数减已转让份数）*单价 + 持有期收益
						Money amount = Money.zero();
						
						// 持有期投资收益=（票面单价*持有份额）* 年化利率 * 持有天数 / 365
						BigDecimal ab = new BigDecimal(Double.toString(project.getActualBuyNum()));
						BigDecimal trans = new BigDecimal(Double.toString(project
							.getTransferedNum()));
						BigDecimal back = new BigDecimal(Double.toString(project.getBuyBackNum()));
						BigDecimal thisTrans = new BigDecimal(Double.toString(apply
							.getTransferNum()));
						//已经赎回的部分
						BigDecimal redeem = new BigDecimal(
							Double.toString(project.getRedeemedNum()));
						
						//实际预测份额=购买份额-转让份额（包含本次和历史已转让）+ 回购份额 - 已赎回部分
						double caculateNum = ab.subtract(trans).subtract(thisTrans).add(back)
							.subtract(redeem).doubleValue();
						
						if (caculateNum < 0)
							caculateNum = 0;
						
						//本金
						amount.addTo(project.getActualPrice().multiply(caculateNum));
						
						//持有天数
						long days = DateUtil.getDateBetween(project.getActualBuyDate(),
							project.getActualExpireDate()) / 1000 / 3600 / 24;
						if (days < 0)
							days = 0;
						Money totalInterest = project.getActualPrice().multiply(caculateNum)
							.multiply(project.getInterestRate()).divide(100).multiply(days)
							.divide(project.getYearDayNum());
						//收益
						amount.addTo(totalInterest);
						forecastAccountOrder.setAmount(amount);
						
						logger.info("理财项目转让不回购重新预测赎回金额,projectCode：{}, forecastAccountOrder：{} ",
							project.getProjectCode(), forecastAccountOrder);
						forecastServiceClient.add(forecastAccountOrder);
					}
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("同步预测数据到资金系统出错");
			logger.error("同步理财转让预测数据到资金系统出错：{}", e);
		}
		return result;
	}
}
