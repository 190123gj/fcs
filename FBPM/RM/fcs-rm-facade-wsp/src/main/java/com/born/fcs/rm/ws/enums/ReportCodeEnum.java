package com.born.fcs.rm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表
 * 
 * @author heh
 */
public enum ReportCodeEnum {
	
	//-------------------------------------内部报表----------------------------------------
	//code type service vm_suffix reprot_name
	BASE_REPORT("BASE_REPORT", ReportTypeEnum.INNER, "", "", "BaseReport", "", "基层定期报表"),
	CUSTOM_REPORT("CUSTOM_REPORT", ReportTypeEnum.INNER, "", "", "CustomReport", "", "自定义报表"),
	GUARANTEE_MAIN_INDEX("GUARANTEE_MAIN_INDEX", ReportTypeEnum.INNER, "综合汇总表",
							"guaranteeKpiDataService", "GuaranteeMainIndex", "ANNUAL_OBJECTIVE",
							"担保业务主要指标汇总表"),
	ENTRUSTEDE_MAIN_INDEX("ENTRUSTEDE_MAIN_INDEX", ReportTypeEnum.INNER, "综合汇总表",
							"entrustedeMainIndexService", "EntrustedeMainIndex",
							"ANNUAL_OBJECTIVE", "委贷业务主要指标汇总表"),
	GUARANTEE_STRUCTURE("GUARANTEE_STRUCTURE", ReportTypeEnum.INNER, "综合汇总表",
						"guaranteeStructureService", "GuaranteeStructure", "", "担保业务结构汇总表"),
	GUARANTEE_CUSTOMER_CLASSIFY("GUARANTEE_CUSTOMER_CLASSIFY", ReportTypeEnum.INNER, "综合汇总表",
								"guaranteeCustomerServise", "GuaranteeCustomerClassify", "",
								"担保业务客户分类汇总表"),
	GUARANTEE_AMOUNT_LIMIT("GUARANTEE_AMOUNT_LIMIT", ReportTypeEnum.INNER, "综合汇总表", "guaranteeAmountLimitService",
							"GuaranteeAmountLimit", "", "担保业务金额/期限分类汇总表"),
	PRODUCT_CLASSIFY("PRODUCT_CLASSIFY", ReportTypeEnum.INNER, "综合汇总表", "projectTypeDataService",
						"ProductClassify", "", "产品分类汇总表"),
	CHANNEL_CLASSIFY("CHANNEL_CLASSIFY", ReportTypeEnum.INNER, "综合汇总表", "channelDataService",
						"ChannelClassify", "", "渠道分类汇总表"),
	PROJECT_PROCESS("PROJECT_PROCESS", ReportTypeEnum.INNER, "综合汇总表", "projectProcessService",
					"ProjectProcess", "", "项目推动情况汇总表"),
	EXPECT_RELEASE("EXPECT_RELEASE", ReportTypeEnum.INNER, "综合汇总表", "expectReleaseService",
					"ExpectRelease", "", "预计解保情况汇总表"),
	EXPECT_EVENT("EXPECT_EVENT", ReportTypeEnum.INNER, "综合汇总表", "expectEventService",
					"ExpectEvent", "", "预计发生情况汇总表"),
	EXPECT_INCOME("EXPECT_INCOME", ReportTypeEnum.INNER, "综合汇总表", "expectIncomeService",
					"ExpectIncome", "", "预计收入情况汇总表"),
	EXPECT_RELEASE_DETAIL("EXPECT_RELEASE_DETAIL", ReportTypeEnum.INNER, "综合汇总表",
							"expectReleaseDetailService", "ExpectReleaseDetail", "",
							"存量项目收入预计明细表（预计解保明细表）"),
	MONTH_PROJECT_CHANGE("MONTH_PROJECT_CHANGE", ReportTypeEnum.INNER, "综合汇总表",
							"monthProjectChangeService", "MonthProjectChange", "", "当月项目发生变动明细表"),
	MAIN_INDEX("MAIN_INDEX", ReportTypeEnum.INNER, "综合汇总表", "mainIndexService", "MainIndex",
				"BFSRCWB", "主要业务指标情况一览表"),
	GUARANTEE_CLASSIFY("GUARANTEE_CLASSIFY", ReportTypeEnum.INNER, "综合汇总表",
						"guaranteeClassifyService", "GuaranteeClassify", "", "担保项目分类汇总表"),
	
	//-------------------------------------外部报表----------------------------------------
	W1_GUARANTEE_BASE_INFO("W1_GUARANTEE_BASE_INFO", ReportTypeEnum.OUTER, "中担协季报",
							"guaranteeBaseInfoService", "W1GuaranteeBaseInfo",
							"ACCOUNT_BALANCE,RZXDBJGQKB", "W1-（中担协）融资性担保机构基本情况"),
	W2_GUARANTEE_DEBT_INFO("W2_GUARANTEE_DEBT_INFO", ReportTypeEnum.OUTER, "中担协季报",
							"guaranteeDebtInfoService", "W2GuaranteeDebtInfo", "ACCOUNT_BALANCE",
							"W2-（中担协）融资性担保机构资产负债情况"),
	W3_GUARANTEE_PROFIT_INFO("W3_GUARANTEE_PROFIT_INFO", ReportTypeEnum.OUTER, "中担协季报",
								"guaranteeProfitInfoService", "W3GuaranteeProfitInfo",
								"ACCOUNT_BALANCE", "W3-（中担协）融资性担保机构收益情况"),
	W4_GUARANTEE_BUSI_INFO("W4_GUARANTEE_BUSI_INFO", ReportTypeEnum.OUTER, "中担协季报",
							"w4GuaranteeBusiInfoService", "W4GuaranteeBusiInfo", "",
							"W4-（中担协）融资性担保机构业务情况（表一）"),
	W5_GUARANTEE_BUSI_INFO("W5_GUARANTEE_BUSI_INFO", ReportTypeEnum.OUTER, "中担协季报", "guaranteeBusiInfoW5Service",
							"W5GuaranteeBusiInfo", "", "W5-（中担协）融资性担保业务情况（表二）"),
	W6_GUARANTEE_RISK_INDEX("W6_GUARANTEE_RISK_INDEX", ReportTypeEnum.OUTER, "中担协季报",
							"guaranteeRiskIndexService", "W6GuaranteeRiskIndex",
							"ACCOUNT_BALANCE,RZXDBJGFXZB", "W6-（中担协）融资性担保机构风险指标"),
	
