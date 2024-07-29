package com.researchspace.dcd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DcdDataset {
  private @JsonProperty("id") String id;
  private @JsonProperty("name") String name;
  private @JsonProperty("description") String description;
  private @JsonProperty("doi") DcdDoi doi;
}


