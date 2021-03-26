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
@JsonPropertyOrder({
"args",
"headers",
"origin",
"url"
})
public class Rest {

  @JsonProperty("args")
  private Args args;
  @JsonProperty("headers")
  private Headers headers;
  @JsonProperty("origin")
  private String origin;
  @JsonProperty("url")
  private String url;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("args")
  public Args getArgs() {
  return args;
  }

  @JsonProperty("args")
  public void setArgs(Args args) {
  this.args = args;
  }

  @JsonProperty("headers")
  public Headers getHeaders() {
  return headers;
  }

  @JsonProperty("headers")
  public void setHeaders(Headers headers) {
  this.headers = headers;
  }

  @JsonProperty("origin")
  public String getOrigin() {
  return origin;
  }

  @JsonProperty("origin")
  public void setOrigin(String origin) {
  this.origin = origin;
  }

  @JsonProperty("url")
  public String getUrl() {
  return url;
  }

  @JsonProperty("url")
  public void setUrl(String url) {
  this.url = url;
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