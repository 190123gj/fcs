package com.born.fcs.rm.biz.service.report.inner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.integration.service.pm.PmReportServiceClient;
import com.born.fcs.rm.ws.info.report.ChannelClassfyOrder;
import com.born.fcs.rm.ws.info.report.inner.ExpectReleaseInfo;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Service("projectTypeDataService")
public class ProjectTypeDataService extends BaseAutowiredDomainService implements
																		ReportProcessService {
	
	@Autowired
	private PmReportServiceClient pmReportServiceClient;
	
	@Override
	public FcsBaseResult save(ReportOrder order) {
		return null;
	}
	
	@Override
	public Object findByAccountPeriod(ReportQueryOrder queryOrder) {
		
		ChannelClassfyOrder order = new ChannelClassfyOrder();
		BeanCopier.staticCopy(queryOrder, order);
		
		return query(order);
		
	}
	
	private Object query(ChannelClassfyOrder order) {
		
		int reportYear = order.getReportYear();
		int reportMonth = order.getReportMonth();
		String tableName = " project_data_info_his_data ";
		String projectDateSql = "";
		String thisEndDay = DateUtil.dtSimpleFormat(DateUtil.getEndTimeByYearAndMonth(reportYear,
			reportMonth));
		if (order.isNowMoth()) {
			tableName = " project_data_info ";
		} else {
			projectDateSql = "  AND project_date= '" + thisEndDay + "'";
		}
		
		//本期时间段开始
		String startTime = DateUtil.simpleFormat(DateUtil.getStartTimeByYearAndMonth(reportYear,
			reportMonth));
		//本期时间段结束
		String endTime = DateUtil.simpleFormat(DateUtil.getEndTimeByYearAndMonth(reportYear,
			reportMonth));
		//去年最后一天
		String lastYearEndDay = order.getCurrYearLast(reportYear - 1);
		//当前期年最后一天
		String yearEndDay = order.getCurrYearLast(reportYear);
		//当前期年初第一天
		String yearBeginDay = reportYear + "-01-01";
		
		//按大类统计 - 银行融资担保 
		String sql5 = getCountSql("^11", "银行融资担保");
		//按大类统计 -债券融资担保
		String sql6 = getCountSql("^12", "债券融资担保");
		//按大类统计 -金融产品担保
		String sql7 = getCountSql("^13", "金融产品担保");
		//按大类统计 -非融资担保业务
		String sql8 = getCountSql("^2", "非融资担保业务");
		//按大类统计 -在担保业务
		String sql9 = getCountSql("^3", "再担保业务");
		//按大类统计 -委托贷款
		String sql10 = getCountSql("^4", "委托贷款");
		//按大类统计 -承销
		String sql11 = getCountSql("^5", "承销");
		//按大类统计 -小贷业务
		String sql12 = getCountSql("^6", "小贷业务");
		//按大类统计 -其它业务
		String sql13 = getCountSql("^7", "其它业务");
		//按大类统计 -合计
		String sql14 = getCountSql("^12|^13|^2|^3|^4|^5|^6|^7|^11", "合计");
		
		//按小类统计
		String sql15 = getTypeSql();
		
		//参数替换
		String sql = " (" + sql5 + ") UNION (" + sql15 + ") UNION (" + sql6 + ") UNION (" + sql7
						+ ") UNION (" + sql8 + ") UNION (" + sql9 + ") UNION (" + sql10
						+ ") UNION (" + sql11 + ") UNION (" + sql12 + ") UNION (" + sql13
						+ ") UNION (" + sql14 + ")";
		
		sql = (sql).replaceAll("tableName", tableName).replaceAll("startTime", startTime)
			.replaceAll("endTime", endTime).replaceAll("lastYearEndDay", lastYearEndDay)
			.replaceAll("yearEndDay", yearEndDay).replaceAll("projectDateSql", projectDateSql)
			.replace("yearBeginDay", yearBeginDay).replaceAll("thisEndDay", thisEndDay);
		
		//查询数据
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(sql);
		logger.info("产品分类汇总表 查询sql={}", sql);
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		//暂存结果
		Map<String, ExpectReleaseInfo> rsMap = new HashMap<>();
		for (DataListItem itm : dataResult) {
			ExpectReleaseInfo info = new ExpectReleaseInfo();
			info.setData1(String.valueOf(itm.getMap().get("产品名")));
			info.setData2(String.valueOf(itm.getMap().get("新增户数")));
			info.setData3(String.valueOf(itm.getMap().get("新增笔数")));
			info.setData4(String.valueOf(itm.getMap().get("在保户数")));
			info.setData5(String.valueOf(itm.getMap().get("在保笔数")));
			info.setData6(toMoneyStr(itm.getMap().get("担保合同金额")));
			info.setData7(toMoneyStr(itm.getMap().get("年初在保余额")));
			info.setData8(toMoneyStr(itm.getMap().get("本年累计发生额")));
			info.setData9(toMoneyStr(itm.getMap().get("本年累计解保额")));
			info.setData10(toMoneyStr(itm.getMap().get("期末在保余额")));
			double data11 = Math
				.round(NumberUtil.parseDouble((String) itm.getMap().get("平均费率")) * 100) / 100.00;
			info.setData11(String.valueOf(data11));
			info.setData12(String.valueOf(itm.getMap().get("产品类别")));
			rsMap.put(info.getData1(), info);
		}
		QueryBaseBatchResult<ExpectReleaseInfo> result = new QueryBaseBatchResult<ExpectReleaseInfo>();
		if (!rsMap.isEmpty()) {
			//小分类统计
			String countTitles = "债券融资担保,金融产品担保,非融资担保业务,再担保业务,委托贷款,承销,小贷业务,其它业务";
			//页面展示排序
			String pageShow = "债券融资担保,私募债担保,企业债担保,公司债担保,项目收益债担保,短期融资券担保,中期票据担保,"
								+ "金融产品担保,信托产品担保,资管产品担保,资产证券化担保,融资租赁担保,直融产品担保,"
								+ "非融资担保业务,诉讼保函,履约保函,关税保函,保本基金担保," + "再担保业务,比例分保,溢额再保,"
								+ "委托贷款,委托贷款,承销,承销,小贷业务,小额贷款,票据贴现,其它业务,其他,合计";
			List<ExpectReleaseInfo> list = getYhrzData(order, rsMap.get("银行融资担保"));
			String[] index = pageShow.split(",");
			String fontTitle = "";
			for (String s : index) {
				if (rsMap.containsKey(s)) {
					ExpectReleaseInfo info = rsMap.get(s);
					if (countTitles.indexOf(s) > -1 && StringUtil.notEquals(s, fontTitle)) {
						ExpectReleaseInfo info0 = new ExpectReleaseInfo();
						BeanCopier.staticCopy(info, info0);
						info0.setData13("total");
						fontTitle = s;
						list.add(info0);
					} else {
						list.add(info);
					}
					
				}
			}
			result.setPageList(list);
			result.setSuccess(true);
			result.setMessage("查询成功");
		}
		
		return result;
	}
	
	/**
	 * 银行融资担保数据
	 * @param clun_type 分类字段
	 * @param name_showe 页面展示类型名
	 * 
	 * */
	private List<ExpectReleaseInfo> getYhrzData(ChannelClassfyOrder order, ExpectReleaseInfo info) {
		
		int reportYear = order.getReportYear();
		int reportMonth = order.getReportMonth();
		String tableName = " project_channel_relation_his ";
		String projectDateSql = "";
		//当前期最后一天时间
		String thisEndDay = DateUtil.dtSimpleFormat(DateUtil.getEndTimeByYearAndMonth(reportYear,
			reportMonth));
		if (order.isNowMoth()) {
			tableName = " project_channel_relation ";
		} else {
			projectDateSql = "  AND project_date= '" + thisEndDay + "'";
		}
		
		//本期时间段开始
		String startTime = DateUtil.simpleFormat(DateUtil.getStartTimeByYearAndMonth(reportYear,
			reportMonth));
		//本期时间段结束
		String endTime = DateUtil.simpleFormat(DateUtil.getEndTimeByYearAndMonth(reportYear,
			reportMonth));
		//当前期年初第一天
		String yearBeginDay = reportYear + "-01-01";
		ExpectReleaseInfo hj = info;
		if (info != null) {
			hj.setData13("total");
		}
		
		//银行融资担保 - 流动资金贷款
		ExpectReleaseInfo d1 = getRs("流动资金贷款");
		//银行融资担保 - 固定资产融资
		ExpectReleaseInfo d2 = getRs("固定资产融资");
		//银行融资担保 - 承兑汇票担保
		ExpectReleaseInfo d3 = getRs("承兑汇票担保");
		//银行融资担保 - 信用证担保
		ExpectReleaseInfo d4 = getRs("信用证担保");
		
		//本年累计解保金额---------------------------------------------------------------
		String sql = "SELECT IFNULL(SUM(f.liquidity_loan_amount),0)  'd1' ,IFNULL(SUM(f.fixed_assets_financing_amount),0) 'd2' ,IFNULL(SUM(f.acceptance_bill_amount),0) 'd3' ,IFNULL(SUM(f.credit_letter_amount),0) 'd4'  FROM  f_counter_guarantee_repay f JOIN  f_counter_guarantee_release fg ON fg.form_id=f.form_id JOIN  project p ON fg.project_code=p.project_code JOIN form fm ON f.form_id=fm.form_id WHERE fm.status='APPROVAL' AND  p.busi_type='11' AND  fm.finish_time >= '"
						+ yearBeginDay + "' AND fm.finish_time <= '" + thisEndDay + "'";
		
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(sql);
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (ListUtil.isNotEmpty(dataResult)) {
			//			Object d = 0;
			for (DataListItem itm : dataResult) {
				
				if (itm.getMap().containsKey("d1")) {
					d1.setData9(toMoneyStr(itm.getMap().get("d1")));
					//					d = objAdd(d, itm.getMap().get("d1"));
				}
				if (itm.getMap().containsKey("d2")) {
					d2.setData9(toMoneyStr(itm.getMap().get("d2")));
					//					d = objAdd(d, itm.getMap().get("d2"));
				}
				if (itm.getMap().containsKey("d3")) {
					d3.setData9(toMoneyStr(itm.getMap().get("d3")));
					//					d = objAdd(d, itm.getMap().get("d3"));
				}
				if (itm.getMap().containsKey("d4")) {
					d4.setData9(toMoneyStr(itm.getMap().get("d4")));
					//					d = objAdd(d, itm.getMap().get("d4"));
				}
			}
			//			hj.setData9(toMoneyStr(d));
		}
		//本年累计发生额---------------------------------------------------
		sql = "SELECT IFNULL(SUM(f.liquidity_loan_amount),0)  'd1' ,IFNULL(SUM(f.fixed_assets_financing_amount),0) 'd2' ,IFNULL(SUM(f.acceptance_bill_amount),0) 'd3' ,IFNULL(SUM(f.credit_letter_amount),0) 'd4'  FROM f_loan_use_apply_receipt f JOIN project p ON f.project_code=p.project_code WHERE p.busi_type='11' AND f.raw_add_time >= '"
				+ yearBeginDay + "' AND f.raw_add_time <= '" + thisEndDay + "' ";
		queyOrder.setSql(sql);
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (ListUtil.isNotEmpty(dataResult)) {
			//			Object d = 0;
			for (DataListItem itm : dataResult) {
				if (itm.getMap().containsKey("d1")) {
					d1.setData8(toMoneyStr(itm.getMap().get("d1")));
					//					d = objAdd(d, itm.getMap().get("d1"));
				}
				if (itm.getMap().containsKey("d2")) {
					d2.setData8(toMoneyStr(itm.getMap().get("d2")));
					//					d = objAdd(d, itm.getMap().get("d2"));
				}
				if (itm.getMap().containsKey("d3")) {
					d3.setData8(toMoneyStr(itm.getMap().get("d3")));
					//					d = objAdd(d, itm.getMap().get("d3"));
				}
				if (itm.getMap().containsKey("d4")) {
					d4.setData8(toMoneyStr(itm.getMap().get("d4")));
					//					d = objAdd(d, itm.getMap().get("d4"));
				}
			}
			//			hj.setData8(toMoneyStr(d));
		}
		//期末在保余额 = 累计发生-累计解保---------------------------------------------------
		//到当前累计发生额
		sql = "SELECT IFNULL(SUM(f.liquidity_loan_amount),0)  'd1' ,IFNULL(SUM(f.fixed_assets_financing_amount),0) 'd2' ,IFNULL(SUM(f.acceptance_bill_amount),0) 'd3' ,IFNULL(SUM(f.credit_letter_amount),0) 'd4'  FROM f_loan_use_apply_receipt f JOIN project p ON f.project_code=p.project_code WHERE p.busi_type='11'  AND f.raw_add_time <= '"
				+ thisEndDay + "' ";
		queyOrder.setSql(sql);
		List<DataListItem> fs = pmReportServiceClient.doQuery(queyOrder);
		//到当前累计解保
		sql = "SELECT IFNULL(SUM(f.liquidity_loan_amount),0)  'd1' ,IFNULL(SUM(f.fixed_assets_financing_amount),0) 'd2' ,IFNULL(SUM(f.acceptance_bill_amount),0) 'd3' ,IFNULL(SUM(f.credit_letter_amount),0) 'd4'    FROM  f_counter_guarantee_repay f JOIN  f_counter_guarantee_release fg ON fg.form_id=f.form_id JOIN  project p ON fg.project_code=p.project_code JOIN form fm ON f.form_id=fm.form_id WHERE fm.status='APPROVAL' AND  p.busi_type='11' AND fm.finish_time <= '"
				+ thisEndDay + "'";
		queyOrder.setSql(sql);
		List<DataListItem> jb = pmReportServiceClient.doQuery(queyOrder);
		if (ListUtil.isNotEmpty(fs)) {
			DataListItem fsData = fs.get(0);
			DataListItem jbData = null;
			Object a1 = 0;
			Object a2 = 0;
			Object a3 = 0;
			Object a4 = 0;
			if (ListUtil.isNotEmpty(jb))
				jbData = jb.get(0);
			if (jbData != null) {
				if (fsData.getMap().containsKey("d1")) {
					if (jbData.getMap().containsKey("d1"))
						a1 = objMul(fsData.getMap().get("d1"), jbData.getMap().get("d1"));
					else
						a1 = fsData.getMap().get("d1");
				}
				if (fsData.getMap().containsKey("d2")) {
					if (jbData.getMap().containsKey("d2"))
						a2 = objMul(fsData.getMap().get("d2"), jbData.getMap().get("d2"));
					else
						a2 = fsData.getMap().get("d2");
				}
				if (fsData.getMap().containsKey("d3")) {
					if (jbData.getMap().containsKey("d3"))
						a3 = objMul(fsData.getMap().get("d3"), jbData.getMap().get("d3"));
					else
						a2 = fsData.getMap().get("d3");
				}
				if (fsData.getMap().containsKey("d4")) {
					if (jbData.getMap().containsKey("d4"))
						a4 = objMul(fsData.getMap().get("d4"), jbData.getMap().get("d4"));
					else
						a4 = fsData.getMap().get("d4");
				}
				
			} else {
				
				if (fsData.getMap().containsKey("d1"))
					a1 = fsData.getMap().get("d1");
				if (fsData.getMap().containsKey("d2"))
					a2 = fsData.getMap().get("d2");
				if (fsData.getMap().containsKey("d3"))
					a3 = fsData.getMap().get("d3");
				if (fsData.getMap().containsKey("d4"))
					a4 = fsData.getMap().get("d4");
			}
			d1.setData10(toMoneyStr(a1));
			d2.setData10(toMoneyStr(a2));
			d3.setData10(toMoneyStr(a3));
			d4.setData10(toMoneyStr(a4));
			//			Object d = objAdd(a1, a2);
			//			d = objAdd(d, a3);
			//			d = objAdd(d, a4);
			//			hj.setData10(toMoneyStr(d));
		}
		//年初在保余额-----------------------------------------------------------
		//到当前累计发生额
		sql = "SELECT IFNULL(SUM(f.liquidity_loan_amount),0)  'd1' ,IFNULL(SUM(f.fixed_assets_financing_amount),0) 'd2' ,IFNULL(SUM(f.acceptance_bill_amount),0) 'd3' ,IFNULL(SUM(f.credit_letter_amount),0) 'd4'   FROM f_loan_use_apply_receipt f JOIN project p ON f.project_code=p.project_code WHERE p.busi_type='11'  AND f.raw_add_time < '"
				+ yearBeginDay + "' ";
		queyOrder.setSql(sql);
		fs = pmReportServiceClient.doQuery(queyOrder);
		//到当前累计解保
		sql = "SELECT  IFNULL(SUM(f.liquidity_loan_amount),0)  'd1' ,IFNULL(SUM(f.fixed_assets_financing_amount),0) 'd2' ,IFNULL(SUM(f.acceptance_bill_amount),0) 'd3' ,IFNULL(SUM(f.credit_letter_amount),0) 'd4'   FROM  f_counter_guarantee_repay f JOIN  f_counter_guarantee_release fg ON fg.form_id=f.form_id JOIN  project p ON fg.project_code=p.project_code JOIN form fm ON f.form_id=fm.form_id WHERE fm.status='APPROVAL' AND  p.busi_type='11' AND fm.finish_time < '"
				+ yearBeginDay + "'";
		queyOrder.setSql(sql);
		jb = pmReportServiceClient.doQuery(queyOrder);
		
		if (ListUtil.isNotEmpty(fs)) {
			DataListItem fsData = fs.get(0);
			DataListItem jbData = null;
			Object a1 = 0;
			Object a2 = 0;
			Object a3 = 0;
			Object a4 = 0;
			if (ListUtil.isNotEmpty(jb))
				jbData = jb.get(0);
			if (jbData != null) {
				if (fsData.getMap().containsKey("d1")) {
					if (jbData.getMap().containsKey("d1"))
						a1 = objMul(fsData.getMap().get("d1"), jbData.getMap().get("d1"));
					else
						a1 = fsData.getMap().get("d1");
				}
				if (fsData.getMap().containsKey("d2")) {
					if (jbData.getMap().containsKey("d2"))
						a2 = objMul(fsData.getMap().get("d2"), jbData.getMap().get("d2"));
					else
						a2 = fsData.getMap().get("d2");
				}
				if (fsData.getMap().containsKey("d3")) {
					if (jbData.getMap().containsKey("d3"))
						a3 = objMul(fsData.getMap().get("d3"), jbData.getMap().get("d3"));
					else
						a3 = fsData.getMap().get("d3");
				}
				if (fsData.getMap().containsKey("d4")) {
					if (jbData.getMap().containsKey("d4"))
						a4 = objMul(fsData.getMap().get("d4"), jbData.getMap().get("d4"));
					else
						a4 = fsData.getMap().get("d4");
				}
				
			} else {
				
				if (fsData.getMap().containsKey("d1"))
					a1 = fsData.getMap().get("d1");
				if (fsData.getMap().containsKey("d2"))
					a2 = fsData.getMap().get("d2");
				if (fsData.getMap().containsKey("d3"))
					a3 = fsData.getMap().get("d3");
				if (fsData.getMap().containsKey("d4"))
					a4 = fsData.getMap().get("d4");
				
			}
			d1.setData7(toMoneyStr(a1));
			d2.setData7(toMoneyStr(a2));
			d3.setData7(toMoneyStr(a3));
			d4.setData7(toMoneyStr(a4));
			//			Object d = objAdd(a1, a2);
			//			d = objAdd(d, a3);
			//			d = objAdd(d, a4);
			//			hj.setData7(toMoneyStr(d));
		}
		// 合同金额——— ———————— ———————— —————————— ———————— ——————
		sql = "SELECT  IFNULL(SUM(c.liquidity_loans_amount),0)  'd1' ,IFNULL(SUM(c.financial_amount),0) 'd2' ,IFNULL(SUM(c.acceptance_bill_amount),0) 'd3' ,IFNULL(SUM(c.credit_amount),0) 'd4' FROM project_channel_relation c JOIN project_data_info p ON c.project_code=p.project_code WHERE c.channel_relation='CAPITAL_CHANNEL' AND c.latest='YES' AND  p.busi_type='11' AND p.contract_time <= '"
				+ endTime + "' AND p.contract_time >= '" + yearBeginDay + "'";
		
		queyOrder.setSql(sql);
		jb = pmReportServiceClient.doQuery(queyOrder);
		if (ListUtil.isNotEmpty(jb)) {
			//			Object d = 0;
			for (DataListItem itm : jb) {
				
				if (itm.getMap().containsKey("d1")) {
					d1.setData6(toMoneyStr(itm.getMap().get("d1")));
					//					d = objAdd(d, itm.getMap().get("d1"));
				}
				if (itm.getMap().containsKey("d2")) {
					d2.setData6(toMoneyStr(itm.getMap().get("d2")));
					//					d = objAdd(d, itm.getMap().get("d2"));
				}
				if (itm.getMap().containsKey("d3")) {
					d3.setData6(toMoneyStr(itm.getMap().get("d3")));
					//					d = objAdd(d, itm.getMap().get("d3"));
				}
				if (itm.getMap().containsKey("d4")) {
					d4.setData6(toMoneyStr(itm.getMap().get("d4")));
					//					d = objAdd(d, itm.getMap().get("d4"));
				}
			}
			//			hj.setData6(toMoneyStr(d));
		}
		
		//新增户数----------------------------------------------
		if (order.isNowMoth()) {
			tableName = " project_data_info ";
		} else {
			tableName = " project_data_info_his_data ";
		}
		
		sql = "SELECT SUM(da.num) rs,da.type FROM (SELECT MAX(1/c.num) num,c.type,p.customer_id FROM (SELECT a.project_code,a.fke,a.type,a.type_name,b.num FROM (SELECT v.project_code,v.fke,v.type,v.type_name FROM view_bank_financing_type v JOIN (SELECT project_code,MAX(fke) fke FROM view_bank_financing_type GROUP BY project_code) m ON v.project_code=m.project_code AND v.fke=m.fke) a JOIN (SELECT v.project_code,COUNT(v.project_code) num FROM view_bank_financing_type v JOIN (SELECT project_code,MAX(fke) fke FROM view_bank_financing_type GROUP BY project_code) m ON v.project_code=m.project_code AND v.fke=m.fke GROUP BY m.project_code) b ON a.project_code=b.project_code) c JOIN tableName p ON c.project_code=p.project_code WHERE p.customer_add_time>='startTime' AND p.customer_add_time<='endTime' projectDateSql GROUP BY c.type,p.customer_id) da GROUP BY da.type"
			.replaceAll("tableName", tableName).replaceAll("endTime", endTime)
			.replaceAll("startTime", startTime).replaceAll("projectDateSql", projectDateSql);
		queyOrder.setSql(sql);
		jb = pmReportServiceClient.doQuery(queyOrder);
		if (ListUtil.isNotEmpty(jb)) {
			//			Object d = 0;
			for (DataListItem itm : jb) {
				if ("t1".equals(itm.getMap().get("type")))
					d1.setData2(toRs(itm.getMap().get("rs")));
				if ("t2".equals(itm.getMap().get("type")))
					d2.setData2(toRs(itm.getMap().get("rs")));
				if ("t3".equals(itm.getMap().get("type")))
					d3.setData2(toRs(itm.getMap().get("rs")));
				if ("t4".equals(itm.getMap().get("type")))
					d4.setData2(toRs(itm.getMap().get("rs")));
				//				d = objAdd(d, itm.getMap().get("rs"));
			}
			
			//			hj.setData2(toRs(d));
		}
		//在保户数
		sql = "SELECT SUM(da.num) rs,da.type FROM (SELECT MAX(1/c.num) num,c.type,p.customer_id FROM (SELECT a.project_code,a.fke,a.type,a.type_name,b.num FROM (SELECT v.project_code,v.fke,v.type,v.type_name FROM view_bank_financing_type v JOIN (SELECT project_code,MAX(fke) fke FROM view_bank_financing_type GROUP BY project_code) m ON v.project_code=m.project_code AND v.fke=m.fke) a JOIN (SELECT v.project_code,COUNT(v.project_code) num FROM view_bank_financing_type v JOIN (SELECT project_code,MAX(fke) fke FROM view_bank_financing_type GROUP BY project_code) m ON v.project_code=m.project_code AND v.fke=m.fke GROUP BY m.project_code) b ON a.project_code=b.project_code) c JOIN tableName p ON c.project_code=p.project_code WHERE p.custom_name1='在保' projectDateSql GROUP BY c.type,p.customer_id) da GROUP BY da.type"
			.replaceAll("tableName", tableName).replaceAll("endTime", endTime)
			.replaceAll("startTime", startTime).replaceAll("projectDateSql", projectDateSql);
		queyOrder.setSql(sql);
		jb = pmReportServiceClient.doQuery(queyOrder);
		if (ListUtil.isNotEmpty(jb)) {
			//			Object d = 0;
			for (DataListItem itm : jb) {
				if ("t1".equals(itm.getMap().get("type")))
					d1.setData4(toRs(itm.getMap().get("rs")));
				if ("t2".equals(itm.getMap().get("type")))
					d2.setData4(toRs(itm.getMap().get("rs")));
				if ("t3".equals(itm.getMap().get("type")))
					d3.setData4(toRs(itm.getMap().get("rs")));
				if ("t4".equals(itm.getMap().get("type")))
					d4.setData4(toRs(itm.getMap().get("rs")));
				//				d = objAdd(d, itm.getMap().get("rs"));
			}
			
			//			hj.setData4(toRs(d));
		}
		//新增笔数
		sql = "SELECT SUM(1/c.num) rs, c.type FROM (SELECT a.project_code, a.fke, a.type, a.type_name, b.num FROM (SELECT v.project_code, v.fke, v.type, v.type_name FROM view_bank_financing_type v JOIN (SELECT project_code, MAX(fke) fke FROM view_bank_financing_type GROUP BY project_code) m ON v.project_code=m.project_code AND v.fke=m.fke) a JOIN (SELECT v.project_code, COUNT(v.project_code) num FROM view_bank_financing_type v JOIN (SELECT project_code, MAX(fke) fke FROM view_bank_financing_type GROUP BY project_code) m ON v.project_code=m.project_code AND v.fke=m.fke GROUP BY m.project_code) b ON a.project_code=b.project_code) c JOIN tableName p ON c.project_code=p.project_code WHERE p.apply_date>='startTime' AND p.apply_date<='endTime' projectDateSql GROUP BY c.type"
			.replaceAll("tableName", tableName).replaceAll("endTime", endTime)
			.replaceAll("startTime", startTime).replaceAll("projectDateSql", projectDateSql);
		queyOrder.setSql(sql);
		jb = pmReportServiceClient.doQuery(queyOrder);
		if (ListUtil.isNotEmpty(jb)) {
			//			Object d = 0;
			for (DataListItem itm : jb) {
				if ("t1".equals(itm.getMap().get("type")))
					d1.setData3(toRs(itm.getMap().get("rs")));
				if ("t2".equals(itm.getMap().get("type")))
					d2.setData3(toRs(itm.getMap().get("rs")));
				if ("t3".equals(itm.getMap().get("type")))
					d3.setData3(toRs(itm.getMap().get("rs")));
				if ("t4".equals(itm.getMap().get("type")))
					d4.setData3(toRs(itm.getMap().get("rs")));
				//				d = objAdd(d, itm.getMap().get("rs"));
			}
			
			//			hj.setData3(toRs(d));
		}
		
		//再保笔数
		sql = "SELECT SUM(1/c.num) rs, c.type FROM (SELECT a.project_code, a.fke, a.type, a.type_name, b.num FROM (SELECT v.project_code, v.fke, v.type, v.type_name FROM view_bank_financing_type v JOIN (SELECT project_code, MAX(fke) fke FROM view_bank_financing_type GROUP BY project_code) m ON v.project_code=m.project_code AND v.fke=m.fke) a JOIN (SELECT v.project_code, COUNT(v.project_code) num FROM view_bank_financing_type v JOIN (SELECT project_code, MAX(fke) fke FROM view_bank_financing_type GROUP BY project_code) m ON v.project_code=m.project_code AND v.fke=m.fke GROUP BY m.project_code) b ON a.project_code=b.project_code) c JOIN tableName p ON c.project_code=p.project_code WHERE  p.custom_name1='在保'  projectDateSql GROUP BY c.type"
			.replaceAll("tableName", tableName).replaceAll("endTime", endTime)
			.replaceAll("startTime", startTime).replaceAll("projectDateSql", projectDateSql);
		queyOrder.setSql(sql);
		jb = pmReportServiceClient.doQuery(queyOrder);
		if (ListUtil.isNotEmpty(jb)) {
			//			Object d = 0;
			for (DataListItem itm : jb) {
				if ("t1".equals(itm.getMap().get("type")))
					d1.setData5(toRs(itm.getMap().get("rs")));
				if ("t2".equals(itm.getMap().get("type")))
					d2.setData5(toRs(itm.getMap().get("rs")));
				if ("t3".equals(itm.getMap().get("type")))
					d3.setData5(toRs(itm.getMap().get("rs")));
				if ("t4".equals(itm.getMap().get("type")))
					d4.setData5(toRs(itm.getMap().get("rs")));
				//				d = objAdd(d, itm.getMap().get("rs"));
			}
			
			//			hj.setData5(toRs(d));
		}
		
		//平均费率----------------------------------------------------------
		sql = "SELECT SUM((fs.d1-jb.d1)*fs.guarantee_fee_rate)/SUM(fs.d1-jb.d1) AS d1, SUM((fs.d2-jb.d1)*fs.guarantee_fee_rate)/SUM(fs.d2-jb.d2) AS d2, SUM((fs.d3-jb.d3)*fs.guarantee_fee_rate)/SUM(fs.d3-jb.d3) AS d3, SUM((fs.d4-jb.d4)*fs.guarantee_fee_rate)/SUM(fs.d4-jb.d4) AS d4 FROM (SELECT p.project_code, p.guarantee_fee_rate, IFNULL(SUM(f.liquidity_loan_amount),0) 'd1', IFNULL(SUM(f.fixed_assets_financing_amount),0) 'd2', IFNULL(SUM(f.acceptance_bill_amount),0) 'd3', IFNULL(SUM(f.credit_letter_amount),0) 'd4' FROM f_loan_use_apply_receipt f JOIN project_data_info p ON f.project_code=p.project_code WHERE p.busi_type='11' AND f.raw_add_time <= 'endTime' GROUP BY p.project_code) fs JOIN (SELECT p.project_code, IFNULL(SUM(f.liquidity_loan_amount),0) 'd1', IFNULL(SUM(f.fixed_assets_financing_amount),0) 'd2', IFNULL(SUM(f.acceptance_bill_amount),0) 'd3', IFNULL(SUM(f.credit_letter_amount),0) 'd4' FROM f_counter_guarantee_repay f JOIN f_counter_guarantee_release fg ON fg.form_id=f.form_id JOIN project_data_info p ON fg.project_code=p.project_code JOIN form fm ON f.form_id=fm.form_id WHERE fm.status='APPROVAL' AND p.busi_type='11' AND fm.finish_time<='endTime' GROUP BY p.project_code) jb ON fs.project_code=jb.project_code"
			.replaceAll("endTime", endTime);
		queyOrder.setSql(sql);
		jb = pmReportServiceClient.doQuery(queyOrder);
		if (ListUtil.isNotEmpty(jb)) {
			for (DataListItem itm : jb) {
				if (itm.getMap().containsKey("d1"))
					d1.setData11(toRs(itm.getMap().get("d1")));
				if (itm.getMap().containsKey("d2"))
					d2.setData11(toRs(itm.getMap().get("d2")));
				if (itm.getMap().containsKey("d3"))
					d3.setData11(toRs(itm.getMap().get("d3")));
				if (itm.getMap().containsKey("d4"))
					d4.setData11(toRs(itm.getMap().get("d4")));
			}
		}
		List<ExpectReleaseInfo> list = new ArrayList<ExpectReleaseInfo>();
		list.add(hj);
		list.add(d1);
		list.add(d2);
		list.add(d3);
		list.add(d4);
		return list;
		
	}
	
	/** 减法 */
	private Object objMul(Object obj1, Object obj2) {
		try {
			double l1 = Double.parseDouble(String.valueOf(obj1));
			double l2 = Double.parseDouble(String.valueOf(obj2));
			return l1 - l2;
		} catch (Exception e) {
		}
		return "0";
		
	}
	
	/** 加法 */
	private Object objAdd(Object obj1, Object obj2) {
		try {
			double l1 = Double.parseDouble(String.valueOf(obj1));
			double l2 = Double.parseDouble(String.valueOf(obj2));
			return l1 + l2;
		} catch (Exception e) {
			logger.info("+++++++obj1={},obj2={}:", obj1, obj2, e);
		}
		return "0";
		
	}
	
	/** 返回的数字保留1位小数 */
	private String toRs(Object obj) {
		try {
			double a = Math.round(Double.parseDouble(String.valueOf(obj)) * 10);
			if (a % 10 == 0) {
				return String.valueOf((int) a / 10);
			} else {
				return String.valueOf(a / 10.0);
			}
		} catch (Exception e) {
			
		}
		return "0";
		
	}
	
	/**
	 * 按大类统计
	 * */
	private String getCountSql(String busy_types, String show_name) {
		
		String sql = "SELECT 'show_name' AS '产品类别', 'show_name' AS '产品名', IFNULL(count1,0) AS '新增户数',IFNULL(count2,0) AS '新增笔数', IFNULL(count3,0) AS '在保户数',IFNULL(count4,0) AS '在保笔数',IFNULL(count5,0) AS '担保合同金额',IFNULL(count6,0) AS '年初在保余额',count7 AS '本年累计发生额',count8 AS '本年累计解保额',IFNULL(count9,0) AS '期末在保余额',IFNULL(count10,0) AS '平均费率' FROM "
						+ "("
						+ "(SELECT COUNT(DISTINCT customer_id) count1 FROM  tableName WHERE   customer_add_time >= 'startTime' AND 'endTime'  >= customer_add_time    projectDateSql AND busi_type REGEXP 'busy_types' "
						+ ") s1 "
						+ "LEFT JOIN  "
						+ "(SELECT COUNT(DISTINCT project_code) count2 FROM  tableName  WHERE apply_date >= 'startTime' AND  'endTime'  >= apply_date   projectDateSql AND busi_type REGEXP 'busy_types'  "
						+ ") s2 ON 1=1  "
						+ "LEFT JOIN  "
						+ "(SELECT COUNT(DISTINCT customer_id) count3,COUNT(DISTINCT project_code) count4 FROM  tableName  WHERE  custom_name1 = '在保'   projectDateSql AND busi_type REGEXP 'busy_types'  "
						+ ") s3 ON 1=1  "
						+ "LEFT JOIN  "
						+ "(SELECT IFNULL(SUM(blance),0) count6  FROM project_data_info_his_data  WHERE  project_date= 'lastYearEndDay' AND busi_type REGEXP 'busy_types'   AND custom_name1='在保'  "
						+ ") s4 ON 1=1  "
						+ "LEFT JOIN  "
						
						//担保合同金额   
						+ "(SELECT IFNULL(SUM(contract_amount),0) count5  FROM  project_data_info WHERE contract_time >= 'yearBeginDay' AND contract_time <= 'thisEndDay' AND busi_type REGEXP 'busy_types'  "
						+ ") s44 ON 1=1  "
						+ "LEFT JOIN  "
						
						//本年累计发生额 7
						+ "(SELECT IFNULL(SUM(occur_amount),0) AS count7  FROM view_project_occer_detail  WHERE  occur_date >= 'yearBeginDay' AND occur_date <= 'thisEndDay' AND busi_type REGEXP 'busy_types' "
						+ ") s80 ON 1 = 1  "
						+ "LEFT JOIN  "
						
						//本年累计解保金额 8
						+ "(SELECT IFNULL(SUM(repay_amount),0) AS count8  FROM view_project_repay_detail  WHERE  repay_confirm_time >= 'yearBeginDay' AND repay_confirm_time <= 'thisEndDay' AND busi_type REGEXP 'busy_types' AND ((busi_type='411' AND repay_type REGEXP '^代偿|^解保') OR busi_type!='411') "
						+ ") s50 ON 1 = 1  "
						+ "LEFT JOIN  "
						
						+ "(SELECT IFNULL(SUM(blance),0) count9  FROM tableName  WHERE  busi_type REGEXP 'busy_types'    AND custom_name1='在保'  projectDateSql  "
						+ ") s6  ON 1=1 "
						+ "LEFT JOIN  "
						+ "(SELECT  (SUM(guarantee_fee_rate*blance)+SUM((guarantee_amount/contract_amount)*blance))/SUM(blance) count10  FROM tableName  WHERE   busi_type REGEXP 'busy_types' projectDateSql "
						+ ") s7  ON 1=1 " + ")";
		return sql.replaceAll("busy_types", busy_types).replace("show_name", show_name);
	}
	
	/** 按项目小类型统计 */
	private String getTypeSql() {
		String sql = "SELECT c.busi_type AS '产品类别', c.busi_type_name AS '产品名', IFNULL(count1,0) AS '新增户数',IFNULL(count2,0) AS '新增笔数', IFNULL(count3,0) AS '在保户数',IFNULL(count4,0) AS '在保笔数',IFNULL(count5,0) AS '担保合同金额',IFNULL(count6,0) AS '年初在保余额',count7 AS '本年累计发生额',count8 AS '本年累计解保额',IFNULL(count9,0) AS '期末在保余额',IFNULL(count10,0) AS '平均费率' FROM "
						+ "("
						+ "(SELECT busi_type_name,busi_type FROM tableName WHERE busi_type IS NOT NULL AND busi_type !='11' GROUP BY busi_type "
						+ ") c LEFT JOIN "
						+ "(SELECT COUNT(DISTINCT customer_id) count1,busi_type FROM  tableName WHERE   customer_add_time >= 'startTime' AND 'endTime'  >= customer_add_time    projectDateSql AND busi_type IS NOT NULL GROUP BY busi_type "
						+ ") s1  ON c.busi_type=s1.busi_type "
						+ "LEFT JOIN  "
						+ "(SELECT COUNT(DISTINCT project_code) count2,busi_type FROM  tableName  WHERE apply_date >= 'startTime' AND  'endTime'  >= apply_date   projectDateSql GROUP BY busi_type  "
						+ ") s2 ON c.busi_type=s2.busi_type  "
						+ "LEFT JOIN  "
						+ "(SELECT COUNT(DISTINCT customer_id) count3,COUNT(DISTINCT project_code) count4 ,busi_type FROM  tableName  WHERE  busi_type IS NOT NULL    projectDateSql AND custom_name1 = '在保' GROUP BY busi_type  "
						+ ") s3 ON c.busi_type=s3.busi_type  "
						+ "LEFT JOIN  "
						+ "(SELECT IFNULL(SUM(blance),0) count6 , busi_type FROM project_data_info_his_data  WHERE  busi_type IS NOT NULL    AND project_date= 'lastYearEndDay'  AND custom_name1='在保' GROUP BY busi_type  "
						+ ") s4 ON c.busi_type=s4.busi_type  "
						+ "LEFT JOIN  "
						
						//担保合同金额   
						+ "(SELECT IFNULL(SUM(contract_amount),0) count5 ,busi_type FROM  project_data_info WHERE busi_type IS NOT NULL AND contract_time >= 'yearBeginDay' AND contract_time <= 'thisEndDay' GROUP BY busi_type  "
						+ ") s44 ON c.busi_type=s44.busi_type  "
						+ "LEFT JOIN  "
						
						//本年累计发生额 7
						+ "(SELECT IFNULL(SUM(occur_amount),0) AS count7 ,busi_type FROM view_project_occer_detail  WHERE  occur_date >= 'yearBeginDay' AND occur_date <= 'thisEndDay'  GROUP BY busi_type "
						+ ") s50 ON c.busi_type=s50.busi_type  "
						+ "LEFT JOIN  "
						
						//本年累计解保金额 8
						+ "(SELECT IFNULL(SUM(repay_amount),0) AS count8 ,busi_type  FROM view_project_repay_detail  WHERE  repay_confirm_time >= 'yearBeginDay' AND repay_confirm_time <= 'thisEndDay' AND ((busi_type='411' AND repay_type REGEXP '^代偿|^解保') OR busi_type!='411') GROUP BY busi_type "
						+ ") s60 ON c.busi_type=s60.busi_type  "
						+ "LEFT JOIN  "
						
						+ "(SELECT busi_type,IFNULL(SUM(blance),0) count9  FROM tableName  WHERE  busi_type IS NOT NULL   AND custom_name1='在保'  projectDateSql  GROUP BY busi_type "
						+ ") s7  ON c.busi_type=s7.busi_type "
						+ "LEFT JOIN  "
						+ "(SELECT busi_type, (SUM(guarantee_fee_rate*blance)+SUM((guarantee_amount/contract_amount)*blance))/SUM(blance) count10  FROM tableName  WHERE  busi_type IS NOT NULL   projectDateSql  GROUP BY busi_type"
						+ ") s8  ON c.busi_type=s8.busi_type " + ")";
		return sql;
	}
	
	/** 初始返回结果 */
	private ExpectReleaseInfo getRs(String name) {
		ExpectReleaseInfo info = new ExpectReleaseInfo();
		info.setData1(name);
		info.setData2("0");
		info.setData3("0");
		info.setData4("0");
		info.setData5("0");
		info.setData11("0");
		return info;
	}
	
}
