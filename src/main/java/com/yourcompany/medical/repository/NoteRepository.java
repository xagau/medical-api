package com.yourcompany.medical.repository;

import com.yourcompany.medical.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByPatientId(Long patientId);
}
