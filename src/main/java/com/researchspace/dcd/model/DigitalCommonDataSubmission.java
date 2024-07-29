package com.researchspace.dcd.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * When creating a new Deposition, metadata may be attached to the request that
 * will be associated with the new Deposition.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DigitalCommonDataSubmission {

  /*
   * If a DigitalCommonsDataSubmission is sent with the request to create a new dataset
   */
  private String title;
  private String description;

  @JsonProperty("upload_type")
  private String uploadType;

}
