package com.cube.kiosk.modules.common.utils;

import com.cube.common.https.SSLClient;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RestTemplate extends org.springframework.web.client.RestTemplate {

    @Value("${neofaith.token}")
    private String token;

    @Value("${neofaith.hosId}")
    private String hosId;

    @Value("${neofaith.url}")
    private String hisUrl;

    public String doPostHisApi(Map<String,Object> result,String method) throws Exception {
        String charset = "utf-8";
        SSLClient sslClient = new SSLClient();
        String token = this.token;
        String hosId = this.hosId;
        Map<String,Object> requestJson = new HashMap<>(16);
        result.put("token", token);
        result.put("hosId", hosId);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        requestJson.put("requestJson",jsonStr);
        String a = requestJson.toString();
        String b = a.substring(1,a.length()-1);
        String httpOrgCreateTestRtn = sslClient.doPost(this.hisUrl+method, b, charset);
        return httpOrgCreateTestRtn;
    }
}
