package com.higgsup.xshop.security.auth.jwt.verifier;

import com.higgsup.xshop.entity.UserToken;
import com.higgsup.xshop.repository.UserTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * BloomFilterTokenVerifier
 * 
 * @author vladimir.stankovic
 *
 * Aug 17, 2016
 */
@Component
public class RefreshTokenVerifier implements TokenVerifier {

    private final UserTokenRepository userTokenRepository;

    public RefreshTokenVerifier(
        UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    @Override
    public boolean verify(String userId, String refreshToken) {
        Optional<UserToken> userToken = userTokenRepository.findById(Long.valueOf(userId));
        return userToken.map(
            uToken -> uToken.getRefreshToken().equals(refreshToken))
            .orElse(false);
    }
}
