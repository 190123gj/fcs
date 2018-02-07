package com.born.face.webui.table;

import java.util.List;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import com.born.fcs.pm.ws.service.report.result.ReportItem;

public class TableBuilderTest {
	public static void main(String[] args) {
		//"D:\\test.xls";
		makeExcel(args);
		makeHtml(args);
	}
	
	public static void makeHtml(String[] args) {
		ReportDataResult dataResult = makeResult();
		ReportTemplate reportTemplate = new ReportTemplate();
		//多行表头
		String[][] head = new String[2][3];
		head[0] = new String[] { "大类", "大类", "数据" };
		head[1] = new String[] { "类型", "名称", "数据" };
		reportTemplate.setColHeadString(head);
		
		reportTemplate.setMergeRow(true);
		reportTemplate.setMergeColCount(2);
		TableBuilder builder = new TableBuilder(dataResult, reportTemplate,
			TableBuilder.Table_Option_Html, false);
		builder.init();
		builder.dataBind(null);
		System.out.print(builder.getString());
	}
	
	public static void makeExcel(String[] args) {
		ReportDataResult dataResult = makeResult();
		ReportTemplate reportTemplate = new ReportTemplate();
		reportTemplate.setReportName("我的测试报表");
		String[][] head = new String[2][3];
		head[0] = new String[] { "大类", "大类", "数据" };
		head[1] = new String[] { "类型", "名称", "数据" };
		reportTemplate.setColHeadString(head);
		reportTemplate.setMergeRow(true);
		reportTemplate.setMergeColCount(2);
		TableBuilder builder = new TableBuilder(dataResult, reportTemplate,
			TableBuilder.Table_Option_Excel, false);
		builder.init();
		builder.dataBind(null, null);
		
	}
	
	private static ReportDataResult makeResult() {
		ReportDataResult dataResult = new ReportDataResult();
		dataResult.getFcsFields().add(new FcsField("type", "类型", null));
		dataResult.getFcsFields().add(new FcsField("name", "名称", null));
		dataResult.getFcsFields().add(new FcsField("data", "数据", null, DataTypeEnum.MONEY));
		List<DataListItem> dataListItems = Lists.newArrayList();
		DataListItem item = new DataListItem();
		List<ReportItem> valueList = Lists.newArrayList();
		ReportItem reportItem = new ReportItem();
		reportItem.setKey("name");
		reportItem.setValue("宝马");
		reportItem.setDataTypeEnum(DataTypeEnum.STRING);
		
		valueList.add(reportItem);
		reportItem = new ReportItem();
		reportItem.setKey("type");
		reportItem.setValue("汽车");
		reportItem.setDataTypeEnum(DataTypeEnum.STRING);
		
		valueList.add(reportItem);
		
		reportItem = new ReportItem();
		reportItem.setKey("data");
		reportItem.setValue("1000000");
		reportItem.setDataTypeEnum(DataTypeEnum.MONEY);
		
		valueList.add(reportItem);
		
		item.setValueList(valueList);
		dataListItems.add(item);
		item = new DataListItem();
		reportItem = new ReportItem();
		reportItem.setKey("name");
		reportItem.setValue("宝马");
		reportItem.setDataTypeEnum(DataTypeEnum.STRING);
		
		valueList.add(reportItem);
		reportItem = new ReportItem();
		reportItem.setKey("type");
		reportItem.setValue("汽车");
		reportItem.setDataTypeEnum(DataTypeEnum.STRING);
		
		valueList.add(reportItem);
		
		reportItem = new ReportItem();
		reportItem.setKey("data");
		reportItem.setValue("2000000");
		reportItem.setDataTypeEnum(DataTypeEnum.MONEY);
		
		valueList.add(reportItem);
		
		item.setValueList(valueList);
		
		dataListItems.add(item);
		
		reportItem = new ReportItem();
		reportItem.setKey("data");
		reportItem.setValue("1000000");
		reportItem.setDataTypeEnum(DataTypeEnum.MONEY);
		
		valueList.add(reportItem);
		
		item.setValueList(valueList);
		dataListItems.add(item);
		
		item = new DataListItem();
		reportItem = new ReportItem();
		reportItem.setKey("name");
		reportItem.setValue("501");
		reportItem.setDataTypeEnum(DataTypeEnum.STRING);
		
		valueList.add(reportItem);
		reportItem = new ReportItem();
		reportItem.setKey("type");
		reportItem.setValue("船");
		reportItem.setDataTypeEnum(DataTypeEnum.STRING);
		
		valueList.add(reportItem);
		
		reportItem = new ReportItem();
		reportItem.setKey("data");
		reportItem.setValue("5000000");
		reportItem.setDataTypeEnum(DataTypeEnum.MONEY);
		
		valueList.add(reportItem);
		
		item.setValueList(valueList);
		
		dataListItems.add(item);
		
		item = new DataListItem();
		reportItem = new ReportItem();
		reportItem.setKey("name");
		reportItem.setValue("502");
		reportItem.setDataTypeEnum(DataTypeEnum.STRING);
		
		valueList.add(reportItem);
		reportItem = new ReportItem();
		reportItem.setKey("type");
		reportItem.setValue("船");
		reportItem.setDataTypeEnum(DataTypeEnum.STRING);
		
		valueList.add(reportItem);
		
		reportItem = new ReportItem();
		reportItem.setKey("data");
		reportItem.setValue("5000000");
		reportItem.setDataTypeEnum(DataTypeEnum.MONEY);
		
		valueList.add(reportItem);
		
		item.setValueList(valueList);
		
		dataListItems.add(item);
		
		dataResult.setDataList(dataListItems);
		return dataResult;
	}
}
