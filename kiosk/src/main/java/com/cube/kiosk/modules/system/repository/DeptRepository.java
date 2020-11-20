package com.cube.kiosk.modules.system.repository;

import com.cube.kiosk.modules.system.entity.DeptDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptRepository extends JpaRepository<DeptDO,String> {
}
