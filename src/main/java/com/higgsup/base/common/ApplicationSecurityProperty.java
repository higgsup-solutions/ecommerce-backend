/*
 * Copyright (c) 2018 Azeus Systems Holdings Limited. All rights reserved.
 *
 * This software is the confidential and proprietary information of Azeus
 * Systems Holdings, Ltd. ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with Azeus.
 */

package com.higgsup.base.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <b>Program ID</b>: NA <br>
 * <br>
 *
 * <b>Mode</b>: <!-- Online/Batch/Library (Library by default) --> Library <br>
 * <br>
 *
 * <b>Program Name</b>: NA <br>
 * <br>
 *
 * <b>Description</b>: Class/Interface/Enum description here
 * <br>
 * <br>
 *
 * <b>Programming Environment</b>:
 *
 * <table cellspacing="0" cellpadding="0" class="PCMS2">
 * <tr class="header">
 * <td>Related Source</td>
 * <td>Compiler</td>
 * </tr>
 * <p>
 * <!-- Programming Environment Entries -->
 *
 * <tr>
 * <td>NA</td>
 * <td>NA</td>
 * </tr>
 * </table>
 *
 * <br>
 *
 * <b>File Usage</b>: <br>
 * <p>
 * File usage here
 *
 * <pre>
 *   Enclose sample code usage in &lt;pre&gt; tags
 * </pre>
 *
 * <b>Input Parameters</b>:
 *
 * <table cellspacing="0" cellpadding="0" class="PCMS2">
 * <tr class="header">
 * <td>Parameter</td>
 * <td>Type</td>
 * <td>Format</td>
 * <td>Mandatory</td>
 * <td>Description</td>
 * </tr>
 * <p>
 * <!-- Input Parameter Entries -->
 *
 * <tr>
 * <td>NA</td>
 * <td>NA</td>
 * <td>NA</td>
 * <td>NA</td>
 * <td>NA</td>
 * </tr>
 * </table>
 *
 * <br>
 *
 * <b>Screens Used</b>: NA <br>
 * <br>
 *
 * <b>Processing Logic</b>: <br>
 * <p>
 * Place processing logic here.
 *
 * <br>
 * <br>
 *
 * <b>External References</b>:
 *
 * <table cellspacing="0" cellpadding="0" class="PCMS2">
 * <tr class="header">
 * <td>Reference</td>
 * <td>Description</td>
 * </tr>
 * <p>
 * <!-- External References Entries -->
 * <tr>
 * <td>NA</td>
 * <td>NA</td>
 * </tr>
 * </table>
 *
 * <br>
 *
 * <b>Program Limits</b>: NA <br>
 *
 * <ul>
 * <!-- List Program Limits here using
 * <li></li>
 * tags -->
 * </ul>
 *
 * <br>
 *
 * <b>Unit Test Record</b>: NA <br>
 * <br>
 *
 * <b>Amendment History</b>:
 *
 * <table cellspacing="0" cellpadding="0" class="PCMS2">
 * <tr class="header">
 * <td>Reference No.</td>
 * <td>Date (MMM-DD-YYYY)</td>
 * <td>Author</td>
 * <td>Description</td>
 * </tr>
 * <p>
 * <!-- Amendment History Entries -->
 *
 * <tr>
 * <td>&nbsp;</td>
 * <td></td>
 * <td></td>
 * <td></td>
 * </tr>
 * </table>
 *
 * <br>
 *
 * <b>File Created</b>: Feb 12, 2019
 *
 * <br>
 * <br>
 *
 * <b>Author</b>: quytm
 */
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
