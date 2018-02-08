package com.born.fcs.pm.biz.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.crm.ws.service.info.CustomerDetailInfo;
import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.dal.daointerface.ProjectDAO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.daointerface.ExtraDAO;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.crm.service.customer.CustomerServiceClient;
import com.born.fcs.pm.integration.risk.service.RiskSystemFacadeClient;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.ExpireProjectStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.info.expireproject.ExpireProjectInfo;
import com.born.fcs.pm.ws.order.common.ChangeProjectStatusOrder;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.expireproject.ExpireProjectOrder;
import com.born.fcs.pm.ws.order.expireproject.ExpireProjectQueryOrder;
import com.born.fcs.pm.ws.result.common.ProjectResult;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;
import com.born.fcs.pm.ws.service.expireproject.ExpireProjectService;
import com.born.fcs.pm.ws.service.releaseproject.ReleaseProjectService;
import com.bornsoft.pub.enums.RiskTypeEnum;
import com.bornsoft.pub.order.risk.SynRiskInfoOrder;
import com.bornsoft.utils.base.BornSynResultBase;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 项目到期逾期
 * 
 * @author lirz
 * 
 * 2016-4-21 下午3:24:28
 */
@Service("projectExpireJob")
public class ProjectExpireJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ExpireProjectService expireProjectService;
	@Autowired
	private ReleaseProjectService releaseProjectService;
	@Autowired
	private ExtraDAO extraDAO;
	@Autowired
	private ProjectDAO projectDAO;
	@Autowired
	SiteMessageService siteMessageService;//消息推送
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	@Autowired
	CouncilSummaryService councilSummaryService;
	@Autowired
	ProjectService projectService;
	@Autowired
	RiskSystemFacadeClient riskSystemFacadeClient;
	@Autowired
	CustomerServiceClient customerServiceClient;
	
