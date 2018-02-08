package com.born.fcs.pm.ws.service.risklevel;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.common.CollectionInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.risklevel.FRiskLevelInfo;
import com.born.fcs.pm.ws.info.risklevel.FRiskLevelScoreTemplateInfo;
import com.born.fcs.pm.ws.info.risklevel.RiskLevelInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.risklevel.FRiskLevelOrder;
import com.born.fcs.pm.ws.order.risklevel.RiskLevelQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 风险评级
 * 
 * @author lirz
 *
 * 2016-5-19 上午9:29:16
 */
@WebService
public interface RiskLevelService {
	/**
	 * 初评
	 * 
	 * @param order 不能为null
	 * @return 成功/失败
	 */
	FormBaseResult save(FRiskLevelOrder order);
	
	/**
	 * 根据表单id查找
	 * @param formId 表单id
	 * @return 没有数据返回null
	 */
	FRiskLevelInfo findByFormId(long formId);
	
	/**
	 * 查询解保申请列表
	 * 
	 * @param queryOrder 查询条件
	 * @return
	 */
	QueryBaseBatchResult<RiskLevelInfo> queryList(RiskLevelQueryOrder queryOrder);
	
	/**
	 * 获取评分模板数据
	 * @return
	 */
	List<FRiskLevelScoreTemplateInfo> queryTemplates();
	
	/**
	 * 查询净资产保障倍数和销售收入保障倍数 得分
	 * 
	 * @param projectCode
	 * @return
	 */
	CollectionInfo queryMultiple(String projectCode);
	
	/**
	 * 
	 * 项目选择列表
	 * 
	 * @param queryOrder
	 * @return
	 */
	QueryBaseBatchResult<ProjectInfo> queryProjects(ProjectQueryOrder queryOrder);
}
