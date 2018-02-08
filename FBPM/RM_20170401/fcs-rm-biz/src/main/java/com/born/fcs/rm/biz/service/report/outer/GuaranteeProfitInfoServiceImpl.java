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
 * W3-（中担协）融资性担保机构收益情况
 * 
 * @author lirz
 *
 * 2016-8-20 下午3:36:52
 */
@Service("guaranteeProfitInfoService")
public class GuaranteeProfitInfoServiceImpl extends BaseAutowiredDomainService implements
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
			AccountBalanceHelper helper = new AccountBalanceHelper(list); 
			
			List<Money> ends = new ArrayList<>();
			//1. 1. 担保业务收入		6031当年累计发生贷方
			Money m1 = helper.caculateYearCredit("6031");
			ends.add(m1);
			//2.其中：融资性担保费收入	6031当年累计发生贷方
			ends.add(m1);
			//3.2. 担保业务成本	6403借方发生累计
			Money m2 = helper.caculateYearDebit("6403");
			ends.add(m2);
			//4.其中：融资性担保赔偿支出	6511借方发生累计
			ends.add(helper.caculateYearDebit("6511"));
			//5.融资性分担保费支出	6542借方发生累计
			ends.add(helper.caculateYearDebit("6542"));
			//6.营业税金及附加		6403借方发生累计
			ends.add(helper.caculateYearDebit("6403"));
			//7.3.提取未到期责任准备	6501借方发生累计
			Money m3 = helper.caculateYearDebit("6501");
			ends.add(m3);
			//8.4.担保赔偿准备金		6502借方发生累计
			Money m4 = helper.caculateYearDebit("6502");
			ends.add(m4);
			//9.5. 担保业务利润		本表1减2减3减4
			Money m5 = m1.subtract(m2).subtract(m3).subtract(m4);
			ends.add(m5);
			//10.6. 利息净收入（净支出则前加“－”号填列）	6103贷方累计减6421借方累计
			Money m6 = helper.caculateYearCredit("6103").subtract(helper.caculateYearDebit("6421"));
			ends.add(m6);
			//11.7. 其他业务利润	6051贷方累计
			Money m7 = helper.caculateYearCredit("6051");
			ends.add(m7);
			
			//12.8. 业务及管理费	6601借方累计
			Money m8 = helper.caculateYearDebit("6601");
			ends.add(m8);
			//13.9. 投资收益（投资损失则前加“－”号填列）	6111贷方累计
			Money m9 = helper.caculateYearCredit("6111");
			ends.add(m9);
			//14.10. 资产减值损失（转回的金额则前加“－”号填列）6701借方累计
			Money m10 = helper.caculateYearDebit("6701");
			ends.add(m10);
			//15.11. 营业利润	5加6加7减8加9减10
			Money m11 = m5.add(m6).add(m7).subtract(m8).add(m9).subtract(m10);
			ends.add(m11);
			//16.12. 营业外净收入（净亏损则前加“－”号填列）	6301贷方累计-6711借方累计
			Money m12 = helper.caculateYearCredit("6301").subtract(helper.caculateYearDebit("6711"));
			ends.add(m12);
			//17.13. 所得税	6801借方累计
			Money m13 = helper.caculateYearDebit("6801");
			ends.add(m13);
			//18.14. 净利润（净亏损则前加“－”号填列）	11加12减13
			Money m14 = m11.add(m12).subtract(m13);
			ends.add(m14);
			
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
