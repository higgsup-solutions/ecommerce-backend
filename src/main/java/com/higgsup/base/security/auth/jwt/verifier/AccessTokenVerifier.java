package com.higgsup.base.security.auth.jwt.verifier;

import com.higgsup.base.entity.UserToken;
import com.higgsup.base.repository.UserTokenRepository;
import com.higgsup.base.security.exception.JwtExpiredTokenException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccessTokenVerifier implements TokenVerifier {

  private final UserTokenRepository userTokenRepository;

  public AccessTokenVerifier(
      UserTokenRepository userTokenRepository) {
    this.userTokenRepository = userTokenRepository;
  }

  @Override
  public boolean verify(String userId, String accessToken) {
    Optional<UserToken> userToken = userTokenRepository.findById(Long.valueOf(userId));
    return userToken.map(
        uToken -> uToken.getToken().equals(accessToken))
        .orElse(false);
  }
}
