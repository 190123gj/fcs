package com.born.fcs.crm.ws.service.order;

import java.util.Date;
import java.util.List;

import com.born.fcs.crm.ws.service.info.EvaluetingInfo;
import com.born.fcs.pm.ws.order.base.FormOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 评价提交order
 * */
public class EvaluetingOrder extends FormOrderBase {
	
	private static final long serialVersionUID = -1508660249927906395L;
	/** 本年度是否有评价数据 */
	private String yearEvalueId;
	//评价分类
	private String evalue_type;
	/** 操作人 */
	private String operator;
	/** 审核状态 */
	private String auditStatus;
	//基本信息
	/** 客户Id */
	private String customerId;
	/** 客户姓名 */
	private String customerName;
	
	//评级基本信息
	/** 评价方式：内部/外部 */
	private String evaluetingType;
	/** 被公司提起或准备提起法律诉讼的客户 */
	private String isProsecute;
	
	//外部评级信息
	/** 外部_评级机构 */
	private String evaluetingInstitutions;
	/** 外部_评级日期 */
	private Date evaluetingTime;
	/** 外部_会议纪要图片 */
	private String auditImg;
	
	//客户概况
	/** 客户修改记录Id */
	private Long dataId;
	/** 贷款卡号 */
	private String loanCardNo;
	/** 注册：实收资本 */
	private Money actualCapital;
	/** 隶属关系 */
	private String subordinateRelationship;
	/** 销售额（去年） */
	private Money salesProceedsLastYear;
	/** 基本账户行 */
	private String accountNo;
	/** 是否集团客户 */
	private String isGroup;
	/** 是否上市公司 */
	private String isListedCompany;
	
	//评价详情
	/** 一般，财务，资本信用，调整事项 */
	List<EvaluetingInfo> EvaluetingInfo;
	
	/** 评价等级 */
	private String level;
	
	/** 是否暂存 IS/ON */
	private String isTemporary;
	/** 关联项目Id */
	private String projectCode;
	
	/** 完整性 */
	private String editStatus;
	
	@Override
	public void check() {
		validateHasText(evalue_type, "评级分类");
		validateHasText(customerName, "客户姓名");
		
		if ("IS".equals(isProsecute)) {
			setLevel("F");
		} else {
			//外部评级
			if ("outer".equals(evaluetingType)) {
				if (!"IS".equals(isTemporary)) {
					validateHasText(evaluetingInstitutions, "评级机构");
					validateNotNull(evaluetingTime, "评级日期");
					validateHasText(auditImg, "会议纪要图片");
					validateHasText(level, "评价等级 ");
				}
			} else if("none".equals(evaluetingType)){

			} else{
				//内部评级
				
				//1.清除外部评级信息
				this.evaluetingInstitutions = null;
				this.evaluetingTime = null;
				this.auditImg = null;
				this.evaluetingInstitutions = null;
				//2.清除等级 仅提交计分总表保存等级
				//				if (!"JFZB".equals(evalue_type))
				//					this.level = null;
				//3.验证数据完整性
				if (!"IS".equals(isTemporary)) {
					if ("KHGK".equals(evalue_type)) {//客户概况
					//						validateHasText(loanCardNo, "贷款卡号");
					//						validateNotNull(actualCapital, "实收资本");
						validateHasText(subordinateRelationship, "隶属关系");
						//						validateNotNull(salesProceedsLastYear, "销售额");
						//						validateHasText(accountNo, "基本账户行");
					} else if ("JFZB".equals(evalue_type)) {//计分总表
						if (EvaluetingInfo == null) {
							validateHasText(level, "评价等级");
						}
					} else {
						//评级信息
						validateNotNull(EvaluetingInfo, "评级详情");
						
					}
				}
			}
		}
		
	}
	
	public String getEditStatus() {
		return this.editStatus;
	}
	
