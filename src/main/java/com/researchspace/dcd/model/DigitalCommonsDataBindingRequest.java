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
public class DigitalCommonsDataBindingRequest {

  private boolean empty;
  private long size;

  @JsonProperty("ticket_id")
  private String ticketId;

  private String filename;
  private String description;

  @JsonProperty("parent_folder")
  private DcdParentFolder parentFolder;

  @JsonProperty("media_type")
  private String mediaType;

}