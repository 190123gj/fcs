package com.born.fcs.crm.ws.service.order;

import java.util.HashMap;
import java.util.List;

import com.born.fcs.crm.ws.service.enums.ChangeTypeEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 修改记录
 * */
public class ChangeListOrder extends ValidateOrderBase {
	private static final long serialVersionUID = -2527932952801990527L;
	/** 主键 */
	private long changeId;
	/** 客户ID */
	private long customerUserId;
	/** 修改人姓名 */
	private String operName;
	/** 修改人Id */
	private long operId;
	/** 表单Id */
	private long formId;
	/** 修改来源 */
	private ChangeTypeEnum changeType;
	/** 数据类型 */
	private String dataType;
	/** 修改详情 */
	private List<ChangeDetailOrder> chageDetailList;
	
	@Override
	public void check() {
		validateHasText(operName, "操作人");
		validateHasZore(customerUserId, "修改客户Id");
		validateNotNull(changeType, "修改来源");
		validateNotNull(chageDetailList, "修改详情");
	}
	
	/** 客户信息修改字段名 */
	public static HashMap<String, String> changeMap() {
		HashMap<String, String> map = new HashMap<>();
		map.put("companyQualification", "已获得的资质证书");
		map.put("companyOwnershipStructure", "公司股权结构 ");
		map.put("reqList", "其下公司");
		map.put("customerName", "客户名");
		map.put("sex", "性别");
		map.put("resoursFrom", "客户来源");
		map.put("certType", "证件类型");
		map.put("busiLicenseNo", "营业执照号");
		map.put("certNo", "证件号码");
		
		map.put("countryName", "国家");
		map.put("provinceName", "省");
		map.put("cityName", "市");
		map.put("countyName", "区域名");
		map.put("channalName", "渠道名 ");
		map.put("channalType", "项目渠道 ");
		map.put("enterpriseType", "企业性质 ");
		
		map.put("channalName", "渠道名 ");
		map.put("loanCardNo", "贷款卡号 ");
		map.put("finalYearCheck", "最后年检年度 ");
		
		map.put("industryName", "所属行业名字 ");
		map.put("isLocalGovPlatform", "是否地方政府融资平台企业");
		map.put("isExportOrientedEconomy", "是否外向型经济客户 ");
		map.put("isGroup", "是否集团客户 ");
		map.put("isListedCompany", "是否上市公司 ");
		map.put("enterpriseType", "企业性质 ");
		
		map.put("registerCapital", "注册资本 ");
		//		map.put("actualCapital", "实收资本");
		map.put("establishedTime", "成立时间 ");
		//		map.put("subordinateRelationship", "隶属关系 ");
		map.put("companyAddress", "企业地址 ");
		map.put("staffNum", "员工人数 ");
		
		map.put("scale", "企业规模");
		map.put("contactMan", "联系人");
		map.put("contactNo", "联系电话 ");
		map.put("busiScope", "经营范围");
		map.put("majorProduct", "主导产品");
		map.put("relation", "客户与我公司关系");
		map.put("legalPersion", "法定代表人");
		map.put("legalPersionCertNo", "法定代表人证件号码");
		map.put("legalPersionAddress", "法定代表人地址");
		map.put("actualControlMan", "实际控制人");
		map.put("actualControlManCertNo", "实际控制人证件号");
		map.put("actualControlManCertType", "实际控制人证件类型");
		map.put("actualControlManAddress", "实际控制人地址");
		map.put("settleBankAccount1", "主要结算账户开户行1");
		map.put("settleAccountNo1", "主要结算账户开户行1-账号");
		map.put("settleBankAccount2", "主要结算账户开户行2");
		map.put("settleAccountNo2", "主要结算账户开户行2-账号");
		map.put("settleBankAccount3", "其他结算账户开户行");
		map.put("settleAccountNo3", "其他结算账户开户行-账号");
		map.put("totalAsset", "总资产");
		map.put("netAsset", "净资产");
		map.put("assetLiabilityRatio", "资产负债率");
		map.put("liquidityRatio", "流动比率");
		map.put("quickRatio", "速动比率");
		map.put("salesProceedsLastYear", "去年销售收入");
		map.put("totalProfitLastYear", "去年利润总额");
		map.put("salesProceedsThisYear", "今年销售收入");
		map.put("totalProfitThisYear", "今年利润总额 ");
		map.put("busiLicenseUrl", "营业执照图片");
		map.put("isOneCert", "是否三证合一");
		map.put("orgCode", "组织机构代码");
		map.put("orgCodeUrl", "组织机构图片");
		map.put("taxCertificateNo", "国税证");
		map.put("taxCertificateUrl", "国税证图片");
		map.put("localTaxCertNo", "地税证");
		map.put("localTaxCertUrl", "地税证图片 ");
		//个人详情
		map.put("customerNamePx", "姓名拼音缩写");
		map.put("citizenType", "公民类型");
		map.put("nation", "民族");
		map.put("birthDay", "出生日期");
		map.put("maritalStatus", "婚姻状况");
		map.put("mobile", "联系电话");
		map.put("fix", "传真");
		map.put("address", "企业地址&详细住址");
		map.put("originPlace", "籍贯");
		map.put("registeredAddress", "户口所在地");
		map.put("postcode", "邮政编码");
		map.put("company", "现工作单位");
		map.put("job", "职务");
		map.put("technical", "技术职称");
		map.put("customerJobType", "客户类型");
		map.put("spoEmail", "配偶电子邮箱");
		map.put("spoRealName", "配偶姓名");
		map.put("spoSex", "配偶性别");
		map.put("spoCitizenType", "配偶公民类型");
		map.put("spoNation", "配偶民族");
		map.put("spoCertType", "配偶证件类型");
		map.put("spoCertNo", "配偶证件号码");
		map.put("spoMaritalStatus", "配偶婚姻状况");
		map.put("spoEducation", "配偶学历");
		map.put("spoBirthDay", "配偶出生日期 ");
		map.put("spoMobile", "配偶联系电话");
		map.put("spoFix", "配偶传真");
		map.put("spoAddress", "配偶固定住址");
		map.put("spoOriginPlace", "配偶籍贯");
		map.put("spoRegisteredAddress", "配偶户口所在地");
		map.put("spoPostcode", "配偶邮政编码");
		map.put("spoCompany", "配偶现工作单位");
		map.put("spoJob", "配偶职务");
		map.put("spoTechnical", "配偶技术职称");
		map.put("spoCustomerType", "配偶客户类型");
		map.put("familyAsset", "家庭财产");
		map.put("totalLoan", "总负债 ");
		map.put("totalOutcome", "年总支出");
		map.put("bankAccount", "基本账户开户行&开户行 ");
		map.put("accountNo", "基本账户开户行-账号&账号 ");
		map.put("accountHolder", "开户人");
		map.put("bankAccountWages", "本人工资代发账户:开户行");
		map.put("accountNoWages", "本人工资代发账户:账号");
		map.put("accountHolderWages", "本人工资代发账户:开户人");
		map.put("spoBankAccountWages", "配偶工资代发账户:开户行");
		map.put("spoAccountNoWages", "配偶工资代发账户:账号");
		map.put("spoAccountHolderWages", "配偶工资代发账户:开户人");
		map.put("house", "家庭财产（房产）");
		map.put("car", "家庭财产（车辆）");
		map.put("memo", "备注");
		map.put("certImg", "证照上传-上传其他附件");
		map.put("certImgFont", "身份证正面");
		map.put("certImgBack", "身份证反面 ");
		map.put("moneyType", "币种");
		map.put("moneyTypeName", "币种名 ");
		return map;
		
	}
	
