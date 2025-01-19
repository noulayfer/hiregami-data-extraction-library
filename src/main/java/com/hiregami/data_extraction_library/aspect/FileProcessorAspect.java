package com.hiregami.data_extraction_library.aspect;

import com.hiregami.data_extraction_library.annotation.FileProcessor;
import com.hiregami.data_extraction_library.dto.ProcessingContext;
import com.hiregami.data_extraction_library.strategy.FileParserStrategy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Aspect
@Component
public class FileProcessorAspect {

    @Autowired
    private List<FileParserStrategy> strategies;

    @Around("@annotation(fileProcessor)")
    public Object processFile(ProceedingJoinPoint joinPoint, FileProcessor fileProcessor) throws Throwable {
        Object[] args = joinPoint.getArgs();
        ProcessingContext context = null;

        // Extract the ProcessingContext
        for (Object arg : args) {
            if (arg instanceof ProcessingContext) {
                context = (ProcessingContext) arg;
                break;
            }
        }

        if (context == null) {
            throw new IllegalArgumentException("ProcessingContext is required as an argument");
        }

        // Extract contentType and inputStream from the context
        String contentType = context.get("contentType", String.class);
        InputStream inputStream = context.get("inputStream", InputStream.class);

        // Simulate parsing logic
        String parsedData = "Parsed data from " + contentType; // Replace with actual parsing logic
        System.out.println("FileProcessor: Parsed data = " + parsedData);

        // Set the parsedData in the context
        context.set("parsedData", parsedData);

        // Proceed with the original method
        return joinPoint.proceed(args);
    }
}


