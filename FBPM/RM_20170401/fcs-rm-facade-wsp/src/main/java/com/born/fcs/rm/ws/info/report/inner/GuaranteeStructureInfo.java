package com.born.fcs.rm.ws.info.report.inner;

import com.born.fcs.rm.ws.info.common.BaseToStringInfo;

import java.util.List;

/**
 *
 * 担保业务结构汇总表
 *
 * @author heh
 *
 * 2016年11月18日10:29:01
 */
public class GuaranteeStructureInfo extends BaseToStringInfo     {

    private static final long serialVersionUID = -4921807372945135662L;

    private List<ExpectReleaseInfo> expectReleaseInfos;

    public List<ExpectReleaseInfo> getExpectReleaseInfos() {
        return expectReleaseInfos;
    }

    public void setExpectReleaseInfos(List<ExpectReleaseInfo> expectReleaseInfos) {
        this.expectReleaseInfos = expectReleaseInfos;
    }
}
