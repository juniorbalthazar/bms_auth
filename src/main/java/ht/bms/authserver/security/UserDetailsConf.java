package ht.bms.authserver.security;

import ht.bms.authserver.domain.repo.RetrytableRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class UserDetailsConf implements ReactiveUserDetailsService {

    private final RetrytableRepo repo;

    public Mono<UserDetails> findByUsername(String username) {
        Optional<User> userD= repo.findByUserName(username)
                .map(user -> new User(user.getEmail(), user.getPassword(), Collections.singleton(
                        new SimpleGrantedAuthority(user.getBmsRole().getRoleName())
                )));
        var userCheck = userD.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return Mono.just(userD.get());

    }

/*    public Mono<UserDetails> findByUsername(String username) {
        Optional<UserDetailsMore> userD= repo.findByUserName(username)
                .map(user -> new UserDetailsMore(user.getEmail(), new BCryptPasswordEncoder().encode(user.getPassword()), Collections.singleton(
                        new SimpleGrantedAuthority(user.getBmsRole().getRoleName())
                ),user.getBmsUser().getUserId(),user.getBmsUser().getInstitutionId()));
        var userCheck = userD.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return Mono.just(userD.get());

    }*/


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
