package com.born.fcs.pm.biz.service.council;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.biz.service.common.MailService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.dal.dataobject.CouncilApplyDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.council.CouncilApplyInfo;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SendMailOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.SysWebAccessTokenOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.council.CouncilApplyResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.SysWebAccessTokenService;
import com.born.fcs.pm.ws.service.council.CouncilApplyService;
import com.born.fcs.pm.ws.service.sms.SMSService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("councilApplyService")
public class CouncilApplyServiceImpl extends BaseFormAutowiredDomainService implements
																			CouncilApplyService {
	
	@Autowired
	private ProjectRelatedUserService projectRelatedUserService;
	
	@Autowired
	protected SiteMessageService siteMessageService;
	
	@Autowired
	private BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	protected SysWebAccessTokenService sysWebAccessTokenService;
	
	@Autowired
	protected MailService mailService;
	
	@Autowired
	protected SMSService sMSService;
	
	@Override
	public FcsBaseResult saveCouncilApply(final CouncilApplyOrder order) {
		
		return commonProcess(order, "项目上会申请", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				if (order.getApplyId() <= 0) {
					
					//保存
					CouncilApplyDO councilApplyDO = new CouncilApplyDO();
					BeanCopier.staticCopy(order, councilApplyDO);
					if (order.getChildId() != null) {
						councilApplyDO.setChildId(order.getChildId());
					}
					
					/// 公司名【目前用户信汇】  /-- 兼容非信汇角色添加信汇数据或系统添加的情况
					if (order.getCompanyName() != null) {
						councilApplyDO.setCompanyName(order.getCompanyName().code());
					}
					/// 是否上母公司会 YES/NO 
					if (order.getMotherCompanyApply() != null) {
						councilApplyDO.setMotherCompanyApply(order.getMotherCompanyApply().code());
						// 若上母公司会且没有会议类型，报凑
						if (order.getMotherCompanyApply() == BooleanEnum.YES
							&& StringUtil.isBlank(councilApplyDO.getMotherCouncilCode())) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"选择上母公司会时候未填写母公司会议类型！");
						}
					}
					// 若判定用户id是信汇
					Long userId = councilApplyDO.getApplyManId();
					if (projectRelatedUserService.isBelong2Dept(userId, sysParameterService
						.getSysParameterValue(SysParamEnum.SYS_PARAM_XINHUI_DEPT_CODE.code()))) {
						councilApplyDO.setCompanyName(CompanyNameEnum.XINHUI.code());
						
					}
					// 新增判断，用于兼容信汇上完子会之后再上母会需要类型为母公司
					if (order.isHasCompanyName()) {
						councilApplyDO.setCompanyName(order.getCompanyName().code());
					}
					
					councilApplyDO.setRawAddTime(now);
					long applyId = councilApplyDAO.insert(councilApplyDO);
					order.setApplyId(applyId);
					FcsBaseResult result = (FcsBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(applyId);
				} else {
					//更新
					CouncilApplyDO councilApplyDO = councilApplyDAO.findById(order.getApplyId());
					if (null == councilApplyDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到项目上会申请记录");
					}
					BeanCopier.staticCopy(order, councilApplyDO);
					
					/// 公司名【目前用户信汇】  /-- 兼容非信汇角色添加信汇数据或系统添加的情况
					if (order.getCompanyName() != null) {
						councilApplyDO.setCompanyName(order.getCompanyName().code());
					}
					/// 是否上母公司会 YES/NO 
					if (order.getMotherCompanyApply() != null) {
						councilApplyDO.setMotherCompanyApply(order.getMotherCompanyApply().code());
					}
					
					councilApplyDAO.update(councilApplyDO);
					FcsBaseResult result = (FcsBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(order.getApplyId());
				}
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				
				// 添加任务，上会后向秘书岗发送信息
				// 尊敬的用户张三， 项目 XXX项目名称XXX 已被安排进入待上会列表，快去查看[列表]吧！
				// 尊敬的用户张三，有新项目等待上会，项目名称： XXX项目名称XXX ，快去安排会议吧！
				List<SysUser> users = new ArrayList<SysUser>();
				// 判断是 向风控委秘书还是总经理秘书还是信汇秘书发信息
				CouncilApplyDO DO = councilApplyDAO.findById(order.getApplyId());
				if (CouncilTypeEnum.GM_WORKING.code().equals(DO.getCouncilCode())) {
					if (CompanyNameEnum.XINHUI.code().equals(DO.getCompanyName())) {
						// 代表信汇秘书
						String roleName = sysParameterService
							.getSysParameterValue(SysParamEnum.SYS_PARAM_MANAGER_SECRETARY_XH_ROLE_NAME
								.code());
						
						users = bpmUserQueryService.findUserByRoleAlias(roleName);
					} else {
						// 代表总经理秘书
						String roleName = sysParameterService
							.getSysParameterValue(SysParamEnum.SYS_PARAM_MANAGER_SECRETARY_ROLE_NAME
								.code());
						
						users = bpmUserQueryService.findUserByRoleAlias(roleName);
					}
				} else {
					// 代表风控委秘书
					// 获取风控委秘书
					String roleName = sysParameterService
						.getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_SECRETARY_ROLE_NAME
							.code());
					
					users = bpmUserQueryService.findUserByRoleAlias(roleName);
				}
				
				for (SysUser user : users) {
					councilApplyNotice(DO, user);
				}
				
				return null;
			}
			
			private void councilApplyNotice(CouncilApplyDO applyDO, SysUser sendUser) {
				//表单站内地址
				String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/meetingMg/awaitCouncilProjectList.htm";
				String forMessage = "<a href='" + messageUrl + "'>查看待上会列表</a>";
				String subject = "进入待上会提醒";
				List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
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
					
					SimpleUserInfo[] sendUsers = notifyUserList
						.toArray(new SimpleUserInfo[notifyUserList.size()]);
					//替换审核地址
					StringBuilder sb = new StringBuilder();
					sb.append("尊敬的用户 ");
					sb.append(sendUsers[0].getUserName());
					sb.append("，有新项目等待上会，项目名称：  ");
					sb.append(applyDO.getProjectName());
					sb.append(" ，快去安排会议吧！");
					String messageContent = sb.toString();
					messageContent += forMessage;
					if (StringUtil.isNotBlank(messageContent)) {
						MessageOrder ormessageOrderder = MessageOrder.newSystemMessageOrder();
						ormessageOrderder.setMessageSubject(subject);
						ormessageOrderder.setMessageTitle("项目进入待上会提醒");
						ormessageOrderder.setMessageContent(messageContent);
						ormessageOrderder.setSendUsers(sendUsers);
						siteMessageService.addMessageInfo(ormessageOrderder);
					}
					
					for (SimpleUserInfo userInfo : notifyUserList) {
						
						//发送邮件
						
						String accessToken = getAccessToken(userInfo);
						
						//站外审核地址
						String outSiteUrl = "<a href=\"" + getFaceWebUrl() + messageUrl
											+ "&accessToken=" + accessToken
											+ "\" target=\"_blank\">点击处理</a>";
						
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
			
		});
		
	}
	
	@Override
	public List<CouncilApplyInfo> queryCouncilApplyByProjectCode(String projectCode) {
		List<CouncilApplyInfo> infos = new ArrayList<CouncilApplyInfo>();
		List<CouncilApplyDO> infoDOs = councilApplyDAO.findByProjectCode(projectCode);
		for (CouncilApplyDO councilApplyDO : infoDOs) {
			
			CouncilApplyInfo info = new CouncilApplyInfo();
			councilApplyDO2Info(councilApplyDO, info);
			infos.add(info);
		}
		return infos;
	}
	
	@Override
	public CouncilApplyInfo queryCouncilApplyByApplyId(long applyId) {
		CouncilApplyDO councilApplyDO = councilApplyDAO.findById(applyId);
		CouncilApplyInfo info = new CouncilApplyInfo();
		councilApplyDO2Info(councilApplyDO, info);
		return info;
	}
	
	@Override
	public QueryBaseBatchResult<CouncilApplyInfo> queryCouncilApply(CouncilApplyQueryOrder councilApplyQueryOrder) {
		QueryBaseBatchResult<CouncilApplyInfo> batchResult = new QueryBaseBatchResult<CouncilApplyInfo>();
		try {
			CouncilApplyDO councilApplyDO = new CouncilApplyDO();
			BeanCopier.staticCopy(councilApplyQueryOrder, councilApplyDO);
			if (councilApplyQueryOrder.getStatus() != null) {
				councilApplyDO.setStatus(councilApplyQueryOrder.getStatus().code());
			}
			
			long totalCount = councilApplyDAO.findByConditionCount(councilApplyDO,
				councilApplyQueryOrder.getCouncilTypeCodes(),
				councilApplyQueryOrder.getCompanyNames());
			PageComponent component = new PageComponent(councilApplyQueryOrder, totalCount);
			
			List<CouncilApplyDO> list = councilApplyDAO.findByCondition(councilApplyDO,
				component.getFirstRecord(), component.getPageSize(),
				councilApplyQueryOrder.getCouncilTypeCodes(),
				councilApplyQueryOrder.getCompanyNames());
			
			List<CouncilApplyInfo> pageList = new ArrayList<CouncilApplyInfo>(list.size());
			for (CouncilApplyDO messageInfoDO : list) {
				CouncilApplyInfo info = new CouncilApplyInfo();
				councilApplyDO2Info(messageInfoDO, info);
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询待上会项目列表失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	private void councilApplyDO2Info(CouncilApplyDO councilApplyDO, CouncilApplyInfo info) {
		BeanCopier.staticCopy(councilApplyDO, info);
		info.setTimeUnit(TimeUnitEnum.getByCode(councilApplyDO.getTimeUnit()));
		info.setMotherCompanyApply(BooleanEnum.getByCode(councilApplyDO.getCompanyName()));
		info.setCompanyName(CompanyNameEnum.getByCode(councilApplyDO.getCompanyName()));
	}
	
	@Override
	public String getAvailableCouncilCodeSeq(CompanyNameEnum company, CouncilTypeEnum councilType) {
		return dateSeqService.getAvailableCouncilCodeSeqByParam(company, councilType);
	}
	
	@Override
	protected FormBaseResult createResult() {
		return new CouncilApplyResult();
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
