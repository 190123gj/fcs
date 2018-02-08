package com.born.fcs.pm.biz.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
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
 * 理财项目到期通知任务
 * 
 * @author wuzj
 *
 */
@Service("financialProjectExpireNotifyJob")
public class FinancialProjectExpireNotifyJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FinancialProjectService financialProjectService;
	
	@Autowired
	private SiteMessageService siteMessageService;
	
	/**
	 * 晚上2点统一发消息
	 * @throws Exception
	 * @see com.born.fcs.pm.biz.job.inter.ProcessJobService#doJob()
	 */
	@Scheduled(cron = "0 0 2 * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		
		try {
			FinancialProjectQueryOrder order = new FinancialProjectQueryOrder();
			order.setStatus(FinancialProjectStatusEnum.EXPIRE.code());
			order.setPageSize(50);
			QueryBaseBatchResult<ProjectFinancialInfo> expireList = financialProjectService
				.query(order);
			if (expireList.getTotalCount() > 0) {
				//提醒到期
				for (ProjectFinancialInfo expire : expireList.getPageList()) {
					notify(expire);
				}
				//循环翻页处理
				long totalPage = expireList.getPageCount();
				long pageNumber = order.getPageNumber();
				while (pageNumber < totalPage) {
					pageNumber++;
					order.setPageNumber(pageNumber);
					expireList = financialProjectService.query(order);
					//提醒到期
					for (ProjectFinancialInfo expire : expireList.getPageList()) {
						notify(expire);
					}
				}
			}
		} catch (Exception e) {
			logger.error("理财项目到期通知异常：{}", e);
		}
	}
	
	//发送消息
	private void notify(ProjectFinancialInfo project) {
		String messageContent = "请及时对理财项目[ 项目编号： "
								+ project.getProjectCode()
								+ "，产品名称： "
								+ project.getProductName()
								+ " ]进行到期项目信息维护！<a href='/projectMg/financialProject/maintainExpire.htm?projectCode="
								+ project.getProjectCode() + "'>马上就去</a>";
		MessageOrder order = MessageOrder.newSystemMessageOrder();
		order.setMessageSubject("理财项目到期信息维护提醒");
		order.setMessageTitle("理财项目到期信息维护提醒");
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
