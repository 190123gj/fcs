package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 收费依据
 *
 * @author heh
 *
 */
public enum ChargeBasisEnum {

    PROJECT("PROJECT", "项目"),

    BROKER_BUSI("BROKER_BUSI", "经纪业务");



    /** 枚举值 */
    private final String code;

    /** 枚举描述 */
    private final String message;

    /**
     * 构造一个<code>ChargeBaseEnum</code>枚举对象
     *
     * @param code
     * @param message
     */
    private ChargeBasisEnum(String code, String message) {
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
     * @return ChargeBaseEnum
     */
    public static ChargeBasisEnum getByCode(String code) {
        for (ChargeBasisEnum _enum : values()) {
            if (_enum.getCode().equals(code)) {
                return _enum;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<ChargeBaseEnum>
     */
    public static List<ChargeBasisEnum> getAllEnum() {
        List<ChargeBasisEnum> list = new ArrayList<ChargeBasisEnum>();
        for (ChargeBasisEnum _enum : values()) {
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
        for (ChargeBasisEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
