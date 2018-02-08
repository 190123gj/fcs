package com.born.fcs.pm.ws.service.council;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.council.FCouncilSummaryInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectBondInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectCreditConditionInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectEntrustedInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectGuaranteeInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectGuarantorInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectLgLitigationInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectPledgeAssetInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectUnderwritingInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryRiskHandleInfo;
import com.born.fcs.pm.ws.order.council.CouncilSummaryUploadOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectBondOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectEntrustedOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectGuaranteeOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectLgLitigationOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryProjectUnderwritingOrder;
import com.born.fcs.pm.ws.order.council.FCouncilSummaryRiskHandleBatchOrder;
import com.born.fcs.pm.ws.order.council.OneVoteDownOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.council.CouncilSummaryResult;

/**
 * 会议纪要Service
 * 
 * @author wuzj
 * 
 */
@WebService
public interface CouncilSummaryService {
	
	/**
	 * 初始化会议纪要
	 * 
	 * @param order
	 * @return
	 */
	CouncilSummaryResult initCouncilSummary(FCouncilSummaryOrder order);
	
	/**
	 * 保存会议纪要 - 概况
	 * 
	 * @param order
	 * @return
	 */
	CouncilSummaryResult saveCouncilSummary(FCouncilSummaryOrder order);
	
	/**
	 * 上传会议纪要 - 概况
	 * 
	 * @param order
	 * @return
	 */
	CouncilSummaryResult saveGmworkingCouncilSummary(CouncilSummaryUploadOrder order);
	
	/**
	 * 根据Id查询会议纪要概况
	 * 
	 * @param summaryId
	 * @return
	 */
	FCouncilSummaryInfo queryCouncilSummaryById(long summaryId);
	
	/**
	 * 根据表单ID查询会议纪要概况
	 * 
	 * @param formId
	 * @return
	 */
	FCouncilSummaryInfo queryCouncilSummaryByFormId(long formId);
	
	/**
	 * 根据会议ID查询会议纪要概况
	 * 
	 * @param councilId
	 * @return
	 */
	FCouncilSummaryInfo queryCouncilSummaryByCouncilId(long councilId);
	
	/**
	 * 根据会议编号查询会议纪要概况
	 * 
	 * @param councilCode
	 * @return
	 */
	FCouncilSummaryInfo queryCouncilSummaryByCouncilCode(String councilCode);
	
	/**
	 * 根据会议纪要ID查询 项目会议纪要
	 * 
	 * @param summaryId
	 * @return
	 */
	List<FCouncilSummaryProjectInfo> queryProjectCsBySummaryId(long summaryId);
	
	/**
	 * 根据项目编号查询项目会议纪要
	 * 
	 * @param projectCode
	 * @return
	 */
	FCouncilSummaryProjectInfo queryProjectCsByProjectCode(String projectCode);
	
	/**
	 * 根据会议纪要ID查询项目会议纪要
	 * 
	 * @param projectCode
	 * @return
	 */
	FCouncilSummaryProjectInfo queryProjectCsBySpId(long spId);
	
	/**
	 * 根据会议纪要编号查询项目会议纪要
	 * 
	 * @param projectCode
	 * @return
	 */
	FCouncilSummaryProjectInfo queryProjectCsBySpCode(String spCode);
	
	/**
	 * 保存项目会议纪要通用部分
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult saveProjectCsCommon(FCouncilSummaryProjectOrder order);
	
	/**
	 * 保存发债类项目会议纪要
	 * 
	 * @param order
	 * @return
	 */
	FormBaseResult saveBondProjectCs(FCouncilSummaryProjectBondOrder order);
	
	/**
	 * 查询发债项目会议纪要
	 * 
	 * @param projectCode
	 * @param queryAll 是否查询所有信息（收费方式 抵押 质押等）
	 * @return
	 */
	FCouncilSummaryProjectBondInfo queryBondProjectCsByProjectCode(String projectCode,
																	boolean queryAll);
	
	/**
	 * 查询发债项目会议纪要
	 * 
	 * @param projectCode
	 * @param queryAll 是否查询所有信息（收费方式 抵押 质押等）
	 * @return
	 */
	FCouncilSummaryProjectBondInfo queryBondProjectCsBySpId(long spId, boolean queryAll);
	
	/**
	 * 保存 担保项目会议纪要
	 * 
	 * @param order
	 * @return
	 */
	FormBaseResult saveGuaranteeProjectCs(FCouncilSummaryProjectGuaranteeOrder order);
	
	/**
	 * 查询担保项目会议纪要
	 * 
	 * @param projectCode
	 * @param queryAll 是否查询所有信息（收费方式 抵押 质押等）
	 * @return
	 */
	FCouncilSummaryProjectGuaranteeInfo queryGuaranteeProjectCsByProjectCode(String projectCode,
																				boolean queryAll);
	
