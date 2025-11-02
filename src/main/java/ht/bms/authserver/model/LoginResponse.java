package ht.bms.authserver.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class LoginResponse {

    private String token;
    private String username;
    private AuthoritiesEnum authorities;
    public enum AuthoritiesEnum {
        ADMIN("ADMIN"),

        CUSTOMER("CUSTOMER"),

        SUPERVISOR("SUPERVISOR");

        private String value;

        AuthoritiesEnum(String value) {
            this.value = value;
        }


        public String getValue() {
            return value;
        }

        public String toString() {
            return String.valueOf(value);
        }


        public static AuthoritiesEnum fromValue(String value) {
            for (AuthoritiesEnum b : AuthoritiesEnum.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AuthoritiesEnum getAuthorities() {
        return authorities;
    }

    public void setAuthorities(AuthoritiesEnum authorities) {
        this.authorities = authorities;
    }
}
