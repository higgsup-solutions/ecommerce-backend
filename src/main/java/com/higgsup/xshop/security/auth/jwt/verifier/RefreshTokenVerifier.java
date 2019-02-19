package com.higgsup.xshop.security.auth.jwt.verifier;

import com.higgsup.xshop.common.TokenType;
import com.higgsup.xshop.entity.UserToken;
import com.higgsup.xshop.repository.UserTokenRepository;
import org.springframework.stereotype.Component;

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
        UserToken userToken = userTokenRepository
            .findByUserIdAndType(Integer.valueOf(userId), TokenType.REFRESH);
        return userToken != null && userToken.getToken().equals(refreshToken);
    }
}
