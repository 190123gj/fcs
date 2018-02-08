package com.born.fcs.rm.biz.service.report.inner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.util.DateUtil;
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
 * 存量项目收入预计明细表（预计解保明细表）
 * @author wuzj
 */
@Service("expectReleaseDetailService")
public class ExpectReleaseDetailService extends BaseAutowiredDomainService implements
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
			
			int maxLength = 0;
			for (ExpectReleaseInfo info : data) {
				if (maxLength < info.getDatas().length)
					maxLength = info.getDatas().length;
			}
			
			for (ExpectReleaseInfo info : data) {
				info.setData19(String.valueOf(maxLength - info.getDatas().length));
			}
			
			result.setSuccess(true);
			result.setPageList(data);
			result.setPageSize(maxLength);
			result.setTotalCount(data.size());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("存量项目收入预计明细表（预计解保明细表）出错");
			logger.error("查询存量项目收入预计明细表（预计解保明细表）出错：{}", e);
		}
		return result;
	}
	
	/**
	 * 查询
	 * @param order
	 * @return
	 */
	private List<ExpectReleaseInfo> query(ReportQueryOrder order) {
		
		String sql = "SELECT p.project_code,CASE WHEN p.busi_type LIKE '4%' THEN '委贷' ELSE '担保'  END busi_big_type,  p.dept_name,  p.busi_manager_name,  r.user_name risk_manager_name,  p.customer_name,  CASE p.scale WHEN 'HUGE' THEN '特大' WHEN 'BIG' THEN '大型' WHEN 'MEDIUM' THEN '中型' WHEN 'SMALL' THEN '小型' WHEN 'TINY' THEN '微型' ELSE '规模未知'  END scale, "
						+ " CASE WHEN cd.is_local_gov_platform = 'IS' THEN '融资平台' WHEN p.enterprise_type = 'STATE_OWNED' THEN '国有企业' WHEN p.enterprise_type = 'PRIVATE' THEN '民营企业' WHEN p.enterprise_type = 'FOREIGN_OWNED' THEN '外资企业' WHEN p.enterprise_type = 'OTHER' THEN '其他' ELSE '未知'  END enterprise_type,  p.industry_name,  CASE c.province_code WHEN '500000' THEN '重庆' ELSE '异地'  END region, "
						+ " CASE WHEN c.country_name = '中国' THEN c.province_name ELSE c.country_name  END province_name,  cp.channel_name,  CASE WHEN btype.busi_type IS NULL OR    btype.busi_type = '' THEN p.busi_type_name ELSE btype.busi_type  END busi_type_name,  DATE_FORMAT(p.start_time,'%Y-%m-%d') start_time,  DATE_FORMAT(p.end_time,'%Y-%m-%d') end_time,  CASE WHEN p.guarantee_fee_rate = 0 THEN ROUND((p.guarantee_amount / p.amount) * 100,2) ELSE guarantee_fee_rate  END fee_rate,  repay.repay_time,  repay.repay_amount,p.blance FROM  ${pmDbTitle}.${projectTableName} p  "
						+ "JOIN  ${crmDbTitle}.customer_base_info c    ON p.customer_id = c.user_id JOIN ${crmDbTitle}.customer_company_detail cd ON c.customer_id = cd.customer_id  "
						+ "LEFT JOIN (SELECT      project_code,      GROUP_CONCAT(repay_amount ORDER BY repay_time) repay_amount,      GROUP_CONCAT(repay_time ORDER BY repay_time) repay_time    FROM  ${pmDbTitle}.view_project_repay_detail    WHERE repay_time <= '${reportEndDay}'    GROUP BY project_code) repay    ON p.project_code = repay.project_code  "
						+ "LEFT JOIN (SELECT      t.project_code,      CONCAT_WS(',', liquidity, fixed_assets_financing, acceptance_bill, credit) busi_type    FROM (SELECT        project_code,        CASE WHEN SUM(liquidity_loan_amount) > 0 THEN '流动资金贷款' ELSE NULL        END liquidity,        CASE WHEN SUM(fixed_assets_financing_amount) > 0 THEN '固定资产融资' ELSE NULL        END fixed_assets_financing,        CASE WHEN SUM(acceptance_bill_amount) > 0 THEN '承兑汇票担保' ELSE NULL        END acceptance_bill,        CASE WHEN SUM(credit_letter_amount) > 0 THEN '信用证担保' ELSE NULL        END credit      FROM  ${pmDbTitle}.f_loan_use_apply_receipt      GROUP BY project_code) t) btype    ON btype.project_code = p.project_code  "
						+ "LEFT JOIN (SELECT      project_code,      GROUP_CONCAT(channel_name ORDER BY channel_code) channel_name    FROM  ${pmDbTitle}.project_channel_relation    WHERE latest = 'YES'    AND channel_relation = 'CAPITAL_CHANNEL'    GROUP BY project_code) cp    ON cp.project_code = p.project_code  "
						+ "LEFT JOIN (SELECT      project_code,      user_name    FROM  ${pmDbTitle}.project_related_user    WHERE user_type = 'RISK_MANAGER'    AND is_current = 'IS') r    ON r.project_code = p.project_code WHERE (p.busi_type LIKE '1%'OR p.busi_type LIKE '2%'OR p.busi_type LIKE '3%'OR p.busi_type LIKE '4%') AND p.busi_type != '211' AND p.blance > 0 ${projectDateSql}";
		
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
		
		Money dbRepayTotal = Money.zero();
		Money dbIncomeTotal = Money.zero();
		Money dbBalanceTotal = Money.zero();
		
		Money edRepayTotal = Money.zero();
		Money edIncomeTotal = Money.zero();
		Money edBalanceTotal = Money.zero();
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
				
				info.setData13(String.valueOf(itm.getMap().get("start_time")));
				//授信截止时间
				Object endTime = itm.getMap().get("end_time");
				
				info.setData14(String.valueOf(endTime));
				
				//费率
				double feeRate = NumberUtil.parseDouble(
					String.valueOf(itm.getMap().get("fee_rate")), 0);
				Money balance = toMoney(itm.getMap().get("blance"));
				
				info.setData15(feeRate + "");
				info.setData16(MoneyUtil.formatW(balance));
				
				Money income = Money.zero();
				if (endTime != null) {
					//预计产生收入  = 在保余额*费率*距离授信截止日还有的自然月月数/12（不足一个月的部分暂不计入）
					Date projectEndTime = DateUtil.parse(endTime.toString());
					Date reportDate = DateUtil.parse(order.getReportMonthEndDay());
					int month = DateUtil.getMonthBetween(reportDate, projectEndTime);
					if (month > 0 && feeRate > 0) {
						income = balance.multiply(feeRate).divide(100).multiply(month).divide(12);
					}
				}
				
				info.setData17(MoneyUtil.formatW(income));
				
				//多次还款
				Object repayTimes = itm.getMap().get("repay_time");
				Object repayAmounts = itm.getMap().get("repay_amount");
				
				List<String> repayList = Lists.newArrayList();
				Money repayAmount = Money.zero();
				if (repayTimes != null && repayAmounts != null) {
					String[] repayTimeArr = repayTimes.toString().split(",");
					String[] repayAmountArr = repayAmounts.toString().split(",");
					for (int i = 0; i < repayTimeArr.length; i++) {
						repayList.add(repayTimeArr[i]);
						if (repayAmountArr.length >= i) {
							Money amount = toMoney(repayAmountArr[i]);
							repayList.add(MoneyUtil.formatW(amount));
							repayAmount.addTo(amount);
						}
					}
				}
				
				info.setData18(MoneyUtil.formatW(repayAmount));
				info.setDatas(repayList.toArray(new String[repayList.size()]));
				
				if (StringUtil.equals("担保", info.getData1())) {
					dbRepayTotal.addTo(repayAmount);
					dbBalanceTotal.addTo(balance);
					dbIncomeTotal.addTo(income);
					dbList.add(info);
				} else {
					edRepayTotal.addTo(repayAmount);
					edBalanceTotal.addTo(balance);
					edIncomeTotal.addTo(income);
					edList.add(info);
				}
			}
			
			if (dbList.size() > 0) {
				ExpectReleaseInfo db = new ExpectReleaseInfo();
				db.setData1("合计");
				db.setData16(MoneyUtil.formatW(dbBalanceTotal));
				db.setData17(MoneyUtil.formatW(dbIncomeTotal));
				db.setData18(MoneyUtil.formatW(dbRepayTotal));
				db.setDatas(new String[] {});
				dbList.add(db);
			}
			
			if (edList.size() > 0) {
				ExpectReleaseInfo ed = new ExpectReleaseInfo();
				ed.setData1("合计");
				ed.setData16(MoneyUtil.formatW(edBalanceTotal));
				ed.setData17(MoneyUtil.formatW(edIncomeTotal));
				ed.setData18(MoneyUtil.formatW(edRepayTotal));
				ed.setDatas(new String[] {});
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
