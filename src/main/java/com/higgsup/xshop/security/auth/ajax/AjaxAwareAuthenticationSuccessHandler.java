package com.higgsup.xshop.security.auth.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.higgsup.xshop.common.TokenType;
import com.higgsup.xshop.entity.UserToken;
import com.higgsup.xshop.security.model.UserContext;
import com.higgsup.xshop.security.model.token.JwtToken;
import com.higgsup.xshop.security.model.token.JwtTokenFactory;
import com.higgsup.xshop.service.IUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AjaxAwareAuthenticationSuccessHandler
 * 
 * @author vladimir.stankovic
 *
 *         Aug 3, 2016
 */
@Component
public class AjaxAwareAuthenticationSuccessHandler implements
    AuthenticationSuccessHandler {
    private final ObjectMapper mapper;
    private final JwtTokenFactory tokenFactory;
    private final IUserTokenService userTokenService;

    @Autowired
    public AjaxAwareAuthenticationSuccessHandler(final ObjectMapper mapper,
        final JwtTokenFactory tokenFactory,
        IUserTokenService userTokenService) {
        this.mapper = mapper;
        this.tokenFactory = tokenFactory;
        this.userTokenService = userTokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException,
        ServletException {
        UserContext userContext = (UserContext) authentication.getPrincipal();
        
        JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);
        JwtToken refreshToken = tokenFactory.createRefreshToken(userContext);

        List<UserToken> userTokens = new ArrayList<>();

        UserToken accessTokenEntity = new UserToken();
        accessTokenEntity.setUserId(userContext.getUserId());
        accessTokenEntity.setToken(accessToken.getToken());
        accessTokenEntity.setType(TokenType.ACCESS);
        userTokens.add(accessTokenEntity);

        UserToken refreshTokenEntity = new UserToken();
        refreshTokenEntity.setUserId(userContext.getUserId());
        refreshTokenEntity.setToken(refreshToken.getToken());
        refreshTokenEntity.setType(TokenType.REFRESH);
        userTokens.add(refreshTokenEntity);

        userTokenService.saveAll(userTokens);

        Map<String, String> tokenMap = new HashMap<String, String>();
        tokenMap.put("token", accessToken.getToken());
        tokenMap.put("refreshToken", refreshToken.getToken());

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getWriter(), tokenMap);

        clearAuthenticationAttributes(request);
    }

    /**
     * Removes temporary authentication-related data which may have been stored
     * in the session during the authentication process..
     * 
     */
    protected final void clearAuthenticationAttributes(
        HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
