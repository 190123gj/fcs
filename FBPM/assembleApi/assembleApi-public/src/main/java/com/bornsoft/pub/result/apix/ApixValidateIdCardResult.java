package com.bornsoft.pub.result.apix;

import com.bornsoft.utils.base.BornInfoBase;
import com.bornsoft.utils.tool.SerializeAlias;
import com.google.gson.annotations.SerializedName;

/**
 * @Description: apix 校验身份证
 * @author taibai@yiji.com
 * @date 2016-8-25 上午11:31:22
 * @version V1.0
 */
public class ApixValidateIdCardResult extends ApixResultBase {

	/**
	 */
	private static final long serialVersionUID = -653934887312485359L;

	@SerializedName("data")
	private PersonInfo personInfo;

	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

	public static class PersonInfo extends BornInfoBase {
		private String birthday;// 出生日期
		/** M为男，F为女 */
		private String sex;// 性别（M为男，F为女）
		private String address; // 地址
		private String name;// 传入的姓名
		@SerializedName("cardno")
		private String certNo;// 传入的身份证号
		@SerializeAlias(alias = "idcardphoto")
		private String idcardphoto;
		
		public String getIdcardphoto() {
			return idcardphoto;
		}

		public void setIdcardphoto(String idcardphoto) {
			this.idcardphoto = idcardphoto;
		}

		public String getBirthday() {
			return birthday;
		}

		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}

		/***
		 * M为男，F为女
		 * 
		 * @return
		 */
		public String getSex() {
			return sex;
		}

		/***
		 * M为男，F为女
		 * 
		 * @return
		 */
		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCertNo() {
			return certNo;
		}

		public void setCertNo(String certNo) {
			this.certNo = certNo;
		}

	}

}
