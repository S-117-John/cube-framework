package com.cube.kiosk.modules.recharge.service;

import com.cube.kiosk.modules.common.ResponseBank;
import com.cube.kiosk.modules.common.model.PatientInfo;
import com.cube.kiosk.modules.recharge.linstener.RechargeLinstener;

public interface RechargeService {

    /**
     * 获取二维码
     * @param money
     * @return
     */
    String getQrCode(Double money) throws Exception;

    /**
     * 查询消费订单-就诊卡充值
     * @param tradeNo ：支付订单号
     * @return
     * @throws Exception
     */
    void getConsumer0rderCard(String tradeNo, RechargeLinstener linstener);

    /**
     * 查询消费订单-住院预交金
     * @param tradeNo ：支付订单号
     * @return
     * @throws Exception
     */
    void getConsumer0rderPaymen(String tradeNo, RechargeLinstener linstener);

    /**
     * 关闭订单
     * @param tradeNo：支付订单号
     * @param linstener
     */
    void closeOrder(String tradeNo, RechargeLinstener linstener);

    /**
     * 申请退款
     * @param tradeNo:支付订单号
     * @param vfTradeNo
     * @param linstener
     */
    void applyForRefund(String tradeNo, String vfTradeNo, RechargeLinstener linstener);

    /**
     * 查询退款订单
     * @param vfTradeNo
     * @param linstener
     */
    void getApplyForRefund(String vfTradeNo, RechargeLinstener linstener);
    /**
     * 查询门诊患者基本信息
     * @param cardId
     * @return
     */
    String getPatientnameInfo(String cardId) throws Exception;

    /**
     * 在线建卡
     * @param patientInfo
     * @param linstener
     */
    void cardIssuers(PatientInfo patientInfo, RechargeLinstener linstener);

    /**
     * 查询就在卡余额
     * @return
     * @throws Exception
     */
    void getBalance(String patientName, String identitycard, RechargeLinstener linstener);

    /**
     * 就诊卡充值-His
     * @param patientInfo
     * @param linstener
     */
    void payMedicalCard(PatientInfo patientInfo, ResponseBank responseBank, RechargeLinstener linstener);

    /**
     * 查询住院患者基本信息
     * @param inHosid:住院号
     * @param linstener
     */
    void getPatientInfo(String inHosid, RechargeLinstener linstener);

    /**
     * 查询住院预交金余额
     * @param inHosid
     * @param linstener
     */
    void paymenPrepaidBalance(String inHosid, RechargeLinstener linstener);

    /**
     * 住院预交金充值
     * @param patientInfo
     * @param responseBank
     * @param linstener
     */
    void paymenPrepaid(PatientInfo patientInfo, ResponseBank responseBank, RechargeLinstener linstener);
}
