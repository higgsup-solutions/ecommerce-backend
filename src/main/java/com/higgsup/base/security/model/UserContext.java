package com.higgsup.base.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.List;

public class UserContext {
    private final Long userId;
    private final String username;
    private final List<GrantedAuthority> authorities;

    private UserContext(Long userId, String username,
        List<GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.authorities = authorities;
    }
    
    public static UserContext create(Long userId, String username, List<GrantedAuthority> authorities) {
        if (StringUtils.isEmpty(username)) throw new IllegalArgumentException("Username is blank: " + username);
        return new UserContext(userId, username, authorities);
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
