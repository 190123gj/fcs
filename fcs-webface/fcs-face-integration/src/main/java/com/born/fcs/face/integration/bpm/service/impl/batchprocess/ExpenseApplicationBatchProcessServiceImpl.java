package com.born.fcs.face.integration.bpm.service.impl.batchprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.bpm.service.BpmUserQueryService;
import com.born.fcs.face.integration.bpm.service.impl.batchprocess.base.WorkflowBatchProcessServiceBaseImpl;
import com.born.fcs.fm.ws.enums.CostDirectionEnum;
import com.born.fcs.fm.ws.enums.ReportCompanyEnum;
import com.born.fcs.fm.ws.enums.SubjectStatusEnum;
import com.born.fcs.fm.ws.info.bank.BankMessageInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationInfo;
import com.born.fcs.fm.ws.order.bank.BankMessageQueryOrder;
import com.born.fcs.fm.ws.service.bank.BankMessageService;
import com.born.fcs.fm.ws.service.payment.ExpenseApplicationService;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.StringUtil;

/**
 * 费用支付批量审核处理
 * @author wuzj
 */
@Service("expenseApplicationBatchProcessService")
public class ExpenseApplicationBatchProcessServiceImpl extends WorkflowBatchProcessServiceBaseImpl {
	
	@Autowired
	ExpenseApplicationService expenseApplicationServiceClient;
	
	@Autowired
	BankMessageService bankMessageServiceClient;
	
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	
	private ThreadLocal<FormExpenseApplicationInfo> infoLocal = new ThreadLocal<FormExpenseApplicationInfo>();
	
	@Override
	public boolean isSupport(FormInfo form, SimpleUserInfo executor) {
		
		//获取当前审核任务url
		String taskUrl = getUserTaskUrl(form, executor.getUserId());
		
		if (taskUrl.startsWith("/fundMg/expenseApplication/audit/canModify.htm")) {
			//特殊节点
			FormExpenseApplicationInfo info = expenseApplicationServiceClient.queryByFormId(form
				.getFormId());
			setDefaultPayBank(info);
			infoLocal.set(info);
			//不存在默认账户就不支持批量审核
			if (StringUtil.isBlank(info.getPayBankAccount())
				|| StringUtil.isBlank(info.getPayBank())) {
				return false;
			} else {//存在默认账户不支持批量审核
				return true;
			}
		} else {
			return true;
		}
	}
	
	@Override
	public Map<String, Object> makeCustomizeMap(FormInfo form, SimpleUserInfo executor) {
		
		//获取当前审核任务url
		String taskUrl = getUserTaskUrl(form, executor.getUserId());
		
		Map<String, Object> customizeMap = super.makeCustomizeMap(form, executor);
		
		if (taskUrl.startsWith("/fundMg/expenseApplication/audit/canModify.htm")) { //特殊节点
		
			FormExpenseApplicationInfo info = infoLocal.get();
			if (info == null || form.getFormId() != info.getFormId())
				info = expenseApplicationServiceClient.queryByFormId(form.getFormId());
			
			//不存在默认账户就不支持批量审核
			if (StringUtil.isBlank(info.getPayBankAccount())
				|| StringUtil.isBlank(info.getPayBank())) {
				setDefaultPayBank(info);
			}
			//设置流程审核自定义变量
			if (StringUtil.isNotBlank(info.getPayBankAccount())
				|| StringUtil.isNotBlank(info.getPayBank())) {
				customizeMap.put("isFYFG", "true");
				customizeMap.put("bank", info.getPayBank());
				customizeMap.put("bankAccount", info.getPayBankAccount());
				if (info.getDirection() != CostDirectionEnum.PRIVATE) {
					customizeMap.put("bankId", info.getBankId());
				}
			}
		} else {
			customizeMap.put("isFYFG", "false");
		}
		
		return customizeMap;
	}
	
	/**
	 * 设置默认支付账户
	 * @param info
	 */
	private void setDefaultPayBank(FormExpenseApplicationInfo info) {
		if ((StringUtil.isBlank(info.getPayBankAccount()) && StringUtil.isBlank(info.getPayBank()))) {
			getPayBank(info);
		}
	}
	
