package com.born.fcs.face.web.controller.report;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.bpm.service.client.user.SysUser;
import com.born.fcs.crm.ws.service.enums.ChanalTypeEnum;
import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
import com.born.fcs.crm.ws.service.info.CustomerDetailInfo;
import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
import com.born.fcs.face.integration.pm.service.common.SysParameterServiceClient;
import com.born.fcs.face.integration.pm.service.report.PmReportServiceClient;
import com.born.fcs.face.integration.pm.service.report.ProjectBaseInfoServiceClient;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.fm.ws.info.payment.FormPaymentFeeInfo;
import com.born.fcs.fm.ws.info.payment.FormPaymentFormInfo;
import com.born.fcs.fm.ws.info.receipt.FormReceiptFeeInfo;
import com.born.fcs.fm.ws.info.receipt.FormReceiptFormInfo;
import com.born.fcs.fm.ws.order.payment.FormPaymentQueryOrder;
import com.born.fcs.fm.ws.order.receipt.FormReceiptQueryOrder;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.base.QueryPageBase;
import com.born.fcs.pm.ws.enums.ContractStatusEnum;
import com.born.fcs.pm.ws.enums.EnterpriseNatureEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractResultInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.FCreditConditionConfirmInfo;
import com.born.fcs.pm.ws.info.report.ProjectInfoBaseInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryInfo;
import com.born.fcs.pm.ws.info.risklevel.RiskLevelInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractQueryOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.FCreditConditionConfirmQueryOrder;
import com.born.fcs.pm.ws.order.risklevel.RiskLevelQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.order.ProjectBaseInfoQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportItem;
import com.born.fcs.rm.ws.info.report.ChannelClassfyOrder;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/** 基层定期报表数据页面生成 */
@Controller
@RequestMapping("baseDataLoad")
public class EditBaseDataController extends BaseController {
	
	@Autowired
	private PmReportServiceClient pmReportService;
	
	@Autowired
	private ProjectBaseInfoServiceClient projectBaseInfoServiceClient;
	
	/**
	 * 查询数据
	 * 
	 * 根据不同页签查询需要的数据
	 * */
	@RequestMapping("toDataPage.htm")
	public String getData(HttpServletRequest request, Model model) {
		String page = request.getParameter("page");//页签
		switch (page) {
			case "customerBasicInfo":
				customerBasicInfo(request, model);
				break;
			case "projectBasicInfo":
				ProjectBaseInfoQueryOrder queryOrder = new ProjectBaseInfoQueryOrder();
				WebUtil.setPoPropertyByRequest(queryOrder, request);
				ProjectInfoBaseInfo projectBaseInfo = projectBaseInfoServiceClient
					.queryProjectBaseInfo(queryOrder);
				model.addAttribute("projectBaseInfo", projectBaseInfo);
				model.addAttribute("channelTypes", ChanalTypeEnum.getAllEnum());
				break;
			case "projectProgressInfo":
				projectProgressInfo(request, model);
				break;
			case "projectSaveInfo":
				projectSaveInfo(request, model);
				break;
			case "projectRunInfo":
				projectRunInfo(request, model);
				break;
			case "projectIncomeInfo":
				projectIncomeInfo(request, model);
				break;
			case "projectRiskInfo":
				projectRiskInfo(request, model);
				break;
			default:
				break;
		}
		getLoginUserInfo(model);
		return "/reportMg/report/userDefinedTable/" + page + ".vm";
	}
	
	/** 获取批量保存项目 */
	@ResponseBody
	@RequestMapping("plProjectList.json")
	public JSONObject plProjectList(HttpServletRequest request) {
		
		ChannelClassfyOrder order = new ChannelClassfyOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		
		JSONObject josn = new JSONObject();
		String tableName = "project_data_info_his_data p ";
		if (order.isNowMoth())
			tableName = "project_data_info p ";
		String sql = "";
		String page = request.getParameter("page");//页签
		switch (page) {
			case "customerBasicInfo":
				sql = "SELECT DISTINCT p.customer_id,p.customer_name FROM "
						+ tableName
						+ "  WHERE ( p.phases!='SET_UP_PHASES'AND p.customer_id>0   OR  p.phases='SET_UP_PHASES'AND p.phases_status='APPROVAL' AND  p.customer_id>0 )";
				break;
			case "projectBasicInfo":
				sql = "SELECT DISTINCT project_code,project_name,customer_name FROM "
						+ tableName
						+ " WHERE ( phases!='SET_UP_PHASES'AND customer_id>0   OR  phases='SET_UP_PHASES'AND phases_status='APPROVAL' AND  customer_id>0 )";
				break;
			case "projectProgressInfo":
				sql = "SELECT DISTINCT project_code,project_name,customer_name FROM "
						+ tableName
						+ " WHERE (phases ='INVESTIGATING_PHASES' OR phases='COUNCIL_PHASES' AND phases_status!='APPROVAL') AND customer_id>0";
				break;
			case "projectSaveInfo":
				sql = "SELECT DISTINCT project_code,project_name,customer_name FROM " + tableName
						+ " WHERE approval_time>0 AND loaned_amount=0  AND customer_id>0";
				break;
			case "projectRunInfo":
				sql = "SELECT DISTINCT project_code,project_name,customer_name FROM "
						+ tableName
						+ " WHERE  loaned_amount>0 AND busi_type REGEXP '^11|^12|^13|^411|^6'  AND customer_id>0";
				break;
			case "projectIncomeInfo":
				sql = "SELECT DISTINCT project_code,project_name,customer_name FROM " + tableName
						+ " WHERE  loaned_amount>0  AND customer_id>0";
				break;
			case "projectRiskInfo":
				sql = "SELECT DISTINCT project_code,project_name,customer_name FROM "
						+ tableName
						+ " WHERE  loaned_amount>0 AND busi_type REGEXP '^11|^12|^13|^411|^6'  AND customer_id>0";
				
				break;
			default:
				break;
		}
		if (!order.isNowMoth() && order.getReportYear() > 0) {
			String thisEndDay = DateUtil.dtSimpleFormat(DateUtil.getEndTimeByYearAndMonth(
				order.getReportYear(), order.getReportMonth()));
			sql += " AND project_date='" + thisEndDay + "'";
			
		}
		//按条件查询
		String customerName = request.getParameter("customerName");
		String busiLicenseNo = request.getParameter("busiLicenseNo");
		String projectName = request.getParameter("projectName");
		String projectCode = request.getParameter("projectCode");
		String projectCodeOrName = request.getParameter("projectCodeOrName");
		if (StringUtil.isNotBlank(customerName))
			sql += " AND p.customer_name like '%" + customerName + "%'";
		if (StringUtil.isNotBlank(busiLicenseNo) && StringUtil.equals(page, "customerBasicInfo")) {
			sql = "SELECT DISTINCT p.customer_id,p.customer_name,c.busi_license_no FROM "
					+ tableName
					+ " join f_project_customer_base_info c on p.customer_id=c.customer_id WHERE ( p.phases!='SET_UP_PHASES'AND p.customer_id>0   OR  p.phases='SET_UP_PHASES'AND p.phases_status='APPROVAL' AND  p.customer_id>0 )"
					+ " AND c.busi_license_no like '%" + busiLicenseNo + "%'";
			if (StringUtil.isNotBlank(customerName))
				sql += " AND p.customer_name like '%" + customerName + "%'";
		}
		if (StringUtil.isNotBlank(projectName))
			sql += " AND p.project_name like '%" + projectName + "%'";
		if (StringUtil.isNotBlank(projectCode))
			sql += " AND p.project_code like '%" + projectCode + "%'";
		if (StringUtil.isNotBlank(projectCodeOrName))
			sql += " AND (p.project_code like '%" + projectCodeOrName
					+ "%' OR p.project_name like '%" + projectCodeOrName + "%')";
		
		QueryPageBase page1 = new QueryPageBase();
		WebUtil.setPoPropertyByRequest(page1, request);
		PageComponent component = null;
		if (StringUtil.isNotBlank(request.getParameter("pageNumber"))) {
			long count = pmReportService.doQueryCount(sql);
			component = new PageComponent(page1, count);
			sql += " LIMIT " + component.getFirstRecord() + "," + component.getPageSize();
		}
		
		PmReportDOQueryOrder queryOrder = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		queryOrder.setFieldMap(fieldMap);
		queryOrder.setSql(sql);
		List<DataListItem> rs = pmReportService.doQuery(queryOrder);
		if (ListUtil.isNotEmpty(rs)) {
			List<JSONObject> list = new ArrayList<JSONObject>();
			for (DataListItem itm : rs) {
				JSONObject rsJ = new JSONObject();
				if (StringUtil.notEquals("null", String.valueOf(itm.getMap().get("project_code"))))
					rsJ.put("projectCode", String.valueOf(itm.getMap().get("project_code")));
				if (StringUtil.notEquals("null", String.valueOf(itm.getMap().get("customer_id"))))
					rsJ.put("customerId",
						Long.parseLong(String.valueOf(itm.getMap().get("customer_id"))));
				if (StringUtil.notEquals("null", String.valueOf(itm.getMap().get("customer_name"))))
					rsJ.put("customerName", String.valueOf(itm.getMap().get("customer_name")));
				if (StringUtil.notEquals("null", String.valueOf(itm.getMap().get("project_name"))))
					rsJ.put("projectName", String.valueOf(itm.getMap().get("project_name")));
				if (StringUtil.notEquals("null",
					String.valueOf(itm.getMap().get("busi_license_no"))))
					rsJ.put("busiLicenseNo", String.valueOf(itm.getMap().get("busi_license_no")));
				
				list.add(rsJ);
			}
			
			QueryBaseBatchResult<JSONObject> batchResult = new QueryBaseBatchResult<JSONObject>();
			batchResult.setPageList(list);
			batchResult.setPageNumber(1);
			batchResult.setPageCount(1);
			batchResult.setTotalCount(list.size());
			batchResult.setSuccess(true);
			if (component == null) {
				component = new PageComponent(page1, list.size());
			}
			batchResult.initPageParam(component);
			josn.put("data", batchResult);
			josn.put("total", list.size());
			josn.put("list", list);
			josn.put("success", true);
		} else {
			josn.put("success", false);
			josn.put("message", "无满足条件的项目");
		}
		
		return josn;
		
	}
	
