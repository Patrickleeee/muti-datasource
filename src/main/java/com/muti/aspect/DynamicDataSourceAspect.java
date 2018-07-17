package com.muti.aspect;

import com.muti.annotation.DS;
import com.muti.config.dataSource.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Desciption 数据源动态切换切面
 * Create By  li.bo
 * CreateTime 2018/7/10 11:28
 * UpdateTime 2018/7/10 11:28
 */
@Aspect
@Component
@Slf4j
public class DynamicDataSourceAspect {

    @Before("@annotation(DS)")
    public void beforeSwitchDS(JoinPoint point, DS DS) {
        // get current class
        Class<?> className = point.getTarget().getClass();
        // get method name
        String methodName = point.getSignature().getName();
        // get all parameter types of method
        Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();

        String dataSource = DataSourceContextHolder.DEFAULT_DB;
        try {
            // get method object
            Method method = className.getMethod(methodName, argClass);
            // if exist @DS annotation
            if (method.isAnnotationPresent(DS.class)) {
                DS annotation = method.getAnnotation(DS.class);
                // get the datasource of annotation
                dataSource = annotation.value();
            }
        } catch (NoSuchMethodException e) {
            log.error("切换数据源失败");
            e.printStackTrace();
        }
        // switch datasource
        DataSourceContextHolder.setDb(dataSource);
    }

    @After("@annotation(DS)")
    public void afterSwitchDS(JoinPoint point, DS DS) {
        DataSourceContextHolder.clearDB();
    }
}
