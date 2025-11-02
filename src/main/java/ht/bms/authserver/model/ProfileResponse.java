package ht.bms.authserver.model;

import java.util.Set;

public record ProfileResponse(String username, Set<String> roles) {
}
