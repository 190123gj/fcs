package com.born.fcs.pm.biz.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.dal.daointerface.ProjectFinancialWithdrawingDAO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialWithdrawingDO;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.financialproject.FinancialProjectInterestResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
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
	private ProjectFinancialWithdrawingDAO projectFinancialWithdrawingDAO;
	
	private final static Money ZERO = Money.zero();
	
	//每月28/29/30/31 0点5分执行
	@Scheduled(cron = "0 5 0 28,29,30,31 * ? ")
	@Override
	public void doJob() throws Exception {
		
		//是否当月最后一天
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
			return;
		}
		
		if (!isRun)
			return;
		logger.info("理财项目每月计提台帐开始在{} ", DateUtil.simpleFormat(new Date()));
		try {
			Date now = new Date();
			String withDrawDate = DateUtil.getFormat("yyyy-MM").format(now);
			FinancialProjectQueryOrder order = new FinancialProjectQueryOrder();
			List<String> statusList = Lists.newArrayList();
			List<FinancialProjectStatusEnum> status = FinancialProjectStatusEnum.getAllEnum();
			for (FinancialProjectStatusEnum s : status) {
				if (s.code().equals("PURCHASED") || s.code().startsWith("TRANSFER")
					|| s.code().startsWith("REDEEM") || s.code().startsWith("BUY_BACK")) {
					statusList.add(s.code());
				}
			}
			order.setPageSize(100);
			order.setStatusList(statusList);
			long pageNumber = 1;
			QueryBaseBatchResult<ProjectFinancialInfo> projects = new QueryBaseBatchResult<ProjectFinancialInfo>();
			while (pageNumber <= projects.getPageCount()) {
				order.setPageNumber(pageNumber);
				projects = financialProjectService.query(order);
				if (projects != null && projects.getTotalCount() > 0) {
					for (ProjectFinancialInfo project : projects.getPageList()) {
						FinancialProjectInterestResult result = financialProjectService
							.caculateProjectInterest(project, now);
						if (result.isSuccess() && result.getWithdrawingInterest().greaterThan(ZERO)) {
							//新增计提台帐记录
							ProjectFinancialWithdrawingDO withdrawing = new ProjectFinancialWithdrawingDO();
							BeanCopier.staticCopy(project, withdrawing);
							withdrawing.setBuyDate(project.getActualBuyDate());
							withdrawing.setTotalInterest(result.getWithdrawingInterest());
							withdrawing.setWithdrawDate(withDrawDate);
							withdrawing.setRawAddTime(now);
							projectFinancialWithdrawingDAO.insert(withdrawing);
						}
					}
				}
				pageNumber = projects.getPageNumber() + 1;
			}
			
			//查询当月到期或者完成的项目
			statusList.clear();
			statusList.add(FinancialProjectStatusEnum.EXPIRE.code());
			statusList.add(FinancialProjectStatusEnum.FINISH.code());
			order.setStatusList(statusList);
			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_MONTH, 1);
			c.set(Calendar.HOUR, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			order.setExpireDateStart(c.getTime());
			order.setExpireDateEnd(DateUtil.string2DateTimeBy23(DateUtil.dtSimpleFormat(now)));
			
			projects = new QueryBaseBatchResult<ProjectFinancialInfo>();
			pageNumber = 1;
			while (pageNumber <= projects.getPageCount()) {
				order.setPageNumber(pageNumber);
				projects = financialProjectService.query(order);
				if (projects != null && projects.getTotalCount() > 0) {
					for (ProjectFinancialInfo project : projects.getPageList()) {
						FinancialProjectInterestResult result = financialProjectService
							.caculateProjectInterest(project, now);
						if (result.isSuccess() && result.getWithdrawingInterest().greaterThan(ZERO)) {
							//新增计提台帐记录
							ProjectFinancialWithdrawingDO withdrawing = new ProjectFinancialWithdrawingDO();
							BeanCopier.staticCopy(project, withdrawing);
							withdrawing.setBuyDate(project.getActualBuyDate());
							withdrawing.setTotalInterest(result.getWithdrawingInterest());
							withdrawing.setWithdrawDate(withDrawDate);
							withdrawing.setRawAddTime(now);
							projectFinancialWithdrawingDAO.insert(withdrawing);
						}
					}
				}
				pageNumber = projects.getPageNumber() + 1;
			}
			
		} catch (Exception e) {
			logger.error("理财项目每月计提台帐异常 {}", e);
		}
		
		logger.info("理财项目每月计提台 结束在{} ", DateUtil.simpleFormat(new Date()));
	}
}
