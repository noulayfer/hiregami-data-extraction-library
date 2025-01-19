package com.hiregami.data_extraction_library.strategy.impl;


import com.hiregami.data_extraction_library.strategy.FileParserStrategy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Component;

import java.io.InputStream;

public class WordParserStrategy implements FileParserStrategy {

    @Override
    public boolean supports(String contentType) {
        return "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equalsIgnoreCase(contentType);
    }

    @Override
    public String parse(InputStream inputStream) throws Exception {
        XWPFDocument document = new XWPFDocument(inputStream);
        return document.getParagraphs().stream()
                .map(XWPFParagraph::getText)
                .reduce("", (a, b) -> a + "\n" + b);
    }
}

