package com.cube.kiosk.modules.log.repository;

import com.cube.kiosk.modules.log.entity.TransLogDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransLogRepository extends JpaRepository<TransLogDO,String> {
}
