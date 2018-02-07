package com.born.fcs.face.web.util;

import java.util.List;

/**
 * 
 * 解析excel得到的数据
 * 
 * @author lirz
 *
 * 2016-3-24 下午5:06:30
 */
public class ExcelData {
	private int columns; //列数
	private int rows; //行数
	
	private String [][] datas; //数据
	private List<String[]> list; //数据(新)
	
	public ExcelData() {}
	
	public ExcelData(int columns, int rows, String[][] datas) {
		this.columns = columns;
		this.rows = rows;
		this.datas = datas;
	}
	
	public ExcelData(int column, int row, List<String[]> list) {
		this.columns = column;
		this.rows = row;
		this.list = list;
	}

	public int getColumns() {
		return this.columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getRows() {
		return this.rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String[][] getDatas() {
		return this.datas;
	}

	public void setDatas(String[][] datas) {
		this.datas = datas;
	}

	public List<String[]> getList() {
		return this.list;
	}

	public void setList(List<String[]> list) {
		this.list = list;
	}
	
	
}
