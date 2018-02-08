package com.born.fcs.pm.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataBaseUtil {
	static final Logger			log	= LoggerFactory.getLogger(DataBaseUtil.class);
	protected String			lookupSchemaName;
	DatabaseMetaData			dbData;
	private final Connection	connection;
	
	// protected boolean checkPk=false;
	public DataBaseUtil(Connection connection, String lookupSchemaName) {
		this.connection = connection;
		this.lookupSchemaName = lookupSchemaName;
	}
	
	/* ====================================================================== */
	
	/* ====================================================================== */
	
	public void makeSql() {
		List<String> messages = new ArrayList<String>();
		TreeSet<String> tableNames = this.getTableNames(messages);
		Map<String, Map<String, ColumnCheckInfo>> colInfo = this.getColumnInfo(tableNames, true,
			messages);
		Iterator<String> iterator = tableNames.iterator();
		StringBuffer sb = new StringBuffer();
		String lineSeparator = System.getProperty("line.separator");
		while (iterator.hasNext()) {
			String tableName = iterator.next();
			Map<String, ColumnCheckInfo> colMap = colInfo.get(tableName);
			ColumnCheckInfo[] colInfoArray = new ColumnCheckInfo[colMap.size()];
			Iterator<Map.Entry<String, ColumnCheckInfo>> colMapIt = colMap.entrySet().iterator();
			List<ColumnCheckInfo> pkList = new ArrayList<ColumnCheckInfo>();
			while (colMapIt.hasNext()) {
				Map.Entry<String, ColumnCheckInfo> entry = colMapIt.next();
				colInfoArray[entry.getValue().seq - 1] = entry.getValue();
			}
			for (int i = 0; i < colInfoArray.length; i++) {
				if (colInfoArray[i].isPk) {
					pkList.add(colInfoArray[i]);
				}
			}
			makeInsertSql(sb, tableName, colInfoArray, pkList);
			sb.append(lineSeparator);
			sb.append(lineSeparator);
			makeUpdateSql(sb, tableName, colInfoArray, pkList);
			sb.append(lineSeparator);
			sb.append(lineSeparator);
			makeDeleteSql(sb, tableName, colInfoArray, pkList);
			sb.append(lineSeparator);
			sb.append(lineSeparator);
			
			makeLoadIdSql(sb, tableName, colInfoArray, pkList);
			sb.append(lineSeparator);
			sb.append(lineSeparator);
			makeLoadCountSql(sb, tableName, colInfoArray, pkList);
			sb.append(lineSeparator);
			sb.append(lineSeparator);
			sb.append("------------------------------------------------------------------------");
			sb.append(lineSeparator);
		}
		try {
			this.writerFile(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void makeDeleteSql(StringBuffer sb, String tableName, ColumnCheckInfo[] colInfoArray,
								List<ColumnCheckInfo> pkList) {
		// TODO Auto-generated method stub
		sb.append("DELETE FROM " + tableName + " WHERE ");
		for (int i = 0; i < pkList.size(); i++) {
			if (i == 0)
				sb.append(pkList.get(i).columnName + "=?");
			else
				sb.append(" AND " + pkList.get(i).columnName + "=?");
		}
	}
	
	private void makeLoadIdSql(StringBuffer sb, String tableName, ColumnCheckInfo[] colInfoArray,
								List<ColumnCheckInfo> pkList) {
		// TODO Auto-generated method stub
		sb.append("SELECT  ");
		//		int realIndex = 0;
		for (int i = 0; i < colInfoArray.length; i++) {
			
			if (i == 0)
				sb.append(colInfoArray[i].columnName + " ");
			else
				sb.append("," + colInfoArray[i].columnName + " ");
			//			realIndex++;
		}
		sb.append(" FROM " + tableName);
		sb.append(" WHERE ");
		for (int i = 0; i < pkList.size(); i++) {
			if (i == 0)
				sb.append(pkList.get(i).columnName + "=?");
			else
				sb.append(" AND " + pkList.get(i).columnName + "=?");
		}
	}
	
	private void makeLoadCountSql(StringBuffer sb, String tableName,
									ColumnCheckInfo[] colInfoArray, List<ColumnCheckInfo> pkList) {
		// TODO Auto-generated method stub
		sb.append("SELECT  count(*) FROM " + tableName);
		
	}
	
	private void makeUpdateSql(StringBuffer sb, String tableName, ColumnCheckInfo[] colInfoArray,
								List<ColumnCheckInfo> pkList) {
		sb.append("UPDATE " + tableName + " SET ");
		int realIndex = 0;
		for (int i = 0; i < colInfoArray.length; i++) {
			if (colInfoArray[i].isPk)
				continue;
			
			if ("raw_update_time".equalsIgnoreCase(colInfoArray[i].columnName))
				continue;
			
			if ("raw_add_time".equalsIgnoreCase(colInfoArray[i].columnName))
				continue;
			
			if (realIndex == 0)
				sb.append(colInfoArray[i].columnName + "=?");
			else
				sb.append("," + colInfoArray[i].columnName + "=?");
			realIndex++;
		}
		sb.append(" WHERE ");
		for (int i = 0; i < pkList.size(); i++) {
			if (i == 0)
				sb.append(pkList.get(i).columnName + "=?");
			else
				sb.append(" AND " + pkList.get(i).columnName + "=?");
		}
	}
	
	private void makeInsertSql(StringBuffer sb, String tableName, ColumnCheckInfo[] colInfoArray,
								List<ColumnCheckInfo> pkList) {
		sb.append("INSERT INTO " + tableName);
		sb.append("(");
		for (int i = 0; i < colInfoArray.length; i++) {
			if ("raw_update_time".equalsIgnoreCase(colInfoArray[i].columnName))
				continue;
			if (i == 0)
				sb.append(colInfoArray[i].columnName);
			else
				sb.append("," + colInfoArray[i].columnName);
		}
		sb.append(")");
		sb.append(" VALUES (");
		for (int i = 0; i < colInfoArray.length; i++) {
			if ("raw_update_time".equalsIgnoreCase(colInfoArray[i].columnName))
				continue;
			if (i == 0)
				sb.append("?");
			else
				sb.append(",?");
		}
		sb.append(")");
		
	}
	
	private void writerFile(String sb) throws IOException {
		BufferedWriter bw = null;
		File file = new File("D:\\dataSql.txt");
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK"));
			bw.write(sb);
			bw.newLine();
		} catch (IOException e) {
			log.error("writerTemp error:", e);
		} finally {
			if (bw != null) {
				bw.flush();
				bw.close();
			}
		}
	}
	
	public void printDbMiscData(DatabaseMetaData dbData, Connection con) {
		if (dbData == null) {
			return;
		}
		// Database Info
		if (log.isInfoEnabled()) {
			try {
				log.info("Database Product Name is " + dbData.getDatabaseProductName());
				log.info("Database Product Version is " + dbData.getDatabaseProductVersion());
			} catch (SQLException sqle) {
				log.warn("Unable to get Database name & version information");
			}
		}
		// JDBC Driver Info
		if (log.isInfoEnabled()) {
			try {
				log.info("Database Driver Name is " + dbData.getDriverName());
				log.info("Database Driver Version is " + dbData.getDriverVersion());
				log.info("Database Driver JDBC Version is " + dbData.getJDBCMajorVersion() + "."
							+ dbData.getJDBCMinorVersion());
			} catch (SQLException sqle) {
				log.warn("Unable to get Driver name & version information", sqle);
			} catch (AbstractMethodError ame) {
				log.warn("Unable to get Driver JDBC Version", ame);
			} catch (Exception e) {
				log.warn("Unable to get Driver JDBC Version", e);
			}
		}
		// Db/Driver support settings
		if (log.isInfoEnabled()) {
			try {
				log.info("Database Setting/Support Information (those with a * should be true):");
				log.info("- supports transactions    [" + dbData.supportsTransactions() + "]*");
				log.info("- isolation None           ["
							+ dbData.supportsTransactionIsolationLevel(Connection.TRANSACTION_NONE)
							+ "]");
				log.info("- isolation ReadCommitted  ["
							+ dbData
								.supportsTransactionIsolationLevel(Connection.TRANSACTION_READ_COMMITTED)
							+ "]");
				log.info("- isolation ReadUncommitted["
							+ dbData
								.supportsTransactionIsolationLevel(Connection.TRANSACTION_READ_UNCOMMITTED)
							+ "]");
				log.info("- isolation RepeatableRead ["
							+ dbData
								.supportsTransactionIsolationLevel(Connection.TRANSACTION_REPEATABLE_READ)
							+ "]");
				log.info("- isolation Serializable   ["
							+ dbData
								.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE)
							+ "]");
				log.info("- default fetchsize        [" + con.createStatement().getFetchSize()
							+ "]");
				log.info("- forward only type        ["
							+ dbData.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY) + "]");
				log.info("- scroll sensitive type    ["
							+ dbData.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE) + "]");
				log.info("- scroll insensitive type  ["
							+ dbData.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE) + "]");
				log.info("- is case sensitive        [" + dbData.supportsMixedCaseIdentifiers()
							+ "]");
				log.info("- stores LowerCase         [" + dbData.storesLowerCaseIdentifiers() + "]");
				log.info("- stores MixedCase         [" + dbData.storesMixedCaseIdentifiers() + "]");
				log.info("- stores UpperCase         [" + dbData.storesUpperCaseIdentifiers() + "]");
				log.info("- max table name length    [" + dbData.getMaxTableNameLength() + "]");
				log.info("- max column name length   [" + dbData.getMaxColumnNameLength() + "]");
				log.info("- max schema name length   [" + dbData.getMaxSchemaNameLength() + "]");
				log.info("- concurrent connections   [" + dbData.getMaxConnections() + "]");
				log.info("- concurrent statements    [" + dbData.getMaxStatements() + "]");
				log.info("- ANSI SQL92 Entry         [" + dbData.supportsANSI92EntryLevelSQL()
							+ "]");
				log.info("- ANSI SQL92 Itermediate   [" + dbData.supportsANSI92IntermediateSQL()
							+ "]");
				log.info("- ANSI SQL92 Full          [" + dbData.supportsANSI92FullSQL() + "]");
				log.info("- ODBC SQL Grammar Core    [" + dbData.supportsCoreSQLGrammar() + "]");
				log.info("- ODBC SQL Grammar Extended[" + dbData.supportsExtendedSQLGrammar() + "]");
				log.info("- ODBC SQL Grammar Minimum [" + dbData.supportsMinimumSQLGrammar() + "]");
				log.info("- outer joins              [" + dbData.supportsOuterJoins() + "]*");
				log.info("- limited outer joins      [" + dbData.supportsLimitedOuterJoins() + "]");
				log.info("- full outer joins         [" + dbData.supportsFullOuterJoins() + "]");
				log.info("- group by                 [" + dbData.supportsGroupBy() + "]*");
				log.info("- group by not in select   [" + dbData.supportsGroupByUnrelated() + "]");
				log.info("- column aliasing          [" + dbData.supportsColumnAliasing() + "]");
				log.info("- order by not in select   [" + dbData.supportsOrderByUnrelated() + "]");
				// this doesn't work in HSQLDB, other databases?
				// log.info("- named parameters [" +
				// dbData.supportsNamedParameters() + "]");
				log.info("- alter table add column   [" + dbData.supportsAlterTableWithAddColumn()
							+ "]*");
				log.info("- non-nullable column      [" + dbData.supportsNonNullableColumns()
							+ "]*");
			} catch (Exception e) {
				log.warn("Unable to get misc. support/setting information", e);
			}
		}
	}
	
	public DatabaseMetaData getDatabaseMetaData(Connection connection, Collection<String> messages) {
		if (connection == null) {
			connection = getConnection();
		}
		DatabaseMetaData dbData = null;
		try {
			dbData = connection.getMetaData();
		} catch (SQLException sqle) {
			String message = "Unable to get database meta data... Error was:" + sqle.toString();
			log.error(message, sqle);
			if (messages != null) {
				messages.add(message);
			}
			return null;
		}
		
		if (dbData == null) {
			log.warn("Unable to get database meta data; method returned null");
		}
		
		return dbData;
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public TreeSet<String> getTableNames(Collection<String> messages) {
		Connection connection = null;
		connection = getConnection();
		
		dbData = this.getDatabaseMetaData(connection, messages);
		if (dbData == null) {
			return null;
		}
		
		printDbMiscData(dbData, connection);
		if (log.isInfoEnabled())
			log.info("Getting Table Info From Database");
		
		// get ALL tables from this database
		TreeSet<String> tableNames = new TreeSet<String>();
		ResultSet tableSet = null;
		
		try {
			String[] types = { "TABLE", "VIEW", "ALIAS", "SYNONYM" };
			lookupSchemaName = getSchemaName(dbData);
			tableSet = dbData.getTables(null, lookupSchemaName, null, types);
			if (tableSet == null) {
				log.warn("getTables returned null set");
			}
		} catch (SQLException sqle) {
			String message = "Unable to get list of table information, let's try the create anyway... Error was:"
								+ sqle.toString();
			log.error(message, sqle);
			if (messages != null)
				messages.add(message);
			
			try {
				connection.close();
			} catch (SQLException sqle2) {
				String message2 = "Unable to close database connection, continuing anyway... Error was:"
									+ sqle2.toString();
				log.error(message2);
				if (messages != null)
					messages.add(message2);
			}
			// we are returning an empty set here because databases like SapDB
			// throw an exception when there are no tables in the database
			return tableNames;
		}
		
		try {
			//			boolean needsUpperCase = false;
			//			try {
			//				needsUpperCase = dbData.storesLowerCaseIdentifiers()
			//									|| dbData.storesMixedCaseIdentifiers();
			//			} catch (SQLException sqle) {
			//				String message = "Error getting identifier case information... Error was:"
			//									+ sqle.toString();
			//				log.error(message, sqle);
			//				if (messages != null)
			//					messages.add(message);
			//			}
			while (tableSet.next()) {
				try {
					String tableName = tableSet.getString("TABLE_NAME");
					// for those databases which do not return the schema name
					// with the table name (pgsql 7.3)
					boolean appendSchemaName = false;
					if (tableName != null && lookupSchemaName != null
						&& !tableName.startsWith(lookupSchemaName)) {
						appendSchemaName = true;
					}
					//					if (needsUpperCase && tableName != null) {
					//						tableName = tableName.toUpperCase();
					//					}
					if (appendSchemaName) {
						tableName = lookupSchemaName + "." + tableName;
					}
					// NOTE: this may need a toUpperCase in some cases, keep an
					// eye on it, okay for now just do a compare with
					// equalsIgnoreCase
					String tableType = tableSet.getString("TABLE_TYPE");
					// only allow certain table types
					if (tableType != null && !"TABLE".equalsIgnoreCase(tableType)
						&& !"VIEW".equalsIgnoreCase(tableType)
						&& !"ALIAS".equalsIgnoreCase(tableType) &&
						
						!"SYNONYM".equalsIgnoreCase(tableType)) {
						continue;
					}
					
					// String remarks = tableSet.getString("REMARKS");
					tableNames.add(tableName);
					// if (log.isInfoEnabled()) log.info("Found table named [" +
					// tableName + "] of type [" + tableType +
					// "] with remarks: " + remarks);
				} catch (SQLException sqle) {
					String message = "Error getting table information... Error was:"
										+ sqle.toString();
					log.error(message, sqle);
					if (messages != null)
						messages.add(message);
					continue;
				}
			}
		} catch (SQLException sqle) {
			String message = "Error getting next table information... Error was:" + sqle.toString();
			log.error(message, sqle);
			if (messages != null)
				messages.add(message);
		} finally {
			try {
				tableSet.close();
			} catch (SQLException sqle) {
				String message = "Unable to close ResultSet for table list, continuing anyway... Error was:"
									+ sqle.toString();
				log.error(message, sqle);
				if (messages != null)
					messages.add(message);
			}
			
			//			try {
			//				connection.close();
			//			} catch (SQLException sqle) {
			//				String message = "Unable to close database connection, continuing anyway... Error was:" + sqle.toString();
			//				log.error(message, sqle);
			//				if (messages != null)
			//					messages.add(message);
			//			}
		}
		return tableNames;
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, Map<String, ColumnCheckInfo>> getColumnInfo(Set<String> tableNames,
																	boolean getPks,
																	Collection<String> messages) {
		// if there are no tableNames, don't even try to get the columns
		if (tableNames.size() == 0) {
			return new HashMap<String, Map<String, ColumnCheckInfo>>();
		}
		
		Connection connection = null;
		connection = getConnection();
		try {
			
			if (log.isInfoEnabled())
				log.info("Getting Column Info From Database");
			
			Map<String, Map<String, ColumnCheckInfo>> colInfo = new HashMap<String, Map<String, ColumnCheckInfo>>();
			String lookupSchemaName = this.getSchemaName(null);
			boolean isUserName = false;
			try {
				
				boolean needsUpperCase = false;
				try {
					needsUpperCase = dbData.storesLowerCaseIdentifiers()
										|| dbData.storesMixedCaseIdentifiers();
				} catch (SQLException sqle) {
					String message = "Error getting identifier case information... Error was:"
										+ sqle.toString();
					log.error(message, sqle);
					if (messages != null)
						messages.add(message);
				}
				
				boolean foundCols = false;
				Map<String, Map<String, ColumnCheckInfo>> kpMap = new HashMap<String, Map<String, ColumnCheckInfo>>();
				ResultSet rsCols = dbData.getColumns(null, lookupSchemaName, null, null);
				if (!rsCols.next()) {
					try {
						rsCols.close();
					} catch (SQLException sqle) {
						String message = "Unable to close ResultSet for column list, continuing anyway... Error was:"
											+ sqle.toString();
						log.error(message, sqle);
						if (messages != null)
							messages.add(message);
					}
					rsCols = dbData.getColumns(null, lookupSchemaName, "%", "%");
					if (!rsCols.next()) {
						// TODO: now what to do? I guess try one table name at a
						// time...
					} else {
						foundCols = true;
					}
				} else {
					foundCols = true;
				}
				if (foundCols) {
					do {
						try {
							ColumnCheckInfo ccInfo = new ColumnCheckInfo();
							
							ccInfo.tableName = ColumnCheckInfo.fixupTableName(
								rsCols.getString("TABLE_NAME"), lookupSchemaName, needsUpperCase);
							
							// ignore the column info if the table name is not
							// in the list we are concerned with
							if (!tableNames.contains(ccInfo.tableName)) {
								continue;
							}
							
							ccInfo.columnName = rsCols.getString("COLUMN_NAME");
							//							if (needsUpperCase && ccInfo.columnName != null) {
							//								ccInfo.columnName = ccInfo.columnName.toUpperCase();
							//							}
							// NOTE: this may need a toUpperCase in some cases,
							// keep an eye on it
							ccInfo.typeName = rsCols.getString("TYPE_NAME");
							ccInfo.columnSize = rsCols.getInt("COLUMN_SIZE");
							ccInfo.decimalDigits = rsCols.getInt("DECIMAL_DIGITS");
							// NOTE: this may need a toUpperCase in some cases,
							// keep an eye on it
							ccInfo.isNullable = rsCols.getString("IS_NULLABLE");
							if (getPks) {
								if (!kpMap.containsKey(ccInfo.tableName)) {
									ResultSet rsPks = null;
									Map<String, ColumnCheckInfo> pkcolMap = new HashMap<String, ColumnCheckInfo>();
									try {
										String pkTabbleName = ccInfo.tableName;
										if (isUserName) {
											int dotIndex = ccInfo.tableName.indexOf(".");
											if (dotIndex >= 0) {
												pkTabbleName = ccInfo.tableName
													.substring(dotIndex + 1);
											}
										}
										rsPks = dbData.getPrimaryKeys(null, lookupSchemaName,
											pkTabbleName);
										while (rsPks.next()) {
											ColumnCheckInfo ccInfoTemp = new ColumnCheckInfo();
											ccInfoTemp.columnName = rsPks.getString("COLUMN_NAME");
											ccInfoTemp.pkSeq = rsPks.getShort("KEY_SEQ");
											ccInfoTemp.pkName = rsPks.getString("PK_NAME");
											pkcolMap.put(ccInfoTemp.columnName, ccInfoTemp);
										}
									} catch (Exception e) {
										String message = "Unable to close ResultSet for primary key list, continuing anyway... Error was:"
															+ e.toString();
										log.error(message, e);
									}
									kpMap.put(ccInfo.tableName, pkcolMap);
								}
								Map pkcolMap = kpMap.get(ccInfo.tableName);
								if (pkcolMap.containsKey(ccInfo.columnName)) {
									ColumnCheckInfo ccInfoTemp = (ColumnCheckInfo) pkcolMap
										.get(ccInfo.columnName);
									ccInfo.isPk = true;
									ccInfo.pkSeq = ccInfoTemp.pkSeq;
									ccInfo.pkName = ccInfoTemp.pkName;
								}
								
							}
							Map<String, ColumnCheckInfo> tableColInfo = colInfo
								.get(ccInfo.tableName);
							if (tableColInfo == null) {
								tableColInfo = new HashMap<String, ColumnCheckInfo>();
								colInfo.put(ccInfo.tableName, tableColInfo);
							}
							ccInfo.seq = tableColInfo.size() + 1;
							tableColInfo.put(ccInfo.columnName, ccInfo);
							//							List<String> fieldList = new ArrayList<String>();
							
						} catch (SQLException sqle) {
							String message = "Error getting column info for column. Error was:"
												+ sqle.toString();
							log.error(message, sqle);
							if (messages != null)
								messages.add(message);
							continue;
						}
					} while (rsCols.next());
				}
				
				try {
					rsCols.close();
				} catch (SQLException sqle) {
					String message = "Unable to close ResultSet for column list, continuing anyway... Error was:"
										+ sqle.toString();
					log.error(message, sqle);
					if (messages != null)
						messages.add(message);
				}
			} catch (SQLException sqle) {
				String message = "Error getting column meta data for Error was:" + sqle.toString()
									+ ". Not checking columns.";
				log.error(message, sqle);
				if (messages != null)
					messages.add(message);
				// we are returning an empty set in this case because databases
				// like SapDB throw an exception when there are no tables in the
				// database
				// colInfo = null;
			}
			return colInfo;
		} finally {
			if (connection != null) {
				//				try {
				//					//connection.close();
				//				} catch (SQLException sqle) {
				//					String message = "Unable to close database connection, continuing anyway... Error was:" + sqle.toString();
				//					log.error(message, sqle);
				//					if (messages != null)
				//						messages.add(message);
				//				}
			}
		}
	}
	
	public String getSchemaName(DatabaseMetaData dbData) {
		// if (this.datasourceInfo.useSchemas &&
		// dbData.supportsSchemasInTableDefinitions()) {
		// if (this.datasourceInfo.schemaName != null &&
		// this.datasourceInfo.schemaName.length() > 0) {
		// return this.datasourceInfo.schemaName;
		// } else {
		// return dbData.getUserName();
		// }
		// }
		return this.lookupSchemaName;
	}
	
	/* ====================================================================== */
	
	/* ====================================================================== */
	public static class ColumnCheckInfo {
		public String	tableName;
		public String	columnName;
		public String	typeName;
		public int		columnSize;
		public int		decimalDigits;
		public boolean	isPk	= false;	// YES/NO or "" = ie nobody knows
		public String	isNullable;		// YES/NO or "" = ie nobody knows
		public int		pkSeq;
		public String	pkName;
		public int		seq;
		
		public static String fixupTableName(String rawTableName, String lookupSchemaName,
											boolean needsUpperCase) {
			String tableName = rawTableName;
			// for those databases which do not return the schema name with the
			// table name (pgsql 7.3)
			boolean appendSchemaName = false;
			if (tableName != null && lookupSchemaName != null
				&& !tableName.startsWith(lookupSchemaName)) {
				appendSchemaName = true;
			}
			//			if (needsUpperCase && tableName != null) {
			//				tableName = tableName.toUpperCase();
			//			}
			if (appendSchemaName) {
				tableName = lookupSchemaName + "." + tableName;
			}
			return tableName;
		}
	}
	
	public static class ReferenceCheckInfo {
		public String	pkTableName;
		
		/**
		 * Comma separated list of column names in the related tables primary
		 * key
		 */
		public String	pkColumnName;
		public String	fkName;
		public String	fkTableName;
		
		/**
		 * Comma separated list of column names in the primary tables foreign
		 * keys
		 */
		public String	fkColumnName;
		
		@Override
		public String toString() {
			return "FK Reference from table " + fkTableName + " called " + fkName
					+ " to PK in table " + pkTableName;
		}
	}
}
