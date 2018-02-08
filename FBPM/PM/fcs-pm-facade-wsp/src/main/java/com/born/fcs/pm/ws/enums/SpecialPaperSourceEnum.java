package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 特殊纸编号来源
 *
 * @author heh
 *
 * 2016-6-14 下午3:11:26
 */
public enum SpecialPaperSourceEnum {

    CHECKIN("CHECKIN", "登记"),
    PROVIDE_DEPT("PROVIDE_DEPT", "部门发放"),
    PROVIDE_PROJECT("PROVIDE_PROJECT", "项目发放"),
    INVALID("INVALID", "作废");

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
    private SpecialPaperSourceEnum(String code, String message) {
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
    public static SpecialPaperSourceEnum getByCode(String code) {
        for (SpecialPaperSourceEnum _enum : values()) {
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
    public static List<SpecialPaperSourceEnum> getAllEnum() {
        List<SpecialPaperSourceEnum> list = new ArrayList<SpecialPaperSourceEnum>();
        for (SpecialPaperSourceEnum _enum : values()) {
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
        for (SpecialPaperSourceEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
