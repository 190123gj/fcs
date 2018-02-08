package com.born.fcs.pm.biz.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.dal.daointerface.FProjectFinancialTansferApplyDAO;
import com.born.fcs.pm.dal.daointerface.ProjectFinancialDAO;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialTansferApplyDO;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.info.council.CouncilProjectInfo;
import com.born.fcs.pm.ws.service.council.CouncilProjectService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectTransferService;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 理财项目转让上会结果
 * 
 * @author wuzj
 *
 */
@Service("financialProjectTransferCouncilResultJob")
public class FinancialProjectTransferCouncilResultJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ProjectFinancialDAO projectFinancialDAO;
	
	@Autowired
	private FProjectFinancialTansferApplyDAO FProjectFinancialTansferApplyDAO;
	
	@Autowired
	private FinancialProjectTransferService financialProjectTransferService;
	
	@Autowired
	private CouncilProjectService councilProjectService;
	
	@Scheduled(cron = "0 0/5 * * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		try {
			//Date now = new Date();
			//查询转让上会中的理财项目 
			List<FProjectFinancialTansferApplyDO> applys = FProjectFinancialTansferApplyDAO
				.findCouncilWaitingApply(100);
			logger.info("扫描到上会中理财项目转让数量：{} ", applys == null ? 0 : applys.size());
			if (applys != null && applys.size() > 0) {
				for (FProjectFinancialTansferApplyDO apply : applys) {
					logger.info("扫描到转让上会中理财项目：{} ", apply.getProjectCode());
					
					//查询上会结果
					CouncilProjectInfo councilProject = councilProjectService
						.getLastInfoByApplyId(apply.getCouncilApplyId());
					if (councilProject.getProjectVoteResult() == ProjectVoteResultEnum.END_PASS) {
						//会议通过
						apply.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_APPROVAL.code());
						FProjectFinancialTansferApplyDAO.update(apply);
						logger.info("理财项目转让上会通过  projectCode : {}", apply.getProjectCode());
						//同步到资金预测
						financialProjectTransferService.syncForecast(apply.getApplyId());
					} else if (ProjectVoteResultEnum.END_NOPASS == councilProject
						.getProjectVoteResult()) {
						//会议不通过
						apply.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_DENY.code());
						FProjectFinancialTansferApplyDAO.update(apply);
						logger.info("理财项目转让上会未通过  projectCode : {}", apply.getProjectCode());
					}
					
				}
			}
		} catch (Exception e) {
			logger.error("扫描理财项目转让上会结果异常---异常原因：{}", e);
		}
	}
}
