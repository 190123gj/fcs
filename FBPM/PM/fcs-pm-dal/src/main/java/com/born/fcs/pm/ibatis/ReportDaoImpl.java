package com.born.fcs.pm.ibatis;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportItem;
import com.google.common.collect.Lists;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class ReportDaoImpl extends SqlMapClientDaoSupport implements
															com.born.fcs.pm.daointerface.ReportDao {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public List<DataListItem> query(String sql, Map<String, FcsField> fieldMap) {
		List<DataListItem> list = new ArrayList<DataListItem>();
		
		DataSource dataSource = this.getDataSource();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = null;
			rs = st.executeQuery(sql);
			list = transformData(rs, fieldMap);
			rs.close();
			st.close();
		} catch (SQLException e) {
			logger.info("ReportDaoImpl query Error:" + e.getMessage());
			//e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	/**
	 * ResultSet 转换为 List <HashMap<String,String>>
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private List<DataListItem> transformData(ResultSet rs, Map<String, FcsField> fieldMap)
																							throws Exception {
		ArrayList<DataListItem> al = new ArrayList<DataListItem>();
		DataListItem h = null;
		ResultSetMetaData rsmd = rs.getMetaData();
		DataListItem dataList = null;
		while (rs.next()) {
			dataList = new DataListItem();
			h = new DataListItem();
			List<ReportItem> valueList = Lists.newArrayList();
			ReportItem reportItem = null;
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String sData = rs.getString(i);
				String columnLabel = rsmd.getColumnLabel(i);
				reportItem = new ReportItem();
				reportItem.setKey(columnLabel);
				reportItem.setValue(sData);
				FcsField fcsField = fieldMap.get(columnLabel);
				if (fcsField != null) {
					reportItem.setDataTypeEnum(fcsField.getDataTypeEnum());
				}
				valueList.add(reportItem);
			}
			dataList.setValueList(valueList);
			al.add(dataList);
		}
		return al;
	}
	
	@Override
	public long queryCount(String sql) {
		long count = 0;
		DataSource dataSource = this.getDataSource();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				count = rs.getLong("ounterCount");
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			logger.info("ReportDaoImpl queryCount Error:" + e.getMessage());
			//e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return count;
	}
	
	@Override
	public int call(String sql) {
		DataSource dataSource = this.getDataSource();
		Connection conn = null;
		int rs = 0;
		try {
			conn = dataSource.getConnection();
			Statement st = conn.createStatement();
			if (sql.indexOf("CALL") >= 0) {
				rs = st.executeUpdate(sql);
			} else {
				rs = -1;
			}
			st.close();
		} catch (SQLException e) {
			logger.info("ReportDaoImpl query Error:" + e.getMessage());
			//e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return rs;
	}
	
}
