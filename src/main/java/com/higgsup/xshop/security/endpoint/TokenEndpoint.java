package com.higgsup.xshop.security.endpoint;

import com.higgsup.xshop.common.ApplicationSecurityProperty;
import com.higgsup.xshop.common.TokenType;
import com.higgsup.xshop.entity.Role;
import com.higgsup.xshop.entity.User;
import com.higgsup.xshop.entity.UserToken;
import com.higgsup.xshop.security.auth.ajax.LoginRequest;
import com.higgsup.xshop.security.auth.jwt.extractor.TokenExtractor;
import com.higgsup.xshop.security.auth.jwt.verifier.TokenVerifier;
import com.higgsup.xshop.security.config.WebSecurityConfig;
import com.higgsup.xshop.security.exception.InvalidJwtToken;
import com.higgsup.xshop.security.exception.JwtExpiredTokenException;
import com.higgsup.xshop.security.model.UserContext;
import com.higgsup.xshop.security.model.token.AccessJwtToken;
import com.higgsup.xshop.security.model.token.JwtToken;
import com.higgsup.xshop.security.model.token.JwtTokenFactory;
import com.higgsup.xshop.security.model.token.RawAccessJwtToken;
import com.higgsup.xshop.security.model.token.RefreshToken;
import com.higgsup.xshop.service.IUserService;
import com.higgsup.xshop.service.IUserTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * RefreshTokenEndpoint
 * 
 * @author vladimir.stankovic
 *
 * Aug 17, 2016
 */
@RestController
public class TokenEndpoint {

    private final JwtTokenFactory tokenFactory;
    private final ApplicationSecurityProperty applicationSecurityProperty;
    private final IUserService userService;
    private final IUserTokenService userTokenService;
    private final TokenExtractor tokenExtractor;
    private final TokenVerifier refreshTokenVerifier;
    private final TokenVerifier accessTokenVerifier;

    public TokenEndpoint(
        JwtTokenFactory tokenFactory,
        ApplicationSecurityProperty applicationSecurityProperty,
        IUserService userService,
        IUserTokenService userTokenService,
        TokenExtractor tokenExtractor,
        TokenVerifier refreshTokenVerifier,
        TokenVerifier accessTokenVerifier) {
        this.tokenFactory = tokenFactory;
        this.applicationSecurityProperty = applicationSecurityProperty;
        this.userService = userService;
        this.userTokenService = userTokenService;
        this.tokenExtractor = tokenExtractor;
        this.refreshTokenVerifier = refreshTokenVerifier;
        this.accessTokenVerifier = accessTokenVerifier;
    }

    @GetMapping("/api/auth/token")
    public JwtToken refreshToken(
        HttpServletRequest request, HttpServletResponse response) throws IOException,
        ServletException {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.AUTHENTICATION_HEADER_NAME));
        
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, applicationSecurityProperty.getJwt().getTokenSigningKey()).orElseThrow(() -> new InvalidJwtToken());

        String userId = refreshToken.getUserId();
        if (!refreshTokenVerifier.verify(userId, tokenPayload)) {
            throw new InvalidJwtToken();
        }

        String subject = refreshToken.getSubject();
        User user = userService.getByEmail(subject);
        if (user == null)
            throw new UsernameNotFoundException("User not found: " + subject);
        Role role = user.getRole();
        if (role == null)
            throw new InsufficientAuthenticationException(
                "User has no roles assigned");
        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(
            role.authority());
        authorities.add(simpleGrantedAuthority);

        UserContext userContext = UserContext
            .create(user.getId(), user.getEmail(), authorities);

        AccessJwtToken accessJwtToken = tokenFactory.createAccessJwtToken(userContext);

        UserToken accessToken = userTokenService
            .findByUserIdAndType(user.getId(), TokenType.ACCESS);
        accessToken.setToken(accessJwtToken.getToken());

        userTokenService.save(accessToken);

        return accessJwtToken;
    }

    @PostMapping("/api/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.AUTHENTICATION_HEADER_NAME));
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        try {
            Jws<Claims> jwsClaims = rawToken.parseClaims(applicationSecurityProperty.getJwt().getTokenSigningKey());
            String id = jwsClaims.getBody().getId();
            if(accessTokenVerifier.verify(id, tokenPayload)) {
                userTokenService.deleteByUserId(Integer.valueOf(id));
            }
        } catch (BadCredentialsException | JwtExpiredTokenException ex) {
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw e;
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/auth/login")
    public void login(@RequestBody LoginRequest loginRequest) {

    }
}
