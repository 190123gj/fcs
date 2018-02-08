package com.bornsoft.web.app.project.riskcontrol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.PlanTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.fund.ChargeRepayPlanDetailInfo;
import com.born.fcs.pm.ws.info.riskwarning.FRiskWarningCreditInfo;
import com.born.fcs.pm.ws.info.riskwarning.FRiskWarningInfo;
import com.born.fcs.pm.ws.info.riskwarning.RiskWarningInfo;
import com.born.fcs.pm.ws.info.riskwarning.RiskWarningSignalInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanDetailQueryOrder;
import com.born.fcs.pm.ws.order.riskwarning.FRiskWarningCreditOrder;
import com.born.fcs.pm.ws.order.riskwarning.FRiskWarningOrder;
import com.born.fcs.pm.ws.order.riskwarning.RiskWarningQueryOrder;
import com.born.fcs.pm.ws.order.riskwarning.RiskWarningSignalQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.bornsoft.api.service.app.JckFormService;
import com.bornsoft.api.service.app.util.WebUtil;
import com.bornsoft.jck.dal.daointerface.MessageReadStatusDAO;
import com.bornsoft.jck.dal.dataobject.MessageReadStatusDO;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.DateUtils;
import com.bornsoft.utils.tool.GsonUtil;
import com.bornsoft.utils.tool.JsonParseUtil;
import com.bornsoft.web.app.base.WorkflowBaseController;
import com.google.common.collect.Lists;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * 
 * 风险预警
 * 
 * 
 * @author lirz
 * 
 * 2016-4-22 下午4:15:47
 */
@Controller
@RequestMapping("projectMg/riskWarning")
public class RiskWarningController extends WorkflowBaseController {
	
	@Autowired
	private JckFormService jckFormService;
	@Autowired
	private MessageReadStatusDAO messageReadStatus;
	
	@ResponseBody
	@RequestMapping("queryProjects.json")
	public JSONObject queryProjects(HttpServletRequest request,
			ProjectQueryOrder queryOrder) {
		String likeCodeOrName = request.getParameter(LIKE_CODE_OR_NAME);
		if(StringUtils.isNotBlank(likeCodeOrName)){
			queryOrder.setProjectCodeOrName(likeCodeOrName);
		}
		// 查询项目特有条件
		List<ProjectPhasesEnum> phasesList = Arrays.asList(ProjectPhasesEnum.CONTRACT_PHASES,ProjectPhasesEnum.LOAN_USE_PHASES,
				ProjectPhasesEnum.FUND_RAISING_PHASES,ProjectPhasesEnum.AFTERWARDS_PHASES,ProjectPhasesEnum.RECOVERY_PHASES);
		queryOrder.setPhasesList(phasesList);
		return jckFormService.queryProjects(request, queryOrder);
	}
	
	@ResponseBody
	@RequestMapping("read.json")
	public JSONObject read(HttpServletRequest request,long warningId) {
		JSONObject result = new JSONObject();
		try {
			if(warningId>0){
				MessageReadStatusDO messageReadInfo = new MessageReadStatusDO();
				messageReadInfo.setRelatedId(String.valueOf(warningId));
				messageReadInfo.setRowAddTime(DateUtils.getCurrentDate());
				messageReadInfo.setUserName(ShiroSessionUtils.getSessionLocal().getUserName());
				messageReadStatus.insert(messageReadInfo);
				toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
			}else{
				toJSONResult(result, AppResultCodeEnum.FAILED, "风险预警ID不能为空");
			}
		} catch (Exception e) {
			toJSONResult(result, AppResultCodeEnum.FAILED, "标记为已读失败");
			logger.error("标记为已读出错",e);
		}
		return result;
	}
	
	private static enum QueryType{
		/**已读**/
		READ("read"),
		/**"未读"*/
		UN_READ("unRead");
		private String code;
		
