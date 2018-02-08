package com.born.fcs.pm.biz.service.riskHandleTeam;

import java.util.ArrayList;
import java.util.List;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.ProjectRiskHandleTeamDO;
import com.born.fcs.pm.ws.info.riskHandleTeam.RiskHandleTeamInfo;
import com.born.fcs.pm.ws.order.riskHandleTeam.RiskHandleTeamProcessOrder;
import com.born.fcs.pm.ws.order.riskHandleTeam.RiskHandlerTeamQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.riskHandleTeam.RiskHandleTeamService;
import com.google.common.collect.Lists;

/**
 * Created by wqh on 2016/9/8.
 */
@Service("riskHandleTeamService")
public class RiskHandleTeamServiceImpl extends BaseAutowiredDomainService implements
																			RiskHandleTeamService {

	@Autowired
	ProjectRelatedUserService projectRelatedUserService;


	@Autowired
	BpmUserQueryService bpmUserQueryService;

	@Autowired
	SiteMessageService siteMessageService;

	@Override
	public RiskHandleTeamInfo findByTeamId(long teamId) {
		ProjectRiskHandleTeamDO teamDO = projectRiskHandleTeamDAO.findById(teamId);
		if (teamDO != null) {
			RiskHandleTeamInfo info = new RiskHandleTeamInfo();
			BeanCopier.staticCopy(teamDO, info);
			return info;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<RiskHandleTeamInfo> queryRiskHandleTeam(RiskHandlerTeamQueryOrder riskHandlerTeamQueryOrder) {
		QueryBaseBatchResult<RiskHandleTeamInfo> batchResult = new QueryBaseBatchResult<RiskHandleTeamInfo>();
		try {
			riskHandlerTeamQueryOrder.check();
			List<RiskHandleTeamInfo> pageList = Lists.newArrayList();
			ProjectRiskHandleTeamDO projectRiskHandleTeamDO = new ProjectRiskHandleTeamDO();
			BeanCopier.staticCopy(riskHandlerTeamQueryOrder,projectRiskHandleTeamDO);
			long totalCount = projectRiskHandleTeamDAO.findByConditionCount(
				projectRiskHandleTeamDO, riskHandlerTeamQueryOrder.getStartTimeBegin(),
				riskHandlerTeamQueryOrder.getStartTimeEnd(),riskHandlerTeamQueryOrder.getLoginUserId(),riskHandlerTeamQueryOrder.getDeptIdList(),riskHandlerTeamQueryOrder.getRelatedRoleList());
			PageComponent component = new PageComponent(riskHandlerTeamQueryOrder, totalCount);
			List<ProjectRiskHandleTeamDO> recordList = projectRiskHandleTeamDAO.findByCondition(
				projectRiskHandleTeamDO, component.getFirstRecord(), component.getPageSize(),
				riskHandlerTeamQueryOrder.getStartTimeBegin(),
				riskHandlerTeamQueryOrder.getStartTimeEnd(),riskHandlerTeamQueryOrder.getLoginUserId(),riskHandlerTeamQueryOrder.getDeptIdList(),riskHandlerTeamQueryOrder.getRelatedRoleList(),riskHandlerTeamQueryOrder.getSortCol(),riskHandlerTeamQueryOrder.getSortOrder());
			for (ProjectRiskHandleTeamDO item : recordList) {
				RiskHandleTeamInfo info = new RiskHandleTeamInfo();
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
	public FcsBaseResult save(final RiskHandleTeamProcessOrder processOrder) {
		return commonProcess(processOrder, "风险处置小组列表", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				RiskHandlerTeamQueryOrder teamQueryOrder = new RiskHandlerTeamQueryOrder();
				teamQueryOrder.setProjectCode(processOrder.getProjectCode());
				List<RiskHandleTeamInfo> teamInfos = queryRiskHandleTeam(teamQueryOrder)
						.getPageList();
				logger.info("该项目已经过成立风险处置小组teamInfos={}", teamInfos);
				if (ListUtil.isNotEmpty(teamInfos)) {
					for (RiskHandleTeamInfo teamInfo : teamInfos) {
						if (processOrder.getTeamId() != teamInfo.getTeamId()) {
							throw ExceptionFactory.newFcsException(
									FcsResultEnum.DO_ACTION_STATUS_ERROR, "该项目已经过成立风险处置小组");
						}
					}
				}
				ProjectRiskHandleTeamDO projectRiskHandleTeamDO = new ProjectRiskHandleTeamDO();
				BeanCopier.staticCopy(processOrder, projectRiskHandleTeamDO);
				projectRiskHandleTeamDO.setCreateManId(processOrder.getUserId());
				projectRiskHandleTeamDO.setCreateManName(processOrder.getUserName());
				if (processOrder.getTeamId() != 0) {
					projectRiskHandleTeamDAO.update(projectRiskHandleTeamDO);
				} else {
					projectRiskHandleTeamDO.setCreateTime(getSysdate());
					projectRiskHandleTeamDO.setRawAddTime(getSysdate());
					projectRiskHandleTeamDAO.insert(projectRiskHandleTeamDO);
				}
				List<Long> memberIds = new ArrayList<Long>();
				memberIds.add(projectRiskHandleTeamDO.getViceLeaderId());
				memberIds.add(projectRiskHandleTeamDO.getChiefLeaderId());
				String[] mIds = projectRiskHandleTeamDO.getMemberIds().split(",");
				for (String mid : mIds) {
					memberIds.add(NumberUtil.parseLong(mid));
				}
				projectRelatedUserService.setRiskTeamMember(projectRiskHandleTeamDO.getProjectCode(), memberIds);
				FcsPmDomainHolder.get().addAttribute("projectRiskHandleTeamDO", projectRiskHandleTeamDO);
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			@Override
			public Domain after(Domain domain) {

				//抓去项目名称
				String projectCode = processOrder.getProjectCode();
				String subject = " 成立风险处置小组";
				//尊敬的用户张三，您的项目 XXX项目名称XXX 已上会完成，投票通过，快去查看详情吧！
				// 发送给业务经理 A角
				ProjectRiskHandleTeamDO projectRiskHandleTeamDO = (ProjectRiskHandleTeamDO)FcsPmDomainHolder.get().getAttribute("projectRiskHandleTeamDO");
				List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
			;
				ProjectRiskHandleTeamDO teamDO = new ProjectRiskHandleTeamDO();
				teamDO.setProjectCode(projectCode);
				List<ProjectRiskHandleTeamDO> teamDOs = projectRiskHandleTeamDAO.findByCondition(teamDO, 0,
						0, null, null, 0,null,null,null,null);
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

				StringBuilder summaryIds = new StringBuilder();
				boolean isFirst = true;
				if(ListUtil.isNotEmpty(notifyUserList)){
					for(SimpleUserInfo sendUser :notifyUserList){
						if(!isFirst){
							summaryIds.append(",");
						}
						summaryIds.append(sendUser.getUserName());
						isFirst = false;
					}
				}


				if (ListUtil.isNotEmpty(notifyUserList)) {
					//"尊敬的用户张三，项目：XXX项目编号XXXX  风险小组已成立，成员有:张三，李四……快去查看详情吧。     现在查看
					//表单站内地址
					String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/riskHandleTeam/info.htm?teamId="
							+ projectRiskHandleTeamDO.getTeamId() ;
					String forMessage = "<a href='" + messageUrl + "'>查看详情</a>";

					for (SimpleUserInfo userInfo : notifyUserList) {
						SimpleUserInfo[] userInfos = new SimpleUserInfo[1];
						userInfos[0] = userInfo;
						StringBuilder sb = new StringBuilder();
						sb.append("尊敬的用户");
						sb.append(userInfo.getUserName());
						sb.append("，项目：").append(processOrder.getProjectName()).append("  项目编号").append(processOrder.getProjectCode()).append(" 风险小组已成立")
						.append("，成员有：").append(summaryIds).append("快去查看详情吧,现在去看");
						String messageContent = sb.toString();
						messageContent += forMessage;
						MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
						messageOrder.setMessageSubject(subject);
						messageOrder.setMessageTitle("成立风险处置小组");
						messageOrder.setMessageContent(messageContent);
						messageOrder.setSendUsers(userInfos);
						siteMessageService.addMessageInfo(messageOrder);
					}
				}
				return null;
			}
		});
	}
}
