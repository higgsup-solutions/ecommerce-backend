package com.higgsup.base.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * 
 * @author vladimir.stankovic
 *
 * Aug 3, 2016
 */
public class WebUtil {
    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    private static final String X_REQUESTED_WITH = "X-Requested-With";

    private static final String CONTENT_TYPE = "Content-type";
    private static final String CONTENT_TYPE_JSON = "application/json";

    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);

    public static boolean isAjax(HttpServletRequest request) {
        return XML_HTTP_REQUEST.equals(request.getHeader(X_REQUESTED_WITH));
    }

    public static boolean isAjax(SavedRequest request) {
        return request.getHeaderValues(X_REQUESTED_WITH).contains(XML_HTTP_REQUEST);
    }

    public static boolean isContentTypeJson(SavedRequest request) {
        return request.getHeaderValues(CONTENT_TYPE).contains(CONTENT_TYPE_JSON);
    }

    public static String getRequestBody(
        final ContentCachingRequestWrapper wrapper) {
        String payload = null;
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                try {
                    int maxLength = buf.length > 500 ? 500 : buf.length;
                    payload = new String(buf, 0, maxLength,
                        wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                    logger.error("UnsupportedEncoding.", e);
                }
            }
        }
        return payload;
    }
}
