package com.higgsup.xshop.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "higgsup.security")
public class ApplicationSecurityProperty {

  private final Jwt jwt = new Jwt();

  private final Cors cors = new Cors();

  public Jwt getJwt() {
    return jwt;
  }

  public Cors getCors() {
    return cors;
  }

  public static class Jwt {


    private Long tokenExpirationTime;

    private Long refreshTokenExpTime;

    private String tokenIssuer;

    private String tokenSigningKey;

    public void setTokenExpirationTime(Long tokenExpirationTime) {
      this.tokenExpirationTime = tokenExpirationTime;
    }

    public void setRefreshTokenExpTime(Long refreshTokenExpTime) {
      this.refreshTokenExpTime = refreshTokenExpTime;
    }

    public void setTokenIssuer(String tokenIssuer) {
      this.tokenIssuer = tokenIssuer;
    }

    public void setTokenSigningKey(String tokenSigningKey) {
      this.tokenSigningKey = tokenSigningKey;
    }

    public Long getTokenExpirationTime() {
      return tokenExpirationTime;
    }

    public Long getRefreshTokenExpTime() {
      return refreshTokenExpTime;
    }

    public String getTokenIssuer() {
      return tokenIssuer;
    }

    public String getTokenSigningKey() {
      return tokenSigningKey;
    }
  }

  public static class Cors {

    private String allowedOrigin;

    private String allowedHeader;

    private Long maxAge;

    private String[] allowedMethod;

    public void setAllowedOrigin(String allowedOrigin) {
      this.allowedOrigin = allowedOrigin;
    }

    public void setAllowedHeader(String allowedHeader) {
      this.allowedHeader = allowedHeader;
    }

    public void setMaxAge(Long maxAge) {
      this.maxAge = maxAge;
    }

    public void setAllowedMethod(String[] allowedMethod) {
      this.allowedMethod = allowedMethod;
    }

    public String getAllowedOrigin() {
      return allowedOrigin;
    }

    public String getAllowedHeader() {
      return allowedHeader;
    }

    public Long getMaxAge() {
      return maxAge;
    }

    public String[] getAllowedMethod() {
      return allowedMethod;
    }
  }
}
