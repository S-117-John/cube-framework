package com.cube.kiosk.modules.hardware.repository;

import com.cube.kiosk.modules.hardware.entity.HardWareRecordDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HardWareRecordRepository extends JpaRepository<HardWareRecordDO,String> {
}
