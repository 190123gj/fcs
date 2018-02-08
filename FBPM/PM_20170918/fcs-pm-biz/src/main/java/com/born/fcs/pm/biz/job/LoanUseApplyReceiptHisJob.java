//package com.born.fcs.pm.biz.job;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import com.born.fcs.pm.biz.job.inter.ProcessJobService;
//import com.born.fcs.pm.dal.daointerface.FLoanUseApplyReceiptDAO;
//import com.born.fcs.pm.dal.daointerface.FLoanUseApplyReceiptHiDAO;
//import com.born.fcs.pm.dal.dataobject.FLoanUseApplyReceiptDO;
//import com.born.fcs.pm.dal.dataobject.FLoanUseApplyReceiptHiDO;
//import com.born.fcs.pm.daointerface.ExtraDAO;
//import com.born.fcs.pm.util.DateUtil;
//import com.yjf.common.lang.beans.cglib.BeanCopier;
//
///**
// * 放用款数据每天备份  目前没什么用，
// * */
//@Service("loanUseApplyReceiptHisJob")
//public class LoanUseApplyReceiptHisJob extends noticeJobBase implements ProcessJobService {
//	
//	@Autowired
//	ExtraDAO extraDAO;
//	@Autowired
//	private FLoanUseApplyReceiptDAO fLoanUseApplyReceiptDAO;
//	@Autowired
//	private FLoanUseApplyReceiptHiDAO fLoanUseApplyReceiptHiDAO;
//	/** 上次同步日期 */
//	public static Date nextDate = null;
//	
//	@Scheduled(cron = "0 0 0,2,3 * * ?")
//	@Override
//	public void doJob() throws Exception {
//		if (!isRun)
//			return;
//		try {
//			logger.info("放用款批量数据备份开始:" + DateUtil.simpleFormat(new Date()));
//			
//			Date now = new Date();
//			Calendar nowCalendar = Calendar.getInstance();
//			nowCalendar.setTime(now);
//			nowCalendar.add(Calendar.HOUR_OF_DAY, -5);
//			now = DateUtil.getStartTimeOfTheDate(nowCalendar.getTime());
//			if (nextDate != null) {
//				long diff = now.getTime() - nextDate.getTime();
//				long hours = diff / (1000 * 60 * 60);
//				if (hours < 6) {
//					logger.info("放用款批量数据备份开始结束,今天已同步过，不在重复处理:project_date={}", now);
//					return;
//				}
//			}
//			
//			//累计异常次数
//			long errorCount = 0;
//			
//			List<FLoanUseApplyReceiptDO> datas = fLoanUseApplyReceiptDAO.findAll();
//			fLoanUseApplyReceiptHiDAO.deleteByProjectDate(now);
//			for (FLoanUseApplyReceiptDO data : datas) {
//				try {
//					FLoanUseApplyReceiptHiDO dataHis = new FLoanUseApplyReceiptHiDO();
//					BeanCopier.staticCopy(data, dataHis);
//					dataHis.setProjectDate(now);
//					dataHis.setId(0);
//					fLoanUseApplyReceiptHiDAO.insert(dataHis);
//				} catch (Exception e) {
//					errorCount++;
//				}
//				
//			}
//			nextDate = now;
//			logger.info("放用款批量数据备份结束:date={},共出现异常{}次", DateUtil.simpleFormat(new Date()),
//				errorCount);
//		} catch (Exception e) {
//			logger.error("放用款批量数据备份出错:{}", e);
//		}
//	}
//	
//	private Date getSysdate() {
//		try {
//			Date sysDate = extraDAO.getSysdate();
//			logger.info("系统时间：sysDate=" + sysDate);
//			return sysDate;
//		} catch (Exception e) {
//			logger.error(e.getLocalizedMessage(), e);
//		}
//		return new Date();
//	}
//	
//}
