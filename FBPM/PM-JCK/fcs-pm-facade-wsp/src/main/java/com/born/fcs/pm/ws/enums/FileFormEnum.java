package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 档案表单类型
 *
 * @author heh
 *
 */
public enum FileFormEnum {

    OUTPUT("OUTPUT", "出库"),

    VIEW("VIEW", "查阅"),;

    /** 枚举值 */
    private final String code;

    /** 枚举描述 */
    private final String message;

    /**
     * 构造一个<code>CouncilTypeEnum</code>枚举对象
     *
     * @param code
     * @param message
     */
    private FileFormEnum(String code, String message) {
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
     * @return CouncilTypeEnum
     */
    public static FileFormEnum getByCode(String code) {
        for (FileFormEnum _enum : values()) {
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
    public static List<FileFormEnum> getAllEnum() {
        List<FileFormEnum> list = new ArrayList<FileFormEnum>();
        for (FileFormEnum _enum : values()) {
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
        for (FileFormEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
