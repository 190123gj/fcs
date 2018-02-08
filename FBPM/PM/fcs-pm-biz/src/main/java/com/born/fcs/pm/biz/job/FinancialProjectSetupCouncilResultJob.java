package com.born.fcs.pm.biz.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.dal.daointerface.FProjectFinancialDAO;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialDO;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.info.council.CouncilProjectInfo;
import com.born.fcs.pm.ws.service.council.CouncilProjectService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectSetupService;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 理财项目立项上会结果
 * 
 * @author wuzj
 *
 */
@Service("financialProjectSetupCouncilResultJob")
public class FinancialProjectSetupCouncilResultJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FinancialProjectService financialProjectService;
	
	@Autowired
	FinancialProjectSetupService financialProjectSetupService;
	
	@Autowired
	private FProjectFinancialDAO FProjectFinancialDAO;
	
	@Autowired
	private CouncilProjectService councilProjectService;
	
	@Scheduled(cron = "0 0/5 * * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		try {
			//Date now = new Date();
			//查询立项上会中的理财项目 
			List<FProjectFinancialDO> applys = FProjectFinancialDAO.findCouncilWaitingApply(100);
			logger.info("扫描到理财产品立项上会中 数量：{} ", applys == null ? 0 : applys.size());
			if (applys != null && applys.size() > 0) {
				for (FProjectFinancialDO apply : applys) {
					logger.info("扫描到立项上会中理财产品：{} ", apply.getProjectCode());
					//查询上会结果
					CouncilProjectInfo councilProject = councilProjectService
						.getLastInfoByApplyId(apply.getCouncilApplyId());
					if (councilProject.getProjectVoteResult() == ProjectVoteResultEnum.END_PASS) {
						//会议通过
						apply.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_APPROVAL.code());
						FProjectFinancialDAO.update(apply);
						
						logger.info("理财产品立项上会通过 {}", apply.getProjectCode());
						
						//同步预测数据到资金系统
						financialProjectSetupService.syncForecast(apply.getProjectCode());
					} else if (ProjectVoteResultEnum.END_NOPASS == councilProject
						.getProjectVoteResult()) {
						//会议不通过
						apply.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_DENY.code());
						FProjectFinancialDAO.update(apply);
						logger.info("理财产品立项上会未通过 {}", apply.getProjectCode());
					}
					
				}
			}
		} catch (Exception e) {
			logger.error("扫描理财项目立项上会结果异常---异常原因：{}", e);
		}
	}
}
