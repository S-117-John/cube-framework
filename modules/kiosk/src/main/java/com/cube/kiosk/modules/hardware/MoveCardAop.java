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

/**
 * 移动卡
 * @author 李晋
 */
@Component
@Aspect
@Order(3)
public class MoveCardAop {


    @Autowired
    private HardWareRecordRepository hardWareRecordRepository;

    private String inPutParam = "<invoke name=\"READCARDMOVECARDTORF\">\n" +
            "<arguments>\n" +
            "</arguments>\n" +
            "</invoke>";


    @Around(value = "@annotation(com.cube.kiosk.modules.hardware.MoveCard)")
    public Object doBefore(ProceedingJoinPoint proceedingJoinPoint){
        ResponseData responseData = ResponseDatabase.newResponseData();
        Object object = null;
        String ip = IpUtil.getRemoteAddr();
        HardWareRecordDO hardWareRecordDO = new HardWareRecordDO();
        hardWareRecordDO.setCreateTime(new Date());
        hardWareRecordDO.setParam(inPutParam);
        hardWareRecordDO.setIp(ip);
        try {
            Socket socket = SocketUtils.sendMessage(ip, MySocket.SOCKET_PORT,inPutParam);
            String result = SocketUtils.reciveMessage(socket);
            hardWareRecordDO.setResult(result);
            if(result.indexOf("SUCCESS")>0){
                object = proceedingJoinPoint.proceed();
            }else{

                responseData.setCode("500");
                responseData.setData(null);
                responseData.setType("MoveCard");
                responseData.setMessage("移动卡失败");
                object = responseData;
            }
        } catch (Throwable throwable) {

            responseData.setCode("500");
            responseData.setData(null);
            responseData.setType("MoveCard");
            responseData.setMessage(throwable.getMessage());
            hardWareRecordDO.setResult(throwable.getMessage());
            object = responseData;
        }
        hardWareRecordRepository.save(hardWareRecordDO);
        return object;
    }
}
