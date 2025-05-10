package com.xagau.medical.repository;

import com.xagau.medical.model.MedicalDevice;
import com.xagau.medical.model.MedicalDeviceFeed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalDeviceFeedRepository extends JpaRepository<MedicalDeviceFeed, Long> {
    List<MedicalDeviceFeed> findByDevice(MedicalDevice device);
}
