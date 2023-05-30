package com.yyyy.multisend.cron.xxl.constant;

/**
 * @author isADuckA
 * @Date 2023/5/7 22:35
 *  一些定时的常量参数
 */
public class XxlJobConstant {


    /**
     * 任务信息接口路径
     */
    public static final String LOGIN_URL = "/login";
    public static final String INSERT_URL = "/jobinfo/add";
    public static final String UPDATE_URL = "/jobinfo/update";
    public static final String DELETE_URL = "/jobinfo/remove";
    public static final String RUN_URL = "/jobinfo/start";
    public static final String STOP_URL = "/jobinfo/stop";

    /**
     * 执行器组接口路径
     */
    public static final String JOB_GROUP_PAGE_LIST = "/jobgroup/pageList";
    public static final String JOB_GROUP_INSERT_URL = "/jobgroup/save";


    /**
     * cron时间格式
     */
    public final static String CRON_FORMAT = "ss mm HH dd MM ? yyyy-yyyy";
    /**
     * 执行任务名称
     */
    public static final String JOB_HANDLER_NAME = "multisendJob";

    /**
     * 超时时间
     */
    public static final Integer TIME_OUT = 120;

    /**
     * 失败重试次数
     */
    public static final Integer RETRY_COUNT = 0;

    /**
     * 立即执行的任务 延迟时间(秒数)
     */
    public static final Integer DELAY_TIME = 10;
    /**
     * 调度类型
     */
    public static final String CRON="CRON";

    /**
     * 调度过期策略
     */
    public static final String DO_NOTHING="DO_NOTHING";

    /**
     * 执行器路由策略
     */
    public static final String CONSISTENT_HASH="CONSISTENT_HASH";
}
