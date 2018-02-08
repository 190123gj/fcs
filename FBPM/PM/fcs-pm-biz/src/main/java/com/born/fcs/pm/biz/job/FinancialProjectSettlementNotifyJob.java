package com.born.fcs.pm.biz.job;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.daointerface.ExtraDAO;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.enums.InterestSettlementWayEnum;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 理财项目结息通知任务
 * 
 * @author wuzj
 *
 */
@Service("financialProjectSettlementNotifyJob")
public class FinancialProjectSettlementNotifyJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FinancialProjectService financialProjectService;
	
	@Autowired
	private SiteMessageService siteMessageService;
	
	@Autowired
	ExtraDAO extraDAO;
	
	/**
	 * 统一发消息
	 * @throws Exception
	 * @see com.born.fcs.pm.biz.job.inter.ProcessJobService#doJob()
	 */
	@Scheduled(cron = "0 0 9 * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		
		try {
			
			Date now = extraDAO.getSysdate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			
			//每天 按天结息
			FinancialProjectQueryOrder order = new FinancialProjectQueryOrder();
			order.setStatus(FinancialProjectStatusEnum.PURCHASED.code());
			order.setPageSize(50);
			order.setInterestSettlementWay(InterestSettlementWayEnum.DAY);
			queryAndNotify(order);
			
			//星期3 按周结息
			if (calendar.get(Calendar.DAY_OF_WEEK) == 4) {
				order.setInterestSettlementWay(InterestSettlementWayEnum.WEEK);
				queryAndNotify(order);
			}
			
			//每月20日 按月结息
			if (calendar.get(Calendar.DAY_OF_MONTH) == 20) {
				order.setInterestSettlementWay(InterestSettlementWayEnum.MONTH);
				queryAndNotify(order);
				//月份
				int month = calendar.get(Calendar.MONTH) + 1;
				//按季度 3月20、6月20、12月20日结息
				if (month == 3 || month == 6 || month == 12) {
					order.setInterestSettlementWay(InterestSettlementWayEnum.SESSON);
					queryAndNotify(order);
					//按半年 6月20、12月20结息
					if (month == 6 || month == 12) {
						order.setInterestSettlementWay(InterestSettlementWayEnum.HALF_YEAR);
						queryAndNotify(order);
						//按年12月20结息
						if (month == 12) {
							order.setInterestSettlementWay(InterestSettlementWayEnum.YEAR);
							queryAndNotify(order);
						}
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("理财项目结息通知异常：{}", e);
		}
	}
	
	private void queryAndNotify(FinancialProjectQueryOrder order) {
		
		QueryBaseBatchResult<ProjectFinancialInfo> notifyList = financialProjectService
			.query(order);
		if (notifyList.getTotalCount() > 0) {
			//提醒结息
			for (ProjectFinancialInfo project : notifyList.getPageList()) {
				notify(project);
			}
			//循环翻页处理
			long totalPage = notifyList.getPageCount();
			long pageNumber = order.getPageNumber();
			while (pageNumber < totalPage) {
				pageNumber++;
				order.setPageNumber(pageNumber);
				notifyList = financialProjectService.query(order);
				//提醒结息
				for (ProjectFinancialInfo project : notifyList.getPageList()) {
					notify(project);
				}
			}
		}
	}
	
	//发送消息
	private void notify(ProjectFinancialInfo project) {
		String messageContent = "请及时对理财项目[ 项目编号： "
								+ project.getProjectCode()
								+ "，产品名称： "
								+ project.getProductName()
								+ " ]进行结息操作！<a href='/projectMg/financialProject/toSettlement.htm?projectCode="
								+ project.getProjectCode() + "'>马上就去</a>";
		MessageOrder order = MessageOrder.newSystemMessageOrder();
		order.setMessageSubject("理财项目结息提醒");
		order.setMessageTitle("理财项目结息提醒");
		order.setMessageContent(messageContent);
		SimpleUserInfo user = new SimpleUserInfo();
		user.setUserId(project.getCreateUserId());
		user.setUserAccount(project.getCreateUserAccount());
		user.setUserName(project.getCreateUserName());
		order.setSendUsers(new SimpleUserInfo[] { user });
		order.setWithSenderName(true);
		siteMessageService.addMessageInfo(order);
	}
}
