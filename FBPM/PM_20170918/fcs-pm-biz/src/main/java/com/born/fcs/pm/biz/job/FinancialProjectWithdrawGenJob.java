package com.born.fcs.pm.biz.job;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.dal.daointerface.ProjectFinancialWithdrawingDAO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialWithdrawingDO;
import com.born.fcs.pm.daointerface.BusiDAO;
import com.born.fcs.pm.daointerface.ExtraDAO;
import com.born.fcs.pm.dataobject.ProjectGenWithdrawFinancialTransferDO;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.result.financialproject.FinancialProjectInterestResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 理财项目每月初生成当月待计提数据
 * 
 * @author wuzj
 *
 */
@Service("financialProjectWithdrawGenJob")
public class FinancialProjectWithdrawGenJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FinancialProjectService financialProjectService;
	
	@Autowired
	private ProjectFinancialWithdrawingDAO projectFinancialWithdrawingDAO;
	
	@Autowired
	ExtraDAO extraDAO;
	
	@Autowired
	BusiDAO busiDAO;
	
	DateFormat monthFormat = DateUtil.getFormat("yyyy-MM");
	DateFormat dayFormat = DateUtil.getFormat("yyyy-MM-dd");
	
	//每月1号凌晨0点到2点执行
	@Scheduled(cron = "0 0/5 0-2 1 * ? ")
	@Override
	public void doJob() throws Exception {
		
		if (!isRun)
			return;
		try {
			Date now = extraDAO.getSysdate();
			//本月
			String thisMonth = monthFormat.format(now);
			//今天
			String thisDay = dayFormat.format(now);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.MONTH, -1);
			//上月
			String lastMonth = monthFormat.format(calendar.getTime());
			
			//查询本月待生成计提数据的项目
			List<ProjectFinancialDO> waitProjectList = busiDAO.waitGenWithdrawFinancialProject(
				thisMonth, thisDay, 20);
			if (ListUtil.isNotEmpty(waitProjectList)) {
				for (ProjectFinancialDO project : waitProjectList) {
					ProjectFinancialWithdrawingDO withdrawDO = new ProjectFinancialWithdrawingDO();
					withdrawDO.setWithdrawMonth(thisMonth);
					withdrawDO.setWithdrawType("RECEIPT");
					withdrawDO.setProjectCode(project.getProjectCode());
					withdrawDO.setBuyDate(project.getActualBuyDate());
					withdrawDO.setExpireDate(project.getPreFinishTime() == null ? project
						.getCycleExpireDate() : project.getPreFinishTime());
					if (BooleanEnum.IS.code().equals(project.getIsOpen())) {
						withdrawDO.setExpireDate(null);
					}
					withdrawDO.setInterestRate(project.getInterestRate());
					withdrawDO.setProductId(project.getProductId());
					withdrawDO.setProductName(project.getProductName());
					withdrawDO.setYearDayNum(project.getYearDayNum());
					withdrawDO.setRawAddTime(now);
					//上月计提
					ProjectFinancialWithdrawingDO lasthMonthWithdraw = projectFinancialWithdrawingDAO
						.findByProjectCodeAndMonth(withdrawDO.getProjectCode(), lastMonth);
					//上月累计结息
					Money lastMonthTotalInterest = Money.zero();
					if (lasthMonthWithdraw != null) {
						lastMonthTotalInterest = lasthMonthWithdraw.getTotalInterest();
					}
					//计算实时的计提数据
					FinancialProjectInterestResult interestResult = financialProjectService
						.caculateProjectInterest(DoConvert.convertFinancialProjectInfo(project),
							now);
					//截止当前累计计提利息
					Money withdrawingInterest = interestResult.getWithdrawingInterest();
					withdrawDO.setTotalInterest(lastMonthTotalInterest);
					withdrawDO.setPrincipal(interestResult.getCaculatePrincipal());
					withdrawDO.setWithdrawDate(now);
					withdrawDO.setWithdrawDay(thisDay);
					//本月实时结息金额  = 当前累计计提  -上月累计计提
					withdrawDO.setWithdrawingInterest(withdrawingInterest
						.subtract(lastMonthTotalInterest));
					//月初本月已结息金额 = 0;
					withdrawDO.setWithdrawedInterest(Money.zero());
					withdrawDO.setWithdrawFinish("NO");
					//计算计提数据
					logger.info("生成理财项目待计提数据  month {} {} ", thisMonth, withdrawDO);
					projectFinancialWithdrawingDAO.insert(withdrawDO);
					
				}
			}
			
			//查询本月待生成计提数据的转让
			List<ProjectGenWithdrawFinancialTransferDO> waitTransferList = busiDAO
				.waitGenWithdrawFinancialTransfer(thisMonth, thisDay, 20);
			if (ListUtil.isNotEmpty(waitTransferList)) {
				for (ProjectGenWithdrawFinancialTransferDO transfer : waitTransferList) {
					ProjectFinancialWithdrawingDO withdrawDO = new ProjectFinancialWithdrawingDO();
					withdrawDO.setWithdrawMonth(thisMonth);
					withdrawDO.setWithdrawType("PAYMENT");
					withdrawDO.setTransferTradeId(transfer.getTradeId());
					withdrawDO.setProjectCode(transfer.getProjectCode());
					withdrawDO.setTransferTo(transfer.getTransferTo());
					withdrawDO.setYearDayNum(transfer.getYearDayNum());
					
					//本金=转让份数*购买价格
					withdrawDO.setPrincipal(transfer.getActualPrice().multiply(
						transfer.getTransferNum()));
					withdrawDO.setBuyDate(transfer.getTransferTime());
					//到期日（需要回购的为回购日期，不需要回购的为产品到期日） ，
					if (StringUtil.equals(transfer.getIsBuyBack(), BooleanEnum.IS.code())) {
						withdrawDO.setExpireDate(transfer.getBuyBackTime());
					} else {
						withdrawDO.setExpireDate(transfer.getPreFinishTime() == null ? transfer
							.getCycleExpireDate() : transfer.getPreFinishTime());
						if (BooleanEnum.IS.code().equals(transfer.getIsOpen())) {
							withdrawDO.setExpireDate(null);
						}
					}
					//利率=融资利率
					withdrawDO.setInterestRate(transfer.getInterestRate());
					withdrawDO.setProductId(transfer.getProductId());
					withdrawDO.setProductName(transfer.getProductName());
					withdrawDO.setRawAddTime(now);
					
					Date caculateDate = now;
					if (withdrawDO.getExpireDate() != null
						&& caculateDate.after(withdrawDO.getExpireDate())) {
						caculateDate = withdrawDO.getExpireDate();
					}
					
					//上月计提
					ProjectFinancialWithdrawingDO lasthMonthWithdraw = projectFinancialWithdrawingDAO
						.findByTradeIdAndMonth(withdrawDO.getTransferTradeId(), lastMonth);
					//上月累计计提
					Money lastMonthTotalInterest = Money.zero();
					if (lasthMonthWithdraw != null) {
						lastMonthTotalInterest = lasthMonthWithdraw.getTotalInterest();
					}
					
					//持有天数
					long transferDays = DateUtil.getDateBetween(withdrawDO.getBuyDate(),
						caculateDate) / 1000 / 3600 / 24;
					//截到现在累计计提数据 本金*融资利率*持有天数/365   每个月月末计提
					Money transferWithdrawInterest = withdrawDO.getPrincipal()
						.multiply(withdrawDO.getInterestRate()).divide(100).multiply(transferDays)
						.divide(withdrawDO.getYearDayNum());
					//月初尚未结息，累计计提为上月计提
					withdrawDO.setTotalInterest(lastMonthTotalInterest);
					
					withdrawDO.setWithdrawDate(now);
					withdrawDO.setWithdrawDay(thisDay);
					//本月实时结息金额  = 当前累计计提  -上月累计计提
					withdrawDO.setWithdrawingInterest(transferWithdrawInterest
						.subtract(lastMonthTotalInterest));
					//月初本月已计提金额为0
					withdrawDO.setWithdrawedInterest(Money.zero());
					withdrawDO.setWithdrawFinish("NO");
					
					withdrawDO.setWithdrawDate(now);
					withdrawDO.setWithdrawDay(thisDay);
					
					logger.info("生成理财转让待计提数据  month {} {} ", thisMonth, withdrawDO);
					projectFinancialWithdrawingDAO.insert(withdrawDO);
				}
			}
		} catch (Exception e) {
			logger.error("理财项目每月生成待计提数据异常 {}", e);
		}
	}
}
