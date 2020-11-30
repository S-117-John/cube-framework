package com.cube.kiosk.modules.patient.service.impl;


import com.cube.kiosk.modules.common.ResponseData;
import com.cube.kiosk.modules.common.ResponseHisData;
import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.common.utils.RestTemplate;
import com.cube.kiosk.modules.patient.model.Patient;
import com.cube.kiosk.modules.patient.model.PatientParam;
import com.cube.kiosk.modules.patient.repository.PatientRepository;
import com.cube.kiosk.modules.patient.service.PatientService;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LMZ
 */
@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PatientRepository patientRepository;


    @Value("${neofaith.token}")
    private String token;

    @Value("${neofaith.hosId}")
    private String hosId;
    /**
     * 获取病人信息
     *
     * @param
     * @param linstener
     * @return
     */
    @Override
    public void get(String cardNo, ResultListener linstener) {
        ResponseData<Patient> responseData = new ResponseData<>();

        if(cardNo.length()>28){
            String a = cardNo.substring(0,6);
            String b = cardNo.substring(7,10);
            String c = cardNo.substring(11,12);
            String d = cardNo.substring(13,14);
            String e = cardNo.substring(15,cardNo.length());
            cardNo = a+b+c+d+e;
        }

        try{
            Map<String,Object> paramMap = new HashMap<>(16);

            paramMap.put("cardId",cardNo);
            String result = restTemplate.doPostHisApi(paramMap,"his/getPatientInfo");
            Gson gson = new Gson();
            ResponseHisData<Object> responseHisData = gson.fromJson(result,ResponseHisData.class);
            if(responseHisData.getCode()==1){
                responseData.setCode("500");
                responseData.setData(null);
                responseData.setMessage((String) responseHisData.getResponseData());
                linstener.error(responseData);
                return;
            }

            if(responseHisData.getCode()==0){
                LinkedTreeMap<String,Object> linkedTreeMap = (LinkedTreeMap<String, Object>) responseHisData.getResponseData();
                Patient patient = new Patient();
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
                String json = gson.toJson(paramMap);
                result = restTemplate.doPostNewHisApi(json,"his/getBalance");
                responseHisData = gson.fromJson(result,ResponseHisData.class);
                if(responseHisData.getCode()==0){
                    Map<String,Object> map = (Map<String, Object>) responseHisData.getResponseData();
                    patient.setBalance(MapUtils.getString(map,"balance"));
                }
                responseData.setCode("200");
                responseData.setData(patient);
                responseData.setMessage("success");
                linstener.success(responseData);
            }

        }catch (Exception exception){
            responseData.setCode("500");
            responseData.setData(null);
            responseData.setMessage(exception.getMessage());
            linstener.exception(responseData);
        }
    }

    @Override
    public String delete(String cardNo) {

        String cardNoParam = cardNo.substring(0,32);
        String a = cardNoParam.substring(0,6);
        String b = cardNoParam.substring(7,10);
        String c = cardNoParam.substring(11,12);
        String d = cardNoParam.substring(13,14);
        String e = cardNoParam.substring(15,cardNoParam.length());
        Gson gson = new Gson();

        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("hosId",hosId);
        map.put("cardTypeName","身份证");
        map.put("cardId",a+b+c+d+e);

        String param = gson.toJson(map);

        String result = restTemplate.doPostNewHisApi(param,"his/revokeCard");

        ResponseHisData<Object> responseHisData = gson.fromJson(result,ResponseHisData.class);
        if(responseHisData.getCode()!=0){
            throw new RuntimeException("撤销建卡失败");
        }
        return result;
    }

    @Override
    public String queryHosPatient(String inId) {
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("hosId",hosId);
        map.put("inHosid",inId);
        String param = gson.toJson(map);
        String result = restTemplate.doPostNewHisApi(param,"his/getPatientInfo");
        return result;
    }
}
