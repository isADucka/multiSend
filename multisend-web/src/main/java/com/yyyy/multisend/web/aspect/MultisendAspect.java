package com.yyyy.multisend.web.aspect;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yyyy.multisend.web.vo.LogVo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author isADuckA
 * @Date 2023/5/4 22:29
 * AmultiSend切面
 *
 */
@Slf4j
@Aspect
@Component
public class MultisendAspect {

    private final String REQUEST_KEY="request_uuid";

    @Autowired
    private HttpServletRequest request;

    @Pointcut("@within(com.yyyy.multisend.web.annocation.MultisendAspect)||@annotation(com.yyyy.multisend.web.annocation.MultisendAspect)")
    public void executeService() {
    }

    /**
     * 前置通知
     * @param joinPoint
     */
    @Before("executeService()")
    public void logBeforeExec(JoinPoint joinPoint){
        //用于打印请求日志
        this.printRequest(joinPoint);
    }

    /**
     * 异常通知
     *
     * @param ex
     */
    @AfterThrowing(value = "executeService()", throwing = "ex")
    public void logException(Throwable ex) {
        this.printExceptionLog(ex);
    }


    /**
     * 打印请求日志
     * @param joinPoint
     */
    public void printRequest(JoinPoint joinPoint){
        //获取签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取参数
        Object[] args = joinPoint.getArgs();

        LogVo logVo=new LogVo();
        logVo.setId(IdUtil.fastUUID());
        request.setAttribute(REQUEST_KEY,logVo.getId());
        logVo.setArgs(args);
        logVo.setPath(signature.getDeclaringTypeName() + "." + signature.getMethod().getName());
        logVo.setUrI(request.getRequestURI());
        logVo.setMethod(request.getMethod());
        log.info(JSON.toJSONString(logVo));

    }

    /**
     * 用于记录异常
     * @param ex
     */
    public void printExceptionLog(Throwable ex){
        JSONObject logVo = new JSONObject();
        logVo.put("id", request.getAttribute(REQUEST_KEY));
        log.error(JSON.toJSONString(logVo), ex);
    }
}
