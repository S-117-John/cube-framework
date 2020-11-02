package com.cube.kiosk.modules.patient.controller;


import com.cube.core.system.annotation.SysLog;
import com.cube.kiosk.modules.anno.Access;
import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.patient.anno.PatientParamResolver;
import com.cube.kiosk.modules.patient.model.PatientParam;
import com.cube.kiosk.modules.patient.service.PatientService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/patient")
@Api(value = "患者信息",tags = "患者信息")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @ApiOperation(httpMethod = "GET",value = "查询患者西悉尼")
    @RequestMapping("index")
    @SysLog("查询患者信息")
    @Access
    @PatientParamResolver
    public String index(PatientParam patientParam){
        final Object[] objects = new Object[1];
        patientService.get(patientParam, new ResultListener() {
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
        Gson gson = new Gson();

        return gson.toJson(objects[0]);
    }
}
