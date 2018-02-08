package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域
 *
 * @author heh
 *         <p>
 *         2016-3-10 下午3:49:01
 */
public enum RegionTypeEnum {

    INSIDE_CITY("INSIDE_CITY", "市内"),

    OUTSIDE_CITY("OUTSIDE_CITY", "市外");

    /**
     * 枚举值
     */
    private final String code;

    /**
     * 枚举描述
     */
    private final String message;

    /**
     * 构造一个<code>SiteStatusEnum</code>枚举对象
     *
     * @param code
     * @param message
     */
    private RegionTypeEnum(String code, String message) {
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
     * @return BusiTypeEnum
     */
    public static RegionTypeEnum getByCode(String code) {
        for (RegionTypeEnum _enum : values()) {
            if (_enum.getCode().equals(code)) {
                return _enum;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<BusiTypeEnum>
     */
    public static List<RegionTypeEnum> getAllEnum() {
        List<RegionTypeEnum> list = new ArrayList<RegionTypeEnum>();
        for (RegionTypeEnum _enum : values()) {
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
        for (RegionTypeEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
