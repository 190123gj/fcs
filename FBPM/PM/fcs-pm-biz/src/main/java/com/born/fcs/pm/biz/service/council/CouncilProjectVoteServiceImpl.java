package com.born.fcs.pm.biz.service.council;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.biz.service.common.MailService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.dal.dataobject.CouncilDO;
import com.born.fcs.pm.dal.dataobject.CouncilProjectVoteDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.VoteResultEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.council.CouncilProjectVoteInfo;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SendMailOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.SysWebAccessTokenOrder;
import com.born.fcs.pm.ws.order.council.CouncilProjectVoteOrder;
import com.born.fcs.pm.ws.order.council.CouncilProjectVoteQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.SysWebAccessTokenService;
import com.born.fcs.pm.ws.service.council.CouncilProjectVoteService;
import com.born.fcs.pm.ws.service.sms.SMSService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("councilProjectVoteService")
public class CouncilProjectVoteServiceImpl extends BaseAutowiredDomainService implements
																				CouncilProjectVoteService {
	
	@Autowired
	protected SiteMessageService siteMessageService;
	//	
	//	@Autowired
	//	protected RelatedUserService relatedUserService;
	
	@Autowired
	private BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	protected SysWebAccessTokenService sysWebAccessTokenService;
	
	@Autowired
	protected MailService mailService;
	
	@Autowired
	protected SMSService sMSService;
	
	@Override
	public FcsBaseResult saveCouncilProjectVote(final CouncilProjectVoteOrder order) {
		
		return commonProcess(order, "保存初始投票记录", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				order.check();
				if (order.getId() <= 0) {
					// 保存
					CouncilProjectVoteDO counciVotelDO = new CouncilProjectVoteDO();
					convert2DO(order, counciVotelDO);
					long voteId = councilProjectVoteDAO.insert(counciVotelDO);
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(voteId);
				}
				return null;
			}
		}, null, null);
	}
	
	/**
	 * 催促还未投票的人
	 * 
	 * @param order
	 * @return
	 */
	@Override
	public FcsBaseResult urgeVote(CouncilProjectVoteOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		CouncilProjectVoteDO counciVotelDO = new CouncilProjectVoteDO();
		counciVotelDO.setCouncilId(order.getCouncilId());
		counciVotelDO.setCouncilCode(order.getCouncilCode());
		counciVotelDO.setProjectCode(order.getProjectCode());
		counciVotelDO.setJudgeId(order.getJudgeId());
		counciVotelDO.setVoteStatus(BooleanEnum.NO.code());
		counciVotelDO.setId(order.getId());
		
		List<CouncilProjectVoteDO> list = councilProjectVoteDAO
			.findByCondition(counciVotelDO, 0, 0);
		if (list != null) {
			for (CouncilProjectVoteDO item : list) {
				//  发送短信或站内信
				System.out.println("call send message" + item.getJudgeId());
				councilChangeForVoter(order, item.getJudgeId());
				
			}
		} else {
			result.setSuccess(false);
			result.setMessage("未找到需要通知的人");
		}
		result.setSuccess(true);
		return result;
		
	}
	
	private void councilChangeForVoter(final CouncilProjectVoteOrder order, Long userId) {
		List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
		// 抓取会议
		CouncilDO councilDO = councilDAO.findById(order.getCouncilId());
		//表单站内地址
		
		String leftUrl = "/projectMg/meetingMg/councilList.htm";
		String messageUrl = "/projectMg/meetingMg/allCouncilProjectList.htm" + "?councilId="
							+ councilDO.getCouncilId();
		String mainUrl = "/projectMg/index.htm";
		String forMessage = "<a href='" + messageUrl + "'   mainUrl='" + mainUrl + "'   navUrl='"
							+ leftUrl + "'   class='fnNewWindowOpen' >马上投票</a>";
		
		//		String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/meetingMg/allCouncilProjectList.htm&councilId="
		//							+ councilDO.getCouncilId();
		//		String forMessage = "<a href='" + messageUrl + "'>马上投票</a>";
		String subject = "投票提醒";
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
			
			//尊敬的用户罗佳，有会议等待您投票，会议编号：(2016)第2期    会议时间：2016-08-08 14:43:00 ，请尽快前去投票！   马上投票（点击以后进入该会议的待投票列表）
			
			StringBuilder sb = new StringBuilder();
			sb.append("尊敬的用户");
			sb.append(sendUsers[0].getUserName());
			sb.append("，有会议等待您投票，会议编号：");
			sb.append(councilDO.getCouncilCode());
			sb.append("  会议时间：");
			sb.append(DateUtil.simpleFormat(councilDO.getStartTime()));
			sb.append("，请尽快前去投票！  ");
			String messageContent = sb.toString();
			messageContent += forMessage;
			if (StringUtil.isNotBlank(messageContent)) {
				MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
				messageOrder.setMessageSubject(subject);
				messageOrder.setMessageTitle("催促投票提醒");
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
	
	@Override
	public FcsBaseResult updateCouncilProjectVote(final CouncilProjectVoteOrder order) {
		
		return commonProcess(order, "投票", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				order.check();
				
				doProjectVote(order);
				
				return null;
			}
			
		}, null, null);
	}
	
	@Override
	public FcsBaseResult destroyCouncilProjectVote(final CouncilProjectVoteOrder order) {
		
		return commonProcess(order, "删除投票记录", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				// 删除
				CouncilProjectVoteDO counciVotelDO = councilProjectVoteDAO.findById(order.getId());
				if (null == counciVotelDO) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未找到投票记录");
				}
				councilProjectVoteDAO.deleteById(order.getId());
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<CouncilProjectVoteInfo> queryCouncilProjectVote(CouncilProjectVoteQueryOrder councilProjectVoteQueryOrder) {
		QueryBaseBatchResult<CouncilProjectVoteInfo> batchResult = new QueryBaseBatchResult<CouncilProjectVoteInfo>();
		
		try {
			CouncilProjectVoteDO counciVotelDO = new CouncilProjectVoteDO();
			BeanCopier.staticCopy(councilProjectVoteQueryOrder, counciVotelDO);
			
			long totalCount = councilProjectVoteDAO.findByConditionCount(counciVotelDO);
			PageComponent component = new PageComponent(councilProjectVoteQueryOrder, totalCount);
			
			List<CouncilProjectVoteDO> list = councilProjectVoteDAO.findByCondition(counciVotelDO,
				component.getFirstRecord(), component.getPageSize());
			
			List<CouncilProjectVoteInfo> pageList = new ArrayList<CouncilProjectVoteInfo>(
				list.size());
			for (CouncilProjectVoteDO item : list) {
				CouncilProjectVoteInfo info = new CouncilProjectVoteInfo();
				BeanCopier.staticCopy(item, info);
				info.setVoteStatus(BooleanEnum.getByCode(item.getVoteStatus()));
				info.setVoteResult(VoteResultEnum.getByCode(item.getVoteResult()));
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询投票记录失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public List<CouncilProjectVoteInfo> queryCouncilProjectVoteByProjectCode(String projectCode) {
		CouncilProjectVoteQueryOrder order = new CouncilProjectVoteQueryOrder();
		order.setProjectCode(projectCode);
		return this.queryCouncilProjectVote(order).getPageList();
	}
	
	private void convert2DO(final CouncilProjectVoteOrder order, CouncilProjectVoteDO counciVotelDO) {
		BeanCopier.staticCopy(order, counciVotelDO);
		counciVotelDO.setVoteResult(order.getVoteResult());
		if (StringUtil.isNotEmpty(order.getVoteResult())) {
			counciVotelDO.setVoteResultDesc(VoteResultEnum.getByCode(order.getVoteResult())
				.message());
			
		}
		counciVotelDO.setVoteResultDesc(VoteResultEnum.getByCode(order.getVoteResult()).message());
		counciVotelDO.setVoteStatus(order.getVoteStatus().code());
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
}
