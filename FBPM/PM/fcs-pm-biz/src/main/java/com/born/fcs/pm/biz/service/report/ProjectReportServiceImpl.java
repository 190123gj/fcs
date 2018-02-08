package com.born.fcs.pm.biz.service.report;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.daointerface.ViewProjectChargeDetailDAO;
import com.born.fcs.pm.dal.daointerface.ViewProjectPayDetailDAO;
import com.born.fcs.pm.dal.daointerface.ViewProjectRepayDetailDAO;
import com.born.fcs.pm.dal.dataobject.ViewProjectChargeDetailDO;
import com.born.fcs.pm.dal.dataobject.ViewProjectPayDetailDO;
import com.born.fcs.pm.dal.dataobject.ViewProjectRepayDetailDO;
import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.order.projectcreditcondition.FCreditConditionConfirmQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.PmReportService;
import com.born.fcs.pm.ws.service.report.ProjectReportService;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.info.AmountRecordInfo;
import com.born.fcs.pm.ws.service.report.info.ProjectChargeDetailInfo;
import com.born.fcs.pm.ws.service.report.info.ProjectPayDetailInfo;
import com.born.fcs.pm.ws.service.report.info.ProjectRepayDetailInfo;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.order.ProjectChargeDetailQueryOrder;
import com.born.fcs.pm.ws.service.report.order.ProjectPayDetailQueryOrder;
import com.born.fcs.pm.ws.service.report.order.ProjectRepayDetailQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目相关报表
 * @author wuzj
 */
