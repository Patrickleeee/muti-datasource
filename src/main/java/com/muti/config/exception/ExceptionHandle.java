package com.muti.config.exception;

import com.muti.bean.dto.DescribeException;
import com.muti.bean.dto.Result;
import com.muti.bean.enums.ExceptionEnum;
import com.muti.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Desciption
 * Create By  li.bo
 * CreateTime 2018/7/17 15:34
 * UpdateTime 2018/7/17 15:34
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionGet(Exception e) {
        if (e instanceof DescribeException) {
            DescribeException exception = (DescribeException)e;
            return ResultUtil.error(exception.getCode(), exception.getMessage());
        }
        log.error("【系统异常】:", e);
        return ResultUtil.error(ExceptionEnum.UNKNOW_ERROR);
    }
}
