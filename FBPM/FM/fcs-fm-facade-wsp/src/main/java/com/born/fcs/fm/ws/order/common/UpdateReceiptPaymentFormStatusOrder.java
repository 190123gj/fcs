package com.born.fcs.fm.ws.order.common;

import com.born.fcs.fm.ws.enums.ReceiptPaymentFormStatusEnum;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 收款单/付款单数据来源Order
 * @author wuzj
 */
public class UpdateReceiptPaymentFormStatusOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 8028718108572367448L;
	
	/** 来源单据ID */
	private String sourceFormId;
	/** 单据来源 */
	private SourceFormEnum sourceForm;
	/** 处理状态 */
	private ReceiptPaymentFormStatusEnum status;
	
	@Override
	public void check() {
		validateNotNull(sourceFormId, "来源单据ID");
		validateNotNull(sourceForm, "来源单据类型");
		validateNotNull(status, "处理状态");
	}
	
	public String getSourceFormId() {
		return this.sourceFormId;
	}
	
	public void setSourceFormId(String sourceFormId) {
		this.sourceFormId = sourceFormId;
	}
	
	public SourceFormEnum getSourceForm() {
		return sourceForm;
	}
	
	public void setSourceForm(SourceFormEnum sourceForm) {
		this.sourceForm = sourceForm;
	}
	
	public ReceiptPaymentFormStatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(ReceiptPaymentFormStatusEnum status) {
		this.status = status;
	}
	
}
