package com.cube.kiosk.modules.hardware;


import com.cube.common.utils.IpUtil;
import com.cube.kiosk.modules.common.ResponseData;
import com.cube.kiosk.modules.common.ResponseDatabase;

import com.cube.kiosk.modules.common.constant.MySocket;
import com.cube.kiosk.modules.hardware.entity.HardWareRecordDO;
import com.cube.kiosk.modules.hardware.repository.HardWareRecordRepository;
import com.cube.kiosk.socket.SocketUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;

import org.springframework.stereotype.Component;

import java.net.Socket;
import java.util.Date;

@Component
@Aspect
@Order(1)
public class AllowCardInAop {


    @Autowired
    private HardWareRecordRepository hardWareRecordRepository;

    private String inPutParam = "<invoke name=\"READCARDALLOWCARDIN\">" +
            "<arguments>" +
            "</arguments>" +
            "</invoke>";

    private String result = null;

    @Around(value = "@annotation(com.cube.kiosk.modules.hardware.AllowCardIn)")
    public Object doBefore(ProceedingJoinPoint proceedingJoinPoint){
        HardWareRecordDO hardWareRecordDO = new HardWareRecordDO();
        hardWareRecordDO.setCreateTime(new Date());
        hardWareRecordDO.setParam(inPutParam);
        Object object = null;
        try{
            String ip = IpUtil.getRemoteAddr();
            hardWareRecordDO.setIp(ip);
            Socket socket = SocketUtils.sendMessage(ip, MySocket.SOCKET_PORT,inPutParam);
            result = SocketUtils.reciveMessage(socket);
            hardWareRecordDO.setResult(result);
            if(result.indexOf("SUCCESS")>0){
                object = proceedingJoinPoint.proceed();
            }else{
                ResponseData responseData = ResponseDatabase.newResponseData();
                responseData.setCode("500");
                responseData.setData(result);
                responseData.setType("AllowCardIn");
                responseData.setMessage("允许进卡失败");
                object = responseData;
            }
        } catch (Throwable throwable) {
            ResponseData responseData = ResponseDatabase.newResponseData();
            responseData.setCode("500");
            responseData.setData(null);
            responseData.setType("AllowCardIn");
            responseData.setMessage("允许进卡失败:"+ throwable.getMessage());
            hardWareRecordDO.setResult(throwable.getMessage());
            object = responseData;
        }
        hardWareRecordRepository.save(hardWareRecordDO);
        return object;
    }


}
