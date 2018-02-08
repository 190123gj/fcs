package com.born.fcs.pm.biz.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.biz.service.common.MailService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.dal.daointerface.CouncilDAO;
import com.born.fcs.pm.dal.dataobject.CouncilDO;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CouncilStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.info.council.CouncilInfo;
import com.born.fcs.pm.ws.info.council.CouncilProjectVoteInfo;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SendMailOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.SysWebAccessTokenOrder;
import com.born.fcs.pm.ws.order.council.CouncilProjectVoteQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.SysWebAccessTokenService;
import com.born.fcs.pm.ws.service.council.CouncilProjectVoteService;
import com.born.fcs.pm.ws.service.council.CouncilService;
import com.born.fcs.pm.ws.service.sms.SMSService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 会议开始
 * 
 * @author fei
 * 
 * 2016-4-21 下午3:24:28
 */
@Service("councilBeginJob")
public class CouncilBeginJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CouncilDAO councilDAO;
	
	@Autowired
	private CouncilService councilService;
	
	@Autowired
	private BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	private SiteMessageService siteMessageService;
	
	@Autowired
	private SysWebAccessTokenService sysWebAccessTokenService;
	
	@Autowired
	private SysParameterService sysParameterService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private SMSService sMSService;
	
	@Autowired
	private CouncilProjectVoteService councilProjectVoteService;
	
	// 每天的8点19点每5分钟一次触发
	//	@Scheduled(cron = "1,31,51 * * * * ? ")
	//	@Scheduled(cron = "0 0/2 * * * ? ")
	@Scheduled(cron = "0 0/1 * * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		logger.info("处理会议相关任务开始在 " + DateUtil.simpleFormat(new Date()));
		try {
			int advancedMinute = 10;// 提前10分钟开始
			CouncilQueryOrder order = new CouncilQueryOrder();
			order.setStatus(CouncilStatusEnum.NOT_BEGIN.code());
			order.setPageSize(0);
			List<CouncilInfo> ls = councilService.queryCouncil(order).getPageList();
			// 获取系统时间
			Date sysDate = councilService.getSysDate();
			for (CouncilInfo info : ls) {
				// 因为这里取到的不是系统时间，所以取出系统时间来判定
				// Date startTime = info.getStartTime();
				// Calendar calendar = Calendar.getInstance();
				// calendar.setTime(startTime);
				// calendar.add(Calendar.MINUTE, -advancedMinute);
				//
				// DateUtil.isAfterNowDay(calendar.getTime());
				
				// 判定是否应该开始
				Date startTime = info.getStartTime();
				Calendar calendarNow = Calendar.getInstance();
				Calendar calendarStartTime = Calendar.getInstance();
				calendarNow.setTime(sysDate);
				calendarStartTime.setTime(startTime);
				// 20171108 实时开始 一分钟一次定时任务
				//calendarStartTime.add(Calendar.MINUTE, -advancedMinute);
				
				// 现在的时间 大于预计开始时间，代表时间还没到，继续等
				if (calendarNow.getTimeInMillis() < calendarStartTime.getTimeInMillis()) {
					continue;
				}
				FcsBaseResult result = councilService.beginCouncil(info.getCouncilId());
				if (result.isSuccess()) {
					logger.info("会议开始设置成功：" + info.getCouncilId() + " " + info.getCouncilCode());
					logger.info("startTime:" + calendarStartTime + "nowTime:" + calendarNow);
					
					/// 任务2.向评委发送站内信
					//					 抓去需要发送站内信[还未投票]的会议
					CouncilProjectVoteQueryOrder queryOrder = new CouncilProjectVoteQueryOrder();
					
					queryOrder.setCouncilId(info.getCouncilId());
					queryOrder.setVoteStatus(BooleanEnum.NO.code());
					queryOrder.setPageSize(1000L);
					QueryBaseBatchResult<CouncilProjectVoteInfo> voteResult = councilProjectVoteService
						.queryCouncilProjectVote(queryOrder);
					if (voteResult.isSuccess() && voteResult.isExecuted()) {
						// 剔除重复的评委id
						List<Long> usedJudgeId = new ArrayList<Long>();
						for (CouncilProjectVoteInfo voteInfo : voteResult.getPageList()) {
							// 若id存在，代表已经执行过，直接忽略
							
							if (usedJudgeId.contains(voteInfo.getJudgeId())) {
								continue;
							}
							Long userId = voteInfo.getJudgeId();
							judgeInCouncil(info, userId);
							usedJudgeId.add(userId);
						}
					} else {
						logger.error("查询评委投票失败，发送信息失败！");
					}
					
				}
			}
			
		} catch (Exception e) {
			logger.error("处理会议相关任务异常---异常原因：" + e.getMessage(), e);
		}
		
		logger.info("处理会议相关任务结束在 " + DateUtil.simpleFormat(new Date()));
	}
	
	private void judgeInCouncil(CouncilInfo info, Long userId) {
		
		// 站内信
		//				<a href="/projectMg/meetingMg/allCouncilProjectList.htm?councilId=9" 
		//						mainUrl="/projectMg/index.htm" 
		//						navUrl="/projectMg/meetingMg/councilList.htm" 
		//						class="fnNewWindowOpen">
		//				马上投票</a>
		// 邮件 
		//    http://127.0.0.1:8096/projectMg/index.htm#direct=/projectMg/meetingMg/allCouncilProjectList.htm
		//    ?councilId=9&sidebarUrl=/projectMg/meetingMg/councilList.htm
		Long councilId = info.getCouncilId();
		// 判断是否是总经理办公会
		CouncilDO councilDO = councilDAO.findById(councilId);
		boolean gmWorking = false;
		if (CouncilTypeEnum.GM_WORKING.code().equals(councilDO.getCouncilTypeCode())) {
			gmWorking = true;
		}
		String leftUrl = "/projectMg/meetingMg/councilList.htm";
		String messageUrl = "/projectMg/meetingMg/allCouncilProjectList.htm" + "?councilId="
							+ info.getCouncilId();
		if (gmWorking) {
			messageUrl = "/projectMg/meetingMg/showCouncil.htm" + "?councilId=" + councilId;
		}
		String mainUrl = "/projectMg/index.htm";
		String forMessage = "<a href='" + messageUrl + "'   mainUrl='" + mainUrl + "'   navUrl='"
							+ leftUrl + "'   class='fnNewWindowOpen' >马上投票</a>";
		if (gmWorking) {
			forMessage = "<a href='" + messageUrl + "'   mainUrl='" + mainUrl + "'   navUrl='"
							+ leftUrl + "'   class='fnNewWindowOpen' >查看详情</a>";
		}
		//表单站内地址
		//				String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/meetingMg/allCouncilProjectList.htm&councilId="
		//									+ order.getCouncilId();
		//				String forMessage = "<a href='" + messageUrl + "'>马上投票</a>";
		String subject = "上会提醒";
		List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
		SysUser sendUser = null;
		sendUser = bpmUserQueryService.findUserByUserId(userId);
		if (sendUser != null) {
			SimpleUserInfo userInfo = new SimpleUserInfo();
			userInfo.setUserId(sendUser.getUserId());
			userInfo.setUserName(sendUser.getFullname());
			userInfo.setUserAccount(sendUser.getAccount());
			userInfo.setEmail(sendUser.getEmail());
			userInfo.setMobile(sendUser.getMobile());
			notifyUserList.add(userInfo);
		}
		if (ListUtil.isNotEmpty(notifyUserList)) {
			
			SimpleUserInfo[] sendUsers = notifyUserList.toArray(new SimpleUserInfo[notifyUserList
				.size()]);
			//替换审核地址
			
			//尊敬的用户张三，您有会议已经开始，需要您投票，会议编号：（2015）第32期  会议时间：2016-3-16 14:20 ，快去投票吧！   马上投票（点击后进入待投票会议列表）
			StringBuilder sb = new StringBuilder();
			sb.append("尊敬的用户");
			sb.append(sendUsers[0].getUserName());
			sb.append("，您有会议已经开始，需要您投票，会议编号：");
			sb.append(info.getCouncilCode());
			sb.append("  会议时间：");
			sb.append(DateUtil.simpleFormat(info.getStartTime()));
			if (gmWorking) {
				sb.append(" ，快去查看详情吧！");
			} else {
				sb.append(" ，快去投票吧！   ");
			}
			String messageContent = sb.toString();
			messageContent += forMessage;
			if (StringUtil.isNotBlank(messageContent)) {
				MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
				messageOrder.setMessageSubject(subject);
				messageOrder.setMessageTitle("上会投票提醒");
				messageOrder.setMessageContent(messageContent);
				messageOrder.setSendUsers(sendUsers);
				siteMessageService.addMessageInfo(messageOrder);
			}
			
			for (SimpleUserInfo userInfo : notifyUserList) {
				
				//发送邮件
				
				String accessToken = getAccessToken(userInfo);
				// 邮件 
				//    http://127.0.0.1:8096/projectMg/index.htm#direct=/projectMg/meetingMg/allCouncilProjectList.htm
				//    ?councilId=9&sidebarUrl=/projectMg/meetingMg/councilList.htm
				String mailUrl = mainUrl + "#direct=" + messageUrl + "&sidebarUrl=" + leftUrl;
				//站外审核地址
				String outSiteUrl = "<a href=\"" + getFaceWebUrl() + mailUrl + "&accessToken="
									+ accessToken + "\" target=\"_blank\">点击处理</a>";
				
				String mailContent = sb.toString();
				mailContent += outSiteUrl;
				if (StringUtil.isNotBlank(mailContent)) {
					//邮件地址
					List<String> mailAddress = Lists.newArrayList(userInfo.getEmail());
					SendMailOrder mailOrder = new SendMailOrder();
					mailOrder.setSendTo(mailAddress);
					mailOrder.setSubject(subject);
					mailOrder.setContent(mailContent);
					mailService.sendHtmlEmail(mailOrder);
				}
				if (StringUtil.isNotEmpty(userInfo.getMobile())) {
					
					String contentTxt = sb.toString();
					//发送短信
					sMSService.sendSMS(userInfo.getMobile(), contentTxt, null);
				}
			}
		}
	}
	
	/**
	 * 获取站外访问密钥
	 * @param userInfo
	 * @return
	 */
	protected String getAccessToken(SimpleUserInfo userInfo) {
		SysWebAccessTokenOrder tokenOrder = new SysWebAccessTokenOrder();
		BeanCopier.staticCopy(userInfo, tokenOrder);
		tokenOrder.setRemark("站外访问链接");
		FcsBaseResult tokenResult = sysWebAccessTokenService.addUserAccessToken(tokenOrder);
		if (tokenResult != null && tokenResult.isSuccess()) {
			return tokenResult.getUrl();
		} else {
			return "";
		}
	}
	
	/**
	 * 获取web访问地址
	 * @return
	 */
	protected String getFaceWebUrl() {
		String faceUrl = sysParameterService
			.getSysParameterValue(SysParamEnum.SYS_PARAM_FACE_WEB_URL.code());
		if (faceUrl != null && faceUrl.endsWith("/")) {
			faceUrl = faceUrl.substring(0, faceUrl.length() - 1);
		}
		return faceUrl;
	}
}
