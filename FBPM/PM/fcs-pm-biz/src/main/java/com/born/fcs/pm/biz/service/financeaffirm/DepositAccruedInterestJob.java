package com.born.fcs.pm.biz.service.financeaffirm;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.born.fcs.pm.dal.daointerface.FFinanceAffirmDetailDAO;
import com.born.fcs.pm.dal.dataobject.FFinanceAffirmDetailDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import com.born.fcs.pm.biz.job.JobBase;
import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.daointerface.ExtraDAO;
import com.born.fcs.pm.util.DateUtil;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 存入存出保证金每日统计计提利息
 * 
 * @author heh
 *
 */
@Service("depositAccruedInterestJob")
public class DepositAccruedInterestJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FFinanceAffirmDetailDAO fFinanceAffirmDetailDAO;
	
	@Autowired
	ExtraDAO extraDAO;

	//每天凌晨0点执行
	@Scheduled(cron = "0 0 0 * * ? ")
	@Override
	public void doJob() throws Exception {
		
		if (!isRun)
			return;
		
		try {
			Date now = extraDAO.getSysdate();
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			logger.info("存入存出保证金每日计提任务开始在：{}", new Date());
			//得到需要计算计提利息的数据
			List<FFinanceAffirmDetailDO> detailDOs=fFinanceAffirmDetailDAO.findNeedAccruedInterest();
			if(ListUtil.isNotEmpty(detailDOs)){
				for(FFinanceAffirmDetailDO DO:detailDOs){
					//计算计提利息 保证金剩余金额*利率/365*（当前时间-存入时间）
					int days = DateUtil.calculateDecreaseDate(DateUtil.dtSimpleFormat(DO.getPayTime()),
							DateUtil.dtSimpleFormat(now));
					if(days<0){
						days=0;
					}
					Money interest=Money.zero();
					interest=DO.getReturnCustomerAmount().multiply(DO.getMarginRate()/100).multiply(days).divide(365);
					DO.setAccruedInterest(DO.getAccruedInterest().addTo(interest));
					fFinanceAffirmDetailDAO.update(DO);
				}
			}

		} catch (Exception e) {
			logger.error("存入存出保证金每日计提任务异常 {}", e);
		}
		logger.info("存入存出保证金每日计提任务结束在：{}", new Date());
	}

	public static void  main(String [] args){
		Money interest=Money.zero();
		Money leftAmount=new Money("1000.00");
		double margeRate=10.00;
		Date now =new Date();
		String payTime="2016-11-28";
		int days = 0;
		try {
			days = DateUtil.calculateDecreaseDate(DateUtil.dtSimpleFormat(DateUtil.parse(payTime)),
                    DateUtil.dtSimpleFormat(now));
			interest=leftAmount.multiply(margeRate/100).multiply(days).divide(365);
			System.out.println("相差"+days+"天，利息:"+interest);
		} catch (ParseException e) {
			e.printStackTrace();
		}


	}
}


