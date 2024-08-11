package com.titool.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Descrpition 日志切面
 * @Date 2024/8/11
 */
@Component
@Aspect
public class LogAspect {

    private static Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 切点方法
     */
    @Pointcut("@annotation(LogAnnotation)")
    public void logPointCut(){}

    /**
     * 在方法前后执行日志切面操作
     * @param joinPoint         切点
     * @param logAnnotation     日志注解
     * @return 方法执行结果
     */
    @Around("@annotation(logAnnotation)")
    public Object doAround(ProceedingJoinPoint joinPoint, LogAnnotation logAnnotation) throws Throwable {
        String name = joinPoint.getSignature().getName();
        LogLine logLine = new LogLine();
        prepare(joinPoint, logAnnotation, logLine);
        Object result = null;
        try{
//            LogUtil.info(LOGGER, "开始执行方法["+name+"]","方法描述："+logAnnotation.desc(), "方法参数"+args);
            result = joinPoint.proceed();
            return result;
        } catch (Throwable e) {
            logLine.setThrowable(e);
            throw e;
        } finally {
            logLine.setResult(result);
            logLine.setEndTime(System.currentTimeMillis());
            LogUtil.info(LOGGER, logLine.printLog());
        }

    }

    /**
     * 根据切入点和日志注解的属性填充日志的基本属性
     *
     * @param joinPoint         切入点
     * @param logAnnotation     日志注解
     * @param logLine           日志信息
     */
    private static void prepare(ProceedingJoinPoint joinPoint, LogAnnotation logAnnotation, LogLine logLine){
        Object[] args = joinPoint.getArgs();
        // 设置基础打印信息
        logLine.setClassName(joinPoint.getTarget().getClass().getName());
        logLine.setMethodName(joinPoint.getSignature().getName());
        logLine.setArgs(args);
        logLine.setThrowable(null);
        logLine.setDesc(logAnnotation.desc());
        // 获取关键参数与关键参数的字段
        int keyArgIndexes = logAnnotation.keyArgIndex();
        if (keyArgIndexes >= 0 && keyArgIndexes < args.length) {
            Object keyArg = args[keyArgIndexes];
            // 默认设置为关键参数
            logLine.setKeyArgInfo(keyArg);
            // 获取关键参数中指定字段的值
            String[] keyArgsProps = logAnnotation.keyArgsProps();
            if (keyArg != null && keyArgsProps.length > 0) {
                assembleKeyArgInfo(keyArg, keyArgsProps, logLine);
            }
        }
        logLine.setStartTime(System.currentTimeMillis());
    }

    /**
     * 根据日志注解拼接请求参数日志
     *
     * @param keyArg
     * @param keyArgProps
     * @param logLine
     */
    private static void assembleKeyArgInfo(Object keyArg,final String[] keyArgProps, LogLine logLine){
        StringBuilder keyArgInfo = new StringBuilder();
        for (final String keyPropName : keyArgProps) {
            try{
                Method methodGetter = getMethodGetter(keyArg, keyPropName);
                Object keyPropsValue = methodGetter.invoke(keyArg);
                if(keyArgInfo.length() != 0){
                    keyArgInfo.append(", ");
                }
                keyArgInfo.append(keyPropName).append("=").append(keyPropsValue.toString());
            } catch (Exception e) {
                LogUtil.error(LOGGER, e, "参数拼接失败");
            }
        }
        logLine.setKeyArgInfo(keyArgInfo.toString());
    }

    /**
     * 获取对象指定字段的get方法
     *
     * @param object        对象
     * @param keyPropName   属性名
     * @return 对应属性的get方法
     */
    private static Method getMethodGetter(Object object, String keyPropName) throws NoSuchMethodException {
        Method getter;
        try{
            getter = object.getClass().getMethod(
                    "get" + keyPropName.substring(0,1).toUpperCase()+keyPropName.substring(1));
        } catch (NoSuchMethodException e) {
            getter = object.getClass().getMethod(
                    "is" + keyPropName.substring(0,1).toUpperCase()+keyPropName.substring(1));
        }
        return getter;
    }

}
