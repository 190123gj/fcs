package com.born.fcs.rm.biz.service.report.inner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.integration.service.pm.PmReportServiceClient;
import com.born.fcs.rm.ws.info.report.inner.GuaranteeClassifyInfo;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.yjf.common.lang.util.money.Money;

/**
 * 担保项目分类汇总表
 * @author wuzj
 */
@Service("guaranteeClassifyService")
public class GuaranteeClassifyService extends BaseAutowiredDomainService implements
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
	private LinkedHashMap<String, GuaranteeClassifyInfo> initData() {
		
		LinkedHashMap<String, GuaranteeClassifyInfo> data = new LinkedHashMap<String, GuaranteeClassifyInfo>();
		
		data.put("总计", new GuaranteeClassifyInfo());
		
		//一、按担保业务分类
		data.put("一、按担保业务分类", null);
		data.put("间接融资担保", new GuaranteeClassifyInfo());
		data.put("直接融资担保", new GuaranteeClassifyInfo());
		data.put("非融资担保", new GuaranteeClassifyInfo());
		data.put("再担保", new GuaranteeClassifyInfo());
		
		//二、按企业规模分类
		data.put("二、按企业规模分类", null);
		//data.put("特大", new GuaranteeClassifyInfo());
		data.put("大型", new GuaranteeClassifyInfo());
		data.put("中型", new GuaranteeClassifyInfo());
		data.put("小型", new GuaranteeClassifyInfo());
		data.put("微型", new GuaranteeClassifyInfo());
		//data.put("未知-规模", new GuaranteeClassifyInfo());
		
		//三、按企业性质分类
		data.put("三、按企业性质分类", null);
		data.put("国有企业", new GuaranteeClassifyInfo());
		data.put("民营企业", new GuaranteeClassifyInfo());
		data.put("外资企业", new GuaranteeClassifyInfo());
		data.put("其他-企业性质", new GuaranteeClassifyInfo());
		data.put("其中融资平台", new GuaranteeClassifyInfo());
		//data.put("未知-企业性质", new GuaranteeClassifyInfo());
		
		//四、按地区分类
		data.put("四、按地区分类", null);
		data.put("重庆", new GuaranteeClassifyInfo());
		data.put("异地", new GuaranteeClassifyInfo());
		
		//五、按到期期限分类
		data.put("五、按到期期限分类", null);
		data.put("1年以内", new GuaranteeClassifyInfo());
		data.put("1年（含）至2年", new GuaranteeClassifyInfo());
		data.put("2年（含）至3年", new GuaranteeClassifyInfo());
		data.put("3年（含）至5年", new GuaranteeClassifyInfo());
		data.put("5年以以上", new GuaranteeClassifyInfo());
		data.put("诉保", new GuaranteeClassifyInfo());
		data.put("其他-期限", new GuaranteeClassifyInfo());
		
		//六、按行业分类
		data.put("六、按行业分类", null);
		data.put("农、林、牧、渔业", new GuaranteeClassifyInfo());
		data.put("制造业", new GuaranteeClassifyInfo());
		data.put("电力热力水生产和供应业", new GuaranteeClassifyInfo());
		data.put("建筑业", new GuaranteeClassifyInfo());
		data.put("房地产业", new GuaranteeClassifyInfo());
		data.put("交通运输仓储和邮政业", new GuaranteeClassifyInfo());
		data.put("租赁和商务服务业", new GuaranteeClassifyInfo());
		data.put("其他-行业", new GuaranteeClassifyInfo());
		
		//七、按产品分类
		data.put("七、按产品分类", null);
		data.put("债券融资担保", new GuaranteeClassifyInfo());
		data.put("银行融资担保", new GuaranteeClassifyInfo());
		data.put("金融产品融资担保", new GuaranteeClassifyInfo());
		data.put("非融资担保-1", new GuaranteeClassifyInfo());
		data.put("再担保-1", new GuaranteeClassifyInfo());
		
		//八、按资金渠道分类
		data.put("八、按资金渠道分类", null);
		data.put("银行", new GuaranteeClassifyInfo());
		data.put("证券公司", new GuaranteeClassifyInfo());
		data.put("信托公司", new GuaranteeClassifyInfo());
		data.put("交易平台", new GuaranteeClassifyInfo());
		data.put("基金公司", new GuaranteeClassifyInfo());
		data.put("租赁公司", new GuaranteeClassifyInfo());
		data.put("网络金融平台", new GuaranteeClassifyInfo());
		data.put("自主营销", new GuaranteeClassifyInfo());
		data.put("本公司", new GuaranteeClassifyInfo());
		data.put("其他-资金渠道", new GuaranteeClassifyInfo());
		
		return data;
	}
	
	@Override
	public Object findByAccountPeriod(ReportQueryOrder order) {
		QueryBaseBatchResult<GuaranteeClassifyInfo> result = new QueryBaseBatchResult<GuaranteeClassifyInfo>();
		List<GuaranteeClassifyInfo> data = new ArrayList<GuaranteeClassifyInfo>();
		try {
			
			//初始化
			LinkedHashMap<String, GuaranteeClassifyInfo> dataMap = initData();
			//在保户数、笔数
			zbHsAndBs(order, dataMap);
			//上年余额、期末余额
			balance(order, dataMap);
			//本年新增
			increase(order, dataMap);
			//本年还款
			repay(order, dataMap);
			
			//循环
			for (String classify : dataMap.keySet()) {
				GuaranteeClassifyInfo info = dataMap.get(classify);
				if (info == null) {
					info = new GuaranteeClassifyInfo();
					info.setTitle(true);
				}
				if (classify != null && classify.indexOf("-") > 0) {
					classify = classify.substring(0, classify.indexOf("-"));
				}
				info.setClassify(classify);
				data.add(info);
			}
			
			result.setSuccess(true);
			result.setPageList(data);
			result.setTotalCount(data.size());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("担保项目分类汇总表出错");
			logger.error("查询担保项目分类汇总表出错：{}", e);
		}
		return result;
	}
	
	/**
	 * 在保户数，在保笔数
	 * @param order
	 * @param data
	 */
	private void zbHsAndBs(ReportQueryOrder order, Map<String, GuaranteeClassifyInfo> data) {
		
		String sql = "SELECT   '总计' classify,  COUNT(DISTINCT customer_id) hs,   COUNT(*) bs FROM ${pmDbTitle}.${projectTableName} WHERE custom_name1 = '在保' AND customer_id > 0 ${projectDateSql} AND (busi_type LIKE '1%' OR busi_type LIKE '2%' OR busi_type LIKE '3%')  "
						+ "UNION ALL  SELECT   CASE WHEN busi_type LIKE '11%' THEN '间接融资担保' WHEN busi_type LIKE '12%' OR     busi_type LIKE '13%' THEN '直接融资担保' WHEN busi_type LIKE '2%' THEN '非融资担保' ELSE '再担保'   END classify,   COUNT(DISTINCT customer_id) hs,   COUNT(*) bs FROM ${pmDbTitle}.${projectTableName} WHERE custom_name1 = '在保' AND customer_id > 0 ${projectDateSql} AND (busi_type LIKE '1%' OR busi_type LIKE '2%' OR busi_type LIKE '3%') GROUP BY classify  "
						+ "UNION ALL  SELECT   CASE scale WHEN 'HUGE' THEN '特大' WHEN 'BIG' THEN '大型' WHEN 'MEDIUM' THEN '中型' WHEN 'SMALL' THEN '小型' WHEN 'TINY' THEN '微型' ELSE '未知-规模'   END classify,   COUNT(DISTINCT customer_id) hs,   COUNT(*) bs FROM ${pmDbTitle}.${projectTableName} WHERE custom_name1 = '在保' AND customer_id > 0 ${projectDateSql} AND (busi_type LIKE '1%' OR busi_type LIKE '2%' OR busi_type LIKE '3%') GROUP BY scale  "
						+ "UNION ALL  SELECT   CASE enterprise_type WHEN 'STATE_OWNED' THEN '国有企业' WHEN 'PRIVATE' THEN '民营企业' WHEN 'FOREIGN_OWNED' THEN '外资企业' WHEN 'OTHER' THEN '其他-企业性质' ELSE '未知-企业性质'   END classify,   COUNT(DISTINCT customer_id) hs,   COUNT(*) bs FROM ${pmDbTitle}.${projectTableName} WHERE custom_name1 = '在保' AND customer_id > 0 ${projectDateSql} AND (busi_type LIKE '1%' OR busi_type LIKE '2%' OR busi_type LIKE '3%') GROUP BY enterprise_type  "
						+ "UNION ALL  SELECT   '其中融资平台' classify,   COUNT(DISTINCT p.customer_id) hs,   COUNT(*) bs FROM ${pmDbTitle}.${projectTableName} p   JOIN ${pmDbTitle}.f_project_customer_base_info c     ON p.project_code = c.project_code WHERE custom_name1 = '在保' AND p.customer_id > 0 ${projectDateSql} AND (busi_type LIKE '1%' OR busi_type LIKE '2%' OR busi_type LIKE '3%') AND c.is_local_gov_platform = 'IS'  "
						+ "UNION ALL  SELECT   classify,   SUM(hs) hs,   SUM(bs) bs FROM (SELECT     CASE WHEN province_code = '500000' THEN '重庆' ELSE '异地'     END classify,     COUNT(DISTINCT customer_id) hs,     COUNT(*) bs   FROM ${pmDbTitle}.${projectTableName}   WHERE custom_name1 = '在保'   AND customer_id > 0 ${projectDateSql}   AND (busi_type LIKE '1%'   OR busi_type LIKE '2%'   OR busi_type LIKE '3%')   GROUP BY province_name) t GROUP BY t.classify  "
						+ "UNION ALL  SELECT     CASE WHEN (time_limit = 0 AND     time_unit IS NULL) THEN '诉保' WHEN time_unit = 'M' AND     time_limit < 12 THEN '1年以内' WHEN time_unit = 'M' AND     time_limit >= 12 AND     time_limit < 24 THEN '1年（含）至2年' WHEN time_unit = 'M' AND     time_limit >= 24 AND     time_limit < 36 THEN '2年（含）至3年' WHEN time_unit = 'M' AND     time_limit >= 36 AND     time_limit < 60 THEN '3年（含）至5年' WHEN time_unit = 'M' AND     time_limit >= 60 THEN '5年以以上' WHEN time_unit = 'Y' AND     time_limit < 1 THEN '1年以内' WHEN time_unit = 'Y' AND     time_limit >= 1 AND     time_limit < 2 THEN '1年（含）至2年' WHEN time_unit = 'Y' AND     time_limit >= 2 AND     time_limit < 3 THEN '2年（含）至3年' WHEN time_unit = 'Y' AND     time_limit >= 3 AND     time_limit < 5 THEN '3年（含）至5年' WHEN time_unit = 'Y' AND     time_limit >= 5 THEN '5年以以上' WHEN time_unit = 'D' AND     time_limit < 365 THEN '1年以内' WHEN time_unit = 'D' AND     time_limit >= 365 AND     time_limit < 730 THEN '1年（含）至2年' WHEN time_unit = 'D' AND     time_limit >= 730 AND     time_limit < 1095 THEN '2年（含）至3年' WHEN time_unit = 'D' AND     time_limit >= 1095 AND     time_limit < 1825 THEN '3年（含）至5年' WHEN time_unit = 'D' AND     time_limit >= 1825 THEN '5年以以上' ELSE '其他-期限'   END classify,   COUNT(DISTINCT customer_id) hs,   COUNT(*) bs FROM ${pmDbTitle}.${projectTableName} WHERE custom_name1 = '在保' AND customer_id > 0 ${projectDateSql} AND (busi_type LIKE '1%' OR busi_type LIKE '2%' OR busi_type LIKE '3%') GROUP BY classify  "
						+ "UNION ALL  SELECT   CASE WHEN industry_code LIKE 'A%' THEN '农、林、牧、渔业' WHEN industry_code LIKE 'C%' THEN '制造业' WHEN industry_code LIKE 'D%' THEN '电力热力水生产和供应业' WHEN industry_code LIKE 'E%' THEN '建筑业' WHEN industry_code LIKE 'K%' THEN '房地产业' WHEN industry_code LIKE 'G%' THEN '交通运输仓储和邮政业' WHEN industry_code LIKE 'L%' THEN '租赁和商务服务业' ELSE '其他-行业'   END classify,   COUNT(DISTINCT customer_id) hs,   COUNT(*) bs FROM ${pmDbTitle}.${projectTableName} WHERE custom_name1 = '在保' AND customer_id > 0 ${projectDateSql} AND (busi_type LIKE '1%' OR busi_type LIKE '2%' OR busi_type LIKE '3%') GROUP BY classify   "
						+ "UNION ALL  SELECT   CASE WHEN busi_type LIKE '11%' THEN '银行融资担保' WHEN busi_type LIKE '12%' THEN '债券融资担保' WHEN busi_type LIKE '13%' THEN '金融产品融资担保' WHEN busi_type LIKE '2%' THEN '非融资担保-1' ELSE '再担保-1'   END classify,   COUNT(DISTINCT customer_id) hs,   COUNT(*) bs FROM ${pmDbTitle}.${projectTableName} WHERE custom_name1 = '在保' AND customer_id > 0 ${projectDateSql} AND (busi_type LIKE '1%' OR busi_type LIKE '2%' OR busi_type LIKE '3%') GROUP BY classify  "
						+ "UNION ALL  SELECT   CASE c.channel_type WHEN 'YH' THEN '银行' WHEN 'ZQ' THEN '证券公司' WHEN 'XT' THEN '信托公司' WHEN 'JYPT' THEN '交易平台' WHEN 'JJ' THEN '基金公司' WHEN 'ZL' THEN '租赁公司' WHEN 'WLJR' THEN '网络金融平台' WHEN 'ZZYX' THEN '自主营销' WHEN 'BGS' THEN '本公司' ELSE '其他-资金渠道'   END classify,   COUNT(DISTINCT customer_id) hs,   COUNT(*) bs FROM ${pmDbTitle}.${projectTableName} p   JOIN ${pmDbTitle}.project_channel_relation c     ON p.project_code = c.project_code WHERE c.channel_relation = 'CAPITAL_CHANNEL' AND c.latest = 'YES' AND custom_name1 = '在保' AND customer_id > 0 ${projectDateSql} AND (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') GROUP BY classify ";
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
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
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String classify = String.valueOf(itm.getMap().get("classify"));
				String hs = String.valueOf(itm.getMap().get("hs"));
				String bs = String.valueOf(itm.getMap().get("bs"));
				GuaranteeClassifyInfo info = data.get(classify);
				if (info != null) {
					info.setZbbs(bs);
					info.setZbhs(hs);
				}
			}
		}
	}
	
	/**
	 * 上年余额、期末余额
	 * @param order
	 * @param data
	 */
	private void balance(ReportQueryOrder order, Map<String, GuaranteeClassifyInfo> data) {
		
		String sql = "SELECT   '总计' classify,  SUM(blance) balance FROM ${pmDbTitle}.${projectTableName} WHERE (busi_type LIKE '1%' OR busi_type LIKE '2%' OR busi_type LIKE '3%') ${projectDateSql} "
						+ "UNION ALL  SELECT   CASE WHEN busi_type LIKE '11%' THEN '间接融资担保' WHEN busi_type LIKE '12%' OR     busi_type LIKE '13%' THEN '直接融资担保' WHEN busi_type LIKE '2%' THEN '非融资担保' ELSE '再担保'   END classify,   SUM(blance) balance FROM ${pmDbTitle}.${projectTableName} WHERE (busi_type LIKE '1%' OR busi_type LIKE '2%' OR busi_type LIKE '3%')  ${projectDateSql}  GROUP BY classify  "
						+ "UNION ALL  SELECT   CASE scale WHEN 'HUGE' THEN '特大' WHEN 'BIG' THEN '大型' WHEN 'MEDIUM' THEN '中型' WHEN 'SMALL' THEN '小型' WHEN 'TINY' THEN '微型' ELSE '未知-规模'   END classify,   SUM(blance) balance FROM ${pmDbTitle}.${projectTableName} WHERE (busi_type LIKE '1%' OR busi_type LIKE '2%' OR busi_type LIKE '3%')  ${projectDateSql}  GROUP BY scale  "
						+ "UNION ALL  SELECT   CASE enterprise_type WHEN 'STATE_OWNED' THEN '国有企业' WHEN 'PRIVATE' THEN '民营企业' WHEN 'FOREIGN_OWNED' THEN '外资企业' WHEN 'OTHER' THEN '其他-企业性质' ELSE '未知-企业性质'   END classify,   SUM(blance) balance FROM ${pmDbTitle}.${projectTableName} WHERE (busi_type LIKE '1%' OR busi_type LIKE '2%' OR busi_type LIKE '3%') ${projectDateSql} GROUP BY enterprise_type  "
						+ "UNION ALL  SELECT   '其中融资平台' classify,   SUM(blance) balance FROM ${pmDbTitle}.${projectTableName} p   JOIN ${pmDbTitle}.f_project_customer_base_info c     ON p.project_code = c.project_code WHERE (busi_type LIKE '1%' OR busi_type LIKE '2%' OR busi_type LIKE '3%')  ${projectDateSql}  AND c.is_local_gov_platform = 'IS'  "
						+ "UNION ALL  SELECT   classify,   SUM(blance) balance FROM (SELECT     CASE WHEN province_code = '500000' THEN '重庆' ELSE '异地'     END classify,     sum(blance) blance   FROM ${pmDbTitle}.${projectTableName}   WHERE (busi_type LIKE '1%'   OR busi_type LIKE '2%'   OR busi_type LIKE '3%') ${projectDateSql} GROUP BY province_name) t GROUP BY t.classify  "
						+ "UNION ALL  SELECT     CASE WHEN (time_limit = 0 AND     time_unit IS NULL) THEN '诉保' WHEN time_unit = 'M' AND     time_limit < 12 THEN '1年以内' WHEN time_unit = 'M' AND     time_limit >= 12 AND     time_limit < 24 THEN '1年（含）至2年' WHEN time_unit = 'M' AND     time_limit >= 24 AND     time_limit < 36 THEN '2年（含）至3年' WHEN time_unit = 'M' AND     time_limit >= 36 AND     time_limit < 60 THEN '3年（含）至5年' WHEN time_unit = 'M' AND     time_limit >= 60 THEN '5年以以上' WHEN time_unit = 'Y' AND     time_limit < 1 THEN '1年以内' WHEN time_unit = 'Y' AND     time_limit >= 1 AND     time_limit < 2 THEN '1年（含）至2年' WHEN time_unit = 'Y' AND     time_limit >= 2 AND     time_limit < 3 THEN '2年（含）至3年' WHEN time_unit = 'Y' AND     time_limit >= 3 AND     time_limit < 5 THEN '3年（含）至5年' WHEN time_unit = 'Y' AND     time_limit >= 5 THEN '5年以以上' WHEN time_unit = 'D' AND     time_limit < 365 THEN '1年以内' WHEN time_unit = 'D' AND     time_limit >= 365 AND     time_limit < 730 THEN '1年（含）至2年' WHEN time_unit = 'D' AND     time_limit >= 730 AND     time_limit < 1095 THEN '2年（含）至3年' WHEN time_unit = 'D' AND     time_limit >= 1095 AND     time_limit < 1825 THEN '3年（含）至5年' WHEN time_unit = 'D' AND     time_limit >= 1825 THEN '5年以以上' ELSE '其他-期限'   END classify,   SUM(blance) balance FROM ${pmDbTitle}.${projectTableName} WHERE (busi_type LIKE '1%' OR busi_type LIKE '2%' OR busi_type LIKE '3%') ${projectDateSql}  GROUP BY classify  "
						+ "UNION ALL  SELECT   CASE WHEN industry_code LIKE 'A%' THEN '农、林、牧、渔业' WHEN industry_code LIKE 'C%' THEN '制造业' WHEN industry_code LIKE 'D%' THEN '电力热力水生产和供应业' WHEN industry_code LIKE 'E%' THEN '建筑业' WHEN industry_code LIKE 'K%' THEN '房地产业' WHEN industry_code LIKE 'G%' THEN '交通运输仓储和邮政业' WHEN industry_code LIKE 'L%' THEN '租赁和商务服务业' ELSE '其他-行业'   END classify,   SUM(blance) balance FROM ${pmDbTitle}.${projectTableName} WHERE (busi_type LIKE '1%' OR busi_type LIKE '2%' OR busi_type LIKE '3%') ${projectDateSql}   GROUP BY classify   "
						+ "UNION ALL  SELECT   CASE WHEN busi_type LIKE '11%' THEN '银行融资担保' WHEN busi_type LIKE '12%' THEN '债券融资担保' WHEN busi_type LIKE '13%' THEN '金融产品融资担保' WHEN busi_type LIKE '2%' THEN '非融资担保-1' ELSE '再担保-1'   END classify,   SUM(blance) balance FROM ${pmDbTitle}.${projectTableName} WHERE (busi_type LIKE '1%' OR busi_type LIKE '2%' OR busi_type LIKE '3%') ${projectDateSql}  GROUP BY classify  "
						+ "UNION ALL  SELECT   CASE c.channel_type WHEN 'YH' THEN '银行' WHEN 'ZQ' THEN '证券公司' WHEN 'XT' THEN '信托公司' WHEN 'JYPT' THEN '交易平台' WHEN 'JJ' THEN '基金公司' WHEN 'ZL' THEN '租赁公司' WHEN 'WLJR' THEN '网络金融平台' WHEN 'ZZYX' THEN '自主营销' WHEN 'BGS' THEN '本公司' ELSE '其他-资金渠道'   END classify,   SUM(blance) balance FROM ${pmDbTitle}.${projectTableName} p   JOIN ${pmDbTitle}.project_channel_relation c     ON p.project_code = c.project_code WHERE c.channel_relation = 'CAPITAL_CHANNEL' AND c.latest = 'YES' AND custom_name1 = '在保' AND customer_id > 0 ${projectDateSql} AND (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') GROUP BY classify ";
		
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		
		//上年余额
		String lastYearSql = sql.replaceAll("\\$\\{projectTableName\\}",
			"project_data_info_his_data").replaceAll("\\$\\{projectDateSql\\}",
			"AND project_date = '" + order.getReportLastYearEndDay() + "'");
		
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(lastYearSql);
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String classify = String.valueOf(itm.getMap().get("classify"));
				Object balance = itm.getMap().get("balance");
				GuaranteeClassifyInfo info = data.get(classify);
				if (info != null && balance != null) {
					info.setBalanceLastYear(toMoney(balance));
				}
			}
		}
		
		//期末余额
		if (order.isThisMonth()) {
			sql = sql.replaceAll("\\$\\{projectTableName\\}", "project_data_info");
			sql = sql.replaceAll("\\$\\{projectDateSql\\}", "");
		} else {
			sql = sql.replaceAll("\\$\\{projectTableName\\}", "project_data_info_his_data");
			sql = sql.replaceAll("\\$\\{projectDateSql\\}",
				"AND project_date = '" + order.getReportMonthEndDay() + "'");
		}
		
		queyOrder.setSql(sql);
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String classify = String.valueOf(itm.getMap().get("classify"));
				Object balance = itm.getMap().get("balance");
				GuaranteeClassifyInfo info = data.get(classify);
				if (info != null && balance != null) {
					info.setBalanceEnding(toMoney(balance));
				}
			}
		}
	}
	
	/**
	 * 本年新增
	 * @param order
	 * @param data
	 */
	private void increase(ReportQueryOrder order, Map<String, GuaranteeClassifyInfo> data) {
		
		String sql = "SELECT   '总计' classify,   IFNULL(SUM(r.occur_amount), 0) amount FROM ${pmDbTitle}.view_project_actual_occer_detail r   JOIN ${pmDbTitle}.project p     ON p.project_code = r.project_code WHERE (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') AND r.occur_date >= '${loanStartTime}' AND r.occur_date <= '${loanEndTime}'  "
						+ "UNION ALL  SELECT   CASE WHEN p.busi_type LIKE '11%' THEN '间接融资担保' WHEN p.busi_type LIKE '12%' OR p.busi_type LIKE '13%' THEN '直接融资担保' WHEN p.busi_type LIKE '2%' THEN '非融资担保' ELSE '其他担保'   END classify,   IFNULL(SUM(r.occur_amount), 0) amount FROM ${pmDbTitle}.view_project_actual_occer_detail r   JOIN ${pmDbTitle}.project p     ON p.project_code = r.project_code WHERE (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') AND r.occur_date >= '${loanStartTime}' AND r.occur_date <= '${loanEndTime}' GROUP BY classify  "
						+ "UNION ALL  SELECT   CASE scale WHEN 'HUGE' THEN '特大' WHEN 'BIG' THEN '大型' WHEN 'MEDIUM' THEN '中型' WHEN 'SMALL' THEN '小型' WHEN 'TINY' THEN '微型' ELSE '规模未知'   END classify,   IFNULL(SUM(r.occur_amount), 0) amount FROM ${pmDbTitle}.view_project_actual_occer_detail r   JOIN ${pmDbTitle}.project p     ON p.project_code = r.project_code   JOIN ${pmDbTitle}.f_project_customer_base_info c     ON c.project_code = p.project_code WHERE (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%')  AND r.occur_date >= '${loanStartTime}' AND r.occur_date <= '${loanEndTime}' GROUP BY scale  "
						+ "UNION ALL  SELECT   CASE enterprise_type WHEN 'STATE_OWNED' THEN '国有企业' WHEN 'PRIVATE' THEN '民营企业' WHEN 'FOREIGN_OWNED' THEN '外资企业' WHEN 'OTHER' THEN '其他-企业性质' ELSE '未知-企业性质'   END classify,   IFNULL(SUM(r.occur_amount), 0) amount FROM ${pmDbTitle}.view_project_actual_occer_detail r   JOIN ${pmDbTitle}.project p     ON p.project_code = r.project_code   JOIN ${pmDbTitle}.f_project_customer_base_info c     ON c.project_code = p.project_code WHERE (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') AND r.occur_date >= '${loanStartTime}' AND r.occur_date <= '${loanEndTime}' GROUP BY enterprise_type  "
						+ "UNION ALL  SELECT   '其中融资平台' classify,   IFNULL(SUM(r.occur_amount), 0) amount FROM ${pmDbTitle}.view_project_actual_occer_detail r   JOIN ${pmDbTitle}.project p     ON p.project_code = r.project_code   JOIN ${pmDbTitle}.f_project_customer_base_info c     ON c.project_code = p.project_code WHERE (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') AND r.occur_date >= '${loanStartTime}' AND r.occur_date <= '${loanEndTime}' AND c.is_local_gov_platform = 'IS'  "
						+ "UNION ALL  SELECT   classify,   IFNULL(SUM(occur_amount), 0) amount FROM (SELECT     CASE WHEN province_code = '500000' THEN '重庆' ELSE '异地'     END classify,     SUM(r.occur_amount) occur_amount   FROM ${pmDbTitle}.view_project_actual_occer_detail r     JOIN ${pmDbTitle}.project p       ON p.project_code = r.project_code     JOIN ${pmDbTitle}.f_project_customer_base_info c       ON c.project_code = p.project_code   WHERE (p.busi_type LIKE '1%'   OR p.busi_type LIKE '2%'   OR p.busi_type LIKE '3%')  AND r.occur_date >= '${loanStartTime}'   AND r.occur_date <= '${loanEndTime}'   GROUP BY province_name) t GROUP BY t.classify  "
						+ "UNION ALL  SELECT   CASE WHEN (p.time_limit = 0 AND p.time_unit IS NULL) THEN '诉保' WHEN p.time_unit = 'M' AND p.time_limit < 12 THEN '1年以内' WHEN p.time_unit = 'M' AND p.time_limit >= 12 AND p.time_limit < 24 THEN '1年（含）至2年' WHEN p.time_unit = 'M' AND p.time_limit >= 24 AND p.time_limit < 36 THEN '2年（含）至3年' WHEN p.time_unit = 'M' AND  p.time_limit >= 36 AND  p.time_limit < 60 THEN '3年（含）至5年' WHEN p.time_unit = 'M' AND p.time_limit >= 60 THEN '5年以以上' WHEN p.time_unit = 'Y' AND p.time_limit < 1 THEN '1年以内' WHEN p.time_unit = 'Y' AND p.time_limit >= 1 AND p.time_limit < 2 THEN '1年（含）至2年' WHEN p.time_unit = 'Y' AND p.time_limit >= 2 AND p.time_limit < 3 THEN '2年（含）至3年' WHEN p.time_unit = 'Y' AND p.time_limit >= 3 AND p.time_limit < 5 THEN '3年（含）至5年' WHEN p.time_unit = 'Y' AND p.time_limit >= 5 THEN '5年以以上' WHEN p.time_unit = 'D' AND p.time_limit < 365 THEN '1年以内' WHEN p.time_unit = 'D' AND p.time_limit >= 365 AND p.time_limit < 730 THEN '1年（含）至2年' WHEN p.time_unit = 'D' AND  p.time_limit >= 730 AND p.time_limit < 1095 THEN '2年（含）至3年' WHEN p.time_unit = 'D' AND p.time_limit >= 1095 AND p.time_limit < 1825 THEN '3年（含）至5年' WHEN p.time_unit = 'D' AND p.time_limit >= 1825 THEN '5年以以上' ELSE '其他-期限'   END classify,   IFNULL(SUM(r.occur_amount), 0) amount FROM ${pmDbTitle}.view_project_actual_occer_detail r   JOIN ${pmDbTitle}.project p     ON p.project_code = r.project_code WHERE (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') AND r.occur_date >= '${loanStartTime}' AND r.occur_date <= '${loanEndTime}' GROUP BY classify  "
						+ "UNION ALL  SELECT   CASE WHEN p.industry_code LIKE 'A%' THEN '农、林、牧、渔业' WHEN p.industry_code LIKE 'C%' THEN '制造业' WHEN p.industry_code LIKE 'D%' THEN '电力热力水生产和供应业' WHEN p.industry_code LIKE 'E%' THEN '建筑业' WHEN p.industry_code LIKE 'K%' THEN '房地产业' WHEN p.industry_code LIKE 'G%' THEN '交通运输仓储和邮政业' WHEN p.industry_code LIKE 'L%' THEN '租赁和商务服务业' ELSE '其他-行业'   END classify,   IFNULL(SUM(r.occur_amount), 0) amount FROM ${pmDbTitle}.view_project_actual_occer_detail r   JOIN ${pmDbTitle}.project p     ON p.project_code = r.project_code WHERE (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') AND r.occur_date >= '${loanStartTime}' AND r.occur_date <= '${loanEndTime}' GROUP BY classify   "
						+ "UNION ALL  SELECT   CASE WHEN p.busi_type LIKE '11%' THEN '银行融资担保' WHEN p.busi_type LIKE '12%' THEN '债券融资担保' WHEN p.busi_type LIKE '13%' THEN '金融产品融资担保' WHEN p.busi_type LIKE '2%' THEN '非融资担保-1' ELSE '再担保-1'   END classify,   IFNULL(SUM(r.occur_amount), 0) amount FROM ${pmDbTitle}.view_project_actual_occer_detail r   JOIN ${pmDbTitle}.project p     ON p.project_code = r.project_code WHERE (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') AND r.occur_date >= '${loanStartTime}' AND r.occur_date <= '${loanEndTime}' GROUP BY classify  "
						+ "UNION ALL  SELECT   CASE r.capital_channel_type WHEN 'YH' THEN '银行' WHEN 'ZQ' THEN '证券公司' WHEN 'XT' THEN '信托公司' WHEN 'JYPT' THEN '交易平台' WHEN 'JJ' THEN '基金公司' WHEN 'ZL' THEN '租赁公司' WHEN 'WLJR' THEN '网络金融平台' WHEN 'ZZYX' THEN '自主营销' WHEN 'BGS' THEN '本公司' ELSE '其他-资金渠道'      END classify,   IFNULL(SUM(r.occur_amount), 0) amount FROM ${pmDbTitle}.view_project_actual_occer_detail r   JOIN ${pmDbTitle}.project p     ON p.project_code = r.project_code WHERE (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') AND r.occur_date >= '${loanStartTime}' AND r.occur_date <= '${loanEndTime}' GROUP BY classify ";
		
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		sql = sql.replaceAll("\\$\\{loanStartTime\\}", order.getReportYearStartDay()).replaceAll(
			"\\$\\{loanEndTime\\}", order.getReportMonthEndDay());
		
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(sql);
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String classify = String.valueOf(itm.getMap().get("classify"));
				Object amount = itm.getMap().get("amount");
				GuaranteeClassifyInfo info = data.get(classify);
				if (info != null && amount != null) {
					info.setIncrease(toMoney(amount));
				}
			}
		}
		
	}
	
	/**
	 * 本年还款
	 * @param order
	 * @param data
	 */
	private void repay(ReportQueryOrder order, Map<String, GuaranteeClassifyInfo> data) {
		
		String sql = "  SELECT   '总计' classify,   IFNULL(SUM(r.repay_amount), 0) amount FROM  ${pmDbTitle}.view_project_repay_detail r   JOIN  ${pmDbTitle}.project p     ON p.project_code = r.project_code WHERE (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') AND r.repay_type in ('解保','代偿','诉保解保') AND r.repay_time >= '${repayTimeStart}' AND r.repay_time <= '${repayTimeEnd}'  "
						+ "UNION ALL  SELECT   CASE WHEN p.busi_type LIKE '11%' THEN '间接融资担保' WHEN p.busi_type LIKE '12%' OR     p.busi_type LIKE '13%' THEN '直接融资担保' WHEN p.busi_type LIKE '2%' THEN '非融资担保' ELSE '其他担保'   END classify,   IFNULL(SUM(r.repay_amount), 0) amount FROM  ${pmDbTitle}.view_project_repay_detail r   JOIN  ${pmDbTitle}.project p     ON p.project_code = r.project_code WHERE (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') AND r.repay_type in ('解保','代偿','诉保解保') AND r.repay_time >= '${repayTimeStart}' AND r.repay_time <= '${repayTimeEnd}' GROUP BY classify  "
						+ "UNION ALL  SELECT   CASE scale WHEN 'HUGE' THEN '特大' WHEN 'BIG' THEN '大型' WHEN 'MEDIUM' THEN '中型' WHEN 'SMALL' THEN '小型' WHEN 'TINY' THEN '微型' ELSE '规模未知'   END classify,   IFNULL(SUM(r.repay_amount), 0) amount FROM  ${pmDbTitle}.view_project_repay_detail r   JOIN  ${pmDbTitle}.project p     ON p.project_code = r.project_code   JOIN  ${pmDbTitle}.f_project_customer_base_info c     ON c.project_code = p.project_code WHERE (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') AND r.repay_type in ('解保','代偿','诉保解保') AND r.repay_time >= '${repayTimeStart}' AND r.repay_time <= '${repayTimeEnd}' GROUP BY scale  "
						+ "UNION ALL   SELECT   CASE enterprise_type WHEN 'STATE_OWNED' THEN '国有企业' WHEN 'PRIVATE' THEN '民营企业' WHEN 'FOREIGN_OWNED' THEN '外资企业' WHEN 'OTHER' THEN '其他-企业性质' ELSE '未知-企业性质'   END classify,   IFNULL(SUM(r.repay_amount), 0) amount FROM  ${pmDbTitle}.view_project_repay_detail r   JOIN  ${pmDbTitle}.project p     ON p.project_code = r.project_code   JOIN  ${pmDbTitle}.f_project_customer_base_info c     ON c.project_code = p.project_code WHERE (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') AND r.repay_type in ('解保','代偿','诉保解保') AND r.repay_time >= '${repayTimeStart}' AND r.repay_time <= '${repayTimeEnd}' GROUP BY enterprise_type  "
						+ "UNION ALL  SELECT   '其中融资平台' classify,   IFNULL(SUM(r.repay_amount), 0) amount FROM  ${pmDbTitle}.view_project_repay_detail r   JOIN  ${pmDbTitle}.project p     ON p.project_code = r.project_code   JOIN  ${pmDbTitle}.f_project_customer_base_info c     ON c.project_code = p.project_code WHERE (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') AND r.repay_type in ('解保','代偿','诉保解保') AND r.repay_time >= '${repayTimeStart}' AND r.repay_time <= '${repayTimeEnd}' AND c.is_local_gov_platform = 'IS'  "
						+ "UNION ALL  SELECT   classify,   IFNULL(SUM(repay_amount), 0) amount FROM (SELECT     CASE WHEN province_code = '500000' THEN '重庆' ELSE '异地'     END classify,     SUM(r.repay_amount) repay_amount   FROM  ${pmDbTitle}.view_project_repay_detail r     JOIN  ${pmDbTitle}.project p       ON p.project_code = r.project_code     JOIN  ${pmDbTitle}.f_project_customer_base_info c       ON c.project_code = p.project_code   WHERE (p.busi_type LIKE '1%'   OR p.busi_type LIKE '2%'   OR p.busi_type LIKE '3%')   AND r.repay_type in ('解保','代偿','诉保解保')   AND r.repay_time >= '${repayTimeStart}'   AND r.repay_time <= '${repayTimeEnd}'   GROUP BY province_name) t GROUP BY t.classify  "
						+ "UNION ALL  SELECT     CASE WHEN (time_limit = 0 AND     time_unit IS NULL) THEN '诉保' WHEN time_unit = 'M' AND     time_limit < 12 THEN '1年以内' WHEN time_unit = 'M' AND     time_limit >= 12 AND     time_limit < 24 THEN '1年（含）至2年' WHEN time_unit = 'M' AND     time_limit >= 24 AND     time_limit < 36 THEN '2年（含）至3年' WHEN time_unit = 'M' AND     time_limit >= 36 AND     time_limit < 60 THEN '3年（含）至5年' WHEN time_unit = 'M' AND     time_limit >= 60 THEN '5年以以上' WHEN time_unit = 'Y' AND     time_limit < 1 THEN '1年以内' WHEN time_unit = 'Y' AND     time_limit >= 1 AND     time_limit < 2 THEN '1年（含）至2年' WHEN time_unit = 'Y' AND     time_limit >= 2 AND     time_limit < 3 THEN '2年（含）至3年' WHEN time_unit = 'Y' AND     time_limit >= 3 AND     time_limit < 5 THEN '3年（含）至5年' WHEN time_unit = 'Y' AND     time_limit >= 5 THEN '5年以以上' WHEN time_unit = 'D' AND     time_limit < 365 THEN '1年以内' WHEN time_unit = 'D' AND     time_limit >= 365 AND     time_limit < 730 THEN '1年（含）至2年' WHEN time_unit = 'D' AND     time_limit >= 730 AND     time_limit < 1095 THEN '2年（含）至3年' WHEN time_unit = 'D' AND     time_limit >= 1095 AND     time_limit < 1825 THEN '3年（含）至5年' WHEN time_unit = 'D' AND     time_limit >= 1825 THEN '5年以以上' ELSE '其他-期限'   END classify,   IFNULL(SUM(r.repay_amount), 0) amount FROM  ${pmDbTitle}.view_project_repay_detail r   JOIN  ${pmDbTitle}.project p     ON p.project_code = r.project_code WHERE (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') AND r.repay_type in ('解保','代偿','诉保解保') AND r.repay_time >= '${repayTimeStart}' AND r.repay_time <= '${repayTimeEnd}' GROUP BY classify  "
						+ "UNION ALL  SELECT   CASE WHEN p.industry_code LIKE 'A%' THEN '农、林、牧、渔业' WHEN p.industry_code LIKE 'C%' THEN '制造业' WHEN p.industry_code LIKE 'D%' THEN '电力热力水生产和供应业' WHEN p.industry_code LIKE 'E%' THEN '建筑业' WHEN p.industry_code LIKE 'K%' THEN '房地产业' WHEN p.industry_code LIKE 'G%' THEN '交通运输仓储和邮政业' WHEN p.industry_code LIKE 'L%' THEN '租赁和商务服务业' ELSE '其他-行业'   END classify,   IFNULL(SUM(r.repay_amount), 0) amount FROM  ${pmDbTitle}.view_project_repay_detail r   JOIN  ${pmDbTitle}.project p     ON p.project_code = r.project_code WHERE (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') AND r.repay_type in ('解保','代偿','诉保解保') AND r.repay_time >= '${repayTimeStart}' AND r.repay_time <= '${repayTimeEnd}' GROUP BY classify   "
						+ "UNION ALL  SELECT   CASE WHEN p.busi_type LIKE '11%' THEN '银行融资担保' WHEN p.busi_type LIKE '12%' THEN '债券融资担保' WHEN p.busi_type LIKE '13%' THEN '金融产品融资担保' WHEN p.busi_type LIKE '2%' THEN '非融资担保-1' ELSE '再担保-1'   END classify,   IFNULL(SUM(r.repay_amount), 0) amount FROM  ${pmDbTitle}.view_project_repay_detail r   JOIN  ${pmDbTitle}.project p     ON p.project_code = r.project_code WHERE (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') AND r.repay_type in ('解保','代偿','诉保解保') AND r.repay_time >= ' ${repayTimeStart}' AND r.repay_time <= ' ${repayTimeEnd}' GROUP BY classify  "
						+ "UNION ALL   SELECT   CASE r.capital_channel_type WHEN 'YH' THEN '银行' WHEN 'ZQ' THEN '证券公司' WHEN 'XT' THEN '信托公司' WHEN 'JYPT' THEN '交易平台' WHEN 'JJ' THEN '基金公司' WHEN 'ZL' THEN '租赁公司' WHEN 'WLJR' THEN '网络金融平台' WHEN 'ZZYX' THEN '自主营销' WHEN 'BGS' THEN '本公司' ELSE '其他-资金渠道'     END classify,   IFNULL(SUM(r.repay_amount), 0) amount FROM  ${pmDbTitle}.f_counter_guarantee_repay r   JOIN  ${pmDbTitle}.f_counter_guarantee_release g     ON r.form_id = g.form_id   JOIN  ${pmDbTitle}.project p     ON p.project_code = g.project_code   JOIN  ${pmDbTitle}.form f     ON g.form_id = f.form_id     AND f.status = 'APPROVAL' WHERE (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') AND r.repay_time >= '${repayTimeStart}' AND r.repay_time <= '${repayTimeEnd}' GROUP BY classify  ";
		
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		sql = sql.replaceAll("\\$\\{repayTimeStart\\}", order.getReportYearStartDay()).replaceAll(
			"\\$\\{repayTimeEnd\\}", order.getReportMonthEndDay());
		
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(sql);
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String classify = String.valueOf(itm.getMap().get("classify"));
				Object amount = itm.getMap().get("amount");
				GuaranteeClassifyInfo info = data.get(classify);
				if (info != null && amount != null) {
					info.setRepayThisYear(toMoney(amount));
				}
			}
		}
	}
	
	//转化Money
	private Money toMoney(Object fen) {
		try {
			String s = (fen == null ? "" : String.valueOf(fen));
			return Money.amout(s).divide(100);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return Money.zero();
	}
}
