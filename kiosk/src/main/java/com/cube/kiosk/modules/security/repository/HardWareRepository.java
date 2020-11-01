package com.cube.kiosk.modules.security.repository;

import com.cube.kiosk.modules.security.model.HardWareDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HardWareRepository extends JpaRepository<HardWareDO,String> {

    HardWareDO findByIp(String ip);
}
