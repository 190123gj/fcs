package com.born.fcs.rm.biz.service.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.pm.page.Page;
import com.born.fcs.pm.page.PageParam;
import com.born.fcs.pm.page.PageParamUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDAOService;
import com.born.fcs.rm.dal.dataobject.ReportRuleDO;
import com.born.fcs.rm.ws.info.report.ReportRuleData;
import com.born.fcs.rm.ws.order.report.ReportQueryParam;
import com.born.fcs.rm.ws.order.report.ReportSqlQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportBySqlService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.StringUtil;

@Service("reportBySqlService")
public class ReportBySqlServiceImpl extends BaseAutowiredDAOService implements ReportBySqlService {
	
	@Override
	public ReportRuleData getQueryRule(long sqlId) {
		ReportRuleData reportRuleData = new ReportRuleData();
		ReportRuleDO queryRule = reportRuleDao.findById(sqlId);
		if (queryRule != null) {
			BeanCopier.staticCopy(queryRule, reportRuleData);
			return reportRuleData;
		} else {
			return null;
		}
		
	}
	
	@Override
	public QueryBaseBatchResult<ReportRuleData> getMyReport(ReportSqlQueryOrder queryOrder) {
		return null;
	}
	
	@Override
	public FcsBaseResult queryReportData(ReportQueryParam queryParam, ReportRuleData rule) {
		FcsBaseResult result = new FcsBaseResult();
		result.setSuccess(true);
		ReportRuleDO queryRuleDO = new ReportRuleDO();
		BeanCopier.staticCopy(rule, queryRuleDO);
		String sql = creatSql(queryParam, queryRuleDO);
		result.setReturnObject(getJSONString(doQuery(sql)));
		return result;
	}
	
	@Override
	public long addQueryRule(ReportRuleData ruleData) throws Exception {
		ReportRuleDO queryRuleDO = new ReportRuleDO();
		ruleData.check();
		BeanCopier.staticCopy(ruleData, queryRuleDO);
		long reportId = reportRuleDao.insert(queryRuleDO);
		return reportId;
	}
	
	@Override
	public long udpateQueryRule(ReportRuleData ruleData) throws Exception {
		ReportRuleDO queryRuleDO = new ReportRuleDO();
		ruleData.check();
		BeanCopier.staticCopy(ruleData, queryRuleDO);
		reportRuleDao.update(queryRuleDO);
		return queryRuleDO.getReportId();
	}
	
	@Override
	public long grant2Roles(long reportId, long[] roleIds) throws Exception {
		return 0;
	}
	
	@Override
	public int deleteQueryRule(long reportId) {
		return reportRuleDao.deleteById(reportId);
		
	}
	
	@Override
	public int refreshData() {
		return 0;
	}
	
