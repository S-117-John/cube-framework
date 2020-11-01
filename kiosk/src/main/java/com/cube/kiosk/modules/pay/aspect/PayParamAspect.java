package com.cube.kiosk.modules.pay.aspect;

import com.cube.common.utils.IpUtil;
import com.cube.kiosk.modules.hardware.entity.HardWareRecordDO;
import com.cube.kiosk.modules.hardware.repository.HardWareRecordRepository;
import com.cube.kiosk.modules.pay.model.PayParam;
import com.cube.kiosk.modules.register.RegisterParamResolver;
import com.cube.kiosk.modules.register.model.RegisterParam;
import com.cube.kiosk.modules.security.model.HardWareDO;
import com.cube.kiosk.modules.security.repository.HardWareRepository;
import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.util.Date;

@Component
@Aspect
public class PayParamAspect {

    @Autowired
    private HardWareRepository hardWareRepository;

    @Around(value = "@annotation(com.cube.kiosk.modules.pay.anno.PayParamResolver)")
    public Object around(ProceedingJoinPoint joinPoint){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String param = request.getParameter("param");
        String ip = IpUtil.getRemoteAddr(joinPoint);

        Gson gson = new Gson();
        PayParam payParam = gson.fromJson(param,PayParam.class);
        HardWareDO hardWareDO = hardWareRepository.findByIp(ip);
        if(hardWareDO!=null){
            payParam.setPosNo(hardWareDO.getPosNo());
            payParam.setTid(hardWareDO.getTid());
            NumberFormat nf = NumberFormat.getInstance();
            //设置是否使用分组
            nf.setGroupingUsed(false);
            //设置最大整数位数
            nf.setMaximumIntegerDigits(6);
            //设置最小整数位数
            nf.setMinimumIntegerDigits(6);
            payParam.setTraceNo(nf.format(hardWareDO.getTraceNo()));

            hardWareDO.setTraceNo(hardWareDO.getTraceNo()+1);
            hardWareRepository.save(hardWareDO);
        }
        Object[] args = joinPoint.getArgs();
        args[0] = payParam;
        Object object = null;
        try {
            object = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return object;
    }


}
