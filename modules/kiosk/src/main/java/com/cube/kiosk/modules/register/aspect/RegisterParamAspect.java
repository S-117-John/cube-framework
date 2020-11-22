package com.cube.kiosk.modules.register.aspect;

import com.cube.common.utils.IpUtil;
import com.cube.kiosk.modules.hardware.entity.HardWareRecordDO;
import com.cube.kiosk.modules.hardware.repository.HardWareRecordRepository;
import com.cube.kiosk.modules.register.model.RegisterParam;
import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Aspect
public class RegisterParamAspect {



    @Autowired
    private HardWareRecordRepository hardWareRecordRepository;



    @Around(value = "@annotation(com.cube.kiosk.modules.register.anno.RegisterCard)")
    public Object registerCard(ProceedingJoinPoint joinPoint){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String param = request.getParameter("param");
        String ip = IpUtil.getRemoteAddr();
        Gson gson = new Gson();
        RegisterParam registerParam = gson.fromJson(param,RegisterParam.class);

        Object[] args = joinPoint.getArgs();
        args[0] = registerParam;
        Object object = null;
        try {
            object = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return object;
    }
}
