package com.born.fcs.face.web.controller.project.afterloan.expireproject;

import javax.servlet.http.HttpServletRequest;

import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.info.expireproject.ExpireProjectNoticeTemplateInfo;
import com.born.fcs.pm.ws.order.common.ChangeProjectStatusOrder;
import com.born.fcs.pm.ws.order.expireproject.ExpireProjectNoticeTemplateOrder;
import com.born.fcs.pm.ws.result.common.ProjectResult;
import com.yjf.common.lang.util.money.Money;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.ws.enums.ExpireProjectStatusEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.expireproject.ExpireFormProjectInfo;
import com.born.fcs.pm.ws.info.expireproject.ExpireProjectInfo;
import com.born.fcs.pm.ws.order.expireproject.ExpireProjectOrder;
import com.born.fcs.pm.ws.order.expireproject.ExpireProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

import java.util.Date;

/**
 * 
 * 授信后管理 到期项目
 *
 *
 * @author lirz
 *
 * 2016-4-22 上午9:36:32
 */
@Controller
@RequestMapping("projectMg/expireProject")
public class ExpireProjectController extends BaseController {
	
	private static final String VM_PATH = "/projectMg/afterLoanMg/timeOutNotice/";
	
	/**
	 * 查询到期逾期项目列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		
		try {
			ExpireProjectQueryOrder queryOrder = new ExpireProjectQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<ExpireFormProjectInfo> batchResult = expireProjectServiceClient
				.queryExpireFormProjectInfo(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错");
		}
		
		return VM_PATH + "list.vm";
	}
	
	/**
	 * 打印通知
	 * 
	 * @param request
	 * @param model
	 * @param projectCode 项目编号
	 * @return
	 */
	@RequestMapping("printNotice.htm")
	public String printNotice(HttpServletRequest request, Model model, String projectCode) {
		String error = VM_PATH + "printNoticeError.vm";
		try {
			ExpireProjectInfo info = expireProjectServiceClient
				.queryExpireProjectByProjectCode(projectCode);
			if (null != info
				&& (info.getStatus() == ExpireProjectStatusEnum.EXPIRE || info.getStatus() == ExpireProjectStatusEnum.OVERDUE)) {
				model.addAttribute("info", info);
				//项目信息
				ProjectInfo project = projectServiceClient.queryByCode(projectCode, false);
				model.addAttribute("project", project);
				
				if (info.getStatus() == ExpireProjectStatusEnum.EXPIRE) { //到期
					if (null != project && isUnderwriting(project.getBusiType())) {
						return VM_PATH + "tplCXIsDue.vm";
					} else {
						return VM_PATH + "tplCreditIsDue.vm";
					}
				} else {
					return VM_PATH + "tplCreditOverdue.vm";
				}
			} else {
				return error;
			}
		} catch (Exception e) {
			logger.error("查询出错");
		}
		
		return error;
	}


	/**
	 * 到期通知
	 *
	 * @param request
	 * @param model
	 * @param projectCode 项目编号
	 * @return
	 */
	@RequestMapping("notice.htm")
	public String notice(HttpServletRequest request,Long stampFormId,String templateId,String fromStamp, Model model, String projectCode,String expireId) {
		String error = VM_PATH + "printNoticeError.vm";
		try {
			if(templateId!=null){
				ExpireProjectNoticeTemplateInfo templateInfo=expireProjectServiceClient.findTemplateById(Long.parseLong(templateId));
				ExpireProjectInfo info = expireProjectServiceClient
						.queryExpireProjectByExpireId(templateInfo.getExpireId()+"");
				model.addAttribute("fromStamp",fromStamp);
				model.addAttribute("templateInfo",templateInfo);
				model.addAttribute("isCanPrint", DataPermissionUtil.isCanPrint(stampFormId));
				model.addAttribute("info", info);
				return VM_PATH + "viewNotice.vm";
			}
			ExpireProjectInfo info = expireProjectServiceClient
					.queryExpireProjectByExpireId(expireId);
			ProjectInfo project = projectServiceClient.queryByCode(info.getProjectCode(), false);
			boolean isUnderwriting= ProjectUtil.isUnderwriting(project.getBusiType());//是否承销
			String status=info.getStatus().code();
			if(isUnderwriting){//承销项目只有到期通知
				status=ExpireProjectStatusEnum.EXPIRE.code();
			}
			ExpireProjectNoticeTemplateInfo templateInfo=expireProjectServiceClient.findTemplateByExpireIdAndStatus(expireId,status);
			if(templateInfo!=null){
				model.addAttribute("templateInfo",templateInfo);
				model.addAttribute("isCanPrint", DataPermissionUtil.isCanPrint(stampFormId));
				model.addAttribute("info", info);
				return VM_PATH + "viewNotice.vm";
			}
			if (null != info
					&& (info.getStatus() == ExpireProjectStatusEnum.EXPIRE || info.getStatus() == ExpireProjectStatusEnum.OVERDUE)) {
				model.addAttribute("info", info);
				//项目信息
				model.addAttribute("project", project);
				model.addAttribute("nowDate", new Date());
				Money releasingAmount=project.getLoanedAmount().subtract(project.getReleasedAmount());//在保余额，已放款金额-已解保金额
				model.addAttribute("releasingAmount", MoneyUtil.digitUppercase(releasingAmount.getCent()));
				model.addAttribute("accumulatedIssueAmount", MoneyUtil.digitUppercase(project.getAccumulatedIssueAmount().getCent()));
				if (info.getStatus() == ExpireProjectStatusEnum.EXPIRE) { //到期
					if (null != project && isUnderwriting(project.getBusiType())) {
						return VM_PATH + "tplCXIsDueAdd.vm";
					} else {
						return VM_PATH + "tplCreditIsDueAdd.vm";
					}
				} else {
					if (null != project && isUnderwriting(project.getBusiType())) {
						return VM_PATH + "tplCXIsDueAdd.vm";
					} else {
						return VM_PATH + "tplCreditOverdueAdd.vm";
					}
				}
			} else {
				return error;
			}
		} catch (Exception e) {
			logger.error("查询出错");
		}

		return error;
	}

