package com.higgsup.base.security.auth.jwt;

import com.higgsup.base.common.ApplicationSecurityProperty;
import com.higgsup.base.repository.UserTokenRepository;
import com.higgsup.base.security.auth.JwtAuthenticationToken;
import com.higgsup.base.security.auth.jwt.verifier.TokenVerifier;
import com.higgsup.base.security.exception.InvalidJwtToken;
import com.higgsup.base.security.model.UserContext;
import com.higgsup.base.security.model.token.RawAccessJwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@SuppressWarnings("unchecked")
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final ApplicationSecurityProperty applicationSecurityProperty;
    private final UserTokenRepository userTokenRepository;
    private final TokenVerifier accessTokenVerifier;
    
    @Autowired
    public JwtAuthenticationProvider(ApplicationSecurityProperty applicationSecurityProperty,
        UserTokenRepository userTokenRepository,
        TokenVerifier accessTokenVerifier) {
        this.applicationSecurityProperty = applicationSecurityProperty;
        this.userTokenRepository = userTokenRepository;
        this.accessTokenVerifier = accessTokenVerifier;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws
        AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(applicationSecurityProperty.getJwt().getTokenSigningKey());
        String subject = jwsClaims.getBody().getSubject();
        String id = jwsClaims.getBody().getId();
        List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
        List<GrantedAuthority> authorities = scopes.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        if (!accessTokenVerifier.verify(id, rawAccessToken.getToken())) {
            throw new InvalidJwtToken();
        }

        UserContext context = UserContext.create(Long.valueOf(id), subject, authorities);

        return new JwtAuthenticationToken(context, context.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
