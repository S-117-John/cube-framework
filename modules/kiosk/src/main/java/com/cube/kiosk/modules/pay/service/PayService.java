package com.cube.kiosk.modules.pay.service;

import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.pay.model.PayParam;
import com.cube.kiosk.modules.pay.model.TransQueryParam;
import com.cube.kiosk.modules.pay.model.TransactionData;

public interface PayService {

    void getQrCode(PayParam payParam, ResultListener linstener);

    TransactionData queryResult(String qrCodeUrl);

    void save(String tradeNo,ResultListener linstener);

    void saveHospitalized(String tradeNo,ResultListener linstener);

    String saveHos(String tradNo);
}
