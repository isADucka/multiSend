package com.yyyy.multisend.handler.dutyHandler;

import com.yyyy.multisend.common.enums.SendType;
import com.yyyy.multisend.dao.handler.DutyChain;
import com.yyyy.multisend.handler.dutyHandler.Duty;
import com.yyyy.multisend.handler.dutyHandler.dudyHandlerImpl.AfterCheckDuty;
import com.yyyy.multisend.handler.dutyHandler.dudyHandlerImpl.PreCheckDuty;
import com.yyyy.multisend.handler.dutyHandler.dudyHandlerImpl.SendMqDuty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author isADuckA
 * @Date 2023/4/11 19:12
 * 处理Duty的执行顺序的
 */

@Service
public class SendInTurn {

    @Autowired
    private  PreCheckDuty preCheckDuty;

    @Autowired
    private AfterCheckDuty afterCheckDuty;

    @Autowired
    private SendMqDuty sendMqDuty;


    private  Map<SendType,List<Duty>> processMap;

    /**
     * 先定义好执行的顺序
     */
    @PostConstruct
    public void before(){
        System.out.println(" i am coming");
        List<Duty> list=new ArrayList<>();
        list.add(0,preCheckDuty);
        list.add(1,afterCheckDuty);
        list.add(2, sendMqDuty);
        processMap=new HashMap<>();
        processMap.put(SendType.SEND,list);
    }

    public DutyChain process(DutyChain dutyChain){
        //先写死只有发送的顺序链
//        System.out.println(processMap.isEmpty());
        List<Duty> list = processMap.get(SendType.SEND);
        for (Duty duty:list){
            DutyChain p = duty.porcess(dutyChain);
            if(p.isOver()){
              //说明责任链执行有问题，需要终止
                break;
            }
        }
        //有点怪怪的
        return dutyChain ;
    }
}
