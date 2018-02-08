package com.born.fcs.pm.ws.service.common;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.enums.ChannelRelationEnum;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationAmountOrder;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationBatchOrder;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@WebService
public interface ProjectChannelRelationService {
	
	/**
	 * 根据表单保存（ 根据表单ID和类型先删除再新增）
	 * @param order
	 * @return
	 */
	FcsBaseResult saveByBizNoAndType(ProjectChannelRelationBatchOrder order);
	
	/**
	 * 更新渠道相关金额
	 * @param order
	 * @return
	 */
	FcsBaseResult updateRelatedAmount(ProjectChannelRelationAmountOrder order);
	
	/**
	 * 根据表单和类型查询
	 * @param formId
	 * @param type
	 * @return
	 */
	List<ProjectChannelRelationInfo> queryByBizNoAndType(String bizNo, ChannelRelationEnum type);
	
	/**
	 * 查询项目资金渠道
	 * @param projectCode
	 * @return
	 */
	List<ProjectChannelRelationInfo> queryCapitalChannel(String projectCode);
	
	/**
	 * 查询项目渠道
	 * @param projectCode
	 * @return
	 */
	ProjectChannelRelationInfo queryProjectChannel(String projectCode);
	
	/**
	 * 根据条件查询
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectChannelRelationInfo> query(ProjectChannelRelationQueryOrder order);
	
}
