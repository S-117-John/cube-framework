package com.cube.kiosk.modules.register.service.impl;

import com.cube.kiosk.modules.common.ResponseHisData;
import com.cube.kiosk.modules.common.model.HisData;
import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.common.utils.RestTemplate;
import com.cube.kiosk.modules.register.model.RegisterParam;
import com.cube.kiosk.modules.register.service.RegisterService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void register(RegisterParam param, ResultListener listener) {
        try{
            Map<String,Object> paramMap = new HashMap<>(16);
            paramMap.put("cardTypeName","身份证");
            String cardNoParam = param.getCardNo().substring(0,32);
            String a = cardNoParam.substring(0,6);
            String b = cardNoParam.substring(7,10);
            String c = cardNoParam.substring(11,12);
            String d = cardNoParam.substring(13,14);
            String e = cardNoParam.substring(15,cardNoParam.length());



            paramMap.put("cardId",a+b+c+d+e);
            paramMap.put("patienttype","1");
            paramMap.put("patientname",param.getName());
            paramMap.put("guarname","");
            paramMap.put("identityName","身份证");
            paramMap.put("identitycard",param.getIdCard());
            paramMap.put("sex",param.getSex());
            paramMap.put("birthday",param.getBirthday());
            paramMap.put("homeplace","");
            paramMap.put("employment","");
            paramMap.put("registeredAddress",param.getAddress());
            paramMap.put("postalCode","");
            paramMap.put("telephone","");
            paramMap.put("workUnit","");
            paramMap.put("unitTelephone","");
            paramMap.put("unitPostCode","");
            paramMap.put("nationality","");
            paramMap.put("nation","");
            paramMap.put("password","");
            paramMap.put("operatorid","");
            String result = restTemplate.doPostHisApi(paramMap,"his/cardIssuers");
            Gson gson = new Gson();
            ResponseHisData<String> responseHisData = gson.fromJson(result,ResponseHisData.class);
            if(responseHisData.getCode()==1){
                listener.error(responseHisData.getResponseData());
                return;
            }

            if(responseHisData.getCode()==0){
                listener.success(responseHisData.getResponseData());
            }

        }catch (Exception e){
            listener.exception(e.getMessage());
        }
    }
}
