package com.cube.kiosk.modules.recharge.service.impl;


import com.cube.common.https.HttpsUtils;
import com.cube.kiosk.modules.common.ResponseBank;
import com.cube.kiosk.modules.common.ResultData;
import com.cube.kiosk.modules.common.model.PatientInfo;
import com.cube.kiosk.modules.common.utils.RestTemplateUtil;
import com.cube.kiosk.modules.pay.entity.TransactionRecordDO;
import com.cube.kiosk.modules.recharge.linstener.RechargeLinstener;
import com.cube.kiosk.modules.recharge.service.RechargeService;
import com.cube.kiosk.modules.recharge.service.TransactionRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李晋
 */
@Service
public class RechargeServiceImpl implements RechargeService {

    @Value("${neofaith.url}")
    private String url;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public String getQrCode(Double money) throws Exception {

        String result = HttpsUtils.doPost("http://127.0.0.1:8090/comlink-interface-abc-forward/comlink/pay", new HashMap<>(16));
        return result;
    }

    /**
     * 查询消费订单-就诊卡充值
     * @param tradeNo ：支付订单号
     * @return
     * @throws Exception
     */
    @Override
    public void getConsumer0rderCard(String tradeNo, RechargeLinstener linstener){
        System.out.println("接口调用getConsumer0rder");
        Map<String,Object> map = new HashMap<>(16);
        map.put("tradeNo", tradeNo);//支付订单号
        try{
            String result = HttpsUtils.doPost("http://127.0.0.1:8090/comlink-interface-abc-forward/comlink/pay", map);
            //Gson gson = new Gson();
           // ResultData<ResponseBank> responseBankResultData = gson.fromJson(result, new TypeToken<ResponseBank>(){}.getType());
            //if("00".equals(responseBankResultData.getResponseData().getRespCode())){
            if("00".equals("01")){
                linstener.success("");
            }else{
                linstener.error("");
            }
        }catch (Exception e){
            linstener.exception(e.getMessage());
        }
    }

    /**
     * 查询消费订单-住院预交金
     * @param tradeNo ：支付订单号
     * @return
     * @throws Exception
     */
    @Override
    public void getConsumer0rderPaymen(String tradeNo, RechargeLinstener linstener) {
        System.out.println("接口调用getConsumer0rder");
        Map<String,Object> map = new HashMap<>(16);
        map.put("tradeNo", tradeNo);//支付订单号
        try{
            String result = HttpsUtils.doPost("http://127.0.0.1:8090/comlink-interface-abc-forward/comlink/pay", map);
            //Gson gson = new Gson();
            // ResultData<ResponseBank> responseBankResultData = gson.fromJson(result, new TypeToken<ResponseBank>(){}.getType());
            //if("00".equals(responseBankResultData.getResponseData().getRespCode())){
            if("00".equals("01")){
                linstener.success("");
            }else{
                linstener.error("");
            }
        }catch (Exception e){
            linstener.exception(e.getMessage());
        }
    }

    /**
     * 关闭订单
     * @param tradeNo：支付订单号
     * @param linstener
     */
    @Override
    public void closeOrder(String tradeNo, RechargeLinstener linstener) {
        Map<String,Object> map = new HashMap<>(16);
        map.put("tradeNo", tradeNo);//支付订单号
        try{
            String result = HttpsUtils.doPost("http://127.0.0.1:8090/comlink-interface-abc-forward/comlink/pay", map);
        }catch (Exception e){
            linstener.exception(e.getMessage());
        }
    }

    /**
     * 申请退款
     * @param tradeNo:支付订单号
     * @param vfTradeNo
     * @param linstener
     */
    @Override
    public void applyForRefund(String tradeNo, String vfTradeNo, RechargeLinstener linstener) {
        Map<String,Object> map = new HashMap<>(16);
        map.put("tradeNo", tradeNo);//支付订单号
        map.put("vfTradeNo", vfTradeNo);
        try{
            String result = HttpsUtils.doPost("http://127.0.0.1:8090/comlink-interface-abc-forward/comlink/pay", map);
        }catch (Exception e){
            linstener.exception(e.getMessage());
        }
    }

