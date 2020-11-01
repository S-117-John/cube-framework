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
 * 检测是否有卡
 * @author 李晋
 */
@Component
@Aspect
@Order(2)
public class CheckCardAop {

    @Autowired
    private HardWareRecordRepository hardWareRecordRepository;

    private String inPutParam = "<invoke name=\"READCARDTESTINSERTCARD\">\n" +
            "<arguments>\n" +
            "</arguments>\n" +
            "</invoke>";

    @Around(value = "@annotation(com.cube.kiosk.modules.hardware.CheckCard)")
    public Object doBefore(ProceedingJoinPoint proceedingJoinPoint){
        Object object = null;
        String ip = IpUtil.getRemoteAddr(proceedingJoinPoint);
        ResponseData responseData = ResponseDatabase.newResponseData();
        HardWareRecordDO hardWareRecordDO = new HardWareRecordDO();
        hardWareRecordDO.setCreateTime(new Date());
        hardWareRecordDO.setParam(inPutParam);
        hardWareRecordDO.setIp(ip);
        try {
            Socket socket = SocketUtils.sendMessage(ip, MySocket.SOCKET_PORT,inPutParam);
            String result = SocketUtils.reciveMessage(socket);
            hardWareRecordDO.setResult(result);
            //1:有卡,0:无卡
            if(result.indexOf("1")>0){
                object = proceedingJoinPoint.proceed();
            }else{

                responseData.setCode("500");
                responseData.setData(null);
                responseData.setType("CheckCard");
                responseData.setMessage("请插入您的就诊卡");
                object = responseData;
            }
        } catch (Throwable throwable) {

            responseData.setCode("500");
            responseData.setData(null);
            responseData.setType("CheckCard");
            responseData.setMessage(throwable.getMessage());
            object = responseData;
            hardWareRecordDO.setResult(throwable.getMessage());
        }

        hardWareRecordRepository.save(hardWareRecordDO);
        return object;
    }
}
