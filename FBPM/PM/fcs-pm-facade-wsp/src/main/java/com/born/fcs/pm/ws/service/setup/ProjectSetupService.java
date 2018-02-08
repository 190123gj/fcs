package com.born.fcs.pm.ws.service.setup;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.setup.FProjectBankLoanInfo;
import com.born.fcs.pm.ws.info.setup.FProjectCounterGuaranteeGuarantorInfo;
import com.born.fcs.pm.ws.info.setup.FProjectCounterGuaranteePledgeInfo;
import com.born.fcs.pm.ws.info.setup.FProjectCustomerBaseInfo;
import com.born.fcs.pm.ws.info.setup.FProjectEquityStructureInfo;
import com.born.fcs.pm.ws.info.setup.FProjectExternalGuaranteeInfo;
import com.born.fcs.pm.ws.info.setup.FProjectGuaranteeEntrustedInfo;
import com.born.fcs.pm.ws.info.setup.FProjectInfo;
import com.born.fcs.pm.ws.info.setup.FProjectLgLitigationInfo;
import com.born.fcs.pm.ws.info.setup.FProjectUnderwritingInfo;
import com.born.fcs.pm.ws.info.setup.SetupFormProjectInfo;
import com.born.fcs.pm.ws.order.common.CopyHisFormOrder;
import com.born.fcs.pm.ws.order.setup.*;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 担保/委贷/承销项目立项
 *
 * @author wuzj
 *
 */
@WebService
public interface ProjectSetupService {
	
	/**
	 * 保存担保/委贷类项目信息
	 * @param order
	 * @return
	 */
	FormBaseResult saveGuaranteeEntrustedProject(FProjectGuaranteeEntrustedOrder order);
	
	/**
	 * 保存承销类项目信息
	 * @param order
	 * @return
	 */
	FormBaseResult saveUnderwritingProject(FProjectUnderwritingOrder order);
	
	/**
	 * 保存诉讼保函类项目信息
	 * @param order
	 * @return
	 */
	FormBaseResult saveLgLitigationProject(FProjectLgLitigationOrder order);
	
	/**
	 * 查询项目公共信息
	 * @param formId
	 * @return
	 */
	FProjectInfo queryProjectByFormId(long formId);
	
	/**
	 * 查询项目公共信息
	 * @param formId
	 * @return
	 */
	FProjectInfo queryProjectByCode(String projectCode);
	
	/**
	 * 查询担保/委贷项目信息
	 * @param formId
	 * @return
	 */
	FProjectGuaranteeEntrustedInfo queryGuaranteeEntrustedProjectByFormId(long formId);
	
	/**
	 * 查询担保/委贷项目信息
	 * @param projectCode
	 * @return
	 */
	FProjectGuaranteeEntrustedInfo queryGuaranteeEntrustedProjectByCode(String projectCode);
	
	/**
	 * 查询承销类项目信息
	 * @param formId
	 * @return
	 */
	FProjectUnderwritingInfo queryUnderwritingProjectByFormId(long formId);
	
	/**
	 * 查询承销类项目信息
	 * @param projectCode
	 * @return
	 */
	FProjectUnderwritingInfo queryUnderwritingProjectByCode(String projectCode);
	
	/**
	 * 查询诉讼保函类项目信息
	 * @param formId
	 * @return
	 */
	FProjectLgLitigationInfo queryLgLitigationProjectByFormId(long formId);
	
	/**
	 * 查询诉讼保函项目信息
	 * @param projectCode
	 * @return
	 */
	FProjectLgLitigationInfo queryLgLitigationProjectByCode(String projectCode);
	
	/**
	 * 保存企业基本情况
	 * @param order
	 * @return
	 */
	FormBaseResult saveCustomerBaseInfo(FProjectCustomerBaseInfoOrder order);

	/**
	 *创新项目
	 * @param order
	 * @return
	 */
	FormBaseResult saveInnovativeProduct(ProjectInnovativeProductOrder order);

	/**
	 * 根据表单ID查询企业基本信息
	 * @param formId
	 * @return
	 */
	FProjectCustomerBaseInfo queryCustomerBaseInfoByFormId(long formId);
	
	/**
	 * 根据项目编号查询企业基本信息
	 * @param projectCode
	 * @return
	 */
	FProjectCustomerBaseInfo queryCustomerBaseInfoByProjectCode(String projectCode);
	
	/**
	 * 保存股权结构
	 * @param order
	 * @return
	 */
	FormBaseResult saveEquityStructure(FProjectEquityStructureBatchOrder order);
	
	/**
	 * 根据表单ID查询股权结构
	 * @param formId
	 * @return
	 */
	List<FProjectEquityStructureInfo> queryEquityStructureByFormId(long formId);
	
	/**
	 * 根据项目编号查询股权结构
	 * @param projectCode
	 * @return
	 */
	List<FProjectEquityStructureInfo> queryEquityStructureByProjectCode(String projectCode);
	
	/**
	 * 保存银行贷款情况
	 * @param order
	 * @return
	 */
	FormBaseResult saveBankLoan(FProjectBankLoanBatchOrder order);
	
	/**
	 * 根据表单ID查询银行贷款情况
	 * @param formId
	 * @return
	 */
	List<FProjectBankLoanInfo> queryBankLoanByFormId(long formId);
	
	/**
	 * 根据项目编号查询银行贷款情况
	 * @param projectCode
	 * @return
	 */
	List<FProjectBankLoanInfo> queryBankLoanByProjectCode(String projectCode);
	
	/**
	 * 保存对外担保情况
	 * @param order
	 * @return
	 */
	FormBaseResult saveExternalGuarantee(FProjectExternalGuaranteeBatchOrder order);
	
	/**
	 * 根据表单ID查询对外担保情况
	 * @param formId
	 * @return
	 */
	List<FProjectExternalGuaranteeInfo> queryExternalGuaranteeByFormId(long formId);
	
	/**
	 * 根据项目编号查询对外担保情况
	 * @param projectCode
	 * @return
	 */
	List<FProjectExternalGuaranteeInfo> queryExternalGuaranteeByProjectCode(String projectCode);
	
	/**
	 * 保存对反担保/担保情况(抵（质）押 & 保证人)
	 * @param order
	 * @return
	 */
	FormBaseResult saveCounterGuarantee(FProjectCounterGuaranteeOrder order);
	
	/**
	 * 根据表单编号查询 抵（质）押
	 * @param formId
	 * @return
	 */
	List<FProjectCounterGuaranteePledgeInfo> queryCounterGuaranteePledgeByFormId(long formId);
	
	/**
	 * 根据项目编号查询 抵（质）押
	 * @param projectCode
	 * @return
	 */
	List<FProjectCounterGuaranteePledgeInfo> queryCounterGuaranteePledgeByProjectCode(	String projectCode);
	
	/**
	 * 根据表单ID查询保证人
	 * @param formId
	 * @return
	 */
	List<FProjectCounterGuaranteeGuarantorInfo> queryCounterGuaranteeGuarantorByFormId(long formId);
	
	/**
	 * 根据项目编号查询保证人
	 * @param projectCode
	 * @return
	 */
	List<FProjectCounterGuaranteeGuarantorInfo> queryCounterGuaranteeGuarantorByProjectCode(String projectCode);
	
	/**
	 * 项目立项表单列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<SetupFormProjectInfo> querySetupForm(SetupFormQueryOrder order);
	
	/**
	 * 复制存量项目
	 * @param formId
	 * @return
	 */
	FormBaseResult copyHistory(CopyHisFormOrder order);
}
