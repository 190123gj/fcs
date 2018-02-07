package com.born.fcs.face.integration.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.face.integration.result.ProjectCompResult;
import com.born.fcs.face.integration.result.ProjectCompResult.CompInfo;
import com.born.fcs.face.integration.service.ProjectRelatedDataService;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.info.financeaffirm.ProjectChargePayInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.financeaffirm.ProjectChargePayQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.financeaffirm.FinanceAffirmService;
import com.born.fcs.pm.ws.service.report.PmReportService;
import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Service("projectRelatedDataService")
public class ProjectRelatedDataServiceImpl implements ProjectRelatedDataService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	FinanceAffirmService financeAffirmServiceClient;
	
	@Autowired
	PmReportService pmReportServiceClient;
	
	@Value("${pm.database.title}")
	private String dataPmTitle;
	
	@Value("${fm.database.title}")
	private String dataFmTitle;
	
	@Override
	public ProjectCompResult projectCompData(String projectCode) {
		ProjectCompResult compResult = new ProjectCompResult();
		try {
			compResult.setProjectCode(projectCode);
			
			ProjectChargePayQueryOrder queryOrder = new ProjectChargePayQueryOrder();
			
			queryOrder.setProjectCode(projectCode);
			
			List<String> feeTypeList = Lists.newArrayList();
			//收费
			feeTypeList.add(FeeTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL.code());
			feeTypeList.add(FeeTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL.code());
			feeTypeList.add(FeeTypeEnum.COMPENSATORY_DEDIT_WITHDRAWAL.code());
			feeTypeList.add(FeeTypeEnum.COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL.code());
			feeTypeList.add(FeeTypeEnum.COMPENSATORY_OTHER_WITHDRAWAL.code());
			//划付
			feeTypeList.add(PaymentMenthodEnum.COMPENSATORY_PRINCIPAL.code());
			feeTypeList.add(PaymentMenthodEnum.COMPENSATORY_INTEREST.code());
			feeTypeList.add(PaymentMenthodEnum.COMPENSATORY_PENALTY.code());
			feeTypeList.add(PaymentMenthodEnum.COMPENSATORY_LIQUIDATED_DAMAGES.code());
			feeTypeList.add(PaymentMenthodEnum.COMPENSATORY_OTHER.code());
			queryOrder.setFeeTypeList(feeTypeList);
			queryOrder.setPageSize(999);
			QueryBaseBatchResult<ProjectChargePayInfo> compDataResult = financeAffirmServiceClient
				.queryProjectChargePayDetail(queryOrder);
			
			if (compDataResult != null && compDataResult.isSuccess()
				&& compDataResult.getTotalCount() > 0) {
				
				Map<String, CompInfo> compMap = Maps.newHashMap();
				
				for (ProjectChargePayInfo compDetail : compDataResult.getPageList()) {
					
					String compDate = DateUtil.dtSimpleFormat(compDetail.getPayTime());
					
					if ("CHARGE_NOTIFICATION".equals(compDetail.getAffirmFormType())) {//入
					
						CompInfo compInfo = compMap.get(compDate + "_IN");
						if (compInfo == null) {
							compInfo = compResult.new CompInfo();
							compInfo.setCompDate(compDate);
							compInfo.setDirection(FundDirectionEnum.IN);
							compMap.put(compDate + "_IN", compInfo);
						}
						FeeTypeEnum chargeType = compDetail.getChargeType();
						if (chargeType == FeeTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL) {
							compInfo.setCompPrincipal(compDetail.getPayAmount());
							compResult.getCompPrincipalBack().addTo(compDetail.getPayAmount());
						} else if (chargeType == FeeTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL) {
							compInfo.setCompInterest(compDetail.getPayAmount());
							compResult.getCompInterestBack().addTo(compDetail.getPayAmount());
						} else if (chargeType == FeeTypeEnum.COMPENSATORY_DEDIT_WITHDRAWAL) {
							compInfo.setCompPenalty(compDetail.getPayAmount());
							compResult.getCompPenaltyBack().addTo(compDetail.getPayAmount());
						} else if (chargeType == FeeTypeEnum.COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL) {
							compInfo.setCompPenaltyInterest(compDetail.getPayAmount());
							compResult.getCompPenaltyInterestBack()
								.addTo(compDetail.getPayAmount());
						} else if (chargeType == FeeTypeEnum.COMPENSATORY_OTHER_WITHDRAWAL) {
							compInfo.setCompOther(compDetail.getPayAmount());
							compResult.getCompOtherBack().addTo(compDetail.getPayAmount());
						}
						compResult.getCompTotalBack().addTo(compDetail.getPayAmount());
					} else {//出
					
						CompInfo compInfo = compMap.get(compDate + "_OUT");
						if (compInfo == null) {
							compInfo = compResult.new CompInfo();
							compInfo.setCompDate(compDate);
							compInfo.setDirection(FundDirectionEnum.OUT);
							compMap.put(compDate + "_OUT", compInfo);
						}
						
						PaymentMenthodEnum payType = compDetail.getPayType();
						if (payType == PaymentMenthodEnum.COMPENSATORY_PRINCIPAL) {
							compInfo.setCompPrincipal(compDetail.getPayAmount());
							compResult.getCompPrincipal().addTo(compDetail.getPayAmount());
						} else if (payType == PaymentMenthodEnum.COMPENSATORY_INTEREST) {
							compInfo.setCompInterest(compDetail.getPayAmount());
							compResult.getCompInterest().addTo(compDetail.getPayAmount());
						} else if (payType == PaymentMenthodEnum.COMPENSATORY_PENALTY) {
							compInfo.setCompPenalty(compDetail.getPayAmount());
							compResult.getCompPenalty().addTo(compDetail.getPayAmount());
						} else if (payType == PaymentMenthodEnum.COMPENSATORY_LIQUIDATED_DAMAGES) {
							compInfo.setCompPenaltyInterest(compDetail.getPayAmount());
							compResult.getCompPenaltyInterest().addTo(compDetail.getPayAmount());
						} else if (payType == PaymentMenthodEnum.COMPENSATORY_OTHER) {
							compInfo.setCompOther(compDetail.getPayAmount());
							compResult.getCompOther().addTo(compDetail.getPayAmount());
						}
						compResult.getCompTotal().addTo(compDetail.getPayAmount());
					}
				}
				
				if (!compMap.isEmpty()) {
					List<CompInfo> compList = Lists.newArrayList();
					for (String compDate : compMap.keySet()) {
						compList.add(compMap.get(compDate));
					}
					Collections.sort(compList, new Comparator<CompInfo>() {
						@Override
						public int compare(CompInfo o1, CompInfo o2) {
							Date compDate1 = DateUtil.parse(o1.getCompDate());
							Date compDate2 = DateUtil.parse(o2.getCompDate());
							return compDate1.compareTo(compDate2);
						}
					});
					compResult.setCompList(compList);
				}
				compResult.setSuccess(true);
			}
		} catch (Exception e) {
		}
		
		return compResult;
	}
	
	@Override
	public ReportDataResult projectListExportData(ProjectQueryOrder order) {
		
		logger.info("开始查询项目列表导出数据，order : {}", order);
		
		ReportDataResult result = new ReportDataResult();
		List<FcsField> fields = new ArrayList<FcsField>();
		fields.add(new FcsField("project_code", "项目编码", null));
		fields.add(new FcsField("project_name", "项目名称", null));
		fields.add(new FcsField("customer_name", "客户名称", null));
		fields.add(new FcsField("dept_name", "业务部门", null));
		fields.add(new FcsField("busi_manager_name", "客户经理", null));
		fields.add(new FcsField("busi_type_name", "业务品种", null));
		fields.add(new FcsField("start_time", "授信开始时间", null, DataTypeEnum.DATE));
		fields.add(new FcsField("end_time", "授信截止时间", null, DataTypeEnum.DATE));
		fields.add(new FcsField("contract_amount", "合同金额(元)", null, DataTypeEnum.MONEY));
		fields.add(new FcsField("occur_amount", "发生金额(元)", null, DataTypeEnum.MONEY));
		fields.add(new FcsField("balance", "在保余额(元)", null, DataTypeEnum.MONEY));
		fields.add(new FcsField("released_amount", "解保金额(元)", null, DataTypeEnum.MONEY));
		fields.add(new FcsField("charged_amount", "收费金额(元)", null, DataTypeEnum.MONEY));
		fields.add(new FcsField("confirmed_income_amount", "确认收入金额(元)", null, DataTypeEnum.MONEY));
		
		HashMap<String, FcsField> fieldMap = new HashMap<String, FcsField>();
		fieldMap.put("project_code", new FcsField("project_code", "项目编码", null));
		fieldMap.put("project_name", new FcsField("project_name", "项目名称", null));
		fieldMap.put("customer_name", new FcsField("customer_name", "客户名称", null));
		fieldMap.put("dept_name", new FcsField("dept_name", "业务部门", null));
		fieldMap.put("busi_manager_name", new FcsField("busi_manager_name", "客户经理", null));
		fieldMap.put("busi_type_name", new FcsField("busi_type_name", "业务品种", null));
		fieldMap.put("start_time", new FcsField("start_time", "授信开始时间", null, DataTypeEnum.DATE));
		fieldMap.put("end_time", new FcsField("end_time", "授信截止时间", null, DataTypeEnum.DATE));
		fieldMap.put("contract_amount", new FcsField("contract_amount", "合同金额(元)", null,
			DataTypeEnum.MONEY));
		fieldMap.put("occur_amount", new FcsField("occur_amount", "发生金额(元)", null,
			DataTypeEnum.MONEY));
		fieldMap.put("balance", new FcsField("balance", "在保余额(元)", null, DataTypeEnum.MONEY));
		fieldMap.put("released_amount", new FcsField("released_amount", "解保金额(元)", null,
			DataTypeEnum.MONEY));
		fieldMap.put("charged_amount", new FcsField("charged_amount", "收费金额(元)", null,
			DataTypeEnum.MONEY));
		fieldMap.put("confirmed_income_amount", new FcsField("confirmed_income_amount",
			"确认收入金额(元)", null, DataTypeEnum.MONEY));
		
		String sql = "SELECT p.project_code,p.project_name,p.customer_name,p.dept_name, p.busi_manager_name, p.busi_type_name,p.start_time,p.end_time,p.contract_amount,"
						+ "CASE WHEN p.busi_type LIKE '12%' THEN p.accumulated_issue_amount WHEN p.busi_type LIKE '211%' THEN p.contract_amount ELSE p.loaned_amount END occur_amount,"
						+ "CASE WHEN p.busi_type LIKE '4%' AND p.loaned_amount > 0 THEN p.loaned_amount - p.released_amount - p.comp_principal_amount WHEN releasable_amount > 0 THEN p.releasable_amount - p.released_amount - p.comp_principal_amount ELSE 0 END balance,"
						+ "p.released_amount, p.charged_amount,ic.confirmed_income_amount FROM "
						+ dataPmTitle
						+ "."
						+ " project p LEFT JOIN "
						+ dataFmTitle
						+ "."
						+ "income_confirm ic ON p.project_code = ic.project_code ";
		
		if (order.getLoginUserId() > 0) {
			sql += " JOIN (SELECT COUNT(*) rnum,project_code FROM project_related_user WHERE user_id = "
					+ order.getLoginUserId()
					+ " AND is_del = 'NO' "
					+ " GROUP BY project_code) r  ON r.project_code = p.project_code AND r.rnum > 0 ";
		}
		if (ListUtil.isNotEmpty(order.getDeptIdList())) {
			String deptIds = "";
			for (long deptId : order.getDeptIdList()) {
				deptIds += String.valueOf(deptId) + ",";
			}
			if (!"".equals(deptIds)) {
				deptIds = deptIds.substring(0, deptIds.length() - 1);
				sql += " JOIN (SELECT COUNT(*) rnum,project_code FROM project_related_user WHERE is_del = 'NO' AND dept_id in ("
						+ deptIds
						+ ")"
						+ "GROUP BY project_code) rl ON rl.project_code = p.project_code AND rl.rnum > 0 ";
			}
		}
		sql += "where 1=1";
		if (StringUtil.isNotBlank(order.getProjectCode())) {
			sql += " and p.project_code = '" + order.getProjectCode() + "'";
		}
		if (StringUtil.isNotBlank(order.getBusiType())) {
			sql += " and p.busi_type like '" + order.getBusiType() + "%'";
		}
		if (StringUtil.isNotBlank(order.getCustomerName())) {
			sql += " and p.customer_name like '%" + order.getCustomerName() + "%'";
		}
		if (StringUtil.isNotBlank(order.getBusiManagerName())) {
			sql += " and p.busi_manager_name like '%" + order.getBusiManagerName() + "%'";
		}
		if (order.getPhases() != null) {
			sql += " and p.phases = '" + order.getPhases().code() + "'";
		}
		
		sql += " ORDER BY p.project_id";
		PmReportDOQueryOrder queryOrder = new PmReportDOQueryOrder();
		queryOrder.setSql(sql);
		//		queryOrder.setLimitStart(limitStart);
		//		queryOrder.setPageSize(pageSize);
		queryOrder.setFieldMap(fieldMap);
		List<DataListItem> items = pmReportServiceClient.doQuery(queryOrder);
		
		result.setDataList(items);
		result.setFcsFields(fields);
		result.setSuccess(true);
		
		return result;
	}
}