		private QueryType(String code) {
			this.code = code;
		}
	}
	
	
	 /** 风险预警处理表
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("warningCount.json")
	public JSONObject warningCount(HttpServletRequest request,String qyeryType) {
		JSONObject result = new JSONObject();
		try {
			JSONObject unRead =  list(request,QueryType.UN_READ.code);
			if(StringUtil.equals(unRead.getString(CODE), AppResultCodeEnum.SUCCESS.code())){
				JSONObject page = unRead.getJSONObject("page");
				int ur = page.getIntValue("totalCount");
				int total = page.getIntValue("oldTotalCount");
				result.put(QueryType.UN_READ.code, ur);
				result.put(QueryType.READ.code, total - ur);
				result.put(TOTAL, total);
				toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
			
			}else{
				throw new BornApiException("查询未读风险消息失败");
			}
		} catch (Exception e) {
			toJSONResult(result, AppResultCodeEnum.FAILED, e.getMessage());
			logger.error("查询出错",e);
		}
		return result;
	}
	
	/**
	 * 风险预警处理表
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("list.json")
	public JSONObject list(HttpServletRequest request,String queryType) {
		JSONObject result = new JSONObject();
		try {

			RiskWarningQueryOrder queryOrder = new RiskWarningQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);

			//郁闷啊,内存里面分页
			int pageNo = (int) (queryOrder.getPageNumber() > 0?queryOrder.getPageNumber():1);
			int pageSize = (int) (queryOrder.getPageSize()>0 ? queryOrder.getPageSize():10);
			setSessionLocalInfo2Order(queryOrder);
			queryOrder.setPageNumber(1);
			queryOrder.setPageSize(300);
			
			QueryBaseBatchResult<RiskWarningInfo> batchResult = riskWarningServiceClient
					.queryList(queryOrder);
			List<RiskWarningInfo> tmpList = new ArrayList<>();
			if(batchResult!=null && batchResult.getTotalCount()>0){
				//查询已读列表
				List<MessageReadStatusDO>  readList = messageReadStatus.queryByUserName(ShiroSessionUtils.getSessionLocal().getUserName());
				for(RiskWarningInfo info : batchResult.getPageList()){
					if(QueryType.READ.code.equals(queryType)){
						if(hasRead(info.getWarningId(),readList)){
							tmpList.add(info);
						}
					}else{
						if(!hasRead(info.getWarningId(),readList)){
							tmpList.add(info);
						}
					}
				}
			}
			
			JSONArray items = new JSONArray();
			JSONObject page = new JSONObject();
			JSONObject jsonItem = null;
			int startIndex = (pageNo-1) * pageSize;
			for(int len=0,i=startIndex;i<tmpList.size() && len<pageSize;i++,len++){
				RiskWarningInfo info = tmpList.get(i);
				jsonItem = new JSONObject();
				jsonItem.put("formId",info.getFormId());
				jsonItem.put("warningId",info.getWarningId());
				if(info.getSignalLevel()!=null){
					jsonItem.put("signalLevel",info.getSignalLevel().message());
				}
				jsonItem.put("customerName",info.getCustomerName());
				jsonItem.put("formUserName",info.getFormUserName());
				jsonItem.put("formUserId",info.getFormUserId());
				jsonItem.put("warningBillType",info.getWarningBillType());
				jsonItem.put("formStatus",info.getFormStatus().getCode());
				jsonItem.put("operateTime", StringUtil.defaultIfBlank(DateUtils.toDefaultString(info.getSubmitTime()), DateUtils.toDefaultString(info.getFormAddTime())));
				items.add(jsonItem);
			}
			page.put("result", items);
			page.put("oldTotalCount", batchResult.getTotalCount());
			page.put("totalCount", tmpList.size());
			page.put("totalPageCount", 1+(tmpList.size()/pageSize));
			page.put("currentPageNo", pageNo);
			result.put("page", page);
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			toJSONResult(result, AppResultCodeEnum.FAILED, e.getMessage());
			logger.error("查询出错",e);
		}
		return result;
	}
	
	/**
	 * 某个ID是否已读
	 * @param warningId
	 * @return
	 */
	private boolean hasRead(long warningId, List<MessageReadStatusDO>  readList) {
		for(MessageReadStatusDO m : readList){
			if(String.valueOf(warningId).equals(m.getRelatedId())){
				return true;
			}
		}
		return false;
	}

	@ResponseBody
	@RequestMapping("info.json")
	public JSONObject view(@RequestParam(value = "formId", required = true) long formId,
						HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			FRiskWarningInfo info = riskWarningServiceClient.findByFormId(formId);
			List<FRiskWarningCreditInfo>  list = new ArrayList<>(); 
			for(FRiskWarningCreditInfo credit : info.getCredits()){
				if(credit.getJsonObject()!=null && "Y".equals(credit.getJsonObject().getString("selectItem"))){
					list.add(credit);
				}
			}
			info.setCredits(list);
			JSONObject riskInfo = (JSONObject) GsonUtil.toJSONObject(info);
			result.put("riskInfo", riskInfo);
			JSONArray credits = riskInfo.getJSONArray("credits");
			JSONObject json = null;
			for(Object o : credits){
				json = (JSONObject) o;
				json = (JSONObject) json.get("jsonObject");
				if(json!=null){
					if("Y".equals(json.get("selectItem"))){
						json.put("checked", BooleanEnum.IS.code());
					};
				}
			}
			//富文本标签去除
			String tmpStr = riskInfo.getString("riskSignalDetail");
			riskInfo.put("riskSignalDetail", getTextMsg(tmpStr));
			tmpStr = riskInfo.getString("riskMeasure");
			riskInfo.put("riskMeasure", getTextMsg(tmpStr));
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			logger.error("查询出错",e);
			result.clear();
			toJSONResult(result, AppResultCodeEnum.FAILED, "查询出错:" + e.getMessage());
		}
		
