package com.born.fcs.pm.biz.service.common;

import javax.jws.WebService;

import com.born.fcs.pm.ws.order.common.SendMailOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/***
 * 
 * 邮件发送服务
 *
 * @author wuzj
 */
@WebService
public interface MailService {
	
	/**
	 * 发送简单邮件
	 * @param order
	 * @return
	 */
	FcsBaseResult sendTextEmail(SendMailOrder order);
	
	/**
	 * 发送HTML邮件
	 * @param order
	 * @return
	 */
	FcsBaseResult sendHtmlEmail(SendMailOrder order);
}
