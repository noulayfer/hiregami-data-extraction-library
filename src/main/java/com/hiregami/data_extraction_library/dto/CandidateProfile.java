package com.hiregami.data_extraction_library.dto;

import java.util.List;
import lombok.Data;

@Data
public class CandidateProfile {
  private String name;
  private List<String> skills;
}
