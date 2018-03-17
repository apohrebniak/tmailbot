package com.github.apohrebniak.tmail.api.telegram.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.apohrebniak.tmail.api.telegram.domain.Update;
import java.util.List;
import lombok.Data;

@Data
public class UpdatesResponse {

  @JsonProperty("ok")
  private Boolean success;
  @JsonProperty("result")
  private List<Update> updates;
}
