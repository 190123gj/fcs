package com.born.fcs.rm.ws.order.report;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 
 * 查询报表
 * 
 * @author lirz
 * 
 * 2016-8-5 上午11:49:00
 */
public class ReportQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -996959723373514955L;
	
	private long reportId;
	private String reportName;
	private String reportType;
	private String reportTypes;
	private String reportCode;
	private int reportYear;
	private int reportMonth;
	private String version;
	private long operatorId;
	private String operatorAccount;
	private String operatorName;
	private long deptId;
	private String deptCode;
	private String deptName;
	private String deptPath;
	private String deptPathName;
	
	private String startDate;
	
	private String endDate;
	
	//用户所属部门列表
	private List<Long> deptIdList;
	
	/** 当前期最后一天 */
	public String getThisEndDay() {
		Date date = DateUtil.getEndTimeByYearAndMonth(reportYear, reportMonth);
		return DateUtil.dtSimpleFormat(date);
	}
	
	/**
	 * 查询账期
	 * @return
	 */
	public String getReportYearMonth() {
		return reportYear + (reportMonth < 10 ? "-0" : "-") + reportMonth;
	}
	
	/**
	 * 查询当年第一天
	 * @return
	 */
	public String getReportYearStartDay() {
		return reportYear + "-01-01";
	}
	
	/**
	 * 查询当月第一天
	 * @return
	 */
	public String getReportMonthStartDay() {
		return getReportYearMonth() + "-01";
	}
	
	/** 去年最后一天 */
	public String getLastYearEndDay() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.YEAR, reportYear - 1);
		now.set(Calendar.MONTH, 11);
		now.set(Calendar.DAY_OF_MONTH, now.getActualMaximum(Calendar.DAY_OF_MONTH));
		return DateUtil.dtSimpleFormat(now.getTime());
	}
	
	/**
	 * 查询当月结束时间（不超过当月最大时间）
	 * @return
	 */
	public String getReportMonthEndDay() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int moth = now.get(Calendar.MONTH) + 1;
		if (year == this.reportYear && moth == this.reportMonth) {
			//当月
		} else {
			now.set(Calendar.YEAR, reportYear);
			now.set(Calendar.MONTH, reportMonth - 1);
			now.set(Calendar.DAY_OF_MONTH, now.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		return DateUtil.dtSimpleFormat(now.getTime());
	}
	/**
	 * 查询去年同期结束时间
	 * @return
	 */
	public String getReportLastYearMonthEndDay() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int moth = now.get(Calendar.MONTH) + 1;
		if (year == this.reportYear && moth == this.reportMonth) {
			//当月
			now.set(Calendar.YEAR, reportYear-1);
		} else {
			now.set(Calendar.YEAR, reportYear-1);
			now.set(Calendar.MONTH, reportMonth - 1);
			now.set(Calendar.DAY_OF_MONTH, now.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		return DateUtil.dtSimpleFormat(now.getTime());
	}
	
	/**
	 * 查询报告期上个月最后一天结束时间
	 * @return
	 */
	public String getReportLastMonthEndDay() {
		Calendar date = Calendar.getInstance();
		date.setTime(DateUtil.strToDtSimpleFormat(getReportMonthStartDay()));
		date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
		return DateUtil.dtSimpleFormat(date.getTime());
	}
	
	/**
	 * 查询报告期上个月开始时间
	 * @return
	 */
	public String getReportLastMonthStartDay() {
		Calendar date = Calendar.getInstance();
		date.setTime(DateUtil.strToDtSimpleFormat(getReportMonthStartDay()));
		date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
		return DateUtil.getFormat("yyyy-MM").format(date.getTime()) + "-01";
	}
	
	/**
	 * 获得本年开始时间
	 * 
	 * @return
	 */
	public String getYearStartTime() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			c.set(Calendar.YEAR, reportYear);
			c.set(Calendar.MONTH, 0);
			c.set(Calendar.DATE, 1);
			SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd");
			now = longSdf.parse(longSdf.format(c.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DateUtil.simpleFormat(now);
	}
	
	/**
	 * 获得本期起始时间
	 * 
	 * @return
	 */
	public String getThisStartTime() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			c.set(Calendar.YEAR, reportYear);
			c.set(Calendar.MONTH, reportMonth - 1);
			c.set(Calendar.DATE, 1);
			SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd");
			now = longSdf.parse(longSdf.format(c.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DateUtil.simpleFormat(now);
	}
	
	/**
	 * 获得本期结束时间
	 * 
	 * @return
	 */
	public String getThisEndTime() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			c.set(Calendar.YEAR, reportYear);
			c.set(Calendar.MONTH, reportMonth);
			c.set(Calendar.DATE, 1);
			c.add(Calendar.DATE, -1);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 0);
			now = longSdf.parse(longSdf.format(c.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DateUtil.simpleFormat(now);
	}
	
	/**
	 * 获取指定 年月的 季度开始时间
	 * @return
	 */
	public Date getQuarterStartTimeByYearAndMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd");
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, month);
			int currentMonth = month;
			if (currentMonth >= 1 && currentMonth <= 3)
				c.set(Calendar.MONTH, 0);
			else if (currentMonth >= 4 && currentMonth <= 6)
				c.set(Calendar.MONTH, 3);
			else if (currentMonth >= 7 && currentMonth <= 9)
				c.set(Calendar.MONTH, 6);
			else if (currentMonth >= 10 && currentMonth <= 12)
				c.set(Calendar.MONTH, 9);
			c.set(Calendar.DATE, 1);
			now = longSdf.parse(longSdf.format(c.getTime()) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}
	
	/**
	 * 获取指定 年月的 季度结束时间
	 * @return
	 */
	public Date getQuarterEndTimeByYearAndMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd");
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, month);
			int currentMonth = month;
			if (currentMonth >= 1 && currentMonth <= 3) {
				c.set(Calendar.MONTH, 2);
				c.set(Calendar.DATE, 31);
			} else if (currentMonth >= 4 && currentMonth <= 6) {
				c.set(Calendar.MONTH, 5);
				c.set(Calendar.DATE, 30);
			} else if (currentMonth >= 7 && currentMonth <= 9) {
				c.set(Calendar.MONTH, 8);
				c.set(Calendar.DATE, 31);
			} else if (currentMonth >= 10 && currentMonth <= 12) {
				c.set(Calendar.MONTH, 11);
				c.set(Calendar.DATE, 31);
			}
			now = longSdf.parse(longSdf.format(c.getTime()) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}
	
	/***
	 * 查询报告年结束时间（不当年最大时间）
	 * @return
	 */
	public String getReportYearEndDay() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		if (year == this.reportYear) {
			//当年
		} else {
			now.set(Calendar.YEAR, reportYear);
			now.set(Calendar.MONTH, 11);
			now.set(Calendar.DAY_OF_MONTH, now.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		return DateUtil.dtSimpleFormat(now.getTime());
	}
	
	/***
	 * 查询报告年上年开始时间
	 * @return
	 */
	public String getReportLastYearStartDay() {
		return (reportYear - 1) + "-01-01";
	}
	
	/***
	 * 查询报告年上年结束时间
	 * @return
	 */
	public String getReportLastYearEndDay() {
		return (reportYear - 1) + "-12-31";
	}
	
	/** 查询的是否本年 */
	public boolean isThisYear() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		return year == this.reportYear;
	}
	
	/** 查询的是否本月 */
	public boolean isThisMonth() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int moth = now.get(Calendar.MONTH) + 1;
		return year == this.reportYear && moth == this.reportMonth;
	}
	
	/** 查询 当前月是不是指定的某季度 */
	public boolean isThisQuarter(int reportYear, int reportMonth) {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int moth = now.get(Calendar.MONTH) + 1;
		//这里转化成当前季度季度最后一月
		if (moth == 1 || moth == 2 || moth == 3) {
			moth = 3;
		} else if (moth == 4 || moth == 5 || moth == 6) {
			moth = 6;
		} else if (moth == 7 || moth == 8 || moth == 9) {
			moth = 9;
		} else {
			moth = 12;
		}
		return year == reportYear && moth == reportMonth;
	}
	
	/** 查询的是否本年第一天 */
	public boolean isThisYearFirstDay() {
		return StringUtil.equals(getReportYearStartDay(), DateUtil.dtSimpleFormat(new Date()));
	}
	
	/** 查询的是否本月第一天 */
	public boolean isThisMonthFirstDay() {
		return StringUtil.equals(getReportMonthStartDay(), DateUtil.dtSimpleFormat(new Date()));
	}
	
	public List<Long> getDeptIdList() {
		return deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public long getReportId() {
		return reportId;
	}
	
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
	
	public String getReportName() {
		return reportName;
	}
	
	public void setReportName(String reportName) {
		if (!isNull(reportName)) {
			this.reportName = reportName.trim();
		} else {
			this.reportName = reportName;
		}
	}
	
	public String getReportType() {
		return reportType;
	}
	
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	public String getReportTypes() {
		return reportTypes;
	}
	
	public void setReportTypes(String reportTypes) {
		this.reportTypes = reportTypes;
	}
	
	public String getReportCode() {
		return reportCode;
	}
	
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	
	public int getReportYear() {
		return reportYear;
	}
	
	public void setReportYear(int reportYear) {
		this.reportYear = reportYear;
	}
	
	public int getReportMonth() {
		return reportMonth;
	}
	
	public void setReportMonth(int reportMonth) {
		this.reportMonth = reportMonth;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public long getOperatorId() {
		return operatorId;
	}
	
	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}
	
	public String getOperatorAccount() {
		return operatorAccount;
	}
	
	public void setOperatorAccount(String operatorAccount) {
		this.operatorAccount = operatorAccount;
	}
	
	public String getOperatorName() {
		return operatorName;
	}
	
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public long getDeptId() {
		return deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptCode() {
		return deptCode;
	}
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getDeptPath() {
		return deptPath;
	}
	
	public void setDeptPath(String deptPath) {
		this.deptPath = deptPath;
	}
	
	public String getDeptPathName() {
		return deptPathName;
	}
	
	public void setDeptPathName(String deptPathName) {
		this.deptPathName = deptPathName;
	}
	
	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
