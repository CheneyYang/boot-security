package com.iooiee.common;

/**
 * Description:
 *
 * @Author: Yanggc
 * DateTime: 10/22 13:54
 */
public enum ResultCodeEnum {

    /* 状态码 */
    UNKNOWN_ERROR(404, "未知错误"),
    SUCCESS(200,"成功"),
    ERROR (201,"失败");

    private Integer code;

    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        for (ResultCodeEnum item : ResultCodeEnum.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (ResultCodeEnum item : ResultCodeEnum.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }


}
