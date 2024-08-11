package com.titool.log;

import lombok.Data;

import java.io.Serializable;

/**
 * @Descrpition 日志输出格式
 * @Date 2024/8/11
 */
@Data
public class LogLine implements Serializable {
    /**
     * 左括号
     */
    private static final String S_PREFIX = "(";

    /**
     * 右括号
     */
    private static final String S_SUFFIX = ")";

    /**
     * 左中括号
     */
    private static final String C_PREFIX = "[";

    /**
     * 右中括号
     */
    private static final String C_SUFFIX = "]";

    /**
     * 逗号
     */
    private static final String COMMA = ",";

    /**
     * 句号
     */
    private static final String DOT = ".";

    /**
     * 成功
     */
    private static final String SUCCESS = "SUCCESS";

    /**
     * 失败
     */
    private static final String FAIL = "FAIL";

    /**
     * 毫秒
     */
    private static final String MSEL = "ms";

    /**
     * 无
     */
    private static final String NONE = "-";

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 方法开始时间
     */
    private long startTime;

    /**
     * 方法结束时间
     */
    private long endTime;

    /**
     * 抛出异常
     */
    private Throwable throwable;

    /**
     * 调用参数
     */
    private Object[] args;

    /**
     * 返回结果
     */
    private Object result;

    /**
     * 日志描述
     */
    private String desc;

    /**
     * 业务关键参数
     */
    private Object keyArgInfo;

    /**
     * 输出日志
     *
     * @return 要打印的日志字符串
     */
    public String printLog(){
        boolean isSuccess = (throwable == null);
        // 打印日志
        return C_PREFIX +
                // 方法描述
                S_PREFIX + StringUtil.defaultIfBlank(desc, NONE) + S_SUFFIX +
                S_PREFIX +
                // 类名
                StringUtil.defaultIfBlank(className, NONE) + DOT +
                // 方法名
                StringUtil.defaultIfBlank(methodName, NONE) + COMMA +
                // 是否执行成功
                StringUtil.defaultIfBlank(isSuccess ? SUCCESS : FAIL, NONE) + COMMA +
                // 执行时间
                getElapseTime() + MSEL +
                S_SUFFIX +
                // 打印参数，如果不指定关键参数默认打印方法所有参数
                S_PREFIX + (keyArgInfo == null ? argString(args) : keyArgInfo) + S_SUFFIX +
                // 打印结果
                S_PREFIX + (result == null ? NONE : result) + S_SUFFIX +
                C_SUFFIX;
    }

    /**
     * 计算方法实际执行时间
     *
     * @return 方法实际执行时间
     */
    private long getElapseTime() {
        return endTime-startTime;
    }

    /**
     * 拼接方法参数字符串
     *
     * @return 方法参数字符串
     */
    private String argString(Object[] curArgs){
        StringBuilder argSb = new StringBuilder();
        for(Object arg : curArgs){
            argSb.append(arg);
        }
        return argSb.toString();
    }


}