	@ResponseBody
	@RequestMapping("loadPageData.json")
	public JSONObject loadPageData(HttpServletRequest request) {
		JSONObject josn = new JSONObject();
		String url = getUrl(request);
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Cookie", request.getHeader("Cookie"));
		httpGet.addHeader("Apache", request.getHeader("Apache"));
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpResponse response = httpclient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(response.getEntity(), "utf-8");
				if (StringUtil.isNotBlank(result)) {
					String type = request.getParameter("type");
					if (StringUtil.equals(type, "pl")) {
						josn.put("data", result.replace(
							"<div func='日期插入'></div>",
							"<p class='fn-tac fn-fwb fn-fs16 fn-f0 fn-mt20 table-ttl'>"
									+ request.getParameter("reportYear") + "年"
									+ request.getParameter("reportMonth") + "月</p>"));
						
					} else {
						josn.put("data", result.replace("hiddenTable", "active"));
					}
					josn.put("success", true);
				} else {
					josn.put("success", false);
					josn.put("message", "查询失败");
				}
			}
		} catch (ClientProtocolException e2) {
			logger.error(e2.getMessage(), e2);
		} catch (IOException e2) {
			logger.error(e2.getMessage(), e2);
		}
		
		return josn;
		
	}
	
	/** 项目储备情况 */
	private void projectSaveInfo(HttpServletRequest request, Model model) {
		String projectName = request.getParameter("projectName");
		String customerName = request.getParameter("customerName");
		String projectCode = request.getParameter("projectCode");
		if (StringUtil.isBlank(customerName) && StringUtil.isBlank(projectName)
			&& StringUtil.isBlank(projectCode)) {
			return;
		}
		
		String add_condition = "";
		String sql = "SELECT p.project_code,p.project_name,p.contract_time,p.customer_name,IFNULL(c.busi_license_no,'') busi_license_no,IFNULL(p.amount,0) amount, IFNULL(p.guarantee_fee_rate,0) guarantee_fee_rate,p.guarantee_amount,p.contract_amount,IFNULL(pr.time_limit,0) time_limit,pr.time_unit,p.phases,p.phases_status ,MAX(f.submit_time) submit_time,p.approval_time FROM project_data_info p JOIN project pr ON p.project_code=pr.project_code JOIN f_project_customer_base_info c ON p.project_code=c.project_code JOIN f_investigation fi ON p.project_code=fi.project_code JOIN form f ON fi.form_id=f.form_id  WHERE p.approval_time>0 AND p.loaned_amount=0  add_condition ORDER BY p.raw_update_time DESC LIMIT 0,1";
		if (StringUtil.isNotBlank(projectName))
			add_condition += " AND p.project_name='projectName'"
				.replace("projectName", projectName);
		if (StringUtil.isNotBlank(customerName))
			add_condition += " AND p.customer_name='customerName'".replace("customerName",
				customerName);
		if (StringUtil.isNotBlank(projectCode))
			add_condition += " AND p.project_code='projectCode'"
				.replace("projectCode", projectCode);
		
		sql = sql.replace("add_condition", add_condition);
		
		PmReportDOQueryOrder order = new PmReportDOQueryOrder();
		HashMap<String, FcsField> fieldMap = new HashMap<>();
		FcsField field = new FcsField();
		field.setDataTypeEnum(DataTypeEnum.MONEY);
		field.setColName("amount");
		fieldMap.put("amount", field);
		
		FcsField field1 = new FcsField();
		field1.setDataTypeEnum(DataTypeEnum.DATE);
		field1.setColName("submit_time");
		fieldMap.put("submit_time", field1);
		
		FcsField field2 = new FcsField();
		field2.setDataTypeEnum(DataTypeEnum.DATE);
		field2.setColName("approval_time");
		fieldMap.put("approval_time", field1);
		
		order.setFieldMap(fieldMap);
		order.setSql(sql);
		List<DataListItem> list = pmReportService.doQuery(order);
		if (ListUtil.isNotEmpty(list)) {
			DataListItem itm = list.get(0);
			if (itm.getMap().get("project_code") != null) {
				ProjectInfo info = new ProjectInfo();
				info.setProjectCode(String.valueOf(itm.getMap().get("project_code")));
				info.setProjectName(String.valueOf(itm.getMap().get("project_name")));
				info.setCustomerName(String.valueOf(itm.getMap().get("customer_name")));
				info.setBusiManagerAccount(String.valueOf(itm.getMap().get("busi_license_no")));//营业执照号
				info.setBusiManagerbAccount(String.valueOf(itm.getMap().get("guarantee_fee_rate")));//担保费率
				itm.getMap().get("guarantee_amount");
				itm.getMap().get("contract_amount");
				info.setTimeLimit(Integer.parseInt(String.valueOf(itm.getMap().get("time_limit"))));
				info.setTimeUnit(TimeUnitEnum.getByCode(String.valueOf(itm.getMap()
					.get("time_unit"))));
				info.setAmount(Money.amout(String.valueOf(itm.getMap().get("amount"))));
				info.setPhases(ProjectPhasesEnum.getByCode(String.valueOf(itm.getMap()
					.get("phases"))));
				info.setPhasesStatus(ProjectPhasesStatusEnum.getByCode(String.valueOf(itm.getMap()
					.get("phases_status"))));
				info.setStartTime((Date) itm.getMap().get("submit_time"));//提交尽职调查报告的时间
				info.setApprovalTime((Date) itm.getMap().get("approval_time"));//上会通过的时间
				
				//查询项目全部合同
				ProjectContractQueryOrder cOrder = new ProjectContractQueryOrder();
				cOrder.setProjectCode(String.valueOf(itm.getMap().get("project_code")));
				QueryBaseBatchResult<ProjectContractResultInfo> contract = projectContractServiceClient
					.query(cOrder);
				boolean htQb = true;//合同全部通过
				String htString = "合同已全部签订";
				if (contract.isSuccess() && ListUtil.isNotEmpty(contract.getPageList())) {
					
					for (ProjectContractResultInfo infos : contract.getPageList()) {
						if (StringUtil.notEquals(String.valueOf(infos.getContractStatus()), "null")
							&& (infos.getContractStatus() == ContractStatusEnum.SUBMIT
								|| infos.getContractStatus() == ContractStatusEnum.AUDITING
								|| infos.getContractStatus() == ContractStatusEnum.CHECKED
								|| infos.getContractStatus() == ContractStatusEnum.APPROVAL
								|| infos.getContractStatus() == ContractStatusEnum.DENY
								|| infos.getContractStatus() == ContractStatusEnum.CONFIRMED
								|| infos.getContractStatus() == ContractStatusEnum.SEALING || infos
								.getContractStatus() == ContractStatusEnum.SEAL)) {
							htQb = false;
							htString = "合同申请通过，未全部签订，";
							break;
						}
					}
				} else {
					htString = "";
				}
				//查询授信落实
				FCreditConditionConfirmQueryOrder queryOrder = new FCreditConditionConfirmQueryOrder();
				queryOrder.setProjectCode(projectCode);
				QueryBaseBatchResult<FCreditConditionConfirmInfo> credit = projectCreditConditionServiceClient
					.query(queryOrder);
				String sxString = "授信条件未全部落实";
				if (credit.isSuccess() && ListUtil.isNotEmpty(credit.getPageList())) {
					boolean qb = true;//已全部落实
					for (FCreditConditionConfirmInfo infos : credit.getPageList()) {
						if (infos.getFormStatus() == FormStatusEnum.SUBMIT
							|| infos.getFormStatus() == FormStatusEnum.AUDITING
							|| infos.getFormStatus() == FormStatusEnum.BACK
							|| infos.getFormStatus() == FormStatusEnum.DENY) {
							qb = false;
							break;
						}
					}
					if (qb)
						sxString = "授信条件已全部落实";
				}
				//项目阶段
				if (contract.isSuccess() && ListUtil.isNotEmpty(contract.getPageList())) {
					if (htQb) {
						info.setDeptName(htString);
					} else {
						info.setDeptName(htString + sxString);
					}
				}
				
				model.addAttribute("info", info);
			}
			
		}
	}
	
	/** 项目推进情况 */
	private void projectProgressInfo(HttpServletRequest request, Model model) {
		String projectName = request.getParameter("projectName");
		String projectCode = request.getParameter("projectCode");
		String customerName = request.getParameter("customerName");
		if (StringUtil.isBlank(customerName) && StringUtil.isBlank(projectName)
			&& StringUtil.isBlank(projectCode)) {
			return;
		}
		ProjectQueryOrder projectQueryOrder = new ProjectQueryOrder();
		projectQueryOrder.setProjectName(projectName);
		projectQueryOrder.setCustomerName(customerName);
		projectQueryOrder.setProjectCode(projectCode);
		QueryBaseBatchResult<ProjectInfo> result = projectServiceClient
			.queryProject(projectQueryOrder);
		if (result != null && ListUtil.isNotEmpty(result.getPageList())) {
			ProjectInfo info = result.getPageList().get(0);
			info.setContractNo("");//作判断复议字段用
			if (StringUtil.isBlank(projectCode)
				&& StringUtil.notEquals(info.getPhases().getCode(),
					ProjectPhasesEnum.SET_UP_PHASES.code())
				&& StringUtil.notEquals(info.getPhases().getCode(),
					ProjectPhasesEnum.INVESTIGATING_PHASES.code())
				&& (StringUtil.notEquals(info.getPhases().getCode(),
					ProjectPhasesEnum.COUNCIL_PHASES.code()) || (StringUtil.equals(info.getPhases()
					.getCode(), ProjectPhasesEnum.COUNCIL_PHASES.code()) && info.getPhasesStatus() == ProjectPhasesStatusEnum.APPROVAL))) {
				//没用项目编号查的是单个查询，只查批复前的
				return;
			}
			
			//立项通过时间 startTime
			String sql = "SELECT u.raw_update_time 'date' FROM f_project p JOIN form f ON p.form_id=f.form_id JOIN form_related_user u ON f.form_id=u.form_id WHERE f.status='APPROVAL' AND p.project_code='"
							+ info.getProjectCode() + "' ORDER BY u.raw_update_time DESC LIMIT 0,1";
			PmReportDOQueryOrder order = new PmReportDOQueryOrder();
			HashMap<String, FcsField> fieldMap = new HashMap<>();
			FcsField field = new FcsField();
			field.setDataTypeEnum(DataTypeEnum.DATE);
			field.setColName("date");
			fieldMap.put("date", field);
			order.setFieldMap(fieldMap);
			order.setSql(sql);
			List<DataListItem> list = pmReportService.doQuery(order);
			if (ListUtil.isNotEmpty(list))
				for (DataListItem itm : list) {
					for (ReportItem it : itm.getValueList()) {
						info.setStartTime((Date) it.getObject());
					}
				}
			
			//尽调通过时间 endTime
			sql = "SELECT f.finish_time 'date' FROM f_investigation p JOIN form f ON p.form_id=f.form_id  WHERE f.status='APPROVAL' AND p.project_code='"
					+ info.getProjectCode() + "'";
			order.setSql(sql);
			list = pmReportService.doQuery(order);
			if (ListUtil.isNotEmpty(list))
				for (DataListItem itm : list) {
					for (ReportItem it : itm.getValueList()) {
						info.setEndTime((Date) it.getObject());
					}
				}
			
			//会议开始时间
			sql = "SELECT c.start_time 'date' FROM council_projects p JOIN council c ON p.council_code=c.council_code WHERE p.project_code='"
					+ info.getProjectCode() + "'";
			order.setSql(sql);
			list = pmReportService.doQuery(order);
			if (ListUtil.isNotEmpty(list))
				for (DataListItem itm : list) {
					for (ReportItem it : itm.getValueList()) {
						info.setApprovalTime((Date) it.getObject());
					}
					//					info.setApprovalTime(DateUtil.parse(String.valueOf(itm.getMap().get("date"))));
				}
			
			//复议
			String huyMessage = "";
			
			//有会议开始时间却没的尽调时间，提起会议了,重新查尽调时间
			if (info.getEndTime() == null && info.getApprovalTime() != null) {
				
				sql = "SELECT f.finish_time 'date' FROM f_investigation p JOIN form f ON p.form_id=f.form_id  WHERE f.status='DELETED' AND p.project_code='"
						+ info.getProjectCode() + "'";
				order.setSql(sql);
				list = pmReportService.doQuery(order);
				if (ListUtil.isNotEmpty(list))
					for (DataListItem itm : list) {
						for (ReportItem it : itm.getValueList()) {
							info.setEndTime((Date) it.getObject());
						}
					}
				
				huyMessage = "7、已提起复议";
			}
			
			//营业执照号 industryName
			sql = "SELECT IFNULL(busi_license_no,'') no FROM f_project_customer_base_info WHERE project_code='"
					+ info.getProjectCode() + "'";
			order.setSql(sql);
			list = pmReportService.doQuery(order);
			if (ListUtil.isNotEmpty(list))
				for (DataListItem itm : list) {
					info.setIndustryName(String.valueOf(itm.getMap().get("no")));
					
				}
			
			String lxMessage = "";//项目立项阶段记录
			sql = "SELECT f.status status,f.submit_time 'date' FROM f_project  p JOIN form f ON f.form_id=p.form_id WHERE p.project_code='"
					+ info.getProjectCode() + "'";
			order.setSql(sql);
			list = pmReportService.doQuery(order);
			if (ListUtil.isNotEmpty(list)) {
				String tj = "";//提交信息
				String sh = "";//审核信息
				for (DataListItem itm : list) {
					ProjectPhasesStatusEnum status = ProjectPhasesStatusEnum.getByCode(String
						.valueOf(itm.getMap().get("status")));
					if (status == null) {
						if (StringUtil.equals(String.valueOf(itm.getMap().get("status")),
							FormStatusEnum.DENY.getCode())
							|| StringUtil.equals(String.valueOf(itm.getMap().get("status")),
								FormStatusEnum.BACK.getCode())) {
							status = ProjectPhasesStatusEnum.NOPASS;
						}
						
					}
					if (status == ProjectPhasesStatusEnum.NOPASS
						|| status == ProjectPhasesStatusEnum.APPROVAL
						|| status == ProjectPhasesStatusEnum.AUDITING) {
						
						if (status == ProjectPhasesStatusEnum.AUDITING) {
							tj += "1、" + "立项申请已提交，等待审核通过；";
							
						} else {
							tj += "1、" + "立项申请已提交，等待审核通过；";
							
							sh += "2、" + "立项申请" + status.getMessage() + "；";
							
						}
					}
					
				}
				lxMessage = tj + sh;
			}
			
			//尽调阶段记录
			String jdMessage = "";//项目尽调阶段记录
			sql = "SELECT status ,f.submit_time 'date' FROM f_investigation i JOIN form f ON i.form_id=f.form_id WHERE project_code='"
					+ info.getProjectCode() + "'";
			order.setSql(sql);
			list = pmReportService.doQuery(order);
			if (ListUtil.isNotEmpty(list)) {
				String tj = "";//提交信息
				String sh = "";//审核信息
				for (DataListItem itm : list) {
					ProjectPhasesStatusEnum status = ProjectPhasesStatusEnum.getByCode(String
						.valueOf(itm.getMap().get("status")));
					if (status == null) {
						if (StringUtil.equals(String.valueOf(itm.getMap().get("status")),
							FormStatusEnum.DENY.getCode())
							|| StringUtil.equals(String.valueOf(itm.getMap().get("status")),
								FormStatusEnum.BACK.getCode())) {
							status = ProjectPhasesStatusEnum.NOPASS;
						}
						
					}
					if (StringUtil.equals("DELETED", String.valueOf(itm.getMap().get("status")))
						&& info.getApprovalTime() != null) {
						status = ProjectPhasesStatusEnum.APPROVAL;
					}
					if (status == ProjectPhasesStatusEnum.NOPASS
						|| status == ProjectPhasesStatusEnum.APPROVAL
						|| status == ProjectPhasesStatusEnum.AUDITING) {
						
						if (status == ProjectPhasesStatusEnum.AUDITING) {
							tj += "3、" + "尽职调查报告已提交，等待审核通过；";
						} else {
							tj += "3、" + "尽职调查报告已提交，等待审核通过；";
							
							sh += "4、" + "尽职调查报告审核" + status.getMessage() + "；";
						}
						
					}
					
				}
				jdMessage = tj + sh;
			}
			
			//会议阶段记录
			String hyMessage = "";//项目会议阶段记录
			sql = "SELECT c.council_id,c.council_code,IFNULL(c.project_vote_result,'') 'status',co.start_time 'date' FROM council_apply a LEFT JOIN council_projects c ON a.apply_id=c.apply_id  JOIN council co ON c.council_id=co.council_id WHERE c.project_code='"
					+ info.getProjectCode()
					+ "'  ORDER BY a.raw_add_time ASC,c.raw_add_time ASC,co.raw_add_time ASC";
			order.setSql(sql);
			list = pmReportService.doQuery(order);
			if (ListUtil.isNotEmpty(list)) {
				String tj = "";//提交信息
				String sh = "";//审核信息
				for (DataListItem itm : list) {
					String status = String.valueOf(itm.getMap().get("status"));
					String code = String.valueOf(itm.getMap().get("council_code"));
					
					if (StringUtil.isBlank(status)
						|| StringUtil.equals(status, ProjectVoteResultEnum.NOT_BEGIN.code())
						|| StringUtil.equals(status, ProjectVoteResultEnum.IN_VOTE.code())) {
						
						if (StringUtil.isBlank(tj)) {
							tj += "5、" + "已安排" + code + "上会，等待上会完成；";
						} else {
							tj += "已安排" + code + "上会，等待上会完成；";
						}
					} else {
						String rs = ProjectVoteResultEnum.getMsgByCode(status);
						if (StringUtil.isBlank(tj)) {
							tj += "5、" + "已安排" + code + "上会，等待上会完成；";
						} else {
							tj += "已安排" + code + "上会，等待上会完成；";
						}
						
						if (StringUtil.isBlank(sh)) {
							sh += "6、" + "上会" + code + "" + rs + "；";
						} else {
							sh += "上会" + code + "" + rs + "；";
						}
						
					}
				}
				hyMessage = tj + sh;
			}
			
			model.addAttribute("message", lxMessage + jdMessage + hyMessage + huyMessage);
			model.addAttribute("info", info);
			model.addAttribute("timeUnitEnum", TimeUnitEnum.getAllEnum());
			model.addAttribute("projectPhasesEnum", ProjectPhasesEnum.getAllEnum());
			model.addAttribute("status", ProjectPhasesStatusEnum.getAllEnum());
		}
		
	}
	
	/** 查询客户基本情况 */
	private void customerBasicInfo(HttpServletRequest request, Model model) {
		String customerName = request.getParameter("customerName");
		String busiLicenseNo = request.getParameter("busiLicenseNo");
		String customerId = request.getParameter("customerId");
		if (StringUtil.isBlank(customerName) && StringUtil.isBlank(busiLicenseNo)
			&& StringUtil.isBlank(customerId)) {
			return;
		}
		
		long userId = 0;
		if (StringUtil.isNotBlank(customerId)) {
			userId = Long.parseLong(customerId);
		} else {
			CustomerQueryOrder queryOrder = new CustomerQueryOrder();
			queryOrder.setCustomerName(customerName);
			queryOrder.setBusiLicenseNo(busiLicenseNo);
			QueryBaseBatchResult<CustomerBaseInfo> result = customerServiceClient.list(queryOrder);
			if (result != null && ListUtil.isNotEmpty(result.getPageList())) {
				userId = result.getPageList().get(0).getUserId();
				
			}
		}
		
		if (userId > 0) {
			CustomerDetailInfo userInfo = customerServiceClient.queryByUserId(userId);
			if (StringUtil.isNotBlank(userInfo.getIndustryCode()))
				userInfo.setIndustryCode(userInfo.getIndustryCode().substring(0, 1));//行业代码大类
			//info3 是否本年新增客户
			if (StringUtil.equals(
				DateUtil.formatYear(userInfo.getRawAddTime()),
				StringUtil.defaultIfBlank(request.getParameter("reportYear"),
					DateUtil.formatYear(new Date())))) {
				userInfo.setInfo3("Y");
			} else {
				userInfo.setInfo3("N");
			}
			//是否在保客户
			long count = pmReportService
				.doQueryCount("SELECT * FROM project_data_info WHERE custom_name1='在保' AND customer_id="
								+ userId);
			if (count > 0) {
				userInfo.setProjectStatus("Y");
			} else {
				userInfo.setProjectStatus("N");
			}
			model.addAttribute("info", userInfo);
			model.addAttribute("enterpriseNatureEnum", EnterpriseNatureEnum.getAllEnum());
		}
		
	}
	
	/** 项目运行情况表 */
	private void projectRunInfo(HttpServletRequest request, Model model) {
		ProjectQueryOrder queryOrder = new ProjectQueryOrder();
		queryOrder.setCustomerName(request.getParameter("customerName"));
		queryOrder.setProjectCodeOrName(request.getParameter("projectCodeOrName"));
		if (StringUtil.isNotBlank(request.getParameter("projectCode"))) {
			//批量生成时只传入项目编号
			queryOrder.setProjectCode(request.getParameter("projectCode"));
		}
		QueryBaseBatchResult<ProjectInfo> result = projectServiceClient.queryProject(queryOrder);
		if (result != null && ListUtil.isNotEmpty(result.getPageList())) {
			ProjectInfo projectInfo = result.getPageList().get(0);
			long userId = projectInfo.getCustomerId();
			CustomerDetailInfo userInfo = customerServiceClient.queryByUserId(userId);
			model.addAttribute("projectInfo", projectInfo);
			model.addAttribute("customerInfo", userInfo);
			Date now = councilServiceClient.getSysDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			if (StringUtil.isNotBlank(request.getParameter("reportYear"))) {
				year = Integer.parseInt(request.getParameter("reportYear"));
			}
			if (StringUtil.isNotBlank(request.getParameter("reportMonth"))) {
				month = Integer.parseInt(request.getParameter("reportMonth"));
			}
			PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
			HashMap<String, FcsField> fieldMap = new HashMap<>();
			queyOrder.setFieldMap(fieldMap);
			//年初在保余额
			String balanceYearInitSql = "SELECT occer.project_code, (oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount FROM view_project_occer_detail WHERE occur_date <= '"
										+ (year - 1)
										+ "-12-31' and project_code='"
										+ projectInfo.getProjectCode()
										+ "' GROUP BY project_code) occer "
										+ "LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_confirm_time <= '"
										+ (year - 1)
										+ "-12-31' and project_code='"
										+ projectInfo.getProjectCode()
										+ "'  GROUP BY project_code) repay ON repay.project_code = occer.project_code ";
			queyOrder.setSql(balanceYearInitSql);
			List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
			if (dataResult != null) {
				for (DataListItem itm : dataResult) {
					String balanceYearInitAmount = MoneyUtil.format(Money.amout(
						String.valueOf(itm.getMap().get("balance"))).divide(100));
					model.addAttribute("balanceYearInitAmount", balanceYearInitAmount);
				}
			}
			//年末在保余额
			String balanceYearEndSql = "SELECT occer.project_code, (oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount FROM view_project_occer_detail WHERE occur_date <= '"
										+ DateUtil.dtSimpleFormat(DateUtil
											.getEndTimeByYearAndMonth(year, month))
										+ "' and project_code='"
										+ projectInfo.getProjectCode()
										+ "' GROUP BY project_code) occer "
										+ "LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_confirm_time <= '"
										+ DateUtil.dtSimpleFormat(DateUtil
											.getEndTimeByYearAndMonth(year, month))
										+ "' and project_code='"
										+ projectInfo.getProjectCode()
										+ "'  GROUP BY project_code) repay ON repay.project_code = occer.project_code ";
			queyOrder.setSql(balanceYearEndSql);
			dataResult = pmReportServiceClient.doQuery(queyOrder);
			if (dataResult != null) {
				for (DataListItem itm : dataResult) {
					String balanceYearEndAmount = MoneyUtil.format(Money.amout(
						String.valueOf(itm.getMap().get("balance"))).divide(100));
					model.addAttribute("balanceYearEndAmount", balanceYearEndAmount);
				}
			}
			//发生额
			String occurYearSql = "SELECT SUM(r.occur_amount) amount FROM view_project_occer_detail r "
									+ "WHERE  r.occur_date >= '"
									+ year
									+ "-01-01' AND occur_date <= '"
									+ year
									+ "-12-31' and project_code='"
									+ projectInfo.getProjectCode()
									+ "' GROUP BY r.project_code";
			//本年累计
			queyOrder.setSql(occurYearSql);
			dataResult = pmReportServiceClient.doQuery(queyOrder);
			if (dataResult != null) {
				for (DataListItem itm : dataResult) {
					String accurAmountYear = MoneyUtil.format(Money.amout(
						String.valueOf(itm.getMap().get("amount"))).divide(100));
					model.addAttribute("accurAmountYear", accurAmountYear);
				}
			}
			//本月
			String occurThisMonthSql = "SELECT r.occur_amount amount,r.occur_date occur_date FROM view_project_occer_detail r "
										+ "WHERE  r.occur_date >= '"
										+ DateUtil.dtSimpleFormat(DateUtil
											.getStartTimeByYearAndMonth(year, month))
										//+ "2017-01-01"
										+ "' AND occur_date <= '"
										+ DateUtil.dtSimpleFormat(DateUtil
											.getEndTimeByYearAndMonth(year, month))
										+ "' and project_code='"
										+ projectInfo.getProjectCode()
										+ "' order by r.occur_date";
			fieldMap = new HashMap<>();
			queyOrder.setSql(occurThisMonthSql);
			dataResult = pmReportServiceClient.doQuery(queyOrder);
			if (dataResult != null) {
				List<String[]> occurThisMonthList = new ArrayList<String[]>();
				for (DataListItem itm : dataResult) {
					String[] temp = new String[2];
					String accurAmountMonth = MoneyUtil.format(Money.amout(
						String.valueOf(itm.getMap().get("amount"))).divide(100));
					String occurDate = String.valueOf(itm.getMap().get("occur_date"));
					temp[0] = accurAmountMonth;
					temp[1] = occurDate;
					occurThisMonthList.add(temp);
				}
				model.addAttribute("occurThisMonthList", occurThisMonthList);
			}
			//解保额
			String relaseYearSql = "SELECT SUM(r.repay_amount) amount FROM view_project_repay_detail r "
									+ "WHERE  r.repay_confirm_time >= '"
									+ year
									+ "-01-01' AND r.repay_confirm_time <= '"
									+ year
									+ "-12-31' and project_code='"
									+ projectInfo.getProjectCode()
									+ "' conditionStr" + " GROUP BY r.project_code";
			if (ProjectUtil.isEntrusted(projectInfo.getBusiType())) {
				relaseYearSql = relaseYearSql.replaceAll("conditionStr", " and repay_type='解保'");
			} else if (ProjectUtil.isLitigation(projectInfo.getBusiType())) {
				relaseYearSql = relaseYearSql.replaceAll("conditionStr", " and repay_type='诉保解保'");
			} else {
				relaseYearSql = relaseYearSql.replaceAll("conditionStr", "");
			}
			//本年累计
			queyOrder.setSql(relaseYearSql);
			dataResult = pmReportServiceClient.doQuery(queyOrder);
			if (dataResult != null) {
				for (DataListItem itm : dataResult) {
					String relaseAmountYear = MoneyUtil.format(Money.amout(
						String.valueOf(itm.getMap().get("amount"))).divide(100));
					model.addAttribute("relaseAmountYear", relaseAmountYear);
				}
			}
			//本月
			String relaseThisMonthSql = "SELECT r.repay_amount amount,r.repay_confirm_time repay_time FROM view_project_repay_detail r "
										+ "WHERE  r.repay_confirm_time >= '"
										+ DateUtil.dtSimpleFormat(DateUtil
											.getStartTimeByYearAndMonth(year, month))
										//+ "2017-01-01"
										+ "' AND r.repay_confirm_time <= '"
										+ DateUtil.dtSimpleFormat(DateUtil
											.getEndTimeByYearAndMonth(year, month))
										+ "' and project_code='"
										+ projectInfo.getProjectCode()
										+ "' conditionStr" + " order by r.repay_confirm_time";
			fieldMap = new HashMap<>();
			if (ProjectUtil.isEntrusted(projectInfo.getBusiType())) {
				relaseThisMonthSql = relaseThisMonthSql.replaceAll("conditionStr",
					" and repay_type='解保'");
			} else if (ProjectUtil.isLitigation(projectInfo.getBusiType())) {
				relaseThisMonthSql = relaseThisMonthSql.replaceAll("conditionStr",
					" and repay_type='诉保解保'");
			} else {
				relaseThisMonthSql = relaseThisMonthSql.replaceAll("conditionStr", "");
			}
			queyOrder.setSql(relaseThisMonthSql);
			dataResult = pmReportServiceClient.doQuery(queyOrder);
			if (dataResult != null) {
				List<String[]> occurThisMonthList = new ArrayList<String[]>();
				for (DataListItem itm : dataResult) {
					String[] temp = new String[2];
					String relaseAmountMonth = MoneyUtil.format(Money.amout(
						String.valueOf(itm.getMap().get("amount"))).divide(100));
					String relaseDate = String.valueOf(itm.getMap().get("repay_time"));
					temp[0] = relaseAmountMonth;
					temp[1] = relaseDate;
					occurThisMonthList.add(temp);
				}
				model.addAttribute("relaseThisMonthList", occurThisMonthList);
			}
			
		}
		
	}
	
	/** 项目收入情况表 */
	private void projectIncomeInfo(HttpServletRequest request, Model model) {
		ProjectQueryOrder queryOrder = new ProjectQueryOrder();
		queryOrder.setCustomerName(request.getParameter("customerName"));
		queryOrder.setProjectCodeOrName(request.getParameter("projectName"));
		if (StringUtil.isNotBlank(request.getParameter("projectCode"))) {
			//批量生成时只传入项目编号
			queryOrder.setProjectCode(request.getParameter("projectCode"));
		}
		QueryBaseBatchResult<ProjectInfo> result = projectServiceClient.queryProject(queryOrder);
		if (result != null && ListUtil.isNotEmpty(result.getPageList())) {
			ProjectInfo projectInfo = result.getPageList().get(0);
			long userId = projectInfo.getCustomerId();
			CustomerDetailInfo userInfo = customerServiceClient.queryByUserId(userId);
			model.addAttribute("projectInfo", projectInfo);
			model.addAttribute("customerInfo", userInfo);
			Date now = councilServiceClient.getSysDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			if (StringUtil.isNotBlank(request.getParameter("reportYear"))) {
				year = Integer.parseInt(request.getParameter("reportYear"));
			}
			if (StringUtil.isNotBlank(request.getParameter("reportMonth"))) {
				month = Integer.parseInt(request.getParameter("reportMonth"));
			}
			PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
			HashMap<String, FcsField> fieldMap = new HashMap<>();
			queyOrder.setFieldMap(fieldMap);
			//发生额本月
			String occurThisMonthSql = "SELECT r.occur_amount amount,r.occur_date occur_date FROM view_project_occer_detail r "
										+ "WHERE  r.occur_date >= '"
										+ DateUtil.dtSimpleFormat(DateUtil
											.getStartTimeByYearAndMonth(year, month))
										//+ "2017-01-01"
										+ "' AND occur_date <= '"
										+ DateUtil.dtSimpleFormat(DateUtil
											.getEndTimeByYearAndMonth(year, month))
										+ "' and project_code='"
										+ projectInfo.getProjectCode()
										+ "' order by r.occur_date";
			
			fieldMap = new HashMap<>();
			queyOrder.setSql(occurThisMonthSql);
			List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
			if (dataResult != null) {
				List<String[]> occurThisMonthList = new ArrayList<String[]>();
				for (DataListItem itm : dataResult) {
					String[] temp = new String[2];
					String accurAmountMonth = MoneyUtil.format(Money.amout(
						String.valueOf(itm.getMap().get("amount"))).divide(100));
					String occurDate = String.valueOf(itm.getMap().get("occur_date"));
					temp[0] = accurAmountMonth;
					temp[1] = occurDate;
					occurThisMonthList.add(temp);
				}
				model.addAttribute("occurThisMonthList", occurThisMonthList);
			}
			//解保额本月
			String relaseThisMonthSql = "SELECT r.repay_amount amount,r.repay_confirm_time repay_time FROM view_project_repay_detail r "
										+ "WHERE  r.repay_confirm_time >= '"
										+ DateUtil.dtSimpleFormat(DateUtil
											.getStartTimeByYearAndMonth(year, month))
										//+ "2017-01-01"
										+ "' AND r.repay_confirm_time <= '"
										+ DateUtil.dtSimpleFormat(DateUtil
											.getEndTimeByYearAndMonth(year, month))
										+ "' and project_code='"
										+ projectInfo.getProjectCode()
										+ "' conditionStr" + " order by r.repay_confirm_time";
			if (ProjectUtil.isEntrusted(projectInfo.getBusiType())) {
				relaseThisMonthSql = relaseThisMonthSql.replaceAll("conditionStr",
					" and repay_type='解保'");
			} else if (ProjectUtil.isLitigation(projectInfo.getBusiType())) {
				relaseThisMonthSql = relaseThisMonthSql.replaceAll("conditionStr",
					" and repay_type='诉保解保'");
			} else {
				relaseThisMonthSql = relaseThisMonthSql.replaceAll("conditionStr", "");
			}
			fieldMap = new HashMap<>();
			queyOrder.setSql(relaseThisMonthSql);
			dataResult = pmReportServiceClient.doQuery(queyOrder);
			if (dataResult != null) {
				List<String[]> occurThisMonthList = new ArrayList<String[]>();
				for (DataListItem itm : dataResult) {
					String[] temp = new String[2];
					String relaseAmountMonth = MoneyUtil.format(Money.amout(
						String.valueOf(itm.getMap().get("amount"))).divide(100));
					String relaseDate = String.valueOf(itm.getMap().get("repay_time"));
					temp[0] = relaseAmountMonth;
					temp[1] = relaseDate;
					occurThisMonthList.add(temp);
				}
				model.addAttribute("relaseThisMonthList", occurThisMonthList);
			}
			String pmDbTitle = propertyConfigService.getPmDataBaseTitile();
			String fmDbTitle = propertyConfigService.getFmDataBaseTitile();
			//确认收入
			//本年
			String sql = "SELECT IFNULL(SUM(income_confirmed_amount),0) amount FROM "
							+ "(SELECT SUBSTRING(income_period, 1, 4) year,SUBSTRING(income_period, 6, 2) month,d.income_confirmed_amount "
							+ "	FROM fmDbTitleStr.income_confirm c JOIN fmDbTitleStr.income_confirm_detail d  ON c.income_id = d.income_id AND d.confirm_status = 'IS_CONFIRM' "
							+ "	JOIN pmDbTitleStr.project p ON p.project_code = c.project_code WHERE p.project_code='"
							+ projectInfo.getProjectCode() + "') t ";
			
			sql = sql.replaceAll("pmDbTitleStr", pmDbTitle).replaceAll("fmDbTitleStr", fmDbTitle);
			//本年累计sql
			String thisYearSql = sql
									+ " WHERE year = 'reportYearStr' AND month <= 'reportMonthStr'";
			thisYearSql = thisYearSql.replaceAll("reportYearStr", year + "").replaceAll(
				"reportMonthStr", (month < 10 ? "0" + month : "" + month));
			queyOrder.setSql(thisYearSql);
			dataResult = pmReportServiceClient.doQuery(queyOrder);
			if (dataResult != null) {
				for (DataListItem itm : dataResult) {
					String inComeAmountYear = MoneyUtil.format(Money.amout(
						String.valueOf(itm.getMap().get("amount"))).divide(100));
					model.addAttribute("inComeAmountYear", inComeAmountYear);
				}
			}
			//本月sql
			String thisMonthSql = sql
									+ " WHERE year = 'reportYearStr' AND month = 'reportMonthStr'";
			thisMonthSql = thisMonthSql.replaceAll("reportYearStr", year + "").replaceAll(
				"reportMonthStr", (month < 10 ? "0" + month : "" + month));
			queyOrder.setSql(thisMonthSql);
			dataResult = pmReportServiceClient.doQuery(queyOrder);
			if (dataResult != null) {
				for (DataListItem itm : dataResult) {
					String inComeAmountMonth = MoneyUtil.format(Money.amout(
						String.valueOf(itm.getMap().get("amount"))).divide(100));
					model.addAttribute("inComeAmountMonth", inComeAmountMonth);
				}
			}
		}
		
	}
	
	/** 项目风险情况表 */
	private void projectRiskInfo(HttpServletRequest request, Model model) {
		try {
			String yearStartTime = getYearStartTime();
			String thisEndTime = getThisEndTime();
			String thisStartTime = getThisStartTime();
			if (StringUtil.isNotBlank(request.getParameter("reportYear"))
				&& StringUtil.isNotBlank(request.getParameter("reportMonth"))) {
				int year = Integer.parseInt(request.getParameter("reportYear"));
				int month = Integer.parseInt(request.getParameter("reportMonth"));
				yearStartTime = request.getParameter("reportYear") + "-01-01";
				thisStartTime = DateUtil.dtSimpleFormat(DateUtil.getStartTimeByYearAndMonth(year,
					month));
				thisEndTime = DateUtil.dtSimpleFormat(DateUtil
					.getEndTimeByYearAndMonth(year, month));
			}
			
			String customerName = request.getParameter("customerName");
			String projectCode = request.getParameter("projectCode");
			ProjectQueryOrder queryOrder = new ProjectQueryOrder();
			queryOrder.setCustomerName(customerName);
			queryOrder.setProjectCode(projectCode);
			
			Money yearStartCompMonut = Money.zero();//年初代偿余额
			Money yearSumCompMonut = Money.zero();//本年累计代偿金额
			
			Money yearSumBackMonut = Money.zero();//本年累计回收金额
			
			Money compBJ = Money.zero();//年初代偿本金
			Money compLX = Money.zero();//年初代偿利息
			Money compBackBJ = Money.zero();//年初代偿本金收回
			Money compBackLX = Money.zero();//年初代偿利息收回
			
			List<FormPaymentFeeInfo> listThisFees = Lists.newArrayList();//本月发生代偿金额明细
			
			List<FormReceiptFeeInfo> listThisBackFees = Lists.newArrayList();//本月回收金额明细
			QueryBaseBatchResult<ProjectInfo> result = projectServiceClient
				.queryProject(queryOrder);
			if (result != null && ListUtil.isNotEmpty(result.getPageList())
				&& (StringUtil.isNotEmpty(customerName) || StringUtil.isNotEmpty(projectCode))) {
				ProjectInfo projectInfo = result.getPageList().get(0);
				
				long userId = projectInfo.getCustomerId();
				CustomerDetailInfo userInfo = customerServiceClient.queryByUserId(userId);
				model.addAttribute("projectInfo", projectInfo);
				model.addAttribute("customerInfo", userInfo);
				
				FormPaymentQueryOrder paymentQueryOrder = new FormPaymentQueryOrder();
				paymentQueryOrder.setProjectCode(projectInfo.getProjectCode());
				//			paymentQueryOrder.setQueryFeeDetail(true);
				QueryBaseBatchResult<FormPaymentFormInfo> paymentBatchResult = paymentApplyServiceClient
					.searchForm(paymentQueryOrder);
				List<FormPaymentFormInfo> listFormPaymentFormInfos = Lists.newArrayList();
				if (paymentBatchResult != null && paymentBatchResult.getPageList().size() > 0) {
					listFormPaymentFormInfos = paymentBatchResult.getPageList();
					
					for (FormPaymentFormInfo formPaymentFormInfo : listFormPaymentFormInfos) {
						FormInfo formInfo = formServiceFmClient.findByFormId(formPaymentFormInfo
							.getFormId());
						if (formInfo.getStatus() == FormStatusEnum.APPROVAL) {
							List<FormPaymentFeeInfo> listFeeInfo = paymentApplyServiceClient
								.findPaymentFeeByFormId(formInfo.getFormId());
							
							if (ListUtil.isNotEmpty(listFeeInfo) && listFeeInfo.size() > 0) {
								for (FormPaymentFeeInfo formPaymentFeeInfo : listFeeInfo) {
									String paymentDate = DateUtil.dtSimpleFormat(formPaymentFeeInfo
										.getPaymentDate());
									if (DateUtil.calculateDecreaseDate(paymentDate, yearStartTime) >= 0) {//年初代偿余额
										if (formPaymentFeeInfo.getFeeType() == SubjectCostTypeEnum.COMPENSATORY_PRINCIPAL) {
											compBJ = compBJ.add(formPaymentFeeInfo.getAmount());
										}
										if (formPaymentFeeInfo.getFeeType() == SubjectCostTypeEnum.COMPENSATORY_INTEREST) {
											compLX = compLX.add(formPaymentFeeInfo.getAmount());
										}
									}
									//本年累计代偿金额
									if (DateUtil.calculateDecreaseDate(paymentDate, yearStartTime) <= 0
										&& DateUtil.calculateDecreaseDate(thisEndTime, paymentDate) <= 0) {
										
										//本年累计代偿金额
										if (formPaymentFeeInfo.getFeeType() == SubjectCostTypeEnum.COMPENSATORY_PRINCIPAL) {
											yearSumCompMonut = yearSumCompMonut
												.add(formPaymentFeeInfo.getAmount());
										}
										if (formPaymentFeeInfo.getFeeType() == SubjectCostTypeEnum.COMPENSATORY_INTEREST) {
											yearSumCompMonut = yearSumCompMonut
												.add(formPaymentFeeInfo.getAmount());
										}
									}
									if (DateUtil.calculateDecreaseDate(paymentDate, thisStartTime) <= 0
										&& DateUtil.calculateDecreaseDate(thisEndTime, paymentDate) <= 0) {
										//本月发生代偿金额 明细
										if (formPaymentFeeInfo.getFeeType() == SubjectCostTypeEnum.COMPENSATORY_PRINCIPAL) {
											listThisFees.add(formPaymentFeeInfo);
										}
										if (formPaymentFeeInfo.getFeeType() == SubjectCostTypeEnum.COMPENSATORY_INTEREST) {
											listThisFees.add(formPaymentFeeInfo);
										}
									}
								}
							}
							
						}
						
					}
					
					FormReceiptQueryOrder receiptQueryOrder = new FormReceiptQueryOrder();
					receiptQueryOrder.setProjectCode(projectInfo.getProjectCode());
					//			paymentQueryOrder.setQueryFeeDetail(true);
					QueryBaseBatchResult<FormReceiptFormInfo> receiptBatchResult = receiptApplyServiceClient
						.searchForm(receiptQueryOrder);
					List<FormReceiptFormInfo> listFormReceiptFormInfos = Lists.newArrayList();
					if (receiptBatchResult != null && receiptBatchResult.getPageList().size() > 0) {
						listFormReceiptFormInfos = receiptBatchResult.getPageList();
						
						for (FormReceiptFormInfo formReceiptFormInfo : listFormReceiptFormInfos) {
							FormInfo formInfo = formServiceFmClient
								.findByFormId(formReceiptFormInfo.getFormId());
							if (formInfo.getStatus() == FormStatusEnum.APPROVAL) {
								List<FormReceiptFeeInfo> listFeeInfo = receiptApplyServiceClient
									.findReceiptFeeByFormId(formInfo.getFormId());
								
								if (ListUtil.isNotEmpty(listFeeInfo) && listFeeInfo.size() > 0) {
									for (FormReceiptFeeInfo formReceiptFeeInfo : listFeeInfo) {
										String paymentDate = DateUtil
											.dtSimpleFormat(formReceiptFeeInfo.getReceiptDate());
										if (DateUtil.calculateDecreaseDate(paymentDate,
											yearStartTime) >= 0) {//年初代偿收回余额
										
											if (formReceiptFeeInfo.getFeeType() == SubjectCostTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL) {
												compBackBJ = compBackBJ.add(formReceiptFeeInfo
													.getAmount());
											}
											if (formReceiptFeeInfo.getFeeType() == SubjectCostTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL) {
												compBackLX = compBackLX.add(formReceiptFeeInfo
													.getAmount());
											}
										}
										if (DateUtil.calculateDecreaseDate(paymentDate,
											yearStartTime) <= 0
											&& DateUtil.calculateDecreaseDate(thisEndTime,
												paymentDate) <= 0) {
											//本年累计回收金额
											if (formReceiptFeeInfo.getFeeType() == SubjectCostTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL) {
												yearSumBackMonut = yearSumBackMonut
													.add(formReceiptFeeInfo.getAmount());
											}
											if (formReceiptFeeInfo.getFeeType() == SubjectCostTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL) {
												yearSumBackMonut = yearSumBackMonut
													.add(formReceiptFeeInfo.getAmount());
											}
										}
										if (DateUtil.calculateDecreaseDate(paymentDate,
											thisStartTime) <= 0
											&& DateUtil.calculateDecreaseDate(thisEndTime,
												paymentDate) <= 0) {
											
											//本月回收金额明细
											if (formReceiptFeeInfo.getFeeType() == SubjectCostTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL) {
												listThisBackFees.add(formReceiptFeeInfo);
											}
											if (formReceiptFeeInfo.getFeeType() == SubjectCostTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL) {
												listThisBackFees.add(formReceiptFeeInfo);
											}
										}
									}
								}
							}
							
						}
					}
				}
				
				yearStartCompMonut = compBJ.add(compLX).subtract(compBackLX).subtract(compBackBJ);
				model.addAttribute("yearStartCompMount", yearStartCompMonut.toStandardString());
				
				model.addAttribute("yearSumCompMount", yearSumCompMonut.toStandardString());
				model.addAttribute("yearSumBackMount", yearSumBackMonut.toStandardString());
				//期末代偿金额
				model.addAttribute("yearSumMount", yearStartCompMonut.addTo(yearSumCompMonut)
					.subtract(yearSumBackMonut).toStandardString());
				model.addAttribute("listThisFees", listThisFees);
				model.addAttribute("listThisBackFees", listThisBackFees);
				
				List<ProjectRecoveryInfo> recoveryResult = projectRecoveryServiceClient
					.findByProjectCode(projectInfo.getProjectCode(), false);
				//损失金额
				if (ListUtil.isNotEmpty(recoveryResult)) {
					Money loseCognizanceAmount = Money.zero();
					for (ProjectRecoveryInfo recovery : recoveryResult) {
						loseCognizanceAmount.addTo(recovery.getLoseCognizanceAmount());
					}
					model.addAttribute("loseCognizanceAmount",
						loseCognizanceAmount.toStandardString());
				} else {
					model.addAttribute("loseCognizanceAmount", Money.zero());
				}
				//项目最新五级分类结果
				
				String newRisekLevel = "";
				RiskLevelQueryOrder riskLevelQueryOrder = new RiskLevelQueryOrder();
				riskLevelQueryOrder.setProjectCode(projectCode);
				riskLevelQueryOrder.setFormStatus("APPROVAL");
				QueryBaseBatchResult<RiskLevelInfo> resultRisekLevel = riskLevelServiceClient
					.queryList(riskLevelQueryOrder);
				if (resultRisekLevel != null && resultRisekLevel.getPageList().size() > 0) {
					List<RiskLevelInfo> levelInfo = resultRisekLevel.getPageList();
					for (RiskLevelInfo riskLevelInfo : levelInfo) {
						if (riskLevelInfo.getCheckLevel() != null) {
							newRisekLevel = riskLevelInfo.getCheckLevel().getMessage();
						}
						
					}
				}
				model.addAttribute("newRisekLevel", newRisekLevel);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Autowired
	SysParameterServiceClient sysParameterServiceClient;
	
	//生成访问url
	private String getUrl(HttpServletRequest request) {
		String page = request.getParameter("page");
		String customerName = request.getParameter("customerName");
		String busiLicenseNo = request.getParameter("busiLicenseNo");
		String projectCode = request.getParameter("projectCode");
		String projectName = request.getParameter("projectName");
		String projectCodeOrName = request.getParameter("projectCodeOrName");
		String reportYear = request.getParameter("reportYear");
		String reportMonth = request.getParameter("reportMonth");
		String customerId = request.getParameter("customerId");
		//
		String url = sysParameterServiceClient.getSysParameterValue("SYS_PARAM_FACE_INTRANET_URL")
						+ "/baseDataLoad/toDataPage.htm?page=" + page;
		if (StringUtil.isNotBlank(customerName))
			url += "&customerName=" + customerName.trim();
		if (StringUtil.isNotBlank(customerId))
			url += "&customerId=" + customerId;
		if (StringUtil.isNotBlank(busiLicenseNo))
			url += "&busiLicenseNo=" + busiLicenseNo.trim();
		if (StringUtil.isNotBlank(projectCode))
			url += "&projectCode=" + projectCode.trim();
		if (StringUtil.isNotBlank(projectName))
			url += "&projectName=" + projectName.trim();
		if (StringUtil.isNotBlank(projectCodeOrName))
			url += "&projectCodeOrName=" + projectCodeOrName.trim();
		if (StringUtil.isNotBlank(reportYear))
			url += "&reportYear=" + reportYear;
		if (StringUtil.isNotBlank(reportMonth))
			url += "&reportMonth=" + reportMonth;
		return url.replaceAll("null", "");
	}
	
	/**
	 * 获得本年开始时间
	 * 
	 * @return
	 */
	public String getYearStartTime() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			c.set(Calendar.MONTH, 0);
			c.set(Calendar.DATE, 1);
			SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd");
			now = longSdf.parse(longSdf.format(c.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DateUtil.dtSimpleFormat(now);
	}
	
	/**
	 * 获得本期起始时间
	 * 
	 * @return
	 */
	public String getThisStartTime() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			//			c.set(Calendar.YEAR, reportYear);
			//			c.set(Calendar.MONTH, reportMonth - 1);
			c.set(Calendar.DATE, 1);
			SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd");
			now = longSdf.parse(longSdf.format(c.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DateUtil.dtSimpleFormat(now);
	}
	
	/**
	 * 获得本期结束时间
	 * 
	 * @return
	 */
	public String getThisEndTime() {
		Calendar c = Calendar.getInstance();
		Date now = null;
		try {
			SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd");
			//			c.set(Calendar.YEAR, c.YEAR);
			c.set(Calendar.MONTH, c.MONTH);
			c.set(Calendar.DATE, 1);
			c.add(Calendar.DATE, -1);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 0);
			now = longSdf.parse(longSdf.format(c.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DateUtil.dtSimpleFormat(now);
	}
	
	/** 获取填表人信息 */
	private void getLoginUserInfo(Model model) {
		UserDetailInfo user = ShiroSessionUtils.getSessionLocal().getUserDetailInfo();
		
		model.addAttribute("user", ShiroSessionUtils.getSessionLocal().getRealName());
		model.addAttribute("mobile", user.getMobile());
		model.addAttribute("date", new Date());
		if (user.getPrimaryOrg() != null) {
			List<SysUser> userInfos = bpmUserQueryService.findChargeByOrgCode(user.getPrimaryOrg()
				.getCode());
			if (ListUtil.isNotEmpty(userInfos))
				model.addAttribute("fzr", userInfos.get(0).getFullname());
		}
		
	}
}
