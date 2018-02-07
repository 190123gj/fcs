package com.born.fcs.face.web.controller.customer.channal;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.born.face.webui.table.ReportTemplate;
import com.born.face.webui.table.TableBuilder;
import com.born.fcs.face.integration.pm.service.report.PmReportServiceClient;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.info.ChannelReportInfo;
import com.born.fcs.pm.ws.service.report.order.ChannelReportQueryOrder;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * 渠道报表
 * */
@Controller
@RequestMapping("customerMg/channal/report")
public class ChannalReportController extends BaseController {
	@Autowired
	private PmReportServiceClient pmReportService;
	
	@RequestMapping("{type}.htm")
	public String toReportPage(@PathVariable String type, HttpServletRequest request, Model model) {
		
		ChannelReportQueryOrder order = new ChannelReportQueryOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		String typeName = "渠道报表";
		if (StringUtil.equals(type, "channalProject")) {
			typeName = "渠道在保项目报表";
			
		} else if (StringUtil.equals(type, "firstTen")) {
			//仅担保业务进入该统计范围，除122,123,124,125,126
			typeName = "担保前十大客户报表";
			
		} else if (StringUtil.equals(type, "bank")) {
			typeName = "进出口担保银行合作情况";
			
		} else if (StringUtil.equals(type, "other")) {
			typeName = "进出口担保非银渠道合作情况";
			
		}
		String channelCode = request.getParameter("channelCode");
		if (StringUtil.isNotBlank(channelCode)) {
			String[] arr = channelCode.split(",");
			List<String> list = new ArrayList<>();
			for (String s : arr) {
				list.add(s);
			}
			order.setChannelCodeList(list);
		}
		String busi = request.getParameter("busiType");
		if (StringUtil.isNotBlank(busi)) {
			String[] arr = busi.split(",");
			List<String> list = new ArrayList<>();
			for (String s : arr) {
				list.add(s);
			}
			order.setBusiTypeList(list);
		}
		order.setNeedPage(true);
		order.setTypes(type);
		QueryBaseBatchResult<ChannelReportInfo> result = channelReportClient.list(order);
		if (StringUtil.equals(type, "firstTen")) {
			result.setPageCount(result.getPageCount() > 1 ? 1 : result.getPageCount());
			result.setTotalCount(result.getTotalCount() >= 10 ? 10 : result.getTotalCount());
		}
		setCustomerEnums(model);
		model.addAttribute("page", PageUtil.getCovertPage(result));
		model.addAttribute("type", type);
		model.addAttribute("typeName", typeName);
		model.addAttribute("reqData", WebUtil.getRequestMap(request));
		return "/customerMg/channel/channalReport.vm";
		
	}
	
	/***
	 * 导出
	 * @throws ParseException
	 */
	@RequestMapping("export.htm")
	public String export(String type, String typeName, HttpServletRequest request,
							HttpServletResponse response) throws ParseException {
		
		//		PmReportDOQueryOrder order = new PmReportDOQueryOrder();
		//		HashMap<String, FcsField> fieldMap = new HashMap<>();
		//		for (FcsField info : ReportQueryData.channalProject) {
		//			fieldMap.put(info.getColName(), info);
		//		}
		//		order.setFieldMap(fieldMap);
		//		order
		//			.setSql("SELECT customer_name,project_name,channel_name,sub_channel_name,busi_type_name,start_time,end_time,contract_amount,blance,fan_guarantee_methord FROM view_channel_report WHERE custom_name1='在保' AND busi_type NOT IN ('211','511') ORDER BY raw_update_time DESC");
		//		List<DataListItem> dataList = pmReportServiceClient.doQuery(order);
		//		for (DataListItem data : dataList) {
		//			for (ReportItem itm : data.getValueList()) {
		//				if (StringUtil.isNotBlank(itm.getValue())) {
		//					if (DataTypeEnum.MONEY == itm.getDataTypeEnum()) {
		//						itm.setValue(Money.cent(Long.parseLong(itm.getValue())).toStandardString());
		//					} else if (DataTypeEnum.DATE == itm.getDataTypeEnum()) {
		//						itm.setValue(itm.getValue());
		//					}
		//					
		//				}
		//			}
		//		}
		ChannelReportQueryOrder order = new ChannelReportQueryOrder();
		WebUtil.setPoPropertyByRequest(order, request);
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
		
		ReportDataResult dataResult = new ReportDataResult();
		dataResult.setPageSize(list.size());
		dataResult.setDataList(list);
		dataResult.setFcsFields(ReportQueryData.channalProject);
		ReportTemplate reportTemplate = new ReportTemplate();
		reportTemplate.setReportName(typeName);
		TableBuilder builder = new TableBuilder(dataResult, reportTemplate,
			TableBuilder.Table_Option_Excel, false);
		builder.init();
		builder.dataBind(request, response);
		return null;
	}
}
