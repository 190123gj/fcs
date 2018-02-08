package com.born.fcs.pm.util;

import com.yjf.common.lang.util.money.Money;

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
	
	/**
	 * 判断项目是否银行融资担保
	 * @param busiType
	 * @return
	 */
	public static boolean isBankFinancing(String busiType) {
		if (StringUtil.isNotBlank(busiType) && busiType.trim().startsWith("11")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 根据项目编号获取业务类型
	 * @param projectCode
	 * @return
	 */
	public static String getBusiTypeByCode(String projectCode) {
		String busiType = "";
		if (StringUtil.isNotBlank(projectCode)) {
			if (isFinancial(projectCode)) {
				busiType = "理财项目";
			} else {
				String[] codeArr = projectCode.split("-");
				if (codeArr.length > 3) {
					busiType = codeArr[2];
					if (busiType.startsWith("0")) {
						busiType = busiType.substring(1, busiType.length());
					}
					if (busiType.startsWith("0")) {
						busiType = busiType.substring(1, busiType.length());
					}
				}
			}
		}
		return busiType;
	}
	
	/**
	 * 获取分保发生额
	 * @param totalIssueAmount 累计发行金额
	 * @param reinsuranceAmount 分保金额
	 * @param approvalAmount 监管机构批复金额
	 * @return
	 */
	public static Money getReinsuranceOccurAmount(Money totalIssueAmount, Money reinsuranceAmount,
													Money approvalAmount) {
		long maxDisivor = NumberUtil.maxDivisor(reinsuranceAmount.getCent(),
			approvalAmount.getCent());
		long fz = reinsuranceAmount.getCent() / maxDisivor;
		long fm = approvalAmount.getCent() / maxDisivor;
		return totalIssueAmount.multiply(fz).divide(fm);
	}
}
