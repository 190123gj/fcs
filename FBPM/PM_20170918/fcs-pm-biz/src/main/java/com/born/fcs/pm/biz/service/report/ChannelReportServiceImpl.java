package com.born.fcs.pm.biz.service.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDAOService;
import com.born.fcs.pm.integration.crm.service.customer.ChannelClient;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.ChannelReportService;
import com.born.fcs.pm.ws.service.report.PmReportService;
import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.info.ChannelReportInfo;
import com.born.fcs.pm.ws.service.report.order.ChannelReportQueryOrder;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

@Service("channelReportService")
public class ChannelReportServiceImpl extends BaseAutowiredDAOService implements
																		ChannelReportService {
	
	@Autowired
	private ChannelClient channelClient;
	
	@Autowired
	private PmReportService pmReportService;
	
	@Override
	public QueryBaseBatchResult<ChannelReportInfo> list(ChannelReportQueryOrder order) {
		QueryBaseBatchResult<ChannelReportInfo> result = new QueryBaseBatchResult<ChannelReportInfo>();
		try {
			if (StringUtil.equals(order.getTypes(), "channalProject")) {
				//在保项目
				channalProject(order, result);
			} else if (StringUtil.equals(order.getTypes(), "firstTen")) {
				//担保前十大客户
				firstTen(order, result);
			} else if (StringUtil.equals(order.getTypes(), "bank")) {
				//银行合作情况
				bank(order, result);
			} else if (StringUtil.equals(order.getTypes(), "other")) {
				//非银行合作情况
				other(order, result);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("查询异常");
			logger.error("查询渠道报表异常：", e);
		}
		
		return result;
	}
	
	private void firstTen(ChannelReportQueryOrder order,
							QueryBaseBatchResult<ChannelReportInfo> result) {
		
		String tableName = "project_channel_relation";
		String projectDate = "";
		if (order.isHis()) {
			tableName = "project_channel_relation_his";
			projectDate = " AND c.project_date='" + order.getProjectDate() + "' ";
		}
		
		//数据sql
		String dataSql = "SELECT p.customer_name,p.project_name,GROUP_CONCAT(c.channel_name) 'channel_name',GROUP_CONCAT(c.sub_channel_name) 'sub_channel_name',p.busi_type_name,DATE_FORMAT(p.start_time,'%Y-%m-%d') 'start_time',DATE_FORMAT(p.end_time,'%Y-%m-%d') 'end_time',p.contract_amount,p.blance,p.fan_guarantee_methord FROM "
							+ tableName
							+ " c JOIN project_data_info p ON p.project_code=c.project_code WHERE c.channel_relation='CAPITAL_CHANNEL' AND c.latest='YES' AND p.custom_name1='在保' "
							+ projectDate;
		
		if (ListUtil.isNotEmpty(order.getChannelCodeList())) {
			String types = "";
			for (String s : order.getChannelCodeList()) {
				if (types.length() > 0)
					types += ",'" + s + "'";
				else
					types += "'" + s + "'";
			}
			String codeList = " AND c.channel_code IN (types)".replace("types", types);
			
			dataSql += codeList;
		}
		if (ListUtil.isNotEmpty(order.getBusiTypeList())) {
			String types = "";
			for (String s : order.getChannelCodeList()) {
				if (types.length() > 0)
					types += ",'" + s + "'";
				else
					types += "'" + s + "'";
			}
			String codeList = " AND c.busi_type IN (types)".replace("types", types);
			dataSql += codeList;
		}
		dataSql += " GROUP BY p.project_code ORDER BY p.blance DESC LIMIT 0,10 ";
		
		//查询
		PmReportDOQueryOrder queryOrder = new PmReportDOQueryOrder();
		queryOrder.setSql(dataSql);
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		FcsField f = new FcsField();
		f.setColName("contract_amount");
		f.setDataTypeEnum(DataTypeEnum.MONEY);
		fieldMap.put("contract_amount", f);
		FcsField f1 = new FcsField();
		f1.setColName("blance");
		f1.setDataTypeEnum(DataTypeEnum.MONEY);
		fieldMap.put("blance", f1);
		queryOrder.setFieldMap(fieldMap);
		List<DataListItem> list = pmReportService.doQuery(queryOrder);
		//处理结果
		List<ChannelReportInfo> pageList = new ArrayList<ChannelReportInfo>();
		if (ListUtil.isNotEmpty(list)) {
			ChannelReportInfo info = null;
			for (DataListItem itm : list) {
				info = new ChannelReportInfo();
				HashMap<String, Object> map = itm.getMap();
				info.setCustomerName(String.valueOf(map.get("customer_name")));
				info.setProjectName(String.valueOf(map.get("project_name")));
				info.setChannelName(String.valueOf(map.get("channel_name")));
				info.setSubChannelName(String.valueOf(map.get("sub_channel_name")));
				info.setBusiTypeName(String.valueOf(map.get("busi_type_name")));
				info.setStartTime(String.valueOf(map.get("start_time")));
				info.setEndTime(String.valueOf(map.get("end_time")));
				info.setContractAmount(Money.amout(String.valueOf(map.get("contract_amount"))));
				info.setBlance(Money.amout(String.valueOf(map.get("blance"))));
				info.setFanGuaranteeMethord(String.valueOf(map.get("fan_guarantee_methord"))
					.replace("null", ""));
				pageList.add(info);
			}
			result.setSuccess(true);
			result.setPageList(pageList);
			PageComponent page = new PageComponent(order, pageList.size());
			result.initPageParam(page);
			
		}
		
	}
	
	private void bank(ChannelReportQueryOrder order, QueryBaseBatchResult<ChannelReportInfo> result) {
		
		String tableName = "project_channel_relation";
		String projectDate = "";
		if (order.isHis()) {
			tableName = "project_channel_relation_his";
			projectDate = " AND c.project_date='" + order.getProjectDate() + "' ";
		}
		
		//数据sql
		String dataSql = "SELECT c.channel_id,c.channel_name,IFNULL(SUM(c.in_amount),0) amount,IFNULL(SUM(c.in_project),0) num FROM "
							+ tableName
							+ " c  WHERE c.channel_relation='CAPITAL_CHANNEL' AND c.latest='YES' AND c.channel_type='YH'"
							+ projectDate;
		
		if (ListUtil.isNotEmpty(order.getChannelCodeList())) {
			String types = "";
			for (String s : order.getChannelCodeList()) {
				if (types.length() > 0)
					types += ",'" + s + "'";
				else
					types += "'" + s + "'";
			}
			String codeList = " AND c.channel_code IN (types)".replace("types", types);
			
			dataSql += codeList;
		}
		
		dataSql += " GROUP BY c.channel_name ORDER BY amount DESC  ";
		
		//统计	
		long count = pmReportService.doQueryCount(dataSql);
		PageComponent page = new PageComponent(order, count);
		
		if (order.isNeedPage())
			dataSql += "LIMIT " + page.getFirstRecord() + "," + page.getPageSize() + "";
		
		//查询
		PmReportDOQueryOrder queryOrder = new PmReportDOQueryOrder();
		queryOrder.setSql(dataSql);
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		FcsField f = new FcsField();
		f.setColName("amount");
		f.setDataTypeEnum(DataTypeEnum.MONEY);
		fieldMap.put("amount", f);
		queryOrder.setFieldMap(fieldMap);
		List<DataListItem> list = pmReportService.doQuery(queryOrder);
		//处理结果
		List<ChannelReportInfo> pageList = new ArrayList<ChannelReportInfo>();
		if (ListUtil.isNotEmpty(list)) {
			ChannelReportInfo info = null;
			for (DataListItem itm : list) {
				HashMap<String, Object> map = itm.getMap();
				info = setValue(Long.parseLong(String.valueOf(map.get("channel_id"))));
				info.setChannelName(String.valueOf(map.get("channel_name")));
				info.setInProject(checkNum(map.get("num")));
				info.setContractAmount(Money.amout(String.valueOf(map.get("amount"))));
				
				pageList.add(info);
			}
			result.setSuccess(true);
			result.setPageList(pageList);
			if (order.isNeedPage())
				result.initPageParam(page);
			else
				result.setTotalCount(count);
			
		}
		
	}
	
	private void other(ChannelReportQueryOrder order, QueryBaseBatchResult<ChannelReportInfo> result) {
		
		String tableName = "project_channel_relation";
		String projectDate = "";
		if (order.isHis()) {
			tableName = "project_channel_relation_his";
			projectDate = " AND c.project_date='" + order.getProjectDate() + "' ";
		}
		
		//数据sql
		String dataSql = "SELECT c.channel_name,IFNULL(SUM(c.in_amount),0) amount,IFNULL(SUM(c.in_project),0) num FROM "
							+ tableName
							+ " c  WHERE c.channel_relation='CAPITAL_CHANNEL' AND c.latest='YES' AND c.channel_type !='YH'"
							+ projectDate;
		
		if (ListUtil.isNotEmpty(order.getChannelCodeList())) {
			String types = "";
			for (String s : order.getChannelCodeList()) {
				if (types.length() > 0)
					types += ",'" + s + "'";
				else
					types += "'" + s + "'";
			}
			String codeList = " AND c.channel_code IN (types)".replace("types", types);
			
			dataSql += codeList;
		}
		
		dataSql += " GROUP BY c.channel_name ORDER BY amount DESC  ";
		
		//统计	
		long count = pmReportService.doQueryCount(dataSql);
		PageComponent page = new PageComponent(order, count);
		
		if (order.isNeedPage())
			dataSql += "LIMIT " + page.getFirstRecord() + "," + page.getPageSize() + "";
		
		//查询
		PmReportDOQueryOrder queryOrder = new PmReportDOQueryOrder();
		queryOrder.setSql(dataSql);
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		FcsField f = new FcsField();
		f.setColName("amount");
		f.setDataTypeEnum(DataTypeEnum.MONEY);
		fieldMap.put("amount", f);
		queryOrder.setFieldMap(fieldMap);
		List<DataListItem> list = pmReportService.doQuery(queryOrder);
		//处理结果
		List<ChannelReportInfo> pageList = new ArrayList<ChannelReportInfo>();
		if (ListUtil.isNotEmpty(list)) {
			ChannelReportInfo info = null;
			for (DataListItem itm : list) {
				HashMap<String, Object> map = itm.getMap();
				info = new ChannelReportInfo();
				info.setChannelName(String.valueOf(map.get("channel_name")));
				info.setInProject(checkNum(map.get("num")));
				info.setBlance(Money.amout(String.valueOf(map.get("amount"))));
				pageList.add(info);
			}
			result.setSuccess(true);
			result.setPageList(pageList);
			if (order.isNeedPage())
				result.initPageParam(page);
			else
				result.setTotalCount(count);
			
		}
		
	}
	
	/**
	 * 渠道在保项目
	 * */
	private void channalProject(ChannelReportQueryOrder order,
								QueryBaseBatchResult<ChannelReportInfo> result) {
		
		String tableName = "project_channel_relation";
		String projectDate = "";
		if (order.isHis()) {
			tableName = "project_channel_relation_his";
			projectDate = " AND c.project_date='" + order.getProjectDate() + "' ";
		}
		
		//数据sql
		String dataSql = "SELECT p.customer_name,p.project_name,GROUP_CONCAT(c.channel_name) 'channel_name',GROUP_CONCAT(c.sub_channel_name) 'sub_channel_name',p.busi_type_name,DATE_FORMAT(p.start_time,'%Y-%m-%d') 'start_time',DATE_FORMAT(p.end_time,'%Y-%m-%d') 'end_time',p.contract_amount,p.blance,p.fan_guarantee_methord FROM "
							+ tableName
							+ " c JOIN project_data_info p ON p.project_code=c.project_code WHERE c.channel_relation='CAPITAL_CHANNEL' AND c.latest='YES' AND p.custom_name1='在保' "
							+ projectDate;
		
		if (ListUtil.isNotEmpty(order.getChannelCodeList())) {
			String types = "";
			for (String s : order.getChannelCodeList()) {
				if (types.length() > 0)
					types += ",'" + s + "'";
				else
					types += "'" + s + "'";
			}
			String codeList = " AND c.channel_code IN (types)".replace("types", types);
			
			dataSql += codeList;
		}
		if (ListUtil.isNotEmpty(order.getBusiTypeList())) {
			String types = "";
			for (String s : order.getBusiTypeList()) {
				if (types.length() > 0)
					types += ",'" + s + "'";
				else
					types += "'" + s + "'";
			}
			
			String codeList = " AND c.busi_type IN (types)".replace("types", types);
			dataSql += codeList;
		}
		dataSql += " GROUP BY p.project_code ORDER BY p.raw_add_time DESC  ";
		
		//统计	
		long count = pmReportService.doQueryCount(dataSql);
		PageComponent page = new PageComponent(order, count);
		
		if (order.isNeedPage())
			dataSql += "LIMIT " + page.getFirstRecord() + "," + page.getPageSize() + "";
		
		//查询
		PmReportDOQueryOrder queryOrder = new PmReportDOQueryOrder();
		queryOrder.setSql(dataSql);
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		FcsField f = new FcsField();
		f.setColName("contract_amount");
		f.setDataTypeEnum(DataTypeEnum.MONEY);
		fieldMap.put("contract_amount", f);
		FcsField f1 = new FcsField();
		f1.setColName("blance");
		f1.setDataTypeEnum(DataTypeEnum.MONEY);
		fieldMap.put("blance", f1);
		queryOrder.setFieldMap(fieldMap);
		List<DataListItem> list = pmReportService.doQuery(queryOrder);
		//处理结果
		List<ChannelReportInfo> pageList = new ArrayList<ChannelReportInfo>();
		if (ListUtil.isNotEmpty(list)) {
			ChannelReportInfo info = null;
			for (DataListItem itm : list) {
				info = new ChannelReportInfo();
				HashMap<String, Object> map = itm.getMap();
				info.setCustomerName(String.valueOf(map.get("customer_name")));
				info.setProjectName(String.valueOf(map.get("project_name")));
				info.setChannelName(String.valueOf(map.get("channel_name")));
				info.setSubChannelName(String.valueOf(map.get("sub_channel_name")));
				info.setBusiTypeName(String.valueOf(map.get("busi_type_name")));
				info.setStartTime(String.valueOf(map.get("start_time")));
				info.setEndTime(String.valueOf(map.get("end_time")));
				info.setContractAmount(Money.amout(String.valueOf(map.get("contract_amount"))));
				info.setBlance(Money.amout(String.valueOf(map.get("blance"))));
				info.setFanGuaranteeMethord(String.valueOf(map.get("fan_guarantee_methord"))
					.replace("null", ""));
				pageList.add(info);
			}
			result.setSuccess(true);
			result.setPageList(pageList);
			if (order.isNeedPage())
				result.initPageParam(page);
			else
				result.setTotalCount(count);
			
		}
		
	}
	
	/** 渠道信息写入 */
	private ChannelReportInfo setValue(long id) {
		ChannelReportInfo channelReportInfo = new ChannelReportInfo();
		ChannalInfo channalInfo = channelClient.queryById(id);
		
		if (channalInfo != null) {
			BeanCopier.staticCopy(channalInfo, channelReportInfo);
			String creditAmount = "";
			if (StringUtil.equals(BooleanEnum.IS.code(), channalInfo.getIsCreditAmount())) {
				creditAmount = channalInfo.getCreditAmount().toStandardString() + "元";
			}
			if (StringUtil.equals(BooleanEnum.IS.code(), channalInfo.getIsTimes())) {
				if (StringUtil.isNotBlank(creditAmount))
					creditAmount += " 且不超过净资产的" + channalInfo.getTimes() + "倍";
				else
					creditAmount += "不超过净资产的" + channalInfo.getTimes() + "倍";
				
			}
			String singleLimit = "";
			if (StringUtil.equals(BooleanEnum.IS.code(), channalInfo.getIsSingleLimit())) {
				singleLimit = channalInfo.getSingleLimit().toStandardString() + "元";
			}
			if (StringUtil.equals(BooleanEnum.IS.code(), channalInfo.getIsPercent())) {
				if (StringUtil.isNotBlank(creditAmount))
					singleLimit += "且不超过净资产的" + channalInfo.getPercent() + "%";
				else
					singleLimit += "不超过净资产的" + channalInfo.getPercent() + "%";
			}
			String compensatoryLimit = "无期限";
			if (channalInfo.getCompensatoryLimit() != -1)
				compensatoryLimit = "到达授信截止日后 "
									+ channalInfo.getCompensatoryLimit()
									+ "个"
									+ (StringUtil.equals("ZR", channalInfo.getDayType()) ? "自然日"
										: "工作日")
									+ (StringUtil.equals(BooleanEnum.IS.code(),
										channalInfo.getStraddleYear()) ? " 不跨年" : "");
			
			channelReportInfo.setSingleLimitString(singleLimit);
			channelReportInfo.setCompensatoryString(compensatoryLimit);
			channelReportInfo.setCreditAmountString(creditAmount);
		}
		
		return channelReportInfo;
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
}
