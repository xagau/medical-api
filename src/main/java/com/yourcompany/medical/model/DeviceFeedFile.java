package com.yourcompany.medical.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DeviceFeedFile implements MedicalFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceType; // e.g. "HeartRateMonitor", "GlucoseSensor"

    private LocalDateTime recordedAt;

    private String dataFormat; // e.g. "JSON", "CSV", "FHIR"

    @Lob
    private String rawPayload;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private String data;
    @Override
    public String getType() {
        return "device_feed";
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getRawPayload() {
        return rawPayload;
    }

    public void setRawPayload(String rawPayload) {
        this.rawPayload = rawPayload;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String getContent() {
        return this.data;
    }

}
