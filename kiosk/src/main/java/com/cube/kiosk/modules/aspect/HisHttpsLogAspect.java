package com.cube.kiosk.modules.aspect;

import com.cube.kiosk.modules.log.entity.HisHttpsLog;
import com.cube.kiosk.modules.log.entity.TransLogDO;
import com.cube.kiosk.modules.log.repository.HisHttpsLogRepository;
import com.cube.kiosk.modules.log.repository.TransLogRepository;
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
    private TransLogRepository transLogRepository;

    @AfterReturning(value = "execution(* com.cube.kiosk.modules.common.utils.HttpsRestTemplate.postForString(..))",returning = "object")
    public void doAfter(JoinPoint joinPoint, Object object){
       try{
           Object[] args = joinPoint.getArgs();
           String param = args[0].toString();
           String method = args[1].toString();
           TransLogDO transLogDO = new TransLogDO();
           transLogDO.setCreateTime(new Date());
           transLogDO.setMethod(method);
           transLogDO.setParam(param);
           transLogDO.setResult((String) object);
           transLogRepository.save(transLogDO);
       }catch (Exception e){

       }
    }
}
