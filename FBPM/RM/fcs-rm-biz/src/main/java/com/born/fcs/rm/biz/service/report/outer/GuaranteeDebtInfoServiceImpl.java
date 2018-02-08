package com.born.fcs.rm.biz.service.report.outer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.biz.service.report.AccountBalanceHelper;
import com.born.fcs.rm.ws.info.accountbalance.AccountBalanceDataInfo;
import com.born.fcs.rm.ws.order.accountbalance.AccountBalanceDataQueryOrder;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.service.accountbalance.AccountBalanceService;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 外部报表<br />
 * 中担协季报<br /> 
 * W2-（中担协）融资性担保机构资产负债情况
 * 
 * @author lirz
 * 
 * 2016-8-11 下午5:10:21
 */
@Service("guaranteeDebtInfoService")
public class GuaranteeDebtInfoServiceImpl extends BaseAutowiredDomainService implements
																			ReportProcessService {
	@Autowired
	private AccountBalanceService accountBalanceService;
	
	@Override
	public FcsBaseResult save(ReportOrder order) {
		return null;
	}
	
	@Override
	public Object findByAccountPeriod(ReportQueryOrder queryOrder) {
		AccountBalanceDataQueryOrder abQueryOrder = new AccountBalanceDataQueryOrder();
		abQueryOrder.setReportYear(queryOrder.getReportYear());
		abQueryOrder.setReportMonth(queryOrder.getReportMonth());
		List<AccountBalanceDataInfo> list = accountBalanceService.queryDatas(abQueryOrder);
		if (ListUtil.isNotEmpty(list)) {
			AccountBalanceHelper helper = new AccountBalanceHelper(list, "1", "2"); 
			
			List<Money> ends = new ArrayList<>();
			//1.资产总额	1开头的借方-贷方，加和
			Money capital = helper.caculateCapital();
			ends.add(capital);
			//2.其中：代币资金	1001+1002+1012 借方余额
			ends.add(helper.caculateEndingDebit("1001", "1002", "1012"));
			//3.存出保证金	1031借方余额
			ends.add(helper.caculateEndingDebit("1031"));
			//4.债权投资		1501借方余额
			ends.add(helper.caculateEndingDebit("1501"));
			//5.其他投资，合计	1321+1511 借方余额
			ends.add(helper.caculateEndingDebit("1321", "1511"));
			//6.其他投资，其中：长期股权投资	1511借方余额
			ends.add(helper.caculateEndingDebit("1511"));
			//7.固定资产 1601借方-1602贷方
			Money c1601 = helper.caculateEndingDebit("1601");
			ends.add(c1601.subtract(helper.caculateEndingCredit("1602")));
			//8.抵债资产		1911借方余额
			ends.add(helper.caculateEndingDebit("1911"));
			//9.应收账款：合计	1201借余+1221借余+1122借余-1231贷方余额+1132借方
			Money s1231 = helper.caculateEndingDebit("1201", "1221", "1122", "1132");
			ends.add(s1231.subtract(helper.caculateEndingCredit("1231")));
			//10.应收账款：其中：期限在 2 年以上（含）的应收代偿款 0
			ends.add(new Money(0L));
			//11.应收账款：其他应收款	1221借余
			ends.add(helper.caculateEndingDebit("1221"));
			//12.负债总额 2开头的所有贷方余额-借方余额
			Money debt = helper.caculateDebt();
			ends.add(debt);
			//13.其中：借款	2001+2501贷方
			ends.add(helper.caculateEndingCredit("2001", "2501"));
			//14.应付款项	2241+2211+2221贷方
			ends.add(helper.caculateEndingCredit("2241", "2211", "2221"));
			//15.存入保证金 	2002贷方余额
			ends.add(helper.caculateEndingCredit("2002"));
			//16.预计负债	2801贷方余额
			ends.add(helper.caculateEndingCredit("2801"));
			//17.未到期责任准备金	2601贷方余额
			ends.add(helper.caculateEndingCredit("2601"));
			//18.担保赔偿准备金		2602贷余
			ends.add(helper.caculateEndingCredit("2602"));
			//19.3. 净资产			表内1减2
			ends.add(capital.subtract(debt));
			//20.其中：实收资本		4001期末贷方余额
			ends.add(helper.caculateEndingCredit("4001"));
			//21.一般风险准备		4102贷方余额
			ends.add(helper.caculateEndingCredit("4102"));
			
			QueryBaseBatchResult<String> batchResult = new QueryBaseBatchResult<>();
			List<String> lists = new ArrayList<>(ends.size());
			for (Money m : ends) {
				lists.add(MoneyUtil.getMoneyByw2(m));
			}
			batchResult.setPageList(lists);
			batchResult.setSuccess(true);
			return batchResult;
		} 
		
		return null;
	}
	
}
