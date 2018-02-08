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
public abstract class MobileInfoDOAbstract {

	public static final String MOBILEINFODO = "MOBILEINFODO";
    /***/
    private java.lang.Long mid;
    /***/
    private String realName;
    /***/
    private String certNo;
    /**实名手机号*/
    private String mobile;
    /**M为男，F为女*/
    private String sex;
    /**出生日期*/
    private String birthday;
    /**地址*/
    private String address;
    /**手机号归属(省)*/
    private String province;
    /**手机号归属(市)*/
    private String city;
    /**运营商*/
    private String operator;
	
    /**
     *	获取
     *	@return mid 
     */
    public java.lang.Long getMid (){
        return this.mid;
	}
		
    /**
     *	设置
     *	@param mid 
     */
    public void setMid (java.lang.Long mid){
        this.mid = mid;
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
     *	获取手机号归属(省)
     *	@return province 手机号归属(省)
     */
    public String getProvince (){
        return this.province;
	}
		
    /**
     *	设置手机号归属(省)
     *	@param province 手机号归属(省)
     */
    public void setProvince (String province){
        this.province = province;
    }
    /**
     *	获取手机号归属(市)
     *	@return city 手机号归属(市)
     */
    public String getCity (){
        return this.city;
	}
		
    /**
     *	设置手机号归属(市)
     *	@param city 手机号归属(市)
     */
    public void setCity (String city){
        this.city = city;
    }
    /**
     *	获取运营商
     *	@return operator 运营商
     */
    public String getOperator (){
        return this.operator;
	}
		
    /**
     *	设置运营商
     *	@param operator 运营商
     */
    public void setOperator (String operator){
        this.operator = operator;
    }
	
}