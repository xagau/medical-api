package com.xagau.medical.controller;

import com.xagau.medical.model.MedicalDevice;
import com.xagau.medical.model.Patient;
import com.xagau.medical.repository.MedicalDeviceRepository;
import com.xagau.medical.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/devices")
public class DeviceController {

    @Autowired
    private MedicalDeviceRepository deviceRepository;

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping
    public MedicalDevice addDevice(@RequestParam Long patientId,
                                   @RequestBody MedicalDevice device) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        device.setPatient(patient);
        return deviceRepository.save(device);
    }

    @GetMapping
    public List<MedicalDevice> getAllDevices(@RequestParam(required = false) Long patientId) {
        if (patientId != null) {
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
            return deviceRepository.findByPatient(patient);
        } else {
            return deviceRepository.findAll();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteDevice(@PathVariable Long id) {
        deviceRepository.deleteById(id);
    }
}
