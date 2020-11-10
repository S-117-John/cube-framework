package com.cube.kiosk.modules.aspect;

import com.cube.common.utils.IpUtil;
import com.cube.kiosk.modules.common.ResponseData;
import com.cube.kiosk.modules.security.model.HardWareConfigDO;
import com.cube.kiosk.modules.security.service.HardWareRegisterService;
import com.google.gson.Gson;
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
        ResponseData<String> responseData = new ResponseData<>();
        Gson gson = new Gson();
        String ip = IpUtil.getRemoteAddr(proceedingJoinPoint);
        responseData.setCode("500");
        responseData.setData(null);
        responseData.setMessage(String.format("ip:[%s]自助机未注册",ip));
        String result = gson.toJson(responseData);
        Object object = result;
       try{

           List<HardWareConfigDO> hardWareList = hardWareRegisterService.getAllHardWare();
           for (HardWareConfigDO hardWareConfigDO : hardWareList) {
               if(ip.equalsIgnoreCase(hardWareConfigDO.getIp())){
                   object = proceedingJoinPoint.proceed();
               }
           }
       } catch (Throwable throwable) {
           responseData.setCode("500");
           responseData.setData(null);
           responseData.setMessage(throwable.getMessage());
           result = gson.toJson(responseData);
           object = result;
       }
       return object;
    }
}
