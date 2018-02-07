package com.born.fcs.face.integration.bpm.service.impl;

import java.net.SocketTimeoutException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.bpm.service.client.user.UserDetailsServiceProxy;
import com.born.fcs.face.integration.bpm.service.PasswordManagerService;
import com.born.fcs.face.integration.bpm.service.order.UpdatePasswordOrder;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientBaseSevice;
import com.born.fcs.pm.biz.service.common.OperationJournalService;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.order.common.OperationJournalAddOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

@Service("passwordManagerService")
public class PasswordManagerServiceImpl extends ClientBaseSevice implements PasswordManagerService {
	
	@Autowired
	OperationJournalService operationJournalWebService;
	
	@Override
	public FcsBaseResult updateUserPassword(final UpdatePasswordOrder passwordOrder) {
		FcsBaseResult result = callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				FcsBaseResult result = new FcsBaseResult();
				UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
					propertyConfigService.getBmpServiceUserDetailsService());
				try {
					passwordOrder.check();
					String resultJson = serviceProxy.updatePwd(passwordOrder.getUserName(),
						passwordOrder.getNewPassword());
					Map<String, Object> resultMap = MiscUtil.parseJSON(resultJson);
					if ("1".equals(String.valueOf(resultMap.get("result")))) {
						result.setSuccess(true);
						OperationJournalAddOrder journalAddOrder = new OperationJournalAddOrder();
						journalAddOrder.setBaseModuleName("密码管理");
						journalAddOrder.setPermissionName("修改密码");
						journalAddOrder.setMemo(passwordOrder.getUserName() + "主动修改密码");
						journalAddOrder.setOperationContent("修改密码");
						journalAddOrder.setOperatorId(-1);
						journalAddOrder.setOperatorIp("127.0.0.1");
						journalAddOrder.setOperatorName(passwordOrder.getUserName());
						operationJournalWebService.addOperationJournalInfo(journalAddOrder);
					} else {
						result.setSuccess(false);
						result.setMessage(String.valueOf(resultMap.get("message")));
					}
				} catch (java.rmi.RemoteException e) {
					logger.error(e.getMessage(), e);
					result.setSuccess(false);
					result.setFcsResultEnum(FcsResultEnum.CALL_REMOTE_SERVICE_ERROR);
					result.setMessage(e.getMessage());
				} catch (IllegalArgumentException e) {
					result.setSuccess(false);
					result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
					result.setMessage(e.getMessage());
				}
				
				return result;
			}
			
		});
		return result;
	}
}
