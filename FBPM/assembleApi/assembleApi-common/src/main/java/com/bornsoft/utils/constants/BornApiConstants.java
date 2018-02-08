package com.bornsoft.utils.constants;

import com.bornsoft.utils.tool.PropertiesUtil;

/**
 * @Description: bornapi 常用常量 
 * @author taibai@yiji.com
 * @date 2016-11-10 下午2:03:35
 * @version V1.0
 */
public class BornApiConstants {
	public static final String CODE = "code";
	public static final String MESSAGE = "message";
	public static final String LIKE_CODE_OR_NAME = "likeCodeOrName";
	public static final String TOTAL = "total";
	public static final String VERSION = "version";
	public static final String DEVICE_NO = "deviceNo";
	public static final String E_TOKEN = "e_token";
	public static final String N_TOKEN = "n_token";
	public static final String CUR_ORG_ID = "curOrgId";
	public static final String LOGING_NAME = "loginName";
	public static final String USER_NAME = "userName";
	public static final String ERR_MSG = "errMsg";
	public static final String ERR_CODE = "errCode";
	
	public static final  int MinVersion = 3;
	
	public static String lineSeperate = System.getProperty("line.separator");
	/**
	 * 获取项目的访问路径
	 * @return
	 */
	public static String getProjectPath() {
		return PropertiesUtil.getProperty("capitalpool.url");
	}
}