	protected void getPayBank(FormExpenseApplicationInfo info) {
		BankMessageQueryOrder order = new BankMessageQueryOrder();
		if (CostDirectionEnum.PRIVATE == info.getDirection()) {
			order.setDefaultPersonalAccount(BooleanEnum.YES);
		} else {
			order.setDefaultCompanyAccount(BooleanEnum.YES);
		}
		
		int setCount = 0;
		order.setStatus(SubjectStatusEnum.NORMAL);
		QueryBaseBatchResult<BankMessageInfo> bankMessageInfo = bankMessageServiceClient
			.queryBankMessageInfo(order);
		if (bankMessageInfo != null && bankMessageInfo.getPageList() != null
			&& !bankMessageInfo.getPageList().isEmpty()) {
			// 20161104 添加判断，按照部门选择默认银行信息
			//					SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			//					String localCode = sessionLocal.getUserDetailInfo().getPrimaryOrg().getCode(); //id code name
			Long localId = info.getExpenseDeptId();
			Org orgXH = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XINHUI.getDeptCode());
			Org orgBF = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.BEIJING.getDeptCode());
			// 诚本公司
			Org orgCB = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CB.getDeptCode());
			// 诚鑫公司
			Org orgCX = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CX.getDeptCode());
			// 诚融公司
			Org orgCR = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CR.getDeptCode());
			// 诚正公司
			Org orgCZ = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CZ.getDeptCode());
			// 诚远公司
			Org orgCY = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XHTZ_CY.getDeptCode());
			List<Long> spcDeptIds = new ArrayList<Long>();
			if (orgCB != null) {
				spcDeptIds.add(orgCB.getId());
			}
			if (orgCX != null) {
				spcDeptIds.add(orgCX.getId());
			}
			if (orgCR != null) {
				spcDeptIds.add(orgCR.getId());
			}
			if (orgCZ != null) {
				spcDeptIds.add(orgCZ.getId());
			}
			if (orgCY != null) {
				spcDeptIds.add(orgCY.getId());
			}
			if (orgXH.getId() == localId) {
				// 信汇
				for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
					if (bankInfo.getDeptId() == orgXH.getId()) {
						// 添加判断，出现多个对应部门的默认账户，不给默认值
						if (setCount == 0) {
							setCount++;
							info.setPayBank(bankInfo.getBankName());
							info.setPayBankAccount(bankInfo.getAccountNo());
						} else {
							info.setPayBank("");
							info.setPayBankAccount("");
						}
					}
				}
			} else if (orgBF.getId() == localId) {
				// 北分
				for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
					if (bankInfo.getDeptId() == orgBF.getId()) {
						// 添加判断，出现多个对应部门的默认账户，不给默认值
						if (setCount == 0) {
							setCount++;
							info.setPayBank(bankInfo.getBankName());
							info.setPayBankAccount(bankInfo.getAccountNo());
						} else {
							info.setPayBank("");
							info.setPayBankAccount("");
						}
					}
				}
			} else if (spcDeptIds.contains(localId)) {
				for (Long spcId : spcDeptIds) {
					if (spcId.equals(localId)) {
						
						for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
							if (bankInfo.getDeptId() == spcId) {
								// 添加判断，出现多个对应部门的默认账户，不给默认值
								if (setCount == 0) {
									setCount++;
									info.setPayBank(bankInfo.getBankName());
									info.setPayBankAccount(bankInfo.getAccountNo());
								} else {
									info.setPayBank("");
									info.setPayBankAccount("");
								}
							}
						}
					}
				}
				// 有多个的情况 先找自己 再找公司 
				// 若找完自己时没有值，再找所有
				if (StringUtil.isBlank(info.getPayBank())) {
					for (Long spcId : spcDeptIds) {
						for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
							if (bankInfo.getDeptId() == spcId) {
								// 添加判断，出现多个对应部门的默认账户，不给默认值
								if (setCount == 0) {
									setCount++;
									info.setPayBank(bankInfo.getBankName());
									info.setPayBankAccount(bankInfo.getAccountNo());
								} else {
									info.setPayBank("");
									info.setPayBankAccount("");
								}
							}
						}
					}
				}
			}
			//				else if(ReportCompanyEnum.XINHUI.getDeptCode().equals(localCode)){
			//					
			//				}
			else {
				// 主部门
				for (BankMessageInfo bankInfo : bankMessageInfo.getPageList()) {
					// 添加判断。优先寻找部门相同的部门信息
					
					if (localId == bankInfo.getDeptId()) {
						info.setPayBank(bankInfo.getBankName());
						info.setPayBankAccount(bankInfo.getAccountNo());
						break;
					}
					
					if (bankInfo.getDeptId() != orgBF.getId()
						&& bankInfo.getDeptId() != orgXH.getId()
						&& !spcDeptIds.contains(bankInfo.getDeptId())) {
						// 添加判断，出现多个对应部门的默认账户，不给默认值
						if (setCount == 0) {
							setCount++;
							info.setPayBank(bankInfo.getBankName());
							info.setPayBankAccount(bankInfo.getAccountNo());
						} else {
							info.setPayBank("");
							info.setPayBankAccount("");
						}
					}
				}
			}
			
			//				info.setPayBank(bankMessageInfo.getPageList().get(0).getBankName());
			//				info.setPayBankAccount(bankMessageInfo.getPageList().get(0).getAccountNo());}
		}
	}
}
