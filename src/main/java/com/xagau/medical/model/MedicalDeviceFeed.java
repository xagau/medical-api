package com.xagau.medical.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MedicalDeviceFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String data;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private MedicalDevice device;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public MedicalDevice getDevice() {
        return device;
    }

    public void setDevice(MedicalDevice device) {
        this.device = device;
    }
}
