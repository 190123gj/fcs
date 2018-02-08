package com.born.fcs.pm.biz.service.basicmaintain;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.CouncilTypeDO;
import com.born.fcs.pm.dal.dataobject.DecisionInstitutionDO;
import com.born.fcs.pm.dal.dataobject.DecisionMemberDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.basicmaintain.DecisionInstitutionInfo;
import com.born.fcs.pm.ws.info.basicmaintain.DecisionMemberInfo;
import com.born.fcs.pm.ws.order.basicmaintain.DecisionInstitutionOrder;
import com.born.fcs.pm.ws.order.basicmaintain.DecisionInstitutionQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.basicmaintain.DecisionInstitutionService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("decisionInstitutionService")
public class DecisionInstitutionServiceImpl extends BaseFormAutowiredDomainService implements
																					DecisionInstitutionService {
	
	private DecisionInstitutionInfo convertDO2Info(DecisionInstitutionDO DO) {
		if (DO == null)
			return null;
		DecisionInstitutionInfo info = new DecisionInstitutionInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	private DecisionMemberDO convertInfo2DO(DecisionMemberInfo info) {
		if (info == null)
			return null;
		DecisionMemberDO DO = new DecisionMemberDO();
		BeanCopier.staticCopy(info, DO);
		return DO;
	}
	
	@Override
	public DecisionInstitutionInfo findById(long institutionId) {
		DecisionInstitutionInfo info = null;
		if (institutionId > 0) {
			DecisionInstitutionDO DO = decisionInstitutionDAO.findById(institutionId);
			info = convertDO2Info(DO);
		}
		return info;
	}
	
	@Override
	public FcsBaseResult save(final DecisionInstitutionOrder order) {
		
		return commonProcess(order, "保存决策机构名称", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				DecisionInstitutionDO decisionInstitution = null;
				if (order.getInstitutionId() != null && order.getInstitutionId() > 0) {
					decisionInstitution = decisionInstitutionDAO.findById(order.getInstitutionId());
					if (decisionInstitution == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"决策机构名称不存在");
					}
				}
				
				if (decisionInstitution == null) { //新增
					decisionInstitution = new DecisionInstitutionDO();
					BeanCopier.staticCopy(order, decisionInstitution,
						UnBoxingConverter.getInstance());
					decisionInstitution.setDeleteMark("0");
					decisionInstitution.setRawAddTime(now);
					decisionInstitution.setRawUpdateTime(now);
					decisionInstitutionDAO.insert(decisionInstitution);
					//决策机构保存成功后，再保存决策机构人员
					List<DecisionMemberInfo> listMemberInfo = order.getDecisionMembers();
					DecisionInstitutionInfo decisionInfo = convertDO2Info(decisionInstitutionDAO
						.findDecisionInstitutionByInstitutionName(order.getInstitutionName()));
					for (DecisionMemberInfo decisionMemberInfo : listMemberInfo) {
						DecisionMemberDO decisionMemberDO = new DecisionMemberDO();
						decisionMemberInfo.setInstitutionId(decisionInfo.getInstitutionId());
						decisionMemberDO = convertInfo2DO(decisionMemberInfo);
						decisionMemberDO.setRawAddTime(now);
						decisionMemberDO.setDeleteMark("0");
						decisionMemberDAO.insert(decisionMemberDO);
					}
				} else { //修改
					BeanCopier.staticCopy(order, decisionInstitution,
						UnBoxingConverter.getInstance());
					decisionInstitution.setDeleteMark("0");
					decisionInstitutionDAO.update(decisionInstitution);
					//决策机构更新成功后，再更新决策机构人员
					List<DecisionMemberInfo> listMemberInfo = order.getDecisionMembers();
					Long institutionId = order.getInstitutionId();
					List<DecisionMemberDO> listMemberDO = decisionMemberDAO
						.findListByInstitutionId(decisionInstitution.getInstitutionId());
					//先删除决策机构原有人员不在这次保存中
					for (DecisionMemberDO decisionMemberDO : listMemberDO) {
						Boolean mark = false;
						
						for (DecisionMemberInfo decisionMemberInfo : listMemberInfo) {
							if (decisionMemberDO.getUserId() == decisionMemberInfo.getUserId()) {
								mark = true;
								
								break;
							}
						}
						if (mark == false) {
							decisionMemberDO.setDeleteMark("1");
							decisionMemberDAO.update(decisionMemberDO);
						}
					}
					//保存新的决策机构人员和更新决策机构人员
					//					int sort = 0;
					for (DecisionMemberInfo decisionMemberInfo : listMemberInfo) {
						DecisionMemberDO memberDO = decisionMemberDAO.findByInstitutionIdAndUserId(
							institutionId, decisionMemberInfo.getUserId());
						
						if (memberDO == null) {
							DecisionMemberDO decisionMemberDO1 = new DecisionMemberDO();
							decisionMemberInfo.setInstitutionId(institutionId);
							decisionMemberDO1 = convertInfo2DO(decisionMemberInfo);
							decisionMemberDO1.setDeleteMark("0");
							decisionMemberDO1.setRawAddTime(now);
							//							decisionMemberDO1.setSortOrder(sort);
							decisionMemberDAO.insert(decisionMemberDO1);
						} else {
							memberDO.setUserId(decisionMemberInfo.getUserId());
							memberDO.setUserName(decisionMemberInfo.getUserName());
							//							memberDO.setRawUpdateTime(now);
							memberDO.setDeleteMark("0");
							//							memberDO.setSortOrder(sort);
							decisionMemberDAO.update(memberDO);
						}
						//						sort++;
					}
					
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<DecisionInstitutionInfo> query(DecisionInstitutionQueryOrder order) {
		QueryBaseBatchResult<DecisionInstitutionInfo> baseBatchResult = new QueryBaseBatchResult<DecisionInstitutionInfo>();
		
		DecisionInstitutionDO queryCondition = new DecisionInstitutionDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (order.getInstitutionId() != null)
			queryCondition.setInstitutionId(order.getInstitutionId());
		
		long totalSize = decisionInstitutionDAO.findByConditionCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<DecisionInstitutionDO> pageList = decisionInstitutionDAO.findByCondition(
			queryCondition, component.getFirstRecord(), component.getPageSize(),
			order.getSortOrder(), order.getSortCol());
		
		List<DecisionInstitutionInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (DecisionInstitutionDO product : pageList) {
				list.add(convertDO2Info(product));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public DecisionInstitutionInfo findDecisionInstitutionByInstitutionName(String institutionName) {
		DecisionInstitutionDO decisionMemberDO = decisionInstitutionDAO
			.findDecisionInstitutionByInstitutionName(institutionName);
		return convertDO2Info(decisionMemberDO);
	}
	
	@Override
	public int deleteById(long institutionId) {
		//删除前判断会议类型是否引用该决策机构
		List<CouncilTypeDO> listTypeDO = councilTypeDAO.findCouncilTypeDecisionId(institutionId);
		if (listTypeDO == null || listTypeDO.size() == 0) {
			//删除决策机构同时删除对应的决策机构人员
			DecisionInstitutionDO decisionInstitutionDO = decisionInstitutionDAO
				.findById(institutionId);
			decisionInstitutionDO.setDeleteMark("1");
			List<DecisionMemberDO> memberDO = decisionMemberDAO
				.findListByInstitutionId(institutionId);
			for (DecisionMemberDO decisionMemberDO : memberDO) {
				decisionMemberDO.setDeleteMark("1");
				decisionMemberDAO.update(decisionMemberDO);
			}
			return decisionInstitutionDAO.update(decisionInstitutionDO);
		} else {
			return 0;
		}
	}
	
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
	
}