	public void setEditStatus(String editStatus) {
		this.editStatus = editStatus;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getEvalue_type() {
		return this.evalue_type;
	}
	
	public void setEvalue_type(String evalue_type) {
		this.evalue_type = evalue_type;
	}
	
	public String getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getEvaluetingType() {
		return this.evaluetingType;
	}
	
	public void setEvaluetingType(String evaluetingType) {
		this.evaluetingType = evaluetingType;
	}
	
	public String getIsProsecute() {
		return this.isProsecute;
	}
	
	public void setIsProsecute(String isProsecute) {
		this.isProsecute = isProsecute;
	}
	
	public String getEvaluetingInstitutions() {
		return this.evaluetingInstitutions;
	}
	
	public void setEvaluetingInstitutions(String evaluetingInstitutions) {
		this.evaluetingInstitutions = evaluetingInstitutions;
	}
	
	public Date getEvaluetingTime() {
		return this.evaluetingTime;
	}
	
	public void setEvaluetingTime(Date evaluetingTime) {
		this.evaluetingTime = evaluetingTime;
	}
	
	public String getAuditImg() {
		return this.auditImg;
	}
	
	public void setAuditImg(String auditImg) {
		this.auditImg = auditImg;
	}
	
	public String getLoanCardNo() {
		return this.loanCardNo;
	}
	
	public void setLoanCardNo(String loanCardNo) {
		this.loanCardNo = loanCardNo;
	}
	
	public Money getActualCapital() {
		if (this.actualCapital != null) {
			return this.actualCapital;
			
		}
		return this.actualCapital;
	}
	
	public void setActualCapital(Money actualCapital) {
		this.actualCapital = actualCapital;
	}
	
	public String getSubordinateRelationship() {
		return this.subordinateRelationship;
	}
	
	public void setSubordinateRelationship(String subordinateRelationship) {
		this.subordinateRelationship = subordinateRelationship;
	}
	
	public Money getSalesProceedsLastYear() {
		if (this.salesProceedsLastYear != null) {
			return this.salesProceedsLastYear;
			
		}
		return this.salesProceedsLastYear;
	}
	
	public void setSalesProceedsLastYear(Money salesProceedsLastYear) {
		this.salesProceedsLastYear = salesProceedsLastYear;
	}
	
	public String getAccountNo() {
		return this.accountNo;
	}
	
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public List<EvaluetingInfo> getEvaluetingInfo() {
		return this.EvaluetingInfo;
	}
	
	public void setEvaluetingInfo(List<EvaluetingInfo> evaluetingInfo) {
		EvaluetingInfo = evaluetingInfo;
	}
	
	public String getLevel() {
		return this.level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getOperator() {
		return this.operator;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getAuditStatus() {
		return this.auditStatus;
	}
	
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public String getYearEvalueId() {
		return this.yearEvalueId;
	}
	
	public void setYearEvalueId(String yearEvalueId) {
		this.yearEvalueId = yearEvalueId;
	}
	
	public String getIsTemporary() {
		return this.isTemporary;
	}
	
	public void setIsTemporary(String isTemporary) {
		this.isTemporary = isTemporary;
	}
	
	public String getIsGroup() {
		return this.isGroup;
	}
	
	public void setIsGroup(String isGroup) {
		this.isGroup = isGroup;
	}
	
	public Long getDataId() {
		return this.dataId;
	}
	
	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}
	
	public String getIsListedCompany() {
		return this.isListedCompany;
	}
	
	public void setIsListedCompany(String isListedCompany) {
		this.isListedCompany = isListedCompany;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EvaluetingOrder [yearEvalueId=");
		builder.append(yearEvalueId);
		builder.append(", evalue_type=");
		builder.append(evalue_type);
		builder.append(", operator=");
		builder.append(operator);
		builder.append(", auditStatus=");
		builder.append(auditStatus);
		builder.append(", customerId=");
		builder.append(customerId);
		builder.append(", customerName=");
		builder.append(customerName);
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
		builder.append(", dataId=");
		builder.append(dataId);
		builder.append(", loanCardNo=");
		builder.append(loanCardNo);
		builder.append(", actualCapital=");
		builder.append(actualCapital);
		builder.append(", subordinateRelationship=");
		builder.append(subordinateRelationship);
		builder.append(", salesProceedsLastYear=");
		builder.append(salesProceedsLastYear);
		builder.append(", accountNo=");
		builder.append(accountNo);
		builder.append(", isGroup=");
		builder.append(isGroup);
		builder.append(", isListedCompany=");
		builder.append(isListedCompany);
		builder.append(", EvaluetingInfo=");
		builder.append(EvaluetingInfo);
		builder.append(", level=");
		builder.append(level);
		builder.append(", isTemporary=");
		builder.append(isTemporary);
		builder.append(", projectCode=");
		builder.append(projectCode);
		builder.append("]");
		return builder.toString();
	}
}