    /**
     * 查询退款
     * @param vfTradeNo
     * @param linstener
     */
    @Override
    public void getApplyForRefund(String vfTradeNo, RechargeLinstener linstener) {
        Map<String,Object> map = new HashMap<>(16);
        map.put("vfTradeNo", vfTradeNo);
        try{
            String result = HttpsUtils.doPost("http://127.0.0.1:8090/comlink-interface-abc-forward/comlink/pay", map);
        }catch (Exception e){
            linstener.exception(e.getMessage());
        }
    }

    /**
     * 查询患者基本信息
     *
     * @param cardId
     * @return
     */
    @Override
    public String getPatientnameInfo(String cardId) throws Exception {

        Map<String,Object> map = new HashMap<>(16);
        map.put("cardId",cardId);
        String result = HttpsUtils.doPost(url+"getPatientnameInfo", map);
        return result;
    }

    /**
     * 在线建卡
     * @param patientInfo
     * @param linstener
     */
    @Override
    public void cardIssuers(PatientInfo patientInfo, RechargeLinstener linstener) {
        try {
            Map<String,Object> map =RestTemplateUtil.beanToMap(patientInfo);
            String result = HttpsUtils.doPost(url+"cardIssuers", map);
            Gson gson = new Gson();
            ResultData<PatientInfo> patientInfoResultData = gson.fromJson(result, new TypeToken<ResultData<PatientInfo>>(){}.getType());
            if(patientInfoResultData.getCode()==0){
                linstener.success("建卡成功");
            }else {
                linstener.error("建卡失败,请联系管理员");
            }
        }catch (Exception e){
            linstener.exception(e.getMessage());
        }
    }

    /**
     * 查询就在卡余额
     *
     * @param patientName
     * @param identitycard
     * @param linstener
     * @return
     * @throws Exception
     */
    @Override
    public void getBalance(String patientName, String identitycard, RechargeLinstener linstener){
       try {
           Map<String,Object> map = new HashMap<>(16);
           map.put("identitycard", identitycard);//就诊卡号
           map.put("patientName", patientName);//患者姓名
           String result = HttpsUtils.doPost(url+"getBalance", map);
           Gson gson = new Gson();
           ResultData<PatientInfo> patientInfoResultData = gson.fromJson(result, new TypeToken<ResultData<PatientInfo>>(){}.getType());
           if(patientInfoResultData.getCode()==0){
               linstener.success(patientInfoResultData.getResponseData().getBalance());
           }else {
               linstener.error("查询余额失败");
           }
       }catch (Exception e){
           linstener.exception(e.getMessage());
       }
    }

    /**
     * 就诊卡充值
     * @param patientInfo 患者基础信息
     * @param responseBank 支付基础信息
     * @param linstener
     */
    @Override
    public void payMedicalCard(PatientInfo patientInfo, ResponseBank responseBank, RechargeLinstener linstener) {

        try {
            Map<String,Object> map =RestTemplateUtil.beanToMap(patientInfo);
            map.put("money", responseBank.getTxnAmt());//充值金额
            map.put("modeType", responseBank.getPayType());//充值方式
            //支付成功流水号-支付订单号、后期待确定
            map.put("serialNumber", responseBank.getTradeNo());
            map.put("payDate", responseBank.getTxnTime());//支付时间
            String result = HttpsUtils.doPost(url+"payMedicalCard", map);
            Gson gson = new Gson();
            ResultData<PatientInfo> patientInfoResultData = gson.fromJson(result, new TypeToken<ResultData<PatientInfo>>(){}.getType());
            if(patientInfoResultData.getCode()==0){
                //支付成功返回余额
                linstener.success(patientInfoResultData.getResponseData().getBalance());
            }else {
                linstener.error("HIS卡充值失败！请联系管理员");
            }

            //消费订单HIS状态记录
            TransactionRecordDO tRDO = new TransactionRecordDO();
            tRDO.setId("");//支付id
            tRDO.setHisCode(patientInfoResultData.getCode()); //his响应状态0 成功,1 失败,2 结果为空
            tRDO.setHisMsg("");//his响应结果描述
            tRDO.setHisSerialNumber(patientInfoResultData.getResponseData().getHisSerialNumber());//his交易流水号
            tRDO.setHisDate(new Date());//his交易时间
            transactionRepository.save(tRDO);//记录日志
        }catch (Exception e){
            linstener.exception(e.getMessage());
        }
    }

