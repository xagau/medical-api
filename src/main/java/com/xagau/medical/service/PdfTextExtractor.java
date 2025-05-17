package com.xagau.medical.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class PdfTextExtractor {
    private static final Logger logger = LoggerFactory.getLogger(PdfTextExtractor.class);

    public String extractText(byte[] pdfBytes) {
        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } catch (Exception e) {
            logger.error("Failed to extract text from PDF", e);
            return "[Error extracting PDF text]";
        }
    }
}
