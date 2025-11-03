package ht.bms.authserver.service.facade;

import ht.bms.authserver.domain.repo.RetrytableRepo;
import ht.bms.authserver.exception.InvalidInputException;
import ht.bms.authserver.exception.RecordNotFoundException;
import ht.bms.authserver.model.AccountBean;
import ht.bms.authserver.service.mapper.AuthMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.AssertTrue;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Transactional
@Service
public class AuthService {

    private RetrytableRepo repo;
    private AuthMapper mapper;

    public AuthService(RetrytableRepo repo,AuthMapper mapper){
        this.repo = repo;
        this.mapper = mapper;

    }

    public Mono<AccountBean> findByUsermane(String username){
        return Mono.just(repo.findByUserName(username)
                .map(mapper::toAccountDto)
                .orElseThrow(()->new RecordNotFoundException("No record found for the "+username)));
    }

}
