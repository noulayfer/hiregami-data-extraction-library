package com.hiregami.data_extraction_library.dto;

import java.util.List;
import lombok.Data;

@Data
public class CandidateProfile {
  private String name;
  private String surname;
  private Integer age;
  private String developerType;
  private List<String> programmingLanguages;
  private List<String> skills;
  private List<String> languages;
}
