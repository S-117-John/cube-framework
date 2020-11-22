package com.cube.kiosk.modules.security.repository;

import com.cube.kiosk.modules.security.model.HardWareConfigDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HardWareRepository extends JpaRepository<HardWareConfigDO,String> {

    HardWareConfigDO findByIp(String ip);
}
