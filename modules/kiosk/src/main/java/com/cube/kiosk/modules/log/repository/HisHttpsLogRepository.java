package com.cube.kiosk.modules.log.repository;

import com.cube.kiosk.modules.log.entity.HisHttpsLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HisHttpsLogRepository extends JpaRepository<HisHttpsLog,String> {
}
