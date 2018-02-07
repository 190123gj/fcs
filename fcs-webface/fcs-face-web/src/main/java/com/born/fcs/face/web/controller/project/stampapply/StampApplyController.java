package com.born.fcs.face.web.controller.project.stampapply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.info.ChannalContractInfo;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ContractTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.LetterTypeEnum;
import com.born.fcs.pm.ws.enums.StampSourceEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractItemInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContrctInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectInfo;
import com.born.fcs.pm.ws.info.expireproject.ExpireProjectInfo;
import com.born.fcs.pm.ws.info.expireproject.ExpireProjectNoticeTemplateInfo;
import com.born.fcs.pm.ws.info.notice.ConsentIssueNoticeInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryNoticeLetterInfo;
import com.born.fcs.pm.ws.info.stampapply.StampAFileInfo;
import com.born.fcs.pm.ws.info.stampapply.StampApplyInfo;
import com.born.fcs.pm.ws.info.stampapply.StampApplyProjectResultInfo;
import com.born.fcs.pm.ws.info.stampapply.StampBasicDataApplyInfo;
import com.born.fcs.pm.ws.info.stampapply.StampBasicDataListInfo;
import com.born.fcs.pm.ws.info.stampapply.StampConfigureListInfo;
import com.born.fcs.pm.ws.order.stampapply.StampApplyOrder;
import com.born.fcs.pm.ws.order.stampapply.StampApplyQueryOrder;
import com.born.fcs.pm.ws.order.stampapply.StampBasicDataApplyOrder;
import com.born.fcs.pm.ws.order.stampapply.StampBasicDataOrder;
import com.born.fcs.pm.ws.order.stampapply.StampBasicDataQueryOrder;
import com.born.fcs.pm.ws.order.stampapply.StampConfigureOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.recovery.ProjectRecoveryResult;
import com.yjf.common.lang.util.ListUtil;

@Controller
@RequestMapping("projectMg/stampapply")
public class StampApplyController extends WorkflowBaseController {
	
