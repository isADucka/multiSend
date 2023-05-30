package com.yyyy.multisend.cron.xxl.service;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.mysql.jdbc.log.LogUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.yyyy.multisend.common.enums.ResponseCodeType;
import com.yyyy.multisend.common.povo.po.MessageModel;
import com.yyyy.multisend.common.povo.vo.Result;
import com.yyyy.multisend.cron.xxl.constant.XxlJobConstant;
import com.yyyy.multisend.cron.xxl.po.XxlJobGroup;
import com.yyyy.multisend.cron.xxl.po.XxlJobInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static net.sf.jsqlparser.parser.feature.Feature.execute;

/**
 * @author isADuckA
 * @Date 2023/5/8 8:35
 */
@Service
@Slf4j
public class CronTaskServiceImpl implements CronTaskService{

    @Value("${xxl.job.admin.addresses}")
    private String xxlJobAddress;


    @Value("${xxl.job.admin.username}")
    private String userName;

    @Value("${xxl.job.admin.password}")
    private String password;



    /**
     * 如果有执行器主键id,就获取并返回，没有则新建一个
     * @param appName
     * @param title
     * @return
     */
    @Override
    public Integer getGroupId(String appName, String title) {
        //先拼接查询获取id的地址
        String searchUrl=xxlJobAddress+ XxlJobConstant.JOB_GROUP_PAGE_LIST;
        System.out.println("searchUrl+"+searchUrl);
        HashMap<String,Object> parm=new HashMap<>();
        parm.put("appname",appName);
        parm.put("title",title);

        HttpResponse response;
        response = HttpRequest.post(searchUrl).form(parm).cookie(getCookie()).execute();
        //me：这个获取到的id,应该是执行器的id
        Integer id = JSON.parseObject(response.body()).getJSONArray("data").getJSONObject(0).getInteger("id");
        if(response.isOk()&&Objects.nonNull(id)){
            return id;
        }else{
            //说明没有groudId,创建一个新的groupId
           String insertGrouId=xxlJobAddress+XxlJobConstant.JOB_GROUP_INSERT_URL;
           parm.put("addressType",0);
           response=HttpRequest.post(insertGrouId).form(parm).cookie(getCookie()).execute();
            ReturnT returnT = JSON.parseObject(response.body(), ReturnT.class);
            if(response.isOk()&&ReturnT.SUCCESS_CODE==returnT.getCode()){
                getGroupId(appName,title);
            }
        }
        return null;

    }

    @Override
    public String getCookie() {
        //先拼接url
        String logUrl=xxlJobAddress+XxlJobConstant.LOGIN_URL;
        System.out.println("getCOokie+"+logUrl);
        HashMap<String,Object> param=new HashMap<>();
        param.put("userName",userName);
        param.put("password",password);
        param.put("randomCode", IdUtil.fastSimpleUUID());


        HttpResponse response = null;
        try {
            response = HttpRequest.post(logUrl).form(param).execute();
            System.out.println("cookie——response:"+response);
            if (response.isOk()) {
                List<HttpCookie> cookies = response.getCookies();
                System.out.println(cookies.size());
                StringBuilder sb = new StringBuilder();
                for (HttpCookie cookie : cookies) {
                    sb.append(cookie.toString());
                }

                return sb.toString();
            }
        } catch (Exception e) {
            log.error("CronTaskService#createGroup getCookie,获取cookie异常");
        }
        return null;
    }

    @Override
    public Result saveCronTask(XxlJobInfo xxlJobInfo) {
        System.out.println("执行saveCronTask");
        Map<String, Object> params = JSON.parseObject(JSON.toJSONString(xxlJobInfo), Map.class);
        //有id就update,没有执行插入
        String path = Objects.isNull(xxlJobInfo.getId()) ? xxlJobAddress + XxlJobConstant.INSERT_URL
                : xxlJobAddress + XxlJobConstant.UPDATE_URL;

        HttpResponse response;
        ReturnT returnT = null;
        try {
            response = HttpRequest.post(path).form(params).cookie(getCookie()).execute();
            returnT = JSON.parseObject(response.body(), ReturnT.class);
            System.out.println(returnT);
            // 插入时需要返回Id，而更新时不需要
            if (response.isOk() && ReturnT.SUCCESS_CODE == returnT.getCode()) {
                if (path.contains(XxlJobConstant.INSERT_URL)) {
                    Integer taskId = Integer.parseInt(String.valueOf(returnT.getContent()));
                    return new Result(ResponseCodeType.OK_200,taskId);
                } else if (path.contains(XxlJobConstant.UPDATE_URL)) {
                    //这里是更新，所以返回的值没有id
                    return new Result(ResponseCodeType.OK_200);
                }
            }
        } catch (Exception e) {
            log.error("CronTaskService#saveTask fail,reascom={}","保存任务模块异常");
        }
        return new Result("保存模板失败");
    }

    /**
     * 定时模块开始
     * @param cronTaskId
     */
    @Override
    public Result startCronTask(Integer cronTaskId) {
        String path = xxlJobAddress + XxlJobConstant.RUN_URL;

        HashMap<String, Object> params = MapUtil.newHashMap();
        params.put("id", cronTaskId);

        HttpResponse response;
        ReturnT returnT = null;
        try {
            response = HttpRequest.post(path).form(params).cookie(getCookie()).execute();

            returnT = JSON.parseObject(response.body(), ReturnT.class);
            if (response.isOk() && ReturnT.SUCCESS_CODE == returnT.getCode()) {
                System.err.println("启动正常");
                return Result.SUCCESS("定时模块启动正常");
            }
        } catch (Exception e) {
            log.error("CronTaskService#startCronTask fail,reson={}","定时模块启动失败");
        }
        return new Result("定时模块启动失败");

    }

    /**
     * 暂停定时模块
     * @param cronTaskId
     * @return
     */
    @Override
    public Result stopCronTask(Integer cronTaskId) {
        String path = xxlJobAddress + XxlJobConstant.STOP_URL;

        HashMap<String, Object> parms = MapUtil.newHashMap();
        parms.put("id", cronTaskId);

        HttpResponse response;
        ReturnT returnT = null;
        try {
            response = HttpRequest.post(path).form(parms).cookie(getCookie()).execute();
            returnT = JSON.parseObject(response.body(), ReturnT.class);
            if (response.isOk() && ReturnT.SUCCESS_CODE == returnT.getCode()) {
                return Result.SUCCESS("成功暂停定时模板"+cronTaskId);
            }
        } catch (Exception e) {
            log.error("CronTaskService#stopCronTask fail,e:{},param:{},response:{}", Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(parms), JSON.toJSONString(returnT));
        }
        return new Result(ResponseCodeType.ERROR_500,"请求失败");
    }


}
