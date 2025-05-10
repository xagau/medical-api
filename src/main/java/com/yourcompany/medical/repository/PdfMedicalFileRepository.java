package com.yourcompany.medical.repository;

import com.yourcompany.medical.model.PdfMedicalFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PdfMedicalFileRepository extends JpaRepository<PdfMedicalFile, Long> {
    List<PdfMedicalFile> findByPatientId(Long patientId);
}
