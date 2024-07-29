package com.researchspace.dcd.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Metadata returned by the DigitalCommonsDataFile server upon a successful submission of a
 * file.
 */
@Data
public class DigitalCommonsDataBindingResponse {

  public String id;

  @JsonProperty("ticket_id")
  public String ticketId;

  public String filename;
  public String description;
  public long size;

  @JsonProperty("media_type")
  public String mediaType;

  @JsonProperty("sha256Hash")
  public String sha256_hash;

  @JsonProperty("$ref")
  public String ref;

  @JsonProperty("parent_folder")
  public DcdParentFolder parentFolder;

}



