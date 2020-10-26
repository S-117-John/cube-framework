package com.cube.kiosk.modules.log.repository;

import com.cube.kiosk.modules.log.entity.HisLogDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HisLogRepository extends JpaRepository<HisLogDO,String> {
}
