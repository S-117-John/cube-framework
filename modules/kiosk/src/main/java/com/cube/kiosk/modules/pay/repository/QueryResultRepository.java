package com.cube.kiosk.modules.pay.repository;

import com.cube.kiosk.modules.pay.model.QueryResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryResultRepository extends JpaRepository<QueryResult,String> {
}
