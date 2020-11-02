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
        patientParam.setCardNo(param);
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

}
