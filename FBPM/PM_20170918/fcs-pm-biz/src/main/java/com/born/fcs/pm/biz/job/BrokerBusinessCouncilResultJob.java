package com.born.fcs.pm.biz.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.dal.daointerface.CouncilProjectDAO;
import com.born.fcs.pm.dal.daointerface.FBrokerBusinessDAO;
import com.born.fcs.pm.dal.dataobject.CouncilProjectDO;
import com.born.fcs.pm.dal.dataobject.FBrokerBusinessDO;
import com.born.fcs.pm.ws.enums.FormChangeApplyStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 经纪业务上会结果
 * 
 * @author wuzj
 *
 */
@Service("brokerBusinessCouncilResultJob")
public class BrokerBusinessCouncilResultJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FBrokerBusinessDAO FBrokerBusinessDAO;
	
	@Autowired
	private CouncilProjectDAO councilProjectDAO;
	
	@Scheduled(cron = "0 0/5 * * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		try {
			//查询上会中的经纪业务
			List<FBrokerBusinessDO> applys = FBrokerBusinessDAO.findCouncilWaiting(100);
			logger.info("扫描到上会中经纪业务数量：{} ", applys == null ? 0 : applys.size());
			if (applys != null && applys.size() > 0) {
				for (FBrokerBusinessDO apply : applys) {
					
					logger.info("扫描到上会中经纪业务申请单：{} ", apply.getProjectCode());
					
					//查询上会结果
					List<CouncilProjectDO> councilProject = councilProjectDAO
						.findByCouncilProjectCode(apply.getProjectCode());
					if (councilProject != null && councilProject.size() > 0) {
						CouncilProjectDO cp = councilProject.get(0); //取最新一条数据
						if (ProjectVoteResultEnum.END_NOPASS.code().equals(
							cp.getProjectVoteResult())) {//不通过
							apply.setStatus(FormChangeApplyStatusEnum.COUNCIL_DENY.code());
							FBrokerBusinessDAO.update(apply);
							logger.info("经纪业务上会未通过：{} ", apply.getProjectCode());
						} else if (ProjectVoteResultEnum.END_PASS.code().equals(
							cp.getProjectVoteResult())) {//通过
							apply.setStatus(FormChangeApplyStatusEnum.COUNCIL_APPROVAL.code());
							FBrokerBusinessDAO.update(apply);
							logger.info("经纪业务上会通过：{} ", apply.getProjectCode());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("扫描经纪业务上会结果异常：{}", e);
		}
	}
}
