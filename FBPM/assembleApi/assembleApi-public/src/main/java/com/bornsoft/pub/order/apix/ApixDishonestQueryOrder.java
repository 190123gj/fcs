package com.bornsoft.pub.order.apix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bornsoft.pub.enums.ApixServiceEnum;
import com.bornsoft.utils.base.IBornEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.GsonUtil;
import com.bornsoft.utils.tool.SerializeAlias;
import com.bornsoft.utils.tool.ValidateParamUtil;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.yjf.common.lang.util.StringUtil;

/**
 * @Description: 失信黑名单
 * @author taibai@yiji.com
 * @date 2016-8-25 上午11:29:54
 * @version V1.0
 */
public class ApixDishonestQueryOrder extends ApixOrderBase {
	/**
	 */
	private static final long serialVersionUID = -6305141192584920010L;

	@SerializeAlias(alias = "type")
	private ApixUserTypeEnum type;// 选择查询个人还是企业，person表示个人，company表示公司
	private String name;// 个人或企业名称
	@SuppressWarnings("unused")
	@SerializeAlias(alias = "cardno")
	private String cardNo;

	/****是否三证合一****/
	@SerializeAlias(ignore=true,alias="")
	private boolean oneCert;
	/******身份号码******/
	@SerializeAlias(ignore=true,alias="")
	private String certNo;
	/******营业执照号|三证合一之后的号码*/
	@SerializeAlias(ignore=true,alias="")
	private String licenseNo;
	/******组织结构代码**/
	@SerializeAlias(ignore=true,alias="")
	private String orgCode;
	
	@SerializeAlias(alias = "phoneNo")
	private String phone;// 手机号或电话号码
	private String email;// 邮箱账号
	
	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		if(type==ApixUserTypeEnum.COMPANY){
			if(oneCert){
				ValidateParamUtil.hasText(licenseNo, "统一信用代码不能为空");
				if(licenseNo.length()!=18){
					throw new BornApiException("统一信用代码为18位");
				}
			}else{
				ValidateParamUtil.hasText(orgCode, "组织机构代码不能为空");
			}
		}else{
			ValidateParamUtil.hasText(certNo, "身份证号不能为空");
		}
	}
	
	public String getCardNo() {
		if(type==ApixUserTypeEnum.COMPANY){
			if(oneCert){
				return getOrgCode(licenseNo);
			}else{
				return orgCode;
			}
		}else{
			return certNo;
		}
	}

	private String getOrgCode(String licenseNo2) {
		if(StringUtil.isNotBlank(licenseNo2)){
			if(licenseNo2.length()!=18){
				throw new BornApiException("统一信用代码为18位");
			}else{
				return licenseNo2.substring(8,17);//共九位
			}
		}else{
			return "";
		}
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public boolean isOneCert() {
		return oneCert;
	}

	public void setOneCert(boolean oneCert) {
		this.oneCert = oneCert;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public ApixUserTypeEnum getType() {
		return type;
	}

	public void setType(ApixUserTypeEnum type) {
		this.type = type;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static enum ApixUserTypeEnum implements IBornEnum {
		PERSON("person", "个人"), COMPANY("company", "公司"), ;
		/** 枚举值 */
		private final String code;

		/** 枚举描述 */
		private final String message;

		private ApixUserTypeEnum(String code, String message) {
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

		/**
		 * @return Returns the code.
		 */
		public String code() {
			return code;
		}

		/**
		 * @return Returns the message.
		 */
		public String message() {
			return message;
		}

		/**
		 * 通过枚举<code>code</code>获得枚举
		 * 
		 * @param code
		 * @return LogResultEnum
		 */
		public static ApixUserTypeEnum getByCode(String code) {
			for (ApixUserTypeEnum _enum : values()) {
				if (_enum.getCode().equalsIgnoreCase(code)) {
					return _enum;
				}
			}
			return null;
		}

		/**
		 * 获取全部枚举
		 * 
		 * @return List<LogResultEnum>
		 */
		public List<ApixUserTypeEnum> getAllEnum() {
			List<ApixUserTypeEnum> list = new ArrayList<ApixUserTypeEnum>();
			for (ApixUserTypeEnum _enum : values()) {
				list.add(_enum);
			}
			return list;
		}

		/**
		 * 获取全部枚举值
		 * 
		 * @return List<String>
		 */
		public List<String> getAllEnumCode() {
			List<String> list = new ArrayList<String>();
			for (ApixUserTypeEnum _enum : values()) {
				list.add(_enum.code());
			}
			return list;
		}

		{
			GsonUtil.builder.registerTypeAdapter(getClass(),
					new TypeAdapter<ApixUserTypeEnum>() {

						@Override
						public ApixUserTypeEnum read(JsonReader reader)
								throws IOException {
							if (reader.peek() == JsonToken.NULL) {
								reader.nextNull();
								return null;
							} else {
								String value = reader.nextString();
								return ApixUserTypeEnum.getByCode(value);
							}
						}

						@Override
						public void write(JsonWriter arg0, ApixUserTypeEnum arg1)
								throws IOException {
							if (arg1 == null) {
								arg0.nullValue();
							} else {
								arg0.value(arg1.code());
							}

						}
					});
		}
	}
	@Override
	public String getUrl() {
		return "http://e.apix.cn/apixcredit/blacklist/dishonest";
	}
	
	@Override
	public String getService() {
		return ApixServiceEnum.DishonestQuery.code();
	}
}
