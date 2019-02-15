package com.higgsup.base.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.higgsup.base.common.ApplicationSecurityProperty;
import com.higgsup.base.security.CustomCorsFilter;
import com.higgsup.base.security.RestAuthenticationEntryPoint;
import com.higgsup.base.security.auth.ajax.AjaxAuthenticationProvider;
import com.higgsup.base.security.auth.ajax.AjaxLoginProcessingFilter;
import com.higgsup.base.security.auth.jwt.JwtAuthenticationProvider;
import com.higgsup.base.security.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import com.higgsup.base.security.auth.jwt.SkipPathRequestMatcher;
import com.higgsup.base.security.auth.jwt.extractor.TokenExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * WebSecurityConfig
 *
 * @author vladimir.stankovic
 * <p>
 * Aug 3, 2016
 */
@Configuration
@EnableWebSecurity
@ConfigurationProperties(prefix = "higgsup.security.config")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  public static final String AUTHENTICATION_HEADER_NAME = "X-Authorization";

  private static final String AUTHENTICATION_URL = "/api/auth/login";

  private static final String REFRESH_TOKEN_URL = "/api/auth/token";

  private static final String API_ROOT_URL = "/api/**";

  private String[] ignoreUrls;

  @Autowired
  private ApplicationSecurityProperty applicationSecurityProperty;

  @Autowired
  private RestAuthenticationEntryPoint authenticationEntryPoint;

  @Autowired
  private AuthenticationSuccessHandler successHandler;

  @Autowired
  private AuthenticationFailureHandler failureHandler;

  @Autowired
  private AjaxAuthenticationProvider ajaxAuthenticationProvider;

  @Autowired
  private JwtAuthenticationProvider jwtAuthenticationProvider;

  @Autowired
  private TokenExtractor tokenExtractor;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private ObjectMapper objectMapper;

  protected AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter(
      String loginEntryPoint) throws Exception {
    AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(
        loginEntryPoint, successHandler, failureHandler, objectMapper);
    filter.setAuthenticationManager(this.authenticationManager);
    return filter;
  }

  protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter(
      List<String> pathsToSkip, String pattern) throws Exception {
    SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip,
        pattern);
    JwtTokenAuthenticationProcessingFilter filter
        = new JwtTokenAuthenticationProcessingFilter(failureHandler,
        tokenExtractor, matcher);
    filter.setAuthenticationManager(this.authenticationManager);
    return filter;
  }

  protected CustomCorsFilter buildCustomCorsFilter() {

    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setAllowedOrigins(Arrays
        .asList(applicationSecurityProperty.getCors().getAllowedOrigin()));
    config.addAllowedHeader(
        applicationSecurityProperty.getCors().getAllowedHeader());
    config.setMaxAge(applicationSecurityProperty.getCors().getMaxAge());
    config.setAllowedMethods(Arrays
        .asList(applicationSecurityProperty.getCors().getAllowedMethod()));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/api/**", config);

    return new CustomCorsFilter(source);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(ajaxAuthenticationProvider);
    auth.authenticationProvider(jwtAuthenticationProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    List<String> permitAllEndpointList = new ArrayList<>(Arrays.asList(
        AUTHENTICATION_URL,
        REFRESH_TOKEN_URL
    ));
    if (!StringUtils.isEmpty(ignoreUrls)) {
      permitAllEndpointList.addAll(Arrays.asList(ignoreUrls));
    }
    http
        .csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(this.authenticationEntryPoint)

        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and()
        .authorizeRequests()
        .antMatchers(permitAllEndpointList
            .toArray(new String[permitAllEndpointList.size()]))
        .permitAll()
        .and()
        .authorizeRequests()
        .antMatchers(API_ROOT_URL).authenticated()
        .and()
        .addFilterBefore(buildCustomCorsFilter(),
            UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(buildAjaxLoginProcessingFilter(AUTHENTICATION_URL),
            UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(
            buildJwtTokenAuthenticationProcessingFilter(permitAllEndpointList,
                API_ROOT_URL), UsernamePasswordAuthenticationFilter.class);
  }

  public void setIgnoreUrls(String[] ignoreUrls) {
    this.ignoreUrls = ignoreUrls;
  }
}
