package com.bornsoft.web.app.project.setup;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.crm.ws.service.info.CompanyOwnershipStructureInfo;
import com.born.fcs.crm.ws.service.info.PersonalCustomerInfo;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CertTypeEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.EnterpriseNatureEnum;
import com.born.fcs.pm.ws.enums.EnterpriseScaleEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.BusiTypeInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.setup.FProjectCustomerBaseInfo;
import com.born.fcs.pm.ws.info.setup.FProjectGuaranteeEntrustedInfo;
import com.born.fcs.pm.ws.info.setup.FProjectLgLitigationInfo;
import com.born.fcs.pm.ws.info.setup.FProjectUnderwritingInfo;
import com.born.fcs.pm.ws.info.setup.SetupFormProjectInfo;
import com.born.fcs.pm.ws.order.setup.FProjectCustomerBaseInfoOrder;
import com.born.fcs.pm.ws.order.setup.FProjectGuaranteeEntrustedOrder;
import com.born.fcs.pm.ws.order.setup.FProjectLgLitigationOrder;
import com.born.fcs.pm.ws.order.setup.FProjectUnderwritingOrder;
import com.born.fcs.pm.ws.order.setup.SetupFormQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.bornsoft.api.service.app.DataPermissionUtil;
import com.bornsoft.api.service.app.JckFormService;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.GsonUtil;
import com.bornsoft.web.app.base.WorkflowBaseController;
import com.bornsoft.web.app.util.PageUtil;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;

/**
 * 项目立项
 * 
 * @author wuzj
 */
@Controller
@RequestMapping("projectMg/setUp")
public class ProjectSetupController extends WorkflowBaseController {
	
