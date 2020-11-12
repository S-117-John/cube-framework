package com.cube.kiosk.modules.patient.aspect;

import com.cube.common.utils.IpUtil;
import com.cube.kiosk.modules.hardware.entity.HardWareRecordDO;
import com.cube.kiosk.modules.patient.model.PatientParam;
import com.cube.kiosk.modules.register.model.RegisterParam;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Aspect
public class PatientParamAspect {

    @Around(value = "@annotation(com.cube.kiosk.modules.patient.anno.PatientParamResolver)")
    public Object around(ProceedingJoinPoint joinPoint){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String param = request.getParameter("param");
        String ip = IpUtil.getRemoteAddr(joinPoint);
        PatientParam patientParam = new PatientParam();
        String a = param.substring(0,6);
        String b = param.substring(7,10);
        String c = param.substring(11,12);
        String d = param.substring(13,14);
        String e = param.substring(15,param.length());
        patientParam.setCardNo(a+b+c+d+e);
        Object[] args = joinPoint.getArgs();
        args[0] = patientParam;
        Object object = null;
        try {
            object = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return object;
    }

    public static void main(String[] args) {
        String param = "acc44509240804006263646566676869";
        String a = param.substring(0,6);
        String b = param.substring(7,10);
        String c = param.substring(11,12);
        String d = param.substring(13,14);
        String e = param.substring(15,param.length());
        System.out.println(a+b+c+d+e);
    }
}
