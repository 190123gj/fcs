package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 合同类别
 *
 * @author heh
 *
 * 2016-3-10 下午3:11:26
 *
 */
public enum ContractTypeEnum {

    OTHER("OTHER", "自由合同"),

    STANDARD("STANDARD", "制式合同"),

    //2016年9月18日15:17:59 将NOTSTANDARD的message改成了制式
    NOTSTANDARD("NOTSTANDARD", "制式合同");

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
    private ContractTypeEnum(String code, String message) {
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
    public static ContractTypeEnum getByCode(String code) {
        for (ContractTypeEnum _enum : values()) {
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
    public static List<ContractTypeEnum> getAllEnum() {
        List<ContractTypeEnum> list = new ArrayList<ContractTypeEnum>();
        for (ContractTypeEnum _enum : values()) {
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
        for (ContractTypeEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
