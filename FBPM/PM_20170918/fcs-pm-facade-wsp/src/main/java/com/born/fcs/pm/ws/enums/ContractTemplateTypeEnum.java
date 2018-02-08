package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 合同模板类别
 *
 * @author heh
 *
 * 2016年9月18日10:22:50
 * 为了兼容以前的代码 直接从ContractTypeEnum迁移过来的，直接改了message
 *
 */
public enum ContractTemplateTypeEnum {


    STANDARD("STANDARD", "网页版"),

    NOTSTANDARD("NOTSTANDARD", "word版");

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
    private ContractTemplateTypeEnum(String code, String message) {
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
    public static ContractTemplateTypeEnum getByCode(String code) {
        for (ContractTemplateTypeEnum _enum : values()) {
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
    public static List<ContractTemplateTypeEnum> getAllEnum() {
        List<ContractTemplateTypeEnum> list = new ArrayList<ContractTemplateTypeEnum>();
        for (ContractTemplateTypeEnum _enum : values()) {
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
        for (ContractTemplateTypeEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
