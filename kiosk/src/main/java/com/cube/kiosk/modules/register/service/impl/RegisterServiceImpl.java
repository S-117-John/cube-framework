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
import org.springframework.util.StringUtils;

import java.net.NoRouteToHostException;
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
            if(param.getCardNo().length()<32){
                listener.error("卡号长度小于32");
                return;
            }
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
            if(StringUtils.isEmpty(result)){
                listener.error("未取得HIS系统返回信息，请联系管理员");
                return;
            }
            Gson gson = new Gson();
            ResponseHisData<String> responseHisData = gson.fromJson(result,ResponseHisData.class);
            if(responseHisData.getCode()==1){
                listener.error(String.format("HIS系统执行失败，获取HIS返回信息:%s",responseHisData.getResponseData()));
                return;
            }

            if(responseHisData.getCode()==0){
                listener.success(responseHisData.getResponseData());
            }

        }
        catch (NoRouteToHostException exception){
            listener.exception(String.format("%s:无法链接HIS网络",exception.getMessage()));
        }
        catch (Exception e){

        }
    }
}
