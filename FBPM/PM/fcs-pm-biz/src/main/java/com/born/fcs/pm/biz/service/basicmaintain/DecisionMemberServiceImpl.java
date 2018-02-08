package com.born.fcs.pm.biz.service.basicmaintain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.DecisionMemberDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.basicmaintain.DecisionMemberInfo;
import com.born.fcs.pm.ws.order.basicmaintain.DecisionMemberOrder;
import com.born.fcs.pm.ws.order.basicmaintain.DecisionMemberQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.basicmaintain.DecisionMemberService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("decisionMemberService")
public class DecisionMemberServiceImpl extends BaseFormAutowiredDomainService implements
																				DecisionMemberService {
	
	private DecisionMemberInfo convertDO2Info(DecisionMemberDO DO) {
		if (DO == null)
			return null;
		DecisionMemberInfo info = new DecisionMemberInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	@Override
	public DecisionMemberInfo findById(long memberId) {
		DecisionMemberInfo info = null;
		if (memberId > 0) {
			DecisionMemberDO DO = decisionMemberDAO.findById(memberId);
			info = convertDO2Info(DO);
		}
		return info;
	}
	
	@Override
	public FcsBaseResult save(final DecisionMemberOrder order) {
		
		return commonProcess(order, "保存决策机构人员", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				Date now = FcsPmDomainHolder.get().getSysDate();
				DecisionMemberDO decisionMember = null;
				if (order.getMemberId() != null && order.getMemberId() > 0) {
					decisionMember = decisionMemberDAO.findById(order.getMemberId());
					if (decisionMember == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"决策机构人员不存在");
					}
				}
				
				if (decisionMember == null) { //新增
					decisionMember = new DecisionMemberDO();
					decisionMember.setRawAddTime(now);
					BeanCopier.staticCopy(order, decisionMember, UnBoxingConverter.getInstance());
					decisionMemberDAO.insert(decisionMember);
				} else { //修改
					BeanCopier.staticCopy(order, decisionMember, UnBoxingConverter.getInstance());
					decisionMemberDAO.update(decisionMember);
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public DecisionMemberInfo findDecisionMemberByUserName(String userName) {
		DecisionMemberDO decisionMemberDO = decisionMemberDAO
			.findDecisionMemberByUserName(userName);
		return convertDO2Info(decisionMemberDO);
	}
	
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
	
	@Override
	public QueryBaseBatchResult<DecisionMemberInfo> query(DecisionMemberQueryOrder order) {
		QueryBaseBatchResult<DecisionMemberInfo> baseBatchResult = new QueryBaseBatchResult<DecisionMemberInfo>();
		
		DecisionMemberDO queryCondition = new DecisionMemberDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (order.getMemberId() != null)
			queryCondition.setInstitutionId(order.getMemberId());
		
		long totalSize = decisionMemberDAO.findByConditionCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<DecisionMemberDO> pageList = decisionMemberDAO.findByCondition(queryCondition,
			component.getFirstRecord(), component.getPageSize());
		
		List<DecisionMemberInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (DecisionMemberDO product : pageList) {
				list.add(convertDO2Info(product));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public List<DecisionMemberInfo> queryDecisionMemberInfo(Long institutionId) {
		List<DecisionMemberInfo> listDecisionMemberInfo = new ArrayList<DecisionMemberInfo>();
		List<DecisionMemberDO> decisionMemberDO = decisionMemberDAO
			.findListByInstitutionId((institutionId));
		for (DecisionMemberDO decisionMemberDO2 : decisionMemberDO) {
			DecisionMemberInfo decisionMemberInfo = convertDO2Info(decisionMemberDO2);
			listDecisionMemberInfo.add(decisionMemberInfo);
		}
		return listDecisionMemberInfo;
	}
	
}
