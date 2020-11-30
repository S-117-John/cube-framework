package com.cube.kiosk.modules.pay.model;

import lombok.Data;

@Data
public class BankDTO {

    public String cardNo;

    public String saveMoney;

    public String responseCode;

    /**
     * 卡号
     */
    public String bankCardNo;

    /**
     * 交易类型标志
     */
    public String transType;


    /**
     * 金额
     */
    public String money;

    /**
     * 发卡行代码
     */
    public String bankCode;

    /**
     * 支付方式
     */
    public String payType;

    /**
     * 票据号
     */
    public String billNo;

    /**
     * 授权号
     */
    public String authorNo;

    /**
     * 凭证号
     */
    public String voucherNo;

    /**
     * 批次号
     */
    public String batchNo;

    /**
     * 交易时间日期
     */
    public String transDate;

    /**
     * 参考号
     */
    public String referenceNo;

    /**
     * 商户号
     */
    public String merchantNo;

    /**
     * 终端号
     */
    public String terminalNo;
}
