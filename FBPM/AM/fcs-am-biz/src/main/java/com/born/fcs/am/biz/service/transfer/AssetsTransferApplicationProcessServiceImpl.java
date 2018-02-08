package com.born.fcs.am.biz.service.transfer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.am.biz.exception.ExceptionFactory;
import com.born.fcs.am.biz.service.base.BaseProcessService;
import com.born.fcs.am.dal.dataobject.AssetRelationProjectDO;
import com.born.fcs.am.dal.dataobject.FAssetsTransferApplicationDO;
import com.born.fcs.am.intergration.bpm.result.WorkflowResult;
import com.born.fcs.am.intergration.service.CouncilApplyServiceClient;
import com.born.fcs.am.intergration.service.ProjectServiceClient;
import com.born.fcs.am.ws.enums.AssetStatusEnum;
import com.born.fcs.am.ws.enums.MeetTypeEnum;
import com.born.fcs.am.ws.info.pledgeasset.AssetRelationProjectInfo;
import com.born.fcs.am.ws.info.transfer.FAssetsTransferApplicationInfo;
import com.born.fcs.am.ws.order.pledgeasset.AssetRelationProjectQueryOrder;
import com.born.fcs.am.ws.order.transfer.FAssetsTransferApplicationQueryOrder;
import com.born.fcs.am.ws.service.pledgeasset.PledgeAssetService;
import com.born.fcs.am.ws.service.transfer.AssetsTransferApplicationService;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.SubsystemDockFormTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.ChangeProjectStatusOrder;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.ProjectResult;
import com.born.fcs.pm.ws.service.common.SubsystemDockProjectService;

/**
 * 资产转让申请审核流程处理
 * 
 * @author Ji
 *
 * 2016-5-23 下午17:58:00
 */
@Service("assetsTransferApplicationProcessService")
public class AssetsTransferApplicationProcessServiceImpl extends BaseProcessService {
	@Autowired
	AssetsTransferApplicationService assetsTransferApplicationService;
	@Autowired
	protected CouncilApplyServiceClient councilApplyService;
	@Autowired
	ProjectServiceClient projectServiceClient;
	// 子系统对接项目信息
	@Autowired
	protected SubsystemDockProjectService subsystemDockProjectWebService;
	@Autowired
	protected PledgeAssetService pledgeAssetService;
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			long formId = order.getFormInfo().getFormId();
			FAssetsTransferApplicationDO transferDO = FAssetsTransferApplicationDAO
				.findByFormId(formId);
			if (transferDO == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资产转让申请单未找到");
			}
			// 自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(transferDO.getProjectName() + "-资产转让申请");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("资产转让申请流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		FAssetsTransferApplicationDO transferDO = FAssetsTransferApplicationDAO
			.findByFormId(formId);
		if (transferDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资产转让申请单未找到");
		}
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		FAssetsTransferApplicationDO transferDO = FAssetsTransferApplicationDAO
			.findByFormId(formId);
		if (transferDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资产转让申请单未找到");
		}
		
