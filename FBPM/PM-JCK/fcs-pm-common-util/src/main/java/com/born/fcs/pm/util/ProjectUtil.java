package com.born.fcs.pm.util;

/**
 * 项目相关工具类
 *
 * @author wuzj
 */
public class ProjectUtil {
	
	/**
	 * 是否是承销业务
	 * 
	 * @param busiType
	 * @return 是：true
	 */
	public static boolean isUnderwriting(String busiType) {
		if (StringUtil.isNotBlank(busiType) && busiType.trim().startsWith("5")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 否是诉讼保函业务
	 * 
	 * @param busiType
	 * @return 是：true
	 */
	public static boolean isLitigation(String busiType) {
		if (StringUtil.isNotBlank(busiType) && busiType.trim().startsWith("211")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 是否发债业务
	 * @param busiType
	 * @return
	 */
	public static boolean isBond(String busiType) {
		if (StringUtil.isNotBlank(busiType) && busiType.trim().startsWith("12")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 是否委贷业务
	 * @param busiType
	 * @return
	 */
	public static boolean isEntrusted(String busiType) {
		if (StringUtil.isNotBlank(busiType) && busiType.trim().startsWith("4")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 是否担保业务
	 * @param busiType
	 * @return
	 */
	public static boolean isGuarantee(String busiType) {
		return !isUnderwriting(busiType) && !isLitigation(busiType) && !isBond(busiType)
				&& !isEntrusted(busiType);
	}
	
	/**
	 * 判断项目是否理财项目
	 * @param projectCode
	 * @return
	 */
	public static boolean isFinancial(String projectCode) {
		return projectCode == null ? false : projectCode.startsWith("F");
	}
}
