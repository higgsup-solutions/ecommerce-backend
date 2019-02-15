package com.higgsup.base.security;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

public class CustomCorsFilter extends CorsFilter {

  public CustomCorsFilter(UrlBasedCorsConfigurationSource configSource) {
    super(configSource);
  }

}
