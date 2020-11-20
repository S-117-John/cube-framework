package com.cube.kiosk.modules.system.repository;

import com.cube.kiosk.modules.system.entity.HospitalDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<HospitalDO,String> {
}
