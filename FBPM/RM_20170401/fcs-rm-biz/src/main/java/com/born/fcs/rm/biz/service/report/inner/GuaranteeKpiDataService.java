package com.born.fcs.rm.biz.service.report.inner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.integration.service.pm.PmReportServiceClient;
import com.born.fcs.rm.ws.info.report.inner.GuaranteeKpiDataInfo;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.money.Money;

/**
 * 担保业务主要指标汇总表
 * @author wuzj
 */
@Service("guaranteeKpiDataService")
public class GuaranteeKpiDataService extends BaseAutowiredDomainService implements
																		ReportProcessService {
	
	@Autowired
	private PmReportServiceClient pmReportServiceClient;
	
	@Override
	public FcsBaseResult save(ReportOrder order) {
		return null;
	}
	
	/**
	 * 统计业务范围：融资担保业务
	 */
	@Override
	public Object findByAccountPeriod(ReportQueryOrder order) {
		QueryBaseBatchResult<GuaranteeKpiDataInfo> result = new QueryBaseBatchResult<GuaranteeKpiDataInfo>();
		List<GuaranteeKpiDataInfo> data = new ArrayList<GuaranteeKpiDataInfo>();
		try {
			//年度目标
			yearGoal(order, data);
			//发生额
			occurAmount(order, data);
			//解保额
			releaseAmount(order, data);
			//在保余额
			balanceAmount(order, data);
			//收入
			incomeAmount(order, data);
			
			GuaranteeKpiDataInfo dept1 = null; //业务1部
			GuaranteeKpiDataInfo dept1hn = null; //湖南代表处
			GuaranteeKpiDataInfo dept2 = null; //业务2部
			GuaranteeKpiDataInfo dept2yn = null; //云南代表处
			
			int hnIndex = 0;
			int ynIndex = 0;
			int index = 0;
			for (GuaranteeKpiDataInfo info : data) {
				if (info.getDept().indexOf("湖南") != -1) {
					info.setDept("其中：" + info.getDept());
					dept1hn = info;
					hnIndex = index;
				} else if (info.getDept().indexOf("云南") != -1) {
					info.setDept("其中：" + info.getDept());
					dept2yn = info;
					ynIndex = index;
				} else if (info.getDept().indexOf("一部") != -1) {
					dept1 = info;
				} else if (info.getDept().indexOf("二部") != -1) {
					dept2 = info;
				}
				index++;
			}
			boolean reportAll = true;
			//湖南代表处累加到一部，云南代表处
			for (GuaranteeKpiDataInfo info : data) {
				if (info.getIncomeGoal().getCent() == 0 || info.getBalanceGoal().getCent() == 0
					|| info.getIncreaseGoal().getCent() == 0)
					reportAll = false;
				if (info.getDept().indexOf("一部") != -1 && dept1hn != null) {
					info.getBalanceEnding().addTo(dept1hn.getBalanceEnding());
					info.getBalanceGoal().addTo(dept1hn.getBalanceGoal());
					info.getBalanceInitial().addTo(dept1hn.getBalanceInitial());
					info.getIncomeGoal().addTo(dept1hn.getIncomeGoal());
					info.getIncomeMonth().addTo(dept1hn.getIncomeMonth());
					info.getIncomeYear().addTo(dept1hn.getIncomeYear());
					info.getIncreaseGoal().addTo(dept1hn.getIncreaseGoal());
					info.getIncreaseYear().addTo(dept1hn.getIncreaseYear());
					info.getOccurMonth().addTo(dept1hn.getOccurMonth());
					info.getOccurYear().addTo(dept1hn.getOccurYear());
					info.getReleaseMonth().addTo(dept1hn.getReleaseMonth());
					info.getReleaseYear().addTo(dept1hn.getReleaseYear());
				} else if (info.getDept().indexOf("二部") != -1 && dept2yn != null) {
					info.getBalanceEnding().addTo(dept2yn.getBalanceEnding());
					info.getBalanceGoal().addTo(dept2yn.getBalanceGoal());
					info.getBalanceInitial().addTo(dept2yn.getBalanceInitial());
					info.getIncomeGoal().addTo(dept2yn.getIncomeGoal());
					info.getIncomeMonth().addTo(dept2yn.getIncomeMonth());
					info.getIncomeYear().addTo(dept2yn.getIncomeYear());
					info.getIncreaseGoal().addTo(dept2yn.getIncreaseGoal());
					info.getIncreaseYear().addTo(dept2yn.getIncreaseYear());
					info.getOccurMonth().addTo(dept2yn.getOccurMonth());
					info.getOccurYear().addTo(dept2yn.getOccurYear());
					info.getReleaseMonth().addTo(dept2yn.getReleaseMonth());
					info.getReleaseYear().addTo(dept2yn.getReleaseYear());
				}
				
			}
			//只有湖南代表处新建一部
			if (dept1hn != null && dept1 == null) {
				dept1 = new GuaranteeKpiDataInfo();
				BeanCopier.staticCopy(dept1hn, dept1);
				dept1.setDept("业务发展一部");
				data.add(hnIndex, dept1);
			}
			//只有云南代表处新建二部
			if (dept2yn != null && dept2 == null) {
				dept2 = new GuaranteeKpiDataInfo();
				BeanCopier.staticCopy(dept2yn, dept2);
				dept2.setDept("业务发展二部");
				data.add(ynIndex, dept2);
			}
			
			//重新排序
			Collections.sort(data, new Comparator<GuaranteeKpiDataInfo>() {
				@Override
				public int compare(GuaranteeKpiDataInfo o1, GuaranteeKpiDataInfo o2) {
					if (o1.getDeptCode() == null)
						return 1;
					if (o2.getDeptCode() == null)
						return -1;
					return o1.getDeptCode().compareToIgnoreCase(o2.getDeptCode());
				}
			});
			
			result.setSuccess(true);
			result.setPageList(data);
			result.setTotalCount(data.size());
			result.setSortCol(reportAll ? "IS" : "NO");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("担保业务主要指标汇总表出错");
			logger.error("查询担保业务主要指标汇总表出错：{}", e);
		}
		
		return result;
	}
	
	/**
	 * 发生额
	 * @param order
	 * @param data
	 */
	private void occurAmount(ReportQueryOrder order, List<GuaranteeKpiDataInfo> data) {
		
		String sql = "SELECT p.dept_code,p.dept_name,SUM(r.occur_amount) amount "
						+ "FROM ${pmDbTitle}.view_project_actual_occer_detail r JOIN ${pmDbTitle}.project p ON r.project_code = p.project_code "
						+ "WHERE (p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%') "
						+ "AND r.occur_date >= '${loanStartTime}' AND occur_date <= '${loanEndTime}' GROUP BY p.dept_name ORDER BY p.dept_code";
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		
		if (data == null)
			data = new ArrayList<GuaranteeKpiDataInfo>();
		
		String thisYearTotalSql = sql.replaceAll("\\$\\{loanStartTime\\}",
			order.getReportYearStartDay()).replaceAll("\\$\\{loanEndTime\\}",
			order.getReportMonthEndDay());
		
		//本年累计
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(thisYearTotalSql);
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		Money thisYearTotal = Money.zero();
		if (dataResult != null) {
			
			for (DataListItem itm : dataResult) {
				String deptName = String.valueOf(itm.getMap().get("dept_name"));
				String deptCode = String.valueOf(itm.getMap().get("dept_code"));
				Money amount = toMoney(itm.getMap().get("amount"));
				GuaranteeKpiDataInfo dataInfo = null;
				for (GuaranteeKpiDataInfo info : data) {
					if (StringUtil.equals(info.getDeptCode(), deptCode)) {
						dataInfo = info;
						break;
					}
				}
				if (dataInfo == null) {
					dataInfo = new GuaranteeKpiDataInfo();
					dataInfo.setDept(deptName);
					dataInfo.setDeptCode(deptCode);
					data.add(dataInfo);
				}
				//本年累计发生额
				dataInfo.setOccurYear(amount);
				thisYearTotal.addTo(amount);
			}
		}
		
		//本月sql
		sql = sql.replaceAll("\\$\\{loanStartTime\\}", order.getReportMonthStartDay()).replaceAll(
			"\\$\\{loanEndTime\\}", order.getReportMonthEndDay());
		
		//查询
		queyOrder.setSql(sql);
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		Money thisMonthTotal = Money.zero();
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String deptName = String.valueOf(itm.getMap().get("dept_name"));
				String deptCode = String.valueOf(itm.getMap().get("dept_code"));
				Money amount = toMoney(itm.getMap().get("amount"));
				GuaranteeKpiDataInfo dataInfo = null;
				for (GuaranteeKpiDataInfo info : data) {
					if (StringUtil.equals(info.getDeptCode(), deptCode)) {
						dataInfo = info;
						break;
					}
				}
				if (dataInfo == null) {
					dataInfo = new GuaranteeKpiDataInfo();
					dataInfo.setDept(deptName);
					dataInfo.setDeptCode(deptCode);
					data.add(dataInfo);
				}
				//本月发生额
				dataInfo.setOccurMonth(amount);
				thisMonthTotal.addTo(amount);
			}
		}
		
		GuaranteeKpiDataInfo total = null;
		for (GuaranteeKpiDataInfo info : data) {
			if (StringUtil.equals(info.getDept(), "合计")) {
				total = info;
				break;
			}
		}
		if (total == null) {
			total = new GuaranteeKpiDataInfo();
			total.setDept("合计");
			data.add(total);
		}
		
		total.setOccurYear(thisYearTotal);
		total.setOccurMonth(thisMonthTotal);
	}
	
	/**
	 * 解保额
	 * @param order
	 * @param data
	 */
	private void releaseAmount(ReportQueryOrder order, List<GuaranteeKpiDataInfo> data) {
		
		String sql = "SELECT p.dept_code,p.dept_name,SUM(r.repay_amount) amount "
						+ "FROM ${pmDbTitle}.view_project_repay_detail r JOIN ${pmDbTitle}.project p ON r.project_code = p.project_code "
						+ "WHERE r.repay_type in ('解保','诉保解保','代偿') AND (p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%') AND r.repay_time >= '${releaseStartTime}' AND r.repay_time <= '${releaseEndTime}' GROUP BY p.dept_name ORDER BY p.dept_code";
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		
		if (data == null)
			data = new ArrayList<GuaranteeKpiDataInfo>();
		
		String thisYearTotalSql = sql.replaceAll("\\$\\{releaseStartTime\\}",
			order.getReportYearStartDay()).replaceAll("\\$\\{releaseEndTime\\}",
			order.getReportMonthEndDay());
		
		//本年累计
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(thisYearTotalSql);
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		Money thisYearTotal = Money.zero();
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String deptName = String.valueOf(itm.getMap().get("dept_name"));
				String deptCode = String.valueOf(itm.getMap().get("dept_code"));
				Money amount = toMoney(itm.getMap().get("amount"));
				GuaranteeKpiDataInfo dataInfo = null;
				for (GuaranteeKpiDataInfo info : data) {
					if (StringUtil.equals(info.getDeptCode(), deptCode)) {
						dataInfo = info;
						break;
					}
				}
				if (dataInfo == null) {
					dataInfo = new GuaranteeKpiDataInfo();
					dataInfo.setDept(deptName);
					dataInfo.setDeptCode(deptCode);
					data.add(dataInfo);
				}
				//本年累计解保额
				dataInfo.setReleaseYear(amount);
				thisYearTotal.addTo(amount);
			}
		}
		
		//本月sql
		sql = sql.replaceAll("\\$\\{releaseStartTime\\}", order.getReportMonthStartDay())
			.replaceAll("\\$\\{releaseEndTime\\}", order.getReportMonthEndDay());
		
		//查询
		queyOrder.setSql(sql);
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		Money thisMonthTotal = Money.zero();
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String deptName = String.valueOf(itm.getMap().get("dept_name"));
				String deptCode = String.valueOf(itm.getMap().get("dept_code"));
				Money amount = toMoney(itm.getMap().get("amount"));
				GuaranteeKpiDataInfo dataInfo = null;
				for (GuaranteeKpiDataInfo info : data) {
					if (StringUtil.equals(info.getDeptCode(), deptCode)) {
						dataInfo = info;
						break;
					}
				}
				if (dataInfo == null) {
					dataInfo = new GuaranteeKpiDataInfo();
					dataInfo.setDept(deptName);
					dataInfo.setDeptCode(deptCode);
					data.add(dataInfo);
				}
				//本月解保额
				dataInfo.setReleaseMonth(amount);
				thisMonthTotal.addTo(amount);
			}
		}
		
		GuaranteeKpiDataInfo total = null;
		for (GuaranteeKpiDataInfo info : data) {
			if (StringUtil.equals(info.getDept(), "合计")) {
				total = info;
				break;
			}
		}
		if (total == null) {
			total = new GuaranteeKpiDataInfo();
			total.setDept("合计");
			data.add(total);
		}
		total.setReleaseYear(thisYearTotal);
		total.setReleaseMonth(thisMonthTotal);
	}
	
	/**
	 * 在保余额
	 * @param order
	 * @param data
	 */
	private void balanceAmount(ReportQueryOrder order, List<GuaranteeKpiDataInfo> data) {
		
		//实时数据
		String sql = "SELECT dept_code,dept_name,SUM(blance) amount FROM ${pmDbTitle}.project_data_info "
						+ "WHERE (busi_type like '1%' or busi_type like '2%' or busi_type like '3%') AND  dept_id > 0 GROUP BY dept_name ORDER BY dept_code";
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		
		//历史数据
		String hisSql = "SELECT dept_code,dept_name,SUM(blance) amount FROM ${pmDbTitle}.project_data_info_his_data "
						+ "WHERE (busi_type like '1%' or busi_type like '2%' or busi_type like '3%') AND dept_id > 0 AND project_date='${projectDate}' GROUP BY dept_name ORDER BY dept_code";
		hisSql = hisSql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		
		if (data == null)
			data = new ArrayList<GuaranteeKpiDataInfo>();
		
		//期初余额
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		
		if (order.isThisYearFirstDay()) {//今年第一天查询实时数据
			queyOrder.setSql(sql);
		} else {
			//查询报告期第一天数据
			queyOrder.setSql(hisSql.replaceAll("\\$\\{projectDate\\}",
				order.getReportYearStartDay()));
		}
		
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		Money beginTotal = Money.zero();
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String deptName = String.valueOf(itm.getMap().get("dept_name"));
				String deptCode = String.valueOf(itm.getMap().get("dept_code"));
				Money amount = toMoney(itm.getMap().get("amount"));
				GuaranteeKpiDataInfo dataInfo = null;
				for (GuaranteeKpiDataInfo info : data) {
					if (StringUtil.equals(info.getDeptCode(), deptCode)) {
						dataInfo = info;
						break;
					}
				}
				if (dataInfo == null) {
					dataInfo = new GuaranteeKpiDataInfo();
					dataInfo.setDept(deptName);
					dataInfo.setDeptCode(deptCode);
					data.add(dataInfo);
				}
				//期初余额
				dataInfo.setBalanceInitial(amount);
				beginTotal.addTo(amount);
			}
		}
		
		//期末余额
		if (order.isThisMonth()) {
			//本月就查实时数据
			queyOrder.setSql(sql);
		} else {
			//查询报告期最后一天的数据
			queyOrder
				.setSql(hisSql.replaceAll("\\$\\{projectDate\\}", order.getReportMonthEndDay()));
		}
		
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		Money endTotal = Money.zero();
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String deptName = String.valueOf(itm.getMap().get("dept_name"));
				String deptCode = String.valueOf(itm.getMap().get("dept_code"));
				Money amount = toMoney(itm.getMap().get("amount"));
				GuaranteeKpiDataInfo dataInfo = null;
				for (GuaranteeKpiDataInfo info : data) {
					if (StringUtil.equals(info.getDeptCode(), deptCode)) {
						dataInfo = info;
						break;
					}
				}
				if (dataInfo == null) {
					dataInfo = new GuaranteeKpiDataInfo();
					dataInfo.setDept(deptName);
					dataInfo.setDeptCode(deptCode);
					data.add(dataInfo);
				}
				//期末在保余额
				dataInfo.setBalanceEnding(amount);
				endTotal.addTo(amount);
			}
		}
		
		GuaranteeKpiDataInfo total = null;
		for (GuaranteeKpiDataInfo info : data) {
			if (StringUtil.equals(info.getDept(), "合计")) {
				total = info;
				break;
			}
		}
		if (total == null) {
			total = new GuaranteeKpiDataInfo();
			total.setDept("合计");
			data.add(total);
		}
		total.setBalanceInitial(beginTotal);
		total.setBalanceEnding(endTotal);
	}
	
	/**
	 * 收入
	 * @param order
	 * @param data
	 */
	private void incomeAmount(ReportQueryOrder order, List<GuaranteeKpiDataInfo> data) {
		
		String sql = "SELECT  dept_code,dept_name,SUM(income_confirmed_amount) amount FROM "
						+ "(SELECT SUBSTRING(income_period, 1, 4) year,SUBSTRING(income_period, 6, 2) month,p.dept_code,p.dept_name,d.income_confirmed_amount "
						+ "	FROM ${fmDbTitle}.income_confirm c JOIN ${fmDbTitle}.income_confirm_detail d  ON c.income_id = c.income_id AND d.confirm_status = 'IS_CONFIRM' "
						+ "	JOIN ${pmDbTitle}.project p ON p.project_code = c.project_code WHERE (p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%')) t";
		
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{fmDbTitle\\}",
			fmDbTitle);
		//本年累计sql
		String thisYearSql = sql
								+ " WHERE year = '${reportYear}' AND month <= '${reportMonth}' GROUP BY dept_name ORDER BY t.dept_code";
		thisYearSql = thisYearSql.replaceAll("\\$\\{reportYear\\}", order.getReportYear() + "")
			.replaceAll("\\$\\{reportMonth\\}",
				(order.getReportMonth() < 10 ? "0" : "" + order.getReportMonth()));
		//本月sql
		String thisMonthSql = sql
								+ " WHERE year = '${reportYear}' AND month = '${reportMonth}'  GROUP BY dept_name ORDER BY t.dept_code";
		thisMonthSql = thisMonthSql.replaceAll("\\$\\{reportYear\\}", order.getReportYear() + "")
			.replaceAll("\\$\\{reportMonth\\}",
				(order.getReportMonth() < 10 ? "0" : "" + order.getReportMonth()));
		
		if (data == null)
			data = new ArrayList<GuaranteeKpiDataInfo>();
		
		//本年累计
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(thisYearSql);
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		Money thisYearTotal = Money.zero();
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String deptName = String.valueOf(itm.getMap().get("dept_name"));
				String deptCode = String.valueOf(itm.getMap().get("dept_code"));
				Money amount = toMoney(itm.getMap().get("amount"));
				GuaranteeKpiDataInfo dataInfo = null;
				for (GuaranteeKpiDataInfo info : data) {
					if (StringUtil.equals(info.getDeptCode(), deptCode)) {
						dataInfo = info;
						break;
					}
				}
				if (dataInfo == null) {
					dataInfo = new GuaranteeKpiDataInfo();
					dataInfo.setDept(deptName);
					dataInfo.setDeptCode(deptCode);
					data.add(dataInfo);
				}
				//本年累计确认收入
				dataInfo.setIncomeYear(amount);
				thisYearTotal.addTo(amount);
			}
		}
		
		//查询
		queyOrder.setSql(thisMonthSql);
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		Money thisMonthTotal = Money.zero();
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String deptName = String.valueOf(itm.getMap().get("dept_name"));
				String deptCode = String.valueOf(itm.getMap().get("dept_code"));
				Money amount = toMoney(itm.getMap().get("amount"));
				GuaranteeKpiDataInfo dataInfo = null;
				for (GuaranteeKpiDataInfo info : data) {
					if (StringUtil.equals(info.getDeptCode(), deptCode)) {
						dataInfo = info;
						break;
					}
				}
				if (dataInfo == null) {
					dataInfo = new GuaranteeKpiDataInfo();
					dataInfo.setDept(deptName);
					dataInfo.setDeptCode(deptCode);
					data.add(dataInfo);
				}
				//本月确认收入
				dataInfo.setIncomeMonth(amount);
				thisMonthTotal.addTo(amount);
			}
		}
		
		GuaranteeKpiDataInfo total = null;
		for (GuaranteeKpiDataInfo info : data) {
			if (StringUtil.equals(info.getDept(), "合计")) {
				total = info;
				break;
			}
		}
		if (total == null) {
			total = new GuaranteeKpiDataInfo();
			total.setDept("合计");
			data.add(total);
		}
		total.setIncomeYear(thisYearTotal);
		total.setIncomeMonth(thisMonthTotal);
	}
	
	/**
	 * 净增额年度目标、 在保余额年度目标、收入年度目标(万元)
	 * @param order
	 * @param data
	 */
	private void yearGoal(ReportQueryOrder order, List<GuaranteeKpiDataInfo> data) {
		
		String sql = "SELECT d.data2 dept_code,d.data3 dept_name,d.data4 increase_amount,d.data5 balance_amount,d.data6 income_amount "
						+ "FROM ${rmDbTitle}.submission s JOIN ${rmDbTitle}.submission_data d ON s.submission_id = d.submission_id	"
						+ "WHERE s.report_code = 'ANNUAL_OBJECTIVE' AND d.data1 = '担保业务' AND s.reporter_status IN ('IN_USE','SUBMITTED') "
						+ "AND s.report_year = '${reportYear}' ORDER BY d.data2 ";
		
		sql = sql.replaceAll("\\$\\{rmDbTitle\\}", rmDbTitle).replaceAll("\\$\\{reportYear\\}",
			order.getReportYear() + "");
		
		if (data == null)
			data = new ArrayList<GuaranteeKpiDataInfo>();
		
		//年度目标
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(sql);
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		Money increaseTotal = Money.zero();
		Money balanceTotal = Money.zero();
		Money incomeTotal = Money.zero();
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String deptName = String.valueOf(itm.getMap().get("dept_name"));
				String deptCode = String.valueOf(itm.getMap().get("dept_code"));
				Money increaseAmount = toMoneyW(itm.getMap().get("increase_amount"));
				Money balanceAmount = toMoneyW(itm.getMap().get("balance_amount"));
				Money incomeAmount = toMoneyW(itm.getMap().get("income_amount"));
				GuaranteeKpiDataInfo dataInfo = null;
				for (GuaranteeKpiDataInfo info : data) {
					if (StringUtil.equals(info.getDeptCode(), deptCode)) {
						dataInfo = info;
						break;
					}
				}
				if (dataInfo == null) {
					dataInfo = new GuaranteeKpiDataInfo();
					dataInfo.setDept(deptName);
					dataInfo.setDeptCode(deptCode);
					data.add(dataInfo);
				}
				
				dataInfo.setIncreaseGoal(increaseAmount);
				dataInfo.setBalanceGoal(balanceAmount);
				dataInfo.setIncomeGoal(incomeAmount);
				
				increaseTotal.addTo(increaseAmount);
				balanceTotal.addTo(balanceAmount);
				incomeTotal.addTo(incomeAmount);
			}
		}
		
		GuaranteeKpiDataInfo total = null;
		for (GuaranteeKpiDataInfo info : data) {
			if (StringUtil.equals(info.getDept(), "合计")) {
				total = info;
				break;
			}
		}
		if (total == null) {
			total = new GuaranteeKpiDataInfo();
			total.setDept("合计");
			data.add(total);
		}
		total.setIncreaseGoal(increaseTotal);
		total.setBalanceGoal(balanceTotal);
		total.setIncomeGoal(incomeTotal);
	}
	
	//转化Money
	private Money toMoney(Object fen) {
		try {
			String s = String.valueOf(fen);
			return Money.amout(s).divide(100);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return Money.zero();
	}
	
	//转化Money
	private Money toMoneyW(Object wan) {
		try {
			String s = String.valueOf(wan);
			return Money.amout(s).multiply(10000);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return Money.zero();
	}
}
