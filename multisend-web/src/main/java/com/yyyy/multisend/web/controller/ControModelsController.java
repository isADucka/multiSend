package com.yyyy.multisend.web.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.yyyy.multisend.common.povo.vo.Result;
import com.yyyy.multisend.common.povo.po.MessageModel;
import com.yyyy.multisend.service.service.ControModelsService;
import com.yyyy.multisend.web.annocation.MultisendAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.util.List;

/**
 * @author isADuckA
 * @Date 2023/4/19 16:56
 * 主要是用来存储模板等
 */
@RestController
@RequestMapping("/contro")
@MultisendAspect
@Slf4j
public class ControModelsController {


    @Value("${multisend.cron.path}")
    private String dataPath;
    @Autowired
    private ControModelsService controModelsService;

    @GetMapping("/save")
    public Result SaveMsgModel(@RequestBody MessageModel messageModel){
       return controModelsService.saveModel(messageModel);

    }


    @DeleteMapping("/delete")
    public Result deleteModel(@RequestParam List<Long> modelId){
        System.out.println(modelId);
        //删除模板
        return controModelsService.deleteModel(modelId);

    }

    /**
     * 上传目标人群文件
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("upload")
    public Result saveFile(@RequestParam("file")MultipartFile file) throws IOException {
        String filePath =dataPath+file.getOriginalFilename();
        try {
            File localFile = new File(filePath);
            if (!localFile.exists()) {
                localFile.mkdirs();
            }
            // 注意一点的是，没有用绝对路径会报错

            file.transferTo(localFile.getAbsoluteFile());
            System.out.println(file.getResource());
        } catch (Exception e) {
            log.error("MessageTemplateController#upload fail! e:{},params{}", Throwables.getStackTraceAsString(e), JSON.toJSONString("找不到指定路径"));
        }

        return new Result(filePath);


    }

}


