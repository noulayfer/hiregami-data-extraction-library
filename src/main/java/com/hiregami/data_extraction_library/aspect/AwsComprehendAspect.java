package com.hiregami.data_extraction_library.aspect;

import com.hiregami.data_extraction_library.annotation.AwsComprehendProcess;
import com.hiregami.data_extraction_library.contextProvider.ApplicationContextProvider;
import com.hiregami.data_extraction_library.dto.ProfileContext;
import com.hiregami.data_extraction_library.service.AwsComprehendService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.comprehend.ComprehendClient;
import software.amazon.awssdk.services.comprehend.model.DetectEntitiesRequest;
import software.amazon.awssdk.services.comprehend.model.DetectEntitiesResponse;

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

    ComprehendClient comprehendClient = ApplicationContextProvider.getBean(ComprehendClient.class);
    DetectEntitiesRequest request =
        DetectEntitiesRequest.builder().text(parsedData).languageCode("en").build();

    DetectEntitiesResponse response = comprehendClient.detectEntities(request);

    response
        .entities()
        .forEach(
            entity -> {
              System.out.printf(
                  "Entity: %s, Type: %s, Score: %f%n",
                  entity.text(), entity.typeAsString(), entity.score());
            });

    return joinPoint.proceed();
  }
}
