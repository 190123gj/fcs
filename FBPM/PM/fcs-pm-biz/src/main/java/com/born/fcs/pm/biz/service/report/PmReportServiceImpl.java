package com.born.fcs.pm.biz.service.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseAutowiredDAOService;
import com.born.fcs.pm.daointerface.ReportDao;
import com.born.fcs.pm.report.project.ProjectMakeReportBuilder;
import com.born.fcs.pm.report.project.ProjectMakeReportOrder;
import com.born.fcs.pm.report.project.ProjectMakeReportResult;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.service.report.PmReportService;
import com.born.fcs.pm.ws.service.report.order.LoadReportProjectOrder;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import com.yjf.common.lang.util.ListUtil;

@Service("pmReportService")
public class PmReportServiceImpl extends BaseAutowiredDAOService implements PmReportService {
	
	@Autowired
	ReportDao reportDao;
	
	@Override
	public ReportDataResult getReporData(LoadReportProjectOrder loadReportProjectOrder) {
		ReportDataResult dataResult = new ReportDataResult();
		ProjectMakeReportOrder makeReportOrder = new ProjectMakeReportOrder();
		makeReportOrder.setFcsFields(loadReportProjectOrder.getFcsFields());
		makeReportOrder.setHaving(loadReportProjectOrder.getHaving());
		makeReportOrder.setOrderBy(loadReportProjectOrder.getOrderBy());
		makeReportOrder.setSearchHisData(loadReportProjectOrder.isSearchHisData());
		makeReportOrder.setWhereSql(loadReportProjectOrder.getWhereSql());
		makeReportOrder.setHisDataDeadline(loadReportProjectOrder.getHisDataDeadline());
		makeReportOrder.setHisDataDeadlineArray(loadReportProjectOrder.getHisDataDeadlineArray());
		ProjectMakeReportResult makeReportResult = ProjectMakeReportBuilder
			.makeReportSql(makeReportOrder);
		
		long count = 0;
		if (loadReportProjectOrder.isNeedPage()) {
			count = doQueryCount(makeReportResult.getSql());
			PageComponent component = new PageComponent(loadReportProjectOrder, count);
			PmReportDOQueryOrder qOrder = new PmReportDOQueryOrder();
			qOrder.setSql(makeReportResult.getSql());
			qOrder.setLimitStart(component.getFirstRecord());
			qOrder.setPageSize(component.getPageSize());
			qOrder.setFieldMap(makeReportResult.getFieldMap());
			List<DataListItem> maps = doQuery(qOrder);
			dataResult.setDataList(maps);
			dataResult.setPageNumber(component.getCurPage());
			dataResult.setPageSize(component.getPageSize());
			dataResult.setFcsFields(makeReportResult.getListField());
			dataResult.setTotalCount(component.getRowCount());
			
		} else {
			
			if (ListUtil.isNotEmpty(makeReportResult.getSqlArray())) {
				int i = 0;
				for (String sql : makeReportResult.getSqlArray()) {
					PmReportDOQueryOrder qOrder = new PmReportDOQueryOrder();
					qOrder.setSql(sql);
					qOrder.setFieldMap(makeReportResult.getFieldMap());
					List<DataListItem> maps = doQuery(qOrder);
					if (i == 0) {
						dataResult.setDataList(maps);
					}
					if (i == 1) {
						dataResult.setDataList2(maps);
					}
					if (i == 2) {
						dataResult.setDataList3(maps);
					}
					if (i == 3) {
						dataResult.setDataList4(maps);
						break;
					}
					i++;
				}
				
			} else {
				PmReportDOQueryOrder qOrder = new PmReportDOQueryOrder();
				qOrder.setSql(makeReportResult.getSql());
				qOrder.setFieldMap(makeReportResult.getFieldMap());
				List<DataListItem> maps = doQuery(qOrder);
				dataResult.setDataList(maps);
			}
			dataResult.setFcsFields(makeReportResult.getListField());
		}
		dataResult.setSuccess(true);
		return dataResult;
	}
	
	/**
	 * 根据合成的SQL 查询 数据
	 * @param sql
	 * @return
	 */
	@Override
	public List<DataListItem> doQuery(PmReportDOQueryOrder order) {
		if (order == null)
			logger.error("doQuery() 查询失败！参数为空");
		String sql = order.getSql();
		long pageSize = order.getPageSize();
		long limitStart = order.getLimitStart();
		logger.info("Excel report sql:" + sql);
		String sqlStr = sql.toUpperCase();
		if (sqlStr.indexOf("DELETE ") >= 0 || sqlStr.indexOf("UPDATE ") >= 0
			|| sqlStr.indexOf("TRUNCATE ") >= 0 || sqlStr.indexOf("INSERT ") >= 0
			|| sqlStr.indexOf("DROP ") >= 0) {
			logger.error("doQuery() 查询失败！SQL 注入风险，放弃执行");
			return null;
		}
		if (pageSize > 0) {
			sql = sql + " LIMIT " + limitStart + "," + pageSize;
		}
		return reportDao.query(sql, order.getFieldMap());
		
	}
	
	@Override
	public long doQueryCount(String sql) {
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
		return reportDao.queryCount(countSql);
	}
}
