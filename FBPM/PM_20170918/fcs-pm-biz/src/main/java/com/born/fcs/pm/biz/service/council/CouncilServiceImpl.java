package com.born.fcs.pm.biz.service.council;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.biz.service.common.DateSeqService;
import com.born.fcs.pm.biz.service.common.MailService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.dal.dataobject.CouncilApplyDO;
import com.born.fcs.pm.dal.dataobject.CouncilDO;
import com.born.fcs.pm.dal.dataobject.CouncilJudgeDO;
import com.born.fcs.pm.dal.dataobject.CouncilParticipantDO;
import com.born.fcs.pm.dal.dataobject.CouncilProjectDO;
import com.born.fcs.pm.dal.dataobject.CouncilProjectVoteDO;
import com.born.fcs.pm.dal.dataobject.CouncilTypeDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.OneVoteResultEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.VoteResultEnum;
import com.born.fcs.pm.ws.enums.VoteRuleTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.council.CouncilInfo;
import com.born.fcs.pm.ws.info.council.CouncilJudgeInfo;
import com.born.fcs.pm.ws.info.council.CouncilParticipantInfo;
import com.born.fcs.pm.ws.info.council.CouncilProjectInfo;
import com.born.fcs.pm.ws.info.council.CouncilTypeInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryInfo;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SendMailOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.SysWebAccessTokenOrder;
import com.born.fcs.pm.ws.order.council.CouncilDelOrder;
import com.born.fcs.pm.ws.order.council.CouncilOrder;
import com.born.fcs.pm.ws.order.council.CouncilProjectVoteOrder;
import com.born.fcs.pm.ws.order.council.CouncilQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.SysWebAccessTokenService;
import com.born.fcs.pm.ws.service.council.CouncilApplyService;
import com.born.fcs.pm.ws.service.council.CouncilJudgeService;
import com.born.fcs.pm.ws.service.council.CouncilParticipantService;
import com.born.fcs.pm.ws.service.council.CouncilProjectService;
import com.born.fcs.pm.ws.service.council.CouncilProjectVoteService;
import com.born.fcs.pm.ws.service.council.CouncilService;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;
import com.born.fcs.pm.ws.service.council.CouncilTypeService;
import com.born.fcs.pm.ws.service.sms.SMSService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("councilService")
public class CouncilServiceImpl extends BaseAutowiredDomainService implements CouncilService {
	
	@Autowired
	protected CouncilJudgeService councilJudgeService;
	
	@Autowired
	protected CouncilParticipantService councilParticipantService;
	
	@Autowired
	protected CouncilProjectService councilProjectService;
	
	@Autowired
	protected CouncilProjectVoteService councilProjectVoteService;
	
	@Autowired
	protected DateSeqService dateSeqService;
	
	@Autowired
	CouncilApplyService councilApplyService;
	
	@Autowired
	CouncilTypeService councilTypeService;
	
	@Autowired
	CouncilSummaryService councilSummaryService;
	
	@Autowired
	FormService formService;
	
	@Autowired
	protected SiteMessageService siteMessageService;
	
	@Autowired
	protected ProjectRelatedUserService projectRelatedUserService;
	
	@Autowired
	private BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	protected SysWebAccessTokenService sysWebAccessTokenService;
	
	@Autowired
	protected MailService mailService;
	
	@Autowired
	protected SMSService sMSService;
	
