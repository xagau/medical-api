package com.yourcompany.medical.model;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Embedded
    private Preferences preferences;

    //@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    //private List<MedicalFile> medicalFiles;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PdfMedicalFile> medicalFiles = new ArrayList<>();

    public List<PdfMedicalFile> getMedicalFiles() {
        return medicalFiles;
    }

    // Getters and setters omitted for brevity
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
 
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

}
