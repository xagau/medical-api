package com.xagau.medical.repository;

import com.xagau.medical.model.DeviceFeedFile;
import com.xagau.medical.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceFeedFileRepository extends JpaRepository<DeviceFeedFile, Long> {
    List<DeviceFeedFile> findByPatient(Patient patient);
}
