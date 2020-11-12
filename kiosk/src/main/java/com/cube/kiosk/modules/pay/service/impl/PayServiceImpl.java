package com.cube.kiosk.modules.pay.service.impl;


import com.cube.common.https.SSLClient;
import com.cube.common.utils.SnowflakeIdWorker;
import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.common.utils.HttpsRestTemplate;
import com.cube.kiosk.modules.common.utils.RestTemplate;
import com.cube.kiosk.modules.pay.model.PayParam;
import com.cube.kiosk.modules.pay.model.TransactionData;
import com.cube.kiosk.modules.pay.service.PayService;
import com.cube.kiosk.modules.pay.utils.IdWorker;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
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



    @Value("${app-pay.mid}")
    private String mid;

    @Autowired
    private HttpsRestTemplate httpsRestTemplate;


    @Autowired
    private RestTemplate restTemplate;


    public void doPost(PayParam payParam, ResultListener linstener) {
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

            requestJson.put("txnAmt", payParam.getTxnAmt());
            String a = requestJson.toString();
            String b = a.substring(1,a.length()-1);
//            String result = sslClient.doPost("https://"+ip+":"+port+"/comlink-interface-abc-forward/comlink/pay", b, charset);
//            ResponseDataPay responseDataPay = gson.fromJson(result, new TypeToken<ResponseDataPay>(){}.getType());
//            if("00".equals(responseDataPay.getRespCode())){
//                linstener.success(payParam);
//            }else {
//                linstener.error(payParam);
//            }

        } catch (Exception e) {
            linstener.exception(e.getMessage());
        }


    }

    @Override
    public void getQrCode(PayParam payParam, ResultListener linstener) {
        try{
            TransactionData transactionData = new TransactionData();
            transactionData.setPosNo(payParam.getPosNo());
            transactionData.setTranType("F");
            transactionData.setTxnAmt(payParam.getTxnAmt());
            transactionData.setTraceNo(payParam.getTraceNo());
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
            long id = idWorker.nextId();
            transactionData.setMerTradeNo(id+"");
            transactionData.setMid(mid);
            transactionData.setTid(payParam.getTid());
            Gson gson = new Gson();
            String transParam = gson.toJson(transactionData);
            String result = restTemplate.doPostBankApi(transParam,"");
            transactionData = gson.fromJson(result,TransactionData.class);
            if("00".equals(transactionData.getRespCode())){
                linstener.success(transactionData.getScanCode());
            }else {
                linstener.error(transactionData.getRespMsg());
            }
        }catch (Exception exception){
            linstener.exception(exception.getMessage());
        }

    }

    @Override
    public void queryResult(PayParam payParam, ResultListener linstener) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            Gson gson = new Gson();
            TransactionData transactionData = new TransactionData();

            String transParam = gson.toJson(transactionData);
            Callable<String> task = new Callable<String>() {
                @Override
                public String call() throws Exception {

                    String result = "";
                    while (true){
                        result = restTemplate.doPostBankApi(transParam,"");
                        TransactionData transactionData = gson.fromJson(result,TransactionData.class);
                        if("00".equals(transactionData.getRespCode())){
                            return result;
                        }
                    }
                }
            };


            Future<String> future = executorService.submit(task);
            String result = future.get(10, TimeUnit.SECONDS);
            TransactionData transactionResultData = gson.fromJson(result,TransactionData.class);

            if("00".equals(transactionResultData.getRespCode())){
                linstener.success(payParam);
            }else {
                linstener.error(payParam);
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
