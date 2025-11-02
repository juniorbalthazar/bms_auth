package ht.bms.authserver.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;

public class UserDetailsMore  implements UserDetails {

    public UserDetailsMore(String email, String password, Collection<? extends GrantedAuthority> authorities, BigDecimal userId, BigDecimal institutionId) {
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


    public BigDecimal userId() {
        return null;
    }

    public BigDecimal InstitutionId() {
        return null;
    }
}
