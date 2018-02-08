package com.born.fcs.fm.ws.order.common;

import java.util.List;

import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.enums.ReceiptPaymentFormStatusEnum;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.pm.ws.base.QueryProjectBase;

/**
 * 收款单/付款单数据来源查询Order
 * @author wuzj
 */
public class ReceiptPaymentFormQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = -7795788968738484834L;
	
	/** 主键 */
	private long sourceId;
	/** 单据来源 */
	private SourceFormEnum sourceForm;
	/** 来源单据ID */
	private String sourceFormId;
	/** 单据来源系统 */
	private SourceFormEnum sourceFormSys;
	/** 合同编号 */
	private String contractNo;
	/** 处理状态 */
	private ReceiptPaymentFormStatusEnum status;
	/** 资金流向 */
	private FundDirectionEnum fundDirection;
	/** 处理状态 */
	private List<ReceiptPaymentFormStatusEnum> statusList;
	/** 发起人 */
	private long userId;
	/** 发起人名称 */
	private String userName;
	/** 发起部门ID */
	private long deptId;
	/** 发起部门名称 */
	private String deptName;
	
	/** 是否查询费用明细 */
	private boolean queryDetail;
	
	/**
	 * 简单流程（IS/NO）
	 */
	private String isSimple;
	
	public long getSourceId() {
		return sourceId;
	}
	
	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	
	public SourceFormEnum getSourceForm() {
		return sourceForm;
	}
	
	public void setSourceForm(SourceFormEnum sourceForm) {
		this.sourceForm = sourceForm;
	}
	
	public String getSourceFormId() {
		return this.sourceFormId;
	}
	
	public void setSourceFormId(String sourceFormId) {
		this.sourceFormId = sourceFormId;
	}
	
	public SourceFormEnum getSourceFormSys() {
		return sourceFormSys;
	}
	
	public void setSourceFormSys(SourceFormEnum sourceFormSys) {
		this.sourceFormSys = sourceFormSys;
	}
	
	public String getContractNo() {
		return contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public ReceiptPaymentFormStatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(ReceiptPaymentFormStatusEnum status) {
		this.status = status;
	}
	
	public List<ReceiptPaymentFormStatusEnum> getStatusList() {
		return statusList;
	}
	
	public void setStatusList(List<ReceiptPaymentFormStatusEnum> statusList) {
		this.statusList = statusList;
	}
	
	public FundDirectionEnum getFundDirection() {
		return fundDirection;
	}
	
	public void setFundDirection(FundDirectionEnum fundDirection) {
		this.fundDirection = fundDirection;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public long getDeptId() {
		return deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public boolean isQueryDetail() {
		return queryDetail;
	}
	
	public void setQueryDetail(boolean queryDetail) {
		this.queryDetail = queryDetail;
	}
	
	public String getIsSimple() {
		return isSimple;
	}
	
	public void setIsSimple(String isSimple) {
		this.isSimple = isSimple;
	}
	
}
