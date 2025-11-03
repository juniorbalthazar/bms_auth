package ht.bms.authserver.service.handles;


import ht.bms.authserver.domain.BmsAccount;
import ht.bms.authserver.domain.repo.RetrytableRepo;
import ht.bms.authserver.model.AccountBean;
import ht.bms.authserver.model.LoginRequest;
import ht.bms.authserver.model.LoginResponse;
import ht.bms.authserver.security.CurrentUser;
import ht.bms.authserver.security.TokenProvider;
import ht.bms.authserver.service.facade.AuthService;
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
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.http.HttpHeaders;
import java.security.Principal;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class AuthHandler {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(AuthHandler.class);
    private final PasswordEncoder passwordEncoder;
    private final ReactiveUserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;
    private final AuthService service;

    @Autowired
    private CurrentUser currentUser;



    public AuthHandler(PasswordEncoder passwordEncoder, ReactiveUserDetailsService userDetailsService, TokenProvider tokenProvider,AuthService service) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
        this.service = service;
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

    public Mono<ServerResponse> isAuth(ServerRequest request) {
        var token =request.exchange().getRequest().getHeaders().getFirst(AUTHORIZATION);
               if (token ==null || !token.startsWith("Bearer "))
                    return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        return ServerResponse.ok()
                .body(service.findByUsermane(
                      currentUser.isTokenValid(token.substring( "Bearer ".length()))
                     ), AccountBean.class);


    }



}
