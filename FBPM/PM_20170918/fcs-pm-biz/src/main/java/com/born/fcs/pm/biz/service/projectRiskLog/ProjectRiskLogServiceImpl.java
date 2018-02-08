package com.born.fcs.pm.biz.service.projectRiskLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.dal.dataobject.ProjectRiskHandleTeamDO;
import com.born.fcs.pm.dal.dataobject.ProjectRiskLogDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.projectRiskLog.ProjectRiskLogInfo;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.projectRiskLog.ProjectRiskLogProcessOrder;
import com.born.fcs.pm.ws.order.projectRiskLog.ProjectRiskLogQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.projectRiskLog.ProjectRiskLogService;
import com.google.common.collect.Lists;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * Created by wqh on 2016/9/8.
 */
@Service("projectRiskLogService")
public class ProjectRiskLogServiceImpl extends BaseAutowiredDomainService implements
																			ProjectRiskLogService {
	@Autowired
	protected ProjectRelatedUserService projectRelatedUserService;
	
	@Autowired
	protected BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	protected SiteMessageService siteMessageService;
	
	@Override
	public FcsBaseResult deleteByLogId(long logId) {
		FcsBaseResult baseResult = new FcsBaseResult();
		try {
			projectRiskLogDAO.deleteById(logId);
			baseResult.setSuccess(true);
		} catch (IllegalArgumentException e) {
			baseResult.setSuccess(false);
			baseResult.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			baseResult.setMessage(e.getMessage());
		} catch (Exception e) {
			baseResult.setSuccess(false);
			baseResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		return baseResult;
	}
	
	@Override
	public ProjectRiskLogInfo findByLogId(long logId) {
		ProjectRiskLogDO logDO = projectRiskLogDAO.findById(logId);
		if (logDO != null) {
			ProjectRiskLogInfo info = new ProjectRiskLogInfo();
			BeanCopier.staticCopy(logDO, info);
			return info;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectRiskLogInfo> queryProjectRiskLogInfo(ProjectRiskLogQueryOrder riskLogQueryOrder) {
		QueryBaseBatchResult<ProjectRiskLogInfo> batchResult = new QueryBaseBatchResult<ProjectRiskLogInfo>();
		try {
			riskLogQueryOrder.check();
			List<ProjectRiskLogInfo> pageList = Lists.newArrayList();
			ProjectRiskLogDO riskLogDO = new ProjectRiskLogDO();
			BeanCopier.staticCopy(riskLogQueryOrder, riskLogDO);
			long totalCount = projectRiskLogDAO.findByConditionCount(riskLogDO,
				riskLogQueryOrder.getStartTimeBegin(), riskLogQueryOrder.getStartTimeEnd(),riskLogQueryOrder.getOccurTimeBegin(),riskLogQueryOrder.getOccurTimeEnd(),
				riskLogQueryOrder.getTitleDetail(),riskLogQueryOrder.getLoginUserId(),riskLogQueryOrder.getDeptIdList(),riskLogQueryOrder.getRelatedRoleList());
			PageComponent component = new PageComponent(riskLogQueryOrder, totalCount);
			List<ProjectRiskLogDO> recordList = projectRiskLogDAO.findByCondition(riskLogDO,
				component.getFirstRecord(), component.getPageSize(),
				riskLogQueryOrder.getStartTimeBegin(), riskLogQueryOrder.getStartTimeEnd(),riskLogQueryOrder.getOccurTimeBegin(),riskLogQueryOrder.getOccurTimeEnd(),
				riskLogQueryOrder.getTitleDetail(), riskLogQueryOrder.getSortCol(),
				riskLogQueryOrder.getSortOrder(),riskLogQueryOrder.getLoginUserId(),riskLogQueryOrder.getDeptIdList(),riskLogQueryOrder.getRelatedRoleList());
			for (ProjectRiskLogDO item : recordList) {
				ProjectRiskLogInfo info = new ProjectRiskLogInfo();
				BeanCopier.staticCopy(item, info);
				pageList.add(info);
			}
			batchResult.initPageParam(component);
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
		} catch (IllegalArgumentException e) {
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			batchResult.setMessage(e.getMessage());
		} catch (Exception e) {
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return batchResult;
	}
	
	@Override
	public FcsBaseResult save(final ProjectRiskLogProcessOrder processOrder) {
		return commonProcess(processOrder, "风险日志", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				ProjectRiskHandleTeamDO teamDO = new ProjectRiskHandleTeamDO();
				teamDO.setProjectCode(processOrder.getProjectCode());
				List<ProjectRiskHandleTeamDO> teamDOs = projectRiskHandleTeamDAO.findByCondition(
					teamDO, 0, 0, null, null,0,null,null, null, null);
				if (ListUtil.isEmpty(teamDOs)) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "没有成立风险小组");
				}
				
				ProjectRiskHandleTeamDO handleTeamDO = teamDOs.get(0);
				String[] memberIds = handleTeamDO.getMemberIds().split(",");
				boolean isExist = false;
				for (String memberId : memberIds) {
					if (StringUtil.equals(memberId, "" + processOrder.getUserId())) {
						isExist = true;
						break;
					}
				}

				if (!isExist) {
					if(processOrder.getUserId() == handleTeamDO.getChiefLeaderId()){
						isExist = true;
					}
				}

				if(!isExist){
					if(processOrder.getUserId() == handleTeamDO.getViceLeaderId()){
						isExist = true;
					}
				}

				
				if (!isExist) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS,
						"只有风险小组的成员，才可以新增日志");
				}
				
				ProjectRiskLogDO projectRiskLogDO = new ProjectRiskLogDO();
				BeanCopier.staticCopy(processOrder, projectRiskLogDO);
				projectRiskLogDO.setCreateManId(processOrder.getUserId());
				projectRiskLogDO.setCreateManName(processOrder.getUserName());
				if (processOrder.getLogId() != 0) {
					projectRiskLogDAO.update(projectRiskLogDO);
				} else {
					projectRiskLogDO.setRawAddTime(getSysdate());
					projectRiskLogDAO.insert(projectRiskLogDO);
				}
				FcsPmDomainHolder.get().addAttribute("projectRiskLogDO", projectRiskLogDO);
				FcsBaseResult result = (FcsBaseResult)FcsPmDomainHolder.get().getAttribute("result");
				result.setKeyId(projectRiskLogDO.getLogId());

				return null;
			}
		}, null, new AfterProcessInvokeService() {
			@Override
			public Domain after(Domain domain) {
				if (StringUtil.equals("YES", processOrder.getNeedAnnounce())
					&& !StringUtil.equals(processOrder.getLogStatus(), "DRAFT")) {
					ProjectRiskLogDO projectRiskLogDO = (ProjectRiskLogDO) FcsPmDomainHolder.get()
						.getAttribute("projectRiskLogDO");
					notice(projectRiskLogDO);
				}
				return null;
			}
		});
	}
	
	private void notice(ProjectRiskLogDO projectRiskLogDO) {
		//抓去项目名称
		String projectCode = projectRiskLogDO.getProjectCode();
		String subject = "风险管控新增管理日志";
		//尊敬的用户张三，您的项目 XXX项目名称XXX 已上会完成，投票通过，快去查看详情吧！
		// 发送给业务经理 A角
		
		List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
		List<SimpleUserInfo> busiManager = projectRelatedUserService
			.getDirector(sysParameterService
				.getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_DEPT_CODE.code()));
		notifyUserList.addAll(busiManager);
		
		List<SimpleUserInfo> busiManager2 = projectRelatedUserService
			.getDirector(sysParameterService
				.getSysParameterValue(SysParamEnum.SYS_PARAM_FW_DEPT_CODE.code()));
		notifyUserList.addAll(busiManager2);
		ProjectRiskHandleTeamDO teamDO = new ProjectRiskHandleTeamDO();
		teamDO.setProjectCode(projectCode);
		List<ProjectRiskHandleTeamDO> teamDOs = projectRiskHandleTeamDAO.findByCondition(teamDO, 0,
			0, null, null,0,null,null,null, null);
		String[] memberIds = teamDOs.get(0).getMemberIds().split(",");
		for (String memberId : memberIds) {
			SysUser sysUser = bpmUserQueryService.findUserByUserId(NumberUtil.parseLong(memberId));
			if (sysUser != null) {
				SimpleUserInfo sendUser = new SimpleUserInfo();
				sendUser.setUserId(sysUser.getUserId());
				sendUser.setUserAccount(sysUser.getAccount());
				sendUser.setUserName(sysUser.getFullname());
				sendUser.setEmail(sysUser.getEmail());
				sendUser.setMobile(sysUser.getMobile());
				notifyUserList.add(sendUser);
			}
		}
		
		SysUser chiefUser = bpmUserQueryService.findUserByUserId(teamDOs.get(0).getChiefLeaderId());
		if (chiefUser != null) {
			SimpleUserInfo sendUser = new SimpleUserInfo();
			sendUser.setUserId(chiefUser.getUserId());
			sendUser.setUserAccount(chiefUser.getAccount());
			sendUser.setUserName(chiefUser.getFullname());
			sendUser.setEmail(chiefUser.getEmail());
			sendUser.setMobile(chiefUser.getMobile());
			notifyUserList.add(sendUser);
		}
		
		SysUser viceUser = bpmUserQueryService.findUserByUserId(teamDOs.get(0).getViceLeaderId());
		if (viceUser != null) {
			SimpleUserInfo sendUser = new SimpleUserInfo();
			sendUser.setUserId(viceUser.getUserId());
			sendUser.setUserAccount(viceUser.getAccount());
			sendUser.setUserName(viceUser.getFullname());
			sendUser.setEmail(viceUser.getEmail());
			sendUser.setMobile(viceUser.getMobile());
			notifyUserList.add(sendUser);
		}
		
		if (ListUtil.isNotEmpty(notifyUserList)) {
			
			//表单站内地址
			String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/projectRiskLog/info.htm?logId="
								+ projectRiskLogDO.getLogId();
			String forMessage = "<a href='" + messageUrl + "'>查看详情</a>";
			Map<Long, SimpleUserInfo> mapUserInfo = new HashMap<Long, SimpleUserInfo>();
			for (SimpleUserInfo userInfo : notifyUserList) {
				if (mapUserInfo.get(userInfo.getUserId()) != null || userInfo.getUserId() == projectRiskLogDO.getCreateManId()) {
					continue;
				}
				mapUserInfo.put(userInfo.getUserId(), userInfo);
				SimpleUserInfo[] userInfos = new SimpleUserInfo[1];
				userInfos[0] = userInfo;
				//替换审核地址
				StringBuilder sb = new StringBuilder();
				sb.append("尊敬的用户");
				sb.append(userInfo.getUserName());
				sb.append("，").append(projectRiskLogDO.getCreateManName()).append("发布了新的公告，项目编号：");
				sb.append(projectCode).append("。   ");
				String messageContent = sb.toString();
				messageContent += forMessage;
				MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
				messageOrder.setMessageSubject(subject);
				messageOrder.setMessageTitle("风险管控新增管理日志");
				messageOrder.setMessageContent(messageContent);
				messageOrder.setSendUsers(userInfos);
				siteMessageService.addMessageInfo(messageOrder);
			}
		}
	}
}
