package com.cube.kiosk.modules.pay.controller;

import com.cube.core.global.anno.ResponseApi;
import com.cube.core.system.annotation.SysLog;
import com.cube.kiosk.modules.anno.Access;
import com.cube.kiosk.modules.common.ResponseData;
import com.cube.kiosk.modules.common.ResponseHisData;
import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.pay.anno.PayParamResolver;
import com.cube.kiosk.modules.pay.model.*;
import com.cube.kiosk.modules.pay.repository.NoticeRepository;
import com.cube.kiosk.modules.pay.service.PayService;
import com.cube.kiosk.modules.register.anno.RegisterResolver;
import com.cube.kiosk.modules.register.model.RegisterParam;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/pay")
@Api(value = "支付",tags = "支付")
public class PayController {

    @Autowired
    private PayService payService;

    @Autowired
    private NoticeRepository noticeRepository;

    @ApiOperation(httpMethod = "POST",value = "获取支付二维码")
    @RequestMapping("index")
    @SysLog("获取支付二维码")
    public ResponseData<TransactionData> index(@RequestBody PayParam payParam){
        final Object[] objects = new Object[1];
        payService.getQrCode(payParam, new ResultListener() {
            @Override
            public void success(Object object) {
                ResponseData<TransactionData> responseData = new ResponseData<>();
                responseData.setCode("200");
                responseData.setMessage("SUCCESS");
                responseData.setData((TransactionData) object);
                objects[0] = responseData;
            }

            @Override
            public void error(Object object) {
                ResponseData<TransactionData> responseData = new ResponseData<>();
                responseData.setCode("500");
                responseData.setMessage("ERROR");
                responseData.setData((TransactionData) object);
                objects[0] = responseData;
            }

            @Override
            public void exception(Object object) {
                ResponseData<String> responseData = new ResponseData<>();
                responseData.setCode("500");
                responseData.setMessage((String) object);
                responseData.setData(null);
                objects[0] = responseData;
            }
        });
        return (ResponseData<TransactionData>) objects[0];
    }


    @ApiOperation(httpMethod = "POST",value = "消费订单查询")
    @RequestMapping("query")
    @ResponseApi
    public Object  queryResult(@RequestBody String qrCodeUrl){

//        return  "HELLO";
        Gson gson = new Gson();
        TransactionData transactionData = payService.queryResult(qrCodeUrl);
        return transactionData;
    }

    @ApiOperation(httpMethod = "POST",value = "主扫交易通知")
    @RequestMapping("notice")
    @SysLog("交易回调")
    public void notice(@RequestBody Notice notice){
        noticeRepository.save(notice);
    }



    @ApiOperation(httpMethod = "POST",value = "充值")
    @RequestMapping("save")
    @SysLog("充值")
    public ResponseData<String> save(@RequestBody String merTradeNo){
        final Object[] objects = new Object[1];
        payService.save(merTradeNo, new ResultListener() {
            @Override
            public void success(Object object) {
                objects[0] = object;
            }

            @Override
            public void error(Object object) {
                objects[0] = object;
            }

            @Override
            public void exception(Object object) {
                objects[0] = object;
            }
        });
        return (ResponseData<String>) objects[0];
    }


    @ApiIgnore
    @RequestMapping("hospitalized")
    @SysLog("住院预交金充值")
    public ResponseData<String> saveHospitalized(@RequestBody String tradNo){
        final Object[] objects = new Object[1];
        payService.saveHospitalized(tradNo, new ResultListener() {
            @Override
            public void success(Object object) {
                objects[0] = object;
            }

            @Override
            public void error(Object object) {
                objects[0] = object;
            }

            @Override
            public void exception(Object object) {
                objects[0] = object;
            }
        });
        return (ResponseData<String>) objects[0];
    }


    @ApiOperation(httpMethod = "POST",value = "住院充值")
    @RequestMapping("saveHos")
    @ResponseApi
    public Object saveHos(@RequestBody String tradNo){
     String result = payService.saveHos(tradNo);
     Gson gson = new Gson();
        ResponseHisData<Object> responseHisData = gson.fromJson(result,ResponseHisData.class);

        if(responseHisData.getCode()==1||responseHisData.getCode()==2){
            throw  new RuntimeException((String) responseHisData.getResponseData());
        }
        Map<String,Object> map = new HashMap<>();
        map = (Map<String, Object>) responseHisData.getResponseData();
        PayResultVO payResultVO = new PayResultVO();
        payResultVO.setHisSerialNumber(MapUtils.getString(map,"hisSerialNumber"));
        payResultVO.setHisPayDate(MapUtils.getString(map,"hisPayDate"));
        payResultVO.setBalance(MapUtils.getString(map,"balance"));
        return payResultVO;
    }
}
