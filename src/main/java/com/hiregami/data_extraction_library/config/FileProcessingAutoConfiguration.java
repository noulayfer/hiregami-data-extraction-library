package com.hiregami.data_extraction_library.config;

import com.hiregami.data_extraction_library.aspect.AwsComprehendAspect;
import com.hiregami.data_extraction_library.aspect.FileProcessorAspect;
import com.hiregami.data_extraction_library.service.AwsComprehendService;
import com.hiregami.data_extraction_library.strategy.StrategyFactory;
import com.hiregami.data_extraction_library.strategy.impl.PdfParserStrategy;
import com.hiregami.data_extraction_library.strategy.impl.WordParserStrategy;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.comprehend.ComprehendClient;

@Configuration
@EnableAspectJAutoProxy
public class FileProcessingAutoConfiguration {

  @Value("${aws.accessKeyId}")
  private String accessKeyId;
  @Value("aws.secretAccessKey")
  private String secretAccessKey;
  @Value("aws.region")
  private String region;


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

  @Bean
  public ComprehendClient comprehendClient() {
    AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretAccessKey);

    return ComprehendClient.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
            .build();
  }
}
