package com.born.fcs.face.web.controller.project.contract;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.born.fcs.am.ws.info.pledgetype.PledgeTypeSimpleInfo;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.pm.service.common.SysParameterServiceClient;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.pm.ws.enums.*;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.*;
import com.born.fcs.pm.ws.order.contract.FContractTemplateOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.am.ws.info.pledgetype.PledgeTypeInfo;
import com.born.fcs.am.ws.order.pledgetype.PledgeTypeQueryOrder;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.util.EscapeUtil;
import com.born.fcs.face.web.util.HtmlToWord;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.info.contract.ContractTemplateInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractItemInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContrctInfo;
import com.born.fcs.pm.ws.info.council.CouncilInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectGuarantorInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectPledgeAssetInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.InvestigationInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.info.setup.FProjectCustomerBaseInfo;
import com.born.fcs.pm.ws.order.contract.ContractTemplateOrder;
import com.born.fcs.pm.ws.order.contract.ContractTemplateQueryOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractItemOrder;
import com.born.fcs.pm.ws.order.finvestigation.InvestigationQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.contract.ProjectContractCheckMessageResult;
import com.born.fcs.pm.ws.service.investigation.InvestigationService;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.ListUtil;

import freemarker.cache.StringTemplateLoader;
import freemarker.core.Environment;
import freemarker.core.InvalidReferenceException;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

@Controller
@RequestMapping("projectMg/contract")
public class ContractTemplateController extends WorkflowBaseController {
	
	final static String vm_path = "/projectMg/assistSys/ContractMg/";
	
