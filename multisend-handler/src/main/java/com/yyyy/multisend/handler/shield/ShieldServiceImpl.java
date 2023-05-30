package com.yyyy.multisend.handler.shield;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyyy.multisend.common.enums.ShieldType;
import com.yyyy.multisend.common.povo.po.logPo.Business;
import com.yyyy.multisend.common.povo.po.logPo.BusinessResult;
import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.dao.utils.LogUtils;
import com.yyyy.multisend.handler.utils.RedisUtils;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.util.JAXBSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author isADuckA
 * @Date 2023/5/7 10:56
 *
 */
@Service
public class ShieldServiceImpl implements ShieldService{

    @Autowired
    private LogUtils logUtils;

    @Autowired
    private RedisUtils redisUtils;


    private static final String TOMORROW_SEND = "night_shield_send";
    private static final Long SECOND=86400L;
    /**
     * 处理屏蔽操作
     * @param msgTask
     */
    @Override
//    public void shield(MsgTask msgTask) {
    public MsgTask shield(MsgTask msgTask) {
        if(ShieldType.SHIELD.getCode().equals(msgTask.getShield())){
            //夜间不屏蔽
            return msgTask;
        }

        if(isNight()){
            if(ShieldType.SHILE_NOT_SEND.getCode().equals(msgTask.getShield())){
                //夜间屏蔽不发送,直接丢弃消息就好了
                logUtils.print(Business.builder()
                        .businessId(msgTask.getBusinessId())
                        .receiverType(msgTask.getReceiverType())
                        .result(BusinessResult.SHIELD_NOT_SEND)
                        .build());
            }
           else if(ShieldType.SHILED_TOMORROW_SEND.getCode().equals(msgTask.getShield())){
                try {
                //夜间隔天发送
//                redisUtils.lPush(TOMORROW_SEND, JSON.toJSONString(msgTask, SerializerFeature.WriteClassName),SECOND);


                    String msg=new ObjectMapper().writeValueAsString(msgTask);
                    redisUtils.lPush(TOMORROW_SEND, msg,SECOND);

                logUtils.print(Business.builder()
                        .businessId(msgTask.getBusinessId())
                        .receiverType(msgTask.getReceiverType())
                        .result(BusinessResult.SHIELD_SEND)
                        .build());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
//            msgTask.setReceiver(new HashSet<>());
            Map<String,Map<String,String>> map=null;
            msgTask.setReceiverAndParm(map);
        }
        return msgTask;
    }

    /**
     * 小时 < 8 默认就认为是凌晨(夜晚)
     *
     * @return
     */
    private boolean isNight() {
        return LocalDateTime.now().getHour() > 8;

    }
}
