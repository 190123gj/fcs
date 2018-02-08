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
//import com.born.fcs.pm.dal.daointerface.FormChangeApplyDAO;
//import com.born.fcs.pm.dal.dataobject.CouncilProjectDO;
//import com.born.fcs.pm.dal.dataobject.FormChangeApplyDO;
//import com.born.fcs.pm.util.DateUtil;
//import com.born.fcs.pm.ws.enums.BooleanEnum;
//import com.born.fcs.pm.ws.enums.FormChangeApplyStatusEnum;
//import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
//import com.yjf.common.log.Logger;
//import com.yjf.common.log.LoggerFactory;
//
///**
// * 
// * 签报上会结果
// * 
// * @author wuzj
// *
// */
//@Service("formChangeApplyCouncilResultJob")
//public class FormChangeApplyCouncilResultJob extends JobBase implements ProcessJobService {
//	
//	protected final Logger logger = LoggerFactory.getLogger(getClass());
//	
//	@Autowired
//	private FormChangeApplyDAO formChangeApplyDAO;
//	
//	@Autowired
//	private CouncilProjectDAO councilProjectDAO;
//	
//	@Scheduled(cron = "0 0/5 * * * ? ")
//	@Override
//	public void doJob() throws Exception {
//		if (!isRun)
//			return;
//		logger.info("扫描签报上会结果开始在 " + DateUtil.simpleFormat(new Date()));
//		try {
//			//Date now = new Date();
//			//查询上会中的签报申请
//			FormChangeApplyDO applyDO = new FormChangeApplyDO();
//			applyDO.setIsNeedCouncil(BooleanEnum.IS.code());
//			applyDO.setStatus(FormChangeApplyStatusEnum.COUNCIL_WAITING.code());
//			List<FormChangeApplyDO> applys = formChangeApplyDAO.findByCondition(applyDO, 0, null,
//				0, null, null, null, null, 0, 20);
//			logger.info("扫描到上会中签报数量：{} ", applys == null ? 0 : applys.size());
//			if (applys != null && applys.size() > 0) {
//				for (FormChangeApplyDO apply : applys) {
//					//查询上会结果
//					List<CouncilProjectDO> councilProject = councilProjectDAO
//						.findByCouncilProjectCode(apply.getApplyCode());
//					if (councilProject != null && councilProject.size() > 0) {
//						CouncilProjectDO cp = councilProject.get(0); //取最新一条数据
//						if (ProjectVoteResultEnum.END_NOPASS.code().equals(
//							cp.getProjectVoteResult())) {//不通过
//							apply.setStatus(FormChangeApplyStatusEnum.COUNCIL_DENY.code());
//							formChangeApplyDAO.update(apply);
//							logger.info("签报上会未通过：{} " + apply.getApplyCode());
//						} else if (ProjectVoteResultEnum.END_PASS.code().equals(
//							cp.getProjectVoteResult())) {//通过
//							apply.setStatus(FormChangeApplyStatusEnum.COUNCIL_APPROVAL.code());
//							formChangeApplyDAO.update(apply);
//							logger.info("签报上会通过：{} " + apply.getApplyCode());
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.error("扫描签报上会结果异常---异常原因：" + e.getMessage(), e);
//		}
//		
//		logger.info("扫描签报上会结果结束在 " + DateUtil.simpleFormat(new Date()));
//	}
//}
