package com.bornsoft.facade.info;

import com.bornsoft.utils.base.BornInfoBase;

/**
 * @Description: 短信发送请求order
 * @author taibai@yiji.com
 * @date 2016-12-1 上午10:41:25
 * @version V1.0
 */
public class SmsInfo extends BornInfoBase {

	/***/
    private long rid;
    /**手机号*/
    private String mobile;
    /**短信内容*/
    private String content;
    /**短信渠道号*/
    private String channel;
    /**状态：1 成功 0 失败*/
    private String status;
    /**添加时间*/
    private String rowAddTime;
	
    /**
     *	获取
     *	@return rid 
     */
    public java.lang.Long getRid (){
        return this.rid;
	}
		
    /**
     *	设置
     *	@param rid 
     */
    public void setRid (java.lang.Long rid){
        this.rid = rid;
    }
    /**
     *	获取手机号
     *	@return mobile 手机号
     */
    public String getMobile (){
        return this.mobile;
	}
		
    /**
     *	设置手机号
     *	@param mobile 手机号
     */
    public void setMobile (String mobile){
        this.mobile = mobile;
    }
    /**
     *	获取短信内容
     *	@return content 短信内容
     */
    public String getContent (){
        return this.content;
	}
		
    /**
     *	设置短信内容
     *	@param content 短信内容
     */
    public void setContent (String content){
        this.content = content;
    }
    /**
     *	获取短信渠道号
     *	@return channel 短信渠道号
     */
    public String getChannel (){
        return this.channel;
	}
		
    /**
     *	设置短信渠道号
     *	@param channel 短信渠道号
     */
    public void setChannel (String channel){
        this.channel = channel;
    }
    /**
     *	获取状态：1 成功 0 失败
     *	@return status 状态：1 成功 0 失败
     */
    public String getStatus (){
        return this.status;
	}
		
    /**
     *	设置状态：1 成功 0 失败
     *	@param status 状态：1 成功 0 失败
     */
    public void setStatus (String status){
        this.status = status;
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
