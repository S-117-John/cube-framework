package com.cube.kiosk.modules.common.utils;

import com.cube.kiosk.modules.common.config.HttpsClientRequestFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

@Component
public class HttpsRestTemplate{

    @Value("${app-pay.url}")
    private String url;

    @Value("${sharedSecret}")
    private String sharedSecret;


    public String postForString(String param,String method){
        RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());
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
        String httpBody = "";
        HttpEntity<String> httpEntity = new HttpEntity<String>(httpBody, headers);
        StringBuffer paramUrl = new StringBuffer(url);
        URI uri = URI.create(paramUrl.toString());
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET,httpEntity,String.class);
        return responseEntity.getBody();
    }

    public static void main(String[] args) {
        HttpsRestTemplate httpsRestTemplate = new HttpsRestTemplate();
        String body = httpsRestTemplate.postForString("","");
        System.out.println(body);
    }
}
