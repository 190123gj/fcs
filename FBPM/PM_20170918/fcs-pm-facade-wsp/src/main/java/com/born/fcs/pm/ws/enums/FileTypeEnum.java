package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 档案类型
 *
 * @author heh
 *
 */
public enum FileTypeEnum {

    CREDIT_BUSSINESS("CREDIT_BUSSINESS", "授信业务(基础卷)"),

    CREDIT_BEFORE_MANAGEMENT("CREDIT_BEFORE_MANAGEMENT", "授信后管理卷"),

    DOCUMENT_OF_TITLE("DOCUMENT_OF_TITLE", "权利凭证卷"),

    RISK_COMMON("RISK_COMMON","风险常规卷"),

    RISK_LAWSUIT("RISK_LAWSUIT", "风险诉讼卷");

    /** 枚举值 */
    private final String code;

    /** 枚举描述 */
    private final String message;

    /**
     * 构造一个<code>FileTypeEnum</code>枚举对象
     *
     * @param code
     * @param message
     */
    private FileTypeEnum(String code, String message) {
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
     * @return FileTypeEnum
     */
    public static FileTypeEnum getByCode(String code) {
        for (FileTypeEnum _enum : values()) {
            if (_enum.getCode().equals(code)) {
                return _enum;
            }
        }
        return null;
    }

    /**
     * 通过枚举<message>message</message>获得枚举
     *
     * @param message
     * @return FileTypeEnum
     */
    public static FileTypeEnum getByMessage(String message) {
        for (FileTypeEnum _enum : values()) {
            if (_enum.getMessage().equals(message)) {
                return _enum;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<FileTypeEnum>
     */
    public static List<FileTypeEnum> getAllEnum() {
        List<FileTypeEnum> list = new ArrayList<FileTypeEnum>();
        for (FileTypeEnum _enum : values()) {
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
        for (FileTypeEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
