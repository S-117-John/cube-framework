package com.cube.core.global.aspect;

import com.cube.common.utils.IpUtil;
import com.cube.core.global.model.ResponseVO;
import com.cube.core.system.annotation.SysLog;
import com.cube.core.system.entity.SystemLogDO;
import com.cube.core.system.repository.SystemLogRepository;
import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class ResponseAspect {

    @Autowired
    private SystemLogRepository systemLogRepository;

    @Around(value = "@annotation(com.cube.core.global.anno.ResponseApi)")
    public ResponseVO handlerController(ProceedingJoinPoint proceedingJoinPoint){
        ResponseVO response = new ResponseVO();
        Gson gson = new Gson();
        SystemLogDO systemLog = new SystemLogDO();
        try {
            Object proceed = proceedingJoinPoint.proceed();
            if (proceed instanceof ResponseVO) {
                response = (ResponseVO) proceed;
            } else {
                response.setData(proceed);
            }
            systemLog.setResult(gson.toJson(proceed));
        }
        catch (Throwable throwable) {
            response = handlerException(throwable);
            systemLog.setResult(response.getMessage());
        }

        //记录系统操作日志

        MethodSignature signature = (MethodSignature)proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog myLog = (SysLog)method.getAnnotation(SysLog.class);
        String className;
        if (myLog != null) {
            className = myLog.value();
            systemLog.setOperation(className);
        }

        className = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        systemLog.setMethod(className + "." + methodName);
        Object[] args = proceedingJoinPoint.getArgs();
        systemLog.setCreateDate(new Date());
        systemLog.setUsername("");
        systemLog.setIp(IpUtil.getRemoteAddr());
        String param = gson.toJson(args);
        systemLog.setParams(param);


        systemLogRepository.save(systemLog);

        return response;
    }

    /**
     * 异常处理
     */
    private ResponseVO handlerException(Throwable throwable) {
        ResponseVO response = new ResponseVO();
        String errorName = throwable.toString();
        errorName = errorName.substring(errorName.lastIndexOf(".") + 1);
        response.setCode("500");
        response.setMessage(errorName);
        return response;
    }


}
