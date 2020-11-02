package com.cube.kiosk.modules.patient.service.impl;


import com.cube.common.https.HttpsUtils;
import com.cube.kiosk.modules.common.ResponseData;
import com.cube.kiosk.modules.common.ResponseDatabase;
import com.cube.kiosk.modules.common.ResponseHisData;
import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.common.utils.RestTemplate;
import com.cube.kiosk.modules.log.entity.HisLogDO;
import com.cube.kiosk.modules.log.repository.HisLogRepository;
import com.cube.kiosk.modules.patient.model.Patient;
import com.cube.kiosk.modules.patient.model.PatientParam;
import com.cube.kiosk.modules.patient.service.PatientService;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LMZ
 */
@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取病人信息
     *
     * @param patientParam
     * @param linstener
     * @return
     */
    @Override
    public void get(PatientParam patientParam, ResultListener linstener) {
        ResponseData<Patient> responseData = new ResponseData<>();
        try{
            Map<String,Object> paramMap = new HashMap<>(16);

            paramMap.put("cardId",patientParam.getCardNo());
            String result = restTemplate.doPostHisApi(paramMap,"his/getPatientnameInfo");
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
                responseData.setCode("200");
                responseData.setData(patient);
                responseData.setMessage("success");
                linstener.success(responseData);
            }

        }catch (Exception e){
            responseData.setCode("500");
            responseData.setData(null);
            responseData.setMessage(e.getMessage());
            linstener.exception(responseData);
        }
    }
}
