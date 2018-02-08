package com.born.fcs.pm.ws.service.common;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.enums.ProjectExtendPropertyEnum;
import com.born.fcs.pm.ws.info.common.ProjectExtendInfo;
import com.born.fcs.pm.ws.order.common.ProjectExtendFormOrder;
import com.born.fcs.pm.ws.order.common.ProjectExtendOrder;
import com.born.fcs.pm.ws.order.common.ProjectExtendQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 项目扩展属性service
 *
 * @author wuzj
 */
@WebService
public interface ProjectExtendService {
	
	/**
	 * 保存扩展信息
	 * @param order
	 * @return
	 */
	FcsBaseResult saveExtendInfo(ProjectExtendOrder order);
	
	/**
	 * 保存扩展数据-只能存一个
	 * @param order
	 * @return
	 */
	FormBaseResult saveExtendInfoWithForm(ProjectExtendFormOrder order);
	
	/**
	 * 查询项目扩展信息
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectExtendInfo> query(ProjectExtendQueryOrder order);
	
	/**
	 * 查询已通过审批的扩展属性
	 * @param order
	 * @return
	 */
	List<ProjectExtendInfo> findApprovalProjectExtendInfo(ProjectExtendQueryOrder order);
	
	/**
	 * 删除项目所有扩展属性
	 * @param projectCode
	 * @return
	 */
	FcsBaseResult deleteByProjectCode(String projectCode);
	
	/**
	 * 根据属性删除
	 * @param property
	 * @return
	 */
	FcsBaseResult deleteByProjectCodeAndProperty(String projectCode,
													ProjectExtendPropertyEnum property);
}
