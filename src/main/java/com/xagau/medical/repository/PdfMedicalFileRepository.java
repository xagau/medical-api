package com.xagau.medical.repository;

import com.xagau.medical.model.PdfMedicalFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PdfMedicalFileRepository extends JpaRepository<PdfMedicalFile, Long> {
    List<PdfMedicalFile> findByPatient(Long patient);
}
