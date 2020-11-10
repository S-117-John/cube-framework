package com.cube.kiosk.modules.register.controller;

import com.cube.core.system.annotation.SysLog;
import com.cube.kiosk.modules.anno.Access;
import com.cube.kiosk.modules.common.ResponseData;
import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.register.RegisterParamResolver;
import com.cube.kiosk.modules.register.anno.RegisterCard;
import com.cube.kiosk.modules.register.anno.RegisterResolver;
import com.cube.kiosk.modules.register.model.RegisterParam;
import com.cube.kiosk.modules.register.service.RegisterService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YYF
 */
@RestController
@RequestMapping("api/register")
@Api(value = "患者注册",tags = "患者注册")
public class RegisterController {

    @Autowired
    private RegisterService registerService;


    @ApiOperation(httpMethod = "GET",value = "读取身份证信息")
    @RequestMapping("index")
    @SysLog("读取身份证信息")
    @Access
    @RegisterResolver
    public String index(RegisterParam registerParam){
        Gson gson = new Gson();
        ResponseData<RegisterParam> responseData = new ResponseData<>();
        responseData.setCode("200");
        responseData.setData(registerParam);
        responseData.setMessage("success");
        return gson.toJson(responseData);
    }

    @ApiOperation(httpMethod = "POST",value = "读取身份证信息")
    @RequestMapping("")
    @SysLog("办卡")
    @Access
    @RegisterCard
    public String register(RegisterParam registerParam){
        final Object[] objects = new Object[1];
        registerService.register(registerParam, new ResultListener() {
            @Override
            public void success(Object object) {
                ResponseData<RegisterParam> responseData = new ResponseData<>();
                responseData.setCode("200");
                registerParam.setCardNo((String) object);
                responseData.setData(registerParam);
                responseData.setMessage("success");
                objects[0] = responseData;
            }

            @Override
            public void error(Object object) {
                ResponseData<RegisterParam> responseData = new ResponseData<>();
                responseData.setCode("500");
                responseData.setData(null);
                responseData.setMessage((String) object);
                objects[0] = responseData;
            }

            @Override
            public void exception(Object object) {
                ResponseData<RegisterParam> responseData = new ResponseData<>();
                responseData.setCode("500");
                responseData.setData(null);
                responseData.setMessage((String) object);

                objects[0] = responseData;
            }
        });
        Gson gson = new Gson();
        return gson.toJson(objects[0]);
    }

}
