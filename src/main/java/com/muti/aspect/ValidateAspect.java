package com.muti.aspect;

import com.muti.annotation.ValidateField;
import com.muti.annotation.ValidateGroup;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Desciption 数据校验切面
 * Create By  li.bo
 * CreateTime 2018/7/17 10:43
 * UpdateTime 2018/7/17 10:43
 */
@Component
@Aspect
@Slf4j
public class ValidateAspect {

    @SuppressWarnings({"finally", "rawtypes"})
    @Around("@annotation(com.muti.annotation.ValidateGroup)")
    public Object validateAround(ProceedingJoinPoint joinPoint) throws Throwable {

        boolean flag = false;
        ValidateGroup an = null;
        Object[] args = null;
        Method method = null;
        Object target = null;
        String methodName = null;
        try {
            methodName = joinPoint.getSignature().getName();
            target = joinPoint.getTarget();
            method = this.getMethodByClassAndName(target.getClass(), methodName);// 得到拦截的方法
            args = joinPoint.getArgs();// 方法的参数
            an = (ValidateGroup) this.getAnnotationByMethod(method, ValidateGroup.class);
            flag = this.validateField(an.fields(), args);
        } catch (Exception e) {
            flag = false;
        } finally {
            // 可根据需要，返回不同的结果
            if (!flag) {
                log.info("验证未通过");
                Class returnType = method.getReturnType();// 得到方法返回值类型
                if(returnType == String.class){ //如果返回值为Stirng
                    return "fail";
                }
                return null;
            }
            log.info("验证通过");
            return joinPoint.proceed();
        }
    }

    /**
     * 校验参数是否合法
     * @param validateFields
     * @param args
     * @return
     */
    public boolean validateField(ValidateField[] validateFields, Object[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        for (ValidateField validateField : validateFields) {
            Object arg  = null;
            if ("".equals(validateField.fieldName())) {
                arg = args[validateField.index()];
            } else {
                arg = getFieldByObjectAndFieldName(args[validateField.index()], validateField.fieldName());
            }

            // 校验参数是否为空
            if (validateField.notNull()) {
                if (arg == null) {
                    return false;
                }
            } else {
                if (arg == null) {
                    return true;
                }
            }

            // 校验字符串最大长度
            if (validateField.maxLength() > 0) {
                if (((String) arg).length() > validateField.maxLength()) {
                    return false;
                }
            }

            // 校验字符串最小长度
            if (validateField.minLength() > 0) {
                if (((String) arg).length() < validateField.minLength()) {
                    return false;
                }
            }

            // 校验数值最大值
            if (validateField.maxValue() != -1) {
                if ((Integer) arg > validateField.maxValue()) {
                    return false;
                }
            }

            // 校验数值最小值
            if (validateField.minValue() != -1) {
                if ((Integer) arg < validateField.minValue()) {
                    return false;
                }
            }

            if (!"".equals(validateField.regStr())) {
                if (arg instanceof String) {
                    if (!((String) arg).matches(validateField.regStr())) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 根据对象和属性名得到属性
     * @param targetObject
     * @param fieldName
     * @return
     */
    private Object getFieldByObjectAndFieldName(Object targetObject, String fieldName) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String tmp[] = fieldName.split("\\.");
        Object arg = targetObject;
        for (int i = 0; i < tmp.length; i++) {
            Method method = arg.getClass().getMethod(this.getGetterNameByFieldName(tmp[i]));
            arg = method.invoke(arg);
        }
        return arg;
    }

    /**
     * 根据属性名 得到该属性的getter方法名
     * @param fieldName
     * @return
     */
    private String getGetterNameByFieldName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * 根据目标方法和注解类型得到该目标方法的指定注解
     * @param method
     * @param annotationClass
     * @return
     */
    public Annotation getAnnotationByMethod(Method method, Class annotationClass) {
        Annotation all[] = method.getAnnotations();
        for (Annotation annotation : all) {
            if (annotation.annotationType() == annotationClass) {
                return annotation;
            }
        }
        return null;
    }

    /**
     * 根据类和方法名得到方法
     * @param c
     * @param methodName
     * @return
     */
    public Method getMethodByClassAndName(Class c, String methodName) {
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }
}
