package com.cube.kiosk.modules.common;

import lombok.Data;

/**
 * 支付相应体
 * CreationTime:2020-08-24
 */
@Data
public class ResponseBank {
    private String respCode;//响应码
    private String respMsg;//响应码解释信
    private String tranType;//交易类型
    private String txnAmt;//交易金额
    private String traceNo;//终端流水号
    private String tradeNo;//支付订单号
    private String transNo;//第三方支付订单号
    private String mid;//
    private String tid;//
    private String merTradeNo;//商户系统订单
    private String scanCode;//二维码信息
    private String payType;//支付方式
    private String txnTime;//交易时间
}
