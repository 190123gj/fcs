package com.born.fcs.rm.biz.service.report.inner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yjf.common.lang.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.integration.service.SysParameterServiceClient;
import com.born.fcs.rm.integration.service.pm.ToReportServiceClient;
import com.born.fcs.rm.ws.info.report.inner.ExpectReleaseInfo;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.born.fcs.rm.ws.service.submission.SubmissionService;
import com.yjf.common.lang.util.money.Money;

/**
 *
 * 内部报表<br />
 * 综合汇总表<br />
 * 预计解保情况汇总表
 *
 * @author heh
 *
 * 2016年8月27日 15:41:33
 */
@Service("expectIncomeService")
public class ExpectIncomeServiceImpl extends BaseAutowiredDomainService implements
																		ReportProcessService {
	
	@Autowired
	ToReportServiceClient toReportServiceClient;
	
	@Autowired
	SysParameterServiceClient sysParameterServiceClient;
	
	@Autowired
	SubmissionService submissionService;
	
	@Override
	public FcsBaseResult save(ReportOrder order) {
		return null;
	}
	
	@Override
	public Object findByAccountPeriod(ReportQueryOrder queryOrder) {
		QueryBaseBatchResult<ExpectReleaseInfo> batchResult = new QueryBaseBatchResult<>();
		//获取上报部门
		String deptCodes[] = sysParameterServiceClient.getSysParameterValue(
			SysParamEnum.SYS_PARAM_ANNUAL_OBJECTIVE_DEPT_CODE.code()).split(",");
		int year = queryOrder.getReportYear();
		int month = queryOrder.getReportMonth();
		List<ExpectReleaseInfo> infos = new ArrayList<>();
		List<ExpectReleaseInfo> infosW = new ArrayList<>();
		for (String deptCode : deptCodes) {
			String result = submissionService.findDeptInfoByDeptCode(deptCode);
			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			Map<String, Object> sysOrgMap = (Map<String, Object>) resultMap.get("sysOrg");
			ExpectReleaseInfo infoD = new ExpectReleaseInfo();//担保
			ExpectReleaseInfo infoW = new ExpectReleaseInfo();//委贷
			infoD.setDept(String.valueOf(sysOrgMap.get("orgName")));
			infoW.setDept(String.valueOf(sysOrgMap.get("orgName")));
			Money totalD = new Money(0.00);
			Money totalW = new Money(0.00);
			for (int i = 1; i <= month; i++) {
				List<String> amount = toReportServiceClient.getRepayOrChargeAmount(deptCode,
					"CHARGE_PLAN", year, i);
				infoD = setValue(infoD, i, Double.parseDouble(amount.get(0))/100+"");
				totalD = totalD.add(new Money(Double.parseDouble(amount.get(0))/100));
				infoW = setValue(infoW, i,Double.parseDouble(amount.get(1))/100+"");
				totalW = totalW.add(new Money(Double.parseDouble(amount.get(1))/100));
			}
			infoD.setTotal(MoneyUtil.getMoneyByw2(totalD));
			infoW.setTotal(MoneyUtil.getMoneyByw2(totalW));
			infoD.setBusiType("担保");
			infoW.setBusiType("委贷");
			infos.add(infoD);
			infosW.add(infoW);
		}
		//合计
		infos.add(getTotalInfo(infos,"担保"));
		if (infosW.size() > 0) {
			for (ExpectReleaseInfo itemInfo : infosW) {
				infos.add(itemInfo);
			}
			infos.add(getTotalInfo(infosW,"委贷"));
		}
		//没有数据数据的业务部门不显示
		List<ExpectReleaseInfo> returnInfos = new ArrayList<>();
		if(ListUtil.isNotEmpty(infos)){
			for(ExpectReleaseInfo info:infos){
				if(!isEmpty(info)){
					returnInfos.add(info);
				}
			}
		}
		batchResult.setPageList(returnInfos);
		batchResult.setSuccess(true);
		return batchResult;
	}

	public boolean isEmpty(ExpectReleaseInfo info) {
		return 	  "0.00".equals(info.getData1() )
				&&"0.00".equals(info.getData2() )
				&&"0.00".equals(info.getData3() )
				&&"0.00".equals(info.getData4() )
				&&"0.00".equals(info.getData5() )
				&&"0.00".equals(info.getData6() )
				&&"0.00".equals(info.getData7() )
				&&"0.00".equals(info.getData8() )
				&&"0.00".equals(info.getData9() )
				&&"0.00".equals(info.getData10())
				&&"0.00".equals(info.getData11())
				&&"0.00".equals(info.getData12())
				&&"0.00".equals(info.getData13())
				&&"0.00".equals(info.getData14())
				&&"0.00".equals(info.getData15())
				&&"0.00".equals(info.getData16())
				&&"0.00".equals(info.getData17())
				&&"0.00".equals(info.getData18())
				&&"0.00".equals(info.getData19())
				&&"0.00".equals(info.getData20())
				&&"0.00".equals(info.getData21())
				&&"0.00".equals(info.getData22())
				&&"0.00".equals(info.getData23())
				&&"0.00".equals(info.getData24())
				;
	}
	
	private ExpectReleaseInfo setValue(ExpectReleaseInfo info, int month, String amount) {
		switch (month) {
			case 1:
				info.setData1(MoneyUtil.getMoneyByw2(new Money(amount)));
				break;
			case 2:
				info.setData2(MoneyUtil.getMoneyByw2(new Money(amount)));
				break;
			case 3:
				info.setData3(MoneyUtil.getMoneyByw2(new Money(amount)));
				break;
			case 4:
				info.setData4(MoneyUtil.getMoneyByw2(new Money(amount)));
				break;
			case 5:
				info.setData5(MoneyUtil.getMoneyByw2(new Money(amount)));
				break;
			case 6:
				info.setData6(MoneyUtil.getMoneyByw2(new Money(amount)));
				break;
			case 7:
				info.setData7(MoneyUtil.getMoneyByw2(new Money(amount)));
				break;
			case 8:
				info.setData8(MoneyUtil.getMoneyByw2(new Money(amount)));
				break;
			case 9:
				info.setData9(MoneyUtil.getMoneyByw2(new Money(amount)));
				break;
			case 10:
				info.setData10(MoneyUtil.getMoneyByw2(new Money(amount)));
				break;
			case 11:
				info.setData11(MoneyUtil.getMoneyByw2(new Money(amount)));
				break;
			case 12:
				info.setData12(MoneyUtil.getMoneyByw2(new Money(amount)));
				break;
			default:
				break;
		}
		return info;
	}
	
	private ExpectReleaseInfo getTotalInfo(List<ExpectReleaseInfo> infos,String busiType) {
		ExpectReleaseInfo info = new ExpectReleaseInfo();
		if (infos.size() > 0) {
			Money totalData1 = new Money(0.00);
			Money totalData2 = new Money(0.00);
			Money totalData3 = new Money(0.00);
			Money totalData4 = new Money(0.00);
			Money totalData5 = new Money(0.00);
			Money totalData6 = new Money(0.00);
			Money totalData7 = new Money(0.00);
			Money totalData8 = new Money(0.00);
			Money totalData9 = new Money(0.00);
			Money totalData10 = new Money(0.00);
			Money totalData11 = new Money(0.00);
			Money totalData12 = new Money(0.00);
			Money total = new Money(0.00);
			for (ExpectReleaseInfo itemInfo : infos) {
				totalData1 = totalData1.add(new Money(itemInfo.getData1()));
				totalData2 = totalData2.add(new Money(itemInfo.getData2()));
				totalData3 = totalData3.add(new Money(itemInfo.getData3()));
				totalData4 = totalData4.add(new Money(itemInfo.getData4()));
				totalData5 = totalData5.add(new Money(itemInfo.getData5()));
				totalData6 = totalData6.add(new Money(itemInfo.getData6()));
				totalData7 = totalData7.add(new Money(itemInfo.getData7()));
				totalData8 = totalData8.add(new Money(itemInfo.getData8()));
				totalData9 = totalData9.add(new Money(itemInfo.getData9()));
				totalData10 = totalData10.add(new Money(itemInfo.getData10()));
				totalData11 = totalData11.add(new Money(itemInfo.getData11()));
				totalData12 = totalData12.add(new Money(itemInfo.getData12()));
			}
			total = total.add(totalData1).add(totalData2).add(totalData3).add(totalData4)
				.add(totalData5).add(totalData6).add(totalData7).add(totalData8).add(totalData9)
				.add(totalData10).add(totalData11).add(totalData12);
			info.setDept("合计");
			info.setBusiType(busiType);
			info.setData1(totalData1.toString());
			info.setData2(totalData2.toString());
			info.setData3(totalData3.toString());
			info.setData4(totalData4.toString());
			info.setData5(totalData5.toString());
			info.setData6(totalData6.toString());
			info.setData7(totalData7.toString());
			info.setData8(totalData8.toString());
			info.setData9(totalData9.toString());
			info.setData10(totalData10.toString());
			info.setData11(totalData11.toString());
			info.setData12(totalData12.toString());
			info.setTotal(total.toString());
			
		}
		return info;
	}
}
