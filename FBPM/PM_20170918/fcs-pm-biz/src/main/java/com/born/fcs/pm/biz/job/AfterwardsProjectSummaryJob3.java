package com.born.fcs.pm.biz.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.MessageTypeEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.service.council.CouncilService;
import com.born.fcs.pm.ws.service.summary.AfterwardsProjectSummaryService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 保后项目汇总信息未录入消息推送3
 * 
 * @author Ji
 * 
 * 2016-6-4
 */
@Service("afterwardsProjectSummaryJob3")
public class AfterwardsProjectSummaryJob3 extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	SiteMessageService siteMessageService;//消息推送
	@Autowired
	AfterwardsProjectSummaryService afterwardsProjectSummaryService;
	@Autowired
	SysParameterService sysParameterService;
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	@Autowired
	private CouncilService councilService;
	
	// 每季度次月1号0点1分触发
	@Scheduled(cron = "0 1 0 1 2,5,8,11 ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		logger.info("保后项目汇总信息未录入消息推送开始在 " + DateUtil.simpleFormat(new Date()));
		try {
			// 获取系统时间
			Date sysDate = councilService.getSysDate();
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);//当前年
			int season = getSeason(sysDate);//当前季度
			String reportPeriod = "";
			if (season == 1) {
				year = year - 1; //用来计算期数
				season = 4;
				
			} else {
				season = season - 1;
			}
			
			reportPeriod = year + "年第" + season + "期"; //根据所属报告期和部门 查询该部门是否已填报告期
			
			String sysParamValueZJ = sysParameterService
				.getSysParameterValue(SysParamEnum.SYS_PARAM_DIRECTOR_POSITION.code()); //业务总监 参数
			
			String sysParamValueDepts = sysParameterService
				.getSysParameterValue(SysParamEnum.SYS_PARAM_BUSINESS_DEPARTMENT.code()); //业务部所有部门 参数
			String[] depts = sysParamValueDepts.split(",");
			
			for (String string : depts) {
				
				String jobCodeZJ = string.toLowerCase() + "_" + sysParamValueZJ; //当前业务部门的总监
				
				List<SysUser> sysUserListZJ = bpmUserQueryService.findUserByJobCode(jobCodeZJ);
				if (sysUserListZJ == null || sysUserListZJ.size() == 0) {
					logger.error("该部门总监未配置或不存在");
				}
				if (ListUtil.isNotEmpty(sysUserListZJ)) {
					
					MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
					String url = CommonUtil
						.getRedirectUrl("/projectMg/afterwardsSummary/summaryList.htm");
					StringBuffer sb = new StringBuffer();
					sb.append(reportPeriod);
					sb.append("授信后检查汇总报告没有填写，请尽快填写！");
					sb.append("<a href='" + url + "'>马上填写</a>");
					sb.append("。");
					String content = sb.toString();
					messageOrder.setMessageContent(content);
					messageOrder.setMessageType(MessageTypeEnum.SYSTEM);
					List<SimpleUserInfo> sendUserList = Lists.newArrayList();
					for (SysUser sysUser1 : sysUserListZJ) {
						SimpleUserInfo user = new SimpleUserInfo();
						user.setUserAccount(sysUser1.getAccount());
						user.setUserId(sysUser1.getUserId());
						user.setUserName(sysUser1.getFullname());
						sendUserList.add(user);
					}
					messageOrder.setSendUsers(sendUserList.toArray(new SimpleUserInfo[sendUserList
						.size()]));
					siteMessageService.addMessageInfo(messageOrder);
					
				}
				
			}
			
		} catch (Exception e) {
			logger.error("保后项目汇总信息未录入消息推送异常---异常原因：" + e.getMessage(), e);
		}
		
		logger.info("保后项目汇总信息未录入消息推送结束在 " + DateUtil.simpleFormat(new Date()));
	}
	
	/**
	 * 
	 * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
	 * 
	 * @param date
	 * @return
	 */
	public static int getSeason(Date date) {
		
		int season = 0;
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		switch (month) {
			case Calendar.JANUARY:
			case Calendar.FEBRUARY:
			case Calendar.MARCH:
				season = 1;
				break;
			case Calendar.APRIL:
			case Calendar.MAY:
			case Calendar.JUNE:
				season = 2;
				break;
			case Calendar.JULY:
			case Calendar.AUGUST:
			case Calendar.SEPTEMBER:
				season = 3;
				break;
			case Calendar.OCTOBER:
			case Calendar.NOVEMBER:
			case Calendar.DECEMBER:
				season = 4;
				break;
			default:
				break;
		}
		return season;
	}
}
