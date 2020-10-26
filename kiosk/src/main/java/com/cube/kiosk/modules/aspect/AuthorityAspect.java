package com.cube.kiosk.modules.aspect;

import com.cube.common.utils.IpUtil;
import com.cube.kiosk.modules.security.model.HardWareDO;
import com.cube.kiosk.modules.security.service.HardWareRegisterService;
import com.sun.javafx.binding.StringFormatter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
public class AuthorityAspect {

    @Autowired
    private HardWareRegisterService hardWareRegisterService;

    @Around(value = "@annotation(com.cube.kiosk.modules.anno.Access)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint){
        String ip = IpUtil.getRemoteAddr(proceedingJoinPoint);
        Object object = String.format("ip:[%s]自助机未注册",ip);
       try{

           List<HardWareDO> hardWareList = hardWareRegisterService.getAllHardWare();
           for (HardWareDO hardWareDO : hardWareList) {
               if(ip.equalsIgnoreCase(hardWareDO.getIp())){
                   object = proceedingJoinPoint.proceed();
               }
           }
       } catch (Throwable throwable) {
           object = throwable.getMessage();
       }
       return object;
    }
}
