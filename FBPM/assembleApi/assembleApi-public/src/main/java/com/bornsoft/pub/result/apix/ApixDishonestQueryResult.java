package com.bornsoft.pub.result.apix;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bornsoft.utils.base.BornInfoBase;
import com.google.gson.annotations.SerializedName;

/**
 * @Description: 失信黑名单
 * @author taibai@yiji.com
 * @date 2016-8-25 上午11:29:54
 * @version V1.0
 */
public class ApixDishonestQueryResult extends ApixResultBase {

	/**
0	 */
	private static final long serialVersionUID = -5497244762408507712L;

	@SerializedName("data")
	private List<DishonestInfo> dishonestList;

	public List<DishonestInfo> getDishonestList() {
		return dishonestList;
	}

	public void setDishonestList(List<DishonestInfo> dishonestList) {
		this.dishonestList = dishonestList;
	}

	@Override
	public void setCode(String code) {
		super.setCode(code);
		if(StringUtils.equals(code, "1")){
			super.setCode("0");
		}
	}
	public static class DishonestInfo extends BornInfoBase {
		private String duty;// 生效法律文书确定的义务
		@SerializedName("disrupt_type")
		private String disruptType; // 失信被执行人行为具体情形
		private String code;// 案号
		private String sex;// 性别
		@SerializedName("pub_time")
		private String pubTime; // 发布时间
		private String court; // 执行法院
		private String name; // 姓名
		private String area; // 省份
		private String age; // 年龄
		private String performance; // 被执行人的履行情况
		@SerializedName("idcardno")
		private String certNo; // 身份证号

		public String getDuty() {
			return duty;
		}

		public void setDuty(String duty) {
			this.duty = duty;
		}

		public String getDisruptType() {
			return disruptType;
		}

		public void setDisruptType(String disruptType) {
			this.disruptType = disruptType;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getPubTime() {
			return pubTime;
		}

		public void setPubTime(String pubTime) {
			this.pubTime = pubTime;
		}

		public String getCourt() {
			return court;
		}

		public void setCourt(String court) {
			this.court = court;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}

		public String getPerformance() {
			return performance;
		}

		public void setPerformance(String performance) {
			this.performance = performance;
		}

		public String getCertNo() {
			return certNo;
		}

		public void setCertNo(String certNo) {
			this.certNo = certNo;
		}

	}

}
