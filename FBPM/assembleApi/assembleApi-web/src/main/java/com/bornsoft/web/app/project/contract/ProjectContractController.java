package com.bornsoft.web.app.project.contract;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ContractStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbPhaseEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractItemInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractResultInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContrctInfo;
import com.born.fcs.pm.ws.order.contract.ProjectContractItemOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.BornApiRequestUtils;
import com.bornsoft.utils.tool.GsonUtil;
import com.bornsoft.utils.tool.ReflectUtils;
import com.bornsoft.web.app.base.WorkflowBaseController;
import com.bornsoft.web.app.util.PageUtil;
import com.yjf.common.lang.util.money.Money;

@Controller
@RequestMapping("projectMg/contract")
public class ProjectContractController extends WorkflowBaseController {
	final static String vm_path = "/projectMg/beforeLoanMg/contract/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "operateDate", "signedTime", "approvedTime" };
	}
	
	/**
	 * 合同列表
	 * 
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("list.json")
	public JSONObject projectContractList(ProjectContractQueryOrder queryOrder, HttpServletRequest request) {
		logger.info("查询合同列表，入参={}", ReflectUtils.toString(queryOrder));
		JSONObject result = null;
		try {
			String likeCodeOrName = request.getParameter(LIKE_CODE_OR_NAME);
			if(StringUtils.isNotBlank(likeCodeOrName)){
				queryOrder.setLikeContractCodeOrName(likeCodeOrName);
			}
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<ProjectContractResultInfo> batchResult = projectContractServiceClient
					.query(queryOrder);
			result = toJSONResult(batchResult);
			result.put("page", GsonUtil.toJSONObject(PageUtil.getCovertPage(batchResult)));
		} catch (Exception e) {
			logger.error("查询合同列表失败： ", e);
			result = toJSONResult(AppResultCodeEnum.FAILED, "查询");
		}
		logger.info("查询合同列表，出参={}", result);
		return result;
	}
	
	/**
	 * 合同列表
	 * 
	 * @param model
	 * @return
	 */
	public JSONObject queryMainContract(ProjectContractQueryOrder queryOrder, Model model) {
		logger.info("查询主合同，入参={}", ReflectUtils.toString(queryOrder));
		JSONObject result = new JSONObject();
		try {
			ProjectContractResultInfo contractInfo = getMainContract(queryOrder);
			if(contractInfo!=null){
				result.put("contractInfo", GsonUtil.toJSONObject(contractInfo));
				result = toJSONResult(result,AppResultCodeEnum.SUCCESS, "查询成功");
			}else{
				result = toJSONResult(AppResultCodeEnum.FAILED, "未找到主合同");
			}
		} catch (Exception e) {
			logger.error("查询主合同失败： ", e);
			result = toJSONResult(AppResultCodeEnum.FAILED, e.getMessage());
		}
		logger.info("查询主合同，出参={}", result);
		return result;
	}

	private ProjectContractResultInfo getMainContract(
			ProjectContractQueryOrder queryOrder) {
		setSessionLocalInfo2Order(queryOrder);
		
		queryOrder.setContractStatus(ContractStatusEnum.SEAL.code());
		QueryBaseBatchResult<ProjectContractResultInfo> batchResult = projectContractServiceClient
				.query(queryOrder);
		ProjectContractResultInfo contractInfo = null;
		//筛选主合同
		for(ProjectContractResultInfo info : batchResult.getPageList()){
			if(BooleanEnum.IS.code().equals(info.getIsMain())){
				contractInfo = info;
				break;
			}
		}
		return contractInfo;
	}
	
	/**
	 * 查询主合同
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("queryMainContract.json")
	public JSONObject queryMainContract(ProjectContractQueryOrder queryOrder) {
		JSONObject result = new JSONObject();
		try {
			if(StringUtils.isBlank(queryOrder.getProjectCode())){
				throw new BornApiException("项目编号为空");
			}
			ProjectContractResultInfo contractInfo = getMainContract(queryOrder);
			if(contractInfo!=null){
				ProjectContractItemInfo info = projectContractServiceClient.findContractItemById(contractInfo.getId());
				ProjectContrctInfo contrctInfo = projectContractServiceClient
					.findById(info.getContractId());
				ProjectInfo projectInfo = projectServiceClient.queryByCode(contrctInfo.getProjectCode(),
					false);
				ProjectRelatedUserInfo managerB = projectRelatedUserServiceClient.getBusiManagerbByPhase(
					projectInfo.getProjectCode(), ChangeManagerbPhaseEnum.CONTRACT_PHASES);
				
				if (info.getSignedTime() == null || info.getSignPersonA() == null) {
					info.setSignPersonAId(projectInfo.getBusiManagerId());
					info.setSignPersonA(projectInfo.getBusiManagerName());
					info.setSignPersonBId(managerB.getUserId() + "");
					info.setSignPersonB(managerB.getUserName());
				}
				result.put("isBond", ProjectUtil.isBond(projectInfo.getBusiType()));
				result.put("contractInfo", GsonUtil.toJSONObject(info));
				result = toJSONResult(result,AppResultCodeEnum.SUCCESS, "查询成功");
			}else{
				result = toJSONResult(AppResultCodeEnum.FAILED, "未找到主合同");
			}
		} catch (Exception e) {
			logger.error("查询主合同失败:",e);
			result = toJSONResult(AppResultCodeEnum.FAILED, "查询主合同失败"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 合同回传
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveContractBack.json")
	@ResponseBody
	public JSONObject saveContractBack(ProjectContractItemOrder order, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if(order==null||order.getId()==null){
				try {
					order = parseStreamToObject(request, ProjectContractItemOrder.class);
				} catch (Exception e) {
					toJSONResult(jsonObject, AppResultCodeEnum.FAILED, "请求数据不合法");
					return jsonObject;
				}
				if(order==null || order.getId()==null){
					toJSONResult(jsonObject, AppResultCodeEnum.FAILED, "合同ID不能为空");
					return jsonObject;
				}
			}
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			setSessionLocalInfo2Order(order);
			ProjectContractItemInfo itemInfo = projectContractServiceClient
				.findContractItemById(order.getId());
			ProjectContrctInfo contractInfo = projectContractServiceClient.findById(itemInfo
				.getContractId());
			ProjectInfo projectInfo = projectServiceClient.queryByCode(
				contractInfo.getProjectCode(), false);
			
			if (projectInfo.getStatus() == ProjectStatusEnum.PAUSE) {
				toJSONResult(jsonObject, AppResultCodeEnum.FAILED, "该项目已被暂缓，不允许回传！");
				logger.error("合同回传出错", "该项目已被暂缓，不允许回传！");
			}else{
				String contractAmout = BornApiRequestUtils.getParameter(request, "contractAmout");
				if(StringUtils.isNotBlank(contractAmout)){
					order.setContractAmount(Money.amout(contractAmout));
				}
				// 初始化Form验证信息
				FcsBaseResult saveResult = projectContractServiceClient.saveContractBack(order);
				toJSONResult(jsonObject, saveResult, "合同回传成功", null);
			}
		} catch (Exception e) {
			logger.error("合同回传出错", e);
			toJSONResult(jsonObject,AppResultCodeEnum.FAILED, e.getMessage());
		}
		
		return jsonObject;
	}
	
}
