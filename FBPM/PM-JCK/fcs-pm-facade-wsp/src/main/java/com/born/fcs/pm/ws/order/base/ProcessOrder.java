package com.born.fcs.pm.ws.order.base;

/**
 * 调用commonProcess时传的Order
 *
 * @author wuzj
 */
public class ProcessOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -3442732124237556404L;
	/**
	 * 从web端传入 方便写入日志
	 */
	//用户ID
	protected Long userId;
	//用户账号
	protected String userAccount;
	//用户名称
	protected String userName;
	//用户IP
	protected String userIp;
	
	public Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUserAccount() {
		return this.userAccount;
	}
	
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserIp() {
		return this.userIp;
	}
	
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
}
