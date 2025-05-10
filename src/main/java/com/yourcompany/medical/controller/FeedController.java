package com.yourcompany.medical.controller;

import com.yourcompany.medical.model.MedicalDevice;
import com.yourcompany.medical.model.MedicalDeviceFeed;
import com.yourcompany.medical.repository.MedicalDeviceFeedRepository;
import com.yourcompany.medical.repository.MedicalDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feeds")
public class FeedController {

    @Autowired
    private MedicalDeviceFeedRepository feedRepository;

    @Autowired
    private MedicalDeviceRepository deviceRepository;

    @PostMapping
    public MedicalDeviceFeed addFeed(@RequestParam Long deviceId,
                                     @RequestBody MedicalDeviceFeed feed) {
        MedicalDevice device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("Device not found"));
        feed.setDevice(device);
        return feedRepository.save(feed);
    }

    @GetMapping
    public List<MedicalDeviceFeed> getFeeds(@RequestParam(required = false) Long deviceId) {
        if (deviceId != null) {
            MedicalDevice device = deviceRepository.findById(deviceId)
                    .orElseThrow(() -> new IllegalArgumentException("Device not found"));
            return feedRepository.findByDevice(device);
        } else {
            return feedRepository.findAll();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteFeed(@PathVariable Long id) {
        feedRepository.deleteById(id);
    }
}
