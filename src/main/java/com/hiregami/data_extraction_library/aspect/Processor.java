package com.hiregami.data_extraction_library.aspect;

import com.hiregami.data_extraction_library.dto.ProfileContext;

public interface Processor {
  void process(ProfileContext context);
}
