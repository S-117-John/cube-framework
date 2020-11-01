package com.cube.kiosk.modules.aspect;

import com.cube.kiosk.modules.log.entity.HisHttpsLog;
import com.cube.kiosk.modules.log.repository.HisHttpsLogRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Aspect
public class HisHttpsLogAspect {

    @Autowired
    private HisHttpsLogRepository hisHttpsLogRepository;

    @AfterReturning(value = "execution(* com.cube.common.https.SSLClient.doPost(..))",returning = "object")
    public void doAfter(JoinPoint joinPoint, Object object){
       try{
           Object[] args = joinPoint.getArgs();
           String url = args[0].toString();
           String param = args[1].toString();
           HisHttpsLog hisHttpsLog = new HisHttpsLog();
           hisHttpsLog.setCreateTime(new Date());
           hisHttpsLog.setNote(url);
           hisHttpsLog.setParam(param);
           String result = (String) object;
           hisHttpsLog.setResult(result);
           hisHttpsLogRepository.save(hisHttpsLog);
       }catch (Exception e){

       }
    }
}
