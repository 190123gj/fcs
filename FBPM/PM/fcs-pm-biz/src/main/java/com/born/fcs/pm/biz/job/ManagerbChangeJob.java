package com.born.fcs.pm.biz.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.dal.daointerface.FManagerbChangeApplyDAO;
import com.born.fcs.pm.dal.daointerface.ProjectDAO;
import com.born.fcs.pm.dal.dataobject.FManagerbChangeApplyDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbStatusEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbWayEnum;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 业务经理B角变更任务
 * 
 * @author wuzj
 *
 */
@Service("managerbChangeJob")
public class ManagerbChangeJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FManagerbChangeApplyDAO FManagerbChangeApplyDAO;
	
	@Autowired
	private ProjectRelatedUserService projectRelatedUserService;
	
	@Autowired
	ProjectDAO projectDAO;
	
	@Scheduled(cron = "0 0/1 * * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		
		logger.info("业务经理B角变更任务开始在 {}", DateUtil.simpleFormat(new Date()));
		try {
			List<FManagerbChangeApplyDO> applyList = FManagerbChangeApplyDAO
				.findWaitChangeApply(50);
			logger.info("扫描到业务经理B角变更申请数量", applyList == null ? 0 : applyList.size());
			if (ListUtil.isNotEmpty(applyList)) {
				for (FManagerbChangeApplyDO apply : applyList) {
					
					ProjectRelatedUserOrder changeOrder = new ProjectRelatedUserOrder();
					if (StringUtil.equals(apply.getStatus(),
						ChangeManagerbStatusEnum.WAIT_RESTORE.code())) { //待还原的
						changeOrder.setProjectCode(apply.getProjectCode());
						changeOrder.setUserId(apply.getOldBid());
						changeOrder.setUserName(apply.getOldBname());
						changeOrder.setUserAccount(apply.getOldBaccount());
						changeOrder.setUserType(ProjectRelatedUserTypeEnum.BUSI_MANAGERB);
						changeOrder.setDelOrinigal(true);
						changeOrder.setTransferTime(new Date());
						changeOrder.setRemark("B角变更"
												+ DateUtil.simpleFormat(apply.getChangeStartTime())
												+ "到"
												+ DateUtil.simpleFormat(apply.getChangeEndTime())
												+ "还原");
						FcsBaseResult changeResult = projectRelatedUserService
							.setRelatedUser(changeOrder);
						logger.info("项目{}-{}，业务经理B角变更超过时间段{} 到 {} ，由 {} 还原成 {}，还原结果：{}",
							apply.getProjectName(), apply.getProjectCode(),
							DateUtil.simpleFormat(apply.getChangeStartTime()),
							DateUtil.simpleFormat(apply.getChangeEndTime()), apply.getNewBname(),
							apply.getOldBname(), changeResult);
						if (changeResult.isSuccess()) {
							apply.setStatus(ChangeManagerbStatusEnum.APPLIED.code());
							FManagerbChangeApplyDAO.update(apply);
							
							ProjectDO project = projectDAO
								.findByProjectCode(apply.getProjectCode());
							if (project != null) {
								project.setBusiManagerbId(apply.getOldBid());
								project.setBusiManagerbName(apply.getOldBname());
								project.setBusiManagerbAccount(apply.getOldBaccount());
								projectDAO.update(project);
							}
						}
					} else if (StringUtil.equals(apply.getStatus(),
						ChangeManagerbStatusEnum.APPROVAL.code())) { //待变更的
						changeOrder.setProjectCode(apply.getProjectCode());
						changeOrder.setUserId(apply.getNewBid());
						changeOrder.setUserName(apply.getNewBname());
						changeOrder.setUserAccount(apply.getNewBaccount());
						changeOrder.setUserType(ProjectRelatedUserTypeEnum.BUSI_MANAGERB);
						changeOrder.setDelOrinigal(true);
						changeOrder.setTransferTime(new Date());
						changeOrder.setRemark("B角变更"
												+ DateUtil.simpleFormat(apply.getChangeStartTime())
												+ "到"
												+ DateUtil.simpleFormat(apply.getChangeEndTime()));
						FcsBaseResult changeResult = projectRelatedUserService
							.setRelatedUser(changeOrder);
						logger.info("项目{}-{}，业务经理B角在 {} 到 {} ，由 {} 变更成 {}，变更结果：{}",
							apply.getProjectName(), apply.getProjectCode(),
							DateUtil.simpleFormat(apply.getChangeStartTime()),
							DateUtil.simpleFormat(apply.getChangeEndTime()), apply.getOldBname(),
							apply.getNewBname(), changeResult);
						if (changeResult.isSuccess()) { //变成待还原状态
							if (StringUtil.equals(apply.getChangeWay(),
								ChangeManagerbWayEnum.PERIOD.code())) {
								apply.setStatus(ChangeManagerbStatusEnum.WAIT_RESTORE.code());
							} else {
								apply.setStatus(ChangeManagerbStatusEnum.APPLIED.code());
							}
							FManagerbChangeApplyDAO.update(apply);
							
							ProjectDO project = projectDAO
								.findByProjectCode(apply.getProjectCode());
							if (project != null) {
								project.setBusiManagerbId(apply.getNewBid());
								project.setBusiManagerbName(apply.getNewBname());
								project.setBusiManagerbAccount(apply.getNewBaccount());
								projectDAO.update(project);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("业务经理B角变更任务异常---异常原因：{}", e);
		}
		
		logger.info("业务经理B角变更任务结束在 {}", DateUtil.simpleFormat(new Date()));
	}
}
