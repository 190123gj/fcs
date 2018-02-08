package com.born.fcs.rm.ws.service.report;

import javax.jws.WebService;

import com.born.fcs.pm.page.PageParam;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.ws.info.report.ReportRuleData;
import com.born.fcs.rm.ws.order.report.ReportQueryParam;
import com.born.fcs.rm.ws.order.report.ReportSqlQueryOrder;

/**
 * 自定义sql查询报表
 * */
@WebService
public interface ReportBySqlService {
	
	/**
	 * 获取某报表的 查询规则
	 * @param sqlId
	 * @return
	 */
	public ReportRuleData getQueryRule(long sqlId);
	
	/**
	 * 获取系列报表的 查询规则
	 * @param ReportSqlQueryOrder
	 * @return
	 */
	public QueryBaseBatchResult<ReportRuleData> getQueryRules(ReportSqlQueryOrder queryOrder);
	
	/**
	 * 获取当前用户的系列报表
	 * @param ReportSqlQueryOrder
	 * @return
	 */
	public QueryBaseBatchResult<ReportRuleData> getMyReport(ReportSqlQueryOrder queryOrder);
	
	/**
	 * 根据页“面录入查询条件” 和 “查询规则” 获取 生成Excel所需的数据对象List Map（String,String）
	 * @param queryParam 面录入查询条件
	 * @param rule 查询规则
	 * @return returnObject 存放返回数据需转换格式 格式 List<Map<String, String>>
	 */
	public FcsBaseResult queryReportData(ReportQueryParam queryParam, ReportRuleData rule);
	
	public String getQueryParam(ReportQueryParam queryParam, ReportRuleData rule);
	
	long addQueryRule(ReportRuleData ruleData) throws Exception;
	
	long udpateQueryRule(ReportRuleData ruleData) throws Exception;
	
	/**
	 * 为某个报表授权
	 * @param reportId
	 * @param roleIds
	 * @return
	 * @throws Exception
	 */
	long grant2Roles(long reportId, long[] roleIds) throws Exception;
	
	public int deleteQueryRule(long reportId);
	
	/**
	 * 
	 * @return returnObject 存放返回数据需转换格式 Page<Map<String, String>>
	 * **/
	public FcsBaseResult queryReportPage(ReportQueryParam queryParam, PageParam pageParam,
											ReportRuleData rule);
	
	public int refreshData();
	
}
