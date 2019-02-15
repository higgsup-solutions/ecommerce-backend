package com.higgsup.base.config;

import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomDispatcherServlet extends DispatcherServlet {

  @Override
  protected void doDispatch(HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if (!(request instanceof ContentCachingRequestWrapper)) {
      request = new ContentCachingRequestWrapper(request);
    }
    super.doDispatch(request, response);
  }

}
