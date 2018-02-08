package com.bornsoft.pub.result.apix;

import com.bornsoft.utils.base.BornInfoBase;
import com.google.gson.annotations.SerializedName;

/**
 * @Description: apix 基础结果
 * @author taibai@yiji.com
 * @date 2016-8-25 上午11:31:22
 * @version V1.0
 */
public class ApixValidateBankCardResult extends ApixResultBase {
	/**
	 */
	private static final long serialVersionUID = -7775225395966062188L;

	@SerializedName("data")
	private BankCardInfo data;
	@SerializedName("idcard_data")
	private IdCardInfo idCardData;
	@SerializedName("phone_data")
	private PhoneDataInfo phoneDataInfo;

	public BankCardInfo getData() {
		return data;
	}

	public void setData(BankCardInfo data) {
		this.data = data;
	}

	public IdCardInfo getIdCardData() {
		return idCardData;
	}

	public void setIdCardData(IdCardInfo idCardData) {
		this.idCardData = idCardData;
	}

	public PhoneDataInfo getPhoneDataInfo() {
		return phoneDataInfo;
	}

	public void setPhoneDataInfo(PhoneDataInfo phoneDataInfo) {
		this.phoneDataInfo = phoneDataInfo;
	}

	public static class PhoneDataInfo extends BornInfoBase {
		@SerializedName("phone")
		private String phone;// 手机号
		@SerializedName("mobile_prov")
		private String moibleProv;// 手机号归属地，省份
		@SerializedName("mobile_city")
		private String mobileCity;// 手机号归属地，城市
		@SerializedName("mobile_type")
		private String mobileType;// 手机卡类型

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getMoibleProv() {
			return moibleProv;
		}

		public void setMoibleProv(String moibleProv) {
			this.moibleProv = moibleProv;
		}

		public String getMobileCity() {
			return mobileCity;
		}

		public void setMobileCity(String mobileCity) {
			this.mobileCity = mobileCity;
		}

		public String getMobileType() {
			return mobileType;
		}

		public void setMobileType(String mobileType) {
			this.mobileType = mobileType;
		}

	}

	public static class IdCardInfo extends BornInfoBase {
		@SerializedName("name")
		private String name;// 身份证姓名
		@SerializedName("sex")
		private String sex;// 性别
		@SerializedName("birthday")
		private String birthday;// 出生日期
		@SerializedName("address")
		private String address;// 出生地
		@SerializedName("idcardno")
		private String certNo; // 身份证号码

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

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

		public String getCertNo() {
			return certNo;
		}

		public void setCertNo(String certNo) {
			this.certNo = certNo;
		}

	}

	public static class BankCardInfo extends BornInfoBase {
		@SerializedName("cardtype")
		private String cardType;// 卡种，有借记卡（储蓄卡）、贷记卡（信用卡）和预付费卡等
		@SerializedName("cardname")
		private String cardName;// 卡片类型
		@SerializedName("bankname")
		private String bankName;// 开户行名称
		@SerializedName("areainfo")
		private String areaInfo;// 开户地
		@SerializedName("servicephone")
		private String servicePhone;// 客服电话
		@SerializedName("bankurl")
		private String bankUrl;// 银行官网
		@SerializedName("cardprefixnum")
		private String cardBinNum;// 银行卡bin号

		public String getCardType() {
			return cardType;
		}

		public void setCardType(String cardType) {
			this.cardType = cardType;
		}

		public String getCardName() {
			return cardName;
		}

		public void setCardName(String cardName) {
			this.cardName = cardName;
		}

		public String getBankName() {
			return bankName;
		}

		public void setBankName(String bankName) {
			this.bankName = bankName;
		}

		public String getAreaInfo() {
			return areaInfo;
		}

		public void setAreaInfo(String areaInfo) {
			this.areaInfo = areaInfo;
		}

		public String getServicePhone() {
			return servicePhone;
		}

		public void setServicePhone(String servicePhone) {
			this.servicePhone = servicePhone;
		}

		public String getBankUrl() {
			return bankUrl;
		}

		public void setBankUrl(String bankUrl) {
			this.bankUrl = bankUrl;
		}

		public String getCardBinNum() {
			return cardBinNum;
		}

		public void setCardBinNum(String cardBinNum) {
			this.cardBinNum = cardBinNum;
		}

	}

}
