package com.cube.kiosk.socket;

import com.cube.kiosk.modules.hardware.utils.StringUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * socket工具
 * 用于调用自助机硬件
 */
public class SocketUtils {

    //发送
    public static Socket sendMessage(String host,int port,String message) throws IOException {
        //与服务端建立连接
        Socket socket = new Socket(host, port);
        socket.setOOBInline(true);
        //建立连接后获取输出流
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        outputStream.write(message.getBytes());
        return socket;
    }

    //接受
    public static String reciveMessage(Socket socket) throws IOException {
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        String content = "";
        while (content.indexOf("/return")<0){


            byte[] buff = new byte[1024];
            inputStream.read(buff);
            String buffer = new String(buff, "utf-8");
            content += buffer;
        }
         content = StringUtil.trimnull(content);
        return content;
    }
}
