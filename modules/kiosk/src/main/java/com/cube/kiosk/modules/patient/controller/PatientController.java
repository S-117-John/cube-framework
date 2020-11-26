package com.cube.kiosk.modules.patient.controller;


import com.cube.core.global.anno.ResponseApi;
import com.cube.core.system.annotation.SysLog;
import com.cube.kiosk.modules.anno.Access;
import com.cube.kiosk.modules.common.ResponseHisData;
import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.patient.anno.PatientParamResolver;
import com.cube.kiosk.modules.patient.model.HosPatientDO;
import com.cube.kiosk.modules.patient.model.PatientParam;
import com.cube.kiosk.modules.patient.repository.HosPatientRepository;
import com.cube.kiosk.modules.patient.service.PatientService;
import com.cube.kiosk.modules.system.entity.HospitalDO;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/patient")
@Api(value = "患者信息",tags = "患者信息")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private HosPatientRepository hosPatientRepository;

    @ApiOperation(httpMethod = "POST",value = "查询患者西悉尼")
    @RequestMapping("index")
    @SysLog("查询患者信息")
    public String index(@RequestBody String cardNo){
        final Object[] objects = new Object[1];
        patientService.get(cardNo, new ResultListener() {
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


    @ApiOperation(httpMethod = "POST",value = "撤销建卡")
    @RequestMapping("delete")
    @ResponseApi
    public Object delete(@RequestBody String cardNo){
        patientService.delete(cardNo);
        return "SUCCESS";
    }

    @ApiOperation(httpMethod = "POST",value = "查询住院患者信息")
    @RequestMapping("hosInfo")
    @ResponseApi
    public Object queryHosPatient(@RequestBody String hosId){

        if(StringUtils.isEmpty(hosId)){
            throw new RuntimeException("请输入住院号");
        }

        Gson gson = new Gson();
        String result = patientService.queryHosPatient(hosId);

        if(StringUtils.isEmpty(result)){
            throw new RuntimeException(String.format("HIS平台未返回住院号【%s】的信息",hosId));
        }
        ResponseHisData<Object> responseHisData = gson.fromJson(result,ResponseHisData.class);
        if(responseHisData.getCode()==1||responseHisData.getCode()==2){
            throw new RuntimeException((String) responseHisData.getResponseData());
        }
        Map<String,Object> map = (Map<String, Object>) responseHisData.getResponseData();
        HosPatientDO hosPatientDO = new HosPatientDO();
        hosPatientDO.setHosId(hosId);
        hosPatientDO.setName(MapUtils.getString(map,"patientname"));
        hosPatientDO.setAge(MapUtils.getString(map,"age"));
        hosPatientDO.setSex(MapUtils.getString(map,"sex"));
//        hosPatientDO.setIdCard(MapUtils.getString(map,"sex"));
        hosPatientRepository.save(hosPatientDO);
        return hosPatientDO;
    }
}