		return result;
	}
	
	private JSONObject addInit(String projectCode,String customerName,String customerId) {
		JSONObject result = new JSONObject();
		FRiskWarningInfo info = null;
		try {
			
			//TODO 暂时先用客户名称customerName代替查询
			// 添加兼容 工作台过来的项目code用于新增
			if (StringUtil.isBlank(customerName) && StringUtil.isNotBlank(projectCode)) {
				ProjectInfo projectInfo = projectServiceClient.queryByCode(projectCode, false);
				if (projectInfo != null) {
					customerName = projectInfo.getCustomerName();
				}
			}
			if (StringUtil.isNotBlank(customerName)) {
				info = new FRiskWarningInfo();
				ProjectQueryOrder projectQueryOrder = new ProjectQueryOrder();
				//TODO 这儿需要用customerId来查询客户的所有项目信息
				projectQueryOrder.setCustomerId( NumberUtil.parseLong(customerId));
				projectQueryOrder.setCustomerName(customerName);
				projectQueryOrder.setIsApproval(BooleanEnum.IS);
				projectQueryOrder.setPageSize(999);
				QueryBaseBatchResult<ProjectInfo> batchResult = projectServiceClient
					.queryProject(projectQueryOrder);
				
				if (ListUtil.isNotEmpty(batchResult.getPageList())) {
					List<FRiskWarningCreditInfo> credits = new ArrayList<>();
					for (ProjectInfo project : batchResult.getPageList()) {
						FRiskWarningCreditInfo credit = new FRiskWarningCreditInfo();
						credit.setDeptName(project.getDeptName());
						credit.setProjectCode(project.getProjectCode());
						ChargeRepayPlanDetailQueryOrder planDetailQueryOrder = new ChargeRepayPlanDetailQueryOrder();
						planDetailQueryOrder.setPageSize(1);
						planDetailQueryOrder.setProjectCode(project.getProjectCode());
						planDetailQueryOrder.setPlanType(PlanTypeEnum.REPAY_PLAN.code());
						QueryBaseBatchResult<ChargeRepayPlanDetailInfo> queryBaseBatchResult = chargeRepayPlanServiceClient
							.queryPlanDetail(planDetailQueryOrder);
						if (queryBaseBatchResult.getTotalCount() > 0) {
							credit.setHasRepayPlan(BooleanEnum.YES);
						} else {
							credit.setHasRepayPlan(BooleanEnum.NO);
						}

						JSONObject jsonObject = credit.getJsonObject();
						jsonObject.put("customerId", project.getBusiManagerId());
						credit.setJsonObject(jsonObject);
						credit.setLoanAmount(project.getBalance());
						credit.setIssueDate(project.getStartTime());
						credit.setExpireDate(project.getEndTime());
						
						credits.add(credit);
						info.setCustomerId( NumberUtil.parseLong(customerId));
						info.setCustomerName(project.getCustomerName());
					}
					info.setCredits(credits);
				}
				info.setWarningBillType("风险预警处理表");
				//附加处理
				JSONObject warnInfo = (JSONObject) GsonUtil.toJSONObject(info);
				handleCredit(customerId, warnInfo);
				result.put("warnInfo", warnInfo);
			}
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
			
		} catch (Exception e) {			
			logger.error("新增风险信息获取初始化数据失败:",e);
			result.clear();
			toJSONResult(result, AppResultCodeEnum.FAILED, e.getMessage());
		}
		return result;
	}
	private void handleCredit(String customerId,
			JSONObject warnInfo) {
		//富文本标签去除
		String tmpStr = warnInfo.getString("riskSignalDetail");
		warnInfo.put("riskSignalDetail", getTextMsg(tmpStr));
		tmpStr = warnInfo.getString("riskMeasure");
		warnInfo.put("riskMeasure", getTextMsg(tmpStr));
		
		JSONArray jsonArr = warnInfo.getJSONArray("credits");
		JSONObject json ;
		String url = sysParameterServiceClient.getSysParameterValue(ApiSystemParamEnum.FACE_URL.code());
		if(StringUtil.isBlank(url)){
			throw new BornApiException("未配置face访问地址");
		}
		String token = getAccessToken();
		for(Object obj : jsonArr){
			json = (JSONObject) obj;
			JSONObject tmp = json.getJSONObject("jsonObject");
			if(tmp!=null){
				json.put("customerId",  tmp.getString("customerId"));//FIXME 
				String checked = tmp.getString("selectItem");
				if("Y".equals(checked)){
					json.put("checked",  BooleanEnum.YES.code());
				}else{
					json.put("checked",  BooleanEnum.NO.code());
				}
				json.put("isBusiManager",  isBusiManager(json.getString("projectCode")) ? BooleanEnum.IS.code() : BooleanEnum.NO.code());
				if(BooleanEnum.YES.code().equals(json.getString("hasRepayPlan"))){
					String tmpUrl = url + "/projectMg/chargeRepayPlan/view.htm?projectCode="+json.getString("projectCode")+"&isViewPlan=Y"+"&accessToken="+token;
					json.put("url",tmpUrl);
				}
			}
			
		}
	}
	

	@ResponseBody
	@RequestMapping("editInit.json")
	public JSONObject edit(	@RequestParam(value = "formId", required = false, defaultValue = "0") long formId,
			String projectCode,String customerName,String customerId) {
		JSONObject result = new JSONObject();
		try {
			result.put("formId", formId);
			FRiskWarningInfo info = null;
			if (formId > 0) {
				info = riskWarningServiceClient.findByFormId(formId);
				JSONObject warnInfo = (JSONObject) GsonUtil.toJSONObject(info);
				handleCredit(customerId, warnInfo);
				result.put("warnInfo", warnInfo);
				FormInfo form = formServiceClient.findByFormId(formId);
				result.put("form", GsonUtil.toJSONObject(form));
				toJSONResult(result,AppResultCodeEnum.SUCCESS, "");
			}else{
				result = addInit(projectCode, customerName, customerId);
			}
			
		} catch (Exception e) {
			logger.error("编辑风险预警处置信息初始化失败：", e);
			result.clear();
			toJSONResult(result,AppResultCodeEnum.FAILED, e.getMessage());
		} 
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("save.json")
	public Object save(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			String reqJson = parseStreamToString(request);
			LocalFRiskWarningOrder orderOrg = JsonParseUtil.parseObject(reqJson, LocalFRiskWarningOrder.class);
			FRiskWarningOrder order = orderOrg;
			String status = orderOrg.getStatus();
			
			if(StringUtil.isBlank(order.getSpecialSignalDesc())){
				//特殊信号自动设置
				String spicial = order.getSpecialSignal();
				if(spicial!=null){
					QueryBaseBatchResult<RiskWarningSignalInfo> batchResult = riskWarningSignalServiceClient
							.findCompanySpecial();
					String spicials[] = spicial.split(",");
					StringBuilder sb = new StringBuilder();
					for(String s : spicials){
						for(RiskWarningSignalInfo info : batchResult.getPageList()){
							if(StringUtil.equals(String.valueOf(info.getId()), s)){
								sb.append(info.getSignalTypeName());
							}
						}
					}
					order.setSpecialSignalDesc(sb.toString());
				}
			}
			if(StringUtil.isBlank(order.getNomalSignalDesc())){
				//普通信号自动设置
				String normal = order.getNomalSignal();
				if(normal!=null){
					QueryBaseBatchResult<RiskWarningSignalInfo> batchResult = riskWarningSignalServiceClient
							.findCompanyNomal();
					String normals[] = normal.split(",");
					StringBuilder sb = new StringBuilder();
					for(String s : normals){
						for(RiskWarningSignalInfo info : batchResult.getPageList()){
							if(StringUtil.equals(String.valueOf(info.getId()), s)){
								sb.append(info.getSignalTypeName());
							}
						}
					}
					order.setNomalSignalDesc(sb.toString());
				}
			}

			if(StringUtil.isBlank(status) || StringUtil.equals(status, "zc")){
				order.setState("SAVE");
				status = "zc";
			}else if(StringUtil.equals(status, "on")){
				order.setState("SUBMIT");
			}else{
				throw new BornApiException("sttaus 非法");
			}
			String relatedProjectCode = "";
			
			long oldFormId = NumberUtil.parseLong(request.getParameter("oldFormId"), 0);
			if (oldFormId > 0) {
				FRiskWarningInfo fRiskWarningInfo = riskWarningServiceClient
					.findByFormId(oldFormId);
				order = new FRiskWarningOrder();
				BeanCopier.staticCopy(fRiskWarningInfo, order);
				order.setFormCode(FormCodeEnum.RISK_WARNING);
				order.setSrcWaningId(fRiskWarningInfo.getWarningId());
				order.setCheckIndex(0);
				order.setCheckStatus(1);
				//			order.setCheckStatus(0); //没有暂存，直接提交
				setSessionLocalInfo2Order(order);
				order.setWarningBillType("解除风险预警表");
				order.setFormId(0L);
				order.setWarningId(0l);
				order.setLiftingReason(request.getParameter("liftingReason"));
				
				if (ListUtil.isNotEmpty(fRiskWarningInfo.getCredits())) {
					List<FRiskWarningCreditOrder> creditOrders = Lists.newArrayList();
					for (FRiskWarningCreditInfo creditInfo : fRiskWarningInfo.getCredits()) {
						FRiskWarningCreditOrder creditOrder = new FRiskWarningCreditOrder();
						BeanCopier.staticCopy(creditInfo, creditOrder);
						creditOrder.setId(0);
						creditOrder.setWarningId(0);
						creditOrder.setDebitInterestStr(creditInfo.getDebitInterest().toString());
						creditOrder.setLoanAmountStr(creditInfo.getLoanAmount().toString());
						creditOrders.add(creditOrder);
					}
					order.setWarningCredits(creditOrders);
				}
			} else {
				order.setFormCode(FormCodeEnum.RISK_WARNING);
				order.setCheckIndex(0);
				//			order.setCheckStatus(0); //没有暂存，直接提交
				setSessionLocalInfo2Order(order);
				
				if (ListUtil.isNotEmpty(order.getWarningCredits())) {
					JSONObject json = JsonParseUtil.parseObject(reqJson,JSONObject.class);
					JSONArray credits = json.getJSONArray("warningCredits");
					int i = 0;
					for (FRiskWarningCreditOrder creditOrder : order.getWarningCredits()) {
						JSONObject jsonObject = creditOrder.getJsonObject();
						JSONObject credit = credits.getJSONObject(i);
						jsonObject.put("customerId",credit.getString("customerId"));
						jsonObject.put("selectItem", StringUtil.isNotBlank(credit.getString("selectItem")) ? "Y" : "N");
						creditOrder.setHasRepayPlan(BooleanEnum.getByCode(credit.getString("hasRepayPlan")));
						if (creditOrder.getHasRepayPlan() == null) {
							creditOrder.setHasRepayPlan(BooleanEnum.NO);
						}
						if (StringUtil.equals("Y", jsonObject.getString("selectItem"))) {
							if (relatedProjectCode.length() == 0) {
								relatedProjectCode = creditOrder.getProjectCode();
							} else {
								relatedProjectCode += "," + creditOrder.getProjectCode();
							}
						}
						i++;
					}
				}
			}
			order.setRelatedProjectCode(relatedProjectCode);
			FormBaseResult formResult = riskWarningServiceClient.save(order);
			if(StringUtil.equals(status, "on")){
				result = jckFormService.submit(formResult.getFormInfo().getFormId(), request);
			}else{
				result = toJSONResult(formResult);
			}
		} catch (Exception e) {
			logger.error("保存风险预警处置信息失败：", e);
			result.clear();
			result = toJSONResult(AppResultCodeEnum.FAILED, e.getMessage());
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("querySignal.json")
	public Object querySignal(HttpServletRequest request, Model model,
								RiskWarningSignalQueryOrder queryOrder) {
		
		JSONObject result = new JSONObject();
		try {
			QueryBaseBatchResult<RiskWarningSignalInfo> batchResult1 = riskWarningSignalServiceClient
				.findCompanySpecial();
			QueryBaseBatchResult<RiskWarningSignalInfo> batchResult2 = riskWarningSignalServiceClient
				.findCompanyNomal();
			result.put("spe", converToArray(batchResult1.getPageList()));
			result.put("nor", converToArray(batchResult2.getPageList()));
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			logger.error("查询出错",e);
			toJSONResult(result, AppResultCodeEnum.FAILED, e.getMessage());
		}
		
		return result;
	}
	
	private JSONArray converToArray(List<RiskWarningSignalInfo> list) {
		JSONArray array = new JSONArray();
		if (ListUtil.isNotEmpty(list)) {
			for (RiskWarningSignalInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("code", "" + info.getId());
				json.put("msg", info.getSignalTypeName());
				array.add(json);
			}
		}
		return array;
	}
	
	public static class LocalFRiskWarningOrder extends FRiskWarningOrder{
		/**
		 */
		private static final long serialVersionUID = 1L;
		private  String status;

		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this,
					ToStringStyle.SHORT_PREFIX_STYLE);
		}
	}
	
}
