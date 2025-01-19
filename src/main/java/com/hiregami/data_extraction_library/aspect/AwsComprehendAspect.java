package com.hiregami.data_extraction_library.aspect;

import com.hiregami.data_extraction_library.annotation.AwsComprehendProcess;
import com.hiregami.data_extraction_library.dto.CandidateProfile;
import com.hiregami.data_extraction_library.dto.ProcessingContext;
import com.hiregami.data_extraction_library.service.AwsComprehendService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@RequiredArgsConstructor
public class AwsComprehendAspect {

    @Autowired
    private final AwsComprehendService awsComprehendService;

    @Around("@annotation(awsComprehendProcess)")
    public Object processAwsComprehend(ProceedingJoinPoint joinPoint, AwsComprehendProcess awsComprehendProcess) throws Throwable {
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

        // Retrieve parsedData from the context
        String parsedData = context.get("parsedData", String.class);

        // Simulate AWS Comprehend logic
        CandidateProfile candidateProfile = new CandidateProfile();
        candidateProfile.setName("John Doe");
        candidateProfile.setSkills(List.of("Java", "Spring Boot", "AWS"));
        System.out.println("AwsComprehendProcess: Candidate profile created");

        // Set the candidateProfile in the context
        context.set("candidateProfile", candidateProfile);

        // Proceed with the original method
        return joinPoint.proceed(args);
    }
}
