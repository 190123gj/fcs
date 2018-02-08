package com.born.fcs.pm.biz.service.councilRisk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.born.fcs.pm.dal.dataobject.ProjectRiskReportDO;
import com.born.fcs.pm.ws.order.councilRisk.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.dal.dataobject.CouncilRiskDO;
import com.born.fcs.pm.dal.dataobject.CouncilRiskSummaryDO;
import com.born.fcs.pm.dal.dataobject.ProjectRiskHandleTeamDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.CouncilRiskTypeEnum;
import com.born.fcs.pm.ws.enums.CouncilStatusEnum;
import com.born.fcs.pm.ws.enums.SysDateSeqNameEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.councilRisk.CouncilRiskInfo;
import com.born.fcs.pm.ws.info.councilRisk.CouncilRiskSummaryInfo;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.councilRisk.CouncilRiskService;
import com.google.common.collect.Lists;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * Created by wqh on 2016/9/9.
 */
@Service("councilRiskService")
public class CouncilRiskServiceImpl extends BaseAutowiredDomainService implements
																		CouncilRiskService {
	
	@Autowired
	ProjectRelatedUserService projectRelatedUserService;
	
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	SiteMessageService siteMessageService;
	
	@Override
	public CouncilRiskInfo findBySummaryQueryOrder(CouncilRiskSummaryQueryOrder queryOrder) {
		CouncilRiskDO councilRiskDO = councilRiskDAO.findById(queryOrder.getCouncilId());
		if (councilRiskDO != null) {
			CouncilRiskInfo councilRiskInfo = doToInfo(councilRiskDO);
			CouncilRiskSummaryDO councilRiskSummaryDO = new CouncilRiskSummaryDO();
			councilRiskSummaryDO.setCouncilId(councilRiskDO.getCouncilId());
			List<CouncilRiskSummaryDO> summaryDOs = councilRiskSummaryDAO
				.findByCondition(councilRiskSummaryDO);
			List<CouncilRiskSummaryInfo> summaryInfos = null;
			if (ListUtil.isNotEmpty(summaryDOs)) {
				councilRiskInfo.setIsSummary("Y");
				summaryInfos = new ArrayList<CouncilRiskSummaryInfo>();
				String isConfirm = null;
				for (CouncilRiskSummaryDO summaryDO : summaryDOs) {
					if (ListUtil.isNotEmpty(queryOrder.getSummaryIds())) {
						if (!queryOrder.getSummaryIds().contains(summaryDO.getSummaryId() + "")) {
							continue;
						}
					}else{
						if(!StringUtil.equalsIgnoreCase("YES",summaryDO.getIsConfirm())){
							continue;
						}
					}
					CouncilRiskSummaryInfo summaryInfo = new CouncilRiskSummaryInfo();
					BeanCopier.staticCopy(summaryDO, summaryInfo);
					summaryInfos.add(summaryInfo);
						if (StringUtil.isEmpty(isConfirm)) {
							if (StringUtil.equalsIgnoreCase("YES",summaryDO.getNeedConfirm()) && (StringUtil.isEmpty(summaryDO.getIsConfirm())
									|| !StringUtil.equals(summaryDO.getIsConfirm(), "YES"))) {
								isConfirm = "NO";
							}
						}
						if (!StringUtil.equalsIgnoreCase(councilRiskInfo.getIsNeedConfirm(), "YES")) {
							councilRiskInfo.setIsNeedConfirm(summaryDO.getNeedConfirm());
						}


				}
				councilRiskInfo.setIsConfirm(StringUtil.defaultIfEmpty(isConfirm, "YES"));
			}

			councilRiskInfo.setSummaryInfoList(summaryInfos);
			return councilRiskInfo;
		}
		return null;
	}
	
	@Override
	public CouncilRiskInfo findByCouncilId(long councilId) {
		CouncilRiskDO councilRiskDO = councilRiskDAO.findById(councilId);
		if (councilRiskDO != null) {
			CouncilRiskInfo councilRiskInfo = doToInfo(councilRiskDO);
			CouncilRiskSummaryDO councilRiskSummaryDO = new CouncilRiskSummaryDO();
			councilRiskSummaryDO.setCouncilId(councilRiskDO.getCouncilId());
			List<CouncilRiskSummaryDO> summaryDOs = councilRiskSummaryDAO
				.findByCondition(councilRiskSummaryDO);
			List<CouncilRiskSummaryInfo> summaryInfos = null;
			if (ListUtil.isNotEmpty(summaryDOs)) {
				councilRiskInfo.setIsSummary("Y");
				summaryInfos = new ArrayList<CouncilRiskSummaryInfo>();
				String isConfirm = null;
				for (CouncilRiskSummaryDO summaryDO : summaryDOs) {
					CouncilRiskSummaryInfo summaryInfo = new CouncilRiskSummaryInfo();
					BeanCopier.staticCopy(summaryDO, summaryInfo);
					summaryInfos.add(summaryInfo);
					councilRiskInfo.setIsConfirm(summaryInfo.getIsConfirm());
					if (StringUtil.isEmpty(isConfirm)) {
						if (StringUtil.equalsIgnoreCase("YES",summaryDO.getNeedConfirm()) && (StringUtil.isEmpty(summaryDO.getIsConfirm())
								|| !StringUtil.equals(summaryDO.getIsConfirm(), "YES"))) {
							isConfirm = "NO";
						}
					}
					if (!StringUtil.equalsIgnoreCase(councilRiskInfo.getIsNeedConfirm(), "YES")) {
						councilRiskInfo.setIsNeedConfirm(summaryDO.getNeedConfirm());
					}
				}
				councilRiskInfo.setIsConfirm(StringUtil.defaultIfEmpty(isConfirm, "YES"));
			}

			councilRiskInfo.setSummaryInfoList(summaryInfos);
			return councilRiskInfo;
		}
		return null;
	}
	
	@Override
	public CouncilRiskInfo findByCouncilIdAndUserId(long councilId, long userId) {
		CouncilRiskDO councilRiskDO = councilRiskDAO.findById(councilId);
		if (councilRiskDO != null) {
			CouncilRiskInfo councilRiskInfo = doToInfo(councilRiskDO);
			CouncilRiskSummaryDO councilRiskSummaryDO = new CouncilRiskSummaryDO();
			councilRiskSummaryDO.setCouncilId(councilRiskDO.getCouncilId());
			List<CouncilRiskSummaryDO> summaryDOs = councilRiskSummaryDAO
				.findByCondition(councilRiskSummaryDO);
			List<CouncilRiskSummaryInfo> summaryInfos = null;
			if (ListUtil.isNotEmpty(summaryDOs)) {
				councilRiskInfo.setIsSummary("Y");
				summaryInfos = new ArrayList<CouncilRiskSummaryInfo>();
				for (CouncilRiskSummaryDO summaryDO : summaryDOs) {
					String confirmManIds = summaryDO.getConfirmManIds();
					if (StringUtil.isNotEmpty(confirmManIds)) {
						if (confirmManIds.indexOf("" + userId) < 0) {
							continue;
						}
					}
					CouncilRiskSummaryInfo summaryInfo = new CouncilRiskSummaryInfo();
					BeanCopier.staticCopy(summaryDO, summaryInfo);
					summaryInfos.add(summaryInfo);
					councilRiskInfo.setIsConfirm(summaryInfo.getIsConfirm());
				}
			}
			councilRiskInfo.setSummaryInfoList(summaryInfos);
			return councilRiskInfo;
		}
		return null;
	}
	
	private CouncilRiskInfo doToInfo(CouncilRiskDO councilRiskDO) {
		CouncilRiskInfo councilRiskInfo = new CouncilRiskInfo();
		BeanCopier.staticCopy(councilRiskDO, councilRiskInfo);
		councilRiskInfo.setCouncilStatus(CouncilStatusEnum.getByCode(councilRiskDO
			.getCouncilStatus()));
		councilRiskInfo
			.setCouncilType(CouncilRiskTypeEnum.getByCode(councilRiskDO.getCouncilType()));
		return councilRiskInfo;
	}
	
	@Override
	public QueryBaseBatchResult<CouncilRiskInfo> queryCouncilRiskInfo(	CouncilRiskQueryOrder riskQueryOrder) {
		QueryBaseBatchResult<CouncilRiskInfo> batchResult = new QueryBaseBatchResult<CouncilRiskInfo>();
		try {
			riskQueryOrder.check();
			List<CouncilRiskInfo> pageList = Lists.newArrayList();
			CouncilRiskDO councilRiskDO = new CouncilRiskDO();
			if (riskQueryOrder.getCouncilType() != null) {
				councilRiskDO.setCouncilType(riskQueryOrder.getCouncilType().getCode());
			}
			if (riskQueryOrder.getCouncilStatus() != null) {
				councilRiskDO.setCouncilStatus(riskQueryOrder.getCouncilStatus().getCode());
			}
			
			long totalCount = councilRiskDAO.findByConditionCount(councilRiskDO,
				riskQueryOrder.getStartTimeBegin(), riskQueryOrder.getStartTimeEnd(),
				riskQueryOrder.getLoginUserId(), riskQueryOrder.getDeptIdList(),
				riskQueryOrder.getRelatedRoleList());
			PageComponent component = new PageComponent(riskQueryOrder, totalCount);
			List<CouncilRiskDO> recordList = councilRiskDAO.findByCondition(councilRiskDO,
				component.getFirstRecord(), component.getPageSize(),
				riskQueryOrder.getStartTimeBegin(), riskQueryOrder.getStartTimeEnd(),
				riskQueryOrder.getLoginUserId(), riskQueryOrder.getDeptIdList(),
				riskQueryOrder.getRelatedRoleList(), riskQueryOrder.getSortCol(),
				riskQueryOrder.getSortOrder());
			for (CouncilRiskDO item : recordList) {
				CouncilRiskInfo info = doToInfo(item);
				CouncilRiskSummaryDO councilRiskSummaryDO = new CouncilRiskSummaryDO();
				councilRiskSummaryDO.setCouncilId(info.getCouncilId());
				if (ListUtil
					.isNotEmpty(councilRiskSummaryDAO.findByCondition(councilRiskSummaryDO))) {
					info.setIsSummary("Y");
				}
				councilRiskSummaryDO = new CouncilRiskSummaryDO();
				councilRiskSummaryDO.setCouncilId(item.getCouncilId());
				List<CouncilRiskSummaryDO> summaryDOs = councilRiskSummaryDAO
					.findByCondition(councilRiskSummaryDO);
				if (ListUtil.isNotEmpty(summaryDOs)) {
					String isConfirm = null;
					for (CouncilRiskSummaryDO summaryDO : summaryDOs) {
						if (StringUtil.isEmpty(isConfirm)) {
							if (StringUtil.equalsIgnoreCase("YES",summaryDO.getNeedConfirm()) && (StringUtil.isEmpty(summaryDO.getIsConfirm())
								|| !StringUtil.equals(summaryDO.getIsConfirm(), "YES"))) {
								isConfirm = "NO";
							}
						}
						if (!StringUtil.equalsIgnoreCase(info.getIsNeedConfirm(), "YES")) {
							info.setIsNeedConfirm(summaryDO.getNeedConfirm());
						}
					}
					info.setIsConfirm(StringUtil.defaultIfEmpty(isConfirm, "YES"));
					
				}
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
	public FcsBaseResult save(final CouncilRiskProcessOrder processOrder) {
		return commonProcess(processOrder, "新增小组会议", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				ProjectRiskHandleTeamDO teamDO = new ProjectRiskHandleTeamDO();
				teamDO.setProjectCode(processOrder.getProjectCode());
				List<ProjectRiskHandleTeamDO> teamDOList = projectRiskHandleTeamDAO
					.findByCondition(teamDO, 0, 0, null, null,0,null,null, null, null);
				if (ListUtil.isEmpty(teamDOList)) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "没有成立风险小组");
				}
				
				String[] memberIds = teamDOList.get(0).getMemberIds().split(",");
				boolean isMember = false;
				for (String member : memberIds) {
					if (StringUtil.equalsIgnoreCase(member, "" + processOrder.getUserId())) {
						isMember = true;
						break;
					}
				}
				
				if (!isMember) {
					if (processOrder.getUserId() == teamDOList.get(0).getViceLeaderId()) {
						isMember = true;
					}
				}
				
				if (!isMember) {
					if (processOrder.getUserId() == teamDOList.get(0).getChiefLeaderId()) {
						isMember = true;
					}
				}
				
				if (!isMember) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS,
						"只有小组成员才可以新增小组会议");
				}
				
				if (StringUtil.equals(processOrder.getCouncilType(),
					CouncilRiskTypeEnum.MONTH_MEETING.getCode())) {
					CouncilRiskDO riskDO = new CouncilRiskDO();
					riskDO.setCouncilType(CouncilRiskTypeEnum.MONTH_MEETING.getCode());
					riskDO.setProjectCode(processOrder.getProjectCode());
					long totalCount = councilRiskDAO.findByConditionCount(riskDO,
						DateUtil.getCurrentMonthStartTime(), DateUtil.getCurrentMonthEndTime(), 0,
						null, null);
					if (totalCount > 0) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"本项目本月已经成立过月会");
					}
				}
				
				CouncilRiskDO councilRiskDO = null;
				if (processOrder.getCouncilId() > 0) {
					councilRiskDO = councilRiskDAO.findById(processOrder.getCouncilId());
					if (councilRiskDO == null) {
						throw ExceptionFactory
							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "会议不存在");
					}
					//修改前的会议
					CouncilRiskDO oldCouncilRisk = new CouncilRiskDO();
					BeanCopier.staticCopy(councilRiskDO, oldCouncilRisk);
					FcsPmDomainHolder.get().addAttribute("oldCouncilRisk", oldCouncilRisk);
				} else {
					councilRiskDO = new CouncilRiskDO();
				}
				BeanCopier.staticCopy(processOrder, councilRiskDO);
				councilRiskDO.setApplyManId(processOrder.getUserId());
				councilRiskDO.setApplyManName(processOrder.getUserName());
				if (StringUtil.isEmpty(councilRiskDO.getCouncilStatus())) {
					councilRiskDO.setCouncilStatus(CouncilStatusEnum.NOT_BEGIN.getCode());
				}
				if (councilRiskDO.getCouncilId() != 0) {
					councilRiskDAO.update(councilRiskDO);
				} else {
					councilRiskDO.setCouncilCode(genCouncilCode(processOrder));
					councilRiskDO.getCouncilCode();
					councilRiskDO.setRawAddTime(getSysdate());
					councilRiskDO.setCouncilId(councilRiskDAO.insert(councilRiskDO));
				}
				FcsPmDomainHolder.get().addAttribute("councilRisk", councilRiskDO);
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				//发送消息给参会人员 尊敬的用户张三，项目 : xxx项目编号xxx   有新发起的小组会议，会议时间:xxxxxx , 会议地点:xxxxxx , 发起人:李四 ，请及时参加！
				CouncilRiskDO councilRisk = (CouncilRiskDO) FcsPmDomainHolder.get().getAttribute(
					"councilRisk");
				CouncilRiskDO oldCouncilRisk = (CouncilRiskDO) FcsPmDomainHolder.get()
					.getAttribute("oldCouncilRisk");
				if (councilRisk != null) {
					
					String councilAddr = "<a href='/projectMg/index.htm?systemNameDefautUrl=/projectMg/councilRisk/info.htm&councilId="
											+ councilRisk.getCouncilId() + "'>查看详情</a>";
					
					//构建消息
					String messageContent = "项目：" + councilRisk.getProjectCode() + "有新发起的小组会议："
											+ councilRisk.getCouncilCode() + "，会议时间："
											+ DateUtil.simpleFormat(councilRisk.getBeginTime())
											+ "，会议地点：" + councilRisk.getCouncilPlace() + "，发起人："
											+ councilRisk.getApplyManName() + "，请及时参加！"
											+ councilAddr;
					
					if (oldCouncilRisk != null) {//编辑会议通知参会人员/通知被移除的参会人员
						String[] oldpidArr = oldCouncilRisk.getParticipantIds().split(",");
						String[] oldpnameArr = oldCouncilRisk.getParticipantNames().split(",");
						String[] newpidArr = councilRisk.getParticipantIds().split(",");
						String[] newpnameArr = councilRisk.getParticipantNames().split(",");
						Map<Long, String> newp = Maps.newHashMap(); //新的人员
						Map<Long, String> oldp = Maps.newHashMap();//原来的人员
						Map<Long, String> removedMan = Maps.newHashMap();//被移除的人员
						Map<Long, String> newAddMan = Maps.newHashMap(); //新增的人员
						for (int i = 0; i < newpidArr.length; i++) {
							long pid = NumberUtil.parseLong(newpidArr[i]);
							if (pid > 0)
								newp.put(pid, newpnameArr[i]);
						}
						for (int i = 0; i < oldpidArr.length; i++) {
							long pid = NumberUtil.parseLong(oldpidArr[i]);
							if (pid > 0)
								oldp.put(pid, oldpnameArr[i]);
							if (!newp.containsKey(pid)) {//新的参会人员不包含的旧参会人员就是移除的
								removedMan.put(pid, oldpnameArr[i]);
							}
						}
						for (int i = 0; i < newpidArr.length; i++) {
							long pid = NumberUtil.parseLong(newpidArr[i]);
							if (!oldp.containsKey(pid)) {//旧的参会人员不包含的新参会人员就是新增的
								newAddMan.put(pid, newpnameArr[i]);
							}
						}
						if (!newAddMan.isEmpty()) {//有新的参会人员
							SimpleUserInfo[] sendUsers = new SimpleUserInfo[newAddMan.size()];
							int i = 0;
							for (long pid : newAddMan.keySet()) {
								SimpleUserInfo user = new SimpleUserInfo();
								user.setUserId(pid);
								user.setUserName(newAddMan.get(pid));
								sendUsers[i] = user;
								i++;
							}
							MessageOrder order = MessageOrder.newSystemMessageOrder();
							order.setMessageSubject("小组会议参会提醒");
							order.setMessageTitle("小组会议参会提醒");
							order.setMessageContent(messageContent);
							order.setSendUsers(sendUsers);
							order.setWithSenderName(true);
							siteMessageService.addMessageInfo(order);
						}
						if (!removedMan.isEmpty()) {//通知被移除的参会人员
						
							messageContent = "您被移出小组会议：" + councilRisk.getCouncilCode() + "，会议时间："
												+ DateUtil.simpleFormat(councilRisk.getBeginTime())
												+ "，会议地点：" + councilRisk.getCouncilPlace()
												+ "，发起人：" + councilRisk.getApplyManName() + "，"
												+ councilAddr;
							SimpleUserInfo[] sendUsers = new SimpleUserInfo[removedMan.size()];
							int i = 0;
							for (long pid : removedMan.keySet()) {
								SimpleUserInfo user = new SimpleUserInfo();
								user.setUserId(pid);
								user.setUserName(removedMan.get(pid));
								sendUsers[i] = user;
								i++;
							}
							MessageOrder order = MessageOrder.newSystemMessageOrder();
							order.setMessageSubject("移出小组会议提醒");
							order.setMessageTitle("移出小组会议提醒");
							order.setMessageContent(messageContent);
							order.setSendUsers(sendUsers);
							order.setWithSenderName(true);
							siteMessageService.addMessageInfo(order);
						}
					} else {//新增直接发送给参会人员
						String[] pidArr = councilRisk.getParticipantIds().split(",");
						String[] pnameArr = councilRisk.getParticipantNames().split(",");
						if (pidArr != null && pidArr.length > 0) {
							SimpleUserInfo[] sendUsers = new SimpleUserInfo[pidArr.length];
							for (int i = 0; i < pidArr.length; i++) {
								long pid = NumberUtil.parseLong(pidArr[i]);
								SimpleUserInfo user = new SimpleUserInfo();
								user.setUserId(pid);
								user.setUserName(pnameArr[i]);
								sendUsers[i] = user;
							}
							MessageOrder order = MessageOrder.newSystemMessageOrder();
							order.setMessageSubject("小组会议参会提醒");
							order.setMessageTitle("小组会议参会提醒");
							order.setMessageContent(messageContent);
							order.setSendUsers(sendUsers);
							order.setWithSenderName(true);
							siteMessageService.addMessageInfo(order);
						}
					}
				}
				return null;
			}
		});
	}
	
	/**
	 * 风（年月）月/季/例-部门编码-顺序号
	 * @param processOrder
	 * @return
	 */
	private synchronized String genCouncilCode(CouncilRiskProcessOrder processOrder) {
		String deptCode = (processOrder.getDeptCode() == null ? "" : processOrder.getDeptCode());
		String projectCode = "风（"
								+ DateUtil.simpleFormatYM(processOrder.getBeginTime()).replace(".",
									"")
								+ ")"
								+ CouncilRiskTypeEnum.getByCode(processOrder.getCouncilType())
									.getAlias() + "-" + StringUtil.leftPad(deptCode, 3, "0");
		String seqName = SysDateSeqNameEnum.COUNCIL_RISK_CODE_SEQ.code();
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		projectCode += "-" + StringUtil.leftPad(String.valueOf(seq), 3, "0");
		return projectCode;
	}
	
	@Override
	public FcsBaseResult endCouncilRisk(final EndCouncilRiskProcessOrder riskProcessOrder) {
		return commonProcess(riskProcessOrder, "结束会议", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				CouncilRiskDO councilRiskDO = councilRiskDAO.findById(riskProcessOrder
					.getCouncilId());
				councilRiskDO.setCouncilStatus(CouncilStatusEnum.BREAK_UP.getCode());
				councilRiskDAO.update(councilRiskDO);
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult saveSummary(final AddCouncilRiskSummaryOrder riskSummaryOrder) {
		return commonProcess(riskSummaryOrder, "保存会议纪要", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				CouncilRiskDO councilRiskDO = councilRiskDAO.findById(riskSummaryOrder
					.getCouncilId());
				
				if (ListUtil.isNotEmpty(riskSummaryOrder.getSummaryOrder())) {
					for (CouncilRiskSummaryOrder councilRiskSummaryOrder : riskSummaryOrder
						.getSummaryOrder()) {
						CouncilRiskSummaryDO councilRiskSummaryDO = new CouncilRiskSummaryDO();
						BeanCopier.staticCopy(councilRiskSummaryOrder, councilRiskSummaryDO);
						if (councilRiskSummaryDO.getSummaryId() != 0) {
							councilRiskSummaryDAO.update(councilRiskSummaryDO);
							CouncilRiskSummaryDO summaryDO = councilRiskSummaryDAO
								.findById(councilRiskSummaryDO.getSummaryId());
							if (!StringUtil.equals(summaryDO.getConfirmManIds(),
								councilRiskSummaryDO.getConfirmManIds())
								&& StringUtil.isNotEmpty(councilRiskSummaryOrder.getConfirmManIds())) {
								String[] confirmManIds = councilRiskSummaryOrder.getConfirmManIds()
									.split(",");
								for (String manId : confirmManIds) {
									SimpleUserInfo simpleUserInfo = new SimpleUserInfo();
									simpleUserInfo.setUserId(NumberUtil.parseLong(manId));
									projectRelatedUserService.manualGrant(
										councilRiskDO.getProjectCode(), "会议落实人员", simpleUserInfo);
								}
							}
						} else {
							councilRiskSummaryDO.setCouncilId(riskSummaryOrder.getCouncilId());
							councilRiskSummaryDAO.insert(councilRiskSummaryDO);
							if (StringUtil.isNotEmpty(councilRiskSummaryOrder.getConfirmManIds())) {
								String[] confirmManIds = councilRiskSummaryOrder.getConfirmManIds()
									.split(",");
								for (String manId : confirmManIds) {
									SimpleUserInfo simpleUserInfo = new SimpleUserInfo();
									simpleUserInfo.setUserId(NumberUtil.parseLong(manId));
									projectRelatedUserService.manualGrant(
										councilRiskDO.getProjectCode(), "会议落实人员", simpleUserInfo);
								}
								
							}
							
						}
					}
				}
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			@Override
			public Domain after(Domain domain) {
				if (ListUtil.isNotEmpty(riskSummaryOrder.getSummaryOrder())) {
					boolean isConfirm = false;
					for (CouncilRiskSummaryOrder councilRiskSummaryOrder : riskSummaryOrder
						.getSummaryOrder()) {
						if (StringUtil.equals(councilRiskSummaryOrder.getIsConfirm(), "YES")) {
							notice(riskSummaryOrder);
							continue;
						}
					}
				}
				return null;
			}
		});
	}
	
	private void notice(AddCouncilRiskSummaryOrder riskSummaryOrder) {
		
		CouncilRiskDO riskDO = councilRiskDAO.findById(riskSummaryOrder.getCouncilId());
		if (riskDO == null) {
			return;
		}
		//抓去项目名称
		String projectCode = riskDO.getProjectCode();
		String subject = "会议落实信息";
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
			0, null, null,0,null,null, null, null);
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
		if(ListUtil.isNotEmpty(riskSummaryOrder.getSummaryOrder())){
			for(CouncilRiskSummaryOrder summaryOrder : riskSummaryOrder.getSummaryOrder()){
				if(!isFirst){
					summaryIds.append(",");
				}
				summaryIds.append(summaryOrder.getSummaryId());
				isFirst = false;
			}
		}


		if (ListUtil.isNotEmpty(notifyUserList)) {
			//"尊敬的用户张三，有新的会议落实信息，会议编号：（2015）001，会议议题：XXXXXXXXX.  现在查看"
			//表单站内地址
			String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/councilRisk/confirmSummaryInfo.htm?councilId="
								+ riskDO.getCouncilId() +"&summaryIds="+summaryIds.toString();
			String forMessage = "<a href='" + messageUrl + "'>查看详情</a>";
			
			for (SimpleUserInfo userInfo : notifyUserList) {
				SimpleUserInfo[] userInfos = new SimpleUserInfo[1];
				userInfos[0] = userInfo;
				//替换审核地址
				StringBuilder sb = new StringBuilder();
				sb.append("尊敬的用户");
				sb.append(userInfo.getUserName());
				sb.append("，有新的会议落实信息，会议编号：").append(riskDO.getCouncilCode()).append(",会议议题：")
					.append(riskDO.getCouncilSubject()).append(",现在去看");
				sb.append(projectCode).append("。   ");
				String messageContent = sb.toString();
				messageContent += forMessage;
				MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
				messageOrder.setMessageSubject(subject);
				messageOrder.setMessageTitle("会议落实信息");
				messageOrder.setMessageContent(messageContent);
				messageOrder.setSendUsers(userInfos);
				siteMessageService.addMessageInfo(messageOrder);
			}
		}
	}
	
	@Override
	public void startCouncilRisk() {
		CouncilRiskQueryOrder riskQueryOrder = new CouncilRiskQueryOrder();
		riskQueryOrder.setStartTimeEnd(getSysdate());
		riskQueryOrder.setCouncilStatus(CouncilStatusEnum.NOT_BEGIN);
		riskQueryOrder.setPageSize(99999999999l);
		List<CouncilRiskInfo> councilRiskInfoList = queryCouncilRiskInfo(riskQueryOrder)
			.getPageList();
		if (ListUtil.isNotEmpty(councilRiskInfoList)) {
			for (CouncilRiskInfo riskInfo : councilRiskInfoList) {
				try {
					CouncilRiskDO councilRiskDO = new CouncilRiskDO();
					BeanCopier.staticCopy(riskInfo, councilRiskDO);
					if (riskInfo.getCouncilType() != null) {
						councilRiskDO.setCouncilType(riskInfo.getCouncilType().getCode());
					}
					councilRiskDO.setCouncilStatus(CouncilStatusEnum.IN_PROGRESS.getCode());
					councilRiskDAO.update(councilRiskDO);
				} catch (Exception e) {
					logger.error("启动风险管控小组会议报错", e);
				}
			}
		}
	}
	
	@Override
	public void noticeMonth() {
		ProjectRiskHandleTeamDO projectRiskHandleTeamDO = new ProjectRiskHandleTeamDO();
		List<ProjectRiskHandleTeamDO> recordList = projectRiskHandleTeamDAO.findByCondition(
			projectRiskHandleTeamDO, 0, 0, null, null,0,null,null, null, null);
		if (ListUtil.isEmpty(recordList)) {
			return;
		}
		for (ProjectRiskHandleTeamDO item : recordList) {
			CouncilRiskDO councilRiskDO = new CouncilRiskDO();
			councilRiskDO.setProjectCode(item.getProjectCode());
			councilRiskDO.setCouncilType(CouncilRiskTypeEnum.MONTH_MEETING.getCode());
			List<CouncilRiskDO> riskDOs = councilRiskDAO.findByCondition(councilRiskDO, 0, 0,
				DateUtil.getCurrentMonthStartTime(), DateUtil.getCurrentMonthEndTime(), 0, null,
				null, null, null);
			if (ListUtil.isNotEmpty(riskDOs)) {
				continue;
			}
			
			String projectCode = item.getProjectCode();
			String subject = "小组会议月会召开提醒";
			//尊敬的用户张三，您的项目 XXX项目名称XXX 已上会完成，投票通过，快去查看详情吧！
			// 发送给业务经理 A角
			
			List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
			ProjectRelatedUserInfo busiManager = projectRelatedUserService.getBusiManager(item
				.getProjectCode());
			if (busiManager != null) {
				SimpleUserInfo sendUser = new SimpleUserInfo();
				sendUser.setUserId(busiManager.getUserId());
				sendUser.setUserAccount(busiManager.getUserAccount());
				sendUser.setUserName(busiManager.getUserName());
				sendUser.setEmail(busiManager.getUserEmail());
				sendUser.setMobile(busiManager.getUserMobile());
				notifyUserList.add(sendUser);
			}
			
			List<SimpleUserInfo> busiManager2 = projectRelatedUserService.getBusiDirector(item
				.getProjectCode());
			
			notifyUserList.addAll(busiManager2);
			
			if (ListUtil.isNotEmpty(notifyUserList)) {
				//"尊敬的用户张三，有新的会议落实信息，会议编号：（2015）001，会议议题：XXXXXXXXX.  现在查看"
				//表单站内地址
				String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/councilRisk/toAdd.htm?councilId=0";
				String forMessage = "<a href='" + messageUrl + "'>现在就去</a>";
				
				for (SimpleUserInfo userInfo : notifyUserList) {
					SimpleUserInfo[] userInfos = new SimpleUserInfo[1];
					userInfos[0] = userInfo;
					//替换审核地址
					StringBuilder sb = new StringBuilder();
					sb.append("尊敬的用户");
					sb.append(userInfo.getUserName());
					sb.append("，请及时对风险客户（客户名称：").append(item.getCustomerName()).append("）召开月会");
					sb.append(projectCode).append("。   ");
					String messageContent = sb.toString();
					messageContent += forMessage;
					MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
					messageOrder.setMessageSubject(subject);
					messageOrder.setMessageTitle("小组会议月会召开提醒");
					messageOrder.setMessageContent(messageContent);
					messageOrder.setSendUsers(userInfos);
					siteMessageService.addMessageInfo(messageOrder);
				}
			}
		}
	}
	
	@Override
	public void addNoticeMonth() {
		ProjectRiskHandleTeamDO projectRiskHandleTeamDO = new ProjectRiskHandleTeamDO();
		List<ProjectRiskHandleTeamDO> recordList = projectRiskHandleTeamDAO.findByCondition(
			projectRiskHandleTeamDO, 0, 0, null, null,0,null,null, null, null);
		if (ListUtil.isEmpty(recordList)) {
			return;
		}
		for (ProjectRiskHandleTeamDO item : recordList) {
			ProjectRiskReportDO councilRiskDO = new ProjectRiskReportDO();
			councilRiskDO.setProjectCode(item.getProjectCode());
			councilRiskDO.setReportType("month");
			List<ProjectRiskReportDO> riskDOs = projectRiskReportDAO.findByCondition(councilRiskDO, 0, 0,
				DateUtil.getCurrentMonthStartTime(), DateUtil.getCurrentMonthEndTime(), 0, null,
				null, null, null);
			if (ListUtil.isNotEmpty(riskDOs)) {
				continue;
			}
			
			String projectCode = item.getProjectCode();
			String subject = "风险项目情况跟踪月报";
			//尊敬的用户张三，您的项目 XXX项目名称XXX 已上会完成，投票通过，快去查看详情吧！
			// 发送给业务经理 A角
			
			List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
			SysUser busiManager = bpmUserQueryService.findUserByUserId(item
				.getBusiManagerId());
			if (busiManager != null) {
				SimpleUserInfo sendUser = new SimpleUserInfo();
				sendUser.setUserId(busiManager.getUserId());
				sendUser.setUserAccount(busiManager.getAccount());
				sendUser.setUserName(busiManager.getFullname());
				sendUser.setEmail(busiManager.getEmail());
				sendUser.setMobile(busiManager.getMobile());
				notifyUserList.add(sendUser);
			}
			
			if (ListUtil.isNotEmpty(notifyUserList)) {
				//"尊敬的用户张三，有新的会议落实信息，会议编号：（2015）001，会议议题：XXXXXXXXX.  现在查看"
				//表单站内地址
				String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/projectRiskReport/toAdd.htm?type=month";
				String forMessage = "<a href='" + messageUrl + "'>现在就去</a>";
				
				for (SimpleUserInfo userInfo : notifyUserList) {
					SimpleUserInfo[] userInfos = new SimpleUserInfo[1];
					userInfos[0] = userInfo;
					//替换审核地址
					StringBuilder sb = new StringBuilder();
					sb.append("尊敬的用户");
					sb.append(userInfo.getUserName());
					sb.append("，请及时对风险项目（项目编号：").append(projectCode).append("）进行《风险项目情况跟踪月报》表填写。");
					String messageContent = sb.toString();
					messageContent += forMessage;
					MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
					messageOrder.setMessageSubject(subject);
					messageOrder.setMessageTitle("风险项目情况跟踪月报");
					messageOrder.setMessageContent(messageContent);
					messageOrder.setSendUsers(userInfos);
					siteMessageService.addMessageInfo(messageOrder);
				}
			}
		}
	}
}
