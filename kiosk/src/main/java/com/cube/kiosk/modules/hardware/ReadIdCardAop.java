package com.cube.kiosk.modules.hardware;


import com.cube.common.utils.IpUtil;
import com.cube.kiosk.modules.common.constant.MySocket;
import com.cube.kiosk.modules.hardware.entity.HardWareRecordDO;
import com.cube.kiosk.modules.hardware.repository.HardWareRecordRepository;
import com.cube.kiosk.socket.SocketUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.Socket;
import java.util.Date;

/**
 * @author YYF
 */
@Component
@Aspect
public class ReadIdCardAop {


    @Autowired
    private HardWareRecordRepository hardWareRecordRepository;


    private String inPutParam = "<invoke name=\"SHENFENZHENG\">\n" +
            "<arguments></arguments>\n" +
            "</invoke>";

    @Around(value = "@annotation(com.cube.kiosk.modules.hardware.ReadIdCard)")
    public Object doBefore(ProceedingJoinPoint proceedingJoinPoint){
        String ip = IpUtil.getRemoteAddr();
        HardWareRecordDO hardWareRecordDO = new HardWareRecordDO();
        hardWareRecordDO.setCreateTime(new Date());
        hardWareRecordDO.setParam(inPutParam);
        hardWareRecordDO.setIp(ip);
        Object object = null;
        try{
            Socket socket = SocketUtils.sendMessage(ip, MySocket.SOCKET_PORT,inPutParam);
            String result = SocketUtils.reciveMessage(socket);
            
        }catch (Exception e){

        }finally {
            return object;
        }
    }
}
