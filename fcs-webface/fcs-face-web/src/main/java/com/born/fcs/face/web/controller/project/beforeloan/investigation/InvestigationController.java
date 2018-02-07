package com.born.fcs.face.web.controller.project.beforeloan.investigation;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.am.ws.info.pledgeasset.PledgeAssetInfo;
import com.born.fcs.am.ws.info.pledgeasset.PledgeTypeCommonInfo;
import com.born.fcs.crm.ws.service.info.CompanyQualificationInfo;
import com.born.fcs.crm.ws.service.info.CustomerDetailInfo;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.ExcelData;
import com.born.fcs.face.web.util.ExcelUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.util.DataFinancial;
import com.born.fcs.pm.util.DataFinancialHelper;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.RateUtil;
import com.born.fcs.pm.ws.enums.AdjustTypeEnum;
import com.born.fcs.pm.ws.enums.AuditOpinionEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChargePhaseEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.ChargeWayEnum;
import com.born.fcs.pm.ws.enums.CheckPointEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.CreditLevelEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.EnterpriseNatureEnum;
import com.born.fcs.pm.ws.enums.EnterpriseRelationEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.GradeLevelEnum;
import com.born.fcs.pm.ws.enums.GuarantorTypeEnum;
import com.born.fcs.pm.ws.enums.HolderTypeEnum;
import com.born.fcs.pm.ws.enums.PledgePropertyEnum;
import com.born.fcs.pm.ws.enums.PledgeTypeEnum;
import com.born.fcs.pm.ws.enums.SiteStatusEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.UnderwritingChargeWaytEnum;
import com.born.fcs.pm.ws.enums.UpAndDownEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbPhaseEnum;
import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectVOInfo;
import com.born.fcs.pm.ws.info.creditrefrerenceapply.CreditRefrerenceApplyReusltInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemeInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemePledgeAssetInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCsRationalityReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationFinancialDataExplainInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationFinancialReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationFinancialReviewKpiInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationLitigationInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMabilityReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewBankInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewCertificateInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMajorEventInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationOpabilityReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationProjectStatusInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationRiskInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationUnderwritingInfo;
import com.born.fcs.pm.ws.info.finvestigation.FinancialReviewKpiInfo;
import com.born.fcs.pm.ws.info.finvestigation.InvestigationBaseInfo;
import com.born.fcs.pm.ws.info.finvestigation.InvestigationInfo;
import com.born.fcs.pm.ws.info.finvestigation.checking.InvestigationCheckingInfo;
import com.born.fcs.pm.ws.info.riskreview.RiskReviewReportInfo;
import com.born.fcs.pm.ws.info.setup.FProjectCustomerBaseInfo;
import com.born.fcs.pm.ws.info.setup.FProjectGuaranteeEntrustedInfo;
import com.born.fcs.pm.ws.info.setup.FProjectInfo;
import com.born.fcs.pm.ws.info.setup.FProjectLgLitigationInfo;
import com.born.fcs.pm.ws.order.base.ProjectFormOrderBase;
import com.born.fcs.pm.ws.order.common.CommonAttachmentQueryOrder;
import com.born.fcs.pm.ws.order.creditrefrerenceapply.CreditRefrerenceApplyQueryOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationCreditSchemeOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationCsRationalityReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationFinancialDataExplainOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationFinancialReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationLitigationOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMabilityReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMainlyReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMajorEventOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationOpabilityReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationProjectStatusOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationRiskOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationUnderwritingOrder;
import com.born.fcs.pm.ws.order.finvestigation.InvestigationQueryOrder;
import com.born.fcs.pm.ws.order.finvestigation.UpdateInvestigationCustomerOrder;
import com.born.fcs.pm.ws.order.finvestigation.base.FInvestigationBaseOrder;
import com.born.fcs.pm.ws.order.finvestigation.base.InvestigationBaseOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 尽职调查报告
 * 
 * @author lirz
 * 
 * 2016-3-15 上午10:52:06
 */
@Controller
@RequestMapping("projectMg/investigation")
public class InvestigationController extends WorkflowBaseController {
	
	private static final String VM_PATH = "/projectMg/beforeLoanMg/investigation/";
	private static final String EXPORT_VM_PATH = "/layout/projectMg/beforeLoanMg/investigation/";
	
	private boolean isCompare() {
		return true;
	}
	
	protected String getNextUrl(Integer toIndex) {
		if (null != toIndex /*&& toIndex <= NEXT.length*/) {
			if (toIndex == -1) {
				return "#";
			} else if (toIndex == -2) {
				return "##";
			}
			
			return "/projectMg/investigation/edit.htm";
			//return NEXT[toIndex];
		}
		
		return "";
	}
	
	protected JSONObject toUrl(String tipPrefix, String url, ProjectFormOrderBase order) {
		logger.info(tipPrefix + "，没有数据改变，不需要保存，直接跳转：" + url);
		JSONObject json = new JSONObject();
		json.put("success", true);
		json.put("message", "保存成功");
		json.put("url", url);
		if (isSubmit(url)) {
			FormInfo formInfo = formServiceClient.findByFormId(order.getFormId());
			json.put("form", formInfo);
		}
		json.put("toIndex", null == order.getToIndex() ? 0 : order.getToIndex());
		return json;
	}
	
	/**
	 * 
	 * 是否提交过
	 * 
	 * @param url
	 * @return
	 */
	private boolean isSubmit(String url) {
		return "##".equals(url);
	}
	
	protected void addExceptionToken(HttpSession session) {
		String token = UUID.randomUUID().toString();
		session.setAttribute("token", token);
	}
	
	/**
	 * 处理保存结果
	 * 
	 * @param result 保存结果
	 * @param tipPrefix 提示信息前缀
	 * @return
	 */
	protected JSONObject getSaveResult(FormBaseResult result, String tipPrefix) {
		JSONObject json = new JSONObject();
		if (null != result && result.isSuccess()) {
			json.put("success", true);
			json.put("message", "成功");
			json.put("form", result.getFormInfo());
		} else {
			json.put("success", false);
			json.put("message", tipPrefix + "失败" + result.getMessage());
		}
		return json;
	}
	
	/**
	 * 处理保存结果
	 * 
	 * @param result 保存结果
	 * @param tipPrefix 提示信息前缀
	 * @return
	 */
	protected JSONObject toJSONResult(FcsBaseResult result, String tipPrefix, HttpSession session) {
		return this.toJSONResult(result, tipPrefix, 0, "", session);
	}
	
	/**
	 * 处理保存结果 成功后跳转
	 * 
	 * @param result 保存结果
	 * @param tipPrefix 提示信息前缀
	 * @return
	 */
	protected JSONObject toJSONResult(FcsBaseResult result, String tipPrefix, int toIndex,
										String nextUrl, HttpSession session) {
		JSONObject json = new JSONObject();
		if (null != result && result.isSuccess()) {
			json.put("success", true);
			//			json.put("message", tipPrefix + "成功");
			json.put("message", "保存成功");
			if (result instanceof FormBaseResult) {
				FormInfo form = ((FormBaseResult) result).getFormInfo();
				if (form != null) {
					json.put("form", form.toJson());
				}
					
			}
			json.put("toIndex", toIndex);
			json.put("url", nextUrl);
			session.removeAttribute("token"); //结果成功后清除token
			if (isSubmit(nextUrl)) {
				String token = UUID.randomUUID().toString();
				session.setAttribute("token", token);
				json.put("token", token);
			}
		} else {
			json.put("success", false);
			json.put("message", tipPrefix + "失败" + result.getMessage());
		}
		return json;
	}
	
	/**
	 * 处理保存异常信息
	 * 
	 * @param tipPrefix 提示信息前缀
	 * @param exMsg 异常消息
	 * @return
	 */
	protected JSONObject getExceptionResult(String tipPrefix, String exMsg) {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", tipPrefix + "出错[" + exMsg + "]");
		return json;
	}
	
	//	protected String initProject(HttpServletRequest request, HttpSession session, Model model,
	//									long formId, InvestigationBaseInfo info) {
	//		String projectCode = "";
	//		if (null == info) {
	//			FInvestigationInfo baseInfo = investigationServiceClient
	//				.findInvestigationByFormId(formId);
	//			model.addAttribute("baseInfo", baseInfo);
	//			if (null != baseInfo) {
	//				projectCode = baseInfo.getProjectCode();
	//				ProjectInfo project = projectServiceClient.queryByCode(baseInfo.getProjectCode(),
	//					false);
	//				model.addAttribute("project", project);
	//			}
	//		} else {
	//			projectCode = info.getProjectCode();
	//		}
	//		String token = UUID.randomUUID().toString();
	//		session.setAttribute("token", token);
	//		
	//		String submited = "submited";
	//		if (StringUtil.isNotBlank(request.getParameter(submited))) {
	//			FormInfo form = formServiceClient.findByFormId(formId);
	//			if (null != form) {
	//				model.addAttribute(submited, form.getCheckStatus());
	//			}
	//		}
	//		
	////		model.addAttribute("customerCoverRate", getRiskCoverRate(formId));
	//		model.addAttribute("customerCoverRate", info.getRiskRate());
	//		
	//		return projectCode;
	//	}
	
	/**
	 * 尽职调查报告列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		
		try {
			InvestigationQueryOrder queryOrder = new InvestigationQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<InvestigationInfo> batchResult = investigationServiceClient
				.queryInvestigation(queryOrder);
			
			if (batchResult != null && ListUtil.isNotEmpty(batchResult.getPageList())) {
				Map<Long, String> projectEndFileMap = Maps.newHashMap();
				
				List<CommonAttachmentTypeEnum> typeList = Lists.newArrayList();
				typeList.add(CommonAttachmentTypeEnum.PROJECT_END_FILE);
				
				for (InvestigationInfo info : batchResult.getPageList()) {
					//查询终止附件
					if (info.getFormStatus() == FormStatusEnum.DENY) {
						CommonAttachmentQueryOrder attachOrder = new CommonAttachmentQueryOrder();
						attachOrder.setBizNo(info.getFormId() + "");
						attachOrder.setModuleTypeList(typeList);
						QueryBaseBatchResult<CommonAttachmentInfo> attachs = commonAttachmentServiceClient
							.queryCommonAttachment(attachOrder);
						if (attachs != null && ListUtil.isNotEmpty(attachs.getPageList())) {
							String attStr = null;
							for (CommonAttachmentInfo attach : attachs.getPageList()) {
								if (StringUtil.isBlank(attStr)) {
									attStr = attach.getFileName() + ","
												+ attach.getFilePhysicalPath() + ","
												+ attach.getRequestPath();
								} else {
									attStr += ";" + attach.getFileName() + ","
												+ attach.getFilePhysicalPath() + ","
												+ attach.getRequestPath();
								}
							}
							projectEndFileMap.put(info.getFormId(), attStr);
						}
					}
				}
				model.addAttribute("projectEndFileMap", projectEndFileMap);
			}
			HttpSession session = request.getSession();
			if (StringUtils.isBlank(request.getParameter("version"))) {
				session.removeAttribute("version");
			} else {
				String version = request.getParameter("version");
				boolean isOld =!(StringUtils.isBlank(version) || (!StringUtils.isBlank(version) && version.equals("false")) ) ? Boolean.TRUE : Boolean.FALSE;
				session.setAttribute("version", isOld);
			}
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错");
		}
		
		return VM_PATH + "list.vm";
	}
	
	private String hasRiskReviewReport(long formId) {
		RiskReviewReportInfo riskReviewInfo = riskReviewReportServiceClient
			.queryRiskReviewByFormId(formId);
		return (null != riskReviewInfo) ? BooleanEnum.YES.code() : BooleanEnum.NO.code();
	}
	
	/**
	 * 
	 * 提交表单公共数据
	 * 
	 * @param request
	 * @param session
	 * @param model
	 * @param formId
	 * @return
	 */
	protected FormInfo initSubmitCommon(HttpServletRequest request, HttpSession session,
										Model model, long formId) {
		FormInfo form = formServiceClient.findByFormId(formId);
		model.addAttribute("form", form);
		
		String token = UUID.randomUUID().toString();
		session.setAttribute("token", token);
		
		String submited = "submited";
		if (StringUtil.isNotBlank(request.getParameter(submited))) {
			model.addAttribute(submited, form.getCheckStatus());
		}
		Map<String, Object> customizeFieldMap = form.getCustomizeFieldMap();
		Object zxCustomerjson = customizeFieldMap.get("zxCustomerjson");
		if (null != zxCustomerjson) {
			model.addAttribute("zxCustomerjson", JSONArray.parse(zxCustomerjson.toString()));
		}
		model.addAttribute("customerCoverRate", getRiskCoverRate(formId));
		
		return form;
	}
	
	//添加尽职调查申明
	@RequestMapping("addDeclare.htm")
	public String addDeclare(HttpServletRequest request, HttpSession session, Model model,
								@RequestParam(value = "projectCode", required = false,
										defaultValue = "") String projectCode,
								@RequestParam(value = "formId", required = false,
										defaultValue = "0") long formId) {
		if (formId > 0) {
			return editDeclare(request, session, model, "", formId, "NO");
		}
		
		FInvestigationInfo baseInfo = new FInvestigationInfo();
		if (StringUtil.isNotBlank(projectCode)) {
			//如果有草稿状态的
			/*InvestigationQueryOrder queryOrder = new InvestigationQueryOrder();
			queryOrder.setProjectCode(projectCode);
			queryOrder.setFormStatus(FormStatusEnum.DRAFT.code());
			//			queryOrder.setReview(BooleanEnum.IS.code());
			queryOrder.setPageSize(1L);
			queryOrder.setPageNumber(1L);
			QueryBaseBatchResult<InvestigationInfo> batchResult = investigationServiceClient
				.queryInvestigation(queryOrder);
			
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				SimpleFormProjectVOInfo info = batchResult.getPageList().get(0);
				long oldFormId = info.getFormId();
				//草稿
				return editDeclare(request, session, model, "", oldFormId);
			}*/
			long oldFormId = queryDraft(projectCode);
			if (oldFormId > 0) {
				return editDeclare(request, session, model, "", oldFormId, "NO");
			}
			
			ProjectInfo project = projectServiceClient.queryByCode(projectCode, false);
			if (null != project) {
				baseInfo.setProjectCode(projectCode);
				baseInfo.setProjectName(project.getProjectName());
				baseInfo.setCustomerId(project.getCustomerId());
				baseInfo.setCustomerName(project.getCustomerName());
				baseInfo.setBusiType(project.getBusiType());
				baseInfo.setBusiTypeName(project.getBusiTypeName());
				baseInfo.setAmount(project.getAmount());
				model.addAttribute("busiManagerName", project.getBusiManagerName());
				model.addAttribute("busiManagerbName", project.getBusiManagerbName());
				model.addAttribute("busiManagerbId", project.getBusiManagerbId());
				model.addAttribute("projectInfo", project);
			}
			ProjectRelatedUserInfo busiManager = projectRelatedUserServiceClient
				.getBusiManagerb(projectCode);
			if (null != busiManager) {
				baseInfo.setInvestigatePersionId(busiManager.getUserId() + "");
				baseInfo.setInvestigatePersion(busiManager.getUserName());
			}
			
			// 查询客户信息
			CustomerDetailInfo customerInfo = customerServiceClient.queryByUserId(baseInfo
				.getCustomerId());
			if (null != customerInfo) {
				baseInfo.setCustomerName(customerInfo.getCustomerName());
			}
			
			baseInfo.setDeclares(request.getParameter("declares"));
			
			if (StringUtil.isNotBlank(baseInfo.getDeclares())) {
				//已经阅读5秒并作了选择
				//				model.addAttribute("info", baseInfo);
				model.addAttribute("show", BooleanEnum.NO.code());
				//				return VM_PATH + "editDeclare.vm";
			}
		}
		
		model.addAttribute("info", baseInfo);
		String version = request.getParameter("version");
		boolean isOld =!(StringUtils.isBlank(version) || (!StringUtils.isBlank(version) && "false".equals(version)) ) ? Boolean.TRUE : Boolean.FALSE;
		
		if (null == session.getAttribute("version") && !StringUtils.isBlank(request.getParameter("version"))) {
			session.setAttribute("version", isOld);
		}
		return VM_PATH + "addDeclare.vm";
	}
	
	//编辑尽职调查申明
	@RequestMapping("editDeclare.htm")
	public String editDeclare(HttpServletRequest request, HttpSession session, Model model,
								@RequestParam(value = "projectCode", required = false,
										defaultValue = "") String projectCode,
								@RequestParam(value = "formId", required = false,
										defaultValue = "0") long formId,
								@RequestParam(value = "checkPoint", required = false,
										defaultValue = "") String checkPoint) {
		
		Boolean review = StringUtil.equals(BooleanEnum.YES.code(), request.getParameter("review"));
		if (review) {
			InvestigationQueryOrder queryOrder = new InvestigationQueryOrder();
			queryOrder.setProjectCode(projectCode);
			queryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
			queryOrder.setPageSize(1L);
			queryOrder.setPageNumber(1L);
			QueryBaseBatchResult<InvestigationInfo> batchResult = investigationServiceClient
				.queryInvestigation(queryOrder);
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				//审核通过后的尽调表单formId
				SimpleFormProjectVOInfo info = batchResult.getPageList().get(0);
				formId = info.getFormId();
				InvestigationBaseOrder reviewOrder = new InvestigationBaseOrder();
				reviewOrder.setFormId(formId);
				reviewOrder.setProjectCode(projectCode);
				reviewOrder.setReview(BooleanEnum.YES.code());
				//复议复制 (同步复制，时间会稍微久一点)
				FcsBaseResult result = investigationServiceClient.updateInvestigation(reviewOrder);
				if (null != result) {
					formId = result.getKeyId();
				} else {
					formId = 0L;
				}
			} else {
				queryOrder.setFormStatus(FormStatusEnum.DRAFT.code());
				setSessionLocalInfo2Order(queryOrder);
				batchResult = investigationServiceClient.queryInvestigation(queryOrder);
				if (ListUtil.isNotEmpty(batchResult.getPageList())) {
					SimpleFormProjectVOInfo info = batchResult.getPageList().get(0);
					formId = info.getFormId();
				}
			}
		}
		
		initSubmitCommon(request, session, model, formId);
		
		UpdateInvestigationCustomerOrder order = new UpdateInvestigationCustomerOrder();
		order.setFormId(formId);
		order.setToIndex(99); //99特定表明是声明的客户信息修改
		updateCustomerInfo(order);
		
		FInvestigationInfo info = investigationServiceClient.findInvestigationByFormId(formId);
		//		if (StringUtil.isNotBlank(checkPoint)) {
		//			long checkFormId = findFormId(checkPoint, formId);
		//			if (checkFormId > 0) {
		//				info = investigationServiceClient.findInvestigationByFormId(formId);
		//			}
		//		}
		//		if (null == info) {
		//			info = investigationServiceClient.findInvestigationByFormId(formId);
		//		}
		