	public boolean needSaveChange() {
		if (changeType != null) {
			return true;
		}
		return false;
		
	}
	
	public long getChangeId() {
		return this.changeId;
	}
	
	public void setChangeId(long changeId) {
		this.changeId = changeId;
	}
	
	public long getCustomerUserId() {
		return this.customerUserId;
	}
	
	public void setCustomerUserId(long customerUserId) {
		this.customerUserId = customerUserId;
	}
	
	public String getOperName() {
		return this.operName;
	}
	
	public void setOperName(String operName) {
		this.operName = operName;
	}
	
	public long getOperId() {
		return this.operId;
	}
	
	public void setOperId(long operId) {
		this.operId = operId;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public ChangeTypeEnum getChangeType() {
		return this.changeType;
	}
	
	public void setChangeType(ChangeTypeEnum changeType) {
		this.changeType = changeType;
	}
	
	public String getDataType() {
		return this.dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public List<ChangeDetailOrder> getChageDetailList() {
		return this.chageDetailList;
	}
	
	public void setChageDetailList(List<ChangeDetailOrder> chageDetailList) {
		this.chageDetailList = chageDetailList;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChangeListOrder [changeId=");
		builder.append(changeId);
		builder.append(", customerUserId=");
		builder.append(customerUserId);
		builder.append(", operName=");
		builder.append(operName);
		builder.append(", operId=");
		builder.append(operId);
		builder.append(", formId=");
		builder.append(formId);
		builder.append(", changeType=");
		builder.append(changeType);
		builder.append(", dataType=");
		builder.append(dataType);
		builder.append(", chageDetailList=");
		builder.append(chageDetailList);
		builder.append("]");
		return builder.toString();
	}
	
}
