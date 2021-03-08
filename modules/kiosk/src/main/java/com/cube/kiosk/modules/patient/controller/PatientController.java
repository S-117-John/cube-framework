package com.cube.kiosk.modules.patient.controller;


import com.cube.core.global.anno.ResponseApi;
import com.cube.core.system.annotation.SysLog;
import com.cube.kiosk.modules.anno.Access;
import com.cube.kiosk.modules.common.ResponseData;
import com.cube.kiosk.modules.common.ResponseHisData;
import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.common.utils.RestTemplate;
import com.cube.kiosk.modules.patient.anno.PatientParamResolver;
import com.cube.kiosk.modules.patient.model.HosPatientDO;
import com.cube.kiosk.modules.patient.model.Patient;
import com.cube.kiosk.modules.patient.model.PatientParam;
import com.cube.kiosk.modules.patient.repository.HosPatientRepository;
import com.cube.kiosk.modules.patient.repository.PatientRepository;
import com.cube.kiosk.modules.patient.service.PatientService;
import com.cube.kiosk.modules.system.entity.HospitalDO;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/patient")
@Api(value = "患者信息",tags = "患者信息")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private HosPatientRepository hosPatientRepository;


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
        String result = patientService.delete(cardNo);
        return result;
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
        hosPatientDO.setIdCard(MapUtils.getString(map,"postalCode"));
        hosPatientRepository.save(hosPatientDO);
        return hosPatientDO;
    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PatientRepository patientRepository;


    @Value("${neofaith.token}")
    private String token;

    @Value("${neofaith.hosId}")
    private String hosId;

    @ApiOperation(httpMethod = "POST",value = "查询患者信息")
    @RequestMapping("index")
    @ResponseApi
    public Object patient(@RequestBody String cardNo){
        Patient patient = new Patient();
//        if(cardNo.length()>28){
//            String a = cardNo.substring(0,6);
//            String b = cardNo.substring(7,10);
//            String c = cardNo.substring(11,12);
//            String d = cardNo.substring(13,14);
//            String e = cardNo.substring(15,cardNo.length());
//            cardNo = a+b+c+d+e;
//        }
        cardNo = cardNo.substring(0,28);
        Map<String,Object> paramMap = new HashMap<>(16);
        Gson gson = new Gson();
        paramMap.put("cardId",cardNo);
        paramMap.put("token", token);
        paramMap.put("hosId", hosId);
        String json = gson.toJson(paramMap);
        String result = restTemplate.doPostNewHisApi(json,"his/getPatientnameInfo");
        ResponseHisData<Object> responseHisData = gson.fromJson(result,ResponseHisData.class);
        if(responseHisData.getCode()==1){
            throw new RuntimeException((String) responseHisData.getResponseData());
        }

        if(responseHisData.getCode()==0){
            LinkedTreeMap<String,Object> linkedTreeMap = (LinkedTreeMap<String, Object>) responseHisData.getResponseData();

            patient.setName(linkedTreeMap.get("patientname").toString());
            patient.setAge(linkedTreeMap.get("age").toString());
            patient.setSex(linkedTreeMap.get("sex").toString());
            patient.setCardNo(cardNo);
            try{
                patientRepository.save(patient);
            }catch (Exception e){

            }
            //查询余额
            paramMap.put("patientname",patient.getName());
            paramMap.put("identitycard",patient.getCardNo());
            paramMap.put("token", token);
            paramMap.put("hosId", hosId);
            json = gson.toJson(paramMap);
            result = restTemplate.doPostNewHisApi(json,"his/getBalance");
            responseHisData = gson.fromJson(result,ResponseHisData.class);
            if(responseHisData.getCode()==0){
                Map<String,Object> map = (Map<String, Object>) responseHisData.getResponseData();
                patient.setBalance(MapUtils.getString(map,"balance"));
            }

        }
        return patient;
    }
}
