package com.born.fcs.pm.ws.service.common;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.enums.ProjectTransferStatusEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.transfer.ProjectTransferDetailFormInfo;
import com.born.fcs.pm.ws.info.transfer.ProjectTransferDetailInfo;
import com.born.fcs.pm.ws.order.common.TransferProjectOrder;
import com.born.fcs.pm.ws.order.transfer.ProjectTransferAcceptOrder;
import com.born.fcs.pm.ws.order.transfer.ProjectTransferDetailOrder;
import com.born.fcs.pm.ws.order.transfer.ProjectTransferDetailQueryOrder;
import com.born.fcs.pm.ws.order.transfer.ProjectTransferOrder;
import com.born.fcs.pm.ws.order.transfer.ProjectTransferSelectOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 项目移交Service
 * @author wuzj
 */
@WebService
public interface ProjectTransferService {
	
	/**
	 * 原项目列表中项目移交[客户经理、风险经理、法务经理]
	 * @see ProjectService.transferProject
	 * @param order
	 * @return
	 */
	FcsBaseResult transferProject(TransferProjectOrder order);
	
	/**
	 * 移交到法务部申请
	 * @return
	 */
	FormBaseResult saveTransferApply(ProjectTransferOrder order);
	
	/**
	 * 选择移交的A角B角
	 * @param order
	 * @return
	 */
	FcsBaseResult setAcceptUser(ProjectTransferAcceptOrder order);
	
	/**
	 * 项目移交明细记录
	 * @return
	 */
	FcsBaseResult logTransferDetail(ProjectTransferDetailOrder order);
	
	/**
	 * 项目移交明细
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectTransferDetailFormInfo> queryDetail(ProjectTransferDetailQueryOrder order);
	
	/**
	 * 可移交项目粗选
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectInfo> selectProject(ProjectTransferSelectOrder order);
	
	/**
	 * 根据formId查询
	 * @param formId
	 * @return
	 */
	List<ProjectTransferDetailInfo> queryByFormId(long formId);
	
	/**
	 * 修改移交状态
	 * @param status
	 * @param formId
	 * @return
	 */
	FcsBaseResult updateStatusByFormId(ProjectTransferStatusEnum status, long formId);
	
	/**
	 * 项目移交
	 * @param formId
	 * @return
	 */
	FcsBaseResult doTransfer(long formId);
	
	/**
	 * 根据项目编号查询
	 * @param projectCode
	 * @return
	 */
	List<ProjectTransferDetailInfo> queryByProjectCode(String projectCode);
}
