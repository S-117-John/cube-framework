package com.cube.kiosk.modules.patient.repository;

import com.cube.kiosk.modules.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,String> {
}
