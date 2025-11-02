package ht.bms.authserver.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {

    private final JwtService jwtService;

    public CurrentUser(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String getUsername(String token) {
        return jwtService.extractUsername(token);
    }

    public String isTokenValid(String token) {
            if (jwtService.isTokenValid(token))
                return jwtService.extractUsername(token);
        return null;
    }


    public String getToken(UserDetails userDetails) {
        return jwtService.generateToken(userDetails);
    }


}
