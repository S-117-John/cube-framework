package com.cube.kiosk.modules.pay.repository;

import com.cube.kiosk.modules.pay.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice,String> {
}
