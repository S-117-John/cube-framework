package com.cube.kiosk.modules.pay.service;

import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.pay.model.RequestDataPay;

public interface PayService {

    void doPost(RequestDataPay requestDataPay, ResultListener linstener);

    void queryResult(RequestDataPay requestDataPay, ResultListener linstener);
}
