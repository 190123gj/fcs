package com.born.fcs.pm.biz.service.stampapply;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FStampApplyDO;
import com.born.fcs.pm.dal.dataobject.FStampApplyFileDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 替换用印审批流程处理
 *
 * @author heh
 *
 * 2016-4-23 下午1:46:05
 */
@Service("replaceStampProcessService")
public class ReplaceStampProcessServiceImpl extends BaseProcessService {

	@Override
	public Map<String, String> makeMessageVar(FormInfo formInfo) {
		Map<String, String> vars = Maps.newHashMap();
		FStampApplyDO stampApply = fStampApplyDAO.findByFormId(formInfo.getFormId());
		vars.put("申请单编号", stampApply.getApplyCode());
		return vars;
	}
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		FStampApplyDO stampApply = fStampApplyDAO.findByFormId(formInfo.getFormId());
		if (null == stampApply) {
			return null;
		}
		List<FStampApplyFileDO> fileList = fStampApplyFileDAO.findByReplaceApplyId(stampApply
			.getApplyId());
		String isEqual = "Y";
		for (FStampApplyFileDO DO : fileList) {
			long max = DO.getOldLegalChapterNum();
			if (DO.getOldCachetNum() > max) {
				max = DO.getOldCachetNum();
			}
			if (DO.getOldFileNum() != max) {
				isEqual = "N";
				break;
			}
		}
		boolean hasG = false;
		boolean hasF = false;
		String sealType = "F";//F:法人章 G:公章 B:公章、法人章
		for (FStampApplyFileDO DO : fileList) {
			if (DO.getLegalChapterNum() != 0 && DO.getCachetNum() != 0) {
				sealType = "B";
				hasF = true;
				hasG = true;
				break;
			}
			if (DO.getCachetNum() != 0) {
				hasG = true;
			}
			if (DO.getLegalChapterNum() != 0) {
				hasF = true;
			}
		}
		if (hasG && hasF) {
			sealType = "B";
		} else if (hasG && !hasF) {
			sealType = "G";
		} else if (hasF && !hasG) {
			sealType = "F";
		}
		List<FlowVarField> fields = Lists.newArrayList();
		FlowVarField equal = new FlowVarField();
		equal.setVarName("equal");
		equal.setVarType(FlowVarTypeEnum.STRING);
		equal.setVarVal(isEqual);
		fields.add(equal);
		FlowVarField sealID = new FlowVarField();
		sealID.setVarName("sealType");
		sealID.setVarType(FlowVarTypeEnum.STRING);
		sealID.setVarVal(sealType);
		fields.add(sealID);
		return fields;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FStampApplyDO applyDO = fStampApplyDAO.findByFormId(order.getFormInfo().getFormId());
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(applyDO.getProjectName() + "-用印文件替换申请");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("用印文件替换申请流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
}
