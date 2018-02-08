package com.born.fcs.pm.biz.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.am.ws.order.pledgeasset.AssetRelationProjectBindOrder;
import com.born.fcs.am.ws.service.pledgeasset.PledgeAssetService;
import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.dal.daointerface.FCouncilSummaryProjectDAO;
import com.born.fcs.pm.dal.daointerface.ProjectDAO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 项目批复作废
 * 
 * @author wuzj
 * 
 */
@Service("projectApprovalExpireJob")
public class ProjectApprovalExpireJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private FCouncilSummaryProjectDAO FCouncilSummaryProjectDAO;
	
	@Autowired
	PledgeAssetService pledgeAssetServiceClient;
	
	@Scheduled(cron = "0 0/5 * * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		logger.info("项目批复作废任务开始在 {}", DateUtil.simpleFormat(new Date()));
		try {
			
			//Date now = new Date();
			ProjectDO queryDO = new ProjectDO();
			queryDO.setIsApproval(BooleanEnum.IS.code()); //已通过
			queryDO.setIsRecouncil(BooleanEnum.NO.code()); //不可复议、批复作废后会设置成IS
			queryDO.setIsApprovalDel(BooleanEnum.NO.code());
			queryDO.setPhases(ProjectPhasesEnum.CONTRACT_PHASES.code()); //合同阶段
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, -3);
			//查询3个月内合同未签订完成的项目
			List<ProjectDO> projectList = projectDAO.findByCondition(queryDO, null, 0, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null,
				c.getTime(), BooleanEnum.NO.code(), null, null, null, null, null, null, null, null,
				0, 50);
			if (ListUtil.isNotEmpty(projectList)) {
				for (ProjectDO project : projectList) {
					//if (project.getLastRecouncilTime() == null) {//只能复议一次
					project.setIsRecouncil(BooleanEnum.IS.code());
					//}
					project.setIsApprovalDel(BooleanEnum.IS.code());
					//					project.setStatus(ProjectStatusEnum.FAILED.code());
					projectDAO.update(project);
					
					FCouncilSummaryProjectDO sp = FCouncilSummaryProjectDAO.findBySpCode(project
						.getSpCode());
					if (sp != null) {
						sp.setIsDel(BooleanEnum.IS.code());
						FCouncilSummaryProjectDAO.update(sp);
					}
					// 取消资产关联
					AssetRelationProjectBindOrder bindOrder = new AssetRelationProjectBindOrder();
					BeanCopier.staticCopy(project, bindOrder);
					bindOrder.setDelOld(true);
					bindOrder.setAssetList(null);
					logger.info("解除资产项目关系 {} ", bindOrder);
					pledgeAssetServiceClient.assetRelationProject(bindOrder);
				}
			}
		} catch (Exception e) {
			logger.error("项目批复作废任务异常---异常原因：{}", e);
		}
		
		logger.info("项目批复作废任务结束在 {}", DateUtil.simpleFormat(new Date()));
	}
	
}
