package com.bornsoft.pub.order.risk;

import java.util.List;

import com.bornsoft.utils.base.BornInfoBase;
import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;


public class SynOperatorInfoOrder extends BornOrderBase {

	/**
	 */
	private static final long serialVersionUID = 1L;
	
	private List<OperatorInfo> list;
	
	public static class OperatorInfo extends BornInfoBase{

		/**操作员账户名*/
		private String operator;
		/**手机号*/
		private String mobile;	
		/**邮箱*/
		private String email;	
		
		public String getOperator() {
			return operator;
		}
		
		public void setOperator(String operator) {
			this.operator = operator;
		}
		
		public String getMobile() {
			return mobile;
		}
		
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		
		public String getEmail() {
			return email;
		}
		
		public void setEmail(String email) {
			this.email = email;
		}
		
		@Override
		public void validateOrder() throws BornApiException {
			super.validateOrder();
			ValidateParamUtil.hasTextV2(operator, "operator");
			ValidateParamUtil.hasTextV2(this.mobile, "mobile");
			ValidateParamUtil.hasTextV2(this.email, "email");
		}
		
	}
	

	public List<OperatorInfo> getList() {
		return list;
	}

	public void setList(List<OperatorInfo> list) {
		this.list = list;
	}

	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasTrue(list!=null&list.size()>0, "操作员账户至少有一个");
		for(OperatorInfo info : list ){
			info.validateOrder();
		}

	}

	@Override
	public String getService() {
		return "synOperatorInfo";
	}

}
