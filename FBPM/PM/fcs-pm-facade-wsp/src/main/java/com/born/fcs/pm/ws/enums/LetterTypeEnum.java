package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 函/通知书模板类别
 *
 * @author heh
 *
 * 2016年9月19日18:29:31
 *
 */
public enum LetterTypeEnum {


    CONTRACT("CONTRACT", "合同类函"),

    NOT_CONTRACT("NOT_CONTRACT", "非合同类函"),

    NOTICE("NOTICE", "通知书"),

    WRIT("WRIT", "文书");

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
    private LetterTypeEnum(String code, String message) {
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
    public static LetterTypeEnum getByCode(String code) {
        for (LetterTypeEnum _enum : values()) {
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
    public static List<LetterTypeEnum> getAllEnum() {
        List<LetterTypeEnum> list = new ArrayList<LetterTypeEnum>();
        for (LetterTypeEnum _enum : values()) {
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
        for (LetterTypeEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
