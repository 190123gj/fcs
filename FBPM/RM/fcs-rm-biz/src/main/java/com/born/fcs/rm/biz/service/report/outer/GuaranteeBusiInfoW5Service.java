package com.born.fcs.rm.biz.service.report.outer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.integration.service.pm.PmReportServiceClient;
import com.born.fcs.rm.ws.info.report.outer.GuaranteeBusiInfoW5Info;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.money.Money;

/**
 * W5-（中担协）融资性担保业务情况（表二）
 * @author wuzj
 */
@Service("guaranteeBusiInfoW5Service")
public class GuaranteeBusiInfoW5Service extends BaseAutowiredDomainService implements
																			ReportProcessService {
	
	@Autowired
	private PmReportServiceClient pmReportServiceClient;
	
	@Override
	public FcsBaseResult save(ReportOrder order) {
		return null;
	}
	
	/**
	 * 初始化
	 * @return
	 */
	private LinkedHashMap<String, GuaranteeBusiInfoW5Info> initData() {
		
		LinkedHashMap<String, GuaranteeBusiInfoW5Info> data = new LinkedHashMap<String, GuaranteeBusiInfoW5Info>();
		
		//一、按担按单笔担保金额划分
		data.put("100万元（含）以下", new GuaranteeBusiInfoW5Info("amount"));
		data.put("100-300万元（含）", new GuaranteeBusiInfoW5Info("amount"));
		data.put("300-500万元（含）", new GuaranteeBusiInfoW5Info("amount"));
		data.put("500-1000万元（含）", new GuaranteeBusiInfoW5Info("amount"));
		data.put("1000-2000万元（含）", new GuaranteeBusiInfoW5Info("amount"));
		data.put("2000-5000万元（含）", new GuaranteeBusiInfoW5Info("amount"));
		data.put("5000万元以上", new GuaranteeBusiInfoW5Info("amount"));
		data.put("合计", new GuaranteeBusiInfoW5Info("amount"));
		
		//二、按受保对象类型划分（个人贷款用于企业经营属于企业担保）
		data.put("个人（包括个体工商户、农户）", new GuaranteeBusiInfoW5Info("object"));
		data.put("特大", new GuaranteeBusiInfoW5Info("object"));
		data.put("大型", new GuaranteeBusiInfoW5Info("object"));
		data.put("中型", new GuaranteeBusiInfoW5Info("object"));
		data.put("小型", new GuaranteeBusiInfoW5Info("object"));
		data.put("微型", new GuaranteeBusiInfoW5Info("object"));
		data.put("未知", new GuaranteeBusiInfoW5Info("object"));
		
		return data;
	}
	
	@Override
	public Object findByAccountPeriod(ReportQueryOrder order) {
		QueryBaseBatchResult<GuaranteeBusiInfoW5Info> result = new QueryBaseBatchResult<GuaranteeBusiInfoW5Info>();
		List<GuaranteeBusiInfoW5Info> data = new ArrayList<GuaranteeBusiInfoW5Info>();
		try {
			
			//初始化
			LinkedHashMap<String, GuaranteeBusiInfoW5Info> dataMap = initData();
			
			//期初、期末在保额、户数、笔数
			zbBalanceHsAndBs(order, dataMap);
			//本期解除担保额
			decrease(order, dataMap);
			//减少户数
			//decreaseHs(order, dataMap);
			//本期新增担保户数、笔数
			increaseBsAndHs(order, dataMap);
			
			List<GuaranteeBusiInfoW5Info> amountList = Lists.newArrayList();
			List<GuaranteeBusiInfoW5Info> objList = Lists.newArrayList();
			
			//循环
			GuaranteeBusiInfoW5Info amountTotal = dataMap.get("合计");
			
			for (String classify : dataMap.keySet()) {
				
				GuaranteeBusiInfoW5Info info = dataMap.get(classify);
				info.setClassify(classify);
				//计算减少户数
				info.setDecreaseHs(info.getZbhsBeginning() + info.getHs() - info.getZbhsEnding());
				//info.setZbhsBeginning(info.getZbhsEnding() - info.getHs() + info.getDecreaseHs());
				//计算本期新增
				info.setIncrease(info.getBalanceEnding().subtract(info.getBalanceBeginning())
					.add(info.getDecrease()));
				if ("amount".equals(info.getClasifyby()) && !"合计".equals(classify)) {
					//					amountTotal.getBalanceBeginning().addTo(info.getBalanceBeginning());
					//					amountTotal.getBalanceEnding().addTo(info.getBalanceEnding());
					amountTotal.getDecrease().addTo(info.getDecrease());
					amountTotal.getIncrease().addTo(info.getIncrease());
					amountList.add(info);
				} else {
					objList.add(info);
				}
			}
			
			GuaranteeBusiInfoW5Info objTotal = new GuaranteeBusiInfoW5Info();
			BeanCopier.staticCopy(amountTotal, objTotal);
			objTotal.setClasifyby("object");
			objList.add(objTotal);
			
			data.addAll(amountList);
			data.addAll(objList);
			
			result.setSuccess(true);
			result.setPageList(data);
			result.setTotalCount(data.size());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("融资性担保业务情况（表二）出错");
			logger.error("查询融资性担保业务情况（表二）出错：{}", e);
		}
		return result;
	}
	
	/**
	 * 在保余额、户数、笔数(期初、期末)
	 * @param order
	 * @param data
	 */
	private void zbBalanceHsAndBs(ReportQueryOrder order, Map<String, GuaranteeBusiInfoW5Info> data) {
		
		String sql = "SELECT '合计' classify, COUNT(DISTINCT p.customer_id) hs, COUNT(*) bs, SUM(ba.balance) balance FROM ${pmDbTitle}.project_data_info p "
						+ "	JOIN (SELECT occer.project_code, (oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date <= '${reportMonthEndDay}' GROUP BY project_code) occer "
						+ "LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_confirm_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE ba.balance > 0 AND ( p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%') "
						+ ""
						+ "UNION ALL  SELECT CASE WHEN p.amount <= 100000000 THEN '100万元（含）以下' WHEN p.amount > 100000000 AND p.amount <= 300000000 THEN '100-300万元（含）' WHEN p.amount > 300000000 AND p.amount <= 500000000 THEN '300-500万元（含）' WHEN p.amount > 500000000 AND p.amount <= 1000000000 THEN '500-1000万元（含）' WHEN p.amount > 1000000000 AND p.amount <= 2000000000 THEN '1000-2000万元（含）' WHEN p.amount > 2000000000 AND p.amount <= 5000000000 THEN '2000-5000万元（含）' ELSE '5000万元以上' END classify, COUNT(DISTINCT p.customer_id) hs, COUNT(*) bs, SUM(ba.balance) balance FROM ${pmDbTitle}.project_data_info p "
						+ "JOIN (SELECT occer.project_code, (oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date <= '${reportMonthEndDay}' GROUP BY project_code) occer "
						+ "LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_confirm_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE ba.balance > 0 AND ( p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%') GROUP BY classify "
						+ ""
						+ "UNION ALL SELECT CASE WHEN p.customer_type = 'PERSIONAL' THEN '个人（包括个体工商户、农户）' WHEN p.scale = 'HUGE' THEN '特大' WHEN p.scale = 'BIG' THEN '大型' WHEN p.scale = 'MEDIUM' THEN '中型' WHEN p.scale = 'SMALL' THEN '小型' WHEN p.scale = 'TINY' THEN '微型' ELSE '未知' END classify, COUNT(DISTINCT p.customer_id) hs, COUNT(*) bs, SUM(ba.balance) balance FROM ${pmDbTitle}.project_data_info p "
						+ "JOIN (SELECT occer.project_code, (oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date <= '${reportMonthEndDay}' GROUP BY project_code) occer "
						+ "LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_confirm_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE ba.balance > 0 AND ( p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%')  GROUP BY classify";
		
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		
		//期末在保
		String endSql = sql;
		endSql = endSql.replaceAll("\\$\\{reportMonthEndDay\\}", order.getReportMonthEndDay());
		
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(endSql);
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String classify = String.valueOf(itm.getMap().get("classify"));
				String hs = String.valueOf(itm.getMap().get("hs"));
				String bs = String.valueOf(itm.getMap().get("bs"));
				GuaranteeBusiInfoW5Info info = data.get(classify);
				if (info != null) {
					info.setZbbsEnding(NumberUtil.parseLong(bs));
					info.setZbhsEnding(NumberUtil.parseLong(hs));
					info.setBalanceEnding(toMoney(itm.getMap().get("balance")));
				}
			}
		}
		
		//期初在保
		sql = sql.replaceAll("\\$\\{reportMonthEndDay\\}", order.getReportLastMonthEndDay());
		queyOrder.setSql(sql);
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String classify = String.valueOf(itm.getMap().get("classify"));
				String hs = String.valueOf(itm.getMap().get("hs"));
				String bs = String.valueOf(itm.getMap().get("bs"));
				GuaranteeBusiInfoW5Info info = data.get(classify);
				if (info != null) {
					info.setZbbsBeginning(NumberUtil.parseLong(bs));
					info.setZbhsBeginning(NumberUtil.parseLong(hs));
					info.setBalanceBeginning(toMoney(itm.getMap().get("balance")));
				}
			}
		}
	}
	
	/**
	 * 本期解除担保责任额
	 * @param order
	 * @param data
	 */
	private void decrease(ReportQueryOrder order, Map<String, GuaranteeBusiInfoW5Info> data) {
		
		String sql = "SELECT CASE WHEN p.amount <= 100000000 THEN '100万元（含）以下' WHEN p.amount > 100000000 AND p.amount <= 300000000 THEN '100-300万元（含）' WHEN p.amount > 300000000 AND p.amount <= 500000000 THEN '300-500万元（含）' WHEN p.amount > 500000000 AND p.amount <= 1000000000 THEN '500-1000万元（含）' WHEN p.amount > 1000000000 AND p.amount <= 2000000000 THEN '1000-2000万元（含）' WHEN p.amount > 2000000000 AND p.amount <= 5000000000 THEN '2000-5000万元（含）' ELSE '5000万元以上' END classify, SUM(repay.re_amount) repay_amount FROM ${pmDbTitle}.project_data_info p "
						+ "JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保','诉保解保' ,'代偿') AND repay_confirm_time >= '${reportMonthStartDay}' AND repay_confirm_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON p.project_code = repay.project_code WHERE ( p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%') GROUP BY classify "
						+ ""
						+ "UNION ALL SELECT CASE WHEN p.customer_type = 'PERSIONAL' THEN '个人（包括个体工商户、农户）' WHEN p.scale = 'HUGE' THEN '特大' WHEN p.scale = 'BIG' THEN '大型' WHEN p.scale = 'MEDIUM' THEN '中型' WHEN p.scale = 'SMALL' THEN '小型' WHEN p.scale = 'TINY' THEN '微型' ELSE '未知' END classify, SUM(repay.re_amount) repay_amount FROM ${pmDbTitle}.project_data_info p "
						+ "JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保','诉保解保' ,'代偿') AND repay_confirm_time >= '${reportMonthStartDay}' AND repay_confirm_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON p.project_code = repay.project_code WHERE ( p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%') GROUP BY classify ";
		
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		sql = sql.replaceAll("\\$\\{reportMonthStartDay\\}", order.getReportMonthStartDay());
		sql = sql.replaceAll("\\$\\{reportMonthEndDay\\}", order.getReportMonthEndDay());
		
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(sql);
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String classify = String.valueOf(itm.getMap().get("classify"));
				GuaranteeBusiInfoW5Info info = data.get(classify);
				if (info != null) {
					info.setDecrease(toMoney(itm.getMap().get("repay_amount")));
				}
			}
		}
	}
	
	/**
	 * 本期新增笔数、户数(本期第一次放用款（担保）、第一次发售（发债）、第一次上传裁定书（诉保）)
	 * @param order
	 * @param data
	 */
	private void increaseBsAndHs(ReportQueryOrder order, Map<String, GuaranteeBusiInfoW5Info> data) {
		
		String sql = "SELECT '合计' classify, COUNT(DISTINCT p.customer_id) hs, COUNT(*) bs FROM project_data_info p "
						+ "	JOIN (SELECT project_code, occur_amount FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date >= '${reportMonthStartDay}' AND occur_date <= '${reportMonthEndDay}' GROUP BY project_code) this_month ON p.project_code = this_month.project_code "
						+ "LEFT JOIN (SELECT project_code, occur_amount FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date <= '${lastMonthEndDay}' GROUP BY project_code) last_month ON this_month.project_code = last_month.project_code WHERE this_month.occur_amount > 0 AND (last_month.occur_amount = 0 OR last_month.occur_amount IS NULL) AND ( p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%')"
						+ " "
						+ "UNION ALL SELECT CASE WHEN p.amount <= 100000000 THEN '100万元（含）以下' WHEN p.amount > 100000000 AND p.amount <= 300000000 THEN '100-300万元（含）' WHEN p.amount > 300000000 AND p.amount <= 500000000 THEN '300-500万元（含）' WHEN p.amount > 500000000 AND p.amount <= 1000000000 THEN '500-1000万元（含）' WHEN p.amount > 1000000000 AND p.amount <= 2000000000 THEN '1000-2000万元（含）' WHEN p.amount > 2000000000 AND p.amount <= 5000000000 THEN '2000-5000万元（含）' ELSE '5000万元以上' END classify, COUNT(DISTINCT p.customer_id) hs, COUNT(*) bs FROM ${pmDbTitle}.project_data_info p "
						+ "JOIN (SELECT project_code, occur_amount FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date >= '${reportMonthStartDay}' AND  occur_date <= '${reportMonthEndDay}' GROUP BY project_code) this_month ON p.project_code = this_month.project_code "
						+ "LEFT JOIN (SELECT project_code, occur_amount FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date <= '${lastMonthEndDay}' GROUP BY project_code) last_month ON this_month.project_code = last_month.project_code WHERE this_month.occur_amount > 0 AND (last_month.occur_amount = 0 OR last_month.occur_amount IS NULL) AND ( p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%') GROUP BY classify"
						+ " "
						+ "UNION ALL SELECT CASE WHEN p.customer_type = 'PERSIONAL' THEN '个人（包括个体工商户、农户）' WHEN p.scale = 'HUGE' THEN '特大' WHEN p.scale = 'BIG' THEN '大型' WHEN p.scale = 'MEDIUM' THEN '中型' WHEN p.scale = 'SMALL' THEN '小型' WHEN p.scale = 'TINY' THEN '微型' ELSE '未知' END classify, COUNT(DISTINCT p.customer_id) hs, COUNT(*) bs FROM ${pmDbTitle}.project_data_info p "
						+ "JOIN (SELECT project_code, occur_amount FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date >= '${reportMonthStartDay}' AND  occur_date <= '${reportMonthEndDay}' GROUP BY project_code) this_month ON p.project_code = this_month.project_code "
						+ "LEFT JOIN (SELECT project_code, occur_amount FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date <= '${lastMonthEndDay}' GROUP BY project_code) last_month ON this_month.project_code = last_month.project_code WHERE this_month.occur_amount > 0 AND (last_month.occur_amount = 0 OR last_month.occur_amount IS NULL) AND ( p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%') GROUP BY classify";
		
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		sql = sql.replaceAll("\\$\\{reportMonthStartDay\\}", order.getReportMonthStartDay());
		sql = sql.replaceAll("\\$\\{reportMonthEndDay\\}", order.getReportMonthEndDay());
		sql = sql.replaceAll("\\$\\{lastMonthEndDay\\}", order.getReportLastMonthEndDay());
		
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(sql);
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String classify = String.valueOf(itm.getMap().get("classify"));
				String bs = String.valueOf(itm.getMap().get("bs"));
				String hs = String.valueOf(itm.getMap().get("hs"));
				GuaranteeBusiInfoW5Info info = data.get(classify);
				if (info != null) {
					info.setBs(NumberUtil.parseLong(bs));
					info.setHs(NumberUtil.parseLong(hs));
				}
			}
		}
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
