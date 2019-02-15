package com.higgsup.base.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(initApis())
        .build()
        .globalOperationParameters(initParameter())
        .apiInfo(apiInfo())
        .securityContexts(Lists.newArrayList(securityContext()))
        .securitySchemes(Lists.newArrayList(apiKey()));
  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
        "My REST API",
        "Some custom description of API.",
        "API_0_9",
        "Terms of service",
        new Contact("Back end Team", "www.higgsup.com",
            "backend.team@higgsup.com"),
        "License of API", "API license URL", Collections.emptyList());
  }

  private Predicate<RequestHandler> initApis() {
    return Predicates
        .or(RequestHandlerSelectors.basePackage("com.higgsup.base.controller"),
            RequestHandlerSelectors.basePackage("com.higgsup.base.security.endpoint"));
  }

  private List<Parameter> initParameter() {
    List<Parameter> parameters = new ArrayList<>();
    parameters.add(new ParameterBuilder()
        .name("Accept-Language")
        .description("Description of header")
        .modelRef(new ModelRef("string"))
        .parameterType("header")
        .required(false)
        .defaultValue("en")
        .build());
    return parameters;
  }

  private ApiKey apiKey() {
    return new ApiKey("Bearer", "X-Authorization", "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
        .securityReferences(defaultAuth())
        .forPaths(Predicates.and(PathSelectors.regex("/api/.*"), Predicates.not(PathSelectors.regex("/api/auth/.*"))))
        .build();
  }

  List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope
        = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Lists.newArrayList(
        new SecurityReference("Bearer", authorizationScopes));
  }
}

