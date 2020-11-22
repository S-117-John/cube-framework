package com.cube.kiosk.modules.pay.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "交易查询参数")
public class TransQueryParam implements Serializable {

    @ApiModelProperty(value = "设备终端编号",required = true)
    private String posNo;

    @ApiModelProperty(value = "交易类型",required = true)
    private String tranType;

    @ApiModelProperty(value = "终端流水号",required = true)
    private String traceNo;

    @ApiModelProperty(value = "商户号",required = true)
    private String mid;

    @ApiModelProperty(value = "终端号",required = true)
    private String tid;

    @ApiModelProperty(value = "商户系统订单号",required = true)
    private String merTradeNo;

    @ApiModelProperty(value = "支付订单号",required = true)
    private String tradeNo;
}
