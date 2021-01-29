package com.cube.kiosk.modules.register.service.impl;

import com.cube.kiosk.modules.common.ResponseHisData;
import com.cube.kiosk.modules.common.model.HisData;
import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.common.utils.RestTemplate;
import com.cube.kiosk.modules.patient.model.Patient;
import com.cube.kiosk.modules.patient.repository.PatientRepository;
import com.cube.kiosk.modules.register.model.RegisterParam;
import com.cube.kiosk.modules.register.service.RegisterService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.NoRouteToHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${neofaith.token}")
    private String token;

    @Value("${neofaith.hosId}")
    private String hosId;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public RegisterParam register(RegisterParam param){
        Map<String,Object> paramMap = new HashMap<>(16);
        String str = param.getBirthday();   //
        String string = "2020-02-01";  //yyyy-MM-dd
        try {
            //   yyyyMMdd转yyyy-MM-dd
            Date format1 = null;
            format1 = new SimpleDateFormat("yyyyMMdd").parse(str);
            String longDate = new SimpleDateFormat("yyyy-MM-dd").format(format1);
            if(StringUtils.isEmpty(longDate)){
                paramMap.put("birthday",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            }else{
                paramMap.put("birthday",longDate);
            }

        } catch (ParseException e) {
            throw new RuntimeException("出生日期转换异常");
        }


        paramMap.put("cardTypeName","身份证");
        String cardNoParam = param.getCardNo().substring(0,32);
        String a = cardNoParam.substring(0,6);
        String b = cardNoParam.substring(7,10);
        String c = cardNoParam.substring(11,12);
        String d = cardNoParam.substring(13,14);
        String e = cardNoParam.substring(15,cardNoParam.length());
        String cardNo = a+b+c+d+e;

        if(cardNo.indexOf("0")==0){
            cardNo = cardNo.substring(1);
        }
        paramMap.put("cardId",cardNo);
        paramMap.put("patienttype","1");
        paramMap.put("patientname",param.getName());
        paramMap.put("guarname","");
        paramMap.put("identityName","身份证");
        paramMap.put("identitycard",param.getIdCard());
        paramMap.put("sex",param.getSex());

        paramMap.put("homeplace","");
        paramMap.put("employment","");
        paramMap.put("registeredAddress",param.getAddress());
        paramMap.put("postalCode","");
        paramMap.put("telephone",param.getPhone());
        paramMap.put("workUnit","");
        paramMap.put("unitTelephone","");
        paramMap.put("unitPostCode","");
        paramMap.put("nationality","");
        paramMap.put("nation","");
        paramMap.put("password","");
        paramMap.put("operatorid","");
        paramMap.put("token", token);
        paramMap.put("hosId", hosId);
        Gson gson = new Gson();
        String hsiparam  = gson.toJson(paramMap);
        String result = restTemplate.doPostNewHisApi(hsiparam,"his/cardIssuers");
        if(StringUtils.isEmpty(result)){
            throw new RuntimeException("未取得HIS系统返回信息，请联系管理员");
        }
        ResponseHisData<String> responseHisData = gson.fromJson(result,ResponseHisData.class);
        if(responseHisData.getCode()==1){
            throw new RuntimeException(String.format("HIS系统执行失败，获取HIS返回信息:%s",responseHisData.getResponseData()));
        }

        if(responseHisData.getCode()==0){
            Patient patient = new Patient();
            patient.setCardNo(a+b+c+d+e);
            patient.setName(param.getName());
            patientRepository.save(patient);
        }
        param.setCardNo(a+b+c+d+e);
        return  param;
    }
}
