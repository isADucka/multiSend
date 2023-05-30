package com.yyyy.multisend.serviceimpl.impl;


import com.yyyy.multisend.common.povo.vo.Result;
import com.yyyy.multisend.handler.utils.RedisUtils;
import com.yyyy.multisend.service.service.TraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author isADuckA
 * @Date 2023/5/7 1:59
 * 链路追踪
 */
@Service
public class TraceServiceImpl implements TraceService {


    @Autowired
    private RedisUtils redisUtils;

    /***
     * 通过接收者id来获取相对应的下发情况
     * @param receiver
     * @return
     */
    @Override
    public Result getTraceByReceiver(String receiver) {
        Result result=null;
        if(receiver==null){
            result=new Result("请输入非空数据");
        }else{
            //获取记录
            List<String> list = redisUtils.lRange(receiver, 0, -1);
            result= new Result(list);
        }
        return result;

    }
}
