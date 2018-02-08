//package com.born.fcs.pm.biz.job;
//
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import com.born.fcs.pm.biz.job.inter.ProcessJobService;
//import com.born.fcs.pm.dal.daointerface.CouncilProjectDAO;
//import com.born.fcs.pm.dal.daointerface.ProjectFinancialDAO;
//import com.born.fcs.pm.dal.dataobject.CouncilProjectDO;
//import com.born.fcs.pm.dal.dataobject.ProjectFinancialDO;
//import com.born.fcs.pm.util.DateUtil;
//import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
//import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
//import com.yjf.common.log.Logger;
//import com.yjf.common.log.LoggerFactory;
//
///**
// * 
// * 理财项目上会结果
// * 
// * @author wuzj
// *
// */
//@Service("financialProjectCouncilResultJob")
//public class FinancialProjectCouncilResultJob extends JobBase implements ProcessJobService {
//	
//	protected final Logger logger = LoggerFactory.getLogger(getClass());
//	
//	@Autowired
//	private ProjectFinancialDAO projectFinancialDAO;
//	
//	@Autowired
//	private CouncilProjectDAO councilProjectDAO;
//	
//	@Scheduled(cron = "0 0/5 * * * ? ")
//	@Override
//	public void doJob() throws Exception {
//		if (!isRun)
//			return;
//		logger.info("扫描理财项目上会结果开始在 " + DateUtil.simpleFormat(new Date()));
//		try {
//			//Date now = new Date();
//			//查询上会中的理财项目 
//			ProjectFinancialDO queryDO = new ProjectFinancialDO();
//			queryDO.setStatus(FinancialProjectStatusEnum.COUNCIL_WAITING.code());
//			List<ProjectFinancialDO> projects = projectFinancialDAO.findByCondition(queryDO, 0,
//				null, null, null, null, null, null, null, null, null, null, 0, 20);
//			logger.info("扫描到上会中理财项目数量：{} ", projects == null ? 0 : projects.size());
//			if (projects != null && projects.size() > 0) {
//				for (ProjectFinancialDO project : projects) {
//					//查询上会结果
//					List<CouncilProjectDO> councilProject = councilProjectDAO
//						.findByCouncilProjectCode(project.getProjectCode());
//					if (councilProject != null && councilProject.size() > 0) {
//						CouncilProjectDO cp = councilProject.get(0); //取最新一条数据
//						if (ProjectVoteResultEnum.END_NOPASS.code().equals(
//							cp.getProjectVoteResult())) {//不通过
//							project.setStatus(FinancialProjectStatusEnum.FAILED.code());
//							projectFinancialDAO.update(project);
//							logger.info("理财项目上会未通过：{} " + project.getProjectCode());
//						} else if (ProjectVoteResultEnum.END_PASS.code().equals(
//							cp.getProjectVoteResult())) {//通过
//							project.setStatus(FinancialProjectStatusEnum.CAPITAL_APPLY_WAITING
//								.code());
//							projectFinancialDAO.update(project);
//							logger.info("理财项目上会通过：{} 进入待购买状态" + project.getProjectCode());
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.error("扫描理财项目上会结果异常---异常原因：" + e.getMessage(), e);
//		}
//		
//		logger.info("扫描理财项目上会结果结束在 " + DateUtil.simpleFormat(new Date()));
//	}
//}
