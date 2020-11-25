package com.imooc.mall.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

/**
 * 打印请求和响应信息
 */
@Aspect
@Component
public class WebLogAspect {

    private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
    @Pointcut("execution(public * com.imooc.mall.controller.*.*(..))")
    public void weblog(){

    }

    @Before("weblog()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        logger.info("URL:"+requestAttributes.getRequest().toString());
        logger.info("HTTP_METHOD:"+requestAttributes.getRequest().getMethod());
        logger.info("IP_ADDRESS:"+requestAttributes.getRequest().getRemoteAddr());
        logger.info("CLASS_METHOD:"+joinPoint.getSignature().getDeclaringTypeName()+
                "."+joinPoint.getSignature().getName());
        logger.info("ARGS:"+ Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "res",pointcut = "weblog()")
    public void doAfterReturning(Object res) throws JsonProcessingException {
        logger.info("RESPONSE:"+new ObjectMapper().writeValueAsString(res));
    }

}
