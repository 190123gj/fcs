package com.born.fcs.am.biz.job;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.am.biz.job.inter.ProcessJobService;
import com.born.fcs.am.dal.daointerface.AssetRelationProjectDAO;
import com.born.fcs.am.dal.daointerface.FAssetsTransferApplicationDAO;
import com.born.fcs.am.dal.dataobject.AssetRelationProjectDO;
import com.born.fcs.am.dal.dataobject.FAssetsTransferApplicationDO;
import com.born.fcs.am.intergration.common.SiteMessageServiceClient;
import com.born.fcs.am.intergration.service.ProjectServiceClient;
import com.born.fcs.am.ws.enums.AssetStatusEnum;
import com.born.fcs.am.ws.info.pledgeasset.AssetRelationProjectInfo;
import com.born.fcs.am.ws.info.transfer.FAssetsTransferApplicationInfo;
import com.born.fcs.am.ws.order.pledgeasset.AssetRelationProjectQueryOrder;
import com.born.fcs.am.ws.order.transfer.FAssetsTransferApplicationQueryOrder;
import com.born.fcs.am.ws.service.pledgeasset.PledgeAssetService;
import com.born.fcs.am.ws.service.transfer.AssetsTransferApplicationService;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilCompereMessageEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.council.CouncilProjectInfo;
import com.born.fcs.pm.ws.order.common.ChangeProjectStatusOrder;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.ProjectResult;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.council.CouncilProjectService;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 资产转让上会结果
 * 
 * @author Ji
 *
 */
