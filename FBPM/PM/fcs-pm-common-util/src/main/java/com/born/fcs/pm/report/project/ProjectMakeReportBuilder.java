package com.born.fcs.pm.report.project;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.field.FcsFunctionEnum;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

public class ProjectMakeReportBuilder {
	private final static String hisTable = "project_data_info_his_data";
	private final static String curlTable = "project_data_info";
	private final static Map<String, DataTypeEnum> dataTypeMap = new HashMap<String, DataTypeEnum>();
	static {
		dataTypeMap.put("loaned_amount", DataTypeEnum.MONEY);
		dataTypeMap.put("used_amount", DataTypeEnum.MONEY);
		dataTypeMap.put("repayed_amount", DataTypeEnum.MONEY);
		dataTypeMap.put("charged_amount", DataTypeEnum.MONEY);
		dataTypeMap.put("refund_amount", DataTypeEnum.MONEY);
		dataTypeMap.put("released_amount", DataTypeEnum.MONEY);
		dataTypeMap.put("releasable_amount", DataTypeEnum.MONEY);
		dataTypeMap.put("releasing_amount", DataTypeEnum.MONEY);
		dataTypeMap.put("customer_deposit_amount", DataTypeEnum.MONEY);
		dataTypeMap.put("self_deposit_amount", DataTypeEnum.MONEY);
		dataTypeMap.put("comp_principal_amount", DataTypeEnum.MONEY);
		dataTypeMap.put("comp_interest_amount", DataTypeEnum.MONEY);
		dataTypeMap.put("guarantee_amount", DataTypeEnum.MONEY);
		dataTypeMap.put("blance", DataTypeEnum.MONEY);
		dataTypeMap.put("contract_time", DataTypeEnum.DATE);
		dataTypeMap.put("last_recouncil_time", DataTypeEnum.DATE);
		dataTypeMap.put("setup_date", DataTypeEnum.DATE);
		dataTypeMap.put("apply_date", DataTypeEnum.DATE);
		dataTypeMap.put("survey_date", DataTypeEnum.DATE);
		dataTypeMap.put("on_will_date", DataTypeEnum.DATE);
		dataTypeMap.put("first_lending_date", DataTypeEnum.DATE);
	}
	
	public static ProjectMakeReportResult makeReportSql(ProjectMakeReportOrder makeReportOrder) {
		ProjectMakeReportResult makeReportResult = new ProjectMakeReportResult();
		String tableName = "";
		String sql = "";
		List<String> sqlArray = null;
		if (makeReportOrder.isSearchHisData()) {
			tableName = hisTable;
			if (makeReportOrder.getHisDataDeadline() == null
				&& makeReportOrder.getHisDataDeadlineArray() == null) {
				throw new RuntimeException("历史数据截至日期不能空");
			}
			if (makeReportOrder.getHisDataDeadlineArray() != null) {
				sqlArray = Lists.newArrayList();
			}
		} else {
			tableName = curlTable;
		}
		if (ListUtil.isEmpty(makeReportOrder.getFcsFields())) {
			throw new RuntimeException("查询字段不能空");
		}
		String selectSql = "";
		String groupBySql = "";
		List<FcsField> groupbys = Lists.newArrayList();
		List<FcsField> returnFields = Lists.newArrayList();
		HashMap<String, FcsField> fieldMap = new HashMap<String, FcsField>();
		Boolean booleanEnum = Boolean.FALSE;
		for (FcsField fcsField : makeReportOrder.getFcsFields()) {
			if (selectSql.length() == 0) {
				SqlBean sqlBean = makeFieldSql(fcsField, returnFields, fieldMap);
				selectSql = sqlBean.getSelectSql();
				if (sqlBean.getGroupBy()) {
					booleanEnum = true;
				}
			} else {
				SqlBean sqlBean = makeFieldSql(fcsField, returnFields, fieldMap);
				selectSql += "," + sqlBean.getSelectSql();
				if (sqlBean.getGroupBy()) {
					booleanEnum = true;
				}
			}
			if (fcsField.getFunction() == null) {
				groupbys.add(fcsField);
			}
		}
		if (booleanEnum == Boolean.TRUE) {
			for (FcsField fcsField : groupbys) {
				if (groupBySql.length() == 0) {
					groupBySql = fcsField.getColName();
				} else {
					groupBySql += "," + fcsField.getColName();
				}
			}
		}
		if (sqlArray != null) {
			
			int i = 0;
			for (String sqlItem : sqlArray) {
				for (Date deadLine : makeReportOrder.getHisDataDeadlineArray()) {
					sqlItem = makeFullSql(makeReportOrder, tableName, selectSql, groupBySql,
						deadLine);
					sqlArray.add(sqlItem);
				}
				i++;
			}
		} else {
			sql = makeFullSql(makeReportOrder, tableName, selectSql, groupBySql,
				makeReportOrder.getHisDataDeadline());
		}
		makeReportResult.setSql(sql);
		makeReportResult.setSqlArray(sqlArray);
		makeReportResult.setListField(returnFields);
		makeReportResult.setFieldMap(fieldMap);
		return makeReportResult;
	}
	
