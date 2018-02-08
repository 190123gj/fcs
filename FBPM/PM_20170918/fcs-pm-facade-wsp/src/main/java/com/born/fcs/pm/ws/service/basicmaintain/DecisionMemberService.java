package com.born.fcs.pm.ws.service.basicmaintain;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.basicmaintain.DecisionMemberInfo;
import com.born.fcs.pm.ws.order.basicmaintain.DecisionMemberOrder;
import com.born.fcs.pm.ws.order.basicmaintain.DecisionMemberQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 决策机构维护
 *
 * @author wuzj
 */
@WebService
public interface DecisionMemberService {
	
	/**
	 * 根据ID决策机构人员
	 * @param memberId
	 * @return
	 */
	DecisionMemberInfo findById(long memberId);
	
	/**
	 * 保存决策机构人员
	 * @param order
	 * @return
	 */
	FcsBaseResult save(DecisionMemberOrder order);
	
	/**
	 * 查询决策机构人员
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<DecisionMemberInfo> query(DecisionMemberQueryOrder order);
	
	/**
	 * 查询决策机构下的所有人员
	 * @param institutionId
	 * @return
	 */
	List<DecisionMemberInfo> queryDecisionMemberInfo(Long institutionId);
	
	/**
	 * 根据决策机构人员名称 查找决策人员信息
	 * @param userName 决策机构人员名称
	 * @return 没有数据返回null
	 */
	DecisionMemberInfo findDecisionMemberByUserName(String userName);
}
