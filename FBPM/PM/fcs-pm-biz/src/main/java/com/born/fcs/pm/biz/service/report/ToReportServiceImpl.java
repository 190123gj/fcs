package com.born.fcs.pm.biz.service.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.born.fcs.pm.dataobject.ToReportGuaranteeStructreDO;
import com.born.fcs.pm.ws.enums.ReportSysDataTypeEnum;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.ReportExpectEventDO;
import com.born.fcs.pm.dataobject.ToReportDO;
import com.born.fcs.pm.dataobject.ToReportExpectEventDO;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.ws.info.report.ToReportInfo;
import com.born.fcs.pm.ws.service.report.ToReportService;
import com.yjf.common.lang.util.money.Money;

/**
 *
 * 报表系统抓取项目管理的数据
 *
 * @author heh
 *
 * 2016年8月26日 14:18:40
 */
@Service("toReportService")
public class ToReportServiceImpl extends BaseAutowiredDomainService implements ToReportService {
	
	@Override
	public String getReleasingAmount(int year, int month) {
		List<ToReportInfo> infos = new ArrayList<>();
		Date now = new Date();
		int currYear = Integer.parseInt(DateUtil.getYear(now));
		int currMonth = Integer.parseInt(DateUtil.getMonth(now));
		if (year == currYear && month == currMonth) {//查询当前年月统计实时数据
			return busiDAO.getReleasingAmountToReport();
		} else {
			ReportExpectEventDO queryDO = new ReportExpectEventDO();

			List<String> typeList = new ArrayList<>();
			queryDO.setType(ReportSysDataTypeEnum.RXDB_BALANCE_AMOUNT.code());
			queryDO.setYear(year);
			queryDO.setMonth(month);
			List<ReportExpectEventDO> DOs = reportExpectEventDAO.findByCondition(queryDO, null,null);
			if (DOs != null && DOs.size() > 0) {
				return DOs.get(0).getAmount()+"";
			}
		}
		return "0.00";
	}
	
	@Override
	public List<String> getRepayOrChargeAmount(String deptCode, String type, int year, int month) {
		Date startTime = DateUtil.getStartTimeByYearAndMonth(year, month);
		Date endTime = DateUtil.getEndTimeByYearAndMonth(year, month);
		List<String> list = busiDAO.getRepayOrChargeAmount(deptCode, type, startTime, endTime);
		return list;
	}
	
	@Override
	public List<ToReportInfo> getExpectEvent(String deptCodes, int year, int month) {
		List<ToReportInfo> infos = new ArrayList<>();
		Date now = new Date();
		int currYear = Integer.parseInt(DateUtil.getYear(now));
		int currMonth = Integer.parseInt(DateUtil.getMonth(now));
		Date signedTime=DateUtil.getEndTimeByYearAndMonth(year,month);
//		if (year == currYear && month == currMonth) {//查询当前年月统计实时数据
			List<ToReportExpectEventDO> eventDOs = busiDAO.getExpectEvent(signedTime);
			if (eventDOs != null && eventDOs.size() > 0) {
				for (ToReportExpectEventDO eventDO : eventDOs) {
					ToReportInfo info = new ToReportInfo();
					info.setData1(eventDO.getDeptCode());
					info.setData2(eventDO.getDeptName());
					info.setData3(eventDO.getBusiType());
					info.setData4(eventDO.getType());
					info.setData5(MoneyUtil.getMoneyByw2(new Money(eventDO.getAmount()).divide(100)));
					infos.add(info);
				}
			} else {
				return null;
			}
//		} else {
//			ReportExpectEventDO queryDO = new ReportExpectEventDO();
//			String deptCode[] = deptCodes.split(",");
//			List<String> deptList = new ArrayList<>();
//			for (String code : deptCode) {
//				deptList.add(code);
//			}
//			List<String> typeList = new ArrayList<>();
//			typeList.add(ReportSysDataTypeEnum.ALL_SIGN.code());
//			typeList.add(ReportSysDataTypeEnum.NOT_ALL_SIGN.code());
//			queryDO.setYear(year);
//			queryDO.setMonth(month);
//			List<ReportExpectEventDO> DOs = reportExpectEventDAO.findByCondition(queryDO, deptList,typeList);
//			if (DOs != null && DOs.size() > 0) {
//				for (ReportExpectEventDO DO : DOs) {
//					ToReportInfo info = new ToReportInfo();
//					info.setData1(DO.getDeptCode());
//					info.setData2(DO.getDeptName());
//					info.setData3(DO.getBusiType());
//					info.setData4(DO.getType());
//					info.setData5(MoneyUtil.getMoneyByw2(new Money(DO.getAmount()).divide(100)));
//					infos.add(info);
//				}
//
//			}
//		}
		return infos;
	}
	
