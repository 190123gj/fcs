package com.born.fcs.pm.ws.order.finvestigation;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.OwnerEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.DateUtil;

/**
 * 已获的资格证书
 * 
 * @author lirz
 *
 * 2016-3-10 下午1:56:04
 */
public class FInvestigationMainlyReviewCertificateOrder extends ValidateOrderBase {

	private static final long serialVersionUID = -5736480882646297208L;

	private long id; //主键
	private OwnerEnum owner; //所属
	private long MReviewId; //对应客户主体评价ID
	private String certificateName; //资格证书名称
	private String certificateCode; //编码
	private Date validDate; //有效期yyyyMMdd
	private String certificateUrl; //拍照地址
	private int sortOrder;
	
	public boolean isNull() {
		return  isNull(certificateName)
				&& isNull(certificateCode)
				&& isNull(validDate)
				&& isNull(certificateUrl) 
				;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public OwnerEnum getOwner() {
		return owner;
	}

	public void setOwner(OwnerEnum owner) {
		this.owner = owner;
	}

	public long getMReviewId() {
		return MReviewId;
	}
	
	public void setMReviewId(long MReviewId) {
		this.MReviewId = MReviewId;
	}

	public String getCertificateName() {
		return certificateName;
	}
	
	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	public String getCertificateCode() {
		return certificateCode;
	}
	
	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}

	public Date getValidDate() {
		return validDate;
	}
	
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	
	public void setValidDateStr(String validDateStr) {
		this.validDate = DateUtil.strToDtSimpleFormat(validDateStr);
	}

	public String getCertificateUrl() {
		return certificateUrl;
	}
	
	public void setCertificateUrl(String certificateUrl) {
		this.certificateUrl = certificateUrl;
	}

	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
     * @return
     *
     * @see java.lang.Object#toString()
     */
	@Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
