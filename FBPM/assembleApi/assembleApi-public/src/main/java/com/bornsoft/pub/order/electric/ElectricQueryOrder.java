package com.bornsoft.pub.order.electric;

import java.util.ArrayList;
import java.util.List;

import com.bornsoft.utils.base.BornOutOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.DateUtils;
import com.bornsoft.utils.tool.ValidateParamUtil;

/**
  * @Description: 电量信息查询 
  * @author taibai@yiji.com 
  * @date  2016-8-15 下午4:38:15
  * @version V1.0
 */
public class ElectricQueryOrder extends BornOutOrderBase {

	/**
	 */
	private static final long serialVersionUID = 1L;
	/** 证照号码 */
	private String licenseNo;
	/** 机构名称*/
	private String customName;
	/** 查询条件 */
	private String conditions;
	/** 查询模式 */
	private QueryModeEnum queryMode;
	
	/**
	  * @Description: 查询模式 
	  * @author taibai@yiji.com 
	  * @date  2016-8-15 下午5:17:31
	  * @version V1.0
	 */
	public static enum QueryModeEnum{

		THAT_DAY("that-day", "查询某天电量分时数据") {
			@Override
			public void checkConditions(String value) {
				try {
					DateUtils.toDate(value, DateUtils.PATTERN1);
				} catch (Exception e) {
					throw new BornApiException(this.code() +" 参数有误: " + value);
				}
			}
		},
		DAYS("days", "近几天查询") {
			@Override
			public void checkConditions(String value) {
				checkNum(value);
			}
		},
		WEEKS("weeks", "近几周查询") {
			@Override
			public void checkConditions(String value) {
				checkNum(value);
			}
		}, 
		MONTHS("months", "按月查询") {
			@Override
			public void checkConditions(String value) {
				checkNum(value);
			}
		},
		;
		/***
		 * 分模式校验条件
		 * @param value
		 * @return
		 */
		public abstract void checkConditions(String value);
		
		protected void checkNum(String value){
			try {
				Integer.valueOf(value);
			} catch (Exception e) {
				throw new BornApiException(this.code() +" 参数有误: " + value);
			}
		
		}
		/**
		 * 枚举值
		 */
		private final String code;

		/**
		 * 枚举描述
		 */
		private final String message;

		/**
		 * 构造一个<code>TransferType</code>枚举对象
		 */
		private QueryModeEnum(String code, String message) {
			this.code = code;
			this.message = message;
		}

		/**
		 * @return Returns the code.
		 */
		public String getCode() {
			return code;
		}

		/**
		 * @return Returns the message.
		 */
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
		 * @return DepositResultEnum
		 */
		public static QueryModeEnum getByCode(String code) {
			for (QueryModeEnum _enum : values()) {
				if (_enum.getCode().equals(code)) {
					return _enum;
				}
			}
			return null;
		}

		/**
		 * 获取全部枚举
		 * 
		 * @return List<DepositResultEnum>
		 */
		public List<QueryModeEnum> getAllEnum() {
			List<QueryModeEnum> list = new ArrayList<QueryModeEnum>();
			for (QueryModeEnum _enum : values()) {
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
			for (QueryModeEnum _enum : values()) {
				list.add(_enum.code());
			}
			return list;
		}


	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}


	public QueryModeEnum getQueryMode() {
		return queryMode;
	}

	public void setQueryMode(QueryModeEnum queryMode) {
		this.queryMode = queryMode;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	@Override
	public String getService() {
		return "queryElectric";
	}

	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasTextV2(this.licenseNo, "licenseNo");
		ValidateParamUtil.hasTextV2(this.customName, "customName");
		ValidateParamUtil.hasTrue(queryMode!=null, "查询模式必填");
		queryMode.checkConditions(conditions);
		
	}
	@Override
	public boolean isRedirect() {
		return false;
	}

}
