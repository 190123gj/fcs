package com.born.fcs.pm.biz.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.dal.daointerface.ChargeRepayPlanDetailDAO;
import com.born.fcs.pm.dal.dataobject.ChargeRepayPlanDetailDO;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.PlanTypeEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 收费/还款计划通知
 * 
 * @author wuzj
 *
 */
@Service("chargeRepayPlanNotifyJob")
public class ChargeRepayPlanNotifyJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ChargeRepayPlanDetailDAO chargeRepayPlanDetailDAO;
	
	@Autowired
	private SiteMessageService siteMessageService;
	
	@Autowired
	private ProjectRelatedUserService projectRelatedUserService;
	
	@Autowired
	private ProjectService projectService;
	
	@Scheduled(cron = "0 0/5 * * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		logger.info("收费/还款计划通知任务开始在 {}", DateUtil.simpleFormat(new Date()));
		try {
			Calendar nc = Calendar.getInstance();
			nc.add(Calendar.DAY_OF_WEEK, 7);//提前一周提醒
			ChargeRepayPlanDetailDO chargeRepayPlanDetail = new ChargeRepayPlanDetailDO();
			chargeRepayPlanDetail.setEndTime(nc.getTime());
			chargeRepayPlanDetail.setIsNotify(BooleanEnum.NO.code());
			List<ChargeRepayPlanDetailDO> details = chargeRepayPlanDetailDAO.findByCondition(
				chargeRepayPlanDetail, "plan_detail_id", "asc", 0, 50);
			if (ListUtil.isNotEmpty(details)) {
				//按照项目分组一下下
				Map<String, List<ChargeRepayPlanDetailDO>> detailMap = Maps.newHashMap();
				for (ChargeRepayPlanDetailDO detail : details) {
					List<ChargeRepayPlanDetailDO> dList = detailMap.get(detail.getProjectCode());
					if (dList == null) {
						dList = Lists.newArrayList();
						dList.add(detail);
						detailMap.put(detail.getProjectCode(), dList);
					} else {
						dList.add(detail);
					}
				}
				
				for (String projectCode : detailMap.keySet()) {
					ProjectInfo project = projectService.queryByCode(projectCode, false);
					if (project != null) {
						ProjectRelatedUserInfo busiManager = projectRelatedUserService
							.getBusiManager(projectCode);
						if (busiManager != null) {
							MessageOrder order = MessageOrder.newSystemMessageOrder();
							for (ChargeRepayPlanDetailDO detail : detailMap.get(projectCode)) {
								boolean isChargePlan = PlanTypeEnum.CHARGE_PLAN.code().equals(
									detail.getPlanType());
								
								String url = CommonUtil
									.getRedirectUrl("/projectMg/chargeRepayPlan/view.htm")
												+ "&planId=" + detail.getPlanId();
								
								String messageContent = "项目编号：" + project.getProjectCode()
														+ "，客户名称：" + project.getCustomerName()
														+ "，该有" + (isChargePlan ? "收费" : "还款")
														+ "计划即将到期，<a href='" + url + "'>查看详情</>";
								
								order.setMessageSubject(isChargePlan ? "收费计划到期提醒" : "还款计划到期提醒");
								order.setMessageTitle(order.getMessageSubject());
								order.setMessageContent(messageContent);
								order.setSendUsers(new SimpleUserInfo[] { busiManager
									.toSimpleUserInfo() });
								siteMessageService.addMessageInfo(order);
								
								//更新已通知
								detail.setIsNotify(BooleanEnum.IS.code());
								chargeRepayPlanDetailDAO.update(detail);
							}
						}
						
					}
					
				}
			}
		} catch (Exception e) {
			logger.error("收费/还款计划通知任务异常---异常原因：{}", e);
		}
		
		logger.info("收费/还款计划通知任务结束在 {}", DateUtil.simpleFormat(new Date()));
	}
}
