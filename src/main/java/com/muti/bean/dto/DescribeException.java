package com.muti.bean.dto;

import com.muti.bean.enums.ExceptionEnum;

/**
 * Desciption 自定义异常
 * Create By  li.bo
 * CreateTime 2018/7/17 15:29
 * UpdateTime 2018/7/17 15:29
 */
public class DescribeException extends RuntimeException {

    private Integer code;

    /**
     * 继承exception，加入错误状态值
     * @param exceptionEnum
     */
    public DescribeException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }

    /**
     * 自定义错误信息
     * @param message
     * @param code
     */
    public DescribeException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