	@Override
	public QueryBaseBatchResult<ReportRuleData> getQueryRules(ReportSqlQueryOrder reportQueryOrder) {
		QueryBaseBatchResult<ReportRuleData> batchResult = new QueryBaseBatchResult<ReportRuleData>();
		
		try {
			reportQueryOrder.check();
			List<ReportRuleData> pageList = new ArrayList<ReportRuleData>(
				(int) reportQueryOrder.getPageSize());
			
			ReportRuleDO reportRule = new ReportRuleDO();
			reportRule.setReportName(reportQueryOrder.getReportName());
			reportRule.setNote(reportQueryOrder.getNote());
			long reportId = 0;
			if (reportQueryOrder.getReportId() != null
				&& !"".equals(reportQueryOrder.getReportId())) {
				reportId = Long.parseLong(reportQueryOrder.getReportId());
			}
			reportRule.setReportId(reportId);
			
			long totalCount = 0;
			PageComponent component = null;
			List<ReportRuleDO> recordList = null;
			
			totalCount = reportRuleDao.findByConditionCount(reportRule);
			component = new PageComponent(reportQueryOrder, totalCount);
			recordList = reportRuleDao.findByCondition(reportRule, component.getFirstRecord(),
				component.getPageSize());
			
			for (ReportRuleDO item : recordList) {
				ReportRuleData rule = new ReportRuleData();
				BeanCopier.staticCopy(item, rule);
				pageList.add(rule);
			}
			batchResult.initPageParam(component);
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
		} catch (IllegalArgumentException e) {
			batchResult.setSuccess(false);
			batchResult.setMessage(e.getMessage());
			logger.error(e.getLocalizedMessage(), e);
		} catch (Exception e) {
			batchResult.setSuccess(false);
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return batchResult;
		
	}
	
	@Override
	public FcsBaseResult queryReportPage(ReportQueryParam queryParam, PageParam pageParam,
											ReportRuleData rule) {
		
		long limitStart = (pageParam.getPageNo() - 1) * pageParam.getPageSize();
		long pageSize = pageParam.getPageSize();
		long totalSize = this.queryReportCount(queryParam, rule);
		List<Map<String, String>> rsList = this.queryReportList(queryParam, rule, limitStart,
			pageSize);
		
		if (totalSize == 0 && rsList.size() > 0) { //调用存储过程的结果特殊处理
			totalSize = rsList.size();
			pageSize = rsList.size();
		}
		
		int start = PageParamUtil
			.startValue((int) totalSize, (int) pageSize, pageParam.getPageNo());
		Page<Map<String, String>> pageActivityDetail = new Page<Map<String, String>>(start,
			totalSize, pageParam.getPageSize(), rsList);
		
		FcsBaseResult result = new FcsBaseResult();
		result.setSuccess(true);
		result.setReturnObject(getJSONString(pageActivityDetail));
		return result;
		
	}
	
	public List<Map<String, String>> queryReportList(ReportQueryParam queryParam,
														ReportRuleData rule, long limitStart,
														long pageSize) {
		
		ReportRuleDO queryRuleDO = new ReportRuleDO();
		BeanCopier.staticCopy(rule, queryRuleDO);
		String sql = creatSql(queryParam, queryRuleDO);
		return doQueryList(sql, limitStart, pageSize);
	}
	
	private List<Map<String, String>> doQueryList(String sql, long limitStart, long pageSize) {
		logger.debug("Excel report sql :" + sql);
		String sqlStr = sql.toUpperCase();
		if (sqlStr.indexOf("DELETE ") >= 0 || sqlStr.indexOf("UPDATE ") >= 0
			|| sqlStr.indexOf("INSERT ") >= 0 || sqlStr.indexOf("DROP ") >= 0
			|| sqlStr.indexOf("TRUNCATE") >= 0) {
			logger.error("doQuery() 查询失败！SQL 注入风险，放弃执行");
			return null;
		}
		String limitSql = "";
		if (sqlStr.indexOf("CALL ") >= 0) {//调用存储过程
			limitSql = sql;
		} else {
			limitSql = "select outerTmp.* from (" + sql + ")outerTmp   LIMIT " + limitStart + ","
						+ pageSize;
		}
		return reportSqlDao.query(limitSql);
	}
	
	public long queryReportCount(ReportQueryParam queryParam, ReportRuleData rule) {
		
		ReportRuleDO queryRuleDO = new ReportRuleDO();
		BeanCopier.staticCopy(rule, queryRuleDO);
		String sql = creatSql(queryParam, queryRuleDO);
		return doQueryCount(sql);
	}
	
	/**
	 * 根据合成的SQL 查询 数据
	 * @param sql
	 * @return
	 */
	private List<Map<String, String>> doQuery(String sql) {
		logger.info("Excel report sql:" + sql);
		String sqlStr = sql.toUpperCase();
		if (sqlStr.indexOf("DELETE ") >= 0 || sqlStr.indexOf("UPDATE ") >= 0
			|| sqlStr.indexOf("TRUNCATE ") >= 0 || sqlStr.indexOf("INSERT ") >= 0
			|| sqlStr.indexOf("DROP ") >= 0) {
			logger.error("doQuery() 查询失败！SQL 注入风险，放弃执行");
			return null;
		}
		return reportSqlDao.query(sql);
		
	}
	
	private long doQueryCount(String sql) {
		logger.debug("Excel report sql :" + sql);
		String sqlStr = sql.toUpperCase();
		if (sqlStr.indexOf("DELETE ") >= 0 || sqlStr.indexOf("UPDATE ") >= 0
			|| sqlStr.indexOf("INSERT ") >= 0 || sqlStr.indexOf("DROP ") >= 0
			|| sqlStr.indexOf("TRUNCATE ") >= 0) {
			logger.error("doQuery() 查询失败！SQL 注入风险，放弃执行");
			return 0;
		}
		if (sqlStr.indexOf("CALL ") >= 0) {//调用存储过程
			return 0;
		}
		String countSql = "select count(1) ounterCount from (" + sql + ")outerTmp";
		return reportSqlDao.queryCount(countSql);
	}
	
	private String creatSql(ReportQueryParam queryParam, ReportRuleDO rule) {
		String sql = rule.getSqlStr();
		
		logger.debug("befor sql:" + sql);
		
		String filterSql1 = rule.getFilter1Sql();
		if (filterSql1 != null && StringUtil.isNotEmpty(queryParam.getFilter1Value())) {
			filterSql1 = filterSql1.replaceAll("\\?", queryParam.getFilter1Value());
			sql = sql.replaceAll("/\\*AND1\\*/", filterSql1);
		}
		
		String filterSql2 = rule.getFilter2Sql();
		if (filterSql2 != null && StringUtil.isNotEmpty(queryParam.getFilter2Value())) {
			filterSql2 = filterSql2.replaceAll("\\?", queryParam.getFilter2Value());
			sql = sql.replaceAll("/\\*AND2\\*/", filterSql2);
		}
		
		String filterSql3 = rule.getFilter3Sql();
		if (filterSql3 != null && StringUtil.isNotEmpty(queryParam.getFilter3Value())) {
			filterSql3 = filterSql3.replaceAll("\\?", queryParam.getFilter3Value());
			sql = sql.replaceAll("/\\*AND3\\*/", filterSql3);
		}
		
		String filterSql4 = rule.getFilter4Sql();
		if (filterSql4 != null && StringUtil.isNotEmpty(queryParam.getFilter4Value())) {
			filterSql4 = filterSql4.replaceAll("\\?", queryParam.getFilter4Value());
			sql = sql.replaceAll("/\\*AND4\\*/", filterSql4);
		}
		
		String filterSql5 = rule.getFilter5Sql();
		if (filterSql5 != null && StringUtil.isNotEmpty(queryParam.getFilter5Value())) {
			filterSql5 = filterSql5.replaceAll("\\?", queryParam.getFilter5Value());
			sql = sql.replaceAll("/\\*AND5\\*/", filterSql5);
		}
		
		String filterSql6 = rule.getFilter6Sql();
		if (filterSql6 != null && StringUtil.isNotEmpty(queryParam.getFilter6Value())) {
			filterSql6 = filterSql6.replaceAll("\\?", queryParam.getFilter6Value());
			sql = sql.replaceAll("/\\*AND6\\*/", filterSql6);
		}
		
		logger.info(" Report sql:" + sql);
		
		return sql;
	}
	
	@Override
	public String getQueryParam(ReportQueryParam queryParam, ReportRuleData rule) {
		StringBuffer filters = new StringBuffer("");
		if (rule.getFilter1Sql() != null && StringUtil.isNotEmpty(queryParam.getFilter1Value())) {
			filters.append(rule.getFilter1Name() + "  " + queryParam.getFilter1Value());
		}
		
		if (rule.getFilter2Sql() != null && StringUtil.isNotEmpty(queryParam.getFilter2Value())) {
			filters.append(rule.getFilter2Name() + "  " + queryParam.getFilter2Value());
		}
		
		if (rule.getFilter3Sql() != null && StringUtil.isNotEmpty(queryParam.getFilter3Value())) {
			filters.append(rule.getFilter3Name() + "  " + queryParam.getFilter3Value());
		}
		
		if (rule.getFilter4Sql() != null && StringUtil.isNotEmpty(queryParam.getFilter4Value())) {
			filters.append(rule.getFilter4Name() + "  " + queryParam.getFilter4Value());
		}
		
		if (rule.getFilter5Sql() != null && StringUtil.isNotEmpty(queryParam.getFilter5Value())) {
			filters.append(rule.getFilter5Name() + "  " + queryParam.getFilter5Value());
		}
		
		if (rule.getFilter6Sql() != null && StringUtil.isNotEmpty(queryParam.getFilter6Value())) {
			filters.append(rule.getFilter6Name() + "  " + queryParam.getFilter6Value());
		}
		
		return filters.toString();
	}
	
	/** 转换成jsonString */
	private String getJSONString(Object obj) {
		JSONObject json = new JSONObject();
		json.put("data", obj);
		return json.toJSONString();
	}
	
}
