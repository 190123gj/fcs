package com.born.fcs.fm.biz.service.innerLoan;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.biz.exception.ExceptionFactory;
import com.born.fcs.fm.biz.service.base.BaseProcessService;
import com.born.fcs.fm.integration.bpm.result.WorkflowResult;
import com.born.fcs.fm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.FormInnerLoanTypeEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.fm.ws.info.innerLoan.FormInnerLoanInfo;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormFeeOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountOrder;
import com.born.fcs.fm.ws.result.innerLoan.FormInnerLoanResult;
import com.born.fcs.fm.ws.service.common.ReceiptPaymentFormService;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.fm.ws.service.innerLoan.FormInnerLoanService;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.google.common.collect.Lists;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;

@Service("formInnerLoanProcessService")
public class FormInnerLoanProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	FormInnerLoanService formInnerLoanService;
	
	@Autowired
	ReceiptPaymentFormService receiptPaymentFormService;
	
	@Autowired
	ForecastService forecastService;
	
	/**
	 * 流程通过 同步信息到 待处理收/付单据
	 */
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		
		try {
			FormInnerLoanResult innerLoanResult = formInnerLoanService.findByFormId(formInfo
				.getFormId());
			
			logger.info("完成内部借款单处理：{}", innerLoanResult);
			FormInnerLoanInfo info = innerLoanResult.getFormInnerLoanInfo();
			//同步至 待处理收/付单据
			ReceiptPaymentFormOrder receiptPaymentOrder = new ReceiptPaymentFormOrder();
			receiptPaymentOrder.setSourceForm(SourceFormEnum.INNER_LOAN);
			receiptPaymentOrder.setSourceFormId(String.valueOf(formInfo.getFormId()));
			receiptPaymentOrder.setSourceFormSys(SystemEnum.FM);
			//			receiptPaymentOrder.setProjectCode(projectCode)
			//			receiptPaymentOrder.setContractNo(info.getProtocolCode());
			//			receiptPaymentOrder.setContractName(info.getProtocolCode());
			
			receiptPaymentOrder.setUserId(formInfo.getUserId());
			receiptPaymentOrder.setUserAccount(formInfo.getUserAccount());
			receiptPaymentOrder.setUserName(formInfo.getUserName());
			//			receiptPaymentOrder.setDeptId(formInfo.getDeptId());
			//			receiptPaymentOrder.setDeptCode(formInfo.getDeptCode());
			//			receiptPaymentOrder.setDeptName(formInfo.getDeptName());
			
			Org org = bpmUserQueryService.findDeptById(new Long(info.getCreditorId()));
			receiptPaymentOrder.setDeptId(org.getId());
			receiptPaymentOrder.setDeptCode(org.getCode());
			receiptPaymentOrder.setDeptName(org.getName());
			
			receiptPaymentOrder.setRemark(info.getCreditorName());
			
			// 增加费用明细
			List<ReceiptPaymentFormFeeOrder> feeOrderList = new ArrayList<ReceiptPaymentFormFeeOrder>();
			ReceiptPaymentFormFeeOrder formFeeOrder = new ReceiptPaymentFormFeeOrder();
			formFeeOrder.setFeeType(SubjectCostTypeEnum.INTERNAL_FUND_LENDING);
			formFeeOrder.setAmount(info.getLoanAmount());
			formFeeOrder.setRemark("内部借款单");
			feeOrderList.add(formFeeOrder);
			receiptPaymentOrder.setFeeOrderList(feeOrderList);
			receiptPaymentFormService.add(receiptPaymentOrder);
			
			// 20161121 备用金类型不增加收款单数据
			if (FormInnerLoanTypeEnum.PETTY_CASH != info.getInnerLoanType()) {
				
				// 20161115 增加一份收款单数据
				receiptPaymentOrder.setSourceForm(SourceFormEnum.INNER_LOAN_IN);
				formFeeOrder.setFeeType(SubjectCostTypeEnum.INNER_ACCOUNT_LENDING);
				org = bpmUserQueryService.findDeptById(info.getApplyDeptId());
				receiptPaymentOrder.setDeptId(org.getId());
				receiptPaymentOrder.setDeptCode(org.getCode());
				receiptPaymentOrder.setDeptName(org.getName());
				receiptPaymentFormService.add(receiptPaymentOrder);
			}
			
			//同步预测数据
			ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
			forecastAccountOrder.setForecastStartTime(info.getBackTime());
			forecastAccountOrder.setAmount(info.getLoanAmount());
			forecastAccountOrder.setForecastMemo("内部借款还款（" + info.getProtocolCode() + "）");
			forecastAccountOrder.setForecastType(ForecastTypeEnum.IN_INNER_LOAN);
			forecastAccountOrder.setFundDirection(FundDirectionEnum.IN);
			forecastAccountOrder.setOrderNo(info.getProtocolCode() + "_" + info.getFormId());
			forecastAccountOrder.setSystemForm(SystemEnum.FM);
			forecastAccountOrder.setUsedDeptId(String.valueOf(info.getApplyDeptId()));
			forecastAccountOrder.setUsedDeptName(info.getApplyDeptName());
			forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.IN_INNER_LOAN);
			logger.info("内部资金拆借还款资金流入预测 , protocolCode：{}, forecastAccountOrder：{} ",
				info.getProtocolCode(), forecastAccountOrder);
			forecastService.add(forecastAccountOrder);
			
		} catch (Exception e) {
			logger.error("完成内部借款单处理出错：{}", e);
		}
		
	}
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		
		List<FlowVarField> fields = Lists.newArrayList();
		
		try {
			FormInnerLoanResult innerLoanResult = formInnerLoanService.findByFormId(formInfo
				.getFormId());
			FormInnerLoanInfo info = innerLoanResult.getFormInnerLoanInfo();
			FormInnerLoanTypeEnum type = info.getInnerLoanType();
			if (type == null) {
				type = FormInnerLoanTypeEnum.LOAN_AGREEMENT;
			}
			FlowVarField flowVarField = new FlowVarField();
			flowVarField.setVarName("innerLoanType");//借款单据类型
			flowVarField.setVarType(FlowVarTypeEnum.STRING);
			flowVarField.setVarVal(type.code());
			fields.add(flowVarField);
			
			Org org = bpmUserQueryService.findDeptById(info.getApplyDeptId());
			// 20161124 传入是否有总经理，若有总经理 传入部门负责人。分管副总
			//  传入 部门负责人id 借款部门的部门负责人
			List<SimpleUserInfo> users = projectRelatedUserService.getRoleDeptUser(info
				.getApplyDeptId(), sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code()));
			if (users != null && ListUtil.isNotEmpty(users)) {
				String str = "";
				for (SimpleUserInfo userInfo : users) {
					if (StringUtil.isNotEmpty(str)) {
						str += ",";
					}
					str += userInfo.getUserId();
				}
				flowVarField = new FlowVarField();
				flowVarField.setVarName("departmentHead");//
				flowVarField.setVarType(FlowVarTypeEnum.STRING);
				flowVarField.setVarVal(str);
				fields.add(flowVarField);
			}
			//  传入分管副总id 借款部门的分管副总 
			users = projectRelatedUserService.getFgfz(org.getCode());
			if (users != null && ListUtil.isNotEmpty(users)) {
				String str = "";
				for (SimpleUserInfo userInfo : users) {
					if (StringUtil.isNotEmpty(str)) {
						str += ",";
					}
					str += userInfo.getUserId();
				}
				flowVarField = new FlowVarField();
				flowVarField.setVarName("master");//
				flowVarField.setVarType(FlowVarTypeEnum.STRING);
				flowVarField.setVarVal(str);
				fields.add(flowVarField);
			}
			//   是否财务  按照借款部门判定
			String department = "";
			if (org.getName().contains("财务部")) {
				department = "caiwu";
			}
			//			else if (org.getName().contains("分公司")) {
			//				department = "fengongsi";
			//			}
			flowVarField = new FlowVarField();
			flowVarField.setVarName("isFinance");//
			flowVarField.setVarType(FlowVarTypeEnum.STRING);
			if (StringUtil.isNotEmpty(department)) {
				flowVarField.setVarVal("Y");
			} else {
				flowVarField.setVarVal("N");
			}
			fields.add(flowVarField);
			// 总经理
			users = projectRelatedUserService.getRoleDeptUser(info.getApplyDeptId(),
				sysParameterServiceClient.getSysParameterValue(SysParamEnum.SYS_PARAM_ZJL_ROLE_NAME
					.code()));
			// 分公司总经理
			List<SimpleUserInfo> users2 = projectRelatedUserService.getRoleDeptUser(info
				.getApplyDeptId(), sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_FGSZJL_ROLE_NAME.code()));
			if (users == null) {
				users = users2;
			} else {
				users.addAll(users2);
			}
			flowVarField = new FlowVarField();
			flowVarField.setVarName("hasGeneralManager");//借款单据类型
			flowVarField.setVarType(FlowVarTypeEnum.STRING);
			if (users != null && ListUtil.isNotEmpty(users)) {
				// 代表拥有总经理，传有总经理
				String str = "";
				for (SimpleUserInfo userInfo : users) {
					if (!str.contains(String.valueOf(userInfo.getUserId()))) {
						if (StringUtil.isNotEmpty(str)) {
							str += ",";
						}
						str += userInfo.getUserId();
					}
				}
				if (StringUtil.isNotEmpty(str)) {
					flowVarField.setVarVal(str);
				} else {
					flowVarField.setVarVal("0");
				}
			} else {
				flowVarField.setVarVal("0");
			}
			fields.add(flowVarField);
			
			//金额
			FlowVarField amount = new FlowVarField();
			amount.setVarName("money");
			amount.setVarType(FlowVarTypeEnum.STRING);
			amount.setVarVal(info.getLoanAmount().toString());
			fields.add(amount);
		} catch (Exception e) {
			logger.error("{}", e);
			throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE, e.getMessage());
		}
		
		return fields;
	}
	
	//默认返回表单的提交人
	@Override
	public List<SimpleUserInfo> resultNotifyUserList(FormInfo formInfo) {
		List<SimpleUserInfo> list = Lists.newArrayList();
		SimpleUserInfo user = new SimpleUserInfo();
		BeanCopier.staticCopy(formInfo, user);
		user.setMobile(formInfo.getUserMobile());
		user.setEmail(formInfo.getUserEmail());
		list.add(user);
		
		if (FormStatusEnum.APPROVAL == formInfo.getStatus()) {
			
			// 20161124 添加财务应付岗
			String roleName = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_CWYFG_ROLE_NAME.code());
			
			List<SysUser> users = bpmUserQueryService.findUserByRoleAlias(roleName);
			if (users != null && ListUtil.isNotEmpty(users)) {
				for (SysUser sendUser : users) {
					SimpleUserInfo userInfo = new SimpleUserInfo();
					userInfo.setUserId(sendUser.getUserId());
					userInfo.setUserName(sendUser.getFullname());
					userInfo.setUserAccount(sendUser.getAccount());
					userInfo.setEmail(sendUser.getEmail());
					userInfo.setMobile(sendUser.getMobile());
					list.add(userInfo);
				}
			}
		}
		return list;
	}
	
}