	@Autowired
	private JckFormService jckFormService;
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "establishedTime" };
	}
	
	/**
	 * 项目立项列表
	 * 
	 * @param queryOrder
	 * @param applyTimeStart
	 * @param applyTimeEnd
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("list.json")
	public JSONObject list(SetupFormQueryOrder queryOrder, String applyTimeStart, String applyTimeEnd) {
		JSONObject result = new JSONObject();
		try {
			if (queryOrder == null)
				queryOrder = new SetupFormQueryOrder();
			
			if (StringUtil.isNotBlank(applyTimeStart)) {
				queryOrder.setSubmitTimeStart(DateUtil.string2DateTimeByAutoZero(applyTimeStart));
			}
			
			if (StringUtil.isNotBlank(applyTimeEnd)) {
				queryOrder.setSubmitTimeEnd(DateUtil.string2DateTimeBy23(applyTimeEnd));
			}
			
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<SetupFormProjectInfo>  queryResult = projectSetupServiceClient.querySetupForm(queryOrder);
			result.put("isBusiManager", isBusiManager());
			result.put("page", GsonUtil.toJSONObject(PageUtil.getCovertPage(queryResult)));
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			toJSONResult(result, AppResultCodeEnum.FAILED, "查询失败：" + e.getMessage());
			logger.error("查询项目立项列表出错");
		}
		return result;
	}
	
	/**
	 * 未APP导航到对应立项页面
	 * @param customerName 客户名称（填写）
	 * @param customerType 客户类型
	 * @param busiType 业务类型编号
	 * @param busiTypeName 业务类型名称（其他填写）
	 * @return
	 */
	@ResponseBody
	@RequestMapping("form.json")
	public JSONObject form(String customerName, CustomerTypeEnum customerType,
			String busiType, String busiTypeName, String certNo,
			HttpSession session) {
		JSONObject result = new JSONObject();
		try {
			if (customerType == null) {
				throw ExceptionFactory.newFcsException(
						FcsResultEnum.INCOMPLETE_REQ_PARAM, "客户类型不能为空");
			}

			if (StringUtil.isEmpty(certNo)) {
				throw ExceptionFactory.newFcsException(
						FcsResultEnum.INCOMPLETE_REQ_PARAM, "证件号不能为空");
			}

			if (StringUtil.isEmpty(busiType)) {
				throw ExceptionFactory.newFcsException(
						FcsResultEnum.INCOMPLETE_REQ_PARAM, "请选择业务类型");
			}

			BusiTypeInfo info = basicDataCacheService.querySingleBusiType(
					busiType, customerType.code());

			if (info == null) {
				throw ExceptionFactory.newFcsException(
						FcsResultEnum.HAVE_NOT_DATA, "业务类型不存在");
			}

			// 根据表单编码找到对应的立项页面
			FormCodeEnum formCode = FormCodeEnum.getByCode(info
					.getSetupFormCode());

			if (formCode == null) {
				throw ExceptionFactory.newFcsException(
						FcsResultEnum.HAVE_NOT_DATA, "无法匹配表单类型");
			}

			// 防止客户信息丢失
			FProjectCustomerBaseInfo customerInfo = new FProjectCustomerBaseInfo();
			customerInfo.setCustomerType(customerType);
			customerInfo.setCustomerName(customerName);
			// 查询客户信息
			if (customerType == CustomerTypeEnum.PERSIONAL) {
				PersonalCustomerInfo pc = personalCustomerClient
						.queryByCertNo(certNo);
				if (pc != null) {
					customerName = pc.getCustomerName();
					BeanCopier.staticCopy(pc, customerInfo);
					customerInfo.setCustomerId(pc.getUserId());
					if (StringUtil.equals(CertTypeEnum.IDENTITY_CARD.code(), pc.getCertType())) {
						customerInfo.setBusiLicenseUrl(pc.getCertImgFont());
						customerInfo.setOrgCodeUrl(pc.getCertImgBack());
					} else {
						customerInfo.setBusiLicenseUrl(pc.getCertImgFont());
					}
					customerInfo.setContactNo(pc.getMobile());
					customerInfo.setCertType(CertTypeEnum.getByCode(pc.getCertType()));
				}
				customerInfo.setCertNo(certNo);
			} else {
				customerInfo.setBusiLicenseNo(certNo);
				CompanyCustomerInfo cc = companyCustomerClient
						.queryByBusiLicenseNo(certNo);
				if (cc != null) {
					customerName = cc.getCustomerName();
					BeanCopier.staticCopy(cc, customerInfo);
					customerInfo.setCustomerId(cc.getUserId());
					customerInfo.setIsExportOrientedEconomy(BooleanEnum.getByCode(cc
						.getIsExportOrientedEconomy()));
					customerInfo
						.setIsLocalGovPlatform(BooleanEnum.getByCode(cc.getIsLocalGovPlatform()));
					customerInfo.setEnterpriseType(EnterpriseNatureEnum.getByCode(cc
						.getEnterpriseType()));
					customerInfo.setScale(EnterpriseScaleEnum.getByCode(cc.getScale()));
					customerInfo.setIsOneCert(BooleanEnum.getByCode(cc.getIsOneCert()));
					customerInfo.setCertType(CertTypeEnum.getByCode(cc.getCertType()));
					customerInfo.setActualControlManCertType(CertTypeEnum.getByCode(cc
						.getActualControlManCertType()));
					//查询股权结构
					List<CompanyOwnershipStructureInfo> structre = cc.getCompanyOwnershipStructure();
					if (ListUtil.isNotEmpty(structre)) {
						session.setAttribute("initStructure", structre);
					}
				}
			}
			FormInfo form = new FormInfo();
			form.setFormCode(formCode);
			result.put("form", GsonUtil.toJSONObject(form));
			result.put("formCode", formCode.getCode());
			result.put("busiType", busiType);
			result.put("busiTypeName", busiTypeName);
			result.put("customerType", customerType);
			result.put("customerName", customerName);
			result.put("certNo", certNo);
			result.put("loanPurpose", getLoanPurpose());
			result.put("isBelong2Xinhui", DataPermissionUtil.isBelong2Xinhui());
			
			result.put("customerBaseInfo", GsonUtil.toJSONObject(customerInfo));
			session.setAttribute("initCustomerInfo", customerInfo);
			
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "成功");
		} catch (Exception e) {
			logger.error("立项界面分发失败", e);
			toJSONResult(result, AppResultCodeEnum.FAILED, e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 编辑立项表单
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("info.json")
	public JSONObject edit(long formId, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		try {
			FormInfo form = formServiceClient.findByFormId(formId);
			if (form == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
			}
			jsonObject.put("form", GsonUtil.toJSONObject(form));
			jsonObject.put("customerBaseInfo", GsonUtil.toJSONObject(getCustomerBaseInfo(form.getFormId(), session)));// 客户基本情况
			jsonObject.put("loanPurpose", getLoanPurpose());
			toJSONResult(jsonObject, AppResultCodeEnum.SUCCESS, "查询成功");
		} catch (Exception e) {
			logger.error("查询项目信息失败");
			toJSONResult(jsonObject, AppResultCodeEnum.FAILED, e.getMessage());
		}
		return jsonObject;
	}
	
	/**
	 * 保存客户基本情况
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping("saveCustomerBaseInfo.json")
	@ResponseBody
	public JSONObject saveCustomerBaseInfo(FProjectCustomerBaseInfoOrder order, HttpSession session,HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				toJSONResult(jsonObject, AppResultCodeEnum.FAILED, "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			BooleanEnum oneCert = BooleanEnum.getByCode(order.getIsOneCert());
			if(oneCert== BooleanEnum.IS || oneCert == BooleanEnum.YES){
				order.setIsOneCert(BooleanEnum.IS.code());
			}
			//设置一些值
			FormBaseResult result = projectSetupServiceClient.saveCustomerBaseInfo(order);
			jsonObject = toJSONResult(jsonObject,result,"保存客户信息成功","保存客户信息失败");
			if (result != null && result.isSuccess()) {
				FormInfo form = result.getFormInfo();
				FormCodeEnum formCode = form.getFormCode();
				jsonObject.put("form", GsonUtil.toJSONObject(form));// 表单信息
				jsonObject.put("isEdit", true);
				
				jsonObject.put("customerBaseInfo", GsonUtil.toJSONObject(getCustomerBaseInfo(form.getFormId(), session)));// 客户基本情况
				
				if (formCode == FormCodeEnum.SET_UP_GUARANTEE_ENTRUSTED) {
					viewGuaranteeOrEntrusted(form, jsonObject, false, session);
				} else if (formCode == FormCodeEnum.SET_UP_UNDERWRITING) {
					viewUnderwriting(form, jsonObject, false, session);
				} else if (formCode == FormCodeEnum.SET_UP_LITIGATION_PERSIONAL) {
					viewLgLitigationPersional(form, jsonObject, false, session);
				} else if (formCode == FormCodeEnum.SET_UP_LITIGATION_ENTERPRISE) {
					viewLgLitigationEnterprise(form, jsonObject, false, session);
				} else {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "无法匹配表单类型");
				}
				jsonObject.put("loanPurpose", getLoanPurpose());
				session.removeAttribute("initCustomerInfo");
				session.removeAttribute("initStructure");
			}
		} catch (Exception e) {
			toJSONResult(jsonObject, AppResultCodeEnum.FAILED, e.getMessage());
			logger.error("保存客户基本情况出错", e);
		}
		logger.error("保存客户信息出参={}", jsonObject);
		return jsonObject;
	}
	
	
	/**
	 * 保存担保/委贷项目信息
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping("saveGuaranteeEntrustedProject.json")
	@ResponseBody
	public JSONObject saveGuaranteeEntrustedProject(FProjectGuaranteeEntrustedOrder order,String status, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			order.setRelatedProjectCode(order.getProjectCode());
			
			//委贷项目 默认资金渠道未进出口担保
			if (ProjectUtil.isEntrusted(order.getBusiType())) {
				String channalCode = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.ENTRUSTED_DEFAULT_CHANNEL.code());
				if (StringUtil.isBlank(channalCode)) {
					throw new BornApiException("资金渠道尚未配置");
				}
				ChannalInfo channel = channalClient.queryByChannalCode(channalCode);
				if (channel == null) {
					throw new BornApiException("资金渠道不存在");
				}
				order.setCapitalChannelId(channel.getId());
				order.setCapitalChannelName(channel.getChannelName());
			}
			if(checkBoolean(order.getBelongToNc())){
				order.setBelongToNc(BooleanEnum.YES.code());
			}
			logger.info("保存担保委贷立项信息：{}",order);
			FormBaseResult result = projectSetupServiceClient.saveGuaranteeEntrustedProject(order);
		
			if(result.isSuccess() && "on".equals(status)){
				jsonObject = submit(result.getFormInfo().getFormId(), request);
			}else{
				toJSONResult(jsonObject, result, "项目信息保存成功", result.getMessage());
			}
			logger.info("保存担保委贷立项，结果={}",result);
		} catch (Exception e) {
			toJSONResult(jsonObject, AppResultCodeEnum.FAILED, e.getMessage());
			logger.error("保存项目信息出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 保存承销项目信息
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping("saveUnderwritingProject.json")
	@ResponseBody
	public JSONObject saveUnderwritingProject(FProjectUnderwritingOrder order,String status, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			order.setRelatedProjectCode(order.getProjectCode());
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			setSessionLocalInfo2Order(order);
			FormBaseResult result = projectSetupServiceClient.saveUnderwritingProject(order);
			
			if(result.isSuccess() && "on".equals(status)){
				jsonObject = submit(result.getFormInfo().getFormId(), request);
			}else{
				toJSONResult(jsonObject, result, "项目信息保存成功", result.getMessage());
			}
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存项目信息出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 保存诉讼保函类项目信息
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping("saveLgLitigationProject.json")
	@ResponseBody
	public JSONObject saveLgLitigationProject(FProjectLgLitigationOrder order,String status, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			order.setRelatedProjectCode(order.getProjectCode());
			
			if(checkBoolean(order.getBelongToNc())){
				order.setBelongToNc(BooleanEnum.YES.code());
			}
			FormBaseResult result = projectSetupServiceClient.saveLgLitigationProject(order);
			
			if(result.isSuccess() && "on".equals(status)){
				jsonObject = submit(result.getFormInfo().getFormId(), request);
			}else{
				toJSONResult(jsonObject, result, "项目信息保存成功", result.getMessage());
			}
			
		} catch (Exception e) {
			logger.error("保存项目信息出错", e);
			jsonObject.put("success", false);
			jsonObject.put("message", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 提交
	 * @param formId
	 * @param request
	 * @return
	 */
	private  JSONObject submit(long formId, HttpServletRequest request) {
		return jckFormService.submit(formId, request);
	}
	
	/**
	 * 获取客户信息
	 * @param formId
	 * @param session
	 * @return
	 */
	private FProjectCustomerBaseInfo getCustomerBaseInfo(long formId, HttpSession session) {
		FProjectCustomerBaseInfo info = projectSetupServiceClient
			.queryCustomerBaseInfoByFormId(formId);
		if (info == null && session.getAttribute("initCustomerInfo") != null) {
			info = (FProjectCustomerBaseInfo) session.getAttribute("initCustomerInfo");
		}
		return info;
	}
	
	
	/**
	 * 查看担保委贷项目详情
	 * 
	 * @param form
	 * @param model
	 * @return
	 */
	private void viewGuaranteeOrEntrusted(FormInfo form, JSONObject model, boolean fromAudit,
											HttpSession session) {
		
		FProjectGuaranteeEntrustedInfo project = projectSetupServiceClient
			.queryGuaranteeEntrustedProjectByFormId(form.getFormId());
		
		if (!fromAudit && !DataPermissionUtil.hasViewPermission(project.getProjectCode())) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS, "无权访问");
		}
		model.put("project", GsonUtil.toJSONObject(project));// 申请贷款/担保情况
	}
	
	/**
	 * 查询诉讼保函-个人项目详情
	 * @param form
	 * @param model
	 * @return
	 */
	private void viewLgLitigationPersional(FormInfo form, JSONObject model, boolean fromAudit,
												HttpSession session) {
		
		FProjectLgLitigationInfo project = projectSetupServiceClient
			.queryLgLitigationProjectByFormId(form.getFormId());
		
		model.put("project", GsonUtil.toJSONObject(project));// 保全信息
		
		if (!fromAudit && !DataPermissionUtil.hasViewPermission(project.getProjectCode())) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS, "无权访问");
		}
		
	}
	
	/**
	 * 查询诉讼保函-企业项目详情
	 * @param form
	 * @param model
	 * @return
	 */
	private void viewLgLitigationEnterprise(FormInfo form, JSONObject model, boolean fromAudit,
												HttpSession session) {
		
		FProjectLgLitigationInfo project = projectSetupServiceClient
			.queryLgLitigationProjectByFormId(form.getFormId());
		
		if (!fromAudit && !DataPermissionUtil.hasViewPermission(project.getProjectCode())) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS, "无权访问");
		}
		
		model.put("project", GsonUtil.toJSONObject(project));// 保全信息
	}
	
	/**
	 * 查看承销项目详情
	 * @param form
	 * @param model
	 * @return
	 */
	private void viewUnderwriting(FormInfo form, JSONObject model, boolean fromAudit,
									HttpSession session) {
		
		FProjectUnderwritingInfo project = projectSetupServiceClient
			.queryUnderwritingProjectByFormId(form.getFormId());
		if (!fromAudit && !DataPermissionUtil.hasViewPermission(project.getProjectCode())) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS, "无权访问");
		}
		model.put("project", GsonUtil.toJSONObject(project));// 承销信息
		
	}
	
}
