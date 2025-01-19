package com.hiregami.data_extraction_library.config;

import com.hiregami.data_extraction_library.aspect.AwsComprehendAspect;
import com.hiregami.data_extraction_library.aspect.FileProcessorAspect;
import com.hiregami.data_extraction_library.service.AwsComprehendService;
import com.hiregami.data_extraction_library.strategy.StrategyFactory;
import com.hiregami.data_extraction_library.strategy.impl.PdfParserStrategy;
import com.hiregami.data_extraction_library.strategy.impl.WordParserStrategy;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class FileProcessingAutoConfiguration {

  @Bean
  public StrategyFactory strategyFactory() {
    return new StrategyFactory(List.of(pdfParserStrategy(), wordParserStrategy()));
  }

  @Bean
  public PdfParserStrategy pdfParserStrategy() {
    return new PdfParserStrategy();
  }

  @Bean
  public WordParserStrategy wordParserStrategy() {
    return new WordParserStrategy();
  }

  @Bean
  public FileProcessorAspect fileProcessorAspect() {
    return new FileProcessorAspect();
  }

  @Bean
  public AwsComprehendAspect awsComprehendAspect(AwsComprehendService awsComprehendService) {
    return new AwsComprehendAspect(awsComprehendService);
  }

  @Bean
  public AwsComprehendService awsComprehendService() {
    return new AwsComprehendService();
  }
}
