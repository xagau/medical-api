package com.yourcompany.medical.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime timestamp = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Note() {}

    public Note(String content, Patient patient) {
        this.content = content;
        this.patient = patient;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters...

    // Getter and setter for patient
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

   // Getter and setter for text
    public String getText() {
        return content;
    }

    public void setText(String text) {
        this.content = content;
    }

}
