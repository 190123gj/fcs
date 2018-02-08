package com.born.fcs.pm.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class RateUtil {
	public static String getRate(double value) {
		return getRateOnly(value) + "%";
	}
	
	public static String getRateOnly(double value) {
		BigDecimal bg = new BigDecimal(value);
		bg = bg.multiply(new BigDecimal(100));
		double d = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return d + "";
	}
	
	public static double getRateDoubleValue(double value) {
		BigDecimal bg = new BigDecimal(value);
		bg = bg.multiply(new BigDecimal(100));
		return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static String formatRate(Double value) {
		if (null == value) {
			return "";
		}
		
		DecimalFormat format = new DecimalFormat("#0.00");
		return format.format(value);
//		Money m = new Money(value);
//		return m.toStandardString();
	}
	
	public static String formatRateStr(String value) {
		if (null == value || "".equals(value.trim())) {
			return "";
		}
		try {
			Double d = Double.parseDouble(value);
//			DecimalFormat format = new DecimalFormat("#0.00");
//			return format.format(d);
			return formatRate(d);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String toPercent(String value) {
		if (null == value || "".equals(value.trim())) {
			return "";
		}
		
		try {
			Double d = Double.parseDouble(value) * 100;
			DecimalFormat format = new DecimalFormat("#0.00");
			return format.format(d);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getRateDoubleValue(336.57));
	}
}
