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
 * 市金融办月/季报<br />
 * W11-（市金融办）融资性担保公司资金来源及运用月报表
 * 
 * @author lirz
 * 
 * 2016-8-26 上午11:10:48
 */
@Service("guaranteeSourceUseMonthlyService")
public class GuaranteeSourceUseMonthlyServiceImpl extends BaseAutowiredDomainService implements
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
		List<AccountBalanceDataInfo> abs = accountBalanceService.queryDatas(abQueryOrder);
		if (ListUtil.isNotEmpty(abs)) {
			AccountBalanceHelper helper = new AccountBalanceHelper(abs, "1");
			
			List<Money> list = new ArrayList<>();
			//4001期末贷方余额
			Money m1 = helper.caculateEndingCredit("4001");
			list.add(m1);
			//0
			Money m2 = ZERO_MONEY;
			list.add(m2);
			//0
			list.add(ZERO_MONEY);
			//6501借方发生累计
			Money c6501 = helper.caculateCurrentDebit("6501");
			//6502借方发生累计
			Money c6502 = helper.caculateCurrentDebit("6502");
			//4102借方发生累计
			Money c4102 = helper.caculateCurrentDebit("4102");
			//上面三项相加
			Money m3 = c6501.add(c6502).add(c4102);
			list.add(m3);
			list.add(c6501);
			list.add(c6502);
			list.add(c4102);
			//2002贷方余额
			Money m4 = helper.caculateEndingCredit("2002");
			list.add(m4);
			//4103贷方余额+4104贷方余额+4101贷方余额
			list.add(helper.caculateEndingCredit("4103", "4104", "4101"));
			// 总资产-上面1、2、3、4
			Money capital = helper.caculateCapital();
			Money m5 = capital.subtract(m1).subtract(m2).subtract(m3).subtract(m4);
			list.add(m5);
			//TODO 财务部提供
			list.add(ZERO_MONEY);
			list.add(ZERO_MONEY);
			list.add(ZERO_MONEY);
			//1002+1001借方余额
			Money m6 = helper.caculateEndingDebit("1002", "1001");
			list.add(m6);
			//1031借方余额
			Money m7 = helper.caculateEndingDebit("1031");
			list.add(m7);
			// 月报1  21项第一个-委贷
			//下两项+1501借方 0 + 6103.02借方发生累计(委贷)
			Money m8 = helper.caculateEndingDebit("1501");
			list.add(m8);
			//1501借方余额
			list.add(helper.caculateEndingDebit("1501"));
			//5214万(固定值)
			list.add(new Money(5214L * 10000));
			//对外投资总数-前两项
			list.add(m8.subtract(helper.caculateEndingDebit("1501")).subtract(new Money(5214L * 10000)));
			//6103.02借方余额-贷方余额
			Money m10 = helper.caculateDebitSubCredit("6103.02", "6103.02");
			list.add(m10);
			//1601借方余额-1602贷方余额
			Money m11 = helper.caculateDebitSubCredit("1601", "1602");
			list.add(m11);
			//1601.03借方-1602.03贷方
			list.add(helper.caculateDebitSubCredit("1601.03", "1602.03"));
			//1601.02借方-1602.02贷方
			list.add(helper.caculateDebitSubCredit("1601.02", "1602.02"));
			//1601.01借方-1602.01贷方
			list.add(helper.caculateDebitSubCredit("1601.01", "1602.01"));
			//1601.04借方-1602.04贷方
			list.add(helper.caculateDebitSubCredit("1601.04", "1602.04"));
			// 0
			Money m12 = ZERO_MONEY;
			list.add(m12);
			// 0
			list.add(ZERO_MONEY);
			//1201借方-1231贷方
			Money m13 = helper.caculateDebitSubCredit("1201", "1231");
			list.add(m13);
			// 0 
			Money m14 = ZERO_MONEY;
			list.add(m14);
			//总资产-6-7-8-10-11-12-13-14
			Money m15 = capital.subtract(m6).subtract(m7).subtract(m8).subtract(m10).subtract(m11)
				.subtract(m12).subtract(m13).subtract(m14);
			list.add(m15);
			//
			list.add(ZERO_MONEY);
			list.add(ZERO_MONEY);
			list.add(ZERO_MONEY);
			list.add(ZERO_MONEY);
			list.add(ZERO_MONEY);
			//1002.35.03 借方余额
			Money m16=helper.caculateEndingDebit("1002.35.03");
			list.add(m16);
			//1002.29.07借方余额
			Money m17=helper.caculateEndingDebit("1002.29.07");
			list.add(helper.caculateEndingDebit("1002.29.07"));
			//1002.09.01 借方余额
			Money m18=helper.caculateEndingDebit("1002.09.01");
			list.add(helper.caculateEndingDebit("1002.09.01"));
			//小计
			list.add(m16.add(m17).add(m18));
			
			QueryBaseBatchResult<String> batchResult = new QueryBaseBatchResult<>();
			List<String> datas = new ArrayList<>(list.size());
			for (Money m : list) {
				datas.add(MoneyUtil.getMoneyByw2(m));
			}
			//1002.35.03 开户行
			datas.add("三峡银行大渡口支行");
			//1002.29.07 开户行
			datas.add("重庆银行南坪支行");
			//1002.09.01 开户行
			datas.add("三峡银行渝北支行");
			batchResult.setPageList(datas);
			batchResult.setSuccess(true);
			return batchResult;
		}
		
		return null;
	}
	
}
