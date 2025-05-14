package com.xagau.medical.repository;

import com.xagau.medical.model.TreatmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlan, Long> {
    List<TreatmentPlan> findByPatient_Id(Long patientId);
}
