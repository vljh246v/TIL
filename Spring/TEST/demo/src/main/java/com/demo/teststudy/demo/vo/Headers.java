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
"Accept",
"Accept-Encoding",
"Accept-Language",
"Content-Type",
"Host",
"Origin",
"Referer",
"Sec-Ch-Ua",
"Sec-Ch-Ua-Mobile",
"Sec-Fetch-Dest",
"Sec-Fetch-Mode",
"Sec-Fetch-Site",
"User-Agent",
"X-Amzn-Trace-Id"
})
public class Headers {

  @JsonProperty("Accept")
  private String accept;
  @JsonProperty("Accept-Encoding")
  private String acceptEncoding;
  @JsonProperty("Accept-Language")
  private String acceptLanguage;
  @JsonProperty("Content-Type")
  private String contentType;
  @JsonProperty("Host")
  private String host;
  @JsonProperty("Origin")
  private String origin;
  @JsonProperty("Referer")
  private String referer;
  @JsonProperty("Sec-Ch-Ua")
  private String secChUa;
  @JsonProperty("Sec-Ch-Ua-Mobile")
  private String secChUaMobile;
  @JsonProperty("Sec-Fetch-Dest")
  private String secFetchDest;
  @JsonProperty("Sec-Fetch-Mode")
  private String secFetchMode;
  @JsonProperty("Sec-Fetch-Site")
  private String secFetchSite;
  @JsonProperty("User-Agent")
  private String userAgent;
  @JsonProperty("X-Amzn-Trace-Id")
  private String xAmznTraceId;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("Accept")
  public String getAccept() {
  return accept;
  }

  @JsonProperty("Accept")
  public void setAccept(String accept) {
  this.accept = accept;
  }

  @JsonProperty("Accept-Encoding")
  public String getAcceptEncoding() {
  return acceptEncoding;
  }

  @JsonProperty("Accept-Encoding")
  public void setAcceptEncoding(String acceptEncoding) {
  this.acceptEncoding = acceptEncoding;
  }

  @JsonProperty("Accept-Language")
  public String getAcceptLanguage() {
  return acceptLanguage;
  }

  @JsonProperty("Accept-Language")
  public void setAcceptLanguage(String acceptLanguage) {
  this.acceptLanguage = acceptLanguage;
  }

  @JsonProperty("Content-Type")
  public String getContentType() {
  return contentType;
  }

  @JsonProperty("Content-Type")
  public void setContentType(String contentType) {
  this.contentType = contentType;
  }

  @JsonProperty("Host")
  public String getHost() {
  return host;
  }

  @JsonProperty("Host")
  public void setHost(String host) {
  this.host = host;
  }

  @JsonProperty("Origin")
  public String getOrigin() {
  return origin;
  }

  @JsonProperty("Origin")
  public void setOrigin(String origin) {
  this.origin = origin;
  }

  @JsonProperty("Referer")
  public String getReferer() {
  return referer;
  }

  @JsonProperty("Referer")
  public void setReferer(String referer) {
  this.referer = referer;
  }

  @JsonProperty("Sec-Ch-Ua")
  public String getSecChUa() {
  return secChUa;
  }

  @JsonProperty("Sec-Ch-Ua")
  public void setSecChUa(String secChUa) {
  this.secChUa = secChUa;
  }

  @JsonProperty("Sec-Ch-Ua-Mobile")
  public String getSecChUaMobile() {
  return secChUaMobile;
  }

  @JsonProperty("Sec-Ch-Ua-Mobile")
  public void setSecChUaMobile(String secChUaMobile) {
  this.secChUaMobile = secChUaMobile;
  }

  @JsonProperty("Sec-Fetch-Dest")
  public String getSecFetchDest() {
  return secFetchDest;
  }

  @JsonProperty("Sec-Fetch-Dest")
  public void setSecFetchDest(String secFetchDest) {
  this.secFetchDest = secFetchDest;
  }

  @JsonProperty("Sec-Fetch-Mode")
  public String getSecFetchMode() {
  return secFetchMode;
  }

  @JsonProperty("Sec-Fetch-Mode")
  public void setSecFetchMode(String secFetchMode) {
  this.secFetchMode = secFetchMode;
  }

  @JsonProperty("Sec-Fetch-Site")
  public String getSecFetchSite() {
  return secFetchSite;
  }

  @JsonProperty("Sec-Fetch-Site")
  public void setSecFetchSite(String secFetchSite) {
  this.secFetchSite = secFetchSite;
  }

  @JsonProperty("User-Agent")
  public String getUserAgent() {
  return userAgent;
  }

  @JsonProperty("User-Agent")
  public void setUserAgent(String userAgent) {
  this.userAgent = userAgent;
  }

  @JsonProperty("X-Amzn-Trace-Id")
  public String getXAmznTraceId() {
  return xAmznTraceId;
  }

  @JsonProperty("X-Amzn-Trace-Id")
  public void setXAmznTraceId(String xAmznTraceId) {
  this.xAmznTraceId = xAmznTraceId;
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