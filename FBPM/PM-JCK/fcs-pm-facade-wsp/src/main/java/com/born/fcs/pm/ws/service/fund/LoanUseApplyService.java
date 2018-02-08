package com.born.fcs.pm.ws.service.fund;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyFeeInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyReceiptInfo;
import com.born.fcs.pm.ws.order.fund.FLoanUseApplyOrder;
import com.born.fcs.pm.ws.order.fund.FLoanUseApplyReceiptOrder;
import com.born.fcs.pm.ws.order.fund.LoanUseApplyFormInfo;
import com.born.fcs.pm.ws.order.fund.LoanUseApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.fund.LoanUseApplyResult;

/**
 * 
 * 放用款申请
 *
 * @author wuzj
 *
 */
@WebService
public interface LoanUseApplyService {
	
	/**
	 * 保存放用款申请单
	 * @param order
	 * @return
	 */
	LoanUseApplyResult saveApply(FLoanUseApplyOrder order);
	
	/**
	 * 保存放用款申请单 - 回执
	 * @param order
	 * @return
	 */
	FcsBaseResult saveApplyReceipt(FLoanUseApplyReceiptOrder order);
	
	/**
	 * 根据表单ID查询放用款申请单
	 * @param formId
	 * @return
	 */
	FLoanUseApplyInfo queryByFormId(long formId);
	
	/**
	 * 根据申请单ID查询放用款申请单
	 * @param formId
	 * @return
	 */
	FLoanUseApplyInfo queryByApplyId(long applyId);
	
	/**
	 * 根据项目编号查询放用款申请
	 * @param projectCode
	 * @return
	 */
	List<FLoanUseApplyInfo> queryByProjectCode(String projectCode);
	
	/**
	 * 查询费用收取情况
	 * @param applyId
	 * @return
	 */
	List<FLoanUseApplyFeeInfo> queryFeeByApplyId(long applyId);
	
	/**
	 * 查询放用款申请单回执
	 * @param applyId
	 * @return
	 */
	FLoanUseApplyReceiptInfo queryReceiptByApplyId(long applyId);
	
	/**
	 * 根据项目编号查询放用款回执
	 * @param projectCode
	 * @return
	 */
	List<FLoanUseApplyReceiptInfo> queryReceipt(String projectCode);
	
	/**
	 * 申请单列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<LoanUseApplyFormInfo> searchApplyForm(LoanUseApplyQueryOrder order);
	
	/**
	 * 选择放用款项目
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectSimpleDetailInfo> selectLoanUseApplyProject(QueryProjectBase order);
	
	/**
	 * 获取申请中金额
	 * @param projectCode
	 * @param currentApplyId
	 * @return
	 */
	LoanUseApplyResult getApplyingAmountByProjectCode(String projectCode, long currentApplyId);
	
	/**
	 * 获取申请中金额
	 * @param project
	 * @param currentApplyId
	 * @return
	 */
	LoanUseApplyResult getApplyingAmount(ProjectInfo project, long currentApplyId);
	
	/**
	 * 查询项目实时各种金额
	 * @param project
	 * @param currentApplyId
	 * @return
	 */
	LoanUseApplyResult queryProjectAmount(ProjectInfo project, long currentApplyId);
	
	/**
	 * 查询表单各种金额
	 * @param apply
	 * @return
	 */
	LoanUseApplyResult queryFormAmount(FLoanUseApplyInfo apply);
}
