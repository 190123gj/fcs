package com.born.fcs.rm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 报表分类
 * 
 * @author lirz
 *
 * 2016-8-5 下午3:30:59
 */
public enum ReportTypeEnum {

    INNER("INNER","内部报表"),

    OUTER("OUTER", "外部报表"),

    DEF("DEF", "自定义报表");


    /** 表单code */
    private final String code;

    /** 枚举描述 */
    private final String message;

    /**
     * 构造一个<code>ReportTypeEnum</code>枚举对象
     *
     * @param code
     * @param message
     */
    private ReportTypeEnum(String code, String message) {
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
     * @return ReportCodeEnum
     */
    public static ReportTypeEnum getByCode(String code) {
        for (ReportTypeEnum _enum : values()) {
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
    public static List<ReportTypeEnum> getAllEnum() {
        List<ReportTypeEnum> list = new ArrayList<ReportTypeEnum>();
        for (ReportTypeEnum _enum : values()) {
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
        for (ReportTypeEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
