package com.born.fcs.rm.biz.service.report.inner;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.born.fcs.rm.ws.info.report.inner.GuaranteeCustomerData;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.yjf.common.lang.util.money.Money;

/**
 * 担保业务客户分类汇总表
 * 
 * */
@Service("guaranteeCustomerServise")
public class GuaranteeCustomerServise extends BaseAutowiredDomainService implements
																		ReportProcessService {
	
	@Autowired
	private PmReportServiceClient pmReportServiceClient;
	
	@Override
	public FcsBaseResult save(ReportOrder order) {
		return null;
	}
	
	@Override
	public Object findByAccountPeriod(ReportQueryOrder queryOrder) {
		
		QueryBaseBatchResult<GuaranteeCustomerData> result = new QueryBaseBatchResult<GuaranteeCustomerData>();
		List<GuaranteeCustomerData> data = new ArrayList<GuaranteeCustomerData>();
		Map<String, GuaranteeCustomerData> rsMap = new HashMap<String, GuaranteeCustomerData>();
		//新增客户数
		newCustomer(queryOrder, rsMap);
		//新增项目数
		newProject(queryOrder, rsMap);
		//在保客户，在保项目
		inCustomerAndProject(queryOrder, rsMap);
		//在保余额
		blance(queryOrder, rsMap);
		//合同金额
		contractAmount(queryOrder, rsMap);
		//年初在保余额
		nczbye(queryOrder, rsMap);
		//本年累计发生额
		bnljfse(queryOrder, rsMap);
		//本年累计解保额
		bnljjb(queryOrder, rsMap);
		
		GuaranteeCustomerData hj = new GuaranteeCustomerData();
		if (rsMap.containsKey("合计")) {
			hj = rsMap.get("合计");
			if (hj.getZbAmount().getCent() > 0) {
				hj.setYezb(100);
			}
			if (hj.getFsAmount().getCent() > 0)
				hj.setFszb(100);
		}
		String[] showRan = "按客户性质分类:国有企业;按客户性质分类:其中：融资平台;按客户性质分类:民营企业;按客户性质分类:外资企业;按客户性质分类:其他"
			.split(";");
		setRs(rsMap, hj, showRan, data);
		hj.setTypes("按客户性质分类");
		data.add(hj);
		
		showRan = "按客户规模分类:大型企业;按客户规模分类:中型企业;按客户规模分类:小型企业;按客户规模分类:微型企业;按客户规模分类:其他企业".split(";");
		setRs(rsMap, hj, showRan, data);
		hj.setTypes("按客户规模分类");
		data.add(hj);
		
		showRan = "按客户行业分类:农、林、牧、渔业;按客户行业分类:制造业;按客户行业分类:电力、热力、燃气及水生产和供应业;按客户行业分类:建筑业;按客户行业分类:房地产业;按客户行业分类:交通运输、仓储和邮政业;按客户行业分类:租赁和商务服务业;按客户行业分类:其他行业"
			.split(";");
		setRs(rsMap, hj, showRan, data);
		hj.setTypes("按客户行业分类");
		data.add(hj);
		
		setAreaData(rsMap, data, hj);
		hj.setTypes("按客户地区分类");
		data.add(hj);
		
		result.setPageList(data);
		result.setSuccess(true);
		return result;
		
	}
	
	/** 占比计算 */
	private void zbJs(GuaranteeCustomerData info, GuaranteeCustomerData hj) {
		if (info.getFsAmount().getCent() > 0) {
			Double zb = (Double.parseDouble(String.valueOf(info.getFsAmount())) / Double
				.parseDouble(String.valueOf(hj.getFsAmount()))) * 100.00;
			info.setFszb(Math.rint(zb * 100) / 100.0);
		}
		if (info.getZbAmount().getCent() > 0) {
			Double zb = (Double.parseDouble(String.valueOf(info.getZbAmount())) / Double
				.parseDouble(String.valueOf(hj.getZbAmount()))) * 100.00;
			info.setYezb(Math.rint(zb * 100) / 100.0);
		}
	}
	
	/** 按地区分类数据 */
	private void setAreaData(Map<String, GuaranteeCustomerData> rsMap,
								List<GuaranteeCustomerData> data, GuaranteeCustomerData hj) {
		GuaranteeCustomerData title = new GuaranteeCustomerData();
		title.setTypes("按客户地区分类");
		title.setClassify("重庆");
		title.setTitle(true);
		data.add(title);
		GuaranteeCustomerData info = init("按客户地区分类");
		info.setClassify("主城");
		if (rsMap.containsKey("主城")) {
			info = rsMap.get("主城");
			rsMap.remove("主城");
		}
		info.setChildren(true);
		info.setTypes("按客户地区分类");
		zbJs(info, hj);
		data.add(info);
		//重庆区县
		List<GuaranteeCustomerData> list1 = new ArrayList<>();
		//外省
		List<GuaranteeCustomerData> list2 = new ArrayList<>();
		for (String s : rsMap.keySet()) {
			GuaranteeCustomerData d = rsMap.get(s);
			d.setTypes("按客户地区分类");
			d.setClassify(s.substring(s.indexOf("-") + 1));
			d.setChildren(true);
			zbJs(d, hj);
			if (s.indexOf("1-") > -1) {
				list1.add(d);
			} else if (s.indexOf("2-") > -1) {
				list2.add(d);
			}
		}
		data.addAll(list1);
		GuaranteeCustomerData title1 = new GuaranteeCustomerData();
		title1.setTypes("按客户地区分类");
		title1.setClassify("异地");
		title1.setTitle(true);
		data.add(title1);
		
		data.addAll(list2);
		
		if (rsMap.containsKey("其他地区")) {
			GuaranteeCustomerData d = rsMap.get("其他地区");
			d.setTypes("按客户地区分类");
			d.setChildren(true);
			zbJs(d, hj);
			data.add(d);
		}
		
	}
	
	/** 处理返回数据 */
	private void setRs(Map<String, GuaranteeCustomerData> rsMap, GuaranteeCustomerData hj,
						String[] showRan, List<GuaranteeCustomerData> data) {
		for (String s : showRan) {
			String types = s.split(":")[0];
			String classify = s.split(":")[1];
			GuaranteeCustomerData info = new GuaranteeCustomerData();
			info.setClassify(classify);
			if (rsMap.containsKey(classify)) {
				info = rsMap.get(classify);
				rsMap.remove(classify);
			}
			info.setTypes(types);
			zbJs(info, hj);
			data.add(info);
		}
	}
	
	/** 本年累计解保额 */
	private void bnljjb(ReportQueryOrder queryOrder, Map<String, GuaranteeCustomerData> rsMap) {
		//解保金额
		//		String sql = "SELECT CASE enterprise_type WHEN 'STATE_OWNED' THEN  '国有企业' WHEN  'PRIVATE' THEN '民营企业' WHEN 'FOREIGN_OWNED' THEN '外资企业' ELSE '其他' END classify,   IFNULL(SUM(fr.repay_amount),0) rs  FROM f_counter_guarantee_repay fr JOIN form fm ON fr.form_id=fm.form_id  JOIN f_counter_guarantee_release f ON f.form_id=fr.form_id JOIN project_data_info p ON f.project_code=p.project_code WHERE p.busi_type REGEXP '^1|^2|^3'  AND fm.status='APPROVAL' AND  fr.repay_confirm_time >= 'yearStartTime' AND fr.repay_confirm_time<='endDate' GROUP BY classify "
		//						+ " UNION ALL  SELECT '合计' classify,IFNULL(SUM(fr.repay_amount),0) rs  FROM f_counter_guarantee_repay fr JOIN form fm ON fr.form_id=fm.form_id  JOIN f_counter_guarantee_release f ON f.form_id=fr.form_id JOIN project_data_info p ON f.project_code=p.project_code WHERE p.busi_type REGEXP '^1|^2|^3'  AND  fr.repay_confirm_time > 'lastYearEndDay' AND fr.repay_confirm_time<='endDate' AND fm.status='APPROVAL'    GROUP BY classify  "
		//						+ " UNION ALL  SELECT CASE scale  WHEN 'BIG' THEN '大型企业' WHEN 'MEDIUM' THEN '中型企业' WHEN 'SMALL' THEN '小型企业' WHEN 'TINY' THEN '微型企业' ELSE '其他企业'   END classify,  IFNULL(SUM(fr.repay_amount),0) rs  FROM f_counter_guarantee_repay fr JOIN form fm ON fr.form_id=fm.form_id  JOIN f_counter_guarantee_release f ON f.form_id=fr.form_id JOIN project_data_info p ON f.project_code=p.project_code WHERE p.busi_type REGEXP '^1|^2|^3'  AND  fr.repay_confirm_time >= 'yearStartTime' AND fr.repay_confirm_time<='endDate' AND fm.status='APPROVAL' GROUP BY classify  "
		//						+ " UNION ALL  SELECT CASE  WHEN industry_code LIKE 'A%' THEN '农、林、牧、渔业' WHEN industry_code LIKE 'C%' THEN '制造业' WHEN industry_code LIKE 'D%' THEN '电力、热力、燃气及水生产和供应业' WHEN industry_code LIKE 'E%' THEN '建筑业' WHEN industry_code LIKE 'K%' THEN '房地产业' WHEN industry_code LIKE 'G%' THEN '交通运输、仓储和邮政业' WHEN industry_code LIKE 'L%' THEN '租赁和商务服务业' ELSE '其他行业'   END classify,IFNULL(SUM(fr.repay_amount),0) rs from f_counter_guarantee_repay fr JOIN form fm ON fr.form_id=fm.form_id  JOIN f_counter_guarantee_release f ON f.form_id=fr.form_id JOIN project_data_info p ON f.project_code=p.project_code WHERE p.busi_type REGEXP '^1|^2|^3'  AND  fr.repay_confirm_time >= 'yearStartTime' AND fr.repay_confirm_time<='endDate' AND fm.status='APPROVAL' GROUP BY classify  "
		//						+ " UNION ALL  SELECT '其中：融资平台' classify, IFNULL(SUM(fr.repay_amount),0) rs   FROM f_counter_guarantee_repay fr JOIN form fm ON fr.form_id=fm.form_id  JOIN f_counter_guarantee_release f ON f.form_id=fr.form_id JOIN  project_data_info p ON f.project_code=p.project_code  JOIN f_project_customer_base_info c     ON p.project_code = c.project_code WHERE p.busi_type REGEXP '^1|^2|^3'  AND  fr.repay_confirm_time >= 'yearStartTime' AND fr.repay_confirm_time<='endDate' AND c.is_local_gov_platform = 'IS' AND p.enterprise_type='STATE_OWNED' AND fm.status='APPROVAL' GROUP BY classify   "
		//						+ " UNION ALL  SELECT CASE WHEN  county_name REGEXP '^九龙坡|^渝|^巴南|^大渡口|^沙坪|^南岸|^江北' THEN '主城' ELSE   CONCAT('1-',county_name)  END  classify,   IFNULL(SUM(fr.repay_amount),0) rs  FROM f_counter_guarantee_repay fr JOIN form fm ON fr.form_id=fm.form_id  JOIN f_counter_guarantee_release f ON f.form_id=fr.form_id JOIN project_data_info_his_data p ON f.project_code=p.project_code WHERE p.busi_type REGEXP '^1|^2|^3'  AND  fr.repay_confirm_time >= 'yearStartTime' AND fr.repay_confirm_time<='endDate' AND p.province_name='重庆市' AND fm.status='APPROVAL'  GROUP BY classify  "
		//						+ " UNION ALL  SELECT CASE WHEN  c.country_name = '中国'  AND c.province_name !='重庆市' AND c.province_name IS NOT NULL  THEN  CONCAT('2-',c.province_name)  ELSE   '其他地区'  END  classify,IFNULL(SUM(fr.repay_amount),0) rs   FROM f_counter_guarantee_repay fr JOIN form fm ON fr.form_id=fm.form_id  JOIN f_counter_guarantee_release f ON f.form_id=fr.form_id JOIN f_project_customer_base_info c ON f.project_code = c.project_code join project p on p.project_code=f.project_code  WHERE  p.busi_type REGEXP '^1|^2|^3'  AND fr.repay_confirm_time >= 'yearStartTime' AND fr.repay_confirm_time<='endDate' AND fm.status='APPROVAL' GROUP BY classify";
		
		String sql = "SELECT CASE enterprise_type WHEN 'STATE_OWNED' THEN  '国有企业' WHEN  'PRIVATE' THEN '民营企业' WHEN 'FOREIGN_OWNED' THEN '外资企业' ELSE '其他' END classify,   IFNULL(SUM(v.repay_amount),0) rs  FROM view_project_repay_detail v JOIN project_data_info p ON v.project_code=p.project_code WHERE p.busi_type REGEXP '^1|^2|^3'   AND  v.repay_confirm_time >= 'yearStartTime' AND v.repay_confirm_time<='endDate' GROUP BY classify "
						+ " UNION ALL  SELECT '合计' classify,IFNULL(SUM(v.repay_amount),0) rs  FROM view_project_repay_detail v JOIN project_data_info p ON v.project_code=p.project_code WHERE p.busi_type REGEXP '^1|^2|^3'  AND  v.repay_confirm_time > 'lastYearEndDay' AND v.repay_confirm_time<='endDate'     GROUP BY classify  "
						+ " UNION ALL  SELECT CASE scale  WHEN 'BIG' THEN '大型企业' WHEN 'MEDIUM' THEN '中型企业' WHEN 'SMALL' THEN '小型企业' WHEN 'TINY' THEN '微型企业' ELSE '其他企业'   END classify,  IFNULL(SUM(v.repay_amount),0) rs  FROM view_project_repay_detail v JOIN project_data_info p ON v.project_code=p.project_code WHERE p.busi_type REGEXP '^1|^2|^3'  AND  v.repay_confirm_time >= 'yearStartTime' AND v.repay_confirm_time<='endDate'  GROUP BY classify  "
						+ " UNION ALL  SELECT CASE  WHEN industry_code LIKE 'A%' THEN '农、林、牧、渔业' WHEN industry_code LIKE 'C%' THEN '制造业' WHEN industry_code LIKE 'D%' THEN '电力、热力、燃气及水生产和供应业' WHEN industry_code LIKE 'E%' THEN '建筑业' WHEN industry_code LIKE 'K%' THEN '房地产业' WHEN industry_code LIKE 'G%' THEN '交通运输、仓储和邮政业' WHEN industry_code LIKE 'L%' THEN '租赁和商务服务业' ELSE '其他行业'   END classify,IFNULL(SUM(v.repay_amount),0) rs from view_project_repay_detail v JOIN project_data_info p ON v.project_code=p.project_code WHERE p.busi_type REGEXP '^1|^2|^3'  AND  v.repay_confirm_time >= 'yearStartTime' AND v.repay_confirm_time<='endDate'  GROUP BY classify  "
						+ " UNION ALL  SELECT '其中：融资平台' classify, IFNULL(SUM(v.repay_amount),0) rs   FROM view_project_repay_detail v JOIN  project_data_info p ON v.project_code=p.project_code  JOIN f_project_customer_base_info c     ON p.project_code = c.project_code WHERE p.busi_type REGEXP '^1|^2|^3'  AND  v.repay_confirm_time >= 'yearStartTime' AND v.repay_confirm_time<='endDate' AND c.is_local_gov_platform = 'IS' AND p.enterprise_type='STATE_OWNED'  GROUP BY classify   "
						+ " UNION ALL  SELECT CASE WHEN  county_name REGEXP '^九龙坡|^渝|^巴南|^大渡口|^沙坪|^南岸|^江北' THEN '主城' ELSE   CONCAT('1-',county_name)  END  classify,   IFNULL(SUM(v.repay_amount),0) rs  FROM view_project_repay_detail v JOIN project_data_info_his_data p ON v.project_code=p.project_code WHERE p.busi_type REGEXP '^1|^2|^3'  AND  v.repay_confirm_time >= 'yearStartTime' AND v.repay_confirm_time<='endDate' AND p.province_name='重庆市'   GROUP BY classify  "
						+ " UNION ALL  SELECT CASE WHEN  c.country_name = '中国'  AND c.province_name !='重庆市' AND c.province_name IS NOT NULL  THEN  CONCAT('2-',c.province_name)  ELSE   '其他地区'  END  classify,IFNULL(SUM(v.repay_amount),0) rs   FROM view_project_repay_detail v JOIN f_project_customer_base_info c ON v.project_code = c.project_code join project p on p.project_code=v.project_code  WHERE  p.busi_type REGEXP '^1|^2|^3'  AND v.repay_confirm_time >= 'yearStartTime' AND v.repay_confirm_time<='endDate'  GROUP BY classify";
		
		//查询数据 
		logger.info("担保业务客户分类汇总表- 本年累计解保额");
		List<DataListItem> result = getResult(queryOrder, sql);
		if (result == null)
			return;
		for (DataListItem itm : result) {
			String classify = String.valueOf(itm.getMap().get("classify"));
			String rs = String
				.valueOf(Double.parseDouble(String.valueOf(itm.getMap().get("rs"))) / 100);
			if (rsMap.containsKey(classify)) {
				rsMap.get(classify).setJbAmount(Money.amout(rs));
			} else {
				GuaranteeCustomerData data = new GuaranteeCustomerData();
				data.setClassify(classify);
				data.setJbAmount(Money.amout(rs));
				rsMap.put(classify, data);
			}
		}
		//		//加上 本年累计代偿金额
		//		sql = "SELECT CASE f.enterprise_type WHEN 'STATE_OWNED' THEN  '国有企业' WHEN  'PRIVATE' THEN '民营企业' WHEN 'FOREIGN_OWNED' THEN '外资企业' ELSE '其他' END classify,   IFNULL(SUM(v.repay_amount),0) rs  FROM view_project_repay_detail v  JOIN f_project_customer_base_info f ON f.customer_id=v.customer_id  WHERE v.busi_type REGEXP '^1|^2|^3' AND v.repay_confirm_time >= 'yearStartTime' AND v.repay_confirm_time<='endDate' GROUP BY classify "
		//				+ " UNION ALL  SELECT '合计' classify,IFNULL(SUM(v.repay_amount),0) rs  FROM view_project_repay_detail v  WHERE v.busi_type REGEXP '^1|^2|^3'   GROUP BY classify  "
		//				+ " UNION ALL  SELECT CASE f.scale  WHEN 'BIG' THEN '大型企业' WHEN 'MEDIUM' THEN '中型企业' WHEN 'SMALL' THEN '小型企业' WHEN 'TINY' THEN '微型企业' ELSE '其他企业'   END classify,  IFNULL(SUM(v.repay_amount),0) rs  FROM view_project_repay_detail v JOIN f_project_customer_base_info f ON f.customer_id=v.customer_id   WHERE v.busi_type REGEXP '^1|^2|^3'  AND  v.repay_confirm_time >= 'yearStartTime' AND v.repay_confirm_time<='endDate' GROUP BY classify  "
		//				+ " UNION ALL  SELECT CASE  WHEN f.industry_code LIKE 'A%' THEN '农、林、牧、渔业' WHEN f.industry_code LIKE 'C%' THEN '制造业' WHEN f.industry_code LIKE 'D%' THEN '电力、热力、燃气及水生产和供应业' WHEN f.industry_code LIKE 'E%' THEN '建筑业' WHEN f.industry_code LIKE 'K%' THEN '房地产业' WHEN f.industry_code LIKE 'G%' THEN '交通运输、仓储和邮政业' WHEN f.industry_code LIKE 'L%' THEN '租赁和商务服务业' ELSE '其他行业'   END classify,IFNULL(SUM(v.repay_amount),0) rs FROM view_project_repay_detail v JOIN f_project_customer_base_info f ON f.customer_id=v.customer_id WHERE v.busi_type REGEXP '^1|^2|^3'  AND  v.repay_confirm_time >= 'yearStartTime' AND v.repay_confirm_time<='endDate'  GROUP BY classify  "
		//				+ " UNION ALL  SELECT '其中：融资平台' classify, IFNULL(SUM(v.repay_amount),0) rs   FROM view_project_repay_detail v JOIN f_project_customer_base_info f ON f.customer_id=v.customer_id  WHERE v.busi_type REGEXP '^1|^2|^3'  AND  v.repay_confirm_time >= 'yearStartTime' AND v.repay_confirm_time<='endDate' AND f.is_local_gov_platform = 'IS' AND f.enterprise_type='STATE_OWNED'  GROUP BY classify   "
		//				+ " UNION ALL  SELECT CASE WHEN  f.county_name REGEXP '^九龙坡|^渝|^巴南|^大渡口|^沙坪|^南岸|^江北' THEN '主城' ELSE   CONCAT('1-',county_name)  END  classify,   IFNULL(SUM(v.repay_amount),0) rs  FROM view_project_repay_detail v JOIN f_project_customer_base_info f ON f.customer_id=v.customer_id  WHERE v.busi_type REGEXP '^1|^2|^3'  AND  v.repay_confirm_time >= 'yearStartTime' AND v.repay_confirm_time<='endDate' AND f.province_name='重庆市'  GROUP BY classify  "
		//				+ " UNION ALL  SELECT CASE WHEN  f.country_name = '中国'  AND f.province_name !='重庆市' AND f.province_name IS NOT NULL  THEN  CONCAT('2-',f.province_name)  ELSE   '其他地区'  END  classify,IFNULL(SUM(v.repay_amount),0) rs   FROM view_project_repay_detail v JOIN f_project_customer_base_info f ON f.customer_id=v.customer_id  WHERE  v.busi_type REGEXP '^1|^2|^3'  AND v.repay_confirm_time >= 'yearStartTime' AND v.repay_confirm_time<='endDate' GROUP BY classify";
		//		//查询数据 
		//		logger.info("担保业务客户分类汇总表- 本年累计代偿金额");
		//		result = getResult(queryOrder, sql);
		//		if (result == null)
		//			return;
		//		for (DataListItem itm : result) {
		//			String classify = String.valueOf(itm.getMap().get("classify"));
		//			String rs = String
		//				.valueOf(Double.parseDouble(String.valueOf(itm.getMap().get("rs"))) / 100);
		//			if (rsMap.containsKey(classify)) {
		//				GuaranteeCustomerData jb = rsMap.get(classify);
		//				jb.setJbAmount(jb.getJbAmount().add(Money.amout(rs)));
		//			} else {
		//				GuaranteeCustomerData data = new GuaranteeCustomerData();
		//				data.setClassify(classify);
		//				data.setJbAmount(Money.amout(rs));
		//				rsMap.put(classify, data);
		//			}
		//		}
		//加上诉保的解保额
		
		//		sql = "SELECT CASE f.enterprise_type WHEN 'STATE_OWNED' THEN  '国有企业' WHEN  'PRIVATE' THEN '民营企业' WHEN 'FOREIGN_OWNED' THEN '外资企业' ELSE '其他' END classify,   IFNULL(SUM(v.repay_amount),0) rs  FROM view_project_repay_detail v  JOIN f_project_customer_base_info f ON f.customer_id=v.customer_id  WHERE v.busi_type='211' AND v.repay_confirm_time >= 'yearStartTime' AND v.repay_confirm_time<='endDate' GROUP BY classify "
		//				+ " UNION ALL  SELECT '合计' classify,IFNULL(SUM(v.repay_amount),0) rs  FROM view_project_repay_detail v  WHERE v.busi_type='211'   GROUP BY classify  "
		//				+ " UNION ALL  SELECT CASE f.scale  WHEN 'BIG' THEN '大型企业' WHEN 'MEDIUM' THEN '中型企业' WHEN 'SMALL' THEN '小型企业' WHEN 'TINY' THEN '微型企业' ELSE '其他企业'   END classify,  IFNULL(SUM(v.repay_amount),0) rs  FROM view_project_repay_detail v JOIN f_project_customer_base_info f ON f.customer_id=v.customer_id   WHERE v.busi_type='211'  AND  v.repay_confirm_time >= 'yearStartTime' AND v.repay_confirm_time<='endDate' GROUP BY classify  "
		//				+ " UNION ALL  SELECT CASE  WHEN f.industry_code LIKE 'A%' THEN '农、林、牧、渔业' WHEN f.industry_code LIKE 'C%' THEN '制造业' WHEN f.industry_code LIKE 'D%' THEN '电力、热力、燃气及水生产和供应业' WHEN f.industry_code LIKE 'E%' THEN '建筑业' WHEN f.industry_code LIKE 'K%' THEN '房地产业' WHEN f.industry_code LIKE 'G%' THEN '交通运输、仓储和邮政业' WHEN f.industry_code LIKE 'L%' THEN '租赁和商务服务业' ELSE '其他行业'   END classify,IFNULL(SUM(v.repay_amount),0) rs FROM view_project_repay_detail v JOIN f_project_customer_base_info f ON f.customer_id=v.customer_id WHERE v.busi_type='211'  AND  v.repay_confirm_time >= 'yearStartTime' AND v.repay_confirm_time<='endDate'  GROUP BY classify  "
		//				+ " UNION ALL  SELECT '其中：融资平台' classify, IFNULL(SUM(v.repay_amount),0) rs   FROM view_project_repay_detail v JOIN f_project_customer_base_info f ON f.customer_id=v.customer_id  WHERE v.busi_type='211'  AND  v.repay_confirm_time >= 'yearStartTime' AND v.repay_confirm_time<='endDate' AND f.is_local_gov_platform = 'IS' AND f.enterprise_type='STATE_OWNED'  GROUP BY classify   "
		//				+ " UNION ALL  SELECT CASE WHEN  f.county_name REGEXP '^九龙坡|^渝|^巴南|^大渡口|^沙坪|^南岸|^江北' THEN '主城' ELSE   CONCAT('1-',county_name)  END  classify,   IFNULL(SUM(v.repay_amount),0) rs  FROM view_project_repay_detail v JOIN f_project_customer_base_info f ON f.customer_id=v.customer_id  WHERE v.busi_type='211'  AND  v.repay_confirm_time >= 'yearStartTime' AND v.repay_confirm_time<='endDate' AND f.province_name='重庆市'  GROUP BY classify  "
		//				+ " UNION ALL  SELECT CASE WHEN  f.country_name = '中国'  AND f.province_name !='重庆市' AND f.province_name IS NOT NULL  THEN  CONCAT('2-',f.province_name)  ELSE   '其他地区'  END  classify,IFNULL(SUM(v.repay_amount),0) rs   FROM view_project_repay_detail v JOIN f_project_customer_base_info f ON f.customer_id=v.customer_id  WHERE  v.busi_type='211'  AND v.repay_confirm_time >= 'yearStartTime' AND v.repay_confirm_time<='endDate' GROUP BY classify";
		//		//查询数据 
		//		logger.info("担保业务客户分类汇总表- 诉保本年累计解保金额");
		//		result = getResult(queryOrder, sql);
		//		if (result == null)
		//			return;
		//		for (DataListItem itm : result) {
		//			String classify = String.valueOf(itm.getMap().get("classify"));
		//			String rs = String
		//				.valueOf(Double.parseDouble(String.valueOf(itm.getMap().get("rs"))) / 100);
		//			if (rsMap.containsKey(classify)) {
		//				GuaranteeCustomerData jb = rsMap.get(classify);
		//				jb.setJbAmount(jb.getJbAmount().add(Money.amout(rs)));
		//			} else {
		//				GuaranteeCustomerData data = new GuaranteeCustomerData();
		//				data.setClassify(classify);
		//				data.setJbAmount(Money.amout(rs));
		//				rsMap.put(classify, data);
		//			}
		//		}
		
	}
	
	/** 本年累计发生额 */
	private void bnljfse(ReportQueryOrder queryOrder, Map<String, GuaranteeCustomerData> rsMap) {
		
		String sql = "SELECT CASE p.enterprise_type WHEN 'STATE_OWNED' THEN  '国有企业' WHEN  'PRIVATE' THEN '民营企业' WHEN 'FOREIGN_OWNED' THEN '外资企业' ELSE '其他' END classify,  SUM(v.occur_amount) rs  FROM view_project_occer_detail v JOIN project_data_info p ON p.project_code=v.project_code WHERE p.busi_type REGEXP '^1|^2|^3'  AND v.occur_date>='yearStartTime' AND v.occur_date<='endDate'  GROUP BY classify"
						
						+ " UNION ALL  SELECT  '合计' classify,  SUM(v.occur_amount) rs  FROM view_project_occer_detail v JOIN project_data_info p ON p.project_code=v.project_code WHERE p.busi_type REGEXP '^1|^2|^3'  AND v.occur_date>='yearStartTime' AND v.occur_date<='endDate'  GROUP BY classify "
						
						+ " UNION ALL  SELECT CASE p.scale  WHEN 'BIG' THEN '大型企业' WHEN 'MEDIUM' THEN '中型企业' WHEN 'SMALL' THEN '小型企业' WHEN 'TINY' THEN '微型企业' ELSE '其他企业'  END classify, SUM(v.occur_amount) rs  FROM view_project_occer_detail v JOIN project_data_info p ON p.project_code=v.project_code WHERE p.busi_type REGEXP '^1|^2|^3'  AND v.occur_date>='yearStartTime' AND v.occur_date<='endDate'  GROUP BY classify "
						
						+ " UNION ALL  SELECT CASE  WHEN p.industry_code LIKE 'A%' THEN '农、林、牧、渔业' WHEN p.industry_code LIKE 'C%' THEN '制造业' WHEN p.industry_code LIKE 'D%' THEN '电力、热力、燃气及水生产和供应业' WHEN p.industry_code LIKE 'E%' THEN '建筑业' WHEN p.industry_code LIKE 'K%' THEN '房地产业' WHEN p.industry_code LIKE 'G%' THEN '交通运输、仓储和邮政业' WHEN p.industry_code LIKE 'L%' THEN '租赁和商务服务业' ELSE '其他行业' END classify, SUM(v.occur_amount) rs  FROM view_project_occer_detail v JOIN project_data_info p ON p.project_code=v.project_code WHERE p.busi_type REGEXP '^1|^2|^3'  AND v.occur_date>='yearStartTime' AND v.occur_date<='endDate'  GROUP BY classify "
						
						+ " UNION ALL  SELECT '其中：融资平台' classify, SUM(v.occur_amount) rs  FROM view_project_occer_detail v JOIN f_project_customer_base_info p ON v.project_code=p.project_code JOIN project pr ON p.project_code=pr.project_code  WHERE pr.busi_type REGEXP '^1|^2|^3'  AND v.occur_date>='yearStartTime' AND v.occur_date<='endDate' AND p.is_local_gov_platform = 'IS' AND p.enterprise_type='STATE_OWNED'  GROUP BY classify  "
						
						+ " UNION ALL  SELECT CASE WHEN  p.county_name REGEXP '^九龙坡|^渝|^巴南|^大渡口|^沙坪|^南岸|^江北' THEN '主城' ELSE   CONCAT('1-',county_name)  END  classify, SUM(v.occur_amount) rs  FROM view_project_occer_detail v JOIN project_data_info p ON p.project_code=v.project_code WHERE p.busi_type REGEXP '^1|^2|^3'  AND v.occur_date>='yearStartTime' AND v.occur_date<='endDate'  GROUP BY classify "
						
						+ " UNION ALL  SELECT CASE WHEN  c.country_name = '中国'  AND c.province_name !='重庆市' AND c.province_name IS NOT NULL  THEN  CONCAT('2-',c.province_name)  ELSE   '其他地区'  END  classify, SUM(v.occur_amount) rs  FROM view_project_occer_detail v JOIN f_project_customer_base_info c ON v.project_code=c.project_code JOIN project_data_info p ON v.project_code=p.project_code  WHERE p.busi_type REGEXP '^1|^2|^3'  AND v.occur_date>='yearStartTime' AND v.occur_date<='endDate'  GROUP BY classify";
		//查询数据 
		logger.info("担保业务客户分类汇总表- 本年累计发生额 ");
		List<DataListItem> result = getResult(queryOrder, sql);
		if (result == null)
			return;
		for (DataListItem itm : result) {
			String classify = String.valueOf(itm.getMap().get("classify"));
			String rs = String
				.valueOf(Double.parseDouble(String.valueOf(itm.getMap().get("rs"))) / 100);
			if (rsMap.containsKey(classify)) {
				rsMap.get(classify).setFsAmount(Money.amout(rs));
			} else {
				GuaranteeCustomerData data = new GuaranteeCustomerData();
				data.setClassify(classify);
				data.setFsAmount(Money.amout(rs));
				rsMap.put(classify, data);
			}
		}
		//		//加上诉保项目的
		//		sql = "SELECT CASE pd.enterprise_type WHEN 'STATE_OWNED' THEN  '国有企业' WHEN  'PRIVATE' THEN '民营企业' WHEN 'FOREIGN_OWNED' THEN '外资企业' ELSE '其他' END classify,   IFNULL(SUM(pd.contract_amount),0) rs  FROM project_data_info pd JOIN project p ON pd.project_code=p.project_code WHERE p.busi_type='211'  AND  p.court_ruling_time >= 'yearStartTime' AND p.court_ruling_time<='endDate' GROUP BY classify "
		//				+ " UNION ALL  SELECT '合计' classify,IFNULL(SUM(pd.contract_amount),0) rs FROM project_data_info pd JOIN project p ON pd.project_code=p.project_code WHERE p.busi_type='211'  AND  p.court_ruling_time >= 'yearStartTime' AND p.court_ruling_time<='endDate' GROUP BY classify  "
		//				+ " UNION ALL  SELECT CASE pd.scale  WHEN 'BIG' THEN '大型企业' WHEN 'MEDIUM' THEN '中型企业' WHEN 'SMALL' THEN '小型企业' WHEN 'TINY' THEN '微型企业' ELSE '其他企业'   END classify,  IFNULL(SUM(pd.contract_amount),0) rs  FROM project_data_info pd JOIN project p ON pd.project_code=p.project_code WHERE p.busi_type='211'  AND  p.court_ruling_time >= 'yearStartTime' AND p.court_ruling_time<='endDate' GROUP BY classify  "
		//				+ " UNION ALL  SELECT CASE  WHEN pd.industry_code LIKE 'A%' THEN '农、林、牧、渔业' WHEN p.industry_code LIKE 'C%' THEN '制造业' WHEN p.industry_code LIKE 'D%' THEN '电力、热力、燃气及水生产和供应业' WHEN p.industry_code LIKE 'E%' THEN '建筑业' WHEN p.industry_code LIKE 'K%' THEN '房地产业' WHEN p.industry_code LIKE 'G%' THEN '交通运输、仓储和邮政业' WHEN p.industry_code LIKE 'L%' THEN '租赁和商务服务业' ELSE '其他行业'   END classify,IFNULL(SUM(pd.contract_amount),0) rs  FROM project_data_info pd JOIN project p ON pd.project_code=p.project_code WHERE p.busi_type='211' AND  p.court_ruling_time >= 'yearStartTime' AND p.court_ruling_time<='endDate' GROUP BY classify  "
		//				+ " UNION ALL  SELECT '其中：融资平台' classify, IFNULL(SUM(pd.contract_amount),0) rs   FROM project_data_info pd JOIN project p ON pd.project_code=p.project_code  JOIN f_project_customer_base_info c     ON  p.project_code = c.project_code WHERE p.busi_type='211'  AND  p.court_ruling_time >= 'yearStartTime' AND p.court_ruling_time<='endDate' AND c.is_local_gov_platform = 'IS' AND c.enterprise_type='STATE_OWNED'  GROUP BY classify   "
		//				+ " UNION ALL  SELECT CASE WHEN  pd.county_name REGEXP '^九龙坡|^渝|^巴南|^大渡口|^沙坪|^南岸|^江北' THEN '主城' ELSE   CONCAT('1-',pd.county_name)  END  classify,   IFNULL(SUM(pd.contract_amount),0) rs  FROM project_data_info pd JOIN project p ON pd.project_code=p.project_code WHERE p.busi_type='211'  AND  p.court_ruling_time >= 'yearStartTime' AND p.court_ruling_time<='endDate' AND pd.province_name='重庆市' GROUP BY classify  "
		//				+ " UNION ALL  SELECT CASE WHEN  c.country_name = '中国'  AND c.province_name !='重庆市' AND c.province_name IS NOT NULL  THEN  CONCAT('2-',c.province_name)  ELSE   '其他地区'  END  classify,IFNULL(SUM(pd.contract_amount),0) rs   FROM project_data_info pd JOIN project p ON pd.project_code=p.project_code JOIN f_project_customer_base_info c ON  p.project_code = c.project_code  WHERE p.busi_type='211'  AND  p.court_ruling_time >= 'yearStartTime' AND p.court_ruling_time<='endDate' GROUP BY classify";
		//		//查询数据 
		//		logger.info("担保业务客户分类汇总表- 诉保本年累计发生额 ");
		//		result = getResult(queryOrder, sql);
		//		if (result == null)
		//			return;
		//		for (DataListItem itm : result) {
		//			String classify = String.valueOf(itm.getMap().get("classify"));
		//			String rs = String
		//				.valueOf(Double.parseDouble(String.valueOf(itm.getMap().get("rs"))) / 100);
		//			if (rsMap.containsKey(classify)) {
		//				GuaranteeCustomerData fs = rsMap.get(classify);
		//				fs.setFsAmount(fs.getFsAmount().add(Money.amout(rs)));
		//			} else {
		//				GuaranteeCustomerData data = new GuaranteeCustomerData();
		//				data.setClassify(classify);
		//				data.setFsAmount(Money.amout(rs));
		//				rsMap.put(classify, data);
		//			}
		//		}
	}
	
	/** 年初在保余额 */
	private void nczbye(ReportQueryOrder queryOrder, Map<String, GuaranteeCustomerData> rsMap) {
		
		String sql = "SELECT CASE enterprise_type WHEN 'STATE_OWNED' THEN  '国有企业' WHEN  'PRIVATE' THEN '民营企业' WHEN 'FOREIGN_OWNED' THEN '外资企业' ELSE '其他' END classify,   IFNULL(SUM(blance),0) rs  FROM project_data_info_his_data WHERE  busi_type REGEXP '^1|^2|^3' AND project_date='lastYearEndDay'  GROUP BY classify "
						+ " UNION ALL  SELECT '合计' classify,   IFNULL(SUM(blance),0) rs  FROM project_data_info_his_data  WHERE  busi_type REGEXP '^1|^2|^3' AND project_date='lastYearEndDay' "
						+ " UNION ALL  SELECT CASE scale  WHEN 'BIG' THEN '大型企业' WHEN 'MEDIUM' THEN '中型企业' WHEN 'SMALL' THEN '小型企业' WHEN 'TINY' THEN '微型企业' ELSE '其他企业'   END classify,  IFNULL(SUM(blance),0) rs  FROM project_data_info_his_data p WHERE  p.busi_type REGEXP '^1|^2|^3' AND project_date='lastYearEndDay' GROUP BY classify "
						+ " UNION ALL  SELECT CASE  WHEN industry_code LIKE 'A%' THEN '农、林、牧、渔业' WHEN industry_code LIKE 'C%' THEN '制造业' WHEN industry_code LIKE 'D%' THEN '电力、热力、燃气及水生产和供应业' WHEN industry_code LIKE 'E%' THEN '建筑业' WHEN industry_code LIKE 'K%' THEN '房地产业' WHEN industry_code LIKE 'G%' THEN '交通运输、仓储和邮政业' WHEN industry_code LIKE 'L%' THEN '租赁和商务服务业' ELSE '其他行业'   END classify,IFNULL(SUM(blance),0) rs  FROM project_data_info_his_data p WHERE  p.busi_type REGEXP '^1|^2|^3' AND project_date='lastYearEndDay' GROUP BY classify "
						+ " UNION ALL  SELECT '其中：融资平台' classify, IFNULL(SUM(p.blance),0) rs   FROM project_data_info_his_data p   JOIN f_project_customer_base_info c     ON  p.project_code = c.project_code WHERE  p.busi_type REGEXP '^1|^2|^3' AND  c.is_local_gov_platform = 'IS' AND p.enterprise_type='STATE_OWNED' AND project_date='lastYearEndDay'  "
						+ " UNION ALL  SELECT CASE WHEN  county_name REGEXP '^九龙坡|^渝|^巴南|^大渡口|^沙坪|^南岸|^江北' THEN '主城' ELSE   CONCAT('1-',county_name)  END  classify,   IFNULL(SUM(blance),0) rs  FROM project_data_info_his_data p WHERE p.busi_type REGEXP '^1|^2|^3'  AND  province_name='重庆市' AND project_date='lastYearEndDay' GROUP BY classify  "
						+ " UNION ALL  SELECT CASE WHEN  f.country_name = '中国'  AND f.province_name !='重庆市' AND f.province_name IS NOT NULL  THEN  CONCAT('2-',f.province_name)  ELSE   '其他地区'  END  classify,IFNULL(SUM(p.blance),0) rs   FROM project_data_info_his_data p JOIN f_project_customer_base_info f ON p.project_code = f.project_code  WHERE p.busi_type REGEXP '^1|^2|^3'  AND  project_date='lastYearEndDay' GROUP BY classify";
		//查询数据 
		logger.info("担保业务客户分类汇总表- 年初在保余额 ");
		List<DataListItem> result = getResult(queryOrder, sql);
		if (result == null)
			return;
		for (DataListItem itm : result) {
			String classify = String.valueOf(itm.getMap().get("classify"));
			String rs = String
				.valueOf(Double.parseDouble(String.valueOf(itm.getMap().get("rs"))) / 100);
			if (rsMap.containsKey(classify)) {
				rsMap.get(classify).setNczbAmount(Money.amout(rs));
			} else {
				GuaranteeCustomerData data = new GuaranteeCustomerData();
				data.setClassify(classify);
				data.setNczbAmount(Money.amout(rs));
				rsMap.put(classify, data);
			}
		}
	}
	
	/** 在保余额 */
	private void blance(ReportQueryOrder queryOrder, Map<String, GuaranteeCustomerData> rsMap) {
		
		String sql = "SELECT CASE enterprise_type WHEN 'STATE_OWNED' THEN  '国有企业' WHEN  'PRIVATE' THEN '民营企业' WHEN 'FOREIGN_OWNED' THEN '外资企业' ELSE '其他' END classify,  IFNULL(SUM(blance),0) rs1  FROM tableName p WHERE  p.busi_type REGEXP '^1|^2|^3' projectDate  GROUP BY classify "
						+ " UNION ALL  SELECT '合计' classify, IFNULL(SUM(blance),0) rs1  FROM tableName p WHERE  p.busi_type REGEXP '^1|^2|^3'  projectDate"
						+ " UNION ALL  SELECT CASE scale  WHEN 'BIG' THEN '大型企业' WHEN 'MEDIUM' THEN '中型企业' WHEN 'SMALL' THEN '小型企业' WHEN 'TINY' THEN '微型企业' ELSE '其他企业'   END classify, IFNULL(SUM(blance),0) rs1  FROM tableName p WHERE  p.busi_type REGEXP '^1|^2|^3'  projectDate GROUP BY classify "
						+ " UNION ALL  SELECT CASE  WHEN industry_code LIKE 'A%' THEN '农、林、牧、渔业' WHEN industry_code LIKE 'C%' THEN '制造业' WHEN industry_code LIKE 'D%' THEN '电力、热力、燃气及水生产和供应业' WHEN industry_code LIKE 'E%' THEN '建筑业' WHEN industry_code LIKE 'K%' THEN '房地产业' WHEN industry_code LIKE 'G%' THEN '交通运输、仓储和邮政业' WHEN industry_code LIKE 'L%' THEN '租赁和商务服务业' ELSE '其他行业'   END classify, IFNULL(SUM(DISTINCT blance),0) rs1  FROM tableName p WHERE  p.busi_type REGEXP '^1|^2|^3'  projectDate GROUP BY classify "
						+ " UNION ALL  SELECT '其中：融资平台' classify,IFNULL(SUM(p.blance),0) rs1   FROM tableName p   JOIN f_project_customer_base_info c     ON  p.project_code = c.project_code WHERE  p.busi_type REGEXP '^1|^2|^3'  AND p.enterprise_type='STATE_OWNED' AND c.is_local_gov_platform = 'IS' AND p.enterprise_type='STATE_OWNED' projectDate  "
						+ " UNION ALL  SELECT CASE WHEN  county_name REGEXP '^九龙坡|^渝|^巴南|^大渡口|^沙坪|^南岸|^江北' THEN '主城' ELSE   CONCAT('1-',county_name)  END  classify,IFNULL(SUM(blance),0) rs1  FROM tableName p WHERE  p.busi_type REGEXP '^1|^2|^3'   AND province_name='重庆市' projectDate GROUP BY classify  "
						+ " UNION ALL  SELECT CASE WHEN  f.country_name = '中国'  AND f.province_name !='重庆市' AND f.province_name IS NOT NULL  THEN  CONCAT('2-',f.province_name)  ELSE   '其他地区'  END  classify, IFNULL(SUM(p.blance),0) rs1   FROM tableName p JOIN f_project_customer_base_info f ON p.project_code = f.project_code  WHERE p.busi_type REGEXP '^1|^2|^3'  projectDate GROUP BY classify";
		logger.info("担保业务客户分类汇总表- 合同金额，在保余额 ");
		//查询数据
		List<DataListItem> result = getResult(queryOrder, sql);
		if (result == null)
			return;
		for (DataListItem itm : result) {
			String classify = String.valueOf(itm.getMap().get("classify"));
			String zbye = String
				.valueOf(Double.parseDouble(String.valueOf(itm.getMap().get("rs1"))) / 100);
			if (rsMap.containsKey(classify)) {
				rsMap.get(classify).setZbAmount(Money.amout(zbye));
			} else {
				GuaranteeCustomerData data = new GuaranteeCustomerData();
				data.setClassify(classify);
				data.setZbAmount(Money.amout(zbye));
				rsMap.put(classify, data);
			}
		}
	}
	
	/** 合同金额 */
	private void contractAmount(ReportQueryOrder queryOrder,
								Map<String, GuaranteeCustomerData> rsMap) {
		
		String sql = "SELECT CASE enterprise_type WHEN 'STATE_OWNED' THEN  '国有企业' WHEN  'PRIVATE' THEN '民营企业' WHEN 'FOREIGN_OWNED' THEN '外资企业' ELSE '其他' END classify,   IFNULL(SUM(contract_amount),0) rs , IFNULL(SUM(blance),0) rs1  FROM project_data_info p WHERE  p.busi_type REGEXP '^1|^2|^3' AND p.contract_time>'lastYearEndDay' AND p.contract_time<='endDate'  GROUP BY classify "
						+ " UNION ALL  SELECT '合计' classify,   IFNULL(SUM(contract_amount),0) rs , IFNULL(SUM(blance),0) rs1  FROM project_data_info p WHERE  p.busi_type REGEXP '^1|^2|^3'   AND p.contract_time>'lastYearEndDay' AND p.contract_time<='endDate' "
						+ " UNION ALL  SELECT CASE scale  WHEN 'BIG' THEN '大型企业' WHEN 'MEDIUM' THEN '中型企业' WHEN 'SMALL' THEN '小型企业' WHEN 'TINY' THEN '微型企业' ELSE '其他企业'   END classify,  IFNULL(SUM(contract_amount),0) rs , IFNULL(SUM(blance),0) rs1  FROM project_data_info p WHERE  p.busi_type REGEXP '^1|^2|^3'   AND p.contract_time>'lastYearEndDay' AND p.contract_time<='endDate'  GROUP BY classify "
						+ " UNION ALL  SELECT CASE  WHEN industry_code LIKE 'A%' THEN '农、林、牧、渔业' WHEN industry_code LIKE 'C%' THEN '制造业' WHEN industry_code LIKE 'D%' THEN '电力、热力、燃气及水生产和供应业' WHEN industry_code LIKE 'E%' THEN '建筑业' WHEN industry_code LIKE 'K%' THEN '房地产业' WHEN industry_code LIKE 'G%' THEN '交通运输、仓储和邮政业' WHEN industry_code LIKE 'L%' THEN '租赁和商务服务业' ELSE '其他行业'   END classify, IFNULL(SUM(DISTINCT contract_amount),0) rs , IFNULL(SUM(DISTINCT blance),0) rs1  FROM project_data_info p WHERE  p.busi_type REGEXP '^1|^2|^3'   AND p.contract_time>'lastYearEndDay' AND p.contract_time<='endDate'  GROUP BY classify "
						+ " UNION ALL  SELECT '其中：融资平台' classify,IFNULL(SUM(p.contract_amount),0) rs, IFNULL(SUM(p.blance),0) rs1   FROM project_data_info p   JOIN f_project_customer_base_info c     ON  p.project_code = c.project_code WHERE  p.busi_type REGEXP '^1|^2|^3'  AND p.enterprise_type='STATE_OWNED' AND c.is_local_gov_platform = 'IS' AND p.enterprise_type='STATE_OWNED'  AND p.contract_time>'lastYearEndDay' AND p.contract_time<='endDate'   "
						+ " UNION ALL  SELECT CASE WHEN  county_name REGEXP '^九龙坡|^渝|^巴南|^大渡口|^沙坪|^南岸|^江北' THEN '主城' ELSE   CONCAT('1-',county_name)  END  classify, IFNULL(SUM(contract_amount),0) rs , IFNULL(SUM(blance),0) rs1  FROM project_data_info p WHERE  p.busi_type REGEXP '^1|^2|^3'   AND province_name='重庆市'  AND p.contract_time>'lastYearEndDay' AND p.contract_time<='endDate'  GROUP BY classify  "
						+ " UNION ALL  SELECT CASE WHEN  f.country_name = '中国'  AND f.province_name !='重庆市' AND f.province_name IS NOT NULL  THEN  CONCAT('2-',f.province_name)  ELSE   '其他地区'  END  classify,IFNULL(SUM(p.contract_amount),0) rs, IFNULL(SUM(p.blance),0) rs1   FROM project_data_info p JOIN f_project_customer_base_info f ON p.project_code = f.project_code  WHERE p.busi_type REGEXP '^1|^2|^3'   AND p.contract_time>'lastYearEndDay' AND p.contract_time<='endDate'  GROUP BY classify";
		logger.info("担保业务客户分类汇总表- 合同金额，在保余额 ");
		//查询数据
		List<DataListItem> result = getResult(queryOrder, sql);
		if (result == null)
			return;
		for (DataListItem itm : result) {
			String classify = String.valueOf(itm.getMap().get("classify"));
			String htje = String
				.valueOf(Double.parseDouble(String.valueOf(itm.getMap().get("rs"))) / 100);
			if (rsMap.containsKey(classify)) {
				rsMap.get(classify).setHtAmount(Money.amout(htje));
			} else {
				GuaranteeCustomerData data = new GuaranteeCustomerData();
				data.setClassify(classify);
				data.setHtAmount(Money.amout(htje));
				rsMap.put(classify, data);
			}
		}
	}
	
	/** 在保客户，在保项目 */
	private void inCustomerAndProject(ReportQueryOrder queryOrder,
										Map<String, GuaranteeCustomerData> rsMap) {
		String sql = "SELECT CASE enterprise_type WHEN 'STATE_OWNED' THEN  '国有企业' WHEN  'PRIVATE' THEN '民营企业' WHEN 'FOREIGN_OWNED' THEN '外资企业' ELSE '其他' END classify,   COUNT(DISTINCT project_code) rs , COUNT(DISTINCT customer_id) rs1  FROM tableName  WHERE busi_type REGEXP '^1|^2|^3' AND custom_name1='在保' projectDate  GROUP BY classify "
						+ " UNION ALL  SELECT '合计' classify,   COUNT(DISTINCT project_code) rs , COUNT(DISTINCT customer_id) rs1  FROM tableName  WHERE busi_type REGEXP '^1|^2|^3' AND custom_name1='在保'  projectDate"
						+ " UNION ALL  SELECT CASE scale  WHEN 'BIG' THEN '大型企业' WHEN 'MEDIUM' THEN '中型企业' WHEN 'SMALL' THEN '小型企业' WHEN 'TINY' THEN '微型企业' ELSE '其他企业'   END classify,  COUNT(DISTINCT project_code) rs , COUNT(DISTINCT customer_id) rs1  FROM tableName WHERE busi_type REGEXP '^1|^2|^3' AND custom_name1='在保' projectDate GROUP BY classify "
						+ " UNION ALL  SELECT CASE  WHEN industry_code LIKE 'A%' THEN '农、林、牧、渔业' WHEN industry_code LIKE 'C%' THEN '制造业' WHEN industry_code LIKE 'D%' THEN '电力、热力、燃气及水生产和供应业' WHEN industry_code LIKE 'E%' THEN '建筑业' WHEN industry_code LIKE 'K%' THEN '房地产业' WHEN industry_code LIKE 'G%' THEN '交通运输、仓储和邮政业' WHEN industry_code LIKE 'L%' THEN '租赁和商务服务业' ELSE '其他行业'   END classify,   COUNT(DISTINCT project_code) rs , COUNT(DISTINCT customer_id) rs1  FROM tableName p WHERE p.busi_type REGEXP '^1|^2|^3' AND custom_name1='在保' projectDate GROUP BY classify "
						+ " UNION ALL  SELECT '其中：融资平台' classify,   COUNT(DISTINCT p.project_code) rs, COUNT(DISTINCT p.customer_id) rs1   FROM tableName p   JOIN f_project_customer_base_info c     ON p.project_code = c.project_code WHERE p.busi_type REGEXP '^1|^2|^3' AND p.custom_name1='在保' AND  c.is_local_gov_platform = 'IS' AND p.enterprise_type='STATE_OWNED' projectDate  "
						+ " UNION ALL  SELECT CASE WHEN  county_name REGEXP '^九龙坡|^渝|^巴南|^大渡口|^沙坪|^南岸|^江北' THEN '主城' ELSE   CONCAT('1-',county_name)  END  classify,   COUNT(DISTINCT project_code) rs , COUNT(DISTINCT customer_id) rs1  FROM tableName p WHERE p.busi_type REGEXP '^1|^2|^3' AND custom_name1='在保' AND province_name='重庆市' projectDate GROUP BY classify  "
						+ " UNION ALL  SELECT CASE WHEN  f.country_name = '中国'  AND f.province_name !='重庆市' AND f.province_name IS NOT NULL  THEN  CONCAT('2-',f.province_name)  ELSE   '其他地区'  END  classify,   COUNT(DISTINCT p.project_code) rs, COUNT(DISTINCT p.customer_id) rs1   FROM tableName p JOIN f_project_customer_base_info f ON p.project_code = f.project_code  WHERE p.busi_type REGEXP '^1|^2|^3' AND custom_name1='在保' projectDate GROUP BY classify";
		logger.info("担保业务客户分类汇总表- 在保客户，在保项目");
		//查询数据
		List<DataListItem> result = getResult(queryOrder, sql);
		if (result == null)
			return;
		for (DataListItem itm : result) {
			String classify = String.valueOf(itm.getMap().get("classify"));
			String zbxm = String.valueOf(itm.getMap().get("rs"));
			String zbhs = String.valueOf(itm.getMap().get("rs1"));
			if (rsMap.containsKey(classify)) {
				rsMap.get(classify).setZbhs(zbhs);
				rsMap.get(classify).setZbxm(zbxm);
			} else {
				GuaranteeCustomerData data = new GuaranteeCustomerData();
				data.setClassify(classify);
				data.setZbhs(zbhs);
				data.setZbxm(zbxm);
				rsMap.put(classify, data);
			}
		}
	}
	
	/** 新增项目数量 */
	private void newProject(ReportQueryOrder queryOrder, Map<String, GuaranteeCustomerData> rsMap) {
		
		String sql = "SELECT CASE enterprise_type WHEN 'STATE_OWNED' THEN  '国有企业' WHEN  'PRIVATE' THEN '民营企业' WHEN 'FOREIGN_OWNED' THEN '外资企业' ELSE '其他' END classify,   COUNT(DISTINCT project_code) rs  FROM tableName WHERE busi_type REGEXP '^1|^2|^3' AND apply_date >='startDate' AND  apply_date<= 'endDate' projectDate  GROUP BY classify "
						+ " UNION ALL  SELECT '合计' classify,   COUNT(DISTINCT project_code) rs  FROM tableName WHERE busi_type REGEXP '^1|^2|^3' AND apply_date >='startDate' AND  apply_date<= 'endDate' projectDate "
						+ " UNION ALL  SELECT CASE scale  WHEN 'BIG' THEN '大型企业' WHEN 'MEDIUM' THEN '中型企业' WHEN 'SMALL' THEN '小型企业' WHEN 'TINY' THEN '微型企业' ELSE '其他企业'   END classify,  COUNT(DISTINCT project_code) rs  FROM tableName WHERE  busi_type REGEXP '^1|^2|^3' AND apply_date >='startDate' AND  apply_date<= 'endDate' projectDate GROUP BY classify "
						+ " UNION ALL  SELECT CASE  WHEN industry_code LIKE 'A%' THEN '农、林、牧、渔业' WHEN industry_code LIKE 'C%' THEN '制造业' WHEN industry_code LIKE 'D%' THEN '电力、热力、燃气及水生产和供应业' WHEN industry_code LIKE 'E%' THEN '建筑业' WHEN industry_code LIKE 'K%' THEN '房地产业' WHEN industry_code LIKE 'G%' THEN '交通运输、仓储和邮政业' WHEN industry_code LIKE 'L%' THEN '租赁和商务服务业' ELSE '其他行业'   END classify,   COUNT(DISTINCT project_code) rs  FROM tableName WHERE  busi_type REGEXP '^1|^2|^3' AND apply_date >='startDate' AND  apply_date<= 'endDate' projectDate GROUP BY classify "
						+ " UNION ALL  SELECT '其中：融资平台' classify,   COUNT(DISTINCT p.project_code) rs  FROM tableName p   JOIN f_project_customer_base_info c     ON p.project_code = c.project_code WHERE p.busi_type REGEXP '^1|^2|^3' AND p.apply_date >='startDate' AND  p.apply_date<= 'endDate' AND  c.is_local_gov_platform = 'IS' AND p.enterprise_type='STATE_OWNED' projectDate  "
						+ " UNION ALL  SELECT CASE WHEN  county_name REGEXP '^九龙坡|^渝|^巴南|^大渡口|^沙坪|^南岸|^江北' THEN '主城' ELSE   CONCAT('1-',county_name)  END  classify,   COUNT(DISTINCT project_code) rs  FROM tableName WHERE  busi_type REGEXP '^1|^2|^3' AND apply_date >='startDate' AND  apply_date<= 'endDate' AND province_name='重庆市' projectDate GROUP BY classify  "
						+ " UNION ALL  SELECT CASE WHEN  f.country_name = '中国'  AND f.province_name !='重庆市' AND f.province_name IS NOT NULL  THEN  CONCAT('2-',f.province_name)  ELSE   '其他地区'  END  classify,   COUNT(DISTINCT p.project_code) rs  FROM tableName p JOIN f_project_customer_base_info f ON p.project_code = f.project_code  WHERE p.busi_type REGEXP '^1|^2|^3' AND p.apply_date >='startDate' AND  p.apply_date<= 'endDate' projectDate GROUP BY classify";
		logger.info("担保业务客户分类汇总表- 新增项目数量");
		//查询数据
		List<DataListItem> result = getResult(queryOrder, sql);
		if (result == null)
			return;
		for (DataListItem itm : result) {
			String classify = String.valueOf(itm.getMap().get("classify"));
			String rs = String.valueOf(itm.getMap().get("rs"));
			if (rsMap.containsKey(classify)) {
				rsMap.get(classify).setXzxm(rs);
			} else {
				GuaranteeCustomerData data = new GuaranteeCustomerData();
				data.setClassify(classify);
				data.setXzxm(rs);
				rsMap.put(classify, data);
			}
		}
		
	}
	
	/** 新增客户数量 */
	private void newCustomer(ReportQueryOrder queryOrder, Map<String, GuaranteeCustomerData> rsMap) {
		String sql = "SELECT CASE enterprise_type WHEN 'STATE_OWNED' THEN  '国有企业' WHEN  'PRIVATE' THEN '民营企业' WHEN 'FOREIGN_OWNED' THEN '外资企业' ELSE '其他' END classify,   COUNT(DISTINCT customer_id) rs  FROM tableName WHERE busi_type REGEXP '^1|^2|^3' AND customer_add_time >='startDate' AND  customer_add_time<= 'endDate' projectDate  GROUP BY classify "
						+ " UNION ALL  SELECT '合计' classify,COUNT(DISTINCT customer_id) rs  FROM tableName WHERE busi_type REGEXP '^1|^2|^3' AND customer_add_time >='startDate' AND  customer_add_time<= 'endDate' projectDate "
						+ " UNION ALL  SELECT CASE scale  WHEN 'BIG' THEN '大型企业' WHEN 'MEDIUM' THEN '中型企业' WHEN 'SMALL' THEN '小型企业' WHEN 'TINY' THEN '微型企业' ELSE '其他企业'   END classify,  COUNT(DISTINCT customer_id) rs  FROM tableName WHERE busi_type REGEXP '^1|^2|^3' AND customer_add_time >='startDate' AND  customer_add_time<= 'endDate' projectDate GROUP BY classify "
						+ " UNION ALL  SELECT CASE  WHEN industry_code LIKE 'A%' THEN '农、林、牧、渔业' WHEN industry_code LIKE 'C%' THEN '制造业' WHEN industry_code LIKE 'D%' THEN '电力、热力、燃气及水生产和供应业' WHEN industry_code LIKE 'E%' THEN '建筑业' WHEN industry_code LIKE 'K%' THEN '房地产业' WHEN industry_code LIKE 'G%' THEN '交通运输、仓储和邮政业' WHEN industry_code LIKE 'L%' THEN '租赁和商务服务业' ELSE '其他行业'   END classify,   COUNT(DISTINCT customer_id) rs  FROM tableName WHERE busi_type REGEXP '^1|^2|^3' AND customer_add_time >='startDate' AND  customer_add_time<= 'endDate' projectDate GROUP BY classify "
						+ " UNION ALL  SELECT '其中：融资平台' classify, COUNT(DISTINCT p.customer_id) rs  FROM tableName p   JOIN f_project_customer_base_info c     ON p.project_code = c.project_code WHERE p.busi_type REGEXP '^1|^2|^3' AND p.customer_add_time >='startDate' AND  p.customer_add_time<= 'endDate' AND  c.is_local_gov_platform = 'IS' AND p.enterprise_type='STATE_OWNED' projectDate  "
						+ " UNION ALL  SELECT CASE WHEN  county_name REGEXP '^九龙坡|^渝|^巴南|^大渡口|^沙坪|^南岸|^江北' THEN '主城' ELSE   CONCAT('1-',county_name)  END  classify,   COUNT(DISTINCT customer_id) rs  FROM tableName WHERE busi_type REGEXP '^1|^2|^3' AND customer_add_time >='startDate' AND  customer_add_time<= 'endDate' AND province_name='重庆市' projectDate GROUP BY classify  "
						+ " UNION ALL  SELECT CASE WHEN  f.country_name = '中国'  AND f.province_name !='重庆市' AND f.province_name IS NOT NULL  THEN  CONCAT('2-',f.province_name)  ELSE   '其他地区'  END  classify,   COUNT(DISTINCT p.customer_id) rs  FROM tableName p JOIN f_project_customer_base_info f ON p.project_code = f.project_code  WHERE p.busi_type REGEXP '^1|^2|^3' AND p.customer_add_time >='startDate' AND  p.customer_add_time<= 'endDate' projectDate GROUP BY classify";
		logger.info("担保业务客户分类汇总表- 新增客户数量");
		//查询数据
		List<DataListItem> result = getResult(queryOrder, sql);
		if (result == null)
			return;
		for (DataListItem itm : result) {
			String classify = String.valueOf(itm.getMap().get("classify"));
			String rs = String.valueOf(itm.getMap().get("rs"));
			if (rsMap.containsKey(classify)) {
				rsMap.get(classify).setXzhs(rs);
			} else {
				GuaranteeCustomerData data = new GuaranteeCustomerData();
				data.setClassify(classify);
				data.setXzhs(rs);
				rsMap.put(classify, data);
			}
		}
	}
	
	/** 查询数据 */
	private List<DataListItem> getResult(ReportQueryOrder queryOrder, String sql) {
		String tableName = "project_data_info_his_data";
		String projectDate = "";
		if (queryOrder.isThisMonth()) {
			tableName = "project_data_info";
		} else {
			projectDate = "AND project_date='" + queryOrder.getReportMonthEndDay() + "'";
		}
		sql = sql.replaceAll("startDate", queryOrder.getThisStartTime())
			.replaceAll("endDate", queryOrder.getThisEndTime()).replaceAll("tableName", tableName)
			.replaceAll("projectDate", projectDate)
			.replaceAll("lastYearEndDay", queryOrder.getLastYearEndDay())
			.replaceAll("yearStartTime", queryOrder.getYearStartTime());
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(sql);
		logger.info("担保客户分类汇总表查询：sql={}", sql);
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		return dataResult;
	}
	
	public GuaranteeCustomerData init(String types) {
		GuaranteeCustomerData data = new GuaranteeCustomerData();
		data.setTypes(types);
		return data;
		
	}
	
}