	/**
	 * 查询担保项目会议纪要
	 * 
	 * @param projectCode
	 * @param queryAll 是否查询所有信息（收费方式 抵押 质押等）
	 * @return
	 */
	FCouncilSummaryProjectGuaranteeInfo queryGuaranteeProjectCsBySpId(long spId, boolean queryAll);
	
	/**
	 * 保存 委贷项目会议纪要
	 * 
	 * @param order
	 * @return
	 */
	FormBaseResult saveEntrustedProjectCs(FCouncilSummaryProjectEntrustedOrder order);
	
	/**
	 * 保存诉讼保函项目会议纪要
	 * 
	 * @param order
	 * @return
	 */
	FormBaseResult saveLgLitigationProjectCs(FCouncilSummaryProjectLgLitigationOrder order);
	
	/**
	 * 一票否决
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult oneVoteDown(OneVoteDownOrder order);
	
	/**
	 * 查询委贷项目会议纪要
	 * 
	 * @param projectCode
	 * @param queryAll 是否查询所有信息（收费方式 抵押 质押等）
	 * @return
	 */
	FCouncilSummaryProjectEntrustedInfo queryEntrustedProjectCsByProjectCode(String projectCode,
																				boolean queryAll);
	
	/**
	 * 查询委贷项目会议纪要
	 * 
	 * @param projectCode
	 * @param queryAll 是否查询所有信息（收费方式 抵押 质押等）
	 * @return
	 */
	FCouncilSummaryProjectEntrustedInfo queryEntrustedProjectCsBySpId(long spId, boolean queryAll);
	
	/**
	 * 查询诉讼保函项目会议纪要
	 * 
	 * @param projectCode
	 * @param queryAll 是否查询所有信息（收费方式 抵押 质押等）
	 * @return
	 */
	FCouncilSummaryProjectLgLitigationInfo queryLgLitigationProjectCsByProjectCode(	String projectCode,
																					boolean queryAll);
	
	/**
	 * 查询诉讼保函项目会议纪要
	 * 
	 * @param projectCode
	 * @param queryAll 是否查询所有信息（收费方式 抵押 质押等）
	 * @return
	 */
	FCouncilSummaryProjectLgLitigationInfo queryLgLitigationProjectCsBySpId(long spId,
																			boolean queryAll);
	
	/**
	 * 保存承销项目会议纪要
	 * 
	 * @param order
	 * @return
	 */
	FormBaseResult saveUnderwritingProjectCs(FCouncilSummaryProjectUnderwritingOrder order);
	
	/**
	 * 查询承销项目会议纪要
	 * 
	 * @param projectCode
	 * @param queryAll 是否查询所有信息（收费方式 等）
	 * @return
	 */
	FCouncilSummaryProjectUnderwritingInfo queryUnderwritingProjectCsByProjectCode(	String projectCode,
																					boolean queryAll);
	
	/**
	 * 查询承销项目会议纪要
	 * 
	 * @param projectCode
	 * @param queryAll 是否查询所有信息（收费方式 等）
	 * @return
	 */
	FCouncilSummaryProjectUnderwritingInfo queryUnderwritingProjectCsBySpId(long spId,
																			boolean queryAll);
	
	/**
	 * 保存风险处置会纪要
	 * 
	 * @param order
	 * @return
	 */
	FormBaseResult saveRiskHandleCs(FCouncilSummaryRiskHandleBatchOrder order);
	
	/**
	 * 查询风险处置会纪要
	 * 
	 * @param projectCode
	 * @return
	 */
	List<FCouncilSummaryRiskHandleInfo> queryRiskHandleCsBySummaryId(long summaryId);
	
	/**
	 * 查询风险处置会纪要
	 * 
	 * @param projectCode
	 * @return
	 */
	List<FCouncilSummaryRiskHandleInfo> queryRiskHandleCsByProjectCode(String projectCode);
	
	/**
	 * 查询已通过审核风险处置会纪要
	 * 
	 * @param projectCode
	 * @return
	 */
	List<FCouncilSummaryRiskHandleInfo> queryApprovalRiskHandleCsByProjectCode(String projectCode);
	
	/**
	 * 查询会议纪要授信条件
	 * @param spId
	 * @param queryCommon 是否查询项目公用信息
	 * @return
	 */
	FCouncilSummaryProjectCreditConditionInfo queryCreditConditionBySpId(long spId,
																			boolean queryCommon);
	
	/**
	 * 根据主键ID查询 抵押/质押 资产
	 * 
	 * @param itemId
	 * @return
	 */
	FCouncilSummaryProjectPledgeAssetInfo queryPledgeAssetById(long itemId);
	
	/**
	 * 根据主键ID查询 保证人
	 * 
	 * @param itemId
	 * @return
	 */
	FCouncilSummaryProjectGuarantorInfo queryGuarantorById(long itemId);
}