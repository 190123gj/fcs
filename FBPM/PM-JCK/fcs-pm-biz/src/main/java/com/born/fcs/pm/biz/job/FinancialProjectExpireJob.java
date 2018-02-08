package com.born.fcs.pm.biz.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.dal.daointerface.ProjectFinancialDAO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialDO;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 理财项目到期
 * 
 * @author wuzj
 *
 */
@Service("financialProjectExpireJob")
public class FinancialProjectExpireJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ProjectFinancialDAO projectFinancialDAO;
	
	@Scheduled(cron = "0 0/5 * * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		try {
			Date now = new Date();
			//查询到期的理财项目
			ProjectFinancialDO queryDO = new ProjectFinancialDO();
			queryDO.setActualExpireDate(now);
			List<ProjectFinancialDO> projects = projectFinancialDAO.findByCondition(queryDO, 0,
				null, null, null, null, null, null, null, null, null, null, 0, 50);
			logger.info("扫描到到期理财项目数量：{}", projects == null ? 0 : projects.size());
			if (projects != null && projects.size() > 0) {//修改成到期状态
				for (ProjectFinancialDO project : projects) {
					logger.info("理财项目到期 ： {}", project.getProjectCode());
					project.setStatus(FinancialProjectStatusEnum.EXPIRE.code());
					projectFinancialDAO.update(project);
				}
			}
		} catch (Exception e) {
			logger.error("处理理财项目到期任务异常---异常原因：" + e.getMessage(), e);
		}
	}
}
