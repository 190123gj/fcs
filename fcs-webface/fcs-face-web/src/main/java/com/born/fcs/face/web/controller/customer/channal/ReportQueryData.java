package com.born.fcs.face.web.controller.customer.channal;

import java.util.ArrayList;
import java.util.List;

import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.field.FcsFunctionEnum;
import com.google.common.collect.Lists;

/**
 * 报表查询条件设置
 * */
public class ReportQueryData {
	/** 渠道在保项目 */
	public static List<FcsField> channalProject = Lists.newArrayList();
	static {//customer_name,project_name,channel_name,sub_channel_name,busi_type_name,start_time,end_time,contract_amount,blance,
		channalProject.add(new FcsField("customer_name", "客户名称", null, DataTypeEnum.STRING));
		channalProject.add(new FcsField("project_name", "项目名称", null, DataTypeEnum.STRING));
		channalProject.add(new FcsField("channel_name", "合作渠道", null, DataTypeEnum.STRING));
		channalProject.add(new FcsField("sub_channel_name", "二级渠道", null, DataTypeEnum.STRING));
		channalProject.add(new FcsField("busi_type_name", "授信品种", null, DataTypeEnum.STRING));
		channalProject.add(new FcsField("start_time", "授信起始时间", null, DataTypeEnum.DATE));
		channalProject.add(new FcsField("end_time", "授信截止", null, DataTypeEnum.DATE));
		channalProject.add(new FcsField("contract_amount", "合同金额", null, DataTypeEnum.MONEY));
		channalProject.add(new FcsField("blance", "本年余额", null, DataTypeEnum.MONEY));
		channalProject
			.add(new FcsField("fan_guarantee_methord", "反担保措施", null, DataTypeEnum.STRING));
	}
	
	/** 担保前十大客户 */
	public static List<FcsField> firstTen = Lists.newArrayList();
	static {
		firstTen.add(new FcsField("customer_name", "客户名称", null, DataTypeEnum.STRING));
		firstTen.add(new FcsField("project_name", "项目名称", null, DataTypeEnum.STRING));
		firstTen.add(new FcsField("busi_type_name", "授信品种", null, DataTypeEnum.STRING));
		firstTen.add(new FcsField("start_time", "授信起始时间", null, DataTypeEnum.DATE));
		firstTen.add(new FcsField("end_time", "授信截止时间", null, DataTypeEnum.DATE));
		firstTen.add(new FcsField("contract_amount", "合同金额", null, DataTypeEnum.MONEY));
		firstTen.add(new FcsField("blance", "本年余额", null, DataTypeEnum.MONEY));
		firstTen.add(new FcsField("fan_guarantee_methord", "反担保措施", null, DataTypeEnum.STRING));
		
	}
	/** 银行渠道合作情况 */
	public static List<FcsField> bank = Lists.newArrayList();
	static {
		bank.add(new FcsField("capital_channel_name", "合作渠道", null, DataTypeEnum.STRING));
		bank.add(new FcsField("channal_credit_amount", "授信额度", FcsFunctionEnum.NO_GROUP_BY,
			DataTypeEnum.STRING));
		bank.add(new FcsField("channal_bond_rate", "保证金比例", FcsFunctionEnum.NO_GROUP_BY,
			DataTypeEnum.STRING));
		bank.add(new FcsField("channal_credit_startDate", "授信起始时间", FcsFunctionEnum.NO_GROUP_BY,
			DataTypeEnum.DATE));
		bank.add(new FcsField("channal_credit_endDate", "授信截止时间", FcsFunctionEnum.NO_GROUP_BY,
			DataTypeEnum.DATE));
		bank.add(new FcsField("channal_loss_allocationRate", "风险分摊比例", FcsFunctionEnum.NO_GROUP_BY,
			DataTypeEnum.BIGDECIMAL));
		bank.add(new FcsField("channal_single_limit", "单笔限额", FcsFunctionEnum.NO_GROUP_BY,
			DataTypeEnum.STRING));
		bank.add(new FcsField("blance", "本年余额", FcsFunctionEnum.SUM, DataTypeEnum.MONEY));
		bank.add(new FcsField("channal_code", "担保笔数", FcsFunctionEnum.COUNT,
			DataTypeEnum.BIGDECIMAL));
		bank.add(new FcsField("channal_compensatory_limit", "代偿期限", FcsFunctionEnum.NO_GROUP_BY,
			DataTypeEnum.STRING));
		//		bank.add(new FcsField("blance", "本年余额", null, DataTypeEnum.STRING));
		//		
		//		bank.add(new FcsField("capital_channel_id", "渠道Id", null, DataTypeEnum.BIGDECIMAL));
	}
	/** 非银行渠道合作情况 */
	public static List<FcsField> other = Lists.newArrayList();
	static {
		other.add(new FcsField("capital_channel_name", "合作渠道", null, DataTypeEnum.STRING));
		other.add(new FcsField("blance", "本年余额", FcsFunctionEnum.SUM, DataTypeEnum.MONEY));
		other.add(new FcsField("channal_code", "担保笔数", FcsFunctionEnum.COUNT,
			DataTypeEnum.BIGDECIMAL));
		
	}
	/** 包含查询除时间外的所有条件 */
	public static List<String> reqData = new ArrayList<>();
	static {
		//渠道编号
		reqData.add("channal_code");
		//授信品种
		reqData.add("busi_type");
	}
}
