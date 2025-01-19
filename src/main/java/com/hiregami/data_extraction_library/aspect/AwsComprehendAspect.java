package com.hiregami.data_extraction_library.aspect;

import com.hiregami.data_extraction_library.annotation.AwsComprehendProcess;
import com.hiregami.data_extraction_library.dto.CandidateProfile;
import com.hiregami.data_extraction_library.dto.ProfileContext;
import com.hiregami.data_extraction_library.service.AwsComprehendService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
@RequiredArgsConstructor
public class AwsComprehendAspect {

  @Autowired private final AwsComprehendService awsComprehendService;

  @Around("@annotation(awsComprehendProcess)")
  public Object processAwsComprehend(
      ProceedingJoinPoint joinPoint, AwsComprehendProcess awsComprehendProcess) throws Throwable {
    Object[] args = joinPoint.getArgs();
    ProfileContext context = null;

    for (Object arg : args) {
      if (arg instanceof ProfileContext) {
        context = (ProfileContext) arg;
        break;
      }
    }

    if (context == null) {
      throw new IllegalArgumentException("ProcessingContext is required as an argument");
    }

    String parsedData = context.get("parsedData", String.class);

    CandidateProfile candidateProfile = new CandidateProfile();
    candidateProfile.setName("John Doe");
    candidateProfile.setSkills(List.of("Java", "Spring Boot", "AWS"));
    System.out.println("AwsComprehendProcess: Candidate profile created");

    context.set("candidateProfile", candidateProfile);

    return joinPoint.proceed(args);
  }
}