	private static String makeFullSql(ProjectMakeReportOrder makeReportOrder, String tableName,
										String selectSql, String groupBySql, Date deadLine) {
		String sqlItem = "";
		String whereSql = makeReportOrder.getWhereSql();
		if (deadLine == null) {
			
			sqlItem = " select " + selectSql + " from " + tableName + " where 1=1 ";
			
		} else {
			Date tempDate = DateUtil.getStartTimeOfTheDate(deadLine);
			sqlItem = " select " + selectSql + " from " + tableName + " where project_date='"
						+ DateUtil.simpleFormat(tempDate) + "' ";
			
		}
		if (StringUtil.isNotBlank(whereSql)) {
			sqlItem += " and (" + whereSql + ")";
		}
		if (StringUtil.isNotBlank(groupBySql)) {
			sqlItem += " group by " + groupBySql + " ";
		}
		if (StringUtil.isNotBlank(makeReportOrder.getHaving())) {
			sqlItem += " group by " + groupBySql + " ";
		}
		if (StringUtil.isNotBlank(makeReportOrder.getHaving())) {
			sqlItem += " having " + makeReportOrder.getHaving() + " ";
		}
		if (StringUtil.isNotBlank(makeReportOrder.getOrderBy())) {
			sqlItem += " order by  " + makeReportOrder.getOrderBy() + " ";
		}
		return sqlItem;
	}
	
	private static SqlBean makeFieldSql(FcsField fcsField, List<FcsField> returnFields,
										Map<String, FcsField> fieldMap) {
		Boolean booleanEnum = Boolean.FALSE;
		SqlBean bean = new SqlBean();
		String selectSql = "";
		if (fcsField.getFunction() == null || fcsField.getFunction() == FcsFunctionEnum.NO_GROUP_BY) {
			if (StringUtil.isBlank(fcsField.getAliasName())) {
				selectSql = fcsField.getColName() + " " + fcsField.getName();
			} else {
				selectSql = fcsField.getColName() + " " + fcsField.getAliasName();
			}
			
		} else {
			booleanEnum = Boolean.TRUE;
			if (fcsField.getFunction() == FcsFunctionEnum.COUNT_DISTINCT) {
				if (StringUtil.isBlank(fcsField.getAliasName())) {
					selectSql = "count( distinct " + fcsField.getColName() + ") "
								+ fcsField.getName();
				} else {
					selectSql = "count( distinct " + fcsField.getColName() + ") " + " "
								+ fcsField.getAliasName();
				}
			} else {
				if (StringUtil.isBlank(fcsField.getAliasName())) {
					selectSql = fcsField.getFunction().getFunction() + "(" + fcsField.getColName()
								+ ") " + fcsField.getName();
				} else {
					selectSql = fcsField.getFunction().getFunction() + "(" + fcsField.getColName()
								+ ") " + " " + fcsField.getAliasName();
				}
			}
		}
		if (fcsField.getDataTypeEnum() == null) {
			DataTypeEnum dataTypeEnum = dataTypeMap.get(fcsField.getColName().toLowerCase());
			fcsField.setDataTypeEnum(dataTypeEnum);
		}
		if (StringUtil.isBlank(fcsField.getAliasName())) {
			FcsField field = new FcsField(fcsField.getName(), fcsField.getName(), null,
				fcsField.getDataTypeEnum());
			returnFields.add(field);
			fieldMap.put(field.getName(), field);
		} else {
			FcsField field = new FcsField(fcsField.getAliasName(), fcsField.getName(), null,
				fcsField.getDataTypeEnum());
			returnFields.add(field);
			fieldMap.put(field.getName(), field);
		}
		bean.setSelectSql(selectSql);
		bean.setGroupBy(booleanEnum);
		
		return bean;
	}
	
	private static class SqlBean {
		String selectSql;
		Boolean groupBy;
		
		public String getSelectSql() {
			return this.selectSql;
		}
		
		public void setSelectSql(String selectSql) {
			this.selectSql = selectSql;
		}
		
		public Boolean getGroupBy() {
			return this.groupBy;
		}
		
		public void setGroupBy(Boolean groupBy) {
			this.groupBy = groupBy;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("SqlBean [selectSql=");
			builder.append(selectSql);
			builder.append(", groupBy=");
			builder.append(groupBy);
			builder.append("]");
			return builder.toString();
		}
		
	}
}
