package com.yyyy.multisend.common.models;

import lombok.*;

/**
 * @author isADuckA
 * @Date 2023/4/23 22:09
 * 短信占位符
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SsmModle extends Models{

    /**
     * 短信内容
     */
    private String msgContent;


    /**
     * 短信发送连接
     */
    private String url;

}
