package com.researchspace.dcd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DcdDoi {
  private @JsonProperty("status") String status;
  private @JsonProperty("id") String id;
  private @JsonProperty("prefix") String prefix;
}
