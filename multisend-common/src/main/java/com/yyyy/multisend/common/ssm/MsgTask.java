package com.yyyy.multisend.common.ssm;

import com.yyyy.multisend.common.enums.MsgType;
import com.yyyy.multisend.common.enums.ReceiverType;
import com.yyyy.multisend.common.models.Models;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author isADuckA
 * @Date 2023/4/9 19:33
 * 搭建发送消息的渠道，比如说搭建短信，邮件等
 */
@Data
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class MsgTask {

    /**
     * 模板id
     */
   private Long modelId;


    private Map<String,Map<String,String>> receiverAndParm;

    /**
     * 接收者类型
     */
   private Integer receiverType;

    /**
     * 处理后的参数
     */
   private Models models;

    /**
     * 发送渠道,比如说是短信营销，短信验证还是啥
     */
   private Integer msgType;

    /**
     * 业务id
     */
   private Long businessId;

    /**
     * 屏蔽类型
     */
   private Integer shield;

}
