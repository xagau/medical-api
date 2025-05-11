package com.xagau.medical.model;

import javax.persistence.*;

@Entity
@Table(name = "pdf_medical_files")
public class PdfMedicalFile implements MedicalFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type = "pdf";

    private String fileName;

    private String contentType;

    private String description;

    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getContent() {
        return new String(data);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getData() {
        return data;
    }

    public Long getPatientId() {
        return patient.getId();
    }


}
