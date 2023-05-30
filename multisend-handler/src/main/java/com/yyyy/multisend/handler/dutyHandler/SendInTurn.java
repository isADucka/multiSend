package com.yyyy.multisend.handler.dutyHandler;

import com.yyyy.multisend.common.enums.SendType;
import com.yyyy.multisend.dao.handler.DutyChain;
import com.yyyy.multisend.handler.dutyHandler.Duty;
import com.yyyy.multisend.handler.dutyHandler.dudyHandlerImpl.AfterCheckDuty;
import com.yyyy.multisend.handler.dutyHandler.dudyHandlerImpl.PreCheckDuty;
import com.yyyy.multisend.handler.dutyHandler.dudyHandlerImpl.SendMqDuty;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SendInTurn {

    @Autowired
    private  PreCheckDuty preCheckDuty;

    @Autowired
    private AfterCheckDuty afterCheckDuty;

    @Autowired
    private SendMqDuty sendMqDuty;


    //记录责任链执行顺序
    private  Map<SendType,List<Duty>> processMap;

    /**
     * 先定义好执行的顺序
     */
    @PostConstruct
    public void before(){
        List<Duty> list=new ArrayList<Duty>(Arrays.asList(preCheckDuty,afterCheckDuty,sendMqDuty));
//        List numbers = new ArrayList();
        processMap=new HashMap<>();
        processMap.put(SendType.SEND,list);
    }

    public DutyChain process(DutyChain dutyChain){
        List<Duty> list = processMap.get(SendType.SEND);

        for (Duty duty:list){
//            DutyChain p = duty.porcess(dutyChain);
            //作为一个标志，后面让他一开始有问题，一步步处理为没有问题
            dutyChain.setOver(true);
            dutyChain = duty.porcess(dutyChain);
            if(dutyChain.isOver()){
              //说明责任链执行有问题，需要终止
                String err="com.yyyy.multisend.handler.dutyHandler.SendInTurn"+duty;
                log.info("位置{}"+err+" 责任链在此终止");
                break;
            }
        }

        return dutyChain ;
    }
}
