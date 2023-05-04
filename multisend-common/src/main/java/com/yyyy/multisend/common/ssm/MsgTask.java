package com.yyyy.multisend.common.ssm;

import com.yyyy.multisend.common.enums.MsgType;
import com.yyyy.multisend.common.enums.ReceiverType;
import com.yyyy.multisend.common.models.Models;
import lombok.*;

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
public class MsgTask {

//    /**
//     * 消息标题，短信无，邮件有
//     */
//    private String title;
//
//    /**
//     * 消息的内容
//     */
//    private String context;
//
//    /**
//     * 消息内容的方式，比如通知类短信，营销类短信
//     */
////    private MsgType msgType;
//    private Integer msgType;
//
//
//    /**
//     * 消息接收者
//     */
//    private String receiever;
//
//
//    /**
//     * 接收者类型
//     */
////    private ReceiverType receiverType;
//    private Integer receiverType;


    /**
     * 模板id
     */
   private Long modelId;

    /**
     * 包含传进来的参数
     */
   private Map<String,String> parm;

    /**
     * 把他和parm分开是因为没有占位符，输入什么就是什么
     */
   private Set<String> receiver;

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

}
