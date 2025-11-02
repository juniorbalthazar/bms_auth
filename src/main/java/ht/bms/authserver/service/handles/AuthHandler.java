package ht.bms.authserver.service.handles;


import ht.bms.authserver.model.LoginRequest;
import ht.bms.authserver.model.LoginResponse;
import ht.bms.authserver.security.CurrentUser;
import ht.bms.authserver.security.TokenProvider;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.security.Principal;


@Service
public class AuthHandler {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(AuthHandler.class);
    private final PasswordEncoder passwordEncoder;
    private final ReactiveUserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;


    public AuthHandler(PasswordEncoder passwordEncoder, ReactiveUserDetailsService userDetailsService, TokenProvider tokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
    }



    private Mono<UserDetails> validateUser(LoginRequest loginRequest) {
        //log.info("validateUser: " + loginRequest.getEmail() + " " + loginRequest.getPassword());
        return userDetailsService.findByUsername(loginRequest.username())
                .filter(u -> passwordEncoder.matches(loginRequest.password(), u.getPassword()));
    }

    private String generateToken(UserDetails userDetails) {
        return tokenProvider.generateToken(userDetails);
    }

    private LoginResponse loginMapper(UserDetails userDetails) {
        LoginResponse user=new LoginResponse();
        user.setToken(tokenProvider.generateToken(userDetails));
        user.setUsername(userDetails.getUsername());
        user.setAuthorities(
                LoginResponse.AuthoritiesEnum.valueOf(userDetails.getAuthorities().stream()
                        .map(name -> name.getAuthority()).findFirst().get())
        );
        return user;
    }

 public Mono<ServerResponse> login(ServerRequest request) {
      Mono<LoginResponse> user= request.bodyToMono(LoginRequest.class)
                .flatMap(this::validateUser)
                .map(this::loginMapper)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
        return ServerResponse.ok().body(user, LoginResponse.class);
    }

  public Mono<ServerResponse> getProfile(ServerRequest request) {

        Mono<LoginResponse> user= request.principal().map(Principal::getName)
                .flatMap(userDetailsService::findByUsername)
                .map(this::loginMapper)
               .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
      return ServerResponse.ok().body(user, LoginResponse.class);

    }



}
