package com.cube.kiosk.modules.pay.service.impl;


import com.cube.common.https.SSLClient;
import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.pay.model.RequestDataPay;
import com.cube.kiosk.modules.pay.model.ResponseDataPay;
import com.cube.kiosk.modules.pay.service.PayService;
import com.cube.kiosk.modules.pay.utils.IdWorker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author LMZ
 */
@Service
public class PayServiceImpl implements PayService {

    @Value("${app-pay.ip}")
    private String ip;

    @Value("${app-pay.port}")
    private String port;

    @Value("${app-pay.mid}")
    private String mid;

    @Value("${app-pay.tid1}")
    private String tid1;

    @Value("${app-pay.tid2}")
    private String tid2;

    @Value("${app-pay.tid3}")
    private String tid3;

    @Value("${Hardware.ip1}")
    private String hardwareIp1;

    @Value("${Hardware.ip2}")
    private String hardwareIp2;

    @Value("${Hardware.ip3}")
    private String hardwareIp3;



    @Override
    public void doPost(RequestDataPay requestDataPay, ResultListener linstener) {
        String charset = "utf-8";
        SSLClient sslClient = null;
        try {
            sslClient = new SSLClient();
            Map<String,Object> requestJson = new HashMap<>(16);
            Gson gson = new Gson();
            requestJson.put("posNo","");
            requestJson.put("tranType","F");
            requestJson.put("merTradeNo", IdWorker.getInstance().nextId()+"");
            requestJson.put("mid",mid);
            if(hardwareIp1.equals(requestDataPay.getRequestIp())){
                requestJson.put("tid",tid1);
            }
            if(hardwareIp2.equals(requestDataPay.getRequestIp())){
                requestJson.put("tid",tid2);
            }
            if(hardwareIp3.equals(requestDataPay.getRequestIp())){
                requestJson.put("tid",tid3);
            }
            requestJson.put("txnAmt",requestDataPay.getTxnAmt());
            String a = requestJson.toString();
            String b = a.substring(1,a.length()-1);
            String result = sslClient.doPost("https://"+ip+":"+port+"/comlink-interface-abc-forward/comlink/pay", b, charset);
            ResponseDataPay responseDataPay = gson.fromJson(result, new TypeToken<ResponseDataPay>(){}.getType());
            if("00".equals(responseDataPay.getRespCode())){
                linstener.success(requestDataPay);
            }else {
                linstener.error(requestDataPay);
            }

        } catch (Exception e) {
            linstener.exception(e.getMessage());
        }


    }

    @Override
    public void queryResult(RequestDataPay requestDataPay, ResultListener linstener) {
        String charset = "utf-8";
        SSLClient sslClient = null;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            sslClient = new SSLClient();
            Map<String,Object> requestJson = new HashMap<>(16);
            Gson gson = new Gson();
            requestJson.put("posNo","");
            requestJson.put("tranType","G");
            requestJson.put("merTradeNo", requestDataPay.getMerTradeNo());
            requestJson.put("tradeNo", "");
            requestJson.put("mid",mid);
            if(hardwareIp1.equals(requestDataPay.getRequestIp())){
                requestJson.put("tid",tid1);
            }
            if(hardwareIp2.equals(requestDataPay.getRequestIp())){
                requestJson.put("tid",tid2);
            }
            if(hardwareIp3.equals(requestDataPay.getRequestIp())){
                requestJson.put("tid",tid3);
            }
            requestJson.put("txnAmt",requestDataPay.getTxnAmt());
            String a = requestJson.toString();
            String b = a.substring(1,a.length()-1);
            SSLClient finalSslClient = sslClient;
            Callable<String> task = new Callable<String>() {
                @Override
                public String call() throws Exception {

                    String result = "";
                    while (true){
                        result = finalSslClient.doPost("https://"+ip+":"+port+"/comlink-interface-abc-forward/comlink/pay", b, charset);
                        ResponseDataPay responseDataPay = gson.fromJson(result, new TypeToken<ResponseDataPay>(){}.getType());
                        if("00".equals(responseDataPay.getRespCode())){
                            return result;
                        }
                    }
                }
            };


            Future<String> future = executorService.submit(task);
            String result = future.get(10, TimeUnit.SECONDS);
            ResponseDataPay responseDataPay = gson.fromJson(result, new TypeToken<ResponseDataPay>(){}.getType());

            if("00".equals(responseDataPay.getRespCode())){
                linstener.success(requestDataPay);
            }else {
                linstener.error(requestDataPay);
            }

        } catch (Exception e) {
            linstener.exception(e.getMessage());
        }finally {
            executorService.shutdown();
        }

    }

    public static void main(String[] args) {
        Callable<String> task = new Callable<String>() {
            @Override
            public String call() throws Exception {

                Thread.sleep(10000);
                return "success";
            }
        };
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<String> future = executorService.submit(task);

        String result = null;
        try {
            result = future.get(15, TimeUnit.SECONDS);
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

     }
}
