package com.xagau.medical.repository;

import com.xagau.medical.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    // Find patient by email for authentication
    Patient findByEmail(String email);
}
