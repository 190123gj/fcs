package com.born.fcs.pm.biz.job;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.dal.daointerface.ProjectFinancialTradeTansferDAO;
import com.born.fcs.pm.dal.daointerface.ProjectFinancialWithdrawingDAO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialTradeTansferDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialWithdrawingDO;
import com.born.fcs.pm.daointerface.ExtraDAO;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.result.financialproject.FinancialProjectInterestResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 理财项目每月计提台帐
 * 
 * @author wuzj
 *
 */
@Service("financialProjectWithdrawingJob")
public class FinancialProjectWithdrawingJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FinancialProjectService financialProjectService;
	
	@Autowired
	private ProjectFinancialTradeTansferDAO projectFinancialTradeTansferDAO;
	
	@Autowired
	private ProjectFinancialWithdrawingDAO projectFinancialWithdrawingDAO;
	
	@Autowired
	ExtraDAO extraDAO;
	
	DateFormat monthFormat = DateUtil.getFormat("yyyy-MM");
	DateFormat dayFormat = DateUtil.getFormat("yyyy-MM-dd");
	
	//缓存一下转让交易是否回购的数据
	private static Map<Long, BooleanEnum> tradeBuyBackMap = Maps.newHashMap();
	
	//每天凌晨0点到2点计提当天的 月末计提当月完成的
	@Scheduled(cron = "0 0/5 0-2 * * ? ")
	@Override
	public void doJob() throws Exception {
		
		if (!isRun)
			return;
		
		try {
			Date now = extraDAO.getSysdate();
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			//每月1号重新生成计提数据不用重复计提
			if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
				return;
			}
			//是否当月最后一天
			boolean isLastDay = false;
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
				isLastDay = true;
			}
			logger.info("理财每日计提任务开始在：{}", new Date());
			
			//本月
			String thisMonth = monthFormat.format(now);
			//今天
			String thisDay = dayFormat.format(now);
			//昨天
			calendar.setTime(now);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			Date yesterday = calendar.getTime();
			Date withdrawDateStart = DateUtil.getStartTimeOfTheDate(yesterday);
			Date withdrawDateEnd = DateUtil.getEndTimeOfTheDate(yesterday);
			
			//查询待计提（继续昨天的）
			ProjectFinancialWithdrawingDO queryDO = new ProjectFinancialWithdrawingDO();
			queryDO.setWithdrawFinish("NO");
			queryDO.setWithdrawMonth(thisMonth);
			List<ProjectFinancialWithdrawingDO> waitProjectList = projectFinancialWithdrawingDAO
				.findByCondition(queryDO, withdrawDateStart, withdrawDateEnd, null, null, null,
					null, 0, 50);
			
			//项目缓存
			Map<String, ProjectFinancialInfo> projectMap = Maps.newHashMap();
			
			logger.info("扫描到待计提数据数量：{}", waitProjectList == null ? 0 : waitProjectList.size());
			if (ListUtil.isNotEmpty(waitProjectList)) {
				for (ProjectFinancialWithdrawingDO withdraw : waitProjectList) {
					
					ProjectFinancialInfo project = projectMap.get(withdraw.getProjectCode());
					if (project == null) {
						project = financialProjectService.queryByCode(withdraw.getProjectCode());
						projectMap.put(withdraw.getProjectCode(), project);
					}
					
					//未到最后一天则上月累计就是本月累计
					Money lastMonthTotalInterest = withdraw.getTotalInterest();
					withdraw.setWithdrawDate(now);
					withdraw.setWithdrawDay(thisDay);
					if ("RECEIPT".equals(withdraw.getWithdrawType())) { //应收计提
					
						//更新项目的实时到期时间
						withdraw.setExpireDate(project.getPreFinishTime() == null ? project
							.getCycleExpireDate() : project.getPreFinishTime());
						if (project.getIsOpen() == BooleanEnum.IS)
							withdraw.setExpireDate(null);
						
						//计算实时的计提数据
						FinancialProjectInterestResult interestResult = financialProjectService
							.caculateProjectInterest(project, now);
						
						if (interestResult.isSuccess()) {
							//截止当前累计计提利息
							Money withdrawingInterest = interestResult.getWithdrawingInterest();
							withdraw.setPrincipal(interestResult.getCaculatePrincipal());
							if (isLastDay) { //最后一天
								withdraw.setWithdrawFinish("YES");
								//累计计提
								withdraw.setTotalInterest(withdrawingInterest);
								//本月结息金额 = 当前(月末)累计计提  - 上月累计计提
								withdraw.setWithdrawedInterest(withdrawingInterest
									.subtract(lastMonthTotalInterest));
								//当月实时计提变为0
								withdraw.setWithdrawingInterest(Money.zero());
							} else {
								//如果项目到期就结息
								if (project.isExipre(now)) {
									withdraw.setWithdrawFinish("YES");
									withdraw.setTotalInterest(withdrawingInterest);
									withdraw.setWithdrawingInterest(Money.zero());
									withdraw.setWithdrawedInterest(withdrawingInterest
										.subtract(lastMonthTotalInterest));
								} else {
									//本月实时结息金额 = 当前累计计提  - 上月累计计提
									withdraw.setWithdrawingInterest(withdrawingInterest
										.subtract(lastMonthTotalInterest));
								}
							}
						}
					} else { //应付计提
					
						BooleanEnum isBuyBack = tradeBuyBackMap.get(withdraw.getTransferTradeId());
						if (isBuyBack == null) {
							ProjectFinancialTradeTansferDO tradeTransfer = projectFinancialTradeTansferDAO
								.findById(withdraw.getTransferTradeId());
							if (tradeTransfer != null) {
								isBuyBack = BooleanEnum.getByCode(tradeTransfer.getIsBuyBack());
								tradeBuyBackMap.put(withdraw.getTransferTradeId(), isBuyBack);
							}
						}
						
						//不回购到期时间为产品到期时间
						if (isBuyBack != BooleanEnum.IS) {
							withdraw.setExpireDate(project.getPreFinishTime() == null ? project
								.getCycleExpireDate() : project.getPreFinishTime());
							if (BooleanEnum.IS.code().equals(project.getIsOpen())) {
								withdraw.setExpireDate(null);
							}
						}
						
						Date caculateDate = now;
						if (withdraw.getExpireDate() != null
							&& caculateDate.after(withdraw.getExpireDate())) {
							caculateDate = withdraw.getExpireDate();
						}
						
						//持有天数
						long transferDays = DateUtil.getDateBetween(withdraw.getBuyDate(),
							caculateDate) / 1000 / 3600 / 24;
						//截到现在累计计提数据 本金*融资利率*持有天数/365   每个月月末计提
						Money transferWithdrawInterest = withdraw.getPrincipal()
							.multiply(withdraw.getInterestRate()).divide(100)
							.multiply(transferDays).divide(withdraw.getYearDayNum());
						if (isLastDay) { //最后一天
							withdraw.setWithdrawFinish("YES");
							//累计计提
							withdraw.setTotalInterest(transferWithdrawInterest);
							//本月结息金额 = 当前(月末)累计计提  - 上月累计计提
							withdraw.setWithdrawedInterest(transferWithdrawInterest
								.subtract(lastMonthTotalInterest));
							//当月实时计提变为0
							withdraw.setWithdrawingInterest(Money.zero());
						} else {
							//如果项目到期就结息
							if (withdraw.getExpireDate() != null
								&& now.after(withdraw.getExpireDate())) {
								withdraw.setWithdrawFinish("YES");
								withdraw.setTotalInterest(transferWithdrawInterest);
								withdraw.setWithdrawingInterest(Money.zero());
								withdraw.setWithdrawedInterest(transferWithdrawInterest
									.subtract(lastMonthTotalInterest));
							} else {
								//本月实时结息金额 = 当前累计计提  - 上月累计计提
								withdraw.setWithdrawingInterest(transferWithdrawInterest
									.subtract(lastMonthTotalInterest));
							}
						}
					}
					projectFinancialWithdrawingDAO.update(withdraw);
				}
			}
		} catch (Exception e) {
			logger.error("理财项目计提台帐异常 {}", e);
		}
		logger.info("理财每日计提任务结束在：{}", new Date());
	}
}
