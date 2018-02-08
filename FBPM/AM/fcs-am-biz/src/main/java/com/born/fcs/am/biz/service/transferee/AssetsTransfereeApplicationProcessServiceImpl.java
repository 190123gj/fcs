package com.born.fcs.am.biz.service.transferee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.am.biz.exception.ExceptionFactory;
import com.born.fcs.am.biz.service.base.BaseProcessService;
import com.born.fcs.am.dal.dataobject.AssetRelationProjectDO;
import com.born.fcs.am.dal.dataobject.FAssetsTransferApplicationDO;
import com.born.fcs.am.dal.dataobject.FAssetsTransfereeApplicationDO;
import com.born.fcs.am.intergration.bpm.result.WorkflowResult;
import com.born.fcs.am.intergration.service.CouncilApplyServiceClient;
import com.born.fcs.am.intergration.service.ProjectServiceClient;
import com.born.fcs.am.ws.enums.AssetStatusEnum;
import com.born.fcs.am.ws.enums.LiquidaterStatusEnum;
import com.born.fcs.am.ws.info.pledgeasset.AssetRelationProjectInfo;
import com.born.fcs.am.ws.info.transfer.FAssetsTransferApplicationInfo;
import com.born.fcs.am.ws.order.pledgeasset.AssetRelationProjectQueryOrder;
import com.born.fcs.am.ws.order.transfer.FAssetsTransferApplicationQueryOrder;
import com.born.fcs.am.ws.service.pledgeasset.PledgeAssetService;
import com.born.fcs.am.ws.service.transfer.AssetsTransferApplicationService;
import com.born.fcs.am.ws.service.transferee.AssetsTransfereeApplicationService;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CreditConditionTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.SubsystemDockFormTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.common.ChangeProjectStatusOrder;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.ProjectResult;
import com.born.fcs.pm.ws.service.common.SubsystemDockProjectService;
import com.born.fcs.pm.ws.service.projectcreditcondition.ProjectCreditConditionService;
import com.google.common.collect.Maps;

/**
 * 资产受让申请审核流程处理
 * 
 * @author Ji
 *
 * 2016-8-22 下午17:58:00
 */
@Service("assetsTransfereeApplicationProcessService")
public class AssetsTransfereeApplicationProcessServiceImpl extends BaseProcessService {
	@Autowired
	AssetsTransfereeApplicationService assetsTransfereeApplicationService;
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
	@Autowired
	protected ProjectCreditConditionService projectCreditConditionWebService;
	
