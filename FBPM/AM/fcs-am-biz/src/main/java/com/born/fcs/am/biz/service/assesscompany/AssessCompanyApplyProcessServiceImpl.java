package com.born.fcs.am.biz.service.assesscompany;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.am.biz.exception.ExceptionFactory;
import com.born.fcs.am.biz.service.base.BaseProcessService;
import com.born.fcs.am.dal.dataobject.AssessCompanyBusinessScopeDO;
import com.born.fcs.am.dal.dataobject.AssessCompanyContactDO;
import com.born.fcs.am.dal.dataobject.AssetsAssessCompanyDO;
import com.born.fcs.am.dal.dataobject.FAssessCompanyApplyDO;
import com.born.fcs.am.dal.dataobject.FAssessCompanyApplyItemDO;
import com.born.fcs.am.intergration.bpm.result.WorkflowResult;
import com.born.fcs.am.intergration.common.SiteMessageServiceClient;
import com.born.fcs.am.intergration.service.CouncilApplyServiceClient;
import com.born.fcs.am.ws.enums.AppointWayEnum;
import com.born.fcs.am.ws.enums.AssessCompanyApplyStatusEnum;
import com.born.fcs.am.ws.info.assesscompany.FAssessCompanyApplyInfo;
import com.born.fcs.am.ws.order.assesscompany.FAssessCompanyApplyQueryOrder;
import com.born.fcs.am.ws.service.assesscompany.AssessCompanyApplyService;
import com.born.fcs.am.ws.service.transfer.AssetsTransferApplicationService;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;

/**
 * 评估公司申请单审核流程处理
 * 
 * @author Ji
 *
 *         2016-5-23 下午17:58:00
 */
@Service("assessCompanyApplyProcessService")
public class AssessCompanyApplyProcessServiceImpl extends BaseProcessService {
	@Autowired
	AssetsTransferApplicationService assetsTransferApplicationService;
	@Autowired
	protected CouncilApplyServiceClient councilApplyService;
	@Autowired
	AssessCompanyApplyService assessCompanyApplyService;
	@Autowired
	SiteMessageServiceClient siteMessageServiceClient;
	@Autowired
	protected ProjectRelatedUserService projectRelatedUserPmWebService;

	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			long formId = order.getFormInfo().getFormId();
			FAssessCompanyApplyDO DO = FAssessCompanyApplyDAO
					.findByFormId(formId);
			if (DO == null) {
				throw ExceptionFactory.newFcsException(
						FcsResultEnum.HAVE_NOT_DATA, "评估公司申请单未找到");
			}

