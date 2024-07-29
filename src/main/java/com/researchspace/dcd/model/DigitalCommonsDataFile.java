package com.researchspace.dcd.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Data;

/**
 * Metadata returned by the DigitalCommonsDataFile server upon a successful submission of a
 * file.
 */
@Data
public class DigitalCommonsDataFile {

  private String id;
  private String checksum;

  @JsonProperty("content_type")
  private String contentType;

  @JsonProperty("last_modified")
  private Date lastModified;

  @JsonProperty("content_length")
  private long contentLength;

  @JsonProperty("access_control")
  private String accessControl;
}
