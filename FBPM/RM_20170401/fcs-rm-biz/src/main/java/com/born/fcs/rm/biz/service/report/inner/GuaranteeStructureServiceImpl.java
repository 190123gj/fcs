package com.born.fcs.rm.biz.service.report.inner;

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

import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.info.report.ToReportInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.integration.service.SysParameterServiceClient;
import com.born.fcs.rm.integration.service.pm.PmReportServiceClient;
import com.born.fcs.rm.integration.service.pm.ToReportServiceClient;
import com.born.fcs.rm.ws.info.report.inner.ExpectReleaseInfo;
import com.born.fcs.rm.ws.info.report.inner.GuaranteeStructureInfo;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.yjf.common.lang.util.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rop.thirdparty.com.google.common.collect.Lists;

import java.util.*;

@Service("guaranteeStructureService")
public class GuaranteeStructureServiceImpl extends BaseAutowiredDomainService implements
																				ReportProcessService {
	
	@Autowired
	SysParameterServiceClient sysParameterServiceClient;
	@Autowired
	ToReportServiceClient toReportServiceClient;
	@Autowired
	private PmReportServiceClient pmReportServiceClient;
	
	@Override
	public FcsBaseResult save(ReportOrder order) {
		return null;
	}
	
	private final String busiTypes[] = { "间接融资担保", "直接融资担保", "非融资担保", "再担保" };
	
	@Override
	public Object findByAccountPeriod(ReportQueryOrder queryOrder) {
		QueryBaseBatchResult<GuaranteeStructureInfo> batchResult = new QueryBaseBatchResult<>();
		List<GuaranteeStructureInfo> list = new ArrayList<>();
		//获取上报部门
		String deptCodes[] = sysParameterServiceClient.getSysParameterValue(
			SysParamEnum.SYS_PARAM_ANNUAL_OBJECTIVE_DEPT_CODE.code()).split(",");
		int year = queryOrder.getReportYear();
		int month = queryOrder.getReportMonth();
		//年初在保余额
//		List<ToReportInfo> balanceAmount = toReportServiceClient
//			.getReportGuaranteeStructreBalanceAmount(year, month);
		List<ToReportInfo> balanceAmount = balanceAmount(year, month);
		//合同金额
		List<ToReportInfo> contractAmount = toReportServiceClient
				.getReportGuaranteeStructreContractAmount(year, month);
		//统计数据
		List<ToReportInfo> toReportInfos = toReportServiceClient.getReportGuaranteeStructre(year,
			month);
		Map<String, ToReportInfo> balanceMap = new HashMap<>();//年初在保余额map
		Map<String, ToReportInfo> contractMap = new HashMap<>();//年初在保余额map
		Map<String, ToReportInfo> map = new HashMap<>();
		if (balanceAmount != null) {
			for (ToReportInfo reportInfo : balanceAmount) {
				balanceMap.put(reportInfo.getData1() + "_" + reportInfo.getData3()+"_"+reportInfo.getData6(), reportInfo);//部门编号_类型
			}
		}
		if (contractAmount != null) {
			for (ToReportInfo reportInfo : contractAmount) {
				contractMap.put(reportInfo.getData1() + "_" + reportInfo.getData3(), reportInfo);//部门编号_类型
			}
		}
		if (toReportInfos != null) {
			for (ToReportInfo reportInfo : toReportInfos) {
				map.put(reportInfo.getData5() + "_" + reportInfo.getData8(), reportInfo);//部门编号_类型
			}
		}
		//是否本年月
		boolean isCurr=isCurrYearAndMonth(year,month);
		Money data3 = Money.zero();
		Money data4 = Money.zero();
		Money data5 = Money.zero();
		Money data6 = Money.zero();
		Money data7 = Money.zero();
		for (String busiType : busiTypes) {
			GuaranteeStructureInfo guaranteeStructureInfo = new GuaranteeStructureInfo();
			List<ExpectReleaseInfo> releaseInfos = new ArrayList<>();
			Money totalData3 = Money.zero();
			Money totalData4 = Money.zero();
			Money totalData5 = Money.zero();
			Money totalData6 = Money.zero();
			Money totalData7 = Money.zero();
			for (String deptCode : deptCodes) {
				ExpectReleaseInfo info = new ExpectReleaseInfo();
				ToReportInfo beginOfYbalanceAmountInfo = balanceMap.get(deptCode + "_" + busiType+"_LASTYEAR");
				ToReportInfo currYbalanceAmountInfo = balanceMap.get(deptCode + "_" + busiType+"_CURRYEAR");
				ToReportInfo contractAmountInfo = contractMap.get(deptCode + "_" + busiType);
				ToReportInfo toReportInfo = map.get(deptCode + "_" + busiType);
				if (beginOfYbalanceAmountInfo == null && toReportInfo == null) {
					continue;
				} else {
					if (beginOfYbalanceAmountInfo == null) {
						info.setData4("0.00");
					} else {
						info.setData4(beginOfYbalanceAmountInfo.getData5());
					}
				}
				info.setData1(toReportInfo.getData7());
				info.setData2(busiType);
//				info.setData3(toReportInfo.getData1());
//				if(isCurr) {//如果是当前年月取实时数据
//					info.setData3(toReportInfo.getData4());
//				}else{
					if(contractAmountInfo==null){
						info.setData3("0.00");
					}else{
						info.setData3(contractAmountInfo.getData5());
					}
//				}
				info.setData5(toReportInfo.getData2());
				info.setData6(toReportInfo.getData3());
				if(isCurr) {//如果是当前年月取实时数据
					info.setData7(toReportInfo.getData4());
				}else{
					if(currYbalanceAmountInfo==null){
						info.setData7("0.00");
					}else{
						info.setData7(currYbalanceAmountInfo.getData5());
					}
				}
				releaseInfos.add(info);
				totalData3 = totalData3.add(new Money(info.getData3()));
				totalData4 = totalData4.add(new Money(info.getData4()));
				totalData5 = totalData5.add(new Money(info.getData5()));
				totalData6 = totalData6.add(new Money(info.getData6()));
				totalData7 = totalData7.add(new Money(info.getData7()));
			}
			if (releaseInfos.size() > 0) {
				ExpectReleaseInfo info = new ExpectReleaseInfo();
				info.setData1("小计");
				info.setData2(busiType);
				info.setData3(totalData3.toString());
				info.setData4(totalData4.toString());
				info.setData5(totalData5.toString());
				info.setData6(totalData6.toString());
				info.setData7(totalData7.toString());
				releaseInfos.add(info);
				guaranteeStructureInfo.setExpectReleaseInfos(releaseInfos);
				list.add(guaranteeStructureInfo);
				data3 = data3.add(new Money(info.getData3()));
				data4 = data4.add(new Money(info.getData4()));
				data5 = data5.add(new Money(info.getData5()));
				data6 = data6.add(new Money(info.getData6()));
				data7 = data7.add(new Money(info.getData7()));
			}
		}
		//合计
		GuaranteeStructureInfo guaranteeStructureInfo = new GuaranteeStructureInfo();
		ExpectReleaseInfo releaseInfo = new ExpectReleaseInfo();
		List<ExpectReleaseInfo> releaseInfos = new ArrayList<>();
		releaseInfo.setData1("合计");
		releaseInfo.setData2("合计");
		releaseInfo.setData3(data3.toString());
		releaseInfo.setData4(data4.toString());
		releaseInfo.setData5(data5.toString());
		releaseInfo.setData6(data6.toString());
		releaseInfo.setData7(data7.toString());
		releaseInfos.add(releaseInfo);
		guaranteeStructureInfo.setExpectReleaseInfos(releaseInfos);
		list.add(guaranteeStructureInfo);
		batchResult.setPageList(list);
		batchResult.setSuccess(true);
		return batchResult;
	}

	/**
	 * 在保余额
	 */
	private List<ToReportInfo> balanceAmount(int year,int month) {
		List<ToReportInfo> infos= Lists.newArrayList();


		String sql = "SELECT  SUM(ba.balance) amount,p.dept_code,p.dept_id,p.dept_name,''AS TYPE,'间接融资担保' AS busi_type FROM ${pmDbTitle}.project_data_info p "
				+ "	JOIN (SELECT occer.project_code, (oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_actual_occer_detail WHERE occur_date <= '${reportMonthEndDay}' GROUP BY project_code) occer "
				+ "LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE  p.busi_type LIKE '11%' GROUP BY p.dept_code "
				+ " UNION ALL\n"
				+"	SELECT  SUM(ba.balance) amount,p.dept_code,p.dept_id,p.dept_name,''AS TYPE,'直接融资担保' AS busi_type FROM ${pmDbTitle}.project_data_info p "
				+ "	JOIN (SELECT occer.project_code, (oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_actual_occer_detail WHERE occur_date <= '${reportMonthEndDay}' GROUP BY project_code) occer "
				+ "	LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE  (p.busi_type LIKE '12%' OR p.busi_type LIKE '13%') GROUP BY p.dept_code "
				+ " UNION ALL\n"
				+"	SELECT  SUM(ba.balance) amount,p.dept_code,p.dept_id,p.dept_name,''AS TYPE,'非融资担保' AS busi_type FROM ${pmDbTitle}.project_data_info p "
				+ "	JOIN (SELECT occer.project_code, (oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_actual_occer_detail WHERE occur_date <= '${reportMonthEndDay}' GROUP BY project_code) occer "
				+ "	LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE  (p.busi_type LIKE '2%' AND p.busi_type !='241') GROUP BY p.dept_code "
				+ " UNION ALL\n"
				+"	SELECT  SUM(ba.balance) amount,p.dept_code,p.dept_id,p.dept_name,''AS TYPE,'再担保' AS busi_type FROM ${pmDbTitle}.project_data_info p "
				+ "	JOIN (SELECT occer.project_code, (oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_actual_occer_detail WHERE occur_date <= '${reportMonthEndDay}' GROUP BY project_code) occer "
				+ "	LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE  (p.busi_type LIKE '3%') GROUP BY p.dept_code ";
		sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);
		//年初在保余额
		//期初余额
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(sql.replaceAll("\\$\\{reportMonthEndDay\\}", getReportLastYearEndDay(year)));
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				ToReportInfo info=new ToReportInfo();
				info.setData1(String.valueOf(itm.getMap().get("dept_code")));
				info.setData2(String.valueOf(itm.getMap().get("dept_name")));
				info.setData3(String.valueOf(itm.getMap().get("busi_type")));
				info.setData4(String.valueOf(itm.getMap().get("TYPE")));
				info.setData5(MoneyUtil.getMoneyByw2(toMoney(itm.getMap().get("amount"))));
				info.setData6("LASTYEAR");
				infos.add(info);
			}
		}

		//期末余额
		queyOrder.setSql(sql.replaceAll("\\$\\{reportMonthEndDay\\}", getReportMonthEndDay(year,month)));
		dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (dataResult != null) {
			for (DataListItem itm : dataResult) {
				ToReportInfo info=new ToReportInfo();
				info.setData1(String.valueOf(itm.getMap().get("dept_code")));
				info.setData2(String.valueOf(itm.getMap().get("dept_name")));
				info.setData3(String.valueOf(itm.getMap().get("busi_type")));
				info.setData4(String.valueOf(itm.getMap().get("TYPE")));
				info.setData5(MoneyUtil.getMoneyByw2(toMoney(itm.getMap().get("amount"))));
				info.setData6("CURRYEAR");
				infos.add(info);
			}
		}

		return infos;
	}

	private boolean isCurrYearAndMonth(int y,int m){
		Date now = new Date();
		int currYear = Integer.parseInt(DateUtil.getYear(now));
		int currMonth = Integer.parseInt(DateUtil.getMonth(now));
		if (y == currYear && m == currMonth) {//查询当前年月统计实时数据
			return true;
		}
		return false;
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

	/** 查询的是否本年第一天 */
	public boolean isThisYearFirstDay(int reportYear) {
		return com.born.fcs.pm.util.StringUtil.equals(getReportYearStartDay(reportYear), DateUtil.dtSimpleFormat(new Date()));
	}

	/** 查询的是否本月第一天 */
	public boolean isThisMonthFirstDay(int reportYear,int reportMonth) {
		return com.born.fcs.pm.util.StringUtil.equals(getReportMonthStartDay(reportYear,reportMonth), DateUtil.dtSimpleFormat(new Date()));
	}
	/**
	 * 查询当年第一天
	 * @return
	 */
	public String getReportYearStartDay(int reportYear) {
		return reportYear + "-01-01";
	}

	/**
	 * 查询当月第一天
	 * @return
	 */
	public String getReportMonthStartDay(int reportYear,int reportMonth) {
		return getReportYearMonth(reportYear,reportMonth) + "-01";
	}

	/**
	 * 查询账期
	 * @return
	 */
	public String getReportYearMonth(int reportYear,int reportMonth) {
		return reportYear + (reportMonth < 10 ? "-0" : "-") + reportMonth;
	}

	/**
	 * 查询当月结束时间（不超过当月最大时间）
	 * @return
	 */
	public String getReportMonthEndDay(int reportYear,int reportMonth) {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int moth = now.get(Calendar.MONTH) + 1;
		if (year == reportYear && moth == reportMonth) {
			//当月
		} else {
			now.set(Calendar.YEAR, reportYear);
			now.set(Calendar.MONTH, reportMonth - 1);
			now.set(Calendar.DAY_OF_MONTH, now.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		return DateUtil.dtSimpleFormat(now.getTime());
	}

	/** 查询的是否本月 */
	public boolean isThisMonth(int reportYear,int reportMonth) {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int moth = now.get(Calendar.MONTH) + 1;
		return year == reportYear && moth == reportMonth;
	}

	/***
	 * 查询报告年上年结束时间
	 * @return
	 */
	public String getReportLastYearEndDay(int reportYear) {
		return (reportYear - 1) + "-12-31";
	}
}
