package com.born.fcs.rm.biz.service.report.inner;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.integration.service.pm.PmReportServiceClient;
import com.born.fcs.rm.ws.info.report.inner.ExpectReleaseInfo;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.yjf.common.lang.util.money.Money;

/**
 * 当月项目发生变动明细表
 * @author wuzj
 */
@Service("monthProjectChangeService")
public class MonthProjectChangeService extends BaseAutowiredDomainService implements
																			ReportProcessService {
	
	@Autowired
	private PmReportServiceClient pmReportServiceClient;
	
	@Override
	public FcsBaseResult save(ReportOrder order) {
		return null;
	}
	
	@Override
	public Object findByAccountPeriod(ReportQueryOrder order) {
		QueryBaseBatchResult<ExpectReleaseInfo> result = new QueryBaseBatchResult<ExpectReleaseInfo>();
		try {
			List<ExpectReleaseInfo> data = query(order);
			result.setSuccess(true);
			result.setPageList(data);
			result.setTotalCount(data.size());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("当月项目发生变动明细表出错");
			logger.error("查询当月项目发生变动明细表出错：{}", e);
		}
		return result;
	}
	
	/**
	 * 查询
	 * @param order
	 * @return
	 */
	private List<ExpectReleaseInfo> query(ReportQueryOrder order) {
		
		String sql = "SELECT   p.project_code,CASE WHEN p.busi_type LIKE '4%' THEN '委贷' ELSE '担保'   END busi_big_type,   p.dept_name,   p.busi_manager_name,   r.user_name risk_manager_name,   p.customer_name,   "
						+ "CASE p.scale WHEN 'HUGE' THEN '特大' WHEN 'BIG' THEN '大型' WHEN 'MEDIUM' THEN '中型' WHEN 'SMALL' THEN '小型' WHEN 'TINY' THEN '微型' ELSE '规模未知' end scale,"
						+ "CASE WHEN cd.is_local_gov_platform = 'IS' THEN '融资平台' WHEN p.enterprise_type = 'STATE_OWNED' THEN '国有企业' WHEN p.enterprise_type = 'PRIVATE' THEN '民营企业' WHEN p.enterprise_type = 'FOREIGN_OWNED' THEN '外资企业' WHEN p.enterprise_type = 'OTHER' THEN '其他' ELSE '未知'   END enterprise_type,   p.industry_name,   CASE c.province_code WHEN '500000' THEN '重庆' ELSE '异地'   END region, CASE WHEN c.country_name = '中国' THEN c.province_name ELSE c.country_name END province_name,   cp.channel_name,   p.busi_type_name,   loanyear.year_loan_amount,   loan.loan_time,   repayyear.year_repay_amount,   repay.repay_time FROM ${pmDbTitle}.${projectTableName} p   "
						+ "JOIN  ${crmDbTitle}.customer_base_info c    ON p.customer_id = c.user_id JOIN ${crmDbTitle}.customer_company_detail cd ON c.customer_id = cd.customer_id  "
						+ "LEFT JOIN (SELECT  project_code, SUM(repay_amount) month_repay_amount, GROUP_CONCAT(repay_time ORDER BY repay_time) repay_time FROM ${pmDbTitle}.view_project_repay_detail  WHERE repay_time >= '${monthStartDay}'  AND repay_time <= '${reportEndDay}' GROUP BY project_code) repay  ON p.project_code = repay.project_code   "
						+ "LEFT JOIN (SELECT  project_code, SUM(actual_amount) month_loan_amount, GROUP_CONCAT(date_format(raw_add_time,'%Y-%m-%d')  ORDER BY raw_add_time) loan_time     FROM ${pmDbTitle}.f_loan_use_apply_receipt   WHERE raw_add_time >= '${monthStartDay}' AND raw_add_time <= '${reportEndDay}'     GROUP BY project_code) loan     ON loan.project_code = p.project_code   "
						+ "LEFT JOIN (SELECT  project_code, SUM(repay_amount) year_repay_amount, GROUP_CONCAT(repay_time ORDER BY repay_time) repay_time FROM ${pmDbTitle}.view_project_repay_detail  WHERE repay_time >= '${yearStartDay}'  AND repay_time <= '${reportEndDay}' GROUP BY project_code) repayyear  ON p.project_code = repayyear.project_code   "
						+ "LEFT JOIN (SELECT  project_code, SUM(actual_amount) year_loan_amount FROM ${pmDbTitle}.f_loan_use_apply_receipt WHERE raw_add_time >= '${yearStartDay}' AND raw_add_time <= '${reportEndDay}' GROUP BY project_code) loanyear     ON loanyear.project_code = p.project_code   "
						+ "LEFT JOIN (SELECT  project_code, GROUP_CONCAT(channel_name ORDER BY channel_code) channel_name     FROM ${pmDbTitle}.project_channel_relation     WHERE latest = 'YES'     AND channel_relation = 'CAPITAL_CHANNEL'     GROUP BY project_code) cp     ON cp.project_code = p.project_code   "
						+ "LEFT JOIN (SELECT  project_code, user_name FROM ${pmDbTitle}.project_related_user     WHERE user_type = 'RISK_MANAGER'     AND is_current = 'IS') r     ON r.project_code = p.project_code WHERE (p.busi_type like '1%' OR p.busi_type like '2%' OR p.busi_type like '3%' OR p.busi_type like '4%') AND (repay.month_repay_amount > 0 OR loan.month_loan_amount > 0) ";
		
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{crmDbTitle\\}",
			crmDbTitle);
		
		sql = sql.replaceAll("\\$\\{monthStartDay\\}", order.getReportMonthStartDay())
			.replaceAll("\\$\\{yearStartDay\\}", order.getReportYearStartDay())
			.replaceAll("\\$\\{reportEndDay\\}", order.getReportMonthEndDay());
		
		//if (order.isThisMonth()) {
		sql = sql.replaceAll("\\$\\{projectTableName\\}", "project_data_info");
		//		} else {
		//			sql = sql.replaceAll("\\$\\{projectTableName\\}", "project_data_info_his_data");
		//		}
		
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(sql);
		
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		
		List<ExpectReleaseInfo> data = Lists.newArrayList();
		Money dbLoanTotal = Money.zero();
		Money dbRepayTotal = Money.zero();
		Money edLoanTotal = Money.zero();
		Money edRepayTotal = Money.zero();
		List<ExpectReleaseInfo> dbList = Lists.newArrayList();
		List<ExpectReleaseInfo> edList = Lists.newArrayList();
		
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				ExpectReleaseInfo info = new ExpectReleaseInfo();
				info.setDept(String.valueOf(itm.getMap().get("project_code")));
				info.setData1(String.valueOf(itm.getMap().get("busi_big_type")));
				info.setData2(String.valueOf(itm.getMap().get("dept_name")));
				info.setData3(String.valueOf(itm.getMap().get("busi_manager_name")));
				info.setData4(String.valueOf(itm.getMap().get("risk_manager_name")));
				info.setData5(String.valueOf(itm.getMap().get("customer_name")));
				info.setData6(String.valueOf(itm.getMap().get("enterprise_type")));
				info.setData7(String.valueOf(itm.getMap().get("scale")));
				info.setData8(String.valueOf(itm.getMap().get("industry_name")));
				info.setData9(String.valueOf(itm.getMap().get("region")));
				info.setData10(String.valueOf(itm.getMap().get("province_name")));
				Object channelName = itm.getMap().get("channel_name");
				info.setData11(channelName == null ? "-" : channelName.toString());
				info.setData12(String.valueOf(itm.getMap().get("busi_type_name")));
				Object loanTime = itm.getMap().get("loan_time");
				info.setData13(loanTime == null ? "-" : loanTime.toString());
				Money loanAmount = toMoney(itm.getMap().get("year_loan_amount"));
				info.setData14(loanAmount.getCent() == 0 ? "-" : MoneyUtil.formatW(loanAmount));
				Object repayTime = itm.getMap().get("repay_time");
				info.setData15(repayTime == null ? "-" : repayTime.toString());
				Money repayAmount = toMoney(itm.getMap().get("year_repay_amount"));
				info.setData16(repayAmount.getCent() == 0 ? "-" : MoneyUtil.formatW(repayAmount));
				if (StringUtil.equals("担保", info.getData1())) {
					dbLoanTotal.addTo(loanAmount);
					dbRepayTotal.addTo(repayAmount);
					dbList.add(info);
				} else {
					edLoanTotal.addTo(loanAmount);
					edRepayTotal.addTo(repayAmount);
					edList.add(info);
				}
			}
			
			if (dbList.size() > 0) {
				ExpectReleaseInfo db = new ExpectReleaseInfo();
				db.setData1("合计");
				db.setData14(dbLoanTotal.getCent() == 0 ? "-" : MoneyUtil.formatW(dbLoanTotal));
				db.setData16(dbRepayTotal.getCent() == 0 ? "-" : MoneyUtil.formatW(dbRepayTotal));
				dbList.add(db);
			}
			if (edList.size() > 0) {
				ExpectReleaseInfo ed = new ExpectReleaseInfo();
				ed.setData1("合计");
				ed.setData14(edLoanTotal.getCent() == 0 ? "-" : MoneyUtil.formatW(edLoanTotal));
				ed.setData16(edRepayTotal.getCent() == 0 ? "-" : MoneyUtil.formatW(edRepayTotal));
				edList.add(ed);
			}
			
			data.addAll(dbList);
			data.addAll(edList);
		}
		
		return data;
	}
	
	//转化Money
	private Money toMoney(Object fen) {
		if (fen != null) {
			try {
				return Money.amout(fen.toString()).divide(100);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return Money.zero();
	}
}
