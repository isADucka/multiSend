package com.yyyy.multisend.cron.xxl.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import com.yyyy.multisend.common.povo.po.MessageModel;
import com.yyyy.multisend.cron.xxl.constant.XxlJobConstant;
import com.yyyy.multisend.cron.xxl.po.XxlJobInfo;
//import com.yyyy.multisend.cron.xxl.service.CronTaskService;
import com.yyyy.multisend.cron.xxl.service.CronTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author isADuckA
 * @Date 2023/5/7 22:21
 * 定时工具类
 */
@Component
@Slf4j
public class XxlJobUtils {


    @Value("${xxl.job.executor.appname}")
    private String appName;

    @Value("${xxl.job.executor.jobHandlerName}")
    private String jobHandlerName;

    @Autowired
    private CronTaskService cronTaskService;



    /**
     * 构建xxlJobInfo信息
     *
     * @param
     * @return
     */
    public XxlJobInfo buildXxlJobInfo(MessageModel messageModel) {

//        String scheduleConf = messageTemplate.getExpectPushTime();
        String scheduleConf = messageModel.getCronExpre();
        // 如果没有指定cron表达式，说明立即执行(给到xxl-job延迟5秒的cron表达式)
        if (scheduleConf.equals("0")) {
            scheduleConf = DateUtil.format(DateUtil.offsetSecond(new Date(), XxlJobConstant.DELAY_TIME), XxlJobConstant.CRON_FORMAT);
        }

        XxlJobInfo xxlJobInfo = XxlJobInfo.builder()
                .jobGroup(getGroupId())
                .jobDesc(messageModel.getTitle())
                //没设置这个字段，先写死
                .author(messageModel.getCreator())
                .scheduleConf(scheduleConf)
                .scheduleType(XxlJobConstant.CRON)
                .misfireStrategy(XxlJobConstant.DO_NOTHING)
                .executorRouteStrategy(XxlJobConstant.CONSISTENT_HASH)
                .executorHandler(XxlJobConstant.JOB_HANDLER_NAME)
                .executorParam(String.valueOf(messageModel.getModelId()))
                .executorBlockStrategy(ExecutorBlockStrategyEnum.SERIAL_EXECUTION.name())
                .executorTimeout(XxlJobConstant.TIME_OUT)
                .executorFailRetryCount(XxlJobConstant.RETRY_COUNT)
                .glueType(GlueTypeEnum.BEAN.name())
                //先写这个，后续缓存变量
                .triggerStatus(0)
                .glueRemark(StrUtil.EMPTY)
                .glueSource(StrUtil.EMPTY)
                .alarmEmail(StrUtil.EMPTY)
                .childJobId(StrUtil.EMPTY).build();

        if(xxlJobInfo.getExecutorParam()!=null){
            xxlJobInfo.setId(messageModel.getCronTaskId());
        }
        return xxlJobInfo;
    }

    /**
     * 获取执行器主键id
     * @return
     */
    public Integer getGroupId(){
        Integer groupId = cronTaskService.getGroupId(appName, jobHandlerName);
        if(groupId==null){
            log.info("XXlJobUtils#getGroupId {}","获取groupId出现异常");
        }
        return groupId;
    }
}
