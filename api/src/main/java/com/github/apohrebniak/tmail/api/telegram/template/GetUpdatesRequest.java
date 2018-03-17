package com.github.apohrebniak.tmail.api.telegram.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

@JsonInclude(Include.NON_NULL)
@Builder
public class GetUpdatesRequest {

  @JsonProperty
  private Long offset;
  @JsonProperty
  private Long limit;
  @JsonProperty
  private Long timeout;
  @JsonProperty("allowed_updates")
  private List<String> allowedUpdates;
}
