package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 合同模板状态
 *
 * @author heh
 *
 * 2016年10月8日15:10:34
 *
 */
public enum ContractTemplateStatusEnum {


    DELETED("DELETED", "已删除"),

    DRAFT("DRAFT",  "草稿"),

    BACK("BACK",  "驳回"),

    CANCEL("CANCEL",  "已撤销"),

    SUBMIT("SUBMIT", "待审核"),

    END("END","单据已作废"),

    AUDITING("AUDITING",  "审核中"),

    CHECKED("CHECKED", "已审核"),

    IN_USE("IN_USE", "使用中"),

    INVALID("INVALID","已作废");

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
    private ContractTemplateStatusEnum(String code, String message) {
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
    public static ContractTemplateStatusEnum getByCode(String code) {
        for (ContractTemplateStatusEnum _enum : values()) {
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
    public static List<ContractTemplateStatusEnum> getAllEnum() {
        List<ContractTemplateStatusEnum> list = new ArrayList<ContractTemplateStatusEnum>();
        for (ContractTemplateStatusEnum _enum : values()) {
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
        for (ContractTemplateStatusEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
