package com.higgsup.xshop.exception;

import com.higgsup.xshop.common.ErrorCode;
import com.higgsup.xshop.common.WebUtil;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Collections;

@ControllerAdvice
public class GlobalExceptionHandler {

  private final Logger logger = LoggerFactory
      .getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(value = { BusinessException.class })
  public ResponseEntity<IPagedResponse> handleBusinessException(
      HttpServletRequest request, BusinessException exception) {

    StringBuilder apiLog = new StringBuilder();
    apiLog.append("Rest API: ").append(request.getRequestURL().toString())
        .append("\n");
    if ("api/auth/login".equalsIgnoreCase(request.getRequestURI())
        || "post".equalsIgnoreCase(request.getMethod())) {
      apiLog.append("Body:").append(
          WebUtil.getRequestBody((ContentCachingRequestWrapper) request))
          .append("\n");
      for (String header : Collections.list(request.getHeaderNames())) {
        apiLog.append(header).append(":").append(request.getHeader(header))
            .append("\n");
      }
    }

    logger.error(apiLog.toString());

    ResponseMessage<Object> responseMessage = new ResponseMessage<>();

    responseMessage.setMessageCode(
        String.valueOf(exception.getErrorCode().getErrorCode()));
    responseMessage.setMessageString(exception.getMessage());

    return ResponseEntity.ok(new IPagedResponse<>(responseMessage));
  }

  @ExceptionHandler(value = { Exception.class })
  public ResponseEntity<IPagedResponse> handleException(
      HttpServletRequest request, Exception exception) {

    logger.error("uri: " + request.getRequestURI());
    logger.error("method: " + request.getMethod());

    logger.error("error: " + exception.getMessage(), exception.getCause());

    ResponseMessage<Object> responseMessage = new ResponseMessage<>();

    if (exception instanceof ConstraintViolationException) {
      responseMessage.setMessageCode(
          String.valueOf(ErrorCode.VALIDATION.getErrorCode()));
      responseMessage.setMessageString(exception.getMessage());
    } else {
      responseMessage.setMessageCode(
          String.valueOf(ErrorCode.GLOBAL.getErrorCode()));
      responseMessage.setMessageString("Error system.");
    }

    return ResponseEntity.ok(new IPagedResponse<>(responseMessage));
  }

}

