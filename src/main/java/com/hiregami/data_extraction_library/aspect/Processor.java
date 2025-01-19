package com.hiregami.data_extraction_library.aspect;

import com.hiregami.data_extraction_library.dto.ProcessingContext;

public interface Processor {
    void process(ProcessingContext context);
}

