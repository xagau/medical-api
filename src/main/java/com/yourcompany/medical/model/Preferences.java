package com.yourcompany.medical.model;

import javax.persistence.Embeddable;

@Embeddable
public class Preferences {
    private String summaryStyle;
    private String language;

    // Getters and setters
    public String getSummaryStyle() {
        return summaryStyle;
    }
    public void setSummaryStyle(String summaryStyle) {
        this.summaryStyle = summaryStyle;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
}
