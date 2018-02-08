package com.born.fcs.rm.biz.service.report.outer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.biz.service.report.AccountBalanceHelper;
import com.born.fcs.rm.integration.service.pm.PmReportServiceClient;
import com.born.fcs.rm.ws.info.accountbalance.AccountBalanceDataInfo;
import com.born.fcs.rm.ws.order.accountbalance.AccountBalanceDataQueryOrder;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.service.accountbalance.AccountBalanceService;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.born.fcs.rm.ws.service.submission.SubmissionService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 *
 * 外部报表<br />
 * （W13-(市统计局)新型金融业主要统计指标表
 *
 * @author jil
 *
 * 2016年12月19日 15:15:42
 */
@Service("financialIndustryMainIndexService")
public class FinancialIndustryMainIndexServiceImpl extends BaseAutowiredDomainService implements
																						ReportProcessService {
	@Autowired
	private AccountBalanceService accountBalanceService;
	@Autowired
	private SubmissionService submissionService;
	@Autowired
	private PmReportServiceClient pmReportServiceClient;
	
	@Override
	public FcsBaseResult save(ReportOrder order) {
		return null;
	}
	
	@Override
	public Object findByAccountPeriod(ReportQueryOrder queryOrder) {
		
		QueryBaseBatchResult<Money> batchResult = new QueryBaseBatchResult<>();
		List<Money> lists = new ArrayList<>();
		int months = queryOrder.getReportMonth();//这里传季度
		AccountBalanceDataQueryOrder abQueryOrder = new AccountBalanceDataQueryOrder();
		abQueryOrder.setReportYear(queryOrder.getReportYear());
		abQueryOrder.setReportMonth(months);
		List<Money> ends = new ArrayList<>();
		List<AccountBalanceDataInfo> list = accountBalanceService.queryDatas(abQueryOrder);
		if (ListUtil.isNotEmpty(list)) {
			AccountBalanceHelper helper = new AccountBalanceHelper(list, "1", "2", "6");
			//1.资产总额	1开头的借方-贷方，加和
			Money m1 = helper.caculateCapital();
			ends.add(m1);
			//2开头的所有贷方-借方
			Money m2 = helper.caculateDebt();
			ends.add(m2);
			//W8 担保机构总收入6031+6051+6103+6111+6301+6021+6071+6072+6073
			Money m3 = helper.caculateGuarantorsTotalIncome("6031", "6051", "6103", "6111", "6301",
				"6021", "6071", "6072", "6073");
			ends.add(m3);
			//W3 序号4+提取未到期责任准备+序号12+序号14+6421借方发生
			//序号4:6403借方发生累计
			//提取未到期责任准备:6501借方发生累计
			//序号12：6601借方累计
			//序号14：6701借方累计
			Money m4 = helper.caculateYearDebit("6403").add(helper.caculateYearDebit("6501"))
				.add(helper.caculateYearDebit("6601")).add(helper.caculateYearDebit("6701"))
				.add(helper.caculateCurrentDebit("6421"));
			ends.add(m4);
			
		} else {
			ends.add(ZERO_MONEY);
			ends.add(ZERO_MONEY);
			ends.add(ZERO_MONEY);
			ends.add(ZERO_MONEY);
		}
		//在保余额
		Money balanceAmount = balanceAmount(queryOrder);
		ends.add(balanceAmount);
		AccountBalanceDataQueryOrder abQueryOrder1 = new AccountBalanceDataQueryOrder();
		queryOrder.setReportYear(queryOrder.getReportYear() - 1);
		abQueryOrder1.setReportYear(queryOrder.getReportYear());//上一年
		abQueryOrder1.setReportMonth(months);
		List<AccountBalanceDataInfo> list1 = accountBalanceService.queryDatas(abQueryOrder1);
		if (ListUtil.isNotEmpty(list1)) {
			AccountBalanceHelper helper = new AccountBalanceHelper(list1, "1", "2", "6");
			//1.资产总额	1开头的借方-贷方，加和
			Money m5 = helper.caculateCapital();
			ends.add(m5);
			//2开头的所有贷方-借方
			Money m6 = helper.caculateDebt();
			ends.add(m6);
			//W8 担保机构总收入6031+6051+6103+6111+6301+6021+6071+6072+6073
			Money m7 = helper.caculateGuarantorsTotalIncome("6031", "6051", "6103", "6111", "6301",
				"6021", "6071", "6072", "6073");
			ends.add(m7);
			//W3 序号4+提取未到期责任准备+序号12+序号14+6421借方发生
			//序号4:6403借方发生累计
			//提取未到期责任准备:6501借方发生累计
			//序号12：6601借方累计
			//序号14：6701借方累计
			Money m8 = helper.caculateYearDebit("6403").add(helper.caculateYearDebit("6501"))
				.add(helper.caculateYearDebit("6601")).add(helper.caculateYearDebit("6701"))
				.add(helper.caculateCurrentDebit("6421"));
			ends.add(m8);
			
		} else {
			ends.add(ZERO_MONEY);
			ends.add(ZERO_MONEY);
			ends.add(ZERO_MONEY);
			ends.add(ZERO_MONEY);
		}
		//在保余额
		Money balanceAmount1 = balanceAmount(queryOrder);
		ends.add(balanceAmount1);
		for (Money m : ends) {
			lists.add(m.divide(1000));//单位转换
		}
		batchResult.setPageList(lists);
		batchResult.setSuccess(true);
		return batchResult;
	}
	
	/**
	 * 在保余额
	 * @param order
	 * @param data
	 */
	private Money balanceAmount(ReportQueryOrder order) {
		Money balanceAmount = ZERO_MONEY;
		//实时数据
		String sql = "SELECT SUM(blance) amount FROM ${pmDbTitle}.project_data_info "
						+ "WHERE 1=1 AND dept_id > 0";
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		
		//历史数据
		String hisSql = "SELECT SUM(blance) amount FROM ${pmDbTitle}.project_data_info_his_data "
						+ "WHERE dept_id > 0 AND project_date='${projectDate}'";
		hisSql = hisSql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		
		Date quarterStartTime = order.getQuarterStartTimeByYearAndMonth(order.getReportYear(),
			order.getReportMonth());//指定年月的季度开始时间
		
		Date quarterEndTime = order.getQuarterEndTimeByYearAndMonth(order.getReportYear(),
			order.getReportMonth());//指定年月的季度结束时间
		String strDate1 = DateUtil.dtSimpleSlashFormat(quarterStartTime);
		String strDate2 = DateUtil.dtSimpleSlashFormat(quarterEndTime);
		if (DateUtil.compareToNow(quarterStartTime) <= 0
			&& DateUtil.compareToNow(quarterEndTime) >= 0) {//当前时间 在指定年月  季度之内 
			//			queyOrder.setSql(hisSql.replaceAll("\\$\\{projectDate\\}",
			//				DateUtil.dtSimpleSlashFormat(new Date())));
			
			queyOrder.setSql(sql);
		} else if (DateUtil.compareToNow(quarterEndTime) < 0
					&& DateUtil.compareToNow(quarterStartTime) < 0) {//当前时间 在指定年月季度之前
			queyOrder.setSql(hisSql.replaceAll("\\$\\{projectDate\\}",
				DateUtil.dtSimpleSlashFormat(quarterEndTime)));
		}
		if (StringUtil.isNotEmpty(queyOrder.getSql())) {
			List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
			if (dataResult != null) {
				for (DataListItem itm : dataResult) {
					itm.getMap().get("amount");
					Money amount = toMoney(itm.getMap().get("amount") == null ? "0" : itm.getMap()
						.get("amount"));
					balanceAmount = amount;
				}
			}
		}
		
		return balanceAmount;
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
}
