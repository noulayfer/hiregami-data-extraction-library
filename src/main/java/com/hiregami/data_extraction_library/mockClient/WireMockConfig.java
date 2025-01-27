package com.hiregami.data_extraction_library.mockClient;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.FeignHttpClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("mock")
@Configuration
@EnableFeignClients
public class WireMockConfig {

  @Bean
  public feign.Contract feignContract() {
    return new org.springframework.cloud.openfeign.support.SpringMvcContract();
  }

  @Bean
  public FeignHttpClientProperties.OkHttp okHttp() {
    return new FeignHttpClientProperties.OkHttp();
  }

  @Bean
  public WireMockServer wireMockServer() throws IOException {
    WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8090));
    wireMockServer.start();

    wireMockServer.stubFor(
        WireMock.get(WireMock.urlEqualTo("/candidate"))
            .willReturn(
                WireMock.aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(Files.readString(Path.of("src/main/resources/candidates.json")))));

    return wireMockServer;
  }
}
