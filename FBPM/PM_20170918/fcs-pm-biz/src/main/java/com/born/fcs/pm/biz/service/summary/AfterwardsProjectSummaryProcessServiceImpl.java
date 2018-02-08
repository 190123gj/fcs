package com.born.fcs.pm.biz.service.summary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.dal.dataobject.AfterwardsProjectSummaryDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("afterwardsProjectSummaryProcessService")
public class AfterwardsProjectSummaryProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	private SysParameterService sysParameterServiceClient;
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	@Autowired
	protected SiteMessageService siteMessageServiceClient;
	@Autowired
	protected ProjectRelatedUserService projectRelatedUserService;
	
	@Override
	public Map<String, String> makeMessageVar(FormInfo formInfo) {
		Map<String, String> vars = Maps.newHashMap();
		long formId = formInfo.getFormId();
		AfterwardsProjectSummaryDO summaryDO = afterwardsProjectSummaryDAO.findByFormId(formId);
		if (summaryDO != null) {
			
			vars.put("部门名称", summaryDO.getDeptName());
			vars.put("所属报告期", summaryDO.getReportPeriod());
		}
		
		return vars;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			long formId = order.getFormInfo().getFormId();
			AfterwardsProjectSummaryDO project = afterwardsProjectSummaryDAO.findByFormId(formId);
			if (project == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "保后项目汇总信息未找到");
			}
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName("保后项目汇总");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("保后项目汇总流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult doNextBeforeProcess(final WorkFlowBeforeProcessOrder order) {
		return commonProcess(order, "保后项目汇总审核前处理", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				Map<String, Object> customizeMap = order.getCustomizeMap();
				String departmentHead = (String) customizeMap.get("departmentHead");
				String StrFormId = (String) customizeMap.get("formId");
				
				if ("YES".equals(departmentHead) && StrFormId != null && !"".equals(StrFormId)) {
					long formId = NumberUtil.parseLong(StrFormId);
					AfterwardsProjectSummaryDO summaryDO = afterwardsProjectSummaryDAO
						.findByFormId(formId);
					if (summaryDO != null) {
						//风险部总监 和 该部门的分管副总发送消息
						sendMessage1(summaryDO);
					}
				}
				return null;
			}
		}, null, null);
	}
	
	/**
	 * 提交表单
	 * @param order
	 * @return
	 * @see com.born.fcs.pm.biz.service.common.WorkflowExtProcessService#startBeforeProcess(com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder)
	 */
	@Override
	public void startAfterProcess(FormInfo form, WorkflowResult workflowResult) {
		
		long formId = form.getFormId();
		
		AfterwardsProjectSummaryDO project = afterwardsProjectSummaryDAO.findByFormId(formId);
		if (project == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "保后项目汇总信息未找到");
		}
		
	}
	
	/**
	 * 流程结束处理 审查通过
	 * @param formInfo
	 * @param workflowResult
	 * @see com.born.fcs.pm.biz.service.common.WorkflowExtProcessService#endFlowProcess(com.born.fcs.pm.ws.info.common.FormInfo,
	 * com.born.fcs.pm.integration.bpm.result.WorkflowResult)
	 */
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		AfterwardsProjectSummaryDO project = afterwardsProjectSummaryDAO.findByFormId(formId);
		if (project == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "保后项目汇总信息未找到");
		}
		
	}
	
	//流程结束处理 审查未通过
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		AfterwardsProjectSummaryDO project = afterwardsProjectSummaryDAO.findByFormId(formId);
		if (project == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "保后项目汇总信息未找到");
		}
		
	}
	
	//测回
	@Override
	public void cancelAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		AfterwardsProjectSummaryDO project = afterwardsProjectSummaryDAO.findByFormId(formId);
		if (project == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "保后项目汇总信息未找到");
		}
	}
	
	//删除
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		long formId = formInfo.getFormId();
		AfterwardsProjectSummaryDO project = afterwardsProjectSummaryDAO.findByFormId(formId);
		if (project == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "保后项目汇总信息未找到");
		}
		afterwardsProjectSummaryDAO.deleteByFormId(project.getFormId());
		
	}
	
	// 发送消息
	public void sendMessage1(AfterwardsProjectSummaryDO summaryDO) {
		
		MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
		StringBuffer sb = new StringBuffer();
		String url = "/projectMg/afterwardsSummary/viewSummary.htm" + "?formId="
						+ summaryDO.getFormId();
		sb.append(summaryDO.getDeptName() + "已提交" + summaryDO.getReportPeriod() + "保后项目汇总报告，");
		sb.append("<a href='" + url + "'>点击查看详情</a>");
		String content = sb.toString();
		messageOrder.setMessageContent(content);
		
		List<SimpleUserInfo> sendUserList = new ArrayList<SimpleUserInfo>();
		
		//		List<SimpleUserInfo> listUserInfo = projectRelatedUserService.getDeptRoleUser(summaryDO
		//			.getDeptCode(), sysParameterServiceClient
		//			.getSysParameterValue(SysParamEnum.SYS_PARAM_FGFZ_ROLE_NAME.code())); 
		// 分管副总
		List<SimpleUserInfo> listUserInfo = projectRelatedUserService.getFgfz(summaryDO
			.getDeptCode());
		for (SimpleUserInfo simpleUserInfo : listUserInfo) {
			sendUserList.add(simpleUserInfo);
		}
		
		//		List<SimpleUserInfo> listUserInfoFX = projectRelatedUserService.getDeptRoleUser(
		//			sysParameterServiceClient.getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_DEPT_CODE
		//				.code()), sysParameterServiceClient
		//				.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code()));// 风险部门负责人
		//		if (listUserInfoFX != null) {
		//			for (SimpleUserInfo simpleUserInfo : listUserInfoFX) {
		//				sendUserList.add(simpleUserInfo);
		//			}
		//		}
		// 风险部门负责人
		List<SysUser> fzrList = bpmUserQueryService.findChargeByOrgCode(sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_DEPT_CODE.code()));
		if (ListUtil.isNotEmpty(fzrList)) {
			for (SysUser fzr : fzrList) {
				SimpleUserInfo user = new SimpleUserInfo();
				user.setUserId(fzr.getUserId());
				user.setUserAccount(fzr.getAccount());
				user.setUserName(fzr.getFullname());
				user.setMobile(fzr.getMobile());
				user.setEmail(fzr.getEmail());
				sendUserList.add(user);
			}
		}
		
		messageOrder.setSendUsers((SimpleUserInfo[]) sendUserList
			.toArray(new SimpleUserInfo[sendUserList.size()]));
		siteMessageServiceClient.addMessageInfo(messageOrder);
	}
}
