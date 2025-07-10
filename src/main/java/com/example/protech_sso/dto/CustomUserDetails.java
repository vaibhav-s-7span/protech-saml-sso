package com.example.protech_sso.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public record CustomUserDetails(AuthResponse authResponse) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authResponse.getWebRoles().stream()
                .map(webRole -> {
                    // Convert to Spring Security role format: ROLE_<NAME_IN_UPPERCASE_WITH_UNDERSCORES>
                    String roleName = "ROLE_" + webRole.getName()
                            .toUpperCase()
                            .replace(" ", "_")
                            .replace("-", "_");
                    return new SimpleGrantedAuthority(roleName);
                })
                .collect(Collectors.toList());
    }


    @Override
    public String getPassword() {
        return null; // Not used for token-based auth
    }

    @Override
    public String getUsername() {
        return authResponse.getWebLoginName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}