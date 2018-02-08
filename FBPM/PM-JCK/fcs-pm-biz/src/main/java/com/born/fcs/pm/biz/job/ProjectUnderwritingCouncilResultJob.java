package com.born.fcs.pm.biz.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.dal.daointerface.FInvestigationDAO;
import com.born.fcs.pm.dal.daointerface.ProjectDAO;
import com.born.fcs.pm.dal.dataobject.FInvestigationDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.info.council.CouncilProjectInfo;
import com.born.fcs.pm.ws.service.council.CouncilProjectService;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 承销业务总经理办公会结果
 * 
 * @author wuzj
 *
 */
@Service("projectUnderwritingCouncilResultJob")
public class ProjectUnderwritingCouncilResultJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FInvestigationDAO FInvestigationDAO;
	
	@Autowired
	private CouncilProjectService councilProjectService;
	
	@Autowired
	private ProjectDAO projectDAO;
	
	@Scheduled(cron = "0 0/5 * * * ? ")
	@Override
	public void doJob() throws Exception {
		
		if (!isRun)
			return;
		try {
			//Date now = new Date();
			//查询转让上会中的理财项目 
			List<FInvestigationDO> applys = FInvestigationDAO.findCouncilWaitingApply(20);
			logger.info("扫描到上会中承销项目数量 ：{} ", applys == null ? 0 : applys.size());
			if (applys != null && applys.size() > 0) {
				for (FInvestigationDO apply : applys) {
					logger.info("扫描到上会中承销项目  projectCode ：{} ", apply.getProjectCode());
					//查询上会结果
					CouncilProjectInfo councilProject = null;
					try {
						councilProject = councilProjectService.getLastInfoByApplyId(apply
							.getCouncilApplyId());
					} catch (Exception e) {
						logger.error("获取上会结果出错：{}", e);
					}
					
					if (councilProject == null)
						continue;
					
					if (councilProject.getProjectVoteResult() == ProjectVoteResultEnum.END_PASS) {
						//会议通过
						apply.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_APPROVAL.code());
						FInvestigationDAO.update(apply);
						
						//项目通过 进入合同阶段
						ProjectDO proejctDO = projectDAO.findByProjectCode(apply.getProjectCode());
						proejctDO.setIsApproval(BooleanEnum.IS.code()); //项目已批复
						proejctDO.setApprovalTime(new Date());
						proejctDO.setPhases(ProjectPhasesEnum.CONTRACT_PHASES.code());
						proejctDO.setPhasesStatus(ProjectPhasesStatusEnum.WAITING.code());
						projectDAO.update(proejctDO);
						
						logger.info("承销项目上会通过 projectCode : {}", proejctDO.getProjectCode());
					} else if (ProjectVoteResultEnum.END_NOPASS == councilProject
						.getProjectVoteResult()) {
						
						//会议不通过
						apply.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_DENY.code());
						FInvestigationDAO.update(apply);
						
						//项目未通过 项目失败
						ProjectDO proejctDO = projectDAO.findByProjectCode(apply.getProjectCode());
						proejctDO.setIsApproval(BooleanEnum.NO.code());
						proejctDO.setApprovalTime(new Date());
						proejctDO.setPhasesStatus(ProjectPhasesStatusEnum.NOPASS.code());
						proejctDO.setStatus(ProjectStatusEnum.FAILED.code());
						if (proejctDO.getLastRecouncilTime() == null) { //只复议一次
							proejctDO.setIsRecouncil(BooleanEnum.IS.code());
						}
						projectDAO.update(proejctDO);
						logger.info("承销项目上会未通过  projectCode : {}", proejctDO.getProjectCode());
					}
				}
			}
		} catch (Exception e) {
			logger.error("扫描承销项目上会结果异常---异常原因：{}", e);
		}
		
	}
}
