package com.born.fcs.rm.ws.info.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.born.fcs.pm.ws.enums.ChannelRelationEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.DateUtil;

/**
 * 渠道分类汇总表
 * */
public class ChannelClassfyOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 5326294297110659363L;
	/** 查询年 */
	private int reportYear;
	/** 查询月 */
	private int reportMonth;
	/** 按渠道类型：资金/项目 */
	private ChannelRelationEnum type = ChannelRelationEnum.CAPITAL_CHANNEL;
	
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public void check() {
		validateHasZore(reportYear, "查询年");
		validateHasZore(reportMonth, "查询月");
	}
	
	/** 查询的是否本月 */
	public boolean isNowMoth() {
		Date time = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(time);
		
		int year = now.get(Calendar.YEAR);
		int moth = now.get(Calendar.MONTH) + 1;
		return year == this.reportYear && moth == this.reportMonth;
	}
	
	/** 当前月最后一天 */
	public Date getMothEndDay() {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return ca.getTime();
	}
	
	/** 当前年第一天 */
	public Date getYearStartDate() {
		String date = reportYear + "-" + "01-01";
		try {
			return DateUtil.simpleFormatDate(date);
		} catch (ParseException e) {
		}
		return null;
	}
	
	/** 去年最后一天 */
	public Date getLastYearEndDay() {
		Date time = getYearStartDate();
		if (time == null)
			return null;
		return DateUtil.increaseDate(time, -1);
		
	}
	
	/** 今天是不是一月1号 */
	public boolean isYearBeginDay() {
		String[] dStr = format.format(new Date()).split("-");
		return "01".equals(dStr[1]) && "01".equals(dStr[2]);
		
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
	
	/** 当前报告时间 */
	public String getProjectDate() {
		Calendar ca = Calendar.getInstance();
		ca.set(this.reportYear, this.reportMonth, 0);
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return format.format(getMothEndDay());
		
	}
	
	public int getReportYear() {
		return this.reportYear;
	}
	
	public void setReportYear(int reportYear) {
		this.reportYear = reportYear;
	}
	
	public int getReportMonth() {
		return this.reportMonth;
	}
	
	public void setReportMonth(int reportMonth) {
		this.reportMonth = reportMonth;
	}
	
	public ChannelRelationEnum getType() {
		return this.type;
	}
	
	public void setType(ChannelRelationEnum type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChannelClassfyOrder [reportYear=");
		builder.append(reportYear);
		builder.append(", reportMonth=");
		builder.append(reportMonth);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}
	
}
