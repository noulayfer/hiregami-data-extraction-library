package com.hiregami.data_extraction_library.strategy;

import java.io.InputStream;

public interface FileParserStrategy {
    boolean supports(String contentType);
    String parse(InputStream inputStream) throws Exception;

}
