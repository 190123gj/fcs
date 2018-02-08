package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 决策依据
 *
 * @author heh
 *
 * 2016年9月12日17:14:46
 */
public enum BasisOfDecisionEnum {

    PROJECT_APPROVAL("PROJECT_APPROVAL", "项目批复"),

    RISK_HANDLE_COUNCIL_SUMMARY("RISK_HANDLE_COUNCIL_SUMMARY", "风险处理会会议纪要"),

    FORM_CHANGE("FORM_CHANGE", "签报");


    /** 枚举值 */
    private final String code;

    /** 枚举描述 */
    private final String message;

    /**
     * 构造一个<code>CreditTypeEnum</code>枚举对象
     *
     * @param code
     * @param message
     */
    private BasisOfDecisionEnum(String code, String message) {
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
     * @param code
     * @return CreditTypeEnum
     */
    public static BasisOfDecisionEnum getByCode(String code) {
        for (BasisOfDecisionEnum _enum : values()) {
            if (_enum.getCode().equals(code)) {
                return _enum;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<CreditTypeEnum>
     */
    public static List<BasisOfDecisionEnum> getAllEnum() {
        List<BasisOfDecisionEnum> list = new ArrayList<BasisOfDecisionEnum>();
        for (BasisOfDecisionEnum _enum : values()) {
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
        for (BasisOfDecisionEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
