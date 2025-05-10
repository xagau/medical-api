package com.yourcompany.medical.model;

/**
 * Base interface for all medical files (e.g., PDF reports, device feeds).
 */
public interface MedicalFile {
    Long getId();
    String getType();
    String getContent();
}
