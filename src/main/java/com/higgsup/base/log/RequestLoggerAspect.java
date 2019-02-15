package com.higgsup.base.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.higgsup.base.common.WebUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.util.Collections;

@Aspect
@Component
public class RequestLoggerAspect {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Around("execution(@com.higgsup.base.log.RequestLogger * com.higgsup.base.controller.*.*(..)) && @annotation(requestLogger)")
  public Object logRequest(ProceedingJoinPoint joinPoint,
      RequestLogger requestLogger) throws Throwable {
    ContentCachingRequestWrapper request = getWrapper(joinPoint);
    if (request != null) {
      StringBuilder apiLog = new StringBuilder();
      apiLog.append("Rest API: ").append(request.getRequestURL().toString())
          .append("\n");
      apiLog.append("Body:").append(WebUtil.getRequestBody(request))
          .append("\n");
      for (String header : Collections.list(request.getHeaderNames())) {
        apiLog.append(header).append(":").append(request.getHeader(header))
            .append("\n");
      }
      logger.debug(apiLog.toString());
    } else {
      logger.info("Must define parameter HttpServletRequest in method for logging");
    }
    Object retVal = joinPoint.proceed();
    ObjectMapper objectMapper = new ObjectMapper();
    logger.debug("Response:" + objectMapper.writeValueAsString(retVal));
    return retVal;
  }

  private ContentCachingRequestWrapper getWrapper(
      ProceedingJoinPoint joinPoint) {

    Object[] args = joinPoint.getArgs();
    ContentCachingRequestWrapper request = null;
    for (Object arg : args) {
      if (arg instanceof ContentCachingRequestWrapper) {
        request = (ContentCachingRequestWrapper) arg;
        break;
      }
    }
    return request;
  }

}
