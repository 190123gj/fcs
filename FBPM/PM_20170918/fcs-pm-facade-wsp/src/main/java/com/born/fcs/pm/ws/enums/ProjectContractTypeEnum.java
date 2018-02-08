package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目合同集申请类别
 *
 * @author heh
 *
 * 2016年9月21日10:05:16
 */
public enum ProjectContractTypeEnum {

    PROJECT_CONTRACT("PROJECT_CONTRACT", "合同"),

    PROJECT_WRIT("PROJECT_WRIT", "文书"),

    PROJECT_DB_LETTER("PROJECT_DB_LETTER","(担保函/保证合同签订)"),

    PROJECT_LETTER("PROJECT_LETTER","(函/通知书)");

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
    private ProjectContractTypeEnum(String code, String message) {
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
    public static ProjectContractTypeEnum getByCode(String code) {
        for (ProjectContractTypeEnum _enum : values()) {
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
    public static List<ProjectContractTypeEnum> getAllEnum() {
        List<ProjectContractTypeEnum> list = new ArrayList<ProjectContractTypeEnum>();
        for (ProjectContractTypeEnum _enum : values()) {
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
        for (ProjectContractTypeEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
