package com.researchspace.dcd.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * When creating a new Deposition, identifiers related to the deposition can
 * be included such as URIs, DOIs, and others
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DcdRelatedIdentifier {
  private String descriptor;
  private String relation;
  @JsonProperty("resource_type")
  private String resourceType;
  private String identifier;
}