			// 自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder
					.get().getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(DO.getProjectName() + "-评估公司申请");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("评估公司申请流程启动前置处理出错 ： {}", e);
		}
		return result;
	}

	@Override
	public void startAfterProcess(FormInfo formInfo,
			WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		FAssessCompanyApplyDO DO = FAssessCompanyApplyDAO.findByFormId(formId);

		if (DO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					"评估公司申请单未找到");
		}
		DO.setApplyStatus(AssessCompanyApplyStatusEnum.SPECIFIED_AUDIT.code());
		FAssessCompanyApplyDAO.update(DO);
	}

	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		FAssessCompanyApplyDO DO = FAssessCompanyApplyDAO.findByFormId(formId);
		if (DO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					"评估公司申请单未找到");
		}

		DO.setApplyStatus(AssessCompanyApplyStatusEnum.SPECIFIED_AUDIT_PASS
				.code());// 指定审核通过

		FAssessCompanyApplyDAO.update(DO);
		// 评估公司申请：审核通过后向发起申请的客户经理发送系统消息：
		// 您申请的（项目编号XXX项目名称XXXX）评估公司已指派，点击查看详情。
		sendMessageForm(formInfo);
	}

	/**
	 * 流程审核后处理
	 * 
	 * @param formInfo
	 * @param workflowResult
	 */
	@Override
	public void doNextAfterProcess(FormInfo formInfo,
			WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		FAssessCompanyApplyDO DO = FAssessCompanyApplyDAO.findByFormId(formId);
		if (DO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					"评估公司申请单未找到");
		}
		if (formInfo.getStatus() == FormStatusEnum.BACK) {
			DO.setApplyStatus(AssessCompanyApplyStatusEnum.SPECIFIED_REJECT
					.code());// 指定指定驳回
			FAssessCompanyApplyDAO.update(DO);
		}
	}

	@Override
	public void manualEndFlowProcess(FormInfo formInfo,
			WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		FAssessCompanyApplyDO DO = FAssessCompanyApplyDAO.findByFormId(formId);
		if (DO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					"评估公司申请单未找到");
		}
		DO.setApplyStatus(AssessCompanyApplyStatusEnum.SPECIFIED_AUDIT_NO_PASS
				.code());// 指定指定驳回
		FAssessCompanyApplyDAO.update(DO);
	}

	@Override
	public FcsBaseResult doNextBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = createResult();
		try {

			Map<String, Object> customizeMap = order.getCustomizeMap();
			logger.info("评估公司风险指定审核前处理开始 ：{}", customizeMap);

			String chooseAssessCompany = (String) customizeMap
					.get("chooseAssessCompany");

			if (StringUtil.equals(chooseAssessCompany, "YES")) {
				long formId = order.getFormInfo().getFormId();
				FAssessCompanyApplyDO DO = FAssessCompanyApplyDAO
						.findByFormId(formId);
				String appointWay = (String) customizeMap.get("appointWay");
				String companyName = (String) customizeMap.get("companyName");
				String companyId = (String) customizeMap.get("companyId");
				String appointRemark = (String) customizeMap
						.get("appointRemark");
				String appointPerson = (String) customizeMap
						.get("appointPerson");
				String appointPersonName = (String) customizeMap
						.get("appointPersonName");
				String appointPersonAccount = (String) customizeMap
						.get("appointPersonAccount");
				DO.setAppointPerson(Long.parseLong(appointPerson));
				DO.setAppointPersonName(appointPersonName);
				DO.setAppointPersonAccount(appointPersonAccount);
				DO.setAppointRemark(appointRemark);
				DO.setAppointWay(appointWay);
				DO.setCompanyName(companyName);
				DO.setCompanyId(companyId);
				FAssessCompanyApplyDAO.update(DO);
				FAssessCompanyApplyItemDAO.deleteByApplyId(DO.getId());// 删除评估公司临时存储
				String[] strCompanyIds = companyId.split(",");
				String[] strcompanyNames = companyName.split(",");
				for (int i = 0; i < strCompanyIds.length; i++) {
					FAssessCompanyApplyItemDO itemDO = FAssessCompanyApplyItemDAO
							.findByApplyIdAndCompanyId(DO.getId(),
									Long.parseLong(strCompanyIds[i]));
					if (itemDO == null) {
						itemDO = new FAssessCompanyApplyItemDO();
						AssetsAssessCompanyDO companyDO = assetsAssessCompanyDAO
								.findById(Long.parseLong(strCompanyIds[i]));
						itemDO.setApplyId(DO.getId());
						itemDO.setCompanyId(Long.parseLong(strCompanyIds[i]));
						itemDO.setCompanyName(strcompanyNames[i]);
						itemDO.setQualityAssets(companyDO.getQualityAssets());
						itemDO.setQualityHouse(companyDO.getQualityHouse());
						itemDO.setQualityLand(companyDO.getQualityLand());
						itemDO.setCityCode(companyDO.getCityCode());
						itemDO.setCity(companyDO.getCity());
						itemDO.setCountyCode(companyDO.getCountyCode());
						itemDO.setCountryName(companyDO.getCountyName());
						itemDO.setProvinceCode(companyDO.getProvinceCode());
						itemDO.setProvinceName(companyDO.getProvinceName());
						itemDO.setCountryCode(companyDO.getCountryCode());
						itemDO.setCountryName(companyDO.getCountryName());
						itemDO.setContactAddr(companyDO.getContactAddr());
						itemDO.setRegisteredCapital(companyDO
								.getRegisteredCapital());
						itemDO.setStatus(companyDO.getStatus());
						itemDO.setAttach(companyDO.getAttach());
						itemDO.setRemark(companyDO.getRemark());
						itemDO.setWorkSituation(companyDO.getWorkSituation());
						itemDO.setAttachment(companyDO.getAttachment());
						itemDO.setTechnicalLevel(companyDO.getTechnicalLevel());
						itemDO.setEvaluationEfficiency(companyDO
								.getEvaluationEfficiency());
						itemDO.setCooperationSituation(companyDO
								.getCooperationSituation());
						itemDO.setServiceAttitude(companyDO
								.getServiceAttitude());
						List<AssessCompanyContactDO> listContactDO = assessCompanyContactDAO
								.findByAssessCompanyId(companyDO.getId());
						String contactName = "";
						String contactNumber = "";
						if (listContactDO != null) {
							for (AssessCompanyContactDO assessCompanyContactDO : listContactDO) {
								contactName += assessCompanyContactDO
										.getContactName() + ",";
								contactNumber += assessCompanyContactDO
										.getContactNumber() + ",";
							}
							contactName = contactName.substring(0,
									contactName.length() - 1);
							contactNumber = contactNumber.substring(0,
									contactNumber.length() - 1);
						}
						itemDO.setContactName(contactName);
						itemDO.setContactNumber(contactNumber);
						List<AssessCompanyBusinessScopeDO> listScopeDO = assessCompanyBusinessScopeDAO
								.findByAssessCompanyId(companyDO.getId());
						String businessScopeRegion = "";
						String code = "";
						if (listScopeDO != null) {
							for (AssessCompanyBusinessScopeDO assessCompanyBusinessScopeDO : listScopeDO) {
								businessScopeRegion += assessCompanyBusinessScopeDO
										.getBusinessScopeRegion() + ",";
								code += assessCompanyBusinessScopeDO.getCode()
										+ ",";
							}
							businessScopeRegion = businessScopeRegion
									.substring(0,
											businessScopeRegion.length() - 1);
							code = code.substring(0, code.length() - 1);
						}
						itemDO.setBusinessScopeRegion(businessScopeRegion);
						itemDO.setCode(code);
						final Date now = FcsPmDomainHolder.get().getSysDate();
						itemDO.setRawAddTime(now);
						FAssessCompanyApplyItemDAO.insert(itemDO);
					}
				}

			}

			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			logger.error("评估公司风险指定审核前处理出错{}", e);
		}
		return result;
	}

	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		long formId = formInfo.getFormId();
		FAssessCompanyApplyDO applyDO = FAssessCompanyApplyDAO
				.findByFormId(formId);
		if (applyDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					"评估公司申请单未找到");
		}
		List<FlowVarField> fields = Lists.newArrayList();
		// 风险经理ID
		ProjectRelatedUserInfo riskManager = projectRelatedUserPmWebService
				.getRiskManager(applyDO.getProjectCode());
		FlowVarField riskManagerId = new FlowVarField();
		riskManagerId.setVarName("riskManager");
		riskManagerId.setVarType(FlowVarTypeEnum.STRING);
		if (null != riskManager) {
			riskManagerId.setVarVal(String.valueOf(riskManager.getUserId()));

		} else {
			riskManagerId.setVarVal("0");
		}
		FlowVarField manualAssign = new FlowVarField();
		manualAssign.setVarName("manualAssign");
		manualAssign.setVarType(FlowVarTypeEnum.STRING);
		if (applyDO.getAppointWay() != null
				&& applyDO.getAppointWay()
						.equals(AppointWayEnum.APPOINT.code())) {
			manualAssign.setVarVal("Y");
		} else if (applyDO.getAppointWay() != null
				&& applyDO.getAppointWay()
						.equals(AppointWayEnum.EXTRACT.code())) {
			manualAssign.setVarVal("N");
		} else {
			manualAssign.setVarVal("N");
		}
		fields.add(riskManagerId);
		fields.add(manualAssign);
		return fields;
	}

	// 删除
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		long formId = formInfo.getFormId();
		FAssessCompanyApplyDO DO = FAssessCompanyApplyDAO.findByFormId(formId);
		if (DO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					"评估公司申请单未找到");
		}
		FAssessCompanyApplyDAO.deleteById(DO.getId());

	}

	// 测回
	@Override
	public void cancelAfterProcess(FormInfo formInfo,
			WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		FAssessCompanyApplyDO DO = FAssessCompanyApplyDAO.findByFormId(formId);
		if (DO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
					"评估公司申请单未找到");
		}
		DO.setApplyStatus(AssessCompanyApplyStatusEnum.SPECIFIED_UNDO.code());// 指定状态
																				// 测回
		FAssessCompanyApplyDAO.update(DO);
	}

	// 发送消息
	public void sendMessageForm(FormInfo formInfo) {
		try {
			// FAssessCompanyApplyQueryOrder queryOrder = new
			// FAssessCompanyApplyQueryOrder();
			// queryOrder.setFormId(formInfo.getFormId());
			// QueryBaseBatchResult<FAssessCompanyApplyInfo> batchResult =
			// assessCompanyApplyService
			// .query(queryOrder);
			FAssessCompanyApplyInfo info = assessCompanyApplyService
					.findByFormId(formInfo.getFormId());
			// List<FAssessCompanyApplyInfo> listInfo =
			// batchResult.getPageList();
			// for (FAssessCompanyApplyInfo info : listInfo) {

			MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
			StringBuffer sb = new StringBuffer();
			String url = CommonUtil
					.getRedirectUrl("/assetMg/assessCompanyApply/view.htm")
					+ "?formId=" + info.getFormId();
			sb.append("您申请的（");
			sb.append("项目编号" + info.getProjectCode());
			sb.append("项目名称" + info.getProjectName());
			sb.append("）评估公司已指派，");
			sb.append("您可以");
			sb.append("<a href='" + url + "'>查看详情</a>");
			String content = sb.toString();
			messageOrder.setMessageContent(content);
			List<SimpleUserInfo> sendUserList = new ArrayList<SimpleUserInfo>();
			SimpleUserInfo user = new SimpleUserInfo();

			user.setUserAccount(formInfo.getUserAccount());
			user.setUserId(formInfo.getUserId());
			user.setUserName(formInfo.getUserName());

			sendUserList.add(user);
			messageOrder.setSendUsers((SimpleUserInfo[]) sendUserList
					.toArray(new SimpleUserInfo[sendUserList.size()]));
			siteMessageServiceClient.addMessageInfo(messageOrder);
			// }
		} catch (Exception e) {
			logger.error("评估公司申请审核通过后发送消息---异常原因：" + e.getMessage(), e);
		}
	}
}