	@Autowired
	private InvestigationService investigationWebService;
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "operateDate" };
	}
	
	/**
	 * 合同模板管理列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("templateList.htm")
	public String templateList(ContractTemplateQueryOrder order, Model model) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		order.setDraftUserId(sessionLocal.getUserId());
		QueryBaseBatchResult<ContractTemplateInfo> batchResult = contractTemplateServiceClient
			.query(order);
		model.addAttribute("conditions", order);
		model.addAttribute("templateStatus", ContractTemplateStatusEnum.getAllEnum());
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		model.addAttribute("letterTypeEnum", LetterTypeEnum.getAllEnum());
		return vm_path + "list.vm";
	}
	
	/**
	 * 合同初始化
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("addTemplate.htm")
	public String addTemplate(String templateType,HttpServletRequest request, HttpServletResponse response, Model model) {
		ContractTemplateInfo info = new ContractTemplateInfo();
		if(templateType==null){
			info.setTemplateType(TemplateTypeEnum.CONTRACT);
		}else{
			info.setTemplateType(TemplateTypeEnum.getByCode(templateType));
		}
		model.addAttribute("pledgeType", getPledgeType());
		model.addAttribute("info", info);
		return vm_path + "add_contract_template.vm";
	}

	/**
	 * 法务部新增合同需要走流程
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("legalTemplate.htm")
	public String addTemplate(Long id,HttpServletRequest request, HttpServletResponse response, Model model) {
		String isProcess="IS";
		ContractTemplateInfo info = new ContractTemplateInfo();
			info.setTemplateType(TemplateTypeEnum.CONTRACT);
		if(id!=null&&id>0){
			info=contractTemplateServiceClient.findById(id);
			info.setTemplateId(0);
			info.setParentId(id);
			info.setFormId(0);
			if(StringUtil.isNotEmpty(info.getTemplateFile())) {
				info.setTemplateFile(cpoyFile(info.getTemplateFile()));
			}
		}
		info.setIsProcess(isProcess);
		model.addAttribute("pledgeType", getPledgeType());
		model.addAttribute("info", info);
		model.addAttribute("requestUrl", getRequestUrl(request));
		return vm_path + "add_contract_template.vm";
	}

	/**
	 * 进入编辑合同模板
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("modifyTemplate.htm")
	public String modifyTemplate(Long id, HttpServletRequest request, HttpServletResponse response,
									Model model) {
		ContractTemplateInfo info = contractTemplateServiceClient.findById(id);
		FormInfo form=null;
		if(info.getFormId()>0){
			form = formServiceClient.findByFormId(info.getFormId());
		}
		model.addAttribute("pledgeType", getPledgeType());
		model.addAttribute("info", info);
		model.addAttribute("requestUrl", getRequestUrl(request));
		model.addAttribute("form", form);
		return vm_path + "add_contract_template.vm";
	}

	/**
	 * 强制编辑
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("forcedModifyTemplate.htm")
	public String forcedModifyTemplate(Long id, HttpServletRequest request, HttpServletResponse response,
								 Model model) {
		ContractTemplateInfo info = contractTemplateServiceClient.findById(id);
		model.addAttribute("pledgeType", getPledgeType());
		model.addAttribute("info", info);
		model.addAttribute("requestUrl", getRequestUrl(request));
		model.addAttribute("isForcedModify","IS");
		return vm_path + "add_contract_template.vm";
	}
	
	/**
	 * 保存合同模板
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveTemplate.htm")
	@ResponseBody
	public JSONObject saveTemplate(ContractTemplateOrder order, Model model) {
		String tipPrefix = " 保存合同模板";
		JSONObject jsonObject = new JSONObject();
		try {
			if (null == order.getIsMain()) {
				order.setIsMain("NO");
			}
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			ContractTemplateQueryOrder queryOrder = new ContractTemplateQueryOrder();
			queryOrder.setBusiType(order.getBusiType());
			queryOrder.setIsMain("IS");
			List<String> statusList=Lists.newArrayList();
			statusList.add(ContractTemplateStatusEnum.BACK.code());
			statusList.add(ContractTemplateStatusEnum.CANCEL.code());
			statusList.add(ContractTemplateStatusEnum.SUBMIT.code());
			statusList.add(ContractTemplateStatusEnum.AUDITING.code());
			statusList.add(ContractTemplateStatusEnum.CHECKED.code());
			statusList.add(ContractTemplateStatusEnum.IN_USE.code());
			queryOrder.setStatusList(statusList);
			QueryBaseBatchResult<ContractTemplateInfo> batchResult = contractTemplateServiceClient
					.query(queryOrder);
			//一个业务类型只能有一个主合同校验
			boolean isHaveMain=false;
			if ("IS".equals(order.getIsMain())) {
				if (order.getTemplateId() != null && order.getTemplateId() > 0) {//编辑的时候
					ContractTemplateInfo contractTemplateInfo = contractTemplateServiceClient
						.findById(order.getTemplateId());
					//修订原来是主合同
					if(order.getParentId()!=null&&order.getParentId()>0){
						ContractTemplateInfo oldContract = contractTemplateServiceClient
								.findById(order.getParentId());
						if(!"IS".equals(oldContract.getIsMain())&&batchResult != null && batchResult.getPageList().size() > 0){
							isHaveMain=true;
						}
					}else {
						if (!contractTemplateInfo.getIsMain().equals("IS")//原来不是主合同,现在是合同
								|| (contractTemplateInfo.getIsMain().equals("IS")
								&& (contractTemplateInfo.getStatus().code().equals("DRAFT")
								|| contractTemplateInfo.getStatus().code().equals("CANCEL")
								|| contractTemplateInfo.getStatus().code().equals("BACK")))//原来是暂存、撤销、驳回主合同
								|| (order.getBusiType() != null
								&& contractTemplateInfo.getBusiType() != null
								&& !order.getBusiType().equals(contractTemplateInfo.getBusiType()))) {//编辑前不是主合同或者业务类型变了
							if (batchResult != null && batchResult.getPageList().size() > 0) {
								isHaveMain = true;
							}
						}
					}
				} else {//直接新增
					//修订原来是主合同
					if(order.getParentId()!=null&&order.getParentId()>0) {
						ContractTemplateInfo oldContract = contractTemplateServiceClient
								.findById(order.getParentId());
						if (!"IS".equals(oldContract.getIsMain()) && batchResult != null && batchResult.getPageList().size() > 0) {
							isHaveMain = true;
						}
					}else {
						if (batchResult != null && batchResult.getPageList().size() > 0) {
							isHaveMain = true;
						}
					}
				}
			}
			if(isHaveMain){
				jsonObject.put("success", false);
				jsonObject.put("message", "该业务类型已经添加主合同");
				return jsonObject;
			}
			//校验结束
			// 模版内容转码
			String templateContent = order.getTemplateContent();
			if (StringUtil.isNotBlank(templateContent)) {
				String STR2 = StringEscapeUtils.unescapeHtml(EscapeUtil.unescape(templateContent));
				order.setTemplateContent(STR2);
			}
			if(order.getTemplateId()==null||order.getTemplateId()==0){
			order.setUserId(sessionLocal.getUserId());
			order.setUserName(sessionLocal.getRealName());
			order.setUserAccount(sessionLocal.getUserName());
			}
			if("IS".equals(order.getIsProcess())){//需要走流程
				FContractTemplateOrder fOrder=new FContractTemplateOrder();
				BeanCopier.staticCopy(order, fOrder);
				fOrder.setCheckIndex(0);
				fOrder.setCheckStatus(order.getCheckStatus());
				fOrder.setFormId(order.getFormId());
				fOrder.setParentId(order.getParentId());
				fOrder.setFormCode(FormCodeEnum.CONTRACT_TEMPLATE);
				FormBaseResult saveResult = fContractTemplateServiceClient.save(fOrder);
				jsonObject = toJSONResult(jsonObject, saveResult, "保存合同成功", null);
				jsonObject.put("formId", saveResult.getFormInfo().getFormId());
			}else{
				FcsBaseResult saveResult = contractTemplateServiceClient.save(order);
				jsonObject = toJSONResult(jsonObject, saveResult, tipPrefix + "成功", null);
			}
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error(tipPrefix + "出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 合同模板详情
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("viewTemplate.htm")
	public String viewTemplate(Long id,HttpServletRequest request, Model model) {
		String tipPrefix = "合同模板详情";
		try {
			ContractTemplateInfo templateInfo = contractTemplateServiceClient.findById(id);
			if(templateInfo.getFormId()>0){
				FormInfo form=formServiceClient.findByFormId(templateInfo.getFormId());
				model.addAttribute("form", form);
			}
			model.addAttribute("info", templateInfo);
			model.addAttribute("requestUrl", getRequestUrl(request));
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		return vm_path + "view_contract_template.vm";
	}
	/**
	 * 审核
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("auditTemplate.htm")
	public String auditTemplate(long formId, HttpServletRequest request, Model model, HttpSession session) {

		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		ContractTemplateInfo templateInfo = contractTemplateServiceClient.findByFormId(formId);
		model.addAttribute("info", templateInfo);
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", form.getFormCode());
		initWorkflow(model, form, Long.toString(form.getTaskId()));
		model.addAttribute("isAudit", true);
		model.addAttribute("requestUrl", getRequestUrl(request));
		return vm_path + "audit_contract_template.vm";
	}

	/**
	 * 法务经理审核
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("auditTemplateWithLegalManager.htm")
	public String auditTemplateWithLegalManager(long formId, HttpServletRequest request, Model model, HttpSession session) {

		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		ContractTemplateInfo templateInfo = contractTemplateServiceClient.findByFormId(formId);
		model.addAttribute("info", templateInfo);
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", form.getFormCode());
		initWorkflow(model, form, Long.toString(form.getTaskId()));
		model.addAttribute("isAudit", true);
		model.addAttribute("isLegalManager", "IS");
		model.addAttribute("requestUrl", getRequestUrl(request));
		return vm_path + "audit_contract_template.vm";
	}

	/**
	 * 删除合同模板
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("deleteTemplate.htm")
	@ResponseBody
	public JSONObject deleteTemplate(Long id, Model model) {
		String tipPrefix = "删除合同模板";
		ContractTemplateOrder order = new ContractTemplateOrder();
		order.setTemplateId(id);
		JSONObject result = new JSONObject();
		try {
			FcsBaseResult delResult = contractTemplateServiceClient.deleteById(order);
			if (delResult.isSuccess()) {
				result.put("success", true);
				result.put("message", "删除成功");
			} else {
				result.put("success", false);
				result.put("message", delResult.getMessage());
			}
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
	
	/**
	 * 选择合同模板
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping("loadContractTemplate.json")
	@ResponseBody
	public JSONObject loadContractTemplate(ContractTemplateQueryOrder order,HttpServletRequest request) {
		String tipPrefix = "选择合同模板";
		JSONObject result = new JSONObject();
		try {
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			order.setIsChooseNull("IS");
			if (null != order.getBusiType() && order.getBusiType().equals("211")) {
				List<Long> busiTypeList = Lists.newArrayList();
				busiTypeList.add(new Long(211));
				order.setBusiTypeList(busiTypeList);
			} else {
				if (null != order.getBusiType()) {
					List<Long> busiTypeList = Lists.newArrayList();
					for (int i = 0; i < order.getBusiType().length(); i++) {
						busiTypeList.add(Long.parseLong(order.getBusiType().substring(0,
							order.getBusiType().length() - i)));
					}
					
					order.setBusiTypeList(busiTypeList);
				}
			}
			//担保函出具
			String isDBL=request.getParameter("isAddDBL");
			if(StringUtil.equals(isDBL,"IS")){//添加默认担保函
				String dblNames= sysParameterServiceClient.getSysParameterValue(SysParamEnum.SYS_PARAM_GUARANTEE_LETTER_NAME.code());
				if(StringUtil.isBlank(dblNames)){
					result.put("success", false);
					result.put("message", "请联系管理员配置系统参数[SYS_PARAM_GUARANTEE_LETTER_NAME]默认担保函样本的名称");
					return result;
				}else{
				List<String> nameList=Lists.newArrayList();
				String names[]=dblNames.split(",");
				for(String name:names){
					if(StringUtil.isNotBlank(name)){
						nameList.add(name);
					}
				}
				order.setNameList(nameList);
				}
			}

			QueryBaseBatchResult<ContractTemplateInfo> batchResult = contractTemplateServiceClient
				.query(order);
			List<ContractTemplateInfo> list = batchResult.getPageList();

			for (ContractTemplateInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("templateId", info.getTemplateId());
				json.put("name", info.getName());
				json.put("contractType", info.getContractType());
				json.put("busiType", info.getBusiType());
				json.put("busiTypeName", info.getBusiTypeName());
				json.put("isMain", info.getIsMain());
				json.put("creditConditionType", info.getCreditConditionType());
				json.put("pledgeType", info.getPledgeType());
				json.put("stampPhase", info.getStampPhase());
				json.put("templateContent", info.getTemplateContent());
				json.put("status", info.getStatus().code());
				json.put("templateFile", info.getTemplateFile());
				json.put("templateType", info.getTemplateType());
				json.put("templateTypeMessage", info.getTemplateType()==null?null:info.getTemplateType().message());
				json.put("letterType", info.getLetterType());
				json.put("letterTypeMessage", info.getLetterType()==null?null:info.getLetterType().message());
				dataList.add(json);
			}
			data.put("pageCount", batchResult.getPageCount());
			data.put("pageNumber", batchResult.getPageNumber());
			data.put("pageSize", batchResult.getPageSize());
			data.put("totalCount", batchResult.getTotalCount());
			data.put("pageList", dataList);
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		
		return result;
		
	}
	
	/**
	 * 进入填写制式合同
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("fullStandardContract.htm")
	public String fullProjectContract(String projectCode, Long contractId, Long contractItemId,
										Long templateId, String isChecker, Model model) {
		
		model.addAttribute("isChecker", isChecker);
		// 获取制式合同需要的信息
		// 1.基础合同集信，合同编号，项目名称 f_project_contract
		ProjectContrctInfo projectContrct = new ProjectContrctInfo();
		if (contractId != null && contractId > 0) {
			
			projectContrct = projectContractServiceClient.findById(contractId);
			model.addAttribute("projectContrct", projectContrct);
		}
		// 2.基础合同信息 ，这个貌似没用，详情才取这个。
		ProjectContractItemInfo projectContractItem = new ProjectContractItemInfo();
		if (contractItemId != null && contractItemId > 0) {
			
			projectContractItem = projectContractServiceClient.findContractItemById(contractItemId);
			model.addAttribute("projectContractItem", projectContractItem);
		}
		// 3.取出合同模版信息。
		if (projectContractItem != null && projectContractItem.getId() > 0) {
			// 以合同中的模版id为准，若没有合同信息，以传入的模版为准，兼容合同模版的查看
			templateId = projectContractItem.getTemplateId();
		}
		ContractTemplateInfo contractTemplate = new ContractTemplateInfo();
		// 授信条件 抵押/质押
		FCouncilSummaryProjectPledgeAssetInfo councilSummaryProjectPledgeAsset = new FCouncilSummaryProjectPledgeAssetInfo();
		// 授信条件 保证人
		FCouncilSummaryProjectGuarantorInfo councilSummaryProjectGuarantor = new FCouncilSummaryProjectGuarantorInfo();
		if (templateId != null && templateId > 0) {
			contractTemplate = contractTemplateServiceClient.findById(templateId);
			model.addAttribute("contractTemplate", contractTemplate);
			// 判断，若不是制式合同，不允许进入制式合同填写页面
			if (ContractTemplateTypeEnum.STANDARD != contractTemplate.getContractType()) {
				logger.error("不是制式合同，不允许进入制式合同填写页面!");
				return "/error.vm";
			}
			//			f_project_counter_guarantee_guarantor 保证人
			//			f_project_counter_guarantee_pledge 抵押/质押
			// 抓去关联授信错措施
			if (StringUtil.isNotEmpty(projectContractItem.getCreditMeasure())) {
				//  抓去抵押人和保证人
				String[] strs = projectContractItem.getCreditMeasure().split(",");
				for (String str : strs) {
					// 判断是要获取抵押质押还是保证人	
					// 获取项目授信条件数据，用于判断是抵押还是质押
					//					ProjectCreditConditionInfo projectCreditConditionInfo = projectCreditConditionServiceClient
					//						.findByProjectCodeAndItemId(projectCode, NumberUtil.parseLong(str));
					ProjectCreditConditionInfo projectCreditConditionInfo = projectCreditConditionServiceClient
						.findById(NumberUtil.parseLong(str));
					if (projectCreditConditionInfo != null) {
						// 若是抵押/质押
						if (CreditConditionTypeEnum.MORTGAGE == projectCreditConditionInfo
							.getType()
							|| CreditConditionTypeEnum.PLEDGE == projectCreditConditionInfo
								.getType()) {
							//							councilSummaryProjectPledge = councilSummaryServiceClient
							//								.queryPledgeById(projectCreditConditionInfo.getItemId());
							//							
							councilSummaryProjectPledgeAsset = councilSummaryServiceClient
								.queryPledgeAssetById(projectCreditConditionInfo.getItemId());
							
							model.addAttribute("councilSummaryProjectPledge",
								councilSummaryProjectPledgeAsset);
							if (councilSummaryProjectPledgeAsset == null) {
								councilSummaryProjectPledgeAsset = new FCouncilSummaryProjectPledgeAssetInfo();
							}
						} else if (CreditConditionTypeEnum.GUARANTOR == projectCreditConditionInfo
							.getType()) {
							// 若是保证人
							councilSummaryProjectGuarantor = councilSummaryServiceClient
								.queryGuarantorById(projectCreditConditionInfo.getItemId());
							model.addAttribute("councilSummaryProjectGuarantor",
								councilSummaryProjectGuarantor);
							if (councilSummaryProjectGuarantor == null) {
								councilSummaryProjectGuarantor = new FCouncilSummaryProjectGuarantorInfo();
							}
						}
					}
				}
			}
		}
		// 根据模式抓去各项内容。
		/**
		 * 1.尽职调查报告 InvestigationService
		 * 因为尽职调查报告东西拉出来太多而且没有法人身份证号，所以从customer取数据
		 * -----------------------------------------------------------/t/n
		 * 2.会议纪要 projectService.queryDetailInfo
		 * 
		 * */
		// 1.住所 address  2.法定代表人  legalPersion 3.法定代表人身份证号码 legalPersionCertNo 
		// 4.法人住所 legalPersionAddress
		
		/// 1.保证金 project.selfDepositAmount  councilSummaryProjectLgLitigation.deposit 2.保全金额 project.amount 
		//		担保费率-----会议纪要中的担保费用
		//		抵押人---------会议纪要中对应反担保措施的权利人（根据权证号匹配过来） 从合同找
		//		委托贷款银行------会议纪要中的资金渠道  担保。资金渠道   guaranteeDetailInfo.capitalChannelName
		//		拟发行金额小写----会议纪要中的拟发行金额*1000000（此处单位为元，会议纪要中为万元 ） project. amount
		
		ProjectInfo project = new ProjectInfo();
		// 发债
		ProjectBondDetailInfo councilSummaryProjectBond = new ProjectBondDetailInfo();
		// 委贷
		ProjectEntrustedDetailInfo councilSummaryProjectEntrusted = new ProjectEntrustedDetailInfo();
		
		// 承销
		ProjectUnderwritingDetailInfo councilSummaryProjectUnderwriting = new ProjectUnderwritingDetailInfo();
		// 诉讼担保
		ProjectLgLitigationDetailInfo councilSummaryProjectLgLitigation = new ProjectLgLitigationDetailInfo();
		
		// 担保
		ProjectGuaranteeDetailInfo councilSummaryProjectGuarantee = new ProjectGuaranteeDetailInfo();
		
		FProjectCustomerBaseInfo projectCustomerBaseInfo = new FProjectCustomerBaseInfo();
		if (StringUtil.isNotBlank(projectCode)) {
			
			projectCustomerBaseInfo = projectSetupServiceClient
				.queryCustomerBaseInfoByProjectCode(projectCode);
			model.addAttribute("projectCustomerBaseInfo", projectCustomerBaseInfo);
			
			// 查询法人住址[TODO 此处和设计不相符，以后必须删除，另外，法人信息会从客户管理系统调用查询]
			if (projectCustomerBaseInfo != null && projectCustomerBaseInfo.getId() > 0) {
				InvestigationQueryOrder queryOrder = new InvestigationQueryOrder();
				queryOrder.setProjectCode(projectCode);
				queryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
				queryOrder.setPageSize(1L);
				queryOrder.setPageNumber(1L);
				QueryBaseBatchResult<InvestigationInfo> batchResult = investigationWebService
					.queryInvestigation(queryOrder);
				
				if (batchResult != null && ListUtil.isNotEmpty(batchResult.getPageList())) {
					//审核通过后的尽调表单formId
					SimpleFormProjectVOInfo info = batchResult.getPageList().get(0);
					long oldFormId = info.getFormId();
					if (oldFormId > 0) {
						FInvestigationMainlyReviewInfo fInvestigationMainlyReviewInfo = investigationWebService
							.findInvestigationMainlyReviewByFormId(oldFormId);
						if (fInvestigationMainlyReviewInfo != null
							&& fInvestigationMainlyReviewInfo.getMReviewId() > 0) {
							projectCustomerBaseInfo
								.setLegalPersionAddress(fInvestigationMainlyReviewInfo
									.getLivingAddress());
						}
						
					}
				}
				
			}
			
			try {
				project = projectServiceClient.queryByCode(projectCode, true);
				if (ProjectUtil.isBond(project.getBusiType())) { //发债
					councilSummaryProjectBond = project.getBondDetailInfo();
					model.addAttribute("councilSummaryProjectBond", councilSummaryProjectBond);
					if (councilSummaryProjectBond == null) {
						councilSummaryProjectBond = new ProjectBondDetailInfo();
					}
				} else if (ProjectUtil.isEntrusted(project.getBusiType())) { //委贷
					councilSummaryProjectEntrusted = project.getEntrustedDetailInfo();
					model.addAttribute("councilSummaryProjectEntrusted",
						councilSummaryProjectEntrusted);
					if (councilSummaryProjectEntrusted == null) {
						councilSummaryProjectEntrusted = new ProjectEntrustedDetailInfo();
					}
				} else if (ProjectUtil.isUnderwriting(project.getBusiType())) { //承销
					councilSummaryProjectUnderwriting = project.getUnderwritingDetailInfo();
					model.addAttribute("councilSummaryProjectUnderwriting",
						councilSummaryProjectUnderwriting);
					if (councilSummaryProjectUnderwriting == null) {
						councilSummaryProjectUnderwriting = new ProjectUnderwritingDetailInfo();
					}
					
				} else if (ProjectUtil.isLitigation(project.getBusiType())) { //诉讼担保
					// 抓取info。自定义
					councilSummaryProjectLgLitigation = project.getLgLitigationDetailInfo();
					model.addAttribute("projectLgLitigationDetail",
						councilSummaryProjectLgLitigation);
					// 若info未获取到，放置空
					if (councilSummaryProjectLgLitigation == null) {
						councilSummaryProjectLgLitigation = new ProjectLgLitigationDetailInfo();
					}
				} else if (ProjectUtil.isGuarantee(project.getBusiType())) { //诉讼担保
					councilSummaryProjectGuarantee = project.getGuaranteeDetailInfo();
					model.addAttribute("councilSummaryProjectGuarantee",
						councilSummaryProjectGuarantee);
					if (councilSummaryProjectGuarantee == null) {
						councilSummaryProjectGuarantee = new ProjectGuaranteeDetailInfo();
					}
				}
			} catch (Exception e) {
				//				System.out.println(e.getStackTrace());
				logger.info("抓去保证金出错：" + e.getMessage(), e);
			}
			
			model.addAttribute("project", project);
		}
		
		if (contractTemplate != null
			&& StringUtil.isNotBlank(contractTemplate.getTemplateContent())) {
			// 添加判断 ，如果合同内容里面有content内容，就不使用模版了
			if (StringUtil.isNotBlank(projectContractItem.getContent())) {
				contractTemplate.setTemplateContent(projectContractItem.getContent());
				return vm_path + "edit_Standard.vm";
			}
			Configuration cfg = new Configuration();
			StringTemplateLoader stringLoader = new StringTemplateLoader();
			String templateContent = contractTemplate.getTemplateContent();
			
			final List<String> errorMessages = new ArrayList<String>();
			final List<String> errorTitle = new ArrayList<String>();
			try {
				stringLoader.putTemplate("myTemplate", templateContent);
				
				cfg.setTemplateLoader(stringLoader);
				Template template = cfg.getTemplate("myTemplate", "utf-8");
				@SuppressWarnings("rawtypes")
				Map root = new HashMap();
				// 测试数据\
				//				contractInfo.setProjectCode("usertestprojectcode");
				//				contractInfo.setProjectName("[ppppppname");
				//				contractInfo.setCustomerId(22222222222223L);
				//				contractInfo.setCustomerName("cccccccccustom");
				// 合同集
				root.put("projectContrct", projectContrct);
				// 合同
				root.put("projectContractItem", projectContractItem);
				// 企业信息
				root.put("projectCustomerBaseInfo", projectCustomerBaseInfo);
				// 项目
				root.put("project", project);
				// 发债
				root.put("councilSummaryProjectBond", councilSummaryProjectBond);
				// 委贷
				root.put("councilSummaryProjectEntrusted", councilSummaryProjectEntrusted);
				// 承销
				root.put("councilSummaryProjectUnderwriting", councilSummaryProjectUnderwriting);
				// 诉讼担保
				root.put("councilSummaryProjectLgLitigation", councilSummaryProjectLgLitigation);
				// 担保
				root.put("councilSummaryProjectGuarantee", councilSummaryProjectGuarantee);
				// 关联授信措施----授信条件 抵押/质押
				//				root.put("councilSummaryProjectPledge", councilSummaryProjectPledgeAsset);
				root.put("councilSummaryProjectPledgeAsset", councilSummaryProjectPledgeAsset);
				// 关联授信措施----授信条件 保证人
				root.put("councilSummaryProjectGuarantor", councilSummaryProjectGuarantor);
				
				StringWriter writer = new StringWriter();
				try {
					// 添加统一异常控制。未找到的异常收集起来
					template.setTemplateExceptionHandler(new TemplateExceptionHandler() {
						
						@Override
						public void handleTemplateException(TemplateException arg0,
															Environment arg1, Writer arg2)
																							throws TemplateException {
							
							errorMessages.add(arg0.getMessage());
							if (arg0.getClass().isAssignableFrom(InvalidReferenceException.class)) {
								//System.out.println("5555555555555555555555");
								// 切分字符串，取出页面需要的数据
								String[] strs = arg0.getMessage().split(" on line ");
								String[] str2 = strs[0].split("Expression");
								errorTitle.add(str2[1]);
								//logger.info("模版载入出错：" + errorTitle);
								
							}
							//logger.info("模版载入出错全明细：" + arg0.getMessage());
							//System.out.println(arg0.getClass() + "-------------" + arg1);
						}
					});
					
					template.process(root, writer);
					//System.out.println(writer.toString());
					contractTemplate.setTemplateContent(writer.toString());
					if (ListUtil.isNotEmpty(errorMessages)) {
						logger.info("errorMessages-模版载入出错全明细:" + errorMessages);
						if (ListUtil.isNotEmpty(errorTitle)) {
							logger.info("errorTitle-模版载入出错:" + errorTitle);
						}
					}
				} catch (TemplateException e) {
					logger.error("write content fail :" + e.getMessage(), e);
					//e.printStackTrace();
				}
				
			} catch (ParseException e) {
				errorMessages.add(e.getMessage());
				errorMessages.add(e.getMessage());
				logger.error("push content fail :" + e.getMessage(), e);
				//			e.printStackTrace();
			} catch (IOException e) {
				logger.error("push content fail :" + e.getMessage(), e);
				//			e.printStackTrace();
			}
		}
		return vm_path + "edit_Standard.vm";
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	public static void main(String[] args) {

		Configuration cfg = new Configuration();
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		//String templateContent = "欢迎：${name}";
		//		需求：
		//
		//		item.title可能为null也可能为""，那么我们需要默认其值
		//
		//		实现：
		//
		//		${((item.title)?length>0)?string((item.title),"详情")}
		//
		//		如下操作只能处理为不存在或者为null的情况：
		//
		//		${item.title?default('详情')}

		///为了处理缺失变量,FreeMarker提供了两个运算符:
		///!: 指定缺失变量的默认值
		///??: 判断某个变量是否存在

		String templateContent2 = "欢迎：${council.councilId2?default('')},,,,${council.councilId3!},...${council.councilId}";
		//stringLoader.putTemplate("myTemplate", templateContent);
		final List<String> errorMessages = new ArrayList<String>();
		final List<String> errorTitle = new ArrayList<String>();
		try {
			stringLoader.putTemplate("myTemplate2", templateContent2);

			cfg.setTemplateLoader(stringLoader);

			//Template template = cfg.getTemplate("myTemplate", "utf-8");
			Template template2 = cfg.getTemplate("myTemplate2", "utf-8");
			@SuppressWarnings("rawtypes")
			Map root = new HashMap();
			//			root.put("name", "javaboy2012");
			CouncilInfo council = new CouncilInfo();
			council.setCouncilId(100);
			root.put("council", council);
			StringWriter writer = new StringWriter();
			try {
				template2.setTemplateExceptionHandler(new TemplateExceptionHandler() {

					@Override
					public void handleTemplateException(TemplateException arg0, Environment arg1,
														Writer arg2) throws TemplateException {
						errorMessages.add(arg0.getMessage());
						if (arg0.getClass().isAssignableFrom(InvalidReferenceException.class)) {
							System.out.println("5555555555555555555555");
							// 切分字符串，取出页面需要的数据
							String[] strs = arg0.getMessage().split(" on line ");
							String[] str2 = strs[0].split("Expression");
							errorTitle.add(str2[1]);
						}

						System.out.println(arg0.getClass() + "-------------" + arg1);
						System.out.println(arg0.getMessage() + "-------------" + arg1);
					}
				});
				Environment env = template2.createProcessingEnvironment(root, writer);
				//.out.println(Arrays.toString(template2.getCustomAttributeNames()));
				template2.process(root, writer);
				System.out.println(writer.toString());
				System.out.println(errorMessages);
				System.out.println(errorTitle);
			} catch (TemplateException e) {
				e.printStackTrace();
			}

		} catch (ParseException e) {
			errorMessages.add(e.getMessage());
			errorTitle.add(e.getMessage());
			//logger.error("push content fail :" + e.getMessage(), e);
			System.out.println("errorMessages:" + errorMessages);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 填写制式合同提交
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveStandardContract.htm", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject saveStandardContract(String isChecker, HttpServletRequest request,
											ProjectContractItemOrder order, Model model) {
		String tipPrefix = "制式合同提交";
		JSONObject json = new JSONObject();
		setSessionLocalInfo2Order(order);
		if ("true".equals(isChecker)) {
			order.setChecker(true);
		}
		// 模版内容转码
		//		String str = request.getParameter("content");
		//		String str2 = request.getParameter("contentMessage");
		//		Map<String, String> requestMap = WebUtil.getRequestMap(request);
		String content = order.getContent();
		if (StringUtil.isNotBlank(content)) {
			String STR = StringEscapeUtils.unescapeHtml(EscapeUtil.unescape(content));
			order.setContent(STR);
		}
		// 模版内容转码
		String contentMessage = order.getContentMessage();
		if (StringUtil.isNotBlank(contentMessage)) {
			//			String STR2 = StringEscapeUtils.unescapeHtml(EscapeUtil.unescape(contentMessage));
			String STR2 = EscapeUtil.unescape(contentMessage);
			order.setContentMessage(STR2);
		}
		FcsBaseResult result = projectContractServiceClient.saveStandardContractContent(order);
		if (result.isExecuted() && result.isSuccess()) {
			json.put("success", true);
		}
		json = toStandardResult(json, tipPrefix);
		return json;
	}
	
	/**
	 * 制式合同详情
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("standardContractMessage.htm")
	public String standardContractMessage(Long id, String isChecker, Long stampFormId, Model model) {
		model.addAttribute("isChecker", isChecker);
		ProjectContractItemInfo item = projectContractServiceClient.findContractItemById(id);
		model.addAttribute("contractItem", item);
		// 取出合同审核信息
		ProjectContractItemOrder order = new ProjectContractItemOrder();
		order.setId(id);
		setSessionLocalInfo2Order(order);
		ProjectContractCheckMessageResult result = projectContractServiceClient
			.findCheckMessageByContractItemId(order);
		if (result.isExecuted() && result.isSuccess()) {
			if (ListUtil.isNotEmpty(result.getOthersCheckMessage())) {
				model.addAttribute("others", "Y");
			}
			if (ListUtil.isNotEmpty(result.getOwnerCheckMessage())) {
				model.addAttribute("itemCheck", result.getOwnerCheckMessage().get(0));
			}
			model.addAttribute("isCanPrint", DataPermissionUtil.isCanPrint(stampFormId));
			model.addAttribute("allCheck", result.getAllCheckMessage());
			model.addAttribute("ownerCheck", result.getOwnerCheckMessage());
			model.addAttribute("othersCheck", result.getOthersCheckMessage());
			model.addAttribute("modifyInfos", result.getModifyInfos());
		}
		return vm_path + "view_Standard.vm";
	}
	
	/**
	 * 制式合同导出
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("standardContractDownload.htm")
	public void standardContractDownload(HttpServletRequest request, HttpServletResponse response,
											Long id, Model model) {
		BufferedOutputStream output = null;
		BufferedInputStream input = null;
		ServletOutputStream outPutStream = null;
		String timeOfName = new Date().getTime() + "_" + id + ".doc";
		String path = "";
		File file = null;
		try {
			response.reset();
			outPutStream = response.getOutputStream();
			
			//output = new BufferedOutputStream(response.getOutputStream());
			ProjectContractItemInfo item = projectContractServiceClient.findContractItemById(id);
			//model.addAttribute("contractItem", item);
			//			response.setHeader("Content-disposition",
			//				"attachment; filename=" + new String("ssd嗖嗖嗖.doc".getBytes("GB2312"), "ISO8859-1"));
			//			response.setContentType("application/msword");
			//			response.setContentLength((int) item.getContentMessage().length());
			String content = "<html>"
								+ "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"></head>"
								+ "<body>" + item.getContentMessage() + "</body></html>";
			//			WebApplicationContext wac = (WebApplicationContext) request
			//				.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			//			ServletContext context = wac.getServletContext();
			//			// 本地环境用本地路径 测试环境用realPath
			//			String str = context.getRealPath("") + "/docDownLoad/" + timeOfName + ".doc";
			//			String realPath = "/var/www/upload/docDownLoad/" + timeOfName + ".doc";
			
			String downloadFolder = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_WORD_DOWNLOAD_FOLDER.code());
			if (StringUtil.isBlank(downloadFolder)) {
				downloadFolder = "/var/www/upload/docDownLoad";
			}
			// 添加判定，如果是本地服务器【如eclipse】在启动，固定设置到d盘downLoadFile目录
			WebApplicationContext wac = (WebApplicationContext) request
				.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			ServletContext context = wac.getServletContext();
			// 本地环境用本地路径 测试环境用realPath
			String str = context.getRealPath("");
			if (str.contains(":\\")) {
				downloadFolder = "D:\\\\downLoadFile";
			}
			
			//downloadFolder = "D:\\\\";
			File fileFolder = new File(downloadFolder);
			//先创建文件夹
			if (!fileFolder.isFile()) {
				fileFolder.mkdirs();
			}
			path = downloadFolder + "\\" + timeOfName;
			file = new File(path);
			if (!file.isFile()) {
				file.createNewFile();
			}
			FileOutputStream fileos = new FileOutputStream(path);
			HtmlToWord.writeWordFile(content, fileos);
			fileos.close();
			//todo 写入文件服务器 类似upload的目录，然后下载，最后删除文件
			response.setHeader("Content-disposition", "attachment; filename="
														+ new String(timeOfName.getBytes("GB2312"),
															"ISO8859-1"));
			response.setContentType("application/msexcel;charset=utf-8");
			response.setContentLength((int) file.length());
			//			response.setCharacterEncoding("UTF-8");
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
			input.close();
			//outPutStream.flush();
			//fileos.close();
			//			outPutStream.write(item.getContentMessage().length());
			//			logger.info("sssssssssssssssssssssss");
			//			outPutStream.flush();
			//			outPutStream.close();
		} catch (IOException e) {
			logger.error("导出doc异常:+" + e.getMessage(), e);
		} finally {
			//file = new File(path);
			if (file != null && file.exists()) {
				file.delete();
				if (file.exists()) {
					//System.out.println("sssssss");
					logger.error("file:" + file + "未能成功执行删除操作！");
				}
			}
			File file2 = new File("d:\\2.txt");
			if (file2.exists()) {
				file2.delete();
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (outPutStream != null) {
				try {
					outPutStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	//抓取资产系统的抵质押物
	private List<PledgeTypeSimpleInfo> getPledgeType() {
		return pledgeTypeServiceClient.queryAll().getPageList();
	}

	/**
	 * 复制合同副本
	 *
	 * @param
	 * @return
	 */
	public static  String cpoyFile(String filePath) {
		String str[]=filePath.split(",");
		String fileName=str[0];
		String serverPath=str[1];
		String fileUrl=str[2];
		File oldfile = new File(serverPath);
		if (!oldfile.exists()) {
			System.out.println("原合同文件不存在!");
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "原合同文件不存在!");
		}
		String urls[]=fileUrl.split("/");
		//处理名字
		String names[]=urls[urls.length-1].split("\\.");
		String finalUrl="";
		String finalName=names[0]+"fb";
		String suffix=names[1];
		for(int i=0;i<urls.length-1;i++){
			finalUrl=finalUrl+urls[i]+"/";
		}
		finalUrl=finalUrl+finalName+"."+suffix;
		//处理服务器地址
		String serverUrls[]=serverPath.split("/");
		String serverFinalUrl="";
		for(int i=0;i<serverUrls.length-1;i++){
			serverFinalUrl=serverFinalUrl+serverUrls[i]+"/";
		}
		serverFinalUrl=serverFinalUrl+finalName+"."+suffix;
		File newfile = new File(serverFinalUrl);
		FileInputStream fis=null;
		FileOutputStream fos=null;
		FileChannel inC=null;
		FileChannel outC=null;
		try {
			fis = new FileInputStream(oldfile);
			fos = new FileOutputStream(newfile);
			inC=fis.getChannel();
			outC=fos.getChannel();
			outC.transferFrom(inC, 0, inC.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				inC.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				outC.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileName+","+serverFinalUrl+","+finalUrl;
	}


}