@Service("assetsTransferApplicationCouncilResultJob")
public class AssetsTransferApplicationCouncilResultJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AssetsTransferApplicationService assetsTransferApplicationService;
	@Autowired
	SiteMessageServiceClient siteMessageServiceClient;
	@Autowired
	private CouncilProjectService councilProjectServiceClient;
	@Autowired
	private FAssetsTransferApplicationDAO FAssetsTransferApplicationDAO;
	@Autowired
	protected PledgeAssetService pledgeAssetService;
	@Autowired
	protected AssetRelationProjectDAO assetRelationProjectDAO;
	@Autowired
	ProjectServiceClient projectServiceClient;
	@Autowired
	FormService formService;
	
	@Scheduled(cron = "0 0/3 * * * ? ")
	@Override
	public void doJob() throws Exception {
		
		if (!isRun)
			return;
		try {
			
			FAssetsTransferApplicationQueryOrder order = new FAssetsTransferApplicationQueryOrder();
			
			order.setFormStatus(FormStatusEnum.APPROVAL.code());
			order.setIsToMeet(BooleanEnum.IS.code());
			order.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_WAITING.code());
			order.setPageSize(99999999999l);
			QueryBaseBatchResult<FAssetsTransferApplicationInfo> batchResult = assetsTransferApplicationService
				.query(order);
			List<FAssetsTransferApplicationInfo> listInfo = batchResult.getPageList();
			logger.info("扫描到资产转让上会中的项目数量 ：{} ", listInfo == null ? 0 : listInfo.size());
			if (listInfo != null && listInfo.size() > 0) {
				for (FAssetsTransferApplicationInfo applyInfo : listInfo) {
					
					logger.info("扫描到资产转让上会中的项目  projectCode ：{} ", applyInfo.getProjectCode());
					// 查询上会结果
					CouncilProjectInfo councilProject = councilProjectServiceClient
						.getLastInfoByApplyId(applyInfo.getCouncilApplyId());
					
					if (councilProject != null
						&& councilProject.getProjectVoteResult() == ProjectVoteResultEnum.END_PASS) {
						// 会议通过
						FAssetsTransferApplicationDO DO = FAssetsTransferApplicationDAO
							.findById(applyInfo.getId());
						if (DO.getCouncilStatus() == null && applyInfo.getId() > 0) {
							// 发消息
							sendMessageForm(applyInfo.getFormId());
							relation(applyInfo.getProjectCode());
						}
						DO.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_APPROVAL.code());
						FAssetsTransferApplicationDAO.update(DO);
						
						logger.info("资产转让项目上会通过 projectCode : {}", DO.getProjectCode());
						
						//上会通过 同步预测数据
						assetsTransferApplicationService.syncForecast(DO.getFormId());
						
					} else if (councilProject != null
								&& ProjectVoteResultEnum.END_NOPASS == councilProject
									.getProjectVoteResult()) {
						
						// 会议不通过
						FAssetsTransferApplicationDO DO = FAssetsTransferApplicationDAO
							.findById(applyInfo.getId());
						if (DO.getCouncilStatus() == null && applyInfo.getId() > 0) {
							DO.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_DENY.code());
						}
						
						FAssetsTransferApplicationDAO.update(DO);
						logger.info("资产转让项目上会未通过  projectCode : {}", DO.getProjectCode());
					} else if (councilProject != null //本次不义
								&& ProjectVoteResultEnum.END_QUIT == councilProject
									.getProjectVoteResult()) {
						FAssetsTransferApplicationDO DO = FAssetsTransferApplicationDAO
							.findById(applyInfo.getId());
						//被退回,还原该表单最初还未上会状态
						if (councilProject != null) {
							if (councilProject.getCompereMessage() != null
								&& councilProject.getCompereMessage() == ProjectCouncilCompereMessageEnum.INVESTIGATING_PHASES) {
								Long formId = applyInfo.getFormId();
								FormInfo formInfo = formService.findByFormId(formId);
								formInfo.setStatus(FormStatusEnum.DRAFT);
								formService.updateForm(formInfo);
								DO.setCouncilApplyId(0);
								DO.setCouncilStatus(null);
								DO.setCouncilBack(BooleanEnum.YES.code());
							}
						}
						FAssetsTransferApplicationDAO.update(DO);
						
					}
				}
			}
		} catch (Exception e) {
			logger.error("扫描资产转让项目上会结果异常---异常原因：{}", e);
		}
		
	}
	
	// 发送消息
	public void sendMessageForm(long formId) {
		FAssetsTransferApplicationQueryOrder queryOrder = new FAssetsTransferApplicationQueryOrder();
		queryOrder.setFormId(formId);
		QueryBaseBatchResult<FAssetsTransferApplicationInfo> batchResult = assetsTransferApplicationService
			.query(queryOrder);
		List<FAssetsTransferApplicationInfo> listInfo = batchResult.getPageList();
		for (FAssetsTransferApplicationInfo info : listInfo) {
			
			MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
			StringBuffer sb = new StringBuffer();
			String capitalUrl = CommonUtil
				.getRedirectUrl("/projectMg/chargeNotification/addChargeNotification.htm")
								+ "?projectCode=" + info.getProjectCode();
			sb.append("转让项目编号:" + info.getProjectCode());
			sb.append("，转让项目名称:" + info.getProjectName() + "已审核通过（当需要上会，该处为已上会通过），");
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
	
	private void relation(String projectCode) {
		ChangeProjectStatusOrder changeOrder = new ChangeProjectStatusOrder();
		changeOrder.setStatus(ProjectStatusEnum.TRANSFERRED);// 改为已转让 项目状态
		changeOrder.setProjectCode(projectCode);
		ProjectResult presult = projectServiceClient.changeProjectStatus(changeOrder);
		if (!presult.isSuccess()) {
			logger.error("更新项目状态出错：" + presult.getMessage());
		}
		AssetRelationProjectQueryOrder order = new AssetRelationProjectQueryOrder();
		order.setProjectCode(projectCode);
		order.setPageSize(9999);
		QueryBaseBatchResult<AssetRelationProjectInfo> batchReuslt = pledgeAssetService
			.queryAssetRelationProject(order);
		List<AssetRelationProjectInfo> listRelationInfo = batchReuslt.getPageList();
		//已转让：在资产转让申请单中，该资产关联的项目为转让的，（转让确认的条件：该项目不上会，转让申请单审核通过；
		//或者该项目转让需要上会，上会的会议纪要审核通过）；
		//当资产受让中流程通过后，该资产恢复为抵债资产；
		for (AssetRelationProjectInfo assetRelationProjectInfo : listRelationInfo) {
			AssetRelationProjectDO DO = assetRelationProjectDAO.findById(assetRelationProjectInfo
				.getId());
			DO.setAssetsStatus(AssetStatusEnum.TRANSFERRED.code());
			assetRelationProjectDAO.update(DO);
		}
	}
}
