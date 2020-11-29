package com.cube.kiosk.modules.aspect;

import com.cube.common.utils.IpUtil;
import com.cube.core.global.model.ResponseVO;
import com.cube.core.system.annotation.SysLog;
import com.cube.core.system.entity.SystemLogDO;
import com.cube.kiosk.modules.log.entity.HisHttpsLog;
import com.cube.kiosk.modules.log.entity.TransLogDO;
import com.cube.kiosk.modules.log.repository.HisHttpsLogRepository;
import com.cube.kiosk.modules.log.repository.TransLogRepository;
import com.cube.kiosk.modules.pay.model.TransactionData;
import com.cube.kiosk.modules.pay.repository.TransactionRepository;
import com.google.gson.Gson;

import org.apache.commons.text.StringEscapeUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class RestTemplateAspect {

    @Autowired
    private HisHttpsLogRepository hisHttpsLogRepository;

    @Autowired
    private TransLogRepository transLogRepository;

    @Autowired
    private TransactionRepository transactionRepository;



    @Around(value = "@annotation(com.cube.kiosk.modules.anno.HisLog)")
    public Object handlerController(ProceedingJoinPoint proceedingJoinPoint){
       HisHttpsLog hisHttpsLog = new HisHttpsLog();
       Gson gson = new Gson();
        Object proceed = null;
        try {
            proceed = proceedingJoinPoint.proceed();
            Object[] args = proceedingJoinPoint.getArgs();
            for (int i = 0; i < args.length; i++) {
                if(i==0){
                    String param = StringEscapeUtils.unescapeJava((String) args[i]);
                    hisHttpsLog.setParam(param);
                }

                if(i==1){
                    String method = StringEscapeUtils.unescapeJava((String) args[i]);
                    hisHttpsLog.setNote(method);
                }
            }
            hisHttpsLog.setCreateTime(new Date());
            String result = StringEscapeUtils.unescapeJava((String) proceed);
            hisHttpsLog.setResult(result);
            String ip = IpUtil.getRemoteAddr();
            hisHttpsLog.setIp(ip);


        }
        catch (Throwable throwable) {

            hisHttpsLog.setResult(throwable.getMessage());
        }
        hisHttpsLogRepository.save(hisHttpsLog);

       return proceed;
    }


    @Around(value = "@annotation(com.cube.kiosk.modules.anno.BankLog)")
    public Object bankLog(ProceedingJoinPoint proceedingJoinPoint){
        TransLogDO bankLog = new TransLogDO();
        Object proceed = null;
        try {
            proceed = proceedingJoinPoint.proceed();
            Object[] args = proceedingJoinPoint.getArgs();
            for (int i = 0; i < args.length; i++) {
                if(i==0){
                    String param = StringEscapeUtils.unescapeJava((String) args[i]);
                    bankLog.setParam(param);
                }

            }
            bankLog.setCreateTime(new Date());
            String result = StringEscapeUtils.unescapeJava((String) proceed);
            bankLog.setResult(result);
            String ip = IpUtil.getRemoteAddr();
            bankLog.setIp(ip);


        }
        catch (Throwable throwable) {

            bankLog.setResult(throwable.getMessage());
        }
        transLogRepository.save(bankLog);

        return proceed;
    }



}