@Service("projectReportService")
public class ProjectReportServiceImpl extends BaseAutowiredDomainService implements
																		ProjectReportService {
	@Autowired
	ViewProjectChargeDetailDAO viewProjectChargeDetailDAO;
	@Autowired
	ViewProjectPayDetailDAO viewProjectPayDetailDAO;
	@Autowired
	ViewProjectRepayDetailDAO viewProjectRepayDetailDAO;
	@Autowired
	private PmReportService pmReportService;
	
	@Override
	public QueryBaseBatchResult<ProjectRepayDetailInfo> projectRepayDetail(	ProjectRepayDetailQueryOrder order) {
		
		QueryBaseBatchResult<ProjectRepayDetailInfo> baseBatchResult = new QueryBaseBatchResult<ProjectRepayDetailInfo>();
		
		try {
			
			ViewProjectRepayDetailDO searchDO = new ViewProjectRepayDetailDO();
			BeanCopier.staticCopy(order, searchDO);
			
			List<String> relatedRoleList = Lists.newArrayList();
			if (ListUtil.isNotEmpty(order.getRelatedRoleList())) {
				for (ProjectRelatedUserTypeEnum role : order.getRelatedRoleList()) {
					relatedRoleList.add(role.code());
				}
			}
			
			if (StringUtil.isEmpty(order.getSortCol())) {
				order.setSortCol("repay_time");
				order.setSortOrder("desc");
			}
			
			long totalSize = viewProjectRepayDetailDAO.findByConditionCount(searchDO, order
				.getLoginUserId(), order.getDeptIdList(), relatedRoleList, order
				.getProjectCodeOrName(), order.getInGuarantee() == null ? null : order
				.getInGuarantee().code(), order.getRepayTimeStart(), order.getRepayTimeEnd());
			
			PageComponent component = new PageComponent(order, totalSize);
			List<ViewProjectRepayDetailDO> pageList = viewProjectRepayDetailDAO.findByCondition(
				searchDO, order.getLoginUserId(), order.getDeptIdList(), relatedRoleList, order
					.getProjectCodeOrName(), order.getInGuarantee() == null ? null : order
					.getInGuarantee().code(), order.getRepayTimeStart(), order.getRepayTimeEnd(),
				component.getFirstRecord(), component.getPageSize(), order.getSortCol(), order
					.getSortOrder());
			
			List<ProjectRepayDetailInfo> list = baseBatchResult.getPageList();
			if (totalSize > 0) {
				for (ViewProjectRepayDetailDO DO : pageList) {
					ProjectRepayDetailInfo info = new ProjectRepayDetailInfo();
					BeanCopier.staticCopy(DO, info);
					info.setCustomerType(CustomerTypeEnum.getByCode(DO.getCustomerType()));
					list.add(info);
				}
			}
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(list);
			baseBatchResult.setSuccess(true);
		} catch (Exception e) {
			baseBatchResult.setSuccess(false);
			baseBatchResult.setMessage("查询出错");
			logger.error("查询项目还款明细出错：{}", e);
		}
		
		return baseBatchResult;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectPayDetailInfo> projectPayDetail(	ProjectPayDetailQueryOrder order) {
		
		QueryBaseBatchResult<ProjectPayDetailInfo> baseBatchResult = new QueryBaseBatchResult<ProjectPayDetailInfo>();
		try {
			
			ViewProjectPayDetailDO searchDO = new ViewProjectPayDetailDO();
			BeanCopier.staticCopy(order, searchDO);
			
			List<String> relatedRoleList = Lists.newArrayList();
			if (ListUtil.isNotEmpty(order.getRelatedRoleList())) {
				for (ProjectRelatedUserTypeEnum role : order.getRelatedRoleList()) {
					relatedRoleList.add(role.code());
				}
			}
			
			if (StringUtil.isEmpty(order.getSortCol())) {
				order.setSortCol("pay_time");
				order.setSortOrder("desc");
			}
			
			long totalSize = viewProjectPayDetailDAO.findByConditionCount(searchDO, order
				.getLoginUserId(), order.getDeptIdList(), relatedRoleList, order
				.getProjectCodeOrName(), order.getInGuarantee() == null ? null : order
				.getInGuarantee().code(), order.getPayTimeStart(), order.getPayTimeEnd(), order
				.getFeeTypeList());
			
			PageComponent component = new PageComponent(order, totalSize);
			List<ViewProjectPayDetailDO> pageList = viewProjectPayDetailDAO.findByCondition(
				searchDO, order.getLoginUserId(), order.getDeptIdList(), relatedRoleList, order
					.getProjectCodeOrName(), order.getInGuarantee() == null ? null : order
					.getInGuarantee().code(), order.getPayTimeStart(), order.getPayTimeEnd(), order
					.getFeeTypeList(), component.getFirstRecord(), component.getPageSize(), order
					.getSortCol(), order.getSortOrder());
			
			List<ProjectPayDetailInfo> list = baseBatchResult.getPageList();
			if (totalSize > 0) {
				for (ViewProjectPayDetailDO DO : pageList) {
					ProjectPayDetailInfo info = new ProjectPayDetailInfo();
					BeanCopier.staticCopy(DO, info);
					info.setCustomerType(CustomerTypeEnum.getByCode(DO.getCustomerType()));
					info.setFeeType(PaymentMenthodEnum.getByCode(DO.getFeeType()));
					list.add(info);
				}
			}
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(list);
			baseBatchResult.setSuccess(true);
		} catch (Exception e) {
			baseBatchResult.setSuccess(false);
			baseBatchResult.setMessage("查询出错");
			logger.error("查询项目划付明细出错：{}", e);
		}
		
		return baseBatchResult;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectChargeDetailInfo> projectChargeDetail(	ProjectChargeDetailQueryOrder order) {
		
		QueryBaseBatchResult<ProjectChargeDetailInfo> baseBatchResult = new QueryBaseBatchResult<ProjectChargeDetailInfo>();
		try {
			
			ViewProjectChargeDetailDO searchDO = new ViewProjectChargeDetailDO();
			BeanCopier.staticCopy(order, searchDO);
			
			List<String> relatedRoleList = Lists.newArrayList();
			if (ListUtil.isNotEmpty(order.getRelatedRoleList())) {
				for (ProjectRelatedUserTypeEnum role : order.getRelatedRoleList()) {
					relatedRoleList.add(role.code());
				}
			}
			
			if (StringUtil.isEmpty(order.getSortCol())) {
				order.setSortCol("charge_time");
				order.setSortOrder("desc");
			}
			
			long totalSize = viewProjectChargeDetailDAO.findByConditionCount(searchDO, order
				.getLoginUserId(), order.getDeptIdList(), relatedRoleList, order
				.getProjectCodeOrName(), order.getInGuarantee() == null ? null : order
				.getInGuarantee().code(), order.getChargeTimeStart(), order.getChargeTimeEnd(),
				order.getFeeTypeList());
			
			PageComponent component = new PageComponent(order, totalSize);
			List<ViewProjectChargeDetailDO> pageList = viewProjectChargeDetailDAO.findByCondition(
				searchDO, order.getLoginUserId(), order.getDeptIdList(), relatedRoleList, order
					.getProjectCodeOrName(), order.getInGuarantee() == null ? null : order
					.getInGuarantee().code(), order.getChargeTimeStart(), order.getChargeTimeEnd(),
				order.getFeeTypeList(), component.getFirstRecord(), component.getPageSize(), order
					.getSortCol(), order.getSortOrder());
			
			List<ProjectChargeDetailInfo> list = baseBatchResult.getPageList();
			if (totalSize > 0) {
				for (ViewProjectChargeDetailDO DO : pageList) {
					ProjectChargeDetailInfo info = new ProjectChargeDetailInfo();
					BeanCopier.staticCopy(DO, info);
					info.setCustomerType(CustomerTypeEnum.getByCode(DO.getCustomerType()));
					info.setFeeType(FeeTypeEnum.getByCode(DO.getFeeType()));
					list.add(info);
				}
			}
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(list);
			baseBatchResult.setSuccess(true);
		} catch (Exception e) {
			baseBatchResult.setSuccess(false);
			baseBatchResult.setMessage("查询出错");
			logger.error("查询项目收费明细出错：{}", e);
		}
		
		return baseBatchResult;
	}

	@Override
	public QueryBaseBatchResult<AmountRecordInfo> queryRepayRecord(FCreditConditionConfirmQueryOrder order) {
		QueryBaseBatchResult<AmountRecordInfo> batchResult = new QueryBaseBatchResult<>();
		String countSql = "select count(*) as count from project p LEFT JOIN view_project_repay_detail v on p.project_code=v.project_code where p.status!='FAILED' AND v.project_code IS NOT NULL ";
		String sql = "SELECT p.project_code, p.dept_name, p.busi_manager_name,p.project_name,p.busi_type_name,p.amount, CASE WHEN p.busi_type LIKE '4%' THEN (p.loaned_amount-p.released_amount-p.comp_principal_amount) ELSE (p.releasable_amount-p.released_amount-p.comp_principal_amount) END blance,repay.repay_time,repay.repay_amount FROM  project p  "
				+ "LEFT JOIN (SELECT project_code, repay_amount, repay_time FROM view_project_repay_detail) repay ON p.project_code = repay.project_code "
				+ "WHERE p.status!='FAILED'  AND repay.project_code IS NOT NULL ";
		StringBuilder countSb = new StringBuilder();
		countSb.append(countSql);
		StringBuilder sb = new StringBuilder();
		sb.append(sql);
		if (StringUtil.isNotBlank(order.getDeptName())) {
			countSb.append("AND p.dept_name LIKE '%").append(order.getDeptName()).append("%'");
			sb.append("AND p.dept_name LIKE '%").append(order.getDeptName()).append("%'");
		}
		if (StringUtil.isNotBlank(order.getBusiManagerName())) {
			countSb.append("AND p.busi_manager_name ='").append(order.getBusiManagerName()).append("'");
			sb.append("AND p.busi_manager_name ='").append(order.getBusiManagerName()).append("'");
		}
		if (StringUtil.isNotBlank(order.getProjectName())) {
			countSb.append("AND p.project_name LIKE '%").append(order.getProjectName()).append("%'");
			sb.append("AND p.project_name LIKE '%").append(order.getProjectName()).append("%'");
		}
		if (StringUtil.isNotBlank(order.getBusiType())) {
			countSb.append("AND p.busi_type = ").append(order.getBusiType());
			sb.append("AND p.busi_type = ").append(order.getBusiType());
		}
		if (ListUtil.isNotEmpty(order.getDeptIdList())) {
			countSb.append("AND p.dept_id IN(").append(StringUtil.join(order.getDeptIdList().toArray(), ",")).append(")");
			sb.append("AND p.dept_id IN(").append(StringUtil.join(order.getDeptIdList().toArray(), ",")).append(")");
		}
		if (order.getBusiManagerId() > 0) {
			countSb.append("AND p.busi_manager_id = ").append(order.getBusiManagerId());
			sb.append("AND p.busi_manager_id = ").append(order.getBusiManagerId());
		}
		sb.append(" ORDER BY p.project_id DESC ");
		sb.append(" LIMIT ").append(order.getLimitStart()).append(",").append(order.getPageSize());
		
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(countSb.toString());
		
		List<DataListItem> dataResult = pmReportService.doQuery(queyOrder);
		long totalCount=0;
		if (ListUtil.isNotEmpty(dataResult)) {
			for (DataListItem itm : dataResult) {
				totalCount = Long.parseLong(String.valueOf(itm.getMap().get("count")));
				break;
			}
		}
		
		if (totalCount > 0) {
			batchResult.setSuccess(true);
			batchResult.setTotalCount(totalCount);
			
			queyOrder.setSql(sb.toString());
			dataResult = pmReportService.doQuery(queyOrder);
			
			List<AmountRecordInfo> data = Lists.newArrayList();
			int max = 0;
			if (ListUtil.isNotEmpty(dataResult)) {
				for (DataListItem itm : dataResult) {
					AmountRecordInfo info = new AmountRecordInfo();
					info.setDept(String.valueOf(itm.getMap().get("project_code")));
					String [] fixed = new String[6];
					fixed[0] = String.valueOf(itm.getMap().get("dept_name"));
					fixed[1] = String.valueOf(itm.getMap().get("busi_manager_name"));
					fixed[2] = String.valueOf(itm.getMap().get("project_name"));
					fixed[3] = String.valueOf(itm.getMap().get("busi_type_name"));
					fixed[4] = String.valueOf(itm.getMap().get("amount"));
					fixed[5] = String.valueOf(itm.getMap().get("blance"));
					info.setFixed(fixed);
					
					//多次还款
					Object repayTimes = itm.getMap().get("repay_time");
					Object repayAmounts = itm.getMap().get("repay_amount");
					
					List<String> repayList = Lists.newArrayList();
					Money repayAmount = Money.zero();
					if (repayTimes != null && repayAmounts != null) {
						String[] repayTimeArr = repayTimes.toString().split(",");
						String[] repayAmountArr = repayAmounts.toString().split(",");
						max = max < repayTimeArr.length ? repayTimeArr.length : max;
						for (int i = 0; i < repayTimeArr.length; i++) {
							repayList.add(repayTimeArr[i].substring(0, 10));
							if (repayAmountArr.length >= i) {
								Money amount = toMoney(repayAmountArr[i]);
								repayList.add(MoneyUtil.formatW(amount));
								repayAmount.addTo(amount);
							}
						}
					}
					
					info.setDatas(repayList.toArray(new String[repayList.size()]));
					data.add(info);
				}
			}
			PageComponent component = new PageComponent(order, totalCount);
			batchResult.initPageParam(component);
			batchResult.setPageList(data);
			batchResult.setKeyId(max);
		}
		
		return batchResult;
	}

	@Override
	public QueryBaseBatchResult<AmountRecordInfo> queryLoanRecord(FCreditConditionConfirmQueryOrder order) {
		QueryBaseBatchResult<AmountRecordInfo> batchResult = new QueryBaseBatchResult<>();
		String countSql = "select count(*) as count from project p LEFT JOIN view_project_loan_detail v on p.project_code=v.project_code where p.status!='FAILED' AND v.project_code IS NOT NULL ";
		String sql = "SELECT p.project_code, p.dept_name, p.busi_manager_name,p.project_name,p.busi_type_name,p.amount, CASE WHEN p.busi_type LIKE '4%' THEN (p.loaned_amount-p.released_amount-p.comp_principal_amount) ELSE (p.releasable_amount-p.released_amount-p.comp_principal_amount) END blance,repay.repay_time,repay.repay_amount FROM  project p  "
				+ "LEFT JOIN (SELECT project_code, loan_amount AS repay_amount, loan_time AS repay_time FROM view_project_loan_detail) repay ON p.project_code = repay.project_code "
				+ "WHERE p.status!='FAILED' AND repay.project_code IS NOT NULL ";
		StringBuilder countSb = new StringBuilder();
		countSb.append(countSql);
		StringBuilder sb = new StringBuilder();
		sb.append(sql);
		if (StringUtil.isNotBlank(order.getDeptName())) {
			countSb.append("AND p.dept_name LIKE '%").append(order.getDeptName()).append("%'");
			sb.append("AND p.dept_name LIKE '%").append(order.getDeptName()).append("%'");
		}
		if (StringUtil.isNotBlank(order.getBusiManagerName())) {
			countSb.append("AND p.busi_manager_name ='").append(order.getBusiManagerName()).append("'");
			sb.append("AND p.busi_manager_name ='").append(order.getBusiManagerName()).append("'");
		}
		if (StringUtil.isNotBlank(order.getProjectName())) {
			countSb.append("AND p.project_name LIKE '%").append(order.getProjectName()).append("%'");
			sb.append("AND p.project_name LIKE '%").append(order.getProjectName()).append("%'");
		}
		if (StringUtil.isNotBlank(order.getBusiType())) {
			countSb.append("AND p.busi_type = ").append(order.getBusiType());
			sb.append("AND p.busi_type = ").append(order.getBusiType());
		}
		if (ListUtil.isNotEmpty(order.getDeptIdList())) {
			countSb.append("AND p.dept_id IN(").append(StringUtil.join(order.getDeptIdList().toArray(), ",")).append(")");
			sb.append("AND p.dept_id IN(").append(StringUtil.join(order.getDeptIdList().toArray(), ",")).append(")");
		}
		if (order.getBusiManagerId() > 0) {
			countSb.append("AND p.busi_manager_id = ").append(order.getBusiManagerId());
			sb.append("AND p.busi_manager_id = ").append(order.getBusiManagerId());
		}
		sb.append(" ORDER BY p.project_id DESC ");
		sb.append(" LIMIT ").append(order.getLimitStart()).append(",").append(order.getPageSize());
		
		PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queyOrder.setFieldMap(fieldMap);
		queyOrder.setSql(countSb.toString());
		
		List<DataListItem> dataResult = pmReportService.doQuery(queyOrder);
		long totalCount=0;
		if (ListUtil.isNotEmpty(dataResult)) {
			for (DataListItem itm : dataResult) {
				totalCount = Long.parseLong(String.valueOf(itm.getMap().get("count")));
				break;
			}
		}
		
		if (totalCount > 0) {
			batchResult.setSuccess(true);
			batchResult.setTotalCount(totalCount);
			
			queyOrder.setSql(sb.toString());
			dataResult = pmReportService.doQuery(queyOrder);
			
			List<AmountRecordInfo> data = Lists.newArrayList();
			int max = 0;
			if (ListUtil.isNotEmpty(dataResult)) {
				for (DataListItem itm : dataResult) {
					AmountRecordInfo info = new AmountRecordInfo();
					info.setDept(String.valueOf(itm.getMap().get("project_code")));
					String [] fixed = new String[6];
					fixed[0] = String.valueOf(itm.getMap().get("dept_name"));
					fixed[1] = String.valueOf(itm.getMap().get("busi_manager_name"));
					fixed[2] = String.valueOf(itm.getMap().get("project_name"));
					fixed[3] = String.valueOf(itm.getMap().get("busi_type_name"));
					fixed[4] = String.valueOf(itm.getMap().get("amount"));
					fixed[5] = String.valueOf(itm.getMap().get("blance"));
					info.setFixed(fixed);
					
					//多次还款
					Object repayTimes = itm.getMap().get("repay_time");
					Object repayAmounts = itm.getMap().get("repay_amount");
					
					List<String> repayList = Lists.newArrayList();
					Money repayAmount = Money.zero();
					if (repayTimes != null && repayAmounts != null) {
						String[] repayTimeArr = repayTimes.toString().split(",");
						String[] repayAmountArr = repayAmounts.toString().split(",");
						max = max < repayTimeArr.length ? repayTimeArr.length : max;
						for (int i = 0; i < repayTimeArr.length; i++) {
							repayList.add(repayTimeArr[i].substring(0, 10));
							if (repayAmountArr.length >= i) {
								Money amount = toMoney(repayAmountArr[i]);
								repayList.add(MoneyUtil.formatW(amount));
								repayAmount.addTo(amount);
							}
						}
					}
					
					info.setDatas(repayList.toArray(new String[repayList.size()]));
					data.add(info);
				}
			}
			PageComponent component = new PageComponent(order, totalCount);
			batchResult.initPageParam(component);
			batchResult.setPageList(data);
			batchResult.setKeyId(max);
		}
		
		return batchResult;
	}
	
	//转化Money
	private Money toMoney(Object fen) {
		if (fen != null) {
			try {
				return Money.amout(fen.toString()).divide(100);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return Money.zero();
	}
}
