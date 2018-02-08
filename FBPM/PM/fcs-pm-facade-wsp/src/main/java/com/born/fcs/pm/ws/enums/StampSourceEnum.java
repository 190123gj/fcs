package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 申请用印来源
 *
 * @author heh
 *
 * 2016-7-20 下午4:21:26
 */
public enum StampSourceEnum {

    CONTRACT_STANDARD("CONTRACT_STANDARD", "制式合同"),

    CONTRACT_NOTSTANDARD("CONTRACT_NOTSTANDARD", "非制式合同"),

    CONTRACT_OTHER("CONTRACT_OTHER", "其他合同"),

    PROJECT_WRIT("PROJECT_WRIT", "文书"),

    LETTER_STANDARD("LETTER_STANDARD", "(函/通知书)制式"),

    LETTER_NOTSTANDARD("LETTER_NOTSTANDARD", "(函/通知书)非制式"),

    LETTER_OTHER("LETTER_OTHER", "(函/通知书)其他"),

    CLETTER_STANDARD("CLETTER_STANDARD", "合同类函制式"),

    CLETTER_NOTSTANDARD("CLETTER_NOTSTANDARD", "合同类函非制式"),

    CLETTER_OTHER("CLETTER_OTHER", "合同类函其他"),

    CONSENT_ISSSUE_NOTICE("CONSENT_ISSSUE_NOTICE", "同意发行通知书"),

    EXPIRE_NOTICE("EXPIRE_NOTICE", "到期通知"),

    NOTICE_LETTER("NOTICE_LETTER", "通知函"),

    PROJECT_APPROVAL("PROJECT_APPROVAL", "项目批复"),

    CHANNEL_CONTRACT("CHANNEL_CONTRACT", "渠道合同"),

    OTHER("OTHER", "其他");


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
    private StampSourceEnum(String code, String message) {
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
    public static StampSourceEnum getByCode(String code) {
        for (StampSourceEnum _enum : values()) {
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
    public static List<StampSourceEnum> getAllEnum() {
        List<StampSourceEnum> list = new ArrayList<StampSourceEnum>();
        for (StampSourceEnum _enum : values()) {
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
        for (StampSourceEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
