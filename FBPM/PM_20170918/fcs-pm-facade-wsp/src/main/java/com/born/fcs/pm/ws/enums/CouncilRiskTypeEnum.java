package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 会议类型
 * Created by wqh on 2016/9/12.
 */
public enum CouncilRiskTypeEnum {
    REGULAR_MEETING("REGULAR_MEETING", "例会","例"),

    MONTH_MEETING("MONTH_METTING", "月会","月"),

    QUARTER_MEETING("QUARTER_MEETING", "季会","季");

    /** 枚举值 */
    private final String code;



    /** 枚举描述 */
    private final String message;

    private final String alias;

    /**
     * 构造一个<code>CouncilTypeEnum</code>枚举对象
     *
     * @param code
     * @param message
     */
    private CouncilRiskTypeEnum(String code,  String message,String alias) {
        this.code = code;
        this.message = message;
        this.alias = alias;
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

    public String getAlias() {
        return alias;
    }



    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code
     * @return CouncilTypeEnum
     */
    public static CouncilRiskTypeEnum getByCode(String code) {
        for (CouncilRiskTypeEnum _enum : values()) {
            if (_enum.getCode().equals(code)) {
                return _enum;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<CouncilTypeEnum>
     */
    public static List<CouncilRiskTypeEnum> getAllEnum() {
        List<CouncilRiskTypeEnum> list = new ArrayList<CouncilRiskTypeEnum>();
        for (CouncilRiskTypeEnum _enum : values()) {
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
        for (CouncilRiskTypeEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
