package com.born.fcs.fm.integration.pm.service;

import org.springframework.stereotype.Service;

import com.born.fcs.fm.integration.bpm.service.CallExternalInterface;
import com.born.fcs.fm.integration.bpm.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.sms.SMSInfo;
import com.born.fcs.pm.ws.service.sms.SMSService;

@Service("sMSServiceClient")
public class SMSServiceClient extends ClientAutowiredBaseService implements SMSService {
	
	@Override
	public SMSInfo registCdKey() {
		return callInterface(new CallExternalInterface<SMSInfo>() {
			
			@Override
			public SMSInfo call() {
				return sMSWebService.registCdKey();
				
			}
		});
	}
	
	@Override
	public SMSInfo logoutCdKey() {
		return callInterface(new CallExternalInterface<SMSInfo>() {
			
			@Override
			public SMSInfo call() {
				return sMSWebService.logoutCdKey();
				
			}
		});
	}
	
	@Override
	public SMSInfo registdetailinfo(final String ename, final String linkman,
									final String phonenum, final String mobile, final String email,
									final String fax, final String address, final String postcode) {
		return callInterface(new CallExternalInterface<SMSInfo>() {
			
			@Override
			public SMSInfo call() {
				return sMSWebService.registdetailinfo(ename, linkman, phonenum, mobile, email, fax,
					address, postcode);
				
			}
		});
	}
	
	@Override
	public SMSInfo sendSMS(final String mobileNumber, final String smsContent,
							final String addserial) {
		return callInterface(new CallExternalInterface<SMSInfo>() {
			
			@Override
			public SMSInfo call() {
				return sMSWebService.sendSMS(mobileNumber, smsContent, addserial);
				
			}
		});
	}

	@Override
	public SMSInfo sendSMSWithSwitch(final String mobileNumber,final String smsContent,final boolean isSend) {
		return callInterface(new CallExternalInterface<SMSInfo>() {

			@Override
			public SMSInfo call() {
				return sMSWebService.sendSMSWithSwitch(mobileNumber, smsContent, isSend);

			}
		});
	}



	@Override
	public SMSInfo sendTimeSMS(final String mobileNumber, final String smsContent,
								final String addserial, final String sendtime) {
		return callInterface(new CallExternalInterface<SMSInfo>() {
			
			@Override
			public SMSInfo call() {
				return sMSWebService.sendTimeSMS(mobileNumber, smsContent, addserial, sendtime);
				
			}
		});
	}
	
	@Override
	public SMSInfo getMo() {
		return callInterface(new CallExternalInterface<SMSInfo>() {
			
			@Override
			public SMSInfo call() {
				return sMSWebService.getMo();
				
			}
		});
	}
	
	@Override
	public SMSInfo querybalance() {
		return callInterface(new CallExternalInterface<SMSInfo>() {
			
			@Override
			public SMSInfo call() {
				return sMSWebService.querybalance();
				
			}
		});
	}
	
	@Override
	public SMSInfo chargeUp(final String cardNo, final String cardPass) {
		return callInterface(new CallExternalInterface<SMSInfo>() {
			
			@Override
			public SMSInfo call() {
				return sMSWebService.chargeUp(cardNo, cardPass);
				
			}
		});
	}
	
	@Override
	public SMSInfo changePassword(final String password, final String newPassword) {
		return callInterface(new CallExternalInterface<SMSInfo>() {
			
			@Override
			public SMSInfo call() {
				return sMSWebService.changePassword(password, newPassword);
				
			}
		});
	}
	
}
