package com.cube.kiosk.modules.pay.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRANSACTION_RECORD")
@Data
public class TransactionRecordDO {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    //ip地址
    @Column(name = "IP")
    private String ip;
    //患者姓名
    @Column(name = "PATIENT_NAME")
    private String patientName;
    //身份证号
    @Column(name = "ID_NUMBER")
    private String idNumber;
    //就诊卡
    @Column(name = "CARD_ID")
    private String  cardID;
    //交易金额
    @Column(name = "MONEY")
    private Double money;
    //交易方式
    @Column(name = "PAY_TYPE")
    private String  payType;
    //支付订单号
    @Column(name = "TRADE_NO")
    private String  tradeNo;
    //订单时间
    @Column(name = "TRADE_DATE")
    private Date tradeDate;
    //订单结果集
    @Column(name = "TRADE_MSG")
    private String tradeMsg;

    //消费订单查询后插入数据结果集。
    //消费订单状态：00：成功，-订单查询结果
    @Column(name = "RESP_CODE")
    private int respCode;
    //消费订单结果
    @Column(name = "RESP_MSG")
    private String respMsg;
    //消费订单时间
    @Column(name = "RESP_DATE")
    private Date respDate;

    //HIS支付状态结果响应
    //his响应状态0 成功,1 失败,2 结果为空
    @Column(name = "HIS_CODE")
    private int hisCode;
    //his响应结果描述
    @Column(name = "HIS_MSG")
    private String hisMsg;
    //his交易流水号
    @Column(name = "HIS_SERIAL_NUMBER")
    private String hisSerialNumber;
    //his交易时间
    @Column(name = "HIS_DATE")
    private Date hisDate;


}
