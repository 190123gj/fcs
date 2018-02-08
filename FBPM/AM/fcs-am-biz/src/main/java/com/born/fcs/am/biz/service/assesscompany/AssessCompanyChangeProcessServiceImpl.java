package com.born.fcs.am.biz.service.assesscompany;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.am.biz.exception.ExceptionFactory;
import com.born.fcs.am.biz.service.base.BaseProcessService;
import com.born.fcs.am.dal.dataobject.FAssessCompanyApplyDO;
import com.born.fcs.am.intergration.bpm.result.WorkflowResult;
import com.born.fcs.am.intergration.service.CouncilApplyServiceClient;
import com.born.fcs.am.ws.enums.AssessCompanyApplyStatusEnum;
import com.born.fcs.am.ws.info.assesscompany.FAssessCompanyApplyInfo;
import com.born.fcs.am.ws.order.assesscompany.FAssessCompanyApplyQueryOrder;
import com.born.fcs.am.ws.service.assesscompany.AssessCompanyApplyService;
import com.born.fcs.am.ws.service.transfer.AssetsTransferApplicationService;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.CommonUtil;
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
 * 评估公司更换审核流程处理
 * 
 * @author Ji
 *
 * 2016-5-23 下午17:58:00
 */
@Service("assessCompanyChangeProcessService")
public class AssessCompanyChangeProcessServiceImpl extends BaseProcessService {
	@Autowired
	AssetsTransferApplicationService assetsTransferApplicationService;
	@Autowired
	protected CouncilApplyServiceClient councilApplyService;
	@Autowired
	AssessCompanyApplyService assessCompanyApplyService;
	@Autowired
	protected ProjectRelatedUserService projectRelatedUserService;
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			long formId = order.getFormInfo().getFormId();
			FAssessCompanyApplyDO DO = FAssessCompanyApplyDAO.findByFormId(formId);
			if (DO == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "评估公司更换申请单未找到");
			}
			// 自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(DO.getProjectName() + "-评估公司更换申请");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("评估公司更换申请流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		FAssessCompanyApplyDO DO = FAssessCompanyApplyDAO.findByFormId(formId);
		if (DO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "评估公司更换申请单未找到");
		}
		DO.setApplyStatus(AssessCompanyApplyStatusEnum.CHANGE_AUDIT.code());// 变为更换审核中
		FAssessCompanyApplyDAO.update(DO);
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		FAssessCompanyApplyDO DO = FAssessCompanyApplyDAO.findByFormId(formId);
		if (DO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "评估公司更换申请单未找到");
		}
		FAssessCompanyApplyDO DO1 = FAssessCompanyApplyDAO.findById(DO.getReplacedId());
		if (DO1 != null) {
			DO1.setBeReplacedId(DO.getId());
			DO1.setApplyStatus(AssessCompanyApplyStatusEnum.BE_CHANGE_FINISH.code());// 被更换完成通过
			FAssessCompanyApplyDAO.update(DO1);
		}
		DO.setApplyStatus(AssessCompanyApplyStatusEnum.CHANGE_AUDIT_PASS.code());// 变为更换审核通过
		FAssessCompanyApplyDAO.update(DO);
		// 评估公司更换
		// 审核通过后，向该项目的客户经理发送系统消息：项目编号XXX项目名称XXXX的评估公司已由风险经理XXX更改，点击查看详情。
		sendMessageForm(formInfo);
	}
	
	/**
	 * 流程审核后处理
	 * 
	 * @param formInfo
	 * @param workflowResult
	 */
	@Override
	public void doNextAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		FAssessCompanyApplyDO DO = FAssessCompanyApplyDAO.findByFormId(formId);
		if (DO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "评估公司更换申请单未找到");
		}
		if (formInfo.getStatus() == FormStatusEnum.BACK) {
			DO.setApplyStatus(AssessCompanyApplyStatusEnum.CHANGE_REJECT.code());// 指定指定驳回
			FAssessCompanyApplyDAO.update(DO);
		}
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		FAssessCompanyApplyDO DO = FAssessCompanyApplyDAO.findByFormId(formId);
		if (DO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "评估公司更换申请单未找到");
		}
		DO.setApplyStatus(AssessCompanyApplyStatusEnum.CHANGE_AUDIT_NO_PASS.code());// 变为更换审核不通过
		FAssessCompanyApplyDAO.update(DO);
		
		FAssessCompanyApplyDO DO1 = FAssessCompanyApplyDAO.findById(DO.getReplacedId());
		if (DO1 != null) {
			DO1.setBeReplacedId(DO.getId());
			DO1.setApplyStatus(AssessCompanyApplyStatusEnum.BE_CHANGE_NOT_FINISH.code());// 被更换完成不通过
			FAssessCompanyApplyDAO.update(DO1);
		}
	}
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		long formId = formInfo.getFormId();
		FAssessCompanyApplyDO transferDO = FAssessCompanyApplyDAO.findByFormId(formId);
		if (transferDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "评估公司更换申请单未找到");
		}
		List<FlowVarField> fields = Lists.newArrayList();
		//风险经理ID
		ProjectRelatedUserInfo riskManager = projectRelatedUserService.getRiskManager(transferDO
			.getProjectCode());
		FlowVarField riskManagerId = new FlowVarField();
		riskManagerId.setVarName("riskManager");
		riskManagerId.setVarType(FlowVarTypeEnum.STRING);
		if (null != riskManager) {
			riskManagerId.setVarVal(String.valueOf(riskManager.getUserId()));
			
		} else {
			riskManagerId.setVarVal("0");
		}
		fields.add(riskManagerId);
		return fields;
	}
	
	// 测回
	@Override
	public void cancelAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		FAssessCompanyApplyDO DO = FAssessCompanyApplyDAO.findByFormId(formId);
		if (DO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "评估公司申请单未找到");
		}
		DO.setApplyStatus(AssessCompanyApplyStatusEnum.CHANGE_UNDO.code());// 变为更换测回
		FAssessCompanyApplyDAO.update(DO);
	}
	
	// 删除
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		long formId = formInfo.getFormId();
		FAssessCompanyApplyDO DO = FAssessCompanyApplyDAO.findByFormId(formId);
		if (DO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "评估公司更换申请单未找到");
		}
		FAssessCompanyApplyDAO.deleteById(DO.getId());
		FAssessCompanyApplyDO DO1 = FAssessCompanyApplyDAO.findById(DO.getReplacedId());
		if (DO1 != null) {
			DO1.setBeReplacedId(DO.getId());
			DO1.setApplyStatus(AssessCompanyApplyStatusEnum.SPECIFIED_AUDIT_PASS.code());//
			FAssessCompanyApplyDAO.update(DO1);
		}
	}
	
	// 发送消息
	public void sendMessageForm(FormInfo formInfo) {
		try {
			// FAssessCompanyApplyInfo info = assessCompanyApplyService
			// .findByFormId(formInfo.getFormId());
			FAssessCompanyApplyQueryOrder queryOrder = new FAssessCompanyApplyQueryOrder();
			queryOrder.setFormId(formInfo.getFormId());
			QueryBaseBatchResult<FAssessCompanyApplyInfo> batchResult = assessCompanyApplyService
				.query(queryOrder);
			List<FAssessCompanyApplyInfo> listInfo = batchResult.getPageList();
			for (FAssessCompanyApplyInfo info : listInfo) {
				
				ProjectRelatedUserInfo userInfo = projectRelatedUserService.getRiskManager(info
					.getProjectCode());
				ProjectRelatedUserInfo userInfo1 = projectRelatedUserService.getBusiManager(info
					.getProjectCode());
				String riskManager = "";
				if (userInfo != null) {
					riskManager = userInfo.getUserName();
				}
				
				MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
				StringBuffer sb = new StringBuffer();
				String url = CommonUtil.getRedirectUrl("/assetMg/assessCompanyApply/view.htm")
								+ "?formId=" + info.getFormId();
				sb.append("项目编号" + info.getProjectCode());
				sb.append("项目名称" + info.getProjectName());
				sb.append("已由风险经理" + riskManager + "更改，");
				sb.append("您可以");
				sb.append("<a href='" + url + "'>查看详情</a>");
				String content = sb.toString();
				messageOrder.setMessageContent(content);
				List<SimpleUserInfo> sendUserList = new ArrayList<SimpleUserInfo>();
				SimpleUserInfo user = new SimpleUserInfo();
				
				user.setUserAccount(info.getFormUserAccount());
				user.setUserId(info.getFormUserId());
				user.setUserName(info.getFormUserName());
				sendUserList.add(user);
				SimpleUserInfo user1 = new SimpleUserInfo();
				
				user1.setUserAccount(userInfo1.getUserAccount());
				user1.setUserId(userInfo1.getUserId());
				user1.setUserName(userInfo1.getUserName());
				
				sendUserList.add(user1);
				messageOrder.setSendUsers((SimpleUserInfo[]) sendUserList
					.toArray(new SimpleUserInfo[sendUserList.size()]));
				siteMessageServiceClient.addMessageInfo(messageOrder);
			}
		} catch (Exception e) {
			logger.error("评估公司申请审核通过后发送消息---异常原因：" + e.getMessage(), e);
		}
	}
}
