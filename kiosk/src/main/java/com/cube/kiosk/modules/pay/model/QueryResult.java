package com.cube.kiosk.modules.pay.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "QUERY_RESULT")
@ApiModel
public class QueryResult {

    @Id
    @GeneratedValue(
            generator = "system-uuid"
    )
    @GenericGenerator(
            name = "system-uuid",
            strategy = "uuid"
    )
    @ApiModelProperty(hidden = true)
    private String id;


    /**
     * 00 表示成功，其它表示失败
     * 响应码
     */
    @ApiModelProperty(value = "00 表示成功，其它表示失败")
    private String respCode;

    /**
     * 返回码解释信息
     */
    @ApiModelProperty(value = "返回码解释信息")
    private String respMsg;

    /**
     * 设备终端编号
     * 唯一，可以不填写
     */
    @ApiModelProperty(value = "设备终端编号")
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
    @ApiModelProperty(value = " * 交易类型,C：表示被扫消费,R：表示退款,F: 为主扫下单,S: 为关闭订单,G：表示订单支付结果查询,J：表示退货结果查询")
    private String tranType;

    /**
     * 以分为单位的交易金额
     */
    private String txnAmt;

    /**
     * 商户系统订单号
     * 商户系统订单号，消费交易商户生成唯一订单号（如果不
     * 能生成，可以向扫码平台申请商户系统订单号）；支付结
     * 果查询、消费撤销、退货交易需要传入原消费交易商户系
     * 统订单号
     */
    @ApiModelProperty(value = "商户系统订单号")
    private String merTradeNo;

    /**
     * 商户号
     */
    @ApiModelProperty(value = "商户号")
    private String mid;

    /**
     * 商户名称
     */
    @ApiModelProperty(value = "商户名称")
    private String merName;

    /**
     * 终端号
     */
    @ApiModelProperty(value = "终端号")
    private String tid;

    /**
     * 终端流水号
     * 终端号系统跟踪号，同请求报文原值返回，客户端收到应
     * 答报文需要验证 traceNo 字段值，需与请求报文值一致，
     * 如果不一致则丢包交易失败
     */
    @ApiModelProperty(value = "终端流水号")
    private String traceNo;

    /**
     * 支付方式
     * ZFBA-支付宝
     * WEIX-微信
     * UPAY-银联二维码
     * DZZF-电子支付
     */
    @ApiModelProperty(value = "支付方式")
    private String payType;

    /**
     * 交易时间
     */
    @ApiModelProperty(value = "交易时间")
    private String txnTime;

    /**
     * 支付订单号
     * 银行返回系统订单号，需要保存该支付交易订单号
     */
    @ApiModelProperty(value = "支付订单号,银行返回系统订单号，需要保存该支付交易订单号")
    private String tradeNo;

    /**
     * 第三方支付订单号
     */
    @ApiModelProperty(value = "第三方支付订单号")
    private String transNo;


    /**
     * 退款单号
     * 商户系统退货单号，同请求一致
     */
    @ApiModelProperty(value = "退款单号")
    private String vfTradeNo;

    /**
     * 银行优惠金额
     */
    @ApiModelProperty(value = "银行优惠金额")
    private String discountAmt;

    /**
     * 有效时间
     * 二维码本身的有效时间，是相对时间，单位为秒，以接收
     * 方收到报文时间为起始点计时。不同类型的订单以及不同
     * 的订单状况会对应不同的默认有效时间和最大有效时间
     * （可以为空）
     */
    @ApiModelProperty(value = "有效时间,二维码本身的有效时间，是相对时间，单位为秒，以接收,方收到报文时间为起始点计时。不同类型的订单以及不同,的订单状况会对应不同的默认有效时间和最大有效时间")
    private String qrValidTime;

    /**
     * 二维码信息
     * 主扫支付二维码，以二维码形式显示，手机 APP 扫二维码
     * 码消费
     */
    @ApiModelProperty(value = "二维码信息,主扫支付二维码，以二维码形式显示，手机 APP 扫二维码码消费")
    private String scanCode;

    /**
     * 订单数据
     * 当 tranType 为 F 时，payType 值为 ZFBA 或 WEIX 时
     * 支付宝返回的 tradeNo
     * 或者微信返回的 prepayId
     */
    @ApiModelProperty(value = "订单数据，当 tranType 为 F 时，payType 值为 ZFBA 或 WEIX 时，支付宝返回的 tradeNo，或者微信返回的 prepayId")
    private String payData;

    @ApiModelProperty(value = "主扫交易当使用微信和支付宝支付成功后")
    private String callBackUrl;

    private String cardNo;
}
