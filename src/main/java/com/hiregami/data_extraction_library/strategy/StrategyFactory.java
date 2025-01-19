package com.hiregami.data_extraction_library.strategy;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StrategyFactory {
  List<FileParserStrategy> strategies;

  public FileParserStrategy getStrategy(String contentType) {
    return strategies.stream()
        .filter(strategy -> strategy.supports(contentType))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Not supported"));
  }
}
