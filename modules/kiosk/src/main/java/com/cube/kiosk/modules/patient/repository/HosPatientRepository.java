package com.cube.kiosk.modules.patient.repository;

import com.cube.kiosk.modules.patient.model.HosPatientDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HosPatientRepository extends JpaRepository<HosPatientDO,String> {
}
