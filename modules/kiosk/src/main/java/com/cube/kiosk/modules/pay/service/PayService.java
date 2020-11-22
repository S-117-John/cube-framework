package com.cube.kiosk.modules.pay.service;

import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.pay.model.PayParam;
import com.cube.kiosk.modules.pay.model.TransQueryParam;

public interface PayService {

    void getQrCode(PayParam payParam, ResultListener linstener);

    void queryResult(String qrCodeUrl, ResultListener linstener);

    void save(String tradeNo,ResultListener linstener);

    void saveHospitalized(String tradeNo,ResultListener linstener);
}
