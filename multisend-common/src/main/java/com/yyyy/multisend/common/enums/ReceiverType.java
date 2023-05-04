package com.yyyy.multisend.common.enums;

import com.yyyy.multisend.common.models.EmailModel;
import com.yyyy.multisend.common.models.Models;
import com.yyyy.multisend.common.models.SsmModle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author isADuckA
 * @Date 2023/4/11 0:10
 * 描述接收者的账号类型
 */
@Getter
@ToString
@AllArgsConstructor
public enum ReceiverType {
    PHONE(1001,"^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$","短信", SsmModle.class),
//    ALIPAY(1002,"支付宝","支付宝"),
//    WECHAT(1003,"微信","微信"),
    EMAIL(1004,"^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$","邮件", EmailModel.class);


    private final Integer code;
    private final String ruler;
    private final String desc;
    private final Class<? extends Models> ModelClass;

    /***
     * 根据code来获取ruler
     * 如果code真确就能获取到相对应的ruler，否则只能返回null
     * @param code
     * @return
     */
    public static String getRuler(Integer code){
        ReceiverType[] values = ReceiverType.values();
        for (ReceiverType receiverType:values){
            if(receiverType.getCode().equals(code)){
                return receiverType.getRuler();
            }
        }
        return null;
    }

    /**
     * 根据code的值来获取相对应的class内容，class是相对应的发送方式具有占位符的属性
     * @return
     */
    public static Class<? extends Models> getClassByCode(Integer code){
        ReceiverType[] values = ReceiverType.values();
        for(ReceiverType receiverType:values){
            if(receiverType.getCode().equals(code)){
                return receiverType.getModelClass();
            }
        }
        return null;
    }

}
