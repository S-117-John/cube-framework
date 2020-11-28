package com.cube.kiosk.modules.register.controller;

import com.cube.core.global.anno.ResponseApi;
import com.cube.core.system.annotation.SysLog;
import com.cube.kiosk.modules.patient.service.PatientService;
import com.cube.kiosk.modules.register.model.RegisterParam;
import com.cube.kiosk.modules.register.service.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private PatientService patientService;

    @ApiOperation(httpMethod = "POST",value = "注册身份信息")
    @RequestMapping("")
    @ResponseApi
    public Object register(@RequestBody RegisterParam registerParam){

        patientService.delete(registerParam.getCardNo());
        RegisterParam result =  registerService.register(registerParam);
        return result;
    }

}
