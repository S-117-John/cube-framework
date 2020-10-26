package com.cube.kiosk.modules.register.controller;

import com.cube.core.system.annotation.SysLog;
import com.cube.kiosk.modules.anno.Access;
import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.register.RegisterParamResolver;
import com.cube.kiosk.modules.register.model.RegisterParam;
import com.cube.kiosk.modules.register.service.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RegisterParamResolver registerParamResolver;

    @ApiOperation(httpMethod = "GET",value = "办卡")
    @RequestMapping("")
    @SysLog("办卡")
    @Access
    public String index(String param){
        Object[] objects = new Object[1];
        String result = "";
        RegisterParam registerParam = registerParamResolver.getParam(param);

        registerService.register(registerParam, new ResultListener() {
            @Override
            public void success(Object object) {
                objects[0] = object;
            }

            @Override
            public void error(Object object) {

            }

            @Override
            public void exception(Object object) {

            }
        });
        return (String) objects[0];
    }

}
