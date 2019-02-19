package com.higgsup.xshop.security.auth.jwt.verifier;

import com.higgsup.xshop.common.TokenType;
import com.higgsup.xshop.entity.UserToken;
import com.higgsup.xshop.repository.UserTokenRepository;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenVerifier implements TokenVerifier {

  private final UserTokenRepository userTokenRepository;

  public AccessTokenVerifier(
      UserTokenRepository userTokenRepository) {
    this.userTokenRepository = userTokenRepository;
  }

  @Override
  public boolean verify(String userId, String accessToken) {
    UserToken userToken = userTokenRepository
        .findByUserIdAndType(Integer.valueOf(userId), TokenType.ACCESS);
    return userToken != null && userToken.getToken().equals(accessToken);
  }
}