	final static String vm_path = "/projectMg/assistSys/sealMg/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "applyDate" };
	}
	
	/**
	 * 是否印章管理员
	 *
	 * @param sessionLocal
	 * @return
	 */
	private boolean isStampManager(SessionLocal sessionLocal) {
		List<String> roleCodes = sessionLocal.getUserInfo().getRoleAliasList();
		List<StampConfigureListInfo> configureListInfos = stampApplyServiceClient.getStampConfig();
		if (roleCodes != null && configureListInfos != null) {
			for (String roleCode : roleCodes) {
				for (StampConfigureListInfo configInfo : configureListInfos) {
					if (roleCode.equals(configInfo.getFrzRoleCode())
						|| roleCode.equals(configInfo.getGzRoleCode())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 用印管理列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String StampApplyList(String rePage, StampApplyQueryOrder order, Model model) {
		if ("seal".equals(rePage)) {
			//	order.setF_status("CONFIRM");
		}
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		//市场营销岗和公章法人章需要看渠道合同的用印
		if (DataPermissionUtil.isScyx() || DataPermissionUtil.isGZManager()
			|| DataPermissionUtil.isFRZManager() || isStampManager(sessionLocal)) {
			order.setIsNeedChannelContract(BooleanEnum.IS);
		}
		setSessionLocalInfo2Order(order);
		QueryBaseBatchResult<StampApplyProjectResultInfo> batchResult;
		if (null == rePage) {
			batchResult = stampApplyServiceClient.query(order, "");
		} else {
			batchResult = stampApplyServiceClient.query(order, rePage);
		}
		
		Map<String, Integer> sizeMap = new HashMap<String, Integer>();
		if (batchResult.getPageList().size() > 0) {
			String applyCode = batchResult.getPageList().get(0).getApplyCode();
			for (int i = 0; i < batchResult.getPageList().size(); i++) {
				if (i == 0 || !applyCode.equals(batchResult.getPageList().get(i).getApplyCode())) {
					applyCode = batchResult.getPageList().get(i).getApplyCode();
					sizeMap.put(batchResult.getPageList().get(i).getApplyCode(), 0);
				}
				sizeMap.put(batchResult.getPageList().get(i).getApplyCode(),
					sizeMap.get(batchResult.getPageList().get(i).getApplyCode()) + 1);
				
			}
		}
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		model.addAttribute("sizeMap", sizeMap);
		model.addAttribute("rePage", rePage);
		if (null == rePage) {
			return vm_path + "list.vm";
		}
		return vm_path + rePage + "_list.vm";
	}
	
	/**
	 * 用印申请
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("addStampApply.htm")
	public String addStampApply(String contractId, String projectCode, String templateId,
								String consentId, String revoveryId, String noticeLetterId,
								String writIds, String letterIds, String spId,
								String channelFormId, HttpServletRequest request,
								HttpServletResponse response, Model model) {
		
		StampApplyInfo applyInfo = new StampApplyInfo();
		List<StampAFileInfo> fileInfos = Lists.newArrayList();
		String isContract = "NO";
		if (null != contractId) {//合同用印
			String ids[] = contractId.split(",");
			for (String id : ids) {
				StampAFileInfo info = new StampAFileInfo();
				ProjectContractItemInfo itemInfo = projectContractServiceClient
					.findContractItemById(Long.parseLong(id));
				ProjectContrctInfo contrctInfo = projectContractServiceClient.findById(itemInfo
					.getContractId());
				applyInfo.setProjectCode(contrctInfo.getProjectCode());
				applyInfo.setProjectName(contrctInfo.getProjectName());
				applyInfo.setCustomerId(contrctInfo.getCustomerId());
				applyInfo.setCustomerName(contrctInfo.getCustomerName());
				info.setFileName(itemInfo.getContractName());
				if (itemInfo.getContractType() == ContractTypeEnum.NOTSTANDARD) {//非制式合同
					info.setSource(StampSourceEnum.CONTRACT_NOTSTANDARD);
					info.setFileConent(itemInfo.getFileUrl());
				} else if (itemInfo.getContractType() == ContractTypeEnum.STANDARD) {//制式合同
					info.setSource(StampSourceEnum.CONTRACT_STANDARD);
					info.setFileConent("/projectMg/contract/standardContractMessage.htm?id="
										+ itemInfo.getId() + "&isChecker=");
				} else {//其他合同
					info.setSource(StampSourceEnum.CONTRACT_OTHER);
					info.setFileConent(itemInfo.getFileUrl());
				}
				info.setContractCode(itemInfo.getContractCode());
				info.setCnt(itemInfo.getCnt());
				info.setLegalChapterNum(StringUtil.isNotEmpty(itemInfo.getCnt()) ? Integer
					.parseInt(itemInfo.getCnt()) : 0);
				info.setCachetNum(StringUtil.isNotEmpty(itemInfo.getCnt()) ? Integer
					.parseInt(itemInfo.getCnt()) : 0);
				fileInfos.add(info);
				isContract = "IS";
			}
			applyInfo.setStampAFiles(fileInfos);
		} else if (null != templateId) {//到期通知用印
			StampAFileInfo info = new StampAFileInfo();
			ExpireProjectNoticeTemplateInfo templateInfo = expireProjectServiceClient
				.findTemplateById(Long.parseLong(templateId));
			ExpireProjectInfo expireInfo = expireProjectServiceClient
				.queryExpireProjectByExpireId(templateInfo.getExpireId() + "");
			applyInfo.setProjectCode(expireInfo.getProjectCode());
			applyInfo.setProjectName(expireInfo.getProjectName());
			ProjectInfo projectInfo = projectServiceClient.queryByCode(expireInfo.getProjectCode(),
				false);
			applyInfo.setCustomerId(projectInfo.getCustomerId());
			applyInfo.setCustomerName(projectInfo.getCustomerName());
			info.setSource(StampSourceEnum.EXPIRE_NOTICE);
			info.setFileName("到期通知");
			info.setFileConent("/projectMg/expireProject/notice.htm?templateId=" + templateId);
			fileInfos.add(info);
			applyInfo.setStampAFiles(fileInfos);
		} else if (null != consentId) {//发行通知书用印
			StampAFileInfo info = new StampAFileInfo();
			ConsentIssueNoticeInfo consentIssueNoticeInfo = consentIssueNoticeServiceClient
				.findById(Long.parseLong(consentId));
			applyInfo.setProjectCode(consentIssueNoticeInfo.getProjectCode());
			applyInfo.setProjectName(consentIssueNoticeInfo.getCustomerName());
			applyInfo.setCustomerId(consentIssueNoticeInfo.getCustomerId());
			applyInfo.setCustomerName(consentIssueNoticeInfo.getCustomerName());
			info.setSource(StampSourceEnum.CONSENT_ISSSUE_NOTICE);
			info.setFileName("发行通知书");
			info.setFileConent("/projectMg/consentIssueNotice/viewNotice.htm?id=" + consentId);
			fileInfos.add(info);
			applyInfo.setStampAFiles(fileInfos);
		} else if (revoveryId != null && noticeLetterId != null) {//通知函申请用印
			String ids[] = noticeLetterId.split(",");
			ProjectRecoveryResult result = projectRecoveryServiceClient.queryNoticeLetter(Long
				.parseLong(revoveryId));
			List<ProjectRecoveryNoticeLetterInfo> letters = result.getProjectRecoveryInfo()
				.getNoticeLetters();
			ProjectInfo projectInfo = projectServiceClient.queryByCode(result
				.getProjectRecoveryInfo().getProjectCode(), false);
			applyInfo.setProjectCode(projectInfo.getProjectCode());
			applyInfo.setProjectName(projectInfo.getProjectName());
			applyInfo.setCustomerId(projectInfo.getCustomerId());
			applyInfo.setCustomerName(projectInfo.getCustomerName());
			if (letters != null && letters.size() > 0) {
				for (String id : ids) {
					for (ProjectRecoveryNoticeLetterInfo letterInfo : letters) {
						if (letterInfo.getId() == Long.parseLong(id)) {
							StampAFileInfo info = new StampAFileInfo();
							info.setFileName(letterInfo.getLetterType().message());
							info.setSource(StampSourceEnum.NOTICE_LETTER);
							info.setFileConent("/projectMg/recovery/noticeLetterMessage.htm?recoveryId="
												+ revoveryId + "&nLetterId=" + id);
							fileInfos.add(info);
							break;
						}
					}
				}
			}
			applyInfo.setStampAFiles(fileInfos);
		} else if (spId != null) {//项目批复用印
			FCouncilSummaryProjectInfo summary = councilSummaryServiceClient
				.queryProjectCsBySpId(Long.parseLong(spId));
			if (summary != null) {
				applyInfo.setProjectCode(summary.getProjectCode());
				applyInfo.setProjectName(summary.getProjectName());
				applyInfo.setCustomerId(summary.getCustomerId());
				applyInfo.setCustomerName(summary.getCustomerName());
				StampAFileInfo fileInfo = new StampAFileInfo();
				fileInfo.setContractCode(summary.getSpCode());
				fileInfo.setFileName(summary.getProjectName() + "项目批复");
				fileInfo
					.setFileConent("/projectMg/meetingMg/summary/approval.htm?viewType=RED&spId="
									+ spId);
				fileInfo.setSource(StampSourceEnum.PROJECT_APPROVAL);
				fileInfos.add(fileInfo);
				applyInfo.setStampAFiles(fileInfos);
			}
		} else if (writIds != null) {//文书用印
			String ids[] = writIds.split(",");
			for (String id : ids) {
				StampAFileInfo info = new StampAFileInfo();
				ProjectContractItemInfo itemInfo = projectContractServiceClient
					.findContractItemById(Long.parseLong(id));
				ProjectContrctInfo contrctInfo = projectContractServiceClient.findById(itemInfo
					.getContractId());
				applyInfo.setProjectCode(contrctInfo.getProjectCode());
				applyInfo.setProjectName(contrctInfo.getProjectName());
				applyInfo.setCustomerId(contrctInfo.getCustomerId());
				applyInfo.setCustomerName(contrctInfo.getCustomerName());
				info.setFileName(itemInfo.getContractName());
				info.setSource(StampSourceEnum.PROJECT_WRIT);
				info.setFileConent(itemInfo.getFileUrl());
				info.setContractCode(itemInfo.getContractCode());
				fileInfos.add(info);
			}
			applyInfo.setStampAFiles(fileInfos);
		} else if (letterIds != null) {//函通知书用印
			String ids[] = letterIds.split(",");
			for (String id : ids) {
				StampAFileInfo info = new StampAFileInfo();
				ProjectContractItemInfo itemInfo = projectContractServiceClient
					.findContractItemById(Long.parseLong(id));
				ProjectContrctInfo contrctInfo = projectContractServiceClient.findById(itemInfo
					.getContractId());
				applyInfo.setProjectCode(contrctInfo.getProjectCode());
				applyInfo.setProjectName(contrctInfo.getProjectName());
				applyInfo.setCustomerId(contrctInfo.getCustomerId());
				applyInfo.setCustomerName(contrctInfo.getCustomerName());
				info.setFileName(itemInfo.getContractName());
				if (itemInfo.getLetterType() == LetterTypeEnum.CONTRACT) {//合同类函
					if (itemInfo.getContractType() == ContractTypeEnum.NOTSTANDARD) {//非制式合同
						info.setSource(StampSourceEnum.CLETTER_NOTSTANDARD);
						info.setFileConent(itemInfo.getFileUrl());
					} else if (itemInfo.getContractType() == ContractTypeEnum.STANDARD) {//制式合同
						info.setSource(StampSourceEnum.CLETTER_STANDARD);
						info.setFileConent("/projectMg/contract/standardContractMessage.htm?id="
											+ itemInfo.getId() + "&isChecker=");
					} else {//其他合同
						info.setSource(StampSourceEnum.CLETTER_OTHER);
						info.setFileConent(itemInfo.getFileUrl());
					}
					
				} else {
					if (itemInfo.getContractType() == ContractTypeEnum.NOTSTANDARD) {//非制式合同
						info.setSource(StampSourceEnum.LETTER_NOTSTANDARD);
						info.setFileConent(itemInfo.getFileUrl());
					} else if (itemInfo.getContractType() == ContractTypeEnum.STANDARD) {//制式合同
						info.setSource(StampSourceEnum.LETTER_STANDARD);
						info.setFileConent("/projectMg/contract/standardContractMessage.htm?id="
											+ itemInfo.getId() + "&isChecker=");
					} else {//其他合同
						info.setSource(StampSourceEnum.LETTER_OTHER);
						info.setFileConent(itemInfo.getFileUrl());
					}
				}
				info.setContractCode(itemInfo.getContractCode());
				fileInfos.add(info);
			}
			applyInfo.setStampAFiles(fileInfos);
		} else if (channelFormId != null) {//渠道合同
			StampAFileInfo info = new StampAFileInfo();
			applyInfo.setProjectCode("-");
			applyInfo.setProjectName("-");
			String ids[] = channelFormId.split(",");
			for (String id : ids) {
				ChannalContractInfo channalContractInfo = channalContractClient.info(Long
					.parseLong(id));
				info.setFileName(channalContractInfo.getChannalName());
				info.setFileConent("/customerMg/channalContract/view.htm?formId=" + id);
				info.setSource(StampSourceEnum.CHANNEL_CONTRACT);
				info.setContractCode(channalContractInfo.getContractNo());
				applyInfo.setCustomerId(0);
				applyInfo.setCustomerName(channalContractInfo.getChannalName());
				fileInfos.add(info);
			}
			applyInfo.setStampAFiles(fileInfos);
		} else {//其他
			if (projectCode != null) {
				ProjectInfo projectInfo = projectServiceClient.queryByCode(projectCode, false);
				applyInfo.setProjectCode(projectInfo.getProjectCode());
				applyInfo.setProjectName(projectInfo.getProjectName());
				applyInfo.setCustomerId(projectInfo.getCustomerId());
				applyInfo.setCustomerName(projectInfo.getCustomerName());
			}
			StampAFileInfo fileInfo = new StampAFileInfo();
			fileInfo.setFileName(request.getParameter("fileName"));
			fileInfo.setFileConent(null);
			fileInfo.setSource(StampSourceEnum.OTHER);
			fileInfos.add(fileInfo);
			applyInfo.setStampAFiles(fileInfos);
			model.addAttribute("other", "IS");
		}
		//默认是进出口担保有限公司
		StampConfigureListInfo configInfo = stampApplyServiceClient
			.getStampConfigByOrgName("重庆进出口信用担保有限公司");
		if (configInfo != null) {
			applyInfo.setOrgNames(configInfo.getOrgName());
		}
		model.addAttribute("isContract", isContract);
		model.addAttribute("applyInfo", applyInfo);
		model.addAttribute("checkStatus", 0);
		return vm_path + "apply_seal.vm";
	}
	
	/**
	 * 合同管理-用印申请
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("toStampApply.htm")
	public String toStampApply(String contractName, HttpServletRequest request,
								HttpServletResponse response, Model model) {
		StampApplyInfo applyInfo = new StampApplyInfo();
		model.addAttribute("applyInfo", applyInfo);
		model.addAttribute("checkStatus", 0);
		return vm_path + "apply_seal.vm";
	}
	
	/**
	 * 保存用印申请
	 *
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveStampApply.htm")
	@ResponseBody
	public JSONObject saveStampApply(StampApplyOrder order, HttpServletRequest request, Model model) {
		String tipPrefix = " 保存申请";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			ProjectInfo projectInfo = projectServiceClient.queryByCode(order.getProjectCode(),
				false);
			//			if (projectInfo.getStatus() != ProjectStatusEnum.NORMAL
			//				&& projectInfo.getStatus() != ProjectStatusEnum.EXPIRE
			//				&& projectInfo.getStatus() != ProjectStatusEnum.OVERDUE
			//				&& projectInfo.getStatus() != ProjectStatusEnum.RECOVERY) {
			//				jsonObject.put("success", false);
			//				jsonObject.put("message", "该项目状态异常，不允许保存！");
			//				logger.error("保存申请失败", "该项目状态异常，不允许保存！");
			//				return jsonObject;
			//			}
			// 初始化Form验证信息
			if ("SUBMIT".equals(order.getStatus())) {
				order.setCheckStatus(1);
			} else {
				order.setCheckStatus(0);
			}
			order.setCheckIndex(0);
			order.setRelatedProjectCode(order.getProjectCode());
			for (int i = 0; i < order.getFiles().size(); i++) {
				if (null == order.getFiles().get(i).getCachet()) {
					order.getFiles().get(i).setCachetNum(0);
				}
				if (null == order.getFiles().get(i).getLegalChapter()) {
					order.getFiles().get(i).setLegalChapterNum(0);
				}
				if (null == order.getFiles().get(i).getIsReplaceOld()
					|| "NO".equals(order.getFiles().get(i).getIsReplaceOld())) {
					order.getFiles().get(i).setOldFileNum(0);
				}
			}
			if ("IS".equals(order.getIsReplace())) {//申请替换处理
				order.setFormId(order.getFormId());
				order.setFormCode(FormCodeEnum.REPLACE_STAMP_APPLY);
			} else {
				order.setFormCode(FormCodeEnum.STAMP_APPLY);
			}
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FormBaseResult saveResult = stampApplyServiceClient.save(order);
			jsonObject = toJSONResult(jsonObject, saveResult, "保存用印申请成功", null);
			jsonObject.put("status", order.getStatus());
			jsonObject.put("formId", saveResult.getFormInfo().getFormId());
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存用印申请出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 用印申请详情
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("viewStampApply.htm")
	public String viewStampApply(Long id, String fileIds, String toPage, Model model) {
		String tipPrefix = "用印申请详情";
		try {
			StampApplyInfo applyInfo;
			if ("replace".equals(toPage)) {
				applyInfo = stampApplyServiceClient.findById(id, "IS");
			} else {
				applyInfo = stampApplyServiceClient.findById(id, "NO");
			}
			FormInfo form = formServiceClient.findByFormId(applyInfo.getFormId());
			if (null == fileIds) {
				List<StampAFileInfo> files = Lists.newArrayList();
				List<StampAFileInfo> fileInfos = applyInfo.getStampAFiles();
				for (StampAFileInfo fileInfo : fileInfos) {
					long maxItem = fileInfo.getOldLegalChapterNum();
					if (fileInfo.getOldCachetNum() > maxItem) {
						maxItem = fileInfo.getOldCachetNum();
					}
					fileInfo.setMaxItem(maxItem);
					files.add(fileInfo);
				}
				applyInfo.setStampAFiles(files);
			}
			if (null != fileIds) {
				if ("replace".equals(toPage)) {
					String file[] = fileIds.split(",");
					List<StampAFileInfo> files = Lists.newArrayList();
					for (int i = 0; i < file.length; i++) {
						StampAFileInfo info = stampApplyServiceClient.findByFieldId(Long
							.parseLong(file[i]));
						long maxItem = info.getOldLegalChapterNum();
						if (info.getOldCachetNum() > maxItem) {
							maxItem = info.getOldCachetNum();
						}
						info.setMaxItem(maxItem);
						files.add(info);
					}
					applyInfo.setStampAFiles(files);
				}
				applyInfo.setApplyId(0);
				applyInfo.setFormId(0);
			}
			model.addAttribute("applyInfo", applyInfo);
			model.addAttribute("currPage", toPage);
			model.addAttribute("checkStatus", form.getCheckStatus());
			model.addAttribute("formCode", form.getFormCode());
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		return vm_path + "apply_detail_seal.vm";
	}
	
	/**
	 * 编辑用印申请详情
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("editStampApply.htm")
	public String editStampApply(Long formId, String toPage, Model model) {
		String tipPrefix = "编辑用印申请详情";
		try {
			FormInfo form = formServiceClient.findByFormId(formId);
			StampApplyInfo applyInfo;
			if ("REPLACE_STAMP_APPLY".equals(form.getFormCode().code())) {
				applyInfo = stampApplyServiceClient.findByFormId(formId, "IS");
			} else {
				applyInfo = stampApplyServiceClient.findByFormId(formId, "NO");
			}
			model.addAttribute("applyInfo", applyInfo);
			model.addAttribute("currPage", toPage);
			model.addAttribute("checkStatus", form.getCheckStatus());
			model.addAttribute("formCode", form.getFormCode());
			model.addAttribute("form", form);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		return vm_path + "apply_seal.vm";
	}
	
	/**
	 * 撤销提交用印申请
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("revokeStampApply.htm")
	public String revokeStampApply(Long id, Model model) {
		String tipPrefix = "撤销提交用印申请";
		try {
			StampApplyOrder order = new StampApplyOrder();
			order.setApplyId(id);
			order.setStatus("TEMP");
			stampApplyServiceClient.revokeStatusById(order);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		
		return vm_path + "list.vm";
	}
	
	/**
	 * 确认用印
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("confirmStampApply.htm")
	@ResponseBody
	public JSONObject confirmStampApply(Long formId, Model model) {
		String tipPrefix = "确认用印申请";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			StampApplyInfo applyInfo = stampApplyServiceClient.findById(formId, "NO");
			FormInfo form = formServiceClient.findByFormId(applyInfo.getFormId());
			StampApplyOrder order = new StampApplyOrder();
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			order.setFormCode(form.getFormCode());
			order.setApplyId(formId);
			order.setFormId(form.getFormId());
			order.setStatus("CONFIRM");
			FormBaseResult result = stampApplyServiceClient.revokeStatusById(order);
			jsonObject = toJSONResult(jsonObject, result, "确认用印成功", null);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("确认用印申请出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 删除用印
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("deleteStampApply.htm")
	public String deleteStampApply(Long id, Model model) {
		String tipPrefix = "删除用印申请";
		try {
			StampApplyOrder order = new StampApplyOrder();
			order.setApplyId(id);
			stampApplyServiceClient.deleteById(order);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		
		return vm_path + "list.vm";
	}
	
	/**
	 * 用印、文件替换审核
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
		StampApplyInfo applyInfo;
		if ("REPLACE_STAMP_APPLY".equals(form.getFormCode().code())) {
			applyInfo = stampApplyServiceClient.findByFormId(formId, "IS");
		} else {
			applyInfo = stampApplyServiceClient.findByFormId(formId, "NO");
		}
		model.addAttribute("applyInfo", applyInfo);
		//		if (isCanPrint(applyInfo)) {
		//			model.addAttribute("isCanPrint", "IS");
		//		}
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", form.getFormCode());
		model.addAttribute("isAudit", true);
		//		initWorkflow(model, form, Long.toString(form.getTaskId()));
		initWorkflow(model, form, request.getParameter("taskId"));
		
		return vm_path + "audit_detail_seal.vm";
	}
	
	@RequestMapping("viewAudit.htm")
	public String viewAudit(long formId, HttpServletRequest request, Model model,
							HttpSession session) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		StampApplyInfo applyInfo;
		if ("REPLACE_STAMP_APPLY".equals(form.getFormCode().code())) {
			applyInfo = stampApplyServiceClient.findByFormId(formId, "IS");
		} else {
			applyInfo = stampApplyServiceClient.findByFormId(formId, "NO");
			List<StampAFileInfo> infos = applyInfo.getStampAFiles();
			for (StampAFileInfo info : infos) {
				info.setLegalChapterNum(info.getOldLegalChapterNum());
				info.setCachetNum(info.getOldCachetNum());
				info.setFileConent(info.getOldFileContent());
				info.setFileName(info.getFileName());
			}
			applyInfo.setStampAFiles(infos);
		}
		//		if (isCanPrint(applyInfo)) {
		//			model.addAttribute("isCanPrint", "IS");
		//		}
		model.addAttribute("applyInfo", applyInfo);
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("formCode", form.getFormCode());
		return vm_path + "audit_detail_seal.vm";
	}
	
	public boolean isCanPrint(StampApplyInfo applyInfo) {
		boolean hasG = false;
		boolean hasF = false;
		for (StampAFileInfo fileInfo : applyInfo.getStampAFiles()) {
			if (fileInfo.getLegalChapterNum() != 0 && fileInfo.getCachetNum() != 0) {
				hasF = true;
				hasG = true;
				break;
			}
			if (fileInfo.getCachetNum() != 0) {
				hasG = true;
			}
			if (fileInfo.getLegalChapterNum() != 0) {
				hasF = true;
			}
		}
		if (hasG && DataPermissionUtil.isGZManager()) {
			return true;
		}
		if (hasF && DataPermissionUtil.isFRZManager()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 印章配置
	 *
	 * @param
	 * @param model
	 * @return
	 */
	
	@RequestMapping("configure.htm")
	public String configure(HttpServletRequest request, Model model, HttpSession session) {
		List<StampConfigureListInfo> configureListInfo = stampApplyServiceClient.getStampConfig();
		model.addAttribute("configInfo", configureListInfo);
		return vm_path + "config.vm";
	}
	
	/**
	 * 保存印章配置
	 *
	 * @param order
	 * @param model
	 * @return
	 */
	
	@RequestMapping("saveConfig.json")
	@ResponseBody
	public JSONObject saveConfig(StampConfigureOrder order, HttpServletRequest request, Model model) {
		String tipPrefix = "保存印章配置";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			FcsBaseResult saveResult = stampApplyServiceClient.saveStampConfigure(order);
			jsonObject = toJSONResult(jsonObject, saveResult, "保存印章配置成功", null);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存印章配置出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 基础资料清单
	 *
	 * @param
	 * @param model
	 * @return
	 */
	
	@RequestMapping("StampBasicData.htm")
	public String StampBasicDta(StampBasicDataQueryOrder order, HttpServletRequest request,
								Model model, HttpSession session) {
		QueryBaseBatchResult<StampBasicDataListInfo> batchResult = stampApplyServiceClient
			.queryBasicData(order);
		String ids = "";
		if (ListUtil.isNotEmpty(batchResult.getPageList())) {
			for (StampBasicDataListInfo listInfo : batchResult.getPageList()) {
				ids = ids + listInfo.getId() + ",";
			}
		}
		if (StringUtil.isNotBlank(ids)) {
			ids.substring(0, ids.length() - 1);
		}
		model.addAttribute("ids", ids);
		model.addAttribute("basicDataListInfo", batchResult.getPageList());
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "basicData.vm";
	}
	
	/**
	 * 基础资料清单保存
	 *
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveBasicData.json")
	@ResponseBody
	public JSONObject saveBasicData(StampBasicDataOrder order, HttpServletRequest request,
									Model model) {
		String tipPrefix = "保存基础资料清单";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			FcsBaseResult saveResult = stampApplyServiceClient.saveStampBasicData(order);
			jsonObject = toJSONResult(jsonObject, saveResult, "保存基础资料清单成功", null);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存基础资料清单出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 基础资料用印清单弹框选择
	 *
	 * @param
	 * @param model
	 * @return
	 */
	
	@RequestMapping("basicDataList.json")
	@ResponseBody
	public JSONObject basicDataList(StampBasicDataQueryOrder order, HttpServletRequest request,
									Model model, HttpSession session) {
		String tipPrefix = "基础资料用印清单";
		JSONObject result = new JSONObject();
		try {
			QueryBaseBatchResult<StampBasicDataListInfo> batchResult = stampApplyServiceClient
				.queryBasicData(order);
			if (batchResult.isSuccess()) {
				JSONObject data = new JSONObject();
				data.put("pageCount", batchResult.getPageCount());
				data.put("totalCount", batchResult.getTotalCount());
				data.put("pageNumber", batchResult.getPageNumber());
				data.put("pageSize", batchResult.getPageSize());
				data.put("pageList", batchResult.getPageList());
				result = toStandardResult(data, tipPrefix);
			} else {
				result = toStandardResult(null, tipPrefix);
			}
			
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		return result;
	}
	
	/**
	 * 基础资料用印申请
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("editBasicDataStampApply.htm")
	public String editBasicDataStampApply(String formId, HttpServletRequest request,
											HttpServletResponse response, Model model) {
		StampBasicDataApplyInfo applyInfo = new StampBasicDataApplyInfo();
		if (StringUtil.isNotBlank(formId)) {
			FormInfo form = formServiceClient.findByFormId(Long.parseLong(formId));
			applyInfo = stampApplyServiceClient.findStampBasicDataApplyByFormId(Long
				.parseLong(formId));
			model.addAttribute("form", form);
		}
		model.addAttribute("applyInfo", applyInfo);
		return vm_path + "basicDataSeal.vm";
	}
	
	/**
	 * 保存基础资料用印申请
	 *
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveStampBasicDataApply.json")
	@ResponseBody
	public JSONObject saveStampBasicDataApply(StampBasicDataApplyOrder order,
												HttpServletRequest request, Model model) {
		String tipPrefix = " 保存基础资料用印申请";
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			order.setFormCode(FormCodeEnum.STAMP_BASIC_DATA_APPLY);
			order.setCheckIndex(0);
			setSessionLocalInfo2Order(order);
			setCustomizFieldMap(order, request);
			FormBaseResult saveResult = stampApplyServiceClient.saveStampBasicDataApply(order);
			jsonObject = toJSONResult(jsonObject, saveResult, "保存基础资料用印申请成功", null);
			jsonObject.put("formId", saveResult.getFormInfo().getFormId());
			jsonObject.put("status", order.getStatus());
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存基础资料用印申请出错", e);
		}
		
		return jsonObject;
	}
	
	/**
	 * 基础资料用印审核详情
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("basicDataAudit.htm")
	public String basicDataAudit(Long formId, HttpServletRequest request, Model model) {
		String tipPrefix = "基础资料用印审核详情";
		try {
			StampBasicDataApplyInfo applyInfo = stampApplyServiceClient
				.findStampBasicDataApplyByFormId(formId);
			FormInfo form = formServiceClient.findByFormId(applyInfo.getFormId());
			model.addAttribute("applyInfo", applyInfo);
			model.addAttribute("checkStatus", form.getCheckStatus());
			model.addAttribute("formCode", form.getFormCode());
			model.addAttribute("form", form);// 表单信息
			initWorkflow(model, form, request.getParameter("taskId"));
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		return vm_path + "basicDataView.vm";
	}
	
	/**
	 * 基础资料用印申请详情
	 *
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("basicDataView.htm")
	public String basicDataView(Long formId, HttpServletRequest request, Model model) {
		String tipPrefix = "基础资料用印申请详情";
		try {
			StampBasicDataApplyInfo applyInfo = stampApplyServiceClient
				.findStampBasicDataApplyByFormId(formId);
			FormInfo form = formServiceClient.findByFormId(applyInfo.getFormId());
			model.addAttribute("applyInfo", applyInfo);
			model.addAttribute("checkStatus", form.getCheckStatus());
			model.addAttribute("formCode", form.getFormCode());
			model.addAttribute("form", form);// 表单信息
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		return vm_path + "basicDataView.vm";
	}
	
	//	/**
	//	 * 基础资料用印申请审核
	//	 *
	//	 * @param formId
	//	 * @param model
	//	 * @return
	//	 */
	//
	//	@RequestMapping("basicDataApplyAudit.htm")
	//	public String basicDataApplyAudit(long formId, HttpServletRequest request, Model model, HttpSession session) {
	//		FormInfo form = formServiceClient.findByFormId(formId);
	//		if (form == null) {
	//			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
	//		}
	//		StampBasicDataApplyInfo applyInfo = stampApplyServiceClient.findStampBasicDataApplyByFormId(formId);
	//		model.addAttribute("applyInfo", applyInfo);
	//		model.addAttribute("form", form);// 表单信息
	//		model.addAttribute("formCode", form.getFormCode());
	//		model.addAttribute("isAudit", true);
	//		initWorkflow(model, form, request.getParameter("taskId"));
	//
	//		return vm_path + "audit_detail_seal.vm";
	//	}
	
	/**
	 * 基础资料用印申请列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("basicDataApplyList.htm")
	public String basicDataApplyList(StampApplyQueryOrder order, Model model) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		setSessionLocalInfo2Order(order);
		QueryBaseBatchResult<StampApplyProjectResultInfo> batchResult = stampApplyServiceClient
			.queryStampBasicDataApply(order);
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "basicDataApplyList.vm";
	}
}
