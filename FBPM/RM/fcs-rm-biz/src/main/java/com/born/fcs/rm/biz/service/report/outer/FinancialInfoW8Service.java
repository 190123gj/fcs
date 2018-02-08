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
import com.born.fcs.rm.biz.service.report.AccountBalanceHelper;
import com.born.fcs.rm.integration.service.pm.PmReportServiceClient;
import com.born.fcs.rm.ws.info.accountbalance.AccountBalanceDataInfo;
import com.born.fcs.rm.ws.info.report.outer.GuaranteeBusiInfoW5Info;
import com.born.fcs.rm.ws.order.accountbalance.AccountBalanceDataQueryOrder;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.service.accountbalance.AccountBalanceService;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * W8-（工信部网上直报）业绩财务信息
 * @author wuzj
 */
@Service("financialInfoW8Service")
public class FinancialInfoW8Service extends BaseAutowiredDomainService implements
																		ReportProcessService {
	@Autowired
	GuaranteeBusiInfoW5Service guaranteeBusiInfoW5Service;
	
	@Autowired
	private PmReportServiceClient pmReportServiceClient;
	
	@Autowired
	AccountBalanceService accountBalanceService;
	
	@Override
	public FcsBaseResult save(ReportOrder order) {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object findByAccountPeriod(ReportQueryOrder order) {
		QueryBaseBatchResult<GuaranteeBusiInfoW5Info> result = new QueryBaseBatchResult<GuaranteeBusiInfoW5Info>();
		List<GuaranteeBusiInfoW5Info> data = new ArrayList<GuaranteeBusiInfoW5Info>();
		try {
			
			//担保业务情况
			QueryBaseBatchResult<GuaranteeBusiInfoW5Info> gResult = (QueryBaseBatchResult<GuaranteeBusiInfoW5Info>) guaranteeBusiInfoW5Service
				.findByAccountPeriod(order);
			
			//按担按单笔担保金额划分 、按受保对象类型划分（个人贷款用于企业经营属于企业担保）
			List<GuaranteeBusiInfoW5Info> dataList = gResult.getPageList();
			//			GuaranteeBusiInfoW5Info dbTitle = new GuaranteeBusiInfoW5Info(null, null, "一、担保业务状况");
			//			dbTitle.setTitle(true);
			//			data.add(dbTitle);
			
			//合计只加入一次
			boolean hasTotal = false;
			for (GuaranteeBusiInfoW5Info info : dataList) {
				if ("合计".equals(info.getClassify())) {
					if (hasTotal)
						continue;
					info.setClassify("（一）担保业务合计");
					data.add(0, info);
					hasTotal = true;
				} else {
					data.add(info);
				}
			}
			
			//担保业务情况 （四）融资性担保业务 、（五）非融资性担保业务
			data.addAll(dbqk(order));
			
			//科目余额表
			List<AccountBalanceDataInfo> accountBalance = thisMonthBalance(order);
			List<AccountBalanceDataInfo> last = lastYearBalance(order);
			
			//二、准备金提取状况
			data.addAll(zbqk(order, accountBalance));
			
			//三、担保代偿状况
			List<GuaranteeBusiInfoW5Info> compList = comp(order);
			boolean hasReport = false;
			if ("hasReport".equals(compList.get(0).getClassify())) {
				hasReport = true;
				compList.remove(0);
			}
			data.addAll(compList);
			
			//四、财务状况
			data.addAll(cwzk(order, accountBalance));
			
			//五、收益情况
			data.addAll(syqk(order, accountBalance, last));
			
			//六、与金融机构合作情况
			data.addAll(jgqk(order));
			
			result.setSuccess(true);
			result.setPageList(data);
			result.setTotalCount(data.size());
			result.setSortCol(hasReport ? "IS" : "NO");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("W8-（工信部网上直报）业绩财务信息出错");
			logger.error("查询W8-（工信部网上直报）业绩财务信息出错：{}", e);
		}
		return result;
	}
	
	/**
	 * 担保业务情况 （四）融资性担保业务 、（五）非融资性担保业务
	 * @param order
	 * @return
	 */
	private List<GuaranteeBusiInfoW5Info> dbqk(ReportQueryOrder order) {
		
		//初始化 （四）融资性担保业务 、（五）非融资性担保业务
		LinkedHashMap<String, GuaranteeBusiInfoW5Info> dataMap = new LinkedHashMap<String, GuaranteeBusiInfoW5Info>();
		
		//融资性担保业务
		dataMap.put("个人消费贷款担保", new GuaranteeBusiInfoW5Info("busiType", "个人业务"));
		dataMap.put("下岗职工创业贷款担保", new GuaranteeBusiInfoW5Info("busiType", "个人业务"));
		dataMap.put("其他-个人", new GuaranteeBusiInfoW5Info("busiType", "个人业务"));
		
		dataMap.put("流动资金贷款", new GuaranteeBusiInfoW5Info("busiType", "企业业务"));
		dataMap.put("固定资产融资", new GuaranteeBusiInfoW5Info("busiType", "企业业务"));
		dataMap.put("承兑汇票担保", new GuaranteeBusiInfoW5Info("busiType", "企业业务"));
		dataMap.put("信用证担保", new GuaranteeBusiInfoW5Info("busiType", "企业业务"));
		
		dataMap.put("债券类担保（包括短期融资券、债券担保等）", new GuaranteeBusiInfoW5Info("busiType", "企业业务"));
		dataMap.put("其他", new GuaranteeBusiInfoW5Info("busiType", "企业业务"));
		
		//非融资性担保业务
		dataMap.put("个人业务", new GuaranteeBusiInfoW5Info("busiType", null, "个人业务"));
		dataMap.put("企业业务", new GuaranteeBusiInfoW5Info("busiType", null, "企业业务"));
		
		//在保余额、户数、笔数(期初、期末)
		zbBalanceHsAndBs(order, dataMap);
		
		//本期解除担保额
		decrease(order, dataMap);
		
		//本期新增担保户数、笔数
		increaseBsAndHs(order, dataMap);
		
		List<GuaranteeBusiInfoW5Info> busiList = Lists.newArrayList();
		for (String classify : dataMap.keySet()) {
			GuaranteeBusiInfoW5Info info = dataMap.get(classify);
			info.setClassify(classify);
			//计算减少户数
			info.setDecreaseHs(info.getZbhsBeginning() + info.getHs() - info.getZbhsEnding());
			//计算本期新增
			info.setIncrease(info.getBalanceEnding().subtract(info.getBalanceBeginning())
				.add(info.getDecrease()));
			if (classify.indexOf("-") > 0) {
				classify = classify.substring(0, classify.indexOf("-"));
				info.setClassify(classify);
			}
			busiList.add(info);
		}
		
		return busiList;
	}
	
	/**
	 * 报告期-科目余额
	 * @param order
	 * @return
	 */
	private List<AccountBalanceDataInfo> thisMonthBalance(ReportQueryOrder order) {
		AccountBalanceDataQueryOrder abQueryOrder = new AccountBalanceDataQueryOrder();
		abQueryOrder.setReportYear(order.getReportYear());
		abQueryOrder.setReportMonth(order.getReportMonth());
		List<AccountBalanceDataInfo> list = accountBalanceService.queryDatas(abQueryOrder);
		return list;
	}
	
	/**
	 * 报告期去年-科目余额(取上年最后一天)
	 * @param order
	 * @return
	 */
	private List<AccountBalanceDataInfo> lastYearBalance(ReportQueryOrder order) {
		AccountBalanceDataQueryOrder abQueryOrder = new AccountBalanceDataQueryOrder();
		abQueryOrder.setReportYear(order.getReportYear() - 1);
		abQueryOrder.setReportMonth(12);
		List<AccountBalanceDataInfo> list = accountBalanceService.queryDatas(abQueryOrder);
		return list;
	}
	
	/**
	 * 二、准备金提取状况
	 * @param order
	 * @return
	 */
	private List<GuaranteeBusiInfoW5Info> zbqk(ReportQueryOrder order,
												List<AccountBalanceDataInfo> accountBalance) {
		
		List<GuaranteeBusiInfoW5Info> data = new ArrayList<GuaranteeBusiInfoW5Info>();
		//		GuaranteeBusiInfoW5Info title = new GuaranteeBusiInfoW5Info(null, null, "二、准备金提取状况");
		//		title.setTitle(true);
		
		GuaranteeBusiInfoW5Info info1 = new GuaranteeBusiInfoW5Info("zbj", null, "（一）未到期责任准备金（万元）");
		GuaranteeBusiInfoW5Info info2 = new GuaranteeBusiInfoW5Info("zbj", null, "（二）担保赔偿准备金（万元）");
		GuaranteeBusiInfoW5Info info3 = new GuaranteeBusiInfoW5Info("zbj", null, "（三）一般风险准备（万元）");
		//期末-当期
		if (ListUtil.isNotEmpty(accountBalance)) {
			
			//W2-（中担协）融资性担保机构资产负债情况 GuaranteeDebtInfoServiceImpl
			AccountBalanceHelper helper = new AccountBalanceHelper(accountBalance, "1", "2");
			
			//期初
			
			//17.未到期责任准备金	2601贷方余额
			info1.setMoneyBeginning(helper.caculateInitialCredit("2601"));
			//18.担保赔偿准备金		2602贷余
			info2.setMoneyBeginning(helper.caculateInitialCredit("2602"));
			//21.一般风险准备		4102贷方余额
			info3.setMoneyBeginning(helper.caculateInitialCredit("4102"));
			
			//期末
			
			//17.未到期责任准备金	2601贷方余额
			info1.setMoneyEnding(helper.caculateEndingCredit("2601"));
			//18.担保赔偿准备金		2602贷余
			info2.setMoneyEnding(helper.caculateEndingCredit("2602"));
			//21.一般风险准备		4102贷方余额
			info3.setMoneyEnding(helper.caculateEndingCredit("4102"));
			
			//本期净增加=期末-期初
			info1.setMoneyIncrease(info1.getMoneyEnding().subtract(info1.getMoneyBeginning()));
			info2.setMoneyIncrease(info2.getMoneyEnding().subtract(info2.getMoneyBeginning()));
			info3.setMoneyIncrease(info3.getMoneyEnding().subtract(info3.getMoneyBeginning()));
		}
		
		//data.add(title);
		data.add(info1);
		data.add(info2);
		data.add(info3);
		
		return data;
	}
	
	/**
	 * 三、担保代偿状况
	 * @param order
	 * @param data
	 */
	private List<GuaranteeBusiInfoW5Info> comp(ReportQueryOrder order) {
		
		List<GuaranteeBusiInfoW5Info> data = new ArrayList<GuaranteeBusiInfoW5Info>();
		//		GuaranteeBusiInfoW5Info title = new GuaranteeBusiInfoW5Info(null, null, "三、担保代偿状况");
		//		title.setTitle(true);
		
		LinkedHashMap<String, GuaranteeBusiInfoW5Info> dataMap = new LinkedHashMap<String, GuaranteeBusiInfoW5Info>();
		dataMap.put("（一）累计担保代偿额（万元）", new GuaranteeBusiInfoW5Info("dc", null, "（一）累计担保代偿额（万元）"));
		dataMap.put("（二）本期代偿回收额（万元）", new GuaranteeBusiInfoW5Info("dc", null, "（二）本期代偿回收额（万元）"));
		dataMap.put("（三）累计担保代偿笔数（笔）", new GuaranteeBusiInfoW5Info("dc", null, "（三）累计担保代偿笔数（笔）"));
		dataMap.put("（四）累计担保损失额（万元）", new GuaranteeBusiInfoW5Info("dc", null, "（四）累计担保损失额（万元）"));
		
		GuaranteeBusiInfoW5Info dbInfo = new GuaranteeBusiInfoW5Info("dc", null, "（五）累计担保额（万元）");
		
		//上报
		String sql = "SELECT d.data1, d.data2, d.data3, d.data4 FROM ${rmDbTitle}.submission s "
						+ "JOIN  ${rmDbTitle}.submission_data d ON s.submission_id = d.submission_id WHERE s.report_code = 'YJCWXX' AND s.report_time = '${reportMonth}' AND s.reporter_status IN ('IN_USE', 'SUBMITTED')";
		
		sql = sql.replaceAll("\\$\\{rmDbTitle\\}", rmDbTitle).replaceAll("\\$\\{reportMonth\\}",
			order.getReportYearMonth());
		
		//是否已经上报
		boolean hasReport = false;
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(sql);
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String data1 = String.valueOf(itm.getMap().get("data1"));
				String data2 = String.valueOf(itm.getMap().get("data2"));
				String data3 = String.valueOf(itm.getMap().get("data3"));
				String data4 = String.valueOf(itm.getMap().get("data4"));
				GuaranteeBusiInfoW5Info info = dataMap.get(data1);
				if (info != null) {
					if ("（三）累计担保代偿笔数（笔）".equals(data1)) {
						info.setNumBeginning(NumberUtil.parseLong(data2));
						info.setNumIncrease(NumberUtil.parseLong(data3));
						info.setNumEnding(NumberUtil.parseLong(data4));
					} else {
						info.setMoneyBeginning(toMoneyW(data2));
						info.setMoneyIncrease(toMoneyW(data3));
						info.setMoneyEnding(toMoneyW(data4));
					}
				}
				hasReport = true;
			}
		}
		
		//累计担保额
		String dbSql = "SELECT '累计发生额',SUM(occur_amount) amount FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date <= '${reportEndDay}'";
		dbSql = dbSql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		
		//期末
		queyOrder.setSql(dbSql.replaceAll("\\$\\{reportEndDay\\}", order.getReportMonthEndDay()));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String amount = String.valueOf(itm.getMap().get("amount"));
				dbInfo.setMoneyEnding(toMoney(amount));
			}
		}
		
		//期初
		queyOrder
			.setSql(dbSql.replaceAll("\\$\\{reportEndDay\\}", order.getReportLastMonthEndDay()));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String amount = String.valueOf(itm.getMap().get("amount"));
				dbInfo.setMoneyBeginning(toMoney(amount));
			}
		}
		
		//新增
		dbInfo.setMoneyIncrease(dbInfo.getMoneyEnding().subtract(dbInfo.getMoneyBeginning()));
		//data.add(title);
		for (String key : dataMap.keySet()) {
			data.add(dataMap.get(key));
		}
		data.add(dbInfo);
		
		//有报送、虚拟一个
		if (hasReport) {
			data.add(0, new GuaranteeBusiInfoW5Info("dc", null, "hasReport"));
		}
		
		return data;
	}
	
	/**
	 * 四、财务状况
	 * @param order
	 * @return
	 */
	private List<GuaranteeBusiInfoW5Info> cwzk(ReportQueryOrder order,
												List<AccountBalanceDataInfo> accountBalance) {
		
		List<GuaranteeBusiInfoW5Info> data = new ArrayList<GuaranteeBusiInfoW5Info>();
		//		GuaranteeBusiInfoW5Info title = new GuaranteeBusiInfoW5Info(null, null, "四、财务状况");
		//		title.setTitle(true);
		
		GuaranteeBusiInfoW5Info info1 = new GuaranteeBusiInfoW5Info("cw", null, "（一）资产总额");
		GuaranteeBusiInfoW5Info info2 = new GuaranteeBusiInfoW5Info("cw", null, "    1、流动资产合计");
		GuaranteeBusiInfoW5Info info3 = new GuaranteeBusiInfoW5Info("cw", null, "       其中:货币资金");
		GuaranteeBusiInfoW5Info info4 = new GuaranteeBusiInfoW5Info("cw", null, "            短期投资");
		GuaranteeBusiInfoW5Info info5 = new GuaranteeBusiInfoW5Info("cw", null,
			"              其中：委贷");
		GuaranteeBusiInfoW5Info info6 = new GuaranteeBusiInfoW5Info("cw", null, "            存出保证金");
		GuaranteeBusiInfoW5Info info7 = new GuaranteeBusiInfoW5Info("cw", null, "    2、长期投资合计");
		GuaranteeBusiInfoW5Info info8 = new GuaranteeBusiInfoW5Info("cw", null, "       其中：长期股权投资");
		GuaranteeBusiInfoW5Info info9 = new GuaranteeBusiInfoW5Info("cw", null, "    3、固定资产合计");
		GuaranteeBusiInfoW5Info info10 = new GuaranteeBusiInfoW5Info("cw", null, "（二）负债");
		GuaranteeBusiInfoW5Info info11 = new GuaranteeBusiInfoW5Info("cw", null, "    1、流动负债合计");
		GuaranteeBusiInfoW5Info info12 = new GuaranteeBusiInfoW5Info("cw", null, "       其中:短期借款");
		GuaranteeBusiInfoW5Info info13 = new GuaranteeBusiInfoW5Info("cw", null,
			"            存入保证金");
		GuaranteeBusiInfoW5Info info14 = new GuaranteeBusiInfoW5Info("cw", null, "    2、长期负债合计");
		GuaranteeBusiInfoW5Info info15 = new GuaranteeBusiInfoW5Info("cw", null, "（三）所有者权益");
		GuaranteeBusiInfoW5Info info16 = new GuaranteeBusiInfoW5Info("cw", null, "    其中：实收资本");
		
		//期末-当期
		if (ListUtil.isNotEmpty(accountBalance)) {
			
			AccountBalanceHelper helper = new AccountBalanceHelper(accountBalance, "1", "2");
			
			//期初
			
			//总资产（1开头的借方-贷方，加和）
			info1.setMoneyBeginning(helper.caculateCapitalInitial());
			//总资产-1811借方余额-1604借方余额-1701借方 + 1702贷方-（1601借方-1602贷方）- 1511借方
			Money debit = helper.caculateInitialDebit("1811", "1604", "1701", "1601", "1511");
			Money credit = helper.caculateInitialCredit("1702", "1602");
			info2.setMoneyBeginning(info1.getMoneyBeginning().subtract(debit).add(credit));
			//其中:货币资金 1001+1002+1012借方
			info3.setMoneyBeginning(helper.caculateInitialDebit("1001", "1002", "1012"));
			//     短期投资 1321+1501借方
			info4.setMoneyBeginning(helper.caculateInitialDebit("1321", "1501"));
			//     	  其中:委贷 1321借方
			info5.setMoneyBeginning(helper.caculateInitialDebit("1321"));
			//     存出保证金  1031 借方余额
			info6.setMoneyBeginning(helper.caculateInitialDebit("1031"));
			//2、长期投资合计 1511 借方余额
			info7.setMoneyBeginning(helper.caculateInitialDebit("1511"));
			info8.setMoneyBeginning(info7.getMoneyBeginning());
			//3、固定资产合计 1601借方-1602贷方
			info9.setMoneyBeginning(helper.caculateInitialDebit("1601").subtract(
				helper.caculateInitialCredit("1602")));
			//负债 2开头的所有贷方-借方
			info10.setMoneyBeginning(helper.caculateDebtInitial());
			//1、流动负债合计 总负债 -2401 贷方余额
			info11.setMoneyBeginning(info10.getMoneyBeginning().subtract(
				helper.caculateInitialCredit("2401")));
			//	  其中:短期借款 0
			info12.setMoneyBeginning(ZERO_MONEY);
			//		   存入保证金 2002贷方余额
			info13.setMoneyBeginning(helper.caculateInitialCredit("2002"));
			//2、长期负债合计= 负债-流动负债合计
			info14.setMoneyBeginning(info10.getMoneyBeginning()
				.subtract(info11.getMoneyBeginning()));
			//所有者权益=总资产-总负债
			info15
				.setMoneyBeginning(info1.getMoneyBeginning().subtract(info10.getMoneyBeginning()));
			//	其中：实收资本 4001贷方余额
			info16.setMoneyBeginning(helper.caculateInitialCredit("4001"));
			
			//期末
			
			//总资产（1开头的借方-贷方，加和）
			info1.setMoneyEnding(helper.caculateCapital());
			//总资产-1811借方余额-1604借方余额-1701借方 + 1702贷方-（1601借方-1602贷方）- 1511借方
			debit = helper.caculateEndingDebit("1811", "1604", "1701", "1601", "1511");
			credit = helper.caculateEndingCredit("1702", "1602");
			info2.setMoneyEnding(info1.getMoneyEnding().subtract(debit).add(credit));
			//其中:货币资金 1001+1002+1012借方
			info3.setMoneyEnding(helper.caculateEndingDebit("1001", "1002", "1012"));
			//     短期投资 1321+1501借方
			info4.setMoneyEnding(helper.caculateEndingDebit("1321", "1501"));
			//     	  其中:委贷 1321借方
			info5.setMoneyEnding(helper.caculateEndingDebit("1321"));
			//     存出保证金  1031借方余额
			info6.setMoneyEnding(helper.caculateEndingDebit("1031"));
			//2、长期投资合计 1511借方余额
			info7.setMoneyEnding(helper.caculateEndingDebit("1511"));
			info8.setMoneyEnding(info7.getMoneyEnding());
			//3、固定资产合计 1601借方-1602贷方
			info9.setMoneyEnding(helper.caculateEndingDebit("1601").subtract(
				helper.caculateEndingCredit("1602")));
			//负债 2开头的所有贷方-借方
			info10.setMoneyEnding(helper.caculateDebt());
			//1、流动负债合计 总负债 -2401 贷方余额
			info11.setMoneyEnding(info10.getMoneyEnding().subtract(
				helper.caculateEndingCredit("2401")));
			//	  其中:短期借款 0
			info12.setMoneyEnding(ZERO_MONEY);
			//		   存入保证金2002贷方余额
			info13.setMoneyEnding(helper.caculateEndingCredit("2002"));
			//2、长期负债合计= 负债-流动负债合计
			info14.setMoneyEnding(info10.getMoneyEnding().subtract(info11.getMoneyEnding()));
			//所有者权益=总资产-总负债
			info15.setMoneyEnding(info1.getMoneyEnding().subtract(info10.getMoneyEnding()));
			//	其中：实收资本 4001贷方余额
			info16.setMoneyEnding(helper.caculateEndingCredit("4001"));
		}
		
		//data.add(title);
		info1.setClassify(info1.getClassify().replaceAll("  ", "&emsp;"));
		info2.setClassify(info2.getClassify().replaceAll("  ", "&emsp;"));
		info3.setClassify(info3.getClassify().replaceAll("  ", "&emsp;"));
		info4.setClassify(info4.getClassify().replaceAll("  ", "&emsp;"));
		info5.setClassify(info5.getClassify().replaceAll("  ", "&emsp;"));
		info6.setClassify(info6.getClassify().replaceAll("  ", "&emsp;"));
		info7.setClassify(info7.getClassify().replaceAll("  ", "&emsp;"));
		info8.setClassify(info8.getClassify().replaceAll("  ", "&emsp;"));
		info9.setClassify(info9.getClassify().replaceAll("  ", "&emsp;"));
		info10.setClassify(info10.getClassify().replaceAll("  ", "&emsp;"));
		info11.setClassify(info11.getClassify().replaceAll("  ", "&emsp;"));
		info12.setClassify(info12.getClassify().replaceAll("  ", "&emsp;"));
		info13.setClassify(info13.getClassify().replaceAll("  ", "&emsp;"));
		info14.setClassify(info14.getClassify().replaceAll("  ", "&emsp;"));
		info15.setClassify(info15.getClassify().replaceAll("  ", "&emsp;"));
		info16.setClassify(info16.getClassify().replaceAll("  ", "&emsp;"));
		
		data.add(info1);
		data.add(info2);
		data.add(info3);
		data.add(info4);
		data.add(info5);
		data.add(info6);
		data.add(info7);
		data.add(info8);
		data.add(info9);
		data.add(info10);
		data.add(info11);
		data.add(info12);
		data.add(info13);
		data.add(info14);
		data.add(info15);
		data.add(info16);
		
		return data;
	}
	
	/**
	 * 五、收益情况
	 * @param order
	 * @return
	 */
	private List<GuaranteeBusiInfoW5Info> syqk(ReportQueryOrder order,
												List<AccountBalanceDataInfo> accountBalance,
												List<AccountBalanceDataInfo> last) {
		
		List<GuaranteeBusiInfoW5Info> data = new ArrayList<GuaranteeBusiInfoW5Info>();
		//		GuaranteeBusiInfoW5Info title = new GuaranteeBusiInfoW5Info(null, null, "五、收益情况");
		//		title.setTitle(true);
		
		GuaranteeBusiInfoW5Info info1 = new GuaranteeBusiInfoW5Info("sy", null, "项目");
		info1.setBeginning((order.getReportYear() - 1) + "");
		info1.setEnding(order.getReportYear() + "");
		
		GuaranteeBusiInfoW5Info info2 = new GuaranteeBusiInfoW5Info("sy", null, "（一）担保机构总收入");
		GuaranteeBusiInfoW5Info info3 = new GuaranteeBusiInfoW5Info("sy", null, "（二）担保业务收入");
		GuaranteeBusiInfoW5Info info4 = new GuaranteeBusiInfoW5Info("sy", null, "     其中：融资性担保费收入");
		GuaranteeBusiInfoW5Info info5 = new GuaranteeBusiInfoW5Info("sy", null, "（三）担保业务成本");
		GuaranteeBusiInfoW5Info info6 = new GuaranteeBusiInfoW5Info("sy", null, "     其中：营业税金及附加");
		GuaranteeBusiInfoW5Info info7 = new GuaranteeBusiInfoW5Info("sy", null, "（四）担保业务利润");
		GuaranteeBusiInfoW5Info info8 = new GuaranteeBusiInfoW5Info("sy", null, "（五）利息净收入");
		GuaranteeBusiInfoW5Info info9 = new GuaranteeBusiInfoW5Info("sy", null, "（六）其他业务利润");
		GuaranteeBusiInfoW5Info info10 = new GuaranteeBusiInfoW5Info("sy", null, "（七）业务及管理费");
		GuaranteeBusiInfoW5Info info11 = new GuaranteeBusiInfoW5Info("sy", null, "（八）投资净收益");
		GuaranteeBusiInfoW5Info info12 = new GuaranteeBusiInfoW5Info("sy", null, "（九）营业利润");
		GuaranteeBusiInfoW5Info info13 = new GuaranteeBusiInfoW5Info("sy", null,
			"（十）营业外净收入（净亏损以“-”号填列");
		GuaranteeBusiInfoW5Info info14 = new GuaranteeBusiInfoW5Info("sy", null,
			"（十一）资产减值损失（转回的金额以“-”号填列）");
		GuaranteeBusiInfoW5Info info15 = new GuaranteeBusiInfoW5Info("sy", null, "（十二）所得税");
		GuaranteeBusiInfoW5Info info16 = new GuaranteeBusiInfoW5Info("sy", null, "（十三）净利润");
		
		//今年
		if (ListUtil.isNotEmpty(accountBalance)) {
			
			AccountBalanceHelper helper = new AccountBalanceHelper(accountBalance);
			
			//（一）担保机构总收入 6031+6051+6103+6111+6301+6021+6071+6072+6073贷方余额
			info2.setMoneyEnding(helper.caculateYearCredit("6031", "6051", "6103", "6111", "6301",
				"6021", "6071", "6072", "6073"));
			
			//（二）担保业务收入 6031贷方余额
			info3.setMoneyEnding(helper.caculateYearCredit("6031"));
			//		其中：融资性担保费收入
			info4.setMoneyEnding(info3.getMoneyEnding());
			//（三）担保业务成本 6403借方发生累计
			info5.setMoneyEnding(helper.caculateYearDebit("6403"));
			//  	其中：营业税金及附加
			info6.setMoneyEnding(info5.getMoneyEnding());
			
			//GuaranteeProfitInfoServiceImpl W3表实现
			
			//（四）担保业务利润   W3 表中序号9
			//1.1. 担保业务收入		6031当年累计发生贷方
			Money m1 = helper.caculateYearCredit("6031");
			//3.2. 担保业务成本	6403借方发生累计
			Money m2 = helper.caculateYearDebit("6403");
			//7.3.提取未到期责任准备	6501借方发生累计
			Money m3 = helper.caculateYearDebit("6501");
			//8.4.担保赔偿准备金		6502借方发生累计
			Money m4 = helper.caculateYearDebit("6502");
			//9.5. 担保业务利润		本表1减2减3减4
			Money m5 = m1.subtract(m2).subtract(m3).subtract(m4);
			info7.setMoneyEnding(m5);
			
			//（五）利息净收入 6103贷方余额减6421借方余额
			info8.setMoneyEnding(helper.caculateYearCredit("6103").subtract(
				helper.caculateYearDebit("6421")));
			//（六）其他业务利润 6051贷方余额
			info9.setMoneyEnding(helper.caculateYearCredit("6051"));
			//（七）业务及管理费 6601借方余额
			info10.setMoneyEnding(helper.caculateYearDebit("6601"));
			//（八）投资净收益 6111贷方余额
			info11.setMoneyEnding(helper.caculateYearCredit("6111"));
			
			//（九）营业利润   W3 表中序号 15
			//10.6. 利息净收入（净支出则前加“－”号填列）	6103贷方累计减6421借方累计
			Money m6 = helper.caculateYearCredit("6103").subtract(helper.caculateYearDebit("6421"));
			//11.7. 其他业务利润	6051贷方累计
			Money m7 = helper.caculateYearCredit("6051");
			//12.8. 业务及管理费	6601借方累计
			Money m8 = helper.caculateYearDebit("6601");
			//13.9. 投资收益（投资损失则前加“－”号填列）	6111贷方累计
			Money m9 = helper.caculateYearCredit("6111");
			//14.10. 资产减值损失（转回的金额则前加“－”号填列）6701借方累计
			Money m10 = helper.caculateYearDebit("6701");
			//15.11. 营业利润	5加6加7减8加9减10
			Money m11 = m5.add(m6).add(m7).subtract(m8).add(m9).subtract(m10);
			info12.setMoneyEnding(m11);
			
			//（十）营业外净收入（净亏损以“-”号填列  W3表 16 
			//16.12. 营业外净收入（净亏损则前加“－”号填列）	6301贷方累计-6711借方累计
			Money m12 = helper.caculateYearCredit("6301")
				.subtract(helper.caculateYearDebit("6711"));
			info13.setMoneyEnding(m12);
			
			//（十一）资产减值损失（转回的金额以“-”号填列）W3 14 
			info14.setMoneyEnding(m10);
			//（十二）所得税  W3 17
			//17.13. 所得税	6801借方累计
			Money m13 = helper.caculateYearDebit("6801");
			info15.setMoneyEnding(m13);
			//（十三）净利润  W3 18
			//18.14. 净利润（净亏损则前加“－”号填列）	11加12减13
			Money m14 = m11.add(m12).subtract(m13);
			info16.setMoneyEnding(m14);
		}
		
		//去年
		if (ListUtil.isNotEmpty(last)) {
			
			AccountBalanceHelper helper = new AccountBalanceHelper(last);
			
			//（一）担保机构总收入 6031+6051+6103+6111+6301+6021+6071+6072+6073贷方余额
			info2.setMoneyBeginning(helper.caculateYearCredit("6031", "6051", "6103", "6111",
				"6301", "6021", "6071", "6072", "6073"));
			
			//（二）担保业务收入 6031贷方余额
			info3.setMoneyBeginning(helper.caculateYearCredit("6031"));
			//		其中：融资性担保费收入
			info4.setMoneyBeginning(info3.getMoneyEnding());
			//（三）担保业务成本 6403借方发生累计
			info5.setMoneyBeginning(helper.caculateYearDebit("6403"));
			//  	其中：营业税金及附加
			info6.setMoneyBeginning(info5.getMoneyEnding());
			
			//GuaranteeProfitInfoServiceImpl W3表实现
			
			//（四）担保业务利润   W3 表中序号9
			//1.1. 担保业务收入		6031当年累计发生贷方
			Money m1 = helper.caculateYearCredit("6031");
			//3.2. 担保业务成本	6403借方发生累计
			Money m2 = helper.caculateYearDebit("6403");
			//7.3.提取未到期责任准备	6501借方发生累计
			Money m3 = helper.caculateYearDebit("6501");
			//8.4.担保赔偿准备金		6502借方发生累计
			Money m4 = helper.caculateYearDebit("6502");
			//9.5. 担保业务利润		本表1减2减3减4
			Money m5 = m1.subtract(m2).subtract(m3).subtract(m4);
			info7.setMoneyBeginning(m5);
			
			//（五）利息净收入 6103贷方余额减6421借方余额
			info8.setMoneyBeginning(helper.caculateYearCredit("6103").subtract(
				helper.caculateYearDebit("6421")));
			//（六）其他业务利润 6051贷方余额
			info9.setMoneyBeginning(helper.caculateYearCredit("6051"));
			//（七）业务及管理费 6601借方余额
			info10.setMoneyBeginning(helper.caculateYearDebit("6601"));
			//（八）投资净收益 6111贷方余额
			info11.setMoneyBeginning(helper.caculateYearCredit("6111"));
			
			//（九）营业利润   W3 表中序号 15
			//10.6. 利息净收入（净支出则前加“－”号填列）	6103贷方累计减6421借方累计
			Money m6 = helper.caculateYearCredit("6103").subtract(helper.caculateYearDebit("6421"));
			//11.7. 其他业务利润	6051贷方累计
			Money m7 = helper.caculateYearCredit("6051");
			//12.8. 业务及管理费	6601借方累计
			Money m8 = helper.caculateYearDebit("6601");
			//13.9. 投资收益（投资损失则前加“－”号填列）	6111贷方累计
			Money m9 = helper.caculateYearCredit("6111");
			//14.10. 资产减值损失（转回的金额则前加“－”号填列）6701借方累计
			Money m10 = helper.caculateYearDebit("6701");
			//15.11. 营业利润	5加6加7减8加9减10
			Money m11 = m5.add(m6).add(m7).subtract(m8).add(m9).subtract(m10);
			info12.setMoneyBeginning(m11);
			
			//（十）营业外净收入（净亏损以“-”号填列  W3表 16 
			//16.12. 营业外净收入（净亏损则前加“－”号填列）	6301贷方累计-6711借方累计
			Money m12 = helper.caculateYearCredit("6301")
				.subtract(helper.caculateYearDebit("6711"));
			info13.setMoneyBeginning(m12);
			
			//（十一）资产减值损失（转回的金额以“-”号填列）W3 14 
			info14.setMoneyBeginning(m10);
			//（十二）所得税  W3 17
			//17.13. 所得税	6801借方累计
			Money m13 = helper.caculateYearDebit("6801");
			info15.setMoneyBeginning(m13);
			//（十三）净利润  W3 18
			//18.14. 净利润（净亏损则前加“－”号填列）	11加12减13
			Money m14 = m11.add(m12).subtract(m13);
			info16.setMoneyBeginning(m14);
		}
		
		info4.setClassify(info4.getClassify().replaceAll("  ", "&emsp;"));
		info6.setClassify(info6.getClassify().replaceAll("  ", "&emsp;"));
		
		//data.add(title);
		data.add(info1);
		data.add(info2);
		data.add(info3);
		data.add(info4);
		data.add(info5);
		data.add(info6);
		data.add(info7);
		data.add(info8);
		data.add(info9);
		data.add(info10);
		data.add(info11);
		data.add(info12);
		data.add(info13);
		data.add(info14);
		data.add(info15);
		data.add(info16);
		
		return data;
	}
	
	/**
	 * 六、与金融机构合作情况
	 * @param order
	 * @return
	 */
	private List<GuaranteeBusiInfoW5Info> jgqk(ReportQueryOrder order) {
		
		List<GuaranteeBusiInfoW5Info> data = new ArrayList<GuaranteeBusiInfoW5Info>();
		//		GuaranteeBusiInfoW5Info title = new GuaranteeBusiInfoW5Info(null, null, "六、与金融机构合作情况");
		//		title.setTitle(true);
		
		LinkedHashMap<String, GuaranteeBusiInfoW5Info> dataMap = new LinkedHashMap<String, GuaranteeBusiInfoW5Info>();
		dataMap.put("1、政策性银行及邮储银行", new GuaranteeBusiInfoW5Info("jg", null, "1、政策性银行及邮储银行"));
		dataMap.put("2、国有商业银行", new GuaranteeBusiInfoW5Info("jg", null, "2、国有商业银行"));
		dataMap.put("3、股份制商业银行", new GuaranteeBusiInfoW5Info("jg", null, "3、股份制商业银行"));
		dataMap.put("4、城市商业银行", new GuaranteeBusiInfoW5Info("jg", null, "4、城市商业银行"));
		dataMap.put("5、农村金融机构", new GuaranteeBusiInfoW5Info("jg", null, "5、农村金融机构"));
		dataMap.put("6、其他金融机构", new GuaranteeBusiInfoW5Info("jg", null, "6、其他金融机构"));
		
		GuaranteeBusiInfoW5Info total = new GuaranteeBusiInfoW5Info("jg", null, "7、合作的金融机构数量合计");
		
		String sql = "SELECT CASE institutional_attributes "
						+ "WHEN 'ZCXYH' THEN '1、政策性银行及邮储银行' "
						+ "WHEN 'GYSY' THEN '2、国有商业银行' "
						+ "WHEN 'GFYH' THEN '3、股份制商业银行' "
						+ "WHEN 'CSSY' THEN '4、城市商业银行' "
						+ "WHEN 'NCJR' THEN '5、农村金融机构' "
						+ "ELSE '6、其他金融机构' END classify,"
						+ "COUNT(DISTINCT channel_code) num FROM ${crmDbTitle}.channal_info "
						+ "WHERE raw_add_time <='${reportMonthEndDay}' GROUP BY classify";
		
		sql = sql.replaceAll("\\$\\{crmDbTitle\\}", crmDbTitle);
		
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		
		//期末
		queyOrder
			.setSql(sql.replaceAll("\\$\\{reportMonthEndDay\\}", order.getReportMonthEndDay()));
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String classify = String.valueOf(itm.getMap().get("classify"));
				String num = String.valueOf(itm.getMap().get("num"));
				GuaranteeBusiInfoW5Info info = dataMap.get(classify);
				if (info != null) {
					info.setNumEnding(NumberUtil.parseLong(num));
					total.setNumEnding(total.getNumEnding() + info.getNumEnding());
				}
			}
		}
		
		//期初
		queyOrder.setSql(sql.replaceAll("\\$\\{reportMonthEndDay\\}",
			order.getReportLastMonthEndDay()));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String classify = String.valueOf(itm.getMap().get("classify"));
				String num = String.valueOf(itm.getMap().get("num"));
				GuaranteeBusiInfoW5Info info = dataMap.get(classify);
				if (info != null) {
					info.setNumBeginning(NumberUtil.parseLong(num));
					total.setNumBeginning(total.getNumBeginning() + info.getNumBeginning());
				}
			}
		}
		
		//data.add(title);
		for (String classify : dataMap.keySet()) {
			data.add(dataMap.get(classify));
		}
		data.add(total);
		
		return data;
	}
	
	/**
	 * 在保余额、户数、笔数(期初、期末)
	 * @param order
	 * @param data
	 */
	private void zbBalanceHsAndBs(ReportQueryOrder order, Map<String, GuaranteeBusiInfoW5Info> data) {
		
		String sql = "SELECT  CASE WHEN p.customer_type = 'PERSIONAL' THEN '个人业务' WHEN p.busi_type LIKE '12%' THEN '债券类担保（包括短期融资券、债券担保等）' WHEN p.busi_type LIKE '2%' THEN '企业业务' ELSE '其他' END classify, COUNT(DISTINCT p.customer_id) hs, COUNT(*) bs, SUM(ba.balance) balance FROM ${pmDbTitle}.project_data_info p "
						+ "JOIN (SELECT occer.project_code, (oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date <= '${reportMonthEndDay}' GROUP BY project_code) occer "
						+ "LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_confirm_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE ba.balance > 0 AND ( p.busi_type like '12%' or p.busi_type like '13%' or p.busi_type like '2%' or p.busi_type like '3%')  GROUP BY classify";
		
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
		
		//按照业务细分类查询  
		//data.put("流动资金贷款", new GuaranteeBusiInfoW5Info("busiType", "企业业务"));
		sql = "SELECT '流动资金贷款' classify,COUNT(DISTINCT p.customer_id) hs, COUNT(*) bs, SUM(ba.liquidity_loan_balance) liquidity_loan_balance FROM ${pmDbTitle}.project_data_info p JOIN (SELECT occer.project_code, (occer.liquidity_loan - IFNULL(repay.liquidity_loan, 0)) liquidity_loan_balance FROM (SELECT project_code, SUM(liquidity_loan_amount) liquidity_loan FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date <= '${reportMonthEndDay}' GROUP BY project_code) occer LEFT JOIN (SELECT project_code, SUM(liquidity_loan_amount) liquidity_loan FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_confirm_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE liquidity_loan_balance > 0 AND (p.busi_type LIKE '11%')";
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		
		//期末
		queyOrder
			.setSql(sql.replaceAll("\\$\\{reportMonthEndDay\\}", order.getReportMonthEndDay()));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String hs = String.valueOf(itm.getMap().get("hs"));
				String bs = String.valueOf(itm.getMap().get("bs"));
				GuaranteeBusiInfoW5Info info = data.get("流动资金贷款");
				if (info != null) {
					info.setZbbsEnding(NumberUtil.parseLong(bs));
					info.setZbhsEnding(NumberUtil.parseLong(hs));
					info.setBalanceEnding(toMoney(itm.getMap().get("liquidity_loan_balance")));
				}
			}
		}
		//期初
		queyOrder.setSql(sql.replaceAll("\\$\\{reportMonthEndDay\\}",
			order.getReportLastMonthEndDay()));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String hs = String.valueOf(itm.getMap().get("hs"));
				String bs = String.valueOf(itm.getMap().get("bs"));
				GuaranteeBusiInfoW5Info info = data.get("流动资金贷款");
				if (info != null) {
					info.setZbbsBeginning(NumberUtil.parseLong(bs));
					info.setZbhsBeginning(NumberUtil.parseLong(hs));
					info.setBalanceBeginning(toMoney(itm.getMap().get("liquidity_loan_balance")));
				}
			}
		}
		
		//data.put("固定资产融资", new GuaranteeBusiInfoW5Info("busiType", "企业业务"));
		sql = "SELECT '固定资产融资' classify ,COUNT(DISTINCT p.customer_id) hs, COUNT(*) bs, SUM(ba.fixed_assets_financing_balance) fixed_assets_financing_balance FROM ${pmDbTitle}.project_data_info p JOIN (SELECT occer.project_code, (occer.fixed_assets_financing - IFNULL(repay.fixed_assets_financing, 0)) fixed_assets_financing_balance FROM (SELECT project_code, SUM(fixed_assets_financing_amount) fixed_assets_financing FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date <= '${reportMonthEndDay}' GROUP BY project_code) occer LEFT JOIN (SELECT project_code, SUM(fixed_assets_financing_amount) fixed_assets_financing FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_confirm_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE fixed_assets_financing_balance > 0 AND (p.busi_type LIKE '11%')";
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		
		//期末
		queyOrder
			.setSql(sql.replaceAll("\\$\\{reportMonthEndDay\\}", order.getReportMonthEndDay()));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String hs = String.valueOf(itm.getMap().get("hs"));
				String bs = String.valueOf(itm.getMap().get("bs"));
				GuaranteeBusiInfoW5Info info = data.get("固定资产融资");
				if (info != null) {
					info.setZbbsEnding(NumberUtil.parseLong(bs));
					info.setZbhsEnding(NumberUtil.parseLong(hs));
					info.setBalanceEnding(toMoney(itm.getMap()
						.get("fixed_assets_financing_balance")));
				}
			}
		}
		//期初
		queyOrder.setSql(sql.replaceAll("\\$\\{reportMonthEndDay\\}",
			order.getReportLastMonthEndDay()));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String hs = String.valueOf(itm.getMap().get("hs"));
				String bs = String.valueOf(itm.getMap().get("bs"));
				GuaranteeBusiInfoW5Info info = data.get("固定资产融资");
				if (info != null) {
					info.setZbbsBeginning(NumberUtil.parseLong(bs));
					info.setZbhsBeginning(NumberUtil.parseLong(hs));
					info.setBalanceBeginning(toMoney(itm.getMap().get(
						"fixed_assets_financing_balance")));
				}
			}
		}
		
		//data.put("承兑汇票担保", new GuaranteeBusiInfoW5Info("busiType", "企业业务"));
		sql = "SELECT '承兑汇票担保' classify ,COUNT(DISTINCT p.customer_id) hs, COUNT(*) bs, SUM(ba.acceptance_bill_balance) acceptance_bill_balance FROM ${pmDbTitle}.project_data_info p JOIN (SELECT occer.project_code, (occer.acceptance_bill - IFNULL(repay.acceptance_bill, 0)) acceptance_bill_balance FROM (SELECT project_code, SUM(acceptance_bill_amount) acceptance_bill FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date <= '${reportMonthEndDay}' GROUP BY project_code) occer LEFT JOIN (SELECT project_code, SUM(acceptance_bill_amount) acceptance_bill FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_confirm_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE acceptance_bill_balance > 0 AND (p.busi_type LIKE '11%')";
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		
		//期末
		queyOrder
			.setSql(sql.replaceAll("\\$\\{reportMonthEndDay\\}", order.getReportMonthEndDay()));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String hs = String.valueOf(itm.getMap().get("hs"));
				String bs = String.valueOf(itm.getMap().get("bs"));
				GuaranteeBusiInfoW5Info info = data.get("承兑汇票担保");
				if (info != null) {
					info.setZbbsEnding(NumberUtil.parseLong(bs));
					info.setZbhsEnding(NumberUtil.parseLong(hs));
					info.setBalanceEnding(toMoney(itm.getMap().get("acceptance_bill_balance")));
				}
			}
		}
		//期初
		queyOrder.setSql(sql.replaceAll("\\$\\{reportMonthEndDay\\}",
			order.getReportLastMonthEndDay()));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String hs = String.valueOf(itm.getMap().get("hs"));
				String bs = String.valueOf(itm.getMap().get("bs"));
				GuaranteeBusiInfoW5Info info = data.get("承兑汇票担保");
				if (info != null) {
					info.setZbbsBeginning(NumberUtil.parseLong(bs));
					info.setZbhsBeginning(NumberUtil.parseLong(hs));
					info.setBalanceBeginning(toMoney(itm.getMap().get("acceptance_bill_balance")));
				}
			}
		}
		
		//data.put("信用证担保", new GuaranteeBusiInfoW5Info("busiType", "企业业务"));
		sql = "SELECT '信用证担保' classify ,COUNT(DISTINCT p.customer_id) hs, COUNT(*) bs, SUM(ba.credit_letter_balance) credit_letter_balance FROM ${pmDbTitle}.project_data_info p JOIN (SELECT occer.project_code, (occer.credit_letter - IFNULL(repay.credit_letter, 0)) credit_letter_balance FROM (SELECT project_code, SUM(credit_letter_amount) credit_letter FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date <= '${reportMonthEndDay}' GROUP BY project_code) occer LEFT JOIN (SELECT project_code, SUM(credit_letter_amount) credit_letter FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_confirm_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE credit_letter_balance > 0 AND (p.busi_type LIKE '11%')";
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		
		//期末
		queyOrder
			.setSql(sql.replaceAll("\\$\\{reportMonthEndDay\\}", order.getReportMonthEndDay()));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String hs = String.valueOf(itm.getMap().get("hs"));
				String bs = String.valueOf(itm.getMap().get("bs"));
				GuaranteeBusiInfoW5Info info = data.get("信用证担保");
				if (info != null) {
					info.setZbbsEnding(NumberUtil.parseLong(bs));
					info.setZbhsEnding(NumberUtil.parseLong(hs));
					info.setBalanceEnding(toMoney(itm.getMap().get("credit_letter_balance")));
				}
			}
		}
		//期初
		queyOrder.setSql(sql.replaceAll("\\$\\{reportMonthEndDay\\}",
			order.getReportLastMonthEndDay()));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				String hs = String.valueOf(itm.getMap().get("hs"));
				String bs = String.valueOf(itm.getMap().get("bs"));
				GuaranteeBusiInfoW5Info info = data.get("信用证担保");
				if (info != null) {
					info.setZbbsBeginning(NumberUtil.parseLong(bs));
					info.setZbhsBeginning(NumberUtil.parseLong(hs));
					info.setBalanceBeginning(toMoney(itm.getMap().get("credit_letter_balance")));
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
		
		String sql = "SELECT CASE WHEN p.customer_type = 'PERSIONAL' THEN '个人业务' WHEN p.busi_type LIKE '12%' THEN '债券类担保（包括短期融资券、债券担保等）'  WHEN p.busi_type LIKE '2%' THEN '企业业务' ELSE '其他' END classify, SUM(repay.re_amount) repay_amount FROM ${pmDbTitle}.project_data_info p "
						+ "JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保','诉保解保' ,'代偿') AND repay_confirm_time >= '${reportMonthStartDay}' AND repay_confirm_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON p.project_code = repay.project_code WHERE ( p.busi_type like '12%' or p.busi_type like '13%' or p.busi_type like '2%' or p.busi_type like '3%') GROUP BY classify ";
		
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
		
		//按照业务细分类查询  
		//data.put("流动资金贷款", new GuaranteeBusiInfoW5Info("busiType", "企业业务"));
		sql = "SELECT '${classify}' classify ,SUM(repay.re_amount) repay_amount FROM ${pmDbTitle}.project_data_info p JOIN (SELECT project_code, SUM(${amountColumn}) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_confirm_time >= '${reportMonthStartDay}' AND repay_confirm_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON p.project_code = repay.project_code WHERE (p.busi_type LIKE '11%')";
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		sql = sql.replaceAll("\\$\\{reportMonthStartDay\\}", order.getReportMonthStartDay());
		sql = sql.replaceAll("\\$\\{reportMonthEndDay\\}", order.getReportMonthEndDay());
		
		queyOrder.setSql(sql.replaceAll("\\$\\{classify\\}", "流动资金贷款").replaceAll(
			"\\$\\{amountColumn\\}", "liquidity_loan_amount"));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				GuaranteeBusiInfoW5Info info = data.get("流动资金贷款");
				if (info != null) {
					info.setDecrease(toMoney(itm.getMap().get("repay_amount")));
				}
			}
		}
		
		//data.put("固定资产融资", new GuaranteeBusiInfoW5Info("busiType", "企业业务"));
		queyOrder.setSql(sql.replaceAll("\\$\\{classify\\}", "固定资产融资").replaceAll(
			"\\$\\{amountColumn\\}", "fixed_assets_financing_amount"));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				GuaranteeBusiInfoW5Info info = data.get("固定资产融资");
				if (info != null) {
					info.setDecrease(toMoney(itm.getMap().get("repay_amount")));
					
				}
			}
		}
		
		//data.put("承兑汇票担保", new GuaranteeBusiInfoW5Info("busiType", "企业业务"));
		queyOrder.setSql(sql.replaceAll("\\$\\{classify\\}", "承兑汇票担保").replaceAll(
			"\\$\\{amountColumn\\}", "acceptance_bill_amount"));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				GuaranteeBusiInfoW5Info info = data.get("承兑汇票担保");
				if (info != null) {
					info.setDecrease(toMoney(itm.getMap().get("repay_amount")));
					
				}
			}
		}
		
		//data.put("信用证担保", new GuaranteeBusiInfoW5Info("busiType", "企业业务"));
		queyOrder.setSql(sql.replaceAll("\\$\\{classify\\}", "信用证担保").replaceAll(
			"\\$\\{amountColumn\\}", "credit_letter_amount"));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				GuaranteeBusiInfoW5Info info = data.get("信用证担保");
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
		
		String sql = "SELECT CASE WHEN p.customer_type = 'PERSIONAL' THEN '个人业务' WHEN p.busi_type LIKE '12%' THEN '债券类担保（包括短期融资券、债券担保等）' WHEN p.busi_type LIKE '2%' THEN '企业业务' ELSE '其他' END classify, COUNT(DISTINCT p.customer_id) hs, COUNT(*) bs FROM ${pmDbTitle}.project_data_info p "
						+ "JOIN (SELECT project_code, occur_amount FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date >= '${reportMonthStartDay}' AND  occur_date <= '${reportMonthEndDay}' GROUP BY project_code) this_month ON p.project_code = this_month.project_code "
						+ "LEFT JOIN (SELECT project_code, occur_amount FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date <= '${lastMonthEndDay}' GROUP BY project_code) last_month ON this_month.project_code = last_month.project_code WHERE this_month.occur_amount > 0 AND (last_month.occur_amount = 0 OR last_month.occur_amount IS NULL) AND ( p.busi_type like '12%' or p.busi_type like '13%' or p.busi_type like '2%' or p.busi_type like '3%') GROUP BY classify";
		
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
		
		//按照业务细分类查询  
		sql = "SELECT '${classify}' classify, COUNT(DISTINCT p.customer_id) hs, COUNT(*) bs FROM ${pmDbTitle}.project_data_info p "
				+ "JOIN (SELECT project_code, ${amountColumn} occur_amount FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date >= '${reportMonthStartDay}' AND occur_date <= '${reportMonthEndDay}' GROUP BY project_code) this_month ON p.project_code = this_month.project_code "
				+ "LEFT JOIN (SELECT project_code,  ${amountColumn}  occur_amount FROM ${pmDbTitle}.view_project_occer_detail WHERE occur_date <= '${lastMonthEndDay}' GROUP BY project_code) last_month ON this_month.project_code = last_month.project_code WHERE this_month.occur_amount > 0 AND (last_month.occur_amount = 0 OR last_month.occur_amount IS NULL) AND (p.busi_type LIKE '11%')";
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		sql = sql.replaceAll("\\$\\{reportMonthStartDay\\}", order.getReportMonthStartDay());
		sql = sql.replaceAll("\\$\\{reportMonthEndDay\\}", order.getReportMonthEndDay());
		sql = sql.replaceAll("\\$\\{lastMonthEndDay\\}", order.getReportLastMonthEndDay());
		
		//data.put("流动资金贷款", new GuaranteeBusiInfoW5Info("busiType", "企业业务"));
		
		queyOrder.setSql(sql.replaceAll("\\$\\{classify\\}", "流动资金贷款").replaceAll(
			"\\$\\{amountColumn\\}", "liquidity_loan_amount"));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				GuaranteeBusiInfoW5Info info = data.get("流动资金贷款");
				String bs = String.valueOf(itm.getMap().get("bs"));
				String hs = String.valueOf(itm.getMap().get("hs"));
				if (info != null) {
					info.setBs(NumberUtil.parseLong(bs));
					info.setHs(NumberUtil.parseLong(hs));
				}
			}
		}
		
		//data.put("固定资产融资", new GuaranteeBusiInfoW5Info("busiType", "企业业务"));
		queyOrder.setSql(sql.replaceAll("\\$\\{classify\\}", "固定资产融资").replaceAll(
			"\\$\\{amountColumn\\}", "fixed_assets_financing_amount"));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				GuaranteeBusiInfoW5Info info = data.get("固定资产融资");
				String bs = String.valueOf(itm.getMap().get("bs"));
				String hs = String.valueOf(itm.getMap().get("hs"));
				if (info != null) {
					info.setBs(NumberUtil.parseLong(bs));
					info.setHs(NumberUtil.parseLong(hs));
				}
			}
		}
		
		//data.put("承兑汇票担保", new GuaranteeBusiInfoW5Info("busiType", "企业业务"));
		queyOrder.setSql(sql.replaceAll("\\$\\{classify\\}", "承兑汇票担保").replaceAll(
			"\\$\\{amountColumn\\}", "acceptance_bill_amount"));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				GuaranteeBusiInfoW5Info info = data.get("承兑汇票担保");
				String bs = String.valueOf(itm.getMap().get("bs"));
				String hs = String.valueOf(itm.getMap().get("hs"));
				if (info != null) {
					info.setBs(NumberUtil.parseLong(bs));
					info.setHs(NumberUtil.parseLong(hs));
				}
			}
		}
		
		//data.put("信用证担保", new GuaranteeBusiInfoW5Info("busiType", "企业业务"));
		queyOrder.setSql(sql.replaceAll("\\$\\{classify\\}", "信用证担保").replaceAll(
			"\\$\\{amountColumn\\}", "credit_letter_amount"));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				GuaranteeBusiInfoW5Info info = data.get("信用证担保");
				String bs = String.valueOf(itm.getMap().get("bs"));
				String hs = String.valueOf(itm.getMap().get("hs"));
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