		model.addAttribute("info", info);
		
		ProjectInfo project = projectServiceClient.queryByCode(info.getProjectCode(), false);
		if (null != project) {
			model.addAttribute("busiManagerName", project.getBusiManagerName());
			model.addAttribute("projectInfo", project);
		}
		
		model.addAttribute("checkPoint", checkPoint);
		String auditIndex = request.getParameter("auditIndex");
		model.addAttribute("auditIndex", null == auditIndex ? -1 : auditIndex);
		
		
		return VM_PATH + "editDeclare.vm";
	}
	
	private long queryDraft(String projectCode) {
		if (StringUtil.isBlank(projectCode)) {
			return 0L;
		}
		InvestigationQueryOrder queryOrder = new InvestigationQueryOrder();
		queryOrder.setProjectCode(projectCode);
		queryOrder.setFormStatus(FormStatusEnum.DRAFT.code());
		//		queryOrder.setReview(BooleanEnum.IS.code());
		queryOrder.setPageSize(1L);
		queryOrder.setPageNumber(1L);
		setSessionLocalInfo2Order(queryOrder);
		QueryBaseBatchResult<InvestigationInfo> batchResult = investigationServiceClient
			.queryInvestigation(queryOrder);
		
		if (ListUtil.isNotEmpty(batchResult.getPageList())) {
			SimpleFormProjectVOInfo info = batchResult.getPageList().get(0);
			return info.getFormId();
		}
		return 0L;
	}
	
	/**
	 * 保存源数据
	 * @param order
	 */
	private void setOldFormInfo(FInvestigationBaseOrder order) {
		if (StringUtil.isNotBlank(order.getCheckPoint())) {
			FormInfo form = formServiceClient.findByFormId(order.getFormId());
			if (null != form) {
				order.setFormCustomizeFieldMap(form.getCustomizeFieldMap());
			}
		}
	}
	
	//保存尽职调查申明
	@ResponseBody
	@RequestMapping("saveDeclare.json")
	public Object saveDeclare(HttpSession session, String token, HttpServletRequest request,
								FInvestigationOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		if (isRepeatSubmit(json, session, token)) {
			return json;
		}
		
		String tipPrefix = "保存尽职调查申明";
		try {
			if (null == order.getFormId() || order.getFormId() <= 0) {
				long formId = queryDraft(order.getProjectCode());
				if (formId > 0) {
					order.setFormId(formId);
				}
			}
			//默认担保委贷
			order.setFormCode(FormCodeEnum.INVESTIGATION_BASE);
			//			String nextUrl = "/projectMg/investigation/saveInvestigationCreditScheme.htm";
			String nextUrl = "/projectMg/investigation/edit.htm";
			//根据不同的项目业务类型，填写不同的尽职调查报告
			if (isLitigation(order.getBusiType())) { //诉讼保函
				//				nextUrl = "/projectMg/investigation/saveFInvestigationLitigation.htm";
				order.setFormCode(FormCodeEnum.INVESTIGATION_LITIGATION);
			} else if (isUnderwriting(order.getBusiType())) { //承销
				//				nextUrl = "/projectMg/investigation/saveFInvestigationUnderwriting.htm";
				order.setFormCode(FormCodeEnum.INVESTIGATION_UNDERWRITING);
			}else if(ProjectUtil.isInnovativeProduct(order.getBusiType())){
				order.setFormCode(FormCodeEnum.INVESTIGATION_INNOVATIVE_PRODUCT);
			}
			if (order.getToIndex() != null) {
				if (order.getToIndex() == -1) { //暂存处理
					//刷新当前页面
					nextUrl = "/projectMg/investigation/editDeclare.htm";
				} else if (order.getToIndex() == -2) {
					nextUrl = "##";
				}
			}
			//			if (order.noNeedSave()) {
			//				return toUrl(tipPrefix, nextUrl, order);
			//			}
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			setOldFormInfo(order);
			FormBaseResult result = investigationServiceClient.saveInvestigation(order);
			json = toJSONResult(result, tipPrefix, session);
			
			json.put("url", nextUrl);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	//保存尽职调查申明
	@ResponseBody
	@RequestMapping("saveInvestigationSubmit.htm")
	public Object saveInvestigationSubmit(HttpSession session, String token,
											HttpServletRequest request, FInvestigationOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		if (isRepeatSubmit(json, session, token)) {
			return json;
		}
		
		String tipPrefix = "保存尽职调查声明";
		try {
			//默认担保委贷
			order.setFormCode(FormCodeEnum.INVESTIGATION_BASE);
			//			String nextUrl = "/projectMg/investigation/saveInvestigationCreditScheme.htm";
			String nextUrl = "/projectMg/investigation/edit.htm";
			//根据不同的项目业务类型，填写不同的尽职调查报告
			if (isLitigation(order.getBusiType())) { //诉讼保函
				//				nextUrl = "/projectMg/investigation/saveFInvestigationLitigation.htm";
				order.setFormCode(FormCodeEnum.INVESTIGATION_LITIGATION);
			} else if (isUnderwriting(order.getBusiType())) { //承销
				//				nextUrl = "/projectMg/investigation/saveFInvestigationUnderwriting.htm";
				order.setFormCode(FormCodeEnum.INVESTIGATION_UNDERWRITING);
			}
			if (order.getToIndex() != null) {
				if (order.getToIndex() == -1) { //暂存处理
					//刷新当前页面
					nextUrl = "/projectMg/investigation/saveInvestigation.htm";
				} else if (order.getToIndex() == -2) {
					nextUrl = "##";
				}
			}
			if (order.noNeedSave()) {
				return toUrl(tipPrefix, nextUrl, order);
			}
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			setSessionLocalInfo2Order(order);
			FormBaseResult result = investigationServiceClient.saveInvestigation(order);
			json = toJSONResult(result, tipPrefix, session);
			
			json.put("url", nextUrl);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	// 保存授信方案
	@ResponseBody
	@RequestMapping("saveInvestigationCreditSchemeSubmit.htm")
	public Object saveInvestigationCreditSchemeSubmit(HttpSession session, String token,
														HttpServletRequest request,
														FInvestigationCreditSchemeOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		if (isRepeatSubmit(json, session, token)) {
			return json;
		}
		
		String tipPrefix = "保存授信方案";
		try {
			String url = getNextUrl(order.getToIndex());
			//			if (order.noNeedSave()) {
			//				return toUrl(tipPrefix, url, order);
			//			}
			order.setFormCode(FormCodeEnum.INVESTIGATION_BASE);
			order.setCheckIndex(1);
			//			order.setCheckStatus(0);
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FormBaseResult result = investigationServiceClient.saveInvestigationCreditScheme(order);
			json = toJSONResult(result, tipPrefix, order.getToIndex(), url, session);
			//1 写回风险覆盖率
			if (result.isSuccess()) {
				double r = getRiskCoverRate(result.getFormInfo().getFormId());
				String rate = RateUtil.formatRate(r);
				json.put("riskCoverRate", rate);
				long formId = result.getFormInfo().getFormId();
				//保存附件2016-07-21
				addAttachfile(formId + "", request, order.getProjectCode(), "尽调-反担保附件",
					CommonAttachmentTypeEnum.COUNTER_GUARANTEE);
				
				//保存附件2016-08-30 wuzj
				addAttachfile(formId + "_index0", request, order.getProjectCode(), "尽调-授信方案表单附件",
					CommonAttachmentTypeEnum.FORM_ATTACH);
				
				addAttachment(request, formId + "", "",
					CommonAttachmentTypeEnum.INVESTIGATION_REPORT);
			}
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	// 保存客户主体评价
	@ResponseBody
	@RequestMapping("saveInvestigationMainlyReviewSubmit.htm")
	public Object saveInvestigationMainlyReviewSubmit(HttpSession session, String token,
														HttpServletRequest request,
														FInvestigationMainlyReviewOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		if (isRepeatSubmit(json, session, token)) {
			return json;
		}
		
		String tipPrefix = "保存客户主体评价";
		try {
			String url = getNextUrl(order.getToIndex());
			//			if (order.noNeedSave()) {
			//				return toUrl(tipPrefix, url, order);
			//			}
			order.setFormCode(FormCodeEnum.INVESTIGATION_BASE);
			order.setCheckIndex(2);
			//			order.setCheckStatus(0);
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FormBaseResult result = investigationServiceClient.saveInvestigationMainlyReview(order);
			if (result.isSuccess()) {
				addAttachfile(result.getFormInfo().getFormId() + "_index1", request,
					order.getProjectCode(), "尽调-客户主体评价附件", CommonAttachmentTypeEnum.FORM_ATTACH);
				addAttachfile(result.getFormInfo().getFormId() + "", request,
						order.getProjectCode(), CommonAttachmentTypeEnum.CREDIT_REPORT.getMessage(), CommonAttachmentTypeEnum.CREDIT_REPORT);
				//风险消息保存
				verifyMsgSave(request, "JZ" + result.getFormInfo().getFormId() + "index1");
			}
			json = toJSONResult(result, tipPrefix, order.getToIndex(), url, session);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	//保存客户管理能力评价
	@ResponseBody
	@RequestMapping("saveFInvestigationMabilityReviewSubmit.htm")
	public Object saveFInvestigationMabilityReviewSubmit(HttpSession session, String token,
															HttpServletRequest request,
															FInvestigationMabilityReviewOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		if (isRepeatSubmit(json, session, token)) {
			return json;
		}
		
		String tipPrefix = "保存客户管理能力评价";
		try {
			String url = getNextUrl(order.getToIndex());
			if (order.noNeedSave()) {
				return toUrl(tipPrefix, url, order);
			}
			order.setFormCode(FormCodeEnum.INVESTIGATION_BASE);
			order.setCheckIndex(3);
			//			order.setCheckStatus(0);
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FormBaseResult result = investigationServiceClient
				.saveFInvestigationMabilityReview(order);
			if (result.isSuccess()) {
				addAttachfile(result.getFormInfo().getFormId() + "_index2", request,
					order.getProjectCode(), "尽调-客户管理能力评价附件", CommonAttachmentTypeEnum.FORM_ATTACH);
			}
			json = toJSONResult(result, tipPrefix, order.getToIndex(), url, session);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	//保存客户经营能力评价
	@ResponseBody
	@RequestMapping("saveFInvestigationOpabilityReviewSubmit.htm")
	public Object saveFInvestigationOpabilityReviewSubmit(HttpSession session, String token,
															HttpServletRequest request,
															FInvestigationOpabilityReviewOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		if (isRepeatSubmit(json, session, token)) {
			return json;
		}
		
		String tipPrefix = "保存客户经营能力评价";
		try {
			String url = getNextUrl(order.getToIndex());
			if (order.noNeedSave()) {
				return toUrl(tipPrefix, url, order);
			}
			order.setFormCode(FormCodeEnum.INVESTIGATION_BASE);
			order.setCheckIndex(4);
			//			order.setCheckStatus(0);
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FormBaseResult result = investigationServiceClient
				.saveFInvestigationOpabilityReview(order);
			if (result.isSuccess()) {
				addAttachfile(result.getFormInfo().getFormId() + "_index3", request,
					order.getProjectCode(), "尽调-客户经营能力评价附件", CommonAttachmentTypeEnum.FORM_ATTACH);
			}
			json = toJSONResult(result, tipPrefix, order.getToIndex(), url, session);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	/**
	 * 获取财务报表模板
	 * 
	 * @param projectCode
	 * @param request
	 * @param model
	 */
	private void getFinancialTemplate(HttpServletRequest request, String projectCode, Model model) {
		String type = EXCEL_FINANCIAL_TABLES;
	/*	FProjectCustomerBaseInfo customerBaseInfo = projectSetupServiceClient
			.queryCustomerBaseInfoByProjectCode(projectCode);
		if (null != customerBaseInfo) {
			if (StringUtil.isNotBlank(customerBaseInfo.getIndustryCode())
				&& customerBaseInfo.getIndustryCode().startsWith("J")) {
				type = EXCEL_FINANCIAL_TABLES_J;
			}
		}
		*/
		JSONObject json = findFinancialDatas(request, type);
		if (null != json) {
			for (Object obj : json.keySet()) {
				model.addAttribute(obj.toString(), json.get(obj));
			}
		}
		
		model.addAttribute("templateType", type);
	}
	
	private void addKpiClass(FInvestigationFinancialReviewInfo info) {
		if (null != info) {
			addKpiClass(info.getFinancialTables());
			addKpiClass(info.getProfitTables());
			addKpiClass(info.getFlowTables());
		}
	}
	
	private void addKpiClass(List<FinancialReviewKpiInfo> kpiInfoes) {
		if (ListUtil.isNotEmpty(kpiInfoes)) {
			for (FinancialReviewKpiInfo info : kpiInfoes) {
				String kpiClass = DataFinancialHelper.getKpiClass(info.getKpiName());
				info.setKpiClass(kpiClass);
			}
		}
	}
	
	//保存客户财务评价
	@ResponseBody
	@RequestMapping("saveFInvestigationFinancialReviewSubmit.htm")
	public Object saveFInvestigationFinancialReviewSubmit(HttpSession session, String token,
															HttpServletRequest request,
															FInvestigationFinancialReviewOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		if (isRepeatSubmit(json, session, token)) {
			return json;
		}
		
		String tipPrefix = "保存客户财务评价";
		try {
			String url = getNextUrl(order.getToIndex());
			if (null == session.getAttribute("version") && order.getToIndex() != null && order.getToIndex() == 5) {
				url = "##";
			}
			if (order.noNeedSave() && BooleanEnum.IS.code().equals(order.getIsActive())) {
				return toUrl(tipPrefix, url, order);
			}
			order.setFormCode(FormCodeEnum.INVESTIGATION_BASE);
			order.setCheckIndex(5);
			order.setIsActive(BooleanEnum.IS.code());
			String sIndex = request.getParameter("sIndex");
			if (order.getSubIndex() != 1 && StringUtil.isNumeric(sIndex)) {
				order.setSubIndex(Integer.valueOf(sIndex));
			}
			//			order.setCheckStatus(0);
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FormBaseResult result = investigationServiceClient
				.saveFInvestigationFinancialReview(order);
			if (result.isSuccess()) {
				String suffix = "";
				if (order.getSubIndex() == 1) {
					suffix = "_" + order.getSubIndex();
				}
				
				addAttachfile(result.getFormInfo().getFormId() + "_index4" + suffix, request,
					order.getProjectCode(), "尽调-客户财务评价附件", CommonAttachmentTypeEnum.FORM_ATTACH);
				addAttachfile(result.getFormInfo().getFormId() + suffix, request,
					order.getProjectCode(),
					CommonAttachmentTypeEnum.INVESTIGATION_FINANCIAL.message(),
					CommonAttachmentTypeEnum.INVESTIGATION_FINANCIAL);
			}
			json = toJSONResult(result, tipPrefix, order.getToIndex(), url, session);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	//查询财务数据解释说明
	@ResponseBody
	@RequestMapping("findFInvestigationFinancialReviewData.htm")
	public Object findFInvestigationFinancialReviewData(HttpServletRequest request, long id) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		
		String tipPrefix = "查询财务数据解释说明";
		try {
			FInvestigationFinancialDataExplainInfo info = investigationServiceClient
				.findFInvestigationFinancialDataExplainById(id);
			json.put("success", true);
			json.put("message", "查询成功");
			if (info != null) {
				JSONObject data = new JSONObject();
				data.put("id", info.getId());
				data.put("explaination", info.getExplaination());
				if (ListUtil.isNotEmpty(info.getKpiExplains())) {
					JSONArray kpiExplains = new JSONArray();
					for (FInvestigationFinancialReviewKpiInfo rk : info.getKpiExplains()) {
						if (StringUtil.isBlank(rk.getKpiName()) && StringUtil.isBlank(rk.getKpiValue()) && rk.getKpiRatio() == 0) {
							continue;
						}
						JSONObject rkJson = new JSONObject();
						rkJson.put("kpiName", rk.getKpiName());
						rkJson.put("kpiValue", MoneyUtil.getMoney(rk.getKpiValue())
							.toStandardString());
						rkJson.put("kpiRatio", rk.getKpiRatio());
						kpiExplains.add(rkJson);
					}
					data.put("kpiExplains", kpiExplains);
				} else {
					data.put("kpiExplains", new JSONArray());
				}
				json.put("data", data);
			}
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	//保存财务数据说明
	@ResponseBody
	@RequestMapping("saveFInvestigationFinancialReviewDataSubmit.htm")
	public Object saveFInvestigationFinancialReviewDataSubmit(	HttpServletRequest request,
																FInvestigationFinancialDataExplainOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		
		String tipPrefix = "保存财务数据说明";
		try {
			FcsBaseResult result = investigationServiceClient
				.saveFInvestigationFinancialDataExplain(order);
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	// 保存重大事项调查
	@ResponseBody
	@RequestMapping("saveFInvestigationMajorEventSubmit.htm")
	public Object saveFInvestigationMajorEventSubmit(HttpSession session, String token,
														HttpServletRequest request,
														FInvestigationMajorEventOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		if (isRepeatSubmit(json, session, token)) {
			return json;
		}
		
		String tipPrefix = "保存重大事项调查";
		try {
			String url = getNextUrl(order.getToIndex());
			if (order.noNeedSave()) {
				return toUrl(tipPrefix, url, order);
			}
			order.setFormCode(FormCodeEnum.INVESTIGATION_BASE);
			order.setCheckIndex(6);
			//			order.setCheckStatus(0);
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FormBaseResult result = investigationServiceClient.saveFInvestigationMajorEvent(order);
			if (result.isSuccess()) {
				addAttachfile(result.getFormInfo().getFormId() + "_index5", request,
					order.getProjectCode(), "尽调-重大事项调查附件", CommonAttachmentTypeEnum.FORM_ATTACH);
			}
			json = toJSONResult(result, tipPrefix, order.getToIndex(), url, session);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	// 保存项目情况调查
	@ResponseBody
	@RequestMapping("saveFInvestigationProjectStatusSubmit.htm")
	public Object saveFInvestigationProjectStatusSubmit(HttpSession session, String token,
														HttpServletRequest request,
														FInvestigationProjectStatusOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		if (isRepeatSubmit(json, session, token)) {
			return json;
		}
		
		String tipPrefix = "保存项目情况调查";
		try {
			String url = getNextUrl(order.getToIndex());
			if (order.noNeedSave()) {
				return toUrl(tipPrefix, url, order);
			}
			order.setFormCode(FormCodeEnum.INVESTIGATION_BASE);
			order.setCheckIndex(7);
			//			order.setCheckStatus(0);
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FormBaseResult result = investigationServiceClient
				.saveFInvestigationProjectStatus(order);
			if (result.isSuccess()) {
				long keyId = order.getFormId();
				//添加附件
				addAttachfile(keyId + "", request, order.getProjectCode(), "尽调-项目情况调查附件",
					CommonAttachmentTypeEnum.INVESTIGATION_6);
				
				addAttachfile(result.getFormInfo().getFormId() + "_index6", request,
					order.getProjectCode(), "尽调-项目情况调查表单附件", CommonAttachmentTypeEnum.FORM_ATTACH);
			}
			json = toJSONResult(result, tipPrefix, order.getToIndex(), url, session);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	private int[] getYears() {
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		int year = c.get(Calendar.YEAR);
		int size = 10;
		int[] years = new int[size];
		for (int i = 0; i < size; i++) {
			years[i] = year - i;
		}
		return years;
	}
	
	// 保存授信方案合理性评价
	@ResponseBody
	@RequestMapping("saveFInvestigationCsRationalityReviewSubmit.htm")
	public Object saveFInvestigationCsRationalityReviewSubmit(	HttpSession session,
																String token,
																HttpServletRequest request,
																FInvestigationCsRationalityReviewOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		if (isRepeatSubmit(json, session, token)) {
			return json;
		}
		
		String tipPrefix = "保存授信方案合理性评价";
		try {
			String url = getNextUrl(order.getToIndex());
			if (order.noNeedSave()) {
				return toUrl(tipPrefix, url, order);
			}
			order.setFormCode(FormCodeEnum.INVESTIGATION_BASE);
			order.setCheckIndex(8);
			//			order.setCheckStatus(0);
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FormBaseResult result = investigationServiceClient
				.saveFInvestigationCsRationalityReview(order);
			if (result.isSuccess()) {
				addAttachfile(result.getFormInfo().getFormId() + "_index7", request,
					order.getProjectCode(), "尽调-授信方案合理性评价附件", CommonAttachmentTypeEnum.FORM_ATTACH);
				//风险提示保存
				verifyMsgSave(request, "JZ" + result.getFormInfo().getFormId() + "index7");
			}
			json = toJSONResult(result, tipPrefix, order.getToIndex(), url, session);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	// 保存风险点分析和结论意见
	@ResponseBody
	@RequestMapping("saveFInvestigationRiskSubmit.htm")
	public Object saveFInvestigationRiskSubmit(HttpSession session, String token,
												HttpServletRequest request,
												FInvestigationRiskOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		if (isRepeatSubmit(json, session, token)) {
			return json;
		}
		
		String tipPrefix = "保存风险点分析和结论意见";
		try {
			String url = getNextUrl(order.getToIndex());
			if (order.noNeedSave()) {
				return toUrl(tipPrefix, url, order);
			}
			order.setFormCode(FormCodeEnum.INVESTIGATION_BASE);
			order.setCheckIndex(9);
			//			order.setCheckStatus(0);
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FormBaseResult result = investigationServiceClient.saveFInvestigationRisk(order);
			if (result.isSuccess()) {
				addAttachfile(result.getFormInfo().getFormId() + "_index8", request,
					order.getProjectCode(), "尽调-风险点分析和结论意见附件", CommonAttachmentTypeEnum.FORM_ATTACH);
			}
			json = toJSONResult(result, tipPrefix, order.getToIndex(), url, session);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	@RequestMapping("downloadExcelModel.htm")
	public void downloadExcelModel(HttpServletRequest request, HttpServletResponse response,
									String type) {
		if (type.equals("economy_type")) {
			downDoc(request, response, type);
		} else {
			downLoadExcel(request, response, type);
		}
	}
	
	private static final String EXCEL_MODEL_PATH = "/xsl/excelModel/";
	/** 保证人主要财务指标 */
	private static final String EXCEL_FINANCIAL_INDEX = "financial_index_d";
	/** 财务比较分析表 */
	private static final String EXCEL_FINANCIAL_ANALYSE = "financial_analyse_d";
	/** 客户主要高管人员表 */
	private static final String EXCEL_LEADERS = "leaders";
	/** 项目投资与资金筹措表 */
	private static final String EXCEL_PROJECT_FUND = "project_fund";
	/** 一般企业企业报表标准格式-模板 */
	private static final String EXCEL_FINANCIAL_TABLES = "financial_tables_d";
	/** 金融企业报表标准格式-模板 */
	private static final String EXCEL_FINANCIAL_TABLES_J = "financial_tables_J_d";
	/** 保后检查报告-财务状况检查-主要财务指标-模板 */
	private static final String EXCEL_AFTERWARDSCHECK_FINANCIAL_INDEX = "financial_index_f_i";
	/** 尽调 客户主体评价-信用评价 8合1 */
	private static final String EXCEL_INVESTIGATION_CREDIT_4_IN_1 = "e_i_c_4_i_1";
	
	private static final String EXCEL_ECONOMY_TYPE = "economy_type" ;
	
	private static final Map<String, String> EXCEL_MAP = new HashMap<>();
	static {
		//type-fileName(_d)(表示动态，表头列内容随时间变化而变化)
		EXCEL_MAP.put(EXCEL_FINANCIAL_INDEX, "保证人主要财务指标");
		EXCEL_MAP.put(EXCEL_FINANCIAL_ANALYSE, "财务比较分析表");
		//		EXCEL_MAP.put("pay_ability_analyse_d", "偿债能力分析");
		//		EXCEL_MAP.put("operate_ability_analyse_d", "运营能力分析");
		//		EXCEL_MAP.put("benifit_ability_analyse_d", "盈利能力分析");
		//		EXCEL_MAP.put("cash_flow_analyse_d", "现金流分析");
		EXCEL_MAP.put(EXCEL_LEADERS, "客户主要高管人员表");
		EXCEL_MAP.put(EXCEL_PROJECT_FUND, "项目投资与资金筹措表");
		EXCEL_MAP.put(EXCEL_FINANCIAL_TABLES, "一般企业企业报表标准格式-模板");
		EXCEL_MAP.put(EXCEL_FINANCIAL_TABLES_J, "金融企业报表标准格式-模板");
		EXCEL_MAP.put(EXCEL_AFTERWARDSCHECK_FINANCIAL_INDEX, "保后检查报告-财务状况检查-主要财务指标-模板");
		EXCEL_MAP.put(EXCEL_INVESTIGATION_CREDIT_4_IN_1, "尽调-客户主体评价");
		EXCEL_MAP.put(EXCEL_ECONOMY_TYPE, "国民经济行业分类（GBT 4754-2011）");
	}
	
	private JSONObject findFinancialDatas(HttpServletRequest request, String type) {
		String fileName = EXCEL_MAP.get(type);
		if (StringUtil.isNotBlank(fileName)) {
			Date now = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(now);
			int year = c.get(Calendar.YEAR);
			
			if (type.endsWith("_d")) { //动态的，就是需要在表格名称后面添加_$year
				fileName = fileName + "_" + year;
			}
			String excelPath = request.getServletContext().getRealPath("/WEB-INF/")
								+ EXCEL_MODEL_PATH + fileName + ".xls";
			try {
				return DataFinancialHelper.parseExcel(excelPath);
			} catch (BiffException | IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		return null;
	}
	
	private ExcelData findExcelModel(HttpServletRequest request, String type) {
		String fileName = EXCEL_MAP.get(type);
		if (StringUtil.isNotBlank(fileName)) {
			Date now = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(now);
			int year = c.get(Calendar.YEAR);
			
			if (type.endsWith("_d")) { //动态的，就是需要在表格名称后面添加_$year
				fileName = fileName + "_" + year;
			}
			String filePath = request.getServletContext().getRealPath("/WEB-INF/")
								+ EXCEL_MODEL_PATH + fileName + ".xls";
			try {
				return ExcelUtil.parseExcel(filePath);
			} catch (BiffException e) {
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}
	
	public void downLoadExcel(HttpServletRequest request, HttpServletResponse response, String type) {
		BufferedOutputStream output = null;
		BufferedInputStream input = null;
		
		try {
			String fileName = EXCEL_MAP.get(type);
			if (StringUtil.isNotBlank(fileName)) {
				Date now = new Date();
				Calendar c = Calendar.getInstance();
				c.setTime(now);
				int year = c.get(Calendar.YEAR);
				
				if (type.endsWith("_d")) { //动态的，就是需要在表格名称后面添加_$year
					fileName = fileName + "_" + year;
				}
				String filePath = request.getServletContext().getRealPath("/WEB-INF/")
									+ EXCEL_MODEL_PATH + fileName + ".xls";
				File file = new File(filePath);
				response.setHeader("Content-disposition",
					"attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1")
							+ ".xls");
				response.setContentType("application/msexcel");
				response.setContentLength((int) file.length());
				byte[] buffer = new byte[4096];// 缓冲区
				output = new BufferedOutputStream(response.getOutputStream());
				input = new BufferedInputStream(new FileInputStream(file));
				int n = -1;
				//遍历，开始下载
				while ((n = input.read(buffer, 0, 4096)) > -1) {
					output.write(buffer, 0, n);
				}
				output.flush(); //不可少
				response.flushBuffer();//不可少
			}
		} catch (Exception e) {
			//异常自己捕捉
			logger.error("读取excel模板异常：" + e);
		} finally {
			//关闭流，不可少
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	
	
	public void downDoc(HttpServletRequest request, HttpServletResponse response, String type) {
		BufferedOutputStream output = null;
		BufferedInputStream input = null;
		
		try {
			String fileName = EXCEL_MAP.get(type);
			String filePath = request.getServletContext().getRealPath("/WEB-INF/")
								+ "/xsl/excelModel/" + fileName + ".doc";
			File file = new File(filePath);
			response.setHeader("Content-disposition",
				"attachment; filename="
						+ new String(fileName.getBytes("GB2312"), "ISO8859-1") + ".doc");
			response.setContentType("application/msword");
			response.setContentLength((int) file.length());
			byte[] buffer = new byte[4096];// 缓冲区
			output = new BufferedOutputStream(response.getOutputStream());
			input = new BufferedInputStream(new FileInputStream(file));
			int n = -1;
			//遍历，开始下载
			while ((n = input.read(buffer, 0, 4096)) > -1) {
				output.write(buffer, 0, n);
			}
			output.flush(); //不可少
			response.flushBuffer();//不可少
			
		} catch (Exception e) {
			//异常自己捕捉
			logger.error("读取doc模板异常：" + e);
		} finally {
			//关闭流，不可少
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	
	
	
	//上传excel数据
	@RequestMapping("uploadExcel.htm")
	public void uploadExcel(HttpServletRequest request, HttpServletResponse response, String type) {
		JSONObject json = new JSONObject();
		try {
			ExcelData excelData = null;
			if (StringUtil.equals(EXCEL_INVESTIGATION_CREDIT_4_IN_1, type)) {
				List<List<String[]>> list = ExcelUtil.uploadExcel3(request);
				if (ListUtil.isNotEmpty(list)) {
					JSONArray ja = new JSONArray();
					int i = 1;
					for (List<String[]> data : list) {
						JSONObject ret = new JSONObject();
						ret.put("data" + i++, data);
						ja.add(ret);
					}
					json.put("success", true);
					json.put("message", "上传成功");
					json.put("datas", ja);
				} else {
					json.put("success", false);
					json.put("message", "上传失败");
				}
				returnJson(response, true, json);
				return;
			} else if (EXCEL_LEADERS.equals(type)) {
				excelData = ExcelUtil.uploadExcel2(request);
			} else {
				excelData = ExcelUtil.uploadExcel(request);
			}
			if (null != excelData) {
				json.put("success", true);
				json.put("message", "上传成功");
				if (EXCEL_LEADERS.equals(type)) {
					json.put("datas", parse(excelData.getDatas()));
				} else {
					json.put("datas", excelData.getDatas());
				}
			} else {
				json.put("success", false);
				json.put("message", "上传失败");
			}
			
		} catch (BiffException | FileUploadException | IOException e) {
			logger.error(e.getMessage(), e);
			json.put("success", false);
			json.put("message", "上传异常" + e.getMessage());
		}
		
		try {
			returnJson(response, true, json);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private Object parse(String[][] datas) {
		if (null == datas || datas.length <= 0) {
			return null;
		}
		
		JSONArray array = new JSONArray();
		JSONArray subArray = null;
		List<String[]> list = null;
		for (String[] data : datas) {
			if (data.length != 16) {
				return null;
			}
			
			String[] ss = new String[4];
			if (StringUtil.isNotBlank(data[0])) {
				if (subArray != null) {
					subArray.add(list);
					array.add(subArray);
				}
				subArray = new JSONArray();
				subArray.add(data[1]);
				subArray.add(data[3]);
				subArray.add(data[5]);
				subArray.add(data[7]);
				list = new ArrayList<>();
			} else {
				
			}
			ss[0] = (null == data[9]) ? "" : data[9].replaceAll("/", "-");
			ss[1] = (null == data[11]) ? "" : data[11].replaceAll("/", "-");
			ss[2] = data[13];
			ss[3] = data[15];
			list.add(ss);
		}
		if (subArray != null) {
			subArray.add(list);
			array.add(subArray);
		}
		
		return array;
	}
	
	//上传excel数据
	@RequestMapping("uploadFinancialExcel.htm")
	public void uploadFinancialExcel(HttpServletRequest request, String type,
										HttpServletResponse response) {
		JSONObject json = new JSONObject();
		try {
			DataFinancial df = installDataFinancial(request);
			if (null != df) {
				if (df.isHasData()) {
					if ("1".equals(request.getParameter("subIndex"))) {
						//						check = false;
						//						System.out.println("YES" + type);
						type = ""; //不验证
					}
					JSONObject data = DataFinancialHelper.parseToJson(df, type);
					if (null != data) {
						json.put("success", true);
						json.put("message", "上传成功");
						json.put("datas", data);
						returnJson(response, true, json);
						return;
					}
				} else {
					json.put("success", false);
					json.put("message", df.getErrorMsg());
				}
			} else {
				json.put("success", false);
				json.put("message", "上传失败");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			json.put("success", false);
			json.put("message", "上传异常" + e.getMessage());
		}
		
		try {
			returnJson(response, true, json);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private DataFinancial installDataFinancial(HttpServletRequest request) throws IOException {
		ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
		fileUpload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = null;
		boolean check = true;
		try {
			fileList = fileUpload.parseRequest(request);
			if ("1".equals(request.getParameter("subIndex"))) {
				check = false;
			}
		} catch (FileUploadException ex) {
			logger.error(ex.getMessage(), ex);
			return null;
		}
		Iterator<FileItem> it = fileList.iterator();
		InputStream is = null;
		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {
				is = item.getInputStream();
				break;
			}
		}
		
		if (null == is) {
			logger.error("财务报表解析失败：未找到上传的文件流");
			return null;
		}
		
		try {
			DataFinancial dataFinancial = new DataFinancial();
			Map<String, String> data = new HashMap<String, String>();
			Workbook model = Workbook.getWorkbook(is);
			Sheet balanceSheet = model.getSheet(0);
			dataFinancial.setBalance(DataFinancialHelper.installDataSheet(balanceSheet, data));
			Sheet profitSheet = model.getSheet(1);
			dataFinancial.setProfit(DataFinancialHelper.installDataSheet(profitSheet, data));
			Sheet cashFlowSheet = model.getSheet(2);
			dataFinancial.setCashFlow(DataFinancialHelper.installDataSheet(cashFlowSheet, data));
			
			dataFinancial.setData(data);
			
			if (check && !hasItems(data, dataFinancial.getBalance().getYears()[0])) {
				dataFinancial.setHasData(false);
				dataFinancial.setErrorMsg("必需的科目缺失");
				return dataFinancial;
			}
			
			DataFinancialHelper.installDPAanalyze(dataFinancial);
			DataFinancialHelper.instalLOCanalyze(dataFinancial);
			DataFinancialHelper.instalECanalyze(dataFinancial);
			DataFinancialHelper.installCFAanalyze(dataFinancial);
			dataFinancial.setHasData(true);
			return dataFinancial;
		} catch (BiffException | IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	private static final String[] ITEMS = {
											DataFinancialHelper.PROFIT_FINANCIAL_COST,
											DataFinancialHelper.PROFIT_TOTAL_PROFIT,
											DataFinancialHelper.BALANCE_TOTAL_LIABILITY,
											DataFinancialHelper.BALANCE_TOTAL_CAPITAL,
											DataFinancialHelper.BALANCE_ACCRUED_LIABILITIES,
											DataFinancialHelper.BALANCE_TOTAL_FLOW_CAPITAL,
											DataFinancialHelper.BALANCE_TOTAL_CURRENT_LIABILITY,
											DataFinancialHelper.BALANCE_UNAMORTIZED_EXPENSE,
											DataFinancialHelper.BALANCE_INVENTORY,
											DataFinancialHelper.CASHFLOW_NET_AMOUNT_BUSINESS_ACTIVITIES,
											DataFinancialHelper.PROFIT_MAIN_BUSINESS_COST,
											DataFinancialHelper.BALANCE_RECEIVABLES,
											DataFinancialHelper.PROFIT_MAIN_BUSINESS_INCOME,
											DataFinancialHelper.BALANCE_ACCOUNTS_PAYABLE,
											DataFinancialHelper.BALANCE_ADVANCE_PAYMENT,
											DataFinancialHelper.PROFIT_RETAINED_PROFITS,
											DataFinancialHelper.BALANCE_TOTAL_SHAREHOLDERS_EQUITYTOTAL,
											DataFinancialHelper.CASHFLOW_SUBTOTAL_OF_CASH_INFLOW_BUSINESS_ACTIVITIES,
											DataFinancialHelper.CASHFLOW_SUBTOTAL_OF_CASH_OUT_FLOWS_BUSINESS_ACTIVITIES,
											DataFinancialHelper.CASHFLOW_SUBTOTAL_OF_CASH_INFLOW_INVESTMENT,
											DataFinancialHelper.CASHFLOW_SUBTOTAL_OF_CASH_OUT_FLOWS_INVESTMENT,
											DataFinancialHelper.CASHFLOW_NET_AMOUNT_INVESTMENT,
											DataFinancialHelper.CASHFLOW_SUBTOTAL_OF_CASH_INFLOW_FINANCING,
											DataFinancialHelper.CASHFLOW_SUBTOTAL_OF_CASH_OUT_FLOWS_FINANCING,
											DataFinancialHelper.CASHFLOW_NET_AMOUNT_FINANCING,
											DataFinancialHelper.CASHFLOW_THE_NET_CASH_FLOW };
	
	private boolean hasItems(Map<String, String> data, String key) {
		if (null == data || data.size() <= 0) {
			return false;
		}
		
		for (String item : ITEMS) {
			if (!data.containsKey(key + "." + item)) {
				logger.info("报表缺少科目：" + item);
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 编辑前更新客户信息
	 * @param order
	 */
	private void updateCustomerInfo(UpdateInvestigationCustomerOrder order) {
		FcsBaseResult result = investigationServiceClient.updateCustomerInfo(order);
		logger.info("编辑尽调客户信息结果：" + result);
	}
	
	@RequestMapping("edit.htm")
	public String edit(	HttpServletRequest request,
						HttpSession session,
						Model model,
						long formId,
						@RequestParam(value = "toIndex", required = false, defaultValue = "0") int toIndex,
						@RequestParam(value = "forCustomer", required = false, defaultValue = "0") int forCustomer,
						@RequestParam(value = "checkPoint", required = false, defaultValue = "") String checkPoint) {
		String subIndexStr = request.getParameter("subIndex");
		Integer subIndex = null;
		if (StringUtil.isNumeric(subIndexStr)) {
			subIndex = Integer.valueOf(subIndexStr);
		}
		FInvestigationInfo baseInfo = investigationServiceClient.findInvestigationByFormId(formId);
		model.addAttribute("baseInfo", baseInfo);
		String projectCode = baseInfo.getProjectCode();
		Object isOld = session.getAttribute("version");
		
		initSubmitCommon(request, session, model, formId);
		//加载审核过程中编辑的标识
		if (StringUtil.isNotBlank(checkPoint)) {
			model.addAttribute("checkPoint", checkPoint);
			String auditIndex = request.getParameter("auditIndex");
			model.addAttribute("auditIndex", null == auditIndex ? toIndex : auditIndex);
		}
		
		//风险提示查询
		if (toIndex == 1 || toIndex == 7) {
			verifyMsgQuery("JZ" + formId + "index" + toIndex, model);
		}
		if (ProjectUtil.isLitigation(baseInfo.getBusiType())) {
			//更新客户信息(诉保)
			//			UpdateInvestigationCustomerOrder order = new UpdateInvestigationCustomerOrder();
			//			order.setFormId(baseInfo.getFormId());
			//			order.setProjectCode(baseInfo.getProjectCode());
			//			order.setToIndex(-1);
			//			updateCustomerInfo(order);
			
			FInvestigationLitigationInfo litigation = investigationServiceClient
				.findFInvestigationLitigationByFormId(formId);
			
			ProjectInfo project = projectServiceClient
				.queryByCode(baseInfo.getProjectCode(), false);
			model.addAttribute("busiManagerId", project.getBusiManagerId());
			model.addAttribute("busiManagerName", project.getBusiManagerName());
			ProjectRelatedUserInfo busiManagerb = projectRelatedUserServiceClient
				.getBusiManagerbByPhase(projectCode, ChangeManagerbPhaseEnum.INVESTIGATING_PHASES);
			if (null != busiManagerb) {
				model.addAttribute("busiManagerbName", busiManagerb.getUserName());
			}
			
			FProjectLgLitigationInfo flitigation = projectSetupServiceClient
				.queryLgLitigationProjectByCode(projectCode);
			if (null == litigation) {
				litigation = initLitigation(baseInfo);
				litigation.setGuaranteeAmount(project.getAmount()); //保全金额
				if (null != flitigation) {
					litigation.setDeposit(flitigation.getDeposit());
					litigation.setDepositType(flitigation.getDepositType());
					litigation.setDepositAccount(flitigation.getDepositAccount());
					litigation.setInformationFeeType(flitigation.getCoInstitutionChargeType());
					litigation.setInformationFee(flitigation.getCoInstitutionCharge());
					litigation.setCourt(flitigation.getCourt());
					litigation.setContent(flitigation.getAssureObject());
				}
			} else {
				litigation.setAmount(baseInfo.getAmount());
				litigation.setInvestigatePersion(baseInfo.getInvestigatePersion());
				
				if (forCustomer == 1) {
					CustomerDetailInfo customerInfo = customerServiceClient.queryByUserId(baseInfo
						.getCustomerId());
					if (null != customerInfo) {
						if (StringUtil.equals(customerInfo.getCustomerType(),
							CustomerTypeEnum.PERSIONAL.code())) {
							if (StringUtil.isNotBlank(customerInfo.getCustomerName())) {
								litigation.setContactPerson(customerInfo.getCustomerName());
							}
						} else {
							if (StringUtil.isNotBlank(customerInfo.getContactMan())) {
								litigation.setContactPerson(customerInfo.getContactMan());
							}
						}
						if (StringUtil.isNotBlank(customerInfo.getContactNo())) {
							litigation.setContactNo(customerInfo.getContactNo());
						}
					}
				}
			}
			
			model.addAttribute("info", litigation);
			
			model.addAttribute("chargeTypeList", ChargeTypeEnum.getAllEnum());
			return VM_PATH + "editLitigation.vm";
		} else if (ProjectUtil.isUnderwriting(baseInfo.getBusiType())) {
			FInvestigationUnderwritingInfo underwriting = investigationServiceClient
				.findFInvestigationUnderwritingByFormId(formId);
			if (null == underwriting) {
				underwriting = initUnderwriting(baseInfo);
			} else {
				queryCommonAttachmentData(model, underwriting.getUnderwritingId() + "",
					CommonAttachmentTypeEnum.UNDERWRITING_PROJECT);
			}
			
			model.addAttribute("info", underwriting);
			
			model.addAttribute("chargeUnitList", ChargeTypeEnum.getAllEnum());
			model.addAttribute("chargeWayList", UnderwritingChargeWaytEnum.getAllEnum());
			
			return VM_PATH + "editUnderwriting.vm";
		} else if (ProjectUtil.isInnovativeProduct(baseInfo.getBusiType())) {
			FInvestigationUnderwritingInfo underwriting = investigationServiceClient
					.findFInvestigationUnderwritingByFormId(formId);
			if (null == underwriting) {
				underwriting = initUnderwriting(baseInfo);
			} else {
				queryCommonAttachmentData(model, underwriting.getUnderwritingId() + "",
						CommonAttachmentTypeEnum.UNDERWRITING_PROJECT);
			}

			model.addAttribute("info", underwriting);

			model.addAttribute("chargeUnitList", ChargeTypeEnum.getAllEnum());
			model.addAttribute("chargeWayList", UnderwritingChargeWaytEnum.getAllEnum());
			queryCommonAttachmentData(model, formId+ "_INVESTIGATION1",
					CommonAttachmentTypeEnum.INVESTIGATION_INNOVATIVE_PRODUCT);
			return VM_PATH + "editInnovativeProduct.vm";
		}


		else {
			//针对新的尽调 客户财务评价处理
			if (null == isOld && toIndex == 2) {
				toIndex = 4;
			}
			//针对新的尽调 上一步
			if (null == isOld && toIndex == 3) {
				toIndex = 1;
			}
			
			switch (toIndex) {
				case 0:
					initBase0(baseInfo, model, request, forCustomer == 1);
					break;
				case 1:
					initBase1(baseInfo, model, forCustomer == 1);
					break;
				case 2:
					initBase2(baseInfo, model);
					break;
				case 3:
					initBase3(baseInfo, model);
					break;
				case 4:
					initBase4(baseInfo, model, request, subIndex, checkPoint);
					subIndex = baseInfo.getSubIndex();
					break;
				case 5:
					initBase5(baseInfo, model);
					break;
				case 6:
					initBase6(baseInfo, model, request);
					break;
				case 7:
					initBase7(baseInfo, model, request);
					break;
				case 8:
					initBase8(baseInfo, model);
					break;
				default:
					break;
			}
			if (null != subIndex && subIndex > 0) {
				return VM_PATH + "editBase" + toIndex + "" + subIndex + ".vm";
			} else {
				//针对新的尽调 客户主体评价处理
				if (null == isOld && toIndex == 1) {
					toIndex = 11;
				}
				return VM_PATH + "editBase" + toIndex + ".vm";
			}
		}
	}
	
	//查看尽职调查申明
	@RequestMapping("viewDeclare.htm")
	public String viewDeclare(Model model, long formId, @RequestParam(value = "audit",
			required = false, defaultValue = "NO") String audit, @RequestParam(
			value = "checkPoint", required = false, defaultValue = "") String checkPoint,HttpServletRequest request) {
		FInvestigationInfo info = investigationServiceClient.findInvestigationByFormId(formId);
		
		model.addAttribute("info", info);
		model.addAttribute("audit", audit); //YES表示是审核时的查看,NO直接查看
		model.addAttribute("formId", formId);
		model.addAttribute("form", formServiceClient.findByFormId(formId));
		model.addAttribute("hasRiskReviewReport", hasRiskReviewReport(formId));
		
		ProjectInfo project = projectServiceClient.queryByCode(info.getProjectCode(), false);
		if (null != project) {
			model.addAttribute("busiManagerName", project.getBusiManagerName());
		}
		
		CustomerDetailInfo customerInfo = customerServiceClient.queryByUserId(info.getCustomerId());
		model.addAttribute("customerInfo", customerInfo);
		
		String[] exclude = { "formId", "newFormId", "projectCode", "projectName", "customerId",
							"busiTypeName", "investigateId", "rawAddTime", "rawUpdateTime",
							"review", "councilBack", "councilType", "councilStatus", "id",
							"sortOrder", "riskRate" };
		if (info.getNewFormId() > 0 && isCompare()) { //复议|上会退回
			FInvestigationInfo base0His = investigationServiceClient.findInvestigationByFormId(info
				.getNewFormId());
			Map<String, String> map = MiscUtil.comparePo(base0His, info, exclude);
			
			model.addAttribute("cmp", map);
			//			System.out.println("比较结果：" + map);
		}
		
		int curIndex = -1;
		model.addAttribute("curIndex", curIndex);
		model.addAttribute("checkPoint", checkPoint);
		addCmp(checkPoint, model, curIndex, info, info.getNewFormId(), exclude);
		
		return VM_PATH + "viewDeclare.vm";
	}
	
	private void addCmp(String checkPoint, Model model, int curIndex, InvestigationBaseInfo info,
						long newFormId, String[] exclude) {
		Map<CheckPointEnum, InvestigationCheckingInfo> map = queryCmpMap(info.getFormId());
		InvestigationCheckingInfo info0 = queryCheck(curIndex, map.get(CheckPointEnum.SOURCE));
		InvestigationCheckingInfo info1 = queryCheck(curIndex, map.get(CheckPointEnum.TEAM_LEADER));
		InvestigationCheckingInfo info2 = queryCheck(curIndex,
			map.get(CheckPointEnum.VICE_PRESIDENT_BUSINESS));
		//复议
		InvestigationBaseInfo back = null;
		InvestigationBaseInfo source = null;
		InvestigationBaseInfo his1 = null;
		InvestigationBaseInfo his2 = null;
		long formId0 = 0L;
		long formId1 = 0L;
		long formId2 = 0L;
		BooleanEnum onlySource = BooleanEnum.YES; //修改的版本标识
		if (null != info0) {
			formId0 = info0.getFormId();
			if (null != info1 && null != info2) {
				//1改，2改
				formId1 = info2.getFormId();
				formId2 = info.getFormId();
				his2 = info;
			} else if (null != info1 && null == info2) {
				//1改，2不改
				formId1 = info.getFormId();
				his1 = info;
			} else if (null == info1 && null != info2) {
				//1不改，2改
				formId2 = info.getFormId();
				his2 = info;
			} else {
				source = info;
				//				model.addAttribute("onlySource", BooleanEnum.YES.code()); //没有修改的版本标识
			}
		} else {
			source = info;
			//			model.addAttribute("onlySource", BooleanEnum.YES.code()); //没有修改的版本标识
		}
		
		switch (curIndex) {
			case -1:
				//源数据 
				if (null == source) {
					source = investigationServiceClient.findInvestigationByFormId(formId0);
				}
				if (newFormId > 0 && isCompare()) {
					back = investigationServiceClient.findInvestigationByFormId(newFormId);
				}
				//老数据1
				if (null == his1) {
					his1 = investigationServiceClient.findInvestigationByFormId(formId1);
				}
				//老数据2
				if (null == his2) {
					his2 = investigationServiceClient.findInvestigationByFormId(formId2);
				}
				break;
			case 0:
				if (null == source) {
					source = investigationServiceClient
						.findInvestigationCreditSchemeByFormId(formId0);
				}
				if (newFormId > 0 && isCompare()) {
					back = investigationServiceClient
						.findInvestigationCreditSchemeByFormId(newFormId);
				}
				if (null == his1) {
					his1 = investigationServiceClient
						.findInvestigationCreditSchemeByFormId(formId1);
				}
				if (null == his2) {
					his2 = investigationServiceClient
						.findInvestigationCreditSchemeByFormId(formId2);
				}
				break;
			case 1:
				if (null == source) {
					source = investigationServiceClient
						.findInvestigationMainlyReviewByFormId(formId0);
				}
				if (newFormId > 0 && isCompare()) {
					back = investigationServiceClient
						.findInvestigationMainlyReviewByFormId(newFormId);
				}
				if (null == his1) {
					his1 = investigationServiceClient
						.findInvestigationMainlyReviewByFormId(formId1);
				}
				if (null == his2) {
					his2 = investigationServiceClient
						.findInvestigationMainlyReviewByFormId(formId2);
				}
				break;
			case 2:
				if (null == source) {
					source = investigationServiceClient
						.findFInvestigationMabilityReviewByFormId(formId0);
				}
				if (newFormId > 0 && isCompare()) {
					back = investigationServiceClient
						.findFInvestigationMabilityReviewByFormId(newFormId);
				}
				if (null == his1) {
					his1 = investigationServiceClient
						.findFInvestigationMabilityReviewByFormId(formId1);
				}
				if (null == his2) {
					his2 = investigationServiceClient
						.findFInvestigationMabilityReviewByFormId(formId2);
				}
				break;
			case 3:
				if (null == source) {
					source = investigationServiceClient
						.findFInvestigationOpabilityReviewByFormId(formId0);
				}
				if (newFormId > 0 && isCompare()) {
					back = investigationServiceClient
						.findFInvestigationOpabilityReviewByFormId(newFormId);
				}
				if (null == his1) {
					his1 = investigationServiceClient
						.findFInvestigationOpabilityReviewByFormId(formId1);
				}
				if (null == his2) {
					his2 = investigationServiceClient
						.findFInvestigationOpabilityReviewByFormId(formId2);
				}
				break;
			case 4:
				if (null == source) {
					source = investigationServiceClient
						.findFInvestigationFinancialReviewByFormId(formId0);
				}
				if (newFormId > 0 && isCompare()) {
					back = investigationServiceClient
						.findFInvestigationFinancialReviewByFormId(newFormId);
				}
				if (null == his1) {
					his1 = investigationServiceClient
						.findFInvestigationFinancialReviewByFormId(formId1);
				}
				if (null == his2) {
					his2 = investigationServiceClient
						.findFInvestigationFinancialReviewByFormId(formId2);
				}
				break;
			case 5:
				if (null == source) {
					source = investigationServiceClient
						.findFInvestigationMajorEventByFormId(formId0);
				}
				if (newFormId > 0 && isCompare()) {
					back = investigationServiceClient
						.findFInvestigationMajorEventByFormId(newFormId);
				}
				if (null == his1) {
					his1 = investigationServiceClient.findFInvestigationMajorEventByFormId(formId1);
				}
				if (null == his2) {
					his2 = investigationServiceClient.findFInvestigationMajorEventByFormId(formId2);
				}
				break;
			case 6:
				if (null == source) {
					source = investigationServiceClient
						.findFInvestigationProjectStatusByFormId(formId0);
				}
				if (newFormId > 0 && isCompare()) {
					back = investigationServiceClient
						.findFInvestigationProjectStatusByFormId(newFormId);
				}
				if (null == his1) {
					his1 = investigationServiceClient
						.findFInvestigationProjectStatusByFormId(formId1);
				}
				if (null == his2) {
					his2 = investigationServiceClient
						.findFInvestigationProjectStatusByFormId(formId2);
				}
				break;
			case 7:
				if (null == source) {
					source = investigationServiceClient
						.findFInvestigationCsRationalityReviewByFormId(formId0);
				}
				if (newFormId > 0 && isCompare()) {
					back = investigationServiceClient
						.findFInvestigationCsRationalityReviewByFormId(newFormId);
				}
				if (null == his1) {
					his1 = investigationServiceClient
						.findFInvestigationCsRationalityReviewByFormId(formId1);
				}
				if (null == his2) {
					his2 = investigationServiceClient
						.findFInvestigationCsRationalityReviewByFormId(formId2);
				}
				break;
			case 8:
				if (null == source) {
					source = investigationServiceClient.findFInvestigationRiskByFormId(formId0);
				}
				if (newFormId > 0 && isCompare()) {
					back = investigationServiceClient.findFInvestigationRiskByFormId(newFormId);
				}
				if (null == his1) {
					his1 = investigationServiceClient.findFInvestigationRiskByFormId(formId1);
				}
				if (null == his2) {
					his2 = investigationServiceClient.findFInvestigationRiskByFormId(formId2);
				}
				break;
			default:
				break;
		}
		
		source.setBusiTypeName(info.getBusiTypeName());
		model.addAttribute("info0", source); //客户经理版本
		FormInfo form0 = formServiceClient.findByFormId(formId0);
		model.addAttribute("form0", form0); //客户经理版本
		
		if (null != back) {
			back.setBusiTypeName(info.getBusiTypeName());
			FormInfo formBack = formServiceClient.findByFormId(newFormId);
			FormInfo sourceForm = formServiceClient.findByFormId(info.getFormId());
			Map<String, String> cmpMap = MiscUtil.comparePo(back, source, exclude);
			addFormCmp(form0, formBack, curIndex, cmpMap);
			judge(formBack, cmpMap, sourceForm);
			model.addAttribute("cmp", cmpMap);
			//			System.out.println("-----------------: " + cmpMap);
		}
		if (null != his1) {
			his1.setBusiTypeName(info.getBusiTypeName());
			FormInfo form1 = null ;
			if (null != his1 && null != his2) {
				form1 = formServiceClient.findByFormId(info1.getFormId());
				model.addAttribute("form1", form1);
			} else {
				form1 = formServiceClient.findByFormId(formId1);
				model.addAttribute("form1", form1);
			}
			Map<String, String> cmpMap = MiscUtil.comparePo(his1, source, exclude);
			//如果直接 追加，会使第一个页面出现 按钮，如果不追加，会使第二个页面修改后，没有按钮显示
			if (curIndex != -1) {
				cmpMap = judge(form0, cmpMap, form1);
			}
			if (null != cmpMap && cmpMap.size() > 0) {
				model.addAttribute("info1", his1);
				addFormCmp(form0, form1, curIndex, cmpMap);
				//业务部负责人是否修改了 评估机构名称、评估时间、评估方法、初评价值评价 和 结论意见
				model.addAttribute("cmp1", cmpMap);
				onlySource = BooleanEnum.NO;
			}
			//			System.out.println("-----------------: " + cmpMap);
		}
		if (null != his2) {
			his2.setBusiTypeName(info.getBusiTypeName());
			FormInfo form2 = formServiceClient.findByFormId(formId2);
			model.addAttribute("form2", form2);
			Map<String, String> cmpMap = MiscUtil.comparePo(his2, source, exclude);
			//如果直接 追加，会使第一个页面出现 按钮，如果不追加，会使第二个页面修改后，没有按钮显示
			if (curIndex != -1) {
				cmpMap = judge(form0, cmpMap, form2);
			}
			if (null != cmpMap && cmpMap.size() > 0) {
				model.addAttribute("info2", his2);
				//分管业务副总是否修改了 评估机构名称、评估时间、评估方法、初评价值评价 和 结论意见
				addFormCmp(form0, form2, curIndex, cmpMap);
				model.addAttribute("cmp2", cmpMap);
				onlySource = BooleanEnum.NO;
			}
			//			System.out.println("=================: " + cmpMap);
		}
		
		model.addAttribute("onlySource", onlySource); //YES表示没有修改过
	}
	
	/**
	 * 判断两个表单中的自定义字段有没有被修改过
	 * @param formBack
	 * @param cmpMap
	 * @param sourceForm
	 * @return
	 */
	private Map<String, String> judge(FormInfo formBack, Map<String, String> cmpMap, FormInfo sourceForm) {
		if (null != formBack && null != sourceForm) {
			String customizeField1 = sourceForm.getCustomizeField();
			String customizeField2 = formBack.getCustomizeField();
			if (!StringUtils.isBlank(customizeField1) && !StringUtils.isBlank(customizeField2)) {
				Map<String, Object>	customizeFieldMap1 = (Map<String, Object>) JSONObject.parse(customizeField1);
				Map<String, Object>	customizeFieldMap2 = (Map<String, Object>) JSONObject.parse(customizeField2);
				Object object = customizeFieldMap1.get("assessment");
				Object object11 = customizeFieldMap2.get("assessment");
				Object object2 = customizeFieldMap1.get("opinion");
				Object object22 = customizeFieldMap2.get("opinion");
				if (null !=object && null!= object11 && !object.toString().trim().equals(object11.toString().trim())) {
					cmpMap.put("assessment", "true");
				}
				if (null != object2 && null != object22 && !object2.toString().trim().equals(object22.toString().trim())) {
					cmpMap.put("opinion", "opinion");
				}
			}
		}
		return cmpMap;
	}
	
	private InvestigationCheckingInfo queryCheck(int index, InvestigationCheckingInfo checkInfo) {
		if (null == checkInfo) {
			return null;
		}
		
		char[] cs = checkInfo.getFormCode().toCharArray();
		if (CheckPointEnum.TEAM_LEADER == checkInfo.getCheckPoint()) {
			return checkInfo;
		}
		if ("1".equals("" + cs[index + 1])) {
			return checkInfo;
		}
		return null;
	}
	
	private void addFormCmp(FormInfo form1, FormInfo form2, int index, Map<String, String> map) {
		if (null == form1 || null == form2) {
			return;
		}
		if (null == map) {
			map = new HashMap<>();
		}
		
		String s1 = null;
		String s2 = null;
		if (index == -1) {
			s1 = getFormRemark("formRemarkDeclare", form1.getCustomizeFieldMap());
			s2 = getFormRemark("formRemarkDeclare", form2.getCustomizeFieldMap());
		} else {
			s1 = getFormRemark("formRemark" + index, form1.getCustomizeFieldMap());
			s2 = getFormRemark("formRemark" + index, form2.getCustomizeFieldMap());
		}
		
		if (StringUtil.notEquals(s1, s2)) {
			map.put("formRemarkCmp", "");
		}
	}
	
	private String getFormRemark(String key, Map<String, Object> map) {
		if (null == map) {
			return null;
		}
		
		Object obj = map.get(key);
		if (null == obj) {
			return "";
		}
		
		return obj.toString().trim();
	}
	
	//	private void addCmp(Model model, FInvestigationInfo info, String [] exclude) {
	//		Map<CheckPointEnum, InvestigationCheckingInfo> map = queryCmpMap(info.getFormId());
	//		InvestigationCheckingInfo info1 = map.get(CheckPointEnum.TEAM_LEADER);
	//		InvestigationCheckingInfo info2 = map.get(CheckPointEnum.VICE_PRESIDENT_BUSINESS);
	//		if (null != info1) {
	//			Map<String, String> map1 = MiscUtil.comparePo(info, info1, exclude);
	//			model.addAttribute("cmp1", map1);
	//		}
	//		if (null != info2) {
	//			Map<String, String> map2 = MiscUtil.comparePo(info, info2, exclude);
	//			model.addAttribute("cmp2", map2);
	//		}
	//	}
	
	private Map<CheckPointEnum, InvestigationCheckingInfo> queryCmpMap(long relatedFormId) {
		Map<CheckPointEnum, InvestigationCheckingInfo> map = new HashMap<>();
		List<InvestigationCheckingInfo> list = investigationServiceClient
			.findCheckingByRelatedFormId(relatedFormId);
		if (ListUtil.isNotEmpty(list)) {
			for (InvestigationCheckingInfo info : list) {
				if (null != info.getCheckPoint()) {
					map.put(info.getCheckPoint(), info);
				}
			}
		}
		return map;
	}
	
	@RequestMapping("view.htm")
	public String view(	HttpServletRequest request,
						HttpSession session,
						Model model,
						long formId,
						@RequestParam(value = "audit", required = false, defaultValue = "NO") String audit,
						@RequestParam(value = "toIndex", required = false, defaultValue = "0") int toIndex,
						@RequestParam(value = "checkPoint", required = false, defaultValue = "") String checkPoint) {
		String subIndexStr = request.getParameter("subIndex");
		Integer subIndex = null;
		if (StringUtil.isNumeric(subIndexStr)) {
			subIndex = Integer.valueOf(subIndexStr);
		}
		
		model.addAttribute("checkPoint", checkPoint);
		FInvestigationInfo baseInfo = investigationServiceClient.findInvestigationByFormId(formId);
		model.addAttribute("baseInfo", baseInfo);
		Object isOld = session.getAttribute("version");
		
		model.addAttribute("hasRiskReviewReport", hasRiskReviewReport(formId));
		model.addAttribute("audit", audit); //YES表示是审核时的查看,NO直接查看
		model.addAttribute("view", BooleanEnum.YES.code());
		
		FormInfo form = formServiceClient.findByFormId(formId);
		model.addAttribute("form", form);
		Map<String, Object> customizeFieldMap = form.getCustomizeFieldMap();
		Object zxCustomerjson = customizeFieldMap.get("zxCustomerjson");
		if (null != zxCustomerjson) {
			model.addAttribute("zxCustomerjson", JSONArray.parse(zxCustomerjson.toString()));
		}
		
		CustomerDetailInfo customerInfo = customerServiceClient.queryByUserId(baseInfo
			.getCustomerId());
		model.addAttribute("customerInfo", customerInfo);
		//风险提示查询
		if (toIndex == 1 || toIndex == 7) {
			verifyMsgQuery("JZ" + formId + "index" + toIndex, model);
		}
		if (ProjectUtil.isLitigation(baseInfo.getBusiType())) {
			FInvestigationLitigationInfo litigation = investigationServiceClient
				.findFInvestigationLitigationByFormId(formId);
			if (null == litigation) {
				litigation = initLitigation(baseInfo);
			}
			
			ProjectInfo project = projectServiceClient
				.queryByCode(baseInfo.getProjectCode(), false);
			model.addAttribute("busiManagerId", project.getBusiManagerId());
			model.addAttribute("busiManagerName", project.getBusiManagerName());
			//			model.addAttribute("busiManagerbName", project.getBusiManagerbName());
			ProjectRelatedUserInfo busiManagerb = projectRelatedUserServiceClient
				.getBusiManagerbByPhase(baseInfo.getProjectCode(),
					ChangeManagerbPhaseEnum.INVESTIGATING_PHASES);
			String manager = project.getBusiManagerName();
			if (null != busiManagerb) {
				model.addAttribute("busiManagerbName", busiManagerb.getUserName());
				manager = project.getBusiManagerName() + "、" + busiManagerb.getUserName();
			}
			model.addAttribute("info", litigation);
			
			if (baseInfo.getNewFormId() > 0 && isCompare()) { //复议|上会退回
				FInvestigationLitigationInfo litigationHis = investigationServiceClient
					.findFInvestigationLitigationByFormId(baseInfo.getNewFormId());
				String[] exclude = { "formId", "projectCode", "projectName", "customerId",
									"busiTypeName", "litigationId", "rawAddTime", "rawUpdateTime",
									"id", "investigatePersion", "sortOrder" };
				Map<String, String> map = MiscUtil.comparePo(litigationHis, litigation, exclude);
				if (StringUtil.notEquals(litigationHis.getInvestigatePersion(), manager)) {
					map.put("investigatePersion", litigationHis.getInvestigatePersion());
				}
				
				model.addAttribute("cmp", map);
			}
			if (StringUtil.isBlank(litigation.getInvestigatePersion())) {
				litigation.setInvestigatePersion(manager);
			}
			//				if (form.getStatus() == FormStatusEnum.DRAFT) {
			//					litigation.setInvestigatePersion(manager);
			//				}
			return VM_PATH + "viewLitigation.vm";
		} else if (ProjectUtil.isUnderwriting(baseInfo.getBusiType())) {
			FInvestigationUnderwritingInfo underwriting = investigationServiceClient
				.findFInvestigationUnderwritingByFormId(formId);
			if (null == underwriting) {
				underwriting = initUnderwriting(baseInfo);
			} else {
				queryCommonAttachmentData(model, underwriting.getUnderwritingId() + "",
					CommonAttachmentTypeEnum.UNDERWRITING_PROJECT);
			}
			
			model.addAttribute("info", underwriting);
			
			if (baseInfo.getNewFormId() > 0 && isCompare()) { //复议|上会退回
				FInvestigationUnderwritingInfo underwritingHis = investigationServiceClient
					.findFInvestigationUnderwritingByFormId(baseInfo.getNewFormId());
				String[] exclude = { "formId", "projectCode", "projectName", "customerId",
									"busiTypeName", "underwritingId", "rawAddTime",
									"rawUpdateTime", "id", "sortOrder" };
				Map<String, String> map = MiscUtil
					.comparePo(underwritingHis, underwriting, exclude);
				//比较附件
				if (null != underwritingHis) {
					compareAttachment(model, map, underwritingHis.getUnderwritingId(), "",
						CommonAttachmentTypeEnum.UNDERWRITING_PROJECT);
				}
				model.addAttribute("cmp", map);
			}
			
			return VM_PATH + "viewUnderwriting.vm";
		}else if(ProjectUtil.isInnovativeProduct(baseInfo.getBusiType())){
			FInvestigationUnderwritingInfo underwriting = investigationServiceClient
					.findFInvestigationUnderwritingByFormId(formId);
			if (null == underwriting) {
				underwriting = initUnderwriting(baseInfo);
			} else {
				queryCommonAttachmentData(model, underwriting.getUnderwritingId() + "",
						CommonAttachmentTypeEnum.UNDERWRITING_PROJECT);
			}

			model.addAttribute("info", underwriting);

			if (baseInfo.getNewFormId() > 0 && isCompare()) { //复议|上会退回
				FInvestigationUnderwritingInfo underwritingHis = investigationServiceClient
						.findFInvestigationUnderwritingByFormId(baseInfo.getNewFormId());
				String[] exclude = { "formId", "projectCode", "projectName", "customerId",
						"busiTypeName", "underwritingId", "rawAddTime",
						"rawUpdateTime", "id", "sortOrder" };
				Map<String, String> map = MiscUtil
						.comparePo(underwritingHis, underwriting, exclude);
				//比较附件
				if (null != underwritingHis) {
					compareAttachment(model, map, underwritingHis.getUnderwritingId(), "",
							CommonAttachmentTypeEnum.UNDERWRITING_PROJECT);
				}
				model.addAttribute("cmp", map);
			}
			queryCommonAttachmentData(model, form.getFormId() + "_INVESTIGATION1",
					CommonAttachmentTypeEnum.INVESTIGATION_INNOVATIVE_PRODUCT);
			return VM_PATH + "viewInnovativeProduct.vm";

		}else {
			model.addAttribute("curIndex", toIndex);
			if (null == isOld && toIndex == 2) {
				toIndex = 4;
			}
			//新尽调上一步
			if (null == isOld && toIndex == 3) {
				toIndex = 1;
			}
			switch (toIndex) {
				case 0:
					initBase0(baseInfo, model, request, false);
					break;
				case 1:
					initBase1(baseInfo, model, false);
					break;
				case 2:
					initBase2(baseInfo, model);
					break;
				case 3:
					initBase3(baseInfo, model);
					break;
				case 4:
					initBase4(baseInfo, model, request, subIndex, checkPoint);
					break;
				case 5:
					initBase5(baseInfo, model);
					break;
				case 6:
					initBase6(baseInfo, model, request);
					break;
				case 7:
					initBase7(baseInfo, model, request);
					break;
				case 8:
					initBase8(baseInfo, model);
					break;
				default:
					break;
			}
			
			return VM_PATH + "viewBase.vm";
		}
	}
	
	/**
	 * 
	 * 诉讼保函为空时，初始化数据
	 * 
	 * @param baseInfo
	 * @return
	 */
	protected FInvestigationLitigationInfo initLitigation(FInvestigationInfo baseInfo) {
		FInvestigationLitigationInfo litigation = new FInvestigationLitigationInfo();
		litigation.setFormId(baseInfo.getFormId());
		litigation.setProjectCode(baseInfo.getProjectCode());
		litigation.setProjectName(baseInfo.getProjectName());
		litigation.setCustomerId(baseInfo.getCustomerId());
		litigation.setCustomerName(baseInfo.getCustomerName());
		litigation.setInvestigatePersion(baseInfo.getInvestigatePersion());
		litigation.setAmount(baseInfo.getAmount());
		//litigation.setGuaranteeAmount(baseInfo.getAmount());
		
		FProjectLgLitigationInfo projectLitigation = projectSetupServiceClient
			.queryLgLitigationProjectByCode(baseInfo.getProjectCode());
		if (null != projectLitigation) {
			// 根据单位来处理
			litigation.setGuaranteeFee(projectLitigation.getGuaranteeFee());
			litigation.setGuaranteeType(projectLitigation.getGuaranteeFeeType());
			litigation.setCoInstitutionId(projectLitigation.getCoInstitutionId());
			litigation.setCoInstitutionName(projectLitigation.getCoInstitutionName());
			litigation.setCourt(projectLitigation.getCourt());
			litigation.setDeposit(projectLitigation.getDeposit());
			litigation.setDepositType(projectLitigation.getDepositType());
			litigation.setAmount(projectLitigation.getAmount());
		}
		
		FProjectCustomerBaseInfo customerBaseInfo = projectSetupServiceClient
			.queryCustomerBaseInfoByProjectCode(baseInfo.getProjectCode());
		if (null != customerBaseInfo) {
			if (customerBaseInfo.getCustomerType() == CustomerTypeEnum.PERSIONAL) {
				if (StringUtil.isNotBlank(customerBaseInfo.getCustomerName())) {
					litigation.setContactPerson(customerBaseInfo.getCustomerName());
				}
			} else {
				if (StringUtil.isNotBlank(customerBaseInfo.getContactMan())) {
					litigation.setContactPerson(customerBaseInfo.getContactMan());
				}
			}
			if (StringUtil.isNotBlank(customerBaseInfo.getContactNo())) {
				litigation.setContactNo(customerBaseInfo.getContactNo());
			}
		}
		
		return litigation;
	}
	
	/**
	 * 
	 * 当承销项目信息为空时，初始化数据
	 * 
	 * @param baseInfo
	 * @return
	 */
	protected FInvestigationUnderwritingInfo initUnderwriting(FInvestigationInfo baseInfo) {
		FInvestigationUnderwritingInfo underwriting = new FInvestigationUnderwritingInfo();
		
		underwriting.setFormId(baseInfo.getFormId());
		underwriting.setProjectCode(baseInfo.getProjectCode());
		underwriting.setProjectName(baseInfo.getProjectName());
		underwriting.setCustomerId(baseInfo.getCustomerId());
		underwriting.setCustomerName(baseInfo.getCustomerName());
		underwriting.setFinancingAmount(baseInfo.getAmount());
		
		ProjectInfo project = projectServiceClient.queryByCode(baseInfo.getProjectCode(), false);
		if (null != project) {
			underwriting.setFinancingAmount(project.getAmount());
			underwriting.setTimeLimit(project.getTimeLimit());
			underwriting.setTimeUnit(project.getTimeUnit());
		}
		
		return underwriting;
	}
	
	protected FInvestigationCreditSchemeInfo initBase0(FInvestigationInfo baseInfo, Model model,
														HttpServletRequest request, boolean forEdit) {
		int toIndex = 0;
		
		FInvestigationCreditSchemeInfo base0 = investigationServiceClient
			.findInvestigationCreditSchemeByFormId(baseInfo.getFormId());
		model.addAttribute("toIndex", toIndex);
		ProjectInfo project = projectServiceClient.queryByCode(baseInfo.getProjectCode(), false);
		model.addAttribute("projectInfo", project);
		if (null == base0) {
			base0 = new FInvestigationCreditSchemeInfo();
			initBase(base0, baseInfo);
			base0.setBusiType(baseInfo.getBusiType());
			
			if (null != project) {
				base0.setCreditAmount(project.getAmount());
				base0.setTimeLimit(project.getTimeLimit());
				base0.setTimeUnit(project.getTimeUnit());
				
				//XXX 取客户系统的行业
				CustomerDetailInfo customerInfo = customerServiceClient.queryByUserId(project
					.getCustomerId());
				if (customerInfo != null) {
					if (StringUtil.isNotBlank(customerInfo.getIndustryCode())) {
						base0.setIndustryCode(customerInfo.getIndustryCode());
					}
					if (StringUtil.isNotBlank(customerInfo.getIndustryName())) {
						base0.setIndustryName(customerInfo.getIndustryName());
					}
				} else {
					base0.setIndustryCode(project.getIndustryCode());
					base0.setIndustryName(project.getIndustryName());
				}
			}
			
			FProjectGuaranteeEntrustedInfo guaranteeEntrustedInfo = projectSetupServiceClient
				.queryGuaranteeEntrustedProjectByCode(baseInfo.getProjectCode());
			if (null != guaranteeEntrustedInfo) {
				base0.setCapitalChannelId(guaranteeEntrustedInfo.getCapitalChannelId());
				base0.setCapitalChannelCode(guaranteeEntrustedInfo.getCapitalChannelCode());
				base0.setCapitalChannelName(guaranteeEntrustedInfo.getCapitalChannelName());
				base0.setCapitalChannelType(guaranteeEntrustedInfo.getCapitalChannelType());
				//二级渠道
				base0.setCapitalSubChannelId(guaranteeEntrustedInfo.getCapitalSubChannelId());
				base0.setCapitalSubChannelCode(guaranteeEntrustedInfo.getCapitalSubChannelCode());
				base0.setCapitalSubChannelName(guaranteeEntrustedInfo.getCapitalSubChannelName());
				base0.setCapitalSubChannelType(guaranteeEntrustedInfo.getCapitalSubChannelType());
				
				base0.setProjectChannelId(guaranteeEntrustedInfo.getProjectChannelId());
				base0.setProjectChannelCode(guaranteeEntrustedInfo.getProjectChannelCode());
				base0.setProjectChannelName(guaranteeEntrustedInfo.getProjectChannelName());
				base0.setProjectChannelType(guaranteeEntrustedInfo.getProjectChannelType());
				base0.setProjectSubChannelId(guaranteeEntrustedInfo.getProjectSubChannelId());
				base0.setProjectSubChannelCode(guaranteeEntrustedInfo.getProjectSubChannelCode());
				base0.setProjectSubChannelName(guaranteeEntrustedInfo.getProjectSubChannelName());
				base0.setProjectSubChannelType(guaranteeEntrustedInfo.getProjectSubChannelType());
				base0.setCapitalChannel(guaranteeEntrustedInfo.getCapitalChannels());
				
				base0.setLoanPurpose(guaranteeEntrustedInfo.getLoanPurpose());
				
				base0.setDeposit(guaranteeEntrustedInfo.getDeposit());
				base0.setDepositType(guaranteeEntrustedInfo.getDepositType());
				base0.setDepositAccount(guaranteeEntrustedInfo.getDepositAccount());
				//从立项中带附件过来
				queryCommonAttachmentData(model, guaranteeEntrustedInfo.getFormId() + "",
					CommonAttachmentTypeEnum.COUNTER_GUARANTEE);
			}
			//从立项中带过来2016-07-21
			FProjectInfo fproject = projectSetupServiceClient.queryProjectByCode(baseInfo
				.getProjectCode());
			if (null != fproject) {
				base0.setOther(fproject.getOtherCounterGuarntee());
			}
		} else {
			base0.setBusiTypeName(baseInfo.getBusiTypeName());
			//附件2016-07-21
			queryCommonAttachmentData(model, baseInfo.getFormId() + "",
				CommonAttachmentTypeEnum.COUNTER_GUARANTEE);
			
			queryCommonAttachmentData(model, baseInfo.getFormId() + "_index" + toIndex,
				CommonAttachmentTypeEnum.FORM_ATTACH);
			
			if (forEdit) {
				// 取客户系统的行业
				CustomerDetailInfo customerInfo = customerServiceClient.queryByUserId(project
					.getCustomerId());
				if (customerInfo != null) {
					if (StringUtil.isNotBlank(customerInfo.getIndustryCode())) {
						base0.setIndustryCode(customerInfo.getIndustryCode());
					}
					if (StringUtil.isNotBlank(customerInfo.getIndustryName())) {
						base0.setIndustryName(customerInfo.getIndustryName());
					}
				}
			}
		}
		
		model.addAttribute("info", base0);
		if (null == request.getSession().getAttribute("version")) {
			FInvestigationRiskInfo fInvestigationRiskInfo = investigationServiceClient.findFInvestigationRiskByFormId(baseInfo.getFormId());
			model.addAttribute("riskInfo", fInvestigationRiskInfo);
			FInvestigationCsRationalityReviewInfo fInvestigationCsRationalityReviewInfo = investigationServiceClient.findFInvestigationCsRationalityReviewByFormId(baseInfo.getFormId());
			model.addAttribute("reviewInfo", fInvestigationCsRationalityReviewInfo);
		}
		
		//		double riskCoverRate = null == base0 ? 0d : getRiskCoverRate(base0.getFormId());
		double riskCoverRate = null == base0 ? 0d : base0.getRiskRate();
		model.addAttribute("riskCoverRate", riskCoverRate);
		//是否填写了资产信息
		if (null != base0
			&& (ListUtil.isNotEmpty(base0.getPledges()) || ListUtil
				.isNotEmpty(base0.getMortgages()))) {
			model.addAttribute("hasAsset", 1);
		} else {
			model.addAttribute("hasAsset", 0);
			
		}
		// 收费时间
		model.addAttribute("chargePhaseList", ChargePhaseEnum.getAllEnum());
		// 收费方式
		model.addAttribute("chargeWayList", ChargeWayEnum.getAllEnum());
		// 押品类型
		model.addAttribute("pledgeTypeList", PledgeTypeEnum.getAllEnum());
		// 押品性质
		model.addAttribute("pledgePropertyList", PledgePropertyEnum.getAllEnum());
		// 评级
		model.addAttribute("levelList", GradeLevelEnum.getAllEnum());
		// 评级列表
		model.addAttribute("creditLevelList", CreditLevelEnum.getAllEnum());
		// 评级调整方式
		model.addAttribute("adjustTypeList", AdjustTypeEnum.getAllEnum());
		// 评级
		model.addAttribute("updownList", UpAndDownEnum.getAllEnum());
		// 保证类型
		model.addAttribute("guarantorTypeList", GuarantorTypeEnum.getAllEnum());
		
//		model.addAttribute("types", pledgeTypeServiceClient.queryAll().getPageList());
		
		String assetId = request.getParameter("aid");
		long aid = NumberUtil.parseLong(assetId, 0L);
		if (aid > 0) {
			PledgeAssetInfo info = pledgeAssetServiceClient.findById(aid);
			PledgeTypeCommonInfo commonInfo = pledgeAssetServiceClient
				.findAssetCommonByAssetsId(aid);
			FInvestigationCreditSchemePledgeAssetInfo asset = new FInvestigationCreditSchemePledgeAssetInfo();
			if (null != info) {
				asset.setAssetsId(aid);
				asset.setAssetType(info.getAssetType());
				asset.setOwnershipName(info.getOwnershipName());
				asset.setEvaluationPrice(info.getEvaluationPrice());
			}
			if (null != commonInfo) {
				asset.setPledgeRate(commonInfo.getPledgeRate());
			}
			
			if (null != base0.getPledges()) {
				base0.getPledges().add(asset);
			}
			
			model.addAttribute("itype", request.getParameter("itype"));
		}
		
		String[] exclude = { "formId", "projectCode", "projectName", "customerId", "busiTypeName",
							"schemeId", "rawAddTime", "rawUpdateTime", "id", "typeDesc",
							"sortOrder", "bizNo", "phases", "channelRelation", "channelId",
							"channelType", "subChannelId", "subChannelType" };
		/*if (baseInfo.getNewFormId() > 0 && isCompare()) { //复议|上会退回
			FInvestigationCreditSchemeInfo base0His = investigationServiceClient
				.findInvestigationCreditSchemeByFormId(baseInfo.getNewFormId());
			Map<String, String> map = MiscUtil.comparePo(base0His, base0, exclude);
			if (null == map) {
				map = new HashMap<>();
			}
			double riskCoverRateHis = null == base0His ? 0d
				: base0His.getRiskRate();
			if (!StringUtil.equals(riskCoverRate + "", riskCoverRateHis + "")) {
				map.put("riskCoverRate", riskCoverRateHis + "");
			}
			
			//比较附件
			compareAttachment(model, map, baseInfo.getNewFormId(), "",
				CommonAttachmentTypeEnum.COUNTER_GUARANTEE);
			compareAttachment(model, map, baseInfo.getNewFormId(), "_index" + toIndex,
				CommonAttachmentTypeEnum.FORM_ATTACH);
			
			model.addAttribute("cmp", map);
		}*/
		
		//TODO map.put("riskCoverRate", riskCoverRateHis + "");
		addCmp("", model, toIndex, base0, baseInfo.getNewFormId(), exclude);
		
		return base0;
	}
	
	protected FInvestigationMainlyReviewInfo initBase1(FInvestigationInfo baseInfo, Model model,
														boolean forEdit) {
		int toIndex = 1;
		//		if (forEdit) {
		//			UpdateInvestigationCustomerOrder order = new UpdateInvestigationCustomerOrder();
		//			order.setFormId(baseInfo.getFormId());
		//			order.setProjectCode(baseInfo.getProjectCode());
		//			order.setToIndex(toIndex);
		//			updateCustomerInfo(order);
		//		}
		FInvestigationMainlyReviewInfo base1 = investigationServiceClient
			.findInvestigationMainlyReviewByFormId(baseInfo.getFormId());
		model.addAttribute("toIndex", toIndex);
		if (null == base1) {
			base1 = new FInvestigationMainlyReviewInfo();
			initBase(base1, baseInfo);
			
			// 查询客户信息
			CustomerDetailInfo customerInfo = customerServiceClient.queryByUserId(baseInfo
				.getCustomerId());
			if (customerInfo != null) {
				base1.setCustomerId(baseInfo.getCustomerId());
				if (null != customerInfo.getEstablishedTime()) {
					base1.setEstablishedTime(customerInfo.getEstablishedTime());
				}
				if (StringUtil.isNotBlank(customerInfo.getLegalPersion())) {
					base1.setLegalPersion(customerInfo.getLegalPersion());
				}
				if (StringUtil.isNotBlank(customerInfo.getLegalPersionAddress())) {
					base1.setLivingAddress(customerInfo.getLegalPersionAddress());
				}
				if (StringUtil.isNotBlank(customerInfo.getBusiLicenseNo())) {
					base1.setBusiLicenseNo(customerInfo.getBusiLicenseNo());
				}
				if (StringUtil.isNotBlank(customerInfo.getEnterpriseType())) {
					base1.setEnterpriseType(EnterpriseNatureEnum.getByCode(customerInfo
						.getEnterpriseType()));
				}
				if (StringUtil.isNotBlank(customerInfo.getAddress())) {
					base1.setWorkAddress(customerInfo.getAddress());
				}
				// 设置是否三证合一
				if (StringUtil.isNotBlank(customerInfo.getIsOneCert())) {
					base1.setIsOneCert(BooleanEnum.getByCode(customerInfo.getIsOneCert()));
				}
				if (StringUtil.isNotBlank(customerInfo.getOrgCode())) {
					base1.setOrgCode(customerInfo.getOrgCode());
				}
				if (StringUtil.isNotBlank(customerInfo.getTaxCertificateNo())) {
					base1.setTaxCertificateNo(customerInfo.getTaxCertificateNo());
				}
				if (StringUtil.isNotBlank(customerInfo.getLocalTaxCertNo())) {
					base1.setLocalTaxNo(customerInfo.getLocalTaxCertNo());
				}
				if (StringUtil.isNotBlank(customerInfo.getBusiScope())) {
					base1.setBusiScope(customerInfo.getBusiScope());
				}
				
				if (StringUtil.isNotBlank(customerInfo.getActualControlMan())) {
					base1.setActualControlPerson(customerInfo.getActualControlMan());
				}
				if (StringUtil.isNotBlank(customerInfo.getLoanCardNo())) {
					base1.setLoanCardNo(customerInfo.getLoanCardNo());
				}
				if (StringUtil.isNotBlank(customerInfo.getFinalYearCheck())) {
					base1.setLastCheckYear(customerInfo.getFinalYearCheck());
				}
				if (StringUtil.isNotBlank(customerInfo.getLegalPersionAddress())) {
					base1.setLivingAddress(customerInfo.getLegalPersionAddress());
				}
				
				//资质证书
				if (ListUtil.isNotEmpty(customerInfo.getCompanyQualification())) {
					List<FInvestigationMainlyReviewCertificateInfo> certificates = Lists
						.newArrayList();
					for (CompanyQualificationInfo c : customerInfo.getCompanyQualification()) {
						FInvestigationMainlyReviewCertificateInfo r = new FInvestigationMainlyReviewCertificateInfo();
						r.setCertificateCode(c.getQualificationCode());
						r.setCertificateName(c.getQualificationName());
						if (StringUtil.isNotBlank(c.getExperDate())) {
							r.setValidDate(DateUtil.parse(c.getExperDate()));
						}
						certificates.add(r);
					}
					base1.setCertificates(certificates);
				}
				
				//开户行信息
				List<FInvestigationMainlyReviewBankInfo> banks = Lists.newArrayList();
				//基本开户行
				FInvestigationMainlyReviewBankInfo bank = new FInvestigationMainlyReviewBankInfo();
				bank.setBankName(customerInfo.getBankAccount());
				bank.setBankDesc("基本账户开户行");
				bank.setAccountNo(customerInfo.getAccountNo());
				bank.setBasicFlag("YES");
				banks.add(bank);
				//开户行1
				bank = new FInvestigationMainlyReviewBankInfo();
				bank.setBankName(customerInfo.getSettleBankAccount1());
				bank.setBankDesc("主要结算账户开户行1");
				bank.setAccountNo(customerInfo.getSettleAccountNo1());
				bank.setBasicFlag("NO");
				banks.add(bank);
				//开户行2
				bank = new FInvestigationMainlyReviewBankInfo();
				bank.setBankName(customerInfo.getSettleBankAccount2());
				bank.setBankDesc("主要结算账户开户行2");
				bank.setAccountNo(customerInfo.getSettleAccountNo2());
				bank.setBasicFlag("NO");
				banks.add(bank);
				//开户行3
				bank = new FInvestigationMainlyReviewBankInfo();
				bank.setBankName(customerInfo.getSettleBankAccount3());
				bank.setBankDesc("其他结算账户开户行");
				bank.setAccountNo(customerInfo.getSettleAccountNo3());
				bank.setBasicFlag("NO");
				banks.add(bank);
				base1.setBanks(banks);
			}
			
		} else {
			queryCommonAttachmentData(model, baseInfo.getFormId() + "_index" + toIndex,
				CommonAttachmentTypeEnum.FORM_ATTACH);
			queryCommonAttachmentData(model, baseInfo.getFormId()+"",
					CommonAttachmentTypeEnum.CREDIT_REPORT);
			
			if (forEdit) {
				// 查询客户信息
				CustomerDetailInfo customerInfo = customerServiceClient.queryByUserId(baseInfo
					.getCustomerId());
				if (customerInfo != null) {
					base1.setCustomerId(baseInfo.getCustomerId());
					if (null != customerInfo.getEstablishedTime()) {
						base1.setEstablishedTime(customerInfo.getEstablishedTime());
					}
					if (StringUtil.isNotBlank(customerInfo.getLegalPersion())) {
						base1.setLegalPersion(customerInfo.getLegalPersion());
					}
					if (StringUtil.isNotBlank(customerInfo.getLegalPersionAddress())) {
						base1.setLivingAddress(customerInfo.getLegalPersionAddress());
					}
					if (StringUtil.isNotBlank(customerInfo.getBusiLicenseNo())) {
						base1.setBusiLicenseNo(customerInfo.getBusiLicenseNo());
					}
					if (StringUtil.isNotBlank(customerInfo.getEnterpriseType())) {
						base1.setEnterpriseType(EnterpriseNatureEnum.getByCode(customerInfo
							.getEnterpriseType()));
					}
					if (StringUtil.isNotBlank(customerInfo.getAddress())) {
						base1.setWorkAddress(customerInfo.getAddress());
					}
					// 设置是否三证合一
					if (StringUtil.isNotBlank(customerInfo.getIsOneCert())) {
						base1.setIsOneCert(BooleanEnum.getByCode(customerInfo.getIsOneCert()));
					}
					if (StringUtil.isNotBlank(customerInfo.getOrgCode())) {
						base1.setOrgCode(customerInfo.getOrgCode());
					}
					if (StringUtil.isNotBlank(customerInfo.getTaxCertificateNo())) {
						base1.setTaxCertificateNo(customerInfo.getTaxCertificateNo());
					}
					if (StringUtil.isNotBlank(customerInfo.getLocalTaxCertNo())) {
						base1.setLocalTaxNo(customerInfo.getLocalTaxCertNo());
					}
					if (StringUtil.isNotBlank(customerInfo.getBusiScope())) {
						base1.setBusiScope(customerInfo.getBusiScope());
					}
					
					if (StringUtil.isNotBlank(customerInfo.getActualControlMan())) {
						base1.setActualControlPerson(customerInfo.getActualControlMan());
					}
					if (StringUtil.isNotBlank(customerInfo.getLoanCardNo())) {
						base1.setLoanCardNo(customerInfo.getLoanCardNo());
					}
					if (StringUtil.isNotBlank(customerInfo.getFinalYearCheck())) {
						base1.setLastCheckYear(customerInfo.getFinalYearCheck());
					}
					if (StringUtil.isNotBlank(customerInfo.getLegalPersionAddress())) {
						base1.setLivingAddress(customerInfo.getLegalPersionAddress());
					}
					
					//资质证书
					if (ListUtil.isNotEmpty(customerInfo.getCompanyQualification())) {
						List<FInvestigationMainlyReviewCertificateInfo> certificates = Lists
							.newArrayList();
						for (CompanyQualificationInfo c : customerInfo.getCompanyQualification()) {
							FInvestigationMainlyReviewCertificateInfo r = new FInvestigationMainlyReviewCertificateInfo();
							r.setCertificateCode(c.getQualificationCode());
							r.setCertificateName(c.getQualificationName());
							if (StringUtil.isNotBlank(c.getExperDate())) {
								r.setValidDate(DateUtil.parse(c.getExperDate()));
							}
								
							certificates.add(r);
						}
						base1.setCertificates(certificates);
					}
					
					//开户行信息
					//					List<FInvestigationMainlyReviewBankInfo> banks = Lists.newArrayList();
					//基本开户行
					FInvestigationMainlyReviewBankInfo bank = new FInvestigationMainlyReviewBankInfo();
					if (StringUtil.isNotBlank(customerInfo.getBankAccount())
						|| StringUtil.isNotBlank(customerInfo.getAccountNo())) {
						bank.setBankName(customerInfo.getBankAccount());
						bank.setAccountNo(customerInfo.getAccountNo());
						bank.setBankDesc("基本账户开户行");
						bank.setBasicFlag("YES");
						if (base1.getBanks().size() >= 1) {
							base1.getBanks().set(0, bank);
						}
						//						banks.add(bank);
					}
					
					//开户行1
					if (StringUtil.isNotBlank(customerInfo.getSettleBankAccount1())
						|| StringUtil.isNotBlank(customerInfo.getSettleAccountNo1())) {
						bank = new FInvestigationMainlyReviewBankInfo();
						bank.setBankName(customerInfo.getSettleBankAccount1());
						bank.setBankDesc("主要结算账户开户行1");
						bank.setAccountNo(customerInfo.getSettleAccountNo1());
						bank.setBasicFlag("NO");
						if (base1.getBanks().size() >= 2) {
							base1.getBanks().set(1, bank);
						}
						//						banks.add(bank);
					}
					//开户行2
					if (StringUtil.isNotBlank(customerInfo.getSettleBankAccount2())
						&& StringUtil.isNotBlank(customerInfo.getSettleAccountNo2())) {
						bank = new FInvestigationMainlyReviewBankInfo();
						bank.setBankName(customerInfo.getSettleBankAccount2());
						bank.setBankDesc("主要结算账户开户行2");
						bank.setAccountNo(customerInfo.getSettleAccountNo2());
						bank.setBasicFlag("NO");
						if (base1.getBanks().size() >= 3) {
							base1.getBanks().set(2, bank);
						}
						//						banks.add(bank);
					}
					//开户行3
					if (StringUtil.isNotBlank(customerInfo.getSettleBankAccount3())
						|| StringUtil.isNotBlank(customerInfo.getSettleAccountNo3())) {
						bank = new FInvestigationMainlyReviewBankInfo();
						bank.setBankName(customerInfo.getSettleBankAccount3());
						bank.setBankDesc("其他结算账户开户行");
						bank.setAccountNo(customerInfo.getSettleAccountNo3());
						bank.setBasicFlag("NO");
						if (base1.getBanks().size() >= 4) {
							base1.getBanks().set(3, bank);
						}
						//						banks.add(bank);
						//						base1.setBanks(banks);
					}
				}
			}
		}
		
		model.addAttribute("info", base1);
		//是否填写了资产信息
		model.addAttribute("hasAsset", hasAsset(baseInfo.getFormId()));
		
		model.addAttribute("enterprises", EnterpriseNatureEnum.getAllEnum());
		//企业类型
		model.addAttribute("enterTypeList", EnterpriseNatureEnum.getAllEnum());
		//生成最近10年
		model.addAttribute("years", getYears());
		//股东类型
		model.addAttribute("typeList", HolderTypeEnum.getAllEnum());
		//网站状态
		model.addAttribute("statusList", SiteStatusEnum.getAllEnum());
		//与企业的关系
		model.addAttribute("relationList", EnterpriseRelationEnum.getAllEnum());
		
		//查询征信报告
		CreditRefrerenceApplyQueryOrder order = new CreditRefrerenceApplyQueryOrder();
		order.setCompanyName(base1.getCustomerName());
		order.setBusiLicenseNo(base1.getBusiLicenseNo());
		order.setStatus(FormStatusEnum.APPROVAL.code());
		QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo> batchResult = creditRefrerenceApplyServiceClient
			.queryCreditRefrerenceApply(order);
		boolean applied = false;
		boolean hasReport = false;
		if (ListUtil.isNotEmpty(batchResult.getPageList())) {
			applied = true;
			for (CreditRefrerenceApplyReusltInfo credit : batchResult.getPageList()) {
				if (StringUtil.isNotBlank(credit.getCreditReport())) {
					hasReport = true;
					model.addAttribute("reportFormId", credit.getFormId());
					break;
				}
			}
		}
		model.addAttribute("applied", applied);
		model.addAttribute("hasReport", hasReport);
		
		String[] exclude = { "formId", "projectCode", "projectName", "customerId", "busiTypeName",
							"MReviewId", "rawAddTime", "rawUpdateTime", "id", "basicFlag",
							"delAble", "sortOrder", "riskRate" };
		/*if (baseInfo.getNewFormId() > 0 && isCompare()) { //复议|上会退回
			FInvestigationMainlyReviewInfo base1His = investigationServiceClient
				.findInvestigationMainlyReviewByFormId(baseInfo.getNewFormId());
			Map<String, String> map = MiscUtil.comparePo(base1His, base1, exclude);
			//比较附件
			compareAttachment(model, map, baseInfo.getNewFormId(), "_index" + toIndex,
				CommonAttachmentTypeEnum.FORM_ATTACH);
			
			model.addAttribute("cmp", map);
			//			System.out.println(toIndex + "--------------------:" + map);
		}*/
		
		addCmp("", model, toIndex, base1, baseInfo.getNewFormId(), exclude);
		
		return base1;
	}
	
	/**
	 * 比较附件
	 * @param model
	 * @param map
	 * @param formId
	 * @param suffix
	 * @param type
	 */
	private void compareAttachment(Model model, Map<String, String> map, long formId,
									String suffix, CommonAttachmentTypeEnum type) {
		Object url1 = model.asMap().get("hiddenUrls_" + type.code());
		String urlNew = (null == url1) ? "" : url1.toString();
		String urlOld = queryCommonAttachmentUrls(formId + suffix, type, "");
		if (StringUtil.notEquals(urlNew, urlOld)) {
			if (null == map) {
				map = new HashMap<>();
			}
			map.put("hiddenUrls_" + type.code(), urlOld);
		}
	}
	
	protected FInvestigationMabilityReviewInfo initBase2(FInvestigationInfo baseInfo, Model model) {
		FInvestigationMabilityReviewInfo base2 = investigationServiceClient
			.findFInvestigationMabilityReviewByFormId(baseInfo.getFormId());
		int toIndex = 2;
		model.addAttribute("toIndex", toIndex);
		if (null == base2) {
			base2 = new FInvestigationMabilityReviewInfo();
			initBase(base2, baseInfo);
		} else {
			queryCommonAttachmentData(model, baseInfo.getFormId() + "_index" + toIndex,
				CommonAttachmentTypeEnum.FORM_ATTACH);
		}
		
		model.addAttribute("info", base2);
		//是否填写了资产信息
		model.addAttribute("hasAsset", hasAsset(baseInfo.getFormId()));
		
		String[] exclude = { "formId", "projectCode", "projectName", "customerId", "busiTypeName",
							"maReviewId", "rawAddTime", "rawUpdateTime", "id", "tid", "sortOrder",
							"riskRate" };
		/*if (baseInfo.getNewFormId() > 0 && isCompare()) { //复议|上会退回
			FInvestigationMabilityReviewInfo base2His = investigationServiceClient
				.findFInvestigationMabilityReviewByFormId(baseInfo.getNewFormId());
			Map<String, String> map = MiscUtil.comparePo(base2His, base2, exclude);
			//比较附件
			compareAttachment(model, map, baseInfo.getNewFormId(), "_index" + toIndex,
				CommonAttachmentTypeEnum.FORM_ATTACH);
			
			model.addAttribute("cmp", map);
		}*/
		
		addCmp("", model, toIndex, base2, baseInfo.getNewFormId(), exclude);
		
		return base2;
	}
	
	protected FInvestigationOpabilityReviewInfo initBase3(FInvestigationInfo baseInfo, Model model) {
		FInvestigationOpabilityReviewInfo base3 = investigationServiceClient
			.findFInvestigationOpabilityReviewByFormId(baseInfo.getFormId());
		int toIndex = 3;
		model.addAttribute("toIndex", toIndex);
		if (null == base3) {
			base3 = new FInvestigationOpabilityReviewInfo();
			initBase(base3, baseInfo);
		} else {
			queryCommonAttachmentData(model, baseInfo.getFormId() + "_index" + toIndex,
				CommonAttachmentTypeEnum.FORM_ATTACH);
		}
		
		model.addAttribute("info", base3);
		//是否填写了资产信息
		model.addAttribute("hasAsset", hasAsset(baseInfo.getFormId()));
		
		String[] exclude = { "formId", "projectCode", "projectName", "customerId", "busiTypeName",
							"opReviewId", "rawAddTime", "rawUpdateTime", "id", "sortOrder",
							"riskRate" };
		/*if (baseInfo.getNewFormId() > 0 && isCompare()) { //复议|上会退回
			FInvestigationOpabilityReviewInfo base3His = investigationServiceClient
				.findFInvestigationOpabilityReviewByFormId(baseInfo.getNewFormId());
			Map<String, String> map = MiscUtil.comparePo(base3His, base3, exclude);
			//比较附件
			compareAttachment(model, map, baseInfo.getNewFormId(), "_index" + toIndex,
				CommonAttachmentTypeEnum.FORM_ATTACH);
			
			model.addAttribute("cmp", map);
		}*/
		
		addCmp("", model, toIndex, base3, baseInfo.getNewFormId(), exclude);
		return base3;
	}
	
	protected FInvestigationFinancialReviewInfo initBase4(FInvestigationInfo baseInfo, Model model,
															HttpServletRequest request,
															Integer subIndex, String checkPoint) {
		FInvestigationFinancialReviewInfo base4 = null;
		if (StringUtil.isNotBlank(checkPoint)) {
			base4 = investigationServiceClient.findFInvestigationFinancialReviewByFormId(baseInfo
				.getFormId());
		} else {
			if (null != subIndex) {
				base4 = investigationServiceClient
					.findFInvestigationFinancialReviewByFormIdAndSubindex(baseInfo.getFormId(),
						subIndex);
			} else {
				base4 = investigationServiceClient
					.findFInvestigationFinancialReviewByFormId(baseInfo.getFormId());
			}
		}
		int toIndex = 4;
		model.addAttribute("toIndex", toIndex);
		if (null == base4) {
			//			base4 = new FInvestigationFinancialReviewInfo();
			//			initBase(base4, baseInfo);
			FInvestigationFinancialReviewOrder order = new FInvestigationFinancialReviewOrder();
			order.setInit(true);
			order.setFormId(baseInfo.getFormId());
			order.setProjectCode(baseInfo.getProjectCode());
			order.setProjectName(baseInfo.getProjectName());
			order.setCustomerId(baseInfo.getCustomerId());
			order.setCustomerName(baseInfo.getCustomerName());
			order.setFormCode(FormCodeEnum.INVESTIGATION_BASE);
			order.setCheckIndex(5);
			setSessionLocalInfo2Order(order);
			investigationServiceClient.saveFInvestigationFinancialReview(order);
			base4 = investigationServiceClient.findFInvestigationFinancialReviewByFormId(baseInfo
				.getFormId());
		} else {
			String suffix = "";
			if (base4.getSubIndex() == 1) {
				suffix = "_" + base4.getSubIndex();
			}
			queryCommonAttachmentData(model, baseInfo.getFormId() + "_index" + toIndex + suffix,
				CommonAttachmentTypeEnum.FORM_ATTACH);
			queryCommonAttachmentData(model, baseInfo.getFormId() + suffix,
				CommonAttachmentTypeEnum.INVESTIGATION_FINANCIAL);
		}
		baseInfo.setSubIndex(base4.getSubIndex());
		
		model.addAttribute("info", base4);
		//是否填写了资产信息
		model.addAttribute("hasAsset", hasAsset(baseInfo.getFormId()));
		// 获取财务报表
		getFinancialTemplate(request, baseInfo.getProjectCode(), model);
		//审计意见
		model.addAttribute("auditOpinionList", AuditOpinionEnum.getAllEnum());
		
		addKpiClass(base4);
		
		//审计意见
		model.addAttribute("auditOpinionList", AuditOpinionEnum.getAllEnum());
		
		String[] years = (String[]) model.asMap().get("years");
		if (null != years && null != base4) {
			int i = 0;
			for (FInvestigationFinancialReviewKpiInfo kpiInfo : base4.getAuditInfos()) {
				if (StringUtil.isEmpty(kpiInfo.getTermType())) {
					kpiInfo.setTermType(years[i++]);
				} else {
					years[i++] = kpiInfo.getTermType();
				}
			}
		}
		
		String[] exclude = { "formId", "projectCode", "projectName", "customerId", "busiTypeName",
							"FReviewId", "rawAddTime", "rawUpdateTime", "id", "parentId",
							"kpiClass", "kpiLevel", "kpiCode", "kpiUnit", "errorMes", "errorMes1",
							"errorMes2", "errorMes3", "sortOrder", "riskRate" };
		/*if (baseInfo.getNewFormId() > 0 && isCompare()) { //复议|上会退回
			FInvestigationFinancialReviewInfo base4His = investigationServiceClient
				.findFInvestigationFinancialReviewByFormId(baseInfo.getNewFormId());
			Map<String, String> map = MiscUtil.comparePo(base4His, base4, exclude);
			//比较附件
			compareAttachment(model, map, baseInfo.getNewFormId(), "_index" + toIndex,
				CommonAttachmentTypeEnum.FORM_ATTACH);
			compareAttachment(model, map, baseInfo.getNewFormId(), "",
				CommonAttachmentTypeEnum.INVESTIGATION_FINANCIAL);
			
			model.addAttribute("cmp", map);
			//			System.out.println(toIndex + "=========" + map);
		}*/
		
		addCmp("", model, toIndex, base4, baseInfo.getNewFormId(), exclude);
		return base4;
	}
	
	protected FInvestigationMajorEventInfo initBase5(FInvestigationInfo baseInfo, Model model) {
		FInvestigationMajorEventInfo base5 = investigationServiceClient
			.findFInvestigationMajorEventByFormId(baseInfo.getFormId());
		int toIndex = 5;
		model.addAttribute("toIndex", toIndex);
		if (null == base5) {
			base5 = new FInvestigationMajorEventInfo();
			initBase(base5, baseInfo);
		} else {
			queryCommonAttachmentData(model, baseInfo.getFormId() + "_index" + toIndex,
				CommonAttachmentTypeEnum.FORM_ATTACH);
		}
		
		model.addAttribute("info", base5);
		//是否填写了资产信息
		model.addAttribute("hasAsset", hasAsset(baseInfo.getFormId()));
		
		String[] exclude = { "formId", "projectCode", "projectName", "customerId", "busiTypeName",
							"FReviewId", "rawAddTime", "rawUpdateTime", "id", "sortOrder",
							"riskRate" };
		/*if (baseInfo.getNewFormId() > 0 && isCompare()) { //复议|上会退回
			FInvestigationMajorEventInfo base5His = investigationServiceClient
				.findFInvestigationMajorEventByFormId(baseInfo.getNewFormId());
			Map<String, String> map = MiscUtil.comparePo(base5His, base5, exclude);
			//比较附件
			compareAttachment(model, map, baseInfo.getNewFormId(), "_index" + toIndex,
				CommonAttachmentTypeEnum.FORM_ATTACH);
			
			model.addAttribute("cmp", map);
		}*/
		
		addCmp("", model, toIndex, base5, baseInfo.getNewFormId(), exclude);
		return base5;
	}
	
	protected FInvestigationProjectStatusInfo initBase6(FInvestigationInfo baseInfo, Model model,
														HttpServletRequest request) {
		FInvestigationProjectStatusInfo base6 = investigationServiceClient
			.findFInvestigationProjectStatusByFormId(baseInfo.getFormId());
		int toIndex = 6;
		model.addAttribute("toIndex", toIndex);
		if (null == base6) {
			base6 = new FInvestigationProjectStatusInfo();
			initBase(base6, baseInfo);
		} else {
			//附件列表(原列表修改)
			queryCommonAttachmentData(model, base6.getFormId() + "",
				CommonAttachmentTypeEnum.INVESTIGATION_6);
			queryCommonAttachmentData(model, baseInfo.getFormId() + "_index" + toIndex,
				CommonAttachmentTypeEnum.FORM_ATTACH);
		}
		
		//2016-09-18 这块内容去掉，下面添加附件上传
		/*if (null == base6 || ListUtil.isEmpty(base6.getFunds())) {
			String fileName = EXCEL_MAP.get("project_fund"); //项目投资与资金筹措表
			if (StringUtil.isNotBlank(fileName)) {
				String filePath = request.getServletContext().getRealPath("/WEB-INF/")
									+ EXCEL_MODEL_PATH + fileName + ".xls";
				try {
					ExcelData excel = ExcelUtil.parseExcel(filePath);
					if (null != excel && excel.getDatas() != null) {
						model.addAttribute("funds", excel.getDatas());
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}*/
		
		model.addAttribute("info", base6);
		//是否填写了资产信息
		model.addAttribute("hasAsset", hasAsset(baseInfo.getFormId()));
		
		String[] exclude = { "formId", "projectCode", "projectName", "customerId", "busiTypeName",
							"statusId", "rawAddTime", "rawUpdateTime", "id", "sortOrder",
							"riskRate" };
		/*if (baseInfo.getNewFormId() > 0 && isCompare()) { //复议|上会退回
			FInvestigationProjectStatusInfo base6His = investigationServiceClient
				.findFInvestigationProjectStatusByFormId(baseInfo.getNewFormId());
			if (null == base6His) {
				base6His = new FInvestigationProjectStatusInfo();
				base6His.setFormId(baseInfo.getNewFormId());
			}
			Map<String, String> map = MiscUtil.comparePo(base6His, base6, exclude);
			//比较附件
			compareAttachment(model, map, baseInfo.getNewFormId(), "",
				CommonAttachmentTypeEnum.INVESTIGATION_6);
			compareAttachment(model, map, baseInfo.getNewFormId(), "_index" + toIndex,
				CommonAttachmentTypeEnum.FORM_ATTACH);
			
			model.addAttribute("cmp", map);
		}*/
		
		addCmp("", model, toIndex, base6, baseInfo.getNewFormId(), exclude);
		return base6;
	}
	
	protected FInvestigationCsRationalityReviewInfo initBase7(FInvestigationInfo baseInfo,
																Model model,
																HttpServletRequest request) {
		FInvestigationCsRationalityReviewInfo base7 = investigationServiceClient
			.findFInvestigationCsRationalityReviewByFormId(baseInfo.getFormId());
		int toIndex = 7;
		model.addAttribute("toIndex", toIndex);
		if (null == base7) {
			base7 = new FInvestigationCsRationalityReviewInfo();
			initBase(base7, baseInfo);
		} else {
			queryCommonAttachmentData(model, baseInfo.getFormId() + "_index" + toIndex,
				CommonAttachmentTypeEnum.FORM_ATTACH);
		}
		//  财务指标表
		//		if (ListUtil.isEmpty(base7.getKpies())) {
		//			//保证人主要财务指标
		ExcelData excel = findExcelModel(request, "financial_index_d");
		if (null != excel && excel.getDatas() != null) {
			model.addAttribute("kpies", excel.getDatas());
		}
		//		}
		
		model.addAttribute("info", base7);
		//是否填写了资产信息
		model.addAttribute("hasAsset", hasAsset(baseInfo.getFormId()));
		//企业类型
		model.addAttribute("typeList", EnterpriseNatureEnum.getAllEnum());
		//生成最近10年
		model.addAttribute("years", getYears());
		
		FInvestigationCreditSchemeInfo base0 = investigationServiceClient
			.findInvestigationCreditSchemeByFormId(baseInfo.getFormId());
		if (null != base0 && ListUtil.isNotEmpty(base0.getGuarantors())) {
			model.addAttribute("hasGuarantor", BooleanEnum.YES.code());
		}
		
		String[] exclude = { "formId", "projectCode", "projectName", "customerId", "busiTypeName",
							"csrReviewId", "rawAddTime", "rawUpdateTime", "id", "MReviewId", "tid",
							"maReviewId", "itemCode", "guarantorId", "kpiId", "parentId",
							"sortOrder", "riskRate" };
		/*if (baseInfo.getNewFormId() > 0 && isCompare()) { //复议|上会退回
			FInvestigationCsRationalityReviewInfo base7His = investigationServiceClient
				.findFInvestigationCsRationalityReviewByFormId(baseInfo.getNewFormId());
			// 此处需要特殊处理
			//Object对象，需要特殊处理的，也可以抽象一下
			Map<String, String> map = MiscUtil.comparePo(base7His, base7, exclude);
			//比较附件
			compareAttachment(model, map, baseInfo.getNewFormId(), "_index" + toIndex,
				CommonAttachmentTypeEnum.FORM_ATTACH);
			
			model.addAttribute("cmp", map);
		}*/
		
		addCmp("", model, toIndex, base7, baseInfo.getNewFormId(), exclude);
		
		return base7;
	}
	
	protected FInvestigationRiskInfo initBase8(FInvestigationInfo baseInfo, Model model) {
		FInvestigationRiskInfo base8 = investigationServiceClient
			.findFInvestigationRiskByFormId(baseInfo.getFormId());
		int toIndex = 8;
		model.addAttribute("toIndex", toIndex);
		if (null == base8) {
			base8 = new FInvestigationRiskInfo();
			initBase(base8, baseInfo);
		} else {
			queryCommonAttachmentData(model, baseInfo.getFormId() + "_index" + toIndex,
				CommonAttachmentTypeEnum.FORM_ATTACH);
		}
		
		model.addAttribute("info", base8);
		//是否填写了资产信息
		model.addAttribute("hasAsset", hasAsset(baseInfo.getFormId()));
		
		String[] exclude = { "formId", "projectCode", "projectName", "customerId", "busiTypeName",
							"riskId", "rawAddTime", "rawUpdateTime", "id", "sortOrder", "riskRate" };
		/*if (baseInfo.getNewFormId() > 0 && isCompare()) { //复议|上会退回
			FInvestigationRiskInfo base8His = investigationServiceClient
				.findFInvestigationRiskByFormId(baseInfo.getNewFormId());
			Map<String, String> map = MiscUtil.comparePo(base8His, base8, exclude);
			//比较附件
			compareAttachment(model, map, baseInfo.getNewFormId(), "_index" + toIndex,
				CommonAttachmentTypeEnum.FORM_ATTACH);
			
			model.addAttribute("cmp", map);
		}*/
		
		addCmp("", model, toIndex, base8, baseInfo.getNewFormId(), exclude);
		return base8;
	}
	
	protected void initBase(SimpleFormProjectInfo base, FInvestigationInfo baseInfo) {
		if (null != base && null != baseInfo) {
			base.setFormId(baseInfo.getFormId());
			base.setProjectCode(baseInfo.getProjectCode());
			base.setProjectName(baseInfo.getProjectName());
			base.setCustomerId(baseInfo.getCustomerId());
			base.setCustomerName(baseInfo.getCustomerName());
			base.setBusiTypeName(baseInfo.getBusiTypeName());
			base.setAmount(baseInfo.getAmount());
		}
	}
	
	// 保存诉讼担保项目调查报告
	@ResponseBody
	@RequestMapping("saveFInvestigationLitigationSubmit.htm")
	public Object saveFInvestigationLitigationSubmit(HttpSession session, String token,
														HttpServletRequest request,
														FInvestigationLitigationOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		if (isRepeatSubmit(json, session, token)) {
			return json;
		}
		
		String tipPrefix = "保存诉讼保函项目调查报告";
		try {
			String url = getNextUrl(order.getToIndex());
			//			if (order.noNeedSave()) {
			//				return toUrl(tipPrefix, url, order);
			//			}
			order.setFormCode(FormCodeEnum.INVESTIGATION_LITIGATION);
			order.setCheckIndex(1);
			//			order.setCheckStatus(1);
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FormBaseResult result = investigationServiceClient.saveFInvestigationLitigation(order);
			json = toJSONResult(result, tipPrefix, order.getToIndex(), url, session);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	// 保存承销项目情况
	@ResponseBody
	@RequestMapping("saveFInvestigationUnderwritingSubmit.htm")
	public Object saveFInvestigationUnderwritingSubmit(HttpSession session, String token,
														HttpServletRequest request,
														FInvestigationUnderwritingOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		if (isRepeatSubmit(json, session, token)) {
			return json;
		}
		
		String tipPrefix = "保存承销项目情况";
		try {
			String url = getNextUrl(order.getToIndex());
			//			if (order.noNeedSave()) {
			//				return toUrl(tipPrefix, url, order);
			//			}
			order.setFormCode(FormCodeEnum.INVESTIGATION_UNDERWRITING);
			order.setCheckIndex(1);
			//			order.setCheckStatus(1); //没有暂存，直接提交
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FormBaseResult result = investigationServiceClient
				.saveFInvestigationUnderwriting(order);
			if (result.isSuccess()) {
				long keyId = result.getKeyId();
				//添加附件
				addAttachfile(result.getFormInfo().getFormId() + "_INVESTIGATION1", request,"", "尽调-承销项目情况附件",
						CommonAttachmentTypeEnum.INVESTIGATION_INNOVATIVE_PRODUCT);
				addAttachfile(keyId + "", request, order.getProjectCode(), "尽调-承销项目情况附件",
					CommonAttachmentTypeEnum.UNDERWRITING_PROJECT);
			}
			json = toJSONResult(result, tipPrefix, order.getToIndex(), url, session);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	/**
	 * 试算风险覆盖率
	 * @param formId 项目编号
	 * @return
	 */
	@RequestMapping("calculateRiskCoverRate.json")
	@ResponseBody
	public JSONObject calculateRiskCoverRate(long formId) {
		String tipPrefix = "试算风险覆盖率";
		JSONObject result = new JSONObject();
		try {
			FInvestigationCreditSchemeInfo info = investigationServiceClient
				.findInvestigationCreditSchemeByFormId(formId);
			if (null == info || !info.getCreditAmount().greaterThan(Money.zero())) {
				result.put("success", false);
				result.put("message", "无法试算风险覆盖率");
				return result;
			}
			
			//风险覆盖率
			double d = getRiskCoverRate(formId);
			//预设的风险覆盖率
			double settingValue = findRiskCoverRate(info.getCustomerId());
			
			result.put("success", true);
			result.put("message", "该客户的风险覆盖率为" + RateUtil.formatRate(d) + "%");
			BigDecimal b1 = new BigDecimal(d);
			BigDecimal b2 = new BigDecimal(settingValue);
			int r = b1.compareTo(b2);
			if (r > 0) {
				result.put("message1", "高于公司设定的风险覆盖率阀值" + RateUtil.formatRate(settingValue) + "%");
			} else if (r == 0) {
				result.put("message1", "等于公司设定的风险覆盖率阀值" + RateUtil.formatRate(settingValue) + "%");
			} else {
				result.put("message1", "低于公司设定的风险覆盖率阀值" + RateUtil.formatRate(settingValue) + "%");
			}
			return result;
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
	}
	
	private double getRiskCoverRate(long formId) {
		FInvestigationCreditSchemeInfo info = investigationServiceClient
			.findInvestigationCreditSchemeByFormId(formId);
		if (null == info || !info.getCreditAmount().greaterThan(Money.zero())) {
			return 0d;
		}
		//清算值
		Money clearingAmount = new Money(0L);
		
		clearingAmount.addTo(info.getPledgePrice()).addTo(info.getMortgagePrice());
		//不计算保证人额度
		//		if (null != info.getTotaoGuaranteeAmount()) {
		//			clearingAmount.addTo(info.getTotaoGuaranteeAmount());
		//		}
		
		//风险覆盖率
		double d = (100d * clearingAmount.getCent()) / info.getCreditAmount().getCent();
		return d;
	}
	
	private int hasAsset(long formId) {
		FInvestigationCreditSchemeInfo info = investigationServiceClient
			.findInvestigationCreditSchemeByFormId(formId);
		if (null != info
			&& (ListUtil.isNotEmpty(info.getPledges()) || ListUtil.isNotEmpty(info.getMortgages()))) {
			return 1;
		}
		
		return 0;
	}
	
	/**
	 * 获取系统预设的风险覆盖率
	 * @return
	 */
	private double findRiskCoverRate(long customerId) {
		String rate = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_COVER_RATE.code());
		return NumberUtil.parseDouble(rate);
	}
	
	// 审核
	@RequestMapping("audit.htm")
	public String audit(HttpServletRequest request, Model model, long formId) {
		FormInfo form = formServiceClient.findByFormId(formId);
		if (null != form) {
			model.addAttribute("form", form);// 表单信息
			model.addAttribute("formCode", form.getFormCode());
			initWorkflow(model, form, request.getParameter("taskId"));
		}
		FInvestigationInfo info = investigationServiceClient.findInvestigationByFormId(formId);
		model.addAttribute("info", info);
		
		model.addAttribute("hasRiskReviewReport", hasRiskReviewReport(formId));
		//是否是诉保
		model.addAttribute(
			"isLitigation",
			ProjectUtil.isLitigation(info.getBusiType()) ? BooleanEnum.YES.code() : BooleanEnum.NO
				.code());
		//客户信息
		CustomerDetailInfo customerInfo = customerServiceClient.queryByUserId(info.getCustomerId());
		model.addAttribute("customerInfo", customerInfo);
		
		//		model.addAttribute("checkPoint", CheckPointEnum.VICE_PRESIDENT_BUSINESS.code());
		
		return VM_PATH + "audit.vm";
	}
	
	// 法务审核页面
	@RequestMapping("lawManagerAudit.htm")
	public String lawManagerAudit(HttpServletRequest request, Model model, long formId) {
		FormInfo form = formServiceClient.findByFormId(formId);
		if (null != form) {
			model.addAttribute("form", form);// 表单信息
			model.addAttribute("formCode", form.getFormCode());
			initWorkflow(model, form, request.getParameter("taskId"));
		}
		
		FInvestigationInfo baseInfo = investigationServiceClient.findInvestigationByFormId(formId);
		model.addAttribute("baseInfo", baseInfo);
		FInvestigationLitigationInfo info = investigationServiceClient
			.findFInvestigationLitigationByFormId(formId);
		model.addAttribute("info", info);
		
		return VM_PATH + "auditLawWorksLawsuit.vm";
	}
	
	// 承销项目尽职调查报告审核 - 是否上母公司会议
	@RequestMapping("auditCouncil.htm")
	public String auditCouncil(HttpServletRequest request, Model model, long formId) {
		FormInfo form = formServiceClient.findByFormId(formId);
		if (null != form) {
			model.addAttribute("form", form);// 表单信息
			model.addAttribute("formCode", form.getFormCode());
			initWorkflow(model, form, request.getParameter("taskId"));
		}
		
		FInvestigationInfo info = investigationServiceClient.findInvestigationByFormId(formId);
		model.addAttribute("info", info);
		
		model.addAttribute("hasRiskReviewReport", hasRiskReviewReport(formId));
		
		return VM_PATH + "auditCouncil.vm";
	}
	
	// 导出word
	@RequestMapping("toExportDoc.htm")
	public String toExportDoc(HttpServletRequest request, Model model, long formId) {
		FormInfo form = formServiceClient.findByFormId(formId);
		model.addAttribute("form", form);
		Map<String, Object> customizeFieldMap = form.getCustomizeFieldMap();
		Object zxCustomerjson = customizeFieldMap.get("zxCustomerjson");
		if (null != zxCustomerjson) {
			model.addAttribute("zxCustomerjson", JSONArray.parse(zxCustomerjson.toString()));
		}
		
		FInvestigationInfo baseInfo = investigationServiceClient.findInvestigationByFormId(formId);
		model.addAttribute("base", baseInfo);

		setAuditHistory2Page(form, model);

		if (null == baseInfo) {
			return EXPORT_VM_PATH + "exportError.vm";
		} else if (ProjectUtil.isLitigation(baseInfo.getBusiType())) {
			FInvestigationLitigationInfo litigation = investigationServiceClient
					.findFInvestigationLitigationByFormId(formId);
			model.addAttribute("litigation", litigation);
			return EXPORT_VM_PATH + "exprotInvestigationLitigation.vm";
		} else if (ProjectUtil.isUnderwriting(baseInfo.getBusiType())) {
			FInvestigationUnderwritingInfo underwriting = investigationServiceClient
					.findFInvestigationUnderwritingByFormId(formId);
			model.addAttribute("underwriting", underwriting);
			return EXPORT_VM_PATH + "exprotInvestigationUnderwriting.vm";
		} else if(ProjectUtil.isInnovativeProduct(baseInfo.getBusiType())){
			FInvestigationUnderwritingInfo underwriting = investigationServiceClient
					.findFInvestigationUnderwritingByFormId(formId);
			model.addAttribute("underwriting", underwriting);
			queryCommonAttachmentData(model, form.getFormId() + "_INVESTIGATION1",
					CommonAttachmentTypeEnum.INVESTIGATION_INNOVATIVE_PRODUCT);
			return EXPORT_VM_PATH + "exportInnovativeProduct.vm";
		}else {
			HttpSession session = request.getSession();
			boolean isOld = null == session.getAttribute("version") ? Boolean.FALSE : Boolean.TRUE;
			FInvestigationCreditSchemeInfo base0 = investigationServiceClient
					.findInvestigationCreditSchemeByFormId(baseInfo.getFormId());
			model.addAttribute("base0", base0);
			// double riskCoverRate = null == base0 ? 0d :
			// getRiskCoverRate(base0.getFormId());
			double riskCoverRate = null == base0 ? 0d : base0.getRiskRate();
			model.addAttribute("riskCoverRate", riskCoverRate);
			

			FInvestigationMainlyReviewInfo base1 = investigationServiceClient
					.findInvestigationMainlyReviewByFormId(baseInfo.getFormId());
			model.addAttribute("base1", base1);
			FInvestigationFinancialReviewInfo base4 = investigationServiceClient
					.findFInvestigationFinancialReviewByFormId(baseInfo.getFormId());
			model.addAttribute("base4", base4);
			// 审计意见
			model.addAttribute("auditOpinionList", AuditOpinionEnum.getAllEnum());

			if (isOld) {
				FInvestigationMabilityReviewInfo base2 = investigationServiceClient
						.findFInvestigationMabilityReviewByFormId(baseInfo.getFormId());
				model.addAttribute("base2", base2);
				FInvestigationOpabilityReviewInfo base3 = investigationServiceClient
						.findFInvestigationOpabilityReviewByFormId(baseInfo.getFormId());
				model.addAttribute("base3", base3);
				FInvestigationMajorEventInfo base5 = investigationServiceClient
						.findFInvestigationMajorEventByFormId(baseInfo.getFormId());
				model.addAttribute("base5", base5);
				FInvestigationProjectStatusInfo base6 = investigationServiceClient
						.findFInvestigationProjectStatusByFormId(baseInfo.getFormId());
				model.addAttribute("base6", base6);
				FInvestigationCsRationalityReviewInfo base7 = investigationServiceClient
						.findFInvestigationCsRationalityReviewByFormId(baseInfo.getFormId());
				model.addAttribute("base7", base7);
				// 企业类型
				model.addAttribute("typeList", EnterpriseNatureEnum.getAllEnum());

				FInvestigationRiskInfo base8 = investigationServiceClient
						.findFInvestigationRiskByFormId(baseInfo.getFormId());
				model.addAttribute("base8", base8);
			}

			if (!isOld) {
				FInvestigationRiskInfo fInvestigationRiskInfo = investigationServiceClient.findFInvestigationRiskByFormId(baseInfo.getFormId());
				model.addAttribute("riskInfo", fInvestigationRiskInfo);
				FInvestigationCsRationalityReviewInfo fInvestigationCsRationalityReviewInfo = investigationServiceClient.findFInvestigationCsRationalityReviewByFormId(baseInfo.getFormId());
				model.addAttribute("reviewInfo", fInvestigationCsRationalityReviewInfo);
				return EXPORT_VM_PATH + "newExprotInvestigationBase.vm";
			}
			return EXPORT_VM_PATH + "exprotInvestigationBase.vm";
		}

	}
	
	private void auditCommon(HttpServletRequest request, Model model, long formId,
								String checkPoint, int from) {
		FormInfo form = formServiceClient.findByFormId(formId);
		if (null != form) {
			model.addAttribute("form", form);// 表单信息
			model.addAttribute("formCode", form.getFormCode());
			String taskId = request.getParameter("taskId");
			initWorkflow(model, form, StringUtil.isBlank(taskId) ? form.getTaskId() + "" : taskId);
		}
		FInvestigationInfo info = investigationServiceClient.findInvestigationByFormId(formId);
		model.addAttribute("info", info);
		
		model.addAttribute("hasRiskReviewReport", hasRiskReviewReport(formId));
		//是否是诉保
		model.addAttribute(
			"isLitigation",
			ProjectUtil.isLitigation(info.getBusiType()) ? BooleanEnum.YES.code() : BooleanEnum.NO
				.code());
		//客户信息
		CustomerDetailInfo customerInfo = customerServiceClient.queryByUserId(info.getCustomerId());
		model.addAttribute("customerInfo", customerInfo);
		
		//用于编辑后跳转回审核页面
		String viewUrl = "/projectMg/investigation/viewDeclare.htm?audit=YES&formId=" + formId
							+ "&checkPoint=" + checkPoint;
		if (from != -1) {
			viewUrl = "/projectMg/investigation/view.htm?audit=YES&formId=" + formId
						+ "&checkPoint=" + checkPoint + "&toIndex=" + from;
		}
		model.addAttribute("auditIndex", from); //审核来源页面
		model.addAttribute("viewUrl", viewUrl);
		model.addAttribute("checkPoint", checkPoint);
	}
	
	// 审核过程中编辑 (部门负责人审核)
	@RequestMapping("audit1.htm")
	public String audit1(HttpServletRequest request, Model model, long formId, @RequestParam(
			value = "from", required = false, defaultValue = "-1") int from) {
		String checkPoint = CheckPointEnum.TEAM_LEADER.code();
		auditCommon(request, model, formId, checkPoint, from);
		return VM_PATH + "auditEdit.vm";
	}
	
	// 审核过程中编辑 (业务分管副总审核)
	@RequestMapping("audit2.htm")
	public String audit2(HttpServletRequest request, Model model, long formId, @RequestParam(
			value = "from", required = false, defaultValue = "-1") int from) {
		String checkPoint = CheckPointEnum.VICE_PRESIDENT_BUSINESS.code();
		auditCommon(request, model, formId, checkPoint, from);
		return VM_PATH + "auditEdit.vm";
	}
	
	private long findFormId(String checkPoint, long relatedFormId) {
		InvestigationCheckingInfo check = investigationServiceClient
			.findCheckingByCheckPiontAndRelatedFormId(checkPoint, relatedFormId);
		if (null == check) {
			return 0L;
		}
		
		return check.getFormId();
	}
	
	//编辑尽职调查申明(审核中)
	@RequestMapping("auditEditDeclare.htm")
	public String auditEditDeclare(HttpServletRequest request, HttpSession session, Model model,
									long formId, String checkPoint) {
		
		//formId是源值
		initSubmitCommon(request, session, model, formId);
		//当前审核节点编辑产生的值
		long checkFormId = findFormId(checkPoint, formId);
		
		FInvestigationInfo info = investigationServiceClient.findInvestigationByFormId(formId);
		if (checkFormId > 0) {
			FInvestigationInfo cinfo = investigationServiceClient
				.findInvestigationByFormId(checkFormId);
			if (null != cinfo) {
				info = cinfo;
			}
		}
		
		model.addAttribute("info", info);
		
		ProjectInfo project = projectServiceClient.queryByCode(info.getProjectCode(), false);
		if (null != project) {
			model.addAttribute("busiManagerName", project.getBusiManagerName());
			model.addAttribute("projectInfo", project);
		}
		
		model.addAttribute("checkPoint", checkPoint);
		
		return VM_PATH + "editDeclare.vm";
	}
	
}
