package com.researchspace.dcd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DigitalCommonsData Deposition, as returned by the API.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DigitalCommonsDataDataset {

  private String id;
  private String name;
  private String description;
  private String method;

  @JsonProperty("is_confidential")
  private boolean confidential;

  @JsonProperty("created_date")
  private Date createdDate;

  @JsonProperty("$ref")
  private String ref;
  private Licence licence;
  private String articles;
  private String categories;
  private String contributors;
  private String files;
  private String folders;
  private String versions;
  private String institutions;
  private Funders funders;

  @JsonProperty("related_links")
  private String relatedLinks;

  @JsonProperty("organisational_units")
  private String organisationalUnits;
  private String metadata;


  private DcdDoi doi;
  private int version;

  @JsonProperty("customer_id")
  private String customerId;
  private Owner owner;

  @Data
  class Funders {
    @JsonProperty("$ref")
    private String ref;
  }

  @Data
  class Licence {
    @JsonProperty("$ref")
    private String ref;
  }

  @Data
  class Owner {
    @JsonProperty("$ref")
    private String ref;
  }
}

