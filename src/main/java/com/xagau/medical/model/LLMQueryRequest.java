package com.xagau.medical.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for querying the LLM assistant.")
public class LLMQueryRequest {
    @Schema(description = "User's query for the LLM.", example = "What are the latest guidelines for hypertension?")
    private String query;

    @Schema(description = "Patient ID to provide context.", example = "12345")
    private String patientId;

    @Schema(description = "Whether to include patient context.", example = "true", defaultValue = "false")
    private Boolean includeContext = false;

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public Boolean getIncludeContext() { return includeContext; }
    public void setIncludeContext(Boolean includeContext) { this.includeContext = includeContext; }
}
