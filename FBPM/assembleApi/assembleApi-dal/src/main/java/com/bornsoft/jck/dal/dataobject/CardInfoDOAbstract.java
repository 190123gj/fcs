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
public abstract class CardInfoDOAbstract {

	public static final String CARDINFODO = "CARDINFODO";
    /***/
    private java.lang.Long cardId;
    /**真实姓名*/
    private String realName;
    /**身份证号*/
    private String certNo;
    /**实名手机号*/
    private String mobile;
    /**银行卡号*/
    private String cardNo;
    /**开户行名称*/
    private String bankName;
	
    /**
     *	获取
     *	@return cardId 
     */
    public java.lang.Long getCardId (){
        return this.cardId;
	}
		
    /**
     *	设置
     *	@param cardId 
     */
    public void setCardId (java.lang.Long cardId){
        this.cardId = cardId;
    }
    /**
     *	获取真实姓名
     *	@return realName 真实姓名
     */
    public String getRealName (){
        return this.realName;
	}
		
    /**
     *	设置真实姓名
     *	@param realName 真实姓名
     */
    public void setRealName (String realName){
        this.realName = realName;
    }
    /**
     *	获取身份证号
     *	@return certNo 身份证号
     */
    public String getCertNo (){
        return this.certNo;
	}
		
    /**
     *	设置身份证号
     *	@param certNo 身份证号
     */
    public void setCertNo (String certNo){
        this.certNo = certNo;
    }
    /**
     *	获取实名手机号
     *	@return mobile 实名手机号
     */
    public String getMobile (){
        return this.mobile;
	}
		
    /**
     *	设置实名手机号
     *	@param mobile 实名手机号
     */
    public void setMobile (String mobile){
        this.mobile = mobile;
    }
    /**
     *	获取银行卡号
     *	@return cardNo 银行卡号
     */
    public String getCardNo (){
        return this.cardNo;
	}
		
    /**
     *	设置银行卡号
     *	@param cardNo 银行卡号
     */
    public void setCardNo (String cardNo){
        this.cardNo = cardNo;
    }
    /**
     *	获取开户行名称
     *	@return bankName 开户行名称
     */
    public String getBankName (){
        return this.bankName;
	}
		
    /**
     *	设置开户行名称
     *	@param bankName 开户行名称
     */
    public void setBankName (String bankName){
        this.bankName = bankName;
    }
	
}