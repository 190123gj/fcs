package com.born.fcs.pm.ws.service.contract;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.contract.ProjectContractItemInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractResultInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContrctInfo;
import com.born.fcs.pm.ws.order.contract.ProjectContractItemOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.contract.ProjectContractCheckMessageResult;

/**
 * 项目合同集维护
 * 
 * @author heh
 */
@WebService
public interface ProjectContractService {
	
	/**
	 * 根据ID查询项目合同集
	 * @param id
	 * @return
	 */
	ProjectContrctInfo findById(long id);
	
	/**
	 * 根据FormId查询项目合同集
	 * @param formId
	 * @return
	 */
	ProjectContrctInfo findByFormId(long formId);
	
	/**
	 * 保存项目合同集
	 * @param order
	 * @return
	 */
	FormBaseResult save(ProjectContractOrder order);
	
	/**
	 * 保存项目某一个合同【主要用于制式合同的填写保存】
	 * @param order
	 * @return
	 */
	FcsBaseResult saveStandardContractContent(ProjectContractItemOrder order);
	
	/**
	 * 合同回传
	 * @param order
	 * @return
	 */
	FcsBaseResult saveContractBack(ProjectContractItemOrder order);
	
	/**
	 * 根据ID查询合同
	 * @param contractId
	 * @return
	 */
	ProjectContractItemInfo findContractItemById(long id);
	
	/**
	 * 查询项目合同集
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectContractResultInfo> query(ProjectContractQueryOrder order);
	
	/**
	 * 修改合同状态
	 * @param order
	 * @return
	 */
	FcsBaseResult updateContractStatus(ProjectContractItemOrder order);
	
	/**
	 * 根据项目编码检查是否确认完
	 * @param projectCode
	 * @return
	 */
	long checkISConfirmAll(String projectCode);
	
	/**
	 * 根据项目编码查出没有确认合同
	 * @param projectCode
	 * @return
	 */
	List<ProjectContractResultInfo> searchIsConfirmAll(String projectCode);
	
	/**
	 * 根据合同编码查出没有确认合同
	 * @param contractCode
	 * @return
	 */
	ProjectContractItemInfo findByContractCode(String contractCode);
	
	/**
	 * 根据合同id查出合同历史审核记录
	 * @param contractCode
	 * @return
	 */
	ProjectContractCheckMessageResult findCheckMessageByContractItemId(	ProjectContractItemOrder order);
	
	/**
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult updateComtractItemUrl(ProjectContractItemOrder order);
}
