package com.born.fcs.pm.ws.order.contract;

import com.born.fcs.pm.ws.base.QueryPageBase;
import com.born.fcs.pm.ws.enums.LetterTypeEnum;
import com.born.fcs.pm.ws.enums.TemplateTypeEnum;

import java.util.Date;
import java.util.List;

/**
 * 合同模板查询Order
 *
 * @author heh
 *
 */
public class ContractTemplateQueryOrder extends QueryPageBase {
    private static final long serialVersionUID = -4237659688994086928L;

    private String name;
    private String contractType;
    private String busiType;
    private String busiTypeName;
    private String status;
    private String creditConditionType;
    private String pledgeType;
    private Date operateDate;
    private String isMain;

    private String sortOrder;

    private String sortCol;

    private String isChooseNull;

    private List<Long> busiTypeList;

    private List<String> conditionTypeList;

    private String isGetTemplates;

    private String notGuarantor;

    private String needNullPledgeType;

    private TemplateTypeEnum templateType;

    private LetterTypeEnum letterType;

    public TemplateTypeEnum getTemplateType() {
        return templateType;
    }

    public void setTemplateType(TemplateTypeEnum templateType) {
        this.templateType = templateType;
    }

    public LetterTypeEnum getLetterType() {
        return letterType;
    }

    public void setLetterType(LetterTypeEnum letterType) {
        this.letterType = letterType;
    }

    public String getNeedNullPledgeType() {
        return needNullPledgeType;
    }

    public void setNeedNullPledgeType(String needNullPledgeType) {
        this.needNullPledgeType = needNullPledgeType;
    }

    public String getNotGuarantor() {
        return notGuarantor;
    }

    public void setNotGuarantor(String notGuarantor) {
        this.notGuarantor = notGuarantor;
    }

    public String getIsGetTemplates() {
        return isGetTemplates;
    }

    public void setIsGetTemplates(String isGetTemplates) {
        this.isGetTemplates = isGetTemplates;
    }


    public List<String> getConditionTypeList() {
        return conditionTypeList;
    }

    public void setConditionTypeList(List<String> conditionTypeList) {
        this.conditionTypeList = conditionTypeList;
    }

    public List<Long> getBusiTypeList() {
        return busiTypeList;
    }

    public void setBusiTypeList(List<Long> busiTypeList) {
        this.busiTypeList = busiTypeList;
    }

    public String getIsChooseNull() {
        return isChooseNull;
    }

    public void setIsChooseNull(String isChooseNull) {
        this.isChooseNull = isChooseNull;
    }

    @Override
    public String getSortOrder() {
        return sortOrder;
    }

    @Override
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String getSortCol() {
        return sortCol;
    }

    @Override
    public void setSortCol(String sortCol) {
        this.sortCol = sortCol;
    }

    public String getIsMain() {
        return isMain;
    }

    public void setIsMain(String isMain) {
        this.isMain = isMain;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getCreditConditionType() {
        return creditConditionType;
    }

    public void setCreditConditionType(String creditConditionType) {
        this.creditConditionType = creditConditionType;
    }

    public String getPledgeType() {
        return pledgeType;
    }

    public void setPledgeType(String pledgeType) {
        this.pledgeType = pledgeType;
    }

    public String getBusiTypeName() {
        return busiTypeName;
    }

    public void setBusiTypeName(String busiTypeName) {
        this.busiTypeName = busiTypeName;
    }
}
