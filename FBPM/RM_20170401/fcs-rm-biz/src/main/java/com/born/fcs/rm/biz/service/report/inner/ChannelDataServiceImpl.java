package com.born.fcs.rm.biz.service.report.inner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportItem;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.integration.service.pm.PmReportServiceClient;
import com.born.fcs.rm.ws.info.report.ChannelClassfyOrder;
import com.born.fcs.rm.ws.info.report.inner.ExpectReleaseInfo;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Service("channelDataService")
public class ChannelDataServiceImpl extends BaseAutowiredDomainService implements
																		ReportProcessService {
	
	@Autowired
	private PmReportServiceClient pmReportServiceClient;
	
	@Override
	public FcsBaseResult save(ReportOrder order) {
		return null;
	}
	
	@Override
	public Object findByAccountPeriod(ReportQueryOrder queryOrder) {
		
		ChannelClassfyOrder order = new ChannelClassfyOrder();
		BeanCopier.staticCopy(queryOrder, order);
		List<ExpectReleaseInfo> list1 = query(order, "PROJECT_CHANNEL");//按项目渠道分类		
		List<ExpectReleaseInfo> list2 = queryCap(order);//按资金渠道分类
		List<ExpectReleaseInfo> list = new ArrayList<ExpectReleaseInfo>();//按资金渠道分类
		list.add(typeInfo("一 、按项目渠道分类："));
		list.addAll(list1);
		list.add(typeInfo("二 、按资金渠道分类："));
		list.addAll(list2);
		QueryBaseBatchResult<ExpectReleaseInfo> result = new QueryBaseBatchResult<ExpectReleaseInfo>();
		result.setPageList(list);
		
		if (ListUtil.isNotEmpty(list))
			result.setSuccess(true);
		result.setMessage("查询成功");
		return result;
		
	}
	
	private List<ExpectReleaseInfo> query(ChannelClassfyOrder order, String PROJECT_CHANNEL) {
		
		int reportYear = order.getReportYear();
		int reportMonth = order.getReportMonth();
		String tableName = " project_data_info_his_data ";
		String projectDateSql = "";
		//当前期最后一天时间
		String thisEndDay = DateUtil.dtSimpleFormat(DateUtil.getEndTimeByYearAndMonth(reportYear,
			reportMonth));
		if (order.isNowMoth()) {
			tableName = " project_data_info ";
		} else {
			projectDateSql = "  AND pd.project_date= '" + thisEndDay + "'";
		}
		
		//本期时间段开始
		String startTime = DateUtil.simpleFormat(DateUtil.getStartTimeByYearAndMonth(reportYear,
			reportMonth));
		//本期时间段结束
		String endTime = DateUtil.simpleFormat(DateUtil.getEndTimeByYearAndMonth(reportYear,
			reportMonth));
		//去年最后一天
		String lastYearEndDay = order.getCurrYearLast(reportYear - 1);
		//当前期年最后一天
		String yearEndDay = order.getCurrYearLast(reportYear);
		//当前期年初第一天
		String yearBeginDay = DateUtil.simpleFormat(DateUtil.getStartTimeByYearAndMonth(reportYear,
			1));
		String sql = "";
		if (StringUtil.equals(PROJECT_CHANNEL, "PROJECT_CHANNEL")) {
			sql = setQuerySql_project(order);
		}
		//参数替换
		sql = sql.replaceAll("tableName", tableName).replaceAll("startTime", startTime)
			.replaceAll("endTime", endTime).replaceAll("lastYearEndDay", lastYearEndDay)
			.replaceAll("yearEndDay", yearEndDay).replaceAll("projectDateSql", projectDateSql)
			.replace("yearBeginDay", yearBeginDay).replaceAll("thisEndDay", thisEndDay);
		
		String[] sqls = sql.split("&");
		Map<String, String> rs = new HashMap<String, String>();
		for (String s : sqls) {
			logger.info("");
			getRsValues(s, rs);
		}
		
		List<ExpectReleaseInfo> list = new ArrayList<ExpectReleaseInfo>();
		if (!rs.isEmpty()) {
			List<ChanalTypeEnum> types = ChanalTypeEnum.getAllEnum();
			for (ChanalTypeEnum e : types) {
				String type = e.code();
				ExpectReleaseInfo info = rsInfo(e.getMessage());
				if (rs.containsKey(type + "count1"))
					info.setData2(String.valueOf(rs.get(type + "count1")));
				if (rs.containsKey(type + "count2"))
					info.setData3(String.valueOf(rs.get(type + "count2")));
				if (rs.containsKey(type + "count3"))
					info.setData4(String.valueOf(rs.get(type + "count3")));
				if (rs.containsKey(type + "count4"))
					info.setData5(String.valueOf(rs.get(type + "count4")));
				if (rs.containsKey(type + "count5"))
					info.setData6(toMoneyStr(rs.get(type + "count5")));
				if (rs.containsKey(type + "count6"))
					info.setData7(toMoneyStr(rs.get(type + "count6")));
				if (rs.containsKey(type + "count7"))
					info.setData8(toMoneyStr(rs.get(type + "count7")));
				if (rs.containsKey(type + "count8"))
					info.setData9(toMoneyStr(rs.get(type + "count8")));
				if (rs.containsKey(type + "count9"))
					info.setData10(toMoneyStr(rs.get(type + "count9")));
				if ("合计".equals(e.getCode())) {
					info.setData2(checkNum(rs.get("hjcount1")));
					info.setData4(checkNum(rs.get("hjcount3")));
				}
				list.add(info);
			}
			
		}
		return list;
	}
	
	/** 资金渠道数据 */
	private List<ExpectReleaseInfo> queryCap(ChannelClassfyOrder order) {
		
		int reportYear = order.getReportYear();
		int reportMonth = order.getReportMonth();
		String tableName = " project_channel_relation_his ";
		String projectDateSql = "";
		//当前期最后一天时间
		String thisEndDay = DateUtil.dtSimpleFormat(DateUtil.getEndTimeByYearAndMonth(reportYear,
			reportMonth));
		if (order.isNowMoth()) {
			tableName = " project_channel_relation ";
		} else {
			projectDateSql = "  AND project_date= '" + thisEndDay + "'";
		}
		
		//本期时间段开始
		String startTime = DateUtil.simpleFormat(DateUtil.getStartTimeByYearAndMonth(reportYear,
			reportMonth));
		//本期时间段结束
		String endTime = DateUtil.simpleFormat(DateUtil.getEndTimeByYearAndMonth(reportYear,
			reportMonth));
		//去年最后一天
		String lastYearEndDay = order.getCurrYearLast(reportYear - 1);
		//当前期年最后一天
		String yearEndDay = order.getCurrYearLast(reportYear);
		//当前期年初第一天
		String yearBeginDay = reportYear + "-01-01";
		
		String sql = getCapSql(order);
		
		//参数替换
		sql = sql.replaceAll("tableName", tableName).replaceAll("startTime", startTime)
			.replaceAll("endTime", endTime).replaceAll("lastYearEndDay", lastYearEndDay)
			.replaceAll("yearEndDay", yearEndDay).replaceAll("projectDateSql", projectDateSql)
			.replace("yearBeginDay", yearBeginDay).replaceAll("thisEndDay", thisEndDay);
		
		String[] sqls = sql.split("&");
		Map<String, String> rs = new HashMap<String, String>();
		for (String s : sqls) {
			logger.info("");
			getRsValues(s, rs);
		}
		
		List<ExpectReleaseInfo> list = new ArrayList<ExpectReleaseInfo>();
		if (!rs.isEmpty()) {
			List<ChanalTypeEnum> types = ChanalTypeEnum.getAllEnum();
			for (ChanalTypeEnum e : types) {
				String type = e.code();
				ExpectReleaseInfo info = rsInfo(e.getMessage());
				if (rs.containsKey(type + "count1"))
					info.setData2(checkNum(rs.get(type + "count1")));
				if (rs.containsKey(type + "count2"))
					info.setData3(checkNum(rs.get(type + "count2")));
				if (rs.containsKey(type + "count3"))
					info.setData4(checkNum(rs.get(type + "count3")));
				if (rs.containsKey(type + "count4"))
					info.setData5(checkNum(rs.get(type + "count4")));
				if (rs.containsKey(type + "count5"))
					info.setData6(toMoneyStr(rs.get(type + "count5")));
				if (rs.containsKey(type + "count6"))
					info.setData7(toMoneyStr(rs.get(type + "count6")));
				if (rs.containsKey(type + "count7")) {
					info.setData8(toMoneyStr(rs.get(type + "count7")));
				}
				if (rs.containsKey(type + "count8")) {
					info.setData9(toMoneyStr(rs.get(type + "count8")));
					
				}
				if (rs.containsKey(type + "count9"))
					info.setData10(toMoneyStr(rs.get(type + "count9")));
				if ("合计".equals(e.getCode())) {
					info.setData2(checkNum(rs.get("hjcount1")));
					info.setData4(checkNum(rs.get("hjcount3")));
				}
				
				list.add(info);
			}
			
		}
		return list;
	}
	
	private String checkNum(Object obj) {
		if ("null".equals(String.valueOf(obj))) {
			return "0";
		}
		Double d = Double.parseDouble(String.valueOf(obj));
		if (d == 0) {
			return "0";
		} else {
			if (d * 100 % 100 == 0) {
				return String.valueOf(d).replace(".0", "");
			} else if (d * 100 % 10 == 0) {
				return String.valueOf(d * 10 / 10.0);
			} else {
				return String.valueOf(Math.round(d * 100) / 100.00);
			}
		}
		
	}
	
	/** 过滤掉渠道表多余的渠道 项目渠道 */
	private static String channel_table_project = "SELECT * FROM project_channel_relation WHERE channel_relation='PROJECT_CHANNEL' AND latest='YES'";
	
	/**
	 * 按项目渠道分类 ：统计立项通过以后的项目
	 * 
	 * 设置查询sql
	 * 
	 * 类型统一为type字段，并且放在查询第一位,数据存/取格式：type+字段别名
	 */
	public String setQuerySql_project(ChannelClassfyOrder order) {
		String sql = "";
		//新增户数
		sql = "SELECT pc.channel_type as type, COUNT(DISTINCT pd.customer_id) count1 FROM  tableName  pd LEFT JOIN (channel_table) pc ON pd.project_code = pc.project_code WHERE (pd.phases!='SET_UP_PHASES' OR (pd.phases='SET_UP_PHASES' AND pd.phases_status='APPROVAL')) AND pd.customer_add_time > 'startTime' AND 'endTime'  > pd.customer_add_time    projectDateSql AND pc.channel_type IS NOT NULL GROUP BY pc.channel_type ";
		//新增户数合计
		sql += "&SELECT 'hj' as type, COUNT(DISTINCT pd.customer_id) count1 FROM  tableName  pd LEFT JOIN (channel_table) pc ON pd.project_code = pc.project_code WHERE (pd.phases!='SET_UP_PHASES' OR (pd.phases='SET_UP_PHASES' AND pd.phases_status='APPROVAL')) AND pd.customer_add_time > 'startTime' AND 'endTime'  > pd.customer_add_time    projectDateSql AND pc.channel_type IS NOT NULL ";
		
		//新增笔数
		sql += "&SELECT  pc.channel_type as type, COUNT(DISTINCT pd.project_code) count2 FROM  tableName pd LEFT JOIN (channel_table) pc ON pd.project_code = pc.project_code WHERE (pd.phases!='SET_UP_PHASES' OR (pd.phases='SET_UP_PHASES' AND pd.phases_status='APPROVAL')) AND pd.apply_date > 'startTime' AND  'endTime'  > pd.apply_date   projectDateSql GROUP BY pc.channel_type ";
		//在保户数，笔数
		sql += "&SELECT  pc.channel_type as type, COUNT(DISTINCT pd.customer_id) count3,COUNT(DISTINCT pd.project_code) count4 FROM  tableName pd LEFT JOIN (channel_table) pc ON pd.project_code = pc.project_code WHERE  pd.custom_name1='在保'     projectDateSql GROUP BY pc.channel_type ";
		//在保户数合计
		sql += "&SELECT  'hj' as type, COUNT(DISTINCT pd.customer_id) count3  FROM  (channel_table) pc JOIN  tableName pd  ON pd.project_code = pc.project_code WHERE  pd.custom_name1='在保' projectDateSql";
		//合同金额
		sql += "&SELECT  dd.channel_type as type, SUM(dd.contract_amount) count5  FROM (SELECT DISTINCT pd.project_code, pd.contract_amount ,pc.channel_type FROM  project_data_info pd LEFT JOIN (channel_table) pc ON pd.project_code = pc.project_code WHERE pd.contract_time >= 'yearBeginDay' AND pd.contract_time <='endTime' ) dd GROUP BY dd.channel_type";
		//年初在保余额
		
		sql += "&SELECT  pc.channel_type as type, SUM(pd.blance) count6  FROM project_data_info_his_data pd LEFT JOIN (channel_table) pc ON pd.project_code = pc.project_code WHERE  pd.project_date= 'lastYearEndDay'  GROUP BY pc.channel_type ";
		
		//本年累计发生额
		sql += "&SELECT p.channel_type AS type ,IFNULL(SUM(v.occur_amount),0) AS count7  FROM view_project_actual_occer_detail v JOIN project_channel_relation p ON v.project_code=p.project_code WHERE  v.occur_date >= 'yearBeginDay' AND v.occur_date <= 'thisEndDay' AND p.channel_relation='PROJECT_CHANNEL' AND p.latest='YES' AND p.channel_type IN ('YH','ZQ','XT','JYPT','JJ','ZL','WLJR','QT','BGS','ZZYX')  GROUP BY p.channel_type";
		
		//本年累计解保金额
		sql += "&SELECT p.channel_type AS type, IFNULL(SUM(v.repay_amount),0) AS count8  FROM view_project_repay_detail v JOIN project_channel_relation p ON v.project_code=p.project_code WHERE  v.repay_time >= 'yearBeginDay' AND v.repay_time <= 'thisEndDay' AND ((v.busi_type='411' AND v.repay_type REGEXP '^代偿|^解保') OR v.busi_type!='411') AND p.channel_relation='PROJECT_CHANNEL' AND p.latest='YES' AND p.channel_type IN ('YH','ZQ','XT','JYPT','JJ','ZL','WLJR','QT','BGS','ZZYX') GROUP BY p.channel_type";
		
		// 期末在保余额
		sql += "&SELECT  pc.channel_type as type, SUM(pd.blance) count9  FROM tableName pd LEFT JOIN (channel_table) pc ON pd.project_code = pc.project_code WHERE 1=1   projectDateSql  GROUP BY pc.channel_type";
		
		return sql.replaceAll("channel_table", channel_table_project);
		
	}
	
	private String getCapSql(ChannelClassfyOrder order) {
		//在保余额
		String sql = "SELECT channel_type AS type,SUM(in_amount) AS count9 FROM tableName WHERE channel_type IN ('YH','ZQ','XT','JYPT','JJ','ZL','WLJR','QT','BGS','ZZYX')  projectDateSql AND latest = 'YES' AND channel_relation='CAPITAL_CHANNEL' GROUP BY channel_type";
		
		//新增笔数,在保笔数  2 4
		sql += "&SELECT SUM(new_project) count2 ,SUM(in_project) count4, channel_type as type FROM tableName c  WHERE  c.latest='YES' AND c.channel_relation='CAPITAL_CHANNEL'   projectDateSql AND c.channel_type  IN ('YH','ZQ','XT','JYPT','JJ','ZL','WLJR','QT','BGS','ZZYX') GROUP BY c.channel_type ";
		
		//新增客户 1 
		sql += "&SELECT d.channel_type AS type, SUM(d.new_customer) AS count1  FROM (SELECT MAX(new_customer) new_customer ,channel_type FROM tableName c LEFT JOIN project_data_info p ON c.project_code=p.project_code WHERE c.channel_relation='CAPITAL_CHANNEL' AND c.latest='YES' AND c.new_customer > 0  projectDateSql AND c.channel_type  IN ('YH','ZQ','XT','JYPT','JJ','ZL','WLJR','QT','BGS','ZZYX') GROUP BY p.customer_id ,c.channel_type ) d GROUP BY d.channel_type";
		
		//新增客户合计 1 
		sql += "&SELECT 'hj' AS type, SUM(d.new_customer) AS count1  FROM (SELECT MAX(new_customer) new_customer ,channel_type FROM tableName c LEFT JOIN project_data_info p ON c.project_code=p.project_code WHERE c.channel_relation='CAPITAL_CHANNEL' AND c.latest='YES' AND c.new_customer > 0  projectDateSql AND c.channel_type  IN ('YH','ZQ','XT','JYPT','JJ','ZL','WLJR','QT','BGS','ZZYX') GROUP BY p.customer_id ) d ";
		
		//再包户数 3
		sql += "&SELECT d.channel_type AS type, SUM(d.in_cutomer) AS count3  FROM (SELECT MAX(in_cutomer) in_cutomer ,channel_type FROM tableName c LEFT JOIN project p ON c.project_code=p.project_code WHERE c.channel_relation='CAPITAL_CHANNEL' AND c.in_cutomer > 0  projectDateSql AND c.channel_type  IN ('YH','ZQ','XT','JYPT','JJ','ZL','WLJR','QT','BGS','ZZYX') GROUP BY p.customer_id ,c.channel_type ) d GROUP BY d.channel_type";
		
		//再包户数合计 3
		sql += "&SELECT 'hj' AS type, SUM(d.in_cutomer) AS count3  FROM (SELECT MAX(in_cutomer) in_cutomer ,channel_type FROM tableName c LEFT JOIN project p ON c.project_code=p.project_code WHERE c.channel_relation='CAPITAL_CHANNEL' AND c.in_cutomer > 0  projectDateSql AND c.channel_type  IN ('YH','ZQ','XT','JYPT','JJ','ZL','WLJR','QT','BGS','ZZYX') GROUP BY p.customer_id ) d";
		
		//年初在保余额
		sql += "&SELECT channel_type AS type,IFNULL(SUM(in_amount),0) AS count6 FROM project_channel_relation_his WHERE project_date='lastYearEndDay' AND  channel_type IN ('YH','ZQ','XT','JYPT','JJ','ZL','WLJR','QT','BGS','ZZYX') AND latest = 'YES' AND channel_relation='CAPITAL_CHANNEL' GROUP BY channel_type  ";
		//合同金额 本年
		sql += "&SELECT pc.channel_type AS type,SUM(pc.contract_amount) AS count5 FROM project_channel_relation pc join project_data_info pd on pc.project_code = pd.project_code WHERE pd.contract_time >= 'yearBeginDay' AND pd.contract_time <='endTime' AND  pc.channel_type IN ('YH','ZQ','XT','JYPT','JJ','ZL','WLJR','QT','BGS','ZZYX')   AND pc.latest = 'YES' AND pc.channel_relation='CAPITAL_CHANNEL'  GROUP BY pc.channel_type ";
		
		//本年累计发生额
		sql += "&SELECT capital_channel_type AS type ,IFNULL(SUM(occur_amount),0) AS count7  FROM view_project_actual_occer_detail   WHERE  occur_date >= 'yearBeginDay' AND occur_date <= 'thisEndDay' AND capital_channel_type IN ('YH','ZQ','XT','JYPT','JJ','ZL','WLJR','QT','BGS','ZZYX')  GROUP BY capital_channel_type";
		//本年累计解保额
		sql += "&SELECT capital_channel_type AS type, IFNULL(SUM(repay_amount),0) AS count8  FROM view_project_repay_detail  WHERE  repay_time >= 'yearBeginDay' AND repay_time <= 'thisEndDay' AND ((busi_type='411' AND repay_type REGEXP '^代偿|^解保') OR busi_type!='411') AND capital_channel_type IN ('YH','ZQ','XT','JYPT','JJ','ZL','WLJR','QT','BGS','ZZYX') GROUP BY capital_channel_type ";
		return sql;
	}
	
	// 类型统一为type字段，并且放在查询第一位,数据存/取格式：type+字段别名
	public void getRsValues(String sql, Map<String, String> rs) {
		//查询数据
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(sql);
		List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
		if (ListUtil.isNotEmpty(dataResult)) {
			for (DataListItem itms : dataResult) {
				String type = String.valueOf(itms.getMap().get("type"));
				for (ReportItem itm : itms.getValueList()) {
					if (StringUtil.equals("type", itm.getKey()) || "null".equals(type))
						continue;
					//暂存结果
					if (rs.containsKey(type + itm.getKey())) {
						//key值相同的累加
						try {
							long value = Long.parseLong(itm.getValue() + "")
											+ Long.parseLong(rs.get(type + itm.getKey()));
							rs.put(type + itm.getKey(), String.valueOf(value));
						} catch (Exception e) {
							double value = Double.parseDouble(itm.getValue() + "")
											+ Double.parseDouble(rs.get(type + itm.getKey()));
							rs.put(type + itm.getKey(), String.valueOf(value));
						}
						
					} else {
						rs.put(type + itm.getKey(), itm.getValue());
					}
					
					//合计
					if (rs.containsKey("合计" + itm.getKey())) {
						//key值相同的累加
						try {
							long value = Long.parseLong(itm.getValue() + "")
											+ Long.parseLong(rs.get("合计" + itm.getKey()));
							rs.put("合计" + itm.getKey(), String.valueOf(value));
						} catch (NumberFormatException e) {
							double value = Double.parseDouble(itm.getValue() + "")
											+ Double.parseDouble(rs.get("合计" + itm.getKey()));
							rs.put("合计" + itm.getKey(), String.valueOf(value));
						}
						
					} else {
						rs.put("合计" + itm.getKey(), itm.getValue());
					}
					
				}
				
			}
		}
		
	}
	
	/** 默认返回 */
	private ExpectReleaseInfo rsInfo(String typeName) {
		ExpectReleaseInfo info = new ExpectReleaseInfo();
		info.setData1(typeName);
		info.setData2("0");
		info.setData3("0");
		info.setData4("0");
		info.setData5("0");
		info.setData6("0.00");
		info.setData7("0.00");
		info.setData8("0.00");
		info.setData9("0.00");
		info.setData9("0.00");
		return info;
	}
	
	/** 分类 */
	private ExpectReleaseInfo typeInfo(String typeName) {
		ExpectReleaseInfo info = new ExpectReleaseInfo();
		info.setData1(typeName);
		info.setData2("");
		info.setData3("");
		info.setData4("");
		info.setData5("");
		info.setData6("");
		info.setData7("");
		info.setData8("");
		info.setData9("");
		info.setData9("");
		info.setData10("");
		info.setData11("total");
		return info;
	}
	
	private enum ChanalTypeEnum {
		YH("YH", "银行"),
		ZQ("ZQ", "证券公司"),
		XT("XT", "信托公司"),
		JYPT("JYPT", "交易平台"),
		JJ("JJ", "基金公司"),
		ZL("ZL", "租赁公司"),
		WLJR("WLJR", "网络金融平台"),
		ZZYX("ZZYX", "自主营销"),
		QT("QT", "其他渠道"),
		BGS("BGS", "本公司"),
		合计("合计", "合计");
		/** 枚举值 */
		private final String code;
		/** 枚举描述 */
		private final String message;
		
		/**
		 * 
		 * @param code 枚举值
		 * @param message 枚举描述
		 */
		private ChanalTypeEnum(String code, String message) {
			this.code = code;
			this.message = message;
		}
		
		/**
		 * @return Returns the code.
		 */
		public String getCode() {
			return code;
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
		 * @return ChanalTypeEnum
		 */
		public static ChanalTypeEnum getByCode(String code) {
			for (ChanalTypeEnum _enum : values()) {
				if (_enum.getCode().equals(code)) {
					return _enum;
				}
			}
			return null;
		}
		
		/**
		 * 获取全部枚举
		 * 
		 * @return List<ChanalTypeEnum>
		 */
		public static java.util.List<ChanalTypeEnum> getAllEnum() {
			java.util.List<ChanalTypeEnum> list = new java.util.ArrayList<ChanalTypeEnum>(
				values().length);
			for (ChanalTypeEnum _enum : values()) {
				list.add(_enum);
			}
			return list;
		}
		
		/**
		 * 获取全部枚举值
		 * 
		 * @return List<String>
		 */
		public static java.util.List<String> getAllEnumCode() {
			java.util.List<String> list = new java.util.ArrayList<String>(values().length);
			for (ChanalTypeEnum _enum : values()) {
				list.add(_enum.code());
			}
			return list;
		}
		
		/**
		 * 通过code获取msg
		 * @param code 枚举值
		 * @return
		 */
		public static String getMsgByCode(String code) {
			if (code == null) {
				return null;
			}
			ChanalTypeEnum _enum = getByCode(code);
			if (_enum == null) {
				return null;
			}
			return _enum.getMessage();
		}
		
		/**
		 * 获取枚举code
		 * @param _enum
		 * @return
		 */
		public static String getCode(ChanalTypeEnum _enum) {
			if (_enum == null) {
				return null;
			}
			return _enum.getCode();
		}
	}
	
}