    /**
     *查询住院患者基本新
     * @param inHosid:住院号
     * @param linstener
     */
    @Override
    public void getPatientInfo(String inHosid, RechargeLinstener linstener) {
        try {
            Map<String,Object> map = new HashMap<>(16);
            map.put("inHosid", inHosid);//住院号
            String result = HttpsUtils.doPost(url+"getPatientInfo", map);
            Gson gson = new Gson();
            ResultData<PatientInfo> patientInfoResultData = gson.fromJson(result, new TypeToken<ResultData<PatientInfo>>(){}.getType());
            if(patientInfoResultData.getCode()==0){
                linstener.success(patientInfoResultData.getResponseData().getBalance());
            }else {
                linstener.error("查询住院患者失败");
            }
        }catch (Exception e){
            linstener.exception(e.getMessage());
        }
    }

    /**
     * 查询住院预交金余额
     * @param inHosid:住院号
     * @param linstener
     */
    @Override
    public void paymenPrepaidBalance(String inHosid, RechargeLinstener linstener) {
        try {
            Map<String,Object> map = new HashMap<>(16);
            map.put("inHosid", inHosid);//住院号
            String result = HttpsUtils.doPost(url+"paymenPrepaidBalance", map);
            Gson gson = new Gson();
            ResultData<Double> patientInfoResultData = gson.fromJson(result, new TypeToken<ResultData<Double>>(){}.getType());
            if(patientInfoResultData.getCode()==0){
                linstener.success(patientInfoResultData.getResponseData());
            }else {
                linstener.error("查询预交金余额失败");
            }
        }catch (Exception e){
            linstener.exception(e.getMessage());
        }
    }

    /**
     * 住院预交金充值
     * @param patientInfo
     * @param responseBank
     * @param linstener
     */
    @Override
    public void paymenPrepaid(PatientInfo patientInfo, ResponseBank responseBank, RechargeLinstener linstener) {
        try {
            Map<String,Object> map =RestTemplateUtil.beanToMap(patientInfo);
            map.put("money", responseBank.getTxnAmt());//充值金额
            map.put("modeType", responseBank.getPayType());//充值方式
            //支付成功流水号-支付订单号、后期待确定
            map.put("serialNumber", responseBank.getTradeNo());
            map.put("payDate", responseBank.getTxnTime());//支付时间
            String result = HttpsUtils.doPost(url+"paymenPrepaid", map);
            Gson gson = new Gson();
            ResultData<PatientInfo> patientInfoResultData = gson.fromJson(result, new TypeToken<ResultData<PatientInfo>>(){}.getType());
            if(patientInfoResultData.getCode()==0){
                //支付成功返回余额
                linstener.success(patientInfoResultData.getResponseData().getBalance());
            }else {
                linstener.error("HIS卡充值失败！请联系管理员");
            }

            ////记录支付日志-TRANSACTION_RECORD
            //消费订单HIS状态记录
            TransactionRecordDO tRDO = new TransactionRecordDO();
            tRDO.setId("");//支付id
            tRDO.setHisCode(patientInfoResultData.getCode()); //his响应状态0 成功,1 失败,2 结果为空
            tRDO.setHisMsg("");//his响应结果描述
            tRDO.setHisSerialNumber(patientInfoResultData.getResponseData().getHisSerialNumber());//his交易流水号
            tRDO.setHisDate(new Date());//his交易时间
            transactionRepository.save(tRDO);

        }catch (Exception e){
            linstener.exception(e.getMessage());
        }
    }

}
