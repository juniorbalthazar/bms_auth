package ht.bms.authserver.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;

public class UserDetailsMore  extends User {

   // public UserDetailsMore(String email, String password, Collection<? extends GrantedAuthority> authorities, BigDecimal userId, BigDecimal institutionId) {
   // }
    private BigDecimal institution;
    private BigDecimal userId;
    public UserDetailsMore(String username, String password, Collection<? extends GrantedAuthority> authorities, BigDecimal userId, BigDecimal institutionId) {
        super(username, password, authorities);
        this.institution =institutionId;
        this.userId = userId;
    }

    public BigDecimal getInstitution() {
        return institution;
    }

    public void setInstitution(BigDecimal institution) {
        this.institution = institution;
    }

    public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }
}
