package ht.bms.authserver.service.mapper;

import ht.bms.authserver.domain.BmsAccount;
import ht.bms.authserver.domain.BmsUser;
import ht.bms.authserver.model.AccountBean;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-02T22:34:40-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class AuthMapperImpl implements AuthMapper {

    @Override
    public AccountBean toAccountDto(BmsAccount account) {
        if ( account == null ) {
            return null;
        }

        AccountBean.AccountBeanBuilder accountBean = AccountBean.builder();

        if ( account.getEmail() != null ) {
            accountBean.username( account.getEmail() );
        }
        else {
            accountBean.username( "" );
        }
        BigDecimal userId = accountBmsUserUserId( account );
        if ( userId != null ) {
            accountBean.userId( userId );
        }
        else {
            accountBean.userId( new BigDecimal( "" ) );
        }
        BigDecimal institutionId = accountBmsUserInstitutionId( account );
        if ( institutionId != null ) {
            accountBean.institutionId( institutionId );
        }
        else {
            accountBean.institutionId( new BigDecimal( "" ) );
        }

        return accountBean.build();
    }

    private BigDecimal accountBmsUserUserId(BmsAccount bmsAccount) {
        if ( bmsAccount == null ) {
            return null;
        }
        BmsUser bmsUser = bmsAccount.getBmsUser();
        if ( bmsUser == null ) {
            return null;
        }
        BigDecimal userId = bmsUser.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }

    private BigDecimal accountBmsUserInstitutionId(BmsAccount bmsAccount) {
        if ( bmsAccount == null ) {
            return null;
        }
        BmsUser bmsUser = bmsAccount.getBmsUser();
        if ( bmsUser == null ) {
            return null;
        }
        BigDecimal institutionId = bmsUser.getInstitutionId();
        if ( institutionId == null ) {
            return null;
        }
        return institutionId;
    }
}
