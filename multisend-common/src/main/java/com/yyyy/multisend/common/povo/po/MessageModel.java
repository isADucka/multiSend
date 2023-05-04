package com.yyyy.multisend.common.povo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yyyy.multisend.common.enums.MsgType;
import com.yyyy.multisend.common.enums.ReceiverType;
import lombok.*;
import org.apache.ibatis.annotations.Delete;
import org.hibernate.annotations.Table;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author isADuckA
 * @Date 2023/4/9 19:03
 * 接收消息模板的，这个是存进数据库的
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@ToString
@Getter
public class MessageModel  {
    /**
     * 模板id,这里值的是存进数据库的模板id,每条信息独有的标识
     */
   @Id
//   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modelId;

    /**
     * 模板名称
     */
    private String modelName;

    /**
     * 模板创作者
     */
    private String creator;

//    /**
//     * 模板接收对象，如果是短信的话就是一个手机号码，
//     * 如果是支付宝的话就是支付宝账号，以此类推
//     */
//    private String receiver;


    /**
     * 接受对象的类型，用于后面判断receiver的格式正不正确
     * receiverType的code值就可以当模板类型使用，比如1001代表短信
     */
//    private ReceiverType receiverType;
    private Integer receiverType;

//    /**
//     * 模板类型，比如说短信，支付宝等
//     */
//    private Integer modelType;

    /**
     * 信息的标题
     */
    private String title;

    /**
     * 模板接受内容（这个地方是不是搞成map比较好？）
     */
    private String msgContent;

    /**
     * 信息类型，
     * 101 :验证码
     * 102 ：营销类
     * 103 ：通知类
     */
//    private MsgType msgType;
    private Integer msgType;

    /**
     * 传进来的连接
     */
    private String url;


    private Integer isDelete;




}
