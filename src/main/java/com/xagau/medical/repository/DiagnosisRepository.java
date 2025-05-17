package com.xagau.medical.repository;

import com.xagau.medical.model.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
    List<Diagnosis> findByPatient_Id(Long patientId);
}
