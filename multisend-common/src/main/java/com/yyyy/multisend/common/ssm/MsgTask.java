package com.yyyy.multisend.common.ssm;

import com.yyyy.multisend.common.enums.MsgType;
import com.yyyy.multisend.common.enums.ReceiverType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author isADuckA
 * @Date 2023/4/9 19:33
 * 是发送短信的必须属性
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MsgTask {
    /**
     * 短信签名
     */
    private String singleName;

    /**
     * 短信模板id
     */
    private String templateId;

    /**
     * 短信的内容
     */
    private String context;

    /**
     * 短息内容的方式，比如通知类短信，营销类短信
     */
    private MsgType msgType;


    /**
     * 短信接收者
     */
    private String receiever;


    /**
     * 接收者类型
     */
    private ReceiverType receiverType;

}
