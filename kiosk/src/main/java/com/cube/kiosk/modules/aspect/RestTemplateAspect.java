package com.cube.kiosk.modules.aspect;

import com.cube.kiosk.modules.log.entity.HisHttpsLog;
import com.cube.kiosk.modules.log.entity.TransLogDO;
import com.cube.kiosk.modules.log.repository.HisHttpsLogRepository;
import com.cube.kiosk.modules.log.repository.TransLogRepository;
import com.cube.kiosk.modules.pay.model.TransactionData;
import com.cube.kiosk.modules.pay.repository.TransactionRepository;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
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

    @AfterReturning(value = "execution(* com.cube.kiosk.modules.common.utils.RestTemplate.doPostHisSaveApi(..))",returning = "object")
    public void doAfterSave(JoinPoint joinPoint, Object object){
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

    @AfterReturning(value = "execution(* com.cube.kiosk.modules.common.utils.RestTemplate.doPostBankApi(..))",returning = "object")
    public void doPostBankApi(JoinPoint joinPoint, Object object){
        TransLogDO transLogDO = new TransLogDO();
        try {
            Object[] objects = joinPoint.getArgs();
            String param = "";
            for (Object o : objects) {
                if(o instanceof String){
                    param = o.toString();
                }
            }
            transLogDO.setCreateTime(new Date());
            transLogDO.setParam(param);
            if(object!=null){
                transLogDO.setResult(object.toString());
            }else {
                transLogDO.setResult("返回值为空");
            }
            transLogRepository.save(transLogDO);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
