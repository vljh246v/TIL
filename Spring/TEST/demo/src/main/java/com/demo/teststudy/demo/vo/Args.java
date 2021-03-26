package com.demo.teststudy.demo.vo;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"a"})
public class Args {

  @JsonProperty("a")
  private String a;

  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("a")
  public String getA() {
  return a;
  }

  @JsonProperty("a")
  public void setA(String a) {
  this.a = a;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
  return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
  this.additionalProperties.put(name, value);
  }

}