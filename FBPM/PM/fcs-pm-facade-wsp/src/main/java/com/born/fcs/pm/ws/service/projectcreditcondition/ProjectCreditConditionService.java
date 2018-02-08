package com.born.fcs.pm.ws.service.projectcreditcondition;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.*;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.counterguarantee.CreditConditionReleaseOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.FCreditConditionConfirmOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.FCreditConditionConfirmQueryOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.money.Money;

/**
 * 授信条件落实情况service
 * 
 * @author Ji
 */
@WebService
public interface ProjectCreditConditionService {
	
	/**
	 * 根据ID查询项目授信条件
	 * @param id
	 * @return
	 */
	ProjectCreditConditionInfo findById(long id);
	
	/**
	 * 根据表单ID查询项目信息
	 * @param id
	 * @return
	 */
	FCreditConditionConfirmInfo findCreditConditionByFormId(long formId);
	
	/**
	 * 根据表单ID查询项目授信条件
	 * @param id
	 * @return
	 */
	List<FCreditConditionConfirmItemInfo> findByFormId(long formId);
	
	/**
	 * 根据表单ID查询授信落实概况
	 * @param formId
	 * @return
	 */
	FCreditConditionConfirmInfo queryFCreditConditionConfirmByFormId(long formId);
	
	//	/**
	//	 * 根据表单ID查询授信条件落实情况
	//	 * @param formId 对应的表单ID
	//	 * @return
	//	 */
	//	ProjectCreditConditionInfo queryProjectCreditConditionByFormId(long formId);
	
	//	/**
	//	 * 保存项目授信条件
	//	 * @param order
	//	 * @return
	//	 */
	//	FcsBaseResult saveProjectCreditCondition(ProjectCreditConditionOrder order);
	
	/**
	 * 保存授信条件落实情况
	 * @param order
	 * @return
	 */
	FormBaseResult saveProjectCreditCondition(FCreditConditionConfirmOrder order);
	
	/**
	 * 保存授信情况解保情况
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult saveCreditConditionRelease(CreditConditionReleaseOrder order);
	
	/**
	 * 分页查询授信条件落实情况
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FCreditConditionConfirmInfo> query(FCreditConditionConfirmQueryOrder order);
	
	/**
	 * 根据项目编号查询授信条件落实情况信息
	 * @param projectCode
	 * @return
	 */
	FCreditConditionConfirmInfo findFCreditConditionConfirmByProjectCode(String projectCode);
	
	/**
	 * 根据项目编号查询所有的项目授信条件
	 * @param projectCode
	 * @return
	 */
	List<ProjectCreditConditionInfo> findProjectCreditConditionByProjectCode(String projectCode);
	
	/**
	 * 根据项目编号和状态查询所有的项目授信条件
	 * @param projectCode
	 * @param status
	 * @return
	 */
	List<ProjectCreditConditionInfo> findProjectCreditConditionByProjectCodeAndStatus(	String projectCode,
																						String status);
	
	/**
	 * 保存授信条件落实情况表单数据
	 * @param order
	 * @return
	 */
	FcsBaseResult saveFCreditConditionConfirmItem(ProjectCreditConditionOrder order);
	
	/**
	 * 根据项目编号查询所有的已提交或已已暂存的数据项目授信条件
	 * @param projectCode
	 * @return
	 */
	List<FCreditConditionConfirmItemInfo> findFCreditConditionConfirmItemByProjectCodeAndStatus(String projectCode,
																								String status);
	
	/**
	 * 根据项目编号查询暂存数据和还未发起提交的 项目授信条件(剔除还未落实中 但已发起落实的数据)
	 * @param projectCode
	 * @return
	 */
	List<FCreditConditionConfirmItemInfo> findByConfirmId(long confirmId);
	
	/**
	 * 分页查询授信条件落实情况
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectCreditConditionInfo> queryCreditCondition(	ProjectCreditConditionQueryOrder order);
	
	/**
	 * 新增时去重分页查询授信条件落实情况
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectInfo> queryProjectAndCredit(ProjectQueryOrder order);
	
	/**
	 * 根据itemid[f_council_summary_project_pledge.id,
	 * f_council_summary_project_guarantor.id] 查询项目授信条件
	 * @param projectCode
	 * @return
	 */
	ProjectCreditConditionInfo findByProjectCodeAndItemIdAndType(String projectCode, Long itemId,
																	String type);
	
	/**
	 * 查询 资产附件
	 * @param creditId
	 * @return
	 */
	List<ProjectCreditAssetAttachmentInfo> findByCreditId(long creditId);
	
	/**
	 * 根据项目编号查询 当前项目的保证金金额
	 * @param projectCode
	 * @return
	 */
	Money findMarginAmountByProjectCode(String projectCode);
	/**
	 * 反担保措施表
	 * @param
	 * @return
	 */
	QueryBaseBatchResult<CounterGuaranteeMeasuresInfo> measuresList(FCreditConditionConfirmQueryOrder queryOrder);
	
}
