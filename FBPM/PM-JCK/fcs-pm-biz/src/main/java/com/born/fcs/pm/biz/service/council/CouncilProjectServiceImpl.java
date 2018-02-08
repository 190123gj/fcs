package com.born.fcs.pm.biz.service.council;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.CouncilApplyDO;
import com.born.fcs.pm.dal.dataobject.CouncilDO;
import com.born.fcs.pm.dal.dataobject.CouncilProjectDO;
import com.born.fcs.pm.dal.dataobject.FormDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dataobject.CouncilVoteProjectDO;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilCompereMessageEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.VoteResultEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.council.CouncilProjectInfo;
import com.born.fcs.pm.ws.info.council.CouncilProjectVoteResultInfo;
import com.born.fcs.pm.ws.info.finvestigation.InvestigationInfo;
import com.born.fcs.pm.ws.order.council.CouncilProjectOrder;
import com.born.fcs.pm.ws.order.council.CouncilVoteProjectQueryOrder;
import com.born.fcs.pm.ws.order.finvestigation.InvestigationQueryOrder;
import com.born.fcs.pm.ws.order.finvestigation.base.UpdateInvestigationCouncilBackOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.council.CouncilProjectService;
import com.born.fcs.pm.ws.service.council.CouncilProjectVoteService;
import com.born.fcs.pm.ws.service.investigation.InvestigationService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("councilProjectService")
public class CouncilProjectServiceImpl extends BaseAutowiredDomainService implements
																			CouncilProjectService {
	
	@Autowired
	CouncilProjectVoteService councilProjectVoteService;
	
	@Autowired
	private InvestigationService investigationService;
	
	@Override
	public List<CouncilProjectInfo> queryByCouncilId(long councilId) {
		List<CouncilProjectInfo> rtList = new ArrayList<CouncilProjectInfo>();
		List<CouncilProjectDO> list = councilProjectDAO.findByCouncilId(councilId);
		for (CouncilProjectDO item : list) {
			CouncilProjectInfo info = new CouncilProjectInfo();
			BeanCopier.staticCopy(item, info);
			info.setProjectVoteResult(ProjectVoteResultEnum.getByCode(item.getProjectVoteResult()));
			info.setOneVoteDown(BooleanEnum.getByCode(item.getOneVoteDown()));
			info.setRiskSecretaryQuit(BooleanEnum.getByCode(item.getRiskSecretaryQuit()));
			info.setCompereMessage(ProjectCouncilCompereMessageEnum.getByCode(item
				.getCompereMessage()));
			rtList.add(info);
		}
		return rtList;
	}
	
	@Override
	public QueryBaseBatchResult<CouncilProjectVoteResultInfo> queryProjectVoteResult(	CouncilVoteProjectQueryOrder order) {
		QueryBaseBatchResult<CouncilProjectVoteResultInfo> batchResult = new QueryBaseBatchResult<CouncilProjectVoteResultInfo>();
		
		try {
			CouncilVoteProjectDO councilVoteProjectDO = new CouncilVoteProjectDO();
			
			BeanCopier.staticCopy(order, councilVoteProjectDO);
			if (order.getProjectVoteResult() != null) {
				councilVoteProjectDO.setProjectVoteResult(order.getProjectVoteResult().code());
			}
			long totalCount = extraDAO.searchCouncilVoteProjectCount(councilVoteProjectDO);
			PageComponent component = new PageComponent(order, totalCount);
			
			List<CouncilVoteProjectDO> list = extraDAO.searchCouncilVoteProject(
				councilVoteProjectDO, component.getFirstRecord(), component.getPageSize(), null,
				null);
			
			List<CouncilProjectVoteResultInfo> pageList = new ArrayList<CouncilProjectVoteResultInfo>(
				list.size());
			for (CouncilVoteProjectDO item : list) {
				CouncilProjectVoteResultInfo info = new CouncilProjectVoteResultInfo();
				BeanCopier.staticCopy(item, info);
				info.setCompereMessage(ProjectCouncilCompereMessageEnum.getByCode(item
					.getCompereMessage()));
				setVoteInfo(item, info);
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询各项目投票记录失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public List<CouncilProjectVoteResultInfo> queryProjectVoteResultByCouncilId(long councilId) {
		
		List<CouncilProjectVoteResultInfo> rtList = new ArrayList<CouncilProjectVoteResultInfo>();
		
		CouncilVoteProjectDO councilVoteProjectDO = new CouncilVoteProjectDO();
		
		councilVoteProjectDO.setCouncilId(councilId);
		
		List<CouncilVoteProjectDO> list = extraDAO.searchCouncilVoteProject(councilVoteProjectDO,
			0, 0, null, null);
		
		for (CouncilVoteProjectDO item : list) {
			CouncilProjectVoteResultInfo info = new CouncilProjectVoteResultInfo();
			BeanCopier.staticCopy(item, info);
			setVoteInfo(item, info);
			rtList.add(info);
		}
		return rtList;
	}
	
	private void setVoteInfo(CouncilVoteProjectDO councilVoteProjectDO,
								CouncilProjectVoteResultInfo info) {
		
		int passCnt = councilVoteProjectDO.getPassCount();
		int noPassCnt = councilVoteProjectDO.getNotpassCount();
		int quitCnt = councilVoteProjectDO.getQuitCount();
		int total = councilVoteProjectDO.getJudgesCount();
		// 统计票数
		info.setVoteRatio((passCnt + noPassCnt + quitCnt) + "/" + total);
		info.setProjectVoteResult(ProjectVoteResultEnum.getByCode(councilVoteProjectDO
			.getProjectVoteResult()));
		
		info.setTimeUnit(TimeUnitEnum.getByCode(councilVoteProjectDO.getTimeUnit()));
		info.setVoteResult(VoteResultEnum.getByCode(councilVoteProjectDO.getVoteResult()));
		info.setVoteStatus(BooleanEnum.getByCode(councilVoteProjectDO.getVoteStatus()));
		info.setOneVoteDown(BooleanEnum.getByCode(councilVoteProjectDO.getOneVoteDown()));
		info.setRiskSecretaryQuit(BooleanEnum.getByCode(councilVoteProjectDO.getRiskSecretaryQuit()));
	}
	
	@Override
	public CouncilProjectVoteResultInfo query(long councilId, String projectCode) {
		CouncilVoteProjectQueryOrder order = new CouncilVoteProjectQueryOrder();
		order.setCouncilId(councilId);
		order.setProjectCode(projectCode);
		QueryBaseBatchResult<CouncilProjectVoteResultInfo> batchResult = this
			.queryProjectVoteResult(order);
		
		if (batchResult.getPageList().size() > 0) {
			return batchResult.getPageList().get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public FcsBaseResult oneVoteDown(final CouncilProjectOrder order) {
		
		return commonProcess(order, "一票否决", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				order.check();
				CouncilProjectDO councilProjectDO = null;
				if (order.getId() > 0) {
					councilProjectDO = councilProjectDAO.findById(order.getId());
				} else {
					councilProjectDO = councilProjectDAO.findByCouncilProjectCodeAndCouncilId(
						order.getProjectCode(), order.getCouncilId());
				}
				if (councilProjectDO == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未找到会议项目");
				}
				councilProjectDO.setOneVoteDown(BooleanEnum.YES.getCode());
				councilProjectDO.setOneVoteDownMark(order.getOneVoteDownMark());
				// 董事长一票否决并不将会议结果改变
				//councilProjectDO.setProjectVoteResult(ProjectVoteResultEnum.END_QUIT.code());
				councilProjectDAO.update(councilProjectDO);
				return null;
			}
		}, null, null);
	}
	
	@Override
	public CouncilProjectInfo getLastInfoByApplyId(Long applyId) {
		CouncilProjectInfo info = new CouncilProjectInfo();
		// 需要母公司会的时候，给母公司会的结果，尚未出现： 未开始
		if (applyId == null || applyId <= 0) {
			return info;
		}
		
		// 子会结果
		CouncilProjectDO projectDO = null;
		List<CouncilProjectDO> councils = councilProjectDAO.findByApplyId(applyId);
		if (ListUtil.isNotEmpty(councils)) {
			// 20160929 会议退回之后重新上会，出现的同一个applyid申请两笔councilProjects的情况
			// 若全都是本次不议的会议，取第一个，若并不全是，剔除所有本次不议的会议，留下的选最新的一个
			projectDO = councils.get(0);
			for (CouncilProjectDO cpDO : councils) {
				if (!ProjectVoteResultEnum.END_QUIT.code().equals(cpDO.getProjectVoteResult())) {
					projectDO = cpDO;
				}
			}
		}
		if (projectDO == null) {
			info.setProjectVoteResult(ProjectVoteResultEnum.NOT_BEGIN);
			return info;
		}
		// 子会通过了-- 未开始  子会未通过--未通过
		if (ProjectVoteResultEnum.END_PASS.code().equals(projectDO.getProjectVoteResult())) {
			// 通过之后判断母会
			// 抓取子会
			CouncilApplyDO childDO = councilApplyDAO.findById(applyId);
			
			// 判断是否需要母会
			if (BooleanEnum.YES.code().equals(childDO.getMotherCompanyApply())) {
				
				//母公司为总经理办公会时返回结果，否则结果返回未开始，结果从其他结果入口写入
				if (CouncilTypeEnum.GM_WORKING.code().equals(childDO.getMotherCouncilCode())) {
					// 抓取母会
					CouncilApplyDO newApplyDO = councilApplyDAO.findByChildId(applyId);
					if (newApplyDO == null) {
						info.setProjectVoteResult(ProjectVoteResultEnum.NOT_BEGIN);
						return info;
					}
					CouncilProjectDO projectDO2 = null;
					List<CouncilProjectDO> councils2 = councilProjectDAO.findByApplyId(newApplyDO
						.getApplyId());
					if (ListUtil.isNotEmpty(councils2)) {
						// 20160929 会议退回之后重新上会，出现的同一个applyid申请两笔councilProjects的情况
						// 若全都是本次不议的会议，取第一个，若并不全是，剔除所有本次不议的会议，留下的选最新的一个
						projectDO2 = councils2.get(0);
						for (CouncilProjectDO cpDO : councils2) {
							if (!ProjectVoteResultEnum.END_QUIT.code().equals(
								cpDO.getProjectVoteResult())) {
								projectDO2 = cpDO;
							}
						}
					}
					if (projectDO2 == null) {
						info.setProjectVoteResult(ProjectVoteResultEnum.NOT_BEGIN);
						return info;
					}
					convertDO2Info(info, projectDO2);
					return info;
				} else {
					info.setProjectVoteResult(ProjectVoteResultEnum.NOT_BEGIN);
					return info;
				}
				
			} else {
				// 不需要就返回子会结果
				convertDO2Info(info, projectDO);
				return info;
			}
			//
		} else {
			//  非通过，结果=自身
			convertDO2Info(info, projectDO);
			return info;
		}
		
	}
	
	private void convertDO2Info(CouncilProjectInfo info, CouncilProjectDO projectDO) {
		BeanCopier.staticCopy(projectDO, info);
		info.setProjectVoteResult(ProjectVoteResultEnum.getByCode(projectDO.getProjectVoteResult()));
		info.setOneVoteDown(BooleanEnum.getByCode(projectDO.getOneVoteDown()));
		info.setRiskSecretaryQuit(BooleanEnum.getByCode(projectDO.getRiskSecretaryQuit()));
	}
	
	@Override
	public FcsBaseResult saveCompereAfterMessage(final CouncilProjectOrder order) {
		
		return commonProcess(order, "保存主持人本次不议后续信息", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				// 属性为空
				if (order == null || order.getCouncilId() < 0 || order.getId() < 0
					|| order.getCompereMessage() == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM,
						"请求参数不完整！");
				}
				// 无数据
				CouncilProjectDO projectDO = councilProjectDAO.findById(order.getId());
				if (projectDO == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未查询到数据！");
				}
				// 并非主持人本次不议
				if (!ProjectVoteResultEnum.END_QUIT.code().equals(projectDO.getProjectVoteResult())) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DATABASE_EXCEPTION,
						"改数据并非本次不议的数据！");
				}
				// 已存在后续操作，不可重复操作
				if (StringUtil.isNotBlank(projectDO.getCompereMessage())) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DATABASE_EXCEPTION,
						"改数据已执行过后续操作，无法继续操作！");
				}
				// 退回 
				if (ProjectCouncilCompereMessageEnum.INVESTIGATING_PHASES == order
					.getCompereMessage()) {
					//  退回操作  风险处置会 退回时候不做任何操作 只保存结果
					CouncilDO councilDO = councilDAO.findById(order.getCouncilId());
					
					if (CouncilTypeEnum.RISK_HANDLE.code().equals(councilDO.getCouncilTypeCode())) {
						// donothing
					} else {
						
						String projectCode = projectDO.getProjectCode();
						if (ProjectUtil.isFinancial(projectCode)) {
							// 理财还在改造，此处等待
							//理财项目上会被退回以后回到理财申请列表，可重新编辑立项申请单 from chanpin
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.DATABASE_EXCEPTION, "理财还在改造中，暂无此流程！");
							
						} else {
							
							// 1.1  尽职调查报告更定为草稿状态
							InvestigationQueryOrder queryOrder = new InvestigationQueryOrder();
							queryOrder.setProjectCode(projectCode);
							queryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
							queryOrder.setPageSize(1L);
							queryOrder.setPageNumber(1L);
							QueryBaseBatchResult<InvestigationInfo> batchResult = investigationService
								.queryInvestigation(queryOrder);
							
							if (ListUtil.isEmpty(batchResult.getPageList())) {
								throw ExceptionFactory.newFcsException(
									FcsResultEnum.DATABASE_EXCEPTION, "未查询到该项目的尽职调查报告数据！");
							}
							InvestigationInfo investigationInfo = batchResult.getPageList().get(0);
							// 1.2 更定表单为草稿 
							Long formId = investigationInfo.getFormId();
							FormDO formDO = formDAO.findByFormId(formId);
							formDO.setStatus(FormStatusEnum.DRAFT.code());
							formDAO.update(formDO);
							// 2 项目更定为尽职阶段 状态更定为草稿
							ProjectDO project = projectDAO.findByProjectCodeForUpdate(projectCode);
							if (ProjectStatusEnum.PAUSE.code().equals(project.getStatus())) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS,
									"已暂缓的项目无法修订！");
							}
							project.setStatus(ProjectStatusEnum.NORMAL.code());
							project.setPhases(ProjectPhasesEnum.INVESTIGATING_PHASES.code());
							project.setPhasesStatus(ProjectPhasesStatusEnum.DRAFT.code());
							projectDAO.update(project);
							// 3更新尽调为上会退回
							UpdateInvestigationCouncilBackOrder reviewOrder = new UpdateInvestigationCouncilBackOrder();
							reviewOrder.setFormId(formId);
							reviewOrder.setCouncilBack(BooleanEnum.YES.code());
							investigationService.updateInvestigation(reviewOrder);
						}
					}
					
				} else if (ProjectCouncilCompereMessageEnum.RETURN_COUNCIL_APPLY == order
					.getCompereMessage()) {
					// 重新上会
					councilReadySuccess(projectDO.getApplyId());
				}
				
				projectDO.setCompereMessage(order.getCompereMessage().code());
				councilProjectDAO.update(projectDO);
				
				return null;
			}
		}, null, null);
		
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
	
}
