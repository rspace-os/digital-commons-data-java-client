package com.researchspace.dcd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DcdAccessToken {

  private @JsonProperty("access_token") String accessToken;
  private @JsonProperty("refresh_token") String refreshToken;
  private @JsonProperty("scope") String scope;
  private @JsonProperty("id_token") String idToken;
  private @JsonProperty("token_type") String type;
  private @JsonProperty("created_at") Long createdAt;
  private @JsonProperty("expires_in") Long expiresIn;
}