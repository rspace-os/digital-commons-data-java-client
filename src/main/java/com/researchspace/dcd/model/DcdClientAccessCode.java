package com.researchspace.dcd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DcdClientAccessCode {
  private @JsonProperty("code") String code;
  private @JsonProperty("state") String state;
}
