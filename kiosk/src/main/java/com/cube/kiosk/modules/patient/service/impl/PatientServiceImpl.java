package com.cube.kiosk.modules.patient.service.impl;


import com.cube.common.https.HttpsUtils;
import com.cube.kiosk.modules.common.ResponseData;
import com.cube.kiosk.modules.common.ResponseDatabase;
import com.cube.kiosk.modules.common.ResultData;
import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.log.entity.HisLogDO;
import com.cube.kiosk.modules.log.repository.HisLogRepository;
import com.cube.kiosk.modules.patient.model.Patient;
import com.cube.kiosk.modules.patient.service.PatientService;

import com.google.gson.Gson;
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

    @Value("${neofaith.url}")
    private String url;

    @Autowired
    private HisLogRepository hisLogRepository;

    /**
     * 获取病人信息
     *
     * @param id        卡号
     * @param linstener
     * @return
     */
    @Override
    public void get(String id, ResultListener linstener) {
        Map<String,Object> map = new HashMap<>(16);
        map.put("cardId",id);
        ResponseData<Patient> responseData = ResponseDatabase.newResponseData();
        HisLogDO hisLogDO = new HisLogDO();
        hisLogDO.setParam(id);
        hisLogDO.setMethod("getPatientnameInfo");
        hisLogDO.setCreateTime(new Date());

        try {
            String result = HttpsUtils.doPost(url+"his/getPatientnameInfo", map);
            hisLogDO.setResult(result);
            hisLogRepository.save(hisLogDO);
            //反序列化Patient
            Gson gson = new Gson();
            ResultData<Object> resultData = gson.fromJson(result, new TypeToken<ResultData<Object>>(){}.getType());
            if(resultData.getCode()==1){
                responseData.setCode(500);
                responseData.setData(null);
                responseData.setMessage(resultData.getResponseData().toString());
                linstener.error(responseData);
                return;
            }
            Patient patient = (Patient) resultData.getResponseData();
            map.put("identitycard", id);
            map.put("patientName", patient.getName());
            result = HttpsUtils.doPost(url+"his/getBalance", map);
            resultData = gson.fromJson(result, new TypeToken<ResultData<Object>>(){}.getType());
            if(resultData.getCode()==1){
                responseData.setCode(500);
                responseData.setData(null);
                responseData.setMessage(resultData.getResponseData().toString());
                linstener.error(responseData);
                return;
            }
            Patient blance = (Patient) resultData.getResponseData();
            patient.setBalance(blance.getBalance());
            patient.setAmount(blance.getAmount());
            linstener.success(patient);
        } catch (Exception e) {
            linstener.exception(e.getMessage());
        }
    }
}
