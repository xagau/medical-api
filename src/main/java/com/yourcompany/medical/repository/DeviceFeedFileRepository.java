package com.yourcompany.medical.repository;

import com.yourcompany.medical.model.DeviceFeedFile;
import com.yourcompany.medical.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceFeedFileRepository extends JpaRepository<DeviceFeedFile, Long> {
    List<DeviceFeedFile> findByPatient(Patient patient);
}
