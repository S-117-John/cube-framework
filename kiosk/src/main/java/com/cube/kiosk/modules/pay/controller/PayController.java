package com.cube.kiosk.modules.pay.controller;

import com.cube.core.system.annotation.SysLog;
import com.cube.kiosk.modules.anno.Access;
import com.cube.kiosk.modules.common.ResponseData;
import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.pay.anno.PayParamResolver;
import com.cube.kiosk.modules.pay.model.PayParam;
import com.cube.kiosk.modules.pay.service.PayService;
import com.cube.kiosk.modules.register.anno.RegisterResolver;
import com.cube.kiosk.modules.register.model.RegisterParam;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pay")
@Api(value = "支付",tags = "支付")
public class PayController {

    @Autowired
    private PayService payService;

    @ApiOperation(httpMethod = "GET",value = "获取支付二维码")
    @RequestMapping("index")
    @SysLog("获取支付二维码")
    @Access
    @PayParamResolver
    public String index(PayParam payParam){
        payService.getQrCode(payParam, new ResultListener() {
            @Override
            public void success(Object object) {

            }

            @Override
            public void error(Object object) {

            }

            @Override
            public void exception(Object object) {

            }
        });
       return "abc";
    }
}
