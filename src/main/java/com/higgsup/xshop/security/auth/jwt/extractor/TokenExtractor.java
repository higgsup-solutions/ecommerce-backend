package com.higgsup.xshop.security.auth.jwt.extractor;

/**
 * Implementations of this interface should always return raw xshop-64 encoded
 * representation of JWT Token.
 * 
 * @author vladimir.stankovic
 *
 * Aug 5, 2016
 */
public interface TokenExtractor {
    public String extract(String payload);
}
