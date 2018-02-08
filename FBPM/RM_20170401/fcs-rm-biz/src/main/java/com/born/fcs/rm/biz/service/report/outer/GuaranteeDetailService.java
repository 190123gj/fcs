package com.born.fcs.rm.biz.service.report.outer;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.util.NumberUtil;
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
 * W9-（工信部网上直报）担保业务明细
 * @author wuzj
 */
@Service("guaranteeDetailService")
public class GuaranteeDetailService extends BaseAutowiredDomainService implements
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
			result.setMessage("担保业务明细出错");
			logger.error("查询担保业务明细出错：{}", e);
		}
		return result;
	}
	
	/**
	 * 查询
	 * @param order
	 * @return
	 */
	private List<ExpectReleaseInfo> query(ReportQueryOrder order) {
		
		String sql = "SELECT   p.project_code,   CASE c.enterprise_type WHEN 'STATE_OWNED' THEN '国有企业' WHEN 'PRIVATE' THEN '民营企业' WHEN 'FOREIGN_OWNED' THEN '外资企业' WHEN 'OTHER' THEN '其他' ELSE '未知'   END enterprise_type,   c.customer_name,   CASE WHEN cd.is_one_cert = 'IS' THEN cd.busi_license_no ELSE cd.org_code   END org_code,   cd.industry_name,   cd.sales_proceeds_this_year,   cd.total_asset,   cd.staff_num,   cc.channel_type,   cc.channel_name,   sp.loan_purpose,   sp.amount,   p.contract_no,   gf.amount guarentee_fee,   DATE_FORMAT(loan.first_loan_time,'%Y-%m-%d') first_loan_time,  CASE WHEN pd.phases = 'FINISH_PHASES' THEN re.release_time ELSE '-' END release_time,   entrusted.interest_rate,   CASE WHEN p.busi_type LIKE '4%' THEN entrusted.other_fee WHEN p.busi_type LIKE '12%' THEN bond.other_fee ELSE guarentee.other_fee   END other_fee,   CASE WHEN p.busi_type LIKE '4%' THEN entrusted.other_fee_type WHEN p.busi_type LIKE '12%' THEN bond.other_fee_type ELSE guarentee.other_fee_type   END other_fee_type,   pd.customer_deposit_amount FROM ${pmDbTitle}.${projectTableName} pd   "
						+ "JOIN ${pmDbTitle}.project p     ON pd.project_code = p.project_code   "
						+ "JOIN ${crmDbTitle}.customer_base_info c     ON p.customer_id = c.user_id   "
						+ "JOIN ${crmDbTitle}.customer_company_detail cd     ON c.customer_id = cd.customer_id   "
						+ "JOIN f_council_summary_project sp     ON sp.sp_id = p.sp_id "
						+ "LEFT JOIN f_council_summary_project_bond bond     ON p.sp_id = bond.sp_id   "
						+ "LEFT JOIN f_council_summary_project_guarantee guarentee     ON p.sp_id = guarentee.sp_id   "
						+ "LEFT JOIN f_council_summary_project_entrusted entrusted     ON p.sp_id = entrusted.sp_id   "
						+ "LEFT JOIN (SELECT       SUM(charge_amount) amount,       project_code     FROM ${pmDbTitle}.view_project_charge_detail     WHERE fee_type = 'GUARANTEE_FEE'     AND charge_time <= '${reportEndDay}'     GROUP BY project_code) gf     ON p.project_code = gf.project_code   "
						+ "JOIN (SELECT       project_code,       MIN(actual_loan_time) first_loan_time     FROM ${pmDbTitle}.f_loan_use_apply_receipt     WHERE apply_type IN ('BOTH', 'LOAN')  GROUP BY project_code) loan     ON p.project_code = loan.project_code   "
						+ "LEFT JOIN (SELECT       r.project_code,       DATE_FORMAT(max(f.finish_time), '%Y-%m-%d') release_time     FROM ${pmDbTitle}.f_counter_guarantee_release r       JOIN form f         ON r.form_id = f.form_id     WHERE f.STATUS = 'APPROVAL' GROUP BY r.project_code) re     ON p.project_code = re.project_code   "
						+ "LEFT JOIN (SELECT       project_code,       GROUP_CONCAT(channel.channel_type ORDER BY ID) channel_type,       GROUP_CONCAT(channel.channel_name ORDER BY ID) channel_name     FROM (SELECT         ID,         project_code,         CASE channel_type WHEN 'YH' THEN '银行' WHEN 'ZQ' THEN '证券公司' WHEN 'XT' THEN '信托公司' WHEN 'JYPT' THEN '交易平台' WHEN 'JJ' THEN '基金公司' WHEN 'ZL' THEN '租赁公司' WHEN 'WLJR' THEN '网络金融平台' WHEN 'ZZYX' THEN '自主营销' WHEN 'QT' THEN '其他渠道' WHEN 'BGS' THEN '本公司' ELSE '未知'         END channel_type,         channel_name       FROM ${pmDbTitle}.project_channel_relation       WHERE channel_relation = 'CAPITAL_CHANNEL'       AND latest = 'YES') channel     GROUP BY project_code) cc     ON cc.project_code = p.project_code "
						+ "WHERE pd.loaned_amount > 0 ${projectDateSql}  order by first_loan_time";
		
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle)
			.replaceAll("\\$\\{crmDbTitle\\}", crmDbTitle)
			.replaceAll("\\$\\{reportEndDay\\}", order.getReportMonthEndDay());
		
		if (order.isThisMonth()) {
			sql = sql.replaceAll("\\$\\{projectTableName\\}", "project_data_info");
			sql = sql.replaceAll("\\$\\{projectDateSql\\}", "");
		} else {
			sql = sql.replaceAll("\\$\\{projectTableName\\}", "project_data_info_his_data");
			sql = sql.replaceAll("\\$\\{projectDateSql\\}",
				"AND project_date = '" + order.getReportMonthEndDay() + "'");
		}
		
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(sql);
		
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		
		List<ExpectReleaseInfo> data = Lists.newArrayList();
		
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				ExpectReleaseInfo info = new ExpectReleaseInfo();
				info.setDept(toString(itm.getMap().get("project_code")));
				info.setData1(toString(itm.getMap().get("enterprise_type")));
				info.setData2(toString(itm.getMap().get("customer_name")));
				info.setData3(toString(itm.getMap().get("org_code")));
				info.setData4(toString(itm.getMap().get("industry_name")));
				info.setData5(toMoneyStr(itm.getMap().get("sales_proceeds_this_year")));
				info.setData6(toMoneyStr(itm.getMap().get("total_asset")));
				info.setData7(toString(itm.getMap().get("staff_num")));
				info.setData8(toString(itm.getMap().get("channel_type")));
				info.setData9(toString(itm.getMap().get("channel_name")));
				info.setData10(toString(itm.getMap().get("loan_purpose")));
				Money amount = toMoney(itm.getMap().get("amount"));
				info.setData11(MoneyUtil.formatW(amount));
				info.setData12(toString(itm.getMap().get("contract_no")));
				info.setData13("-");
				info.setData14(toMoneyStr(itm.getMap().get("guarentee_fee")));
				info.setData15(toString(itm.getMap().get("first_loan_time")));
				info.setData16(toString(itm.getMap().get("release_time")));
				Object interestRate = itm.getMap().get("interest_rate");
				if (interestRate == null) {
					info.setData17("-");
				} else {
					double rate = NumberUtil.parseDouble(interestRate.toString());
					info.setData17("" + NumberUtil.formatDouble2(rate));
				}
				String otherFeeType = toString(itm.getMap().get("other_fee_type"));
				Object otherFee = itm.getMap().get("other_fee");
				if (StringUtil.equals(otherFeeType, "AMOUNT")) {
					Money otherFeeMoney = Money.amout(otherFee == null ? "0" : otherFee.toString());
					info.setData18(MoneyUtil.formatW(otherFeeMoney));
				} else {
					double otherFeeRate = otherFee == null ? 0 : NumberUtil.parseDouble(otherFee
						.toString());
					Money otherFeeMoney = amount.multiply(otherFeeRate).divide(100);
					info.setData18(MoneyUtil.formatW(otherFeeMoney));
				}
				info.setData19(toMoneyStr(itm.getMap().get("customer_deposit_amount")));
				data.add(info);
			}
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
	
	private String toString(Object object) {
		if (object == null) {
			return "-";
		} else {
			return String.valueOf(object);
		}
	}
}
