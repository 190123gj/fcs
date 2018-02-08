package com.born.fcs.rm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据报送状态
 *
 * @author heh
 */
public enum ReportStatusEnum {

    DELETED("DELETED", -1, "已删除"),

    DRAFT("DRAFT", 1, "草稿"),

    SUBMITTED("SUBMITTED", 2, "已报送"),

    IN_USE("IN_USE", 3, "已被引用");


    /** 表单code */
    private final String code;

    private final int value;

    /** 枚举描述 */
    private final String message;

    /**
     * 构造一个<code>FormCodeEnum</code>枚举对象
     *
     * @param code
     * @param message
     */
    private ReportStatusEnum(String code, int value, String message) {
        this.code = code;
        this.value=value;
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


    public int value() {
        return value;
    }
    /**
     * @return Returns the message.
     */
    public String message() {
        return message;
    }

    public int getValue() {
        return value;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code
     * @return ReportCodeEnum
     */
    public static ReportStatusEnum getByCode(String code) {
        for (ReportStatusEnum _enum : values()) {
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
    public static List<ReportStatusEnum> getAllEnum() {
        List<ReportStatusEnum> list = new ArrayList<ReportStatusEnum>();
        for (ReportStatusEnum _enum : values()) {
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
        for (ReportStatusEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
