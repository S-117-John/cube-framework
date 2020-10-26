package com.cube.core.system.repository;

import com.cube.core.system.entity.SystemLogDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemLogRepository extends JpaRepository<SystemLogDO,String> {
}
