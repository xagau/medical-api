package com.yourcompany.medical.repository;

import com.yourcompany.medical.model.MedicalDevice;
import com.yourcompany.medical.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalDeviceRepository extends JpaRepository<MedicalDevice, Long> {
    List<MedicalDevice> findByPatient(Patient patient);
}
