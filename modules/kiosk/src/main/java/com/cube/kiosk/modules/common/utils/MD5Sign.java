package com.cube.kiosk.modules.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

public class MD5Sign {

    public static String md5(String inStr) {
        try {
            return DigestUtils.md5Hex(inStr.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误");
        }
    }

    public static void main(String[] args) {

        String param = "{\"posNo\":\"\",\"tranType\":\"F\",\"txnAmt\":\"1\",\"merTradeNo\":\"773139355878293504\",\"mid\":\"103610580626129\",\"tid\":\"\",\"traceNo\":\"000001\"}&Ke377y3ruJogSNx58GBz31DQ8zWbOpi8";
        String result = md5(param);
        System.out.println(result);
    }
}
