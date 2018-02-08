package com.born.fcs.rm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据报送
 *
 * @author heh
 */
public enum SubmissionCodeEnum {

    ANNUAL_OBJECTIVE("ANNUAL_OBJECTIVE", "", "annualObjectives.vm",  "", "年度目标"),
    RZXDBJGQKB("RZXDBJGQKB", "", "FGOrgBasic.vm",  "", "融资性担保机构基本情况"),
    RZXDBJGYWQKB("RZXDBJGYWQKB", "", "FGOrgBusi.vm",  "", "融资性担保机构业务情况"),
    RZXDBJGFXZB("RZXDBJGFXZB", "", "FGOrgRisk.vm",  "", "融资性担保机构风险指标"),
    YJCWXX("YJCWXX", "", "RFInfo.vm",  "", "业绩财务信息"),
    DBGSZYSJYBB("DBGSZYSJYBB", "", "GCMainDataMonth.vm",  "", "担保公司主要数据月报表"),
    RZXDBGSNMJYYCB_FXB("RZXDBGSNMJYYCB_FXB", "", "FGComColsedRC.vm",  "", "融资性担保公司年末经营预测表-风险管理部上报"),
    RZXDBGSNMJYYCB_CWB("RZXDBGSNMJYYCB_CWB", "", "FGComColsedF.vm",  "", "融资性担保公司年末经营预测表-财务部上报"),
    RZXDBGSNMJYYCB_GHTZB("RZXDBGSNMJYYCB_GHTZB", "", "FGComColsedPI.vm",  "", "融资性担保公司年末经营预测表-规划投资部上报"),
    RZXDBGSNMJYQKB("RZXDBGSNMJYQKB", "", "FGComOC.vm",  "", "融资性担保公司经营情况表"),
    BFSRCWB("BFSRCWB", "", "premiumInCome.vm",  "", "保费收入-财务部上报"),
    ;


    /** 表单code */
    private final String code;


    /** 枚举描述 */
    private final String message;

    /**
     * 查看页面
     */
    private final String viewUrl;

    /**
     * 编辑页面
     */
    private final String editUrl;

    /**
     * 打印页面
     */
    private final String printUrl;

    /**
     * 构造一个<code>FormCodeEnum</code>枚举对象
     *
     * @param code
     * @param message
     */
    private SubmissionCodeEnum(String code, String viewUrl,
                           String editUrl, String printUrl,
                           String message) {
        this.code = code;
        this.viewUrl = viewUrl;
        this.editUrl = editUrl;
        this.printUrl = printUrl;
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
     * @return Returns the viewUrl.
     */
    public String getViewUrl() {
        return viewUrl;
    }

    /**
     * @return Returns the editUrl.
     */
    public String getEditUrl() {
        return editUrl;
    }

    /**
     * @return Returns the printUrl.
     */
    public String getPrintUrl() {
        return printUrl;
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
     * @return Returns the viewUrl.
     */
    public String viewUrl() {
        return viewUrl;
    }

    /**
     * @return Returns the editUrl.
     */
    public String editUrl() {
        return editUrl;
    }

    /**
     * @return Returns the printUrl.
     */
    public String printUrl() {
        return printUrl;
    }


    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code
     * @return ReportCodeEnum
     */
    public static SubmissionCodeEnum getByCode(String code) {
        for (SubmissionCodeEnum _enum : values()) {
            if (_enum.getCode().equals(code)) {
                return _enum;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<ReportCodeEnum>
     */
    public static List<SubmissionCodeEnum> getAllEnum() {
        List<SubmissionCodeEnum> list = new ArrayList<SubmissionCodeEnum>();
        for (SubmissionCodeEnum _enum : values()) {
            list.add(_enum);
        }
        return list;
    }

    /**
     * 获取全部枚举值
     *
     * @return List<String>
     */
    public static List<String> getAllEnumCode() {
        List<String> list = new ArrayList<String>();
        for (SubmissionCodeEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
