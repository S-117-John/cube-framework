package com.cube.kiosk.modules.common.utils;

import java.security.MessageDigest;
import java.util.*;

public class HisMd5Sign {

    private static String charset = "UTF-8";

    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    /**
     * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     */
    @SuppressWarnings("rawtypes")
    public static String createSign(SortedMap<String, String> packageParams, String token) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + token);
        System.out.println("md5 sb:" + sb);
        String sign = HisMd5Sign.MD5Encode(sb.toString(), charset)
                .toUpperCase();
        System.out.println("签名:" + sign);
        return sign;

    }

    public static void main(String[] args) {
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("inHosid", "0002010138");
        packageParams.put("hosId", "f27fc323ba174cb9b4d1c6e062a4ae34");
        packageParams.put("money", "1");
        packageParams.put("operatorid", "0102");
        packageParams.put("patientName", "史振杰");
        packageParams.put("payDate", "2020-11-27 13:07:15");
        packageParams.put("payType", "微信");
        packageParams.put("serialNumber", "781833218792882176");
        packageParams.put("token", "48616468f12748e3a26e1af4e793ec4b");
        String sign = HisMd5Sign.createSign(packageParams, "48616468f12748e3a26e1af4e793ec4b");
        System.out.println(sign);
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
