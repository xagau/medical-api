package com.yourcompany.medical.model;

import javax.persistence.*;

@Entity
public class Condition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // Getters and setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }

    public Patient getPatient() { return patient; }

    public void setPatient(Patient patient) { this.patient = patient; }
}
