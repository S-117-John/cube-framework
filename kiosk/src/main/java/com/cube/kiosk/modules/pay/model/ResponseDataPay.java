package com.cube.kiosk.modules.pay.model;

import lombok.Data;

@Data
public class ResponseDataPay {

    /**
     * 响应码
     * 00 表示成功，其它表示失败
     */
    private String respCode;

    /**
     * 响应码解释信
     * 息
     */
    private String respMsg;

    /**
     * 交易类型
     */
    private String tranType;

    /**
     * 交易金额
     */
    private String txnAmt;

    /**
     * 终端流水号
     * 终端号系统跟踪号，同请求报文原值返回，客户端收到应
     * 答报文需要验证 traceNo 字段值，需与请求报文值一致，
     * 如果不一致则丢包交易失败
     */
    private String traceNo;

    /**
     * 支付订单号
     * 银行返回系统订单号，需要保存该支付交易订单号
     */
    private String tradeNo;

    /**
     * 商户号
     */
    private String mid;

    /**
     * 终端号
     */
    private String tid;

    /**
     * 商户系统订单
     * 号
     */
    private String merTradeNo;

    private String scanCode;



}
