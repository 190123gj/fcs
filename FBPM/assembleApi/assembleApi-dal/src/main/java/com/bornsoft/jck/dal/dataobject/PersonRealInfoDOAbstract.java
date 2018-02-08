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
public abstract class PersonRealInfoDOAbstract {

	public static final String PERSONREALINFODO = "PERSONREALINFODO";
    /***/
    private java.lang.Long pid;
    /***/
    private String realName;
    /***/
    private String certNo;
    /**M为男，F为女*/
    private String sex;
    /**出生日期*/
    private String birthday;
    /**地址*/
    private String address;
    /**实名手机号*/
    private String cardPhoto;
	
    /**
     *	获取
     *	@return pid 
     */
    public java.lang.Long getPid (){
        return this.pid;
	}
		
    /**
     *	设置
     *	@param pid 
     */
    public void setPid (java.lang.Long pid){
        this.pid = pid;
    }
    /**
     *	获取
     *	@return realName 
     */
    public String getRealName (){
        return this.realName;
	}
		
    /**
     *	设置
     *	@param realName 
     */
    public void setRealName (String realName){
        this.realName = realName;
    }
    /**
     *	获取
     *	@return certNo 
     */
    public String getCertNo (){
        return this.certNo;
	}
		
    /**
     *	设置
     *	@param certNo 
     */
    public void setCertNo (String certNo){
        this.certNo = certNo;
    }
    /**
     *	获取M为男，F为女
     *	@return sex M为男，F为女
     */
    public String getSex (){
        return this.sex;
	}
		
    /**
     *	设置M为男，F为女
     *	@param sex M为男，F为女
     */
    public void setSex (String sex){
        this.sex = sex;
    }
    /**
     *	获取出生日期
     *	@return birthday 出生日期
     */
    public String getBirthday (){
        return this.birthday;
	}
		
    /**
     *	设置出生日期
     *	@param birthday 出生日期
     */
    public void setBirthday (String birthday){
        this.birthday = birthday;
    }
    /**
     *	获取地址
     *	@return address 地址
     */
    public String getAddress (){
        return this.address;
	}
		
    /**
     *	设置地址
     *	@param address 地址
     */
    public void setAddress (String address){
        this.address = address;
    }

    /**
     * 身份证照片
     * @return
     */
	public String getCardPhoto() {
		return cardPhoto;
	}

    /**
     *	设置身份证照片
     *	@param address 身份证照片
     */
	public void setCardPhoto(String cardPhoto) {
		this.cardPhoto = cardPhoto;
	}
	
}