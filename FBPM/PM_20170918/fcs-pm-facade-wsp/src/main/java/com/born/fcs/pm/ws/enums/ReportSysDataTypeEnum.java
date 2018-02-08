package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表系统数据类型
 *
 * @author heh
 *
 */
public enum ReportSysDataTypeEnum {

    NOT_ALL_SIGN("NOT_ALL_SIGN", "未全签"),

    ALL_SIGN("ALL_SIGN", "全签"),

    RXDB_BALANCE_AMOUNT("RXDB_BALANCE_AMOUNT", "融资担保业务在保余额"),

    DB_BALANCEAMOUNT("DB_BALANCEAMOUNT", "担保类别在保余额(担保业务结构汇总表)"),

    CONTRACT_AMOUNT("CONTRACT_AMOUNT", "合同金额(担保业务结构汇总表)"),
    ;

    /** 枚举值 */
    private final String code;

    /** 枚举描述 */
    private final String message;

    /**
     * 构造一个<code>RepayWayEnum</code>枚举对象
     *
     * @param code
     * @param message
     */
    private ReportSysDataTypeEnum(String code, String message) {
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
     * @return RepayWayEnum
     */
    public static ReportSysDataTypeEnum getByCode(String code) {
        for (ReportSysDataTypeEnum _enum : values()) {
            if (_enum.getCode().equals(code)) {
                return _enum;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<RepayWayEnum>
     */
    public static List<ReportSysDataTypeEnum> getAllEnum() {
        List<ReportSysDataTypeEnum> list = new ArrayList<ReportSysDataTypeEnum>();
        for (ReportSysDataTypeEnum _enum : values()) {
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
        for (ReportSysDataTypeEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
