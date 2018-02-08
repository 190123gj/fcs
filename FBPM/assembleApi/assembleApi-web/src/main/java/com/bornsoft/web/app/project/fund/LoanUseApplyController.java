package com.bornsoft.web.app.project.fund;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.page.Page;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.order.fund.FLoanUseApplyFeeOrder;
import com.born.fcs.pm.ws.order.fund.FLoanUseApplyOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.fund.LoanUseApplyResult;
import com.born.fcs.pm.ws.service.report.ProjectReportService;
import com.born.fcs.pm.ws.service.report.info.ProjectChargeDetailInfo;
import com.born.fcs.pm.ws.service.report.order.ProjectChargeDetailQueryOrder;
import com.bornsoft.api.service.app.DataPermissionUtil;
import com.bornsoft.api.service.app.JckFormService;
import com.bornsoft.api.service.app.util.WebUtil;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.AppUtils;
import com.bornsoft.utils.tool.GsonUtil;
import com.bornsoft.web.app.base.WorkflowBaseController;
import com.bornsoft.web.app.util.PageUtil;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.ListUtil;

/**
 * 放用款申请
 * 
 * @author wuzj
 * 
 */
@Controller
@RequestMapping("projectMg/loanUseApply")
public class LoanUseApplyController extends WorkflowBaseController {

	@Autowired
	JckFormService jckFormService;

