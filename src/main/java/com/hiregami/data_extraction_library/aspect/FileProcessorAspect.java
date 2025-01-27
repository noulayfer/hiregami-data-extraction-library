package com.hiregami.data_extraction_library.aspect;

import com.hiregami.data_extraction_library.annotation.FileProcessor;
import com.hiregami.data_extraction_library.dto.ProfileContext;
import com.hiregami.data_extraction_library.strategy.FileParserStrategy;
import com.hiregami.data_extraction_library.strategy.StrategyFactory;
import java.io.InputStream;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@RequiredArgsConstructor
public class FileProcessorAspect {

  private final StrategyFactory strategyFactory;

  @Around("@annotation(fileProcessor)")
  public Object processFile(ProceedingJoinPoint joinPoint, FileProcessor fileProcessor)
      throws Throwable {
    Object[] args = joinPoint.getArgs();
    ProfileContext context = null;

    for (Object arg : args) {
      if (arg instanceof ProfileContext) {
        context = (ProfileContext) arg;
        break;
      }
    }

    if (Objects.isNull(context)) {
      throw new IllegalArgumentException("ProcessingContext is required as an argument");
    }

    String contentType = context.get("contentType", String.class);
    InputStream inputStream = context.get("inputStream", InputStream.class);

    FileParserStrategy strategy = strategyFactory.getStrategy(contentType);
    String parse = strategy.parse(inputStream);
    String parsedData = "Parsed data from " + contentType;
    System.out.println("FileProcessor: Parsed data = " + parsedData);

    context.set("parsedData", parse);

    return joinPoint.proceed(args);
  }
}
