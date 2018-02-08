package com.born.fcs.crm.biz.service.Date;

import java.util.Date;

/** 获取数据库当前时间 */
public interface SqlDateService {
	/** 获取当前时间 */
	Date getSqlDate();
	
	/** 获取当前年 */
	String getYear();
}
