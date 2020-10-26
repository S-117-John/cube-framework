package com.cube.common.https;


import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Demo class
 *
 * @author 李晋
 * @date 2020/08/12
 */
public class HttpsUtils {

    public static String doPost(String url, Map<String,Object> result) throws Exception {
        String charset = "utf-8";
        SSLClient sslClient = new SSLClient();
        String token = "48616468f12748e3a26e1af4e793ec4b";
        String hosId = "f27fc323ba174cb9b4d1c6e062a4ae34";
        Map<String,Object> requestJson = new HashMap<>(16);
        result.put("token", token);
        result.put("hosId", hosId);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        requestJson.put("requestJson",jsonStr);
        String a = requestJson.toString();
        String b = a.substring(1,a.length()-1);
        String httpOrgCreateTestRtn = sslClient.doPost(url, b, charset);
        return httpOrgCreateTestRtn;
    }
}
