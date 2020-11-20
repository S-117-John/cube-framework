package com.cube.kiosk.modules.system.repository;

import com.cube.kiosk.modules.system.entity.DoctorDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<DoctorDO,String> {

    List<DoctorDO> findByDeptId(String id);
}
