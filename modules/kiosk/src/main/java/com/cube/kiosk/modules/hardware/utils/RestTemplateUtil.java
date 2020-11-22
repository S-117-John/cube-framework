package com.cube.kiosk.modules.hardware.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtil {

    public static String post(String ip,String param){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://"+ip+":50850/api/values";
        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();
//            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//            headers.setContentType(type);
        headers.add("Accept", "*/*");
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("", param);
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, Object>> r = new HttpEntity<>(postParameters, headers);
        String result = restTemplate.postForObject(url,r,String.class);
        return result;
    }
}
