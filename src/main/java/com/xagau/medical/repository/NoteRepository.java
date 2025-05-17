package com.xagau.medical.repository;

import com.xagau.medical.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByPatientId(Long patientId);
}
