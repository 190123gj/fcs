package com.born.fcs.pm.biz.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.dal.daointerface.ProjectDAO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 项目复议机会过期
 * 
 * @author wuzj
 * 
 */
@Service("projectRecouncilExpireJob")
public class ProjectRecouncilExpireJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ProjectDAO projectDAO;
	
	@Scheduled(cron = "0 0/5 * * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		logger.info("项目复议过期任务开始在 {}", DateUtil.simpleFormat(new Date()));
		try {
			
			ProjectDO queryDO = new ProjectDO();
			//queryDO.setIsApproval(BooleanEnum.NO.code()); //未通过
			queryDO.setIsRecouncil(BooleanEnum.IS.code());//可复议标记
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, -6);
			
			//超过6个月未复议的将失去机会
			List<ProjectDO> projectList = projectDAO.findByCondition(queryDO, null, 0, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null,
				c.getTime(), null, null, null, null, null, null, null, null, null, 0, 50);
			if (ListUtil.isNotEmpty(projectList)) {
				for (ProjectDO project : projectList) {
					project.setIsRecouncil(BooleanEnum.NO.code());
					project.setStatus(ProjectStatusEnum.FAILED.code());
					projectDAO.update(project);
				}
			}
		} catch (Exception e) {
			logger.error("项目复议过期任务异常---异常原因：{}", e);
		}
		
		logger.info("项目复议过期任务结束在 {}", DateUtil.simpleFormat(new Date()));
	}
	
}
