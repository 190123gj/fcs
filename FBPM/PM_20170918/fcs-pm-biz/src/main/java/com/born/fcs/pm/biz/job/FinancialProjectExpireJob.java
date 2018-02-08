package com.born.fcs.pm.biz.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.dal.daointerface.ProjectFinancialDAO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialDO;
import com.born.fcs.pm.daointerface.ExtraDAO;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
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
	
	@Autowired
	ExtraDAO extraDAO;
	
	@Scheduled(cron = "0 0/5 * * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		try {
			Date now = extraDAO.getSysdate();
			List<ProjectFinancialDO> projects = projectFinancialDAO.findExipre(now, 50);
			String nowStr = DateUtil.simpleFormat(now);
			logger.info("扫描到到期理财项目数量 {} ：{}", nowStr, projects == null ? 0 : projects.size());
			if (projects != null && projects.size() > 0) {//修改成到期状态
				for (ProjectFinancialDO project : projects) {
					if (BooleanEnum.IS.code().equals(project.getIsOpen())) {//开放不过期
						continue;
					} else if (BooleanEnum.IS.code().equals(project.getIsRoll())) {
						if (project.getTimeLimit() > 0) {
							logger.info("理财项目到期滚动 {} ： {}", nowStr, project.getProjectCode());
							Calendar c = Calendar.getInstance();
							c.setTime(project.getCycleExpireDate());
							if (TimeUnitEnum.YEAR.code().equals(project.getTimeUnit())) {
								c.add(Calendar.YEAR, project.getTimeLimit());
							} else if (TimeUnitEnum.MONTH.code().equals(project.getTimeUnit())) {
								c.add(Calendar.MONTH, project.getTimeLimit());
							} else {
								c.add(Calendar.DAY_OF_MONTH, project.getTimeLimit());
							}
							c.set(Calendar.HOUR_OF_DAY, 0);
							c.set(Calendar.MINUTE, 0);
							c.set(Calendar.SECOND, 0);
							c.set(Calendar.MILLISECOND, 0);
							project.setCycleExpireDate(c.getTime());
						} else {
							logger.info("理财项目到期 {} ： {}", nowStr, project.getProjectCode());
							project.setStatus(FinancialProjectStatusEnum.EXPIRE.code());
						}
					} else {
						logger.info("理财项目到期 {} ： {}", nowStr, project.getProjectCode());
						project.setStatus(FinancialProjectStatusEnum.EXPIRE.code());
					}
					projectFinancialDAO.update(project);
				}
			}
		} catch (Exception e) {
			logger.error("处理理财项目到期任务异常：{}", e);
		}
	}
}
