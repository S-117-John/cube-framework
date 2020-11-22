package com.cube.kiosk.modules.pay.repository;

import com.cube.kiosk.modules.pay.model.TransactionData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionData,String> {

    TransactionData findByScanCode(String scanCode);

    TransactionData findByTradeNoAndTranType(String tradeNo,String tranType);

    TransactionData findByMerTradeNoAndTranType(String merTradeNo,String tranType);
}
