package com.born.fcs.pm.biz.service.council;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.CouncilDO;
import com.born.fcs.pm.dal.dataobject.CouncilTypeDO;
import com.born.fcs.pm.dal.dataobject.DecisionInstitutionDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.VoteRuleTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.basicmaintain.DecisionInstitutionInfo;
import com.born.fcs.pm.ws.info.basicmaintain.DecisionMemberInfo;
import com.born.fcs.pm.ws.info.council.CouncilJudgeInfo;
import com.born.fcs.pm.ws.info.council.CouncilTypeInfo;
import com.born.fcs.pm.ws.order.council.CouncilTypeOrder;
import com.born.fcs.pm.ws.order.council.CouncilTypeQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.basicmaintain.DecisionMemberService;
import com.born.fcs.pm.ws.service.council.CouncilTypeService;
import com.google.common.collect.Lists;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("councilTypeService")
public class CouncilTypeServiceImpl extends BaseFormAutowiredDomainService implements
																			CouncilTypeService {
	
	@Autowired
	DecisionMemberService decisionMemberService;
	
	private CouncilTypeInfo convertDO2Info(CouncilTypeDO DO) {
		if (DO == null)
			return null;
		CouncilTypeInfo info = new CouncilTypeInfo();
		BeanCopier.staticCopy(DO, info);
		info.setTypeCode(CouncilTypeEnum.getByCode(DO.getTypeCode()));
		info.setIfVote(BooleanEnum.getByCode(DO.getIfVote()));
		info.setVoteRuleType(VoteRuleTypeEnum.getByCode(DO.getVoteRuleType()));
		info.setCompanyName(CompanyNameEnum.getByCode(DO.getCompanyName()));
		return info;
	}
	
	private DecisionInstitutionInfo convertDO2Info(DecisionInstitutionDO DO) {
		if (DO == null)
			return null;
		DecisionInstitutionInfo info = new DecisionInstitutionInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	@Override
	public CouncilTypeInfo findById(long typeId) {
		CouncilTypeInfo info = null;
		if (typeId > 0) {
			CouncilTypeDO DO = councilTypeDAO.findById(typeId);
			info = convertDO2Info(DO);
		}
		return info;
	}
	
	@Override
	public FcsBaseResult save(final CouncilTypeOrder order) {
		
		return commonProcess(order, "保存会议类型", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				CouncilTypeDO councilType = null;
				if (order.getTypeId() != null && order.getTypeId() > 0) {
					councilType = councilTypeDAO.findById(order.getTypeId());
					if (councilType == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"会议类型不存在");
					}
				}
				
				if (councilType == null) { //新增
					councilType = new CouncilTypeDO();
					BeanCopier.staticCopy(order, councilType, UnBoxingConverter.getInstance());
					councilType.setRawAddTime(now);
					councilType.setDeleteMark("0");
					if (order.getCompanyName() != null) {
						councilType.setCompanyName(order.getCompanyName().code());
					} else {
						councilType.setCompanyName(CompanyNameEnum.NORMAL.code());
					}
					
					councilTypeDAO.insert(councilType);
				} else { //修改
					BeanCopier.staticCopy(order, councilType, UnBoxingConverter.getInstance());
					councilType.setDeleteMark("0");
					if (order.getCompanyName() != null) {
						councilType.setCompanyName(order.getCompanyName().code());
					} else {
						councilType.setCompanyName(CompanyNameEnum.NORMAL.code());
					}
					
					councilTypeDAO.update(councilType);
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<CouncilTypeInfo> query(CouncilTypeQueryOrder order) {
		QueryBaseBatchResult<CouncilTypeInfo> baseBatchResult = new QueryBaseBatchResult<CouncilTypeInfo>();
		
		CouncilTypeDO queryCondition = new CouncilTypeDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (order.getTypeId() != null)
			queryCondition.setTypeId(order.getTypeId());
		
		long totalSize = councilTypeDAO.findByConditionCount(queryCondition,
			order.getCouncilTypeCodes(), order.getCompanyNames());
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<CouncilTypeDO> pageList = councilTypeDAO.findByCondition(queryCondition,
			component.getFirstRecord(), component.getPageSize(), order.getSortOrder(),
			order.getSortCol(), order.getCouncilTypeCodes(), order.getCompanyNames());
		
		List<CouncilTypeInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (CouncilTypeDO product : pageList) {
				list.add(convertDO2Info(product));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public CouncilTypeInfo findCouncilTypeByTypeName(String typeName, String typeCode) {
		CouncilTypeDO councilTypeDO = councilTypeDAO.findCouncilTypeByTypeName(typeName, typeCode);
		return convertDO2Info(councilTypeDO);
	}
	
	@Override
	public int deleteById(long typeId) {
		//删除前判断会议是否引用该会议类型
		List<CouncilDO> listCouncilDO = councilDAO.findByCouncilType(typeId);
		if (listCouncilDO == null || listCouncilDO.size() == 0) {
			CouncilTypeDO councilTypeDO = councilTypeDAO.findById(typeId);
			councilTypeDO.setDeleteMark("1");//逻辑删除设置
			return councilTypeDAO.update(councilTypeDO);
		} else {
			return 0;
		}
	}
	
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
	
	@Override
	public DecisionInstitutionInfo findDecisionInstitutionByCouncilType(CouncilTypeOrder order) {
		Long decisionInstitutionId = order.getDecisionInstitutionId();
		DecisionInstitutionDO decisionInstitutionDO = decisionInstitutionDAO
			.findById(decisionInstitutionId);
		DecisionInstitutionInfo decisionInstitutionInfo = convertDO2Info(decisionInstitutionDO);
		
		List<DecisionMemberInfo> decisionMembers = decisionMemberService
			.queryDecisionMemberInfo(decisionInstitutionInfo.getInstitutionId());
		
		decisionInstitutionInfo.setDecisionMembers(decisionMembers);
		return decisionInstitutionInfo;
	}
	
	@Override
	public List<CouncilJudgeInfo> findCouncilJudgesByCouncilType(Long councilType) {
		CouncilTypeInfo councilTypeInfo = this.findById(councilType);
		List<DecisionMemberInfo> decisionMembers = decisionMemberService
			.queryDecisionMemberInfo(councilTypeInfo.getDecisionInstitutionId());
		return toJudges(decisionMembers);
	}
	
	private List<CouncilJudgeInfo> toJudges(List<DecisionMemberInfo> decisionMembers) {
		
		List<CouncilJudgeInfo> list = Lists.newArrayList();
		
		for (DecisionMemberInfo info : decisionMembers) {
			CouncilJudgeInfo judge = new CouncilJudgeInfo();
			judge.setJudgeId(info.getUserId());
			judge.setJudgeName(info.getUserName());
			list.add(judge);
		}
		
		return list;
	}
	
}
