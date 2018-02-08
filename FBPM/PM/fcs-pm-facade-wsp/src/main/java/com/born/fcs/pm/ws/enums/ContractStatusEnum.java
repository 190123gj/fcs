package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 合同状态
 *
 * @author heh
 *
 * 2016-4-26 下午3:11:26
 */
public enum ContractStatusEnum {

    DELETED("DELETED", "已删除"),

    DRAFT("DRAFT",  "草稿"),

    BACK("BACK",  "驳回"),

    CANCEL("CANCEL",  "已撤销"),

    SUBMIT("SUBMIT", "待审核"),

    AUDITING("AUDITING",  "审核中"),

    CHECKED("CHECKED", "已审核未确认"),

    APPROVAL("APPROVAL", "已通过"),

    DENY("DENY", "不通过"),

    END("END",  "单据已作废"),

    CONFIRMED("CONFIRMED", "已确认未用印"),

    SEALING("SEALING", "用印中"),

    SEAL("SEAL", "已用印未回传"),

    RETURN("RETURN","已回传"),

    INVALIDING("INVALIDING","作废中"),

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
    private ContractStatusEnum(String code, String message) {
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
    public static ContractStatusEnum getByCode(String code) {
        for (ContractStatusEnum _enum : values()) {
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
    public static List<ContractStatusEnum> getAllEnum() {
        List<ContractStatusEnum> list = new ArrayList<ContractStatusEnum>();
        for (ContractStatusEnum _enum : values()) {
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
        for (ContractStatusEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