		// 不上会的项目的可收费阶段：如果上会的，则为上会通过后，如果不需要上会的，则为单据审核通过后；
		// 消息内容：转让项目编号XXXX，转让项目名称xxxx已审核通过（当需要上会，该处为已上会通过），
		// 您可以去发起收款通知单，点击处理
		if (BooleanEnum.NO.code().equals(transferDO.getIsToMeet())) {
			ChangeProjectStatusOrder changeOrder = new ChangeProjectStatusOrder();
			changeOrder.setStatus(ProjectStatusEnum.TRANSFERRED);// 改为已转让 项目状态
			changeOrder.setProjectCode(transferDO.getProjectCode());
			ProjectResult presult = projectServiceClient.changeProjectStatus(changeOrder);
			if (!presult.isSuccess()) {
				logger.error("更新项目状态出错：" + presult.getMessage());
			}
			sendMessageForm(formInfo);
			AssetRelationProjectQueryOrder order = new AssetRelationProjectQueryOrder();
			order.setProjectCode(transferDO.getProjectCode());
			order.setPageSize(99999999999l);
			QueryBaseBatchResult<AssetRelationProjectInfo> batchReuslt = pledgeAssetService
				.queryAssetRelationProject(order);
			List<AssetRelationProjectInfo> listRelationInfo = batchReuslt.getPageList();
			//已转让：在资产转让申请单中，该资产关联的项目为转让的，（转让确认的条件：该项目不上会，转让申请单审核通过；
			//或者该项目转让需要上会，上会的会议纪要审核通过）；
			//当资产受让中流程通过后，该资产恢复为抵债资产；
			for (AssetRelationProjectInfo assetRelationProjectInfo : listRelationInfo) {
				AssetRelationProjectDO DO = assetRelationProjectDAO
					.findById(assetRelationProjectInfo.getId());
				DO.setAssetsStatus(AssetStatusEnum.TRANSFERRED.code());
				assetRelationProjectDAO.update(DO);
			}
			
		}
		if (BooleanEnum.IS.code().equals(transferDO.getIsToMeet())
			&& transferDO.getMeetType() != null) {
			CouncilApplyOrder order = new CouncilApplyOrder();
			if (transferDO.getMeetType().equals(MeetTypeEnum.RISK_MEET.code())) {
				order.setCouncilTypeDesc(CouncilTypeEnum.RISK_HANDLE.message());
				order.setCouncilCode(CouncilTypeEnum.RISK_HANDLE.code());
			} else {
				order.setCouncilTypeDesc(CouncilTypeEnum.GM_WORKING.message());
				order.setCouncilCode(CouncilTypeEnum.GM_WORKING.code());
			}
			// order.setFormId(formId);
			order.setProjectCode(transferDO.getProjectCode());
			order.setProjectName(transferDO.getProjectName());
			ProjectSimpleDetailInfo projectInfo = projectServiceClient
				.querySimpleDetailInfo(transferDO.getProjectCode());
			order.setCustomerId(projectInfo.getCustomerId());
			order.setCustomerName(projectInfo.getCustomerName());
			order.setAmount(projectInfo.getAmount());
			order.setApplyManId(formInfo.getUserId());
			order.setApplyManName(formInfo.getUserName());
			order.setApplyDeptId(formInfo.getDeptId());
			order.setApplyDeptName(formInfo.getDeptName());
			order.setApplyTime(getSysdate());
			order.setStatus(CouncilApplyStatusEnum.WAIT.code());
			order.setTimeLimit(projectInfo.getTimeLimit());
			if (projectInfo.getTimeUnit() != null) {
				order.setTimeUnit(projectInfo.getTimeUnit().code());
			}
			FcsBaseResult result = councilApplyService.saveCouncilApply(order);
			long id = result.getKeyId();
			transferDO.setCouncilApplyId(id);
			transferDO.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_WAITING.code());
			FAssetsTransferApplicationDAO.update(transferDO);
			if (!result.isSuccess()) {
				logger.info("写入上会数据异常(风险处置)：" + result);
			}
		} else {
			//不上会同步预测数据
			assetsTransferApplicationService.syncForecast(formInfo.getFormId());
		}
		
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		FAssetsTransferApplicationDO transferDO = FAssetsTransferApplicationDAO
			.findByFormId(formId);
		if (transferDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资产转让申请单未找到");
		}
		// 删除表单项目信息 在子系统关联项目信息表中
		
		subsystemDockProjectWebService.deleteByProjectCodeAndDockFormType(
			transferDO.getProjectCode(), SubsystemDockFormTypeEnum.ASSETS_TRANSFER.code());
	}
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		long formId = formInfo.getFormId();
		FAssetsTransferApplicationDO transferDO = FAssetsTransferApplicationDAO
			.findByFormId(formId);
		if (transferDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资产转让申请单未找到");
		}
		List<FlowVarField> fields = Lists.newArrayList();
		FlowVarField overfull = new FlowVarField();
		overfull.setVarName("meetingType");
		overfull.setVarType(FlowVarTypeEnum.STRING);
		if (transferDO.getIsToMeet().equals(BooleanEnum.IS.code())) {// 上会
			if (transferDO.getMeetType() != null) {
				if (transferDO.getMeetType().equals(MeetTypeEnum.RISK_MEET.code())) {
					overfull.setVarVal("fengxian");
				} else if (transferDO.getMeetType().equals(MeetTypeEnum.MANAGER_MEET.code())) {
					overfull.setVarVal("zongjingli");
				} else {
					overfull.setVarVal("0");
				}
			}
		} else {// 不上会
			overfull.setVarVal("0");
		}
		fields.add(overfull);
		return fields;
	}
	
	// 删除
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		long formId = formInfo.getFormId();
		FAssetsTransferApplicationDO transferDO = FAssetsTransferApplicationDAO
			.findByFormId(formId);
		if (transferDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资产转让申请单未找到");
		}
		FAssetsTransferApplicationDAO.deleteById(transferDO.getId());
		// 删除表单项目信息 在子系统关联项目信息表中
		
		subsystemDockProjectWebService.deleteByProjectCodeAndDockFormType(
			transferDO.getProjectCode(), SubsystemDockFormTypeEnum.ASSETS_TRANSFER.code());
	}
	
	// 发送消息
	public void sendMessageForm(FormInfo formInfo) {
		
		FAssetsTransferApplicationQueryOrder queryOrder = new FAssetsTransferApplicationQueryOrder();
		queryOrder.setFormId(formInfo.getFormId());
		QueryBaseBatchResult<FAssetsTransferApplicationInfo> batchResult = assetsTransferApplicationService
			.query(queryOrder);
		List<FAssetsTransferApplicationInfo> listInfo = batchResult.getPageList();
		for (FAssetsTransferApplicationInfo info : listInfo) {
			
			MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
			StringBuffer sb = new StringBuffer();
			String capitalUrl = CommonUtil
				.getRedirectUrl("/projectMg/chargeNotification/addChargeNotification.htm")
								+ "?projectCode=" + info.getProjectCode();
			sb.append("转让项目编号" + info.getProjectCode());
			sb.append("，转让项目名称" + info.getProjectName() + "已审核通过（当需要上会，该处为已上会通过），");
			sb.append("您可以去发起收款通知单，");
			sb.append("<a href='" + capitalUrl + "'>点击处理</a>");
			String content = sb.toString();
			messageOrder.setMessageContent(content);
			List<SimpleUserInfo> sendUserList = new ArrayList<SimpleUserInfo>();
			SimpleUserInfo user = new SimpleUserInfo();
			
			user.setUserAccount(info.getFormUserAccount());
			user.setUserId(info.getFormUserId());
			user.setUserName(info.getFormUserName());
			sendUserList.add(user);
			messageOrder.setSendUsers((SimpleUserInfo[]) sendUserList
				.toArray(new SimpleUserInfo[sendUserList.size()]));
			siteMessageServiceClient.addMessageInfo(messageOrder);
		}
	}
}
