package com.xagau.medical.repository;

import com.xagau.medical.model.MedicalDevice;
import com.xagau.medical.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalDeviceRepository extends JpaRepository<MedicalDevice, Long> {
    List<MedicalDevice> findByPatient(Patient patient);
}
