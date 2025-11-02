package ht.bms.authserver.domain.repo;

import ht.bms.authserver.domain.BmsAccount;
import ht.bms.authserver.domain.BmsUser;
import jakarta.transaction.Transactional;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class RetrytableRepo {

    private BmsAccountsRepository accountsRepository;
    private BmsRolesRepository bmsTxPhotoRepository;
    private BmsUsersRepository bmsUsersRepository;



    public RetrytableRepo(BmsAccountsRepository accountsRepository,BmsRolesRepository bmsTxPhotoRepository,BmsUsersRepository bmsUsersRepository) {

        this.accountsRepository = accountsRepository;
        this.bmsTxPhotoRepository = bmsTxPhotoRepository;
        this.bmsUsersRepository = bmsUsersRepository;

    }



    /**
     * find Calendar by year and month
     * @return
     */

    @Retryable(retryFor = SQLException.class, maxAttemptsExpression = "#{${retry-database.max-attempts}}", backoff = @Backoff(delayExpression = "#{${retry-database.backoff}}"))
    public Optional<BmsAccount> findByUserName(String username){
        return accountsRepository.findBmsAccount(username);
    }


}
