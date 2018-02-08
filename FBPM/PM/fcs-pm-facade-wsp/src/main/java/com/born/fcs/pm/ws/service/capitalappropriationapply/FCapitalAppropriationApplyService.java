package com.born.fcs.pm.ws.service.capitalappropriationapply;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.enums.FCapitalAppropriationApplyTypeEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.info.capitalappropriationapply.CapitalExportInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyFeeInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyReceiptInfo;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyOrder;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyQueryOrder;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyReceiptOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.money.Money;

/**
 * 资金划付申请service
 *
 * @author Ji
 */
@WebService
public interface FCapitalAppropriationApplyService {
	
	/**
	 * 根据ID查询资金划付申请信息
	 * @param id
	 * @return
	 */
	FCapitalAppropriationApplyInfo findById(long id);
	
	/**
	 * 根据表单ID查询资金划付申请信息
	 * @param id
	 * @return
	 */
	FCapitalAppropriationApplyInfo findCapitalAppropriationApplyByFormId(long formId);
	
	/**
	 * 保存资金划付申请信息
	 * @param order
	 * @return
	 */
	FormBaseResult save(FCapitalAppropriationApplyOrder order);
	
	/**
	 * 分页查询资金划付申请信息
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FCapitalAppropriationApplyInfo> query(	FCapitalAppropriationApplyQueryOrder order);
	
	//	/**
	//	 * 根据项目编号查询资金划付申请信息
	//	 * @param projectCode
	//	 * @return
	//	 */
	//	FCreditConditionConfirmInfo findCapitalAppropriationApplyByProjectCode(String projectCode);
	
	/**
	 * 根据项目编号查询所有的资金划付申请信息
	 * @param projectCode
	 * @return
	 */
	List<FCapitalAppropriationApplyInfo> findCapitalAppropriationApplyByProjectCode(String projectCode);
	
	/**
	 * 保存资金划付表单数据
	 * @param order
	 * @return
	 */
	FcsBaseResult saveCapitalAppropriationApply(FCapitalAppropriationApplyOrder order);
	
	/**
	 * 根据申请单id查询所有的资金划付信息
	 * @param projectCode
	 * @return
	 */
	List<FCapitalAppropriationApplyFeeInfo> findByApplyId(long applyId);
	
	/**
	 * 根据项目 获取付款种类
	 * @param projectCode
	 * @return
	 */
	List<PaymentMenthodEnum> getPaymentMenthodEnum(String projectCode);
	
	/**
	 * 根据项目编号 项目类型 获取限额
	 * @param projectCode
	 * @param type
	 * @return
	 */
	List<Money> getLimitByProject(String projectCode, FCapitalAppropriationApplyTypeEnum type,
									long applyId, Boolean isEdit);
	
	/**
	 * 保存资金划付-回执单信息
	 * @param order
	 * @return
	 */
	FcsBaseResult saveReceipt(FCapitalAppropriationApplyReceiptOrder order);
	
	/**
	 * 根据FormID查询资金划付申请单-回执西悉尼
	 * @param formId
	 * @return
	 */
	FCapitalAppropriationApplyReceiptInfo findByReceiptFormId(long formId);
	
	/**
	 * 资金划付导出
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<CapitalExportInfo> capitalExport(FCapitalAppropriationApplyQueryOrder order);
}
