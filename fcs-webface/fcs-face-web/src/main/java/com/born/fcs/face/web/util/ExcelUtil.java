package com.born.fcs.face.web.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.born.fcs.pm.util.DateUtil;
import jxl.*;
import jxl.read.biff.BiffException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * excel导入与处理
 * 
 * @author lirz
 * 
 * 2016-3-24 下午4:20:31
 */
public class ExcelUtil {
	
	protected static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
	
	public static ExcelData parseExcel(String excelPath) throws BiffException, IOException {
		Workbook model = Workbook.getWorkbook(new File(excelPath));
		Sheet sheet = model.getSheet(0);
		int columns = sheet.getColumns();//得到列数
		int rows = sheet.getRows();//得到行数
		logger.info(excelPath + "：共有" + columns + "列，" + rows + "行");
		if (columns > 0 && rows > 0) {
			String[][] datas = new String[rows][columns];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					datas[i][j] = sheet.getCell(j, i).getContents();
				}
			}
			
			return new ExcelData(columns, rows, datas);
		}
		return null;
	}
	
	public static ExcelData parseExcelReplaceYear(String excelPath) throws BiffException,
																	IOException {
		Workbook model = Workbook.getWorkbook(new File(excelPath));
		Sheet sheet = model.getSheet(0);
		int columns = sheet.getColumns();//得到列数
		int rows = sheet.getRows();//得到行数
		logger.info(excelPath + "：共有" + columns + "列，" + rows + "行");
		if (columns > 0 && rows > 0) {
			String[][] datas = new String[rows][columns];
			
			Date now = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(now);
			int year = c.get(Calendar.YEAR);
			int i = 0;
			for (int j = 0; j < columns; j++) {
				String cellContent = sheet.getCell(j, i).getContents();
				if (StringUtil.isNotBlank(cellContent)) {
					datas[i][j] = cellContent.replace("yyyy", year + "");
					year--;
				}
			}
			for (i = 1; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					datas[i][j] = sheet.getCell(j, i).getContents();
				}
			}
			
			return new ExcelData(columns, rows, datas);
		}
		return null;
	}
	
	public static ExcelData parseExcelReplaceYearAndMonth(String excelPath) throws BiffException,
																			IOException {
		return null;
	}
	
	public static void test1(String modelFilePath) throws BiffException, IOException {
		Workbook model = Workbook.getWorkbook(new File(modelFilePath));
		Sheet sheet = model.getSheet(0);
		int clos = sheet.getColumns();//得到所有的列
		int rows = sheet.getRows();//得到所有的行
		System.out.println("共有" + clos + "列，" + rows + "行");
		for (int i = 0; i < rows; i++) {
			int id = 1;
			StringBuilder sb = new StringBuilder();
			sb.append("第" + i + "行：");
			for (int j = 0; j < clos; j++) {
				sb.append(sheet.getCell(j, i).getContents() + "(" + (id++) + ")");
			}
			System.out.println(sb.toString());
		}
	}
	
	public static void main1(String[] args) {
		try {
			test1("F:\\works\\进出口担保项目\\报表\\保证人主要财务指标.xls");
		} catch (BiffException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ExcelData uploadExcel(HttpServletRequest request) throws FileUploadException,
																	IOException, BiffException {
		ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
		fileUpload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = null;
		try {
			fileList = fileUpload.parseRequest(request);
		} catch (FileUploadException ex) {
			logger.error(ex.getMessage(), ex);
			return null;
		}
		Iterator<FileItem> it = fileList.iterator();
		InputStream is = null;
		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {
				is = item.getInputStream();
				break;
			}
		}
		
		if (null != is) {
			Workbook model = Workbook.getWorkbook(is);
			Sheet sheet = model.getSheet(0);
			int columns = sheet.getColumns();//得到列数
			int rows = sheet.getRows();//得到行数
			logger.info("共有" + columns + "列，" + rows + "行");
			DateFormat dateFormat = DateUtil.getFormat("yyyy-MM-dd");
			if (columns > 0 && rows > 0) {
				String[][] datas = new String[rows][columns];
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < columns; j++) {
						if(sheet.getCell(j,i).getType()== CellType.DATE){//日期格式处理
							DateCell dc= (DateCell) sheet.getCell(j,i);
							datas[i][j]=dateFormat.format(dc.getDate());
						}else {
							datas[i][j] = sheet.getCell(j, i).getContents();
						}
					}
				}
				
				return new ExcelData(columns, rows, datas);
			}
		}
		return null;
	}
	
	public static ExcelData uploadExcel2(HttpServletRequest request) throws FileUploadException,
																	IOException, BiffException {
		ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
		fileUpload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = null;
		try {
			fileList = fileUpload.parseRequest(request);
		} catch (FileUploadException ex) {
			logger.error(ex.getMessage(), ex);
			return null;
		}
		Iterator<FileItem> it = fileList.iterator();
		InputStream is = null;
		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {
				is = item.getInputStream();
				break;
			}
		}
		
		if (null != is) {
			Workbook model = Workbook.getWorkbook(is);
			Sheet sheet = model.getSheet("客户主要高管人员表");
			int columns = sheet.getColumns();//得到列数
			int rows = sheet.getRows();//得到行数
			logger.info("共有" + columns + "列，" + rows + "行");
			if (columns > 0 && rows > 0) {
				String[][] datas = new String[rows][columns];
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < columns; j++) {
						datas[i][j] = sheet.getCell(j, i).getContents();
					}
				}
				
				return new ExcelData(columns, rows, datas);
			}
		}
		return null;
	}
	
	public static List<List<String[]>> uploadExcel3(HttpServletRequest request)
																				throws FileUploadException,
																				IOException,
																				BiffException {
		ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
		fileUpload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = null;
		try {
			fileList = fileUpload.parseRequest(request);
		} catch (FileUploadException ex) {
			logger.error(ex.getMessage(), ex);
			return null;
		}
		Iterator<FileItem> it = fileList.iterator();
		InputStream is = null;
		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {
				is = item.getInputStream();
				break;
			}
		}
		
		if (null != is) {
			int size = 8;
			List<List<String[]>> ret = new ArrayList<>(size);
			Workbook model = Workbook.getWorkbook(is);
			Map<Integer, Set<Integer>> map = initMap();
			
			for (int si = 0; si < size; si++) {
				Sheet sheet = model.getSheet(si);
				ExcelData excel = parse(sheet, map.get(si));
				if (null != excel) {
					ret.add(excel.getList());
				}
			}
			return ret;
		}
		return null;
	}
	
	public static ExcelData uploadExcel1(HttpServletRequest request) throws FileUploadException,
																	IOException, BiffException {
		ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
		fileUpload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = null;
		try {
			fileList = fileUpload.parseRequest(request);
		} catch (FileUploadException ex) {
			logger.error(ex.getMessage(), ex);
			return null;
		}
		Iterator<FileItem> it = fileList.iterator();
		InputStream is = null;
		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {
				is = item.getInputStream();
				break;
			}
		}
		
		if (null != is) {
			Workbook model = Workbook.getWorkbook(is);
			Sheet sheet = model.getSheet(0);
			Set<Integer> replaces = new HashSet<>();
			replaces.add(2);
			replaces.add(3);
			return parse(sheet, replaces);
		}
		return null;
	}
	
	private static ExcelData parse(Sheet sheet, Set<Integer> replaces) {
		if (null == sheet) {
			return null;
		}
		
		int column = sheet.getColumns();//得到列数
		int row = sheet.getRows();//得到行数
		logger.info(sheet.getName() + "：共有" + column + "列，" + row + "行");
		if (column <= 0 || row <= 0) {
			return null;
		}
		
		List<String[]> list = new ArrayList<>();
		for (int i = 0; i < row; i++) {
			boolean emptyLine = true;
			String[] datas = new String[column];
			for (int j = 0; j < column; j++) {
				datas[j] = sheet.getCell(j, i).getContents();
				if (StringUtil.isNotBlank(datas[j])) {
					emptyLine = false;
					if (null != replaces && replaces.contains(j)) {
						datas[j] = datas[j].replaceAll("/", "-");
					}
//					if (j == 2 || j == 3) {
//						datas[j] = datas[j].replaceAll("/", "-");
//					}
				}
			}
			if (emptyLine) {
				continue;
			}
			list.add(datas);
		}
		
		if (list.size() <= 0) {
			return null;
		}
		
		return new ExcelData(column, list.size(), list);
	}
	
	private static Map<Integer, Set<Integer>> initMap() {
		int [][] columns = {
		                    {2,3},
		                    {3},
		                    {4,5},
		                    {3},
		                    {2},
		                    {},
		                    {},
		                    {},
		};
		
		int size = columns.length;
		Map<Integer, Set<Integer>> map = new HashMap<>(size);
		
		for (int i=0; i<size; i++) {
			int [] nums = columns[i];
			Set<Integer> set = new HashSet<>();
			if (null != nums && nums.length > 0) {
				for (int j=0; j<nums.length; j++) {
					set.add(nums[j]);
				}
			}
			map.put(i, set);
		}
		
		return map;
	}
}
