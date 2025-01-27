package com.hiregami.data_extraction_library.mockClient;

import com.hiregami.data_extraction_library.dto.CandidateProfile;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
    name = "mockComprehendClient",
    url = "${mock.api.url}",
    configuration = WireMockConfig.class)
public interface MockFeignClient {
  @GetMapping(value = "/candidates")
  List<CandidateProfile> getMockCandidates();
}