	@Override
	public Map<String, String> makeMessageVar(FormInfo formInfo) {
		Map<String, String> vars = Maps.newHashMap();
		long formId = formInfo.getFormId();
		FAssetsTransfereeApplicationDO transfereeDO = FAssetsTransfereeApplicationDAO
			.findByFormId(formId);
		if (transfereeDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资产受让申请单未找到");
		}
		if (transfereeDO.getProjectCode() == null || "".equals(transfereeDO.getProjectCode())) {
			
			vars.put("项目名称", transfereeDO.getProjectName());
			vars.put("客户名称", "-");
		} else {
			ProjectSimpleDetailInfo projectInfo = projectServiceClient
				.querySimpleDetailInfo(transfereeDO.getProjectCode());
			vars.put("项目名称", transfereeDO.getProjectName());
			vars.put("客户名称", projectInfo.getCustomerName());
		}
		return vars;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			long formId = order.getFormInfo().getFormId();
			FAssetsTransfereeApplicationDO transferDO = FAssetsTransfereeApplicationDAO
				.findByFormId(formId);
			if (transferDO == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资产受让申请单未找到");
			}
			// 自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(transferDO.getProjectName() + "-资产受让申请");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("资产受让申请流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		FAssetsTransfereeApplicationDO transferDO = FAssetsTransfereeApplicationDAO
			.findByFormId(formId);
		if (transferDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资产受让申请单未找到");
		}
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		FAssetsTransfereeApplicationDO transfereeDO = FAssetsTransfereeApplicationDAO
			.findByFormId(formId);
		if (transfereeDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资产受让申请单未找到");
		}
		
		if (transfereeDO.getProjectCode() != null) {
			
			AssetRelationProjectQueryOrder order = new AssetRelationProjectQueryOrder();
			order.setProjectCode(transfereeDO.getProjectCode());
			QueryBaseBatchResult<AssetRelationProjectInfo> batchReuslt = pledgeAssetService
				.queryAssetRelationProject(order);
			List<AssetRelationProjectInfo> listRelationInfo = batchReuslt.getPageList();
			//已转让：在资产转让申请单中，该资产关联的项目为转让的，（转让确认的条件：该项目不上会，转让申请单审核通过；
			//或者该项目转让需要上会，上会的会议纪要审核通过）；
			//当资产受让中流程通过后，该资产恢复为抵债资产；
			for (AssetRelationProjectInfo assetRelationProjectInfo : listRelationInfo) {
				AssetRelationProjectDO DO = assetRelationProjectDAO
					.findById(assetRelationProjectInfo.getId());
				ProjectCreditConditionQueryOrder order1 = new ProjectCreditConditionQueryOrder();
				order1.setProjectCode(DO.getProjectCode());
				order1.setAssetId(DO.getAssetsId());
				order1.setIsConfirm(BooleanEnum.IS.code());
				QueryBaseBatchResult<ProjectCreditConditionInfo> result1 = projectCreditConditionWebService
					.queryCreditCondition(order1);
				List<ProjectCreditConditionInfo> infos = result1.getPageList();
				if (infos != null) {
					for (ProjectCreditConditionInfo info : infos) {
						if (CreditConditionTypeEnum.PLEDGE.code().equals(info.getType())) {//抵押
							DO.setAssetsStatus(AssetStatusEnum.SECURED_PLEDGE.code());
						} else if (CreditConditionTypeEnum.MORTGAGE.code().equals(info.getType())) {
							DO.setAssetsStatus(AssetStatusEnum.SECURED_MORTGAGE.code());
						}
					}
				}
				assetRelationProjectDAO.update(DO);
			}
			
			ChangeProjectStatusOrder changeOrder = new ChangeProjectStatusOrder();
			changeOrder.setStatus(ProjectStatusEnum.RECOVERY);// 改为追偿 项目状态
			changeOrder.setProjectCode(transfereeDO.getProjectCode());
			ProjectResult presult = projectServiceClient.changeProjectStatus(changeOrder);
			if (!presult.isSuccess()) {
				logger.error("更新项目状态出错：" + presult.getMessage());
			}
			sendMessageForm(formInfo);
			// 删除表单项目信息 在子系统关联项目信息表中
			
			subsystemDockProjectWebService.deleteByProjectCodeAndDockFormType(
				transfereeDO.getProjectCode(), SubsystemDockFormTypeEnum.ASSETS_TRANSFER.code());
			
		}
		// 更改清收状态
		if (transfereeDO.getProjectCode() != null) {
			FAssetsTransferApplicationQueryOrder order = new FAssetsTransferApplicationQueryOrder();
			order.setProjectCode(transfereeDO.getProjectCode());
			order.setLiquidaterStatus(LiquidaterStatusEnum.NO_LIQUIDATER.code());// 未清收
			QueryBaseBatchResult<FAssetsTransferApplicationInfo> batchResult = assetsTransferApplicationService
				.query(order);
			List<FAssetsTransferApplicationInfo> listAssetsTransfer = batchResult.getPageList();
			if (listAssetsTransfer != null) {
				for (FAssetsTransferApplicationInfo info : listAssetsTransfer) {
					FAssetsTransferApplicationDO DO = FAssetsTransferApplicationDAO.findById(info
						.getId());
					DO.setLiquidaterStatus(LiquidaterStatusEnum.IS_LIQUIDATER.code());
					FAssetsTransferApplicationDAO.update(DO);
				}
			}
		}
		
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		FAssetsTransfereeApplicationDO transferDO = FAssetsTransfereeApplicationDAO
			.findByFormId(formId);
		if (transferDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资产受让申请单未找到");
		}
	}
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		long formId = formInfo.getFormId();
		FAssetsTransfereeApplicationDO transferDO = FAssetsTransfereeApplicationDAO
			.findByFormId(formId);
		if (transferDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资产受让申请单未找到");
		}
		List<FlowVarField> fields = Lists.newArrayList();
		// FlowVarField overfull = new FlowVarField();
		// overfull.setVarName("meetingType");
		// overfull.setVarType(FlowVarTypeEnum.STRING);
		// if (transferDO.getIsToMeet().equals(BooleanEnum.IS.code())) {// 上会
		// if (transferDO.getMeetType() != null) {
		// if (transferDO.getMeetType().equals(
		// MeetTypeEnum.RISK_MEET.code())) {
		// overfull.setVarVal("fengxian");
		// } else if (transferDO.getMeetType().equals(
		// MeetTypeEnum.MANAGER_MEET.code())) {
		// overfull.setVarVal("zongjingli");
		// } else {
		// overfull.setVarVal("0");
		// }
		// }
		// } else {// 不上会
		// overfull.setVarVal("0");
		// }
		// fields.add(overfull);
		return fields;
	}
	
	// 删除
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		long formId = formInfo.getFormId();
		FAssetsTransfereeApplicationDO transferDO = FAssetsTransfereeApplicationDAO
			.findByFormId(formId);
		if (transferDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资产受让申请单未找到");
		}
		FAssetsTransferApplicationDAO.deleteById(transferDO.getId());
		
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
				.getRedirectUrl("/projectMg/fCapitalAppropriationApply/addCapitalAppropriationApply.htm")
								+ "?projectCode="
								+ info.getProjectCode()
								+ "&projectType=NOT_FINANCIAL_PRODUCT";
			sb.append("受让项目编号" + info.getProjectCode());
			sb.append("，受让项目名称" + info.getProjectName() + "已审核通过");
			sb.append("您可以去发起资金划付申请单，");
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
