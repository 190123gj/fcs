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
public abstract class ApixVeryLogDOAbstract {

	public static final String APIXVERYLOGDO = "APIXVERYLOGDO";
    /**主键，自动增加*/
    private java.lang.Long logId;
    /**请求订单号*/
    private String orderNo;
    /**请求服务名称*/
    private String serviceName;
    /**请求参数*/
    private String reqParam;
    /**校验结果[code]*/
    private String veryCode;
    /**校验结果[msg]*/
    private String veryMsg;
    /**创建时间*/
    private String rowAddTime;
	
    /**
     *	获取主键，自动增加
     *	@return logId 主键，自动增加
     */
    public java.lang.Long getLogId (){
        return this.logId;
	}
		
    /**
     *	设置主键，自动增加
     *	@param logId 主键，自动增加
     */
    public void setLogId (java.lang.Long logId){
        this.logId = logId;
    }
    /**
     *	获取请求订单号
     *	@return orderNo 请求订单号
     */
    public String getOrderNo (){
        return this.orderNo;
	}
		
    /**
     *	设置请求订单号
     *	@param orderNo 请求订单号
     */
    public void setOrderNo (String orderNo){
        this.orderNo = orderNo;
    }
    /**
     *	获取请求服务名称
     *	@return serviceName 请求服务名称
     */
    public String getServiceName (){
        return this.serviceName;
	}
		
    /**
     *	设置请求服务名称
     *	@param serviceName 请求服务名称
     */
    public void setServiceName (String serviceName){
        this.serviceName = serviceName;
    }
    /**
     *	获取请求参数
     *	@return reqParam 请求参数
     */
    public String getReqParam (){
        return this.reqParam;
	}
		
    /**
     *	设置请求参数
     *	@param reqParam 请求参数
     */
    public void setReqParam (String reqParam){
        this.reqParam = reqParam;
    }
    /**
     *	获取校验结果[code]
     *	@return veryCode 校验结果[code]
     */
    public String getVeryCode (){
        return this.veryCode;
	}
		
    /**
     *	设置校验结果[code]
     *	@param veryCode 校验结果[code]
     */
    public void setVeryCode (String veryCode){
        this.veryCode = veryCode;
    }
    /**
     *	获取校验结果[msg]
     *	@return veryMsg 校验结果[msg]
     */
    public String getVeryMsg (){
        return this.veryMsg;
	}
		
    /**
     *	设置校验结果[msg]
     *	@param veryMsg 校验结果[msg]
     */
    public void setVeryMsg (String veryMsg){
        this.veryMsg = veryMsg;
    }
    /**
     *	获取创建时间
     *	@return rowAddTime 创建时间
     */
    public String getRowAddTime (){
        return this.rowAddTime;
	}
		
    /**
     *	设置创建时间
     *	@param rowAddTime 创建时间
     */
    public void setRowAddTime (String rowAddTime){
        this.rowAddTime = rowAddTime;
    }
	
}