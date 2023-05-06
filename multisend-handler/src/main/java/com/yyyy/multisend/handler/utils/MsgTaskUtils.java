package com.yyyy.multisend.handler.utils;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * @author isADuckA
 * @Date 2023/5/5 20:38
 * 工具类，用于生成userId
 */
public class MsgTaskUtils {

    /**
     * 生成businessId
     * 结构为：receiverTYpe+modelId+msg_type+日期(月日时分)
     * eg:1004 0015 101 03151515
     * 一共是18位 4+5+3+8
     * @return
     */
    public static long createBusinessId(Long modleId,Integer receiverType,Integer msgType){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmm");
        String time = formatter.format(now);
        //格式化modleId
        String id = String.valueOf(modleId);
        for(int i=id.length();i<=5;i++){
            id=0+id;
        }
        //拼接
        Long businessId = Long.getLong(modleId + msgType + id + time);
        return  businessId;
    }
}
