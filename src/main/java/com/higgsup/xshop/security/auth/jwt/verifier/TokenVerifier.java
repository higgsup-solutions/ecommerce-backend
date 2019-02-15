package com.higgsup.xshop.security.auth.jwt.verifier;

/**
 * 
 * @author vladimir.stankovic
 *
 * Aug 17, 2016
 */
public interface TokenVerifier {
    boolean verify(String userId, String token);
}
