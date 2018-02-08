package com.born.fcs.pm.ws.service.council;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.basicmaintain.DecisionInstitutionInfo;
import com.born.fcs.pm.ws.info.council.CouncilJudgeInfo;
import com.born.fcs.pm.ws.info.council.CouncilTypeInfo;
import com.born.fcs.pm.ws.order.council.CouncilTypeOrder;
import com.born.fcs.pm.ws.order.council.CouncilTypeQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 会议类型维护
 *
 * @author wuxue
 */
@WebService
public interface CouncilTypeService {
	
	/**
	 * 根据ID查询会议类型
	 * @param institutionId
	 * @return
	 */
	CouncilTypeInfo findById(long typeId);
	
	/**
	 * 保存会议类型
	 * @param order
	 * @return
	 */
	FcsBaseResult save(CouncilTypeOrder order);
	
	//	/**
	//	 * 修改销售状态
	//	 * @param productId
	//	 * @param sellStatus
	//	 * @return
	//	 */
	//	FcsBaseResult changeSellStatus(FinancialProductOrder order);
	
	/**
	 * 查询会议类型
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<CouncilTypeInfo> query(CouncilTypeQueryOrder order);
	
	/**
	 * 根据会议名称 查找会议类型
	 * @param typeName 类型名称
	 * @return 没有数据返回null
	 */
	CouncilTypeInfo findCouncilTypeByTypeName(String typeName, String typeCode);
	
	/**
	 * 根据id删除会议类型
	 * @param institutionId
	 * @return
	 */
	int deleteById(long typeId);
	
	/**
	 * 根据会议类型查询决策机构信息
	 * @param institutionId
	 * @return
	 */
	DecisionInstitutionInfo findDecisionInstitutionByCouncilType(CouncilTypeOrder order);
	
	/**
	 * 根据会议类型查询参会评委(决策人员)
	 * @param councilType
	 * @return
	 */
	List<CouncilJudgeInfo> findCouncilJudgesByCouncilType(Long councilType);
}
