package com.cube.kiosk.modules.common.utils;

import com.cube.common.https.SSLClient;
import com.cube.kiosk.modules.anno.BankLog;
import com.cube.kiosk.modules.anno.HisLog;
import com.cube.kiosk.modules.common.config.HttpsClientRequestFactory;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;

@Component
public class RestTemplate{

    @Value("${neofaith.token}")
    private String token;

    @Value("${neofaith.hosId}")
    private String hosId;

    @Value("${neofaith.url}")
    private String hisUrl;

    @Autowired
    private SSLClient sslClient;

    @Value("${app-pay.url}")
    private String url;

    @Value("${app-pay.sharedSecret}")
    private String sharedSecret;

    @HisLog
    public String doPostNewHisApi(String param,String method) {

        org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        converterList.remove(1);    //移除StringHttpMessageConverter
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converterList.add(1, converter);    //convert顺序错误会导致失败
        restTemplate.setMessageConverters(converterList);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.put("requestJson", Collections.singletonList(param));

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(postParameters, headers);
        StringBuffer paramUrl = new StringBuffer(hisUrl+method);
        URI uri = URI.create(paramUrl.toString());
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST,httpEntity,String.class);
        return responseEntity.getBody();





//        String httpOrgCreateTestRtn = sslClient.doPost(this.hisUrl+method, b, charset);
//        return httpOrgCreateTestRtn;
    }

    public String doPostHisApi(Map<String,Object> result,String method) {
        String charset = "utf-8";
        String token = this.token;
        String hosId = this.hosId;
        Map<String,Object> requestJson = new HashMap<>(16);
        result.put("token", token);
        result.put("hosId", hosId);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
//        requestJson.put("requestJson",jsonStr);
//        String a = requestJson.toString();
//        String b = a.substring(1,a.length()-1);
        org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        converterList.remove(1);    //移除StringHttpMessageConverter
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converterList.add(1, converter);    //convert顺序错误会导致失败
        restTemplate.setMessageConverters(converterList);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        String httpBody = jsonStr;
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.put("requestJson", Collections.singletonList(jsonStr));

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(postParameters, headers);
        StringBuffer paramUrl = new StringBuffer(hisUrl+method);
        URI uri = URI.create(paramUrl.toString());
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST,httpEntity,String.class);
        return responseEntity.getBody();





//        String httpOrgCreateTestRtn = sslClient.doPost(this.hisUrl+method, b, charset);
//        return httpOrgCreateTestRtn;
    }

    public String doPostHisSaveApi(String param, String method) {

//        org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate(new HttpsClientRequestFactory());
        org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        converterList.remove(1);    //移除StringHttpMessageConverter
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converterList.add(1, converter);    //convert顺序错误会导致失败
        restTemplate.setMessageConverters(converterList);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        String httpBody = param;
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.put("requestJson", Collections.singletonList(param));

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(postParameters, headers);
        StringBuffer paramUrl = new StringBuffer(hisUrl+method);
        URI uri = URI.create(paramUrl.toString());
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST,httpEntity,String.class);
        return responseEntity.getBody();





    }

//    @BankLog
    public String doPostBankApi(String param,String method){
        org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        converterList.remove(1);    //移除StringHttpMessageConverter
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converterList.add(1, converter);    //convert顺序错误会导致失败
        restTemplate.setMessageConverters(converterList);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        String md5Sign = MD5Sign.md5(param+"&"+sharedSecret);
        headers.add("sign",md5Sign);
        String httpBody = param;
        HttpEntity<String> httpEntity = new HttpEntity<String>(httpBody, headers);
        StringBuffer paramUrl = new StringBuffer(url+method);
        URI uri = URI.create(paramUrl.toString());
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST,httpEntity,String.class);
        return responseEntity.getBody();
    }


}
