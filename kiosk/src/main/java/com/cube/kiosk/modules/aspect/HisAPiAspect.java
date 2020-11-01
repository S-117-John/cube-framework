package com.cube.kiosk.modules.aspect;

import com.cube.kiosk.modules.log.entity.HisHttpsLog;
import com.cube.kiosk.modules.log.repository.HisHttpsLogRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@Aspect
public class HisAPiAspect {

    @Autowired
    private HisHttpsLogRepository hisHttpsLogRepository;

    @AfterReturning(value = "execution(* com.cube.kiosk.modules.common.utils.RestTemplate.doPostHisApi(..))",returning = "object")
    public void doAfter(JoinPoint joinPoint, Object object){
        HisHttpsLog hisHttpsLog = new HisHttpsLog();
        try {
            Object[] objects = joinPoint.getArgs();
            String param = "";
            for (Object o : objects) {
                if(o instanceof Map){
                    param = o.toString();
                }
            }
            hisHttpsLog.setCreateTime(new Date());
            hisHttpsLog.setParam(param);
            if(object!=null){
                hisHttpsLog.setResult(object.toString());
            }else {
                hisHttpsLog.setNote("返回值为空");
            }
            hisHttpsLogRepository.save(hisHttpsLog);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
