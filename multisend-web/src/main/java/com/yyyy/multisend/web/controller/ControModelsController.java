package com.yyyy.multisend.web.controller;

import com.yyyy.multisend.common.povo.vo.Result;
import com.yyyy.multisend.common.povo.po.MessageModel;
import com.yyyy.multisend.service.service.ControModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author isADuckA
 * @Date 2023/4/19 16:56
 * 主要是用来存储模板等
 */
@RestController
@RequestMapping("/contro")
public class ControModelsController {

    @Autowired
    private ControModelsService controModelsService;

    @GetMapping("/save")
    public Result SaveMsgModel(@RequestBody MessageModel messageModel){
        controModelsService.saveModel(messageModel);
        return null;
    }


    @DeleteMapping("/delete")
    public Result deleteModel(@RequestParam List<Long> modelId){
        System.out.println(modelId);
        //删除模板
        return controModelsService.deleteModel(modelId);

    }

}


