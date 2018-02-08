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
public abstract class MessageReadStatusDOAbstract {

	public static final String MESSAGEREADSTATUSDO = "MESSAGEREADSTATUSDO";
    /***/
    private java.lang.Long mId;
    /**用户名*/
    private String userName;
    /**风险预警ID、关联ID*/
    private String relatedId;
    /**添加时间*/
    private String rowAddTime;
	
    /**
     *	获取
     *	@return mId 
     */
    public java.lang.Long getMId (){
        return this.mId;
	}
		
    /**
     *	设置
     *	@param mId 
     */
    public void setMId (java.lang.Long mId){
        this.mId = mId;
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
     *	获取风险预警ID、关联ID
     *	@return relatedId 风险预警ID、关联ID
     */
    public String getRelatedId (){
        return this.relatedId;
	}
		
    /**
     *	设置风险预警ID、关联ID
     *	@param relatedId 风险预警ID、关联ID
     */
    public void setRelatedId (String relatedId){
        this.relatedId = relatedId;
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
	
}