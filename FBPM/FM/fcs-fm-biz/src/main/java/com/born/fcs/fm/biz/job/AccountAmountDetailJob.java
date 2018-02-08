/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午6:24:52 创建
 */
package com.born.fcs.fm.biz.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.biz.job.inter.ProcessJobService;
import com.born.fcs.fm.dal.daointerface.AccountAmountDetailDAO;
import com.born.fcs.fm.dal.daointerface.BankMessageDAO;
import com.born.fcs.fm.dal.daointerface.ForecastAccountDAO;
import com.born.fcs.fm.dal.dataobject.AccountAmountDetailDO;
import com.born.fcs.fm.dal.dataobject.BankMessageDO;
import com.born.fcs.fm.dal.dataobject.ForecastAccountDO;
import com.born.fcs.fm.daointerface.ExtraDAO;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.enums.SubjectAccountTypeEnum;
import com.born.fcs.pm.util.DateUtil;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 资金余额明细表数据
 * @author hjiajie
 * 
 */
@Service("AccountAmountDetailJob")
public class AccountAmountDetailJob extends JobBase implements ProcessJobService {
	
	@Autowired
	private BankMessageDAO bankMessageDAO;
	
	@Autowired
	private ExtraDAO extraDAO;
	
	@Autowired
	private ForecastAccountDAO forecastAccountDAO;
	
	@Autowired
	private AccountAmountDetailDAO accountAmountDetailDAO;
	
	@Override
	// 一天一次 十二点九分触发
	@Scheduled(cron = "0 9 0 * * ? ")
	//	@Scheduled(cron = "0 0/2 * * * ? ")
	public void doJob() throws Exception {
		
		logger.info("处理资金余额明细任务开始在 " + DateUtil.simpleFormat(new Date()));
		// 总计执行365次
		int totalCount = 365 + 365 + 31 + 31;
		try {
			Money amount = Money.zero();
			// 获取今日期初余额 期初数据取当前日期银行信息中的实点余额 即活期存款
			BankMessageDO bankDO = new BankMessageDO();
			bankDO.setAccountType(SubjectAccountTypeEnum.CURRENT.code());
			List<BankMessageDO> banks = bankMessageDAO.findByCondition(bankDO, null, 0, 999, null,
				null);
			if (ListUtil.isNotEmpty(banks)) {
				for (BankMessageDO bank : banks) {
					amount.addTo(bank.getAmount());
				}
			}
			Date now = extraDAO.getSysdate();
			Date addTime = now;
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			// 循环获取dbdo
			List<AccountAmountDetailDO> detailDOs = new ArrayList<AccountAmountDetailDO>();
			for (int i = 0; i < totalCount; i++) {
				// 查询资金预测表，抓取今日的流入和流出
				//forecastAccountDAO.findByCondition(forecastAccount, forecastTimeStart, forecastTimeEnd, loginUserId, deptIdList, relatedRoleList, limitStart, pageSize, sortCol, sortOrder)
				List<ForecastAccountDO> forecasts = forecastAccountDAO.findByCondition(
					new ForecastAccountDO(), DateUtil.getStartTimeOfTheDate(cal.getTime()),
					DateUtil.getEndTimeOfTheDate(cal.getTime()), 0, null, null, 0, 999, null, null,null);
				Money in = Money.zero();
				Money out = Money.zero();
				for (ForecastAccountDO account : forecasts) {
					if (FundDirectionEnum.IN.code().equals(account.getFundDirection())) {
						in.addTo(account.getAmount());
					} else if (FundDirectionEnum.OUT.code().equals(account.getFundDirection())) {
						out.addTo(account.getAmount());
					}
				}
				Money last = amount.add(in).subtract(out);
				//---------
				AccountAmountDetailDO detailDO = new AccountAmountDetailDO();
				// 日期 时间
				detailDO.setTime(cal.getTime());
				// 日期加一
				cal.add(Calendar.DAY_OF_YEAR, 1);
				// 期初余额
				detailDO.setStartAmount(amount);
				//---------
				
				//  预计收款金额
				detailDO.setForecastInAmount(in);
				//  预计用款金额
				detailDO.setForecastOutAmount(out);
				// 预计账户余额
				detailDO.setForecastLastAmount(last);
				// 创建时间
				detailDO.setRawAddTime(addTime);
				
				// 将余额递减
				amount = last;
				detailDOs.add(detailDO);
			}
			
			// 先清空
			accountAmountDetailDAO.deleteAll();
			// 再 入库
			int count = 0;
			for (AccountAmountDetailDO detail : detailDOs) {
				accountAmountDetailDAO.insert(detail);
				count++;
				//				logger.info("处理资金余额完成+1:" + count);
			}
		} catch (Exception e) {
			logger.error("处理资金余额明细任务异常---异常原因：" + e.getMessage(), e);
		}
		logger.info("处理资金余额明细任务结束在 " + DateUtil.simpleFormat(new Date()));
	}
}
