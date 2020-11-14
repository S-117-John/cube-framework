package com.cube.kiosk.modules.pay.aspect;

import com.cube.common.utils.IpUtil;
import com.cube.kiosk.modules.pay.model.PayParam;
import com.cube.kiosk.modules.security.model.HardWareConfigDO;
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
        HardWareConfigDO hardWareConfigDO = hardWareRepository.findByIp(ip);
        if(hardWareConfigDO !=null){
            payParam.setPosNo(hardWareConfigDO.getPosNo());
            payParam.setTid(hardWareConfigDO.getTid());
            payParam.setTxnAmt(payParam.getMoney());
            NumberFormat nf = NumberFormat.getInstance();
            //设置是否使用分组
            nf.setGroupingUsed(false);
            //设置最大整数位数
            nf.setMaximumIntegerDigits(6);
            //设置最小整数位数
            nf.setMinimumIntegerDigits(6);
            payParam.setTraceNo(nf.format(hardWareConfigDO.getTraceNo()));

            hardWareConfigDO.setTraceNo(hardWareConfigDO.getTraceNo()+1);
            hardWareRepository.save(hardWareConfigDO);
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


//    @Around(value = "execution(* com.cube.kiosk.modules.pay.controller.PayController.queryResult(..))")
    public Object transQuery(ProceedingJoinPoint joinPoint){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String param = request.getParameter("param");
        String ip = IpUtil.getRemoteAddr(joinPoint);

        Gson gson = new Gson();
        PayParam payParam = gson.fromJson(param,PayParam.class);
        HardWareConfigDO hardWareConfigDO = hardWareRepository.findByIp(ip);
        if(hardWareConfigDO !=null){
            payParam.setPosNo(hardWareConfigDO.getPosNo());
            payParam.setTid(hardWareConfigDO.getTid());
            NumberFormat nf = NumberFormat.getInstance();
            //设置是否使用分组
            nf.setGroupingUsed(false);
            //设置最大整数位数
            nf.setMaximumIntegerDigits(6);
            //设置最小整数位数
            nf.setMinimumIntegerDigits(6);
            payParam.setTraceNo(nf.format(hardWareConfigDO.getTraceNo()));

            hardWareConfigDO.setTraceNo(hardWareConfigDO.getTraceNo()+1);
            hardWareRepository.save(hardWareConfigDO);
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
