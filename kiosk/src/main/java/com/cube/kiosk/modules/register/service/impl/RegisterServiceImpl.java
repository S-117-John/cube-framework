package com.cube.kiosk.modules.register.service.impl;

import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.common.utils.RestTemplate;
import com.cube.kiosk.modules.register.model.RegisterParam;
import com.cube.kiosk.modules.register.service.RegisterService;
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
            paramMap.put("cardId",param.getIdCard());
            paramMap.put("patienttype","1");
            paramMap.put("patientname",param.getIdCard());
            paramMap.put("guarname",param.getIdCard());
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
            listener.success(result);
        }catch (Exception e){
            listener.exception(e.getMessage());
        }
    }
}