	@Override
	public List<ToReportInfo> getProjectProcess(int year, int month) {
		List<ToReportInfo> infos = new ArrayList<>();
		Date startTime = DateUtil.getStartTimeByYearAndMonth(year, month);
		Date endTime = DateUtil.getEndTimeByYearAndMonth(year, month);
		Date now = new Date();
		int currYear = Integer.parseInt(DateUtil.getYear(now));
		int currMonth = Integer.parseInt(DateUtil.getMonth(now));
		if (year == currYear && month == currMonth) {//查询当前年月统计实时数据
			endTime = now;
		}
		List<ToReportDO> reportDOs = busiDAO.getReportProjectProcess(startTime, endTime);
		if (reportDOs != null && reportDOs.size() > 0) {
			for (ToReportDO DO : reportDOs) {
				ToReportInfo info = new ToReportInfo();
				info.setData1(DO.getData1());
				info.setData2(MoneyUtil.getMoneyByw2(new Money(
					Double.parseDouble(DO.getData2()) / 100)));//金额
				info.setData3(DO.getData3());
				info.setData4(DO.getData4());
				info.setData5(DO.getData5());
				infos.add(info);
			}
			return infos;
		}
		return null;
	}

	@Override
	public List<ToReportInfo> getReportGuaranteeStructre(int year, int month) {
		List<ToReportInfo> infos = new ArrayList<>();
		Date startTime = DateUtil.getStartTimeByYearAndMonth(year, 1);
		Date endTime = DateUtil.getEndTimeByYearAndMonth(year, month);
		List<ToReportGuaranteeStructreDO> reportDOs = busiDAO.getReportGuaranteeStructre(startTime, endTime);
		if (reportDOs != null && reportDOs.size() > 0) {
			for (ToReportGuaranteeStructreDO DO : reportDOs) {
				ToReportInfo info = new ToReportInfo();
				info.setData1(MoneyUtil.getMoneyByw2(DO.getContractAmount()));
				info.setData2(MoneyUtil.getMoneyByw2(DO.getActualAmount()));
				info.setData3(MoneyUtil.getMoneyByw2(DO.getReleasedAmount()));
				info.setData4(MoneyUtil.getMoneyByw2(DO.getReleasingAmount()));
				info.setData5(DO.getDeptCode());
				info.setData6(DO.getDeptId()+"");
				info.setData7(DO.getDeptName());
				info.setData8(DO.getRemark());
				infos.add(info);
			}
			return infos;
		}
		return null;
	}

	@Override
	public List<ToReportInfo> getReportGuaranteeStructreBalanceAmount(int year, int month) {
		List<ToReportInfo> infos= Lists.newArrayList();
		ReportExpectEventDO queryDO = new ReportExpectEventDO();
		queryDO.setType(ReportSysDataTypeEnum.DB_BALANCEAMOUNT.code());
		queryDO.setYear(year);
		queryDO.setMonth(month);
		//本年月
		List<ReportExpectEventDO> DOs = reportExpectEventDAO.findByCondition(queryDO,null,null);
		if (DOs != null && DOs.size() > 0) {
			for (ReportExpectEventDO DO : DOs) {
				ToReportInfo info = new ToReportInfo();
				info.setData1(DO.getDeptCode());
				info.setData2(DO.getDeptName());
				info.setData3(DO.getBusiType());
				info.setData4(DO.getType());
				info.setData5(MoneyUtil.getMoneyByw2(new Money(DO.getAmount()).divide(100)));
				info.setData6("CURRYEAR");
				infos.add(info);
			}

		}
		//年初在保余额(去年12月)
		queryDO.setYear(year-1);
		queryDO.setMonth(12);
		List<ReportExpectEventDO> lastYear = reportExpectEventDAO.findByCondition(queryDO,null,null);
		if (lastYear != null && lastYear.size() > 0) {
			for (ReportExpectEventDO DO : lastYear) {
				ToReportInfo info = new ToReportInfo();
				info.setData1(DO.getDeptCode());
				info.setData2(DO.getDeptName());
				info.setData3(DO.getBusiType());
				info.setData4(DO.getType());
				info.setData5(MoneyUtil.getMoneyByw2(new Money(DO.getAmount()).divide(100)));
				info.setData6("LASTYEAR");
				infos.add(info);
			}

		}
		return infos;
	}

	@Override
	public List<ToReportInfo> getReportGuaranteeStructreContractAmount(int year, int month) {
		List<ToReportInfo> infos= Lists.newArrayList();
		Date startTime = DateUtil.getStartTimeByYearAndMonth(year, 1);
		Date endTime = DateUtil.getEndTimeByYearAndMonth(year, month);
		List<ToReportExpectEventDO> reportDOs = busiDAO.getReportGuaranteeStructreContractAmount(startTime, endTime);
		if (reportDOs != null && reportDOs.size() > 0) {
			for (ToReportExpectEventDO DO : reportDOs) {
				ToReportInfo info = new ToReportInfo();
				info.setData1(DO.getDeptCode());
				info.setData2(DO.getDeptName());
				info.setData3(DO.getBusiType());
				info.setData4("CONTRACT_AMOUNT");
				info.setData5(MoneyUtil.getMoneyByw2(
						new Money(DO.getAmount()).divide(100)));//金额
				infos.add(info);
			}
			return infos;
		}
		return infos;
	}

	public static void main(String[] args) {
		int year = 2015;
		int month = 11;
		Date startTime = DateUtil.getStartTimeByYearAndMonth(year, month);
		Date endTime = DateUtil.getEndTimeByYearAndMonth(year, month);
		System.out.println(startTime + "---" + endTime);
	}
}
