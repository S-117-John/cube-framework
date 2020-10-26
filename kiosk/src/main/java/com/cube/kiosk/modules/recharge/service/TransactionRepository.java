package com.cube.kiosk.modules.recharge.service;

import com.cube.kiosk.modules.pay.entity.TransactionRecordDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionRecordDO, String> {
}