	@Override
	public FcsBaseResult saveCouncil(final CouncilOrder order) {
		
		return commonProcess(order, "保存会议", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				// 添加的从order中抓出，用info存储
				List<CouncilProjectInfo> addProjectList = new ArrayList<CouncilProjectInfo>();
				// 删除的从DO中抓出。用do存储
				List<CouncilProjectDO> delProjectList = new ArrayList<CouncilProjectDO>();
				// 人员被安排上会，新增加的评委和列席人员
				List<CouncilJudgeInfo> addJudges = new ArrayList<CouncilJudgeInfo>();
				List<CouncilParticipantInfo> addparticipants = new ArrayList<CouncilParticipantInfo>();
				// 上会项目被修改，已存在的人员，本人未投票/不需要投票
				List<CouncilJudgeInfo> noticeJudges = new ArrayList<CouncilJudgeInfo>();
				List<CouncilParticipantInfo> noticeparticipants = new ArrayList<CouncilParticipantInfo>();
				
				// 上会项目被修改，已存在的评委 本人已投票
				List<CouncilJudgeInfo> voteJudges = new ArrayList<CouncilJudgeInfo>();
				
				// 上会项目被修改，被删除的列席人员
				List<CouncilParticipantDO> delparticipants = new ArrayList<CouncilParticipantDO>();
				
				// 上会项目被修改，被删除的评委
				List<CouncilJudgeInfo> delJudges = new ArrayList<CouncilJudgeInfo>();
				
				CompanyNameEnum conpanyName = CompanyNameEnum.NORMAL;
				boolean allXL = true;
				// 在添加或修改前添加新判定任务：会议类型与申请人所在相匹配
				// 抓取会议类型
				CouncilTypeDO typeDO = councilTypeDAO.findById(order.getCouncilType());
				for (CouncilProjectInfo info : order.getProjects()) {
					CouncilApplyDO applyDO = councilApplyDAO.findById(info.getApplyId());
					if (StringUtil.isNotBlank(typeDO.getApplyDeptId())) {
						if (!Arrays.asList(typeDO.getApplyDeptId().split(",")).contains(
							String.valueOf(applyDO.getApplyDeptId()))) {
							// 若不处于会议类型的部门中，不允许提交
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.INCOMPLETE_REQ_PARAM, "项目" + info.getProjectName()
																	+ "申请人所属部门未包含在会议中！");
						}
					} else {
						// 会议类型部门为空，不允许提交
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到会议类型所属部门！");
					}
					// 判定是否全是信汇
					if (!CompanyNameEnum.XINHUI.code().equals(applyDO.getCompanyName())) {
						allXL = false;
					}
					// 判定是否已上会
					if (info.getId() <= 0) {
						if (CouncilApplyStatusEnum.IN.code().equals(applyDO.getStatus())) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"已上会的记录无法再次上会！");
						}
					}
				}
				// 全是信汇
				if (allXL) {
					conpanyName = CompanyNameEnum.XINHUI;
				}
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				if (order.getCouncilId() <= 0) {
					
					// check , 添加判定条件，同一时间地点的会议无法添加成功，用于隔离重复提交
					// 线上会议不参与计算
					if (BooleanEnum.YES != order.getCouncilOnline()) {
						List<CouncilDO> councilDOs = councilDAO.findByStartTimeAndPlace(
							order.getStartTime(), order.getCouncilPlace());
						if (ListUtil.isNotEmpty(councilDOs) && councilDOs.size() > 0) {
							// 线上会议不参与计算
							for (CouncilDO cDO : councilDOs) {
								// 并非线上会议，代表已存在，需拦截
								if (StringUtil.notEquals(BooleanEnum.YES.code(),
									cDO.getCouncilOnline())) {
									throw ExceptionFactory.newFcsException(
										FcsResultEnum.HAVE_NOT_DATA, "不允许申请同一时间同一地点的会议！");
								}
							}
						}
					}
					// 保存
					CouncilDO councilDO = new CouncilDO();
					String councilCode = order.getCouncilCode();
					// 20161209 添加判断 若councilcode未修改，使用获取councilcode ，若修改，查询新code是否被使用
					if (StringUtil.equals(StringUtil.trim(order.getCouncilCode()),
						StringUtil.trim(order.getCouncilCodeOld()))) {
						councilCode = dateSeqService.getNextCouncilCodeSeqByParam(conpanyName,
							CouncilTypeEnum.getByCode(order.getCouncilTypeCode()));
					} else {
						// 获取当前code是否已经有会议，若有会议，不允许提交
						List<CouncilDO> oldInfos = councilDAO.findByCode(councilCode);
						if (ListUtil.isNotEmpty(oldInfos)) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.DATABASE_EXCEPTION, "该会议编号" + councilCode
																	+ "已存在，不允许新增！");
						}
						
					}
					
					order2DO(order, councilDO, councilCode);
					councilDO.setCompanyName(conpanyName.code());
					
					councilDO.setCouncilCode(councilCode);
					councilDO.setStatus(CouncilStatusEnum.NOT_BEGIN.getCode());
					councilDO.setRawAddTime(now);
					long councilId = councilDAO.insert(councilDO);
					order.setCouncilId(councilId);
					order.setCouncilCode(councilCode);
					// 评委
					List<CouncilJudgeInfo> councilJudges = order.getJudges();
					List<Long> judgeIds = new ArrayList<Long>();
					if (councilJudges != null) {
						for (CouncilJudgeInfo judgeInfo : councilJudges) {
							judgeIds.add(judgeInfo.getJudgeId());
							CouncilJudgeDO councilJudgeDO = new CouncilJudgeDO();
							judgeInfo2DO(order, judgeInfo, councilJudgeDO);
							councilJudgeDO.setRawAddTime(now);
							
							// 判断是否是主持人
							if (judgeInfo.getJudgeId() == order.getCompereId()) {
								councilJudgeDO.setCompere(BooleanEnum.YES.code());
							}
							
							councilJudgeDAO.insert(councilJudgeDO);
						}
						// 添加人员，用于判定人员权限
						relatedUserInsert(order.getProjects(), councilId,
							ProjectRelatedUserTypeEnum.COUNCIL_JUDGE, judgeIds);
					}
					addJudges = order.getJudges();
					// 参会项目
					// 先抓取出参会项目
					List<CouncilProjectInfo> councilProjects = order.getProjects();
					for (CouncilProjectInfo projectInfo : councilProjects) {
						CouncilProjectDO councilProjectDO = new CouncilProjectDO();
						projectInfo2DO(order, projectInfo, councilProjectDO);
						councilProjectDO.setProjectVoteResult(ProjectVoteResultEnum.NOT_BEGIN
							.code());
						councilProjectDO.setRawAddTime(now);
						councilProjectDAO.insert(councilProjectDO);
						councilApplySuccess(projectInfo.getApplyId());
						
						// 设置所有添加的councilPorject
						addProjectList.add(projectInfo);
					}
					// 列席人员
					List<CouncilParticipantInfo> participants = order.getParticipants();
					if (ListUtil.isEmpty(participants)) {
						participants = new ArrayList<CouncilParticipantInfo>();
					}
					addparticipants = order.getParticipants();
					if (ListUtil.isEmpty(addparticipants)) {
						addparticipants = new ArrayList<CouncilParticipantInfo>();
					}
					
					if (ListUtil.isNotEmpty(participants)) {
						for (CouncilParticipantInfo participantInfo : participants) {
							CouncilParticipantDO councilParticipantDO = new CouncilParticipantDO();
							participantInfo2DO(order, participantInfo, councilParticipantDO);
							councilParticipantDO.setRawAddTime(now);
							councilParticipantDAO.insert(councilParticipantDO);
							// 添加人员，用于判定人员权限
							//relatedUserInsert(councilProjects, councilId,
							//	RelatedUserTypeEnum.COUNCIL_PARTICIPANT,
							//	participantInfo.getParticipantId());
						}
					}
					
					// 初始化投票记录
					List<CouncilJudgeDO> judges = councilJudgeDAO.findByCouncilId(councilId);
					List<CouncilProjectDO> projects = councilProjectDAO.findByCouncilId(councilId);
					for (CouncilProjectDO p : projects) {
						for (CouncilJudgeDO j : judges) {
							CouncilProjectVoteDO vote = new CouncilProjectVoteDO();
							vote.setCouncilProjectId(p.getId());
							vote.setCouncilId(councilId);
							vote.setCouncilCode(councilCode);
							vote.setProjectCode(p.getProjectCode());
							vote.setProjectName(p.getProjectName());
							vote.setJudgeId(j.getJudgeId());
							vote.setJudgeName(j.getJudgeName());
							vote.setVoteStatus(BooleanEnum.NO.code());
							vote.setVoteResult(VoteResultEnum.UNKNOWN.code());
							vote.setVoteResultDesc(VoteResultEnum.UNKNOWN.message());
							vote.setRawAddTime(now);
							councilProjectVoteDAO.insert(vote);
						}
						
					}
					
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(councilId);
				} else {
					// 更新
					CouncilDO councilDO = councilDAO.findById(order.getCouncilId());
					if (null == councilDO) {
						throw ExceptionFactory
							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未找到会议");
					}
					
					// check , 添加判定条件，同一时间地点的会议无法添加成功，用于隔离重复提交
					// 线上会议不参与计算
					if (BooleanEnum.YES != order.getCouncilOnline()) {
						List<CouncilDO> councilDOs = councilDAO.findByStartTimeAndPlace(
							order.getStartTime(), order.getCouncilPlace());
						if (ListUtil.isNotEmpty(councilDOs) && councilDOs.size() > 0) {
							// 线上会议不参与计算
							for (CouncilDO cDO : councilDOs) {
								// 剔除自己
								if (councilDO.getCouncilId() == cDO.getCouncilId()) {
									continue;
								}
								// 并非线上会议，代表已存在，需拦截
								if (StringUtil.notEquals(BooleanEnum.YES.code(),
									cDO.getCouncilOnline())) {
									throw ExceptionFactory.newFcsException(
										FcsResultEnum.HAVE_NOT_DATA, "不允许申请同一时间同一地点的会议！");
								}
							}
						}
					}
					
					String councilCode = councilDO.getCouncilCode();
					
					// 20161209 添加判断 若councilcode未修改，使用获取councilcode ，若修改，查询新code是否被使用
					if (StringUtil.notEquals(StringUtil.trim(order.getCouncilCode()),
						StringUtil.trim(councilCode))) {
						// 若已修改，判断新code是否有会议了
						// 获取当前code是否已经有会议，若有会议，不允许提交 
						List<CouncilDO> oldInfos = councilDAO.findByCode(order.getCouncilCode());
						if (ListUtil.isNotEmpty(oldInfos)) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.DATABASE_EXCEPTION, "该会议编号" + order.getCouncilCode()
																	+ "已存在，不允许新增！");
						}
						
					}
					
					order2DO(order, councilDO, councilCode);
					councilDO.setCompanyName(conpanyName.code());
					
					councilDAO.update(councilDO);
					
					updateJudges(order, councilDO, addJudges, now);
					updateProjects(order, councilDO, addProjectList, delProjectList, now);
					updateParticipants(order, councilDO, addparticipants, noticeparticipants,
						delparticipants, now);
					
					// 重新初始化投票记录
					// 保留老的投票记录，所以不删除
					//councilProjectVoteDAO.deleteByCouncilId(order.getCouncilId());
					List<CouncilProjectVoteDO> allVote = councilProjectVoteDAO
						.findByCouncilId(order.getCouncilId());
					// 已存在的未删除的投票记录直接抓去，就不再访问数据库增加压力了
					List<CouncilProjectVoteDO> allOldUseVote = new ArrayList<CouncilProjectVoteDO>();
					
					List<CouncilJudgeDO> judges = councilJudgeDAO.findByCouncilId(order
						.getCouncilId());
					List<CouncilProjectDO> projects = councilProjectDAO.findByCouncilId(order
						.getCouncilId());
					for (CouncilProjectDO p : projects) {
						for (CouncilJudgeDO j : judges) {
							CouncilProjectVoteDO vote = new CouncilProjectVoteDO();
							vote.setCouncilProjectId(p.getId());
							vote.setCouncilId(order.getCouncilId());
							vote.setCouncilCode(councilCode);
							vote.setProjectCode(p.getProjectCode());
							vote.setProjectName(p.getProjectName());
							vote.setJudgeId(j.getJudgeId());
							vote.setJudgeName(j.getJudgeName());
							vote.setVoteStatus(BooleanEnum.NO.code());
							vote.setVoteResult(VoteResultEnum.UNKNOWN.code());
							vote.setVoteResultDesc(VoteResultEnum.UNKNOWN.message());
							// 添加判断，如果此评委此项目此会议 有评委投票记录，则不新增
							// 访问数据库次数太多，changeTo 抓去每个项目的评委投票记录来比对
							//							CouncilProjectVoteDO voteDO = councilProjectVoteDAO.findProjectsVote(
							//								order.getCouncilId(), p.getProjectCode(), j.getJudgeId());
							//							if (voteDO == null) {
							//								councilProjectVoteDAO.insert(vote);
							//							}
							// 代表是否拥有需要保留的数据库数据
							boolean hasVote = false;
							for (CouncilProjectVoteDO voteDO : allVote) {
								// 判断是否存在此评委此项目此会议 的投票记录，若存在 不插入新数据
								if (StringUtil.isBlank(voteDO.getProjectCode())
									|| voteDO.getJudgeId() <= 0
									|| voteDO.getCouncilProjectId() <= 0) {
									// 脏数据无视，直接看下一笔。
									continue;
								}
								if (voteDO.getProjectCode().equals(p.getProjectCode())
									&& voteDO.getJudgeId() == j.getJudgeId()
									&& voteDO.getCouncilProjectId() == p.getId()) {
									hasVote = true;
									// 已找到的数据插入已存在记录列表
									allOldUseVote.add(voteDO);
									// 此处代表已投票的评委记录
									if (StringUtil.notEquals("UNKNOWN", voteDO.getVoteResult())) {
										CouncilJudgeInfo judgeInfo = new CouncilJudgeInfo();
										judgeInfo.setJudgeId(voteDO.getJudgeId());
										judgeInfo.setJudgeName(voteDO.getJudgeName());
										// 去重
										boolean hasId = false;
										for (CouncilJudgeInfo info : voteJudges) {
											if (judgeInfo.getJudgeId() == info.getJudgeId())
												hasId = true;
										}
										if (!hasId) {
											voteJudges.add(judgeInfo);
										}
									} else {
										// 此处代表未投票已存在的评委【可能和已投票的重合，需要清理一次】
										CouncilJudgeInfo judgeInfo = new CouncilJudgeInfo();
										judgeInfo.setJudgeId(voteDO.getJudgeId());
										judgeInfo.setJudgeName(voteDO.getJudgeName());
										// 去重
										boolean hasId = false;
										for (CouncilJudgeInfo info : noticeJudges) {
											if (judgeInfo.getJudgeId() == info.getJudgeId())
												hasId = true;
										}
										if (!hasId) {
											noticeJudges.add(judgeInfo);
										}
									}
									// 已找到过的数据清除掉
									Iterator<CouncilProjectVoteDO> iter = allVote.iterator();
									while (iter.hasNext()) {
										CouncilProjectVoteDO curDO = iter.next();
										if (curDO.getId() == voteDO.getId()) {
											iter.remove();
										}
									}
									break;
								}
							}
							if (!hasVote) {
								// 若数据库无数据，插入值
								vote.setRawAddTime(now);
								councilProjectVoteDAO.insert(vote);
								// 新增评委算法错误，此处不能用
								//								// 此处代表新增的评委
								//								CouncilJudgeInfo judgeInfo = new CouncilJudgeInfo();
								//								judgeInfo.setJudgeId(vote.getJudgeId());
								//								judgeInfo.setJudgeName(vote.getJudgeName());
								//								// 去重
								//								boolean hasId = false;
								//								for (CouncilJudgeInfo info : addJudges) {
								//									if (judgeInfo.getJudgeId() == info.getJudgeId())
								//										hasId = true;
								//								}
								//								if (!hasId) {
								//									addJudges.add(judgeInfo);
								//								}
							}
						}
						
					}
					// 删除无项目的评委信息
					for (CouncilProjectVoteDO voteDO : allVote) {
						// 此处代表已删除的评委
						CouncilJudgeInfo judgeInfo = new CouncilJudgeInfo();
						judgeInfo.setJudgeId(voteDO.getJudgeId());
						judgeInfo.setJudgeName(voteDO.getJudgeName());
						// 去重
						boolean hasId = false;
						for (CouncilJudgeInfo info : delJudges) {
							if (judgeInfo.getJudgeId() == info.getJudgeId())
								hasId = true;
						}
						if (!hasId) {
							delJudges.add(judgeInfo);
						}
						councilProjectVoteDAO.deleteById(voteDO.getId());
					}
					// 此动作需要在评委计算完成之后执行，故放置在最后，否则会计算到已删除的评委的投票记录
					
					// 添加动作，评委投票修改后 主项目的投票记录已经为空，需要重新插入投票记录
					List<CouncilProjectDO> projectList = councilProjectDAO
						.findByCouncilId(councilDO.getCouncilId());
					for (CouncilProjectDO projectDO : projectList) {
						// 添加判断，兼容修改会议前所有投票者都已投票导致的投票结果已经出现的问题。
						// 如果已经出现投票结果。修复未未投票
						// 20160608 因为始终要再进行系统投票操作，所以干脆全部修订为未开始就行了。
						if (!ProjectVoteResultEnum.NOT_BEGIN.code().equals(
							projectDO.getProjectVoteResult())) {
							projectDO.setProjectVoteResult(ProjectVoteResultEnum.NOT_BEGIN.code());
							councilProjectDAO.update(projectDO);
						}
					}
					for (CouncilProjectVoteDO voteDO : allOldUseVote) {
						CouncilProjectVoteOrder voteOrder = new CouncilProjectVoteOrder();
						for (CouncilProjectDO projectDO : projectList) {
							// 已投票且projectId相同就增加投票属性
							if (BooleanEnum.YES.code().equals(voteDO.getVoteStatus())
								&& voteDO.getCouncilProjectId() == projectDO.getId()) {
								MiscUtil.copyPoObject(voteOrder, voteDO);
								//councilProjectVoteService.updateCouncilProjectVote(voteOrder);
								doProjectVote(voteOrder, true);
								break;
							}
						}
					}
					
					// 从未投票评委列表中去除已投票的评委
					for (CouncilJudgeInfo judged : voteJudges) {
						Iterator<CouncilJudgeInfo> iter = noticeJudges.iterator();
						while (iter.hasNext()) {
							CouncilJudgeInfo noticed = iter.next();
							if (judged.getJudgeId() == noticed.getJudgeId()) {
								// 代表在已投票列表和未投票列表同时存在的评委，从未投票列表中剔除
								iter.remove();
							}
						}
					}
					// 所有评委，去掉已存在且未投票的评委，去掉有投票记录的评委，就等于新增的评委
					
					// 添加判定：当项目无新增的时候，前往投票的评委也展示项目变动通知
					if (addProjectList == null || addProjectList.size() <= 0) {
						noticeJudges.addAll(voteJudges);
						voteJudges = new ArrayList<CouncilJudgeInfo>();
					}
				}
				
				if (ListUtil.isNotEmpty(order.getParticipants())) {
					// 20161202 从列席人员列表中剔除评委列表addJudges中存在的数据
					for (CouncilJudgeInfo judged : addJudges) {
						Iterator<CouncilParticipantInfo> iter = order.getParticipants().iterator();
						while (iter.hasNext()) {
							CouncilParticipantInfo participant = iter.next();
							if (judged.getJudgeId() == participant.getParticipantId()) {
								// 代表在已投票列表和未投票列表同时存在的评委，从未投票列表中剔除
								iter.remove();
							}
						}
					}
					// 20161202 从列席人员列表中剔除评委列表noticeJudges中存在的数据
					for (CouncilJudgeInfo judged : noticeJudges) {
						Iterator<CouncilParticipantInfo> iter = order.getParticipants().iterator();
						while (iter.hasNext()) {
							CouncilParticipantInfo participant = iter.next();
							if (judged.getJudgeId() == participant.getParticipantId()) {
								// 代表在已投票列表和未投票列表同时存在的评委，从未投票列表中剔除
								iter.remove();
							}
						}
					}
				}
				
				FcsPmDomainHolder.get().addAttribute("addProjectList", addProjectList);
				FcsPmDomainHolder.get().addAttribute("delProjectList", delProjectList);
				FcsPmDomainHolder.get().addAttribute("addJudges", addJudges);
				FcsPmDomainHolder.get().addAttribute("addparticipants", addparticipants);
				FcsPmDomainHolder.get().addAttribute("noticeJudges", noticeJudges);
				FcsPmDomainHolder.get().addAttribute("noticeparticipants", noticeparticipants);
				FcsPmDomainHolder.get().addAttribute("voteJudges", voteJudges);
				FcsPmDomainHolder.get().addAttribute("delparticipants", delparticipants);
				FcsPmDomainHolder.get().addAttribute("delJudges", delJudges);
				
				return null;
			}
			
		}, null, new AfterProcessInvokeService() {
			
			@SuppressWarnings("unchecked")
			@Override
			public Domain after(Domain domain) {
				// 尊敬的用户张三，您的项目 XXX项目名称XXX 已被安排上会，快去查看详情吧！
				//  查看详情（点击后打开对应的会议详情页）
				// 添加的从order中抓出，用info存储
				List<CouncilProjectInfo> addProjectList = (List<CouncilProjectInfo>) FcsPmDomainHolder
					.get().getAttribute("addProjectList");
				// 删除的从DO中抓出。用do存储
				List<CouncilProjectDO> delProjectList = (List<CouncilProjectDO>) FcsPmDomainHolder
					.get().getAttribute("delProjectList");
				
				// 人员被安排上会，新增加的评委和列席人员
				List<CouncilJudgeInfo> addJudges = (List<CouncilJudgeInfo>) FcsPmDomainHolder.get()
					.getAttribute("addJudges");
				List<CouncilParticipantInfo> addparticipants = (List<CouncilParticipantInfo>) FcsPmDomainHolder
					.get().getAttribute("addparticipants");
				// 上会项目被修改，已存在的人员，本人未投票/不需要投票
				List<CouncilJudgeInfo> noticeJudges = (List<CouncilJudgeInfo>) FcsPmDomainHolder
					.get().getAttribute("noticeJudges");
				List<CouncilParticipantInfo> noticeparticipants = (List<CouncilParticipantInfo>) FcsPmDomainHolder
					.get().getAttribute("noticeparticipants");
				// 上会项目被修改，已存在的评委 本人已投票
				List<CouncilJudgeInfo> voteJudges = (List<CouncilJudgeInfo>) FcsPmDomainHolder
					.get().getAttribute("voteJudges");
				
				// 上会项目被修改，被删除的列席人员
				List<CouncilParticipantDO> delparticipants = (List<CouncilParticipantDO>) FcsPmDomainHolder
					.get().getAttribute("delparticipants");
				// 上会项目被修改，被删除的评委
				List<CouncilJudgeInfo> delJudges = (List<CouncilJudgeInfo>) FcsPmDomainHolder.get()
					.getAttribute("delJudges");
				
				// 人员从info中抓出
				if (order.getCouncilId() <= 0) {
					logger.error("会议id小于0，无法执行站内信提醒工作！");
				}
				// 添加项目站内信
				for (CouncilProjectInfo projectInfo : addProjectList) {
					projectInCouncil(order, projectInfo);
				}
				
				/// 尊敬的用户张三，您的项目 XXX项目名称XXX 被从新增会议中删除，请等待重新安排上会！
				// 删除项目站内信
				for (CouncilProjectDO projectInfo : delProjectList) {
					projectOutCouncil(projectInfo);
				}
				
				//尊敬的用户张三，您有需要参加的会议，会议编号：（2015）第32期  会议时间：2016-3-16 14:20 ，快去查看详情吧！   查看详情
				// 新增加人员上会消息
				for (CouncilJudgeInfo judge : addJudges) {
					//					String name = judge.getJudgeName();
					Long userId = judge.getJudgeId();
					judgeInCouncil(order, userId);
				}
				for (CouncilParticipantInfo participant : addparticipants) {
					//					String name = Participant.getParticipantName();
					Long userId = participant.getParticipantId();
					participantInCouncil(order, userId);
				}
				
				//  尊敬的用户张三， （2015）第32期 会议上会项目有变动，快去查看详情吧！
				// 上会项目被修改 ，不需要投票的列席人员和未投票的评委
				for (CouncilJudgeInfo judge : noticeJudges) {
					//					String name = judge.getJudgeName();
					Long userId = judge.getJudgeId();
					councilChange(order, userId);
				}
				for (CouncilParticipantInfo participant : noticeparticipants) {
					//					String name = Participant.getParticipantName();
					Long userId = participant.getParticipantId();
					councilChange(order, userId);
				}
				
				// 尊敬的用户张三， （2015）第32期 会议上会项目有变动，您需要对新增的项目进行投票，快去投票吧！
				// 上会项目被修改 已投票的评委
				for (CouncilJudgeInfo judge : voteJudges) {
					//					String name = judge.getJudgeName();
					Long userId = judge.getJudgeId();
					councilChangeForVoter(order, userId);
				}
				// 被移除会议 
				// 尊敬的用户张三，您被移出会议，会议编号xxx，会议时间xxx!
				for (CouncilParticipantDO participant : delparticipants) {
					peopleOutCouncil(order, participant.getParticipantId());
				}
				for (CouncilJudgeInfo judge : delJudges) {
					peopleOutCouncil(order, judge.getJudgeId());
				}
				
				return null;
			}
			
			private void peopleOutCouncil(final CouncilOrder order, Long userId) {
				List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
				String subject = "移出会议提醒";
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
					
					SimpleUserInfo[] sendUsers = notifyUserList
						.toArray(new SimpleUserInfo[notifyUserList.size()]);
					//替换审核地址
					StringBuilder sb = new StringBuilder();
					sb.append("尊敬的用户");
					sb.append(sendUsers[0].getUserName());
					sb.append("，您被移出会议，会议编号：");
					sb.append(order.getCouncilCode());
					sb.append("，会议时间：");
					sb.append(DateUtil.simpleFormat(order.getStartTime()));
					sb.append("！");
					String messageContent = sb.toString();
					if (StringUtil.isNotBlank(messageContent)) {
						MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
						messageOrder.setMessageSubject(subject);
						messageOrder.setMessageTitle("人员被移出提醒");
						messageOrder.setMessageContent(messageContent);
						messageOrder.setSendUsers(sendUsers);
						siteMessageService.addMessageInfo(messageOrder);
					}
					
					for (SimpleUserInfo userInfo : notifyUserList) {
						
						//发送邮件
						
						//						String accessToken = getAccessToken(userInfo);
						//						
						//						//站外审核地址
						//						String outSiteUrl = "<a href=\"" + getFcsFaceUrl() + messageUrl
						//											+ "&accessToken=" + accessToken
						//											+ "\" target=\"_blank\">点击处理</a>";
						
						String mailContent = sb.toString();
						//						mailContent += outSiteUrl;
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
			
			private void councilChangeForVoter(final CouncilOrder order, Long userId) {
				List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
				Long councilId = order.getCouncilId();
				// 判断是否是总经理办公会
				CouncilDO councilDO = councilDAO.findById(councilId);
				boolean gmWorking = false;
				if (CouncilTypeEnum.GM_WORKING.code().equals(councilDO.getCouncilTypeCode())) {
					gmWorking = true;
				}
				
				String leftUrl = "/projectMg/meetingMg/councilList.htm";
				String messageUrl = "/projectMg/meetingMg/allCouncilProjectList.htm"
									+ "?councilId=" + councilId;
				if (gmWorking) {
					messageUrl = "/projectMg/meetingMg/showCouncil.htm" + "?councilId=" + councilId;
				}
				String mainUrl = "/projectMg/index.htm";
				
				String forMessage = "<a href='" + messageUrl + "'   mainUrl='" + mainUrl
									+ "'   navUrl='" + leftUrl
									+ "'   class='fnNewWindowOpen' >马上投票</a>";
				if (gmWorking) {
					forMessage = "<a href='" + messageUrl + "'   mainUrl='" + mainUrl
									+ "'   navUrl='" + leftUrl
									+ "'   class='fnNewWindowOpen' >查看详情</a>";
				}
				//				//表单站内地址
				//				String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/meetingMg/allCouncilProjectList.htm&councilId="
				//									+ order.getCouncilId();
				//				String forMessage = "<a href='" + messageUrl + "'>前去投票</a>";
				String subject = "上会提醒";
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
					
					SimpleUserInfo[] sendUsers = notifyUserList
						.toArray(new SimpleUserInfo[notifyUserList.size()]);
					//替换审核地址
					StringBuilder sb = new StringBuilder();
					sb.append("尊敬的用户");
					sb.append(sendUsers[0].getUserName());
					sb.append("，");
					sb.append(order.getCouncilCode());
					if (gmWorking) {
						sb.append("会议上会项目有变动，快去查看详情吧！");
					} else {
						sb.append("会议上会项目有变动，您需要对新增的项目进行投票，快去投票吧！");
					}
					String messageContent = sb.toString();
					messageContent += forMessage;
					if (StringUtil.isNotBlank(messageContent)) {
						MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
						messageOrder.setMessageSubject(subject);
						messageOrder.setMessageTitle("上会修改提醒");
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
						String mailUrl = mainUrl + "#direct=" + messageUrl + "&sidebarUrl="
											+ leftUrl;
						//站外审核地址
						String outSiteUrl = "<a href=\"" + getFaceWebUrl() + mailUrl
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
			
			private void councilChange(final CouncilOrder order, Long userId) {
				List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
				//表单站内地址
				String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/meetingMg/showCouncil.htm&councilId="
									+ order.getCouncilId();
				String forMessage = "<a href='" + messageUrl + "'>查看详情</a>";
				String subject = "上会提醒";
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
					
					SimpleUserInfo[] sendUsers = notifyUserList
						.toArray(new SimpleUserInfo[notifyUserList.size()]);
					//替换审核地址
					StringBuilder sb = new StringBuilder();
					sb.append("尊敬的用户");
					sb.append(sendUsers[0].getUserName());
					sb.append("，");
					sb.append(order.getCouncilCode());
					sb.append("会议上会项目有变动，快去查看详情吧！");
					String messageContent = sb.toString();
					messageContent += forMessage;
					if (StringUtil.isNotBlank(messageContent)) {
						MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
						messageOrder.setMessageSubject(subject);
						messageOrder.setMessageTitle("上会修改提醒");
						messageOrder.setMessageContent(messageContent);
						messageOrder.setSendUsers(sendUsers);
						siteMessageService.addMessageInfo(messageOrder);
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
			
			private void projectOutCouncil(CouncilProjectDO projectInfo) {
				List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
				SimpleUserInfo sendUser = new SimpleUserInfo();
				String subject = "取消上会提醒";
				// 查询业务经理A角
				// 业务经理A角
				ProjectRelatedUserInfo busiManager = projectRelatedUserService
					.getBusiManager(projectInfo.getProjectCode());
				if (busiManager != null) {
					sendUser.setUserId(busiManager.getUserId());
					SysUser sysUser = bpmUserQueryService.findUserByUserId(busiManager.getUserId());
					if (sysUser != null) {
						sendUser.setUserAccount(sysUser.getAccount());
						sendUser.setUserName(sysUser.getFullname());
						sendUser.setEmail(sysUser.getEmail());
						sendUser.setMobile(sysUser.getMobile());
					}
					notifyUserList.add(sendUser);
				}
				
				if (ListUtil.isNotEmpty(notifyUserList)) {
					
					SimpleUserInfo[] sendUsers = notifyUserList
						.toArray(new SimpleUserInfo[notifyUserList.size()]);
					
					//替换审核地址
					StringBuilder sb = new StringBuilder();
					sb.append("尊敬的用户");
					sb.append(sendUser.getUserName());
					sb.append("，您的项目 ");
					sb.append(projectInfo.getProjectName());
					sb.append(" 被从新增会议中删除，请等待重新安排上会！");
					String messageContent = sb.toString();
					if (StringUtil.isNotBlank(messageContent)) {
						MessageOrder ordemessageOrderr = MessageOrder.newSystemMessageOrder();
						ordemessageOrderr.setMessageSubject(subject);
						ordemessageOrderr.setMessageTitle("项目上会删除提醒");
						ordemessageOrderr.setMessageContent(messageContent);
						ordemessageOrderr.setSendUsers(sendUsers);
						siteMessageService.addMessageInfo(ordemessageOrderr);
					}
					
					for (SimpleUserInfo userInfo : notifyUserList) {
						
						//发送邮件
						
						//String accessToken = getAccessToken(userInfo);
						
						//						//站外审核地址
						//						String outSiteUrl = "<a href=\"" + getFcsFaceUrl() + messageUrl
						//											+ "&accessToken=" + accessToken
						//											+ "\" target=\"_blank\">点击处理</a>";
						//						
						String mailContent = sb.toString();
						//						mailContent += outSiteUrl;
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
			
			private void projectInCouncil(final CouncilOrder order, CouncilProjectInfo projectInfo) {
				List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
				String subject = "上会提醒";
				SimpleUserInfo sendUser = new SimpleUserInfo();
				// 查询业务经理A角
				// 业务经理A角
				ProjectRelatedUserInfo busiManager = projectRelatedUserService
					.getBusiManager(projectInfo.getProjectCode());
				if (busiManager != null) {
					sendUser.setUserId(busiManager.getUserId());
					SysUser sysUser = bpmUserQueryService.findUserByUserId(busiManager.getUserId());
					if (sysUser != null) {
						sendUser.setUserAccount(sysUser.getAccount());
						sendUser.setUserName(sysUser.getFullname());
						sendUser.setEmail(sysUser.getEmail());
						sendUser.setMobile(sysUser.getMobile());
					}
					notifyUserList.add(sendUser);
				}
				
				if (ListUtil.isNotEmpty(notifyUserList)) {
					
					SimpleUserInfo[] sendUsers = notifyUserList
						.toArray(new SimpleUserInfo[notifyUserList.size()]);
					
					//表单站内地址
					String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/meetingMg/showCouncil.htm&councilId="
										+ order.getCouncilId();
					String forMessage = "<a href='" + messageUrl + "'>查看详情</a>";
					
					//替换审核地址
					StringBuilder sb = new StringBuilder();
					sb.append("尊敬的用户");
					sb.append(sendUser.getUserName());
					sb.append("，您的项目 ");
					sb.append(projectInfo.getProjectName());
					sb.append(" 已被安排上会，快去查看详情吧！");
					String messageContent = sb.toString();
					messageContent += forMessage;
					if (StringUtil.isNotBlank(messageContent)) {
						MessageOrder ormessageOrderder = MessageOrder.newSystemMessageOrder();
						ormessageOrderder.setMessageSubject(subject);
						ormessageOrderder.setMessageTitle("项目上会提醒");
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
			
			private void participantInCouncil(CouncilOrder order, Long userId) {
				//表单站内地址
				String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/meetingMg/showCouncil.htm&councilId="
									+ order.getCouncilId();
				String forMessage = "<a href='" + messageUrl + "'>查看详情</a>";
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
					
					SimpleUserInfo[] sendUsers = notifyUserList
						.toArray(new SimpleUserInfo[notifyUserList.size()]);
					//替换审核地址
					StringBuilder sb = new StringBuilder();
					sb.append("尊敬的用户");
					sb.append(sendUsers[0].getUserName());
					sb.append("，您有需要参加的会议，会议编号：");
					sb.append(order.getCouncilCode());
					sb.append("  会议时间：");
					sb.append(DateUtil.simpleFormat(order.getStartTime()));
					sb.append(" ，快去查看详情吧！   ");
					String messageContent = sb.toString();
					messageContent += forMessage;
					if (StringUtil.isNotBlank(messageContent)) {
						MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
						messageOrder.setMessageSubject(subject);
						messageOrder.setMessageTitle("人员上会提醒");
						messageOrder.setMessageContent(messageContent);
						messageOrder.setSendUsers(sendUsers);
						siteMessageService.addMessageInfo(messageOrder);
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
			
			private void judgeInCouncil(final CouncilOrder order, Long userId) {
				
				// 站内信
				//				<a href="/projectMg/meetingMg/allCouncilProjectList.htm?councilId=9" 
				//						mainUrl="/projectMg/index.htm" 
				//						navUrl="/projectMg/meetingMg/councilList.htm" 
				//						class="fnNewWindowOpen">
				//				马上投票</a>
				// 邮件 
				//    http://127.0.0.1:8096/projectMg/index.htm#direct=/projectMg/meetingMg/allCouncilProjectList.htm
				//    ?councilId=9&sidebarUrl=/projectMg/meetingMg/councilList.htm
				Long councilId = order.getCouncilId();
				// 判断是否是总经理办公会
				CouncilDO councilDO = councilDAO.findById(councilId);
				boolean gmWorking = false;
				if (CouncilTypeEnum.GM_WORKING.code().equals(councilDO.getCouncilTypeCode())) {
					gmWorking = true;
				}
				String leftUrl = "/projectMg/meetingMg/councilList.htm";
				String messageUrl = "/projectMg/meetingMg/allCouncilProjectList.htm"
									+ "?councilId=" + order.getCouncilId();
				if (gmWorking) {
					messageUrl = "/projectMg/meetingMg/showCouncil.htm" + "?councilId=" + councilId;
				}
				String mainUrl = "/projectMg/index.htm";
				String forMessage = "<a href='" + messageUrl + "'   mainUrl='" + mainUrl
									+ "'   navUrl='" + leftUrl
									+ "'   class='fnNewWindowOpen' >马上投票</a>";
				if (gmWorking) {
					forMessage = "<a href='" + messageUrl + "'   mainUrl='" + mainUrl
									+ "'   navUrl='" + leftUrl
									+ "'   class='fnNewWindowOpen' >查看详情</a>";
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
					
					SimpleUserInfo[] sendUsers = notifyUserList
						.toArray(new SimpleUserInfo[notifyUserList.size()]);
					//替换审核地址
					StringBuilder sb = new StringBuilder();
					sb.append("尊敬的用户");
					sb.append(sendUsers[0].getUserName());
					sb.append("，您有需要参加的会议，会议编号：");
					sb.append(order.getCouncilCode());
					sb.append("  会议时间：");
					sb.append(DateUtil.simpleFormat(order.getStartTime()));
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
						String mailUrl = mainUrl + "#direct=" + messageUrl + "&sidebarUrl="
											+ leftUrl;
						//站外审核地址
						String outSiteUrl = "<a href=\"" + getFaceWebUrl() + mailUrl
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
	public CouncilInfo queryCouncilById(long councilId) {
		CouncilQueryOrder order = new CouncilQueryOrder();
		order.setCouncilId(councilId);
		QueryBaseBatchResult<CouncilInfo> result = this.queryCouncil(order);
		if (result.getTotalCount() > 0) {
			return result.getPageList().get(0);
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<CouncilInfo> queryCouncil(CouncilQueryOrder councilQueryOrder) {
		
		QueryBaseBatchResult<CouncilInfo> batchResult = new QueryBaseBatchResult<CouncilInfo>();
		
		try {
			CouncilDO councilDO = new CouncilDO();
			BeanCopier.staticCopy(councilQueryOrder, councilDO);
			
			long totalCount = 0;
			
			if (councilQueryOrder.getRelatveId() != 0) {
				totalCount = councilDAO.findByConditionWithRelatveIdCount(councilDO,
					councilQueryOrder.getRelatveId(), null, null,
					councilQueryOrder.getCompanyNames(), councilQueryOrder.getStatuss(),
					councilQueryOrder.getProjectName());
			} else {
				totalCount = councilDAO.findByConditionCount(councilDO, null, null,
					councilQueryOrder.getCouncilTypeCodes(), councilQueryOrder.getCompanyNames(),
					councilQueryOrder.getStatuss(), councilQueryOrder.getProjectName());
			}
			
			PageComponent component = new PageComponent(councilQueryOrder, totalCount);
			
			List<CouncilDO> list = Lists.newArrayList();
			if (councilQueryOrder.getRelatveId() != 0) {
				list = councilDAO.findByConditionWithRelatveId(councilDO,
					councilQueryOrder.getRelatveId(), component.getFirstRecord(),
					component.getPageSize(), null, null, councilQueryOrder.getCompanyNames(),
					councilQueryOrder.getStatuss(), councilQueryOrder.getProjectName());
			} else {
				list = councilDAO.findByCondition(councilDO, component.getFirstRecord(),
					component.getPageSize(), null, null, councilQueryOrder.getCouncilTypeCodes(),
					councilQueryOrder.getCompanyNames(), councilQueryOrder.getStatuss(),
					councilQueryOrder.getProjectName());
			}
			
			List<CouncilInfo> pageList = new ArrayList<CouncilInfo>(list.size());
			for (CouncilDO item : list) {
				CouncilInfo info = new CouncilInfo();
				BeanCopier.staticCopy(item, info);
				info.setStatus(CouncilStatusEnum.getByCode(item.getStatus()));
				info.setCouncilTypeCode(CouncilTypeEnum.getByCode(item.getCouncilTypeCode()));
				
				info.setCouncilOnline(BooleanEnum.getByCode(item.getCouncilOnline()));
				
				info.setIfVote(BooleanEnum.getByCode(item.getIfVote()));
				info.setVoteRuleType(VoteRuleTypeEnum.getByCode(item.getVoteRuleType()));
				info.setIndeterminateNum(item.getIndeterminateNum());
				info.setPassNum(item.getPassNum());
				info.setMajorNum(item.getMajorNum());
				info.setLessNum(item.getLessNum());
				info.setPassRate(item.getPassRate());
				info.setIndeterminateRate(item.getIndeterminateRate());
				info.setCompanyName(CompanyNameEnum.getByCode(item.getCompanyName()));
				List<CouncilProjectInfo> projects = councilProjectService.queryByCouncilId(item
					.getCouncilId());
				List<CouncilJudgeInfo> judges = councilJudgeService.queryByCouncilId(item
					.getCouncilId());
				for (CouncilJudgeInfo judgeInfo : judges) {
					if (BooleanEnum.YES == judgeInfo.getCompere()) {
						info.setCompereId(judgeInfo.getJudgeId());
						info.setCompereName(judgeInfo.getJudgeName());
						// 只存在一个主持人
						break;
					}
				}
				
				List<CouncilParticipantInfo> participants = councilParticipantService
					.queryByCouncilId(item.getCouncilId());
				
				CouncilTypeInfo councilTypeInfo = councilTypeService
					.findById(item.getCouncilType());
				
				if (!councilQueryOrder.isQueryFromSummary()) {
					// 项目评审会和风险处置会 查询会议纪要的相关信息
					if (info.getCouncilTypeCode() == CouncilTypeEnum.PROJECT_REVIEW
						|| info.getCouncilTypeCode() == CouncilTypeEnum.RISK_HANDLE) {
						FCouncilSummaryInfo summary = councilSummaryService
							.queryCouncilSummaryByCouncilId(item.getCouncilId());
						if (summary != null) {
							info.setSummary(summary);
							info.setSummaryForm(formService.findByFormId(summary.getFormId()));
						}
					}
				}
				
				info.setProjects(projects);
				info.setJudges(judges);
				info.setParticipants(participants);
				info.setCouncilTypeInfo(councilTypeInfo);
				
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询会议信息失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
		
	}
	
	@Override
	public Long queryCouncilCount(CouncilQueryOrder councilQueryOrder) {
		
		Long totalCount = null;
		
		try {
			CouncilDO councilDO = new CouncilDO();
			BeanCopier.staticCopy(councilQueryOrder, councilDO);
			
			if (councilQueryOrder.getRelatveId() != 0) {
				totalCount = councilDAO.findByConditionWithRelatveIdCount(councilDO,
					councilQueryOrder.getRelatveId(), null, null,
					councilQueryOrder.getCompanyNames(), councilQueryOrder.getStatuss(),
					councilQueryOrder.getProjectName());
			} else {
				totalCount = councilDAO.findByConditionCount(councilDO, null, null,
					councilQueryOrder.getCouncilTypeCodes(), councilQueryOrder.getCompanyNames(),
					councilQueryOrder.getStatuss(), councilQueryOrder.getProjectName());
			}
		} catch (Exception e) {
			logger.error("查询会议信息count失败" + e.getMessage(), e);
		}
		return totalCount;
		
	}
	
	//	/**
	//	 * 添加人员
	//	 * @param councilProjects 项目列表(每个项目都要添加人员)
	//	 * @param councilId taskId
	//	 * @param userType 用户类型
	//	 * @param judgeId 用户id
	//	 */
	//	private void relatedUserInsert(List<CouncilProjectInfo> councilProjects, long councilId,
	//									RelatedUserTypeEnum userType, Long judgeId) {
	//		for (CouncilProjectInfo projectInfo : councilProjects) {
	//			RelatedUserOrder relatedUserOrder = new RelatedUserOrder();
	//			// formId -- -0
	//			relatedUserOrder.setFormId(0L);
	//			// taskId councilId 
	//			relatedUserOrder.setTaskId(councilId);
	//			// projectCode projectCode
	//			relatedUserOrder.setProjectCode(projectInfo.getProjectCode());
	//			// userType  COUNCIL_JUDGE
	//			relatedUserOrder.setUserType(userType);
	//			// exeStatus NOT_TASK
	//			relatedUserOrder.setExeStatus(ExeStatusEnum.NOT_TASK);
	//			// userId judgeInfo.getJudgeId
	//			relatedUserOrder.setUserId(judgeId);
	//			//FcsBaseResult fcsBaseResult = relatedUserService.setRelatedUser(relatedUserOrder);
	//			//			if (fcsBaseResult.isExecuted() && fcsBaseResult.isSuccess()) {
	//			//				//   DONOTHING
	//			//			}
	//		}
	//	}
	
	// 全删然后添加的方式不支持多次添加
	//	/**
	//	 * 添加人员
	//	 * @param councilProjects 项目列表(每个项目都要添加人员)
	//	 * @param councilId taskId
	//	 * @param userType 用户类型
	//	 * @param userId 用户id
	//	 */
	//	private void relatedUserInsert(List<CouncilProjectInfo> councilProjects, long councilId,
	//									ProjectRelatedUserTypeEnum userType, Long userId) {
	//		if (userId <= 0) {
	//			return;
	//		}
	//		List<Long> userIds = new ArrayList<Long>();
	//		userIds.add(userId);
	//		relatedUserInsert(councilProjects, councilId, userType, userIds);
	//	}
	
	/**
	 * 添加人员
	 * @param councilProjects 项目列表(每个项目都要添加人员)
	 * @param councilId taskId
	 * @param userType 用户类型
	 * @param userIds 用户ids
	 */
	private void relatedUserInsert(List<CouncilProjectInfo> councilProjects, long councilId,
									ProjectRelatedUserTypeEnum userType, List<Long> userIds) {
		for (CouncilProjectInfo projectInfo : councilProjects) {
			projectRelatedUserService.setCouncilJudges(projectInfo.getProjectCode(), userIds,
				councilId);
		}
		
	}
	
	//	/**
	//	 * 删除此评委或列席人员所有项目的用户记录
	//	 * @param councilId
	//	 * @param userType
	//	 * @param judgeId
	//	 */
	//	private void relatedUserDelete(long councilId, RelatedUserTypeEnum userType, Long judgeId) {
	//		
	//		// 抓取人员，然后删除、
	//		RelatedUserDO relatedUserDO = new RelatedUserDO();
	//		relatedUserDO.setFormId(0L);
	//		relatedUserDO.setTaskId(councilId);
	//		relatedUserDO.setUserType(userType.code());
	//		relatedUserDO.setUserId(judgeId);
	//		List<RelatedUserDO> users = relatedUserDAO.findByCondition(relatedUserDO, null, null, null,
	//			null, 0L, 1000L);
	//		if (ListUtil.isNotEmpty(users)) {
	//			for (RelatedUserDO userDO : users) {
	//				relatedUserDAO.deleteById(userDO.getRelatedId());
	//			}
	//		}
	//	}
	
	//	/**
	//	 * 删除本项目所有的评委和列席人员
	//	 * @param councilProjects
	//	 * @param councilId
	//	 * @param userType
	//	 * @param judgeId
	//	 */
	//	private void relatedProjectDelete(List<CouncilProjectInfo> councilProjects, long councilId,
	//										RelatedUserTypeEnum userType, Long judgeId) {
	//		
	//		// 抓取人员，然后删除、
	//				for (CouncilProjectInfo projectInfo : councilProjects) {
	//					RelatedUserDO relatedUserDO = new RelatedUserDO();
	//					relatedUserDO.setFormId(0L);
	//					relatedUserDO.setTaskId(councilId);
	//					relatedUserDO.setUserType(userType.code());
	//					relatedUserDO.setProjectCode(projectInfo.getProjectCode());
	//					relatedUserDO.setUserId(judgeId == null ? 0L : judgeId);
	//					List<RelatedUserDO> users = relatedUserDAO.findByCondition(relatedUserDO, null, null,
	//						null, null, 0L, 1000L);
	//					if (ListUtil.isNotEmpty(users)) {
	//						for (RelatedUserDO userDO : users) {
	//							relatedUserDAO.deleteById(userDO.getRelatedId());
	//						}
	//					}
	//				}
	//	}
	
	//	/**
	//	 * 检查用户是否存在，若不存在，添加
	//	 * @param councilProjects
	//	 * @param councilId
	//	 * @param userType
	//	 * @param judgeId
	//	 */
	//	private void relatedUserCheckAndAdd(List<CouncilProjectInfo> councilProjects, long councilId,
	//										ProjectRelatedUserTypeEnum userType, Long judgeId) {
	//		
	//		// 抓取人员，
	//		for (CouncilProjectInfo projectInfo : councilProjects) {
	//			RelatedUserDO relatedUserDO = new RelatedUserDO();
	//			relatedUserDO.setFormId(0L);
	//			relatedUserDO.setTaskId(councilId);
	//			relatedUserDO.setUserType(userType.code());
	//			relatedUserDO.setProjectCode(projectInfo.getProjectCode());
	//			relatedUserDO.setUserId(judgeId == null ? 0L : judgeId);
	//			List<RelatedUserDO> users = relatedUserDAO.findByCondition(relatedUserDO, null, null,
	//				null, null, 0L, 1000L);
	//			if (ListUtil.isEmpty(users)) {
	//				List<CouncilProjectInfo> insertProjects = new ArrayList<CouncilProjectInfo>();
	//				insertProjects.add(projectInfo);
	//				relatedUserInsert(insertProjects, councilId, userType, judgeId);
	//			}
	//		}
	//	}
	
	private void updateJudges(final CouncilOrder order, CouncilDO councilDO,
								List<CouncilJudgeInfo> addJudges, Date now) {
		List<CouncilJudgeInfo> councilJudges = order.getJudges();
		List<CouncilJudgeDO> delList = councilJudgeDAO.findByCouncilId(councilDO.getCouncilId());
		// 用于更新的评委id列表
		List<Long> judgeIds = new ArrayList<Long>();
		for (CouncilJudgeInfo judgeInfo : councilJudges) {
			judgeIds.add(judgeInfo.getJudgeId());
			CouncilJudgeDO councilJudgeDO = new CouncilJudgeDO();
			judgeInfo2DO(order, judgeInfo, councilJudgeDO);
			
			// 判断是否是主持人
			if (judgeInfo.getJudgeId() == order.getCompereId()) {
				councilJudgeDO.setCompere(BooleanEnum.YES.code());
			} else {
				councilJudgeDO.setCompere(BooleanEnum.NO.code());
			}
			
			if (judgeInfo.getId() != 0) {
				councilJudgeDAO.update(councilJudgeDO);
				Iterator<CouncilJudgeDO> iter = delList.iterator();
				while (iter.hasNext()) {
					CouncilJudgeDO curDO = iter.next();
					if (curDO.getId() == judgeInfo.getId()) {
						iter.remove();
					}
				}
				//				relatedUserCheckAndAdd(order.getProjects(), councilDO.getCouncilId(),
				//					ProjectRelatedUserTypeEnum.COUNCIL_JUDGE, judgeInfo.getJudgeId());
			} else {
				councilJudgeDO.setCouncilId(councilDO.getCouncilId());
				councilJudgeDO.setCouncilCode(councilDO.getCouncilCode());
				councilJudgeDO.setRawAddTime(now);
				councilJudgeDAO.insert(councilJudgeDO);
				addJudges.add(judgeInfo);
			}
		}
		// 添加人员，用于判定人员权限
		relatedUserInsert(order.getProjects(), councilDO.getCouncilId(),
			ProjectRelatedUserTypeEnum.COUNCIL_JUDGE, judgeIds);
		
		for (CouncilJudgeDO councilJudgeDO : delList) {
			councilJudgeDAO.deleteById(councilJudgeDO.getId());
			// 抓取人员，然后删除、
			// 添加的时候已经操作了删除 此处不操作
			//			relatedUserDelete(councilDO.getCouncilId(), RelatedUserTypeEnum.COUNCIL_JUDGE,
			//				councilJudgeDO.getJudgeId());
		}
	}
	
	private void updateProjects(final CouncilOrder order, CouncilDO councilDO,
								List<CouncilProjectInfo> addProjectList,
								List<CouncilProjectDO> dels, Date now) {
		List<CouncilProjectInfo> councilProjects = order.getProjects();
		List<CouncilProjectDO> delList = councilProjectDAO
			.findByCouncilId(councilDO.getCouncilId());
		
		for (CouncilProjectInfo projectInfo : councilProjects) {
			CouncilProjectDO councilProjectDO = councilProjectDAO.findById(projectInfo.getId());
			if (councilProjectDO == null) {
				councilProjectDO = new CouncilProjectDO();
			}
			projectInfo2DO(order, projectInfo, councilProjectDO);
			if (projectInfo.getId() != 0) {
				councilProjectDAO.update(councilProjectDO);
				Iterator<CouncilProjectDO> iter = delList.iterator();
				while (iter.hasNext()) {
					CouncilProjectDO curDO = iter.next();
					if (curDO.getId() == projectInfo.getId()) {
						iter.remove();
					}
				}
			} else {
				CouncilApplyDO applyDO = councilApplyDAO.findById(projectInfo.getApplyId());
				// 判定是否已上会
				if (CouncilApplyStatusEnum.IN.code().equals(applyDO.getStatus())) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						"已上会的记录无法再次上会！");
				}
				councilProjectDO.setCouncilId(councilDO.getCouncilId());
				councilProjectDO.setCouncilCode(councilDO.getCouncilCode());
				councilProjectDO.setRawAddTime(now);
				councilProjectDAO.insert(councilProjectDO);
				councilApplySuccess(projectInfo.getApplyId());
				
				// 设置所有添加的councilPorject
				addProjectList.add(projectInfo);
			}
		}
		
		for (CouncilProjectDO projectDO : delList) {
			councilProjectDAO.deleteById(projectDO.getId());
			// 项目删除后评委记录也删除
			councilProjectVoteDAO.deleteByCouncilIdProjectCode(projectDO.getCouncilId(),
				projectDO.getProjectCode());
			councilReadySuccess(projectDO.getApplyId());
			
			// 设置所有需要删除的councilPorject
			dels.add(projectDO);
			// 项目删除后清除人员信息
			List<CouncilProjectInfo> projectInfos = new ArrayList<CouncilProjectInfo>();
			CouncilProjectInfo projectInfo = new CouncilProjectInfo();
			MiscUtil.copyPoObject(projectInfo, projectDO);
			projectInfos.add(projectInfo);
			
			// 清除项目所有的评委和列席人员
			// relatedUserDAO 更定为 projectrelatedUserDAO  juegeIds传入空代表全删
			relatedUserInsert(order.getProjects(), councilDO.getCouncilId(),
				ProjectRelatedUserTypeEnum.COUNCIL_JUDGE, null);
			
			//			relatedProjectDelete(projectInfos, councilDO.getCouncilId(),
			//				RelatedUserTypeEnum.COUNCIL_JUDGE, null);
			//relatedProjectDelete(projectInfos, councilDO.getCouncilId(),
			//	RelatedUserTypeEnum.COUNCIL_PARTICIPANT, null);
		}
	}
	
	private void updateParticipants(final CouncilOrder order, CouncilDO councilDO,
									List<CouncilParticipantInfo> addparticipants,
									List<CouncilParticipantInfo> noticeparticipants,
									List<CouncilParticipantDO> delparticipants, Date now) {
		List<CouncilParticipantInfo> participants = order.getParticipants();
		if (ListUtil.isEmpty(participants)) {
			participants = new ArrayList<CouncilParticipantInfo>();
		}
		List<CouncilParticipantDO> delList = councilParticipantDAO.findByCouncilId(councilDO
			.getCouncilId());
		
		for (CouncilParticipantInfo participantInfo : participants) {
			// 先判定是否是已存在的列席人员
			boolean isExist = false;
			for (CouncilParticipantDO infoDO : delList) {
				if (participantInfo.getParticipantId() == infoDO.getParticipantId()) {
					isExist = true;
					participantInfo.setId(infoDO.getId());
					break;
				}
			}
			if (isExist) {
				// 已存在的列席人员
				noticeparticipants.add(participantInfo);
				//relatedUserCheckAndAdd(order.getProjects(), councilDO.getCouncilId(),
				//	RelatedUserTypeEnum.COUNCIL_PARTICIPANT, participantInfo.getParticipantId());
			} else {
				// 新增加的列席人员
				addparticipants.add(participantInfo);
				// 添加人员，用于判定人员权限
				//relatedUserInsert(order.getProjects(), councilDO.getCouncilId(),
				//	RelatedUserTypeEnum.COUNCIL_PARTICIPANT, participantInfo.getParticipantId());
			}
			CouncilParticipantDO councilParticipantDO = new CouncilParticipantDO();
			participantInfo2DO(order, participantInfo, councilParticipantDO);
			if (participantInfo.getId() != 0) {
				councilParticipantDAO.update(councilParticipantDO);
				Iterator<CouncilParticipantDO> iter = delList.iterator();
				while (iter.hasNext()) {
					CouncilParticipantDO curDO = iter.next();
					if (curDO.getId() == participantInfo.getId()) {
						iter.remove();
					}
				}
			} else {
				councilParticipantDO.setCouncilId(councilDO.getCouncilId());
				councilParticipantDO.setCouncilCode(councilDO.getCouncilCode());
				councilParticipantDO.setRawAddTime(now);
				councilParticipantDAO.insert(councilParticipantDO);
			}
		}
		
		for (CouncilParticipantDO participantDO : delList) {
			delparticipants.add(participantDO);
			councilParticipantDAO.deleteById(participantDO.getId());
			// 抓取人员，然后删除、
			//relatedUserDelete(councilDO.getCouncilId(), RelatedUserTypeEnum.COUNCIL_PARTICIPANT,
			//	participantDO.getParticipantId());
		}
		
	}
	
	/**
	 * 设置为已经上会中
	 * 
	 * @param applyId
	 * @return
	 */
	private FcsBaseResult councilApplySuccess(long applyId) {
		FcsBaseResult result = new FcsBaseResult();
		CouncilApplyDO councilApplyDO = councilApplyDAO.findById(applyId);
		// 添加暂缓项目判断
		if (CouncilApplyStatusEnum.PAUSE.code().equals(councilApplyDO.getStatus())) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM,
				"暂缓中的项目无法被操作！");
		}
		councilApplyDO.setStatus(CouncilApplyStatusEnum.IN.code());
		councilApplyDAO.update(councilApplyDO);
		result.setSuccess(true);
		return result;
	}
	
	/**
	 * 设置为已经等待上会
	 * 
	 * @param applyId
	 * @return
	 */
	private FcsBaseResult councilReadySuccess(long applyId) {
		FcsBaseResult result = new FcsBaseResult();
		CouncilApplyDO councilApplyDO = councilApplyDAO.findById(applyId);
		// 添加暂缓项目判断
		if (CouncilApplyStatusEnum.PAUSE.code().equals(councilApplyDO.getStatus())) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM,
				"暂缓中的项目无法被操作！");
		}
		councilApplyDO.setStatus(CouncilApplyStatusEnum.WAIT.code());
		councilApplyDAO.update(councilApplyDO);
		result.setSuccess(true);
		return result;
	}
	
	private void order2DO(final CouncilOrder order, CouncilDO councilDO, String councilCode) {
		BeanCopier.staticCopy(order, councilDO);
		CouncilTypeDO councilType = councilTypeDAO.findById(councilDO.getCouncilType());
		
		if (councilType != null) {
			if (StringUtil.isNotBlank(order.getCouncilTypeCode())
				&& StringUtil.notEquals(order.getCouncilTypeCode(), councilType.getTypeCode())) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM,
					"会议类型和会议名称不匹配！");
			}
			councilDO.setCouncilTypeName(councilType.getTypeName());
			councilDO.setCouncilTypeCode(councilType.getTypeCode());
			councilDO.setIfVote(councilType.getIfVote());
			councilDO.setVoteRuleType(councilType.getVoteRuleType());
			councilDO.setIndeterminateNum(councilType.getIndeterminateNum());
			councilDO.setPassNum(councilType.getPassNum());
			councilDO.setMajorNum(councilType.getMajorNum());
			councilDO.setLessNum(councilType.getLessNum());
			councilDO.setPassRate(councilType.getPassRate());
			councilDO.setIndeterminateRate(councilType.getIndeterminateRate());
		}
		councilDO.setCouncilSubject(councilDO.getCouncilTypeName() + "-" + councilCode);
		
		if (order.getCouncilOnline() != null) {
			councilDO.setCouncilOnline(order.getCouncilOnline().code());
			if (BooleanEnum.YES == order.getCouncilOnline()) {
				councilDO.setCouncilPlace(null);
			}
		} else {
			// 默认按照no处理
			councilDO.setCouncilOnline(BooleanEnum.NO.code());
		}
	}
	
	private void participantInfo2DO(CouncilOrder council, CouncilParticipantInfo participantInfo,
									CouncilParticipantDO councilParticipantDO) {
		BeanCopier.staticCopy(participantInfo, councilParticipantDO);
		councilParticipantDO.setCouncilId(council.getCouncilId());
		councilParticipantDO.setCouncilCode(council.getCouncilCode());
	}
	
	private void projectInfo2DO(CouncilOrder council, CouncilProjectInfo projectInfo,
								CouncilProjectDO councilProjectDO) {
		BeanCopier.staticCopy(projectInfo, councilProjectDO);
		councilProjectDO.setCouncilId(council.getCouncilId());
		councilProjectDO.setCouncilCode(council.getCouncilCode());
		councilProjectDO.setJudgesCount(council.getJudges().size());
	}
	
	private void judgeInfo2DO(CouncilOrder council, CouncilJudgeInfo judgeInfo,
								CouncilJudgeDO councilJudgeDO) {
		BeanCopier.staticCopy(judgeInfo, councilJudgeDO);
		councilJudgeDO.setCouncilId(council.getCouncilId());
		councilJudgeDO.setCouncilCode(council.getCouncilCode());
	}
	
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
	
	@Override
	public FcsBaseResult beginCouncil(long councilId) {
		FcsBaseResult result = new FcsBaseResult();
		CouncilDO councilDO = councilDAO.findById(councilId);
		councilDO.setStatus(CouncilStatusEnum.IN_PROGRESS.code());
		councilDAO.update(councilDO);
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public Date getSysDate() {
		return getSysdate();
	}
	
	@Override
	public FcsBaseResult endCouncil(long councilId) {
		final CouncilOrder order = new CouncilOrder();
		order.setCouncilId(councilId);
		
		return commonProcess(order, "结束会议", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				order.check();
				Long councilId = order.getCouncilId();
				CouncilDO councilDO = councilDAO.findById(councilId);
				
				List<CouncilProjectDO> list = councilProjectDAO.findByCouncilId(councilId);
				
				// 总经理办公会不需要投票
				if (!CouncilTypeEnum.GM_WORKING.code().equals(councilDO.getCouncilTypeCode())) {
					int cunt = 0;
					for (CouncilProjectDO info : list) {
						String str = setProjectVoteResult(info);
						if (StringUtil.isNotBlank(str)) {
							//							result.setSuccess(false);
							//							result.setMessage(str);
							//							return result;
							throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE,
								str);
						}
						councilProjectDAO.update(info);
						// 添加判断，若风控委秘书执行了本次不议，不做结果计算
						if (StringUtil.notEquals(BooleanEnum.YES.code(),
							info.getRiskSecretaryQuit())) {
							if (ProjectVoteResultEnum.NOT_BEGIN.code().equals(
								info.getProjectVoteResult())
								|| ProjectVoteResultEnum.IN_VOTE.code().equals(
									info.getProjectVoteResult())) {
								cunt++;
							} else if (ProjectVoteResultEnum.END_QUIT.code().equals(
								info.getProjectVoteResult())) {
								// 添加判断，投票结果本次不议也需要回到待上会列表
								// 20160921 添加判断，本次不议的会议有按钮操作重新上会或者进入尽职调查修改阶段,不再直接执行回到待上会列表操作
								//								councilReadySuccess(info.getApplyId());
							}
							
						}
						// 添加判断 若此项目本次不议，需要回到待上会列表
						if (StringUtil.equals(BooleanEnum.YES.code(), info.getRiskSecretaryQuit())) {
							councilReadySuccess(info.getApplyId());
						}
					}
					if (cunt > 0) {
						//						result.setSuccess(false);
						//						result.setMessage("部分项目投票未完成，不能结束会议!");
						//						return result;
						
						throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE,
							"部分项目投票未完成，不能结束会议!");
					}
				}
				councilDO.setStatus(CouncilStatusEnum.BREAK_UP.code());
				councilDO.setEndTime(FcsPmDomainHolder.get().getSysDate());
				councilDAO.update(councilDO);
				
				return null;
			}
			
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				Long councilId = order.getCouncilId();
				CouncilDO councilDO = councilDAO.findById(councilId);
				
				List<CouncilProjectDO> list = councilProjectDAO.findByCouncilId(councilId);
				for (CouncilProjectDO councilProjectDO : list) {
					endVoteNotice(councilProjectDO);
				}
				
				/// 任务2.向风控委秘书发送站内信
				// 尊敬的用户张三， （2015）第32期 会议已结束，快去填写会议纪要吧！
				// 获取风控委秘书
				String roleName = sysParameterService
					.getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_SECRETARY_ROLE_NAME.code());
				
				List<SysUser> users = bpmUserQueryService.findUserByRoleAlias(roleName);
				for (SysUser user : users) {
					endCouncilNotice(councilDO, user);
				}
				return null;
			}
			
			private void endVoteNotice(CouncilProjectDO councilProjectDO) {
				//抓去项目名称
				String projectName = councilProjectDO.getProjectName();
				String subject = "上会完成提醒";
				//尊敬的用户张三，您的项目 XXX项目名称XXX 已上会完成，投票通过，快去查看详情吧！
				// 发送给业务经理 A角
				
				List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
				SimpleUserInfo sendUser = new SimpleUserInfo();
				// 查询业务经理A角
				// 业务经理A角
				ProjectRelatedUserInfo busiManager = projectRelatedUserService
					.getBusiManager(councilProjectDO.getProjectCode());
				if (busiManager != null) {
					sendUser.setUserId(busiManager.getUserId());
					SysUser sysUser = bpmUserQueryService.findUserByUserId(busiManager.getUserId());
					if (sysUser != null) {
						sendUser.setUserAccount(sysUser.getAccount());
						sendUser.setUserName(sysUser.getFullname());
						sendUser.setEmail(sysUser.getEmail());
						sendUser.setMobile(sysUser.getMobile());
					}
					notifyUserList.add(sendUser);
				}
				
				if (ListUtil.isNotEmpty(notifyUserList)) {
					
					SimpleUserInfo[] sendUsers = notifyUserList
						.toArray(new SimpleUserInfo[notifyUserList.size()]);
					
					//表单站内地址
					String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/meetingMg/showCouncil.htm&councilId="
										+ order.getCouncilId();
					String forMessage = "<a href='" + messageUrl + "'>查看详情</a>";
					
					//替换审核地址
					StringBuilder sb = new StringBuilder();
					sb.append("尊敬的用户");
					sb.append(sendUser.getUserName());
					sb.append("，您的项目 ");
					sb.append(projectName);
					sb.append(" 已上会完成，");
					if (StringUtil.equals(OneVoteResultEnum.NO_PASS.code(),
						councilProjectDO.getOneVoteDown())) {
						sb.append("董事长一票否决");
					} else if (StringUtil.equals(OneVoteResultEnum.RE_COUNCIL.code(),
						councilProjectDO.getOneVoteDown())) {
						sb.append("董事长审批结果：复议");
					} else if (StringUtil.equals(BooleanEnum.YES.code(),
						councilProjectDO.getRiskSecretaryQuit())) {
						sb.append("风控委秘书执行了本次不议");
					} else {
						String voteResult = ProjectVoteResultEnum.getByCode(
							councilProjectDO.getProjectVoteResult()).getMessage();
						sb.append("投票");
						sb.append(voteResult);
					}
					sb.append("，快去查看详情吧！");
					String messageContent = sb.toString();
					messageContent += forMessage;
					if (StringUtil.isNotBlank(messageContent)) {
						MessageOrder ormessageOrderder = MessageOrder.newSystemMessageOrder();
						ormessageOrderder.setMessageSubject(subject);
						ormessageOrderder.setMessageTitle("项目上会完成提醒");
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
			
			private void endCouncilNotice(CouncilDO councilDO, SysUser sendUser) {
				//表单站内地址
				String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/meetingMg/summary/form.htm&councilId="
									+ councilDO.getCouncilId();
				String forMessage = "<a href='" + messageUrl + "'>填写会议记要</a>";
				String subject = "会议结束提醒";
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
					sb.append("尊敬的用户");
					sb.append(sendUsers[0].getUserName());
					sb.append("， ");
					sb.append(councilDO.getCouncilCode());
					sb.append(" 会议已结束，快去填写会议纪要吧！");
					String messageContent = sb.toString();
					messageContent += forMessage;
					if (StringUtil.isNotBlank(messageContent)) {
						MessageOrder ormessageOrderder = MessageOrder.newSystemMessageOrder();
						ormessageOrderder.setMessageSubject(subject);
						ormessageOrderder.setMessageTitle("填写会议记要提醒");
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
	public FcsBaseResult delCouncilByCouncilId(Long councilId) {
		CouncilDelOrder order = new CouncilDelOrder();
		List<Long> councilIds = new ArrayList<Long>();
		councilIds.add(councilId);
		order.setCouncilIds(councilIds);
		return delCouncil(order);
	}
	
	@Override
	public FcsBaseResult delCouncil(final CouncilDelOrder order) {
		return commonProcess(order, "删除会议", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				if (order == null || ListUtil.isEmpty(order.getCouncilIds())) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						"要删除的会议不能为空！");
				}
				// 循环删除会议
				for (Long id : order.getCouncilIds()) {
					// 忽略错误数据
					if (id <= 0) {
						continue;
					}
					// 若会议并非未开始，不允许删除
					CouncilDO councilDO = councilDAO.findById(id);
					if (StringUtil.notEquals(CouncilStatusEnum.NOT_BEGIN.code(),
						councilDO.getStatus())) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"要删除的会议必须处于未开始状态！");
					}
					// 删除评委
					councilJudgeDAO.deleteByCouncilId(id);
					// 将项目设置为等待上会
					List<CouncilProjectDO> councilProjects = councilProjectDAO.findByCouncilId(id);
					for (CouncilProjectDO councilProject : councilProjects) {
						councilReadySuccess(councilProject.getApplyId());
					}
					// 删除项目
					councilProjectDAO.deleteByCouncilId(id);
					// 删除列席人员
					councilParticipantDAO.deleteByCouncilId(id);
					// 删除投票
					councilProjectVoteDAO.deleteByCouncilId(id);
					// 删除会议本体
					councilDAO.deleteById(id);
					
				}
				return null;
			}
			
		}, null, null);
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
	
	@Override
	public FcsBaseResult endOldCouncil(final CouncilOrder order) {
		
		return commonProcess(order, "结束会议[老数据]", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				order.check();
				Long councilId = order.getCouncilId();
				CouncilDO councilDO = councilDAO.findById(councilId);
				// 判定是否是老数据
				boolean oldCouncil = false;
				oldCouncil = checkOldCouncil(councilDO);
				if (!oldCouncil) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS,
						"此会议并非2016年30期以前的老会议，不允许直接结束会议！");
				}
				
				List<CouncilProjectDO> list = councilProjectDAO.findByCouncilId(councilId);
				
				// 拆解投票信息
				String voteMessage = order.getCouncilVoteMessage();
				int judgeCount = 0;
				int passCount = 0;
				if (StringUtil.isNotEmpty(voteMessage)) {
					try {
						String[] votes = voteMessage.split("\\/");
						if (StringUtil.isNotEmpty(votes[0])) {
							passCount = Integer.valueOf(votes[0]);
						}
						if (StringUtil.isNotEmpty(votes[1])) {
							judgeCount = Integer.valueOf(votes[1]);
						}
					} catch (Exception e) {
						//						拆解失败donothing
					}
					
				}
				
				// 总经理办公会不需要投票
				if (!CouncilTypeEnum.GM_WORKING.code().equals(councilDO.getCouncilTypeCode())) {
					for (CouncilProjectDO info : list) {
						// 设定投票结果为通过
						info.setProjectVoteResult(ProjectVoteResultEnum.END_PASS.code());
						info.setPassCount(passCount);
						info.setJudgesCount(judgeCount);
						councilProjectDAO.update(info);
					}
				}
				councilDO.setStatus(CouncilStatusEnum.BREAK_UP.code());
				councilDAO.update(councilDO);
				
				return null;
				
			}
			
			private boolean checkOldCouncil(CouncilDO councilDO) {
				
				// 20170104 所有的风险处置会都可以直接结束
				String councilTypeCode = councilDO.getCouncilTypeCode();
				if (StringUtil.equals(councilTypeCode, CouncilTypeEnum.RISK_HANDLE.code())) {
					//return true;
				}
				
				String councilCode = councilDO.getCouncilCode();
				//				先处理通过的 会议 编号格式 (2016)第10-1期 
				//				(2016)第10期 
				try {
					String yearStr = StringUtil.trim((councilCode.split("\\(")[1]).split("\\)")[0]);
					String dayStr = StringUtil.trim((councilCode.split("第")[1]).split("期")[0]
						.split("-")[0]);
					long year = Long.valueOf(yearStr);
					long day = Long.valueOf(dayStr);
					long limitYeat = 2016;
					long limitDay = 30;
					
					// 20170104 所有的风险处置会都可以在2017年前结束会议直接结束
					councilTypeCode = councilDO.getCouncilTypeCode();
					if (StringUtil.equals(councilTypeCode, CouncilTypeEnum.RISK_HANDLE.code())) {
						//return true;
						if (2017 > year) {
							return true;
						}
					}
					
					if (limitYeat > year) {
						return true;
					} else if (limitYeat == year) {
						if (limitDay >= day) {
							return true;
						} else {
							return false;
						}
					} else if (limitYeat < year) {
						return false;
					}
					
				} catch (Exception e) {
					// 处理出错，默认为老数据
					logger.error(councilCode + "处理出错，默认为老数据!");
					return true;
				}
				return false;
			}
			
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				return null;
			}
			
		});
		
	}
	
}
