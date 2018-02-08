package com.born.fcs.am.ws.service.assesscompany;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.am.ws.info.assesscompany.AssessCompanyEvaluateInfo;
import com.born.fcs.am.ws.info.assesscompany.AssetsAssessCompanyInfo;
import com.born.fcs.am.ws.info.assesscompany.FAssessCompanyApplyInfo;
import com.born.fcs.am.ws.order.assesscompany.AssessCompanyEvaluateOrder;
import com.born.fcs.am.ws.order.assesscompany.FAssessCompanyApplyOrder;
import com.born.fcs.am.ws.order.assesscompany.FAssessCompanyApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 评估公司申请单service
 *
 * @author Ji
 */
@WebService
public interface AssessCompanyApplyService {
	
	/**
	 * 根据ID查询评估公司申请单信息
	 * 
	 * @param id
	 * @return
	 */
	FAssessCompanyApplyInfo findById(long id);
	
	/**
	 * 根据表单ID查询评估公司申请单信息
	 * 
	 * @param id
	 * @return
	 */
	FAssessCompanyApplyInfo findByFormId(long formId);
	
	/**
	 * 分页查询评估公司申请单信息
	 * 
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FAssessCompanyApplyInfo> query(FAssessCompanyApplyQueryOrder order);
	
	// /**
	// * 根据项目编号查询所有的资产转让信息
	// *
	// * @param projectCode
	// * @return
	// */
	// List<FAssetsTransferApplicationInfo> findByProjectCode(String
	// projectCode);
	
	/**
	 * 保存评估公司申请 表单数据
	 * 
	 * @param order
	 * @return
	 */
	FormBaseResult save(FAssessCompanyApplyOrder order);
	
	/**
	 * 保存评估公司申请单 评价数据
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult saveAssessCompanyEvaluate(AssessCompanyEvaluateOrder order);
	
	/**
	 * 根据申请单id查询评价信息
	 * 
	 * @param order
	 * @return
	 */
	List<AssessCompanyEvaluateInfo> findEvaluateByApplyId(long applyId);
	
	/**
	 * 根据项目编号查询当前项目的风险经理 是否评价完成
	 * 
	 * @param id
	 * @return
	 */
	Boolean IsEvaluateByProjectCode(String projectCode);
	
	/**
	 * 根据ID查询评估公司申请单信息
	 * 
	 * @param id
	 * @return
	 */
	AssetsAssessCompanyInfo findByApplyIdAndCompany(long applyId, long companyId);
	
	/**
	 * 评估公司抽取
	 * 
	 * @param id
	 * @param cityName
	 * @param provinceName
	 * @return
	 */
	List<AssetsAssessCompanyInfo> assessCompanyeExtract(long id, String cityName,
														String provinceName);
}
