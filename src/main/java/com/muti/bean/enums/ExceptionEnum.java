package com.muti.bean.enums;

import lombok.Getter;

/**
 * Desciption 异常枚举
 * Create By  li.bo
 * CreateTime 2018/7/17 15:00
 * UpdateTime 2018/7/17 15:00
 */
public enum ExceptionEnum {

    UNKNOW_ERROR(201, "未知错误"),
    USER_NOT_FIND(202, "用户不存在");

    private Integer code;
    private String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
