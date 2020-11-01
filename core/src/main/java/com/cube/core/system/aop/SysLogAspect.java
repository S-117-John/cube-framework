package com.cube.core.system.aop;

import com.cube.common.utils.IpUtil;
import com.cube.core.system.annotation.SysLog;
import com.cube.core.system.entity.SystemLogDO;
import com.cube.core.system.repository.SystemLogRepository;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    @AfterReturning(
            value = "@annotation(com.cube.core.system.annotation.SysLog)",
            returning = "object"
    )
    public void saveSysLog(JoinPoint joinPoint, Object object) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String param = request.getParameter("param");

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
        systemLog.setParams("");
        systemLog.setCreateDate(new Date());
        systemLog.setUsername("");
        systemLog.setIp(IpUtil.getRemoteAddr(joinPoint));
        systemLog.setParams(param);
        Gson gson = new Gson();
        String ret = gson.toJson(object);
        systemLog.setResult(ret);
        systemLogRepository.save(systemLog);
    }
}
