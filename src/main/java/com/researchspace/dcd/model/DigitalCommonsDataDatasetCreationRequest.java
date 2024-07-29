package com.researchspace.dcd.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DigitalCommonsDataDatasetCreationRequest {
  private final boolean empty = false;
  private String name;
  private String description;
  private String method;
  @JsonProperty("is_confidential")
  private final boolean confidential = false;
}