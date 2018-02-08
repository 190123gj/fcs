package com.born.fcs.rm.biz.service.report.outer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import com.born.fcs.rm.biz.service.report.AccountBalanceHelper;
import com.born.fcs.rm.integration.service.pm.PmReportServiceClient;
import com.born.fcs.rm.integration.service.pm.ToReportServiceClient;
import com.born.fcs.rm.ws.enums.ReportStatusEnum;
import com.born.fcs.rm.ws.enums.SubmissionCodeEnum;
import com.born.fcs.rm.ws.info.accountbalance.AccountBalanceDataInfo;
import com.born.fcs.rm.ws.info.submission.SubmissionDataInfo;
import com.born.fcs.rm.ws.info.submission.SubmissionInfo;
import com.born.fcs.rm.ws.order.accountbalance.AccountBalanceDataQueryOrder;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.order.submission.SubmissionQueryOrder;
import com.born.fcs.rm.ws.service.accountbalance.AccountBalanceService;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.born.fcs.rm.ws.service.submission.SubmissionService;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

/**
 *
 * 外部报表<br />
 * 市金融办/月季报<br />
 * （市金融办）融资性担保公司经营情况表
 *
 * @author jil
 *
 * 2016年12月22日 10:50:00
 */
@Service("guaranteeOperateInfoService")
public class GuaranteeOperateInfoServiceImpl extends BaseAutowiredDomainService implements
																				ReportProcessService {
	
	@Autowired
	private SubmissionService submissionService;
	
	@Autowired
	private AccountBalanceService accountBalanceService;
	@Autowired
	private PmReportServiceClient pmReportServiceClient;
	@Autowired
	ToReportServiceClient toReportServiceClient;
	
	@Override
	public FcsBaseResult save(ReportOrder order) {
		return null;
	}
	
	@Override
	public Object findByAccountPeriod(ReportQueryOrder queryOrder) {
		String hasAllData = "IS";
		QueryBaseBatchResult<Money> batchResult = new QueryBaseBatchResult<>();
		List<Money> lists = new ArrayList<>();
		//		int months = quarterLastMonths(queryOrder.getReportMonth());//这里传季度
		int months = queryOrder.getReportMonth();
		int reportYear = queryOrder.getReportYear();
		//		List<String> lists1 = accountBalance(queryOrder.getReportYear(), months[0]);
		//		List<String> lists2 = accountBalance(queryOrder.getReportYear(), months[1]);
		List<String> listsNowYear = accountBalance(reportYear, months, 1, reportYear, months);//当前年季度末
		for (int i = 0; i < listsNowYear.size() - 1; i++) {
			lists.add(new Money(NumberUtil.parseDouble(listsNowYear.get(i))));
			//			lists.add(toMoneyY(listsNowYear.get(i)));
			//			lists.add(MoneyUtil.getMoneyByy2(new Money(listsNowYear.get(i))));
		}
		if ("IS".equals(hasAllData)) {
			hasAllData = listsNowYear.get(22);
		}
		List<String> listsLastYear = accountBalance(reportYear - 1, months, 2, reportYear, months);//上年季度末
		for (int i = 0; i < listsLastYear.size() - 1; i++) {//同比增长
			if (!"0.00".equals((String) listsLastYear.get(i))) {
				
				if (i >= 13 && i <= 17) {//直接取上报数据
					lists.add(new Money(listsLastYear.get(i)));
				} else {
					
					Double now = NumberUtil.parseDouble(listsNowYear.get(i));
					Double last = NumberUtil.parseDouble(listsLastYear.get(i));
					
					Double temp = (now - last) / last * 100;
					lists.add(new Money(temp));
				}
				
			} else {
				lists.add(ZERO_MONEY);
			}
		}
		if ("IS".equals(hasAllData)) {
			hasAllData = listsLastYear.get(22);
		}
		List<String> listsLastYearEnd = accountBalance(reportYear - 1, 12, 3, reportYear, months);//上年末
		for (int i = 0; i < listsLastYearEnd.size() - 1; i++) {//比年初增长
			if (!"0.00".equals((String) listsLastYearEnd.get(i))) {
				if (i >= 13 && i <= 17) {//直接取上报数据
					lists.add(new Money(listsLastYearEnd.get(i)));
				} else {
					
					Double now = NumberUtil.parseDouble(listsNowYear.get(i));
					Double last = NumberUtil.parseDouble(listsLastYearEnd.get(i));
					
					Double temp = (now - last) / last * 100;
					
					lists.add(new Money(temp));
				}
			} else {
				lists.add(ZERO_MONEY);
			}
		}
		if ("IS".equals(hasAllData)) {
			hasAllData = listsLastYearEnd.get(22);
		}
		batchResult.setPageList(lists);
		batchResult.setSuccess(true);
		batchResult.setSortCol(hasAllData);
		return batchResult;
	}
	
	private List<String> accountBalance(int year, int month, int mark, int thisYear, int thisMonth) {//thisYear thisMonth代表查询当前上报年月
		String hasAllData = "IS";
		List<String> lists = new ArrayList<String>(13);
		//科目余额表的数据 
		AccountBalanceDataQueryOrder abQueryOrder = new AccountBalanceDataQueryOrder();
		abQueryOrder.setReportYear(year);
		abQueryOrder.setReportMonth(month);
		List<AccountBalanceDataInfo> list = accountBalanceService.queryDatas(abQueryOrder);
		AccountBalanceHelper helper = null;
		Money m3 = ZERO_MONEY;
		Money m2 = ZERO_MONEY;
		if (ListUtil.isNotEmpty(list)) {
			helper = new AccountBalanceHelper(list, "1", "2", "4", "6");
			//注册资金:4001期末贷方余额
			Money m1 = helper.caculateEndingCredit("4001");
			lists.add(m1.toString());
			//总资产: 1开头的借方-贷方，加和
			m2 = helper.caculateCapital();
			lists.add(m2.toString());
			//净资产:表内1减2
			
			Money capital = helper.caculateCapital();
			Money debt = helper.caculateDebt();
			//19.3. 净资产			表内1减2
			//			lists.add(capital.subtract(debt).toString());
			m3 = capital.subtract(debt);
			lists.add(m3.toString());
		} else {
			hasAllData = "NO";
			for (int i = 0; i < 3; i++) {
				lists.add(ZERO_MONEY.toString());
			}
		}
		//担保发生额:所有担保业务累计发生额合计
		ReportQueryOrder order = new ReportQueryOrder();
		order.setReportYear(year);
		order.setReportMonth(month);
		lists.add(occurAmount(order).toString());
		//在保余额:当期系统系统所有担保业务在保余额合计}
		Money balanceAmount = balanceAmount(order);
		lists.add(balanceAmount.toString());
		
		//			//净资产
		//			Money assets = helper.caculateCapital().subtract(helper.caculateDebt());
		
		//放大倍数（*）:在保余额/净资产（W2表序号19）
		if (!m3.equals(ZERO_MONEY)) {
			lists.add(balanceAmount.divideBy(m3.getAmount()).toString());
		} else {
			lists.add(ZERO_MONEY.toString());
		}
		//净资产收益率（*）:净利润（W3序号18）/净资产（W2表序号19）
		//净利润
		if (helper != null) {
			Money profit = helper.caculateYearCredit("6031")
				.subtract(helper.caculateYearDebit("6403"))
				.subtract(helper.caculateYearDebit("6501"))
				.subtract(helper.caculateYearDebit("6502"))
				.add(helper.caculateYearCredit("6103").subtract(helper.caculateYearDebit("6421")))
				.add(helper.caculateYearCredit("6051")).subtract(helper.caculateYearDebit("6601"))
				.add(helper.caculateYearCredit("6111")).subtract(helper.caculateYearDebit("6701"))
				.add(helper.caculateYearCredit("6301").subtract(helper.caculateYearDebit("6711")))
				.subtract(helper.caculateYearDebit("6801"));
			
			if (!m3.equals(ZERO_MONEY)) {
				lists.add(profit.divideBy(m3.getAmount()).toString());
			} else {
				lists.add(ZERO_MONEY.toString());
			}
			//银行存款:1002期末借方
			Money f1 = helper.caculateEndingDebit("1002");
			lists.add(f1.toString());
			//固定资产:W2序号7
			Money f2 = helper.caculateEndingDebit("1601").subtract(
				helper.caculateEndingCredit("1602"));
			lists.add(f2.toString());
			//存出保证金:1031.01期末借方
			Money f3 = helper.caculateEndingDebit("1031.01");
			lists.add(f3.toString());
			//委托贷款余额:1321期末借方
			Money f4 = helper.caculateEndingDebit("1321");
			lists.add(f4.toString());
			//对外投资余额:不含委贷 --不知道取值
			//			Money f5 = helper.caculateEndingDebit("1501");
			Money f5 = ZERO_MONEY;
			lists.add(f5.toString());
			//其他:总资产-上面各分项
			lists.add(m2.subtract(f1).subtract(f2).subtract(f3).subtract(f4).subtract(f5)
				.toString());
			//风险控制--本年累计代偿
		} else {
			hasAllData = "NO";
			for (int i = 0; i < 7; i++) {
				lists.add(ZERO_MONEY.toString());
			}
		}
		//来自数据报送：融资性担保公司经营情况表
		SubmissionQueryOrder submissionQueryOrder1 = new SubmissionQueryOrder();
		submissionQueryOrder1.setReportCode(SubmissionCodeEnum.RZXDBGSNMJYQKB.code());
		submissionQueryOrder1.setReportYear(thisYear);
		submissionQueryOrder1.setReportMonth(thisMonth);
		List<String> statusList1 = Lists.newArrayList();
		statusList1.add(ReportStatusEnum.SUBMITTED.code());
		statusList1.add(ReportStatusEnum.IN_USE.code());
		submissionQueryOrder1.setStatusList(statusList1);
		submissionQueryOrder1.setPageNumber(1L);
		submissionQueryOrder1.setPageSize(1L);
		QueryBaseBatchResult<SubmissionInfo> batches1 = submissionService
			.query(submissionQueryOrder1);
		if (null != batches1 && ListUtil.isNotEmpty(batches1.getPageList())) {
			SubmissionInfo submission = batches1.getPageList().get(0);
			submission = submissionService.findById(submission.getSubmissionId());
			if (null != submission && ListUtil.isNotEmpty(submission.getData())) {
				List<SubmissionDataInfo> data = submission.getData();
				if (data.size() >= 1) {
					if (mark == 1) {
						lists.add(data.get(0).getData3());
						lists.add(data.get(1).getData3());
						lists.add(data.get(2).getData3());
						lists.add(data.get(3).getData3());
						lists.add(data.get(4).getData3());
					} else if (mark == 2) {
						lists.add(data.get(0).getData4());
						lists.add(data.get(1).getData4());
						lists.add(data.get(2).getData4());
						lists.add(data.get(3).getData4());
						lists.add(data.get(4).getData4());
					} else {
						lists.add(data.get(0).getData5());
						lists.add(data.get(1).getData5());
						lists.add(data.get(2).getData5());
						lists.add(data.get(3).getData5());
						lists.add(data.get(4).getData5());
					}
				}
			}
		} else {
			lists.add(ZERO_MONEY.toString());
			lists.add(ZERO_MONEY.toString());
			lists.add(ZERO_MONEY.toString());
			lists.add(ZERO_MONEY.toString());
			lists.add(ZERO_MONEY.toString());
			hasAllData = "NO";
		}
		
		//准备金余额：2602期末借方
		if (helper != null) {
			lists.add(helper.caculateEndingCredit("2602").toString());
		} else {
			lists.add(ZERO_MONEY.toString());
		}
		//拨备覆盖率（*）： W6表6.1/6.2
		Money guaranteeCompAmount = ZERO_MONEY;//6.2 担保代偿金额
		//来自数据报送：融资性担保机构基本情况
		SubmissionQueryOrder submissionQueryOrder = new SubmissionQueryOrder();
		submissionQueryOrder.setReportCode(SubmissionCodeEnum.RZXDBJGFXZB.code());
		submissionQueryOrder.setReportYear(year);
		submissionQueryOrder.setReportMonth(month);
		List<String> statusList = Lists.newArrayList();
		statusList.add(ReportStatusEnum.SUBMITTED.code());
		statusList.add(ReportStatusEnum.IN_USE.code());
		submissionQueryOrder.setStatusList(statusList);
		submissionQueryOrder.setPageNumber(1L);
		submissionQueryOrder.setPageSize(1L);
		QueryBaseBatchResult<SubmissionInfo> batches = submissionService
			.query(submissionQueryOrder);
		if (null != batches && ListUtil.isNotEmpty(batches.getPageList())) {
			SubmissionInfo submission = batches.getPageList().get(0);
			submission = submissionService.findById(submission.getSubmissionId());
			if (null != submission && ListUtil.isNotEmpty(submission.getData())) {
				List<SubmissionDataInfo> data = submission.getData();
				if (data.size() >= 1) {
					guaranteeCompAmount = Money.amout(String.valueOf(data.get(23).getData5()))
						.multiply(10000);//因为那边的单位是万元
				}
			}
		} else {
			hasAllData = "NO";
		}
		if (!guaranteeCompAmount.equals(ZERO_MONEY) && helper != null) {
			//				Money m11 = helper.caculateEndingCredit("2602", "4102");
			//				BigDecimal b1 = guaranteeCompAmount.getAmount();
			lists.add((helper.caculateEndingCredit("2602", "4102").divide(guaranteeCompAmount
				.getAmount())).toString());
		} else {
			lists.add(ZERO_MONEY.toString());
		}
		//本年累计发生额：关联了该渠道的项目的本年累计发生额
		List<Money> amount = channel(order);
		
		lists.add(amount.get(0).toString());//本年累计发生额
		lists.add(amount.get(1).toString());//期末在保余额
		//余额：关联了该渠道的项目的在保余额
		lists.add(hasAllData);
		return lists;
	}
	
	/**
	 * 本季度担保发生额
	 * @param order
	 * @param data
	 */
	private Money occurAmount(ReportQueryOrder order) {
		Money thisMonthTotal = Money.zero();
		//		String sql = "SELECT SUM(r.actual_amount) amount "
		//						+ "FROM ${pmDbTitle}.f_loan_use_apply_receipt r JOIN ${pmDbTitle}.project p ON r.project_code = p.project_code "
		//						+ "WHERE (p.busi_type like '1%' or (p.busi_type like '2%' and p.busi_type!='211') or p.busi_type like '3%') AND r.apply_type IN ('BOTH', 'LOAN') "
		//						+ "AND r.actual_loan_time >= '${loanStartTime}' AND actual_loan_time <= '${loanEndTime}'";
		
		String sql = "SELECT SUM(occur_amount) AS occurAmount FROM ${pmDbTitle}.view_project_actual_occer_detail WHERE occur_type IN ('诉保', '放款', '发售')AND occur_date >= '${loanStartTime}' AND occur_date <= '${loanEndTime}' AND (busi_type like '1%' or busi_type like '2%' or busi_type like '3%') ";
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		
		//本季度sql
		sql = sql.replaceAll(
			"\\$\\{loanStartTime\\}",
			DateUtil.dtSimpleFormat(order.getQuarterStartTimeByYearAndMonth(order.getReportYear(),
				order.getReportMonth()))).replaceAll(
			"\\$\\{loanEndTime\\}",
			DateUtil.dtSimpleFormat(order.getQuarterEndTimeByYearAndMonth(order.getReportYear(),
				order.getReportMonth())));
		
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		
		//查询
		queyOrder.setSql(sql);
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				Money amount = toMoney(itm.getMap().get("occurAmount") == null ? "0" : itm.getMap()
					.get("occurAmount"));
				thisMonthTotal.addTo(amount);
			}
		}
		return thisMonthTotal;
	}
	
	/**
	 * 本季度担保业务在保余额
	 * @param order
	 * @param data
	 */
	private Money balanceAmount(ReportQueryOrder order) {
		
		String sql = "";
		sql += "SELECT SUM(ba.balance) balance FROM ${pmDbTitle}.project_data_info p "
				+ "	JOIN (SELECT occer.project_code, (oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_actual_occer_detail WHERE occur_date <= '${reportMonthEndDay}' GROUP BY project_code) occer "
				+ "LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE ba.balance > 0 AND ( p.busi_type like '1%' or p.busi_type like '2%' or p.busi_type like '3%') ";
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder
			.setSql(sql.replaceAll("\\$\\{reportMonthEndDay\\}", order.getReportMonthEndDay()));
		
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		Money endTotal = Money.zero();
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				Money amount = toMoney(itm.getMap().get("balance") == null ? "0" : itm.getMap()
					.get("balance"));
				endTotal.addTo(amount);
			}
		}
		
		return endTotal;
	}
	
	/**
	 * 互联网担保--渠道G开头
	 * @param order
	 * @param data
	 */
	private List<Money> channel(ReportQueryOrder order) {
		List<Money> amount = Lists.newArrayList();
		//		String tableName = " project_channel_relation_his ";
		//		String projectDateSql = "";
		//		if (isThisYearMonth(order.getReportYear(), order.getReportMonth())) {
		//			tableName = " project_channel_relation ";
		//		} else {
		//			//			projectDateSql = "  AND project_date= '"
		//			//								+ DateUtil.dtSimpleFormat(DateUtil.getEndTimeByYearAndMonth(
		//			//									order.getReportYear(), order.getReportMonth())) + "'";
		//			
		//			projectDateSql = "  AND project_date= '" + order.getReportMonthEndDay() + "'";
		//			
		//		}
		//		//本期时间段
		//		String startTime = DateUtil.simpleFormat(DateUtil.getStartTimeByYearAndMonth(
		//			order.getReportYear(), order.getReportMonth()));
		//		String endTime = DateUtil.simpleFormat(DateUtil.getEndTimeByYearAndMonth(
		//			order.getReportYear(), order.getReportMonth()));
		String lastYearEndDay = getCurrYearLast(order.getReportYear() - 1);//去年最后一天
		
		String monthEndDay = getYearMonthLast(order.getReportYear(), order.getReportMonth());
		String tempSql = "";
		
		// 期末在保余额
		//		tempSql += "SELECT SUM(ba.balance) AS count9 FROM tableName p"
		//					+ "	JOIN (SELECT occer.project_code, (oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_actual_occer_detail WHERE occur_date <= '${reportMonthEndDay}' GROUP BY project_code) occer "
		//					+ "LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code "
		//					+ " WHERE ba.balance > 0 AND channel_type IN ('WLJR') projectDateSql AND latest = 'YES' AND channel_relation = 'CAPITAL_CHANNEL' ";
		
		tempSql += "SELECT SUM(ba.balance) balance FROM ${pmDbTitle}.project_data_info p "
					+ "	JOIN (SELECT occer.project_code, (oc_amount - IFNULL(re_amount, 0)) balance,capital_channel_type FROM (SELECT project_code, SUM(occur_amount) oc_amount,capital_channel_type FROM ${pmDbTitle}.view_project_actual_occer_detail WHERE occur_date <= '${reportMonthEndDay}' GROUP BY project_code) occer "
					+ "LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE ba.balance > 0 AND ba.capital_channel_type  IN ('WLJR')";
		//本年累计发生额
		//		tempSql += "&SELECT SUM(actual_amount) count7 FROM f_loan_use_apply_receipt WHERE capital_channel_type IN ('WLJR') AND actual_loan_time > 'yearBeginDay' AND actual_loan_time <= 'thisEndDay' GROUP BY capital_channel_type ";
		tempSql += "&SELECT SUM(occur_amount) AS occurAmount FROM ${pmDbTitle}.view_project_actual_occer_detail WHERE occur_type IN ('诉保', '放款', '发售') AND occur_date > 'yearBeginDay' AND occur_date <= 'thisEndDay' AND capital_channel_type IN ('WLJR')";
		
		tempSql = tempSql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		//参数替换
		tempSql = tempSql.replaceAll("yearBeginDay", lastYearEndDay).replaceAll("thisEndDay",
			monthEndDay);
		tempSql = tempSql.replaceAll("\\$\\{reportMonthEndDay\\}", order.getReportMonthEndDay());
		Money total1 = Money.zero();
		Money total2 = Money.zero();
		String[] sql = tempSql.split("&");
		for (String s : sql) {
			
			PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
			HashMap<String, FcsField> fieldMap = new HashMap<>();
			queyOrder.setFieldMap(fieldMap);
			
			//查询
			queyOrder.setSql(s);
			logger.info("渠道分类互联网担保--渠道G开头查询 sql={}", s);
			List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
			
			if (dataResult != null) {
				for (DataListItem itm : dataResult) {
					total1.addTo(toMoney(itm.getMap().get("occurAmount") == null ? "0" : itm
						.getMap().get("occurAmount")));
					
					total2.addTo(toMoney(itm.getMap().get("balance") == null ? "0" : itm.getMap()
						.get("balance")));
					
				}
			}
		}
		amount.add(total1);
		amount.add(total2);
		return amount;
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
	private Money toMoneyY(Object wan) {
		try {
			String s = String.valueOf(wan);
			return Money.amout(s).multiply(100000000);
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
	
	private int quarterLastMonths(int month) {
		int quarterLastMonths = 12;
		if (month == 1 || month == 2 || month == 3) {//1季度
			quarterLastMonths = 3;
		}
		if (month == 4 || month == 5 || month == 6) {//2季度
			quarterLastMonths = 6;
		}
		if (month == 7 || month == 8 || month == 9) {//3季度
			quarterLastMonths = 9;
		}
		if (month == 10 || month == 11 || month == 12) {//4季度
			quarterLastMonths = 12;
		}
		return quarterLastMonths;
	}
	
	/** 查询的是否本月 */
	public boolean isNowMoth(int year1, int month1) {
		Date time = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(time);
		
		int year = now.get(Calendar.YEAR);
		int moth = now.get(Calendar.MONTH) + 1;
		return year == year1 && moth == month1;
	}
	
	/** 获取该年最后一天 */
	public String getCurrYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();
		
		return DateUtil.dtSimpleFormat(currYearLast);
	}
	
	/** 获取指定年月最后一天 */
	public String getYearMonthLast(int year, int mounth) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, mounth - 1);
		calendar.roll(Calendar.DAY_OF_MONTH, -1);
		Date currYearLast = calendar.getTime();
		
		return DateUtil.dtSimpleFormat(currYearLast);
	}
	
	/** 查询的当前年月 是否指定年月 */
	public boolean isThisYearMonth(int reportYear, int reportMonth) {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int moth = now.get(Calendar.MONTH) + 1;
		return year == reportYear && moth == reportMonth;
	}
}
