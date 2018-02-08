package com.bornsoft.jck.dal.dataobject;

/**
 * 
 *                       
 * @Filename Maker.java
 *
 * @Description 自动生成DO
 *
 * @Version 1.0
 *
 * @Author jlcon
 *
 * @Email jianglu@yiji.com
 *       
 * @History
 *<li>Author: jlcon</li>
 *<li>Date: </li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public abstract class AppLoginInfoDOAbstract {

	public static final String APPLOGININFODO = "APPLOGININFODO";
    /***/
    private java.lang.Long logId;
    /**用户名*/
    private String userName;
    /**密码镜像(用于保证各终端之间密码一致性)*/
    private String password;
    /**设备号*/
    private String deviceNo;
    /**一次性token*/
    private String token;
    /**友盟设备号*/
    private String umDeviceNo;
    /**最后一次登陆时间*/
    private String lastLogTime;
    /**最后一次登陆IP*/
    private String lastLogIp;
    /**添加时间*/
    private String rowAddTime;
	
    /**
     *	获取
     *	@return logId 
     */
    public java.lang.Long getLogId (){
        return this.logId;
	}
		
    /**
     *	设置
     *	@param logId 
     */
    public void setLogId (java.lang.Long logId){
        this.logId = logId;
    }
    /**
     *	获取用户名
     *	@return userName 用户名
     */
    public String getUserName (){
        return this.userName;
	}
		
    /**
     *	设置用户名
     *	@param userName 用户名
     */
    public void setUserName (String userName){
        this.userName = userName;
    }
    /**
     *	获取密码镜像(用于保证各终端之间密码一致性)
     *	@return password 密码镜像(用于保证各终端之间密码一致性)
     */
    public String getPassword (){
        return this.password;
	}
		
    /**
     *	设置密码镜像(用于保证各终端之间密码一致性)
     *	@param password 密码镜像(用于保证各终端之间密码一致性)
     */
    public void setPassword (String password){
        this.password = password;
    }
    /**
     *	获取设备号
     *	@return deviceNo 设备号
     */
    public String getDeviceNo (){
        return this.deviceNo;
	}
		
    /**
     *	设置设备号
     *	@param deviceNo 设备号
     */
    public void setDeviceNo (String deviceNo){
        this.deviceNo = deviceNo;
    }
    /**
     *	获取一次性token
     *	@return token 一次性token
     */
    public String getToken (){
        return this.token;
	}
		
    /**
     *	设置一次性token
     *	@param token 一次性token
     */
    public void setToken (String token){
        this.token = token;
    }
    /**
     *	获取最后一次登陆时间
     *	@return lastLogTime 最后一次登陆时间
     */
    public String getLastLogTime (){
        return this.lastLogTime;
	}
		
    /**
     *	设置最后一次登陆时间
     *	@param lastLogTime 最后一次登陆时间
     */
    public void setLastLogTime (String lastLogTime){
        this.lastLogTime = lastLogTime;
    }
    /**
     *	获取最后一次登陆IP
     *	@return lastLogIp 最后一次登陆IP
     */
    public String getLastLogIp (){
        return this.lastLogIp;
	}
		
    /**
     *	设置最后一次登陆IP
     *	@param lastLogIp 最后一次登陆IP
     */
    public void setLastLogIp (String lastLogIp){
        this.lastLogIp = lastLogIp;
    }
    /**
     *	获取添加时间
     *	@return rowAddTime 添加时间
     */
    public String getRowAddTime (){
        return this.rowAddTime;
	}
		
    /**
     *	设置添加时间
     *	@param rowAddTime 添加时间
     */
    public void setRowAddTime (String rowAddTime){
        this.rowAddTime = rowAddTime;
    }

	public String getUmDeviceNo() {
		return umDeviceNo;
	}

	public void setUmDeviceNo(String umDeviceNo) {
		this.umDeviceNo = umDeviceNo;
	}
	
}