//	@Scheduled(cron = "0 0 9-18 * * ? ")
	@Scheduled(cron = "0 0 5 ? * MON")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		logger.info("处理到期逾期任务开始在 " + DateUtil.simpleFormat(new Date()));
		try {
			Date today = new Date();
			//处理已经过期的项目
			ExpireProjectQueryOrder queryOrder = new ExpireProjectQueryOrder();
			queryOrder.setStatus(ExpireProjectStatusEnum.EXPIRE);
			queryOrder.setPageNumber(1L);
			queryOrder.setPageSize(100L);
			int overDueSize = 0;
			int expireSize = 0;
			List<ExpireProjectInfo> expireInfos = expireProjectService.queryExpireProjectInfo(
				queryOrder).getPageList();
			if (ListUtil.isNotEmpty(expireInfos)) {
				for (ExpireProjectInfo info : expireInfos) {
					int days = DateUtil.calculateDecreaseDate(DateUtil.dtSimpleFormat(today),
						DateUtil.dtSimpleFormat(info.getExpireDate()));
					if (days < 0) {
						ProjectDO projectDO = projectDAO.findByProjectCode(info.getProjectCode());
						ExpireProjectOrder order = new ExpireProjectOrder();
						order.setId(info.getId());
						Money releasingAmount = new Money(0);
						if (ProjectUtil.isBond(projectDO.getBusiType())) {
							releasingAmount = projectDO.getAccumulatedIssueAmount().subtract(
								projectDO.getReleasedAmount());//发债类: 已发行金额 - 已解保金额
						} else {
							releasingAmount = projectDO.getLoanedAmount().subtract(
								projectDO.getReleasedAmount());//在保余额，已放款金额-已解保金额
						}
						if (releasingAmount.compareTo(new Money(0)) == 0
							&& (!ProjectUtil.isUnderwriting(projectDO.getBusiType()))) {
							order.setIsDONE("IS");
							expireProjectService.updateToDone(order);
							ChangeProjectStatusOrder changeOrder = new ChangeProjectStatusOrder();
							changeOrder.setStatus(ProjectStatusEnum.FINISH);
							changeOrder.setPhaseStatus(ProjectPhasesStatusEnum.APPROVAL);
							changeOrder.setPhase(ProjectPhasesEnum.FINISH_PHASES);
							changeOrder.setProjectCode(projectDO.getProjectCode());
							ProjectResult presult = projectService.changeProjectStatus(changeOrder);
							if (!presult.isSuccess()) {
								logger.error("更新项目状态出错：" + presult.getMessage());
							}
						} else {
							expireProjectService.updateToOverdue(order);
							ChangeProjectStatusOrder changeOrder = new ChangeProjectStatusOrder();
							changeOrder.setStatus(ProjectStatusEnum.OVERDUE);
							changeOrder.setProjectCode(projectDO.getProjectCode());
							ProjectResult presult = projectService.changeProjectStatus(changeOrder);
							if (!presult.isSuccess()) {
								logger.error("更新项目状态出错：" + presult.getMessage());
							}
							overDueSize++;
							//逾期就将信息写到风险监控系统 承销不管
							if (!ProjectUtil.isUnderwriting(projectDO.getBusiType())) {
								SynRiskInfoOrder riskInfoOrder = new SynRiskInfoOrder();
								List<SynRiskInfoOrder.RiskInfo> list = Lists.newArrayList();
								SynRiskInfoOrder.RiskInfo riskInfo = new SynRiskInfoOrder.RiskInfo();
								CustomerDetailInfo customerDetailInfo = customerServiceClient
									.queryByUserId(projectDO.getCustomerId());
								riskInfo.setLicenseNo(customerDetailInfo.getBusiLicenseNo());
								riskInfo.setCustomName(projectDO.getCustomerName());
								riskInfo.setRiskType(RiskTypeEnum.OVERDUE);
								riskInfo.setCreditAmount(projectDO.getAmount());
								riskInfo.setCreditStartTime(DateUtil.dtSimpleFormat(projectDO
									.getStartTime()));
								riskInfo.setCreditEndTime(DateUtil.dtSimpleFormat(projectDO
									.getEndTime()));
								list.add(riskInfo);
								riskInfoOrder.setList(list);
								riskInfoOrder.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
								BornSynResultBase result = riskSystemFacadeClient
									.synRiskInfo(riskInfoOrder);
								logger.info("项目逾期同步风险系统={},result={}", riskInfo, result);
							}
							
						}
					} else {//到期项目一周发一次
						ProjectDO projectDO = projectDAO.findByProjectCode(info.getProjectCode());
						Calendar cal = Calendar.getInstance();
						cal.setTime(new Date());
						int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
						if (week == 1) {//周一发
							sendMessage(projectDO, info, today);
						}
					}
				}
			}
			
			//处理到期的项目
			queryOrder = new ExpireProjectQueryOrder();
			queryOrder.setStatus(ExpireProjectStatusEnum.UNDUE);
			queryOrder.setPageNumber(1L);
			queryOrder.setPageSize(999L);
			List<ExpireProjectInfo> undueInfos = expireProjectService.queryExpireProjectInfo(
				queryOrder).getPageList();
			if (ListUtil.isNotEmpty(undueInfos)) {
				for (ExpireProjectInfo info : undueInfos) {
					int days = DateUtil.calculateDecreaseDate(DateUtil.dtSimpleFormat(today),
						DateUtil.dtSimpleFormat(info.getExpireDate()));
					;
					if (days < 0) {
						ProjectDO projectDO = projectDAO.findByProjectCode(info.getProjectCode());
						ExpireProjectOrder order = new ExpireProjectOrder();
						order.setId(info.getId());
						Money releasingAmount = new Money(0);
						if (ProjectUtil.isBond(projectDO.getBusiType())) {
							releasingAmount = projectDO.getAccumulatedIssueAmount().subtract(
								projectDO.getReleasedAmount());//发债类: 已发行金额 - 已解保金额
						} else {
							releasingAmount = projectDO.getLoanedAmount().subtract(
								projectDO.getReleasedAmount());//在保余额，已放款金额-已解保金额
						}
						if (releasingAmount.compareTo(new Money(0)) == 0
							&& (!ProjectUtil.isUnderwriting(projectDO.getBusiType()))) {
							order.setIsDONE("IS");
							expireProjectService.updateToDone(order);
							ChangeProjectStatusOrder changeOrder = new ChangeProjectStatusOrder();
							changeOrder.setStatus(ProjectStatusEnum.FINISH);
							changeOrder.setPhaseStatus(ProjectPhasesStatusEnum.APPROVAL);
							changeOrder.setPhase(ProjectPhasesEnum.FINISH_PHASES);
							changeOrder.setProjectCode(projectDO.getProjectCode());
							ProjectResult presult = projectService.changeProjectStatus(changeOrder);
							if (!presult.isSuccess()) {
								logger.error("更新项目状态出错：" + presult.getMessage());
							}
						} else {
							expireProjectService.updateToOverdue(order);
							ChangeProjectStatusOrder changeOrder = new ChangeProjectStatusOrder();
							changeOrder.setStatus(ProjectStatusEnum.OVERDUE);
							changeOrder.setProjectCode(projectDO.getProjectCode());
							ProjectResult presult = projectService.changeProjectStatus(changeOrder);
							if (!presult.isSuccess()) {
								logger.error("更新项目状态出错：" + presult.getMessage());
							}
							overDueSize++;
						}
					} else if (days < 30) {
						logger.info("today=" + today + ",到期日期：" + info.getExpireDate() + "days="
									+ days);
						ProjectDO projectDO = projectDAO.findByProjectCode(info.getProjectCode());
						ExpireProjectOrder order = new ExpireProjectOrder();
						order.setId(info.getId());
						expireProjectService.updateToExpire(order);
						expireSize++;
						ChangeProjectStatusOrder changeOrder = new ChangeProjectStatusOrder();
						changeOrder.setStatus(ProjectStatusEnum.EXPIRE);
						changeOrder.setProjectCode(projectDO.getProjectCode());
						ProjectResult presult = projectService.changeProjectStatus(changeOrder);
						if (!presult.isSuccess()) {
							logger.error("更新项目状态出错：" + presult.getMessage());
						}
						sendMessage(projectDO, info, today);
					}
					//					if (days == 0) {
					//						releaseProjectService.addBond(info.getProjectCode());
					//					}
				}
			}
			
			//处理逾期项目
			queryOrder = new ExpireProjectQueryOrder();
			queryOrder.setStatus(ExpireProjectStatusEnum.OVERDUE);
			queryOrder.setPageNumber(1L);
			queryOrder.setPageSize(999L);
			List<ExpireProjectInfo> overdueInfos = expireProjectService.queryExpireProjectInfo(
				queryOrder).getPageList();
			if (ListUtil.isNotEmpty(undueInfos)) {
				for (ExpireProjectInfo info : overdueInfos) {
					ProjectDO projectDO = projectDAO.findByProjectCode(info.getProjectCode());
					ExpireProjectOrder order = new ExpireProjectOrder();
					order.setId(info.getId());
					Money releasingAmount = new Money(0);
					if (ProjectUtil.isBond(projectDO.getBusiType())) {
						releasingAmount = projectDO.getAccumulatedIssueAmount().subtract(
							projectDO.getReleasedAmount());//发债类: 已发行金额 - 已解保金额
					} else {
						releasingAmount = projectDO.getLoanedAmount().subtract(
							projectDO.getReleasedAmount());//在保余额，已放款金额-已解保金额
					}
					if (releasingAmount.compareTo(new Money(0)) == 0
						&& (!ProjectUtil.isUnderwriting(projectDO.getBusiType()))) {
						order.setIsDONE("IS");
						expireProjectService.updateToDone(order);
						ChangeProjectStatusOrder changeOrder = new ChangeProjectStatusOrder();
						changeOrder.setStatus(ProjectStatusEnum.FINISH);
						changeOrder.setPhaseStatus(ProjectPhasesStatusEnum.APPROVAL);
						changeOrder.setPhase(ProjectPhasesEnum.FINISH_PHASES);
						changeOrder.setProjectCode(projectDO.getProjectCode());
						ProjectResult presult = projectService.changeProjectStatus(changeOrder);
						if (!presult.isSuccess()) {
							logger.error("更新项目状态出错：" + presult.getMessage());
						}
					}
				}
			}
			
			logger.info("处理到期逾期任务，更新为逾期的项目数： " + overDueSize + "；更新为到期的项目数：" + expireSize);
		} catch (Exception e) {
			logger.error("处理到期逾期任务异常---异常原因：" + e.getMessage(), e);
		}
		
		//		logger.info("处理到期逾期任务结束在 " + DateUtil.simpleFormat(new Date()));
		//		
		//		try {
		//			logger.info("处理发债类到期项目任务开始在 " + DateUtil.simpleFormat(new Date()));
		//			Date now = new Date();
		//			Calendar c = Calendar.getInstance();
		//			c.setTime(now);
		//			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);
		//			c.set(Calendar.HOUR_OF_DAY, 23);
		//			c.set(Calendar.MINUTE, 59);
		//			c.set(Calendar.SECOND, 59);
		//			List<ProjectIssueInformationDO> list = extraDAO.queryReleasableProject(c.getTime());
		//			if (ListUtil.isNotEmpty(list)) {
		//				for (ProjectIssueInformationDO issue : list) {
		//					//发债类项目到期后才可以进行解保申请
		//					releaseProjectService.addBond(issue.getProjectCode());
		//					if (issue.getAmount().equals(issue.getActualAmount())) {
		//						continue;
		//					}
		//					ProjectDO project = projectDAO.findByProjectCodeForUpdate(issue
		//						.getProjectCode());
		//					if (null != project) {
		//						project.setReleasableAmount(issue.getActualAmount());
		//						projectDAO.update(project);
		//					}
		//				}
		//				logger.info("处理发债类到期项目任务，影响的项目数： " + list.size());
		//			}
		//			logger.info("处理发债类到期项目任务结束在 " + DateUtil.simpleFormat(new Date()));
		//		} catch (Exception e) {
		//			logger.error("处理发债类到期项目任务异常---异常原因：" + e.getMessage(), e);
		//		}
	}
	
	//发送消息
	public void sendMessage(ProjectDO projectDO, ExpireProjectInfo info, Date today) {
		//		boolean isGuarantee = ProjectUtil.isGuarantee(projectDO.getBusiType());//是否担保业务
		//		boolean isBond = ProjectUtil.isBond(projectDO.getBusiType());//发债业务
		//		boolean isEntrusted = ProjectUtil.isEntrusted(projectDO.getBusiType());//委贷业务
		//		//得到资金渠道
		//		String capitalChannelName = "";
		//		//代偿期限
		//		Date endTime = null;
		//		List<FCouncilSummaryRiskHandleInfo> handleInfos = councilSummaryService
		//			.queryRiskHandleCsByProjectCode(projectDO.getProjectCode());
		//		if (handleInfos != null && handleInfos.size() > 0) {
		//			endTime = handleInfos.get(0).getEndTime();
		//			for (FCouncilSummaryRiskHandleInfo handleInfo : handleInfos) {
		//				if (endTime.before(handleInfo.getEndTime())) {
		//					endTime = handleInfo.getEndTime();
		//				}
		//			}
		//		}
		//		if (isBond) {
		//			FCouncilSummaryProjectBondInfo bondInfo = councilSummaryService
		//				.queryBondProjectCsBySpId(projectDO.getSpId(), false);
		//			capitalChannelName = bondInfo.getCapitalChannelName();
		//		}
		//		if (isEntrusted) {
		//			FCouncilSummaryProjectEntrustedInfo entrustedInfo = councilSummaryService
		//				.queryEntrustedProjectCsBySpId(projectDO.getSpId(), false);
		//			capitalChannelName = entrustedInfo.getCapitalChannelName();
		//		}
		//		if (isGuarantee) {
		//			FCouncilSummaryProjectGuaranteeInfo guaranteeInfo = councilSummaryService
		//				.queryGuaranteeProjectCsBySpId(projectDO.getSpId(), false);
		//			capitalChannelName = guaranteeInfo.getCapitalChannelName();
		//		}
		//		int workDays = 0;//统计工作日
		//		Calendar now = Calendar.getInstance();
		//		now.setTime(today);
		//		while (now.getTime().before(endTime)) {//判断是否到结束日期
		//			if (DateUtil.isWorkDay(now.getTime())) {
		//				workDays++;
		//			}
		//			now.add(Calendar.MONTH, 1);
		//		}
		//发送消息
		
		MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
		String url = CommonUtil.getRedirectUrl("/projectMg/viewProjectAllMessage.htm")
						+ "&projectCode=" + info.getProjectCode();
		StringBuffer sb = new StringBuffer();
		sb.append("项目编号");
		sb.append(projectDO.getProjectCode());
		sb.append(",客户名称");
		sb.append(projectDO.getCustomerName());
		sb.append(",将于");
		sb.append(DateUtil.simpleDateFormatmdhChinese(info.getExpireDate()));
		sb.append("到期。点击<a href='" + url + "'>查看详情</a>");
		//		if (!"".equals(capitalChannelName)) {
		//			sb.append("项目的资金渠道为" + capitalChannelName);
		//			if (capitalChannelName.contains("银行")) {
		//				sb.append("代偿期限为" + workDays + "个工作日(自然日)");
		//			}
		//		}
		String content = sb.toString();
		messageOrder.setMessageContent(content);
		List<SimpleUserInfo> sendUserList = Lists.newArrayList();
		SimpleUserInfo user = new SimpleUserInfo();
		user.setUserAccount(projectDO.getBusiManagerAccount());
		user.setUserId(projectDO.getBusiManagerId());
		user.setUserName(projectDO.getBusiManagerName());
		sendUserList.add(user);
		messageOrder.setSendUsers((SimpleUserInfo[]) sendUserList
			.toArray(new SimpleUserInfo[sendUserList.size()]));
		siteMessageService.addMessageInfo(messageOrder);
	}
	
}