	W7_COMPANY_BASE_INFO("W7_COMPANY_BASE_INFO", ReportTypeEnum.OUTER, "工信部网上直报",
							"companyBaseInfoSerivce", "W7CompanyBaseInfo", "", "W7-（工信部网上直报）公司基本情况"),
	W8_FINANCIAL_INFO("W8_FINANCIAL_INFO", ReportTypeEnum.OUTER, "工信部网上直报", "financialInfoW8Service", "W8FinancialInfo",
						"YJCWXX", "W8-（工信部网上直报）业绩财务信息"),
	W9_GUARANTEE_DETAIL("W9_GUARANTEE_DETAIL", ReportTypeEnum.OUTER, "工信部网上直报", "guaranteeDetailService",
						"W9GuaranteeDetail", "", "W9-（工信部网上直报）担保业务明细"),
	
	W10_GUARANTEE_DATA_MONTHLY("W10_GUARANTEE_DATA_MONTHLY", ReportTypeEnum.OUTER, "市金融办/月季报", "w10GuaranteeDataMonthlyService",
								"W10GuaranteeDataMonthly", "ACCOUNT_BALANCE,DBGSZYSJYBB,RZXDBJGQKB", "W10-（市金融办）担保公司主要数据月报表"),
	W11_GUARANTEE_SOURCE_USE_MONTHLY("W11_GUARANTEE_SOURCE_USE_MONTHLY", ReportTypeEnum.OUTER,
										"市金融办/月季报", "guaranteeSourceUseMonthlyService",
										"W11GuaranteeSourceUseMonthly", "ACCOUNT_BALANCE",
										"W11-（市金融办）融资性担保公司资金来源及运用月报表"),
	GUARANTEE_WEB_LOAN_MONTHLY("GUARANTEE_WEB_LOAN_MONTHLY", ReportTypeEnum.OUTER, "市金融办/月季报",
								"guaranteeWebLoanMonthlyService", "GuaranteeWebLoanMonthly", "",
								"（市金融办）融资担保机构互联网融资担保月报表"),
	GUARANTEE_OPERATE_INFO("GUARANTEE_OPERATE_INFO", ReportTypeEnum.OUTER, "市金融办/月季报",
							"guaranteeOperateInfoService", "GuaranteeOperateInfo",
							"RZXDBGSNMJYQKB,RZXDBJGFXZB", "（市金融办季度报告）融资性担保公司经营情况表"),
	GUARANTEE_YEAR_END_EXPECT("GUARANTEE_YEAR_END_EXPECT", ReportTypeEnum.OUTER, "市金融办/月季报",
								"guaranteeYearEndExpectService", "GuaranteeYearEndExpect",
								"RZXDBGSNMJYYCB_GHTZB,RZXDBGSNMJYYCB_CWB,RZXDBGSNMJYYCB_FXB",
								"融资性担保公司年末经营预测表"),
	W13_FINANCIAL_INDUSTRY_MAIN_INDEX("W13_FINANCIAL_INDUSTRY_MAIN_INDEX", ReportTypeEnum.OUTER,
										"市统计局季报", "financialIndustryMainIndexService",
										"W13FinancialIndustryMainIndex", "ACCOUNT_BALANCE",
										"W13-(市统计局)新型金融业主要统计指标表");
	
	/** 报表code */
	private final String code;
	
	/** 报表分类 */
	private final ReportTypeEnum type;
	
	/** 二级目录名 */
	private final String level2;
	
	/** 报表处理service */
	private final String service;
	
	/** 后缀 */
	private final String suffix;
	
	/** 被引用的上报报表 */
	private final String quoted;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>FormCodeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ReportCodeEnum(String code, ReportTypeEnum type, String level2, String service,
							String suffix, String quoted, String message) {
		this.code = code;
		this.type = type;
		this.level2 = level2;
		this.service = service;
		this.suffix = suffix;
		this.quoted = quoted;
		this.message = message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	
	public ReportTypeEnum getType() {
		return type;
	}
	
	public String getLevel2() {
		return level2;
	}
	
	public String getService() {
		return service;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	public String getQuoted() {
		return quoted;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String message() {
		return message;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return ReportCodeEnum
	 */
	public static ReportCodeEnum getByCode(String code) {
		for (ReportCodeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ReportCodeEnum>
	 */
	public static List<ReportCodeEnum> getAllEnum() {
		List<ReportCodeEnum> list = new ArrayList<ReportCodeEnum>();
		for (ReportCodeEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public static List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (ReportCodeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
