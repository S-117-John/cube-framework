package com.cube.kiosk.modules.pay.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PayParam {

    /**
     * 设备终端编号
     * 唯一，可以不填写
     */
    @ApiModelProperty(name = "设备终端编号")
    private String posNo;

    /**
     * 交易类型
     * C：表示被扫消费
     * R：表示退款
     * F: 为主扫下单
     * S: 为关闭订单
     * G：表示订单支付结果查询
     * J：表示退货结果查询
     */
    @ApiModelProperty(name = "交易类型")
    private String tranType;

    /**
     * 以分为单位的交易金额
     */
    @ApiModelProperty(name = "交易金额")
    private String txnAmt;

    /**
     * 商户系统订单号
     * 商户系统订单号，消费交易商户生成唯一订单号（如果不
     * 能生成，可以向扫码平台申请商户系统订单号）；支付结
     * 果查询、消费撤销、退货交易需要传入原消费交易商户系
     * 统订单号
     */
    @ApiModelProperty(name = "商户系统订单号")
    private String merTradeNo;

    /**
     * 商户号
     */
    @ApiModelProperty(name = "商户号")
    private String mid;

    /**
     * 终端号
     */
    @ApiModelProperty(name = "终端号")
    private String tid;

    private String requestIp;

    private String payType;

    private String cardNo;

    /**
     * 终端流水号
     */
    @ApiModelProperty(name = "终端流水号")
    private String traceNo;
}
