package com.xagau.medical.controller;

import com.xagau.medical.model.Note;
import com.xagau.medical.model.Patient;
import com.xagau.medical.repository.NoteRepository;
import com.xagau.medical.repository.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

    private final NoteRepository noteRepository;
    private final PatientRepository patientRepository;

    public NoteController(NoteRepository noteRepository, PatientRepository patientRepository) {
        this.noteRepository = noteRepository;
        this.patientRepository = patientRepository;
    }

    @PostMapping("/{patientId}")
    public ResponseEntity<Note> addNote(@PathVariable Long patientId, @RequestBody Note note) {
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        if (!optionalPatient.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        note.setPatient(optionalPatient.get());
        return ResponseEntity.ok(noteRepository.save(note));
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<List<Note>> getPatientNotes(@PathVariable Long patientId) {
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        if (!optionalPatient.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(noteRepository.findByPatientId(patientId));
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long noteId) {
        if (!noteRepository.existsById(noteId)) {
            return ResponseEntity.notFound().build();
        }
        noteRepository.deleteById(noteId);
        return ResponseEntity.noContent().build();
    }
}
