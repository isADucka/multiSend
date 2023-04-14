package com.yyyy.multisend.common.povo.vo;


import com.yyyy.multisend.common.enums.ResponseCodeType;
import lombok.Data;


/**
 * @author isADuckA
 * @Date 2023/4/9 19:12
 * 统一的返回结果
 */
@Data
public class Result<T> {

    /**
     * 状态码
     */
    private ResponseCodeType code;

    /**
     * 返回的数据
     */
    private T msg;

    /**
     * 返回状态描述
     */
    private String desc;

    public Result(ResponseCodeType code, T msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

    public Result(ResponseCodeType code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 返回成功
     * @param desc
     * @return
     */
    public static Result SUCCESS(String desc){
       return new Result(ResponseCodeType.OK_200,desc);
    }




}
