package com.born.fcs.face.web.controller.project.setup;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.bpm.service.client.user.SysOrg;
import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.crm.ws.service.info.CompanyOwnershipStructureInfo;
import com.born.fcs.crm.ws.service.info.CustomerDetailInfo;
import com.born.fcs.crm.ws.service.info.PersonalCustomerInfo;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CertTypeEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.DataCodeEnum;
import com.born.fcs.pm.ws.enums.EnterpriseNatureEnum;
import com.born.fcs.pm.ws.enums.EnterpriseScaleEnum;
import com.born.fcs.pm.ws.enums.EvaluateCompanyRegionEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.GuaranteeTypeEnum;
import com.born.fcs.pm.ws.enums.PledgeTypeEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.BusiTypeInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.setup.FProjectCounterGuaranteePledgeInfo;
import com.born.fcs.pm.ws.info.setup.FProjectCustomerBaseInfo;
import com.born.fcs.pm.ws.info.setup.FProjectEquityStructureInfo;
import com.born.fcs.pm.ws.info.setup.FProjectGuaranteeEntrustedInfo;
import com.born.fcs.pm.ws.info.setup.FProjectInfo;
import com.born.fcs.pm.ws.info.setup.FProjectLgLitigationInfo;
import com.born.fcs.pm.ws.info.setup.FProjectUnderwritingInfo;
import com.born.fcs.pm.ws.info.setup.SetupFormProjectInfo;
import com.born.fcs.pm.ws.order.common.CopyHisFormOrder;
import com.born.fcs.pm.ws.order.setup.FProjectBankLoanBatchOrder;
import com.born.fcs.pm.ws.order.setup.FProjectCounterGuaranteeOrder;
import com.born.fcs.pm.ws.order.setup.FProjectCustomerBaseInfoOrder;
import com.born.fcs.pm.ws.order.setup.FProjectEquityStructureBatchOrder;
import com.born.fcs.pm.ws.order.setup.FProjectExternalGuaranteeBatchOrder;
import com.born.fcs.pm.ws.order.setup.FProjectGuaranteeEntrustedOrder;
import com.born.fcs.pm.ws.order.setup.FProjectLgLitigationOrder;
import com.born.fcs.pm.ws.order.setup.FProjectUnderwritingOrder;
import com.born.fcs.pm.ws.order.setup.ProjectInnovativeProductOrder;
import com.born.fcs.pm.ws.order.setup.SetupFormQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
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
	
	final static String vm_path = "/projectMg/beforeLoanMg/setUp/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "establishedTime" };
	}
	
	@Override
	protected String[] getDateInputNameArray() {
		return new String[] { "exchangeUpdateTime" };
	}
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "amount", "registerCapital", "actualCapital", "totalAsset",
								"netAsset", "salesProceedsLastYear", "salesProceedsThisYear",
								"totalProfitLastYear", "totalProfitThisYear" };
	}
	
	@RequestMapping("apply.htm")
	public String apply(Model model) {
		model.addAttribute("certTypeList", CertTypeEnum.getAllEnum());
		return vm_path + "apply.vm";
	}
	
	/**
	 * 跳转到立项页面
	 * @param customerId 客户ID
	 * @param customerName 客户名称
	 * @param customerType 客户类型
	 * @param trueCustomerId 用信客户ID
	 * @param trueCustomerName 用信客户名称
	 * @param trueCustomerType 用信客户类型
	 * @param busiType 业务类型编号
	 * @param busiTypeName 业务类型名称（其他填写）
	 * @return
	 */
	@RequestMapping("form.htm")
	public String form(Long customerId, String customerName, CustomerTypeEnum customerType,
						Long trueCustomerId, String trueCustomerName,
						CustomerTypeEnum trueCustomerType, String busiType, String busiTypeName,
						String certNo, CertTypeEnum certType, HttpServletRequest request,
						HttpSession session, Model model) {
		
		if ((customerId == null || customerId <= 0) && !ProjectUtil.isInnovativeProduct(busiType)) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM, "请选择客户 ");
		}
		
		if (customerType == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM, "客户类型不能为空");
		}
		
		if (StringUtil.isEmpty(certNo) && !ProjectUtil.isInnovativeProduct(busiType)) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM, "证件号不能为空");
		}
		
		if (StringUtil.isEmpty(busiType)) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM, "请选择业务类型");
		}
		
		BusiTypeInfo info = basicDataCacheService
			.querySingleBusiType(busiType, customerType.code());
		
		if (info == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "业务类型不存在");
		}
		
		//根据表单编码找到对应的立项页面
		FormCodeEnum formCode = FormCodeEnum.getByCode(info.getSetupFormCode());
		
		if (formCode == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "无法匹配表单类型");
		}
		
		//防止客户信息丢失
		FProjectCustomerBaseInfo customerInfo = new FProjectCustomerBaseInfo();
		customerInfo.setCustomerType(customerType);
		customerInfo.setCustomerName(customerName);
		customerInfo.setCertType(certType);
		customerInfo.setTrueCustomerId(trueCustomerId == null ? 0 : trueCustomerId);
		customerInfo.setTrueCustomerName(trueCustomerName);
		customerInfo.setTrueCustomerType(trueCustomerType == null ? CustomerTypeEnum.ENTERPRISE
			: trueCustomerType);
		
		if (customerType == CustomerTypeEnum.ENTERPRISE) {
			JSONObject checkResult = null;
			if (trueCustomerId != null && trueCustomerId > 0) {
				checkResult = checkCustomerOccupy(trueCustomerId, trueCustomerName, request);
			} else if (customerId != null) {
				checkResult = checkCustomerOccupy(customerId, customerName, request);
			}
			if (checkResult != null && !checkResult.getBooleanValue("success")) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					checkResult.getString("message"));
			}
		}
		
		//查询客户信息
		if (customerType == CustomerTypeEnum.PERSIONAL) {
			//PersonalCustomerInfo pc = personalCustomerClient.queryByCertNo(certNo);
			PersonalCustomerInfo pc = personalCustomerClient.queryByUserId(customerId);
			if (pc != null) {
				customerName = pc.getCustomerName();
				customerId = pc.getUserId();
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
			//CompanyCustomerInfo cc = companyCustomerClient.queryByBusiLicenseNo(certNo);
			CompanyCustomerInfo cc = companyCustomerClient.queryByUserId(customerId, null);
			if (cc != null) {
				customerName = cc.getCustomerName();
				customerId = cc.getUserId();
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
		
		model.addAttribute("formCode", formCode);
		model.addAttribute("certTypeList", CertTypeEnum.getAllEnum());
		model.addAttribute("enterpriseNature", EnterpriseNatureEnum.getAllEnum());
		model.addAttribute("enterpriseScale", EnterpriseScaleEnum.getAllEnum());
		model.addAttribute("chargeType", ChargeTypeEnum.getAllEnum());
		model.addAttribute("timeUnit", TimeUnitEnum.getAllEnum());
		model.addAttribute("pledgeType", PledgeTypeEnum.getAllEnum());
		model.addAttribute("busiType", busiType);
		model.addAttribute("busiTypeName", busiTypeName);
		model.addAttribute("customerType", customerType);
		model.addAttribute("customerName", customerName);
		model.addAttribute("certNo", certNo);
		model.addAttribute("isEdit", false);
		model.addAttribute("toIndex", 0);
		
		session.setAttribute("initCustomerInfo", customerInfo);
		model.addAttribute("customerBaseInfo", customerInfo);
		
		//委贷项目 默认资金渠道未进出口担保
		if (ProjectUtil.isEntrusted(busiType)) {
			String channalCode = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.ENTRUSTED_DEFAULT_CHANNEL.code());
			if (StringUtil.isBlank(channalCode)) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资金渠道尚未配置");
			}
			ChannalInfo channel = channalClient.queryByChannalCode(channalCode);
			if (channel == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资金渠道不存在");
			}
			model.addAttribute("entrustedDefaultChannelId", channel.getId());
			model.addAttribute("entrustedDefaultChannelCode", channel.getChannelCode());
			model.addAttribute("entrustedDefaultChannelType", channel.getChannelType());
			model.addAttribute("entrustedDefaultChannelName", channel.getChannelName());
		}
		
		setCurrencys(model);
		if (formCode == FormCodeEnum.SET_UP_INNOVATIVE_PRODUCT) {
			return vm_path + "apply_innovative_product.vm";
		} else if (formCode == FormCodeEnum.SET_UP_GUARANTEE_ENTRUSTED) {
			return vm_path + "apply_DB_WD.vm";
		} else if (formCode == FormCodeEnum.SET_UP_UNDERWRITING) {
			return vm_path + "apply_CX.vm";
		} else if (formCode == FormCodeEnum.SET_UP_LITIGATION_PERSIONAL) {
			return vm_path + "apply_SS_P.vm";
		} else if (formCode == FormCodeEnum.SET_UP_LITIGATION_ENTERPRISE) {
			return vm_path + "apply_SS_E.vm";
		} else if (formCode == FormCodeEnum.SET_UP_FINANCING) {
			return vm_path + "apply.vm";
		} else {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "无法匹配表单类型");
		}
	}
	
	/**
	 * 可选货币
	 * @param model
	 */
	private void setCurrencys(Model model) {
		//可选货币
		//		List<SysDataDictionaryInfo> currencys = Lists.newArrayList();
		//		SysDataDictionaryInfo currency1 = new SysDataDictionaryInfo();
		//		currency1.setDataValue("USD");
		//		currency1.setDataValue1("美元");
		//		SysDataDictionaryInfo currency2 = new SysDataDictionaryInfo();
		//		currency2.setDataValue("GBP");
		//		currency2.setDataValue1("英镑");
		//		SysDataDictionaryInfo currency3 = new SysDataDictionaryInfo();
		//		currency3.setDataValue("EUR");
		//		currency3.setDataValue1("欧元");
		//		currencys.add(currency1);
		//		currencys.add(currency2);
		//		currencys.add(currency3);
		model.addAttribute("currencys",
			sysDataDictionaryServiceClient.findByDataCode(DataCodeEnum.CURRENCY));
	}
	
	/**
	 * 从crm获取最新的客户信息
	 * @param customerInfo
	 * @param model
	 * @return
	 */
	private void setCustomerInfoFromCrm(FProjectCustomerBaseInfo customerInfo, Model model) {
		if (customerInfo == null)
			customerInfo = new FProjectCustomerBaseInfo();
		//查询客户信息
		String applyScanningUrl = customerInfo.getApplyScanningUrl();
		if (customerInfo.getCustomerType() == CustomerTypeEnum.PERSIONAL) {
			PersonalCustomerInfo pc = personalCustomerClient.queryByUserId(customerInfo
				.getCustomerId());
			if (pc != null) {
				//BeanCopier.staticCopy(pc, customerInfo);
				MiscUtil.copyPoObjectNoBlankAndZero(customerInfo, pc);
				customerInfo.setCustomerId(pc.getUserId());
				if (StringUtil.equals(CertTypeEnum.IDENTITY_CARD.code(), pc.getCertType())) {
					if (StringUtil.isNotBlank(pc.getCertImgFont()))
						customerInfo.setBusiLicenseUrl(pc.getCertImgFont());
					if (StringUtil.isNotBlank(pc.getCertImgBack()))
						customerInfo.setOrgCodeUrl(pc.getCertImgBack());
				} else {
					if (StringUtil.isNotBlank(pc.getCertImgFont()))
						customerInfo.setBusiLicenseUrl(pc.getCertImgFont());
				}
				if (StringUtil.isNotBlank(pc.getMobile()))
					customerInfo.setContactNo(pc.getMobile());
				if (StringUtil.isNotBlank(pc.getCertType()))
					customerInfo.setCertType(CertTypeEnum.getByCode(pc.getCertType()));
				if (StringUtil.isNotBlank(pc.getCertNo()))
					customerInfo.setCertNo(pc.getCertNo());
			}
		} else {
			
			CompanyCustomerInfo cc = companyCustomerClient.queryByUserId(
				customerInfo.getCustomerId(), null);
			if (cc != null) {
				//BeanCopier.staticCopy(cc, customerInfo);
				MiscUtil.copyPoObjectNoBlankAndZero(customerInfo, cc);
				customerInfo.setCustomerId(cc.getUserId());
				if (StringUtil.isNotBlank(cc.getIsExportOrientedEconomy()))
					customerInfo.setIsExportOrientedEconomy(BooleanEnum.getByCode(cc
						.getIsExportOrientedEconomy()));
				if (StringUtil.isNotBlank(cc.getIsLocalGovPlatform()))
					customerInfo.setIsLocalGovPlatform(BooleanEnum.getByCode(cc
						.getIsLocalGovPlatform()));
				if (StringUtil.isNotBlank(cc.getEnterpriseType()))
					customerInfo.setEnterpriseType(EnterpriseNatureEnum.getByCode(cc
						.getEnterpriseType()));
				if (StringUtil.isNotBlank(cc.getScale()))
					customerInfo.setScale(EnterpriseScaleEnum.getByCode(cc.getScale()));
				if (StringUtil.isNotBlank(cc.getIsOneCert()))
					customerInfo.setIsOneCert(BooleanEnum.getByCode(cc.getIsOneCert()));
				if (StringUtil.isNotBlank(cc.getCertType()))
					customerInfo.setCertType(CertTypeEnum.getByCode(cc.getCertType()));
				if (StringUtil.isNotBlank(cc.getActualControlManCertType()))
					customerInfo.setActualControlManCertType(CertTypeEnum.getByCode(cc
						.getActualControlManCertType()));
				
				//查询股权结构
				List<CompanyOwnershipStructureInfo> structre = cc.getCompanyOwnershipStructure();
				if (ListUtil.isNotEmpty(structre)) {
					List<FProjectEquityStructureInfo> list = Lists.newArrayList();
					for (CompanyOwnershipStructureInfo struc : structre) {
						FProjectEquityStructureInfo sInfo = new FProjectEquityStructureInfo();
						sInfo.setEquityRatio(NumberUtil.parseDouble(struc.getEquity()));//股权比例
						sInfo.setStockholder(struc.getShareholdersName());//主要股东名称
						sInfo.setContributionWay(struc.getMethord()); //出资方式
						sInfo.setCapitalContributions(struc.getAmount());//出资额
						sInfo.setAmountType(struc.getAmountType());
						list.add(sInfo);
					}
					model.addAttribute("equityStructure", list);// 股权结构
				} else {
					model.addAttribute("equityStructure",
						getEquityStructure(customerInfo.getFormId(), null));// 股权结构
				}
			}
		}
		customerInfo.setApplyScanningUrl(applyScanningUrl);
		model.addAttribute("customerBaseInfo", customerInfo);// 客户基本情况
	}
	
	/**
	 * 保存创新业务产品
	 *
	 * @param order
	 * @return
	 */
	@RequestMapping("saveInnovativeProduct.htm")
	@ResponseBody
	public JSONObject saveInnovativeProduct(HttpServletRequest request,
											ProjectInnovativeProductOrder order, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			order.setRemark(request.getParameter("remark"));
			FormBaseResult result = projectSetupServiceClient.saveInnovativeProduct(order);
			
			jsonObject = toJSONResult(jsonObject, result, "保存创新业务产品成功", null);
			if (result != null && result.isSuccess()) {
				session.removeAttribute("initCustomerInfo");
				session.removeAttribute("initStructure");
				verifyMsgSave(request, "LX" + result.getFormInfo().getFormId());
				addAttachfile(result.getFormInfo().getFormId() + "_INNOVATIVE", request, "",
					"创新业务产品附件", CommonAttachmentTypeEnum.PROJECT_INNOVATIVE_PRODUCT);
				
			}
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存创新业务产品出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 保存客户基本情况
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping("saveCustomerBaseInfo.htm")
	@ResponseBody
	public JSONObject saveCustomerBaseInfo(HttpServletRequest request,
											FProjectCustomerBaseInfoOrder order, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			
			FormBaseResult result = projectSetupServiceClient.saveCustomerBaseInfo(order);
			
			jsonObject = toJSONResult(jsonObject, result, "保存客户基本情况成功", null);
			if (result != null && result.isSuccess()) {
				session.removeAttribute("initCustomerInfo");
				session.removeAttribute("initStructure");
				verifyMsgSave(request, "LX" + result.getFormInfo().getFormId());
			}
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存客户基本情况出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 保存股权架构
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping("saveEquityStructure.htm")
	@ResponseBody
	public JSONObject saveEquityStructure(FProjectEquityStructureBatchOrder order,
											HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			
			FormBaseResult result = projectSetupServiceClient.saveEquityStructure(order);
			
			jsonObject = toJSONResult(jsonObject, result, "保存股权结构成功", null);
			if (result != null && result.isSuccess()) {
				session.removeAttribute("initCustomerInfo");
				session.removeAttribute("initStructure");
			}
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存股权结构出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 保存银行贷款情况
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping("saveBankLoan.htm")
	@ResponseBody
	public JSONObject saveBankLoan(FProjectBankLoanBatchOrder order, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			
			FormBaseResult result = projectSetupServiceClient.saveBankLoan(order);
			
			jsonObject = toJSONResult(jsonObject, result, "保存银行贷款情况成功", null);
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存银行贷款情况出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 保存对外担保情况
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping("saveExternalGuarantee.htm")
	@ResponseBody
	public JSONObject saveExternalGuarantee(FProjectExternalGuaranteeBatchOrder order,
											HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			
			FormBaseResult result = projectSetupServiceClient.saveExternalGuarantee(order);
			
			jsonObject = toJSONResult(jsonObject, result, "保存对外担保情况成功", null);
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存对外担保情况出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 保存担保/委贷项目信息
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping("saveGuaranteeEntrustedProject.htm")
	@ResponseBody
	public JSONObject saveGuaranteeEntrustedProject(FProjectGuaranteeEntrustedOrder order,
													HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			
			//委贷项目 默认资金渠道未进出口担保
			if (ProjectUtil.isEntrusted(order.getBusiType())) {
				String channalCode = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.ENTRUSTED_DEFAULT_CHANNEL.code());
				if (StringUtil.isBlank(channalCode)) {
					jsonObject.put("success", false);
					jsonObject.put("message", "资金渠道尚未配置");
					return jsonObject;
				}
				ChannalInfo channel = channalClient.queryByChannalCode(channalCode);
				if (channel == null) {
					jsonObject.put("success", false);
					jsonObject.put("message", "资金渠道不存在");
					return jsonObject;
				}
				order.setCapitalChannelId(channel.getId());
				order.setCapitalChannelCode(channel.getChannelCode());
				order.setCapitalChannelType(channel.getChannelType());
				order.setCapitalChannelName(channel.getChannelName());
			}
			
			FormBaseResult result = projectSetupServiceClient.saveGuaranteeEntrustedProject(order);
			
			jsonObject = toJSONResult(jsonObject, result, "项目信息保存成功", null);
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
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
	@RequestMapping("saveUnderwritingProject.htm")
	@ResponseBody
	public JSONObject saveUnderwritingProject(FProjectUnderwritingOrder order,
												HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			
			FormBaseResult result = projectSetupServiceClient.saveUnderwritingProject(order);
			
			jsonObject = toJSONResult(jsonObject, result, "项目信息保存成功", null);
			
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
	@RequestMapping("saveLgLitigationProject.htm")
	@ResponseBody
	public JSONObject saveLgLitigationProject(FProjectLgLitigationOrder order,
												HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			
			FormBaseResult result = projectSetupServiceClient.saveLgLitigationProject(order);
			
			jsonObject = toJSONResult(jsonObject, result, "项目信息保存成功", null);
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存项目信息出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 保存反担保情况
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping("saveCounterGuarantee.htm")
	@ResponseBody
	public JSONObject saveCounterGuarantee(HttpServletRequest request,
											FProjectCounterGuaranteeOrder order) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			
			FormBaseResult result = projectSetupServiceClient.saveCounterGuarantee(order);
			
			jsonObject = toJSONResult(jsonObject, result, "保存反担保/担保情况成功", null);
			
			//保存附件
			if (result != null && result.isSuccess()) {
				String projectCode = order.getProjectCode();
				try {
					if (StringUtil.isBlank(projectCode)) {
						FProjectInfo project = (FProjectInfo) result.getReturnObject();
						if (project != null)
							projectCode = project.getProjectCode();
					}
				} catch (Exception e) {
				}
				addAttachfile(String.valueOf(result.getFormInfo().getFormId()), request,
					projectCode, "立项-反担保附件", CommonAttachmentTypeEnum.COUNTER_GUARANTEE);
			}
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存反担保/担保情况出错", e);
		}
		return jsonObject;
	}
	
	/**
	 * 复制存量项目
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping("copyHistory.htm")
	@ResponseBody
	public JSONObject copyHistory(HttpServletRequest request, CopyHisFormOrder order) {
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			order.setUserId(sessionLocal.getUserId());
			order.setUserAccount(sessionLocal.getUserName());
			order.setUserName(sessionLocal.getRealName());
			order.setUserIp(sessionLocal.getRemoteAddr());
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (userInfo != null) {
				order.setUserMobile(userInfo.getMoblie());
				order.setUserEmail(userInfo.getEmail());
				SysOrg dept = sessionLocal.getUserInfo().getDept();
				if (dept != null) {
					order.setDeptId(dept.getOrgId());
					order.setDeptCode(dept.getCode());
					order.setDeptName(dept.getOrgName());
					order.setDeptPath(dept.getPath());
					order.setDeptPathName(dept.getOrgPathname());
				}
			}
			if (StringUtil.isBlank(order.getTrueCustomerType())) {
				order.setTrueCustomerType(CustomerTypeEnum.ENTERPRISE.code());
			}
			FormBaseResult result = projectSetupServiceClient.copyHistory(order);
			jsonObject = toJSONResult(jsonObject, result, "复制成功", null);
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("复制存量项目出错", e);
		}
		return jsonObject;
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
	@RequestMapping("list.htm")
	public String list(SetupFormQueryOrder queryOrder, String applyTimeStart, String applyTimeEnd,
						Model model) {
		
		try {
			if (queryOrder == null)
				queryOrder = new SetupFormQueryOrder();
			
			if (StringUtil.isNotBlank(applyTimeStart)) {
				queryOrder.setSubmitTimeStart(DateUtil.string2DateTimeByAutoZero(applyTimeStart));
			}
			
			if (StringUtil.isNotBlank(applyTimeEnd)) {
				queryOrder.setSubmitTimeEnd(DateUtil.string2DateTimeBy23(applyTimeEnd));
			}
			
			// model.addAttribute("busiType", BusiTypeEnum.getAllEnum());
			setSessionLocalInfo2Order(queryOrder);
			//			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			//			if (sessionLocal != null) {
			//				UserInfo userInfo = sessionLocal.getUserInfo();
			//				String zhgrole = sysParameterServiceClient
			//					.getSysParameterValue(SysParamEnum.SYS_PARAM_BUSINESS_ZHG_ROLE_NAME.code());
			//				if (hasAllDataPermission() || DataPermissionUtil.hasRole(zhgrole)) {//拥有所有数据权限
			//					queryOrder.setLoginUserId(0);
			//					queryOrder.setDeptIdList(null);
			//				} else if (hasPrincipalDataPermission() && userInfo != null) { //拥有所负责的数据权限，按照部门维度查询数据
			//					queryOrder.setLoginUserId(0);
			//					queryOrder.setDeptIdList(userInfo.getDeptIdList());
			//				} else { //按照人员维度查询数据
			//					queryOrder.setLoginUserId(sessionLocal.getUserId());
			//					queryOrder.setDeptIdList(null);
			//				}
			//				//查看草稿的人员
			//				queryOrder.setDraftUserId(sessionLocal.getUserId());
			//			}
			model.addAttribute("queryOrder", queryOrder);
			model.addAttribute("applyTimeStart", applyTimeStart);
			model.addAttribute("applyTimeEnd", applyTimeEnd);
			model.addAttribute("isBusiManager", isBusiManager());
			model.addAttribute("statusList", FormStatusEnum.getAllEnum());
			model.addAttribute("page",
				PageUtil.getCovertPage(projectSetupServiceClient.querySetupForm(queryOrder)));
			
		} catch (Exception e) {
			logger.error("查询项目立项列表出错");
		}
		
		return vm_path + "list.vm";
	}
	
	/**
	 * 编辑立项表单
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("edit.htm")
	public String edit(long formId, Integer toIndex, Model model, HttpServletRequest request,
						HttpSession session) {
		
		//SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		FormCodeEnum formCode = form.getFormCode();
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", formCode);
		model.addAttribute("certTypeList", CertTypeEnum.getAllEnum());
		model.addAttribute("enterpriseNature", EnterpriseNatureEnum.getAllEnum());
		model.addAttribute("enterpriseScale", EnterpriseScaleEnum.getAllEnum());
		model.addAttribute("chargeType", ChargeTypeEnum.getAllEnum());
		model.addAttribute("timeUnit", TimeUnitEnum.getAllEnum());
		model.addAttribute("pledgeType", PledgeTypeEnum.getAllEnum());
		model.addAttribute("isEdit", true);
		
		if (toIndex == null)
			toIndex = 0;
		model.addAttribute("toIndex", toIndex);
		//风险提示消息查询
		verifyMsgQuery("LX" + formId, model);
		boolean fetchFromCrm = (StringUtil.equals("YES", request.getParameter("fetchFromCrm")));
		
		setCurrencys(model);
		FProjectInfo project = projectSetupServiceClient.queryProjectByFormId(form.getFormId());
		if (formCode == FormCodeEnum.SET_UP_INNOVATIVE_PRODUCT) {
			viewGuaranteeOrEntrusted(form, model, false, fetchFromCrm, session);
			queryCommonAttachmentData(model, form.getFormId() + "_INNOVATIVE",
				CommonAttachmentTypeEnum.PROJECT_INNOVATIVE_PRODUCT);
			return vm_path + "apply_innovative_product.vm";
		} else if (formCode == FormCodeEnum.SET_UP_GUARANTEE_ENTRUSTED) {
			viewGuaranteeOrEntrusted(form, model, false, fetchFromCrm, session);
			return vm_path + "apply_DB_WD.vm";
		} else if (formCode == FormCodeEnum.SET_UP_UNDERWRITING) {
			viewUnderwriting(form, model, false, fetchFromCrm, session);
			return vm_path + "apply_CX.vm";
		} else if (formCode == FormCodeEnum.SET_UP_LITIGATION_PERSIONAL) {
			viewLgLitigationPersional(form, model, false, fetchFromCrm, session);
			return vm_path + "apply_SS_P.vm";
		} else if (formCode == FormCodeEnum.SET_UP_LITIGATION_ENTERPRISE) {
			viewLgLitigationEnterprise(form, model, false, fetchFromCrm, session);
			return vm_path + "apply_SS_E.vm";
		} else {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "无法匹配表单类型");
		}
	}
	
	/**
	 * 编辑立项表单
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("print.htm")
	public String print(long formId, Model model, HttpServletRequest request, HttpSession session) {
		
		//SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		FormCodeEnum formCode = form.getFormCode();
		model.addAttribute("form", form);// 表单信息
		
		setAuditHistory2Page(form, model);
		boolean fetchFromCrm = (StringUtil.equals("YES", request.getParameter("fetchFromCrm")));
		if (form.getFormCode() == FormCodeEnum.SET_UP_INNOVATIVE_PRODUCT) {
			viewGuaranteeOrEntrusted(form, model, false, fetchFromCrm, session);
			queryCommonAttachmentData(model, form.getFormId() + "_INNOVATIVE",
				CommonAttachmentTypeEnum.PROJECT_INNOVATIVE_PRODUCT);
			return vm_path + "print/print_apply_innovative_product.vm";
		} else if (formCode == FormCodeEnum.SET_UP_GUARANTEE_ENTRUSTED) {
			viewGuaranteeOrEntrusted(form, model, false, fetchFromCrm, session);
			return vm_path + "print/print_apply_DB_WD.vm";
		} else if (formCode == FormCodeEnum.SET_UP_UNDERWRITING) {
			viewUnderwriting(form, model, false, fetchFromCrm, session);
			return vm_path + "print/print_apply_CX.vm";
		} else if (formCode == FormCodeEnum.SET_UP_LITIGATION_PERSIONAL) {
			viewLgLitigationPersional(form, model, false, fetchFromCrm, session);
			return vm_path + "print/print_apply_SS_P.vm";
		} else if (formCode == FormCodeEnum.SET_UP_LITIGATION_ENTERPRISE) {
			viewLgLitigationEnterprise(form, model, false, fetchFromCrm, session);
			return vm_path + "print/print_apply_SS_E.vm";
		} else {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "无法匹配表单类型");
		}
	}
	
	/**
	 * 查看立项表单
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(Long formId, String projectCode, Integer toIndex, Model model,
						HttpServletRequest request, HttpSession session) {
		
		// SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		
		// 添加兼容，首页进入时只有projectCode 没有formId
		if (formId == null || formId <= 0) {
			if (StringUtil.isNotBlank(projectCode)) {
				FProjectInfo fProjectInfo = projectSetupServiceClient
					.queryProjectByCode(projectCode);
				if (fProjectInfo != null) {
					formId = fProjectInfo.getFormId();
				}
			}
		}
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("enterpriseNature", EnterpriseNatureEnum.getAllEnum());
		model.addAttribute("enterpriseScale", EnterpriseScaleEnum.getAllEnum());
		
		if (toIndex == null)
			toIndex = 0;
		
		if (form.getStatus() == FormStatusEnum.APPROVAL) {
			setAuditHistory2Page(form, model);
		}
		boolean fetchFromCrm = (StringUtil.equals("YES", request.getParameter("fetchFromCrm")));
		model.addAttribute("toIndex", toIndex);
		//风险提示消息查询
		verifyMsgQuery("LX" + formId, model);
		if (form.getFormCode() == FormCodeEnum.SET_UP_INNOVATIVE_PRODUCT) {
			viewGuaranteeOrEntrusted(form, model, false, fetchFromCrm, session);
			queryCommonAttachmentData(model, form.getFormId() + "_INNOVATIVE",
				CommonAttachmentTypeEnum.PROJECT_INNOVATIVE_PRODUCT);
			return vm_path + "view_apply_innovative_product.vm";
		} else if (form.getFormCode() == FormCodeEnum.SET_UP_GUARANTEE_ENTRUSTED) {
			return viewGuaranteeOrEntrusted(form, model, false, fetchFromCrm, session);
		} else if (form.getFormCode() == FormCodeEnum.SET_UP_UNDERWRITING) {
			return viewUnderwriting(form, model, false, fetchFromCrm, session);
		} else if (form.getFormCode() == FormCodeEnum.SET_UP_LITIGATION_PERSIONAL) {
			return viewLgLitigationPersional(form, model, false, fetchFromCrm, session);
		} else if (form.getFormCode() == FormCodeEnum.SET_UP_LITIGATION_ENTERPRISE) {
			return viewLgLitigationEnterprise(form, model, false, fetchFromCrm, session);
		} else {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
	}
	
	/**
	 * 审核-选择法务经理
	 * @param formId
	 * @param request
	 * @param toIndex
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("audit/legalManager.htm")
	public String legalManager(long formId, HttpServletRequest request, Integer toIndex,
								Model model, HttpSession session) {
		model.addAttribute("chooseLegalManager", BooleanEnum.YES.code());
		return audit(formId, request, toIndex, model, session);
	}
	
	/**
	 * 审核-选择业务经理B
	 * @param formId
	 * @param request
	 * @param toIndex
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("audit/busiManagerb.htm")
	public String busiManagerb(long formId, HttpServletRequest request, Integer toIndex,
								Model model, HttpSession session) {
		model.addAttribute("chooseBusiManagerb", BooleanEnum.YES.code());
		return audit(formId, request, toIndex, model, session);
	}
	
	/**
	 * 审核 - 选择风险经理
	 * @param formId
	 * @param request
	 * @param toIndex
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("audit/riskManager.htm")
	public String riskManager(long formId, HttpServletRequest request, Integer toIndex,
								Model model, HttpSession session) {
		FProjectGuaranteeEntrustedInfo project = projectSetupServiceClient
			.queryGuaranteeEntrustedProjectByFormId(formId);
		if (project == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
		}
		
		if (project.getHasEvaluateCompany() == BooleanEnum.IS
			&& project.getEvaluateCompanyRegion() == EvaluateCompanyRegionEnum.OUTSIDE_CITY) {
			model.addAttribute("evaluateCompany", project.getEvaluateCompanyRegion());
		}
		model.addAttribute("chooseRiskManager", BooleanEnum.YES.code());
		return audit(formId, request, toIndex, model, session);
	}
	
	/**
	 * 审核- 选择评估公司
	 * @param formId
	 * @param request
	 * @param toIndex
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("audit/evaluateCompany.htm")
	public String evaluateCompany(long formId, HttpServletRequest request, Integer toIndex,
									Model model, HttpSession session) {
		
		FProjectGuaranteeEntrustedInfo project = projectSetupServiceClient
			.queryGuaranteeEntrustedProjectByFormId(formId);
		if (project == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
		}
		
		if (project.getHasEvaluateCompany() == BooleanEnum.IS) {
			model.addAttribute("evaluateCompany", project.getEvaluateCompanyRegion());
		}
		
		return audit(formId, request, toIndex, model, session);
	}
	
	/**
	 * 查看立项表单
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	
	@RequestMapping("audit.htm")
	public String audit(long formId, HttpServletRequest request, Integer toIndex, Model model,
						HttpSession session) {
		
		// SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("enterpriseNature", EnterpriseNatureEnum.getAllEnum());
		model.addAttribute("enterpriseScale", EnterpriseScaleEnum.getAllEnum());
		
		if (toIndex == null)
			toIndex = 0;
		model.addAttribute("toIndex", toIndex);
		
		initWorkflow(model, form, request.getParameter("taskId"));
		//风险提示消息查询
		verifyMsgQuery("LX" + formId, model);
		if (form.getFormCode() == FormCodeEnum.SET_UP_INNOVATIVE_PRODUCT) {
			viewGuaranteeOrEntrusted(form, model, true, false, session);
			queryCommonAttachmentData(model, form.getFormId() + "_INNOVATIVE",
				CommonAttachmentTypeEnum.PROJECT_INNOVATIVE_PRODUCT);
			return vm_path + "audit_apply_innovative_product.vm";
		} else if (form.getFormCode() == FormCodeEnum.SET_UP_GUARANTEE_ENTRUSTED) {
			viewGuaranteeOrEntrusted(form, model, true, false, session);
			return vm_path + "audit_apply_DB_WD.vm";
		} else if (form.getFormCode() == FormCodeEnum.SET_UP_UNDERWRITING) {
			viewUnderwriting(form, model, true, false, session);
			return vm_path + "audit_apply_CX.vm";
		} else if (form.getFormCode() == FormCodeEnum.SET_UP_LITIGATION_PERSIONAL) {
			viewLgLitigationPersional(form, model, true, false, session);
			return vm_path + "audit_apply_SS_P.vm";
		} else if (form.getFormCode() == FormCodeEnum.SET_UP_LITIGATION_ENTERPRISE) {
			viewLgLitigationEnterprise(form, model, true, false, session);
			return vm_path + "audit_apply_SS_E.vm";
		} else {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
	}
	
	@RequestMapping("checkCustomerOccupy.json")
	@ResponseBody
	public JSONObject checkCustomerOccupy(Long customerId, String customerName,
											HttpServletRequest request) {
		JSONObject result = new JSONObject();
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		try {
			
			if (sessionLocal == null) {
				result.put("success", false);
				result.put("message", "当前未登录或者已掉线");
				return result;
			}
			
			if (customerId == null || customerId == 0) {
				result.put("success", false);
				result.put("message", "检查客户占有情况出错[客户ID不能为空]");
				return result;
			} else {
				
				//如果客户的客户经理就是自己就不用去检查立项了？
				CustomerDetailInfo customer = customerServiceClient.queryByUserId(customerId);
				if (customer == null) {
					result.put("success", false);
					result.put("message", "客户不存在");
					return result;
				}
				
				if (customer.getCustomerManagerId() == sessionLocal.getUserId()) {
					result.put("success", true);
					result.put("message", "可立项");
					return result;
				} else if (ListUtil.isNotEmpty(customer.getCustomerManagerIds())) {
					
					for (long managerId : customer.getCustomerManagerIds()) {
						if (managerId == sessionLocal.getUserId()) {
							result.put("success", true);
							result.put("message", "可立项");
							return result;
						}
					}
					
					result.put("success", false);
					result.put("message", "客户 [" + customer.getCustomerName() + "]已存在客户经理");
					return result;
				}
				
				SetupFormQueryOrder queryOrder = new SetupFormQueryOrder();
				queryOrder.setPageSize(999);
				queryOrder.setCustomerId(customerId);
				QueryBaseBatchResult<SetupFormProjectInfo> setupForm = projectSetupServiceClient
					.querySetupForm(queryOrder);
				
				long setupCount = 0; //其他独占数量
				if (setupForm != null && setupForm.getTotalCount() > 0) {
					for (SetupFormProjectInfo sf : setupForm.getPageList()) {
						//授信客户独占数量
						if (sf.getTrueCustomerId() <= 0
							&& sessionLocal.getUserId() != sf.getFormUserId()
							&& sf.getFormStatus() != FormStatusEnum.DELETED
							&& sf.getFormStatus() != FormStatusEnum.DENY
							&& sf.getFormStatus() != FormStatusEnum.END) {
							setupCount++;
						}
					}
				}
				queryOrder.setCustomerId(0);
				queryOrder.setTrueCustomerId(customerId);
				setupForm = projectSetupServiceClient.querySetupForm(queryOrder);
				if (setupForm != null && setupForm.getTotalCount() > 0) {
					for (SetupFormProjectInfo sf : setupForm.getPageList()) {
						//用信客户独占数量
						if (sessionLocal.getUserId() != sf.getFormUserId()
							&& sf.getFormStatus() != FormStatusEnum.DELETED
							&& sf.getFormStatus() != FormStatusEnum.DENY
							&& sf.getFormStatus() != FormStatusEnum.END) {
							setupCount++;
						}
					}
				}
				
				if (setupCount > 0) {
					result.put("success", false);
					if (StringUtil.isNotEmpty(customerName)) {
						result.put("message", "客户 [" + customer.getCustomerName() + "]已由其他客户经理立项");
					} else {
						result.put("message", "客户已由其他客户经理立项");
					}
				} else {
					result.put("success", true);
					result.put("message", "可立项");
				}
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "检查客户占有情况出错");
			logger.error("检查客户占有情况出错：{}", e);
			return result;
		}
		return result;
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
	 * 获取股权结构
	 * @param formId
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<FProjectEquityStructureInfo> getEquityStructure(long formId, HttpSession session) {
		List<FProjectEquityStructureInfo> list = projectSetupServiceClient
			.queryEquityStructureByFormId(formId);
		if (ListUtil.isEmpty(list) && session != null
			&& session.getAttribute("initStructure") != null) {
			List<CompanyOwnershipStructureInfo> structre = (List<CompanyOwnershipStructureInfo>) session
				.getAttribute("initStructure");
			list = Lists.newArrayList();
			for (CompanyOwnershipStructureInfo struc : structre) {
				FProjectEquityStructureInfo sInfo = new FProjectEquityStructureInfo();
				sInfo.setEquityRatio(NumberUtil.parseDouble(struc.getEquity()));//股权比例
				sInfo.setStockholder(struc.getShareholdersName());//主要股东名称
				sInfo.setContributionWay(struc.getMethord()); //出资方式
				sInfo.setCapitalContributions(struc.getAmount());//出资额
				sInfo.setAmountType(struc.getAmountType());
				list.add(sInfo);
			}
		}
		return list;
	}
	
	/**
	 * 查看担保委贷项目详情
	 * 
	 * @param form
	 * @param model
	 * @return
	 */
	private String viewGuaranteeOrEntrusted(FormInfo form, Model model, boolean fromAudit,
											boolean fetchFromCrm, HttpSession session) {
		
		FProjectGuaranteeEntrustedInfo project = projectSetupServiceClient
			.queryGuaranteeEntrustedProjectByFormId(form.getFormId());
		
		model.addAttribute("project", project);// 申请贷款/担保情况
		
		FProjectCustomerBaseInfo customerInfo = getCustomerBaseInfo(form.getFormId(), session);
		//能编辑的时候取客户的最新数据编辑
		if (fetchFromCrm
			&& (form.getStatus() == FormStatusEnum.DRAFT
				|| form.getStatus() == FormStatusEnum.CANCEL || form.getStatus() == FormStatusEnum.BACK)) {
			setCustomerInfoFromCrm(customerInfo, model);
		} else {
			model.addAttribute("customerBaseInfo", customerInfo);// 客户基本情况
			model.addAttribute("equityStructure", getEquityStructure(form.getFormId(), session));// 股权结构
		}
		model.addAttribute("bankLoan",
			projectSetupServiceClient.queryBankLoanByFormId(form.getFormId()));// 银行贷款情况
		model.addAttribute("externalGuarantee",
			projectSetupServiceClient.queryExternalGuaranteeByFormId(form.getFormId()));// 对外担保情况
		
		List<FProjectCounterGuaranteePledgeInfo> list = projectSetupServiceClient
			.queryCounterGuaranteePledgeByFormId(form.getFormId());
		if (list != null) {
			List<FProjectCounterGuaranteePledgeInfo> counterGuaranteePledge = Lists.newArrayList();
			List<FProjectCounterGuaranteePledgeInfo> counterGuaranteeMortgage = Lists
				.newArrayList();
			for (FProjectCounterGuaranteePledgeInfo info : list) {
				if (info.getType() == GuaranteeTypeEnum.PLEDGE) {
					counterGuaranteePledge.add(info);
				} else {
					counterGuaranteeMortgage.add(info);
				}
			}
			model.addAttribute("counterGuaranteePledge", counterGuaranteePledge); //反担保/担保情况 - 抵押
			model.addAttribute("counterGuaranteeMortgage", counterGuaranteeMortgage); //反担保/担保情况 - 质押
			
		}
		
		model.addAttribute("counterGuaranteeGuarantor",
			projectSetupServiceClient.queryCounterGuaranteeGuarantorByFormId(form.getFormId()));// 反担保/担保情况 - 保证人
		
		//委贷项目 默认资金渠道未进出口担保
		if (ProjectUtil.isEntrusted(project.getBusiType())) {
			String channalCode = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.ENTRUSTED_DEFAULT_CHANNEL.code());
			ChannalInfo channel = channalClient.queryByChannalCode(channalCode);
			if (channel != null) {
				model.addAttribute("entrustedDefaultChannelId", channel.getId());
				model.addAttribute("entrustedDefaultChannelCode", channel.getChannelCode());
				model.addAttribute("entrustedDefaultChannelType", channel.getChannelType());
				model.addAttribute("entrustedDefaultChannelName", channel.getChannelName());
			}
		}
		
		//查询反担保附件
		queryCommonAttachmentData(model, String.valueOf(form.getFormId()),
			CommonAttachmentTypeEnum.COUNTER_GUARANTEE);
		
		return vm_path + "view_apply_DB_WD.vm";
	}
	
	/**
	 * 查询诉讼保函-个人项目详情
	 * @param form
	 * @param model
	 * @return
	 */
	private String viewLgLitigationPersional(FormInfo form, Model model, boolean fromAudit,
												boolean fetchFromCrm, HttpSession session) {
		
		FProjectLgLitigationInfo project = projectSetupServiceClient
			.queryLgLitigationProjectByFormId(form.getFormId());
		
		model.addAttribute("project", project);// 保全信息
		
		if (!fromAudit && !DataPermissionUtil.hasViewPermission(project.getProjectCode())) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS, "无权访问");
		}
		
		FProjectCustomerBaseInfo customerInfo = getCustomerBaseInfo(form.getFormId(), session);
		//能编辑的时候取客户的最新数据编辑
		if (fetchFromCrm
			&& (form.getStatus() == FormStatusEnum.DRAFT
				|| form.getStatus() == FormStatusEnum.CANCEL || form.getStatus() == FormStatusEnum.BACK)) {
			setCustomerInfoFromCrm(customerInfo, model);
		} else {
			model.addAttribute("customerBaseInfo", customerInfo);// 客户基本情况
		}
		
		model.addAttribute("bankLoan",
			projectSetupServiceClient.queryBankLoanByFormId(form.getFormId()));// 银行贷款情况
		model.addAttribute("externalGuarantee",
			projectSetupServiceClient.queryExternalGuaranteeByFormId(form.getFormId()));// 对外担保情况
		
		return vm_path + "view_apply_SS_P.vm";
	}
	
	/**
	 * 查询诉讼保函-企业项目详情
	 * @param form
	 * @param model
	 * @return
	 */
	private String viewLgLitigationEnterprise(FormInfo form, Model model, boolean fromAudit,
												boolean fetchFromCrm, HttpSession session) {
		
		FProjectLgLitigationInfo project = projectSetupServiceClient
			.queryLgLitigationProjectByFormId(form.getFormId());
		
		model.addAttribute("project", project);// 保全信息
		
		FProjectCustomerBaseInfo customerInfo = getCustomerBaseInfo(form.getFormId(), session);
		//能编辑的时候取客户的最新数据编辑
		if (fetchFromCrm
			&& (form.getStatus() == FormStatusEnum.DRAFT
				|| form.getStatus() == FormStatusEnum.CANCEL || form.getStatus() == FormStatusEnum.BACK)) {
			setCustomerInfoFromCrm(customerInfo, model);
		} else {
			model.addAttribute("customerBaseInfo", customerInfo);// 客户基本情况
			model.addAttribute("equityStructure", getEquityStructure(form.getFormId(), session));// 股权结构
		}
		
		model.addAttribute("bankLoan",
			projectSetupServiceClient.queryBankLoanByFormId(form.getFormId()));// 银行贷款情况
		model.addAttribute("externalGuarantee",
			projectSetupServiceClient.queryExternalGuaranteeByFormId(form.getFormId()));// 对外担保情况
		
		return vm_path + "view_apply_SS_E.vm";
	}
	
	/**
	 * 查看承销项目详情
	 * 
	 * @param form
	 * @param model
	 * @return
	 */
	private String viewUnderwriting(FormInfo form, Model model, boolean fromAudit,
									boolean fetchFromCrm, HttpSession session) {
		
		FProjectUnderwritingInfo project = projectSetupServiceClient
			.queryUnderwritingProjectByFormId(form.getFormId());
		
		if (!fromAudit && !DataPermissionUtil.hasViewPermission(project.getProjectCode())) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS, "无权访问");
		}
		
		model.addAttribute("project", project);// 承销信息
		
		model.addAttribute("form", form);// 表单信息
		
		FProjectCustomerBaseInfo customerInfo = getCustomerBaseInfo(form.getFormId(), session);
		//能编辑的时候取客户的最新数据编辑
		if (fetchFromCrm
			&& (form.getStatus() == FormStatusEnum.DRAFT
				|| form.getStatus() == FormStatusEnum.CANCEL || form.getStatus() == FormStatusEnum.BACK)) {
			setCustomerInfoFromCrm(customerInfo, model);
		} else {
			model.addAttribute("customerBaseInfo", customerInfo);// 客户基本情况
			model.addAttribute("equityStructure", getEquityStructure(form.getFormId(), session));// 股权结构
		}
		
		return vm_path + "view_apply_CX.vm";
	}
}
