package com.bornsoft.pub.result.risk;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.bornsoft.utils.base.BornInfoBase;
import com.bornsoft.utils.base.BornSynResultBase;
import com.bornsoft.utils.tool.JsonParseUtil;
import com.yjf.common.lang.util.StringUtil;

/***
 * @Description: 类似企业信息查询
 * @author taibai@yiji.com
 * @date 2016-8-6 下午3:45:44
 * @version V1.0
 */
public class QuerySimilarEnterpriseResult extends BornSynResultBase {
	
	/**
	 */
	private static final long serialVersionUID = 1L;
	
	private List<CustomInfo> list;

	public List<CustomInfo> getList() {
		return list;
	}

	public void setList(List<CustomInfo> list) {
		this.list = list;
	}
	
	@Override
	public void setData(String data) {
		JSONObject jsonData = JsonParseUtil.parseObject(data, JSONObject.class);
		if(jsonData.getJSONArray("list")!=null){
			list = JsonParseUtil.parseArray(jsonData.getJSONArray("list").toJSONString(), CustomInfo.class);
		}
	}

	
	public static class CustomInfo extends BornInfoBase {
		/** 信用代码 */
		@JSONField(name="creditCode")
		private String creditCode;
		/** 工商注册号|营业执照号 */
		@JSONField(name="regNumber")
		private String regNum;
		/** 企业名称 */
		private String customName;
		/** 详情页 */
		private String detailUrl;
		
		public String getDetailUrl() {
			return detailUrl;
		}

		public void setDetailUrl(String detailUrl) {
			this.detailUrl = detailUrl;
		}

		public String getCreditCode() {
			return creditCode;
		}

		public void setCreditCode(String creditCode) {
			this.creditCode = creditCode;
		}

		public String getRegNum() {
			return regNum;
		}

		public void setRegNum(String regNum) {
			this.regNum = regNum;
		}

		public String getCustomName() {
			return customName;
		}

		public void setCustomName(String customName) {
			this.customName = customName;
		}
		
		/**
		 * 获取营业执照号|统一信用代码
		 * @return
		 */
		public String getLicenseNo() {
			return StringUtil.defaultIfBlank(regNum, creditCode);
		}

	}
	
	public void setList(String listStr) {
		list = JsonParseUtil.parseArray(listStr, CustomInfo.class);
	}

}
