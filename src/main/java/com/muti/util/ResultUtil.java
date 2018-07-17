package com.muti.util;


import com.muti.bean.dto.Result;
import com.muti.bean.enums.ExceptionEnum;

/**
 * Desciption 返回体工具类
 * Create By  li.bo
 * CreateTime 2018/7/17 15:06
 * UpdateTime 2018/7/17 15:06
 */
public class ResultUtil {

    private ResultUtil() {
        throw new Error("Don't init the instance of Class: " + ResultUtil.class);
    }

    /**
     * if success
     * @param object
     * @return
     */
    public static Result success(Object object) {
        Result result = new Result();
        result.setStatus(200);
        result.setMsg("success");
        result.setData(object);
        return result;
    }

    /**
     * if success and not response body
     * @return
     */
    public static Result success() {
        return success(null);
    }

    /**
     * if error and define the error info
     * @param code
     * @param msg
     * @return
     */
    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setStatus(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    /**
     * if error and we know the error
     * @param exceptionEnum
     * @return
     */
    public static Result error(ExceptionEnum exceptionEnum) {
        Result result = new Result();
        result.setStatus(exceptionEnum.getCode());
        result.setMsg(exceptionEnum.getMsg());
        result.setData(null);
        return result;
    }
}
