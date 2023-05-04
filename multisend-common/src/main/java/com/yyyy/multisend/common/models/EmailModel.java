package com.yyyy.multisend.common.models;


import lombok.*;

/**
 * @author isADuckA
 * @Date 2023/4/23 22:23
 * 邮件占位符
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailModel extends Models {

    /**
     * 邮件标题
     */
    private String title;

    /**
     * 邮件内容
     */
    private String msgContent;

    /**
     * 短信附带文件
     */
    private String url;
}
