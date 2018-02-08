package com.born.fcs.pm.biz.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectExtendInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.common.ProjectExtendQueryOrder;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectExtendService;
import com.born.fcs.pm.ws.service.common.ProjectService;

@Service("projectExtendProcessService")
public class ProjectExtendProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	ProjectExtendService projectExtendService;
	
	@Autowired
	ProjectService projectService;
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = createResult();
		try {
			FormInfo form = order.getFormInfo();
			if (form.getFormCode() == FormCodeEnum.PROJECT_SUMMARY) {//项目小结
				ProjectExtendQueryOrder qOrder = new ProjectExtendQueryOrder();
				qOrder.setFormId(form.getFormId());
				QueryBaseBatchResult<ProjectExtendInfo> extendInfo = projectExtendService
					.query(qOrder);
				if (extendInfo != null && extendInfo.getTotalCount() > 0) {
					ProjectExtendInfo extend = extendInfo.getPageList().get(0);
					ProjectInfo project = projectService
						.queryByCode(extend.getProjectCode(), false);
					if (project != null) {
						//自定义待办任务名称
						WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder
							.get().getAttribute("startOrder");
						if (startOrder != null) {
							startOrder.setCustomTaskName(project.getProjectName() + "-项目小结");
						}
					}
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			logger.error("项目扩展属性表单流程启动前处理出错{}", e);
		}
		return result;
	}
}
