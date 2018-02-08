package com.born.fcs.pm.biz.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.service.projectissueinformation.ProjectIssueInformationService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 项目发售时间到期逾期检查
 * 
 * @author Ji
 *
 */
@Service("projectIssueInformationJob")
public class ProjectIssueInformationJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ProjectIssueInformationService projectIssueInformationService;
	
	//每天0点到1点 每隔3分钟
	@Scheduled(cron = "0 0/3 0-1 * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		logger.info("处理项目发售到期任务开始在 " + DateUtil.simpleFormat(new Date()));
		try {
			//处理到期的项目
			ProjectQueryOrder queryOrder = new ProjectQueryOrder();
			int overDueSize = 0;
			List<ProjectInfo> listInfo = projectIssueInformationService.queryIssueProject(
				queryOrder).getPageList();
			if (ListUtil.isNotEmpty(listInfo)) {
				for (ProjectInfo projectInfo : listInfo) {
					//没有手动停止，但是发售截止日期大于当前系统日期，应结束发售
					
					if (projectInfo.getIsContinue() == BooleanEnum.NO
						&& projectInfo.getEndTime() != null && projectInfo.getStartTime() != null) {
						projectIssueInformationService.updateStatusByEndTime(projectInfo
							.getProjectCode());
						overDueSize++;
					}
				}
			}
			
			logger.info("处理项目发售到期任务，更新项目发售到期状态的项目数为： " + overDueSize);
		} catch (Exception e) {
			logger.error("处理项目发售到期任务异常---异常原因：" + e.getMessage(), e);
		}
		
		logger.info("处理项目发售到期任务结束在 " + DateUtil.simpleFormat(new Date()));
	}
	
}
