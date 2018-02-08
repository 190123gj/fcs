package com.bornsoft.pub.result.apix;

import com.bornsoft.utils.base.BornInfoBase;
import com.google.gson.annotations.SerializedName;


/**
 * @Description: apix 基础结果 
 * @author taibai@yiji.com
 * @date 2016-8-25 上午11:31:22
 * @version V1.0
 */
public class ApixValidateMobileResult extends ApixResultBase{
	/**
	 */
	private static final long serialVersionUID = -7775225395966062188L;
	
	private MobileValidateInfo data;
	
	
	public MobileValidateInfo getData() {
		return data;
	}


	public void setData(MobileValidateInfo data) {
		this.data = data;
	}


	public static class MobileValidateInfo extends BornInfoBase{
		@SerializedName("moible_prov")
		private String moibleProv;
		private String sex;
		private String birthday;
		private String address;
		@SerializedName("mobile_operator")
		private String mobileOperator;
		@SerializedName("mobile_city")
		private String mobileCity;
		public String getMoibleProv() {
			return moibleProv;
		}
		public void setMoibleProv(String moibleProv) {
			this.moibleProv = moibleProv;
		}
		
		/**
		 * M : 男 ,F : 女
		 * @return
		 */
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public String getBirthday() {
			return birthday;
		}
		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getMobileOperator() {
			return mobileOperator;
		}
		public void setMobileOperator(String mobileOperator) {
			this.mobileOperator = mobileOperator;
		}
		public String getMobileCity() {
			return mobileCity;
		}
		public void setMobileCity(String mobileCity) {
			this.mobileCity = mobileCity;
		}
		
	}
	
}
