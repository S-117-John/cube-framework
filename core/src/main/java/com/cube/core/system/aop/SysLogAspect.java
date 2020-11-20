package com.cube.core.system.aop;

import com.cube.common.utils.IpUtil;
import com.cube.core.system.annotation.SysLog;
import com.cube.core.system.entity.SystemLogDO;
import com.cube.core.system.repository.SystemLogRepository;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SystemLogRepository systemLogRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Autowired
    private TransactionDefinition transactionDefinition;

//    @Before(
//            value = "@annotation(com.cube.core.system.annotation.SysLog)"
//    )
    public void saveSysLog(JoinPoint joinPoint) {

        SystemLogDO systemLog = new SystemLogDO();
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog myLog = (SysLog)method.getAnnotation(SysLog.class);
        String className;
        if (myLog != null) {
            className = myLog.value();
            systemLog.setOperation(className);
        }

        className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        systemLog.setMethod(className + "." + methodName);
        Object[] args = joinPoint.getArgs();
        systemLog.setCreateDate(new Date());
        systemLog.setUsername("");
        systemLog.setIp(IpUtil.getRemoteAddr());
        Object object=null;
        Gson gson = new Gson();
        String param = gson.toJson(args);
        systemLog.setParams(param);

//        platformTransactionManager.commit(transactionStatus);
        systemLogRepository.save(systemLog);
    }

    @AfterReturning(value = "@annotation(com.cube.core.system.annotation.SysLog)",returning = "object")
    public void saveSysResult(JoinPoint joinPoint,Object object) {

        SystemLogDO systemLog = new SystemLogDO();
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog myLog = (SysLog)method.getAnnotation(SysLog.class);
        String className;
        if (myLog != null) {
            className = myLog.value();
            systemLog.setOperation(className);
        }

        className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        systemLog.setMethod(className + "." + methodName);
        Object[] args = joinPoint.getArgs();
        systemLog.setCreateDate(new Date());
        systemLog.setUsername("");
        systemLog.setIp(IpUtil.getRemoteAddr());
        Gson gson = new Gson();
        String param = gson.toJson(args);
        systemLog.setParams(param);

        systemLog.setResult(gson.toJson(object));
        systemLogRepository.save(systemLog);
    }
}
