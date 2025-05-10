package com.yourcompany.medical.repository;

import com.yourcompany.medical.model.MedicalDevice;
import com.yourcompany.medical.model.MedicalDeviceFeed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalDeviceFeedRepository extends JpaRepository<MedicalDeviceFeed, Long> {
    List<MedicalDeviceFeed> findByDevice(MedicalDevice device);
}
