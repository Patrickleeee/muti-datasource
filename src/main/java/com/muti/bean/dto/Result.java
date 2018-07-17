package com.muti.bean.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Desciption 返回体
 * Create By  li.bo
 * CreateTime 2018/7/17 14:54
 * UpdateTime 2018/7/17 14:54
 */
@Setter
@Getter
@ToString
public class Result<T> {

    // error_code 状态值：200为成功，其他数值代表失败
    private Integer status;
    // error_msg 错误信息，若status为200时，为success
    private String msg;
    // 返回体报文的出参，使用泛型兼容不同的类型
    private T data;
}
