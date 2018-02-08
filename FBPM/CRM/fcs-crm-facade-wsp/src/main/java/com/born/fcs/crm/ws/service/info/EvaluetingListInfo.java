package com.born.fcs.crm.ws.service.info;

import java.io.Serializable;
import java.util.Date;

/**
 * 评价列表信息
 * */
public class EvaluetingListInfo implements Serializable {
	
	private static final long serialVersionUID = -483743821982747625L;
	/** 主键 */
	private long id;
	/** 审核表单Id */
	private long formId;
	/** 关联项目Id */
	private String projectCode;
	/** 客户Id */
	private String customerId;
	/** 客户名称 */
	private String customerName;
	/** 营业执照号码 */
	private String busiLicenseNo;
	/** 客户等级 */
	private String level;
	/** 评级年限:如 2016 */
	private String year;
	/** 审定日期 */
	private Date auditTime;
	/** 操作人 */
	private String operator;
	/** 评级状态 */
	private String auditStatus;
	/** 评价方式：内部/外部 */
	private String evaluetingType;
	/** 被公司提起或准备提起法律诉讼的客户 */
	private String isProsecute;
	/** 外部_评级机构 */
	private String evaluetingInstitutions;
	/** 外部_评级日期 */
	private Date evaluetingTime;
	/** 外部_评级图片 */
	private String auditImg;
	/** 审核意见1 */
	private String auditOpinion1;
	/** 审核意见2 */
	private String auditOpinion2;
	/** 审核意见3 */
	private String auditOpinion3;
	/** 审核意见4 */
	private String auditOpinion4;
	/** 审核意见5 */
	private String auditOpinion5;
	/** 审核意见6 */
	private String auditOpinion6;
	/** 创建时间 */
	private Date rawAddTime;
	/** 更新时间 */
	private Date rawUpdateTime;
	/** 评级页签完整性 */
	private String editStatus;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EvaluetingListInfo [id=");
		builder.append(id);
		builder.append(", formId=");
		builder.append(formId);
		builder.append(", projectCode=");
		builder.append(projectCode);
		builder.append(", customerId=");
		builder.append(customerId);
		builder.append(", customerName=");
		builder.append(customerName);
		builder.append(", busiLicenseNo=");
		builder.append(busiLicenseNo);
		builder.append(", level=");
		builder.append(level);
		builder.append(", year=");
		builder.append(year);
		builder.append(", auditTime=");
		builder.append(auditTime);
		builder.append(", operator=");
		builder.append(operator);
		builder.append(", auditStatus=");
		builder.append(auditStatus);
		builder.append(", evaluetingType=");
		builder.append(evaluetingType);
		builder.append(", isProsecute=");
		builder.append(isProsecute);
		builder.append(", evaluetingInstitutions=");
		builder.append(evaluetingInstitutions);
		builder.append(", evaluetingTime=");
		builder.append(evaluetingTime);
		builder.append(", auditImg=");
		builder.append(auditImg);
		builder.append(", auditOpinion1=");
		builder.append(auditOpinion1);
		builder.append(", auditOpinion2=");
		builder.append(auditOpinion2);
		builder.append(", auditOpinion3=");
		builder.append(auditOpinion3);
		builder.append(", auditOpinion4=");
		builder.append(auditOpinion4);
		builder.append(", auditOpinion5=");
		builder.append(auditOpinion5);
		builder.append(", auditOpinion6=");
		builder.append(auditOpinion6);
		builder.append(", rawAddTime=");
		builder.append(rawAddTime);
		builder.append(", rawUpdateTime=");
		builder.append(rawUpdateTime);
		builder.append(", editStatus=");
		builder.append(editStatus);
		builder.append("]");
		return builder.toString();
	}
	
}
