package com.born.fcs.pm.ws.order.basicmaintain;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 评估公司管理Order
 *
 * @author heh
 *
 */
public class AssessCompanyQueryOrder extends QueryPageBase {
    private static final long serialVersionUID = 1229903511644311992L;

    private String companyName;

    private String region;

    private String City;

    private String sortOrder;

    private String sortCol;

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
