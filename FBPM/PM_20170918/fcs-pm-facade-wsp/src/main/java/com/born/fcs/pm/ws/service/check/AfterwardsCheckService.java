package com.born.fcs.pm.ws.service.check;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.check.AfterwardsCheckInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckBaseInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckLitigationInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckReportContentInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckReportProjectInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCustomerInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.order.check.AfterwardsEditionQueryOrder;
import com.born.fcs.pm.ws.order.check.AfterwordsCheckQueryOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckBaseOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckLitigationOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckQueryOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckReportContentOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckReportProjectOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCustomerOrder;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 保后检查报告
 * 
 * 
 * @author Fei
 * 
 */
@WebService
public interface AfterwardsCheckService {
	
	/**
	 * 
	 * 根据条件查询列表
	 * 
	 * @param queryOrder
	 * @return
	 */
	QueryBaseBatchResult<AfterwardsCheckInfo> list(AfterwordsCheckQueryOrder queryOrder);
	
	
	/**
	 * 根据表单ID查询保后检查报告
	 * @param formId 对应的表单ID
	 * @return 保后检查报告
	 */
	FAfterwardsCheckInfo findByFormId(long formId);
	
	/**
	 * 保后检查报告-开始填写报告时，保存初始信息
	 * @param order
	 * @return
	 */
	FormBaseResult save(FAfterwardsCheckOrder order);
	
	/**
	 * 保后-保存诉讼保函类
	 * @param order
	 * @return
	 */
	FormBaseResult saveLitigation(FAfterwardsCheckLitigationOrder order);
	
	/**
	 * 根据表单id查询基本项目信息
	 * @param formId
	 * @return
	 */
	FAfterwardsCheckBaseInfo findBaseInfoByFormId(long formId);
	
	/**
	 * 根据表单查询诉讼保函类报告
	 * @param formId
	 * @return
	 */
	FAfterwardsCheckLitigationInfo findLitigationByFormId(long formId);
	
	/**
	 * 
	 * 保存项目基本信息
	 * 
	 * @param order
	 * @return
	 */
	FormBaseResult saveBaseInfo(FAfterwardsCheckBaseOrder order);
	
	/**
	 * 根据表单id查询监管内容信息
	 * 
	 * @param formId
	 * @return
	 */
	FAfterwardsCheckReportContentInfo findContentByFomrId(long formId);
	
	/**
	 * 
	 * 保存监管内容
	 * 
	 * @param order
	 * @return
	 */
	FormBaseResult saveContent(FAfterwardsCheckReportContentOrder order);
	
	/**
	 * 根据id查找 房地产开发业 - 开发项目完成情况检查 (项目详情)
	 * @param id
	 * @return
	 */
	FAfterwardsCheckReportProjectInfo findContentProjectById(long id);
	
	/**
	 * 房地产开发业 - 开发项目完成情况检查 (项目详情)
	 * @param formId
	 * @return
	 */
	FcsBaseResult saveContentProject(FAfterwardsCheckReportProjectOrder order);
	
	/**
	 * 
	 * 检查房地产类开发项目完成情况
	 * 
	 * @param formId
	 * @return
	 */
	FcsBaseResult checkContentProject(long formId);
	
	/**
	 * 
	 * 保后检查报告项目选择
	 * 
	 * @param queryOrder
	 * @return
	 */
	QueryBaseBatchResult<ProjectInfo> queryProjects(ProjectQueryOrder queryOrder);
	
	/**
	 * 
	 * 查询最新一期保后检查报告
	 * 
	 * @param queryOrder
	 * @return
	 */
	FAfterwardsCheckInfo queryLastEdition(FAfterwardsCheckQueryOrder queryOrder);
	
	/**
	 * 
	 * 复制历史版本
	 * 
	 * @param order
	 * @return
	 */
	FormBaseResult copy(FAfterwardsCheckOrder order);
	
	/**
	 * 
	 * 查询审核通过的保后检查报告
	 * 
	 * @param queryOrder
	 * @return
	 */
	QueryBaseBatchResult<FAfterwardsCheckInfo> queryAfterwardsCheckReport(FAfterwardsCheckQueryOrder queryOrder);
	
	/**
	 * 根据projectCode查询保后财务信息
	 * 
	 * @param projectCode
	 * @return
	 */
	FAfterwardsCheckReportContentInfo queryFinancilInfo(String projectCode);
	
	/**
	 * 
	 * 保存客户信息
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult saveCustomerInfo(FAfterwardsCustomerOrder order);
	
	/**
	 * 根据表单id查询客户信息
	 * 
	 * @param formId
	 * @return
	 */
	FAfterwardsCustomerInfo findCustomerByFomrId(long formId);
	
	/**
	 * 验证保后的基数是否可用
	 * @param queryOrder
	 * @return true 期数可用，其它均不可用
	 */
	FcsBaseResult isEditionAvailable(AfterwardsEditionQueryOrder queryOrder);
}
