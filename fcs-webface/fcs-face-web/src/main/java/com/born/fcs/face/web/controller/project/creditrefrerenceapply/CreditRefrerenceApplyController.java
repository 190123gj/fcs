package com.born.fcs.face.web.controller.project.creditrefrerenceapply;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.creditrefrerenceapply.CreditRefrerenceApplyInfo;
import com.born.fcs.pm.ws.info.creditrefrerenceapply.CreditRefrerenceApplyReusltInfo;
import com.born.fcs.pm.ws.info.setup.FProjectCustomerBaseInfo;
import com.born.fcs.pm.ws.order.creditrefrerenceapply.CreditRefrerenceApplyOrder;
import com.born.fcs.pm.ws.order.creditrefrerenceapply.CreditRefrerenceApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

@Controller
@RequestMapping("projectMg/creditRefrerenceApply")
public class CreditRefrerenceApplyController extends WorkflowBaseController {
	
	final static String vm_path = "projectMg/assistSys/creditMg/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "establishedTime", "operateDate" };
	}
	
	/**
	 * 征信查询台账
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String CreditRefrerenceApplyList(CreditRefrerenceApplyQueryOrder order, Model model) {
		setSessionLocalInfo2Order(order);
		Date now = new Date();
		Date fwqDate = councilServiceClient.getSysDate();
		QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo> batchResult = creditRefrerenceApplyServiceClient
			.query(order);
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "list.vm";
	}
	
	/**
	 * 征信查询申请
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("addCreditRefrerenceApply.htm")
	public String addfCreditRefrerenceApply(String projectCode, String customerId, Model model) {
		CreditRefrerenceApplyInfo applyInfo = new CreditRefrerenceApplyInfo();
		String isBeforeFinishCouncil = "NO";
		String isAfterWardsPhases = "NO";
		if (null != projectCode) {
			ProjectSimpleDetailInfo projectInfo = projectServiceClient
				.querySimpleDetailInfo(projectCode);
			//上会是否完成
			if (projectInfo.getPhases().code().equals("SET_UP_PHASES")
				|| projectInfo.getPhases().code().equals("INVESTIGATING_PHASES")
				|| projectInfo.getPhases().code().equals("COUNCIL_PHASES")) {
				isBeforeFinishCouncil = "IS";
			}
			//是否保后阶段
			if (projectInfo.getPhases().code().equals("AFTERWARDS_PHASES")) {
				isAfterWardsPhases = "IS";
			}
			CompanyCustomerInfo customerInfo = companyCustomerClient.queryByUserId(
				projectInfo.getCustomerId(), null);
			applyInfo = setInfo(applyInfo, projectInfo, customerInfo);
			//客户申请书
			FProjectCustomerBaseInfo baseInfo = projectSetupServiceClient
				.queryCustomerBaseInfoByProjectCode(projectCode);
			if (baseInfo != null)
				applyInfo.setCustomerApplyUrl(baseInfo.getApplyScanningUrl());
			model.addAttribute("customerInfo", customerInfo);
			model.addAttribute("baseInfo", baseInfo);
		} else {
			if (StringUtil.isNotBlank(customerId) && Long.parseLong(customerId) > 0) {
				CompanyCustomerInfo customerInfo = companyCustomerClient.queryByUserId(
					Long.parseLong(customerId), null);
				applyInfo = setInfo(applyInfo, null, customerInfo);
			}
			
		}
		model.addAttribute("isBeforeFinishCouncil", isBeforeFinishCouncil);
		model.addAttribute("isAfterWardsPhases", isAfterWardsPhases);
		model.addAttribute("applyInfo", applyInfo);
		return vm_path + "addQuery.vm";
	}
	
	/**
	 * 提交征信查询申请
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveCreditRefrerenceApply.htm")
	@ResponseBody
	public JSONObject saveCreditRefrerenceApply(CreditRefrerenceApplyOrder order,
												HttpServletRequest request, Model model) {
		String tipPrefix = " 提交征信查询申请";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			// 初始化Form验证信息
			order.setCheckStatus(1);
			order.setCheckIndex(0);
			order.setFormCode(FormCodeEnum.CREDIT_REFRERENCE_APPLY);
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			order.setRelatedProjectCode(order.getProjectCode());
			order.setApplyManId(sessionLocal.getUserId());
			order.setApplyManName(sessionLocal.getRealName());
			ProjectInfo projectInfo = projectServiceClient.querySimpleDetailInfo(order
				.getProjectCode());
			CompanyCustomerInfo customerInfo = null;
			if (order.getCustomerId() > 0) {
				customerInfo = companyCustomerClient.queryByUserId(order.getCustomerId(), null);
			}
			if (customerInfo != null) {
				if (projectInfo != null) {
					order.setProjectCode(projectInfo.getProjectCode());
					order.setProjectName(projectInfo.getProjectName());
				}
				order.setCompanyName(customerInfo.getCustomerName());
				order
					.setLegalPersion(StringUtil.isNotEmpty(customerInfo.getLegalPersion()) ? customerInfo
						.getLegalPersion() : order.getLegalPersion());
				order.setAddress(StringUtil.isNotEmpty(customerInfo.getAddress()) ? customerInfo
					.getAddress() : order.getAddress());
				order
					.setRegisterCapitalStr(customerInfo.getRegisterCapital().getCent() != 0 ? customerInfo
						.getRegisterCapital().toString() : new Money(order.getRegisterCapitalStr())
						.multiply(10000L).toString());
				order
					.setBusiScope(StringUtil.isNotEmpty(customerInfo.getBusiScope()) ? customerInfo
						.getBusiScope() : order.getBusiScope());
				order.setEstablishedTime(customerInfo.getEstablishedTime() == null ? customerInfo
					.getEstablishedTime() : order.getEstablishedTime());
				order
					.setBusiLicenseUrl(StringUtil.isNotEmpty(customerInfo.getBusiLicenseUrl()) ? customerInfo
						.getBusiLicenseUrl() : order.getBusiLicenseUrl());
				order
					.setIsThreeInOne(StringUtil.isNotEmpty(customerInfo.getIsOneCert()) ? customerInfo
						.getIsOneCert() : order.getIsThreeInOne());
				order
					.setLoanCardNo(StringUtil.isNotEmpty(customerInfo.getLoanCardNo()) ? customerInfo
						.getLoanCardNo() : order.getLoanCardNo());
				order.setSocialUnityCreditCode(StringUtil.isNotEmpty(customerInfo
					.getBusiLicenseNo()) ? customerInfo.getBusiLicenseNo() : order
					.getSocialUnityCreditCode());
				if (customerInfo.getIsOneCert() == null
					|| !customerInfo.getIsOneCert().equals("IS")) {
					order.setTaxRegCertificateNo(StringUtil.isNotEmpty(customerInfo
						.getTaxCertificateNo()) ? customerInfo.getTaxCertificateNo() : order
						.getTaxRegCertificateNo());
					order
						.setLocalTaxCertNo(StringUtil.isNotEmpty(customerInfo.getLocalTaxCertNo()) ? customerInfo
							.getLocalTaxCertNo() : order.getLocalTaxCertNo());
					order
						.setOrgCode(StringUtil.isNotEmpty(customerInfo.getOrgCode()) ? customerInfo
							.getOrgCode() : customerInfo.getOrgCode());
					order
						.setBusiLicenseNo(StringUtil.isNotEmpty(customerInfo.getBusiLicenseNo()) ? customerInfo
							.getBusiLicenseNo() : order.getBusiLicenseNo());
				}
			}
			FormBaseResult saveResult = creditRefrerenceApplyServiceClient.save(order);
			jsonObject = toJSONResult(jsonObject, saveResult, "提交征信查询申请成功", null);
			jsonObject.put("formId", saveResult.getFormInfo().getFormId());
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("提交征信查询申请出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 征信查询申请审核
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("viewCreditRefrerenceApply.htm")
	public String viewCreditRefrerenceApply(Long id, String toPage, Model model) {
		String tipPrefix = "征信查询申请";
		try {
			CreditRefrerenceApplyInfo applyInfo = creditRefrerenceApplyServiceClient.findById(id);
			model.addAttribute("applyInfo", applyInfo);
			ProjectInfo projectInfo = projectServiceClient.queryByCode(applyInfo.getProjectCode(),
				false);
			CompanyCustomerInfo customerInfo = null;
			if (projectInfo != null) {
				customerInfo = companyCustomerClient.queryByUserId(projectInfo.getCustomerId(),
					null);
			} else {
				customerInfo = companyCustomerClient.queryByUserId(applyInfo.getCustomerId(), null);
			}
			//自动带出客户关系系统的数据
			
			model.addAttribute("customerInfo", customerInfo);
			
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		if ("add".equals(toPage)) {
			return vm_path + "addQuery.vm";
		} else {
			return vm_path + "auditQuery.vm";
		}
	}
	
	/**
	 * 撤销征信查询申请
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("revokeCreditRefrerenceApply.htm")
	public String revokeCreditRefrerenceApply(Long id, Model model) {
		String tipPrefix = "撤销征信查询申请";
		try {
			creditRefrerenceApplyServiceClient.revokeById(id);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		
		return vm_path + "list.vm";
	}
	
	/**
	 * 更新审批状态
	 * 
	 * @param id
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping("updateStatusCreditRefrerenceApply.htm")
	@ResponseBody
	public JSONObject updateStatusCreditRefrerenceApply(Long id, String status, Model model) {
		String tipPrefix = "更新征信查询申请审批状态";
		JSONObject jsonObject = new JSONObject();
		try {
			creditRefrerenceApplyServiceClient.updateStatusById(id, status);
			jsonObject.put("success", true);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			jsonObject.put("success", false);
			jsonObject.put("message", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 更新申请状态
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("updateApplyStatusCreditRefrerenceApply.htm")
	public String updateApplyStatusCreditRefrerenceApply(Long id, Model model) {
		String tipPrefix = "更新征信查询申请审批状态";
		try {
			creditRefrerenceApplyServiceClient.updateApplyStatusById(id, "0");
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		
		return vm_path + "list.vm";
	}
	
	/**
	 * 上传征信报告
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("uploadReport.htm")
	public String uploadReport(Long formId, String isView, String projectCode, Model model) {
		String tipPrefix = "上传征信报告";
		try {
			if (projectCode != null) {//项目详情 查看征信报告查看最新一条
				CreditRefrerenceApplyReusltInfo applyInfo = null;
				CreditRefrerenceApplyQueryOrder order = new CreditRefrerenceApplyQueryOrder();
				order.setProjectCode(projectCode);
				order.setSortCol("raw_add_time");
				order.setSortOrder("DESC");
				QueryBaseBatchResult<CreditRefrerenceApplyReusltInfo> batchResult = creditRefrerenceApplyServiceClient
					.query(order);
				if (batchResult != null && ListUtil.isNotEmpty(batchResult.getPageList())) {
					for (CreditRefrerenceApplyReusltInfo info : batchResult.getPageList()) {
						if (StringUtil.isNotEmpty(info.getCreditReport())) {
							applyInfo = info;
							break;
						}
					}
				}
				if (applyInfo == null) {
					applyInfo = new CreditRefrerenceApplyReusltInfo();
					ProjectInfo projectInfo = projectServiceClient.queryByCode(projectCode, false);
					applyInfo.setProjectCode(projectCode);
					applyInfo.setProjectName(projectInfo.getProjectName());
					applyInfo.setCompanyName(projectInfo.getCustomerName());
				}
				model.addAttribute("applyInfo", applyInfo);
			} else {
				CreditRefrerenceApplyInfo applyInfo = creditRefrerenceApplyServiceClient
					.findByFormId(formId);
				model.addAttribute("applyInfo", applyInfo);
			}
			model.addAttribute("isView", isView);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		return vm_path + "uploadReport.vm";
	}
	
	/**
	 * 保存征信报告
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveReport.htm")
	@ResponseBody
	public JSONObject saveReport(CreditRefrerenceApplyOrder order, Model model) {
		String tipPrefix = "上传征信报告";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			setSessionLocalInfo2Order(order);
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			order.setCheckStatus(1);
			order.setCheckIndex(0);
			FcsBaseResult saveResult = creditRefrerenceApplyServiceClient
				.updateCreditReportById(order);
			jsonObject = toJSONResult(jsonObject, saveResult, "上传征信报告成功", null);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", "上传征信报告失败");
		}
		
		return jsonObject;
	}
	
	/**
	 * 下载附件
	 * 
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	@RequestMapping("downloadAttachment.htm")
	public void downloadAttachment(HttpServletRequest request, HttpServletResponse response,
									String filePath, String fileName) throws Exception {
		if (filePath == null && "".equals(filePath)) {
			return;
		}
		
		FileInputStream fis = null;
		BufferedInputStream buff = null;
		OutputStream servletOS = null;
		File file = new File(filePath);
		try {
			fis = new FileInputStream(file);
			buff = new BufferedInputStream(fis);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		response.setContentType("image/jpeg");
		try {
			response.addHeader("Content-Disposition",
				"attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
		} catch (UnsupportedEncodingException e1) {
			logger.error(e1.getMessage(), e1);
			e1.printStackTrace();
		}
		byte[] b = new byte[1024];
		long k = 0;
		try {
			servletOS = response.getOutputStream();
			while (k < file.length()) {
				int j = buff.read(b, 0, 1024);
				k += j;
				servletOS.write(b, 0, j);
			}
			servletOS.flush();
			servletOS.close();
			fis.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}
			
		}
		
	}
	
	/**
	 * 编辑征信
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("editCredit.htm")
	public String editCredit(Long formId, Model model) {
		String tipPrefix = "编辑征信";
		try {
			FormInfo form = formServiceClient.findByFormId(formId);
			CreditRefrerenceApplyInfo applyInfo = creditRefrerenceApplyServiceClient
				.findByFormId(formId);
			String isBeforeFinishCouncil = "NO";
			String isAfterWardsPhases = "NO";
			ProjectSimpleDetailInfo projectInfo = projectServiceClient
				.querySimpleDetailInfo(applyInfo.getProjectCode());
			CompanyCustomerInfo customerInfo = null;
			if (projectInfo != null) {//上会是否完成
				if (projectInfo.getPhases().code().equals("SET_UP_PHASES")
					|| projectInfo.getPhases().code().equals("INVESTIGATING_PHASES")
					|| projectInfo.getPhases().code().equals("COUNCIL_PHASES")) {
					isBeforeFinishCouncil = "IS";
				}
				//是否保后阶段
				if (projectInfo.getPhases().code().equals("AFTERWARDS_PHASES")) {
					isAfterWardsPhases = "IS";
				}
				FProjectCustomerBaseInfo baseInfo = projectSetupServiceClient
					.queryCustomerBaseInfoByProjectCode(projectInfo.getProjectCode());
				model.addAttribute("baseInfo", baseInfo);
				customerInfo = companyCustomerClient.queryByUserId(projectInfo.getCustomerId(),
					null);
			} else {
				customerInfo = companyCustomerClient.queryByUserId(applyInfo.getCustomerId(), null);
			}
			
			applyInfo = setInfo(applyInfo, projectInfo, customerInfo);
			model.addAttribute("customerInfo", customerInfo);
			model.addAttribute("isBeforeFinishCouncil", isBeforeFinishCouncil);
			model.addAttribute("isAfterWardsPhases", isAfterWardsPhases);
			model.addAttribute("applyInfo", applyInfo);
			model.addAttribute("checkStatus", form.getCheckStatus());
			model.addAttribute("formCode", form.getFormCode());
			model.addAttribute("form", form);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		return vm_path + "addQuery.vm";
	}
	
	/**
	 * 征信查询审核
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	
	@RequestMapping("audit.htm")
	public String audit(long formId, HttpServletRequest request, Model model, HttpSession session) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		CreditRefrerenceApplyInfo applyInfo = creditRefrerenceApplyServiceClient
			.findByFormId(formId);
		CompanyCustomerInfo customerInfo = null;
		ProjectInfo projectInfo = projectServiceClient.queryByCode(applyInfo.getProjectCode(),
			false);
		//自动带出客户关系系统的数据
		if (projectInfo != null) {
			customerInfo = companyCustomerClient.queryByUserId(projectInfo.getCustomerId(), null);
		} else {
			customerInfo = companyCustomerClient.queryByUserId(applyInfo.getCustomerId(), null);
		}
		model.addAttribute("customerInfo", customerInfo);
		model.addAttribute("applyInfo", applyInfo);
		model.addAttribute("checkStatus", form.getCheckStatus());
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("form", form);// 表单信息
		initWorkflow(model, form, Long.toString(form.getTaskId()));
		return vm_path + "auditQuery.vm";
	}
	
	@RequestMapping("viewAudit.htm")
	public String viewAudit(long formId, HttpServletRequest request, Model model,
							HttpSession session) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		CreditRefrerenceApplyInfo applyInfo = creditRefrerenceApplyServiceClient
			.findByFormId(formId);
		ProjectInfo projectInfo = projectServiceClient.queryByCode(applyInfo.getProjectCode(),
			false);
		//自动带出客户关系系统的数据
		CompanyCustomerInfo customerInfo = null;
		if (projectInfo != null) {
			customerInfo = companyCustomerClient.queryByUserId(projectInfo.getCustomerId(), null);
		} else {
			customerInfo = companyCustomerClient.queryByUserId(applyInfo.getCustomerId(), null);
		}
		
		model.addAttribute("customerInfo", customerInfo);
		model.addAttribute("applyInfo", applyInfo);
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", form.getFormCode());
		return vm_path + "viewQuery.vm";
	}
	
	public CreditRefrerenceApplyInfo setInfo(CreditRefrerenceApplyInfo applyInfo,
												ProjectInfo projectInfo,
												CompanyCustomerInfo customerInfo) {
		if (customerInfo != null) {
			if (projectInfo != null) {
				applyInfo.setProjectCode(projectInfo.getProjectCode());
				applyInfo.setProjectName(projectInfo.getProjectName());
			}
			applyInfo.setCustomerId(customerInfo.getUserId());
			applyInfo.setCompanyName(customerInfo.getCustomerName());
			applyInfo
				.setLegalPersion(StringUtil.isNotEmpty(customerInfo.getLegalPersion()) ? customerInfo
					.getLegalPersion() : applyInfo.getLegalPersion());
			applyInfo.setAddress(StringUtil.isNotEmpty(customerInfo.getAddress()) ? customerInfo
				.getAddress() : applyInfo.getAddress());
			applyInfo
				.setRegisterCapital(customerInfo.getRegisterCapital().getCent() != 0 ? customerInfo
					.getRegisterCapital() : applyInfo.getRegisterCapital());
			applyInfo
				.setBusiScope(StringUtil.isNotEmpty(customerInfo.getBusiScope()) ? customerInfo
					.getBusiScope() : applyInfo.getBusiScope());
			applyInfo.setEstablishedTime(customerInfo.getEstablishedTime() != null ? customerInfo
				.getEstablishedTime() : applyInfo.getEstablishedTime());
			applyInfo
				.setBusiLicenseUrl(StringUtil.isNotEmpty(customerInfo.getBusiLicenseUrl()) ? customerInfo
					.getBusiLicenseUrl() : applyInfo.getBusiLicenseUrl());
			applyInfo
				.setIsThreeInOne(StringUtil.isNotEmpty(customerInfo.getIsOneCert()) ? customerInfo
					.getIsOneCert() : applyInfo.getIsThreeInOne());
			applyInfo
				.setLoanCardNo(StringUtil.isNotEmpty(customerInfo.getLoanCardNo()) ? customerInfo
					.getLoanCardNo() : applyInfo.getLoanCardNo());
			applyInfo.setSocialUnityCreditCode(StringUtil.isNotEmpty(customerInfo
				.getBusiLicenseNo()) ? customerInfo.getBusiLicenseNo() : applyInfo
				.getSocialUnityCreditCode());
			if (customerInfo.getIsOneCert() == null || !customerInfo.getIsOneCert().equals("IS")) {
				applyInfo.setTaxRegCertificateNo(StringUtil.isNotEmpty(customerInfo
					.getTaxCertificateNo()) ? customerInfo.getTaxCertificateNo() : applyInfo
					.getTaxRegCertificateNo());
				applyInfo
					.setLocalTaxCertNo(StringUtil.isNotEmpty(customerInfo.getLocalTaxCertNo()) ? customerInfo
						.getLocalTaxCertNo() : applyInfo.getLocalTaxCertNo());
				applyInfo
					.setOrgCode(StringUtil.isNotEmpty(customerInfo.getOrgCode()) ? customerInfo
						.getOrgCode() : customerInfo.getOrgCode());
				applyInfo
					.setBusiLicenseNo(StringUtil.isNotEmpty(customerInfo.getBusiLicenseNo()) ? customerInfo
						.getBusiLicenseNo() : applyInfo.getBusiLicenseNo());
			}
		} else {
//			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未找到客户信息");
			logger.error("未找到客户信息");
		}
		return applyInfo;
	}
}
