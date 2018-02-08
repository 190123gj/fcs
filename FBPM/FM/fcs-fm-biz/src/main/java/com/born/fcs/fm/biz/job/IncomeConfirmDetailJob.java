package com.born.fcs.fm.biz.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.biz.job.inter.ProcessJobService;
import com.born.fcs.fm.ws.enums.ConfirmStatusEnum;
import com.born.fcs.fm.ws.info.incomeconfirm.IncomeConfirmInfo;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmDetailOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmQueryOrder;
import com.born.fcs.fm.ws.service.incomeconfirm.IncomeConfirmService;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationService;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 收入确认单 每月定时任务增加明细
 * 
 * @author Ji
 * 
 */
@Service("incomeConfirmDetailJob")
public class IncomeConfirmDetailJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IncomeConfirmService incomeConfirmService;
	@Autowired
	protected ChargeNotificationService chargeNotificationWebService;
	
	// 每月1号 0点1分提示一次
	@Scheduled(cron = "0 1 0 1 * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		logger.info("收入确认单每月定时任务增加明细任务开始在 " + DateUtil.simpleFormat(new Date()));
		try {
			IncomeConfirmQueryOrder queryOrder = new IncomeConfirmQueryOrder();
			queryOrder.setPageSize(100);
			QueryBaseBatchResult<IncomeConfirmInfo> result = incomeConfirmService.query(queryOrder);
			
			if (result.getTotalCount() > 0) {
				for (IncomeConfirmInfo incomeConfirmInfo : result.getPageList()) {
					doChange(incomeConfirmInfo);
				}
				//循环翻页处理
				long totalPage = result.getPageCount();
				long pageNumber = result.getPageNumber();
				while (pageNumber < totalPage) {
					pageNumber++;
					queryOrder.setPageNumber(pageNumber);
					result = incomeConfirmService.query(queryOrder);
					for (IncomeConfirmInfo incomeConfirmInfo : result.getPageList()) {
						doChange(incomeConfirmInfo);
					}
				}
			}
			logger.info("收入确认单 每月定时任务增加明细任务");
		} catch (Exception e) {
			logger.error("处理收入确认单 每月定时任务增加明细任务异常---异常原因：" + e.getMessage(), e);
		}
		
		logger.info("处理收入确认单 每月定时任务增加明细任务结束在 " + DateUtil.simpleFormat(new Date()));
	}
	
	private void doChange(IncomeConfirmInfo incomeConfirmInfo) {
		logger.info("收入确认定时任务 projectCode={}", incomeConfirmInfo.getProjectCode());
		IncomeConfirmOrder order = new IncomeConfirmOrder();
		order.setIncomeId(incomeConfirmInfo.getIncomeId());
		order.setProjectCode(incomeConfirmInfo.getProjectCode());
		List<IncomeConfirmDetailOrder> incomeConfirmDetailOrders = Lists.newArrayList();
		IncomeConfirmDetailOrder detailOrder = new IncomeConfirmDetailOrder();
		detailOrder.setIncomeId(incomeConfirmInfo.getIncomeId());
		String incomePeriod = DateUtil.dtSimpleChineseFormat(DateUtil
			.getDateByMonth(new Date(), -1));//获取上个月
		detailOrder.setIncomePeriod(incomePeriod.substring(0, incomePeriod.indexOf("月") + 1));
		detailOrder.setConfirmStatus(ConfirmStatusEnum.NO_CONFIRM.code());
		//这里还差系统预估分摊金额
		detailOrder.setSystemEstimatedAmount(Money.zero());
		incomeConfirmDetailOrders.add(detailOrder);
		order.setIncomeConfirmDetailOrders(incomeConfirmDetailOrders);
		order.setCheckIndex(0);
		order.setCheckStatus(0);
		order.setCalculate(true);
		incomeConfirmService.save(order);
	}
}
