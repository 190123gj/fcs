package com.bornsoft.web.app.project.fund;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.am.ws.enums.base.FormStatusEnum;
import com.born.fcs.am.ws.info.transfer.FAssetsTransferApplicationInfo;
import com.born.fcs.am.ws.order.transfer.FAssetsTransferApplicationQueryOrder;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChargeBasisEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractResultInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationFeeInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractQueryOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationFeeOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.bornsoft.api.service.app.DataPermissionUtil;
import com.bornsoft.api.service.app.JckFormService;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.AppUtils;
import com.bornsoft.web.app.base.WorkflowBaseController;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 收费通知
 * @author heh
 *
 */
@Controller
@RequestMapping("projectMg/chargeNotification")
public class ChargeNotificationController extends WorkflowBaseController {

	@Autowired
	private JckFormService jckFormService;
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "payTime", "startTime", "endTime", "timeBegin", "timeEnd" };
	}

	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "payAmount", "chargeBase", "chargeAmount","anotherPayAmount" };
	}

	/**
	 * 筛选合同
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("contractChoose.json")
	@ResponseBody
	public JSONObject contractChoose(ProjectContractQueryOrder order, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			order.setLikeContractCodeOrName(request.getParameter(LIKE_CODE_OR_NAME));
			order.setIsChargeNotification("IS");
			if(StringUtils.isBlank(order.getProjectCode())){
				throw new  BornApiException("项目编码不能为空");
			}
			QueryBaseBatchResult<ProjectContractResultInfo> batchResult = projectContractServiceClient
				.query(order);
			JSONObject page = makePage(batchResult);
			JSONArray dataList = new JSONArray();
			page.put("result", dataList);
			result.put("page", page);
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (ProjectContractResultInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("contractCode", info.getContractCode());
					json.put("contractName", info.getContractName());
					json.put("contractType", info.getContractType().code());
					json.put("contractTypeMessage", info.getContractType().message());
					dataList.add(json);
				}
			}
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			toJSONResult(result, AppResultCodeEnum.FAILED, "查询合同失败");
			logger.error("查询合同失败", e);
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("queryProjects.json")
	public Object queryProjects(HttpServletRequest request, ProjectQueryOrder queryOrder) {
		JSONObject result = new JSONObject();
		String likeCodeOrName = request.getParameter(LIKE_CODE_OR_NAME);
		if(StringUtils.isNotBlank(likeCodeOrName)){
			queryOrder.setProjectCodeOrName(likeCodeOrName);
		}
		try {
			//查询项目特有条件
			List<ProjectStatusEnum> statusList = new ArrayList<>();
			statusList.addAll(Arrays.asList(ProjectStatusEnum.NORMAL,ProjectStatusEnum.RECOVERY,ProjectStatusEnum.TRANSFERRED,ProjectStatusEnum.SELL_FINISH,ProjectStatusEnum.FINISH,ProjectStatusEnum.EXPIRE,ProjectStatusEnum.OVERDUE));
			queryOrder.setStatusList(statusList);
			queryOrder.setHasContract(BooleanEnum.IS);
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (StringUtil.equals("YES", request.getParameter("setAuthCondition"))) {
				setSessionLocalInfo2Order(queryOrder);
			}
			
			if (sessionLocal != null && DataPermissionUtil.isBusiManager()) {
				queryOrder.setBusiManagerId(sessionLocal.getUserId());
			}
			
			if (null != request.getParameter("busiManagerId")) {
				queryOrder.setBusiManagerId(NumberUtil.parseLong(request
					.getParameter("busiManagerId")));
			}
			
			if (null != request.getParameter("fromStamp")
				&& "IS".equals(request.getParameter("fromStamp"))) {//用印
				if (DataPermissionUtil.isRiskSecretary()) {//风控委秘书查所有项目
					queryOrder.setBusiManagerId(0);
				}
			}
			queryOrder.setPhases(ProjectPhasesEnum.getByCode(request.getParameter("phases")));
			queryOrder.setStatus(ProjectStatusEnum.getByCode(request.getParameter("status")));
			queryOrder.setPhasesStatus(ProjectPhasesStatusEnum.getByCode(request
				.getParameter("phasesStatus")));
			queryOrder.setCustomerType(CustomerTypeEnum.getByCode(request
				.getParameter("customerType")));
			queryOrder.setHasContract(BooleanEnum.getByCode(request.getParameter("hasContract")));
			queryOrder.setSortCol("p.raw_update_time");
			queryOrder.setSortOrder("DESC");
			String phasesStrList = request.getParameter("phasesList");
			if (StringUtil.isNotBlank(phasesStrList)) {
				List<ProjectPhasesEnum> phasesList = new ArrayList<>();
				String[] phases = phasesStrList.split(",");
				for (String s : phases) {
					ProjectPhasesEnum e = ProjectPhasesEnum.getByCode(s);
					if (null != e) {
						phasesList.add(e);
					}
				}
				queryOrder.setPhasesList(phasesList);
			}
			String phasesStatusStrList = request.getParameter("phasesStatusList");
			if (StringUtil.isNotBlank(phasesStatusStrList)) {
				List<ProjectPhasesStatusEnum> phasesStatusList = new ArrayList<>();
				String[] phases = phasesStatusStrList.split(",");
				for (String s : phases) {
					ProjectPhasesStatusEnum e = ProjectPhasesStatusEnum.getByCode(s);
					if (null != e) {
						phasesStatusList.add(e);
					}
				}
				queryOrder.setPhasesStatusList(phasesStatusList);
			}
			
			String busiTypes = request.getParameter("busiTypes");
			if (StringUtil.isNotBlank(busiTypes)) {
				List<String> busiTypeList = new ArrayList<>();
				String[] types = busiTypes.split(",");
				for (String s : types) {
					if (StringUtil.isNotEmpty(s)) {
						busiTypeList.add(s.trim());
					}
				}
				queryOrder.setBusiTypeList(busiTypeList);
			}
			
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			QueryBaseBatchResult<ProjectInfo> batchResult = projectServiceClient
				.queryProject(queryOrder);
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (ProjectInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("projectCode", info.getProjectCode());
					json.put("projectName", info.getProjectName());
					json.put("customerId", info.getCustomerId());
					json.put("customerName", info.getCustomerName());
					json.put("busiManagerId", info.getBusiManagerId());
					json.put("busiManagerName", info.getBusiManagerName());
					json.put("deptCode", info.getDeptCode());
					json.put("phases", info.getPhases().message());
					json.put("status", info.getStatus().message());
					json.put("spCode", info.getSpCode());
					json.put("timeLimit", info.getTimeUnit() == null ? "-" : info.getTimeLimit()
																				+ info
																					.getTimeUnit()
																					.viewName());
					json.put("institutionName", info.getIndustryName());
					
					json.put("amount", AppUtils.toString(info.getAmount()));
					json.put("amountW",
						NumberUtil.format(MoneyUtil.getMoneyByw(info.getAmount()), "0.00").toString());
					json.put("busiType", info.getBusiType());
					json.put("busiTypeName", info.getBusiTypeName());
					//已代偿金额
//					json.put("compAmount",AppUtils.toString(info.getCompInterestAmount().add(info.getCompInterestAmount())));
					dataList.add(json);
				}
			}
			data.put("totalPageCount", batchResult.getPageCount());
			data.put("currentPageNo", batchResult.getPageNumber());
			data.put("pageSize", batchResult.getPageSize());
			data.put("totalCount", batchResult.getTotalCount());
			data.put("result", dataList);
			result.put("page", data);
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			logger.error("查询项目列表失败：", e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "查询项目列表失败");
		}
		return result;
	}
	
	/**
	 * 加载费用种类
	 *
	 * @param projectCode
	 * @return
	 */
	@RequestMapping("loadFeeType.json")
	@ResponseBody
	public JSONObject loadFeeTypeEnum(String projectCode) {
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			List<FeeTypeEnum> list=getFeeTypeEnum(projectCode);
			if(list!=null&&list.size()>0){
				for(FeeTypeEnum typeEnum:list){
					JSONObject json = new JSONObject();
					json.put("code",typeEnum.code());
					json.put("message",typeEnum.message());
					dataList.add(json);
				}
				result.put("data", dataList);
			}
			toJSONResult(result,AppResultCodeEnum.SUCCESS, "");
		}catch (Exception e) {
			toJSONResult(result,AppResultCodeEnum.FAILED, "加载收费种类出错:" + e.getMessage());
			logger.error("加载收费种类出错", e);
		}
		return result;
	}
	
	/**
	 * 保存收费通知
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveChargeNotification.json")
	@ResponseBody
	public JSONObject saveChargeNotification(Model model,HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				toJSONResult(jsonObject, AppResultCodeEnum.FAILED, "您未登陆或登陆已失效");
				return jsonObject;
			}
			LocalFChargeNotificationOrder order = parseStreamToObject(request,LocalFChargeNotificationOrder.class);
			ProjectInfo project  = projectServiceClient.queryByCode(order.getProjectCode(), false);
			if(project!=null){
				order.setCustomerId(project.getCustomerId());
				order.setCustomerName(project.getCustomerName());
			}
			if(order.getFeeList().size()==0){
				toJSONResult(jsonObject, AppResultCodeEnum.FAILED, "请填写收费信息");
				return jsonObject;
			
			}
			//App 端默认为项目
			order.setChargeBasis(ChargeBasisEnum.PROJECT);
			if(order.getChargeBasis()==ChargeBasisEnum.PROJECT){
				ProjectInfo projectInfo=projectServiceClient.queryByCode(order.getProjectCode(),false);
				if(projectInfo.getStatus()== ProjectStatusEnum.PAUSE){
					toJSONResult(jsonObject, AppResultCodeEnum.FAILED, "该项目已被暂缓，不允许保存！");
					logger.error("保存收费通知出错", "该项目已被暂缓，不允许保存！");
					return jsonObject;
				}
			}else{
				order.setProjectCode(order.getApplyCode());
				order.setCustomerName(order.getApplyTitle());
			}
			// 初始化Form验证信息
			order.setCheckIndex(0);
			order.setRelatedProjectCode(order.getProjectCode());
			order.setFormId(order.getFormId());
			order.setFormCode(FormCodeEnum.CHARGE_NOTIFICATION);
			order.setSubmitManId(sessionLocal.getUserId());
			order.setSubmitManName(sessionLocal.getRealName());
			setSessionLocalInfo2Order(order);
			//校验feeType是存出保证划回 时不能大于project表self_deposit_amount
			boolean flag = true;
			if (order.getProjectCode() != null&&order.getChargeBasis()==ChargeBasisEnum.PROJECT) {
				flag = checkBackAmount(order);
			}
			if (!flag) {
				toJSONResult(jsonObject, AppResultCodeEnum.FAILED, "存出保证金划回金额不能超过该项目已划付的存出保证金金额");
				logger.error("保存失败,存出保证金划回金额不能超过该项目已划付的存出保证金金额", "");
				return jsonObject;
			}
			
			FormBaseResult saveResult = chargeNotificationServiceClient.saveChargeNotification(order);
			if(saveResult.isSuccess() && (StringUtils.isBlank(order.getStatus()) || "on".equals(order.getStatus()))){
				jsonObject = jckFormService.submit(saveResult.getFormInfo().getFormId(), request);
				if(AppResultCodeEnum.FAILED.code().equals(jsonObject.getString(CODE))){
					jsonObject = toJSONResult(jsonObject, AppResultCodeEnum.FAILED, "保存收费通知成功，但提交失败");
				}
				jsonObject.put("formId", saveResult.getFormInfo().getFormId());
				jsonObject.put("submitStatus", order.getSubmitStatus());
			}else{
				toJSONResult(jsonObject, saveResult, "项目信息保存成功", saveResult.getMessage());
			}
		} catch (Exception e) {
			logger.error("保存收费通知出错", e);
			toJSONResult(jsonObject, AppResultCodeEnum.FAILED, "保存收费通知出错:" + e.getMessage());
		}

		return jsonObject;
	}

	
	/**
	 * 得到费用类型枚举
	 * @param projectCode
	 * @return
	 */
	private List<FeeTypeEnum> getFeeTypeEnum(String projectCode){
		ProjectInfo projectInfo = projectServiceClient.queryByCode(projectCode,false);
		boolean isGuarantee = ProjectUtil.isGuarantee(projectInfo.getBusiType());//是否担保业务
		boolean isLitigation = ProjectUtil.isLitigation(projectInfo.getBusiType());//是否诉讼保函业务
		boolean isUnderwriting = ProjectUtil.isUnderwriting(projectInfo.getBusiType());//是否是承销业务
		boolean isEntrusted = ProjectUtil.isEntrusted(projectInfo.getBusiType());//是否是委贷业务
		boolean isBond = ProjectUtil.isBond(projectInfo.getBusiType());//发债是担保的一种
		List<FeeTypeEnum> feeTypeEnum= Lists.newArrayList();
		if(projectInfo.getStatus()==ProjectStatusEnum.FINISH){
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_DEDIT_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_OTHER_WITHDRAWAL);
		}else{

		if(isGuarantee||isBond){
			//担保费、项目评审费、追偿收入、代偿本金收回、代偿利息收回、顾问费、存出保证金划回、存入保证金、代收费用、其他
			feeTypeEnum.add(FeeTypeEnum.GUARANTEE_FEE);
			feeTypeEnum.add(FeeTypeEnum.PROJECT_REVIEW_FEE);
			feeTypeEnum.add(FeeTypeEnum.RECOVERY_INCOME);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_DEDIT_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_OTHER_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.CONSULT_FEE);
			feeTypeEnum.add(FeeTypeEnum.GUARANTEE_DEPOSIT);
			feeTypeEnum.add(FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK);
			feeTypeEnum.add(FeeTypeEnum.PROXY_CHARGING);
			feeTypeEnum.add(FeeTypeEnum.OTHER);
		}
		if(isEntrusted){
			//项目评审费、追偿收入、代偿本金收回、代偿利息收回、委贷本金收回、委贷利息收回、顾问费、存出保证金划回、存入保证金、其他
			feeTypeEnum.add(FeeTypeEnum.PROJECT_REVIEW_FEE);
			feeTypeEnum.add(FeeTypeEnum.RECOVERY_INCOME);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_DEDIT_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_OTHER_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.ENTRUSTED_LOAN_INTEREST_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.ENTRUSTED_LOAN_POUNDAGE);
			feeTypeEnum.add(FeeTypeEnum.CONSULT_FEE);
			feeTypeEnum.add(FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK);
			feeTypeEnum.add(FeeTypeEnum.GUARANTEE_DEPOSIT);
			feeTypeEnum.add(FeeTypeEnum.OTHER);
		}
		if(isLitigation){
			//担保费、顾问费、代收费、追偿收入、代偿本金收回、代偿利息收回
			feeTypeEnum.add(FeeTypeEnum.GUARANTEE_FEE);
			feeTypeEnum.add(FeeTypeEnum.CONSULT_FEE);
			feeTypeEnum.add(FeeTypeEnum.PROXY_CHARGING);
			feeTypeEnum.add(FeeTypeEnum.RECOVERY_INCOME);
			feeTypeEnum.add(FeeTypeEnum.GUARANTEE_DEPOSIT);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_DEDIT_WITHDRAWAL);
			feeTypeEnum.add(FeeTypeEnum.COMPENSATORY_OTHER_WITHDRAWAL);
		}
		if(isUnderwriting){
			//承销费、代收费用、其他
			feeTypeEnum.add(FeeTypeEnum.CONSIGNMENT_INWARD_INCOME);
			feeTypeEnum.add(FeeTypeEnum.PROXY_CHARGING);
			feeTypeEnum.add(FeeTypeEnum.OTHER);
		}
 //当选择不需要上会，则该申请单审核通过，可收费；如果需要上会，则该项目上会的会议纪要内容为通过，则可收费，收费种类为“”资产转让“
		FAssetsTransferApplicationQueryOrder queryOrder=new FAssetsTransferApplicationQueryOrder();
		queryOrder.setProjectCode(projectCode);
		QueryBaseBatchResult<FAssetsTransferApplicationInfo> batchResult = assetsTransferApplicationServiceClient
				.query(queryOrder);
        if(batchResult!=null&&batchResult.getPageList().size()>0){
			FAssetsTransferApplicationInfo info=batchResult.getPageList().get(0);
			if(info.getIsToMeet()!=null&&info.getIsToMeet().equals("IS")&&info.getCouncilStatus()==ProjectCouncilStatusEnum.COUNCIL_APPROVAL){
				feeTypeEnum.add(FeeTypeEnum.ASSETS_TRANSFER);
			}else if(info.getFormStatus().code().equals(FormStatusEnum.APPROVAL.code())){//审核通过
				feeTypeEnum.add(FeeTypeEnum.ASSETS_TRANSFER);
			}
		}
		}
		return feeTypeEnum;
	}


	private boolean checkBackAmount(FChargeNotificationOrder order) {
		List<FChargeNotificationInfo> noticeInfos = chargeNotificationServiceClient
				.queryAuditingByProjectCode(order.getProjectCode());
		Money totalBackMoney = new Money(0, 0);
		if (noticeInfos != null) {
			for (FChargeNotificationInfo noticeInfo : noticeInfos) {//审核中的单子 存出保证金总计
				List<FChargeNotificationFeeInfo> feeInfos = noticeInfo.getFeeList();
				if (feeInfos != null) {
					for (FChargeNotificationFeeInfo feeInfo : feeInfos) {
						if (feeInfo.getFeeType().equals(FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK)) {
							totalBackMoney = totalBackMoney.add(feeInfo.getChargeAmount());
						}
					}
				}
			}
		}
		List<FChargeNotificationFeeOrder> feeOrders = order.getFeeList();
		if (feeOrders != null) {
			for (FChargeNotificationFeeOrder feeOrder : feeOrders) {//加上本张单子的金额
				if (feeOrder.getFeeType() != null) {
					if (feeOrder.getFeeType().equals(FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK)) {
						totalBackMoney = totalBackMoney.add(feeOrder.getChargeAmount());
					}
				}
			}
		}
		ProjectInfo projectInfo = projectServiceClient.queryByCode(order.getProjectCode(), false);
		//校验
		if (projectInfo.getSelfDepositAmount().compareTo(totalBackMoney) == -1) {
			return false;
		} else {
			return true;
		}
	}

	public static class LocalFChargeNotificationOrder extends FChargeNotificationOrder{
		/**
		 */
		private static final long serialVersionUID = 1L;
		private String status;

		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
	}
}
