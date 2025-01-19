package com.hiregami.data_extraction_library.strategy.impl;

import com.hiregami.data_extraction_library.strategy.FileParserStrategy;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.InputStream;

public class PdfParserStrategy implements FileParserStrategy {
    @Override
    public boolean supports(String contentType) {
        return "application/pdf".equalsIgnoreCase(contentType);
    }

    @Override
    public String parse(InputStream inputStream) throws Exception {
        try (PDDocument document = PDDocument.load(inputStream)) {
            return new PDFTextStripper().getText(document);
        }
    }
}