	/**
	 * 到期项目 上传回执
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping("uploandReceipt.json")
	@ResponseBody
	public Object uploandReceipt(ExpireProjectOrder order) {
		String tipPrefix = "上传回执";
		JSONObject json = new JSONObject();
		try {
			ExpireProjectInfo expireProjectInfo=expireProjectServiceClient.queryExpireProjectByExpireId(order.getId()+"");
			FcsBaseResult result = expireProjectServiceClient.updateToDone(order);
			if(result.isSuccess()&&"IS".equals(order.getIsDONE())){//更新project表状态为已完成
				ChangeProjectStatusOrder changeOrder = new ChangeProjectStatusOrder();
				changeOrder.setStatus(ProjectStatusEnum.FINISH);
				changeOrder.setPhaseStatus(ProjectPhasesStatusEnum.APPROVAL);
				changeOrder.setPhase(ProjectPhasesEnum.FINISH_PHASES);
				changeOrder.setProjectCode(expireProjectInfo.getProjectCode());
				ProjectResult presult = projectServiceClient.changeProjectStatus(changeOrder);
				if (!presult.isSuccess()) {
					logger.error("更新项目状态出错：" + presult.getMessage());
				}
			}
			json = toJSONResult(result, tipPrefix);
			return json;
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		
		return json;
	}

	/**
	 * 保存到期项目通知模板
	 *
	 * @param order
	 * @return
	 */
	@RequestMapping("saveNoticeTemplate.htm")
	@ResponseBody
	public JSONObject saveNoticeTemplate(ExpireProjectNoticeTemplateOrder order, Model model) {
		String tipPrefix = " 保存到期项目通知模板";
		JSONObject result = new JSONObject();
		try {
			ExpireProjectInfo expireProjectInfo=expireProjectServiceClient.queryExpireProjectByExpireId(order.getExpireId()+"");
			ProjectInfo project = projectServiceClient.queryByCode(expireProjectInfo.getProjectCode(), false);
			boolean isUnderwriting= ProjectUtil.isUnderwriting(project.getBusiType());//是否承销
			setSessionLocalInfo2Order(order);
			ExpireProjectStatusEnum status=expireProjectInfo.getStatus();
			if(isUnderwriting){//承销项目只有到期通知
				order.setIsUnderwriting("IS");
				status=ExpireProjectStatusEnum.EXPIRE;
				order.setStatus(ExpireProjectStatusEnum.EXPIRE);
			}else {
				order.setIsUnderwriting("");
				status=expireProjectInfo.getStatus();
				order.setStatus(expireProjectInfo.getStatus());
			}
			FcsBaseResult saveResult = expireProjectServiceClient
					.saveNoticeTemplate(order);
			if (saveResult.isSuccess()) {
				ExpireProjectNoticeTemplateInfo info=expireProjectServiceClient.findTemplateByExpireIdAndStatus(order.getExpireId()+"",status.code());
				result.put("id",info.getId());
				result.put("year",info.getYear());
				result.put("sequence",info.getSequence());
				result.put("success", true);
				result.put("message", "保存成功");
			} else {
				result.put("success", false);
				result.put("message", saveResult.getMessage());
			}
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		return result;
	}
}
