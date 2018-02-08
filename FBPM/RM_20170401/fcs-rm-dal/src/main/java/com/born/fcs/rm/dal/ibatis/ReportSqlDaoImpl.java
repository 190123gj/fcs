package com.born.fcs.rm.dal.ibatis;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.born.fcs.rm.dal.daointerface.ReportSqlDao;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class ReportSqlDaoImpl extends SqlMapClientDaoSupport implements ReportSqlDao {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public List<Map<String, String>> query(String sql) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		DataSource dataSource = this.getDataSource();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = null;
			rs = st.executeQuery(sql);
			list = transformData(rs);
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
	private List<Map<String, String>> transformData(ResultSet rs) throws Exception {
		ArrayList<Map<String, String>> al = new ArrayList<Map<String, String>>();
		HashMap<String, String> h = null;
		ResultSetMetaData rsmd = rs.getMetaData();
		while (rs.next()) {
			h = new LinkedHashMap<String, String>();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String sData = rs.getString(i);
				// String columnName = rsmd.getColumnName(i);
				String columnLabel = rsmd.getColumnLabel(i);
				h.put(columnLabel, sData);
			}
			al.add(new LinkedHashMap<String, String>(h));
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
