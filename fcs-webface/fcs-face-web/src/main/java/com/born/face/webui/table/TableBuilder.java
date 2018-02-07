package com.born.face.webui.table;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.CellType;
import jxl.CellView;
import jxl.Range;
import jxl.WorkbookSettings;
import jxl.write.Alignment;
import jxl.write.Border;
import jxl.write.BorderLineStyle;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.VerticalAlignment;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.born.face.webui.control.ComponentConstant;
import com.born.face.webui.control.Table;
import com.born.face.webui.control.TableCell;
import com.born.face.webui.control.TableRow;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class TableBuilder {
	protected static final Logger logger = LoggerFactory.getLogger(TableBuilder.class);
	public static final int COL_MIN_COUNT = 9;
	
	public static final int SCREEN_DEFAUL_WIDTH = 200;
	
	public static final int DEFAUL_COl_WIDTH = 20;
	public static final int PAGE_SISE = 25;
	
	public static final int Table_Option_Excel = 0;
	public static final int Table_Option_Html = 1;
	List<DataListItem> dataList = null;
	List<Map<String, Object>> dataMapList = null;
	List<String> colOrderList = new LinkedList<String>();
	List<String> headOrderList = new LinkedList<String>();
	List<String> headViewColList = new LinkedList<String>();
	String[] headList = null;
	Map[] headGoupList = null;
	List<Map<String, Object>> headSortList = new LinkedList();
	List headValueList = new ArrayList(20);
	Map headMap = new HashMap();
	List groupHeadList = new LinkedList();
	Map firstMap = new HashMap();
	Map secondMap = new HashMap();
	Map thirdMap = new HashMap();
	int colHeadCount = 0;
	int colHeadOrderCount = 0;
	int colCount = 0;
	int colOrderCount = 0;
	int[] colHeadOrderArray;
	int[] colOrderArray;
	
	Table dt = null;
	int tableOption = Table_Option_Html;
	boolean isExsitHead = false;
	long recordCount = 0;
	private String excelFileName;
	ReportTemplate reportTemplate;
	List<FcsField> fcsFields;
	boolean isCrossReporting = false;
	boolean needPages = false;
	long pageSize;
	long pageNumber;
	String[][] colHeadString;
	
	public TableBuilder(ReportDataResult dataResult, ReportTemplate reportTemplate,
						int tableOption, boolean isCrossReporting) {
		dataList = dataResult.getDataList();
		this.tableOption = tableOption;
		colCount = reportTemplate.getColCount();
		colHeadCount = reportTemplate.getColHeadCount();
		colHeadOrderArray = reportTemplate.getColHeadOrderArray();
		colOrderList = reportTemplate.getColOrderList();
		this.reportTemplate = reportTemplate;
		this.isCrossReporting = isCrossReporting;
		headOrderList = reportTemplate.getHeadOrderList();
		if (colHeadCount > 0) {
			isExsitHead = true;
		} else if (reportTemplate.getColHeadString() != null) {
			isExsitHead = true;
		}
		fcsFields = dataResult.getFcsFields();
		if (dataResult.getPageSize() > 0) {
			needPages = false;
			recordCount = dataResult.getTotalCount();
			pageSize = dataResult.getPageSize();
			pageNumber = dataResult.getPageNumber();
		}
		
	}
	
	public void init() {
		
		headOrderList = new ArrayList<String>();
		
		if ((!this.reportTemplate.isOrderBy() && isExsitHead)
			|| (colOrderArray != null && colOrderArray.length > 0 && !isExsitHead)) {
			dataMapList = MiscUtil.sortMaps(dataMapList, colOrderList);
		}
		if (isCrossReporting && isExsitHead) {
			String oldValue = null;
			int rowCount = 0;
			Iterator it = dataMapList.iterator();
			while (it.hasNext()) {
				Map itemMap = (Map) it.next();
				HeadValue headV = new HeadValue();
				Map headSortMap = new HashMap();
				for (int i = 0; i < headOrderList.size(); i++) {
					String fieldName = headOrderList.get(i);
					Object o = itemMap.get(fieldName);
					headSortMap.put(fieldName, o);
					headV.addValue(o);
					Map groupMap = headGoupList[i];
					if (o != null) {
						if (!groupMap.containsKey(o)) {
							groupMap.put(o, itemMap.get(headList[i]));
						}
					}
				}
				if (!headMap.containsKey(headV)) {
					headMap.put(headV, null);
					headSortList.add(headSortMap);
				} else {
					headSortMap = null;
					headV = null;
				}
				String value = null;
				for (int i = 1; i <= colCount; i++) {
					value += ReportUtil.objectToString(itemMap.get(fcsFields.get(i - 1)
						.getColName()));
				}
				if (value != null) {
					if (oldValue == null || !value.equals(oldValue)) {
						rowCount++;
					}
					oldValue = value;
				}
			}
			
			headSortList = MiscUtil.sortMaps(headSortList, headOrderList);
			Iterator headSortListIt = headSortList.iterator();
			for (int i = 0; i < headSortList.size(); i++) {
				HeadValue headV = new HeadValue();
				Map itemMap = (Map) headSortListIt.next();
				
				headValueList.add(headV);
			}
			recordCount = rowCount;
		}
	}
	
	public void dataBind(HttpServletRequest request) {
		builderHTMLTable(request);
	}
	
	public void dataBind(HttpServletRequest request, HttpServletResponse response) {
		if (this.tableOption == Table_Option_Html) {
			builderHTMLTable(request);
		} else {
			exportExcelToCrystalReeport1(request, response);
		}
	}
	
	private void builderHTMLTable(HttpServletRequest request) {
		if (isExsitHead && isCrossReporting) {
			dt = new Table("", colCount + headSortList.size(), headOrderList.size());
			
		} else {
			if (isExsitHead) {
				dt = new Table("", fcsFields.size(), reportTemplate.getColHeadString().length);
			} else {
				dt = new Table("", fcsFields.size(), 1);
			}
			
		}
		int[] colWidth = new int[dt.columnSize()];
		HtmlBuilderUtil.initDefaultWidth(colWidth, 80);
		dt.setBorder(1);
		dt.setCellSpacing(1);
		dt.setCellPadding(5);
		dt.setAttribute("bordercolorlight", "#000000");
		dt.setAttribute("bordercolordark", "#000000");
		dt.setStyle("border-collapse", "collapse");
		dt.setStyle("table-layout", "fixed");
		dt.setAttribute("align", "center");
		TableRow dr = dt.getRow(0);
		TableRow headDr = dr;
		for (int i = 0; i < dt.columnSize(); i++) {
			TableCell cell = dr.getTableCell(i);
			String strText = reportTemplate.getColNameByIndex(i + 1);
			colWidth[i] = HtmlBuilderUtil.getTextWidth(strText, colWidth[i]);
			cell.setText(strText);
			cell.setHorizontalAlign(ComponentConstant.ALIGN_CENTER);
			cell.setRowSpan(headOrderList.size());
		}
		if (isExsitHead && isCrossReporting) {
			Iterator headSortIt = headSortList.iterator();
			for (int j = colCount; j < colCount + headSortList.size(); j++) {
				Map headItemMap = (Map) headSortIt.next();
				for (int i = 0; i < headOrderList.size(); i++) {
					dr = dt.getRow(i);
					Object o = headItemMap.get(headOrderList.get(i));
					String strText = (String) headGoupList[i].get(o);
					colWidth[j] = HtmlBuilderUtil.getTextWidth(strText, colWidth[j]);
					TableCell cell = dr.getTableCell(j);
					cell.setHorizontalAlign(ComponentConstant.ALIGN_CENTER);
					cell.setText(strText);
					if (i > 0) {
						TableRow predr = dt.getRow(i - 1);
						TableCell prevCell = predr.getTableCell(j);
						if (prevCell.getText() != null && prevCell.getText().equals(strText)) {
							prevCell.setRowSpan(2);
							cell.setVisible(false);
						}
					}
					headDr = dr;
				}
			}
			if (headOrderList.size() > 1) {
				GridBuilder.MergColTable(dt, headOrderList.size(), colCount);
			}
			for (int i = 1; i < headOrderList.size(); i++) {
				TableRow tempDr = dt.getRow(i);
				for (int j = 0; j < colCount; j++) {
					TableCell cell = tempDr.getTableCell(j);
					cell.setVisible(false);
				}
				
			}
		} else if (isExsitHead && reportTemplate.getColHeadString() != null) {
			for (int i = 0; i < reportTemplate.getColHeadString().length; i++) {
				String[] headArray = reportTemplate.getColHeadString()[i];
				TableRow tempDr = dt.getRow(i);
				for (int j = 0; j < headArray.length; j++) {
					TableCell cell = tempDr.getTableCell(j);
					cell.setHorizontalAlign(ComponentConstant.ALIGN_CENTER);
					cell.setText(headArray[j]);
				}
			}
			GridBuilder.MergColTable(dt, reportTemplate.getColHeadString().length, 0);
			GridBuilder.MergHeadTable(dt, 0, reportTemplate.getColHeadString()[0].length, 0);//(dt, reportTemplate.getColHeadString().length, 0);
		} else {
			for (int i = 0; i < fcsFields.size(); i++) {
				TableCell cell = headDr.getTableCell(i);
				cell.setHorizontalAlign(ComponentConstant.ALIGN_CENTER);
				cell.setText(fcsFields.get(i).getName());
			}
		}
		
		PaginationObject page = new PaginationObject(request, recordCount, (String) null, pageSize,
			pageNumber);
		if (request != null) {
			request.setAttribute("page", page);
			request.setAttribute("tableColCount", new Integer(dt.columnSize()));
			
		}
		Iterator<DataListItem> it = dataList.iterator();
		String oldValue = null;
		int rowCount = 0;
		while (it.hasNext()) {
			DataListItem dataRowMap = it.next();
			String value = null;
			if (this.isCrossReporting) {
				for (int i = 1; i <= colCount; i++) {
					value += ReportUtil.objectToString(dataRowMap.getMap().get(
						colOrderList.get(i - 1)));
				}
				if (value != null || !this.isExsitHead) {
					if ((oldValue == null || !value.equals(oldValue)) || !this.isExsitHead) {
						
						if (page.getFirstRecord() <= rowCount && rowCount < page.getLastRecord()) {
							dr = dt.addTableRow();
							for (int i = 0; i < dt.columnSize(); i++) {
								if (i < colCount) {
									TableCell cell = dr.getTableCell(i);
									String tempValue = ReportUtil.objectToString(dataRowMap
										.getMap().get((colOrderList.get((i)))));
									if (tempValue == null)
										tempValue = "&nbsp;";
									colWidth[i] = HtmlBuilderUtil.getTextWidth(tempValue,
										colWidth[i]);
									cell.setText(tempValue);
								} else {
									TableCell cell = dr.getTableCell(i);
									cell.setText("&nbsp;");
								}
							}
						}
						rowCount++;
						oldValue = value;
					}
					if (page.getFirstRecord() < rowCount && rowCount <= page.getLastRecord()) {
						if (this.isExsitHead) {
							int dataIndex = getColIndex(dataRowMap);
							String tempValue = ReportUtil.objectToString(dataRowMap.getMap().get(
								"DATACOL1"));
							if (tempValue == null)
								tempValue = "&nbsp;";
							colWidth[dataIndex] = HtmlBuilderUtil.getTextWidth(tempValue,
								colWidth[dataIndex]);
							dr.getTableCell(dataIndex).setText(tempValue);
						}
					}
				}
			} else {
				dr = dt.addTableRow();
				for (int i = 0; i < dt.columnSize(); i++) {
					TableCell cell = dr.getTableCell(i);
					String tempValue = ReportUtil.objectToString(dataRowMap.getMap().get(
						(fcsFields.get(i).getColName())));
					if (tempValue == null)
						tempValue = "&nbsp;";
					colWidth[i] = HtmlBuilderUtil.getTextWidth(tempValue, colWidth[i]);
					cell.setText(tempValue);
				}
			}
		}
		int totalWidth = 0;
		TableRow colHeaddr = dt.getRow(0);
		
		for (int i = 0; i < colWidth.length; i++) {
			if (i < colCount) {
				colHeaddr.getTableCell(i).setWidth(((colWidth[i] + 10) + "px"));
			} else {
				TableCell tempCell = headDr.getTableCell(i);
				if (tempCell.getColumnSpan() > 1) {
					int cellTotalWidth = colWidth[i];
					for (int k = 1; k < tempCell.getColumnSpan(); k++) {
						cellTotalWidth += colWidth[i + k];
					}
					headDr.getTableCell(i).setWidth(((cellTotalWidth + 10) + "px"));
				} else {
					headDr.getTableCell(i).setWidth(((colWidth[i] + 10) + "px"));
				}
				
			}
			totalWidth += colWidth[i] + 15;
		}
		dt.setWidth(totalWidth + "px");
		if (reportTemplate.isMergeRow()) {
			if (reportTemplate.getMergeColCount() > 0) {
				GridBuilder.MergTable(dt, reportTemplate.getMergeColCount(), headOrderList.size());
			} else {
				GridBuilder.MergTable(dt, colCount, headOrderList.size());
			}
			
		}
		
	}
	
	public String getString() {
		String str = dt.getInnerHtml();
		return str;
	}
	
	public int getColIndex(DataListItem itemMap) {
		
		HeadValue headV = new HeadValue();
		for (int i = 0; i < headOrderList.size(); i++) {
			String fieldName = headOrderList.get(i);
			Object o = itemMap.getMap().get(fieldName);
			headV.addValue(o);
		}
		return headValueList.indexOf(headV) + this.colCount;
	}
	
	/**
	 * 导出excel
	 * @param response
	 */
	public void exportExcelToCrystalReport(HttpServletRequest request, HttpServletResponse response) {
		
		String titleName = this.reportTemplate.getReportName();
		//titleName="xx";
		if (titleName.indexOf("<") > 0) {
			titleName = titleName.substring(0, titleName.indexOf("<"));
		}
		String convertTitleName = ReportUtil.convert(titleName, "gb2312", "ISO8859_1");
		response.setHeader("Content-Disposition", "attachment;filename=" + convertTitleName
													+ ".xls");
		
		Map map = null;
		map = new HashMap<>();
		
		ExcelPrint.exportExcelReport(request, titleName, map, this.dataList, fcsFields, response,
			this.excelFileName, null);
		
	}
	
	/**
	 * 
	 * @param response
	 */
	public void exportExcelToCrystalReeport1(HttpServletRequest request,
												HttpServletResponse response) {
		
		String titleName = reportTemplate.getReportName();
		String strTemplateFile = "";
		//titleName="xx";
		String convertTitleName = ReportUtil.convert(titleName, "gb2312", "ISO8859_1");
		
		if (response != null)
			response.setHeader("Content-Disposition", "attachment;filename=" + convertTitleName
														+ ".xls");
		if (StringUtil.isEmpty(strTemplateFile)) {
			try {
				autoExportExcel1(response, titleName);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		} else {
			Map map = new HashMap<>();
			
			ExcelPrint.exportExcelReport(request, titleName, map, this.dataList, fcsFields,
				response, strTemplateFile, null);
		}
	}
	
	private void autoExportExcel1(HttpServletResponse response, String titleName)
																					throws RowsExceededException,
																					WriteException {
		int columnCount = colCount + headSortList.size();
		if (isExsitHead && isCrossReporting) {
			columnCount = colCount + headSortList.size();
		} else {
			columnCount = fcsFields.size();
			
		}
		int realCount = columnCount;
		if (columnCount < COL_MIN_COUNT) {
			columnCount = COL_MIN_COUNT;
		}
		OutputStream jspos = null;
		try {
			if (response != null)
				jspos = response.getOutputStream();
		} catch (IOException e1) {
			logger.error(e1.getMessage(), e1);
		}
		jxl.write.WritableWorkbook wb = null;
		try {
			WorkbookSettings ws = new WorkbookSettings();
			ws.setLocale(Locale.CHINESE);
			if (jspos == null)
				jspos = new FileOutputStream("D:\\test.xls");
			wb = jxl.Workbook.createWorkbook(jspos, ws);
			
		} catch (IOException e1) {
			logger.error(e1.getMessage(), e1);
		}
		WritableSheet sheet = wb.createSheet("sheet", 0);
		sheet.getSettings().setShowGridLines(true);
		sheet.getSettings().setDefaultColumnWidth(DEFAUL_COl_WIDTH);
		CellView headRowView = new CellView();
		headRowView.setSize(500);
		sheet.setRowView(0, headRowView);
		
		WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
		WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
		WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
		wcf_center.setAlignment(Alignment.CENTRE);
		
		int rowCount = 0;
		jxl.write.Label label_head = null;
		for (short i = 0; i < columnCount; i++) {
			if (i == 0) {
				label_head = new jxl.write.Label(i, rowCount, titleName, wcf_center);
				sheet.addCell(label_head);
			}
			
		}
		sheet.mergeCells(0, rowCount, (COL_MIN_COUNT - 1), rowCount);
		if (reportTemplate.getCaptionProperty().size() > 0) {
			for (int i = 0; i < 3; i++) {
				rowCount++;
				
				for (short j = 0; j < COL_MIN_COUNT; j++) {
					label_head = new jxl.write.Label(j, rowCount, "");
					sheet.addCell(label_head);
					if (j >= 0 && j < 3) {
						String str = reportTemplate.getCaptionProperty().get(
							"left" + (i + 1) + "Caption");
						if (StringUtil.isNotEmpty(str)) {
							String str1 = reportTemplate.getCaptionProperty().get(
								"left" + (i + 1) + "Value");
							label_head.setString(str + "  " + str1);
						} else {
							String str1 = reportTemplate.getCaptionProperty().get(
								"left" + (i + 1) + "Value");
							if (str1 == null)
								str1 = "";
							label_head.setString(str1);
						}
					}
					if (j >= 3 && j < 6) {
						String str = reportTemplate.getCaptionProperty().get(
							"center" + (i + 1) + "Caption");
						
						if (StringUtil.isNotEmpty(str)) {
							String str1 = reportTemplate.getCaptionProperty().get(
								"center" + (i + 1) + "Value");
							label_head.setString(str + "  " + str1);
						} else {
							String str1 = reportTemplate.getCaptionProperty().get(
								"center" + (i + 1) + "Value");
							if (str1 == null)
								str1 = "";
							label_head.setString(str1);
						}
					}
					if (j >= 6 && j < 9) {
						String str = reportTemplate.getCaptionProperty().get(
							"right" + (i + 1) + "Caption");
						
						if (StringUtil.isNotEmpty(str)) {
							String str1 = reportTemplate.getCaptionProperty().get(
								"right" + (i + 1) + "Value");
							label_head.setString(str + "  " + str1);
						} else {
							String str1 = reportTemplate.getCaptionProperty().get(
								"right" + (i + 1) + "Value");
							if (str1 == null)
								str1 = "";
							label_head.setString(str + "  " + str1);
						}
					}
				}
				sheet.mergeCells(0, rowCount, 2, rowCount);
				sheet.mergeCells(3, rowCount, 5, rowCount);
				sheet.mergeCells(6, rowCount, 8, rowCount);
			}
		}
		
		rowCount++;
		int bankRowIndex = rowCount;
		if (this.isExsitHead & this.isCrossReporting) {
			for (int i = 0; i < this.headOrderList.size(); i++) {
				rowCount++;
				for (int j = 0; j < realCount; j++) {
					label_head = new jxl.write.Label(j, rowCount, "");
					sheet.addCell(label_head);
					WritableCellFormat thickLeft = new WritableCellFormat();
					thickLeft.setBorder(Border.LEFT, BorderLineStyle.THIN);
					thickLeft.setBorder(Border.RIGHT, BorderLineStyle.THIN);
					thickLeft.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
					thickLeft.setBorder(Border.TOP, BorderLineStyle.THIN);
					thickLeft.setAlignment(Alignment.CENTRE);
					label_head.setCellFormat(thickLeft);
					//cell.setCellValue(" ");
				}
				if (isExsitHead && headOrderList.size() > 1) {
					sheet.mergeCells(i, bankRowIndex + 1, i,
						bankRowIndex + 1 + this.headOrderList.size() - 1);
				}
			}
			Map mergMap = new HashMap();
			MergColTableExcel(sheet, bankRowIndex + 1, headOrderList.size(), colCount, realCount,
				mergMap);
			sheet.getSettings().setVerticalFreeze(bankRowIndex + 2 + headOrderList.size());
			
		} else if (reportTemplate.getColHeadString() != null) {
			for (int i = 0; i < reportTemplate.getColHeadString().length; i++) {
				rowCount++;
				String[] headArray = reportTemplate.getColHeadString()[i];
				for (int j = 0; j < headArray.length; j++) {
					label_head = new jxl.write.Label(j, rowCount, "");
					sheet.addCell(label_head);
					WritableCellFormat thickLeft = new WritableCellFormat();
					thickLeft.setBorder(Border.LEFT, BorderLineStyle.THIN);
					thickLeft.setBorder(Border.RIGHT, BorderLineStyle.THIN);
					thickLeft.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
					thickLeft.setBorder(Border.TOP, BorderLineStyle.THIN);
					thickLeft.setAlignment(Alignment.CENTRE);
					label_head.setCellFormat(thickLeft);
					label_head.setString(headArray[j]);
					if (i == reportTemplate.getColHeadString().length - 1) {
						int colWidth = ExcelPrintUtil.getTextWidthDef80(headArray[j]);
						CellView colCV = new CellView();
						colCV.setSize(colWidth);
						sheet.setColumnView(j, colCV);
					}
				}
			}
			Map mergMap = new HashMap();
			MergColTableExcel(sheet, bankRowIndex + 1, reportTemplate.getColHeadString().length, 0,
				columnCount, mergMap);
			mergMap = new HashMap();
			MergTableExcel(sheet, (short) fcsFields.size(), bankRowIndex + 1, mergMap);
			sheet.getSettings().setVerticalFreeze(
				bankRowIndex + 1 + reportTemplate.getColHeadString().length);
		} else {
			rowCount++;
			for (int j = 0; j < realCount; j++) {
				label_head = new jxl.write.Label(j, rowCount, "");
				sheet.addCell(label_head);
				WritableCellFormat thickLeft = new WritableCellFormat();
				thickLeft.setBorder(Border.LEFT, BorderLineStyle.THIN);
				thickLeft.setBorder(Border.RIGHT, BorderLineStyle.THIN);
				thickLeft.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
				thickLeft.setBorder(Border.TOP, BorderLineStyle.THIN);
				thickLeft.setAlignment(Alignment.CENTRE);
				label_head.setCellFormat(thickLeft);
				String text = fcsFields.get(j).getName();
				int colWidth = ExcelPrintUtil.getTextWidthDef80(text);
				CellView colCV = new CellView();
				sheet.setColumnView(j, colCV);
				label_head.setString(text);
			}
			sheet.getSettings().setVerticalFreeze(bankRowIndex + 1);
		}
		
		Iterator<DataListItem> it = dataList.iterator();
		String oldValue = null;
		int rowCountIndex = 0;
		HSSFRow hssRow = null;
		while (it.hasNext()) {
			DataListItem dataRowMap = it.next();
			if (this.isCrossReporting) {
				String value = null;
				for (int i = 1; i <= colCount; i++) {
					value += ReportUtil
						.objectToString(dataRowMap.getMap().get(colOrderList.get(i)));
				}
				if (value != null) {
					if (oldValue == null || !value.equals(oldValue)) {
						rowCount++;
						//hssRow = sheet.createRow(sheet.getLastRowNum() + 1);
						rowCountIndex++;
						for (int i = 0; i < realCount; i++) {
							//HSSFCell cell = hssRow.createCell((short) i);
							label_head = new jxl.write.Label(i, rowCount, "");
							sheet.addCell(label_head);
							WritableCellFormat thickLeft = new WritableCellFormat();
							//HSSFCellStyle cellStyle = wb.createCellStyle();
							if (i == 0) {
								thickLeft.setBorder(Border.LEFT, BorderLineStyle.THIN);
								thickLeft.setBorder(Border.RIGHT, BorderLineStyle.THIN);
								//cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
								//cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
							} else {
								thickLeft.setBorder(Border.RIGHT, BorderLineStyle.THIN);
							}
							//cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
							thickLeft.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
							if (i < colCount) {
								//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
								//cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
								//cell.setCellStyle(cellStyle);
								thickLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
								String valueText = ReportUtil.objectToString(dataRowMap.getMap()
									.get("COL" + (i + 1)));
								if (valueText == null) {
									valueText = "";
								}
								
								int colWidth = ExcelPrintUtil.getTextWidthDef80(valueText);
								if (sheet.getColumnView(i).getSize() < colWidth) {
									//sheet.setColumnWidth((short)i,(short)colWidth);	
									CellView colCV = new CellView();
									colCV.setSize(colWidth);
									sheet.setColumnView(i, colCV);
								}
								label_head.setString(valueText);
							} else {
								
							}
							label_head.setCellFormat(thickLeft);
							
						}
						oldValue = value;
					}
					if (isExsitHead) {
						int dataIndex = getColIndex(dataRowMap);
						label_head = (Label) sheet.getCell(dataIndex, rowCount);
						label_head.setString(ReportUtil.objectToString(dataRowMap.getMap().get(
							"DATACOL1")));
					}
				}
				
			} else {
				rowCount++;
				//hssRow = sheet.createRow(sheet.getLastRowNum() + 1);
				rowCountIndex++;
				//TODO
				for (int i = 0; i < fcsFields.size(); i++) {
					label_head = new jxl.write.Label(i, rowCount, "");
					sheet.addCell(label_head);
					String tempValue = ReportUtil.objectToString(dataRowMap.getMap().get(
						(fcsFields.get(i).getColName())));
					
					WritableCellFormat thickLeft = new WritableCellFormat();
					if (i == 0) {
						thickLeft.setBorder(Border.LEFT, BorderLineStyle.THIN);
						thickLeft.setBorder(Border.RIGHT, BorderLineStyle.THIN);
					} else {
						
						thickLeft.setBorder(Border.RIGHT, BorderLineStyle.THIN);
					}
					if (i < fcsFields.size()) {
						thickLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
						
						int colWidth = ExcelPrintUtil.getTextWidthDef80(tempValue);
						if (sheet.getColumnView(i).getSize() < colWidth) {
							CellView colCV = new CellView();
							colCV.setSize(colWidth);
							sheet.setColumnView(i, colCV);
						}
						label_head.setString(tempValue);
					}
					
					thickLeft.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
					label_head.setCellFormat(thickLeft);
					
				}
			}
			
		}
		if (isExsitHead && isCrossReporting) {
			Map mergMap = new HashMap();
			MergTableExcel(sheet, (short) colCount, headOrderList.size() + bankRowIndex + 1,
				mergMap);
		} else if (reportTemplate.isMergeRow) {
			Map mergMap = new HashMap();
			if (reportTemplate.getColHeadString() != null) {
				MergTableExcel(sheet, (short) reportTemplate.mergeColCount,
					reportTemplate.getColHeadString().length + bankRowIndex + 1, mergMap);
			} else {
				MergTableExcel(sheet, (short) reportTemplate.mergeColCount, 1 + bankRowIndex + 1,
					mergMap);
			}
			
		}
		//		Range range = sheet.mergeCells(2, 6, 2, 7);
		//		for (Range range1 : sheet.getMergedCells()) {
		//			System.out.println(range1.toString());
		//		}
		
		//OutputStream jspos = null;
		try {
			//jspos=response.getOutputStream();
			
			wb.write();
			wb.close();
			jspos.flush();
			jspos.close();
			jspos = null;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jspos != null) {
				try {
					jspos.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param response
	 * @param titleName
	 */
	private void autoExportExcel(HttpServletResponse response, String titleName) {
		HSSFWorkbook wb = makeWorkBook(titleName, null);
		OutputStream jspos = null;
		try {
			if (excelFileName == null || excelFileName.length() < 1)
				jspos = response.getOutputStream();
			else
				jspos = new FileOutputStream(excelFileName);
			
			wb.write(jspos);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			{
				try {
					jspos.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		
	}
	
	public HSSFWorkbook makeWorkBook(String titleName, HSSFWorkbook wbSrc) {
		
		int columnCount = colCount + headSortList.size();
		int realCount = columnCount;
		if (columnCount < COL_MIN_COUNT) {
			columnCount = COL_MIN_COUNT;
		}
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		if (wbSrc == null) {
			wb = new HSSFWorkbook();
			sheet = wb.createSheet("1");
		} else {
			wb = wbSrc;
			sheet = wb.getSheetAt(0);
		}
		
		//sheet.setGridsPrinted(true);
		sheet.setDisplayGridlines(true);
		sheet.setDisplayRowColHeadings(true);
		HSSFRow titleRow = sheet.createRow(sheet.getFirstRowNum());
		sheet.setDefaultColumnWidth(80);
		//ÿ365��λ����10px 5000/365��10�����أ�
		//sheet.setColumnWidth((short)0,(short)5000);
		
		titleRow.setHeight((short) 550);
		for (int i = 0; i < columnCount; i++) {
			HSSFCell cell = titleRow.createCell(i);
			if (i == 0) {
				HSSFCellStyle cellStyle = wb.createCellStyle();
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				HSSFFont font = wb.createFont();
				font.setFontName("����");
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				font.setFontHeightInPoints((short) 24);
				cellStyle.setFont(font);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(titleName);
			}
			
		}
		sheet.addMergedRegion(new CellRangeAddress(sheet.getFirstRowNum(), 0, sheet
			.getFirstRowNum(), (COL_MIN_COUNT - 1)));
		for (int i = 0; i < 3; i++) {
			HSSFRow titleCaptionRow = sheet.createRow(sheet.getFirstRowNum() + 1 + i);
			for (int j = 0; j < COL_MIN_COUNT; j++) {
				HSSFCell cell = titleCaptionRow.createCell(j);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				HSSFFont font = wb.createFont();
				//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				font.setFontName("����");
				HSSFCellStyle cellStyle = wb.createCellStyle();
				cellStyle.setFont(font);
				// cellStyle.
				cellStyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
				cellStyle.setBorderRight(HSSFCellStyle.BORDER_NONE);
				cellStyle.setBorderTop(HSSFCellStyle.BORDER_NONE);
				cellStyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);
				cell.setCellStyle(cellStyle);
				cell.setCellValue("");
				if (j >= 0 && j < 3) {
					String str = reportTemplate.getCaptionProperty().get(
						"left" + (i + 1) + "Caption");
					if (StringUtil.isNotEmpty(str)) {
						String str1 = reportTemplate.getCaptionProperty().get(
							"left" + (i + 1) + "Value");
						cell.setCellValue(str + "  " + str1);
					} else {
						String str1 = reportTemplate.getCaptionProperty().get(
							"left" + (i + 1) + "Value");
						if (str1 == null)
							str1 = "";
						cell.setCellValue(str1);
					}
				}
				if (j >= 3 && j < 6) {
					String str = reportTemplate.getCaptionProperty().get(
						"center" + (i + 1) + "Caption");
					
					if (StringUtil.isNotEmpty(str)) {
						String str1 = reportTemplate.getCaptionProperty().get(
							"center" + (i + 1) + "Value");
						cell.setCellValue(str + "  " + str1);
					} else {
						String str1 = reportTemplate.getCaptionProperty().get(
							"center" + (i + 1) + "Value");
						if (str1 == null)
							str1 = "";
						cell.setCellValue(str1);
					}
				}
				if (j >= 6 && j < 9) {
					String str = reportTemplate.getCaptionProperty().get(
						"right" + (i + 1) + "Caption");
					
					if (StringUtil.isNotEmpty(str)) {
						String str1 = reportTemplate.getCaptionProperty().get(
							"right" + (i + 1) + "Value");
						cell.setCellValue(str + "  " + str1);
					} else {
						String str1 = reportTemplate.getCaptionProperty().get(
							"right" + (i + 1) + "Value");
						if (str1 == null)
							str1 = "";
						cell.setCellValue(str + "  " + str1);
					}
				}
			}
			sheet.addMergedRegion(new CellRangeAddress(titleCaptionRow.getRowNum(), 0,
				titleCaptionRow.getRowNum(), (2)));
			sheet.addMergedRegion(new CellRangeAddress(titleCaptionRow.getRowNum(), 3,
				titleCaptionRow.getRowNum(), (5)));
			sheet.addMergedRegion(new CellRangeAddress(titleCaptionRow.getRowNum(), 6,
				titleCaptionRow.getRowNum(), (8)));
			
		}
		HSSFRow bankRow = sheet.createRow(sheet.getLastRowNum() + 1);
		if (this.isExsitHead) {
			for (int i = 0; i < this.headOrderList.size(); i++) {
				HSSFRow captionRow = sheet.createRow(bankRow.getRowNum() + 1 + i);
				for (int j = 0; j < realCount; j++) {
					HSSFCell cell = captionRow.createCell(j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					HSSFFont font = wb.createFont();
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
					font.setFontName("宋体");
					HSSFCellStyle cellStyle = wb.createCellStyle();
					cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
					cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					cellStyle.setFont(font);
					cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
					cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
					cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
					cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
					cell.setCellStyle(cellStyle);
					
				}
			}
		} else {
			HSSFRow captionRow = sheet.createRow(bankRow.getRowNum() + 1);
			for (int j = 0; j < realCount; j++) {
				HSSFCell cell = captionRow.createCell(j);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				HSSFFont font = wb.createFont();
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				font.setFontName("宋体");
				HSSFCellStyle cellStyle = wb.createCellStyle();
				cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				cellStyle.setFont(font);
				cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
				cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cell.setCellStyle(cellStyle);
			}
			
		}
		
		HSSFRow caption1Row = sheet.getRow(bankRow.getRowNum() + 1);
		for (int i = 0; i < colCount; i++) {
			HSSFCell cell = caption1Row.getCell(i);
			String text = reportTemplate.getColNameByIndex(i + 1);
			int colWidth = ExcelPrintUtil.getTextWidthDef80(text);
			sheet.setColumnWidth(i, colWidth);
			cell.setCellValue(text);
			if (isExsitHead && headOrderList.size() > 1) {
				//				sheet.addMergedRegion(new Region(caption1Row.getRowNum(), (short) i, caption1Row
				//					.getRowNum() + this.headOrderList.length - 1, (short) i)); // ָ���ϲ�����
				//				
				sheet.addMergedRegion(new CellRangeAddress(caption1Row.getRowNum(), i, caption1Row
					.getRowNum() + this.headOrderList.size() - 1, i));
			}
			
		}
		Iterator headSortIt = headSortList.iterator();
		for (int j = colCount; j < realCount; j++) {
			Map headItemMap = (Map) headSortIt.next();
			for (int i = 0; i < this.headOrderList.size(); i++) {
				HSSFRow hssRow = sheet.getRow(bankRow.getRowNum() + 1 + i);
				Object o = headItemMap.get(headOrderList.get(i));
				String strText = (String) headGoupList[i].get(o);
				if (i == this.headOrderList.size() - 1) {
					int colWidth = ExcelPrintUtil.getTextWidthDef80(strText);
					sheet.setColumnWidth(j, colWidth);
				}
				HSSFCell cell = hssRow.getCell(j);
				cell.setCellValue(strText);
				if (i > 0) {
					HSSFRow preRow = sheet.getRow(hssRow.getRowNum() - 1);
					HSSFCell prevCell = preRow.getCell(j);
					
					if (prevCell.getStringCellValue() != null
						&& prevCell.getStringCellValue().equals(strText)) {
						//						sheet.addMergedRegion(new Region(preRow.getRowNum(), prevCell.getCellNum(),
						//							hssRow.getRowNum(), cell.getCellNum())); 
						sheet.addMergedRegion(new CellRangeAddress(preRow.getRowNum(), prevCell
							.getCellNum(), hssRow.getRowNum(), cell.getColumnIndex()));
					}
				}
			}
		}
		if (headOrderList.size() > 1) {
			ExcelPrintUtil.MergColTableExcel(sheet, bankRow.getRowNum() + 1, headOrderList.size(),
				colCount, realCount);
		}
		if (isExsitHead)
			sheet.createFreezePane(0, bankRow.getRowNum() + 1 + headOrderList.size());
		else
			sheet.createFreezePane(0, bankRow.getRowNum() + 2);
		Iterator it = dataList.iterator();
		String oldValue = null;
		int rowCount = 0;
		HSSFRow hssRow = null;
		while (it.hasNext()) {
			DataListItem dataRowMap = (DataListItem) it.next();
			String value = null;
			for (int i = 1; i <= colCount; i++) {
				value += ReportUtil.objectToString(dataRowMap.getMap().get(colOrderList.get(i)));
			}
			if (value != null) {
				if (oldValue == null || !value.equals(oldValue)) {
					
					hssRow = sheet.createRow(sheet.getLastRowNum() + 1);
					rowCount++;
					for (int i = 0; i < realCount; i++) {
						HSSFCell cell = hssRow.createCell((short) i);
						//HSSFCellStyle cellStyle = wb.createCellStyle();
						if (i == 0) {
							//cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
							//cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
						} else {
							//cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
						}
						//cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
						if (i < colCount) {
							//cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
							//cell.setCellStyle(cellStyle);
							String valueText = ReportUtil.objectToString(dataRowMap.getMap().get(
								colOrderList.get(i)));
							if (valueText == null) {
								valueText = "";
							}
							
							int colWidth = ExcelPrintUtil.getTextWidthDef80(valueText);
							if (sheet.getColumnWidth((short) i) < colWidth) {
								sheet.setColumnWidth((short) i, (short) colWidth);
							}
							ExcelPrintUtil.setCellValue(cell, valueText);
						} else {
							
						}
						
					}
					oldValue = value;
				}
				if (isExsitHead) {
					int dataIndex = getColIndex(dataRowMap);
					HSSFCell cell = hssRow.getCell(dataIndex);
					//cell.setCellValue(UtilFormatOut.makeString(dataRowMap.get("DATACOL1")));	
					ExcelPrintUtil.setCellValue(cell,
						ReportUtil.objectToString(dataRowMap.getMap().get("DATACOL1")));
				}
			}
		}
		
		if (reportTemplate.isMergeRow())
			ExcelPrintUtil.MergTableExcel(sheet, colCount,
				headOrderList.size() + bankRow.getRowNum() + 1);
		
		if (reportTemplate.isExsitFooter()) {
			bankRow = sheet.createRow(sheet.getLastRowNum() + 1);
			for (int i = 0; i < 3; i++) {
				HSSFRow footerCaptionRow = sheet.createRow(bankRow.getRowNum() + i + 1);
				for (short j = 0; j < COL_MIN_COUNT; j++) {
					HSSFCell cell = footerCaptionRow.createCell(j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					HSSFFont font = wb.createFont();
					//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
					font.setFontName("宋体");
					HSSFCellStyle cellStyle = wb.createCellStyle();
					cellStyle.setFont(font);
					// cellStyle.
					cellStyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
					cellStyle.setBorderRight(HSSFCellStyle.BORDER_NONE);
					cellStyle.setBorderTop(HSSFCellStyle.BORDER_NONE);
					cellStyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);
					cell.setCellStyle(cellStyle);
					cell.setCellValue("");
					if (j >= 0 && j < 3) {
						String str = reportTemplate.getCaptionProperty().get(
							"footerLeft" + (i + 1) + "Caption");
						if (StringUtil.isNotEmpty(str)) {
							String str1 = reportTemplate.getCaptionProperty().get(
								"footerLeft" + (i + 1) + "Value");
							cell.setCellValue(str + "  " + str1);
						} else {
							String str1 = reportTemplate.getCaptionProperty().get(
								"footerLeft" + (i + 1) + "Value");
							if (str1 == null)
								str1 = "";
							cell.setCellValue(str1);
						}
					}
					if (j >= 3 && j < 6) {
						String str = reportTemplate.getCaptionProperty().get(
							"footerCenter" + (i + 1) + "Caption");
						
						if (StringUtil.isNotEmpty(str)) {
							String str1 = reportTemplate.getCaptionProperty().get(
								"footerCenter" + (i + 1) + "Value");
							cell.setCellValue(str + "  " + str1);
						} else {
							String str1 = reportTemplate.getCaptionProperty().get(
								"footerCenter" + (i + 1) + "Value");
							if (str1 == null)
								str1 = "";
							cell.setCellValue(str1);
						}
					}
					if (j >= 6 && j < 9) {
						String str = reportTemplate.getCaptionProperty().get(
							"footerRight" + (i + 1) + "Caption");
						
						if (StringUtil.isNotEmpty(str)) {
							String str1 = reportTemplate.getCaptionProperty().get(
								"footerRight" + (i + 1) + "Value");
							cell.setCellValue(str + "  " + str1);
						} else {
							String str1 = reportTemplate.getCaptionProperty().get(
								"footerRight" + (i + 1) + "Value");
							if (str1 == null)
								str1 = "";
							cell.setCellValue(str + "  " + str1);
						}
					}
				}
				sheet.addMergedRegion(new CellRangeAddress(footerCaptionRow.getRowNum(), 0,
					footerCaptionRow.getRowNum(), 2));
				//				sheet.addMergedRegion(new Region(footerCaptionRow.getRowNum(), (short) 0,
				//					footerCaptionRow.getRowNum(), (short) (2))); // ָ���ϲ�����
				
				sheet.addMergedRegion(new CellRangeAddress(footerCaptionRow.getRowNum(), 3,
					footerCaptionRow.getRowNum(), 5));
				
				//				sheet.addMergedRegion(new Region(footerCaptionRow.getRowNum(), (short) 3,
				//					footerCaptionRow.getRowNum(), (short) (5))); // ָ���ϲ�����
				
				sheet.addMergedRegion(new CellRangeAddress(footerCaptionRow.getRowNum(), 3,
					footerCaptionRow.getRowNum(), 5));
				
				//				sheet.addMergedRegion(new Region(footerCaptionRow.getRowNum(), (short) 6,
				//					footerCaptionRow.getRowNum(), (short) (8))); // ָ���ϲ�����
				sheet.addMergedRegion(new CellRangeAddress(footerCaptionRow.getRowNum(), 6,
					footerCaptionRow.getRowNum(), 8));
			}
			ExcelPrintUtil.MergColTableExcel(sheet, bankRow.getRowNum() + 1, 3, 0, 9);
		}
		return wb;
	}
	
	public static void MergColTableExcel(WritableSheet sheet, int beginRum, int rowNum, int begin,
											int colCount, Map map) throws RowsExceededException,
																	WriteException {
		for (int i = beginRum; i < beginRum + rowNum; i++)
			mergColTableOneExcel(sheet, i, begin, colCount, beginRum, map);
	}
	
	public static boolean cellValueEqual(Cell cell1, Cell cell2) {
		if (cell1 == null && cell2 == null)
			return true;
		else if (cell1 == null || cell2 == null) {
			return false;
		} else {
			if (cell1.getType().toString().equals(cell2.getType().toString())) {
				if (cell1.getType() == CellType.LABEL) {
					Label l1 = (Label) cell1;
					Label l2 = (Label) cell2;
					if (l1.getString() == null && l2.getString() == null) {
						return true;
					} else if (l1.getString() != null) {
						return l1.getString().equals(l2.getString());
					} else {
						return false;
					}
				} else if (cell1.getType() == CellType.NUMBER) {
					Number l1 = (Number) cell1;
					Number l2 = (Number) cell2;
					return l1.getValue() == l2.getValue();
					
				} else if (cell1.getType() == CellType.DATE) {
					DateTime l1 = (DateTime) cell1;
					DateTime l2 = (DateTime) cell2;
					if (l1.getDate() != null) {
						return l1.getDate().equals(l2.getDate());
					} else if (l2.getDate() == null) {
						return true;
					} else {
						return false;
					}
					
				} else if (cell1.getType() == CellType.EMPTY) {
					return true;
				}
				return false;
			} else {
				return false;
			}
		}
	}
	
	public static void mergColTableOneExcel(WritableSheet sheet, int rowNum, int begin,
											int colCount, int beginRum, Map map)
																				throws RowsExceededException,
																				WriteException {
		int i = begin, colSpanNum = 1;
		//HSSFRow row = sheet.getRow(rowNum);
		WritableCellFormat thickCentre = new WritableCellFormat();
		thickCentre.setBorder(Border.LEFT, BorderLineStyle.THIN);
		thickCentre.setBorder(Border.RIGHT, BorderLineStyle.THIN);
		thickCentre.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
		thickCentre.setBorder(Border.TOP, BorderLineStyle.THIN);
		thickCentre.setVerticalAlignment(VerticalAlignment.CENTRE);
		thickCentre.setAlignment(Alignment.CENTRE);
		int rowIndex = rowNum;
		while (i < colCount - 1) {
			Cell gvr = sheet.getCell(i, rowIndex);
			for (++i; i < colCount; i++) {
				Cell nextCell = sheet.getCell(i, rowIndex);//row.getCell((short) i);
				if (nextCell == null || gvr == null) {
					break;
				}
				// System.out.println(nextCell+" gvr "+gvr+" rowNum " +rowNum+" icell "+i);
				if (cellValueEqual(gvr, nextCell)) {
					if (rowNum - 1 > beginRum) {
						CellPoint cPoint = new CellPoint(gvr.getRow(), gvr.getColumn());
						if (map.containsKey(cPoint)) {
							CellPoint newCPoint = new CellPoint(nextCell.getRow(),
								nextCell.getColumn());
							map.put(newCPoint, "true");
							colSpanNum++;
						} else {
							colSpanNum++;
							if (colSpanNum > 0) {
								Range range = sheet.mergeCells(rowNum, gvr.getRow(),
									gvr.getColumn() + colSpanNum - 1, rowNum);
								((WritableCell) nextCell).setCellFormat(thickCentre);
								((WritableCell) gvr).setCellFormat(thickCentre);
								
							}
							colSpanNum = 1;
							break;
						}
					} else {
						CellPoint newCPoint = new CellPoint(nextCell.getRow(), nextCell.getColumn());
						map.put(newCPoint, "true");
						colSpanNum++;
					}
					
				} else {
					if (colSpanNum > 1) {
						Range range = sheet.mergeCells(gvr.getColumn(), rowNum, gvr.getColumn()
																				+ colSpanNum - 1,
							rowNum);
						((WritableCell) nextCell).setCellFormat(thickCentre);
						((WritableCell) gvr).setCellFormat(thickCentre);
						
					}
					colSpanNum = 1;
					break;
				}
				if (i == sheet.getRow(gvr.getRow()).length - 1) {
					if (colSpanNum > 1) {
						Range range = sheet.mergeCells(gvr.getColumn(), rowNum, gvr.getColumn()
																				+ colSpanNum - 1,
							rowNum);
						((WritableCell) nextCell).setCellFormat(thickCentre);
						((WritableCell) gvr).setCellFormat(thickCentre);
						
					}
					colSpanNum = 1;
				}
			}
		}
	}
	
	public static void MergTableExcel(WritableSheet sheet, short cellNum, int begin, Map map)
																								throws RowsExceededException,
																								WriteException {
		for (short i = 0; i < cellNum; i++)
			mergRowTableOneExcel(sheet, i, begin, map);
	}
	
	private static void mergRowTableOneExcel(WritableSheet sheet, short cellNum, int begin, Map map)
																									throws RowsExceededException,
																									WriteException {
		int i = begin, rowSpanNum = 1;
		WritableCellFormat thickCentre = new WritableCellFormat();
		thickCentre.setBorder(Border.LEFT, BorderLineStyle.THIN);
		thickCentre.setBorder(Border.RIGHT, BorderLineStyle.THIN);
		thickCentre.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
		thickCentre.setBorder(Border.TOP, BorderLineStyle.THIN);
		thickCentre.setVerticalAlignment(VerticalAlignment.CENTRE);
		thickCentre.setAlignment(Alignment.CENTRE);
		while (i < sheet.getRows() - 1) {
			//HSSFRow gvr = sheet.getRow(i);
			int gvrIndex = i;
			for (++i; i <= sheet.getRows(); i++) {
				//HSSFRow gvrNext = sheet.getRow(i);
				int gvrNextIndex = i;
				Cell cell1 = sheet.getCell(cellNum, gvrIndex);// gvr.getCell(cellNum);
				
				Cell cell2 = sheet.getCell(cellNum, gvrNextIndex);
				if (cell1 == null || cell2 == null) {
					break;
				}
				
				if (cellValueEqual(cell1, cell2)) {
					if (cellNum > 0) {
						CellPoint cPoint = new CellPoint(cellNum - 1, gvrNextIndex);
						if (map.containsKey(cPoint)) {
							map.put(new CellPoint(cell2.getColumn(), cell2.getRow()), "true");
							rowSpanNum++;
						} else {
							rowSpanNum++;
							if (rowSpanNum > 1) {
								Range range = sheet.mergeCells(cellNum, gvrIndex, cellNum,
									gvrIndex + rowSpanNum - 1);
								((WritableCell) cell1).setCellFormat(thickCentre);
								((WritableCell) cell2).setCellFormat(thickCentre);
							}
							rowSpanNum = 1;
							break;
						}
					} else {
						map.put(new CellPoint(cell2.getColumn(), cell2.getRow()), "true");
						rowSpanNum++;
					}
					
				} else {
					if (rowSpanNum > 1) {
						Range range = sheet.mergeCells(cellNum, gvrIndex, cellNum, gvrIndex
																					+ rowSpanNum
																					- 1);
						((WritableCell) cell1).setCellFormat(thickCentre);
					}
					rowSpanNum = 1;
					break;
				}
				if (i == sheet.getRows() - 1) {
					if (rowSpanNum > 1) {
						Range range = sheet.mergeCells(cellNum, gvrIndex, cellNum, gvrIndex
																					+ rowSpanNum
																					- 1);
						((WritableCell) cell1).setCellFormat(thickCentre);
						((WritableCell) cell2).setCellFormat(thickCentre);
					}
					rowSpanNum = 1;
				}
			}
		}
	}
	
	public static class CellPoint {
		protected int X = 0;
		protected int Y = 0;
		
		public CellPoint(int x, int y) {
			X = x;
			Y = y;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == null)
				return false;
			if (obj instanceof CellPoint) {
				return this.X == ((CellPoint) obj).X && this.Y == ((CellPoint) obj).Y;
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			
			return new Integer(X).hashCode() + new Integer(Y).hashCode();
		}
		
		@Override
		public String toString() {
			return super.toString();
		}
		
	}
	
	public String getExcelFileName() {
		return excelFileName;
	}
	
	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
	
}