	@Autowired
	ProjectReportService projectReportServiceClient;

	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "expectLoanDate", "actualLoanTime",
				"cashDepositEndTime" };
	}

	/**
	 * 选择放用款项目
	 * @param order
	 * @return
	 */
	@RequestMapping("loanUseProject.json")
	@ResponseBody
	public JSONObject loanUseProject(HttpServletRequest request,QueryProjectBase order) {
		
		SessionLocal session = ShiroSessionUtils.getSessionLocal();
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			setSessionLocalInfo2Order(order);
			order.setProjectCodeOrName(request.getParameter(LIKE_CODE_OR_NAME));
			//当前业务经理
			order.setBusiManagerId(session.getUserId());
			
			QueryBaseBatchResult<ProjectSimpleDetailInfo> batchResult = loanUseApplyServiceClient
				.selectLoanUseApplyProject(order);
			List<ProjectSimpleDetailInfo> list = batchResult.getPageList();
			for (ProjectSimpleDetailInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("projectCode", info.getProjectCode());
				json.put("projectName", info.getProjectName());
				json.put("customerId", info.getCustomerId());
				json.put("customerName", info.getCustomerName());
				json.put("busiType", info.getBusiType());
				json.put("busiTypeName", info.getBusiTypeName());

				dataList.add(json);
			}
			data.put("totalCount", batchResult.getTotalCount());
			data.put("totalPageCount", batchResult.getPageCount());
			data.put("currentPageNo", batchResult.getPageNumber());
			data.put("pageSize", batchResult.getPageSize());
			data.put("result", dataList);
			result.put("page", data);
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			toJSONResult(result, AppResultCodeEnum.FAILED, "查询放用款申请项目失败");
			logger.error("查看可放用款项目出错 {}", e);
		}
		return result;
		
	}
	
	@ResponseBody
	@RequestMapping("form.json")
	public JSONObject form(String projectCode, HttpServletRequest request) {

		JSONObject result = new JSONObject();
		try {
			String url = sysParameterServiceClient.getSysParameterValue(ApiSystemParamEnum.FACE_URL.code());
			if(StringUtil.isBlank(url)){
				throw new BornApiException("未配置face访问地址");
			}
			if (StringUtil.isNotBlank(projectCode)) {
				if (!DataPermissionUtil.isBusiManager(projectCode)) {
					toJSONResult(result, AppResultCodeEnum.FAILED, "只有客户经理才能发起放用款");
				} else {
					result.put("projectCode", projectCode);
					applyProjectInfo(projectCode,null, result, getVersion(request));
					result.put("url", url + "/projectMg/chargeNotification/list.htm?accessToken="
					+ getAccessToken());
					toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
				}
			} else {
				toJSONResult(result, AppResultCodeEnum.FAILED, "项目编号不能为空");
			}
		} catch (Exception e) {
			toJSONResult(result, AppResultCodeEnum.FAILED, "获取数据失败,请重试");
			logger.error("",e);
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("saveApply.json")
	public JSONObject saveApply(HttpServletRequest request, String status) {
		
		JSONObject json = new JSONObject();
		try {
			FLoanUseApplyOrder order = new FLoanUseApplyOrder();
			WebUtil.setPoPropertyByRequest(order, request);
			//固定值自行设置
			order.setFormCode(FormCodeEnum.LOAN_USE_APPLY);
			order.setRelatedProjectCode(order.getProjectCode());
			
			//实时查询收费明细
			List<FLoanUseApplyFeeOrder> applyFeeList = Lists.newArrayList();
			ProjectChargeDetailQueryOrder chargeOrder = new ProjectChargeDetailQueryOrder();
			chargeOrder.setProjectCode(order.getProjectCode());
			QueryBaseBatchResult<ProjectChargeDetailInfo> chargeDetail = projectReportServiceClient
				.projectChargeDetail(chargeOrder);
			if (chargeDetail != null && ListUtil.isNotEmpty(chargeDetail.getPageList())) {
				for (ProjectChargeDetailInfo charge : chargeDetail.getPageList()) {
					FLoanUseApplyFeeOrder feeInfo = new FLoanUseApplyFeeOrder();

					feeInfo.setFeeType(charge.getFeeType()!=null?(charge.getFeeType().code()):"");
					feeInfo.setFeeTypeDesc(charge.getFeeType()!=null?(charge.getFeeType().message()):"");

					feeInfo.setChargeBase(charge.getChargeBase());
					feeInfo.setChargeRate(charge.getChargeRate());
					
					feeInfo.setStartTimeStr(DateUtil.dtSimpleFormat(charge.getChargeStartTime()));
					feeInfo.setEndTimeStr(DateUtil.dtSimpleFormat(charge.getChargeEndTime()));
					feeInfo.setChargeAmount(charge.getChargeAmount());
					
					feeInfo.setApplyId(order.getApplyId());
					feeInfo.setId(0L);
					applyFeeList.add(feeInfo);
				}
			}
			order.setFeeOrder(applyFeeList);
		
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				json.put("success", false);
				json.put("message", "您未登陆或登陆已失效");
				return json;
			}
			
			setSessionLocalInfo2Order(order);
			
			LoanUseApplyResult applyResult = loanUseApplyServiceClient.saveApply(order);
		
			toJSONResult(json, applyResult, "申请单保存成功", applyResult.getMessage());
			if(applyResult.isSuccess()){
				if(StringUtils.isBlank(status) || "on".equals(status)){
					json = jckFormService.submit(applyResult.getFormInfo().getFormId(), request);
				}
				json.put("applyingLoanAmount", applyResult.getApplyingLoanAmount());
				json.put("applyingUseAmount", applyResult.getApplyingUseAmount());
				json.put("loanedAmount", applyResult.getLoanedAmount());
				json.put("usedAmount", applyResult.getUsedAmount());
				json.put("balanceLoanAmount", applyResult.getBalanceLoanAmount());
				json.put("balanceUseAmount", applyResult.getBalanceUseAmount());
			}
		} catch (Exception e) {
			toJSONResult(json, AppResultCodeEnum.FAILED, "申请单保存失败");
			logger.error("保存放用款申请单出错：{}", e);
		}
		return json;
	}


	/**
	 * 申请单项目相关信息
	 * @param projectCode
	 * @param model
	 */
	private void applyProjectInfo(String projectCode, FormInfo form,final JSONObject model , int version) {
		
		//仅作查询用
		FLoanUseApplyInfo applyInfo = new FLoanUseApplyInfo();
		applyInfo.setProjectCode(projectCode);
		
		ProjectSimpleDetailInfo project = projectServiceClient.querySimpleDetailInfo(applyInfo.getProjectCode());
		if (project == null) {
			throw ExceptionFactory.newFcsException(
					FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
		}
		model.put("project", GsonUtil.toJSONObject(project));

		LoanUseApplyResult result = null;
		//未提交、撤销、驳回后显示实时的余额
		if (form == null || form.getStatus() == FormStatusEnum.DRAFT
			|| form.getStatus() == FormStatusEnum.CANCEL
			|| form.getStatus() == FormStatusEnum.BACK) {
			result = loanUseApplyServiceClient.queryProjectAmount(project,
				applyInfo.getApplyId());
		} else {//显示表单的余额
			result = loanUseApplyServiceClient.queryFormAmount(applyInfo);
		}

		if (result != null) {
			// 申请中放款
			model.put("applyingLoanAmount", AppUtils.toString(result.getApplyingLoanAmount()));
			// 申请中用款
			model.put("applyingUseAmount", AppUtils.toString(result.getApplyingUseAmount()));
			// 可放款余额
			model.put("balanceLoanAmount", AppUtils.toString(result.getBalanceLoanAmount()));
			// 可用款余额
			model.put("balanceUseAmount", AppUtils.toString(result.getBalanceUseAmount()));
			
			// 剩余可放款余额
			model.put("availableLoanAmount", AppUtils.toString(result.getBalanceLoanAmount().subtract(result.getApplyingLoanAmount())));
			// 剩余可用款余额
			model.put("availableUseAmount", AppUtils.toString(result.getBalanceUseAmount().subtract(result.getApplyingUseAmount())));
		}

		// 授信条件
		if(version >= 9){
			ProjectCreditConditionQueryOrder order = new ProjectCreditConditionQueryOrder();
			order.setProjectCode(projectCode);
			QueryBaseBatchResult<ProjectCreditConditionInfo> batchResult = projectCreditConditionServiceClient
				.queryCreditCondition(order);
			model.put("conditions", GsonUtil.toJSONObject(batchResult.getPageList()));
		}else{
			List<ProjectCreditConditionInfo> allConditions = projectCreditConditionServiceClient
					.findProjectCreditConditionByProjectCode(applyInfo
							.getProjectCode());
			if (ListUtil.isNotEmpty(allConditions)) {
				List<ProjectCreditConditionInfo> conditions = Lists
						.newArrayList();
				for (ProjectCreditConditionInfo condition : allConditions) {
					if (condition.getIsConfirm() == BooleanEnum.IS)
						conditions.add(condition);
				}
				model.put("conditions", GsonUtil.toJSONObject(conditions));
			}
		}
		//是否委贷类业务
		model.put("isEntrusted", isEntrusted(project.getBusiType()));
	}

	/**
	 * 授信条件落实情况查询
	 * @param projectCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("queryCreditConditions.json")
	public JSONObject queryCreditConditions(String projectCode, HttpServletRequest request) {

		JSONObject result = new JSONObject();
		try {
			String url = sysParameterServiceClient.getSysParameterValue(ApiSystemParamEnum.FACE_URL.code());
			if(StringUtil.isBlank(url)){
				throw new BornApiException("未配置face访问地址");
			}
			if(StringUtils.isBlank(projectCode)){
				throw new BornApiException("项目编号[projectCode]不能为空");
			}
			ProjectSimpleDetailInfo project = projectServiceClient.querySimpleDetailInfo(projectCode);
			if (project == null) {
				throw new BornApiException("项目不存在");
			}else{
				result.put("projectName", project.getProjectName());
				result.put("projectCode", projectCode);
				result.put("customerName", project.getCustomerName());
			}
			JSONArray  conditionList = null;
			if(getVersion(request) >= 9){
				ProjectCreditConditionQueryOrder order = new ProjectCreditConditionQueryOrder();
				order.setProjectCode(projectCode);
				QueryBaseBatchResult<ProjectCreditConditionInfo> batchResult = projectCreditConditionServiceClient
					.queryCreditCondition(order);
				if (batchResult != null && batchResult.getFcsResultEnum() == FcsResultEnum.EXECUTE_SUCCESS) {
					Page<ProjectCreditConditionInfo> page = PageUtil.getCovertPage(batchResult);
					JSONObject jsonPage = (JSONObject) GsonUtil.toJSONObject(page);
					result.put("page", jsonPage);
					conditionList = jsonPage.getJSONArray("result");
				}else{
					toJSONResult(result, AppResultCodeEnum.FAILED, batchResult!=null ? batchResult.getMessage() : "查询失败");
					return result;
				}
			
			}else{
				List<ProjectCreditConditionInfo> allConditions = projectCreditConditionServiceClient
						.findProjectCreditConditionByProjectCode(projectCode);
				if (ListUtil.isNotEmpty(allConditions)) {
					List<ProjectCreditConditionInfo> conditions = Lists
							.newArrayList();
					for (ProjectCreditConditionInfo condition : allConditions) {
						if (condition.getIsConfirm() == BooleanEnum.IS)
							conditions.add(condition);
					}
					conditionList = (JSONArray) GsonUtil.toJSONObject(conditions);
					result.put("conditionList", conditionList);
				}
			}
			
			//特殊处理
			if(conditionList!=null){
				String spCode= showProjectApproval(projectCode);
				if(StringUtil.isNotBlank(spCode)){
					String token = getAccessToken();
					for(Object o : conditionList){
						JSONObject e = (JSONObject) o;
						@SuppressWarnings("deprecation")
						String tmpUrl = url+"/projectMg/meetingMg/summary/approval.htm?spCode="+ URLEncoder.encode(spCode)+"&accessToken="+token;
						e.put("url", tmpUrl);
					}
				}
			}
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			toJSONResult(result, AppResultCodeEnum.FAILED, "查询失败：" + e.getMessage());
			logger.error("查询授信落实条件");
		}
		return result;
	} 

}
